package org.kuali.maven.mojo.logging;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

public class MavenLoggerTest {

	@Test
	public void helloWorld() {
		Log log = LogFactory.getLog(this.getClass());
		log.info("Hello world");
	}

}
