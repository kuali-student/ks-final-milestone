/*
 * Copyright 2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Lic+ense is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.enrollment.coursewaitlist.service;


import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.enrollment.coursewaitlist.dto.CourseWaitListEntryInfo;
import org.kuali.student.enrollment.coursewaitlist.dto.CourseWaitListInfo;
import org.kuali.student.enrollment.coursewaitlist.dto.WaitListPositionInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;

import javax.jws.WebParam;
import java.util.List;

/**
 * The base decorator for the {@link org.kuali.student.enrollment.coursewaitlist.service.CourseWaitListService}- Other sub
 * classes of this decorator only have to override the methods to which we want
 * to add additional functionality
 *
 */
public class CourseWaitListServiceDecorator implements CourseWaitListService {

    private CourseWaitListService nextDecorator;

    public CourseWaitListService getNextDecorator() throws OperationFailedException {
        if (null == nextDecorator) {
            throw new OperationFailedException("Misconfigured application: nextDecorator is null");
        }
        return nextDecorator;
    }

    public void setNextDecorator(CourseWaitListService nextDecorator) {
        this.nextDecorator = nextDecorator;
    }

    @Override
    public CourseWaitListInfo getCourseWaitList(String courseWaitListId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getCourseWaitList(courseWaitListId, contextInfo);
    }

    @Override
    public List<CourseWaitListInfo> getCourseWaitListsByIds(List<String> courseWaitListIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getCourseWaitListsByIds(courseWaitListIds, contextInfo);
    }

    @Override
    public List<String> getCourseWaitListIdsByType(String courseWaitListTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getCourseWaitListIdsByType(courseWaitListTypeKey, contextInfo);
    }

    @Override
    public List<CourseWaitListInfo> getCourseWaitListsByActivityOffering(String activityOfferingId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getCourseWaitListsByActivityOffering(activityOfferingId, contextInfo);
    }

    @Override
    public List<CourseWaitListInfo> getCourseWaitListsByFormatOffering(String formatOfferingId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getCourseWaitListsByFormatOffering(formatOfferingId, contextInfo);
    }

    @Override
    public List<String> searchForCourseWaitListIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForCourseWaitListIds(criteria, contextInfo);
    }

    @Override
    public List<CourseWaitListInfo> searchForCourseWaitLists(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForCourseWaitLists(criteria, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateCourseWaitList(String validationTypeKey, String courseWaitListTypeKey, CourseWaitListInfo courseWaitListInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().validateCourseWaitList(validationTypeKey, courseWaitListTypeKey, courseWaitListInfo, contextInfo);
    }

    @Override
    public CourseWaitListInfo createCourseWaitList(String courseWaitListTypeKey, CourseWaitListInfo courseWaitListInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return getNextDecorator().createCourseWaitList(courseWaitListTypeKey, courseWaitListInfo, contextInfo);
    }

    @Override
    public CourseWaitListInfo updateCourseWaitList(String courseWaitListId, CourseWaitListInfo courseWaitListInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return getNextDecorator().updateCourseWaitList(courseWaitListId, courseWaitListInfo, contextInfo);
    }

    @Override
    public StatusInfo changeCourseWaitListState(String courseWaitListId, String stateKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().changeCourseWaitListState(courseWaitListId, stateKey, contextInfo);
    }

    @Override
    public StatusInfo deleteCourseWaitList(String courseWaitListId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteCourseWaitList(courseWaitListId, contextInfo);
    }

    @Override
    public CourseWaitListEntryInfo getCourseWaitListEntry(String courseWaitListEntryId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getCourseWaitListEntry(courseWaitListEntryId, contextInfo);
    }

    @Override
    public List<CourseWaitListEntryInfo> getCourseWaitListEntriesByIds(List<String> courseWaitListEntryIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getCourseWaitListEntriesByIds(courseWaitListEntryIds, contextInfo);
    }

    @Override
    public List<String> getCourseWaitListEntryIdsByType(String courseWaitListEntryTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getCourseWaitListEntryIdsByType(courseWaitListEntryTypeKey, contextInfo);
    }

    @Override
    public List<CourseWaitListEntryInfo> getCourseWaitListEntriesByStudent(String studentId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getCourseWaitListEntriesByStudent(studentId, contextInfo);
    }

    @Override
    public List<CourseWaitListEntryInfo> getCourseWaitListEntriesByCourseWaitList(String courseWaitListId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getCourseWaitListEntriesByCourseWaitList(courseWaitListId, contextInfo);
    }

    @Override
    public List<CourseWaitListEntryInfo> getCourseWaitListEntriesByCourseWaitListAndStudent(String courseWaitListId, String studentId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getCourseWaitListEntriesByCourseWaitListAndStudent(courseWaitListId, studentId, contextInfo);
    }

    @Override
    public List<String> searchForCourseWaitListEntryIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForCourseWaitListEntryIds(criteria, contextInfo);
    }

    @Override
    public List<CourseWaitListEntryInfo> searchForCourseWaitListEntries(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForCourseWaitListEntries(criteria, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateCourseWaitListEntry(String validationTypeKey, String courseWaitListId, String studentId, String courseWaitListEntryTypeKey, CourseWaitListEntryInfo courseWaitListEntryInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().validateCourseWaitListEntry(validationTypeKey, courseWaitListId, studentId, courseWaitListEntryTypeKey, courseWaitListEntryInfo, contextInfo);
    }

    @Override
    public CourseWaitListEntryInfo createCourseWaitListEntry(String courseWaitListId, String studentId, String courseWaitListEntryTypeKey, CourseWaitListEntryInfo courseWaitListEntryInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return getNextDecorator().createCourseWaitListEntry(courseWaitListId, studentId, courseWaitListEntryTypeKey, courseWaitListEntryInfo, contextInfo);
    }

    @Override
    public CourseWaitListEntryInfo updateCourseWaitListEntry(String courseWaitListEntryId, CourseWaitListEntryInfo courseWaitListEntryInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return getNextDecorator().updateCourseWaitListEntry(courseWaitListEntryId, courseWaitListEntryInfo, contextInfo);
    }

    @Override
    public StatusInfo changeCourseWaitListEntryState(String courseWaitListEntryId, String stateKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().changeCourseWaitListEntryState(courseWaitListEntryId, stateKey, contextInfo);
    }

    @Override
    public StatusInfo deleteCourseWaitListEntry(String courseWaitListEntryId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteCourseWaitListEntry(courseWaitListEntryId, contextInfo);
    }

    @Override
    public StatusInfo reorderCourseWaitListEntries(String courseWaitListId, List<String> courseWaitListEntryIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().reorderCourseWaitListEntries(courseWaitListId, courseWaitListEntryIds, contextInfo);
    }

    @Override
    public StatusInfo moveCourseWaitListEntryToPosition(String courseWaitListEntryId, Integer position, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().moveCourseWaitListEntryToPosition(courseWaitListEntryId, position, contextInfo);
    }

    @Override
    public WaitListPositionInfo getWaitListPositionForStudent(String studentId, String courseWaitListId, String activityOfferingId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getWaitListPositionForStudent(studentId, courseWaitListId, activityOfferingId, contextInfo);
    }

    @Override
    public List<CourseWaitListEntryInfo> getTopCourseWaitListEntries(String courseWaitListId, String activityOfferingId, Integer count, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getTopCourseWaitListEntries(courseWaitListId, activityOfferingId, count, contextInfo);
    }
}
