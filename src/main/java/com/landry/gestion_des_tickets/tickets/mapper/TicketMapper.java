package com.landry.gestion_des_tickets.tickets.mapper;

import com.landry.gestion_des_tickets.tickets.dto.TicketDto;
import com.landry.gestion_des_tickets.tickets.models.Ticket;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TicketMapper {

    @Mapping(target = "title", source = "title")
    Ticket mapTicketDtoToTicket(TicketDto ticketsDto);
    @Mapping(target = "title", source = "title")
    TicketDto mapTicketToTicketDto(Ticket tickets);
}
