package com.landry.gestion_des_tickets.users.services.authServices;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.landry.gestion_des_tickets.tickets.dto.TicketDto;
import com.landry.gestion_des_tickets.tickets.exceptions.UserAlreadyExistException;
import com.landry.gestion_des_tickets.tickets.exceptions.UserErrorException;
import com.landry.gestion_des_tickets.tickets.exceptions.UserNotFoundException;
import com.landry.gestion_des_tickets.tickets.mapper.TicketMapper;
import com.landry.gestion_des_tickets.users.configs.JwtService;
import com.landry.gestion_des_tickets.users.dao.TokenRepository;
import com.landry.gestion_des_tickets.users.dao.UsersRepository;
import com.landry.gestion_des_tickets.users.dto.AuthenticationRequest;
import com.landry.gestion_des_tickets.users.dto.AuthenticationResponse;
import com.landry.gestion_des_tickets.users.dto.RegisterRequest;
import com.landry.gestion_des_tickets.users.dto.UserDto;
import com.landry.gestion_des_tickets.users.enums.TokenType;
import com.landry.gestion_des_tickets.users.mapper.UserMapper;
import com.landry.gestion_des_tickets.users.models.Token;
import com.landry.gestion_des_tickets.users.models.Usr;
import com.landry.gestion_des_tickets.users.services.UserServices;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static com.landry.gestion_des_tickets.tickets.utils.*;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements UserServices {

    private final UsersRepository usersRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    private final UserMapper userMapper;
    private final TicketMapper ticketMapper;

    /**
     * Enregistrer un nouveau utilisateurs dans notre base de donnees
     *
     * @param request
     * @return
     */

    public AuthenticationResponse register(RegisterRequest request) {

        var user = Usr.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(request.getRoles())
                .build();

        var savedUser = usersRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(savedUser, jwtToken);

        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    /**
     * enregistrement du token dans notre base de donnees
     *
     * @param user
     * @param jwtToken
     */

    private void saveUserToken(Usr user, String jwtToken) {
        var token = Token.builder()
                .usr(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    /**
     * recuperation du token valide en fonction de l'utilisateur
     *
     * @param user
     */
    private void revokeAllUserTokens(Usr user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(Math.toIntExact(user.getId()));
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    /**
     * rafrechir le token de l'utilisateur present dans notre base de donnees
     *
     * @param request
     * @param response
     * @throws IOException
     */

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = this.usersRepository.findUsrByEmail(userEmail).orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }


    /**
     * Authentifier un nouvelle utilisateur dans notre base de donnees
     *
     * @param request
     * @return
     */
    public AuthenticationResponse authenticate(AuthenticationRequest request) throws UserNotFoundException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = usersRepository.findUsrByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);

        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }


    @Override
    public List<UserDto> getAllUsers() {
        return usersRepository.findAll()
                .stream().map(userMapper::mapUserToUserDto).toList();
    }

    @Override
    public UserDto addUser(UserDto userDto) throws UserAlreadyExistException {

        if (Objects.nonNull(userDto) && Objects.nonNull(userDto.getUsername())
                && usersRepository.findUsrByUsername(userDto.getUsername()).isPresent()) {
            throw new UserAlreadyExistException(USER_ALREADY_EXIST_IN_DATABASE);

        }
        Usr usr = usersRepository.save(userMapper.mapUserDtoToUser(userDto));

        return userMapper.mapUserToUserDto(usr);
    }

    @Override
    public UserDto updateUser(Long id, UserDto userDto) throws UserNotFoundException, UserErrorException {

        if (Objects.nonNull(id) && usersRepository.findById(id).isEmpty()) {

            throw new UserNotFoundException(USER_NOT_EXIST);
        }
        Usr usr = usersRepository.findById(id).get();
        usr.setUsername(userMapper.mapUserDtoToUser(userDto).getUsername());
        usr.setEmail(userMapper.mapUserDtoToUser(userDto).getEmail());

        return userMapper.mapUserToUserDto(usersRepository.save(usr));

    }

    @Override
    public List<TicketDto> getTicketsByUserId(Long userId) throws UserNotFoundException {

        if (usersRepository.findById(userId).isEmpty()) {
            throw new UserNotFoundException("User with ID " + userId + " not found");
        }

        return usersRepository.findById(userId).map(Usr::getTicket).stream()
                .flatMap(Collection::stream).map(ticketMapper::mapTicketToTicketDto)
                .toList();
    }

}
