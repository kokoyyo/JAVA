package model;

public class Corporate extends FlightTicket{
    private final int activeLicensesCount;
    public int getActiveLicensesCount() { return activeLicensesCount; }
    public Corporate(String passengerName, double priceOfTicket, String rate, int activeLicensesCount){
        super(passengerName, priceOfTicket, rate);
        this.activeLicensesCount = Math.max(1, activeLicensesCount);
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
        double finalPriceOfTicket = getPriceOfTicket() * priceWithRate*activeLicensesCount;
        return Math.round(finalPriceOfTicket*100.0)/100.0;
    }

    public void calculateFinalPrice(Baggage baggage){
        System.out.println("ОШИБКА. Нет доступа к этой функции\n. Стоимость будет рассчитана без учета багажа");
        calculateFinalPrice();
    }

    public void registerBaggage(Baggage baggage, double weight){
        System.out.println("ОШИБКА. Нет доступа к этой функции\n. Стоимость будет рассчитана без учета багажа");
        calculateFinalPrice();
    }
}
