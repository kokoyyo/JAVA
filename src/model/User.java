package model;

public class User extends FlightTicket{
    public User(String passengerName, double priceOfTicket){
        super(passengerName, priceOfTicket, "ECONOMY");
    }
    public User(String passengerName, double priceOfTicket, String rate){
        super(passengerName, priceOfTicket, rate.toUpperCase());
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
        return Math.round(finalPriceOfTicket*100.0)/100.0;
    }

    public double calculateFinalPrice(Baggage baggage){
        this.addedBaggage = baggage;
        double finalPrice = calculateFinalPrice() + baggage.getPriceOfBaggage();
        return Math.round(finalPrice *100.0)/100.0;
    }

    public double registerBaggage(Baggage baggage, double weight){
        this.addedBaggage = baggage;
        double priceOfTicket = calculateFinalPrice();
        if (weight > 23.0){
            double finalPrice = priceOfTicket + 2 * baggage.getPriceOfBaggage();
            return Math.round(finalPrice *100.0)/100.0;
        }
        double finalPrice = priceOfTicket + baggage.getPriceOfBaggage();
        return Math.round(finalPrice *100.0)/100.0;
    }
}
