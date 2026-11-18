package domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class HealthcareProfessional extends Professional {

    private String type;
    private List<Specialty> specialties = new ArrayList<>();
    private List<AvailabilitySlot> slots = new ArrayList<>();

    public HealthcareProfessional(String professionalId, String name, String type) {
        super(professionalId, name, "");
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void addSpecialty(Specialty specialty) {
        if (!hasSpecialty(specialty.getName())) {
            specialties.add(specialty);
        }
    }

    public boolean hasSpecialty(String specialtyName) {
        for (Specialty specialty : specialties) {
            if (specialty.getName().equalsIgnoreCase(specialtyName)) {
                return true;
            }
        }
        return false;
    }

    public String getSpecialtyNames() {
        List<String> names = new ArrayList<>();
        for (Specialty specialty : specialties) {
            names.add(specialty.getName());
        }
        return String.join("/", names);
    }

    public List<AvailabilitySlot> getSlots() {
        return slots;
    }

    public List<AvailabilitySlot> getSlots(LocalDate startDate, LocalDate endDate) {
        List<AvailabilitySlot> result = new ArrayList<>();
        for (AvailabilitySlot slot : slots) {
            if (slot.matchesCriteria(startDate, endDate, null)) {
                result.add(slot);
            }
        }
        return result;
    }

    public AvailabilitySlot findSlot(String slotId) {
        for (AvailabilitySlot slot : slots) {
            if (slot.getSlotId().equalsIgnoreCase(slotId)) {
                return slot;
            }
        }
        return null;
    }

    public AvailabilitySlot findSlot(LocalDate date, LocalTime startTime, LocalTime endTime) {
        for (AvailabilitySlot slot : slots) {
            if (slot.getDate().equals(date) && slot.getStartTime().equals(startTime)
                    && slot.getEndTime().equals(endTime)) {
                return slot;
            }
        }
        return null;
    }

    public int addAvailability(List<LocalDate> dates, LocalTime startTime, LocalTime endTime, ClinicLocation location) {
        int created = 0;
        for (LocalDate date : dates) {
            if (addSlot(date, startTime, endTime, location) != null) {
                created++;
            }
        }
        return created;
    }

    public AvailabilitySlot addSlot(LocalDate date, LocalTime startTime, LocalTime endTime, ClinicLocation location) {
        if (!endTime.isAfter(startTime)) {
            return null;
        }
        for (AvailabilitySlot slot : slots) {
            if (slot.overlaps(date, startTime, endTime)) {
                return null;
            }
        }
        String slotId = getProfessionalId() + "-" + (slots.size() + 1);
        AvailabilitySlot slot = new AvailabilitySlot(slotId, this, location, date, startTime, endTime);
        slots.add(slot);
        return slot;
    }

    @Override
    public String toString() {
        return getProfessionalId() + "  " + type + " " + getName() + " (" + getSpecialtyNames() + ")";
    }
}
