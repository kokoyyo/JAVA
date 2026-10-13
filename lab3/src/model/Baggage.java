package model;
public class Baggage {
    private final String name;
    private double priceOfBaggage;

    public Baggage(String name, double price){
        this.name = name.toUpperCase();
        setPriceOfBaggage(price);
    }

    public String getName() {
        return name;
    }
    public double getPriceOfBaggage() {
        return priceOfBaggage;
    }

    public void setPriceOfBaggage(double priceOfBaggage) {
        if (priceOfBaggage < 0){
            this.priceOfBaggage = -priceOfBaggage;
        }else {
            this.priceOfBaggage = priceOfBaggage;
        }
    }
}
