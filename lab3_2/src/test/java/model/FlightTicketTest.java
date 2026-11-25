package model;

import exception.DepatureDateExpiredException;
import exception.InvalidFlightTicketException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class FlightTicketTest {

    @Test
    void testCalculateFinalPrice_WithOptions_ShouldReturnCorrectSum() throws DepatureDateExpiredException {
        // Arrange (Подготовка)
        LocalDate futureDate = LocalDate.now().plusDays(10);
        // Создаем билет (Базовая цена 10000, тариф ECONOMY = коэффициент 1.0)
        User ticket = new User("Иванов", 10000, "ECONOMY", futureDate, 50000);

        // Добавляем 2 опции: Багаж (2000) и Ручная кладь (0)
        ticket.addOption(new Option("БАГАЖ", 2000, 20));
        ticket.addOption(new Option("РУЧНАЯ КЛАДЬ", 0, 8));

        // Act (Действие)
        double finalPrice = ticket.calculateFinalPrice();

        // Assert (Проверка)
        // Ожидаем 10000 * 1.0 + 2000 + 0 = 12000
        assertEquals(12000.0, finalPrice, "Итоговая цена должна учитывать базовую стоимость и опции");
    }

    @Test
    void testCreateTicket_WithNegativePrice_ShouldThrowException() {
        // Arrange
        LocalDate futureDate = LocalDate.now().plusDays(10);

        // Act & Assert (Проверяем, что при создании выбросится исключение)
        assertThrows(InvalidFlightTicketException.class, () -> {
            new User("Петров", -500, "ECONOMY", futureDate, 1000);
        }, "Должно выбрасываться исключение при отрицательной цене");
    }
}