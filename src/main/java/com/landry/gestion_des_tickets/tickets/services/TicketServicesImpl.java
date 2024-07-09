package com.landry.gestion_des_tickets.tickets.services;

import com.landry.gestion_des_tickets.tickets.dao.TicketsRepository;
import com.landry.gestion_des_tickets.tickets.dto.TicketDto;
import com.landry.gestion_des_tickets.tickets.exceptions.TicketAlreadyExistException;
import com.landry.gestion_des_tickets.tickets.exceptions.TicketErrorException;
import com.landry.gestion_des_tickets.tickets.exceptions.TicketNotFoundException;
import com.landry.gestion_des_tickets.tickets.mapper.TicketMapper;
import com.landry.gestion_des_tickets.tickets.models.Ticket;
import com.landry.gestion_des_tickets.users.dao.UsersRepository;
import com.landry.gestion_des_tickets.users.models.Usr;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static com.landry.gestion_des_tickets.tickets.utils.TICKET_ALREADY_EXIST_IN_DATABASE;
import static com.landry.gestion_des_tickets.tickets.utils.TICKET_NOT_EXIST;

@Service
@RequiredArgsConstructor
public class TicketServicesImpl implements TicketServices {

    private final TicketsRepository ticketsRepository;

    private final UsersRepository usersRepository;

    private final TicketMapper mapper;

    @Override
    public TicketDto addTicket(TicketDto ticketsDto) throws TicketAlreadyExistException {

        if (Objects.nonNull(ticketsDto) && Objects.nonNull(ticketsDto.getTitle())
                && ticketsRepository.findTicketsByTitle(ticketsDto.getTitle()).isPresent()) {

            throw new TicketAlreadyExistException(TICKET_ALREADY_EXIST_IN_DATABASE);

        }
        Ticket tickets = ticketsRepository.save(mapper.mapTicketDtoToTicket(ticketsDto));
        return mapper.mapTicketToTicketDto(tickets);
    }

    @Override
    public TicketDto getTicket(Long idTicket) throws TicketNotFoundException {

        return ticketsRepository.findById(idTicket)
                .map(mapper::mapTicketToTicketDto)
                .orElseThrow(() -> new TicketNotFoundException(TICKET_NOT_EXIST));
    }

    @Override
    public List<TicketDto> getAllTickets() {
        return ticketsRepository.findAll()
                .stream().map(mapper::mapTicketToTicketDto).toList();
    }

    @Override
    public TicketDto updateTicket(Long id, TicketDto ticketDto) throws TicketNotFoundException, TicketErrorException {

        if (ticketsRepository.findById(id).isEmpty()) {

            throw new TicketNotFoundException(TICKET_NOT_EXIST);
        }
        Ticket ticket = ticketsRepository.findById(id).get();
        ticket.setTitle(mapper.mapTicketDtoToTicket(ticketDto).getTitle());
        ticket.setDescription(mapper.mapTicketDtoToTicket(ticketDto).getDescription());
        ticket.setStatut(mapper.mapTicketDtoToTicket(ticketDto).getStatut());

        return mapper.mapTicketToTicketDto(ticketsRepository.save(ticket));

    }

    @Override
    public void deleteTicket(Long idTicket) throws TicketNotFoundException {

        if (ticketsRepository.findById(idTicket).isEmpty()){
            throw new TicketNotFoundException(TICKET_NOT_EXIST);
        }
        ticketsRepository.deleteById(idTicket);

    }

    @Override
    public void assignTicketToUser(Long ticketId, Long userId) {

        if (ticketsRepository.findById(ticketId).isPresent() && usersRepository.findById(userId).isPresent()) {
            Ticket ticket = ticketsRepository.findById(ticketId).get();
            Usr usr = usersRepository.findById(userId).get();
            ticket.setUsr(usr);
            usr.getTicket().add(ticket);
            usersRepository.save(usr);
            ticketsRepository.save(ticket);

        }

    }
}
