package errorfiles;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


/**
 * This class is meant to throw unauthorized requests appeared for various reasons.
 */
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnAuthorizedRequest extends Exception {


    public  UnAuthorizedRequest(String var1) {
        super(var1);

    }
}
