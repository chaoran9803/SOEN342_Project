package domain;

import java.util.ArrayList;
import java.util.List;

public class AppointmentCatalog {

    private List<Appointment> appointments = new ArrayList<>();

    public Appointment createIfAvailable(Patient patient, HealthcareProfessional professional, Service service,
                                         AvailabilitySlot slot, Status initialStatus) {
        if (!slot.isAvailable()) {
            return null;
        }
        Appointment appointment = new Appointment(generateId(), patient, professional, service, slot, initialStatus);
        slot.occupy(appointment);
        appointments.add(appointment);
        return appointment;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    private String generateId() {
        return "A" + (appointments.size() + 1);
    }
}
