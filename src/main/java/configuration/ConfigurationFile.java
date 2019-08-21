package configuration;


import java.io.UnsupportedEncodingException;
import java.util.Base64;

public class ConfigurationFile {
    private ConfigurationFile() {

    }

    private static ConfigurationFile configurationFileInstance = new ConfigurationFile();

    public static ConfigurationFile getInstance() {
        if (configurationFileInstance == null) {

            synchronized (ConfigurationFile.class) {

                if (configurationFileInstance == null) {

                    /* instance will be created at request time */
                    configurationFileInstance = new ConfigurationFile();
                }
            }
        }
        return configurationFileInstance;


    }

    private  String APP_NAME;
    private  String CLIENT_ID;
    private  String CLIENT_SECRET;
    private  String SEC_TOKEN;
    private String AUTHORIZATION_USER;

    public String getAUTHORIZATION_USER() {
        return AUTHORIZATION_USER;
    }

    public void setAUTHORIZATION_USER(String AUTHORIZATION_USER) {
        this.AUTHORIZATION_USER = AUTHORIZATION_USER;
    }

    public String getAUTHORIZATION_PASSWORD() {
        return AUTHORIZATION_PASSWORD;
    }

    public void setAUTHORIZATION_PASSWORD(String AUTHORIZATION_PASSWORD) {
        this.AUTHORIZATION_PASSWORD = AUTHORIZATION_PASSWORD;
    }

    private String AUTHORIZATION_PASSWORD;

    public String getSEC_TOKEN() {
        return SEC_TOKEN;
    }

    public void setSEC_TOKEN(String AUTHORIZATION_USER ,String AUTHORIZATION_PASSWORD) throws UnsupportedEncodingException {

        this.SEC_TOKEN = Base64.getEncoder().encodeToString((AUTHORIZATION_USER+":"+AUTHORIZATION_PASSWORD).getBytes("utf-8"));
        System.out.println("Sec token here :"+SEC_TOKEN);
    }


    public void setAPP_NAME(String APP_NAME) {
        this.APP_NAME = APP_NAME;
    }

    public void setCLIENT_ID(String CLIENT_ID) {
        this.CLIENT_ID = CLIENT_ID;
    }

    public void setCLIENT_SECRET(String CLIENT_SECRET) {
        this.CLIENT_SECRET = CLIENT_SECRET;
    }

    public String getAPP_NAME() {
        return APP_NAME;
    }

    public String getCLIENT_ID() {
        return CLIENT_ID;
    }

    public String getCLIENT_SECRET() {
        return CLIENT_SECRET;
    }




}
