/**
 * 
 */
package org.kuali.student.registration.server.gwt;

import java.util.List;

import org.kuali.student.core.spring.BeanFactory;
import org.kuali.student.registration.client.model.GwtLuiPersonRelationDisplay;
import org.kuali.student.registration.client.model.GwtLuiPersonRelationInfo;
import org.kuali.student.registration.client.model.GwtLuiPersonRelationTypeInfo;
import org.kuali.student.registration.client.model.GwtRelationStateInfo;
import org.kuali.student.registration.client.service.RegistrationService;
import org.kuali.student.registration.server.impl.RegistrationServiceImpl;
import org.kuali.student.ui.personidentity.client.model.lu.GwtLuiInfo;

import com.google.gwt.user.client.rpc.SerializableException;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * @author Garey
 *
 */
public class RegistrationServiceImplGwt extends RemoteServiceServlet implements RegistrationService {

	
	private RegistrationService service = (RegistrationService) BeanFactory.getInstance().getBean("registrationService");

	
	public String createLuiPersonRelation(String personId, String luiId,
			GwtRelationStateInfo relationStateInfo,
			GwtLuiPersonRelationTypeInfo luiPersonRelationTypeInfo,
			GwtLuiPersonRelationInfo luiPersonRelationCreateInfo)
			throws SerializableException {

		return service.createLuiPersonRelation(personId, luiId, relationStateInfo, luiPersonRelationTypeInfo, luiPersonRelationCreateInfo);
	}

	
	public List<GwtLuiInfo> findLuiInfosRelatedToPerson(String personId,
			GwtLuiPersonRelationTypeInfo luiPersonRelationTypeInfo,
			GwtRelationStateInfo relationStateInfo)
			throws SerializableException {
		
		return service.findLuiInfosRelatedToPerson(personId, luiPersonRelationTypeInfo, relationStateInfo);
	}

	
	public List<String> createLuiPersonRelation(String personId,
			List<String> luiIds, GwtRelationStateInfo relationStateInfo,
			GwtLuiPersonRelationTypeInfo luiPersonRelationTypeInfo,
			GwtLuiPersonRelationInfo luiPersonRelationCreateInfo)
			throws SerializableException {
		return service.createLuiPersonRelation(personId, luiIds, relationStateInfo, luiPersonRelationTypeInfo, luiPersonRelationCreateInfo);
	}

	
	public boolean updateLuiPersonRelation(String luiPersonRelationId,
			GwtLuiPersonRelationInfo luiPersonRelationUpdateInfo)
			throws SerializableException {
		
		return service.updateLuiPersonRelation(luiPersonRelationId, luiPersonRelationUpdateInfo);
	}

	
	public List<String> findLuiPersonRelationIdsForPerson(String personId)
			throws SerializableException {

		return service.findLuiPersonRelationIdsForPerson(personId);
	}
	
	public List<String> findLuiPersonRelations(String personId, String luiId)
			throws SerializableException {

		return service.findLuiPersonRelations(personId, luiId);
	}

	
	public List<GwtLuiPersonRelationDisplay> findLuiPersonRelationsForPerson(
			String personId) throws SerializableException {
		return service.findLuiPersonRelationsForPerson(personId);
	}

	
	public boolean deleteLuiPersonRelation(String luiPersonRelationId)
			throws SerializableException {
		
		return service.deleteLuiPersonRelation(luiPersonRelationId);
	}


	/**
	 * @return the service
	 */
	public RegistrationService getService() {
		return service;
	}


	/**
	 * @param service the service to set
	 */
	public void setService(RegistrationService service) {
		this.service = service;
	}

}
