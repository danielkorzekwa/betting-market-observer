package dk.bot.marketobserver.cache;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Objects cache.
 * 
 * @author daniel
 * 
 * @param <T>
 */
public class ObjectCache<T> {

	private final String cacheName;
	private final int expiryTime;

	private long creationTime;
	private Map<T,T> objects;

	private ReentrantLock lock = new ReentrantLock();

	/**
	 * 
	 * @param expireTime
	 *            Expiry time in seconds, 0 for no expiry
	 */
	public ObjectCache(String cacheName, int expiryTime) {
		this.cacheName = cacheName;
		this.expiryTime = expiryTime;
	}

	/**
	 * @return null if cache is expired
	 */
	public Collection<T> getObjects() {
		lock.lock();
		try {
			long liveTime = (System.currentTimeMillis() - creationTime);
			if (objects!=null && (expiryTime == 0 || liveTime <= expiryTime * 1000)) {
				return objects.values();
			} else {
				return null;
			}
		} finally {
			lock.unlock();
		}
	}
	
	/**
	 * @return null if cache is expired
	 */
	public T getObject(T object) {
		lock.lock();
		try {
			long liveTime = (System.currentTimeMillis() - creationTime);
			if (objects!=null && (expiryTime == 0 || liveTime <= expiryTime * 1000)) {
				return objects.get(object);
			} else {
				return null;
			}
		} finally {
			lock.unlock();
		}
	}

	public void setObjects(Set<T> objects, long creationTime) {

		lock.lock();
		try {
			this.creationTime = creationTime;
			this.objects = new HashMap<T, T>(objects.size());
			for(T object: objects) {
				this.objects.put(object,object);
			}
		} finally {
			lock.unlock();
		}
	}

	public ObjectCacheInfo getCacheInfo() {
		lock.lock();
		try {
			int size=0;
			long liveTime = (System.currentTimeMillis() - creationTime);
			if (objects!=null && (expiryTime == 0 || liveTime <= expiryTime * 1000)) {
				size=objects.size();
			} 
			return new ObjectCacheInfo(cacheName, size, expiryTime, new Date(creationTime));
		} finally {
			lock.unlock();
		}
	}
}
