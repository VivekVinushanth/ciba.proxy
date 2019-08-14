package transactionartifacts;

public class TokenRequest implements Artifacts{

    private String auth_req_id;
    private String grant_type;
   // private enum errormessage;

    public String getAuth_req_id() {
        return auth_req_id;
    }

    public void setAuth_req_id(String auth_req_id) {
        this.auth_req_id = auth_req_id;
    }

    public String getGrant_type() {
        return grant_type;
    }

    public void setGrant_type(String grant_type) {
        this.grant_type = grant_type;
    }




}
