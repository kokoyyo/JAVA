package AirlineSystem;
import model.FlightTicket;
import service.NotificationService;
public class AirlineSystem {
    private final NotificationService notificationService;
    private static int totalCountOfTickets;
    private static double totalRevenue;

    public static int getTotalCountOfTickets() {return totalCountOfTickets; }
    public static double getTotalRevenue() {return totalRevenue;}
    public AirlineSystem(NotificationService notificationService){
        this.notificationService = notificationService;
    }

    public void processingPayment(FlightTicket ticket, String contact) {
        System.out.println("\n===========================Оплата билета===========================");
        double finalPrice = ticket.calculateFinalPrice();

        java.time.LocalDateTime now = java.time.LocalDateTime.now();
        String message = String.format("%tF, %tT: Билет оплачен на имя %s.\n Тариф [%s]. К оплате: %.2f руб.", now, now,ticket.getPassengerName(),ticket.getRate(), finalPrice);
        if (ticket.getAddedBaggage() != null) {
            System.out.printf("\nВы добавили багаж %s, стоимость багажа до 23 кг составляет: %.2f руб.", ticket.getAddedBaggage().getName(), ticket.getAddedBaggage().getPriceOfBaggage());
        }
        notificationService.sendNotification(message, contact);
        totalRevenue += finalPrice;
        totalCountOfTickets++;
        System.out.println("\n====================================================================");
    }

    public static void printGlobalStatistics(){
        System.out.println("\n==========================================");
        System.out.println("   ГЛОБАЛЬНАЯ СТАТИСТИКА ПРОДАЖИ БИЛЕТОВ   ");
        System.out.println("\n==========================================");
        System.out.println("Общее количество купленных билетов: " + getTotalCountOfTickets());
        System.out.println("Общая выручка: " + Math.round(getTotalRevenue() * 100.0)/100.0 + " руб.");
        System.out.println("\n==========================================");
    }

}
