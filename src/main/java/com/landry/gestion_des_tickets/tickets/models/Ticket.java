package com.landry.gestion_des_tickets.tickets.models;

import com.landry.gestion_des_tickets.users.models.Usr;
import com.landry.gestion_des_tickets.tickets.enums.Statut;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter @Setter
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
   private String title;
   private String description;
   private Statut statut;

   @ManyToOne(fetch = FetchType.EAGER)
   @JoinColumn(name = "usr_id")
   private Usr usr;



}
