package org.kuali.student.enumeration.web.server.gwt;

import java.util.Date;

import org.kuali.student.enumeration.dto.EnumeratedValue;
import org.kuali.student.enumeration.dto.EnumeratedValueList;
import org.kuali.student.enumeration.dto.EnumerationMetaList;
import org.kuali.student.enumeration.web.client.service.EnumerationGWTService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class EnumerationGWTServiceImpl extends RemoteServiceServlet implements EnumerationGWTService {
    private EnumerationGWTService serviceImpl;// = (EnumerationGWTService) BeanFactory.getInstance().getBean("developersGuiService");

    public EnumerationMetaList fetchEnumerationMetas() {
        return serviceImpl.fetchEnumerationMetas();
    }    
    public EnumeratedValueList fetchEnumeration(String enumerationKey,String enumContextKey,String contextValue,Date   contextDate ){
        return serviceImpl.fetchEnumeration(enumerationKey, enumContextKey, contextValue, contextDate);
    }
    
    public EnumeratedValue addEnumeratedValue(String enumerationKey,EnumeratedValue value){
        return serviceImpl.addEnumeratedValue(enumerationKey, value);
    }

    public EnumeratedValue updateEnumeratedValue(String enumerationKey,String code,EnumeratedValue value){
        return serviceImpl.updateEnumeratedValue(enumerationKey, code, value);
    }

    public boolean removeEnumeratedValue(String enumerationKey,String code){
        return serviceImpl.removeEnumeratedValue(enumerationKey, code);
    }
    
    /**
     * @return the serviceImpl
     */
    public EnumerationGWTService getServiceImpl() {
        return serviceImpl;
    }

    /**
     * @param serviceImpl
     *            the serviceImpl to set
     */
    public void setServiceImpl(EnumerationGWTService serviceImpl) {
       this.serviceImpl = serviceImpl;
    }

}