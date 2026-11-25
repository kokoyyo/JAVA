package airlineSystem;
import exception.DuplicatePassengerException;
import exception.FlightTicketNotFoundException;
import exception.InsufficientFundsException;
import exception.InvalidEmailException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import model.FlightTicket;
import service.NotificationService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class AirlineSystem {
    private final NotificationService notificationService;
    @Getter
    private static int totalCountOfTickets;
    @Getter
    private static double totalRevenue;
    @Getter
    private final Map<String, FlightTicket> database = new HashMap<>();
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");
    public void registerFlightTicket(String email, FlightTicket ticket) throws DuplicatePassengerException {
        if (email == null || !EMAIL_PATTERN.matcher(email).matches()){
            throw new InvalidEmailException("Неверный email. [Передано: " + email + "]");
        }
        if (database.containsKey(email)){
            throw new DuplicatePassengerException("Пассажир с email "+ email + " уже зарегистрирован.\n Проверьте почту или введите другой адрес");
        }
        database.put(email, ticket);
    }
    public List<FlightTicket> findVIPTickets(double minCost){
        return database.values().stream().filter(ticket -> ticket.calculateFinalPrice() > minCost).toList();
    }
    public long countBusinessTickets(){
        return database.values().stream().filter( ticket -> ticket.getRate().equals("BUSINESS")).count();

    }
    public Map<String, Long> getTicketCountByRate(){
        return database.values().stream().collect(Collectors.groupingBy(FlightTicket::getRate, Collectors.counting()));
    }
    public double calculateTotalRevenue(){
        double total = database.values().stream().mapToDouble(FlightTicket::calculateFinalPrice).sum();
        return Math.round(total*100.0)/100.0;
    }
    public void processPaymentByEmail(String email) throws FlightTicketNotFoundException, InsufficientFundsException{
        FlightTicket ticket= database.get(email);
        java.time.LocalDateTime now = java.time.LocalDateTime.now();
        if (ticket == null){
            throw new FlightTicketNotFoundException("Билет с email " + email + " не был оформлен в системе.");
        }
        double finalPrice = ticket.calculateFinalPrice();
        if (ticket.getMoneyBalance() < finalPrice){
            throw new InsufficientFundsException("На балансе недостаточно средств. [К оплате: " + finalPrice + " рублей. Баланс " + ticket.getMoneyBalance() + " руб.]");
        }
        String message = String.format("%tF, %tT: Билет оплачен на имя %s.\n Тариф [%s]. К оплате: %.2f руб.", now, now,ticket.getPassengerName(),ticket.getRate(), finalPrice);
        notificationService.sendNotification(message,email);
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
