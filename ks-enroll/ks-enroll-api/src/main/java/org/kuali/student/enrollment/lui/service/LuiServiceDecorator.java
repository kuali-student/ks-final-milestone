package org.kuali.student.enrollment.lui.service;

import java.util.List;

import org.kuali.student.r2.common.datadictionary.dto.DictionaryEntryInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.TypeInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.CircularRelationshipException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DependentObjectsExistException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.enrollment.lpr.service.LuiPersonRelationService;
import org.kuali.student.enrollment.lui.dto.LuiInfo;
import org.kuali.student.enrollment.lui.dto.LuiLuiRelationInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.TypeTypeRelationInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;

public abstract class LuiServiceDecorator implements LuiService {
	
	protected LuiService nextDecorator;


    public LuiService getNextDecorator() {
        return nextDecorator;
    }

	public  void setNextDecorator(LuiService nextDecorator) {
		this.nextDecorator=nextDecorator;
	}
	
	@Override
	public List<String> getDataDictionaryEntryKeys(ContextInfo context)
			throws OperationFailedException, MissingParameterException,
			PermissionDeniedException {
		
		return nextDecorator.getDataDictionaryEntryKeys(context);
	}

	@Override
	public DictionaryEntryInfo getDataDictionaryEntry(String entryKey,
			ContextInfo context) throws OperationFailedException,
			MissingParameterException, PermissionDeniedException,
			DoesNotExistException {
		 
		return nextDecorator.getDataDictionaryEntry(entryKey, context);
	}

	@Override
	public TypeInfo getType(String typeKey, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		return nextDecorator.getType(typeKey, context);
	}

	@Override
	public List<TypeInfo> getTypesByRefObjectURI(String refObjectURI,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		return nextDecorator.getTypesByRefObjectURI(refObjectURI, context);
	}

	@Override
	public List<TypeInfo> getAllowedTypesForType(String ownerTypeKey,
			String relatedRefObjectURI, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		return nextDecorator.getAllowedTypesForType(ownerTypeKey, relatedRefObjectURI, context);
	}

	@Override
	public List<TypeTypeRelationInfo> getTypeRelationsByOwnerType(
			String ownerTypeKey, String relationTypeKey, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		return nextDecorator.getTypeRelationsByOwnerType(ownerTypeKey, relationTypeKey, context);
	}

	@Override
	public LuiInfo getLui(String luiId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		return nextDecorator.getLui(luiId, context);
	}

	@Override
	public List<LuiInfo> getLuisByIdList(List<String> luiIdList,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		return nextDecorator.getLuisByIdList(luiIdList, context);
	}

	@Override
	public List<LuiInfo> getLuisInAtpByCluId(String cluId, String atpKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		return nextDecorator.getLuisInAtpByCluId(cluId, atpKey, context);
	}

	@Override
	public List<String> getLuiIdsByCluId(String cluId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		return nextDecorator.getLuiIdsByCluId(cluId, context);
	}

	@Override
	public List<String> getLuiIdsInAtpByCluId(String cluId, String atpKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		return nextDecorator.getLuiIdsInAtpByCluId(cluId, atpKey, context);
	}

	@Override
	public List<LuiInfo> getLuisByRelation(String relatedLuiId,
			String luLuRelationTypeKey, ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException {
		return nextDecorator.getLuisByRelation(relatedLuiId, luLuRelationTypeKey, context);
	}

	@Override
	public List<String> getLuiIdsByRelation(String relatedLuiId,
			String luLuRelationTypeKey, ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException {
		return nextDecorator.getLuiIdsByRelation(relatedLuiId, luLuRelationTypeKey, context);
	}

	@Override
	public List<LuiInfo> getRelatedLuisByLuiId(String luiId,
			String luLuRelationTypeKey, ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException {
		return nextDecorator.getRelatedLuisByLuiId(luiId, luLuRelationTypeKey, context);
	}

	@Override
	public List<String> getRelatedLuiIdsByLuiId(String luiId,
			String luLuRelationTypeKey, ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException {
		return nextDecorator.getRelatedLuiIdsByLuiId(luiId, luLuRelationTypeKey, context);
	}

	@Override
	public LuiLuiRelationInfo getLuiLuiRelation(String luiLuiRelationId,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		return nextDecorator.getLuiLuiRelation(luiLuiRelationId, context);
	}

	@Override
	public List<LuiLuiRelationInfo> getLuiLuiRelationsByLui(String luiId,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		return nextDecorator.getLuiLuiRelationsByLui(luiId, context);
	}

	@Override
	public List<ValidationResultInfo> validateLui(String validationType,
			LuiInfo luiInfo, ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		return nextDecorator.validateLui(validationType,luiInfo, context);
	}

	@Override
	public LuiInfo createLui(String cluId, String atpKey, LuiInfo luiInfo,
			ContextInfo context) throws AlreadyExistsException,
			DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		return nextDecorator.createLui(cluId,atpKey,luiInfo, context);
	}

	@Override
	public LuiInfo updateLui(String luiId, LuiInfo luiInfo, ContextInfo context)
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			VersionMismatchException {
		return nextDecorator.updateLui(luiId,luiInfo, context);
	}

	@Override
	public StatusInfo deleteLui(String luiId, ContextInfo context)
			throws DependentObjectsExistException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		return nextDecorator.deleteLui(luiId, context);
	}

	@Override
	public LuiInfo updateLuiState(String luiId, String luState,
			ContextInfo context) throws DataValidationErrorException,
			DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		return nextDecorator.updateLuiState(luiId, luState, context);
	}

	@Override
	public List<ValidationResultInfo> validateLuiLuiRelation(
			String validationType, LuiLuiRelationInfo luiLuiRelationInfo,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		return nextDecorator.validateLuiLuiRelation(validationType, luiLuiRelationInfo, context);
	}

	@Override
	public LuiLuiRelationInfo createLuiLuiRelation(String luiId,
			String relatedLuiId, String luLuRelationTypeKey,
			LuiLuiRelationInfo luiLuiRelationInfo, ContextInfo context)
			throws AlreadyExistsException, CircularRelationshipException,
			DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		return nextDecorator.createLuiLuiRelation(luiId, relatedLuiId, luLuRelationTypeKey, luiLuiRelationInfo, context);
	}

	@Override
	public LuiLuiRelationInfo updateLuiLuiRelation(String luiLuiRelationId,
			LuiLuiRelationInfo luiLuiRelationInfo, ContextInfo context)
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			VersionMismatchException {
		return nextDecorator.updateLuiLuiRelation(luiLuiRelationId, luiLuiRelationInfo, context);
	}

	@Override
	public StatusInfo deleteLuiLuiRelation(String luiLuiRelationId,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		return nextDecorator.deleteLuiLuiRelation(luiLuiRelationId,context);
	}

}
