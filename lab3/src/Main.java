import service.NotificationService;
import service.imple.EmailNotification;
import service.imple.SmsNotification;
import model.FlightTicket;
import model.Baggage;
import model.Corporate;
import model.User;
import AirlineSystem.AirlineSystem;

public class Main {
    public static void main(String[] args){
        NotificationService emailService = new EmailNotification();
        NotificationService smsService = new SmsNotification();
        AirlineSystem sms = new AirlineSystem(smsService);
        FlightTicket passenger = new User("Петрович", 20000.0, "BUSINESS");
        sms.processingPayment(passenger, "+8(923)1310011");
        AirlineSystem.printGlobalStatistics();
    }
}
