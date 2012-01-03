package org.kuali.student.r2.core.class1.atp.service.decorators;

import java.util.Date;
import java.util.List;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.kim.api.permission.PermissionService;
import org.kuali.student.r2.common.datadictionary.dto.DictionaryEntryInfo;
import org.kuali.student.r2.common.datadictionary.dto.DictionaryEntryInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StateInfo;
import org.kuali.student.r2.common.dto.StateProcessInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.TypeInfo;
import org.kuali.student.r2.common.dto.TypeTypeRelationInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.common.infc.HoldsPermissionService;
import org.kuali.student.r2.core.atp.dto.AtpAtpRelationInfo;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.dto.MilestoneInfo;
import org.kuali.student.r2.core.atp.service.AtpServiceDecorator;

public class AtpServiceAuthorizationDecorator extends AtpServiceDecorator implements HoldsPermissionService {
    
    private PermissionService permissionService;

    public static final String ENRLLMENT_NAMESPACE = "KS-ENROLL";
    public static final String SERVICE_NAME = "AtpService.";
    
    @Override
    public PermissionService getPermissionService() {
        return permissionService;
    }
    @Override
    public void setPermissionService(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @Override
    public AtpInfo getAtp(String atpId, ContextInfo context)
		    throws	DoesNotExistException, InvalidParameterException, MissingParameterException,
				    OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getAtp", null, null)) {
	        return getNextDecorator().getAtp(atpId, context);
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
    public List<TypeInfo> getTypesByRefObjectURI(String refObjectURI, ContextInfo context) 
    throws DoesNotExistException,InvalidParameterException, 
    MissingParameterException, OperationFailedException {
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
    public List<TypeInfo> getAllowedTypesForType(String ownerTypeKey, String relatedRefObjectURI, ContextInfo context)
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
    public List<TypeTypeRelationInfo> getTypeRelationsByOwnerType(String ownerTypeKey, String relationTypeKey, ContextInfo context)
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
    public StateProcessInfo getProcessByKey(String processKey, ContextInfo context) 
    throws DoesNotExistException, InvalidParameterException, 
    MissingParameterException, OperationFailedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getProcessByKey", null, null)) {
        	return getNextDecorator().getProcessByKey(processKey, context);
        }
        else {
        	throw new OperationFailedException("Permission Denied.");
        }
        
    }

    @Override
    public List<String> getProcessByObjectType(String refObjectUri, ContextInfo context) 
    throws DoesNotExistException, InvalidParameterException, 
    MissingParameterException, OperationFailedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getProcessByObjectType", null, null)) {
        	return getNextDecorator().getProcessByObjectType(refObjectUri, context);
        }
        else {
        	throw new OperationFailedException("Permission Denied.");
        }
    }

    @Override
    public StateInfo getState(String processKey, String stateKey, ContextInfo context) 
    throws DoesNotExistException, InvalidParameterException, 
    MissingParameterException, OperationFailedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getState", null, null)) {
        	return getNextDecorator().getState(processKey, stateKey, context);
        }
        else {
        	throw new OperationFailedException("Permission Denied.");
        }
        
    }

    @Override
    public List<StateInfo> getStatesByProcess(String processKey, ContextInfo context) 
    throws DoesNotExistException, InvalidParameterException, 
    MissingParameterException, OperationFailedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getStatesByProcess", null, null)) {
        	return getNextDecorator().getStatesByProcess(processKey, context);
        }
        else {
        	throw new OperationFailedException("Permission Denied.");
        }
        
    }

    @Override
    public List<StateInfo> getInitialValidStates(String processKey,  ContextInfo context) 
    throws DoesNotExistException, InvalidParameterException, 
    MissingParameterException, OperationFailedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getInitialValidStates", null, null)) {
        	return getNextDecorator().getInitialValidStates(processKey, context);
        }
        else {
        	throw new OperationFailedException("Permission Denied.");
        }
        
    }

    @Override
    public StateInfo getNextHappyState(String processKey, String currentStateKey, ContextInfo context)
    throws DoesNotExistException, InvalidParameterException,
    MissingParameterException, OperationFailedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getNextHappyState", null, null)) {
        	return getNextDecorator().getNextHappyState(processKey, currentStateKey, context);
        }
        else {
        	throw new OperationFailedException("Permission Denied.");
        }
        
    }

    @Override
    public List<AtpInfo> getAtpsByIds(List<String> atpIdList, ContextInfo context)
    throws DoesNotExistException, InvalidParameterException, 
    MissingParameterException, OperationFailedException, 
    PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getAtpsByKeyList", null, null)) {
        	return getNextDecorator().getAtpsByIds(atpIdList, context);
        }
        else {
           throw new PermissionDeniedException();
        }
        
    }

    @Override
    public List<String> getAtpIdsByType(String atpTypeKey, ContextInfo context)
    throws InvalidParameterException, MissingParameterException,
    OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getAtpIdsByType", null, null)) {
        	return getNextDecorator().getAtpIdsByType(atpTypeKey, context);
        }
        else {
           throw new PermissionDeniedException();
        }
        
    }

    @Override
    public List<AtpInfo> getAtpsByDate(Date searchDate, ContextInfo context)
    throws InvalidParameterException, MissingParameterException,
    OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getAtpsByDate", null, null)) {
        	return getNextDecorator().getAtpsByDate(searchDate, context);
        }
        else {
           throw new PermissionDeniedException();
        }
        
    }

    @Override
    public List<AtpInfo> getAtpsByDateAndType(Date searchDate, String searchTypeKey, ContextInfo context) 
    throws InvalidParameterException, MissingParameterException, 
    OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getAtpsByDateAndType", null, null)) {
        	return getNextDecorator().getAtpsByDateAndType(searchDate, searchTypeKey, context);
        }
        else {
           throw new PermissionDeniedException();
        }
        
    }

    @Override
    public List<AtpInfo> getAtpsByDates(Date startDate, Date endDate, ContextInfo context) 
    throws InvalidParameterException, MissingParameterException, 
    OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getAtpsByDates", null, null)) {
        	return getNextDecorator().getAtpsByDates(startDate, endDate, context);
        }
        else {
           throw new PermissionDeniedException();
        }
        
    }

    @Override
    public List<AtpInfo> getAtpsByDatesAndType(Date startDate, Date endDate, String searchTypeKey, ContextInfo context) 
    throws InvalidParameterException, MissingParameterException, 
    OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getAtpsByDatesAndType", null, null)) {
        	return getNextDecorator().getAtpsByDatesAndType(startDate, endDate, searchTypeKey, context);
        }
        else {
           throw new PermissionDeniedException();
        }
        
    }

    @Override
    public List<AtpInfo> getAtpsByStartDateRange(Date searchDateRangeStart, Date searchDateRangeEnd, ContextInfo context) 
    throws InvalidParameterException, MissingParameterException, 
    OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getAtpsByStartDateRange", null, null)) {
        	return getNextDecorator().getAtpsByStartDateRange(searchDateRangeStart, searchDateRangeEnd, context);
        }
        else {
           throw new PermissionDeniedException();
        }
        
    }

    @Override
    public List<AtpInfo> getAtpsByStartDateRangeAndType(Date searchDateRangeStart, Date searchDateRangeEnd, String searchTypeKey, ContextInfo context) 
    throws InvalidParameterException, MissingParameterException, 
    OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getAtpsByStartDateRangeAndType", null, null)) {
        	return getNextDecorator().getAtpsByStartDateRangeAndType(searchDateRangeStart, searchDateRangeEnd, searchTypeKey, context);
        }
        else {
           throw new PermissionDeniedException();
        }
        
    }

    @Override
    public MilestoneInfo getMilestone(String milestoneId, ContextInfo context)
    throws DoesNotExistException, InvalidParameterException,
    MissingParameterException, OperationFailedException,
    PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getMilestone", null, null)) {
        	return getNextDecorator().getMilestone(milestoneId, context);
        }
        else {
           throw new PermissionDeniedException();
        }
        
    }

    @Override
    public List<MilestoneInfo> getMilestonesByIds(List<String> milestoneIdList, ContextInfo context)
    throws DoesNotExistException, InvalidParameterException,
    MissingParameterException, OperationFailedException,
    PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getMilestonesByIds", null, null)) {
        	return getNextDecorator().getMilestonesByIds(milestoneIdList, context);
        }
        else {
           throw new PermissionDeniedException();
        }
    }

    @Override
    public List<String> getMilestoneIdsByType(String milestoneTypeKey, ContextInfo context)
    throws InvalidParameterException, MissingParameterException, 
    OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getMilestoneIdsByType", null, null)) {
        	return getNextDecorator().getMilestoneIdsByType(milestoneTypeKey, context);
        }
        else {
           throw new PermissionDeniedException();
        }
        
    }

    @Override
    public List<MilestoneInfo> getMilestonesForAtp(String atpId, ContextInfo context)
    throws InvalidParameterException, MissingParameterException, 
    OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getMilestonesForAtp", null, null)) {
        	return getNextDecorator().getMilestonesForAtp(atpId, context);
        }
        else {
           throw new PermissionDeniedException();
        }
        
    }

    @Override
    public List<MilestoneInfo> getMilestonesByDates(Date startDate, Date endDate, ContextInfo context)
    throws InvalidParameterException, MissingParameterException,
    OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getMilestonesByDates", null, null)) {
        	return getNextDecorator().getMilestonesByDates(startDate, endDate, context);
        }
        else {
           throw new PermissionDeniedException();
        }
        
    }

    @Override
    public List<String> searchForAtpIds(QueryByCriteria criteria, ContextInfo context)
    throws InvalidParameterException, MissingParameterException, 
    OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "searchForAtpIds", null, null)) {
        	return getNextDecorator().searchForAtpIds(criteria, context);
        }
        else {
           throw new PermissionDeniedException();
        }
        
    }

    @Override
    public List<AtpInfo> searchForAtps(QueryByCriteria criteria, ContextInfo context) 
    throws InvalidParameterException, MissingParameterException, 
    OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "searchForAtps", null, null)) {
        	return getNextDecorator().searchForAtps(criteria, context);
        }
        else {
           throw new PermissionDeniedException();
        }
        
    }

    @Override
    public List<ValidationResultInfo> validateAtp(String validationType, String atpTypeKey,AtpInfo atpInfo, ContextInfo context)
    throws DoesNotExistException, InvalidParameterException,
    MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "validateAtp", null, null)) {
        	return getNextDecorator().validateAtp(validationType, atpTypeKey, atpInfo, context);
        }
        else {
        	throw new OperationFailedException("Permission Denied.");
        }
        
    }

    @Override
    public AtpInfo createAtp(String atpId, AtpInfo atpInfo, ContextInfo context)
    throws AlreadyExistsException, DataValidationErrorException,
    InvalidParameterException, MissingParameterException,
    OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "createAtp", null, null)) {
        	return getNextDecorator().createAtp(atpId, atpInfo, context);
        }
        else {
           throw new PermissionDeniedException();
        }
        
    }

    @Override
    public AtpInfo updateAtp(String atpId, AtpInfo atpInfo, ContextInfo context)
    throws DataValidationErrorException, DoesNotExistException,
    InvalidParameterException, MissingParameterException,
    OperationFailedException, PermissionDeniedException,
    VersionMismatchException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "updateAtp", null, null)) {
            try {
                return getNextDecorator().updateAtp(atpId, atpInfo, context);
            } catch (ReadOnlyException e) {
                throw new OperationFailedException(e.getMessage());
            }
        }
        else {
           throw new PermissionDeniedException();
        }
        
    }

    @Override
    public StatusInfo deleteAtp(String atpId, ContextInfo context)
    throws DoesNotExistException, InvalidParameterException,
    MissingParameterException, OperationFailedException,
    PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "deleteAtp", null, null)) {
        	return getNextDecorator().deleteAtp(atpId, context);
        }
        else {
           throw new PermissionDeniedException();
        }
        
    }

    @Override
    public List<String> searchForMilestoneIds(QueryByCriteria criteria, ContextInfo context)
    throws InvalidParameterException, MissingParameterException, 
    OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "searchForMilestoneIds", null, null)) {
        	return getNextDecorator().searchForMilestoneIds(criteria, context);
        }
        else {
           throw new PermissionDeniedException();
        }
        
    }

    @Override
    public List<MilestoneInfo> searchForMilestones(QueryByCriteria criteria, ContextInfo context) 
    throws InvalidParameterException, MissingParameterException, 
    OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "searchForMilestones", null, null)) {
        	return getNextDecorator().searchForMilestones(criteria, context);
        }
        else {
           throw new PermissionDeniedException();
        }
        
    }

    @Override
    public List<ValidationResultInfo> validateMilestone(String validationType, MilestoneInfo milestoneInfo, ContextInfo context)
    throws DoesNotExistException, InvalidParameterException,
    MissingParameterException, OperationFailedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "validateMilestone", null, null)) {
            try {
                return getNextDecorator().validateMilestone(validationType, milestoneInfo, context);
            } catch (PermissionDeniedException e) {
                throw new OperationFailedException(e.getMessage());
            }
        }
        else {
        	throw new OperationFailedException("Permission Denied.");
        }
        
    }

    @Override
    public MilestoneInfo createMilestone(MilestoneInfo milestoneInfo, ContextInfo context)
    throws DataValidationErrorException,
    InvalidParameterException, MissingParameterException,
    OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "createMilestone", null, null)) {
            try {
                return getNextDecorator().createMilestone(milestoneInfo, context);
            } catch (ReadOnlyException e) {
                throw new OperationFailedException(e.getMessage());
            }
        }
        else {
           throw new PermissionDeniedException();
        }
        
    }

    @Override
    public MilestoneInfo updateMilestone(String milestoneId, MilestoneInfo milestoneInfo, ContextInfo context)
    throws DataValidationErrorException, DoesNotExistException,
    InvalidParameterException, MissingParameterException,
    OperationFailedException, PermissionDeniedException,
    VersionMismatchException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "updateMilestone", null, null)) {
            try {
                return getNextDecorator().updateMilestone(milestoneId, milestoneInfo, context);
            } catch (ReadOnlyException e) {
                throw new OperationFailedException(e.getMessage());
            }
        }
        else {
           throw new PermissionDeniedException();
        }
        
    }

    @Override
    public StatusInfo deleteMilestone(String milestoneId, ContextInfo context)
    throws DoesNotExistException, InvalidParameterException,
    MissingParameterException, OperationFailedException,
    PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "deleteMilestone", null, null)) {
        	return getNextDecorator().deleteMilestone(milestoneId, context);
        }
        else {
           throw new PermissionDeniedException();
        }
        
    }

    @Override
    public AtpAtpRelationInfo getAtpAtpRelation(String atpAtpRelationId, ContextInfo context) 
    throws DoesNotExistException,
    InvalidParameterException, MissingParameterException,
    OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getAtpAtpRelation", null, null)) {
        	return getNextDecorator().getAtpAtpRelation(atpAtpRelationId, context);
        }
        else {
           throw new PermissionDeniedException();
        }
        
    }

    @Override
    public List<AtpAtpRelationInfo> getAtpAtpRelationsByIds(List<String> atpAtpRelationIdList, ContextInfo context)
    throws DoesNotExistException, InvalidParameterException,
    MissingParameterException, OperationFailedException,
    PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getAtpAtpRelationsByIdList", null, null)) {
        	return getNextDecorator().getAtpAtpRelationsByIds(atpAtpRelationIdList, context);
        }
        else {
           throw new PermissionDeniedException();
        }
        
    }

    @Override
    public List<String> getAtpAtpRelationIdsByType(String atpAtpRelationTypeKey, ContextInfo context)
    throws InvalidParameterException, MissingParameterException,
    OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getAtpAtpRelationIdsByType", null, null)) {
        	return getNextDecorator().getAtpAtpRelationIdsByType(atpAtpRelationTypeKey, context);
        }
        else {
           throw new PermissionDeniedException();
        }
        
    }

    @Override
    public List<AtpAtpRelationInfo> getAtpAtpRelationsByAtp(String atpId, ContextInfo context)
    throws InvalidParameterException,
    MissingParameterException, OperationFailedException, 
    PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getAtpAtpRelationsByAtp", null, null)) {
        	return getNextDecorator().getAtpAtpRelationsByAtp(atpId, context);
        }
        else {
           throw new PermissionDeniedException();
        }
        
    }

    @Override
    public List<String> searchForAtpAtpRelationIds(QueryByCriteria criteria, ContextInfo context) 
    throws InvalidParameterException, MissingParameterException, 
    OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "searchForAtpAtpRelationIds", null, null)) {
        	return getNextDecorator().searchForAtpAtpRelationIds(criteria, context);
        }
        else {
           throw new PermissionDeniedException();
        }
        
    }

    @Override
    public List<AtpAtpRelationInfo> searchForAtpAtpRelations(QueryByCriteria criteria, ContextInfo context) 
    throws InvalidParameterException, MissingParameterException, 
    OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "searchForAtpAtpRelations", null, null)) {
        	return getNextDecorator().searchForAtpAtpRelations(criteria, context);
        }
        else {
           throw new PermissionDeniedException();
        }
        
    }

    @Override
    public List<ValidationResultInfo> validateAtpAtpRelation(String validationTypeKey, String atpId, String atpPeerKey,
                                                             String atpAtpRelationTypeKey, AtpAtpRelationInfo atpAtpRelationInfo,
                                                             ContextInfo contextInfo)
    throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException{
        if (null == contextInfo) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(contextInfo.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "validateAtpAtpRelation", null, null)) {
        	return getNextDecorator().validateAtpAtpRelation(validationTypeKey, atpId, atpPeerKey, atpAtpRelationTypeKey, atpAtpRelationInfo, contextInfo);
        }
        else {
        	throw new OperationFailedException("Permission Denied.");
        }
        
    }

    @Override
    public AtpAtpRelationInfo createAtpAtpRelation(String atpId, String atpPeerKey,
                                                   AtpAtpRelationInfo atpAtpRelationInfo, ContextInfo context)
    throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException,
           OperationFailedException, PermissionDeniedException, ReadOnlyException {

        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "createAtpAtpRelation", null, null)) {
        	 return getNextDecorator().createAtpAtpRelation(atpId, atpPeerKey, atpAtpRelationInfo, context);
        }
        else {
           throw new PermissionDeniedException();
        }
       
    }

    @Override
    public AtpAtpRelationInfo updateAtpAtpRelation(String atpAtpRelationId, AtpAtpRelationInfo atpAtpRelationInfo, ContextInfo context)
    throws DataValidationErrorException, DoesNotExistException,
    InvalidParameterException, MissingParameterException,
    OperationFailedException, PermissionDeniedException,
    VersionMismatchException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "updateAtpAtpRelation", null, null)) {
            try {
                return getNextDecorator().updateAtpAtpRelation(atpAtpRelationId, atpAtpRelationInfo, context);
            } catch (ReadOnlyException e) {
                throw new OperationFailedException(e.getMessage());
            }
        }
        else {
           throw new PermissionDeniedException();
        }
        
    }

    @Override
    public StatusInfo deleteAtpAtpRelation(String atpAtpRelationId, ContextInfo context) 
    throws DoesNotExistException, InvalidParameterException, 
    MissingParameterException, OperationFailedException, 
    PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "deleteAtpAtpRelation", null, null)) {
        	return getNextDecorator().deleteAtpAtpRelation(atpAtpRelationId, context);
        }
        else {
           throw new PermissionDeniedException();
        }
        
    }

    @Override
    public List<AtpAtpRelationInfo> getAtpAtpRelationsByTypeAndAtp(String atpId, String relationType, ContextInfo context)
    throws InvalidParameterException,
    MissingParameterException, OperationFailedException,
    PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getAtpAtpRelationsByTypeAndAtp", null, null)) {
        	return getNextDecorator().getAtpAtpRelationsByTypeAndAtp(atpId, relationType, context);
        }
        else {
           throw new PermissionDeniedException();
        }
    }

}
