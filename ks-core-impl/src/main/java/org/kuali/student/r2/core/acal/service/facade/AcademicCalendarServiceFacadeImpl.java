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
import org.apache.log4j.Logger;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.acal.dto.AcademicCalendarInfo;
import org.kuali.student.r2.core.acal.dto.KeyDateInfo;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.acal.service.AcademicCalendarService;
import org.kuali.student.r2.core.constants.AtpServiceConstants;

import javax.annotation.Resource;
import java.util.ArrayList;
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
    private static final Logger LOGGER = Logger.getLogger(AcademicCalendarServiceFacadeImpl.class);

    @Resource(name="acalService")
    private AcademicCalendarService acalService;

    public void setAcalService(AcademicCalendarService acalService) {
        this.acalService = acalService;
    }

    @Override
    public void makeTermOfficialCascaded(String termId, ContextInfo contextInfo)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException,
                   OperationFailedException, DoesNotExistException {
        // KSENROLL-7251 Implement a new servies process ot change the state of the Academic Calendar
        // from draft to official
        TermInfo termInfo = acalService.getTerm(termId, contextInfo);
        if (AtpServiceConstants.ATP_OFFICIAL_STATE_KEY.equals(termInfo.getStateKey())) {
            // If official, then should have already cascaded.
            return;
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
        for (String id: idToCalendar.keySet()) {
            if (AtpServiceConstants.ATP_DRAFT_STATE_KEY.equals(idToCalendar.get(id).getStateKey())) {
                // Only set it if it's still draft
                acalService.changeAcademicCalendarState(id, AtpServiceConstants.ATP_OFFICIAL_STATE_KEY, contextInfo);
            }
        }
    }


    @Override
    public StatusInfo deleteTermCascaded(String termId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
                   OperationFailedException, PermissionDeniedException {
        Set<String> toDeleteTermIds = new HashSet<String>();
        StatusInfo statusInfo = new StatusInfo();

        //build the list of term/sub term ids to be deleted
        //If there is any term/sub term with offcial state, return status false and won't delete anything
        if (buildToDeleteTermIdList(termId, toDeleteTermIds, context)) {
            //delete terms and sub terms
            for (String toDeleteTermId : toDeleteTermIds) {
                //delete associated key dates
                List<KeyDateInfo> keyDateInfos = acalService.getKeyDatesForTerm(toDeleteTermId, context);
                if (keyDateInfos!=null && !keyDateInfos.isEmpty()) {
                    for (KeyDateInfo keyDateInfo : keyDateInfos) {
                        if (!acalService.deleteKeyDate(keyDateInfo.getId(), context).getIsSuccess()){
                            throw new OperationFailedException("Deleting key date failed - key date id:" + keyDateInfo.getId());
                        }
                    }
                }
                //delete term/sub term
                if (!acalService.deleteTerm(toDeleteTermId, context).getIsSuccess()){
                    throw new OperationFailedException("Deleting term failed - term id:" + toDeleteTermId);
                }
            }
            statusInfo.setSuccess(Boolean.TRUE);
            return statusInfo;
        } else {
            throw new OperationFailedException("Term can't be deleted in official state or having sub terms in official state - Term id:" + termId);
        }

    }

    @Override
    public StatusInfo deleteCalendarCascaded(String academicCalendarId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        AcademicCalendarInfo acalInfo = acalService.getAcademicCalendar(academicCalendarId, context);
        Set<String> toDeleteTermIds = new HashSet<String>(); //term ids of the terms to be deleted
        StatusInfo statusInfo = new StatusInfo();

        //if the calendar in official state, not to delete anything
        if (StringUtils.equals(acalInfo.getStateKey(), AtpServiceConstants.ATP_OFFICIAL_STATE_KEY)) {
            throw new OperationFailedException("Calendar can't be deleted in official state - Calendar id:" + academicCalendarId);
        } else {
            List<TermInfo> termInfos = acalService.getTermsForAcademicCalendar(acalInfo.getId(), context);

            //iterate terms/sub terms of the given calendar to build the list of term ids to be deleted
            //If there is any term/sub term with offcial state, throw exception
            for (TermInfo termInfo : termInfos) {
                Set<String> toDeleteTermIdsPerTerm = new HashSet<String>();
                if (buildToDeleteTermIdList(termInfo.getId(), toDeleteTermIdsPerTerm, context)) {
                    toDeleteTermIds.addAll(toDeleteTermIdsPerTerm);
                } else {
                    throw new OperationFailedException("Calendar can't be deleted with term(s) in official state - Calendar id:" + academicCalendarId);
                }
            }

            //delete terms and sub terms
            for (String toDeleteTermId : toDeleteTermIds) {
                //delete associated key dates
                List<KeyDateInfo> keyDateInfos = acalService.getKeyDatesForTerm(toDeleteTermId, context);
                if (keyDateInfos!=null && !keyDateInfos.isEmpty()) {
                    for (KeyDateInfo keyDateInfo : keyDateInfos) {
                        if (!acalService.deleteKeyDate(keyDateInfo.getId(), context).getIsSuccess()){
                            throw new OperationFailedException("Deleting key date failed - key date id:" + keyDateInfo.getId());
                        }
                    }
                }

                if (!acalService.deleteTerm(toDeleteTermId, context).getIsSuccess()){
                    throw new OperationFailedException("Deleting term failed - term id:" + toDeleteTermId);
                }
            }

            //delete calendar
            if (!acalService.deleteAcademicCalendar(academicCalendarId, context).getIsSuccess()) {
                throw new OperationFailedException("Deleting academic calendar failed - academic calendar id:" + academicCalendarId);
            }
            statusInfo.setSuccess(Boolean.TRUE);
            return statusInfo;
        }
    }


    /**
     * build the list of term and sub term ids for the given term recursively. The state of the term and all the sub terms
     * will be checked. If any term/sub term id(s) have the state official, no term or sub term can't be deleted
     * @param termId: id of term on the top level of the hierarchy
     * @param toDeleteTermIds: Set of all the term and sub term ids to be deleted
     * @param context
     * @return true: none of the term/sub term has the official state (they can be deleted)
     *
     */
    private boolean buildToDeleteTermIdList (String termId, Set<String> toDeleteTermIds, ContextInfo context) {
        try {
            TermInfo termInfo = acalService.getTerm(termId, context);

            //if the current term with the state offcial, return false otherwise add it to the to be deleted set
            if (StringUtils.equals(termInfo.getStateKey(), AtpServiceConstants.ATP_OFFICIAL_STATE_KEY)) {
                return false;
            } else {
                toDeleteTermIds.add(termInfo.getId());
            }

            //retrieve the sub terms of the current term.
            //if there is no sub terms, it means we've already reached the bottom, return true.
            // otherwise, call the method recursively for the sub term(s)
            List<TermInfo> subTermInfos = acalService.getIncludedTermsInTerm(termId, context);
            if (subTermInfos == null || subTermInfos.isEmpty()) {
                return true;
            } else {
                for (TermInfo subTermInfo : subTermInfos) {
                    if (!buildToDeleteTermIdList(subTermInfo.getId(), toDeleteTermIds, context)) {
                        return false;
                    }
                }
            }

            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean validateTerm(String termId, ContextInfo context) throws PermissionDeniedException, MissingParameterException, InvalidParameterException, OperationFailedException, DoesNotExistException {
        Set<String> processedTermIds = new HashSet<String>();
        processedTermIds.add(termId);
        return _validateTermRecursive(termId, processedTermIds, context);
    }

    private boolean _validateTermRecursive(String termId, Set<String> processedTermIds, ContextInfo context) throws PermissionDeniedException, MissingParameterException, InvalidParameterException, OperationFailedException, DoesNotExistException {
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
}
