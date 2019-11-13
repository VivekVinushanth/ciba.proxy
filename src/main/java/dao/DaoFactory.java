package dao;

/**
 * This produces the connectors from service layer to data layer.
 * But the conector can be produced in privileged way.
 *
 * */
public class DaoFactory {

    private DaoFactory() {

    }

    private static DaoFactory daoFactoryInstance = new DaoFactory();

    public static DaoFactory getInstance() {
        if (daoFactoryInstance == null) {

            synchronized (DaoFactory.class) {

                if (daoFactoryInstance == null) {

                    /* instance will be created at request time */
                    daoFactoryInstance = new DaoFactory();
                }
            }
        }
        return daoFactoryInstance;
    }


   /**
    *  This returns preferred connectors as the user does.
    *  */
    public ArtifactStoreConnectors getArtifactStoreConnector(String name) {

        if (name.equalsIgnoreCase("InMemoryCache")) {
            return CacheArtifactStoreConnector.getInstance();
            //return new CacheArtifactStoreConnector();
        }

        if (name.equalsIgnoreCase("Redis")) {
            return RedisArtifactStoreConnector.getInstance();
        }

        if (name.equals("JDBC")) {
            return JdbcArtifactStoreConnector.getInstance();
        }
        return null;
    }

    public UserStoreConnector getUserStoreConnector(String name){

        if (name.equalsIgnoreCase("InMemoryCache")) {
            return CibaUserStoreCacheConnector.getInstance();
            //return new CacheStorageConnector();
        }

        if (name.equalsIgnoreCase("Redis")) {
            return CibaUserStoreRedisConnector.getInstance();
        }

        if (name.equals("JDBC")) {
            return CibaUserStoreJdbcConnector.getInstance();
        }
        return null;
    }



    public ClientStoreConnector getClientStoreConnector(String name){

        if (name.equalsIgnoreCase("InMemoryCache")) {
            return CibaClientStoreCacheConnector.getInstance();
            //return new CacheStorageConnector();
        }

        if (name.equalsIgnoreCase("Redis")) {
            return CibaClientStoreRedisConnector.getInstance();
        }

        if (name.equals("JDBC")) {
            return CibaClientStoreJdbcConnector.getInstance();
        }
        return null;
    }


}
