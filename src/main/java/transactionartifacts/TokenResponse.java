package transactionartifacts;

public class TokenResponse implements Artifacts{
    private  String accessToken ;
    private  String tokenType;
    private  String refreshToken ;
    private  long tokenExpirein;
    private  String idToken;


    public long getTokenExpirein() {
        return tokenExpirein;
    }

    public void setTokenExpirein(long tokenExpirein) {
        this.tokenExpirein = tokenExpirein;
    }


    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }



    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }




}
