package org.kuali.student.registration.client.service;

import java.util.List;

import org.kuali.student.registration.client.model.GwtLuiPersonRelationDisplay;
import org.kuali.student.registration.client.model.GwtLuiPersonRelationInfo;
import org.kuali.student.registration.client.model.GwtLuiPersonRelationTypeInfo;
import org.kuali.student.registration.client.model.GwtRelationStateInfo;
import org.kuali.student.ui.personidentity.client.model.lu.GwtLuiInfo;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.SerializableException;

/**
 * @author Garey
 *
 */
public interface RegistrationService extends RemoteService {

	public static final GwtLuiPersonRelationTypeInfo REL_STUDENT = 
		new GwtLuiPersonRelationTypeInfo("kuali.student");
	public static final GwtLuiPersonRelationTypeInfo REL_INSTRUCTOR = 
		new GwtLuiPersonRelationTypeInfo("kuali.instructor");
	
	public static final GwtRelationStateInfo STATE_BASKET = 
		new GwtRelationStateInfo("basket");
	public static final GwtRelationStateInfo STATE_PENDING = 
		new GwtRelationStateInfo("pending");
	public static final GwtRelationStateInfo STATE_COMPLETE = 
		new GwtRelationStateInfo("complete");
	
	
	public String createLuiPersonRelation(String personId, String luiId,
			GwtRelationStateInfo relationStateInfo,
			GwtLuiPersonRelationTypeInfo luiPersonRelationTypeInfo,
			GwtLuiPersonRelationInfo luiPersonRelationCreateInfo)
			throws SerializableException;
	
	public boolean updateLuiPersonRelation(String luiPersonRelationId,
			GwtLuiPersonRelationInfo luiPersonRelationUpdateInfo)
	throws SerializableException;
	
	public List<String> createLuiPersonRelation(String personId, List<String> luiIds,
			GwtRelationStateInfo relationStateInfo,
			GwtLuiPersonRelationTypeInfo luiPersonRelationTypeInfo,
			GwtLuiPersonRelationInfo luiPersonRelationCreateInfo)
			throws SerializableException;
	
	public List<GwtLuiInfo> findLuiInfosRelatedToPerson(String personId,
			GwtLuiPersonRelationTypeInfo luiPersonRelationTypeInfo,
			GwtRelationStateInfo relationStateInfo) throws SerializableException;
	
	public List<String> findLuiPersonRelationIdsForPerson(String personId)
			throws SerializableException;
	
	public List<String> findLuiPersonRelations(String personId, String luiId)
	throws SerializableException;
	
	public List<GwtLuiPersonRelationDisplay> findLuiPersonRelationsForPerson(String personId)
	throws SerializableException;
	
	public boolean deleteLuiPersonRelation(String luiPersonRelationId)
	throws SerializableException;
	
/*
	public boolean deleteLuiPersonRelation(String luiPersonRelationId)
			throws SerializableException;

	public GwtLuiPersonRelationInfo fetchLUIPersonRelation(
			String luiPersonRelationId) throws SerializableException;

	public List<GwtRelationStateInfo> findAllowedRelationStates(
			LuiPersonRelationTypeInfo luiPersonRelationTypeInfo)
			throws SerializableException;
	
	public List<String> findLuiPersonRelationIdsForPerson(String personId)
	throws SerializableException;
	
	*/
}
