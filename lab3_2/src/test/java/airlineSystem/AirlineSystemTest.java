package airlineSystem;
import exception.*;
import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.ConsoleNotificationService;
import service.imple.EmailNotification;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class AirlineSystemTest {
    private AirlineSystem system;
    private LocalDate validDate;

    @BeforeEach
    void setUp() {
        system = new AirlineSystem(new EmailNotification());
        validDate = LocalDate.now().plusDays(5);
    }

    @Test
    @DisplayName("1. Расчет цены: класс ECONOMY + 2 опции + багаж")
    void shouldCalculatePriceWithBaggageAndOptions() throws Exception {
        User ticket = User.builder()
                .passengerName("Ксения")
                .rate("ECONOMY")
                .priceOfTicket(25000)
                .moneyBalance(5000000)
                .departureDate(LocalDate.now().plusDays(5))
                .includedBaggage(Baggage.builder().name("Базовый багаж").weight(20).build())
                .build();
        ticket.addOption(Option.builder().name("Ручная кладь").price(1000.0).weight(5).build());
        ticket.addOption(Option.builder().name("Багаж").price(5000.0).weight(25).build());
        double actualPrice = ticket.calculateFinalPrice();
        assertEquals(36000.0, actualPrice, "Цена должна учитывать тариф, багаж и вседобавленные опции");
    }

    @Test
    @DisplayName("2. Исключение: отрицательная цена билета")
    void shouldThrowExceptionIfPriceIsNegative(){
        double invalidPrice = -10000;
        assertThrows(InvalidFlightTicketException.class, () -> {
            new User("Александра Сычева", invalidPrice, "ECONOMY", validDate, 50000);
        }, "Создание билета с отрциательной стоимостью должно выбрасывать исключение InvalidFlightTicketException");
    }
    @Test
    @DisplayName("3. Исключение: попытка оплаты несуществующего email")
    void shouldThrowExceptionWhenEmailNotFound(){
        String nonExistentEmail = "lalala@ray.ru";
        assertThrows(FlightTicketNotFoundException.class, ()->{
            system.processPaymentByEmail(nonExistentEmail);
        }, "Оплата по отсутствующему в базе email дожна выбрасывать исключение FlightTicketNotFoundException");
    }
    @Test
    @DisplayName("4. Stream API: фильтрация vip-пассажиров выше заданного порога")
    void shouldFindOnlyBusinessPassengersAboveThreshold() throws DepatureDateExpiredException, DuplicatePassengerException {
        FlightTicket passenger1 = new User("VIP-пассажир1",4000.0,"ECONOMY", validDate, 30000);
        FlightTicket passenger2 = new User("VIP-пассажир",40000.0,"BUSINESS", validDate, 50000000);
        FlightTicket corporatePassenger = new Corporate("ООО Пятерочка", 555, "ECONOMY", validDate, 5555, 2);
        system.registerFlightTicket("passenger1@test.com", passenger1);
        system.registerFlightTicket("passenger2@test.com", passenger2);
        system.registerFlightTicket("corporatePassenger@test.com", corporatePassenger);
        List<FlightTicket> vipPassengers = system.findVIPTickets(3000);
        assertEquals(2, vipPassengers.size(), "В списке должно быть ровно 2 VIP-пассажира");
        assertFalse(vipPassengers.contains(corporatePassenger), "ООО Пятерочка с чеком 1110 руб. не должен попасть в VIP");
        assertTrue(vipPassengers.contains(passenger2), "VIP-клиент должен быть в списке");
    }

    @Test
    @DisplayName("5. Stream API: подсчет общей прогнозируемой выручки")
    void shouldCalculateTotalRevenueCorrectly() throws DepatureDateExpiredException, DuplicatePassengerException{
        FlightTicket passenger1 = new User("Пассажир 1",5000.0,"ECONOMY", validDate, 30000);
        FlightTicket passenger2 = new User("Пассажир 2",35000.0,"BUSINESS", validDate, 50000000);
        passenger2.addOption(new Option("РУЧНАЯ КЛАДЬ", 1000, 8));
        system.registerFlightTicket("p1@mail.ru", passenger1);
        system.registerFlightTicket("p2@mail.ru", passenger2);
        double totalRevenue = system.calculateTotalRevenue();
        assertEquals(111000.0, totalRevenue,"Общая выручка должна учитывать тарифные коэффициенты и опции");
    }
    @Test
    @DisplayName("6. Исключение: попытка регистрации двух билетов на один email")
    void shouldThrowExceptionWhenEmailIsDuplicate() throws Exception {
        String sameEmail = "test@test.com";
        FlightTicket firstTicket = new User("Даша Бабич", 10000, "ECONOMY", validDate, 50000);
        FlightTicket secondTicket = new User("Софья Морозова", 15000, "ECONOMY", validDate, 50000);
        system.registerFlightTicket(sameEmail, firstTicket);
        assertThrows(DuplicatePassengerException.class, () -> {
            system.registerFlightTicket(sameEmail, secondTicket);
        }, "Попытка создания билета по существуещему в баз данных email должна выбрасыввть исключени DuplicatePassengerException");
    }

    @Test
    @DisplayName("7. Провекра гранчиеного случая: багаж ровно 23 кг не требует доплаты")
    void shouldNotChargeExtraForExactLimitBaggage() throws Exception {
        Baggage exactly20Kg = Baggage.builder()
                .name("Базовый багаж")
                .weight(23.0)
                .build();
        double extraPrice = exactly20Kg.calculateExtraPrice();
        assertEquals(0.0, extraPrice, "За багаж весом ровно 23 кг доплаты быть не должно");
    }
}