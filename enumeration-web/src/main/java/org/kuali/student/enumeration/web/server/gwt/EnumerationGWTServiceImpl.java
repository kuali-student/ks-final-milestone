package org.kuali.student.enumeration.web.server.gwt;

import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.xml.namespace.QName;

import org.kuali.student.poc.common.ws.beans.JaxWsClientFactoryBean;

import org.kuali.student.enumeration.dto.EnumeratedValue;
import org.kuali.student.enumeration.dto.EnumeratedValueList;
import org.kuali.student.enumeration.dto.EnumerationMeta;
import org.kuali.student.enumeration.dto.EnumerationMetaList;
import org.kuali.student.enumeration.service.EnumerationService;
import org.kuali.student.enumeration.util.di.EnumerationWebServletContextListener;
import org.kuali.student.enumeration.web.client.service.EnumerationGWTService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.inject.Inject;
import com.google.inject.Injector;

public class EnumerationGWTServiceImpl extends RemoteServiceServlet implements EnumerationGWTService {

	@Inject	 
	EnumerationService enumerationService; //Eclipse:right click references to see implementation binding and init
	/**
	 * This method overrides Servlet.init(), it is the earliest time in to bootstrap Guice in a Servlet
	 * 
	 * injectMembers(this) injects the Servlet's fields and methods marked with @Inject
	 * 
	 * @param config
	 * @throws ServletException
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		ServletContext servletContext = config.getServletContext();
		Injector injector = (Injector)servletContext.getAttribute(EnumerationWebServletContextListener.KEY);
		injector.injectMembers(this);
	}

	public EnumerationGWTServiceImpl() {}

	public EnumerationMetaList fetchEnumerationMetas() {
		return enumerationService.findEnumerationMetas();
	}
	
	public EnumerationMeta addEnumerationMeta(EnumerationMeta meta){
		return enumerationService.addEnumerationMeta(meta);
	}

	public EnumeratedValueList fetchEnumeration(String enumerationKey,String enumContextKey,String contextValue,Date   contextDate ){
		return enumerationService.fetchEnumeration(enumerationKey, enumContextKey, contextValue, contextDate);
	}

	public EnumeratedValue addEnumeratedValue(String enumerationKey,EnumeratedValue value){
		return enumerationService.addEnumeratedValue(enumerationKey, value);
	}

	public EnumeratedValue updateEnumeratedValue(String enumerationKey,String code,EnumeratedValue value){
		return enumerationService.updateEnumeratedValue(enumerationKey, code, value);
	}

	public boolean removeEnumeratedValue(String enumerationKey,String code){
		return enumerationService.removeEnumeratedValue(enumerationKey, code);
	}

	public EnumerationService getEnumerationService() {
		return enumerationService;
	}

	public void setEnumerationService(EnumerationService enumerationService) {
		this.enumerationService = enumerationService;
	}
}