package org.kuali.student.enumeration.web.server.gwt;

import java.util.List;

import org.kuali.student.enumeration.web.client.service.EnumerationGWTClientService;


import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class EnumerationGWTClientServiceImplGWT extends RemoteServiceServlet implements EnumerationGWTClientService {
    private EnumerationGWTClientService serviceImpl;// = (EnumerationGWTClientService) BeanFactory.getInstance().getBean("developersGuiService");

    public List<String> fetchEnumertionMeta() {
        return serviceImpl.fetchEnumertionMeta();
    }
    /**
     * @return the serviceImpl
     */
    public EnumerationGWTClientService getServiceImpl() {
        return serviceImpl;
    }

    /**
     * @param serviceImpl
     *            the serviceImpl to set
     */
    public void setServiceImpl(EnumerationGWTClientService serviceImpl) {
       this.serviceImpl = serviceImpl;
    }
}