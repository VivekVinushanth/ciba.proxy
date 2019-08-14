package errorfiles;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


/**
 * This class is meant to throw internal server errors appeared for various reasons.
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalServerError extends Exception {

    public  InternalServerError(String var1) {
        super(var1);

    }

}
