package com.landry.gestion_des_tickets.tickets.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class ErrorMessage {

    private int status;
    private LocalDate timestamp;
    private String message;
    private HttpStatus httpStatus;
}
