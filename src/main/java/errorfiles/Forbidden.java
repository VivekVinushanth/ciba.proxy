package errorfiles;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


/**
 * This class is meant to throw forbidden requests appeared for various reasons.
 */
@ResponseStatus(HttpStatus.FORBIDDEN)
public class Forbidden extends Exception {

    public  Forbidden(String var1) {
        super(var1);

    }
}

