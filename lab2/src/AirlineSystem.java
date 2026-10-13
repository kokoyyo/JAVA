public class AirlineSystem {
    private static int totalCountOfTickets;
    private static double totalRevenue;

    public static int getTotalCountOfTickets() {
        return totalCountOfTickets;
    }

    public static double getTotalRevenue() {
        return totalRevenue;
    }

    public static void processingPayment(FlightTicket ticket, double finalPrice){
        totalRevenue +=finalPrice;
        totalCountOfTickets++;
        System.out.printf("\nОплачен билет для:\n Имя пассажира: %s | Тариф %s | Стоимость билета: %.2f руб",
                ticket.getPassengerName(), ticket.getRate(), finalPrice);
        if (ticket.getAddedBaggage() != null){
            System.out.printf("\nВы добавили багаж %s, стоимость багажа до 23 кг составляет: %.2f руб.", ticket.getAddedBaggage().getName(), ticket.getAddedBaggage().getPriceOfBaggage());
        }
    }
    public static void printGlobalStatistics(){
        System.out.println("\n==========================================");
        System.out.println("   ГЛОБАЛЬНАЯ СТАТИСТИКА ПРОДАЖИ БИЛЕТОВ   ");
        System.out.println("\n==========================================");
        System.out.println("Общее количество купленных билетов: " + getTotalCountOfTickets());
        System.out.println("Общая выручка:" + Math.round(getTotalRevenue() * 100.0)/100.0 + " руб.");
        System.out.println("\n==========================================");
    }

    public static void main(String[] args){
        Baggage baggage= new Baggage("basic", -1000);
        FlightTicket ticket1 = new FlightTicket("Gogol", 15000);
        double finalPrice1 = ticket1.calculateFinalPrice(baggage);
        processingPayment(ticket1, finalPrice1);
        FlightTicket ticket2 = new FlightTicket("Pushkin", 15000);
        double finalPrice2 = ticket2.calculateFinalPrice(baggage);
        processingPayment(ticket2, finalPrice2);
        FlightTicket ticket3= new FlightTicket("Dostoevsky", 15000, "BUSINESS");
        double finalPrice3 = ticket3.registerBaggage(baggage, 25);
        processingPayment(ticket3, finalPrice3);
        printGlobalStatistics();
    }
}
