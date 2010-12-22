package ca.ubc.student.kuali.lum.cdm;

import org.apache.log4j.Logger;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.springframework.aop.ThrowsAdvice;

/**
 * LUM Notifier Advice.
 * 
 * @author jhong
 */
public class LumEventNotifierAdvice implements ThrowsAdvice  {

	private static final Logger LOGGER = Logger.getLogger(LumEventNotifierAdvice.class);
	
	/**
	 * Creates an event exception.
	 * 
	 * @param exc Exception
	 */
	public void afterThrowing(final Exception exc) {
		createEventException(exc);
	}
	
	/**
	 * Creates a Clu event.
	 *  
	 * @param cluInfo the cluInfo
	 */
	public void createCluEvent(final CluInfo cluInfo) {
		LOGGER.info("\n*****  createCluEvent: Successfully created CluInfo with cluId=" + cluInfo.getId() + "\n");
	}

	private void createEventException(final Exception exc) {
		LOGGER.info("\n***********************************************");
		LOGGER.info("*  createCluEvent threw an exception");
		LOGGER.info("*  Exception: " + exc.getMessage());
		LOGGER.info("***********************************************\n");
	}
}
