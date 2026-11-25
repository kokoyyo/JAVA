package model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class Baggage {
    private static final double FREE_WEIGHT_LIMIT = 23.0;
    private static final double PRICE_PER_EXTRA_KG = 500.0;
    private final String name;
    private final double weight;
    public Baggage(String name, double weight) {
        this.name = (name != null) ? name.toUpperCase() : "БАГАЖ";
        this.weight = weight;
    }
    public double calculateExtraPrice() {
        if (weight > FREE_WEIGHT_LIMIT) {
            double extraKg = weight - FREE_WEIGHT_LIMIT;
            return extraKg * PRICE_PER_EXTRA_KG;
        }
        return 0.0;
    }
}