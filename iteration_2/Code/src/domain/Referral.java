package domain;

public class Referral {

    private String referralId;
    private Patient patient;
    private Specialty specialty;
    private String clinicalReason;
    private String priority;
    private String status;

    public Referral(String referralId, Patient patient, Specialty specialty, String clinicalReason, String priority) {
        this.referralId = referralId;
        this.patient = patient;
        this.specialty = specialty;
        this.clinicalReason = clinicalReason;
        this.priority = priority;
        this.status = "Open";
    }

    public boolean isValidFor(Patient otherPatient, HealthcareProfessional professional) {
        return status.equals("Open")
                && patient == otherPatient
                && professional.hasSpecialty(specialty.getName());
    }

    public String getReferralId() {
        return referralId;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return referralId + "  " + patient.getName() + " -> " + specialty.getName()
                + "  priority: " + priority + "  reason: " + clinicalReason + "  " + status;
    }
}
