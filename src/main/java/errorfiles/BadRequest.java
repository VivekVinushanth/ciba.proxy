package errorfiles;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This class is meant to throw bad requests appeared for various reasons.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequest extends Exception {

    public  BadRequest(String var1) {
        super(var1);

    }
}
