package sectorbob.gaming.pokemon.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by ltm688 on 9/1/15.
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalServerErrorException extends RuntimeException {

    public InternalServerErrorException() {
        this("Internal Server Error!");
    }

    public InternalServerErrorException(String message) {
        this(message, null);
    }

    public InternalServerErrorException(String message, Throwable cause) {
        super(message, cause);
    }
}
