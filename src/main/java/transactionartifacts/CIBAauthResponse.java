package transactionartifacts;

public class CIBAauthResponse implements Artifacts{

    /* CIBA authentication_response parameters
    */
    private String auth_req_id;
    private long expires_in;
    private long interval;


    /* Getters and setters of authentication_response parameters
    */
    public String getAuth_req_id() {
        return auth_req_id;
    }

    public void setAuth_req_id(String auth_req_id) {
        this.auth_req_id = auth_req_id;
    }

    public long getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(long expires_in) {
        this.expires_in = expires_in;
    }

    public long getInterval() {
        return interval;
    }

    public void setInterval(long interval) {
        this.interval = interval;
    }

}
