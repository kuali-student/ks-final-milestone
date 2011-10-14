package org.kuali.student.r2.core.class1.atp.service.decorators;

import java.util.Date;
import java.util.List;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.kim.service.PermissionService;
import org.kuali.student.r2.common.datadictionary.dto.DictionaryEntryInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StateInfo;
import org.kuali.student.r2.common.dto.StateProcessInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.TypeInfo;
import org.kuali.student.r2.common.dto.TypeTypeRelationInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.infc.HoldsPermissionService;
import org.kuali.student.r2.core.atp.dto.AtpAtpRelationInfo;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.dto.AtpMilestoneRelationInfo;
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
    public AtpInfo getAtp(String atpKey, ContextInfo context)
		    throws	DoesNotExistException, InvalidParameterException, MissingParameterException,
				    OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getAtp", null, null)) {
	        return getNextDecorator().getAtp(atpKey, context);
        }
        else {
           throw new PermissionDeniedException();
        }
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
    public DictionaryEntryInfo getDataDictionaryEntry(String entryKey, ContextInfo context) 
    throws OperationFailedException, MissingParameterException, 
    PermissionDeniedException, DoesNotExistException {
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
    public List<AtpInfo> getAtpsByKeyList(List<String> atpKeyList, ContextInfo context) 
    throws DoesNotExistException, InvalidParameterException, 
    MissingParameterException, OperationFailedException, 
    PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getAtpsByKeyList", null, null)) {
        	return getNextDecorator().getAtpsByKeyList(atpKeyList, context);
        }
        else {
           throw new PermissionDeniedException();
        }
        
    }

    @Override
    public List<String> getAtpKeysByType(String atpTypeKey, ContextInfo context)
    throws InvalidParameterException, MissingParameterException,
    OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getAtpKeysByType", null, null)) {
        	return getNextDecorator().getAtpKeysByType(atpTypeKey, context);
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
    public MilestoneInfo getMilestone(String milestoneKey, ContextInfo context)
    throws DoesNotExistException, InvalidParameterException,
    MissingParameterException, OperationFailedException,
    PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getMilestone", null, null)) {
        	return getNextDecorator().getMilestone(milestoneKey, context);
        }
        else {
           throw new PermissionDeniedException();
        }
        
    }

    @Override
    public List<MilestoneInfo> getMilestonesByKeyList(List<String> milestoneKeyList, ContextInfo context)
    throws DoesNotExistException, InvalidParameterException,
    MissingParameterException, OperationFailedException,
    PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getMilestonesByKeyList", null, null)) {
        	return getNextDecorator().getMilestonesByKeyList(milestoneKeyList, context);
        }
        else {
           throw new PermissionDeniedException();
        }
    }

    @Override
    public List<String> getMilestoneKeysByType(String milestoneTypeKey, ContextInfo context) 
    throws InvalidParameterException, MissingParameterException, 
    OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getMilestoneKeysByType", null, null)) {
        	return getNextDecorator().getMilestoneKeysByType(milestoneTypeKey, context);
        }
        else {
           throw new PermissionDeniedException();
        }
        
    }

    @Override
    public List<MilestoneInfo> getMilestonesByAtp(String atpKey, ContextInfo context) 
    throws InvalidParameterException, MissingParameterException, 
    OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getMilestonesByAtp", null, null)) {
        	return getNextDecorator().getMilestonesByAtp(atpKey, context);
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
    public List<MilestoneInfo> getMilestonesByDatesAndType(String milestoneTypeKey, Date startDate, Date endDate, ContextInfo context) 
    throws InvalidParameterException, MissingParameterException, 
    OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getMilestonesByDatesAndType", null, null)) {
        	return getNextDecorator().getMilestonesByDatesAndType(milestoneTypeKey, startDate, endDate, context);
        }
        else {
           throw new PermissionDeniedException();
        }
        
    }

    @Override
    public List<String> searchForAtpKeys(QueryByCriteria criteria, ContextInfo context) 
    throws InvalidParameterException, MissingParameterException, 
    OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "searchForAtpKeys", null, null)) {
        	return getNextDecorator().searchForAtpKeys(criteria, context);
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
    public List<ValidationResultInfo> validateAtp(String validationType, AtpInfo atpInfo, ContextInfo context) 
    throws DoesNotExistException, InvalidParameterException, 
    MissingParameterException, OperationFailedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "validateAtp", null, null)) {
        	return getNextDecorator().validateAtp(validationType, atpInfo, context);
        }
        else {
        	throw new OperationFailedException("Permission Denied.");
        }
        
    }

    @Override
    public AtpInfo createAtp(String atpKey, AtpInfo atpInfo, ContextInfo context)
    throws AlreadyExistsException, DataValidationErrorException,
    InvalidParameterException, MissingParameterException,
    OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "createAtp", null, null)) {
        	return getNextDecorator().createAtp(atpKey, atpInfo, context);
        }
        else {
           throw new PermissionDeniedException();
        }
        
    }

    @Override
    public AtpInfo updateAtp(String atpKey, AtpInfo atpInfo, ContextInfo context)
    throws DataValidationErrorException, DoesNotExistException,
    InvalidParameterException, MissingParameterException,
    OperationFailedException, PermissionDeniedException,
    VersionMismatchException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "updateAtp", null, null)) {
        	return getNextDecorator().updateAtp(atpKey, atpInfo, context);
        }
        else {
           throw new PermissionDeniedException();
        }
        
    }

    @Override
    public StatusInfo deleteAtp(String atpKey, ContextInfo context)
    throws DoesNotExistException, InvalidParameterException,
    MissingParameterException, OperationFailedException,
    PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "deleteAtp", null, null)) {
        	return getNextDecorator().deleteAtp(atpKey, context);
        }
        else {
           throw new PermissionDeniedException();
        }
        
    }

    @Override
    public List<String> searchForMilestoneKeys(QueryByCriteria criteria, ContextInfo context) 
    throws InvalidParameterException, MissingParameterException, 
    OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "searchForMilestoneKeys", null, null)) {
        	return getNextDecorator().searchForMilestoneKeys(criteria, context);
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
        	return getNextDecorator().validateMilestone(validationType, milestoneInfo, context);
        }
        else {
        	throw new OperationFailedException("Permission Denied.");
        }
        
    }

    @Override
    public MilestoneInfo createMilestone(String milestoneKey,MilestoneInfo milestoneInfo, ContextInfo context)
    throws AlreadyExistsException, DataValidationErrorException,
    InvalidParameterException, MissingParameterException,
    OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "createMilestone", null, null)) {
        	return getNextDecorator().createMilestone(milestoneKey, milestoneInfo, context);
        }
        else {
           throw new PermissionDeniedException();
        }
        
    }

    @Override
    public MilestoneInfo updateMilestone(String milestoneKey, MilestoneInfo milestoneInfo, ContextInfo context)
    throws DataValidationErrorException, DoesNotExistException,
    InvalidParameterException, MissingParameterException,
    OperationFailedException, PermissionDeniedException,
    VersionMismatchException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "updateMilestone", null, null)) {
        	return getNextDecorator().updateMilestone(milestoneKey, milestoneInfo, context);
        }
        else {
           throw new PermissionDeniedException();
        }
        
    }

    @Override
    public StatusInfo deleteMilestone(String milestoneKey, ContextInfo context)
    throws DoesNotExistException, InvalidParameterException,
    MissingParameterException, OperationFailedException,
    PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "deleteMilestone", null, null)) {
        	return getNextDecorator().deleteMilestone(milestoneKey, context);
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
    public List<AtpAtpRelationInfo> getAtpAtpRelationsByIdList(	List<String> atpAtpRelationIdList, ContextInfo context)
    throws DoesNotExistException, InvalidParameterException,
    MissingParameterException, OperationFailedException,
    PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getAtpAtpRelationsByIdList", null, null)) {
        	return getNextDecorator().getAtpAtpRelationsByIdList(atpAtpRelationIdList, context);
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
    public List<AtpAtpRelationInfo> getAtpAtpRelationsByAtp(String atpKey, ContextInfo context) 
    throws DoesNotExistException, InvalidParameterException, 
    MissingParameterException, OperationFailedException, 
    PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getAtpAtpRelationsByAtp", null, null)) {
        	return getNextDecorator().getAtpAtpRelationsByAtp(atpKey, context);
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
    public List<ValidationResultInfo> validateAtpAtpRelation(String validationType, AtpAtpRelationInfo atpAtpRelationInfo, ContextInfo context) 
    throws DoesNotExistException, InvalidParameterException, 
    MissingParameterException, OperationFailedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "validateAtpAtpRelation", null, null)) {
        	return getNextDecorator().validateAtpAtpRelation(validationType, atpAtpRelationInfo, context);
        }
        else {
        	throw new OperationFailedException("Permission Denied.");
        }
        
    }

    @Override
    public AtpAtpRelationInfo createAtpAtpRelation(AtpAtpRelationInfo atpAtpRelationInfo, ContextInfo context)
    throws AlreadyExistsException, DataValidationErrorException,
    InvalidParameterException, MissingParameterException,
    OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "createAtpAtpRelation", null, null)) {
        	 return getNextDecorator().createAtpAtpRelation(atpAtpRelationInfo, context);
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
        	return getNextDecorator().updateAtpAtpRelation(atpAtpRelationId, atpAtpRelationInfo, context);
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
    public AtpMilestoneRelationInfo getAtpMilestoneRelation(String atpMilestoneRelationId, ContextInfo context)
    throws DoesNotExistException, InvalidParameterException,
    MissingParameterException, OperationFailedException,
    PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getAtpMilestoneRelation", null, null)) {
        	return getNextDecorator().getAtpMilestoneRelation(atpMilestoneRelationId, context);
        }
        else {
           throw new PermissionDeniedException();
        }
        
    }

    @Override
    public List<AtpMilestoneRelationInfo> getAtpMilestoneRelationsByIdList(List<String> atpMilestoneRelationIdList, ContextInfo context)
    throws DoesNotExistException, InvalidParameterException,
    MissingParameterException, OperationFailedException,
    PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getAtpMilestoneRelationsByIdList", null, null)) {
        	return getNextDecorator().getAtpMilestoneRelationsByIdList(atpMilestoneRelationIdList, context);
        }
        else {
           throw new PermissionDeniedException();
        }
        
    }

    @Override
    public List<String> getAtpMilestoneRelationIdsByType(String atpMilestoneRelationTypeKey, ContextInfo context)
    throws InvalidParameterException, MissingParameterException,
    OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getAtpMilestoneRelationIdsByType", null, null)) {
        	return getNextDecorator().getAtpMilestoneRelationIdsByType(atpMilestoneRelationTypeKey, context);
        }
        else {
           throw new PermissionDeniedException();
        }
        
    }

    @Override
    public List<AtpMilestoneRelationInfo> getAtpMilestoneRelationsByAtp(String atpKey, ContextInfo context) 
    throws DoesNotExistException, InvalidParameterException, 
    MissingParameterException, OperationFailedException, 
    PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getAtpMilestoneRelationsByAtp", null, null)) {
        	return getNextDecorator().getAtpMilestoneRelationsByAtp(atpKey, context);
        }
        else {
           throw new PermissionDeniedException();
        }
        
    }

    @Override
    public List<AtpMilestoneRelationInfo> getAtpMilestoneRelationsByMilestone(String milestoneKey, ContextInfo context)
    throws DoesNotExistException, InvalidParameterException,
    MissingParameterException, OperationFailedException,
    PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getAtpMilestoneRelationsByMilestone", null, null)) {
        	return getNextDecorator().getAtpMilestoneRelationsByAtp(milestoneKey, context);
        }
        else {
           throw new PermissionDeniedException();
        }
        
    }

    @Override
    public List<String> searchForAtpMilestoneRelationIds(QueryByCriteria criteria, ContextInfo context) 
    throws InvalidParameterException, MissingParameterException, 
    OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "searchForAtpMilestoneRelationIds", null, null)) {
        	return getNextDecorator().searchForAtpMilestoneRelationIds(criteria, context);
        }
        else {
           throw new PermissionDeniedException();
        }
        
    }

    @Override
    public List<AtpMilestoneRelationInfo> searchForAtpMilestoneRelations(QueryByCriteria criteria, ContextInfo context) 
    throws InvalidParameterException, MissingParameterException, 
    OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "searchForAtpMilestoneRelations", null, null)) {
        	return getNextDecorator().searchForAtpMilestoneRelations(criteria, context);
        }
        else {
           throw new PermissionDeniedException();
        }
        
    }

    @Override
    public List<ValidationResultInfo> validateAtpMilestoneRelation(String validationType, AtpMilestoneRelationInfo atpMilestoneRelationInfo, ContextInfo context) 
    throws DoesNotExistException, InvalidParameterException, 
    MissingParameterException, OperationFailedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "validateAtpMilestoneRelation", null, null)) {
        	return getNextDecorator().validateAtpMilestoneRelation(validationType, atpMilestoneRelationInfo, context);
        }
        else {
        	throw new OperationFailedException("Permission Denied.");
        }
        
    }

    @Override
    public AtpMilestoneRelationInfo createAtpMilestoneRelation(AtpMilestoneRelationInfo atpMilestoneRelationInfo, ContextInfo context) 
    throws AlreadyExistsException, DataValidationErrorException, 
    InvalidParameterException, MissingParameterException, 
    OperationFailedException, PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "createAtpMilestoneRelation", null, null)) {
        	return getNextDecorator().createAtpMilestoneRelation(atpMilestoneRelationInfo, context);
        }
        else {
           throw new PermissionDeniedException();
        }
        
    }

    @Override
    public AtpMilestoneRelationInfo updateAtpMilestoneRelation(String atpMilestoneRelationId,AtpMilestoneRelationInfo atpMilestoneRelationInfo, ContextInfo context) 
    throws DataValidationErrorException, DoesNotExistException, 
    InvalidParameterException, MissingParameterException, 
    OperationFailedException, PermissionDeniedException,
    VersionMismatchException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "updateAtpMilestoneRelation", null, null)) {
        	return getNextDecorator().updateAtpMilestoneRelation(atpMilestoneRelationId, atpMilestoneRelationInfo, context);
        }
        else {
           throw new PermissionDeniedException();
        }
        
    }

    @Override
    public StatusInfo deleteAtpMilestoneRelation(String atpMilestoneRelationId, ContextInfo context) 
    throws DoesNotExistException, InvalidParameterException, 
    MissingParameterException, OperationFailedException, 
    PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "deleteAtpMilestoneRelation", null, null)) {
        	return getNextDecorator().deleteAtpMilestoneRelation(atpMilestoneRelationId, context);
        }
        else {
           throw new PermissionDeniedException();
        }
        
    }

    @Override
    public List<AtpAtpRelationInfo> getAtpAtpRelationsByAtpAndRelationType(String atpKey, String relationType, ContextInfo context)
    throws DoesNotExistException, InvalidParameterException,
    MissingParameterException, OperationFailedException,
    PermissionDeniedException {
        if (null == context) {
            throw new MissingParameterException();
        }
           
        if (permissionService.isAuthorized(context.getPrincipalId(), ENRLLMENT_NAMESPACE, SERVICE_NAME + "getAtpAtpRelationsByAtpAndRelationType", null, null)) {
        	return getNextDecorator().getAtpAtpRelationsByAtpAndRelationType(atpKey, relationType, context);
        }
        else {
           throw new PermissionDeniedException();
        }
    }

}
