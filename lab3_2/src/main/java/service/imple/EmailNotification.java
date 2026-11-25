package service.imple;
import lombok.extern.slf4j.Slf4j;
import service.NotificationService;
@Slf4j
public class EmailNotification implements NotificationService {
    public void sendNotification(String message, String recipient){
        boolean email = recipient.matches("([A-Za-z0-9]{1,}[.]{0,1}[A-Za-z0-9]{1,}[A-Za-z0-9]{1,})+@([A-Za-z0-9]{1,}[.]{0,1}[A-Za-z0-9]{1,}[,]{0,1}[A-Za-z0-9]{1,})+[.]{1}[a-z]{2,4}");
        if (email) log.info("[EMAIL] На адрес {} отправлено письмо: {}", recipient, message);
        else{
            log.error("Неверный почтовый адрес: {}", recipient);
            log.info("Почтовый адрес должен быть в формате example@example.com");
        }
    }
}
