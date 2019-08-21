package configuration;

import dao.DaoFactory;

import java.io.UnsupportedEncodingException;


public class ConfigHandler {
    private ConfigHandler() {

    }

    private static ConfigHandler configHandlerInstance = new ConfigHandler();

    public void getConfiguration(String appname){
        DaoFactory.getInstance().getArtifactStoreConnector("JDBC");
        // TODO: 8/21/19 Implement connectors to get data from IS store and store to localstore-even can be cache
        try {
        ConfigurationFile configurationFile = ConfigurationFile.getInstance();
        configurationFile.setAPP_NAME(appname);
        configurationFile.setCLIENT_ID("PEHhH_VlNfxBO_y_a9EjiK8kX7sa");
        configurationFile.setCLIENT_SECRET("q3EOal32Ewvl33mI1Bzgv80i6IYa");
        configurationFile.setAUTHORIZATION_USER("admin@wso2.com");
        configurationFile.setAUTHORIZATION_PASSWORD("admin");

            configurationFile.setSEC_TOKEN(configurationFile.getAUTHORIZATION_USER(),configurationFile.getAUTHORIZATION_PASSWORD());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void setConfiguration(String appname){
       getConfiguration(appname);

    }



    public static ConfigHandler getInstance() {
        if (configHandlerInstance == null) {

            synchronized (ConfigHandler.class) {

                if (configHandlerInstance == null) {

                    /* instance will be created at request time */
                    configHandlerInstance = new ConfigHandler();
                }
            }
        }
        return configHandlerInstance;


    }


}
