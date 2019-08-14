package cache;

public interface ProxyCache {

       public void add(String key, Object value);

       public void remove(String key);

       public Object get(String key);

       public void clear();

       public long size();

       public void register(Object object);



}
