package site.adithk.apigatewayservice.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import site.adithk.apigatewayservice.exceptions.AuthorizationHeaderNotFoundException;
import site.adithk.apigatewayservice.exceptions.UnauthorizedException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<String> unauthorizedAccess(UnauthorizedException ex){

        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AuthorizationHeaderNotFoundException.class)
    public ResponseEntity<String> authHeaderNotFound(AuthorizationHeaderNotFoundException ex){

        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

}
