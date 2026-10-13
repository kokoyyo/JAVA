package service.imple;
import service.NotificationService;
public class EmailNotification implements NotificationService {
    public void sendNotification(String message, String recipient){
        System.out.printf("[EMAIL] На адрес %s отправлено письмо:\n %s", recipient, message);
    }
}
