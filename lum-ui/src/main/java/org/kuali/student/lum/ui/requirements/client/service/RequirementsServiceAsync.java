/**
 * 
 */
package org.kuali.student.lum.ui.requirements.client.service;

import java.util.List;

import org.kuali.student.lum.lu.dto.ReqComponentTypeInfo;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author zzraly
 */
public interface RequirementsServiceAsync {
    public void getReqComponentTypesForLuStatementType(String luStatementTypeKey,
            AsyncCallback<List<ReqComponentTypeInfo>> asyncCallback);
}
