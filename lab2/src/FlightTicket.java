public class FlightTicket {
    private final String passengerName;
    private final double priceOfTicket;
    private final String rate; // "ECONOMY", "COMFORT", "BUSINESS"
    private Baggage addedBaggage = null;

    public FlightTicket(String passengerName, double priceOfTicket, String rate){
        this.passengerName = passengerName;
        this.priceOfTicket = priceOfTicket;
        this.rate = rate.toUpperCase();
    }
    public FlightTicket(String passengerName, double priceOfTicket){
        this(passengerName, priceOfTicket, "ECONOMY");
    }

    public double calculateFinalPrice(){
        final double priceWithRate = switch (rate){
            case "ECONOMY" -> 1.0;
            case "COMFORT" -> 1.5;
            case "BUSINESS" -> 3.0;
            default -> {
                System.out.println("[Система] Неизвестный тариф. Применен базовый коэффициент.");
                yield 1.0;
            }
        };
        double finalPriceOfTicket = priceOfTicket * priceWithRate;
        return Math.round(finalPriceOfTicket*100.0)/100.0;
    }

    public double calculateFinalPrice(Baggage baggage){
        this.addedBaggage = baggage;
        double finalPrice = calculateFinalPrice() + baggage.getPriceOfBaggage();
        return Math.round(finalPrice *100.0)/100.0;
    }

    public double registerBaggage(Baggage baggage){
        return calculateFinalPrice(baggage);
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

    public String getPassengerName() {
        return passengerName;
    }
    public String getRate() {
        return rate;
    }
    public Baggage getAddedBaggage() {
        return addedBaggage;
    }

    public double getPriceOfTicket() {
        return priceOfTicket;
    }
}
