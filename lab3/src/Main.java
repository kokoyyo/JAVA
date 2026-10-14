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

        NotificationService smsService = new SmsNotification();
        AirlineSystem sms = new AirlineSystem(smsService);
        FlightTicket passenger = new User("Петрович", 20000.0, "comfort");
        sms.processingPayment(passenger, "+89231310011");

        NotificationService smsService2 = new SmsNotification();
        AirlineSystem sms2 = new AirlineSystem(smsService);
        Baggage baggage = new Baggage("basic", 1000);
        FlightTicket passenger2 = new User("Иванов", 5000);
        passenger2.calculateFinalPrice(baggage);
        sms2.processingPayment(passenger2, "+79085977013");

        FlightTicket corporatePassenger = new Corporate("Чкалов", 10000, "Business",2);
        NotificationService emailService = new EmailNotification();
        AirlineSystem email = new AirlineSystem(emailService);
        email.processingPayment(corporatePassenger, "sdsf@mail///.ru");

        AirlineSystem.printGlobalStatistics();
    }
}
