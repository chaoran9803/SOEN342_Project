package domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PatientCatalog {

    private List<Patient> patients = new ArrayList<>();

    public Patient findPatient(String patientId) {
        for (Patient patient : patients) {
            if (patient.getPatientId().equalsIgnoreCase(patientId)) {
                return patient;
            }
        }
        return null;
    }

    public Patient findPatientByName(String name) {
        for (Patient patient : patients) {
            if (patient.getName().equalsIgnoreCase(name)) {
                return patient;
            }
        }
        return null;
    }

    public List<Patient> searchPatients(String text) {
        List<Patient> result = new ArrayList<>();
        for (Patient patient : patients) {
            if (patient.matches(text)) {
                result.add(patient);
            }
        }
        return result;
    }

    public Patient registerPatient(String firstName, String lastName, LocalDate dateOfBirth,
                                   String phone, String ramqNumber) {
        Patient patient = new Patient(generateId(), firstName, lastName, dateOfBirth, phone, ramqNumber);
        patients.add(patient);
        return patient;
    }

    private String generateId() {
        return "PT" + (patients.size() + 1);
    }
}
