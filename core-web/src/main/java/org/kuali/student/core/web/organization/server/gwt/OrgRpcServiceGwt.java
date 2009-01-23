package org.kuali.student.core.web.organization.server.gwt;

import org.kuali.student.core.web.organization.client.service.OrgRpcService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class OrgRpcServiceGwt extends RemoteServiceServlet implements OrgRpcService{

	private static final long serialVersionUID = 1L;

	private OrgRpcService serviceImpl;

	public OrgRpcService getServiceImpl() {
		return serviceImpl;
	}

	public void setServiceImpl(OrgRpcService serviceImpl) {
		this.serviceImpl = serviceImpl;
	}
	
}
