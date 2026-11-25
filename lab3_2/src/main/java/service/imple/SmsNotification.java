package service.imple;
import lombok.extern.slf4j.Slf4j;
import service.NotificationService;
@Slf4j
public class SmsNotification implements NotificationService {
    @Override
    public void sendNotification(String message, String recipient){
        //boolean number = recipient.matches("(\\+*)\\d{11}");
        boolean number = recipient.matches("(\\+7|8)\\d{10}");
        if (number){
            log.info("[SMS] На номер {} отправлено сообщение:\n {}", recipient, message);
        }
        else{
            log.error("Неправильно набран номер: {}", recipient);
            if (!recipient.startsWith("+7") && !recipient.startsWith("8")){
                log.warn("Номер должен начинаться с +7 или 8");
            }
            if (recipient.startsWith("+7") && recipient.length() != 12) {
                log.warn("Номер должен содержать 11 цифр.");
            }
            else if (recipient.startsWith("8") && recipient.length() != 11) {
                log.warn("Номер должен содержать 11 цифр.");
            } else if (!recipient.startsWith("8") && !recipient.startsWith("+") && recipient.length() != 11 || (recipient.startsWith("+") && recipient.length() != 12)) {
                log.warn("Номер должен содержать 11 цифр.");
            }
        }
    }
}