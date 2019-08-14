package handlers;

import com.nimbusds.jose.Payload;
import com.nimbusds.jwt.JWTClaimsSet;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This class extends Runtimeexception for customized responses.
 * */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ErrorCodeHandlers extends RuntimeException {

    private ErrorCodeHandlers() {

    }

    private static ErrorCodeHandlers errorCodeHandlersInstance = new ErrorCodeHandlers();

    public static ErrorCodeHandlers getInstance() {
        if (errorCodeHandlersInstance == null) {

            synchronized (ErrorCodeHandlers.class) {

                if (errorCodeHandlersInstance == null) {

                    /* instance will be created at request time */
                    errorCodeHandlersInstance = new ErrorCodeHandlers();
                }
            }
        }
        return errorCodeHandlersInstance;


    }



    public Payload createErrorResponse(String errorcode, String errormessage) {

        JWTClaimsSet claims = new JWTClaimsSet.Builder()
                .claim("errorcode", errorcode)
                .claim("errormessage", errormessage)
                .build();

        Payload errorpayload = new Payload(claims.toJSONObject());
        return errorpayload;
    }

}
