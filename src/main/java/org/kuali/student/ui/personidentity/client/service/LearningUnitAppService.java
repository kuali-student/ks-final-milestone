/**
 * 
 */
package org.kuali.student.ui.personidentity.client.service;

import java.util.List;

import org.kuali.student.ui.personidentity.client.model.lu.GwtCluCriteria;
import org.kuali.student.ui.personidentity.client.model.lu.GwtCluDisplay;
import org.kuali.student.ui.personidentity.client.model.lu.GwtCluInfo;
import org.kuali.student.ui.personidentity.client.model.lu.GwtLuTypeInfo;
import org.kuali.student.ui.personidentity.client.model.lu.GwtLuiCriteria;
import org.kuali.student.ui.personidentity.client.model.lu.GwtLuiDisplay;
import org.kuali.student.ui.personidentity.client.model.lu.GwtLuiInfo;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.SerializableException;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

/**
 * @author Garey
 *
 */
public interface LearningUnitAppService extends RemoteService  {

    public static final String SERVICE_URI = "LearningUnitAppService";

    public static class Util {

        public static LearningUnitAppServiceAsync getInstance() {

            LearningUnitAppServiceAsync instance = (LearningUnitAppServiceAsync) GWT
                    .create(LearningUnitAppService.class);
            ServiceDefTarget target = (ServiceDefTarget) instance;
            target.setServiceEntryPoint(GWT.getModuleBaseURL() + SERVICE_URI);
            return instance;
        }
    }
    
	List<GwtCluInfo> findClusForLuType(String luTypeKey)throws SerializableException;
	List<GwtLuTypeInfo>findLuTypes() throws SerializableException;
	List<GwtLuiDisplay> findLuisForClu(String cluId, String atpId) throws SerializableException;
	GwtCluInfo fetchClu(String cluId) throws SerializableException;
	
	
	List<GwtCluDisplay> searchForClus(GwtCluCriteria cluCriteria) throws SerializableException;
	List<GwtLuiDisplay> searchForLuis(GwtLuiCriteria cluCriteria) throws SerializableException;
	
	List<GwtCluInfo> searchForCluInfo(GwtCluCriteria cluCriteria) throws SerializableException;
	List<GwtLuiInfo> searchForLuiInfo(GwtLuiCriteria cluCriteria) throws SerializableException;

	GwtLuiInfo fetchLui(String luiId) throws SerializableException;
	
}
