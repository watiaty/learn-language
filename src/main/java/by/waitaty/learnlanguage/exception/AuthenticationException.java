package by.waitaty.learnlanguage.exception;

public class AuthenticationException extends RuntimeException {
    public AuthenticationException() {
        super("Dont have access");
    }
}
