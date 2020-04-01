package io.github.syske.springbootjwtdemo.exception;

/**
 * @program: springboot-jwt-demo
 * @description:
 * @author: syske
 * @create: 2020-03-10 00:01
 */
public class AuthorizationException extends RuntimeException {

    private String message;

    public AuthorizationException() {
        super();
    }


    public AuthorizationException(Exception e, String message) {
        super(e);
        this.message = message;
    }

    public AuthorizationException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        if (message == null || message.length() == 0) {
            return super.getMessage();
        } else {
            return message;
        }
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
