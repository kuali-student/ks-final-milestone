/**
 * 
 */
package org.kuali.student.rules.lumgui.client.service;

import java.util.List;

import org.kuali.student.rules.lumgui.client.model.ReqComponentTypeInfo;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author zzraly
 */
public interface LumGuiServiceAsync {
    public void getReqComponentTypesForLuStatementType(String luStatementTypeKey,
            AsyncCallback<List<ReqComponentTypeInfo>> asyncCallback);
}
