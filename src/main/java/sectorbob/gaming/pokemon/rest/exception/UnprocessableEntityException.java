package sectorbob.gaming.pokemon.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Kyle Heide
 *
 */
@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class UnprocessableEntityException extends RuntimeException {

    public UnprocessableEntityException() {
        this("Unprocessable Entity!");
    }

    public UnprocessableEntityException(String message) {
        this(message, null);
    }

    public UnprocessableEntityException(String message, Throwable cause) {
        super(message, cause);
    }
}
