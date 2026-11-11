import model.Corporate;
import service.NotificationService;
import airlineSystem.AirlineSystem;
import exception.*;
import model.FlightTicket;
import model.Option;
import model.User;
import service.ConsoleNotificationService;

import java.time.LocalDate;
public class Main {
    public static void main(String[] args){
        service.NotificationService service = new ConsoleNotificationService();
        AirlineSystem system = new AirlineSystem(service);
        System.out.println("\n========(Сценарий 1)========\n");
        LocalDate departureDate1 =LocalDate.now().plusDays(14);
        Option baggage = new Option("БАГАЖ", 2350, 23);
        Option handLuggage = new Option("Ручная кладь", 0, 8);
        Option handLuggage2 = new Option("Ручная кладь", 0, 2);

        try {
            FlightTicket ticket1 = new User("Пушкин", 23500, "BUSINESS", departureDate1,10000000);
            ticket1.addOption(baggage);
            ticket1.addOption(handLuggage);
            system.registerFlightTicket("pushkin_as@email.com", ticket1);
            system.processPaymentByEmail("pushkin_as@email.com");

            FlightTicket ticket2 = new User("Фаулз",10000, "ECONOMY", departureDate1, 20000);
            ticket2.addOption(baggage);
            ticket2.addOption(handLuggage);
            ticket2.addOption(handLuggage2);
            system.registerFlightTicket("fowles@mail.ru", ticket2);
        }
        catch (DepatureDateExpiredException | DuplicatePassengerException | FlightTicketNotFoundException |
               InsufficientFundsException exception){
            System.out.println("[ERROR] " + exception.getMessage());
        }
        System.out.println("\n========(Сценарий 2 - проверяемое исключение. Проведение оплаты для несуществующего email)========\n");
        try{
            system.processPaymentByEmail("fake@mail.ru");
        }
        catch (FlightTicketNotFoundException | InsufficientFundsException exception){
            System.out.println("[Перехвачено исключение] " + exception.getMessage());
            System.out.println("-> Доказательство: Программа продолжила работу после обработки ошибки.");
        }


        System.out.println("\n========(Сценарий 3 - непроверяемое исключение. Регистрация билета с отрицательной стоимостью)========\n");
        try {
            FlightTicket ticket3 = new Corporate("ООО Пятерочка", -2500, "Economy", departureDate1,10000000, 2);
        }
        catch (DepatureDateExpiredException | InvalidFlightTicketException exception){
            System.out.println("[ERROR] " + exception.getMessage());
        }
        System.out.println("\n========(Сценарий 4 - непроверяемое исключение. Регистрация билета без имени)========\n");
        try {
            FlightTicket ticket3 = new Corporate(" ", 2000, "Economy", departureDate1,10000000, 2);
        }
        catch (DepatureDateExpiredException | InvalidFlightTicketException exception){
            System.out.println("[ERROR] " + exception.getMessage());
        }


        System.out.println("\n_________Программа успешно завершена_________\n");

    }
}
