package model;
import java.time.LocalDate;
import exception.DepatureDateExpiredException;
import exception.InvalidFlightTicketException;
import exception.InvalidOptionException;
import java.util.regex.Pattern;
import java.util.ArrayList;
import  java.util.List;
public abstract class  FlightTicket {
    private final String passengerName;
    private final double priceOfTicket;
    private final String rate; // "ECONOMY", "COMFORT", "BUSINESS"
    private final LocalDate departureDate;
    public double moneyBalance;
    private final List<Option> addedOption = new ArrayList<>();
    private static final Pattern EMPTY_PATTERN = Pattern.compile("^\\s*$");
    public FlightTicket(String passengerName, double priceOfTicket, String rate, LocalDate date, double balance)
                                        throws DepatureDateExpiredException{

        if (passengerName == null || EMPTY_PATTERN.matcher(passengerName).matches()){
            throw new InvalidFlightTicketException("Имя владельца билета не может быть пустым! Передано [" + passengerName + "]");
        }
        if (priceOfTicket < 0){
            throw new InvalidFlightTicketException("Стоимость билета должна быть больше нуля! Передано:" + priceOfTicket);
        }
        this.passengerName = passengerName;
        this.priceOfTicket = priceOfTicket;
        this.rate = rate.toUpperCase();
        LocalDate today = LocalDate.now();
        if (date.isBefore(today)){
            throw new DepatureDateExpiredException("Дата вылета прошла.");
        }
        if (date.isAfter(today.plusYears(1))){
            throw new IllegalArgumentException("Доступные даты вылета до " + today.plusDays(364));
        }
        this.departureDate = date;
        if (balance < 0){
            throw new InvalidFlightTicketException("Баланс не может быть отрицательным.");
        }
        this.moneyBalance = balance;
    }

    public abstract double calculateFinalPrice();
    public String getPassengerName() {return passengerName; }
    public String getRate() {return rate;}
    public double getPriceOfTicket() { return priceOfTicket;}
    public LocalDate getDepartureDate() {    return departureDate; }

    public List<Option> getAddedOption() {return addedOption;}
    public void addOption(Option option){
        if (option == null){
            throw new IllegalArgumentException("Нельзя добавить пустую опцию!");
        }
        this.addedOption.add(option);
    }
    public double getMoneyBalance(){return moneyBalance;}
    public void transferAssets(double price){
        this.moneyBalance -=price;
    }
}
