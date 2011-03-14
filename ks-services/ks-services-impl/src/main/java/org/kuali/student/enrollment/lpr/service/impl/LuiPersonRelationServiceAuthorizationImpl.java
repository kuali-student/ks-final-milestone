package org.kuali.student.enrollment.lpr.service.impl;

import java.security.InvalidParameterException;
import java.util.List;


import org.kuali.student.common.dto.ContextInfo;
import org.kuali.student.common.infc.ContextInfc;
import org.kuali.student.common.infc.StatusInfc;
import org.kuali.student.common.infc.ValidationResultInfc;
import org.kuali.student.core.dto.StatusInfo;
import org.kuali.student.core.exceptions.AlreadyExistsException;
import org.kuali.student.core.exceptions.DisabledIdentifierException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.exceptions.ReadOnlyException;
import org.kuali.student.core.validation.dto.ValidationResultInfo;
import org.kuali.student.enrollment.lpr.dto.LuiPersonRelationCriteriaInfo;
import org.kuali.student.enrollment.lpr.dto.LuiPersonRelationInfo;
import org.kuali.student.enrollment.lpr.dto.LuiPersonRelationStateInfo;
import org.kuali.student.enrollment.lpr.dto.LuiPersonRelationTypeInfo;
import org.kuali.student.enrollment.lpr.infc.LuiPersonRelationCriteriaInfc;
import org.kuali.student.enrollment.lpr.infc.LuiPersonRelationInfc;
import org.kuali.student.enrollment.lpr.infc.LuiPersonRelationStateInfc;
import org.kuali.student.enrollment.lpr.infc.LuiPersonRelationTypeInfc;
import org.kuali.student.enrollment.lpr.service.LuiPersonRelationService;
import org.kuali.student.enrollment.lpr.service.LuiPersonRelationServiceInfc;



public class LuiPersonRelationServiceAuthorizationImpl implements LuiPersonRelationServiceInfc {
	

	@Override
	public List<LuiPersonRelationTypeInfc> findLuiPersonRelationTypes(
			ContextInfc context) throws OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LuiPersonRelationStateInfc> findLuiPersonRelationStates(
			ContextInfc context) throws OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LuiPersonRelationStateInfc> findAllowedRelationStates(
			String luiPersonRelationType, ContextInfc context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LuiPersonRelationInfc fetchLUIPersonRelation(
			String luiPersonRelationId, ContextInfc context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LuiPersonRelationInfc> findLuiPersonRelationsByIdList(
			List<String> luiPersonRelationIdList, ContextInfc context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> findLuiIdsRelatedToPerson(String personId,
			String luiPersonRelationType, String relationState,
			ContextInfc context) throws DoesNotExistException,
			DisabledIdentifierException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> findPersonIdsRelatedToLui(String luiId,
			String luiPersonRelationType, String relationState,
			ContextInfc context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LuiPersonRelationTypeInfc> findLuiPersonRelationTypesForLuiPersonRelation(
			String personId, String luiId, String relationState,
			ContextInfc context) throws DoesNotExistException,
			DisabledIdentifierException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LuiPersonRelationInfc> findLuiPersonRelations(String personId,
			String luiId, ContextInfc context) throws DoesNotExistException,
			DisabledIdentifierException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> findLuiPersonRelationIds(String personId, String luiId,
			ContextInfc context) throws DoesNotExistException,
			DisabledIdentifierException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LuiPersonRelationInfc> findLuiPersonRelationsForPerson(
			String personId, ContextInfc context) throws DoesNotExistException,
			DisabledIdentifierException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> findLuiPersonRelationIdsForPerson(String personId,
			ContextInfc context) throws DoesNotExistException,
			DisabledIdentifierException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LuiPersonRelationInfc> findLuiPersonRelationsForLui(
			String luiId, ContextInfc context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> findLuiPersonRelationIdsForLui(String luiId,
			ContextInfc context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LuiPersonRelationStateInfc> findValidRelationStatesForLuiPersonRelation(
			String personId, String luiId, String luiPersonRelationType,
			ContextInfc context) throws DoesNotExistException,
			DisabledIdentifierException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean isValidLuiPersonRelation(String personId, String luiId,
			String luiPersonRelationType, String relationState,
			ContextInfc context) throws DoesNotExistException,
			DisabledIdentifierException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean isRelated(String personId, String luiId,
			String luiPersonRelationType, String relationState,
			ContextInfc context) throws DoesNotExistException,
			DisabledIdentifierException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ValidationResultInfc validateLuiPersonRelation(String personId,
			String luiId, String luiPersonRelationType, String relationState,
			ContextInfc context) throws DoesNotExistException,
			DisabledIdentifierException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> findAllValidLuisForPerson(String personId,
			String luiPersonRelationType, String relationState, String atpId,
			ContextInfc context) throws DoesNotExistException,
			DisabledIdentifierException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> findAllValidPeopleForLui(String luiId,
			String luiPersonRelationType, String relationState,
			ContextInfc context) throws DoesNotExistException,
			DisabledIdentifierException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LuiPersonRelationInfc> findOrderedRelationStatesForLuiPersonRelation(
			String luiPersonRelationId, ContextInfc context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> searchForLuiPersonRelationIds(
			LuiPersonRelationCriteriaInfc luiPersonRelationCriteria,
			ContextInfc context) throws InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String createLuiPersonRelation(String personId, String luiId,
			String luiPersonRelationType,
			LuiPersonRelationInfc luiPersonRelationInfo, ContextInfc context)
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
			LuiPersonRelationInfc luiPersonRelationInfo, ContextInfc context)
			throws AlreadyExistsException, DoesNotExistException,
			DisabledIdentifierException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		  System.out.println("Inside authorization impl for createBulkRelationshipsForPerson");
		return null;
	}

	@Override
	public LuiPersonRelationInfo updateLuiPersonRelation(
			String luiPersonRelationId,
			LuiPersonRelationInfc luiPersonRelationInfo, ContextInfc context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, ReadOnlyException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StatusInfc deleteLuiPersonRelation(String luiPersonRelationId,
			ContextInfc context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StatusInfc updateRelationState(String luiPersonRelationId,
			LuiPersonRelationStateInfc relationState, ContextInfc context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

}
