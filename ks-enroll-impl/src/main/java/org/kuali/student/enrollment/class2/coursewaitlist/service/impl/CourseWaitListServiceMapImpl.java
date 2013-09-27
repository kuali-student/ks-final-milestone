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
package org.kuali.student.enrollment.class2.coursewaitlist.service.impl;


import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.common.UUIDHelper;
import org.kuali.student.common.mock.MockService;
import org.kuali.student.enrollment.coursewaitlist.dto.CourseWaitListEntryInfo;
import org.kuali.student.enrollment.coursewaitlist.dto.CourseWaitListInfo;
import org.kuali.student.enrollment.coursewaitlist.service.CourseWaitListService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.MetaInfo;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class CourseWaitListServiceMapImpl implements MockService, CourseWaitListService
{
    // cache variable
    // The LinkedHashMap is just so the values come back in a predictable order
    private Map<String, CourseWaitListInfo> courseWaitListMap = new LinkedHashMap<String, CourseWaitListInfo>();
    private Map<String, CourseWaitListEntryInfo> courseWaitListEntryMap = new LinkedHashMap<String, CourseWaitListEntryInfo>();

    @Override
    public void clear()
    {
        this.courseWaitListMap.clear ();
        this.courseWaitListEntryMap.clear ();
    }


    @Override
    public CourseWaitListInfo getCourseWaitList(String courseWaitListId, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // GET_BY_ID
        if (!this.courseWaitListMap.containsKey(courseWaitListId)) {
            throw new DoesNotExistException(courseWaitListId);
        }
        return new CourseWaitListInfo(this.courseWaitListMap.get (courseWaitListId));
    }

    @Override
    public List<CourseWaitListInfo> getCourseWaitListsByIds(List<String> courseWaitListIds, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // GET_BY_IDS
        List<CourseWaitListInfo> list = new ArrayList<CourseWaitListInfo> ();
        for (String id: courseWaitListIds) {
            list.add (this.getCourseWaitList(id, contextInfo));
        }
        return list;
    }

    @Override
    public List<String> getCourseWaitListIdsByType(String courseWaitListTypeKey, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // GET_IDS_BY_TYPE
        List<String> list = new ArrayList<String> ();
        for (CourseWaitListInfo info: courseWaitListMap.values ()) {
            if (courseWaitListTypeKey.equals(info.getTypeKey())) {
                list.add (info.getId ());
            }
        }
        return list;
    }

    @Override
    public List<CourseWaitListInfo> getCourseWaitListsByFormatOffering(String formatOfferingId, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // GET_INFOS_BY_OTHER
        List<CourseWaitListInfo> list = new ArrayList<CourseWaitListInfo> ();
        for (CourseWaitListInfo info: courseWaitListMap.values ()) {
            if (info.getFormatOfferingIds().contains(formatOfferingId)) {
                list.add (new CourseWaitListInfo(info));
            }
        }
        return list;
    }

    @Override
    public List<CourseWaitListInfo> getCourseWaitListsByActivityOffering(String activityOfferingId, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // GET_INFOS_BY_OTHER
        List<CourseWaitListInfo> list = new ArrayList<CourseWaitListInfo> ();
        for (CourseWaitListInfo info: courseWaitListMap.values ()) {
            if (info.getActivityOfferingIds().contains(activityOfferingId)) {
                list.add (new CourseWaitListInfo(info));
            }
        }
        return list;
    }

    @Override
    public List<String> searchForCourseWaitListIds(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // UNKNOWN
        throw new OperationFailedException ("searchForCourseWaitListIds has not been implemented");
    }

    @Override
    public List<CourseWaitListInfo> searchForCourseWaitLists(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // UNKNOWN
        throw new OperationFailedException ("searchForCourseWaitLists has not been implemented");
    }

    @Override
    public List<ValidationResultInfo> validateCourseWaitList(String validationTypeKey, String courseWaitListTypeKey, CourseWaitListInfo courseWaitListInfo, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // VALIDATE
        return new ArrayList<ValidationResultInfo> ();
    }

    @Override
    public CourseWaitListInfo createCourseWaitList(String courseWaitListTypeKey, CourseWaitListInfo courseWaitListInfo, ContextInfo contextInfo)
            throws DataValidationErrorException
            ,DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
            ,ReadOnlyException
    {
        // CREATE
         CourseWaitListInfo copy = new CourseWaitListInfo(courseWaitListInfo);
        if (copy.getId() == null) {
            copy.setId(UUIDHelper.genStringUUID());
        }
        copy.setMeta(newMeta(contextInfo));
        copy.setTypeKey(courseWaitListTypeKey);
        courseWaitListMap.put(copy.getId(), copy);
        return new CourseWaitListInfo(copy);
    }

    @Override
    public CourseWaitListInfo updateCourseWaitList(String courseWaitListId, CourseWaitListInfo courseWaitListInfo, ContextInfo contextInfo)
            throws DataValidationErrorException
            ,DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
            ,ReadOnlyException
            ,VersionMismatchException
    {
        // UPDATE
        if (!courseWaitListId.equals (courseWaitListInfo.getId())) {
            throw new InvalidParameterException ("The id parameter does not match the id on the info object");
        }
        CourseWaitListInfo copy = new CourseWaitListInfo(courseWaitListInfo);
        CourseWaitListInfo old = this.getCourseWaitList(courseWaitListInfo.getId(), contextInfo);
        if (!old.getMeta().getVersionInd().equals(copy.getMeta().getVersionInd())) {
            throw new VersionMismatchException(old.getMeta().getVersionInd());
        }
        copy.setMeta(updateMeta(copy.getMeta(), contextInfo));
        this.courseWaitListMap .put(courseWaitListInfo.getId(), copy);
        return new CourseWaitListInfo(copy);
    }

    @Override
    public StatusInfo changeCourseWaitListState(String courseWaitListId, String stateKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        CourseWaitListInfo courseWaitList = getCourseWaitList(courseWaitListId, contextInfo);
        courseWaitList.setStateKey(stateKey);
        courseWaitList.setMeta(updateMeta(courseWaitList.getMeta(), contextInfo));
        this.courseWaitListMap.put(courseWaitListId, courseWaitList);
        return new StatusInfo();
    }

    @Override
    public StatusInfo deleteCourseWaitList(String courseWaitListId, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // DELETE
        if (this.courseWaitListMap.remove(courseWaitListId) == null) {
            throw new OperationFailedException(courseWaitListId);
        }
        return newStatus();
    }

    @Override
    public CourseWaitListEntryInfo getCourseWaitListEntry(String courseWaitListEntryId, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // GET_BY_ID
        if (!this.courseWaitListEntryMap.containsKey(courseWaitListEntryId)) {
            throw new DoesNotExistException(courseWaitListEntryId);
        }
        return new CourseWaitListEntryInfo(this.courseWaitListEntryMap.get (courseWaitListEntryId));
    }

    @Override
    public List<CourseWaitListEntryInfo> getCourseWaitListEntriesByIds(List<String> courseWaitListEntryIds, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // GET_BY_IDS
        List<CourseWaitListEntryInfo> list = new ArrayList<CourseWaitListEntryInfo> ();
        for (String id: courseWaitListEntryIds) {
            list.add (this.getCourseWaitListEntry(id, contextInfo));
        }
        return list;
    }

    @Override
    public List<String> getCourseWaitListEntryIdsByType(String courseWaitListEntryTypeKey, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // GET_IDS_BY_TYPE
        List<String> list = new ArrayList<String> ();
        for (CourseWaitListEntryInfo info: courseWaitListEntryMap.values ()) {
            if (courseWaitListEntryTypeKey.equals(info.getTypeKey())) {
                list.add (info.getId ());
            }
        }
        return list;
    }

    @Override
    public List<CourseWaitListEntryInfo> getCourseWaitListEntriesByStudent(String studentId, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // GET_INFOS_BY_OTHER
        List<CourseWaitListEntryInfo> list = new ArrayList<CourseWaitListEntryInfo> ();
        for (CourseWaitListEntryInfo info: courseWaitListEntryMap.values ()) {
            if (studentId.equals(info.getStudentId())) {
                list.add (new CourseWaitListEntryInfo(info));
            }
        }
        return list;
    }

    @Override
    public List<CourseWaitListEntryInfo> getCourseWaitListEntriesByCourseWaitList(String courseWaitListId, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // GET_INFOS_BY_OTHER
        List<CourseWaitListEntryInfo> list = new ArrayList<CourseWaitListEntryInfo> ();
        for (CourseWaitListEntryInfo info: courseWaitListEntryMap.values ()) {
            if (courseWaitListId.equals(info.getCourseWaitListId())) {
                list.add (new CourseWaitListEntryInfo(info));
            }
        }

        sortCourseWaitListEntries(list);

        return list;
    }

    @Override
    public List<CourseWaitListEntryInfo> getCourseWaitListEntriesByCourseWaitListAndStudent(String courseWaitListId, String studentId, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // GET_INFOS_BY_OTHER
        List<CourseWaitListEntryInfo> list = new ArrayList<CourseWaitListEntryInfo> ();
        for (CourseWaitListEntryInfo info: courseWaitListEntryMap.values ()) {
            if (courseWaitListId.equals(info.getCourseWaitListId())) {
                if (studentId.equals(info.getStudentId())) {
                    list.add (new CourseWaitListEntryInfo(info));
                }
            }
        }
        return list;
    }

    @Override
    public List<String> searchForCourseWaitListEntryIds(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // UNKNOWN
        throw new OperationFailedException ("searchForCourseWaitListEntryIds has not been implemented");
    }

    @Override
    public List<CourseWaitListEntryInfo> searchForCourseWaitListEntries(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // UNKNOWN
        throw new OperationFailedException ("searchForCourseWaitListEntries has not been implemented");
    }

    @Override
    public List<ValidationResultInfo> validateCourseWaitListEntry(String validationTypeKey, String courseWaitListId, String studentId, String courseWaitListEntryTypeKey, CourseWaitListEntryInfo courseWaitListEntryInfo, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException {
        return new ArrayList<ValidationResultInfo> ();
    }

    @Override
    public CourseWaitListEntryInfo createCourseWaitListEntry(String courseWaitListId, String studentId, String courseWaitListEntryTypeKey, CourseWaitListEntryInfo courseWaitListEntryInfo, ContextInfo contextInfo)
            throws DataValidationErrorException
            ,DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
            ,ReadOnlyException
    {
        // CREATE
        if (!courseWaitListEntryTypeKey.equals (courseWaitListEntryInfo.getTypeKey())) {
            throw new InvalidParameterException ("The type parameter does not match the type on the info object");
        }
        if(!courseWaitListId.equals(courseWaitListEntryInfo.getCourseWaitListId())) {
            throw new InvalidParameterException ("The CourseWaitList id parameter does not match the CourseWaitList Id on the info object");
        }
        if(!studentId.equals(courseWaitListEntryInfo.getStudentId())) {
            throw new InvalidParameterException ("The Student Id parameter does not match the Student Id on the info object");
        }

        List<CourseWaitListEntryInfo> entries = getCourseWaitListEntriesByCourseWaitList(courseWaitListId, contextInfo);

        CourseWaitListEntryInfo copy = new CourseWaitListEntryInfo(courseWaitListEntryInfo);
        int position;
        if(copy.getPosition() == null) {
            position = entries.size() + 1;
            copy.setPosition(position);
        } else {
            position = copy.getPosition();
            copy.setPosition(entries.size() + 1);
        }


        if (copy.getId() == null) {
            copy.setId(UUIDHelper.genStringUUID());
        }
        copy.setMeta(newMeta(contextInfo));
        courseWaitListEntryMap.put(copy.getId(), copy);

        entries.add(copy);
        moveCourseWaitListEntryToPosition(copy.getId(), entries, position, contextInfo);

        return new CourseWaitListEntryInfo(copy);
    }

    @Override
    public CourseWaitListEntryInfo updateCourseWaitListEntry(String courseWaitListEntryId, CourseWaitListEntryInfo courseWaitListEntryInfo, ContextInfo contextInfo)
            throws DataValidationErrorException
            ,DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
            ,ReadOnlyException
            ,VersionMismatchException
    {
        // UPDATE
        if (!courseWaitListEntryId.equals (courseWaitListEntryInfo.getId())) {
            throw new InvalidParameterException ("The id parameter does not match the id on the info object");
        }
        CourseWaitListEntryInfo copy = new CourseWaitListEntryInfo(courseWaitListEntryInfo);
        CourseWaitListEntryInfo old = this.getCourseWaitListEntry(courseWaitListEntryInfo.getId(), contextInfo);
        if (!old.getMeta().getVersionInd().equals(copy.getMeta().getVersionInd())) {
            throw new VersionMismatchException(old.getMeta().getVersionInd());
        }

        //If the position has changed then update the list.
        if(!copy.getPosition().equals(old.getPosition())) {
            moveCourseWaitListEntryToPosition(copy.getId(), copy.getPosition(), contextInfo);
        }

        copy.setMeta(updateMeta(copy.getMeta(), contextInfo));
        this.courseWaitListEntryMap .put(courseWaitListEntryInfo.getId(), copy);
        return new CourseWaitListEntryInfo(copy);
    }

    @Override
    public StatusInfo changeCourseWaitListEntryState(String courseWaitListEntryId, String stateKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        CourseWaitListEntryInfo courseWaitListEntry = getCourseWaitListEntry(courseWaitListEntryId, contextInfo);
        courseWaitListEntry.setStateKey(stateKey);
        courseWaitListEntry.setMeta(updateMeta(courseWaitListEntry.getMeta(), contextInfo));
        this.courseWaitListEntryMap.put(courseWaitListEntryId, courseWaitListEntry);
        return new StatusInfo();
    }

    @Override
    public StatusInfo deleteCourseWaitListEntry(String courseWaitListEntryId, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        CourseWaitListEntryInfo entry = getCourseWaitListEntry(courseWaitListEntryId, contextInfo);

        //reorder the positions
        List<CourseWaitListEntryInfo> courseWaitListEntries = getCourseWaitListEntriesByCourseWaitList(entry.getCourseWaitListId(), contextInfo);

        if(entry.getPosition() < courseWaitListEntries.size()) {
            moveCourseWaitListEntryToPosition(courseWaitListEntryId, courseWaitListEntries.size(), contextInfo);
        }

        // DELETE
        if (this.courseWaitListEntryMap.remove(courseWaitListEntryId) == null) {
            throw new OperationFailedException(courseWaitListEntryId);
        }
        return newStatus();
    }

    @Override
    public StatusInfo reorderCourseWaitListEntries(String courseWaitListId, List<String> courseWaitListEntryIds, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        //This implementation is very very slow.  The assumption was made that this will be acceptable for the Map Impl.
        for(int i = 0; i < courseWaitListEntryIds.size(); i++) {
            moveCourseWaitListEntryToPosition(courseWaitListEntryIds.get(i), i + 1, contextInfo);
        }
        return new StatusInfo();
    }

    @Override
    public StatusInfo moveCourseWaitListEntryToPosition(String courseWaitListEntryId, Integer position, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        CourseWaitListEntryInfo entry = getCourseWaitListEntry(courseWaitListEntryId, contextInfo);

        if(!entry.getPosition().equals(position)) {
            List<CourseWaitListEntryInfo> courseWaitListEntries = getCourseWaitListEntriesByCourseWaitList(entry.getCourseWaitListId(), contextInfo);
            moveCourseWaitListEntryToPosition(courseWaitListEntryId, courseWaitListEntries, position, contextInfo);
        }
        return new StatusInfo();

    }

    private void moveCourseWaitListEntryToPosition(String courseWaitListEntryId, List<CourseWaitListEntryInfo> courseWaitListEntries, Integer position, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException {
        if(position <= 0 || position > courseWaitListEntries.size()) {
            throw new InvalidParameterException("The new position " + position + " for the wait list entry " + courseWaitListEntryId + " is not valid");
        }

        int currentPosition = 1;
        if(currentPosition == position) {
            currentPosition++;
        }

        for(CourseWaitListEntryInfo currentEntry : courseWaitListEntries) {
            boolean entryChanged = false;
            if(!currentEntry.getId().equals(courseWaitListEntryId)) {
                if(!currentEntry.getPosition().equals(currentPosition)) {
                    currentEntry.setPosition(currentPosition);
                    entryChanged = true;
                }
                currentPosition++;
                if(currentPosition == position) {
                    currentPosition++;
                }
            } else {
                currentEntry.setPosition(position);
                entryChanged = true;
            }

            if(entryChanged) {
                //update directly to prevent cycle
                CourseWaitListEntryInfo updatedEntry = courseWaitListEntryMap.get(currentEntry.getId());
                updatedEntry.setPosition(currentEntry.getPosition());
                updatedEntry.setMeta(updateMeta(updatedEntry.getMeta(), contextInfo));
            }
        }
    }

    private StatusInfo newStatus() {
        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);
        return status;
    }

    private MetaInfo newMeta(ContextInfo context) {
        MetaInfo meta = new MetaInfo();
        meta.setCreateId(context.getPrincipalId());
        meta.setCreateTime(new Date());
        meta.setUpdateId(context.getPrincipalId());
        meta.setUpdateTime(meta.getCreateTime());
        meta.setVersionInd("0");
        return meta;
    }

    private MetaInfo updateMeta(MetaInfo old, ContextInfo context) {
        MetaInfo meta = new MetaInfo(old);
        meta.setUpdateId(context.getPrincipalId());
        meta.setUpdateTime(new Date());
        meta.setVersionInd((Integer.parseInt(meta.getVersionInd()) + 1) + "");
        return meta;
    }

    private void sortCourseWaitListEntries(List<CourseWaitListEntryInfo> entries) {

        Collections.sort(entries, new Comparator<CourseWaitListEntryInfo>() {
            @Override
            public int compare(CourseWaitListEntryInfo o1, CourseWaitListEntryInfo o2) {
                return o1.getPosition().compareTo(o2.getPosition());
            }
        });

    }

}

