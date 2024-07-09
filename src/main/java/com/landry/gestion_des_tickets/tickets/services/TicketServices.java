package com.landry.gestion_des_tickets.tickets.services;


import com.landry.gestion_des_tickets.tickets.dto.TicketDto;
import com.landry.gestion_des_tickets.tickets.exceptions.TicketAlreadyExistException;
import com.landry.gestion_des_tickets.tickets.exceptions.TicketErrorException;
import com.landry.gestion_des_tickets.tickets.exceptions.TicketNotFoundException;

import java.util.List;
import java.util.Optional;

public interface TicketServices {

    TicketDto addTicket(TicketDto ticketsDto) throws TicketAlreadyExistException;
    TicketDto getTicket(Long idTicket) throws TicketNotFoundException;
    List<TicketDto> getAllTickets();
    TicketDto updateTicket(Long id,TicketDto ticketsDto) throws TicketNotFoundException, TicketErrorException;
    void deleteTicket(Long idTicket) throws TicketNotFoundException;
    void assignTicketToUser(Long ticketId, Long userId);







}
