import lombok.extern.slf4j.Slf4j;
import model.Corporate;
import service.NotificationService;
import airlineSystem.AirlineSystem;
import exception.*;
import model.FlightTicket;
import model.Option;
import model.User;
import service.ConsoleNotificationService;
import net.datafaker.Faker;
import service.imple.EmailNotification;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
@Slf4j
public class Main {
    private static void generatePassangers(AirlineSystem airlineSystem, int count){
        Faker faker = new Faker(new Locale("ru"));
        String[] rates = {"ECONOMY", "STANDARD", "BUSINESS"};
        String[] availableOptions = {"БАГАЖ", "РУЧНАЯ КЛАДЬ"};
        for (int i=0;i<count;i++){
            String name =faker.name().fullName();
            double priceTicket = faker.number().randomDouble(2, 2000, 200000);
            String rate = rates[faker.random().nextInt(rates.length)];
            String email = i + "_" + faker.internet().emailAddress();
            String phone = faker.phoneNumber().cellPhone();
            double balance = faker.number().randomDouble(2, 50, 2000000);
            LocalDate departureDate = LocalDate.now().plusDays(1 + faker.random().nextInt(30));
            FlightTicket ticket;
            try {
                if (i % 10 == 0) {
                    ticket = Corporate.builder()
                            .passengerName(faker.company().name())
                            .priceOfTicket(priceTicket)
                            .rate(rate)
                            .departureDate(departureDate)
                            .moneyBalance(balance)
                            .activeLicensesCount(faker.number().numberBetween(2, 10))
                            .build();
                } else {
                    ticket = User.builder()
                            .passengerName(faker.company().name())
                            .priceOfTicket(priceTicket)
                            .rate(rate)
                            .departureDate(departureDate)
                            .moneyBalance(balance)
                            .build();
                    int countOfOptions = faker.number().numberBetween(1, 4);
                    for (int j = 0; j < countOfOptions; j++) {
                        String optionName = availableOptions[faker.random().nextInt(availableOptions.length)];
                        double optionPrice = faker.number().numberBetween(0, 2500);
                        ticket.addOption(Option.builder()
                                .name(optionName)
                                .price(optionPrice)
                                .weight(faker.random().nextDouble(0, 32))
                                .build());
                    }
                    if (faker.bool().bool()) {
                        ticket.addOption(Option.builder()
                                .name("РУЧНАЯ КЛАДЬ")
                                .price(0)
                                .weight(faker.number().randomDouble(2, 5, 10))
                                .build());
                    }
                }
                airlineSystem.registerFlightTicket(email, ticket);
            }catch( DuplicatePassengerException exception){
                log.warn("Не удалось зарегистрировать пассажира {}: {}", email, exception.getMessage());
            }
        }
    }
    public static void main(String[] args) throws FlightTicketNotFoundException, InsufficientFundsException {
        NotificationService emailService = new EmailNotification();
        AirlineSystem system = new AirlineSystem(emailService);
        generatePassangers(system, 50);
        System.out.println("--------------АНАЛИТИКА STREAM API--------------");
        System.out.println("Всего записей в базе: " + system.getDatabase().size());
        System.out.println("Общая выручка: " + system.calculateTotalRevenue());
        System.out.println("Количество BUSINESS билетов: " + system.countBusinessTickets());
        List<FlightTicket> businessClients = system.findVIPTickets(30000);
        System.out.println("BUSINESS-пассажиры (стоимость билета > 30000 руб.): " + businessClients.size());
        businessClients.stream().limit(5).forEach(ticket -> System.out.printf("  * %s [%s] — %.2f руб. (опций: %d)%n",
                ticket.getPassengerName(), ticket.getRate(), ticket.calculateFinalPrice(), ticket.getAddedOption().size()));

        System.out.println("=========Проверка обработки исключений=========");
        try{
            String email = system.getDatabase().keySet().iterator().next();
            system.processPaymentByEmail(email);
            system.processPaymentByEmail("some_email@mail.ru");
        }catch(FlightTicketNotFoundException | InsufficientFundsException e){
            System.out.println("[Перехвачено исключение]: " +e.getMessage());
        }
        try {
            new User("Ксюша", -1000.0, "ECONOMY", LocalDate.now().plusDays(14), 2000);
        }
        catch (DepatureDateExpiredException | InvalidFlightTicketException exception){
            System.out.println("[Перехвачено исключение] " + exception.getMessage());
        }
    }
}
