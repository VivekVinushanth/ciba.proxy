package tempErrorCache;

import java.util.HashMap;

public class TempErrorCache {
    private HashMap<String, String> AuthenticationResponseCache = new HashMap<String ,String>();
    private TempErrorCache() {

    }

    private static TempErrorCache tokenRequestValidatorInstance = new TempErrorCache();

    public static TempErrorCache getInstance() {
        if (tokenRequestValidatorInstance == null) {

            synchronized (TempErrorCache.class) {

                if (tokenRequestValidatorInstance == null) {

                    /* instance will be created at request time */
                    tokenRequestValidatorInstance = new TempErrorCache();
                }
            }
        }
        return tokenRequestValidatorInstance;


    }

    public void addAuthenticationStatus(String auth_req_id, String state){
        AuthenticationResponseCache.put(auth_req_id,state);

    }

    public String  getAuthenticationResponse(String auth_req_id){
        return AuthenticationResponseCache.get(auth_req_id);

    }

    public void removeAuthResponse(String auth_req_id){
        AuthenticationResponseCache.remove(auth_req_id);
    }
}
