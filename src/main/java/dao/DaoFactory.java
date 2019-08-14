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
    *  This returns preferd connectors as the user does.
    *  */
    public DbConnectors getConnector(String name) {

        if (name.equalsIgnoreCase("InMemoryCache")) {
            return CacheConnector.getInstance();
            //return new CacheConnector();
        }

        if (name.equalsIgnoreCase("RedisCache")) {
            return RedisConnector.getInstance();
        }

        if (name.equals("JDBCcache")) {
            return JdbcConnector.getInstance();
        }
        return null;
    }

    public StoreConnector getStorageConnector(){
        return CibaStorageConnector.getInstance();
    }

}
