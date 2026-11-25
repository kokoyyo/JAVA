package service;
import lombok.extern.slf4j.Slf4j;
@Slf4j
public class ConsoleNotificationService implements service.NotificationService {
    @Override
    public void sendNotification(String message, String recipient) {
        log.info("[УВЕДОМЛЕНИЕ для {}]: {}", recipient, message);
    }
}