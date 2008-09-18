/**
 * 
 */
package org.kuali.student.rules.devgui.server.gwt;

import java.util.List;

import org.kuali.student.core.spring.BeanFactory;
import org.kuali.student.rules.devgui.client.model.RulesHierarchyInfo;
import org.kuali.student.rules.devgui.client.service.DevelopersGuiService;
import org.kuali.student.rules.devgui.server.impl.DevelopersGuiServiceImpl;
import org.kuali.student.rules.rulemanagement.dto.BusinessRuleInfoDTO;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * @author zzraly
 */
public class DevelopersGuiServiceImplGWT extends RemoteServiceServlet implements DevelopersGuiService {

    private static final long serialVersionUID = 822326113643828855L;

    private final DevelopersGuiService serviceImpl = (DevelopersGuiService) BeanFactory.getInstance().getBean("developersGuiService");

    // private final DevelopersGuiServiceImpl serviceImpl = new DevelopersGuiServiceImpl();

    public List<RulesHierarchyInfo> findRulesHierarchyInfo() {
        return serviceImpl.findRulesHierarchyInfo();
    }

    public BusinessRuleInfoDTO fetchDetailedBusinessRuleInfo(String ruleId) {
        return serviceImpl.fetchDetailedBusinessRuleInfo(ruleId);
    }

    public List<String> findAgendaTypes() {
        return serviceImpl.findAgendaTypes();
    }

    public List<String> findDeterminationKeysByAgendaType(String businessRuleType) {
        return serviceImpl.findDeterminationKeysByAgendaType(businessRuleType);
    }

    public List<String> findBusinessRuleTypesByDeterminationKeySet(String determinationKeys) {
        return serviceImpl.findBusinessRuleTypesByDeterminationKeySet(determinationKeys);
    }

    /**
     * @return the serviceImpl
     */
    public DevelopersGuiService getServiceImpl() {
        return serviceImpl;
    }

    /**
     * @param serviceImpl
     *            the serviceImpl to set
     */
    public void setServiceImpl(DevelopersGuiServiceImpl serviceImpl) {
    // this.serviceImpl = serviceImpl;
    }
}
