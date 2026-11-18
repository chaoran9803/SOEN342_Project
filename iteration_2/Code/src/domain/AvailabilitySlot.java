package domain;

import java.time.LocalDate;
import java.time.LocalTime;

public class AvailabilitySlot {

    private String slotId;
    private HealthcareProfessional professional;
    private ClinicLocation location;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private Appointment appointment;

    public AvailabilitySlot(String slotId, HealthcareProfessional professional, ClinicLocation location,
                            LocalDate date, LocalTime startTime, LocalTime endTime) {
        this.slotId = slotId;
        this.professional = professional;
        this.location = location;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public boolean isWithin(LocalDate startDate, LocalDate endDate) {
        return (startDate == null || !date.isBefore(startDate))
                && (endDate == null || !date.isAfter(endDate));
    }

    public boolean isAvailable() {
        return appointment == null;
    }

    public boolean isAt(String locationId) {
        return location.getLocationId().equalsIgnoreCase(locationId);
    }

    public void occupy(Appointment appointment) {
        this.appointment = appointment;
    }

    public boolean overlaps(LocalDate otherDate, LocalTime otherStart, LocalTime otherEnd) {
        return date.equals(otherDate) && otherStart.isBefore(endTime) && otherEnd.isAfter(startTime);
    }

    public String getSlotId() {
        return slotId;
    }

    public HealthcareProfessional getProfessional() {
        return professional;
    }

    public ClinicLocation getLocation() {
        return location;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    @Override
    public String toString() {
        return slotId + "  " + date + " " + startTime + "-" + endTime + "  "
                + professional.getType() + " " + professional.getName()
                + " (" + professional.getSpecialtyNames() + ")  " + location.getName()
                + "  " + (isAvailable() ? "Available" : "Booked");
    }
}
