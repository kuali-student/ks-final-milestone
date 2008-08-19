/**
 * 
 */
package org.kuali.student.rules.devgui.server.devguiservice;

import java.util.List;

import org.kuali.student.rules.devgui.client.DevGuiService;
import org.kuali.student.rules.devgui.client.model.BusinessRule;
import org.kuali.student.rules.devgui.server.devguiservice.impl.DevGuiServiceImpl;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * @author zzraly
 */
public class DevGuiServiceServlet extends RemoteServiceServlet implements DevGuiService {
    private static final long serialVersionUID = 1L;
    // in a real application this impl would be injected at runtime
    private final DevGuiService impl = new DevGuiServiceImpl();

    public List<String> findAgendaTypes() {
        return impl.findAgendaTypes();
    }

    public List<String> findDeterminationKeysByAgendaType(String businessRuleType) {
        return impl.findDeterminationKeysByAgendaType(businessRuleType);
    }

    public List<String> findBusinessRuleTypesByDeterminationKeySet(String determinationKeys) {
        return impl.findBusinessRuleTypesByDeterminationKeySet(determinationKeys);
    }

    public List<BusinessRule> findBusinessRules() {
        return impl.findBusinessRules();
    }

}
