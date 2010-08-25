package org.kuali.maven.mojo.logging;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

public class MavenLoggerTest {

	@Test
	public void test001() {
		try {
			Class<?> instance = Class.forName(MyLogger.class.getName());
			System.out.println(instance);
			Log log = LogFactory.getLog(this.getClass());
			log.info("Hello world");
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

}
