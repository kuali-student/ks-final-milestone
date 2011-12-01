package org.kuali.student.r2.core.atp.service;

import java.util.Date;
import java.util.List;

import org.kuali.rice.core.api.criteria.QueryByCriteria;

import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.core.atp.dto.AtpAtpRelationInfo;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.dto.MilestoneInfo;

import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StateInfo;
import org.kuali.student.r2.common.dto.StateProcessInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.TypeInfo;
import org.kuali.student.r2.common.dto.TypeTypeRelationInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.datadictionary.dto.DictionaryEntryInfo;

import javax.jws.WebParam;


public class AtpServiceDecorator implements AtpService {

    private AtpService nextDecorator;

    public AtpService getNextDecorator() throws OperationFailedException {
        if (null == nextDecorator) {
            throw new OperationFailedException("Misconfigured application: nextDecorator is null");
        }

        return nextDecorator;
    }

    public void setNextDecorator(AtpService nextDecorator) {
        this.nextDecorator = nextDecorator;
    }


    @Override
    public List<String> getDataDictionaryEntryKeys(ContextInfo context)
    throws OperationFailedException, MissingParameterException,
    PermissionDeniedException {

        return getNextDecorator().getDataDictionaryEntryKeys(context);
    }

    @Override
    public DictionaryEntryInfo getDataDictionaryEntry(String entryKey, ContextInfo context) 
    throws OperationFailedException, MissingParameterException, 
    PermissionDeniedException, DoesNotExistException {

        return getNextDecorator().getDataDictionaryEntry(entryKey, context);
    }

    @Override
    public TypeInfo getType(String typeKey, ContextInfo context)
    throws DoesNotExistException, InvalidParameterException,
    MissingParameterException, OperationFailedException {

        return getNextDecorator().getType(typeKey, context);
    }

    @Override
    public List<TypeInfo> getTypesByRefObjectURI(String refObjectURI, ContextInfo context) 
    throws DoesNotExistException,InvalidParameterException, 
    MissingParameterException, OperationFailedException {

        return getNextDecorator().getTypesByRefObjectURI(refObjectURI, context);
    }

    @Override
    public List<TypeInfo> getAllowedTypesForType(String ownerTypeKey, String relatedRefObjectURI, ContextInfo context)
    throws DoesNotExistException, InvalidParameterException,
    MissingParameterException, OperationFailedException {

        return getNextDecorator().getAllowedTypesForType(ownerTypeKey, relatedRefObjectURI, context);
    }

    @Override
    public List<TypeTypeRelationInfo> getTypeRelationsByOwnerType(String ownerTypeKey, String relationTypeKey, ContextInfo context)
    throws DoesNotExistException, InvalidParameterException,
    MissingParameterException, OperationFailedException {

        return getNextDecorator().getTypeRelationsByOwnerType(ownerTypeKey, relationTypeKey, context);
    }

    @Override
    public StateProcessInfo getProcessByKey(String processKey, ContextInfo context) 
    throws DoesNotExistException, InvalidParameterException, 
    MissingParameterException, OperationFailedException {

        return getNextDecorator().getProcessByKey(processKey, context);
    }

    @Override
    public List<String> getProcessByObjectType(String refObjectUri, ContextInfo context) 
    throws DoesNotExistException, InvalidParameterException, 
    MissingParameterException, OperationFailedException {

        return getNextDecorator().getProcessByObjectType(refObjectUri, context);
    }

    @Override
    public StateInfo getState(String processKey, String stateKey, ContextInfo context) 
    throws DoesNotExistException, InvalidParameterException, 
    MissingParameterException, OperationFailedException {

        return getNextDecorator().getState(processKey, stateKey, context);
    }

    @Override
    public List<StateInfo> getStatesByProcess(String processKey, ContextInfo context) 
    throws DoesNotExistException, InvalidParameterException, 
    MissingParameterException, OperationFailedException {

        return getNextDecorator().getStatesByProcess(processKey, context);
    }

    @Override
    public List<StateInfo> getInitialValidStates(String processKey,  ContextInfo context) 
    throws DoesNotExistException, InvalidParameterException, 
    MissingParameterException, OperationFailedException {

        return getNextDecorator().getInitialValidStates(processKey, context);
    }

    @Override
    public StateInfo getNextHappyState(String processKey, String currentStateKey, ContextInfo context)
    throws DoesNotExistException, InvalidParameterException,
    MissingParameterException, OperationFailedException {

        return getNextDecorator().getNextHappyState(processKey, currentStateKey, context);
    }

    @Override
    public AtpInfo getAtp(String atpKey, ContextInfo context)
    throws DoesNotExistException, InvalidParameterException,
    MissingParameterException, OperationFailedException,
    PermissionDeniedException {

        return getNextDecorator().getAtp(atpKey, context);
    }

    @Override
    public List<AtpInfo> getAtpsByKeys(List<String> atpKeyList, ContextInfo context)
    throws DoesNotExistException, InvalidParameterException, 
    MissingParameterException, OperationFailedException, 
    PermissionDeniedException {

        return getNextDecorator().getAtpsByKeys(atpKeyList, context);
    }

    @Override
    public List<String> getAtpKeysByType(String atpTypeKey, ContextInfo context)
    throws InvalidParameterException, MissingParameterException,
    OperationFailedException, PermissionDeniedException {

        return getNextDecorator().getAtpKeysByType(atpTypeKey, context);
    }

    @Override
    public List<AtpInfo> getAtpsByDate(Date searchDate, ContextInfo context)
    throws InvalidParameterException, MissingParameterException,
    OperationFailedException, PermissionDeniedException {

        return getNextDecorator().getAtpsByDate(searchDate, context);
    }

    @Override
    public List<AtpInfo> getAtpsByDateAndType(Date searchDate, String searchTypeKey, ContextInfo context) 
    throws InvalidParameterException, MissingParameterException, 
    OperationFailedException, PermissionDeniedException {

        return getNextDecorator().getAtpsByDateAndType(searchDate, searchTypeKey, context);
    }

    @Override
    public List<AtpInfo> getAtpsByDates(Date startDate, Date endDate, ContextInfo context) 
    throws InvalidParameterException, MissingParameterException, 
    OperationFailedException, PermissionDeniedException {

        return getNextDecorator().getAtpsByDates(startDate, endDate, context);
    }

    @Override
    public List<AtpInfo> getAtpsByDatesAndType(Date startDate, Date endDate, String searchTypeKey, ContextInfo context) 
    throws InvalidParameterException, MissingParameterException, 
    OperationFailedException, PermissionDeniedException {

        return getNextDecorator().getAtpsByDatesAndType(startDate, endDate, searchTypeKey, context);
    }

    @Override
    public List<AtpInfo> getAtpsByStartDateRange(Date searchDateRangeStart, Date searchDateRangeEnd, ContextInfo context) 
    throws InvalidParameterException, MissingParameterException, 
    OperationFailedException, PermissionDeniedException {

        return getNextDecorator().getAtpsByStartDateRange(searchDateRangeStart, searchDateRangeEnd, context);
    }

    @Override
    public List<AtpInfo> getAtpsByStartDateRangeAndType(Date searchDateRangeStart, Date searchDateRangeEnd, String searchTypeKey, ContextInfo context) 
    throws InvalidParameterException, MissingParameterException, 
    OperationFailedException, PermissionDeniedException {

        return getNextDecorator().getAtpsByStartDateRangeAndType(searchDateRangeStart, searchDateRangeEnd, searchTypeKey, context);
    }

    @Override
    public MilestoneInfo getMilestone(String milestoneKey, ContextInfo context)
    throws DoesNotExistException, InvalidParameterException,
    MissingParameterException, OperationFailedException,
    PermissionDeniedException {

        return getNextDecorator().getMilestone(milestoneKey, context);
    }

    @Override
    public List<MilestoneInfo> getMilestonesByIds(List<String> milestoneKeyList, ContextInfo context)
    throws DoesNotExistException, InvalidParameterException,
    MissingParameterException, OperationFailedException,
    PermissionDeniedException {

        return getNextDecorator().getMilestonesByIds(milestoneKeyList, context);
    }

    @Override
    public List<String> getMilestoneIdsByType(String milestoneTypeKey, ContextInfo context)
    throws InvalidParameterException, MissingParameterException, 
    OperationFailedException, PermissionDeniedException {

        return getNextDecorator().getMilestoneIdsByType(milestoneTypeKey, context);
    }

    @Override
    public List<MilestoneInfo> getMilestonesForAtp(String atpKey, ContextInfo context)
    throws InvalidParameterException, MissingParameterException, 
    OperationFailedException, PermissionDeniedException {

        return getNextDecorator().getMilestonesForAtp(atpKey, context);
    }

    @Override
    public List<MilestoneInfo> getMilestonesByDatesForAtp(@WebParam(name = "atpKey") String atpKey, @WebParam(name = "startDate") Date startDate, @WebParam(name = "endDate") Date endDate, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getMilestonesByDatesForAtp(atpKey,startDate,endDate,contextInfo);
    }

    @Override
    public List<MilestoneInfo> getMilestonesByTypeForAtp(@WebParam(name = "atpKey") String atpKey, @WebParam(name = "milestoneTypeKey") String milestoneTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getMilestonesByTypeForAtp(atpKey,milestoneTypeKey,contextInfo);
    }

    @Override
    public List<MilestoneInfo> getMilestonesByDates(Date startDate, Date endDate, ContextInfo context)
    throws InvalidParameterException, MissingParameterException,
    OperationFailedException, PermissionDeniedException {

        return getNextDecorator().getMilestonesByDates(startDate, endDate, context);
    }

    @Override
    public List<String> searchForAtpKeys(QueryByCriteria criteria, ContextInfo context) 
    throws InvalidParameterException, MissingParameterException, 
    OperationFailedException, PermissionDeniedException {

        return getNextDecorator().searchForAtpKeys(criteria, context);
    }

    @Override
    public List<AtpInfo> searchForAtps(QueryByCriteria criteria, ContextInfo context) 
    throws InvalidParameterException, MissingParameterException, 
    OperationFailedException, PermissionDeniedException {

        return getNextDecorator().searchForAtps(criteria, context);
    }

    @Override
    public List<ValidationResultInfo> validateAtp(String validationType, String atpTypeKey,AtpInfo atpInfo, ContextInfo context)
    throws DoesNotExistException, InvalidParameterException, 
    MissingParameterException, OperationFailedException, PermissionDeniedException {

        return getNextDecorator().validateAtp(validationType, atpTypeKey, atpInfo, context);
    }

    @Override
    public AtpInfo createAtp(String atpKey, AtpInfo atpInfo, ContextInfo context)
    throws AlreadyExistsException, DataValidationErrorException,
    InvalidParameterException, MissingParameterException,
    OperationFailedException, PermissionDeniedException {

        return getNextDecorator().createAtp(atpKey, atpInfo, context);
    }

    @Override
    public AtpInfo updateAtp(String atpKey, AtpInfo atpInfo, ContextInfo context)
    throws DataValidationErrorException, DoesNotExistException,
    InvalidParameterException, MissingParameterException,
    OperationFailedException, PermissionDeniedException,
    VersionMismatchException, ReadOnlyException {

        return getNextDecorator().updateAtp(atpKey, atpInfo, context);
    }

    @Override
    public StatusInfo deleteAtp(String atpKey, ContextInfo context)
    throws DoesNotExistException, InvalidParameterException,
    MissingParameterException, OperationFailedException,
    PermissionDeniedException {

        return getNextDecorator().deleteAtp(atpKey, context);
    }

    @Override
    public List<String> searchForMilestoneIds(QueryByCriteria criteria, ContextInfo context)
    throws InvalidParameterException, MissingParameterException, 
    OperationFailedException, PermissionDeniedException {

        return getNextDecorator().searchForMilestoneIds(criteria, context);
    }

    @Override
    public List<MilestoneInfo> searchForMilestones(QueryByCriteria criteria, ContextInfo context) 
    throws InvalidParameterException, MissingParameterException, 
    OperationFailedException, PermissionDeniedException {

        return getNextDecorator().searchForMilestones(criteria, context);
    }

    @Override
    public List<ValidationResultInfo> validateMilestone(String validationType, MilestoneInfo milestoneInfo, ContextInfo context)
    throws DoesNotExistException, InvalidParameterException,
    MissingParameterException, OperationFailedException,PermissionDeniedException {

        return getNextDecorator().validateMilestone(validationType, milestoneInfo, context);
    }

    @Override
    public MilestoneInfo createMilestone(MilestoneInfo milestoneInfo, ContextInfo context)
    throws DataValidationErrorException,
    InvalidParameterException, MissingParameterException,
    OperationFailedException, PermissionDeniedException, ReadOnlyException {

        return getNextDecorator().createMilestone(milestoneInfo, context);
    }

    @Override
    public MilestoneInfo updateMilestone(String milestoneKey, MilestoneInfo milestoneInfo, ContextInfo context)
    throws DataValidationErrorException, DoesNotExistException,
    InvalidParameterException, MissingParameterException,
    OperationFailedException, PermissionDeniedException,
    VersionMismatchException, ReadOnlyException {

        return getNextDecorator().updateMilestone(milestoneKey, milestoneInfo, context);
    }

    @Override
    public StatusInfo deleteMilestone(String milestoneKey, ContextInfo context)
    throws DoesNotExistException, InvalidParameterException,
    MissingParameterException, OperationFailedException,
    PermissionDeniedException {

        return getNextDecorator().deleteMilestone(milestoneKey, context);
    }

    @Override
    public StatusInfo addMilestoneToAtp(@WebParam(name = "milestoneId") String milestoneId, @WebParam(name = "atpKey") String atpKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws AlreadyExistsException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().addMilestoneToAtp(milestoneId,atpKey,contextInfo);
    }

    @Override
    public StatusInfo removeMilestoneFromAtp(@WebParam(name = "milestoneId") String milestoneId, @WebParam(name = "atpKey") String atpKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().removeMilestoneFromAtp(milestoneId,atpKey,contextInfo);
    }

    @Override
    public AtpAtpRelationInfo getAtpAtpRelation(String atpAtpRelationId, ContextInfo context) 
    throws DoesNotExistException,
    InvalidParameterException, MissingParameterException,
    OperationFailedException, PermissionDeniedException {

        return getNextDecorator().getAtpAtpRelation(atpAtpRelationId, context);
    }

    @Override
    public List<AtpAtpRelationInfo> getAtpAtpRelationsByIds(List<String> atpAtpRelationIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getAtpAtpRelationsByIds(atpAtpRelationIds,contextInfo);
    }

    @Override
    public List<String> getAtpAtpRelationIdsByType(String atpAtpRelationTypeKey, ContextInfo context)
    throws InvalidParameterException, MissingParameterException,
    OperationFailedException, PermissionDeniedException {

        return getNextDecorator().getAtpAtpRelationIdsByType(atpAtpRelationTypeKey, context);
    }

    @Override
    public List<AtpAtpRelationInfo> getAtpAtpRelationsByAtp(String atpKey, ContextInfo context) 
    throws InvalidParameterException,
    MissingParameterException, OperationFailedException, 
    PermissionDeniedException {

        return getNextDecorator().getAtpAtpRelationsByAtp(atpKey, context);
    }

    @Override
    public List<AtpAtpRelationInfo> getAtpAtpRelationsByAtps(@WebParam(name = "atpKey") String atpKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getAtpAtpRelationsByAtps(atpKey,contextInfo);
    }

    @Override
    public List<String> searchForAtpAtpRelationIds(QueryByCriteria criteria, ContextInfo context) 
    throws InvalidParameterException, MissingParameterException, 
    OperationFailedException, PermissionDeniedException {

        return getNextDecorator().searchForAtpAtpRelationIds(criteria, context);
    }

    @Override
    public List<AtpAtpRelationInfo> searchForAtpAtpRelations(QueryByCriteria criteria, ContextInfo context) 
    throws InvalidParameterException, MissingParameterException, 
    OperationFailedException, PermissionDeniedException {

        return getNextDecorator().searchForAtpAtpRelations(criteria, context);
    }

    @Override
    public List<ValidationResultInfo> validateAtpAtpRelation(String validationTypeKey, String atpKey, String atpPeerKey,
                                                             String atpAtpRelationTypeKey, AtpAtpRelationInfo atpAtpRelationInfo,
                                                             ContextInfo contextInfo)
    throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException{

        return getNextDecorator().validateAtpAtpRelation(validationTypeKey, atpKey, atpPeerKey, atpAtpRelationTypeKey, atpAtpRelationInfo, contextInfo);
    }

    @Override
    public AtpAtpRelationInfo createAtpAtpRelation(String atpKey, String atpPeerKey,
                                                   AtpAtpRelationInfo atpAtpRelationInfo, ContextInfo contextInfo)
    throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException,
           OperationFailedException, PermissionDeniedException, ReadOnlyException {

        return getNextDecorator().createAtpAtpRelation(atpKey, atpPeerKey, atpAtpRelationInfo, contextInfo);
    }

    @Override
    public AtpAtpRelationInfo updateAtpAtpRelation(String atpAtpRelationId, AtpAtpRelationInfo atpAtpRelationInfo, ContextInfo contextInfo)
    throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
           MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException{
        return getNextDecorator().updateAtpAtpRelation(atpAtpRelationId, atpAtpRelationInfo, contextInfo);
    }

    @Override
    public StatusInfo deleteAtpAtpRelation(String atpAtpRelationId, ContextInfo context) 
    throws DoesNotExistException, InvalidParameterException, 
    MissingParameterException, OperationFailedException, 
    PermissionDeniedException {

        return getNextDecorator().deleteAtpAtpRelation(atpAtpRelationId, context);
    }

    @Override
    public List<AtpAtpRelationInfo> getAtpAtpRelationsByTypeAndAtp(String atpKey, String relationType, ContextInfo context)
    throws InvalidParameterException,
    MissingParameterException, OperationFailedException,
    PermissionDeniedException {
        return getNextDecorator().getAtpAtpRelationsByTypeAndAtp(atpKey, relationType, context);
    }

    @Override
    public List<AtpAtpRelationInfo> getAtpAtpRelationsByTypeAndAtp(@WebParam(name = "atpRelationTypeKey") String atpRelationTypeKey, @WebParam(name = "atpKey") String atpKey, @WebParam(name = "atpPeerKey") String atpPeerKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getAtpAtpRelationsByTypeAndAtp(atpRelationTypeKey,atpKey,atpPeerKey,contextInfo);
    }
}
