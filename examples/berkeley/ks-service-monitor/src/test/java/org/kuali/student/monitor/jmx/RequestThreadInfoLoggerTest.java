/**
 * 
 */
package org.kuali.student.monitor.jmx;

import org.junit.Before;
import org.junit.Test;
import org.kuali.student.deployment.monitor.jmx.RequestThreadInfoLogger;

/**
 * @author randy
 *
 */
public class RequestThreadInfoLoggerTest {
	RequestThreadInfoLogger logger;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		// logger = new RequestThreadInfoLogger();
		
	}

	/**
	 * Test method for {@link org.kuali.student.deployment.monitor.jmx.RequestThreadInfoLogger#RequestThreadInfoLogger()}.
	 */
	@Test
	public void testRequestThreadInfoLogger() {
		 logger = new RequestThreadInfoLogger();
		 logger.log("request #1", "threadInfo #1");
		 logger.log("request Info #2", "threadInfo #2");
		 logger.log("request Info #3", "threadInfo #3");
	}
	

}
