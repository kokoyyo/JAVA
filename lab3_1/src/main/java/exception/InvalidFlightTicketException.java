package exception;

public class InvalidFlightTicketException extends RuntimeException{
    public InvalidFlightTicketException(String message){
        super(message);
    }
}
