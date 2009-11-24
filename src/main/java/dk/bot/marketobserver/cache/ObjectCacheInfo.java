package dk.bot.marketobserver.cache;

import java.io.Serializable;
import java.util.Date;

public class ObjectCacheInfo implements Serializable{

	private final String cacheName;
	private final int size;
	private final int expiryTime;
	private final Date creationTime;

	/**
	 * 
	 * @param cacheName
	 * @param size Number of objects in a cache.
	 * @param expiryTime How many seconds after creation time data in cache will be expired.
	 * @param creationTime Creation time in milliseconds of object in a cache.
	 */
	public ObjectCacheInfo(String cacheName, int size, int expiryTime,Date creationTime) {
		this.cacheName = cacheName;
		this.size = size;
		this.expiryTime = expiryTime;
		this.creationTime = creationTime;
	}

	public String getCacheName() {
		return cacheName;
	}

	public int getSize() {
		return size;
	}

	public int getExpiryTime() {
		return expiryTime;
	}

	public Date getCreationTime() {
		return creationTime;
	}
}
