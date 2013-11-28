/**
 * Copyright 2013 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 *
 * Created by Charles on 6/10/13
 */
package org.kuali.student.r2.core.acal.service.facade;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateMidnight;
import org.joda.time.DateTimeConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.core.acal.dto.AcademicCalendarInfo;
import org.kuali.student.r2.core.acal.dto.ExamPeriodInfo;
import org.kuali.student.r2.core.acal.dto.HolidayInfo;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.acal.service.AcademicCalendarService;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.class1.type.dto.TypeTypeRelationInfo;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.core.constants.AcademicCalendarServiceConstants;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.kuali.student.r2.core.constants.TypeServiceConstants;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Provides additional functionality on top of the Acal Service.
 *
 * @author Kuali Student Team
 */
public class AcademicCalendarServiceFacadeImpl implements AcademicCalendarServiceFacade {

    @Resource(name="acalService")
    private AcademicCalendarService acalService;

    @Resource(name="atpService")
    private AtpService atpService;

    @Resource(name="typeService")
    private TypeService typeService;

    public void setAcalService(AcademicCalendarService acalService) {
        this.acalService = acalService;
    }

    public AcademicCalendarService getAcalService() {
        return acalService;
    }

    public void setAtpService(AtpService atpService) {
        this.atpService = atpService;
    }

    public AtpService getAtpService() {
        return atpService;
    }

    public void setTypeService(TypeService typeService) {
        this.typeService = typeService;
    }

    public TypeService getTypeService() {
        return typeService;
    }

    @Override
    public StatusInfo makeTermOfficialCascaded(String termId, ContextInfo contextInfo)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException,
            OperationFailedException, DoesNotExistException {
        StatusInfo statusInfo = new StatusInfo();

        // KSENROLL-7251 Implement a new servies process ot change the state of the Academic Calendar
        // from draft to official
        TermInfo termInfo = acalService.getTerm(termId, contextInfo);
        if (AtpServiceConstants.ATP_OFFICIAL_STATE_KEY.equals(termInfo.getStateKey())) {
            // If official, then should have already cascaded.
            statusInfo.setSuccess(Boolean.TRUE);
            return statusInfo;
        }
        // Assumes state propagation not wired in yet.
        Map<String, TermInfo> termIdToTermInfoProcessed = new HashMap<String, TermInfo>();
        Map<String, TermInfo> termIdToTermInfoToBeProcessed = new HashMap<String, TermInfo>();
        Set<String> parentTermIds = new HashSet<String>();
        termIdToTermInfoToBeProcessed.put(termId, termInfo);  // Put initial term
        while (!termIdToTermInfoToBeProcessed.keySet().isEmpty()) {
            String nextTermId = termIdToTermInfoToBeProcessed.keySet().iterator().next();
            TermInfo nextTerm = termIdToTermInfoToBeProcessed.get(nextTermId);
            if (termIdToTermInfoProcessed.keySet().contains(nextTermId)) {
                // Skip over ones we've seen
                continue;
            }
            // Change the state
            acalService.changeTermState(nextTermId, AtpServiceConstants.ATP_OFFICIAL_STATE_KEY, contextInfo);
            // Change the state of the associated exam period
            changeExamPeriodStateByTermId(nextTermId, AtpServiceConstants.ATP_OFFICIAL_STATE_KEY, contextInfo);
            termIdToTermInfoProcessed.put(nextTermId, nextTerm); // Add to processed
            termIdToTermInfoToBeProcessed.remove(nextTermId); // No longer needs processing, so remove
            // Now visit all parents
            List<TermInfo> terms = acalService.getContainingTerms(nextTermId, contextInfo);
            if (terms.isEmpty()) {
                // Assume only parent terms are connected to calendars
                // Given a tree like structure, there should only ever be one parentTermId in the list
                parentTermIds.add(nextTermId);
            } else {
                for (TermInfo term: terms) {
                    if (!termIdToTermInfoProcessed.keySet().contains(term.getId()) &&
                            AtpServiceConstants.ATP_DRAFT_STATE_KEY.equals(term.getStateKey())) {
                        // Only add if still draft and not yet processed
                        termIdToTermInfoToBeProcessed.put(term.getId(), term);
                    }
                }
            }
        }
        // Access calendar
        Map<String, AcademicCalendarInfo> idToCalendar = new HashMap<String, AcademicCalendarInfo>();
        for (String parentTermId: parentTermIds) {
            List<AcademicCalendarInfo> cals = acalService.getAcademicCalendarsForTerm(parentTermId, contextInfo);
            for (AcademicCalendarInfo cal: cals) {
                idToCalendar.put(cal.getId(), cal);
            }
        }
        // Now iterate over all calendars and make them official
        for (Map.Entry<String, AcademicCalendarInfo> entry: idToCalendar.entrySet()) {
            if (AtpServiceConstants.ATP_DRAFT_STATE_KEY.equals(entry.getValue().getStateKey())) {
                // Only set it if it's still draft
                acalService.changeAcademicCalendarState(entry.getKey(), AtpServiceConstants.ATP_OFFICIAL_STATE_KEY, contextInfo);
            }
        }
        statusInfo.setSuccess(Boolean.TRUE);
        return statusInfo;
    }

    @Override
    @Transactional(rollbackFor = {Throwable.class})
    public StatusInfo deleteCalendarCascaded(String academicCalendarId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        AcademicCalendarInfo acalInfo = acalService.getAcademicCalendar(academicCalendarId, context);
        StatusInfo statusInfo = new StatusInfo();

        //if the calendar in official state, not to delete anything
        if (StringUtils.equals(acalInfo.getStateKey(), AtpServiceConstants.ATP_OFFICIAL_STATE_KEY)) {
            throw new OperationFailedException("Calendar of the state official can't be deleted - Calendar id:" + academicCalendarId);
        }
        List<String> termIds = getTermIdsForAcademicCalendar(academicCalendarId, context);
        if (termIds!=null && !termIds.isEmpty()) {
            for (String termId : termIds) {
                deleteTermCascaded(termId, context);
            }
        }

        //delete calendar
        acalService.deleteAcademicCalendar(academicCalendarId, context);
        statusInfo.setSuccess(Boolean.TRUE);
        return statusInfo;

    }


    @Override
    @Transactional(rollbackFor = {Throwable.class})
    public StatusInfo deleteTermCascaded(String termId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        StatusInfo statusInfo = new StatusInfo();

        //retrieve all the sub term ids of the give term
        List<String> subTermIds = getRelatedAtpIdsForParentAtpIdAndRelationType(termId, AtpServiceConstants.ATP_ATP_RELATION_INCLUDES_TYPE_KEY, context);
        if (subTermIds!=null && !subTermIds.isEmpty()) {
            for (String subTermId : subTermIds) {
                deleteTermCascaded(subTermId, context);
            }
        }

        //delete the associated keydates
        deleteKeyDatesbyTermId(termId, context);
        //delete the associated exam period
        deleteExamPeriodByTermId(termId, context);
        //delete term/subterm
        acalService.deleteTerm(termId, context);
        statusInfo.setSuccess(Boolean.TRUE);
        return statusInfo;
    }

    protected void deleteKeyDatesbyTermId (String termId, ContextInfo context) {
        try {
            List<String> keyDateIds = acalService.getKeyDateIdsForTerm(termId, context);
            if (keyDateIds!=null && !keyDateIds.isEmpty()) {
                for (String keyDateId : keyDateIds) {
                    acalService.deleteKeyDate(keyDateId, context);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    protected void deleteExamPeriodByTermId (String termId, ContextInfo context) {
        try {
            //retrieve all the exam period ids of the give term
            List<String> examPeriodIds = getRelatedAtpIdsForParentAtpIdAndRelationType(termId, AtpServiceConstants.ATP_ATP_RELATION_ASSOCIATED_TERM2EXAMPERIOD_TYPE_KEY, context);
            if (examPeriodIds!=null && !examPeriodIds.isEmpty()) {
                for (String examPeriodId : examPeriodIds) {
                    acalService.deleteExamPeriod(examPeriodId, context);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    protected void changeExamPeriodStateByTermId (String termId, String nextStateKey, ContextInfo context) {
        try {
            //retrieve all the exam period ids of the give term
            List<String> examPeriodIds = getRelatedAtpIdsForParentAtpIdAndRelationType(termId, AtpServiceConstants.ATP_ATP_RELATION_ASSOCIATED_TERM2EXAMPERIOD_TYPE_KEY, context);
            if (examPeriodIds!=null && !examPeriodIds.isEmpty()) {
                for (String examPeriodId : examPeriodIds) {
                    acalService.changeExamPeriodState(examPeriodId, nextStateKey, context);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean validateTerm(String termId, ContextInfo context)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException,
            OperationFailedException, DoesNotExistException {
        Set<String> processedTermIds = new HashSet<String>();
        processedTermIds.add(termId);
        return _validateTermRecursive(termId, processedTermIds, context);
    }

    protected boolean _validateTermRecursive(String termId, Set<String> processedTermIds, ContextInfo context) throws PermissionDeniedException, MissingParameterException, InvalidParameterException, OperationFailedException, DoesNotExistException {
        List<TermInfo> childTerms = acalService.getIncludedTermsInTerm(termId, context);
        TermInfo term = acalService.getTerm(termId, context);

        if (AtpServiceConstants.ATP_DRAFT_STATE_KEY.equals(term.getStateKey())) {
            for (TermInfo subterm: childTerms) {
                // Worth checking all subterms regardless of whether it's been processed or not
                if (AtpServiceConstants.ATP_OFFICIAL_STATE_KEY.equals(subterm.getStateKey())) {
                    return false; // Automatically false
                }
            }
        }
        // Otherwise recurse
        for (TermInfo subterm: childTerms) {
            if (processedTermIds.contains(subterm.getId())) { // To prevent accidental infinite recursion
                continue;
            }
            processedTermIds.add(subterm.getId()); // Add this to processed
            boolean result = _validateTermRecursive(subterm.getId(), processedTermIds, context);
            if (!result) {
                return result; // Validation failed, so return false
            }
        }
        // If we got here, then must be true
        return true;
    }

    @Override
    public boolean validateCalendar(String acalId, ContextInfo context) throws PermissionDeniedException, MissingParameterException, InvalidParameterException, OperationFailedException, DoesNotExistException {
        AcademicCalendarInfo cal = acalService.getAcademicCalendar(acalId, context);
        List<TermInfo> parentTerms = acalService.getTermsForAcademicCalendar(acalId, context);
        if (AtpServiceConstants.ATP_DRAFT_STATE_KEY.equals(cal.getStateKey())) {
            for (TermInfo term: parentTerms) {
                if (AtpServiceConstants.ATP_OFFICIAL_STATE_KEY.equals(term.getStateKey())) {
                    return false;
                }
            }
        }
        // If we get here, then recursively validate each term
        for (TermInfo term: parentTerms) {
            boolean result = validateTerm(term.getId(), context);
            if (!result) {
                return false;
            }
        }
        return true;
    }

    @Override
    public List<String> getRelatedAtpIdsForParentAtpIdAndRelationType(String atpId, String relationTypeKey, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
        //Perform an atp search to get the search results
        List<String> includedTermIds = new ArrayList<String>();

        SearchRequestInfo searchRequestInfo = new SearchRequestInfo("atp.search.relatedAtpIdsByAtpId");
        searchRequestInfo.addParam("atp.queryParam.parentAtpId", atpId);
        searchRequestInfo.addParam("atp.queryParam.relationType", relationTypeKey);
        SearchResultInfo results = atpService.search(searchRequestInfo, context);

        if (results!=null && !results.getRows().isEmpty()) {
            for(SearchResultRowInfo row : results.getRows()){
                for(SearchResultCellInfo cell: row.getCells()){
                    if("atp.resultColumn.relatedAtpId".equals(cell.getKey())){
                        includedTermIds.add(cell.getValue());
                    }
                }
            }
        }

        return includedTermIds;
    }

    @Override
    public List<String> getTermIdsForAcademicCalendar(String acalId, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
        //Perform an atp search to get the search results
        List<String> includedTermIds = new ArrayList<String>();

        //Get a list of term types
        List<TypeTypeRelationInfo> typeRelations = typeService.getTypeTypeRelationsByOwnerAndType(AtpServiceConstants.ATP_PARENT_TERM_GROUPING_TYPE_KEY, TypeServiceConstants.TYPE_TYPE_RELATION_GROUP_TYPE_KEY, context);
        List<String> termTypeIds = new ArrayList<String>();
        for(TypeTypeRelationInfo typeRelation:typeRelations){
            termTypeIds.add(typeRelation.getRelatedTypeKey());
        }

        SearchRequestInfo searchRequestInfo = new SearchRequestInfo("atp.search.relatedAtpIdsByAtpId");
        searchRequestInfo.addParam("atp.queryParam.parentAtpId", acalId);
        searchRequestInfo.addParam("atp.queryParam.relatedAtpTypes", termTypeIds);
        SearchResultInfo results = atpService.search(searchRequestInfo, context);

        for(SearchResultRowInfo row : results.getRows()){
            for(SearchResultCellInfo cell: row.getCells()){
                if("atp.resultColumn.relatedAtpId".equals(cell.getKey())){
                    includedTermIds.add(cell.getValue());
                }
            }
        }

        return includedTermIds;
    }

    @Override
    public ExamPeriodInfo addExamPeriod (String examPeriodTypeKey, List<String> termTypeKeyList, ExamPeriodInfo examPeriodInfo, ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        for (String termTypeKey : termTypeKeyList) {
            if (!_validateExamPeriodType(examPeriodTypeKey, termTypeKey, context)){
                throw new OperationFailedException("Exam Period type validation failed. Exam Period can't be created with the type:" + examPeriodTypeKey + ". Final Exam Period is not allowed for the selected term. (" + termTypeKey +")");
            }
        }

        return acalService.createExamPeriod(examPeriodTypeKey, examPeriodInfo, context);
    }

    // validate if the given examPeriod type is kuali.atp.type.ExamPeriod
    // also validate if the given examPeriod type is allowed by the given term type
    protected boolean _validateExamPeriodType (String examPeriodTypeKey, String termTypeKey, ContextInfo context) {
        if (!StringUtils.equals(examPeriodTypeKey, AtpServiceConstants.ATP_EXAM_PERIOD_TYPE_KEY)){
            return false;
        }

        // check allowed type type relationship
        List<TypeInfo> examPeriodTypes = new ArrayList<TypeInfo>();

        try {
            examPeriodTypes = acalService.getExamPeriodTypesForTermType(termTypeKey, context);
            if (examPeriodTypes != null && examPeriodTypes.size() > 0) {
                return checkTypeInTypes(examPeriodTypeKey, examPeriodTypes);
            } else {
                return false;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    protected boolean checkTypeInTypes(String typeKey, List<TypeInfo> types) {
        if (types != null && !types.isEmpty()) {
            for (TypeInfo type : types) {
                if (type.getKey().equals(typeKey)) {
                    return true;
                }
            }
        }

        return false;
    }
}
