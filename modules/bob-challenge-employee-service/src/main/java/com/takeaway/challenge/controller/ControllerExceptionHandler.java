package com.takeaway.challenge.controller;

import com.takeaway.challenge.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import javax.persistence.EntityNotFoundException;
import java.text.ParseException;
import static com.takeaway.challenge.constants.ErrorMessages.*;

@RestControllerAdvice
@Slf4j
public class ControllerExceptionHandler {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({EntityNotFoundException.class})
    public ErrorResponse entityNotFoundException(EntityNotFoundException e) {
        log.warn("Caught an exception {}" , e.getMessage());
        return new ErrorResponse(ENTITY_NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse validationException(MethodArgumentNotValidException e) {
        log.warn("Caught a validation exception {}" , e.getMessage());
        return new ErrorResponse(DATA_VALIDATION_EXCEPTION + e.getMessage());
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse uniqueConstraintException(DataIntegrityViolationException e) {
        log.warn("Caught a unique constraint exception {}" , e.getMessage());
        return new ErrorResponse(UNIQUE_CONSTRAINT_EXCEPTION + e.getMessage());
    }
    @ExceptionHandler(EmptyResultDataAccessException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse uniqueConstraintException(EmptyResultDataAccessException e) {
        log.warn("Caught empty result set exception {}" , e.getMessage());
        return new ErrorResponse(EMPTY_RESULTSET_EXCEPTION  + e.getMessage());
    }

    @ExceptionHandler(ParseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse parseException(ParseException e) {
        log.warn("Caught parse exception {}" , e.getMessage());
        return new ErrorResponse(PARSE_EXCEPTION + e.getMessage());
    }
}
