/**
 * 
 */
package org.kuali.student.services.test.lum;

import org.junit.Test;

/**
 * @author randy
 *
 */
public class RecursionLoopTest {

	/**
	 * Test method for {@link org.kuali.student.services.test.lum.MemoryLRUCache#isEmpty()}.
	 */
	@Test
	public void testIsEmpty() {
		MemoryLRUCache<String, String> cache = new MemoryLRUCache<String, String>(10);
	    boolean empty = cache.isEmpty();
		
	}

}
