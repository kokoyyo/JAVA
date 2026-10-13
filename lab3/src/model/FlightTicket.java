package model;

public abstract class FlightTicket {
    private final String passengerName;
    private final double priceOfTicket;
    private final String rate; // "ECONOMY", "COMFORT", "BUSINESS"
    protected Baggage addedBaggage = null;
    public FlightTicket(String passengerName, double priceOfTicket, String rate){
        this.passengerName = passengerName;
        this.priceOfTicket = priceOfTicket;
        this.rate = rate.toUpperCase();
    }

    public abstract double calculateFinalPrice();
    public String getPassengerName() {return passengerName; }
    public String getRate() {return rate;}
    public double getPriceOfTicket() { return priceOfTicket;}
    public Baggage getAddedBaggage() {return addedBaggage;}
}
