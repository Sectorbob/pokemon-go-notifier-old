package sectorbob.gaming.pokemon.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Kyle Heide
 *
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class ConflictException extends RuntimeException {

    public ConflictException() {
        this("Conflict!");
    }

    public ConflictException(String message) {
        this(message, null);
    }

    public ConflictException(String message, Throwable cause) {
        super(message, cause);
    }

}
