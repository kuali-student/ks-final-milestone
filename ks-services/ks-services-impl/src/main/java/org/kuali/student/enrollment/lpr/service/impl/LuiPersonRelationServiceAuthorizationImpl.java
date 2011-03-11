package org.kuali.student.enrollment.lpr.service.impl;

import java.security.InvalidParameterException;
import java.util.List;


import org.kuali.student.common.dto.ContextInfo;
import org.kuali.student.core.dto.StatusInfo;
import org.kuali.student.core.exceptions.AlreadyExistsException;
import org.kuali.student.core.exceptions.DisabledIdentifierException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.exceptions.ReadOnlyException;
import org.kuali.student.core.validation.dto.ValidationResultInfo;
import org.kuali.student.enrollment.lpr.dto.LuiPersonRelationCriteria;
import org.kuali.student.enrollment.lpr.dto.LuiPersonRelationInfo;
import org.kuali.student.enrollment.lpr.dto.LuiPersonRelationStateInfo;
import org.kuali.student.enrollment.lpr.dto.LuiPersonRelationTypeInfo;
import org.kuali.student.enrollment.lpr.service.LuiPersonRelationService;



public class LuiPersonRelationServiceAuthorizationImpl implements LuiPersonRelationService {
	

	@Override
	public List<LuiPersonRelationTypeInfo> findLuiPersonRelationTypes(
			ContextInfo context) throws OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LuiPersonRelationStateInfo> findLuiPersonRelationStates(
			ContextInfo context) throws OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LuiPersonRelationStateInfo> findAllowedRelationStates(
			String luiPersonRelationType, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LuiPersonRelationInfo fetchLUIPersonRelation(
			String luiPersonRelationId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LuiPersonRelationInfo> findLuiPersonRelationsByIdList(
			List<String> luiPersonRelationIdList, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> findLuiIdsRelatedToPerson(String personId,
			String luiPersonRelationType, String relationState,
			ContextInfo context) throws DoesNotExistException,
			DisabledIdentifierException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> findPersonIdsRelatedToLui(String luiId,
			String luiPersonRelationType, String relationState,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LuiPersonRelationTypeInfo> findLuiPersonRelationTypesForLuiPersonRelation(
			String personId, String luiId, String relationState,
			ContextInfo context) throws DoesNotExistException,
			DisabledIdentifierException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LuiPersonRelationInfo> findLuiPersonRelations(String personId,
			String luiId, ContextInfo context) throws DoesNotExistException,
			DisabledIdentifierException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> findLuiPersonRelationIds(String personId, String luiId,
			ContextInfo context) throws DoesNotExistException,
			DisabledIdentifierException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LuiPersonRelationInfo> findLuiPersonRelationsForPerson(
			String personId, ContextInfo context) throws DoesNotExistException,
			DisabledIdentifierException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> findLuiPersonRelationIdsForPerson(String personId,
			ContextInfo context) throws DoesNotExistException,
			DisabledIdentifierException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LuiPersonRelationInfo> findLuiPersonRelationsForLui(
			String luiId, ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> findLuiPersonRelationIdsForLui(String luiId,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LuiPersonRelationStateInfo> findValidRelationStatesForLuiPersonRelation(
			String personId, String luiId, String luiPersonRelationType,
			ContextInfo context) throws DoesNotExistException,
			DisabledIdentifierException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean isValidLuiPersonRelation(String personId, String luiId,
			String luiPersonRelationType, String relationState,
			ContextInfo context) throws DoesNotExistException,
			DisabledIdentifierException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean isRelated(String personId, String luiId,
			String luiPersonRelationType, String relationState,
			ContextInfo context) throws DoesNotExistException,
			DisabledIdentifierException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ValidationResultInfo validateLuiPersonRelation(String personId,
			String luiId, String luiPersonRelationType, String relationState,
			ContextInfo context) throws DoesNotExistException,
			DisabledIdentifierException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> findAllValidLuisForPerson(String personId,
			String luiPersonRelationType, String relationState, String atpId,
			ContextInfo context) throws DoesNotExistException,
			DisabledIdentifierException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> findAllValidPeopleForLui(String luiId,
			String luiPersonRelationType, String relationState,
			ContextInfo context) throws DoesNotExistException,
			DisabledIdentifierException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LuiPersonRelationInfo> findOrderedRelationStatesForLuiPersonRelation(
			String luiPersonRelationId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> searchForLuiPersonRelationIds(
			LuiPersonRelationCriteria luiPersonRelationCriteria,
			ContextInfo context) throws InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String createLuiPersonRelation(String personId, String luiId,
			String luiPersonRelationType,
			LuiPersonRelationInfo luiPersonRelationInfo, ContextInfo context)
			throws AlreadyExistsException, DoesNotExistException,
			DisabledIdentifierException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> createBulkRelationshipsForPerson(String personId,
			List<String> luiIdList, String relationState,
			String luiPersonRelationType,
			LuiPersonRelationInfo luiPersonRelationInfo, ContextInfo context)
			throws AlreadyExistsException, DoesNotExistException,
			DisabledIdentifierException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		  System.out.println("PersonId needed............");
		return null;
	}

	@Override
	public LuiPersonRelationInfo updateLuiPersonRelation(
			String luiPersonRelationId,
			LuiPersonRelationInfo luiPersonRelationInfo, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, ReadOnlyException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StatusInfo deleteLuiPersonRelation(String luiPersonRelationId,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StatusInfo updateRelationState(String luiPersonRelationId,
			LuiPersonRelationStateInfo relationState, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

}
