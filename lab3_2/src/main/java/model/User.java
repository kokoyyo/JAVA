package model;
import exception.DepatureDateExpiredException;
import java.time.LocalDate;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.ToString;
@ToString(callSuper = true)
@SuperBuilder
public class User extends FlightTicket{
    public User(String passengerName, double priceOfTicket, String rate, LocalDate date, double balance)
                                  throws DepatureDateExpiredException {
        super(passengerName, priceOfTicket, rate!= null ? rate.toUpperCase() : "ECONOMY",
                date != null ? date : LocalDate.now().plusDays(1), balance, false);
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
        double priceOfBaggage =0.0;
        for (Option b: getAddedOption()){
            priceOfBaggage += b.getPrice();
        }
        double extraPriceForOverweight=0.0;
        if (getIncludedBaggage()!=null){
            extraPriceForOverweight=getIncludedBaggage().calculateExtraPrice();
        }
        finalPriceOfTicket =finalPriceOfTicket+ priceOfBaggage +extraPriceForOverweight;
        return Math.round(finalPriceOfTicket*100.0)/100.0;
    }


}
