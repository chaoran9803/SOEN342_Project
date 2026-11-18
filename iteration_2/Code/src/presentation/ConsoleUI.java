package presentation;

import domain.Appointment;
import domain.AvailabilityController;
import domain.AvailabilitySlot;
import domain.BookingController;
import domain.ClinicLocation;
import domain.HealthcareProfessional;
import domain.MonthlyPattern;
import domain.Patient;
import domain.Referral;
import domain.RepetitionPattern;
import domain.Service;
import domain.WeeklyPattern;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ConsoleUI {

    private Scanner scanner = new Scanner(System.in);
    private BookingController bookingController;
    private AvailabilityController availabilityController;

    public ConsoleUI(BookingController bookingController, AvailabilityController availabilityController) {
        this.bookingController = bookingController;
        this.availabilityController = availabilityController;
    }

    public void start() {
        String choice = "";
        while (!choice.equals("0")) {
            printMenu();
            try {
                choice = read("Choose an option");
                switch (choice) {
                    case "1":
                        importAvailabilities();
                        break;
                    case "2":
                        importAppointments();
                        break;
                    case "3":
                        exportAvailabilities();
                        break;
                    case "4":
                        searchAvailability();
                        break;
                    case "5":
                        makeAvailability();
                        break;
                    case "6":
                        bookAppointment();
                        break;
                    case "7":
                        createReferral();
                        break;
                    case "8":
                        viewAppointments();
                        break;
                    case "0":
                        break;
                    default:
                        System.out.println("Unknown option.");
                }
            } catch (NoSuchElementException e) {
                choice = "0";
            } catch (IOException e) {
                System.out.println("Could not read or write file: " + e.getMessage());
            } catch (RuntimeException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void printMenu() {
        System.out.println();
        System.out.println("=== Appointment Tracking System ===");
        System.out.println("1. Import availabilities from CSV");
        System.out.println("2. Import booked appointments from CSV");
        System.out.println("3. Export availabilities to CSV");
        System.out.println("4. Search availability");
        System.out.println("5. Make availability");
        System.out.println("6. Book appointment");
        System.out.println("7. Create referral");
        System.out.println("8. View appointments");
        System.out.println("0. Exit");
    }

    private void importAvailabilities() throws IOException {
        String fileName = read("File name (e.g. data/availabilities.csv)");
        int count = availabilityController.importAvailabilities(fileName);
        System.out.println("Imported " + count + " availability slot(s).");
    }

    private void importAppointments() throws IOException {
        String fileName = read("File name (e.g. data/appointments.csv)");
        int count = bookingController.importAppointments(fileName);
        System.out.println("Imported " + count + " appointment(s).");
    }

    private void exportAvailabilities() throws IOException {
        String fileName = read("File name (e.g. data/export.csv)");
        int count = availabilityController.exportAvailabilities(fileName);
        System.out.println("Exported " + count + " slot(s) to " + fileName);
    }

    private List<AvailabilitySlot> searchAvailability() {
        printProfessionals();
        String professionalId = readOptional("Professional ID (blank for all)");
        LocalDate startDate = readDate("From date YYYY-MM-DD (blank for no limit)");
        LocalDate endDate = readDate("To date YYYY-MM-DD (blank for no limit)");
        List<AvailabilitySlot> slots = availabilityController.searchAvailability(professionalId, startDate, endDate, null);
        if (slots.isEmpty()) {
            System.out.println("No available slots found.");
        }
        for (AvailabilitySlot slot : slots) {
            System.out.println(slot);
        }
        return slots;
    }

    private void makeAvailability() {
        printProfessionals();
        String professionalId = read("Professional ID");
        for (ClinicLocation location : availabilityController.getLocations()) {
            System.out.println(location);
        }
        String locationId = read("Location ID");
        LocalDate startDate = LocalDate.parse(read("Start date YYYY-MM-DD"));
        LocalDate endDate = LocalDate.parse(read("End date YYYY-MM-DD"));
        RepetitionPattern pattern = readPattern();
        LocalTime startTime = LocalTime.parse(read("Start time HH:MM"));
        LocalTime endTime = LocalTime.parse(read("End time HH:MM"));
        int count = availabilityController.makeAvailability(professionalId, locationId, startDate, endDate,
                pattern, startTime, endTime);
        System.out.println("Created " + count + " availability slot(s).");
    }

    private void bookAppointment() {
        String patientId = selectPatient();
        List<AvailabilitySlot> slots = searchAvailability();
        if (slots.isEmpty()) {
            return;
        }
        String slotId = read("Slot ID");
        AvailabilitySlot slot = null;
        for (AvailabilitySlot s : slots) {
            if (s.getSlotId().equalsIgnoreCase(slotId)) {
                slot = s;
                break;
            }
        }
        if (slot == null) {
            System.out.println("Slot " + slotId + " is not in the list.");
            return;
        }
        for (Service service : bookingController.getServices()) {
            System.out.println(service);
        }
        String serviceId = read("Service ID");
        Appointment appointment = bookingController.bookAppointment(patientId,
                slot.getProfessional().getProfessionalId(), serviceId, slot.getLocation().getLocationId(), slotId);
        System.out.println("Appointment booked: " + appointment);
    }

    private void createReferral() {
        String patientId = selectPatient();
        String specialty = read("Requested specialty");
        String reason = read("Clinical reason");
        String priority = read("Priority (e.g. Routine, Urgent)");
        Referral referral = bookingController.createReferral(patientId, reason, priority, specialty);
        System.out.println("Referral created: " + referral);
    }

    private void viewAppointments() {
        if (bookingController.getAppointments().isEmpty()) {
            System.out.println("No appointments.");
        }
        for (Appointment appointment : bookingController.getAppointments()) {
            System.out.println(appointment);
        }
    }

    private String selectPatient() {
        String text = read("Search patient by name, phone or RAMQ (blank for all)");
        for (Patient patient : bookingController.searchPatients(text)) {
            System.out.println(patient);
        }
        String patientId = readOptional("Patient ID (blank to register a new patient)");
        if (patientId != null) {
            return patientId;
        }
        String firstName = read("First name");
        String lastName = read("Last name");
        LocalDate dateOfBirth = readDate("Date of birth YYYY-MM-DD (blank if unknown)");
        String phone = read("Telephone");
        String ramqNumber = read("RAMQ number (blank if none)");
        Patient patient = bookingController.registerPatient(firstName, lastName, dateOfBirth, phone, ramqNumber);
        System.out.println("Registered: " + patient);
        return patient.getPatientId();
    }

    private RepetitionPattern readPattern() {
        String type = read("Repeat 1) on days of the week  2) monthly");
        if (type.equals("2")) {
            return new MonthlyPattern(Integer.parseInt(read("Day of the month (1-31)")));
        }
        List<DayOfWeek> days = new ArrayList<>();
        for (String day : read("Days of the week (e.g. MON,WED,FRI)").toUpperCase().split(",")) {
            days.add(toDayOfWeek(day.trim()));
        }
        return new WeeklyPattern(days);
    }

    private DayOfWeek toDayOfWeek(String text) {
        for (DayOfWeek day : DayOfWeek.values()) {
            if (text.length() >= 3 && day.name().startsWith(text)) {
                return day;
            }
        }
        throw new IllegalArgumentException("Unknown day: " + text);
    }

    private void printProfessionals() {
        for (HealthcareProfessional professional : availabilityController.getProfessionals()) {
            System.out.println(professional);
        }
    }

    private String read(String label) {
        System.out.print(label + ": ");
        return scanner.nextLine().trim();
    }

    private String readOptional(String label) {
        String value = read(label);
        return value.isEmpty() ? null : value;
    }

    private LocalDate readDate(String label) {
        String value = read(label);
        return value.isEmpty() ? null : LocalDate.parse(value);
    }
}
