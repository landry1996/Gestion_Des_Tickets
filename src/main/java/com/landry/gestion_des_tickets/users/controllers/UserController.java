package com.landry.gestion_des_tickets.users.controllers;

import com.landry.gestion_des_tickets.tickets.dto.TicketDto;
import com.landry.gestion_des_tickets.tickets.exceptions.UserErrorException;
import com.landry.gestion_des_tickets.tickets.exceptions.UserNotFoundException;
import com.landry.gestion_des_tickets.users.dto.AuthenticationRequest;
import com.landry.gestion_des_tickets.users.dto.AuthenticationResponse;
import com.landry.gestion_des_tickets.users.dto.RegisterRequest;
import com.landry.gestion_des_tickets.users.dto.UserDto;
import com.landry.gestion_des_tickets.users.services.authServices.AuthenticationServiceImpl;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin("*")
public class UserController {


    private final AuthenticationServiceImpl authenticationServiceImpl;


    @PostMapping("/users")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(this.authenticationServiceImpl.register(request));
    }

    @PostMapping("/authenticate")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) throws UserNotFoundException {
        AuthenticationResponse idToken = this.authenticationServiceImpl.authenticate(request);
        return ResponseEntity.ok(idToken);

    }

    @GetMapping(path = "/users")
    public ResponseEntity<List<UserDto>> getAllUsers(){
        return new ResponseEntity<>(authenticationServiceImpl.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping(path = "/users/{id}/ticket")
    public ResponseEntity<List<TicketDto>> getAllTicketByIdUser(@PathVariable Long id) throws UserNotFoundException {
        return new ResponseEntity<>(authenticationServiceImpl.getTicketsByUserId(id), HttpStatus.OK);

    }

    @PutMapping(path = "/users/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) throws UserNotFoundException, UserErrorException {
        return new ResponseEntity<>(authenticationServiceImpl.updateUser(id, userDto), HttpStatus.OK);
    }


}
