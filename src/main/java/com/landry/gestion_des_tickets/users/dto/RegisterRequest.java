package com.landry.gestion_des_tickets.users.dto;

import com.landry.gestion_des_tickets.users.enums.Roles;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String username;
    private String email;
    private String password;
    private Roles roles = Roles.USER;
    List<String> titleTicket = new ArrayList<>();
}
