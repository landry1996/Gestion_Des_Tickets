package com.landry.gestion_des_tickets.tickets.dto;

import com.landry.gestion_des_tickets.tickets.enums.Statut;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TicketDto {

    private Long id;
    private String title;
    private String description;
    private Statut statut;
}
