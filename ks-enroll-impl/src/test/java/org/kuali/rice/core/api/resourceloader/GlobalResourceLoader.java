package org.kuali.rice.core.api.resourceloader;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.exception.RiceRemoteServiceConnectionException;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.xml.namespace.QName;

/**
 * Created with IntelliJ IDEA.
 * User: swedev
 * Date: 11/21/12
 * Time: 11:04 AM
 * To change this template use File | Settings | File Templates.
 */
public class GlobalResourceLoader implements ApplicationContextAware{
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(GlobalResourceLoader.class);

    static ApplicationContext applicationContext = null;

    public static <T extends Object> T getService(QName serviceName) {
        if (serviceName == null) {
            throw new IllegalArgumentException("The service name must be non-null.");
        }
        LOG.debug("GlobalResourceLoader fetching service " + serviceName);
        try {
            return getService(serviceName);
        } catch (RiceRemoteServiceConnectionException ex) {
            LOG.warn(ex.getMessage());
            return null;
        }
    }

    public static <T extends Object> T getService(String localServiceName) {
        if (StringUtils.isEmpty(localServiceName)) {
            throw new IllegalArgumentException("The service name must be non-null.");
        }
        //ApplicationContext applicationContext = new AnnotationConfigApplicationContext(GlobalResourceLoader.class);
          return (T)applicationContext.getBean(localServiceName);


    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
