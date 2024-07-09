package com.landry.gestion_des_tickets.tickets.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.landry.gestion_des_tickets.tickets.dto.TicketDto;
import com.landry.gestion_des_tickets.tickets.enums.Statut;
import com.landry.gestion_des_tickets.tickets.models.Ticket;
import com.landry.gestion_des_tickets.tickets.services.TicketServices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(controllers = TicketController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class TicketControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TicketServices ticketServices;

    @Autowired
    private ObjectMapper objectMapper;

/*
    @Test
    public void testCreateTicket_Success(){
        TicketDto ticketDto =  TicketDto.builder()
                .id(1L)
                .title("Programmation")
                .description("Ticket de Programmation")
                .statut(Statut.TERMINER)
                .build();
        TicketDto expectedResponse = new TicketDto(ticketDto.getTitle(),ticketDto.getDescription(),ticketDto.getStatut());

    }*/



}