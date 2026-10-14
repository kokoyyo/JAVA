package service.imple;
import service.NotificationService;
public class EmailNotification implements NotificationService {
    public void sendNotification(String message, String recipient){
        boolean email = recipient.matches("([A-Za-z0-9]{1,}[.]{0,1}[A-Za-z0-9]{1,}[,]{0,1}[A-Za-z0-9]{1,})+@([A-Za-z0-9]{1,}[.]{0,1}[A-Za-z0-9]{1,}[,]{0,1}[A-Za-z0-9]{1,})+[.]{1}[a-z]{2,4}");
        if (email) System.out.printf("[EMAIL] На адрес %s отправлено письмо:\n %s", recipient, message);
        else{
            System.out.println("\nНеверный почтовый адрес.\n");
        }
    }
}
