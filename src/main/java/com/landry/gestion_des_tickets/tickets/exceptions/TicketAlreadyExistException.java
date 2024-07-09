package com.landry.gestion_des_tickets.tickets.exceptions;

public class TicketAlreadyExistException extends Exception{

    public TicketAlreadyExistException(String message){
        super(message);
    }
}
