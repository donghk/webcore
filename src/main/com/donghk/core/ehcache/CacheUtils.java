package com.donghk.core.ehcache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.donghk.core.util.CommonUtil;

/**
 * @ClassName: CacheBuilder
 * @Description: Cache工具类
 * 
 */
public class CacheUtils {

	final static Log log = LogFactory.getLog(CacheUtils.class);

	private static CacheManager manager = new CacheManager(CommonUtil.getRealPath() + "WEB-INF/classes/config/ehcache.xml");

	static {
		manager = CacheManager.getInstance();
		if (manager == null) {
			manager = CacheManager.create(CommonUtil.getRealPath() + "WEB-INF/classes/config/ehcache.xml");
			System.out.println();
		}
	}

	/**
	 * @Description: 将Cache放入CacheManager中
	 * @param cacheEntity
	 */
	protected synchronized static void put(CacheEntity cacheEntity) {
		Cache cache = getCache(cacheEntity.getCacheName());
		if (cache != null) {
			try {
				cache.remove(cacheEntity.getKey());
				Element elem = new Element(cacheEntity.getKey(), cacheEntity.getCacheObject());
				cache.put(elem);
				if (log.isDebugEnabled()) {
					log.debug("put cache(" + cacheEntity.getCacheName() + ") of " + cacheEntity.getKey() + " done.");
				}
			} catch (Exception e) {
				log.error("put cache(" + cacheEntity.getCacheName() + ") of " + cacheEntity.getKey() + " failed.", e);
			}
		}
	}

	/**
	 * @Description: 按缓存名称与KEY取的对象
	 * @param cacheName
	 * @param key
	 * @return
	 */
	public static Object get(CacheName cacheName, String key) {
		Cache cache = getCache(cacheName);
		if (cache != null) {
			try {
				Element elem = cache.get(key);
				if (elem != null && !cache.isExpired(elem))
					return elem.getObjectValue();
			} catch (Exception e) {
				log.error("Get cache(" + cacheName + ") of " + key + " failed.", e);
			}
		}
		log.info("Element is null (" + cacheName + ") or " + key + " undefined.");
		return null;
	}

	/**
	 * @Description:
	 * @param cacheName
	 * @return
	 * @throws IllegalStateException
	 */
	private static Cache getCache(CacheName cacheName) throws IllegalStateException {
		return manager.getCache(cacheName.name);
	}

	/**
	 * @Description: 刷新Cache
	 * @param cache
	 * @throws IllegalStateException
	 * @throws CacheException
	 */
	public static void flushCache(CacheName cacheName) throws IllegalStateException, CacheException {
		Cache cache = getCache(cacheName);
		if (cache != null) {
			cache.flush();
		}
	}

	/**
	 * @Description: 停止缓存管理器
	 */
	public static void shutdown() {
		if (manager != null) {
			manager.shutdown();
		}
	}
}
