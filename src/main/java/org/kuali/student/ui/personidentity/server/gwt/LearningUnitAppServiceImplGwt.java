/**
 * 
 */
package org.kuali.student.ui.personidentity.server.gwt;

import java.util.List;

import org.kuali.student.core.spring.BeanFactory;
import org.kuali.student.ui.personidentity.client.model.lu.GwtCluCriteria;
import org.kuali.student.ui.personidentity.client.model.lu.GwtCluDisplay;
import org.kuali.student.ui.personidentity.client.model.lu.GwtCluInfo;
import org.kuali.student.ui.personidentity.client.model.lu.GwtLuTypeInfo;
import org.kuali.student.ui.personidentity.client.model.lu.GwtLuiCriteria;
import org.kuali.student.ui.personidentity.client.model.lu.GwtLuiDisplay;
import org.kuali.student.ui.personidentity.client.model.lu.GwtLuiInfo;
import org.kuali.student.ui.personidentity.client.service.LearningUnitAppService;
import org.kuali.student.ui.personidentity.server.impl.LearningUnitAppServiceImpl;

import com.google.gwt.user.client.rpc.SerializableException;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * @author Garey
 *
 */
public class LearningUnitAppServiceImplGwt extends RemoteServiceServlet implements LearningUnitAppService {

	private LearningUnitAppService serviceImpl = (LearningUnitAppService) BeanFactory.getInstance().getBean("learningUnitService");
	

	public List<GwtCluInfo> findClusForLuType(String luTypeKey)
			throws SerializableException {
		
		return serviceImpl.findClusForLuType(luTypeKey);
	}

	
	public List<GwtLuTypeInfo> findLuTypes() throws SerializableException {
		
		return serviceImpl.findLuTypes();
	}

	
	public List<GwtLuiDisplay> findLuisForClu(String cluId, String atpId)
			throws SerializableException {
		
		return serviceImpl.findLuisForClu(cluId, atpId);
	}

	
	public GwtCluInfo fetchClu(String cluId) throws SerializableException {
		return serviceImpl.fetchClu(cluId);
	}
	
	public GwtLuiInfo fetchLui(String luiId)throws SerializableException {
		return serviceImpl.fetchLui(luiId);
	}

	
	public List<GwtCluDisplay> searchForClus(GwtCluCriteria cluCriteria)
			throws SerializableException {
		return serviceImpl.searchForClus(cluCriteria);
	}

	
	public List<GwtLuiDisplay> searchForLuis(GwtLuiCriteria luiCriteria)
			throws SerializableException {
		
		return serviceImpl.searchForLuis(luiCriteria);
	}

	
	public List<GwtCluInfo> searchForCluInfo(GwtCluCriteria cluCriteria)
			throws SerializableException {
		return serviceImpl.searchForCluInfo(cluCriteria);
	}

	
	public List<GwtLuiInfo> searchForLuiInfo(GwtLuiCriteria cluCriteria)
			throws SerializableException {
		return serviceImpl.searchForLuiInfo(cluCriteria);
	}


	/**
	 * @return the serviceImpl
	 */
	public LearningUnitAppService getServiceImpl() {
		return serviceImpl;
	}


	/**
	 * @param serviceImpl the serviceImpl to set
	 */
	public void setServiceImpl(LearningUnitAppService serviceImpl) {
		this.serviceImpl = serviceImpl;
	}

}
