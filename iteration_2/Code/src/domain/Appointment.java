package domain;

import java.time.LocalDateTime;

public class Appointment {

    private String appointmentId;
    private LocalDateTime dateTime;
    private Status status;
    private Patient patient;
    private HealthcareProfessional professional;
    private Service service;
    private AvailabilitySlot slot;

    public Appointment(String appointmentId, Patient patient, HealthcareProfessional professional,
                       Service service, AvailabilitySlot slot, Status status) {
        this.appointmentId = appointmentId;
        this.patient = patient;
        this.professional = professional;
        this.service = service;
        this.slot = slot;
        this.status = status;
        this.dateTime = slot.getDate().atTime(slot.getStartTime());
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public Status getStatus() {
        return status;
    }

    public Patient getPatient() {
        return patient;
    }

    public HealthcareProfessional getProfessional() {
        return professional;
    }

    public Service getService() {
        return service;
    }

    public AvailabilitySlot getSlot() {
        return slot;
    }

    @Override
    public String toString() {
        return appointmentId + "  " + slot.getDate() + " " + slot.getStartTime() + "-" + slot.getEndTime()
                + "  " + patient.getName() + " with " + professional.getType() + " " + professional.getName()
                + "  " + service.getName() + "  " + slot.getLocation().getName() + "  " + status;
    }
}
