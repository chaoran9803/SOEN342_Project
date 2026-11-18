package domain;

import datasource.CsvGateway;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class BookingController {

    private PatientCatalog patientCatalog;
    private ProfessionalCatalog professionalCatalog;
    private ServiceCatalog serviceCatalog;
    private AppointmentCatalog appointmentCatalog;
    private ReferralCatalog referralCatalog;
    private CsvGateway csvGateway;

    public BookingController(PatientCatalog patientCatalog, ProfessionalCatalog professionalCatalog,
                             ServiceCatalog serviceCatalog, AppointmentCatalog appointmentCatalog,
                             ReferralCatalog referralCatalog, CsvGateway csvGateway) {
        this.patientCatalog = patientCatalog;
        this.professionalCatalog = professionalCatalog;
        this.serviceCatalog = serviceCatalog;
        this.appointmentCatalog = appointmentCatalog;
        this.referralCatalog = referralCatalog;
        this.csvGateway = csvGateway;
    }

    public Appointment bookAppointment(String patientId, String professionalId, String serviceId,
                                       String locationId, String slotId) {
        Patient patient = patientCatalog.findPatient(patientId);
        if (patient == null) {
            throw new IllegalArgumentException("Patient " + patientId + " not found");
        }
        HealthcareProfessional professional = professionalCatalog.findProfessional(professionalId);
        if (professional == null) {
            throw new IllegalArgumentException("Professional " + professionalId + " not found");
        }
        Service service = serviceCatalog.findService(serviceId);
        if (service == null) {
            throw new IllegalArgumentException("Service " + serviceId + " not found");
        }
        AvailabilitySlot slot = professional.findSlot(slotId);
        if (slot == null || !slot.isAt(locationId)) {
            throw new IllegalArgumentException("Slot " + slotId + " not found");
        }
        if (service.requiresReferral() && !referralCatalog.hasValidReferral(patient, professional)) {
            throw new IllegalArgumentException(service.getName() + " requires a valid referral");
        }
        Appointment appointment = appointmentCatalog.createIfAvailable(patient, professional, service, slot,
                Status.BOOKED);
        if (appointment == null) {
            throw new IllegalArgumentException("Slot " + slotId + " is no longer available");
        }
        return appointment;
    }

    public List<Patient> searchPatients(String text) {
        return patientCatalog.searchPatients(text);
    }

    public Patient registerPatient(String firstName, String lastName, LocalDate dateOfBirth,
                                   String phone, String ramqNumber) {
        return patientCatalog.registerPatient(firstName, lastName, dateOfBirth, phone, ramqNumber);
    }

    public Referral createReferral(String patientId, String clinicalReason, String priority, String specialty) {
        Patient patient = patientCatalog.findPatient(patientId);
        if (patient == null) {
            throw new IllegalArgumentException("Patient " + patientId + " not found");
        }
        return referralCatalog.createReferral(patient, new Specialty(specialty), clinicalReason, priority);
    }

    public int importAppointments(String fileName) throws IOException {
        Service service = serviceCatalog.getServices().get(0);
        int imported = 0;
        for (String[] row : csvGateway.readRows(fileName)) {
            if (row.length < 7) {
                continue;
            }
            HealthcareProfessional professional = professionalCatalog.findProfessionalByName(row[0]);
            if (professional == null) {
                continue;
            }
            AvailabilitySlot slot = professional.findSlot(LocalDate.parse(row[1]), LocalTime.parse(row[2]),
                    LocalTime.parse(row[3]));
            if (slot == null) {
                continue;
            }
            Patient patient = patientCatalog.findPatientByName(row[4]);
            if (patient == null) {
                String[] names = row[4].split(" ", 2);
                String ramqNumber = row[6].length() > 1 ? row[6] : "";
                patient = patientCatalog.registerPatient(names[0], names.length > 1 ? names[1] : "", null, row[5], ramqNumber);
            }
            if (appointmentCatalog.createIfAvailable(patient, professional, service, slot, Status.BOOKED) != null) {
                imported++;
            }
        }
        return imported;
    }

    public List<Service> getServices() {
        return serviceCatalog.getServices();
    }

    public List<Appointment> getAppointments() {
        return appointmentCatalog.getAppointments();
    }
}
