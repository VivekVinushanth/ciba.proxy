package cibaparameters;

/**
 * This class is meant to store the features of the transaction.
 * */
public class CIBAParameters {


    private  CIBAParameters() {

    }

    private  static CIBAParameters cibaParametersInstance = new CIBAParameters();

    public static CIBAParameters getInstance() {
        if (cibaParametersInstance == null) {

            synchronized (CIBAParameters.class) {

                if (cibaParametersInstance == null) {

                    /* instance will be created at request time */
                    cibaParametersInstance = new CIBAParameters();
                }
            }
        }
        return cibaParametersInstance;


    }
    /**
     * Paramters specified in CIBA.
      */
    /*private  String scope;
    private  String client_notification_token;
    private  String acr_values;
    private  String login_hint_token;
    private  String login_hint;
    private  String id_token_hint;
    private  String binding_message;
    private  String user_code;
    private  long requested_expiry;
*/
    /**
     * CIBA_Parameters required if signed.
    */
  /*  private  String aud;
    private  String iss;
    private  long exp;
    private  long iat;
    private  long nbf;
    private  String jti;*/


    /**
     * CIBA authentication_response CIBA_CIBA_Parameters.
     */
    /*private  String auth_req_id = "1c266114-a1be-4252-8ad1-04986c5b9ac1";*/
    private  long expires_in = 3600;
    private long interval=2;

    public String getCallBackURL() {
        return callBackURL;
    }

    public void setCallBackURL(String callBackURL) {
        this.callBackURL = callBackURL;
    }

    private String callBackURL = "http://10.10.10.134:8080/CallBackEndpoint" ;

    private  String grant_type = "urn:openid:params:grant-type:ciba";


    public String getAUTHORIZE_ENDPOINT() {
        return AUTHORIZE_ENDPOINT;
    }

    public void setAUTHORIZE_ENDPOINT(String AUTHORIZE_ENDPOINT) {
        this.AUTHORIZE_ENDPOINT = AUTHORIZE_ENDPOINT;
    }

    private String AUTHORIZE_ENDPOINT = "https://localhost:9443/oauth2/authorize";

    /**
     *  Token response parameters.
     *  */
   /* private  String accessToken = "G5kXH2wHvUra0sHlDy1iTkDJgsgUO1bN";
    private  String token_type = "Bearer";
    private  String refresh_token = "4bwc0ESC_IAhflf-ACC_vjD_ltc11ne-8gFPfA2Kx16";*/
    private  long token_expires_in = 3600;
    //private  String id_token = "eyJhbGciOiJSUzI1NiIsImtpZCI6IjE2NzcyNiJ9.eyJpc3MiOiJodHRwczovL3NlcnZlci5leGFtcGxlLmNvbSIsInN1YiI6IjI0ODI4OTc2MTAwMSIsImF1ZCI6InM2QmhkUmtxdDMiLCJlbWFpbCI6ImphbmVkb2VAZXhhbXBsZS5jb20iLCJleHAiOjE1Mzc4MTk4MDMsImlhdCI6MTUzNzgxOTUwMywiYXRfaGFzaCI6Ild0MGtWRlhNYWNxdm5IZXlVMDAwMXciLCJ1cm46b3BlbmlkOnBhcmFtczpqd3Q6Y2xhaW06cnRfaGFzaCI6InNIYWhDdVNwWENSZzVta0REdnZyNHciLCJ1cm46b3BlbmlkOnBhcmFtczpqd3Q6Y2xhaW06YXV0aF9yZXFfaWQiOiIxYzI2NjExNC1hMWJlLTQyNTItOGFkMS0wNDk4NmM1YjlhYzEifQ.SGB5_a8E7GjwtoYrkFyqOhLK6L8-Wh1nLeREwWj30gNYOZW_ZB2mOeQ5yiXqeKJeNpDPssGUrNo-3N-CqNrbmVCbXYTwmNB7IvwE6ZPRcfxFV22oou-NS4-3rEa2ghG44Fi9D9fVURwxrRqgyezeD3HHVIFUnCxHUou3OOpj6aOgDqKI4Xl2xJ0-kKAxNR8LljUp64OHgoS-UO3qyfOwIkIAR7o4OTK_3Oy78rJNT0Y0RebAWyA81UDCSf_gWVBp-EUTI5CdZ1_odYhwB9OWDW1A22Sf6rmjhMHGbQW4A9Z822yiZZveuT_AFZ2hi7yNp8iFPZ8fgPQJ5pPpjA7udg";



   /* public String getAccessToken() {
        return accessToken;
    }

    public String getToken_type() {
        return token_type;
    }

    public String getRefresh_token() {
        return refresh_token;
    }*/

    public long getToken_expires_in() {
        return token_expires_in;
    }

/*    public String getId_token() {
        return id_token;
    }*/



/*


    public String getGrant_type() {

        return grant_type;
    }

    public void setGrant_type(String grant_type) {
        this.grant_type = grant_type;
    }

    *//*
    Getters and setters for CIBA _Parameterss.
    *//*
    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getClient_notification_token() {
        return client_notification_token;
    }

    public void setClient_notification_token(String client_notification_token) {
        this.client_notification_token = client_notification_token;
    }

    public String getAcr_values() {
        return acr_values;
    }

    public void setAcr_values(String acr_values) {
        this.acr_values = acr_values;
    }

    public String getLogin_hint_token() {
        return login_hint_token;
    }

    public void setLogin_hint_token(String login_hint_token) {
        this.login_hint_token = login_hint_token;
    }

    public String getLogin_hint() {
        return login_hint;
    }

    public void setLogin_hint(String login_hint) {
        this.login_hint = login_hint;
    }

    public String getId_token_hint() {
        return id_token_hint;
    }

    public void setId_token_hint(String id_token_hint) {
        this.id_token_hint = id_token_hint;
    }

    public String getBinding_message() {
        return binding_message;
    }

    public void setBinding_message(String binding_message) {
        this.binding_message = binding_message;
    }

    public String getUser_code() {
        return user_code;
    }

    public void setUser_code(String user_code) {
        this.user_code = user_code;
    }

    public long getRequested_expiry() {
        return requested_expiry;
    }

    public void setRequested_expiry(long requested_expiry) {
        this.requested_expiry = requested_expiry;
    }


    *//*
    Getters and setters if signed request.
    *//*
    public String getAud() {
        return aud;
    }

    public void setAud(String aud) {
        this.aud = aud;
    }

    public String getIss() {
        return iss;
    }

    public void setIss(String iss) {
        this.iss = iss;
    }

    public long getExp() {
        return exp;
    }

    public void setExp(long exp) {
        this.exp = exp;
    }

    public long getIat() {
        return iat;
    }

    public void setIat(long iat) {
        this.iat = iat;
    }

    public long getNbf() {
        return nbf;
    }

    public void setNbf(long nbf) {
        this.nbf = nbf;
    }

    public String getJti() {
        return jti;
    }

    public void setJti(String jti) {
        this.jti = jti;
    }



    *//* Getters and setters of authentication_response CIBA_CIBA_Parameterss
     *//*
    public String getAuth_req_id() {
        return auth_req_id;
    }

    public void setAuth_req_id(String auth_req_id) {
        this.auth_req_id = auth_req_id;
    }*/

    public long getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(long expires_in) {
        this.expires_in = expires_in;
    }

    public long getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public String getGrant_type() {
        return grant_type;
    }

    public void setGrant_type(String grant_type) {
        this.grant_type = grant_type;
    }
}
