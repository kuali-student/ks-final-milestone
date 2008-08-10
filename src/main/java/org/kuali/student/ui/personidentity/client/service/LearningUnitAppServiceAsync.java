/**
 * 
 */
package org.kuali.student.ui.personidentity.client.service;

import java.util.List;

import org.kuali.student.ui.personidentity.client.model.lu.GwtCluCriteria;
import org.kuali.student.ui.personidentity.client.model.lu.GwtLuiCriteria;
import org.kuali.student.ui.personidentity.client.model.lu.GwtLuiInfo;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author Garey
 *
 */
public interface LearningUnitAppServiceAsync {

	public void findClusForLuType(String luTypeKey, AsyncCallback async);
	public void findLuTypes(AsyncCallback callback);
	public void findLuisForClu(String cluId, String atpId, AsyncCallback async);
	
	public void fetchClu(String cluId, AsyncCallback async);
	public void fetchLui(String luiId, AsyncCallback async);
	
	public void searchForClus(GwtCluCriteria cluCriteria, AsyncCallback async);
	public void searchForLuis(GwtLuiCriteria luiCriteria, AsyncCallback async);
	
	public void searchForCluInfo(GwtCluCriteria cluCriteria, AsyncCallback async);
	public void searchForLuiInfo(GwtLuiCriteria luiCriteria, AsyncCallback<List<GwtLuiInfo>> async);
	
	
}
