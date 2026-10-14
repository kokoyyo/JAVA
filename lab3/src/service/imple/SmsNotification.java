package service.imple;
import service.NotificationService;

public class SmsNotification implements NotificationService {
    @Override
    public void sendNotification(String message, String recipient){
        boolean number = recipient.matches("(\\+*)\\d{11}");
        if (number){
            System.out.printf("\n[SMS] На номер %s отправлено сообщение:\n %s", recipient, message);
        }
        else{
            System.out.println("\nНеправильно введен номер телефона!\n");
        }
    }
}