package com.landry.gestion_des_tickets.tickets.exceptions;

public class UserErrorException extends Exception{

    public UserErrorException(String message){
        super(message);
    }
}
