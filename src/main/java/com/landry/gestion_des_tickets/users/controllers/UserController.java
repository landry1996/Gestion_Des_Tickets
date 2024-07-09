package com.landry.gestion_des_tickets.users.controllers;

import com.landry.gestion_des_tickets.tickets.dto.TicketDto;
import com.landry.gestion_des_tickets.tickets.exceptions.UserAlreadyExistException;
import com.landry.gestion_des_tickets.tickets.exceptions.UserErrorException;
import com.landry.gestion_des_tickets.tickets.exceptions.UserNotFoundException;
import com.landry.gestion_des_tickets.users.dao.UsersRepository;
import com.landry.gestion_des_tickets.users.dto.AuthenticationRequest;
import com.landry.gestion_des_tickets.users.dto.AuthenticationResponse;
import com.landry.gestion_des_tickets.users.dto.RegisterRequest;
import com.landry.gestion_des_tickets.users.dto.UserDto;
import com.landry.gestion_des_tickets.users.services.UserServices;
import com.landry.gestion_des_tickets.users.services.authServices.AuthenticationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {


    private final AuthenticationService authenticationService;


    @PostMapping("/users")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(this.authenticationService.register(request));

    }

    @PostMapping("/authenticate")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) throws UserNotFoundException {
        AuthenticationResponse idToken = this.authenticationService.authenticate(request);
        return ResponseEntity.ok(idToken);

    }

    @GetMapping(path = "/users")
    public ResponseEntity<List<UserDto>> getAllUsers(){
        return new ResponseEntity<>(authenticationService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping(path = "/users/{id}/ticket")
    public ResponseEntity<List<TicketDto>> getAllTicketByIdUser(@PathVariable Long id) throws UserNotFoundException {
        return new ResponseEntity<>(authenticationService.getTicketsByUserId(id), HttpStatus.OK);

    }

    @PutMapping(path = "/users/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) throws UserNotFoundException, UserErrorException {
        return new ResponseEntity<>(authenticationService.updateUser(id, userDto), HttpStatus.OK);
    }


}
