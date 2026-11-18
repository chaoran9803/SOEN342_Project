package domain;

import java.time.LocalDate;

public class Patient {

    private String patientId;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String phone;
    private String ramqNumber;

    public Patient(String patientId, String firstName, String lastName, LocalDate dateOfBirth,
                   String phone, String ramqNumber) {
        this.patientId = patientId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.phone = phone;
        this.ramqNumber = ramqNumber;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getName() {
        return firstName + " " + lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getPhone() {
        return phone;
    }

    public String getRamqNumber() {
        return ramqNumber;
    }

    public boolean matches(String text) {
        return getName().toLowerCase().contains(text.toLowerCase())
                || phone.contains(text)
                || ramqNumber.equalsIgnoreCase(text);
    }

    @Override
    public String toString() {
        return patientId + "  " + getName() + "  " + phone + "  RAMQ: " + (ramqNumber.isEmpty() ? "none" : ramqNumber);
    }
}
