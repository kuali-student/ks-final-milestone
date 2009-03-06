/**
 * 
 */
package org.kuali.student.rules.lumgui.server.gwt;

import java.util.List;
import java.util.Map;

import org.kuali.student.core.spring.BeanFactory;
import org.kuali.student.rules.lumgui.client.model.ReqComponentTypeInfo;
import org.kuali.student.rules.lumgui.client.service.LumGuiService;
import org.kuali.student.rules.lumgui.server.impl.LumGuiServiceImpl;
import org.kuali.student.rules.factfinder.dto.FactTypeInfoDTO;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * @author zzraly
 */
public class LumGuiServiceImplGWT extends RemoteServiceServlet implements LumGuiService {

    private static final long serialVersionUID = 822326113643828855L;

    private final LumGuiService serviceImpl = (LumGuiService) BeanFactory.getInstance().getBean("lumGuiService");
    
    /**
     * @return the serviceImpl
     */
    public LumGuiService getServiceImpl() {
        return serviceImpl;
    }

    /**
     * @param serviceImpl
     *            the serviceImpl to set
     */
    public void setServiceImpl(LumGuiServiceImpl serviceImpl) {
    // this.serviceImpl = serviceImpl;
    }
    
    public List<ReqComponentTypeInfo> getReqComponentTypesForLuStatementType(String luStatementTypeKey) throws Exception {
        return serviceImpl.getReqComponentTypesForLuStatementType(luStatementTypeKey);
    }
}
