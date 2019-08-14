package transactionartifacts;

import cache.ProxyCache;
import errorfiles.InternalServerError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashMap;

public class User implements Artifacts {

    private String userName;
    private String password;
    private String clientappid;
    private String appid;
    private HashMap <String , String> claimstore = new HashMap <String,String>();


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getClientappid() {
        return clientappid;
    }

    public void setClientappid(String clientappid) {
        this.clientappid = clientappid;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getClaim(String claim) {
        try {
            if (claimstore.get(claim) != null) {
                return claimstore.get(claim);
            }
            throw new InternalServerError("Unexpected claim request.");

        } catch (InternalServerError internalServerError) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, internalServerError.getMessage());
        }
    }

    public void addClaim(String claimkey,String claimvalue){
        claimstore.put(claimkey,claimvalue);
    }



}
