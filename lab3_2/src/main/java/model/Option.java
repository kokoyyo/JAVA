package model;
import exception.InvalidOptionException;
import lombok.Getter;
import lombok.Builder;
import lombok.ToString;
@Getter
@Builder
public class Option {
    private final String name; // "БАГАЖ", "РУЧНАЯ КЛАДЬ"
    private final double price;
    private final double weight;
    public Option(String name, double price, double weight){
        if (name == null || name.trim().isEmpty()){
            throw new InvalidOptionException("Название опции не может быть пустым.");
        }
        if (weight <=0){
            throw new InvalidOptionException("Вес багажа должен быть больше нуля. [Получено = " + weight + " кг.]");
        }
        if (weight >40){
            throw new InvalidOptionException("Вес багажа превышает нормы. [Получено = " + weight + " кг.]");
        }
        if (price < 0){
            throw new InvalidOptionException("Стоимость багажа не может быть отрицательной. [Получено = " + price + " руб.]");
        }
        this.weight = weight;
        if (weight > 23 ){
            this.name = name.toUpperCase() + "[Перевес]";
            this.price = price*2;
        }
        else if (weight <= 10.0){
            this.name = name.toUpperCase() + "[РУЧНАЯ КЛАДЬ]";
            this.price = 0.0;
        }
        else {
            this.name =name.toUpperCase();
            this.price = price;
        }
    }
}
