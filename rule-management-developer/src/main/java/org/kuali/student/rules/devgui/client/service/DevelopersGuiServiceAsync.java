/**
 * 
 */
package org.kuali.student.rules.devgui.client.service;

import java.util.List;

import org.kuali.student.rules.devgui.client.model.RulesHierarchyInfo;
import org.kuali.student.rules.rulemanagement.dto.BusinessRuleInfoDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author zzraly
 */
public interface DevelopersGuiServiceAsync {
    public void findRulesHierarchyInfo(AsyncCallback<List<RulesHierarchyInfo>> callback);

    public void fetchDetailedBusinessRuleInfo(String ruleId, AsyncCallback<BusinessRuleInfoDTO> callback);

    public void findAgendaTypes(AsyncCallback<List<String>> callback);

    public void findDeterminationKeysByAgendaType(String businessRuleType, AsyncCallback<List<String>> callback);

    public void findBusinessRuleTypesByDeterminationKeySet(String determinationKeys, AsyncCallback<List<String>> callback);
}
