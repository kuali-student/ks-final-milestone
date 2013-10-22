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

import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.core.acal.dto.ExamPeriodInfo;
import org.kuali.student.r2.core.acal.dto.HolidayInfo;

import java.util.List;

/**
 * The facade represents a layer that sits on top of AcademicCalendarServiceImpl to provide additional methods
 * not supported by the services.  It can also serve as an experimental testbed for new service methods.
 *
 * @author Kuali Student Team
 */
public interface AcademicCalendarServiceFacade {
    /**
     * Changes the state of the term and any ancestor term official, as well as the calendar.
     * KSENROLL-7251
     * @param termId The term whose state is made official
     * @param contextInfo The context info
     */
    StatusInfo makeTermOfficialCascaded(String termId, ContextInfo contextInfo)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException,
            OperationFailedException, DoesNotExistException;

    /**
     * Cascaded version of delete. This method will delete the given term and all its sub terms (if any sub term also has
     * sub terms, they will be deleted as well ) only if all the term/sub terms have the draft state. As long as there is
     * one term or sub term with the official state, none of the term or sub term in the tree will be deleted.
     * @param termId The term ID of the term to be deleted
     * @param context The context info
     * @return the status of the operation. This must always be true.
     */
    StatusInfo deleteTermCascaded(String termId, ContextInfo context) throws
        DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
        PermissionDeniedException;

    /**
     * deleteCalendarCascaded
     * @param academicCalendarId ID for academic calendar
     * @param context The context info
     */
    StatusInfo deleteCalendarCascaded(String academicCalendarId, ContextInfo context) throws
            DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException;

    /**
     * Determines if a term and its descendants are valid.  The rule for validity is if a term is draft,
     * its descendant term tree must be draft.  If any terms in the descendant tree is official, then it's
     * not a valid term (tree).  In particular, this code attempts to find parent-child where parent state
     * is draft and whose child state  is official.
     * @param termId The term to verify
     * @param context The context info
     * @return true, if term and descendants are valid
     */
    boolean validateTerm(String termId, ContextInfo context) throws PermissionDeniedException,
            MissingParameterException, InvalidParameterException, OperationFailedException, DoesNotExistException;

    /**
     * Determines if a calendar and its descendants are valid.  The rule for validity is if a calendar is draft,
     * its descendant tree must be draft.  If any terms in the descendant tree is official, then it's
     * not a valid.  In particular, this code attempts to find parent-child where parent state is draft and
     * whose child state is official.
     * @param acalId The term to verify
     * @param context The context info
     * @return true, if term and descendants are valid
     */
    boolean validateCalendar(String acalId, ContextInfo context) throws PermissionDeniedException,
            MissingParameterException, InvalidParameterException, OperationFailedException, DoesNotExistException;



    /**
     * Returns related ATP ids for the given parent ATP id
     * @param termId the parent atp Id
     * @param relationTypeKey the relation type key of the ATP ATP relationship
     * @param context call context
     * @return related ATP ids for the given parent ATP id
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public List<String> getRelatedAtpIdsForParentAtpIdAndRelationType(String termId, String relationTypeKey, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException;


    /**
     * Returns (non subterm) term ids for a given academic calendar
     * @param acalId
     * @param context
     * @return (non subterm) terms for a given academic calendar
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public List<String> getTermIdsForAcademicCalendar(String acalId, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException;

    /**
     * Returns (non subterm) term ids for a given academic calendar
     * @param examPeriodTypeKey the type key of the examPeriod to be created
     * @param termTypeKeyList the type key list of the terms that the examPeriod is associated with
     * @param examPeriodInfo the examPeriod DTO to be created
     * @param context call context
     * @return the ExamPeriodInfo that has been created
     * @throws DataValidationErrorException
     * @throws DoesNotExistException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     * @throws ReadOnlyException
     */
    public ExamPeriodInfo addExamPeriod (String examPeriodTypeKey, List<String> termTypeKeyList, ExamPeriodInfo examPeriodInfo, ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException;

}
