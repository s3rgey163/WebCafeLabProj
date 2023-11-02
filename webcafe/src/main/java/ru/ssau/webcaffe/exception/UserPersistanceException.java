package ru.ssau.webcaffe.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserPersistanceException extends RuntimeException {
    public UserPersistanceException() {
        super();
    }

    public UserPersistanceException(String message) {
        super(message);
    }

    public UserPersistanceException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserPersistanceException(Throwable cause) {
        super(cause);
    }

    protected UserPersistanceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
