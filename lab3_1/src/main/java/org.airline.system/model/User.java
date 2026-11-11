package org.airline.system.model;
import org.airline.system.exception.DepatureDateExpiredException;
import java.time.LocalDate;

public class User extends FlightTicket{
    double moneyBalance;
    public User(String passengerName, double priceOfTicket)
                        throws DepatureDateExpiredException{
        super(passengerName, priceOfTicket, "ECONOMY", LocalDate.now().plusDays(1), 0.0);
    }
    public User(String passengerName, double priceOfTicket, String rate, LocalDate date, double balance)
                                  throws DepatureDateExpiredException {
        super(passengerName, priceOfTicket, rate.toUpperCase(), date, balance);

    }
    @Override
    public double calculateFinalPrice(){
        final double priceWithRate = switch (getRate()){
            case "ECONOMY" -> 1.0;
            case "COMFORT" -> 1.5;
            case "BUSINESS" -> 3.0;
            default -> {
                System.out.println("[Система] Неизвестный тариф. Применен базовый коэффициент.");
                yield 1.0;
            }
        };
        double finalPriceOfTicket = getPriceOfTicket() * priceWithRate;
        double priceOfBaggages =0.0;
        for (Option b: getAddedOption()){
            priceOfBaggages += b.getPrice();
        }
        finalPriceOfTicket+=priceOfBaggages;
        return Math.round(finalPriceOfTicket*100.0)/100.0;
    }


}
