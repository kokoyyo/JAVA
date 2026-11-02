package service.imple;
import service.NotificationService;

public class SmsNotification implements NotificationService {
    @Override
    public void sendNotification(String message, String recipient){
        //boolean number = recipient.matches("(\\+*)\\d{11}");
        boolean number = recipient.matches("(\\+7|8)\\d{10}");
        if (number){
            System.out.printf("\n[SMS] На номер %s отправлено сообщение:\n %s", recipient, message);
        }
        else{
            System.out.printf("\n[ERROR] Неправильно набран номер: %s\n", recipient);
            if (!recipient.startsWith("+7") && !recipient.startsWith("8")){
                System.out.println("Номер должен начинаться с +7 или 8");
            }
            if (recipient.startsWith("+7") && recipient.length() != 12) {
                System.out.println("Номер должен содержать 11 цифр.");
            }
            else if (recipient.startsWith("8") && recipient.length() != 11) {
                System.out.println("Номер должен содержать 11 цифр.");
            } else if (!recipient.startsWith("8") && !recipient.startsWith("+") && recipient.length() != 11 || (recipient.startsWith("+") && recipient.length() != 12)) {
                System.out.println("Номер должен содержать 11 цифр.");
            }
        }
    }
}