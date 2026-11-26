package service.imple;
import lombok.extern.slf4j.Slf4j;
import service.NotificationService;
@Slf4j
public class EmailNotification implements NotificationService {
    public void sendNotification(String message, String recipient){
        boolean email = recipient.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$");
        if (email) log.info("[EMAIL] На адрес {} отправлено письмо: {}", recipient, message);
        else{
            log.error("Неверный почтовый адрес: {}", recipient);
            log.info("Почтовый адрес должен быть в формате example@example.com");
        }
    }
}
