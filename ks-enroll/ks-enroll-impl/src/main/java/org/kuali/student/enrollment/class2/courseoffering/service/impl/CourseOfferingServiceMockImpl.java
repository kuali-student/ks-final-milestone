/*
 * Copyright 2011 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may	obtain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.OfferingInstructorInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupTemplateInfo;
import org.kuali.student.enrollment.courseoffering.dto.SeatPoolDefinitionInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.MetaInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DependentObjectsExistException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.core.type.dto.TypeInfo;

public class CourseOfferingServiceMockImpl implements CourseOfferingService {

    @Override
    public CourseOfferingInfo getCourseOffering(String courseOfferingId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        if (!this.courseOfferingMap.containsKey(courseOfferingId)) {
            throw new DoesNotExistException(courseOfferingId);
        }
        return this.courseOfferingMap.get(courseOfferingId);
    }

    @Override
    public List<CourseOfferingInfo> getCourseOfferingsByIds(List<String> courseOfferingIds, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        List<CourseOfferingInfo> list = new ArrayList<CourseOfferingInfo>();
        for (String id : courseOfferingIds) {
            list.add(this.getCourseOffering(id, context));
        }
        return list;
    }

    @Override
    public List<CourseOfferingInfo> getCourseOfferingsByCourse(String courseId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        List<CourseOfferingInfo> list = new ArrayList<CourseOfferingInfo>();
        for (CourseOfferingInfo info : courseOfferingMap.values()) {
            if (courseId.equals(info.getCourseId())) {
                list.add(info);
            }
        }
        return list;
    }

    @Override
    public List<CourseOfferingInfo> getCourseOfferingsByCourseAndTerm(String courseId, String termId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        List<CourseOfferingInfo> list = new ArrayList<CourseOfferingInfo>();
        for (CourseOfferingInfo info : courseOfferingMap.values()) {
            if (courseId.equals(info.getCourseId())) {
                if (termId.equals(info.getTermId())) {
                    list.add(info);
                }
            }
        }
        return list;
    }

    @Override
    public List<String> getCourseOfferingIdsByTerm(String termId, Boolean useIncludedTerm, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        List<String> list = new ArrayList<String>();
        for (CourseOfferingInfo info : courseOfferingMap.values()) {
            if (termId.equals(info.getTermId())) {
                list.add(info.getId());
            }
            // TODO: check included terms
        }
        return list;
    }

    @Override
    public List<String> getCourseOfferingIdsByTermAndSubjectArea(String termId, String subjectArea, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        List<String> list = new ArrayList<String>();
        for (CourseOfferingInfo info : courseOfferingMap.values()) {
            if (termId.equals(info.getTermId())) {
                if (subjectArea.equals(info.getSubjectArea())) {
                    list.add(info.getId());
                }
            }
        }
        return list;
    }

    @Override
    public List<CourseOfferingInfo> getCourseOfferingsByTermAndInstructor(String termId, String instructorId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        List<CourseOfferingInfo> list = new ArrayList<CourseOfferingInfo>();
        for (CourseOfferingInfo info : courseOfferingMap.values()) {
            if (termId.equals(info.getTermId())) {
                if (matches(instructorId, info.getInstructors())) {
                    list.add(info);
                }
            }
        }
        return list;
    }

    private boolean matches(String personId, List<OfferingInstructorInfo> instructors) {
        for (OfferingInstructorInfo instructor : instructors) {
            if (personId.equals(instructor.getPersonId())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<String> getCourseOfferingIdsByTermAndUnitsContentOwner(String termId, String unitsContentOwnerId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        List<String> list = new ArrayList<String>();
        for (CourseOfferingInfo info : courseOfferingMap.values()) {
            if (termId.equals(info.getTermId())) {
                if (info.getUnitsContentOwner().contains(unitsContentOwnerId)) {
                    list.add(info.getId());
                }
            }
        }
        return list;
    }

    @Override
    public List<String> getCourseOfferingIdsByType(String typeKey, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        List<String> list = new ArrayList<String>();
        for (CourseOfferingInfo info : courseOfferingMap.values()) {
            if (typeKey.equals(info.getTypeKey())) {
                list.add(info.getId());
            }
        }
        return list;
    }

    @Override
    public List<String> getCourseOfferingIdsBySoc(String socId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        throw new OperationFailedException("updateCourseOfferingFromCanonical has not been implemented");
    }

    @Override
    public List<String> getPublishedCourseOfferingIdsBySoc(String socId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        throw new OperationFailedException("updateCourseOfferingFromCanonical has not been implemented");
    }
    // cache variable 
    // The LinkedHashMap is just so the values come back in a predictable order
    private Map<String, CourseOfferingInfo> courseOfferingMap = new LinkedHashMap<String, CourseOfferingInfo>();

    @Override
    public CourseOfferingInfo createCourseOffering(String courseId, String termId, String courseOfferingTypeKey, CourseOfferingInfo courseOfferingInfo, ContextInfo context)
            throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException {
        // create 
        if (!courseOfferingTypeKey.equals(courseOfferingInfo.getTypeKey())) {
            throw new InvalidParameterException("The type parameter does not match the type on the info object");
        }
        // TODO: check the rest of the readonly fields that are specified on the create to make sure they match the info object
        CourseOfferingInfo copy = new CourseOfferingInfo(courseOfferingInfo);
        if (copy.getId() == null) {
            copy.setId(courseOfferingMap.size() + "");
        }
        copy.setMeta(newMeta(context));
        courseOfferingMap.put(copy.getId(), copy);
        return new CourseOfferingInfo(copy);
    }

    @Override
    public CourseOfferingInfo updateCourseOffering(String courseOfferingId, CourseOfferingInfo courseOfferingInfo, ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        // update
        if (!courseOfferingId.equals(courseOfferingInfo.getId())) {
            throw new InvalidParameterException("The id parameter does not match the id on the info object");
        }
        CourseOfferingInfo copy = new CourseOfferingInfo(courseOfferingInfo);
        CourseOfferingInfo old = this.getCourseOffering(courseOfferingInfo.getId(), context);
        if (!old.getMeta().getVersionInd().equals(copy.getMeta().getVersionInd())) {
            throw new VersionMismatchException(old.getMeta().getVersionInd());
        }
        copy.setMeta(updateMeta(copy.getMeta(), context));
        this.courseOfferingMap.put(courseOfferingInfo.getId(), copy);
        return new CourseOfferingInfo(copy);
    }

    @Override
    public CourseOfferingInfo updateCourseOfferingFromCanonical(String courseOfferingId, ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, VersionMismatchException {
        throw new OperationFailedException("updateCourseOfferingFromCanonical has not been implemented");
    }

    @Override
    public StatusInfo deleteCourseOffering(String courseOfferingId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        if (this.courseOfferingMap.remove(courseOfferingId) == null) {
            throw new DoesNotExistException(courseOfferingId);
        }
        return newStatus();
    }

    @Override
    public List<ValidationResultInfo> validateCourseOffering(String validationType, CourseOfferingInfo courseOfferingInfo, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // validate
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    public List<ValidationResultInfo> validateCourseOfferingFromCanonical(CourseOfferingInfo courseOfferingInfo, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // validate
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    public FormatOfferingInfo getFormatOffering(String formatOfferingId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        if (!this.formatOfferingMap.containsKey(formatOfferingId)) {
            throw new DoesNotExistException(formatOfferingId);
        }
        return this.formatOfferingMap.get(formatOfferingId);
    }

    @Override
    public List<FormatOfferingInfo> getFormatOfferingByCourseOfferingId(String courseOfferingId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        List<FormatOfferingInfo> list = new ArrayList<FormatOfferingInfo>();
        for (FormatOfferingInfo info : formatOfferingMap.values()) {
            if (courseOfferingId.equals(info.getCourseOfferingId())) {
                list.add(info);
            }
        }
        return list;
    }
    // cache variable 
    // The LinkedHashMap is just so the values come back in a predictable order
    private Map<String, FormatOfferingInfo> formatOfferingMap = new LinkedHashMap<String, FormatOfferingInfo>();

    @Override
    public FormatOfferingInfo createFormatOffering(String courseOfferingId, String formatId, String formatOfferingType, FormatOfferingInfo formatOfferingInfo, ContextInfo context)
            throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException {
        // create 
        if (!formatOfferingType.equals(formatOfferingInfo.getTypeKey())) {
            throw new InvalidParameterException("The type parameter does not match the type on the info object");
        }
        // TODO: check the rest of the readonly fields that are specified on the create to make sure they match the info object
        FormatOfferingInfo copy = new FormatOfferingInfo(formatOfferingInfo);
        if (copy.getId() == null) {
            copy.setId(formatOfferingMap.size() + "");
        }
        copy.setMeta(newMeta(context));
        formatOfferingMap.put(copy.getId(), copy);
        return new FormatOfferingInfo(copy);
    }

    @Override
    public FormatOfferingInfo updateFormatOffering(String formatOfferingId, FormatOfferingInfo formatOfferingInfo, ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        // update
        if (!formatOfferingId.equals(formatOfferingInfo.getId())) {
            throw new InvalidParameterException("The id parameter does not match the id on the info object");
        }
        FormatOfferingInfo copy = new FormatOfferingInfo(formatOfferingInfo);
        FormatOfferingInfo old = this.getFormatOffering(formatOfferingInfo.getId(), context);
        if (!old.getMeta().getVersionInd().equals(copy.getMeta().getVersionInd())) {
            throw new VersionMismatchException(old.getMeta().getVersionInd());
        }
        copy.setMeta(updateMeta(copy.getMeta(), context));
        this.formatOfferingMap.put(formatOfferingInfo.getId(), copy);
        return new FormatOfferingInfo(copy);
    }

    @Override
    public List<ValidationResultInfo> validateFormatOffering(String validationType, FormatOfferingInfo formatOfferingInfo, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // validate
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    public StatusInfo deleteFormatOffering(String formatOfferingId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, DependentObjectsExistException {
        if (this.formatOfferingMap.remove(formatOfferingId) == null) {
            throw new DoesNotExistException(formatOfferingId);
        }
        return newStatus();
    }

    @Override
    public TypeInfo getActivityOfferingType(String activityOfferingTypeKey, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        throw new OperationFailedException("getActivityOfferingType has not been implemented");
    }

    @Override
    public List<TypeInfo> getActivityOfferingTypes(ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("getActivityOfferingTypes has not been implemented");
    }

    @Override
    public List<TypeInfo> getActivityOfferingTypesForActivityType(String activityTypeKey, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        throw new OperationFailedException("getActivityOfferingTypesForActivityType has not been implemented");
    }

    @Override
    public ActivityOfferingInfo getActivityOffering(String activityOfferingId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        if (!this.activityOfferingMap.containsKey(activityOfferingId)) {
            throw new DoesNotExistException(activityOfferingId);
        }
        return this.activityOfferingMap.get(activityOfferingId);
    }

    @Override
    public List<ActivityOfferingInfo> getActivityOfferingsByIds(List<String> activityOfferingIds, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        List<ActivityOfferingInfo> list = new ArrayList<ActivityOfferingInfo>();
        for (String id : activityOfferingIds) {
            list.add(this.getActivityOffering(id, context));
        }
        return list;
    }

    @Override
    public List<ActivityOfferingInfo> getActivityOfferingsByCourseOffering(String courseOfferingId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        List<ActivityOfferingInfo> list = new ArrayList<ActivityOfferingInfo>();
        for (ActivityOfferingInfo info : activityOfferingMap.values()) {
            if (courseOfferingId.equals(this.getFormatOffering(info.getFormatOfferingId(), context).getCourseOfferingId())) {
                list.add(info);
            }
        }
        return list;
    }

    @Override
    public List<ActivityOfferingInfo> getActivityOfferingsByFormatOffering(String formatOfferingId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        List<ActivityOfferingInfo> list = new ArrayList<ActivityOfferingInfo>();
        for (ActivityOfferingInfo info : activityOfferingMap.values()) {
            if (formatOfferingId.equals(info.getFormatOfferingId())) {
                list.add(info);
            }
        }
        return list;
    }

    @Override
    public List<ActivityOfferingInfo> getActivityOfferingsByCourseOfferingWithoutRegGroup(String courseOfferingId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        throw new OperationFailedException("getActivityOfferingTypesForActivityType has not been implemented");
    }

    @Override
    public List<ActivityOfferingInfo> getUnscheduledActivityOfferingsBySoc(String socId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        throw new OperationFailedException("getActivityOfferingTypesForActivityType has not been implemented");
    }

    @Override
    public List<ActivityOfferingInfo> getUnpublishedActivityOfferingsBySoc(String socId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        throw new OperationFailedException("getActivityOfferingTypesForActivityType has not been implemented");
    }
    // cache variable 
    // The LinkedHashMap is just so the values come back in a predictable order
    private Map<String, ActivityOfferingInfo> activityOfferingMap = new LinkedHashMap<String, ActivityOfferingInfo>();

    @Override
    public ActivityOfferingInfo createActivityOffering(String formatOfferingId, String activityId, String activityOfferingTypeKey, ActivityOfferingInfo activityOfferingInfo, ContextInfo context)
            throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException {
        // create 
        if (!activityOfferingTypeKey.equals(activityOfferingInfo.getTypeKey())) {
            throw new InvalidParameterException("The type parameter does not match the type on the info object");
        }
        // TODO: check the rest of the readonly fields that are specified on the create to make sure they match the info object
        ActivityOfferingInfo copy = new ActivityOfferingInfo(activityOfferingInfo);
        if (copy.getId() == null) {
            copy.setId(activityOfferingMap.size() + "");
        }
        copy.setMeta(newMeta(context));
        activityOfferingMap.put(copy.getId(), copy);
        return new ActivityOfferingInfo(copy);
    }

    @Override
    public List<ActivityOfferingInfo> generateActivityOfferingsForFormatOffering(String formatOfferingId, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("generateActivityOfferingsForFormatOffering has not been implemented");
    }

    @Override
    public ActivityOfferingInfo updateActivityOffering(String activityOfferingId, ActivityOfferingInfo activityOfferingInfo, ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, VersionMismatchException, ReadOnlyException {
        // update
        if (!activityOfferingId.equals(activityOfferingInfo.getId())) {
            throw new InvalidParameterException("The id parameter does not match the id on the info object");
        }
        ActivityOfferingInfo copy = new ActivityOfferingInfo(activityOfferingInfo);
        ActivityOfferingInfo old = this.getActivityOffering(activityOfferingInfo.getId(), context);
        if (!old.getMeta().getVersionInd().equals(copy.getMeta().getVersionInd())) {
            throw new VersionMismatchException(old.getMeta().getVersionInd());
        }
        copy.setMeta(updateMeta(copy.getMeta(), context));
        this.activityOfferingMap.put(activityOfferingInfo.getId(), copy);
        return new ActivityOfferingInfo(copy);
    }

    @Override
    public StatusInfo deleteActivityOffering(String activityOfferingId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        if (this.activityOfferingMap.remove(activityOfferingId) == null) {
            throw new DoesNotExistException(activityOfferingId);
        }
        return newStatus();
    }

    @Override
    public List<ValidationResultInfo> validateActivityOffering(String validationType, ActivityOfferingInfo activityOfferingInfo, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // validate
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    public Float calculateInClassContactHoursForTerm(String activityOfferingId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        throw new OperationFailedException("calculateInClassContactHoursForTerm has not been implemented");
    }

    @Override
    public Float calculateOutofClassContactHoursForTerm(String activityOfferingId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        throw new OperationFailedException("calculateOutofClassContactHoursForTerm has not been implemented");
    }

    @Override
    public Float calculateTotalContactHoursForTerm(String activityOfferingId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        throw new OperationFailedException("calculateTotalContactHoursForTerm has not been implemented");
    }

    @Override
    public RegistrationGroupInfo getRegistrationGroup(String registrationGroupId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        if (!this.registrationGroupMap.containsKey(registrationGroupId)) {
            throw new DoesNotExistException(registrationGroupId);
        }
        return this.registrationGroupMap.get(registrationGroupId);
    }

    @Override
    public List<RegistrationGroupInfo> getRegistrationGroupsByIds(List<String> registrationGroupIds, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        List<RegistrationGroupInfo> list = new ArrayList<RegistrationGroupInfo>();
        for (String id : registrationGroupIds) {
            list.add(this.getRegistrationGroup(id, context));
        }
        return list;
    }

    @Override
    public List<RegistrationGroupInfo> getRegistrationGroupsForCourseOffering(String courseOfferingId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        throw new OperationFailedException("getRegistrationGroupsForCourseOffering has not been implemented");
    }

    @Override
    public List<RegistrationGroupInfo> getRegistrationGroupsWithActivityOfferings(List<String> activityOfferingIds, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        throw new OperationFailedException("getRegistrationGroupsWithActivityOfferings has not been implemented");
    }

    @Override
    public List<RegistrationGroupInfo> getRegistrationGroupsByFormatOffering(String formatOfferingId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        throw new OperationFailedException("getActivityOfferingTypesForActivityType has not been implemented");
    }
    // cache variable 
    // The LinkedHashMap is just so the values come back in a predictable order
    private Map<String, RegistrationGroupInfo> registrationGroupMap = new LinkedHashMap<String, RegistrationGroupInfo>();

    @Override
    public RegistrationGroupInfo createRegistrationGroup(String registrationGroupType, RegistrationGroupInfo registrationGroupInfo, ContextInfo context)
            throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException {
        // create 
        if (!registrationGroupType.equals(registrationGroupInfo.getTypeKey())) {
            throw new InvalidParameterException("The type parameter does not match the type on the info object");
        }
        RegistrationGroupInfo copy = new RegistrationGroupInfo(registrationGroupInfo);
        if (copy.getId() == null) {
            copy.setId(registrationGroupMap.size() + "");
        }
        copy.setMeta(newMeta(context));
        registrationGroupMap.put(copy.getId(), copy);
        return new RegistrationGroupInfo(copy);
    }

    @Override
    public List<RegistrationGroupInfo> generateRegistrationGroupsForFormatOffering(String formatOfferingId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        throw new OperationFailedException("generateRegistrationGroupsForFormatOffering has not been implemented");
    }

    @Override
    public RegistrationGroupInfo updateRegistrationGroup(String registrationGroupId, RegistrationGroupInfo registrationGroupInfo, ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        // update
        if (!registrationGroupId.equals(registrationGroupInfo.getId())) {
            throw new InvalidParameterException("The id parameter does not match the id on the info object");
        }
        RegistrationGroupInfo copy = new RegistrationGroupInfo(registrationGroupInfo);
        RegistrationGroupInfo old = this.getRegistrationGroup(registrationGroupInfo.getId(), context);
        if (!old.getMeta().getVersionInd().equals(copy.getMeta().getVersionInd())) {
            throw new VersionMismatchException(old.getMeta().getVersionInd());
        }
        copy.setMeta(updateMeta(copy.getMeta(), context));
        this.registrationGroupMap.put(registrationGroupInfo.getId(), copy);
        return new RegistrationGroupInfo(copy);
    }

    @Override
    public StatusInfo deleteRegistrationGroup(String registrationGroupId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        if (this.registrationGroupMap.remove(registrationGroupId) == null) {
            throw new DoesNotExistException(registrationGroupId);
        }
        return newStatus();
    }

    @Override
    public List<ValidationResultInfo> validateRegistrationGroup(String validationType, RegistrationGroupInfo registrationGroupInfo, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // validate
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    public RegistrationGroupTemplateInfo getRegistrationGroupTemplate(String registrationGroupTemplateId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        throw new OperationFailedException("getActivityOfferingTypesForActivityType has not been implemented");
    }

    @Override
    public RegistrationGroupTemplateInfo updateRegistrationGroupTemplate(String registrationGroupTemplateId, RegistrationGroupTemplateInfo registrationGroupTemplateInfo, ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        throw new OperationFailedException("getActivityOfferingTypesForActivityType has not been implemented");
    }

    @Override
    public StatusInfo deleteRegistrationGroupTemplate(String registrationGroupTemplateId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        throw new OperationFailedException("getActivityOfferingTypesForActivityType has not been implemented");
    }

    @Override
    public SeatPoolDefinitionInfo getSeatPoolDefinition(String seatPoolDefinitionId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        if (!this.seatPoolDefinitionMap.containsKey(seatPoolDefinitionId)) {
            throw new DoesNotExistException(seatPoolDefinitionId);
        }
        return this.seatPoolDefinitionMap.get(seatPoolDefinitionId);
    }

    @Override
    public List<SeatPoolDefinitionInfo> getSeatPoolDefinitionsForCourseOffering(String courseOfferingId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        throw new OperationFailedException("getSeatPoolDefinitionsForCourseOffering has not been implemented");
    }

    @Override
    public List<SeatPoolDefinitionInfo> getSeatPoolDefinitionsForRegGroup(String registrationGroupId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        throw new OperationFailedException("getSeatPoolDefinitionsForRegGroup has not been implemented");
    }
    // cache variable 
    // The LinkedHashMap is just so the values come back in a predictable order
    private Map<String, SeatPoolDefinitionInfo> seatPoolDefinitionMap = new LinkedHashMap<String, SeatPoolDefinitionInfo>();

    @Override
    public SeatPoolDefinitionInfo createSeatPoolDefinition(SeatPoolDefinitionInfo seatPoolDefinitionInfo, ContextInfo context)
            throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, ReadOnlyException {
        // create 
        SeatPoolDefinitionInfo copy = new SeatPoolDefinitionInfo(seatPoolDefinitionInfo);
        if (copy.getId() == null) {
            copy.setId(seatPoolDefinitionMap.size() + "");
        }
        copy.setMeta(newMeta(context));
        seatPoolDefinitionMap.put(copy.getId(), copy);
        return new SeatPoolDefinitionInfo(copy);
    }

    @Override
    public SeatPoolDefinitionInfo updateSeatPoolDefinition(String seatPoolDefinitionId, SeatPoolDefinitionInfo seatPoolDefinitionInfo, ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        // update
        if (!seatPoolDefinitionId.equals(seatPoolDefinitionInfo.getId())) {
            throw new InvalidParameterException("The id parameter does not match the id on the info object");
        }
        SeatPoolDefinitionInfo copy = new SeatPoolDefinitionInfo(seatPoolDefinitionInfo);
        SeatPoolDefinitionInfo old = this.getSeatPoolDefinition(seatPoolDefinitionInfo.getId(), context);
        if (!old.getMeta().getVersionInd().equals(copy.getMeta().getVersionInd())) {
            throw new VersionMismatchException(old.getMeta().getVersionInd());
        }
        copy.setMeta(updateMeta(copy.getMeta(), context));
        this.seatPoolDefinitionMap.put(seatPoolDefinitionInfo.getId(), copy);
        return new SeatPoolDefinitionInfo(copy);
    }

    @Override
    public List<ValidationResultInfo> validateSeatPoolDefinition(String validationTypeKey, SeatPoolDefinitionInfo seatPoolDefinitionInfo, ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, VersionMismatchException {
        // validate
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    public StatusInfo deleteSeatPoolDefinition(String seatPoolDefinitionId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        if (this.seatPoolDefinitionMap.remove(seatPoolDefinitionId) == null) {
            throw new DoesNotExistException(seatPoolDefinitionId);
        }
        return newStatus();
    }

    @Override
    public List<CourseOfferingInfo> searchForCourseOfferings(QueryByCriteria criteria, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("searchForCourseOfferings has not been implemented");
    }

    @Override
    public List<String> searchForCourseOfferingIds(QueryByCriteria criteria, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("searchForCourseOfferingIds has not been implemented");
    }

    @Override
    public List<ActivityOfferingInfo> searchForActivityOfferings(QueryByCriteria criteria, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("searchForActivityOfferings has not been implemented");
    }

    @Override
    public List<String> searchForActivityOfferingIds(QueryByCriteria criteria, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("searchForActivityOfferingIds has not been implemented");
    }

    @Override
    public List<RegistrationGroupInfo> searchForRegistrationGroups(QueryByCriteria criteria, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("searchForRegistrationGroups has not been implemented");
    }

    @Override
    public List<String> searchForRegistrationGroupIds(QueryByCriteria criteria, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("searchForRegistrationGroupIds has not been implemented");
    }

    @Override
    public List<SeatPoolDefinitionInfo> searchForSeatpoolDefintions(QueryByCriteria criteria, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("searchForSeatpoolDefintions has not been implemented");
    }

    @Override
    public List<String> searchForSeatpoolDefintionIds(QueryByCriteria criteria, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("searchForSeatpoolDefintionIds has not been implemented");
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

    private StatusInfo newStatus() {
        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);
        return status;
    }

    private MetaInfo updateMeta(MetaInfo old, ContextInfo context) {
        MetaInfo meta = new MetaInfo(old);
        meta.setUpdateId(context.getPrincipalId());
        meta.setUpdateTime(new Date());
        meta.setVersionInd((Integer.parseInt(meta.getVersionInd()) + 1) + "");
        return meta;
    }
}
