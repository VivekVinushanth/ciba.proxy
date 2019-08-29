package transactionartifacts;

public class PollingAtrribute implements Artifacts {

    private String auth_req_id;
    private long expiresIn;
    private long lastPolledTime;
    private long pollingInterval;
    private long issuedTime;

    public String getAuth_req_id() {
        return auth_req_id;
    }

    public void setAuth_req_id(String auth_req_id) {
        this.auth_req_id = auth_req_id;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public long getLastPolledTime() {
        return lastPolledTime;
    }

    public void setLastPolledTime(long lastPolledTime) {
        this.lastPolledTime = lastPolledTime;
    }

    public long getPollingInterval() {
        return pollingInterval;
    }

    public void setPollingInterval(long pollingInterval) {
        this.pollingInterval = pollingInterval;
    }

    public long getIssuedTime() {
        return issuedTime;
    }

    public void setIssuedTime(long issuedTime) {
        this.issuedTime = issuedTime;
    }
}
