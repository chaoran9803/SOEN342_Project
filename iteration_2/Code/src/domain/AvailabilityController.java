package domain;

import datasource.CsvGateway;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class AvailabilityController {

    private ProfessionalCatalog professionalCatalog;
    private ClinicLocationCatalog locationCatalog;
    private DateController dateController;
    private CsvGateway csvGateway;

    public AvailabilityController(ProfessionalCatalog professionalCatalog, ClinicLocationCatalog locationCatalog,
                                  DateController dateController, CsvGateway csvGateway) {
        this.professionalCatalog = professionalCatalog;
        this.locationCatalog = locationCatalog;
        this.dateController = dateController;
        this.csvGateway = csvGateway;
    }

    public List<AvailabilitySlot> searchAvailability(String professionalId, LocalDate startDate, LocalDate endDate,
                                                     String locationId) {
        List<AvailabilitySlot> result = new ArrayList<>();
        for (HealthcareProfessional professional : professionalCatalog.getProfessionals()) {
            if (professionalId == null || professional.getProfessionalId().equalsIgnoreCase(professionalId)) {
                for (AvailabilitySlot slot : professional.getSlots(startDate, endDate)) {
                    if (slot.isAvailable() && (locationId == null || slot.isAt(locationId))) {
                        result.add(slot);
                    }
                }
            }
        }
        result.sort(Comparator.comparing(AvailabilitySlot::getDate).thenComparing(AvailabilitySlot::getStartTime));
        return result;
    }

    public int makeAvailability(String professionalId, String locationId, LocalDate startDate, LocalDate endDate,
                                RepetitionPattern repetitionPattern, LocalTime startTime, LocalTime endTime) {
        HealthcareProfessional professional = professionalCatalog.findProfessional(professionalId);
        if (professional == null) {
            throw new IllegalArgumentException("Professional " + professionalId + " not found");
        }
        ClinicLocation location = locationCatalog.findLocation(locationId);
        if (location == null) {
            throw new IllegalArgumentException("Location " + locationId + " not found");
        }
        if (!endTime.isAfter(startTime)) {
            throw new IllegalArgumentException("End time must be after start time");
        }
        List<LocalDate> dates = dateController.generateDates(startDate, endDate, repetitionPattern);
        return professional.addAvailability(dates, startTime, endTime, location);
    }

    public int importAvailabilities(String fileName) throws IOException {
        ClinicLocation location = locationCatalog.getLocations().get(0);
        int imported = 0;
        for (String[] row : csvGateway.readRows(fileName)) {
            if (row.length < 7) {
                continue;
            }
            String name = row[0] + " " + row[1];
            HealthcareProfessional professional = professionalCatalog.findProfessionalByName(name);
            if (professional == null) {
                professional = professionalCatalog.addProfessional(name, row[2]);
            }
            professional.addSpecialty(new Specialty(row[3]));
            LocalDate date = LocalDate.parse(row[4]);
            LocalTime startTime = LocalTime.parse(row[5]);
            LocalTime endTime = LocalTime.parse(row[6]);
            if (professional.addSlot(date, startTime, endTime, location) != null) {
                imported++;
            }
        }
        return imported;
    }

    public int exportAvailabilities(String fileName) throws IOException {
        List<String[]> rows = new ArrayList<>();
        for (HealthcareProfessional professional : professionalCatalog.getProfessionals()) {
            String[] names = professional.getName().split(" ", 2);
            for (AvailabilitySlot slot : professional.getSlots()) {
                rows.add(new String[]{
                        names[0],
                        names.length > 1 ? names[1] : "",
                        professional.getType(),
                        professional.getSpecialtyNames(),
                        slot.getDate().toString(),
                        slot.getStartTime().toString(),
                        slot.getEndTime().toString(),
                        slot.getLocation().getName(),
                        slot.isAvailable() ? "Available" : "Booked"
                });
            }
        }
        csvGateway.writeRows(fileName, "First Name,Last Name,Title,Specialty,Date,Start,End,Location,Status", rows);
        return rows.size();
    }

    public List<HealthcareProfessional> getProfessionals() {
        return professionalCatalog.getProfessionals();
    }

    public List<ClinicLocation> getLocations() {
        return locationCatalog.getLocations();
    }
}
