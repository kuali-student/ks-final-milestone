package org.kuali.student.embedded.workflow;

import javax.xml.namespace.QName;

import org.kuali.student.poc.common.ws.beans.JaxWsClientFactory;
import org.kuali.student.poc.common.ws.beans.JaxWsClientFactoryBean;
import org.kuali.student.poc.wsdl.learningunit.lu.LuService;

public class KSServiceLocator {
	// public static final String LU_SERVICE_URL = "http://kualidev.oti.fsu.edu:28080/learningunit-0.0.1-SNAPSHOT/LuService/LuService";
	public static final String LU_SERVICE_URL = "http://localhost:8181/learningunit/LuService/LuService";
	
	public static final LuService getLuService() throws Exception{
		JaxWsClientFactory client = new JaxWsClientFactoryBean();
		
		client.setAddress(LU_SERVICE_URL);
		client.setServiceEndpointInterface(LuService.class);
		client.setServiceName(new QName("http://student.kuali.org/poc/wsdl/learningunit/lu", "LuService"));
		client.setWsdlLocation(LU_SERVICE_URL + "?wsdl");
		
		return (LuService)client.getObject();		
	}
}
