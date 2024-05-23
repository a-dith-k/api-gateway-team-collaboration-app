package site.adithk.apigatewayservice.exceptions;

public class AuthorizationHeaderNotFoundException extends RuntimeException {
    public AuthorizationHeaderNotFoundException(String message) {
        super(message);
    }
}
