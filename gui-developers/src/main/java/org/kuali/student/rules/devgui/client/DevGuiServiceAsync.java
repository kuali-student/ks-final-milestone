/**
 * 
 */
package org.kuali.student.rules.devgui.client;

import java.util.List;

import org.kuali.student.rules.devgui.client.model.BusinessRule;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author zzraly
 */
public interface DevGuiServiceAsync {
    public void findAgendaTypes(AsyncCallback<List<String>> callback);

    public void findDeterminationKeysByAgendaType(String businessRuleType, AsyncCallback<List<String>> callback);

    public void findBusinessRuleTypesByDeterminationKeySet(String determinationKeys, AsyncCallback<List<String>> callback);

    public void findBusinessRules(AsyncCallback<List<BusinessRule>> callback);
}
