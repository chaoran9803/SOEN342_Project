import datasource.CsvGateway;
import domain.AppointmentCatalog;
import domain.AvailabilityController;
import domain.BookingController;
import domain.ClinicLocationCatalog;
import domain.DateController;
import domain.PatientCatalog;
import domain.ProfessionalCatalog;
import domain.ReferralCatalog;
import domain.ServiceCatalog;
import presentation.ConsoleUI;

public class Main {

    public static void main(String[] args) {
        PatientCatalog patientCatalog = new PatientCatalog();
        ProfessionalCatalog professionalCatalog = new ProfessionalCatalog();
        ServiceCatalog serviceCatalog = new ServiceCatalog();
        ClinicLocationCatalog locationCatalog = new ClinicLocationCatalog();
        AppointmentCatalog appointmentCatalog = new AppointmentCatalog();
        ReferralCatalog referralCatalog = new ReferralCatalog();
        CsvGateway csvGateway = new CsvGateway();

        serviceCatalog.addService("General consultation", false, true);
        serviceCatalog.addService("Specialist consultation", true, true);
        serviceCatalog.addService("Private consultation", false, false);
        locationCatalog.addLocation("Main Clinic", "Montreal");

        BookingController bookingController = new BookingController(patientCatalog, professionalCatalog,
                serviceCatalog, appointmentCatalog, referralCatalog, csvGateway);
        AvailabilityController availabilityController = new AvailabilityController(professionalCatalog,
                locationCatalog, new DateController(), csvGateway);

        new ConsoleUI(bookingController, availabilityController).start();
    }
}
