package com.landry.gestion_des_tickets.tickets.services;

import com.landry.gestion_des_tickets.tickets.dao.TicketsRepository;
import com.landry.gestion_des_tickets.tickets.dto.TicketDto;
import com.landry.gestion_des_tickets.tickets.enums.Statut;
import com.landry.gestion_des_tickets.tickets.exceptions.TicketAlreadyExistException;
import com.landry.gestion_des_tickets.tickets.exceptions.TicketErrorException;
import com.landry.gestion_des_tickets.tickets.exceptions.TicketNotFoundException;
import com.landry.gestion_des_tickets.tickets.mapper.TicketMapper;
import com.landry.gestion_des_tickets.tickets.mapper.TicketMapperImpl;
import com.landry.gestion_des_tickets.tickets.models.Ticket;
import com.landry.gestion_des_tickets.users.dao.UsersRepository;
import com.landry.gestion_des_tickets.users.dto.RegisterRequest;
import com.landry.gestion_des_tickets.users.enums.Roles;
import com.landry.gestion_des_tickets.users.services.authServices.AuthenticationService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.test.context.ContextConfiguration;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.landry.gestion_des_tickets.tickets.utils.TICKET_ALREADY_EXIST_IN_DATABASE;
import static com.landry.gestion_des_tickets.tickets.utils.TICKET_NOT_EXIST;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@ContextConfiguration(classes = {TicketServicesImpl.class, TicketMapperImpl.class,UsersRepository.class})
class TicketServicesImplTest {

    @Mock
    private TicketsRepository ticketsRepository;
    @Mock
    private UsersRepository usersRepository;
    @InjectMocks
    private TicketServicesImpl services;
    @InjectMocks
    private AuthenticationService authenticationService;

    @Mock
    private TicketMapper ticketMapper;


    @Test
     void testAddTicket_Success() throws TicketAlreadyExistException {

        //Given
        TicketDto ticketDto = TicketDto.builder()
                .id(1L)
                .title("Programmation")
                .description("Ticket de Programmation")
                .statut(Statut.TERMINER)
                .build();

        Ticket ticket = ticketMapper.mapTicketDtoToTicket(ticketDto);

        Mockito.when(ticketsRepository.findTicketsByTitle(ticketDto.getTitle())).thenReturn(Optional.empty());
        Mockito.when(ticketsRepository.save(Mockito.any(Ticket.class))).thenReturn(ticket);
        Mockito.when(ticketMapper.mapTicketToTicketDto(ticket)).thenReturn(ticketDto);


        // When
        TicketDto saveTicketDto = services.addTicket(ticketDto);

        // Then
        Assertions.assertThat(saveTicketDto).isNotNull();
    }

    @Test
    void testAddTicket_Error() throws TicketAlreadyExistException{
        // Given

        TicketDto ticketDto = TicketDto.builder()
                .id(1L)
                .title("Programmation")
                .description("Ticket de Programmation")
                .statut(Statut.TERMINER)
                .build();

        Ticket ticket = Ticket.builder()
                .id(1L)
                .title("Programmation")
                .description("Ticket de Programmation")
                .statut(Statut.TERMINER)
                .build();

        //When
        Mockito.when(ticketMapper.mapTicketDtoToTicket(ticketDto)).thenReturn(ticket);
        Mockito.when(ticketMapper.mapTicketToTicketDto(ticket)).thenReturn(ticketDto);
        Mockito.when(ticketsRepository.findTicketsByTitle(ticketDto.getTitle()))
                .thenReturn(Optional.ofNullable(ticket));

        //Then
        Assertions.assertThatThrownBy(()->services.addTicket(ticketDto))
                .isInstanceOf(TicketAlreadyExistException.class)
                .hasMessage(TICKET_ALREADY_EXIST_IN_DATABASE);
    }

    @Test
    void testGetAllListOfTicket_Success(){
        //Given
        List<Ticket> tickets = getAllListTickets();
        Mockito.when(ticketsRepository.findAll()).thenReturn(tickets);

        //When
        List<TicketDto> dtoList = services.getAllTickets();

        //Then
        Assertions.assertThat(dtoList).isNotNull();
        Assertions.assertThat(dtoList.size()).isEqualTo(2);
    }

    @Test
    void testReturnEmptyListOfTicket(){
        //When
        Mockito.when(ticketsRepository.findAll()).thenReturn(Collections.emptyList());
        List<TicketDto> dtoList = services.getAllTickets();

        //Then
        Assertions.assertThat(dtoList).isEmpty();
    }

    @Test
    void testGetTicketById() throws TicketNotFoundException {
        //Given
        Long id = 1L;
        var ticket = getTicketById(id);
        var ticketDto = getTickeDtotById(id);

        Mockito.when(ticketsRepository.findById(id)).thenReturn(Optional.of(ticket));
        Mockito.when(ticketMapper.mapTicketToTicketDto(ticket)).thenReturn(ticketDto);

        //When
        TicketDto foundTicketDto = services.getTicket(id);

        //Then
        Assertions.assertThat(foundTicketDto).isNotNull();
        Assertions.assertThat(foundTicketDto.getTitle()).isNotNull();
        Assertions.assertThat(foundTicketDto.getDescription()).isNotNull();

    }

    @Test
    void testGetTicketById_Error(){
        //Given

        Long id = 1L;
        //When
        Mockito.when(ticketsRepository.findById(id)).thenReturn(Optional.empty());

        //Then
        Assertions.assertThatThrownBy(()-> services.getTicket(id))
                .isInstanceOf(TicketNotFoundException.class)
                .hasMessage(TICKET_NOT_EXIST);

    }

    @Test
    void testUpdateTicket() throws TicketNotFoundException, TicketErrorException {
        //Given
        Long id = 1L;
        Ticket ticket = getTicketToUpdate();
        TicketDto ticketDto = getTicketDtoToUpdate();
        Mockito.when(ticketsRepository.findById(id)).thenReturn(Optional.of(ticket));
        Mockito.when(ticketMapper.mapTicketDtoToTicket(ticketDto)).thenReturn(ticket);
        Mockito.when(ticketsRepository.save(ticket)).thenReturn(ticket);
        Mockito.when(ticketMapper.mapTicketToTicketDto(ticket)).thenReturn(ticketDto);

        //When

        TicketDto updateTicketDto = services.updateTicket(id,ticketDto);
        //Then
        Assertions.assertThat(1L).isEqualTo(updateTicketDto.getId());
        Assertions.assertThat("Jeux").isEqualTo(updateTicketDto.getTitle());
    }

    @Test
    void testTicketToUpdateNotFound(){
        //Given
        Long id = 2L;
        TicketDto ticketDto = getTicketDtoToUpdate();

        //When
        Mockito.when(ticketsRepository.findById(id)).thenReturn(Optional.empty());

        //Then
        Assertions.assertThatThrownBy(()->services.updateTicket(id,ticketDto))
                .isInstanceOf(TicketNotFoundException.class)
                .hasMessage(TICKET_NOT_EXIST);
    }

    @Test
    void testToDeletTicket_If_Ticket_Exist() throws TicketNotFoundException {
        //Given

        Long id = 1L;
        List<Ticket> tickets = getAllListTickets();
        Mockito.when(ticketsRepository.findById(id)).thenReturn(Optional.of(tickets.get(0)));

        //When

        services.deleteTicket(id);

        //Then
        verify(ticketsRepository, Mockito.times(1)).deleteById(id);
    }

    @Test
    public void testDeleteTicket_TicketNotFound() throws TicketNotFoundException {
        //Given
        Long id = 1L;

        //When
        Mockito.when(ticketsRepository.findById(id)).thenReturn(Optional.empty());

        //Then
        services.deleteTicket(id);
    }


    private List<Ticket> getAllListTickets(){
        return Arrays.asList(
                Ticket.builder()
                        .id(1L)
                        .title("Programmation")
                        .description("Ticket de Programmation")
                        .statut(Statut.TERMINER)
                        .build(),

                Ticket.builder()
                        .id(2L)
                        .title("Jeux")
                        .description("Ticket de Jeux")
                        .statut(Statut.TERMINER)
                        .build()
        );
    }

    private Ticket getTicketById(Long id){
        var ticket = Ticket.builder()
                .id(id)
                .title("Jeux")
                .description("Ticket de Jeux")
                .statut(Statut.TERMINER)
                .build();
        return ticket;
    }

    private TicketDto getTickeDtotById(Long id){
        var ticketDto = TicketDto.builder()
                .id(id)
                .title("Jeux")
                .description("Ticket de Jeux")
                .statut(Statut.TERMINER)
                .build();
        return ticketDto;
    }

    private Ticket getTicketToUpdate(){
        return Ticket.builder()
                .id(1L)
                .title("Jeux")
                .description("Ticket de Jeux")
                .statut(Statut.TERMINER)
                .build();
    }

    private TicketDto getTicketDtoToUpdate(){
        return TicketDto.builder()
                .id(1L)
                .title("Jeux")
                .description("Ticket de Jeux")
                .statut(Statut.TERMINER)
                .build();
    }






}