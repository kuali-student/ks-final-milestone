/**
 * 
 */
package org.kuali.student.rules.devgui.client;

import org.kuali.student.rules.devgui.client.model.BusinessRule;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author zzraly
 */
public interface DevGuiServiceAsync {
    public void getBusinessRule(String identifier, AsyncCallback<BusinessRule> callback);
}
