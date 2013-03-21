package org.kuali.student.ap.framework.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Provides static access to the KRAD Spring application context, for supporting
 * delayed web initialization.
 * 
 * @deprecated TODO: KSAP-26 Move to Rice.
 */
public class KradApplicationContextListener implements ApplicationContextAware {

	private static ApplicationContext context;

	/**
	 * Get the KRAD application context.
	 * 
	 * <p>
	 * The context will be available after refreshing an XML with the following
	 * bean definition.
	 * </p>
	 * 
	 * <p>
	 * TODO: KSAP-26: This definition is fed from krad-bootstrap.xml, moved to
	 * KRADSpringBeans.xml
	 * </p>
	 * 
	 * <pre>
	 * &lt;bean class=&quot;org.kuali.student.ap.framework.config.KradApplicationContextListener&quot; /&gt;
	 * </pre>
	 * 
	 * @return The KRAD module's spring application context, null if the module
	 *         has not yet initialized to the point of making the context
	 *         available.
	 */
	public static ApplicationContext getContext() {
		return context;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		context = applicationContext;
	}

}
