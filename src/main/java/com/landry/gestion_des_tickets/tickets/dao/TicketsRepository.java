package com.landry.gestion_des_tickets.tickets.dao;

import com.landry.gestion_des_tickets.tickets.models.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TicketsRepository extends JpaRepository<Ticket, Long> {

    Optional<Ticket> findTicketsByTitle(String title);
}
