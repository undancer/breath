import com.thimbleware.jmemcached.CacheImpl;
import com.thimbleware.jmemcached.Key;
import com.thimbleware.jmemcached.LocalCacheElement;
import com.thimbleware.jmemcached.MemCacheDaemon;
import com.thimbleware.jmemcached.storage.CacheStorage;
import com.thimbleware.jmemcached.storage.hash.ConcurrentLinkedHashMap;
import net.spy.memcached.AddrUtil;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * Created by undancer on 14-4-19.
 */
public class MemcachedSimulator implements InitializingBean, DisposableBean {

    MemCacheDaemon<LocalCacheElement> jmemcached;

    String serverUrl = "localhost:11211";

    @Override
    public void destroy() throws Exception {
        jmemcached.stop();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        jmemcached = new MemCacheDaemon<>();
        CacheStorage<Key, LocalCacheElement> storage
                = ConcurrentLinkedHashMap.create(ConcurrentLinkedHashMap.EvictionPolicy.FIFO, 1024 * 100, 1024 * 100 * 2048);

        jmemcached.setCache(new CacheImpl(storage));

        jmemcached.setAddr(AddrUtil.getAddresses(serverUrl).get(0));

        jmemcached.start();

    }


}
