package dk.bot.marketobserver.cache;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class ObjectCacheTest {

	private ObjectCache<Integer> objCache;

	@Test
	public void testGetObjects() {
		objCache = new ObjectCache<Integer>("cache1",1);
		Set<Integer> numbers = new HashSet<Integer>(); 
		numbers.addAll(Arrays.asList(4,5));
		long creationTime = System.currentTimeMillis();
		objCache.setObjects(numbers, creationTime);

		assertEquals(numbers, new HashSet<Integer>(objCache.getObjects()));
		assertEquals(new Integer(5), objCache.getObject(new Integer(5)));
		assertEquals("cache1", objCache.getCacheInfo().getCacheName());
		assertEquals(new Date(creationTime), objCache.getCacheInfo().getCreationTime());
		assertEquals(2, objCache.getCacheInfo().getSize());
		assertEquals(1, objCache.getCacheInfo().getExpiryTime());
	}

	@Test
	public void testGetObjectsExpired() {
		objCache = new ObjectCache<Integer>("cache1",4);

		Set<Integer> numbers = new HashSet<Integer>(); 
		numbers.addAll(Arrays.asList(4,5));
		long creationTime = System.currentTimeMillis() - 4001;
		objCache.setObjects(numbers, creationTime);

		assertEquals(null, objCache.getObjects());
		assertEquals(null, objCache.getObject(new Integer(5)));
		assertEquals("cache1", objCache.getCacheInfo().getCacheName());
		assertEquals(new Date(creationTime), objCache.getCacheInfo().getCreationTime());
		assertEquals(0, objCache.getCacheInfo().getSize());
		assertEquals(4, objCache.getCacheInfo().getExpiryTime());
	}

	@Test
	public void testGetObjectsExpiryRefreshed() throws InterruptedException {
		objCache = new ObjectCache<Integer>("cache1",1);

		Set<Integer> numbers = new HashSet<Integer>(); 
		numbers.addAll(Arrays.asList(4,5));
		objCache.setObjects(numbers, System.currentTimeMillis());

		Thread.sleep(500);
		assertEquals(numbers, new HashSet<Integer>(objCache.getObjects()));
		assertEquals(new Integer(5), objCache.getObject(new Integer(5)));
		

		Set<Integer> numbers2 = new HashSet<Integer>(); 
		numbers2.addAll(Arrays.asList(4,5,6));
		objCache.setObjects(numbers2, System.currentTimeMillis());

		Thread.sleep(900);
		assertEquals(numbers2, new HashSet<Integer>(objCache.getObjects()));
		assertEquals(new Integer(6), objCache.getObject(new Integer(6)));
		
	}

	@Test
	public void testGetObjectsNoExpiry() throws InterruptedException {
		objCache = new ObjectCache<Integer>("cache1",0);

		Set<Integer> numbers = new HashSet<Integer>(); 
		numbers.addAll(Arrays.asList(4,5));
		long creationTime = System.currentTimeMillis();
		objCache.setObjects(numbers, creationTime);

		Thread.sleep(500);

		assertEquals(numbers, new HashSet<Integer>(objCache.getObjects()));
		assertEquals(new Integer(5), objCache.getObject(new Integer(5)));
		assertEquals("cache1", objCache.getCacheInfo().getCacheName());
		assertEquals(new Date(creationTime), objCache.getCacheInfo().getCreationTime());
		assertEquals(2, objCache.getCacheInfo().getSize());
		assertEquals(0, objCache.getCacheInfo().getExpiryTime());
	}

}
