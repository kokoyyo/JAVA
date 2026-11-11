package org.airline.system.exception;

public class FlightTicketNotFoundException extends Exception{
    public FlightTicketNotFoundException(String message){
        super(message);
    }
}
