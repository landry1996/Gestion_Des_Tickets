package com.landry.gestion_des_tickets.tickets.errorHandles;

import com.landry.gestion_des_tickets.tickets.enums.ErrorCode;
import com.landry.gestion_des_tickets.tickets.exceptions.*;
import com.landry.gestion_des_tickets.tickets.models.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDate;

@RestControllerAdvice
public class ErrorHandle {

    @ExceptionHandler(TicketErrorException.class)
    public ResponseEntity<ErrorMessage> handleException(TicketErrorException exception){
        final HttpStatus internalServerError = HttpStatus.INTERNAL_SERVER_ERROR;
        final ErrorMessage errorMessage = ErrorMessage.builder()
                .status(ErrorCode.INTERNAL_SERVE_ERROR.value)
                .httpStatus(internalServerError)
                .message(exception.getMessage())
                .timestamp(LocalDate.now())
                .build();
        return new ResponseEntity<>(errorMessage, internalServerError);
    }

    @ExceptionHandler(TicketNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleException(TicketNotFoundException exception){
        final HttpStatus notFound = HttpStatus.NOT_FOUND;
        final ErrorMessage errorMessage = ErrorMessage.builder()
                .status(ErrorCode.NOT_FOUND.value)
                .httpStatus(notFound)
                .message(exception.getMessage())
                .timestamp(LocalDate.now())
                .build();
        return new ResponseEntity<>(errorMessage, notFound);
    }

    @ExceptionHandler(TicketAlreadyExistException.class)
    public ResponseEntity<ErrorMessage> handleException(TicketAlreadyExistException exception){
        final HttpStatus alreadyReported = HttpStatus.ALREADY_REPORTED;
        final ErrorMessage errorMessage = ErrorMessage.builder()
                .status(ErrorCode.ALREADY_EXIST.value)
                .httpStatus(alreadyReported)
                .message(exception.getMessage())
                .timestamp(LocalDate.now())
                .build();
        return new ResponseEntity<>(errorMessage, alreadyReported);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleException(UserNotFoundException exception){
        final HttpStatus notFound = HttpStatus.NOT_FOUND;
        final ErrorMessage errorMessage = ErrorMessage.builder()
                .status(ErrorCode.NOT_FOUND.value)
                .httpStatus(notFound)
                .message(exception.getMessage())
                .timestamp(LocalDate.now())
                .build();
        return new ResponseEntity<>(errorMessage, notFound);
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<ErrorMessage> handleException(UserAlreadyExistException exception){
        final HttpStatus alreadyReported = HttpStatus.ALREADY_REPORTED;
        final ErrorMessage errorMessage = ErrorMessage.builder()
                .status(ErrorCode.ALREADY_EXIST.value)
                .httpStatus(alreadyReported)
                .message(exception.getMessage())
                .timestamp(LocalDate.now())
                .build();
        return new ResponseEntity<>(errorMessage, alreadyReported);
    }
    @ExceptionHandler(UserErrorException.class)
    public ResponseEntity<ErrorMessage> handleException(UserErrorException exception){
        final HttpStatus internalServerError = HttpStatus.INTERNAL_SERVER_ERROR;
        final ErrorMessage errorMessage = ErrorMessage.builder()
                .status(ErrorCode.INTERNAL_SERVE_ERROR.value)
                .httpStatus(internalServerError)
                .message(exception.getMessage())
                .timestamp(LocalDate.now())
                .build();
        return new ResponseEntity<>(errorMessage, internalServerError);
    }
}
