package org.kuali.student.message.web.server.gwt;

import java.util.Date;
import javax.xml.namespace.QName;

//import org.apache.cxf.frontend.ClientProxyFactoryBean;
import org.kuali.student.message.service.MessageService;
import org.kuali.student.message.web.client.service.MessageGWTService;
import org.kuali.student.poc.common.ws.beans.JaxWsClientFactoryBean;


import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class MessageGWTServiceImpl extends RemoteServiceServlet implements MessageGWTService {

	 MessageService messageService; 
	 
	 public MessageGWTServiceImpl() {// Hard coding properties after removing Spring and before Guice
		 JaxWsClientFactoryBean factory = new JaxWsClientFactoryBean();
		 factory.setServiceEndpointInterface(MessageService.class);
		 factory.setWsdlLocation("classpath:wsdl/MessageService.wsdl");
		 factory.setServiceName(new QName("http://student.kuali.org/wsdl/MessageService","MessageService"));
		 factory.setAddress("http://localhost:8787/message-services/MessageService");
		 try {
		 messageService = (MessageService) factory.getObject();
		 } catch(Exception e) {
			 e.printStackTrace();
		 }
	 }
	
}