package org.kuali.student.r2.core.class1.atp.service.decorators;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.atp.dto.AtpAtpRelationInfo;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.dto.MilestoneInfo;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;

import javax.jws.WebParam;
import java.util.Date;
import java.util.List;

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
    public AtpInfo getAtp(String atpId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        return getNextDecorator().getAtp(atpId, context);
    }

    @Override
    public List<AtpInfo> getAtpsByIds(List<String> atpIds, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        return getNextDecorator().getAtpsByIds(atpIds, context);
    }

    @Override
    public List<String> getAtpIdsByType(String atpTypeKey, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        return getNextDecorator().getAtpIdsByType(atpTypeKey, context);
    }

    @Override
    public List<AtpInfo> getAtpsByDate(Date searchDate, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        return getNextDecorator().getAtpsByDate(searchDate, context);
    }

    @Override
    public List<AtpInfo> getAtpsByDateAndType(Date searchDate, String searchTypeKey, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        return getNextDecorator().getAtpsByDateAndType(searchDate, searchTypeKey, context);
    }

    @Override
    public List<AtpInfo> getAtpsByDates(Date startDate, Date endDate, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        return getNextDecorator().getAtpsByDates(startDate, endDate, context);
    }

    @Override
    public List<AtpInfo> getAtpsByDatesAndType(Date startDate, Date endDate, String searchTypeKey, ContextInfo context) throws InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {

        return getNextDecorator().getAtpsByDatesAndType(startDate, endDate, searchTypeKey, context);
    }

    @Override
    public List<AtpInfo> getAtpsByStartDateRange(Date searchDateRangeStart, Date searchDateRangeEnd, ContextInfo context) throws InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {

        return getNextDecorator().getAtpsByStartDateRange(searchDateRangeStart, searchDateRangeEnd, context);
    }

    @Override
    public List<AtpInfo> getAtpsByStartDateRangeAndType(Date searchDateRangeStart, Date searchDateRangeEnd, String searchTypeKey, ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {

        return getNextDecorator().getAtpsByStartDateRangeAndType(searchDateRangeStart, searchDateRangeEnd, searchTypeKey, context);
    }

    @Override
    public List<AtpInfo> getAtpsByCode(String code, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getAtpsByCode(code, contextInfo);
    }

    @Override
    public List<AtpInfo> getATPsForMilestone(String milestoneId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getATPsForMilestone(milestoneId, contextInfo);
    }

    @Override
    public MilestoneInfo getMilestone(String milestoneId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        return getNextDecorator().getMilestone(milestoneId, context);
    }

    @Override
    public List<MilestoneInfo> getMilestonesByIds(List<String> milestoneIds, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {

        return getNextDecorator().getMilestonesByIds(milestoneIds, context);
    }

    @Override
    public List<String> getMilestoneIdsByType(String milestoneTypeKey, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        return getNextDecorator().getMilestoneIdsByType(milestoneTypeKey, context);
    }

    @Override
    public List<MilestoneInfo> getMilestonesForAtp(String atpId, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        return getNextDecorator().getMilestonesForAtp(atpId, context);
    }

    @Override
    public List<MilestoneInfo> getMilestonesByDatesForAtp(@WebParam(name = "atpId") String atpId, @WebParam(name = "startDate") Date startDate, @WebParam(name = "endDate") Date endDate,
                                                          @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getMilestonesByDatesForAtp(atpId, startDate, endDate, contextInfo);
    }

    @Override
    public List<MilestoneInfo> getMilestonesByTypeForAtp(@WebParam(name = "atpId") String atpId, @WebParam(name = "milestoneTypeKey") String milestoneTypeKey,
                                                         @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getMilestonesByTypeForAtp(atpId, milestoneTypeKey, contextInfo);
    }

    @Override
    public List<MilestoneInfo> getMilestonesByDates(Date startDate, Date endDate, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        return getNextDecorator().getMilestonesByDates(startDate, endDate, context);
    }

    @Override
    public List<String> searchForAtpIds(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        return getNextDecorator().searchForAtpIds(criteria, context);
    }

    @Override
    public List<AtpInfo> searchForAtps(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        return getNextDecorator().searchForAtps(criteria, context);
    }

    @Override
    public List<ValidationResultInfo> validateAtp(String validationType, String atpTypeKey, AtpInfo atpInfo, ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {

        return getNextDecorator().validateAtp(validationType, atpTypeKey, atpInfo, context);
    }

    @Override
    public AtpInfo createAtp(String atpTypeKey, AtpInfo atpInfo, ContextInfo context) throws DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException {

        return getNextDecorator().createAtp(atpTypeKey, atpInfo, context);
    }

    @Override
    public AtpInfo updateAtp(String atpId, AtpInfo atpInfo, ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, VersionMismatchException, ReadOnlyException {

        return getNextDecorator().updateAtp(atpId, atpInfo, context);
    }

    @Override
    public StatusInfo deleteAtp(String atpId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        return getNextDecorator().deleteAtp(atpId, context);
    }

    @Override
    public List<MilestoneInfo> getImpactedMilestones(String milestoneId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getImpactedMilestones(milestoneId, contextInfo);
    }

    @Override
    public List<String> searchForMilestoneIds(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        return getNextDecorator().searchForMilestoneIds(criteria, context);
    }

    @Override
    public List<MilestoneInfo> searchForMilestones(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        return getNextDecorator().searchForMilestones(criteria, context);
    }

    @Override
    public List<ValidationResultInfo> validateMilestone(String validationType, MilestoneInfo milestoneInfo, ContextInfo context) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {

        return getNextDecorator().validateMilestone(validationType, milestoneInfo, context);
    }

    @Override
    public MilestoneInfo createMilestone(String milestoneTypeKey, MilestoneInfo milestoneInfo, ContextInfo context) throws DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException {

        return getNextDecorator().createMilestone(milestoneTypeKey, milestoneInfo, context);
    }

    @Override
    public MilestoneInfo updateMilestone(String milestoneId, MilestoneInfo milestoneInfo, ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException, ReadOnlyException {

        return getNextDecorator().updateMilestone(milestoneId, milestoneInfo, context);
    }

    @Override
    public StatusInfo deleteMilestone(String milestoneId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        return getNextDecorator().deleteMilestone(milestoneId, context);
    }

    @Override
    public MilestoneInfo calculateMilestone(String milestoneId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().calculateMilestone(milestoneId, contextInfo);
    }

    @Override
    public StatusInfo addMilestoneToAtp(@WebParam(name = "milestoneId") String milestoneId, @WebParam(name = "atpId") String atpId, @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws AlreadyExistsException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().addMilestoneToAtp(milestoneId, atpId, contextInfo);
    }

    @Override
    public StatusInfo removeMilestoneFromAtp(@WebParam(name = "milestoneId") String milestoneId, @WebParam(name = "atpId") String atpId, @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().removeMilestoneFromAtp(milestoneId, atpId, contextInfo);
    }

    @Override
    public AtpAtpRelationInfo getAtpAtpRelation(String atpAtpRelationId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {

        return getNextDecorator().getAtpAtpRelation(atpAtpRelationId, context);
    }

    @Override
    public List<AtpAtpRelationInfo> getAtpAtpRelationsByIds(List<String> atpAtpRelationIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getAtpAtpRelationsByIds(atpAtpRelationIds, contextInfo);
    }

    @Override
    public List<AtpAtpRelationInfo> getAtpAtpRelationsByAtp(String atpId, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        return getNextDecorator().getAtpAtpRelationsByAtp(atpId, context);
    }

    @Override
    public List<AtpAtpRelationInfo> getAtpAtpRelationsByAtps(@WebParam(name = "atpId") String atpId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getAtpAtpRelationsByAtps(atpId, contextInfo);
    }

    @Override
    public AtpAtpRelationInfo createAtpAtpRelation(String atpId,
                                                   String relatedAtpId,
                                                   String atpAtpRelationTypeKey,
                                                   AtpAtpRelationInfo atpAtpRelationInfo,
                                                   ContextInfo contextInfo)
            throws DoesNotExistException,
            DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException {

        return getNextDecorator().createAtpAtpRelation(atpId, relatedAtpId, atpAtpRelationTypeKey, atpAtpRelationInfo,
                contextInfo);
    }

    @Override
    public AtpAtpRelationInfo updateAtpAtpRelation(String atpAtpRelationId, AtpAtpRelationInfo atpAtpRelationInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return getNextDecorator().updateAtpAtpRelation(atpAtpRelationId, atpAtpRelationInfo, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateAtpAtpRelation(String validationTypeKey, String atpId, String atpPeerKey, String atpAtpRelationTypeKey, AtpAtpRelationInfo atpAtpRelationInfo,
                                                             ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        return getNextDecorator().validateAtpAtpRelation(validationTypeKey, atpId, atpPeerKey, atpAtpRelationTypeKey, atpAtpRelationInfo, contextInfo);
    }

    @Override
    public List<AtpAtpRelationInfo> getAtpAtpRelationsByTypeAndAtp(String atpId, String relationType, ContextInfo context) throws InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getAtpAtpRelationsByTypeAndAtp(atpId, relationType, context);
    }

    @Override
    public List<String> getAtpAtpRelationIdsByType(String atpAtpRelationTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().getAtpAtpRelationIdsByType(atpAtpRelationTypeKey, contextInfo);
    }

    @Override
    public List<AtpAtpRelationInfo> searchForAtpAtpRelations(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().searchForAtpAtpRelations(criteria, contextInfo);
    }

    @Override
    public StatusInfo deleteAtpAtpRelation(String atpAtpRelationId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteAtpAtpRelation(atpAtpRelationId, contextInfo);
    }

    @Override
    public List<String> searchForAtpAtpRelationIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().searchForAtpAtpRelationIds(criteria, contextInfo);
    }

    @Override
    public List<TypeInfo> getSearchTypes(ContextInfo contextInfo) throws OperationFailedException, InvalidParameterException, MissingParameterException {
        return getNextDecorator().getSearchTypes(contextInfo);
    }

    @Override
    public TypeInfo getSearchType(@WebParam(name = "searchTypeKey") String searchTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return getNextDecorator().getSearchType(searchTypeKey, contextInfo);
    }

    @Override
    public List<TypeInfo> getSearchTypesByResult(@WebParam(name = "searchResultTypeKey") String searchResultTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return getNextDecorator().getSearchTypesByResult(searchResultTypeKey, contextInfo);
    }

    @Override
    public List<TypeInfo> getSearchTypesByCriteria(@WebParam(name = "searchCriteriaTypeKey") String searchCriteriaTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return getNextDecorator().getSearchTypesByCriteria(searchCriteriaTypeKey, contextInfo);
    }

    @Override
    public List<TypeInfo> getSearchResultTypes(ContextInfo contextInfo) throws OperationFailedException, InvalidParameterException, MissingParameterException {
        return getNextDecorator().getSearchResultTypes(contextInfo);
    }

    @Override
    public List<TypeInfo> getSearchCriteriaTypes(ContextInfo contextInfo) throws OperationFailedException, InvalidParameterException, MissingParameterException {
        return getNextDecorator().getSearchCriteriaTypes(contextInfo);
    }

    @Override
    public SearchResultInfo search(SearchRequestInfo searchRequest, ContextInfo contextInfo) throws MissingParameterException, PermissionDeniedException {
        try {
            return getNextDecorator().search(searchRequest, contextInfo);
        } catch (OperationFailedException e) {
            throw new MissingParameterException("Not a missing Parameter but an OperationFailedException performing search.  SearchManager doesn't support OperationFailedException on search method: "+e.getMessage()+ " : " + e.getStackTrace().toString());
        }
    }
}
