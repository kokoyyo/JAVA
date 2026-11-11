package model;
import exception.InvalidFlightTicketException;
import exception.InvalidOptionException;
import exception.DepatureDateExpiredException;
import java.time.LocalDate;

public class Corporate extends FlightTicket{
    private final int activeLicensesCount;
    public int getActiveLicensesCount() { return activeLicensesCount; }
    public Corporate(String passengerName, double priceOfTicket, String rate, LocalDate date, double balance, int activeLicensesCount)
            throws DepatureDateExpiredException{
        super(passengerName, priceOfTicket, rate.toUpperCase(), date, balance);
        this.activeLicensesCount = Math.max(1, activeLicensesCount);
    }
    @Override
    public void addOption(Option option){
        if (option.getWeight() >10.0){
            throw new InvalidOptionException("Корпоративные клиенты не могут провозить багаж.");
        }
        super.addOption(option);
    }

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
        double finalPriceOfTicket = getPriceOfTicket() * priceWithRate*activeLicensesCount;
        return Math.round(finalPriceOfTicket*100.0)/100.0;
    }

}
