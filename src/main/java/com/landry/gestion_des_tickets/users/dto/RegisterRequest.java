package com.landry.gestion_des_tickets.users.dto;

import com.landry.gestion_des_tickets.users.enums.Roles;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String username;
    private String email;
    private String password;
    private Roles roles ;
    List<String> titleTicket = new ArrayList<>();
}
