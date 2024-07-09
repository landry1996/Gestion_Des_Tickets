package com.landry.gestion_des_tickets.users.services;

import com.landry.gestion_des_tickets.tickets.dto.TicketDto;
import com.landry.gestion_des_tickets.tickets.exceptions.UserAlreadyExistException;
import com.landry.gestion_des_tickets.tickets.exceptions.UserErrorException;
import com.landry.gestion_des_tickets.tickets.exceptions.UserNotFoundException;
import com.landry.gestion_des_tickets.users.dto.UserDto;

import java.util.List;

public interface UserServices {


    List<UserDto> getAllUsers();
    UserDto addUser(UserDto userDto) throws UserAlreadyExistException;
    UserDto updateUser(Long id, UserDto userDto) throws UserNotFoundException, UserErrorException;
    List<TicketDto> getTicketsByUserId(Long userId) throws UserNotFoundException;

}
