/**
 * 
 */
package org.kuali.student.common.spring;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author ocleirig
 *
 */
public class DisplayActiveProfiles implements ApplicationContextAware, InitializingBean {


	private static final Logger log = LoggerFactory.getLogger(DisplayActiveProfiles.class);
	
	private ApplicationContext applicationContext;



	/**
	 * 
	 */
	public DisplayActiveProfiles() {
	}

	

	/* (non-Javadoc)
	 * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
				this.applicationContext = applicationContext;
		
	}



	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		
		String[] profiles = this.applicationContext.getEnvironment().getActiveProfiles();
	
		if (profiles.length == 0) {
			profiles = this.applicationContext.getEnvironment().getDefaultProfiles();
			log.info(applicationContext.getDisplayName() + " : Default Active Profiles are : " + StringUtils.join(profiles, ", "));
		}
		else
			log.info(applicationContext.getDisplayName() + " : Active Profiles are : " + StringUtils.join(profiles, ", "));
	}
	
	

}
