package com.landry.gestion_des_tickets.tickets.exceptions;

public class UserAlreadyExistException extends Exception{

    public UserAlreadyExistException(String message){
        super(message);
    }
}
