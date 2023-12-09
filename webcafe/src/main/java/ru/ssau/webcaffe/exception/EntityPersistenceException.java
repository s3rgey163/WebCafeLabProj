package ru.ssau.webcaffe.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EntityPersistenceException extends RuntimeException {
    public EntityPersistenceException() {
        super();
    }

    public EntityPersistenceException(String message) {
        super(message);
    }

    public EntityPersistenceException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityPersistenceException(Throwable cause) {
        super(cause);
    }

    protected EntityPersistenceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
