package service.imple;
import service.NotificationService;

public class SmsNotification implements NotificationService {
    @Override
    public void sendNotification(String message, String recipient){
        System.out.printf("[SMS] На номер %s отправлено сообщение:\n %s", recipient, message);
    }
}
