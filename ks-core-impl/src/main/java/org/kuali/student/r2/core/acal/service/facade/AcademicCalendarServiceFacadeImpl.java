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

import org.apache.log4j.Logger;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.acal.dto.AcademicCalendarInfo;
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
    public void deleteTermCascaded(String termId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
                   OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("deleteTermCascaded not yet implemented");
    }

    @Override
    public void deleteCalendarCascaded(String academicCalendarKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("deleteCalendarCascaded not yet implemented");
    }
}
