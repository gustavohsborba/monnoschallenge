package com.monnos.api.starwars.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.management.InstanceAlreadyExistsException;

@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {NotParseableFieldsException.class})
    protected ResponseEntity<Object> handleNotParseableFields() {
        return new ResponseEntity<Object>(HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(value = {PlanetNotFoundException.class})
    public ResponseEntity<Object> handlePlanetNotFound(){
        return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {InstanceAlreadyExistsInDatabaseException.class})
    public ResponseEntity<Object> handleInstanceAlredyExists(){
        return new ResponseEntity<Object>(HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = {PlanetAlredyDeletedException.class})
    public ResponseEntity<Object> handlePlanetAlredyDeleted(){
        return new ResponseEntity<Object>(HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = {StarWarsApiUnavailableException.class})
    public ResponseEntity<Object> handleStarWarsApiUnavailable(){
        return new ResponseEntity<Object>(HttpStatus.BAD_GATEWAY);
    }

}