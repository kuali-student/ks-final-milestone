package org.kuali.student.enrollment.class1.lui.service.decorators;

import java.util.List;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.kim.service.PermissionService;
import org.kuali.student.enrollment.lui.dto.LuiCapacityInfo;
import org.kuali.student.enrollment.lui.dto.LuiInfo;
import org.kuali.student.enrollment.lui.dto.LuiLuiRelationInfo;
import org.kuali.student.enrollment.lui.service.LuiServiceDecorator;
import org.kuali.student.r2.common.datadictionary.dto.DictionaryEntryInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.TypeInfo;
import org.kuali.student.r2.common.dto.TypeTypeRelationInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
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
import org.kuali.student.r2.common.infc.HoldsPermissionService;

public class LuiServiceAuthorizationDecorator extends LuiServiceDecorator implements HoldsPermissionService{
    public static final String ENRLLMENT_NAMESPACE = "KS-ENROLL";
    public static final String SERVICE_NAME = "LuiService.";
    
	private PermissionService permissionService;
		
	@Override
	public PermissionService getPermissionService() {
		return permissionService;
	}

	@Override
	public void setPermissionService(PermissionService permissionService) {
		this.permissionService = permissionService;
	}

	@Override
	public List<String> getDataDictionaryEntryKeys(ContextInfo context)
			throws OperationFailedException, MissingParameterException,
			PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getDataDictionaryEntryKeys", null, null)) {
	        return getNextDecorator().getDataDictionaryEntryKeys(context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public DictionaryEntryInfo getDataDictionaryEntry(String entryKey,
			ContextInfo context) throws OperationFailedException,
			MissingParameterException, PermissionDeniedException,
			DoesNotExistException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getDataDictionaryEntry", null, null)) {
	        return getNextDecorator().getDataDictionaryEntry(entryKey, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public TypeInfo getType(String typeKey, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getType", null, null)) {
	        return getNextDecorator().getType(typeKey, context);
        }
        else {
        	throw new OperationFailedException("Permission Denied.");
        }
	}

	@Override
	public List<TypeInfo> getTypesByRefObjectURI(String refObjectURI,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getTypesByRefObjectURI", null, null)) {
	        return getNextDecorator().getTypesByRefObjectURI(refObjectURI, context);
        }
        else {
        	throw new OperationFailedException("Permission Denied.");
        }
	}

	@Override
	public List<TypeInfo> getAllowedTypesForType(String ownerTypeKey,
			String relatedRefObjectURI, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getAllowedTypesForType", null, null)) {
	        return getNextDecorator().getAllowedTypesForType(ownerTypeKey, relatedRefObjectURI, context);
        }
        else {
        	throw new OperationFailedException("Permission Denied.");
        }
	}

	@Override
	public List<TypeTypeRelationInfo> getTypeRelationsByOwnerType(
			String ownerTypeKey, String relationTypeKey, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getTypeRelationsByOwnerType", null, null)) {
	        return getNextDecorator().getTypeRelationsByOwnerType(ownerTypeKey, relationTypeKey, context);
        }
        else {
        	throw new OperationFailedException("Permission Denied.");
        }
	}

	@Override
	public LuiInfo getLui(String luiId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getLui", null, null)) {
	        return getNextDecorator().getLui(luiId, context);
        }
        else {
        	throw new OperationFailedException("Permission Denied.");
        }
	}

	@Override
	public List<LuiInfo> getLuisByIdList(List<String> luiIdList,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getLuisByIdList", null, null)) {
	        return getNextDecorator().getLuisByIdList(luiIdList, context);
        }
        else {
        	throw new OperationFailedException("Permission Denied.");
        }
	}

	@Override
	public List<String> getLuiIdsByType(String luiTypeKey, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getLuiIdsByType", null, null)) {
	        return getNextDecorator().getLuiIdsByType(luiTypeKey, context);
        }
        else {
        	throw new OperationFailedException("Permission Denied.");
        }
	}

	@Override
	public List<String> getLuiIdsByCluId(String cluId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getLuiIdsByCluId", null, null)) {
	        return getNextDecorator().getLuiIdsByCluId(cluId, context);
        }
        else {
        	throw new OperationFailedException("Permission Denied.");
        }
	}

	@Override
	public List<String> getLuiIdsInAtpByCluId(String cluId, String atpKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getLuiIdsInAtpByCluId", null, null)) {
	        return getNextDecorator().getLuiIdsInAtpByCluId(cluId, atpKey, context);
        }
        else {
        	throw new OperationFailedException("Permission Denied.");
        }
	}

	@Override
	public List<LuiInfo> getLuisInAtpByCluId(String cluId, String atpKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getLuisInAtpByCluId", null, null)) {
	        return getNextDecorator().getLuisInAtpByCluId(cluId, atpKey, context);
        }
        else {
        	throw new OperationFailedException("Permission Denied.");
        }
	}

	@Override
	public List<String> getLuiIdsByRelation(String relatedLuiId,
			String luLuRelationTypeKey, ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getLuiIdsByRelation", null, null)) {
	        return getNextDecorator().getLuiIdsByRelation(relatedLuiId, luLuRelationTypeKey, context);
        }
        else {
        	throw new OperationFailedException("Permission Denied.");
        }
	}

	@Override
	public List<LuiInfo> getLuisByRelation(String relatedLuiId,
			String luLuRelationTypeKey, ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getLuisByRelation", null, null)) {
	        return getNextDecorator().getLuisByRelation(relatedLuiId, luLuRelationTypeKey, context);
        }
        else {
        	throw new OperationFailedException("Permission Denied.");
        }
	}

	@Override
	public List<String> getRelatedLuiIdsByLuiId(String luiId,
			String luLuRelationTypeKey, ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getRelatedLuiIdsByLuiId", null, null)) {
	        return getNextDecorator().getRelatedLuiIdsByLuiId(luiId, luLuRelationTypeKey, context);
        }
        else {
        	throw new OperationFailedException("Permission Denied.");
        }
	}

	@Override
	public List<LuiInfo> getRelatedLuisByLuiId(String luiId,
			String luLuRelationTypeKey, ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getRelatedLuisByLuiId", null, null)) {
	        return getNextDecorator().getRelatedLuisByLuiId(luiId, luLuRelationTypeKey, context);
        }
        else {
        	throw new OperationFailedException("Permission Denied.");
        }
	}

	@Override
	public List<String> searchForLuiIds(QueryByCriteria criteria,
			ContextInfo context) throws InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "searchForLuiIds", null, null)) {
	        return getNextDecorator().searchForLuiIds(criteria, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public List<LuiInfo> searchForLuis(QueryByCriteria criteria,
			ContextInfo context) throws InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "searchForLuis", null, null)) {
	        return getNextDecorator().searchForLuis(criteria, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public List<ValidationResultInfo> validateLui(String validationType,
			LuiInfo luiInfo, ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "validateLui", null, null)) {
	        return getNextDecorator().validateLui(validationType, luiInfo, context);
        }
        else {
        	throw new OperationFailedException("Permission Denied.");
        }
	}

	@Override
	public LuiInfo createLui(String cluId, String atpKey, LuiInfo luiInfo,
			ContextInfo context) throws AlreadyExistsException,
			DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "createLui", null, null)) {
	        return getNextDecorator().createLui(cluId, atpKey, luiInfo, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public LuiInfo updateLui(String luiId, LuiInfo luiInfo, ContextInfo context)
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			VersionMismatchException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "updateLui", null, null)) {
	        return getNextDecorator().updateLui(luiId, luiInfo, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public StatusInfo deleteLui(String luiId, ContextInfo context)
			throws DependentObjectsExistException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "deleteLui", null, null)) {
	        return getNextDecorator().deleteLui(luiId, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public LuiInfo updateLuiState(String luiId, String luState,
			ContextInfo context) throws DataValidationErrorException,
			DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "updateLuiState", null, null)) {
	        return getNextDecorator().updateLuiState(luiId, luState, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public LuiLuiRelationInfo getLuiLuiRelation(String luiLuiRelationId,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getLuiLuiRelation", null, null)) {
	        return getNextDecorator().getLuiLuiRelation(luiLuiRelationId, context);
        }
        else {
        	throw new OperationFailedException("Permission Denied.");
        }
	}

	@Override
	public List<LuiLuiRelationInfo> getLuiLuiRelationsByIdList(
			List<String> luiLuiRelationIdList, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getLuiLuiRelationsByIdList", null, null)) {
	        return getNextDecorator().getLuiLuiRelationsByIdList(luiLuiRelationIdList, context);
        }
        else {
        	throw new OperationFailedException("Permission Denied.");
        }
	}

	@Override
	public List<String> getLuiLuiRelationIdsByType(
			String luiLuiRelationTypeKey, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getLuiLuiRelationIdsByType", null, null)) {
	        return getNextDecorator().getLuiLuiRelationIdsByType(luiLuiRelationTypeKey, context);
        }
        else {
        	throw new OperationFailedException("Permission Denied.");
        }
	}

	@Override
	public List<LuiLuiRelationInfo> getLuiLuiRelationsByLui(String luiId,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getLuiLuiRelationsByLui", null, null)) {
	        return getNextDecorator().getLuiLuiRelationsByLui(luiId, context);
        }
        else {
        	throw new OperationFailedException("Permission Denied.");
        }
	}

	@Override
	public List<String> searchForLuiLuiRelationIds(QueryByCriteria criteria,
			ContextInfo context) throws InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "searchForLuiLuiRelationIds", null, null)) {
	        return getNextDecorator().searchForLuiLuiRelationIds(criteria, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public List<LuiLuiRelationInfo> searchForLuiLuiRelations(
			QueryByCriteria criteria, ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "searchForLuiLuiRelations", null, null)) {
	        return getNextDecorator().searchForLuiLuiRelations(criteria, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public List<ValidationResultInfo> validateLuiLuiRelation(
			String validationType, LuiLuiRelationInfo luiLuiRelationInfo,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "validateLuiLuiRelation", null, null)) {
	        return getNextDecorator().validateLuiLuiRelation(validationType, luiLuiRelationInfo, context);
        }
        else {
        	throw new OperationFailedException("Permission Denied.");
        }
	}

	@Override
	public LuiLuiRelationInfo createLuiLuiRelation(String luiId,
			String relatedLuiId, String luLuRelationTypeKey,
			LuiLuiRelationInfo luiLuiRelationInfo, ContextInfo context)
			throws AlreadyExistsException, CircularRelationshipException,
			DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "createLuiLuiRelation", null, null)) {
	        return getNextDecorator().createLuiLuiRelation(luiId, relatedLuiId, luLuRelationTypeKey, luiLuiRelationInfo, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public LuiLuiRelationInfo updateLuiLuiRelation(String luiLuiRelationId,
			LuiLuiRelationInfo luiLuiRelationInfo, ContextInfo context)
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			VersionMismatchException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "updateLuiLuiRelation", null, null)) {
	        return getNextDecorator().updateLuiLuiRelation(luiLuiRelationId, luiLuiRelationInfo, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public StatusInfo deleteLuiLuiRelation(String luiLuiRelationId,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "deleteLuiLuiRelation", null, null)) {
	        return getNextDecorator().deleteLuiLuiRelation(luiLuiRelationId, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public LuiCapacityInfo getLuiCapacity(String luiCapacityId,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getLuiCapacity", null, null)) {
	        return getNextDecorator().getLuiCapacity(luiCapacityId, context);
        }
        else {
        	throw new OperationFailedException("Permission Denied.");
        }
	}

	@Override
	public List<LuiCapacityInfo> getLuiCapacitiesByIdList(
			List<String> luiCapacityIdList, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getLuiCapacitiesByIdList", null, null)) {
	        return getNextDecorator().getLuiCapacitiesByIdList(luiCapacityIdList, context);
        }
        else {
        	throw new OperationFailedException("Permission Denied.");
        }
	}

	@Override
	public List<String> getLuiCapacityIdsByType(String luiCapacityTypeKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getLuiCapacityIdsByType", null, null)) {
	        return getNextDecorator().getLuiCapacityIdsByType(luiCapacityTypeKey, context);
        }
        else {
        	throw new OperationFailedException("Permission Denied.");
        }
	}

	@Override
	public List<String> searchForLuiCapacityIds(QueryByCriteria criteria,
			ContextInfo context) throws InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "searchForLuiCapacityIds", null, null)) {
	        return getNextDecorator().searchForLuiCapacityIds(criteria, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public List<LuiCapacityInfo> searchForLuiCapacities(
			QueryByCriteria criteria, ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "searchForLuiCapacities", null, null)) {
	        return getNextDecorator().searchForLuiCapacities(criteria, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public List<ValidationResultInfo> validateLuiCapacity(
			String validationType, LuiCapacityInfo luiCapacityInfo,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "validateLuiCapacity", null, null)) {
	        return getNextDecorator().validateLuiCapacity(validationType, luiCapacityInfo, context);
        }
        else {
        	throw new OperationFailedException("Permission Denied.");
        }
	}

	@Override
	public LuiCapacityInfo createLuiCapacity(LuiCapacityInfo luiCapacityInfo,
			ContextInfo context) throws AlreadyExistsException,
			DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "createLuiCapacity", null, null)) {
	        return getNextDecorator().createLuiCapacity(luiCapacityInfo, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public LuiCapacityInfo updateLuiCapacity(String luiCapacityId,
			LuiCapacityInfo luiCapacityInfo, ContextInfo context)
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			VersionMismatchException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "updateLuiCapacity", null, null)) {
	        return getNextDecorator().updateLuiCapacity(luiCapacityId, luiCapacityInfo, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	@Override
	public StatusInfo deleteLuiCapacity(String luiCapacityId,
			ContextInfo context) throws DependentObjectsExistException,
			DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "deleteLuiCapacity", null, null)) {
	        return getNextDecorator().deleteLuiCapacity(luiCapacityId, context);
        }
        else {
           throw new PermissionDeniedException();
        }
	}

	
}
