package com.landry.gestion_des_tickets.tickets.exceptions;

public class TicketNotFoundException extends Exception{

    public TicketNotFoundException(String message){
        super(message);
    }
}
