package org.kuali.student.registration.client.service;

import java.util.List;

import org.kuali.student.registration.client.model.GwtLuiPersonRelationInfo;
import org.kuali.student.registration.client.model.GwtLuiPersonRelationTypeInfo;
import org.kuali.student.registration.client.model.GwtRelationStateInfo;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author Garey
 *
 */
public interface RegistrationServiceAsync {

	public void createLuiPersonRelation(String personId, String luiId,
			GwtRelationStateInfo relationStateInfo,
			GwtLuiPersonRelationTypeInfo luiPersonRelationTypeInfo,
			GwtLuiPersonRelationInfo luiPersonRelationCreateInfo,  AsyncCallback async);
	public void createLuiPersonRelation(String personId, List<String> luiIds,
			GwtRelationStateInfo relationStateInfo,
			GwtLuiPersonRelationTypeInfo luiPersonRelationTypeInfo,
			GwtLuiPersonRelationInfo luiPersonRelationCreateInfo,  AsyncCallback async);

	public void findLuiInfosRelatedToPerson(String personId,
			GwtLuiPersonRelationTypeInfo luiPersonRelationTypeInfo,
			GwtRelationStateInfo relationStateInfo, AsyncCallback async);
	
	public void updateLuiPersonRelation(String luiPersonRelationId,
			GwtLuiPersonRelationInfo luiPersonRelationUpdateInfo, AsyncCallback async);
	
	public void findLuiPersonRelationIdsForPerson(String personId, AsyncCallback async);
	
	public void findLuiPersonRelations(String personId, String luiId,AsyncCallback async);
	
	public void findLuiPersonRelationsForPerson(String personId, AsyncCallback async);
	
	public void deleteLuiPersonRelation(String luiPersonRelationId, AsyncCallback async);
}
