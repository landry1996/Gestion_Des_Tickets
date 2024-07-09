package com.landry.gestion_des_tickets.tickets.controller;

import com.landry.gestion_des_tickets.tickets.dto.TicketDto;
import com.landry.gestion_des_tickets.tickets.exceptions.TicketAlreadyExistException;
import com.landry.gestion_des_tickets.tickets.exceptions.TicketErrorException;
import com.landry.gestion_des_tickets.tickets.exceptions.TicketNotFoundException;
import com.landry.gestion_des_tickets.tickets.services.TicketServices;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TicketController {

    private final TicketServices ticketServices;

    @PostMapping(path = "/tickets")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<TicketDto> createTicket(@RequestBody TicketDto ticketsDto) throws TicketAlreadyExistException {
        return new ResponseEntity<>(ticketServices.addTicket(ticketsDto), HttpStatus.CREATED);
    }

    @GetMapping(path = "/tickets")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<List<TicketDto>> getAllTickets(){
        return new ResponseEntity<>(ticketServices.getAllTickets(), HttpStatus.OK);
    }

    @GetMapping(path = "/ticket/{id}")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<TicketDto> getTickeById(@PathVariable Long id) throws TicketNotFoundException {
        return new ResponseEntity<>(ticketServices.getTicket(id), HttpStatus.OK);
    }

    @PutMapping(path = "/tickets/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<TicketDto> updateTicket(@PathVariable Long id, @RequestBody TicketDto ticketDto) throws TicketNotFoundException, TicketErrorException {
        return new ResponseEntity<>(ticketServices.updateTicket(id, ticketDto),HttpStatus.OK);
    }

    @PutMapping(path = "/tickets/{id}/assign/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Void> assignTicketToUser(@PathVariable Long id, @PathVariable Long userId){
        ticketServices.assignTicketToUser(id, userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/tickets/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long id) throws TicketNotFoundException {
        ticketServices.deleteTicket(id);
        return ResponseEntity.ok().build();
    }

}
