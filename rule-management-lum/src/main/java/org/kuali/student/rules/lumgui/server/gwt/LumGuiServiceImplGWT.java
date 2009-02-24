/**
 * 
 */
package org.kuali.student.rules.lum.server.gwt;

import java.util.List;
import java.util.Map;

import org.kuali.student.core.spring.BeanFactory;
import org.kuali.student.rules.lum.client.service.LumGuiService;
import org.kuali.student.rules.lum.server.impl.LumGuiServiceImpl;
import org.kuali.student.rules.factfinder.dto.FactTypeInfoDTO;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * @author zzraly
 */
public class LumGuiServiceImplGWT extends RemoteServiceServlet implements LumGuiService {

    private static final long serialVersionUID = 822326113643828855L;

    private final LumGuiService serviceImpl = (LumGuiService) BeanFactory.getInstance().getBean("LumGuiService");
    
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
}
