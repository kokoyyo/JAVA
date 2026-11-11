package org.airline.system.exception;

public class InsufficientFundsException extends Exception{
    public InsufficientFundsException(String message){
        super(message);
    }
}
