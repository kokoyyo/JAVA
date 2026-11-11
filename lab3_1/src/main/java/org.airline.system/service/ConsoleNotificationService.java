package org.airline.system.service;

public class ConsoleNotificationService implements service.NotificationService {
    @Override
    public void sendNotification(String message, String recipient) {
        System.out.println("[УВЕДОМЛЕНИЕ для " + recipient + "]: " + message);
    }
}