package org.kuali.maven.mojo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.junit.Test;

public class MavenLoggerTest {

	@Test
	public void commonsLogging() {
		Log log = LogFactory.getLog(this.getClass());
		log.info("Hello world");
	}

	@Test
	public void log4jLogging() {
		Logger logger = Logger.getLogger(this.getClass());
		logger.info("Hello world");
	}

}
