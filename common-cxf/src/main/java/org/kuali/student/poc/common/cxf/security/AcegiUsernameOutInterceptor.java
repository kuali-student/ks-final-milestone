package org.kuali.student.poc.common.cxf.security;

import org.acegisecurity.Authentication;
import org.acegisecurity.context.SecurityContextHolder;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.apache.ws.security.handler.WSHandlerConstants;

public class AcegiUsernameOutInterceptor extends AbstractSoapInterceptor {

	public AcegiUsernameOutInterceptor() {
		super(Phase.POST_PROTOCOL);
		getBefore().add(WSS4JOutInterceptor.class.getName());
	}

	@Override
	public void handleMessage(SoapMessage mc) throws Fault {
		Authentication acegiAuth = SecurityContextHolder.getContext().getAuthentication();
		
		String uid = acegiAuth.getName();
		
		mc.put(WSHandlerConstants.USER, uid);
	}

}
