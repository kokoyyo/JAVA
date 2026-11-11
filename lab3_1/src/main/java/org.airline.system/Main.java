package org.airline.system;
import org.airline.system.service.NotificationService;
import org.airline.system.airlineSystem.AirlineSystem;
import org.airline.system.exception.*;
import org.airline.system.model.FlightTicket;
import org.airline.system.model.Option;
import org.airline.system.model.User;
import org.airline.system.service.ConsoleNotificationService;

import java.time.LocalDate;
public class Main {
    public static void main(String[] args){
        service.NotificationService service = new ConsoleNotificationService();
        AirlineSystem system = new AirlineSystem(service);
        System.out.println("\n(Сценарий 1)\n");
        LocalDate departureDate1 =LocalDate.now().plusDays(14);
        Option baggage = new Option("БАГАЖ", 2350, 23);
        Option handLuggage = new Option("Ручная кладь", 0, 8);

        try {
            FlightTicket ticket1 = new User("Пушкин", 23500, "BUSINESS", departureDate1,50000);
            ticket1.addOption(baggage);
            ticket1.addOption(handLuggage);
            system.registerFlightTicket("pushkin_as@email.com", ticket1);
            system.processPaymentByEmail("pushkin_as@email.com");
        }
        catch (DepatureDateExpiredException | DuplicatePassengerException | FlightTicketNotFoundException |
               InsufficientFundsException exception){
            System.out.println("[ERROR]" + exception.getMessage());
        }
    }
}
