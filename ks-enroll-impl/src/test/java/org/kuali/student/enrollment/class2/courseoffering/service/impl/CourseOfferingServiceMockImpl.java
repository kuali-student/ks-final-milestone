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
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.jws.WebParam;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.jacorb.poa.AOM;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.common.mock.MockService;
import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.enrollment.class2.courseoffering.service.decorators.R1CourseServiceHelper;
import org.kuali.student.enrollment.class2.courseoffering.service.transformer.CourseOfferingTransformer;
import org.kuali.student.enrollment.courseoffering.dto.AOClusterVerifyResultsInfo;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingClusterInfo;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingDisplayInfo;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingSetInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingDisplayInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.OfferingInstructorInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.courseoffering.dto.SeatPoolDefinitionInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingServiceBusinessLogic;
import org.kuali.student.enrollment.courseofferingset.dto.SocRolloverResultItemInfo;
import org.kuali.student.r2.common.dto.BulkStatusInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.KeyNameInfo;
import org.kuali.student.r2.common.dto.MetaInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DependentObjectsExistException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.infc.ValidationResult;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.acal.service.AcademicCalendarService;
import org.kuali.student.r2.core.class1.state.dto.StateInfo;
import org.kuali.student.r2.core.class1.state.service.StateService;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.core.scheduling.dto.ScheduleComponentDisplayInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleDisplayInfo;
import org.kuali.student.r2.core.scheduling.service.SchedulingService;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.service.CourseService;
import org.kuali.student.r2.lum.lrc.service.LRCService;

public class CourseOfferingServiceMockImpl implements CourseOfferingService,
        MockService {

    private static Logger log = Logger
            .getLogger(CourseOfferingServiceMockImpl.class);


    @Resource
    private CourseService courseService;

    @Resource
    private AcademicCalendarService acalService;

    @Resource(name = "coBusinessLogic")
    private CourseOfferingServiceBusinessLogic businessLogic;

    @Resource
    private TypeService typeService;

    @Resource
    private StateService stateService;

    @Resource
    private LRCService lrcService;
    
    @Resource
    private SchedulingService schedulingService;

    public SchedulingService getSchedulingService() {
        return schedulingService;
    }

    public void setSchedulingService(SchedulingService schedulingService) {
        this.schedulingService = schedulingService;
    }

    public StateService getStateService() {
        return stateService;
    }

    public void setStateService(StateService stateService) {
        this.stateService = stateService;
    }

    public LRCService getLrcService() {
        return lrcService;
    }

    public void setLrcService(LRCService lrcService) {
        this.lrcService = lrcService;
    }
    
    @Override
    public void clear() {

        this.activityOfferingMap.clear();
        this.courseOfferingMap.clear();
        this.formatOfferingMap.clear();
        this.registrationGroupMap.clear();
        this.seatPoolDefinitionMap.clear();

        activityOfferingToSeatPoolMap.clear();

        this.activityOfferingClusterMap.clear();

    }

    public void setTypeService(TypeService typeService) {
        this.typeService = typeService;
    }

    public CourseOfferingServiceBusinessLogic getBusinessLogic() {
        return businessLogic;
    }

    public void setBusinessLogic(
            CourseOfferingServiceBusinessLogic businessLogic) {
        this.businessLogic = businessLogic;
    }

    public CourseService getCourseService() {
        return courseService;
    }

    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }

    public AcademicCalendarService getAcalService() {
        return acalService;
    }

    public void setAcalService(AcademicCalendarService acalService) {
        this.acalService = acalService;
    }

    @Override
    public TypeInfo getCourseOfferingType(String courseOfferingTypeKey,
                                          ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return typeService.getType(courseOfferingTypeKey, context);
    }

    @Override
    public List<TypeInfo> getCourseOfferingTypes(ContextInfo context)
            throws InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet");
    }

    @Override
    public List<TypeInfo> getInstructorTypesForCourseOfferingType(
            String courseOfferingTypeKey, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet");
    }

    @Override
    public StatusInfo deleteCourseOfferingCascaded(String courseOfferingId,
                                                   ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {


        List<FormatOfferingInfo> fos = getFormatOfferingsByCourseOffering(courseOfferingId, context);

        for (FormatOfferingInfo formatOfferingInfo : fos) {

            StatusInfo status = deleteFormatOfferingCascaded(formatOfferingInfo.getId(), context);

            if (!status.getIsSuccess())
                throw new OperationFailedException("deleteFormatOfferingCascaded(): failed for id = " + formatOfferingInfo.getId());
        }

        StatusInfo status;
        try {
            status = deleteCourseOffering(courseOfferingId, context);
        } catch (DependentObjectsExistException e) {
            throw new OperationFailedException("deleteFormatOfferingCascaded(): failed because dependant objects still exist for courseOfferingId = " + courseOfferingId, e);
        }

        return status;
    }

    @Override
    public StatusInfo deleteFormatOfferingCascaded(String formatOfferingId,
                                                   ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {

        List<ActivityOfferingInfo> aos = getActivityOfferingsByFormatOffering(formatOfferingId, context);

        for (ActivityOfferingInfo activityOfferingInfo : aos) {

            deleteActivityOfferingCascaded(activityOfferingInfo.getId(), context);
        }


        try {
            deleteFormatOffering(formatOfferingId, context);
        } catch (DependentObjectsExistException e) {
            // should never fire since we deleted the dependencies
            throw new OperationFailedException("failed to delete format offering for id = " + formatOfferingId, e);
        }

        return successStatus();
    }

    @Override
    public RegistrationGroupInfo createRegistrationGroup(
            String formatOfferingId, String activityOfferingClusterId, String registrationGroupType,
            RegistrationGroupInfo registrationGroupInfo,
            ContextInfo context)
            throws DoesNotExistException, DataValidationErrorException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException,
            ReadOnlyException {
        // create
        if (!registrationGroupType.equals(registrationGroupInfo.getTypeKey())) {
            throw new InvalidParameterException(
                    "The type parameter does not match the type on the info object");
        }
        RegistrationGroupInfo copy = new RegistrationGroupInfo(
                registrationGroupInfo);
        if (copy.getId() == null) {
            copy.setId(UUIDHelper.genStringUUID());
        }
        copy.setMeta(newMeta(context));
        registrationGroupMap.put(copy.getId(), copy);
        return new RegistrationGroupInfo(copy);
    }

    @Override
    public CourseOfferingInfo getCourseOffering(String courseOfferingId,
                                                ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        if (!this.courseOfferingMap.containsKey(courseOfferingId)) {
            throw new DoesNotExistException(courseOfferingId);
        }
        return this.courseOfferingMap.get(courseOfferingId);
    }

    @Override
    public List<CourseOfferingInfo> getCourseOfferingsByIds(
            List<String> courseOfferingIds, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        List<CourseOfferingInfo> list = new ArrayList<CourseOfferingInfo>();
        for (String id : courseOfferingIds) {
            list.add(this.getCourseOffering(id, context));
        }
        return list;
    }

    @Override
    public List<CourseOfferingInfo> getCourseOfferingsByCourse(String courseId,
                                                               ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        List<CourseOfferingInfo> list = new ArrayList<CourseOfferingInfo>();
        for (CourseOfferingInfo info : courseOfferingMap.values()) {
            if (courseId.equals(info.getCourseId())) {
                list.add(info);
            }
        }
        return list;
    }

    @Override
    public List<CourseOfferingInfo> getCourseOfferingsByCourseAndTerm(
            String courseId, String termId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
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
    public List<String> getCourseOfferingIdsByTerm(String termId,
                                                   Boolean useIncludedTerm, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
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
    public CourseOfferingInfo createCourseOffering(String courseId, String termId, String courseOfferingTypeKey, CourseOfferingInfo courseOfferingInfo,
                                                   List<String> optionKeys, ContextInfo context)
            throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException {
        // create
        if (!courseOfferingTypeKey.equals(courseOfferingInfo.getTypeKey())) {
            throw new InvalidParameterException("The type parameter does not match the type on the info object");
        }
        // TODO: check the rest of the readonly fields that are specified on the create to make sure they match the info object
        CourseOfferingInfo copy = new CourseOfferingInfo(courseOfferingInfo);
        if (copy.getId() == null) {
            copy.setId(UUIDHelper.genStringUUID());
        }
        // TODO: move this logic to the calculation decorator do the persistence layer doesn't have this logic mixed in with it
        // copy from cannonical
        CourseInfo courseInfo = new R1CourseServiceHelper(courseService, acalService).getCourse(courseId);
        CourseOfferingTransformer coTransformer = new CourseOfferingTransformer();
        coTransformer.copyFromCanonical(courseInfo, copy, optionKeys, context);
        copy.setMeta(newMeta(context));
        courseOfferingMap.put(copy.getId(), copy);
        log.info("CourseOfferingMockImpl: created course offering: " + copy.getId() + "term=" + copy.getTermId() + " for course =" + copy.getCourseId());
        return new CourseOfferingInfo(copy);
    }

    @Override
    public List<CourseOfferingInfo> getCourseOfferingsByTermAndInstructor(
            String termId, String instructorId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
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

    private boolean matches(String personId,
                            List<OfferingInstructorInfo> instructors) {
        for (OfferingInstructorInfo instructor : instructors) {
            if (personId.equals(instructor.getPersonId())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<String> getCourseOfferingIdsByTermAndUnitsContentOwner(
            String termId, String unitsContentOwnerId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        List<String> list = new ArrayList<String>();
        for (CourseOfferingInfo info : courseOfferingMap.values()) {
            if (termId.equals(info.getTermId())) {
                if (info.getUnitsContentOwnerOrgIds().contains(
                        unitsContentOwnerId)) {
                    list.add(info.getId());
                }
            }
        }
        return list;
    }

    @Override
    public List<String> getCourseOfferingIdsByType(String typeKey,
                                                   ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        List<String> list = new ArrayList<String>();
        for (CourseOfferingInfo info : courseOfferingMap.values()) {
            if (typeKey.equals(info.getTypeKey())) {
                list.add(info.getId());
            }
        }
        return list;
    }

    // cache variable
    // The LinkedHashMap is just so the values come back in a predictable order
    protected Map<String, CourseOfferingInfo> courseOfferingMap = new LinkedHashMap<String, CourseOfferingInfo>();

    @Override
    public CourseOfferingInfo updateCourseOffering(String courseOfferingId,
                                                   CourseOfferingInfo courseOfferingInfo, ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException,
            ReadOnlyException, VersionMismatchException {
        // update
        if (!courseOfferingId.equals(courseOfferingInfo.getId())) {
            throw new InvalidParameterException(
                    "The id parameter does not match the id on the info object");
        }
        CourseOfferingInfo copy = new CourseOfferingInfo(courseOfferingInfo);
        CourseOfferingInfo old = this.getCourseOffering(
                courseOfferingInfo.getId(), context);
        if (!old.getMeta().getVersionInd()
                .equals(copy.getMeta().getVersionInd())) {
            throw new VersionMismatchException(old.getMeta().getVersionInd());
        }
        copy.setMeta(updateMeta(copy.getMeta(), context));
        this.courseOfferingMap.put(courseOfferingInfo.getId(), copy);
        return new CourseOfferingInfo(copy);
    }

    @Override
    public CourseOfferingInfo updateCourseOfferingFromCanonical(
            String courseOfferingId, List<String> optionKeys,
            ContextInfo context) throws DataValidationErrorException,
            DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException, VersionMismatchException {
        return this.businessLogic.updateCourseOfferingFromCanonical(
                courseOfferingId, optionKeys, context);
    }

    @Override
    public StatusInfo deleteCourseOffering(String courseOfferingId,
                                           ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, DependentObjectsExistException {

        for (FormatOfferingInfo fo: formatOfferingMap.values()) {
            // See if any format offerings are still connected to COs
            if (fo.getCourseOfferingId().equals(courseOfferingId)) {
                throw new DependentObjectsExistException("Format offering still attached to CO (" + courseOfferingId + ")");
            }
        }
        if (this.courseOfferingMap.remove(courseOfferingId) == null) {
            throw new DoesNotExistException(courseOfferingId);
        }
        return successStatus();
    }

    @Override
    public List<ValidationResultInfo> validateCourseOffering(
            String validationType, CourseOfferingInfo courseOfferingInfo,
            ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException {
        // validate
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    public List<ValidationResultInfo> validateCourseOfferingFromCanonical(
            CourseOfferingInfo courseOfferingInfo, List<String> optionKeys,
            ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return this.businessLogic.validateCourseOfferingFromCanonical(
                courseOfferingInfo, optionKeys, context);
    }

    @Override
    public FormatOfferingInfo getFormatOffering(String formatOfferingId,
                                                ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        if (!this.formatOfferingMap.containsKey(formatOfferingId)) {
            throw new DoesNotExistException(formatOfferingId);
        }
        return this.formatOfferingMap.get(formatOfferingId);
    }

    @Override
    public List<FormatOfferingInfo> getFormatOfferingsByCourseOffering(
            String courseOfferingId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
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
    public FormatOfferingInfo createFormatOffering(String courseOfferingId,
                                                   String formatId, String formatOfferingType,
                                                   FormatOfferingInfo formatOfferingInfo, ContextInfo context)
            throws DoesNotExistException, DataValidationErrorException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException,
            ReadOnlyException {
        CourseOfferingInfo co = this.getCourseOffering(courseOfferingId,
                context);
        // create
        if (!courseOfferingId.equals(formatOfferingInfo.getCourseOfferingId())) {
            throw new InvalidParameterException(
                    "The course offering id parameter does not match the course offering id on the info object");
        }
        if (!formatId.equals(formatOfferingInfo.getFormatId())) {
            throw new InvalidParameterException(
                    "The format id parameter does not match the format id on the info object");
        }
        if (!formatOfferingType.equals(formatOfferingInfo.getTypeKey())) {
            throw new InvalidParameterException(
                    "The type parameter does not match the type on the info object");
        }
        // TODO: check the rest of the readonly fields that are specified on the
        // create to make sure they match the info object
        FormatOfferingInfo copy = new FormatOfferingInfo(formatOfferingInfo);
        if (copy.getId() == null) {
            copy.setId(UUIDHelper.genStringUUID());
        }
        copy.setTermId(co.getTermId());
        copy.setMeta(newMeta(context));
        formatOfferingMap.put(copy.getId(), copy);
        log.debug("CourseOfferingMockImpl: created format offering: "
                + copy.getId() + "term=" + copy.getTermId() + " for format ="
                + copy.getFormatId() + " and course offering="
                + copy.getCourseOfferingId());
        return new FormatOfferingInfo(copy);
    }

    @Override
    public FormatOfferingInfo updateFormatOffering(String formatOfferingId,
                                                   FormatOfferingInfo formatOfferingInfo, ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException,
            ReadOnlyException, VersionMismatchException {
        // update
        if (!formatOfferingId.equals(formatOfferingInfo.getId())) {
            throw new InvalidParameterException(
                    "The id parameter does not match the id on the info object");
        }
        FormatOfferingInfo copy = new FormatOfferingInfo(formatOfferingInfo);
        FormatOfferingInfo old = this.getFormatOffering(
                formatOfferingInfo.getId(), context);
        if (!old.getMeta().getVersionInd()
                .equals(copy.getMeta().getVersionInd())) {
            throw new VersionMismatchException(old.getMeta().getVersionInd());
        }
        copy.setMeta(updateMeta(copy.getMeta(), context));
        this.formatOfferingMap.put(formatOfferingInfo.getId(), copy);
        return new FormatOfferingInfo(copy);
    }

    @Override
    public List<ValidationResultInfo> validateFormatOffering(
            String validationType, FormatOfferingInfo formatOfferingInfo,
            ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException {
        // validate
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    public StatusInfo deleteFormatOffering(String formatOfferingId,
                                           ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException,
            DependentObjectsExistException {
        for (ActivityOfferingInfo aoInfo: activityOfferingMap.values()) {
            // test if AOs still attached to FO, if so, throw dependent object exists exception
            if (aoInfo.getFormatOfferingId().equals(formatOfferingId)) {
                throw new DependentObjectsExistException("Activity offerings still attached to FO (" + formatOfferingId + ")");
            }
        }
        if (this.formatOfferingMap.remove(formatOfferingId) == null) {
            throw new DoesNotExistException(formatOfferingId);
        }
        return successStatus();
    }

    @Override
    public TypeInfo getActivityOfferingType(String activityOfferingTypeKey,
                                            ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return typeService.getType(activityOfferingTypeKey, context);
    }

    @Override
    public List<TypeInfo> getActivityOfferingTypes(ContextInfo context)
            throws InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        try {
            return typeService.getTypesForGroupType(
                    LuiServiceConstants.ACTIVITY_OFFERING_GROUP_TYPE_KEY,
                    context);
        } catch (DoesNotExistException e) {
            throw new OperationFailedException(
                    "Invalid group type used to retrieve Activity Offering Types: "
                            + LuiServiceConstants.ACTIVITY_OFFERING_GROUP_TYPE_KEY);
        }
    }

    @Override
    public List<TypeInfo> getActivityOfferingTypesForActivityType(
            String activityTypeKey, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return typeService.getAllowedTypesForType(activityTypeKey, context);
    }

    @Override
    public List<TypeInfo> getInstructorTypesForActivityOfferingType(
            String activityOfferingTypeKey, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        throw new OperationFailedException("not implemented");
    }

    @Override
    public ActivityOfferingInfo getActivityOffering(String activityOfferingId,
                                                    ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        if (!this.activityOfferingMap.containsKey(activityOfferingId)) {
            throw new DoesNotExistException(activityOfferingId);
        }
        return this.activityOfferingMap.get(activityOfferingId);
    }

    @Override
    public List<ActivityOfferingInfo> getActivityOfferingsByIds(
            List<String> activityOfferingIds, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        List<ActivityOfferingInfo> list = new ArrayList<ActivityOfferingInfo>();
        for (String id : activityOfferingIds) {
            list.add(this.getActivityOffering(id, context));
        }
        return list;
    }

    @Override
    public List<ActivityOfferingInfo> getActivityOfferingsByCourseOffering(
            String courseOfferingId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        List<ActivityOfferingInfo> list = new ArrayList<ActivityOfferingInfo>();
        for (ActivityOfferingInfo info : activityOfferingMap.values()) {

            if (courseOfferingId.equals(info.getCourseOfferingId())) {
                list.add(info);
            }
        }

        return list;
    }

    @Override
    public List<ActivityOfferingInfo> getActivityOfferingsByFormatOffering(
            String formatOfferingId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
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
    public List<ActivityOfferingInfo> getActivityOfferingsWithoutClusterByFormatOffering(String formatOfferingId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
    
        List<ActivityOfferingInfo> aos = getActivityOfferingsByFormatOffering(formatOfferingId, contextInfo);
        
        Map<String, ActivityOfferingInfo> aoMap = new HashMap<String, ActivityOfferingInfo>();
        
        for (ActivityOfferingInfo activityOfferingInfo : aos) {
            
            aoMap.put(activityOfferingInfo.getId(), activityOfferingInfo);
        
        }
        
        List<ActivityOfferingClusterInfo> aocs = getActivityOfferingClustersByFormatOffering(formatOfferingId, contextInfo);
    
        for (ActivityOfferingClusterInfo activityOfferingClusterInfo : aocs) {
            
            for (ActivityOfferingSetInfo aoSet : activityOfferingClusterInfo.getActivityOfferingSets()) {
                
                for (String aoId : aoSet.getActivityOfferingIds()) {
                    
                    aoMap.remove(aoId);
                }
            }
        }
        
        return new ArrayList<ActivityOfferingInfo>(aoMap.values());
    }

    @Override
    public List<ActivityOfferingInfo> getActivityOfferingsByFormatOfferingWithoutRegGroup(String formatOfferingId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("unsupported");
    }

    // cache variable
    // The LinkedHashMap is just so the values come back in a predictable order
    private Map<String, ActivityOfferingInfo> activityOfferingMap = new LinkedHashMap<String, ActivityOfferingInfo>();

    @Override
    public ActivityOfferingInfo createActivityOffering(String formatOfferingId,
                                                       String activityId, String activityOfferingTypeKey,
                                                       ActivityOfferingInfo activityOfferingInfo, ContextInfo context)
            throws DoesNotExistException, DataValidationErrorException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException,
            ReadOnlyException {
        FormatOfferingInfo fo = this.getFormatOffering(formatOfferingId,
                context);
        // create
        if (!formatOfferingId
                .equals(activityOfferingInfo.getFormatOfferingId())) {
            throw new InvalidParameterException(
                    "The format offering id parameter does not match the format offering id on the info object");
        }
        if (!activityId.equals(activityOfferingInfo.getActivityId())) {
            throw new InvalidParameterException(
                    "The activity id parameter does not match the activity id on the info object");
        }
        if (!activityOfferingTypeKey.equals(activityOfferingInfo.getTypeKey())) {
            throw new InvalidParameterException(
                    "The type parameter does not match the type on the info object");
        }
        // TODO: check the rest of the readonly fields that are specified on the
        // create to make sure they match the info object
        ActivityOfferingInfo copy = new ActivityOfferingInfo(
                activityOfferingInfo);
        if (copy.getId() == null) {
            copy.setId(UUIDHelper.genStringUUID());
        }
        copy.setTermId(fo.getTermId());
        copy.setMeta(newMeta(context));
        activityOfferingMap.put(copy.getId(), copy);
        log.debug("CourseOfferingMockImpl: created activity offering: "
                + copy.getId() + "term=" + copy.getTermId()
                + " for activity " + copy.getActivityId()
                + " and format offering=" + copy.getFormatOfferingId());
        return new ActivityOfferingInfo(copy);
    }

    @Override
    public ActivityOfferingInfo copyActivityOffering(String activityOfferingId,
                                                     ContextInfo context) throws DoesNotExistException,
            DataValidationErrorException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException, ReadOnlyException {
        throw new OperationFailedException(
                "copyActivityOffering has not been implemented");
    }

    @Override
    public List<ActivityOfferingInfo> generateActivityOfferings(
            String formatOfferingId, String activityOfferingType,
            Integer quantity, ContextInfo context)
            throws InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException(
                "generateActivityOfferings has not been implemented");
    }

    @Override
    public ActivityOfferingInfo updateActivityOffering(
            String activityOfferingId,
            ActivityOfferingInfo activityOfferingInfo, ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException,
            VersionMismatchException, ReadOnlyException {
        // update
        if (!activityOfferingId.equals(activityOfferingInfo.getId())) {
            throw new InvalidParameterException(
                    "The id parameter does not match the id on the info object");
        }
        ActivityOfferingInfo copy = new ActivityOfferingInfo(
                activityOfferingInfo);
        ActivityOfferingInfo old = this.getActivityOffering(
                activityOfferingInfo.getId(), context);
        if (!old.getMeta().getVersionInd()
                .equals(copy.getMeta().getVersionInd())) {
            throw new VersionMismatchException(old.getMeta().getVersionInd());
        }
        copy.setMeta(updateMeta(copy.getMeta(), context));
        this.activityOfferingMap.put(activityOfferingInfo.getId(), copy);
        return new ActivityOfferingInfo(copy);
    }

    @Override
    public StatusInfo deleteActivityOffering(String activityOfferingId,
                                             ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, DependentObjectsExistException {

        for (RegistrationGroupInfo rg : this.registrationGroupMap.values()) {

            if (rg.getActivityOfferingIds().contains(activityOfferingId))
                throw new DependentObjectsExistException("Registration Groups Exist for Activity id = " + activityOfferingId);
        }

        List<String> seatpoolIds =  activityOfferingToSeatPoolMap.get(activityOfferingId);
        if (seatpoolIds != null && !seatpoolIds.isEmpty()) {
            throw new DependentObjectsExistException("Seatpools exists for Activity id = " + activityOfferingId);
        }

        if (this.activityOfferingMap.remove(activityOfferingId) == null) {
            throw new DoesNotExistException(activityOfferingId);
        }
        return successStatus();
    }


    @Override
    public StatusInfo deleteActivityOfferingCascaded(String activityOfferingId,
                                                     ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {

        // delete seat pool registrations

        List<SeatPoolDefinitionInfo> spls = getSeatPoolDefinitionsForActivityOffering(activityOfferingId, context);
        List<String> seatpoolIds = new ArrayList<String>();
        for (SeatPoolDefinitionInfo spInfo: spls) {
            seatpoolIds.add(spInfo.getId());
        }
        // Delete the attachments from AOs to seatpools
        List<String> fetchedIds = activityOfferingToSeatPoolMap.get(activityOfferingId);
        if (fetchedIds != null) {
            fetchedIds.removeAll(seatpoolIds); // Get rid of seatpool IDs in this association
        }

        for (SeatPoolDefinitionInfo spl : spls) {

            StatusInfo status = deleteSeatPoolDefinition(spl.getId(), context);

            if (!status.getIsSuccess())
                throw new OperationFailedException(status.getMessage());

        }


        // delete registration groups

        // intentionally separated to avoid a concurrent modification exception on delete.
        ArrayList<RegistrationGroupInfo> rgs = new ArrayList<RegistrationGroupInfo>(this.registrationGroupMap.values());

        for (RegistrationGroupInfo rg : rgs) {

            if (rg.getActivityOfferingIds().contains(activityOfferingId)) {
                StatusInfo status = deleteRegistrationGroup(rg.getId(), context);

                if (!status.getIsSuccess()) {
                    throw new OperationFailedException(status.getMessage());
                }
            }
        }

        // Remove the AO from the AOC
        for (ActivityOfferingClusterInfo cluster: this.activityOfferingClusterMap.values()) {
            for (ActivityOfferingSetInfo set: cluster.getActivityOfferingSets()) {
                if (set.getActivityOfferingIds().contains(activityOfferingId)) {
                    set.getActivityOfferingIds().remove(activityOfferingId);
                }
            }
        }

        // delete activity offering
        try {
            return deleteActivityOffering(activityOfferingId, context);
        } catch (DependentObjectsExistException e) {
            throw new OperationFailedException("Dependent object still exists for Activity Offering with id = " + activityOfferingId, e);
        }
    }


    @Override
    public StatusInfo scheduleActivityOffering(String activityOfferingId,
                                               ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("implement for M5");
    }

    @Override
    public List<ValidationResultInfo> validateActivityOffering(
            String validationType, ActivityOfferingInfo activityOfferingInfo,
            ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException {
        // validate
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    public Float calculateInClassContactHoursForTerm(String activityOfferingId,
                                                     ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException(
                "calculateInClassContactHoursForTerm has not been implemented");
    }

    @Override
    public Float calculateOutofClassContactHoursForTerm(
            String activityOfferingId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        throw new OperationFailedException(
                "calculateOutofClassContactHoursForTerm has not been implemented");
    }

    @Override
    public Float calculateTotalContactHoursForTerm(String activityOfferingId,
                                                   ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException(
                "calculateTotalContactHoursForTerm has not been implemented");
    }

    @Override
    public RegistrationGroupInfo getRegistrationGroup(
            String registrationGroupId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        if (!this.registrationGroupMap.containsKey(registrationGroupId)) {
            throw new DoesNotExistException(registrationGroupId);
        }
        return this.registrationGroupMap.get(registrationGroupId);
    }

    @Override
    public List<RegistrationGroupInfo> getRegistrationGroupsByIds(
            List<String> registrationGroupIds, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        List<RegistrationGroupInfo> list = new ArrayList<RegistrationGroupInfo>();
        for (String id : registrationGroupIds) {
            list.add(this.getRegistrationGroup(id, context));
        }
        return list;
    }

    @Override
    public List<RegistrationGroupInfo> getRegistrationGroupsForCourseOffering(
            String courseOfferingId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        List<RegistrationGroupInfo> regGroupList = new ArrayList<RegistrationGroupInfo>();

        for (RegistrationGroupInfo rg : this.registrationGroupMap.values()) {
            if (rg.getCourseOfferingId().equals(courseOfferingId)) {
                regGroupList.add(rg);
            }
        }

        return regGroupList;
    }

    @Override
    public List<RegistrationGroupInfo> getRegistrationGroupsWithActivityOfferings(
            List<String> activityOfferingIds, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        List<RegistrationGroupInfo> regGroupList = new ArrayList<RegistrationGroupInfo>();

        for (RegistrationGroupInfo rg : this.registrationGroupMap.values()) {
            if (CollectionUtils.isSubCollection(activityOfferingIds,
                    rg.getActivityOfferingIds())) {
                regGroupList.add(rg);
            }
        }

        return regGroupList;

    }

    @Override
    public List<RegistrationGroupInfo> getRegistrationGroupsByActivityOffering(@WebParam(name = "activityOfferingId") String activityOfferingId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<RegistrationGroupInfo> regGroupList = new ArrayList<RegistrationGroupInfo>();

        for (RegistrationGroupInfo rg : this.registrationGroupMap.values()) {
            if (rg.getActivityOfferingIds().contains(activityOfferingId)) {
                regGroupList.add(rg);
            }
        }

        return regGroupList;
    }

    @Override
    public List<RegistrationGroupInfo> getRegistrationGroupsByFormatOffering(
            String formatOfferingId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        List<RegistrationGroupInfo> regGroupList = new ArrayList<RegistrationGroupInfo>();

        for (RegistrationGroupInfo rg : this.registrationGroupMap.values()) {

            if (rg.getFormatOfferingId().equals(formatOfferingId))
                regGroupList.add(rg);
        }

        return regGroupList;

    }

    // cache variable
    // The LinkedHashMap is just so the values come back in a predictable order
    private Map<String, RegistrationGroupInfo> registrationGroupMap = new LinkedHashMap<String, RegistrationGroupInfo>();


    @Override
    public RegistrationGroupInfo updateRegistrationGroup(
            String registrationGroupId,
            RegistrationGroupInfo registrationGroupInfo, ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException,
            ReadOnlyException, VersionMismatchException {
        // update
        if (!registrationGroupId.equals(registrationGroupInfo.getId())) {
            throw new InvalidParameterException(
                    "The id parameter does not match the id on the info object");
        }
        RegistrationGroupInfo copy = new RegistrationGroupInfo(
                registrationGroupInfo);
        RegistrationGroupInfo old = this.getRegistrationGroup(
                registrationGroupInfo.getId(), context);
        if (!old.getMeta().getVersionInd()
                .equals(copy.getMeta().getVersionInd())) {
            throw new VersionMismatchException(old.getMeta().getVersionInd());
        }
        copy.setMeta(updateMeta(copy.getMeta(), context));
        this.registrationGroupMap.put(registrationGroupInfo.getId(), copy);
        return new RegistrationGroupInfo(copy);
    }

    @Override
    public StatusInfo deleteRegistrationGroup(String registrationGroupId,
                                              ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        if (this.registrationGroupMap.remove(registrationGroupId) == null) {
            throw new DoesNotExistException(registrationGroupId);
        }
        return successStatus();
    }

    
  private BulkStatusInfo bulkDeleteRegistrationGroup(RegistrationGroupInfo regGroup, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        
        StatusInfo status = deleteRegistrationGroup(regGroup.getId(), context);

        BulkStatusInfo bulkStatus = new BulkStatusInfo();
        
        bulkStatus.setId(regGroup.getId());
        bulkStatus.setSuccess(status.getIsSuccess());
        bulkStatus.setMessage("Registration Group Deleted");
        
        return bulkStatus;
    }
    
    @Override
    public List<BulkStatusInfo> deleteRegistrationGroupsByFormatOffering(
            String formatOfferingId, ContextInfo context)
            throws InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {

        List<BulkStatusInfo> rgChanges = new ArrayList<BulkStatusInfo>();
        List<RegistrationGroupInfo> rgs;
        try {
            rgs = getRegistrationGroupsByFormatOffering(formatOfferingId, context);

            for (RegistrationGroupInfo rg : rgs) {

                   rgChanges.add(bulkDeleteRegistrationGroup(rg, context));
            }

        } catch (DoesNotExistException e) {
            throw new OperationFailedException("deleteRegistrationGroupsByFormatOffering (formatOfferingId=" + formatOfferingId + "): failed.", e);
        }

        return rgChanges;
    }

    @Override
    public List<BulkStatusInfo>  deleteGeneratedRegistrationGroupsByFormatOffering(
            String formatOfferingId, ContextInfo context)
            throws InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {

        List<RegistrationGroupInfo> rgs;
        List<BulkStatusInfo> rgChanges = new ArrayList<BulkStatusInfo>();
        
        
        try {
            rgs = getRegistrationGroupsByFormatOffering(formatOfferingId, context);

            for (RegistrationGroupInfo rg : rgs) {

                if (rg.getIsGenerated()) {
                    rgChanges.add(bulkDeleteRegistrationGroup(rg, context));
                }

            }

        } catch (DoesNotExistException e) {
            throw new OperationFailedException("deleteGeneratedRegistrationGroupsByFormatOffering (formatOfferingId=" + formatOfferingId + "): failed", e);
        }


        return rgChanges;
    }

    @Override
    public List<BulkStatusInfo> deleteRegistrationGroupsForCluster(String activityOfferingClusterId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        // copy list to avoid concurrent modification exceptions
        Collection<RegistrationGroupInfo>groups = new ArrayList<RegistrationGroupInfo> (this.registrationGroupMap.values());
        
        List<BulkStatusInfo> rgChanges = new ArrayList<BulkStatusInfo>();
        
       for (RegistrationGroupInfo rg : groups) {
        
            if (rg.getActivityOfferingClusterId().equals(activityOfferingClusterId)) {
                
                try {
                    rgChanges.add(bulkDeleteRegistrationGroup(rg, contextInfo));
                } catch (DoesNotExistException e) {
                    throw new OperationFailedException("Bulk Delete Failed", e);
                }
            }
        }
                
        return rgChanges;
        
        
        }

   

    @Override
    public List<ValidationResultInfo> verifyRegistrationGroup(String registrationGroupId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return Collections.emptyList();
    }

    @Override
    public List<ValidationResultInfo> validateRegistrationGroup(
            String validationType, String activityOfferingClusterId, String registrationGroupType, RegistrationGroupInfo registrationGroupInfo,
            ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException {
        // validate
        // this is actually done at the ValidationDecorator layer
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    public ActivityOfferingClusterInfo getActivityOfferingCluster(
            String activityOfferingClusterId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        ActivityOfferingClusterInfo aoc = this.activityOfferingClusterMap
                .get(activityOfferingClusterId);

        if (aoc == null)
            throw new DoesNotExistException(
                    "No ActivityOfferingCluster for id = "
                            + activityOfferingClusterId);

        return new ActivityOfferingClusterInfo(aoc);

    }

    @Override
    public List<ActivityOfferingClusterInfo> getActivityOfferingClustersByFormatOffering(String formatOfferingId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<ActivityOfferingClusterInfo> clusters = new ArrayList<ActivityOfferingClusterInfo>();
        for (ActivityOfferingClusterInfo info: this.activityOfferingClusterMap.values()) {
            if (info.getFormatOfferingId().equals(formatOfferingId)) {
                clusters.add(info);
            }
        }
        return clusters;
    }


    @Override
    public List<ValidationResultInfo> validateActivityOfferingCluster(
            String validationTypeKey,
            String formatOfferingId,
            ActivityOfferingClusterInfo activityOfferingClusterInfo,
             ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException {
        // Note: validation is handled in the CourseOfferingServiceValidationDecorator
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    public StatusInfo deleteActivityOfferingClusterCascaded(
            String activityOfferingClusterId,
             ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        List<RegistrationGroupInfo> rgList = getRegistrationGroupsByActivityOfferingCluster(activityOfferingClusterId, contextInfo);

        for (RegistrationGroupInfo rg : rgList) {
            deleteRegistrationGroup(rg.getId(), contextInfo);
        }

        try {
            deleteActivityOfferingCluster(activityOfferingClusterId, contextInfo);
        } catch (DependentObjectsExistException e) {
            throw new OperationFailedException("unexpected reg group exists for activityOfferingCluster = " + activityOfferingClusterId);
        }

        return successStatus();
    }

    private Map<String, ActivityOfferingClusterInfo> activityOfferingClusterMap = new LinkedHashMap<String, ActivityOfferingClusterInfo>();

    private void _createAOSets(FormatOfferingInfo foInfo, ActivityOfferingClusterInfo clusterInfo) {
        if (clusterInfo.getActivityOfferingSets() == null) {
            // Shouldn't be necessary, but just in case.
            clusterInfo.setActivityOfferingSets(new ArrayList<ActivityOfferingSetInfo>());
        }
        List<ActivityOfferingSetInfo> setInfos = clusterInfo.getActivityOfferingSets();
        List<String> aoTypeKeys = foInfo.getActivityOfferingTypeKeys();
        if (aoTypeKeys != null) {
            for (String aoTypeKey: aoTypeKeys) {
                // Create an AOSetInfo
                ActivityOfferingSetInfo setInfo = new ActivityOfferingSetInfo();
                setInfo.setActivityOfferingType(aoTypeKey);
                setInfo.setActivityOfferingIds(new ArrayList<String>()); // leave it empty for now
                // Add it to the list
                setInfos.add(setInfo);
            }
        }
    }

    @Override
    public ActivityOfferingClusterInfo createActivityOfferingCluster(String formatOfferingId,
                                                                     String activityOfferingClusterTypeKey, ActivityOfferingClusterInfo activityOfferingClusterInfo,
                                                                     ContextInfo contextInfo)
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException {

        ActivityOfferingClusterInfo copy = new ActivityOfferingClusterInfo(
                activityOfferingClusterInfo);

        if (copy.getActivityOfferingSets().isEmpty()) {
            FormatOfferingInfo foInfo = getFormatOffering(formatOfferingId, contextInfo);
            _createAOSets(foInfo, copy);
        }

        if (copy.getId() == null) {
            copy.setId(UUIDHelper.genStringUUID());
        }

        copy.setMeta(newMeta(contextInfo));

        activityOfferingClusterMap.put(copy.getId(), copy);

        return copy;

    }

    @Override
    public AOClusterVerifyResultsInfo verifyActivityOfferingClusterForGeneration(String activityOfferingClusterId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // This is the same implementation as the CourseOfferingServiceImpl.  (It had been unimplemented).
        // TODO: Find some way to resuse the COSI impl.
        AOClusterVerifyResultsInfo aoClusterVerifyResultsInfo = new AOClusterVerifyResultsInfo();
        List<ValidationResultInfo> validationResultInfos = new ArrayList<ValidationResultInfo>() ;
        ValidationResultInfo validationResultInfo = new ValidationResultInfo();

        try {
            ActivityOfferingClusterInfo aoCInfo = getActivityOfferingCluster(activityOfferingClusterId, contextInfo);
            List<ActivityOfferingSetInfo> aoSetInfos = aoCInfo.getActivityOfferingSets();

            for (ActivityOfferingSetInfo aoSetInfo : aoSetInfos ){
                List<String> aoIdList = aoSetInfo.getActivityOfferingIds();
                if (aoIdList == null || aoIdList.isEmpty()) {
                    //invalidValidationInfo.setError("");
                    validationResultInfo.setLevel(ValidationResult.ErrorLevel.ERROR);
                    validationResultInfos.add(validationResultInfo);
                    aoClusterVerifyResultsInfo.setValidationResults(validationResultInfos);

                    return aoClusterVerifyResultsInfo;
                }
            }
        } catch (Exception ex) {
            throw new OperationFailedException("unexpected", ex);
        }

        validationResultInfo.setLevel(ValidationResult.ErrorLevel.OK);
        validationResultInfos.add(validationResultInfo);
        aoClusterVerifyResultsInfo.setValidationResults(validationResultInfos);

        return aoClusterVerifyResultsInfo;
    }


    @Override
    public ActivityOfferingClusterInfo updateActivityOfferingCluster(String formatOfferingId, String activityOfferingClusterId,
                                                                     ActivityOfferingClusterInfo activityOfferingClusterInfo, ContextInfo contextInfo)
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException,
            VersionMismatchException {

        // update
        if (!activityOfferingClusterId.equals(activityOfferingClusterInfo.getId())) {
            throw new InvalidParameterException(
                    "The id parameter does not match the id on the info object");
        }
        ActivityOfferingClusterInfo copy = new ActivityOfferingClusterInfo(activityOfferingClusterInfo);
        ActivityOfferingClusterInfo old = this.getActivityOfferingCluster(
                activityOfferingClusterId, contextInfo);
        // Figure out IDs that appear in old, but not in copy.
        Set<String> oldIds = new HashSet<String>(); // First the old Ids
        for (ActivityOfferingSetInfo set: old.getActivityOfferingSets()) {
            oldIds.addAll(set.getActivityOfferingIds());
        }
        Set<String> copyIds = new HashSet<String>(); // First the old Ids
        for (ActivityOfferingSetInfo set: copy.getActivityOfferingSets()) {
            copyIds.addAll(set.getActivityOfferingIds());
        }
        oldIds.removeAll(copyIds);
        for (String aoId: oldIds) {
            // Find any RGs that contain these aoIds
            for (Map.Entry<String, RegistrationGroupInfo> entry: this.registrationGroupMap.entrySet()) {
                if (entry.getValue().getFormatOfferingId().equals(formatOfferingId) &&
                        entry.getValue().getActivityOfferingIds().contains(aoId)) {
                    // Delete RG with this ao id
                    this.registrationGroupMap.remove(entry.getKey());
                }
            }
        }

        if (!old.getMeta().getVersionInd()
                .equals(copy.getMeta().getVersionInd())) {
            throw new VersionMismatchException(old.getMeta().getVersionInd());
        }
        copy.setMeta(updateMeta(copy.getMeta(), contextInfo));
        this.activityOfferingClusterMap.put(activityOfferingClusterInfo.getId(), copy);

        return new ActivityOfferingClusterInfo(copy);

    }

    @Override
    public StatusInfo deleteActivityOfferingCluster(
            String activityOfferingClusterId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException, DependentObjectsExistException {

        // note validation is in the validation decorator.
        ActivityOfferingClusterInfo aoc = this.activityOfferingClusterMap.remove(activityOfferingClusterId);

        if (aoc == null)
            throw new DoesNotExistException("No ActivityOfferingCluster for Id = " + activityOfferingClusterId);

        return successStatus();

    }

    @Override
    public SeatPoolDefinitionInfo getSeatPoolDefinition(
            String seatPoolDefinitionId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        if (!this.seatPoolDefinitionMap.containsKey(seatPoolDefinitionId)) {
            throw new DoesNotExistException(seatPoolDefinitionId);
        }
        return this.seatPoolDefinitionMap.get(seatPoolDefinitionId);
    }

    @Override
    public List<SeatPoolDefinitionInfo> getSeatPoolDefinitionsForActivityOffering(
            String activityOfferingId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        // will throw does not exist exception if there is no matching activity id
        ActivityOfferingInfo ao = getActivityOffering(activityOfferingId, context);

        List<String> seatPoolIds = activityOfferingToSeatPoolMap.get(activityOfferingId);

        if (seatPoolIds == null)
            return new ArrayList<SeatPoolDefinitionInfo>();

        List<SeatPoolDefinitionInfo> seatPoolInfos = new ArrayList<SeatPoolDefinitionInfo>(seatPoolIds.size());

        for (String spId : seatPoolIds) {

            SeatPoolDefinitionInfo sp = getSeatPoolDefinition(spId, context);

            seatPoolInfos.add(sp);
        }

        return seatPoolInfos;


    }

    // cache variable
    // The LinkedHashMap is just so the values come back in a predictable order
    private Map<String, SeatPoolDefinitionInfo> seatPoolDefinitionMap = new LinkedHashMap<String, SeatPoolDefinitionInfo>();

    @Override
    public SeatPoolDefinitionInfo createSeatPoolDefinition(
            SeatPoolDefinitionInfo seatPoolDefinitionInfo, ContextInfo context)
            throws DataValidationErrorException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException, ReadOnlyException {
        // create
        SeatPoolDefinitionInfo copy = new SeatPoolDefinitionInfo(
                seatPoolDefinitionInfo);
        if (copy.getId() == null) {
            copy.setId(UUIDHelper.genStringUUID());
        }
        copy.setMeta(newMeta(context));
        seatPoolDefinitionMap.put(copy.getId(), copy);
        return new SeatPoolDefinitionInfo(copy);
    }

    @Override
    public SeatPoolDefinitionInfo updateSeatPoolDefinition(
            String seatPoolDefinitionId,
            SeatPoolDefinitionInfo seatPoolDefinitionInfo, ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException,
            ReadOnlyException, VersionMismatchException {
        // update
        if (!seatPoolDefinitionId.equals(seatPoolDefinitionInfo.getId())) {
            throw new InvalidParameterException(
                    "The id parameter does not match the id on the info object");
        }
        SeatPoolDefinitionInfo copy = new SeatPoolDefinitionInfo(
                seatPoolDefinitionInfo);
        SeatPoolDefinitionInfo old = this.getSeatPoolDefinition(
                seatPoolDefinitionInfo.getId(), context);
        if (!old.getMeta().getVersionInd()
                .equals(copy.getMeta().getVersionInd())) {
            throw new VersionMismatchException(old.getMeta().getVersionInd());
        }
        copy.setMeta(updateMeta(copy.getMeta(), context));
        this.seatPoolDefinitionMap.put(seatPoolDefinitionInfo.getId(), copy);
        return new SeatPoolDefinitionInfo(copy);
    }

    @Override
    public List<ValidationResultInfo> validateSeatPoolDefinition(
            String validationTypeKey,
            SeatPoolDefinitionInfo seatPoolDefinitionInfo, ContextInfo context)
            throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // validate
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    public StatusInfo deleteSeatPoolDefinition(String seatPoolDefinitionId,
                                               ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        if (this.seatPoolDefinitionMap.remove(seatPoolDefinitionId) == null) {
            throw new DoesNotExistException(seatPoolDefinitionId);
        }
        return successStatus();
    }

    @Override
    public List<CourseOfferingInfo> searchForCourseOfferings(
            QueryByCriteria criteria, ContextInfo context)
            throws InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {

        throw new OperationFailedException(
                "searchForCourseOfferings has not been implemented");
    }

    @Override
    public List<String> searchForCourseOfferingIds(QueryByCriteria criteria,
                                                   ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        throw new OperationFailedException(
                "searchForCourseOfferingIds has not been implemented");
    }

    @Override
    public List<ActivityOfferingInfo> searchForActivityOfferings(
            QueryByCriteria criteria, ContextInfo context)
            throws InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException(
                "searchForActivityOfferings has not been implemented");
    }

    @Override
    public List<String> searchForActivityOfferingIds(QueryByCriteria criteria,
                                                     ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        throw new OperationFailedException(
                "searchForActivityOfferingIds has not been implemented");
    }

    @Override
    public List<RegistrationGroupInfo> searchForRegistrationGroups(
            QueryByCriteria criteria, ContextInfo context)
            throws InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException(
                "searchForRegistrationGroups has not been implemented");
    }

    @Override
    public List<String> searchForRegistrationGroupIds(QueryByCriteria criteria,
                                                      ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        throw new OperationFailedException(
                "searchForRegistrationGroupIds has not been implemented");
    }

    @Override
    public List<SeatPoolDefinitionInfo> searchForSeatpoolDefinitions(
            QueryByCriteria criteria, ContextInfo context)
            throws InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException(
                "searchForSeatpoolDefinitions has not been implemented");
    }

    @Override
    public List<String> searchForSeatpoolDefinitionIds(
            QueryByCriteria criteria, ContextInfo context)
            throws InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException(
                "searchForSeatpoolDefinitionIds has not been implemented");
    }

    @Override
    public CourseOfferingDisplayInfo getCourseOfferingDisplay(
            String courseOfferingId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        CourseOfferingInfo co = this.getCourseOffering(courseOfferingId, context);
        CourseOfferingDisplayInfo info = new CourseOfferingDisplayInfo();
        info.setId(co.getId());
        info.setTypeKey(co.getTypeKey());
        info.setStateKey(co.getStateKey());
        info.setDescr (co.getDescr ());
        info.setCourseId(co.getCourseId());
        info.setTermId(co.getTermId());
        info.setCourseOfferingCode(co.getCourseOfferingCode());
        info.setCourseOfferingTitle(co.getCourseOfferingTitle());
        info.setSubjectArea(co.getSubjectArea());
        TermInfo term = this.acalService.getTerm(co.getTermId(), context);
        info.setTermName(term.getName());
        info.setTermCode(term.getCode());
        info.setGradingOption(new KeyNameInfo(co.getGradingOptionId(), co.getGradingOptionName()));
        info.setCreditOption(new KeyNameInfo(co.getCreditOptionId(), co.getCreditOptionName()));
        TypeInfo type = typeService.getType(co.getTypeKey(), context);
        info.setTypeName(type.getName());
        StateInfo state = stateService.getState(co.getStateKey(), context);
        info.setStateName(state.getName());
        info.setMeta(co.getMeta());
        info.setAttributes(co.getAttributes());
        return info;
    }

    private List<String> calcActivityOfferingTypes(CourseOfferingInfo co, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        List<String> list = new ArrayList<String>();
        for (FormatOfferingInfo fo : this.getFormatOfferingsByCourseOffering(co.getId(), context)) {
            list.addAll(fo.getActivityOfferingTypeKeys());
        }
        return list;
    }

    @Override
    public List<CourseOfferingDisplayInfo> getCourseOfferingDisplaysByIds(
            List<String> courseOfferingIds, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        throw new OperationFailedException(
                "getCourseOfferingDisplayByIds has not been implemented");
    }

    @Override
    public ActivityOfferingDisplayInfo getActivityOfferingDisplay(
            String activityOfferingId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
                MissingParameterException, OperationFailedException,
                PermissionDeniedException {
        ActivityOfferingInfo ao = this.getActivityOffering(activityOfferingId, contextInfo);
        ActivityOfferingDisplayInfo info = new ActivityOfferingDisplayInfo();
        info.setId(ao.getId());
        info.setTypeKey(ao.getTypeKey());
        info.setStateKey(ao.getStateKey());
        info.setName(ao.getName());
        info.setDescr(ao.getDescr());
        TypeInfo type = typeService.getType(ao.getTypeKey(), contextInfo);
        info.setTypeName(type.getName());
        StateInfo state = stateService.getState(ao.getStateKey(), contextInfo);
        info.setStateName(state.getName());
        info.setCourseOfferingTitle(ao.getCourseOfferingTitle());
        info.setCourseOfferingCode(ao.getCourseOfferingCode());
        info.setFormatOfferingId(ao.getFormatOfferingId());
        info.setFormatOfferingName(ao.getFormatOfferingName());
        info.setActivityOfferingCode(ao.getActivityCode());
       
        info.setInstructorId(ao.getInstructors().get(0).getPersonId());
        info.setInstructorName(ao.getInstructors().get(0).getPersonName());

        info.setIsHonorsOffering(ao.getIsHonorsOffering());
        info.setMaximumEnrollment(ao.getMaximumEnrollment());
        //  Get the schedule components from all schedules and put them in a single ScheduleDisplayInfo.
        if (ao.getScheduleIds() != null && ! ao.getScheduleIds().isEmpty()) {
            List<ScheduleDisplayInfo> scheduleDisplays = schedulingService.getScheduleDisplaysByIds(ao.getScheduleIds(), contextInfo);
            List<ScheduleComponentDisplayInfo> scheduleComponentDisplayInfos = new ArrayList<>();
            for (ScheduleDisplayInfo sdi : scheduleDisplays) {
                scheduleComponentDisplayInfos.addAll((List<ScheduleComponentDisplayInfo>) sdi.getScheduleComponentDisplays());
            }
            ScheduleDisplayInfo sdiAggregate = new ScheduleDisplayInfo();
            sdiAggregate.setScheduleComponentDisplays(scheduleComponentDisplayInfos);
            info.setScheduleDisplay(sdiAggregate);
        }
        info.setMeta(ao.getMeta());
        info.setAttributes(ao.getAttributes());
        return info;
    }

    
	@Override
	public List<ActivityOfferingDisplayInfo> getActivityOfferingDisplaysByIds(
			List<String> activityOfferingIds, ContextInfo contextInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		throw new UnsupportedOperationException("Not supported yet");
	}

    @Override
    public List<ActivityOfferingDisplayInfo> getActivityOfferingDisplaysForCourseOffering(
            String courseOfferingId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet");
    }

    @Override
    public List<String> getValidCanonicalCourseToCourseOfferingOptionKeys(
            ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException, ReadOnlyException {
        List<String> options = new ArrayList();
        options.add(CourseOfferingSetServiceConstants.NOT_COURSE_TITLE_OPTION_KEY);
        options.add(CourseOfferingSetServiceConstants.CREDITS_MATCH_SCHEDULED_HOURS_OPTION_KEY);
        return options;
    }

    @Override
    public List<String> getValidRolloverOptionKeys(ContextInfo context)
            throws InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException,
            ReadOnlyException {
        List<String> options = new ArrayList();
        // what courses
        options.add(CourseOfferingSetServiceConstants.STILL_OFFERABLE_OPTION_KEY);
        options.add(CourseOfferingSetServiceConstants.IF_NO_NEW_VERSION_OPTION_KEY);
        options.add(CourseOfferingSetServiceConstants.IGNORE_CANCELLED_OPTION_KEY);
        options.add(CourseOfferingSetServiceConstants.SKIP_IF_ALREADY_EXISTS_OPTION_KEY);
        // what data
        options.add(CourseOfferingSetServiceConstants.USE_CANONICAL_OPTION_KEY);
        options.add(CourseOfferingSetServiceConstants.NO_INSTRUCTORS_OPTION_KEY);
        options.add(CourseOfferingSetServiceConstants.NO_SCHEDULE_OPTION_KEY);
        return options;
    }

    @Override
    public SocRolloverResultItemInfo rolloverCourseOffering(
            String sourceCourseOfferingId, String targetTermId,
            List<String> optionKeys, ContextInfo context)
            throws AlreadyExistsException, DataValidationErrorException,
            DoesNotExistException, DataValidationErrorException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException,
            ReadOnlyException {
        return this.businessLogic.rolloverCourseOffering(
                sourceCourseOfferingId, targetTermId, optionKeys, context);
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

    private StatusInfo successStatus() {
        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);
        return status;
    }

    private StatusInfo failStatus() {
        StatusInfo status = new StatusInfo();
        status.setMessage("Operation Failed");
        status.setSuccess(Boolean.FALSE);
        return status;
    }

    private MetaInfo updateMeta(MetaInfo old, ContextInfo context) {
        MetaInfo meta = new MetaInfo(old);
        meta.setUpdateId(context.getPrincipalId());
        meta.setUpdateTime(new Date());
        meta.setVersionInd((Integer.parseInt(meta.getVersionInd()) + 1) + "");
        return meta;
    }

    private Map<String, List<String>> activityOfferingToSeatPoolMap = new HashMap<String, List<String>>();


    @Override
    public StatusInfo addSeatPoolDefinitionToActivityOffering(
            String seatPoolDefinitionId, String activityOfferingId,
            ContextInfo contextInfo) throws AlreadyExistsException,
            DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        // first check that both the reg group and seat pool exist
        // these will throw does not exist exceptions
        ActivityOfferingInfo ao = getActivityOffering(activityOfferingId, contextInfo);

        SeatPoolDefinitionInfo spd = getSeatPoolDefinition(seatPoolDefinitionId, contextInfo);

        // now check for an existing association
        List<String> seatPoolIds = activityOfferingToSeatPoolMap.get(activityOfferingId);

        if (seatPoolIds == null) {
            seatPoolIds = new ArrayList<String>();
            activityOfferingToSeatPoolMap.put(activityOfferingId, seatPoolIds);
        }

        if (seatPoolIds.contains(seatPoolDefinitionId))
            throw new AlreadyExistsException("registration group (" + activityOfferingId + ") is already associated to seat pool definition (" + seatPoolDefinitionId + ")");

        seatPoolIds.add(seatPoolDefinitionId);

        return successStatus();
    }

    @Override
    public StatusInfo removeSeatPoolDefinitionFromActivityOffering(
            String seatPoolDefinitionId, String activityOfferingId,
            ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {

        // first check that both the reg group and seat pool exist
        // these will throw does not exist exceptions
        ActivityOfferingInfo ao = getActivityOffering(activityOfferingId, contextInfo);

        SeatPoolDefinitionInfo spd = getSeatPoolDefinition(seatPoolDefinitionId, contextInfo);

        getSeatPoolDefinitionsForActivityOffering(activityOfferingId, contextInfo);

        List<String> seatPoolIds = activityOfferingToSeatPoolMap.get(activityOfferingId);

        if (seatPoolIds.remove(seatPoolDefinitionId))
            return successStatus();
        else
            throw new DoesNotExistException("no seatpool association for spId=" + seatPoolDefinitionId + " and activityOfferingId = " + activityOfferingId);

    }

    @Override
    public List<BulkStatusInfo> generateRegistrationGroupsForFormatOffering(
            String formatOfferingId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException, DataValidationErrorException {

        return businessLogic.generateRegistrationGroupsForFormatOffering(formatOfferingId, context);
    }

    @Override
    public List<String> getCourseOfferingIdsByTermAndSubjectArea(String termId,
                                                                 String subjectArea, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
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
    public StatusInfo changeCourseOfferingState(
            String courseOfferingId,
            String nextStateKey,
             ContextInfo contextInfo)
            throws DoesNotExistException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        try {
            /*
                * get won't work because it doesn't return the map bound instance.
                * We need to get that instance ourselves manually.
                */
            CourseOfferingInfo co = this.courseOfferingMap.get(courseOfferingId);

            if (co == null)
                throw new DoesNotExistException("No CourseOffering for id= " + courseOfferingId);

            co.setStateKey(nextStateKey);

            return successStatus();

        } catch (Exception e) {
            throw new OperationFailedException("changeCourseOfferingState (id=" + courseOfferingId + ", nextStateKey=" + nextStateKey, e);
        }
    }

    @Override
    public StatusInfo changeFormatOfferingState(
            String formatOfferingId,
            String nextStateKey,
             ContextInfo contextInfo)
            throws DoesNotExistException, MissingParameterException, OperationFailedException {
        try {
            /*
                * get won't work because it doesn't return the map bound instance.
                * We need to get that instance ourselves manually.
                */
            FormatOfferingInfo fo = this.formatOfferingMap.get(formatOfferingId);

            if (fo == null)
                throw new DoesNotExistException("No FormatOffering for id= " + formatOfferingId);

            fo.setStateKey(nextStateKey);

            return successStatus();

        } catch (Exception e) {
            throw new OperationFailedException("changeFormatOfferingState (id=" + formatOfferingId + ", nextStateKey=" + nextStateKey, e);
        }
    }

    @Override
    public StatusInfo changeActivityOfferingState(
            String activityOfferingId,
            String nextStateKey,
             ContextInfo contextInfo)
            throws DoesNotExistException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        try {
            /*
                * get won't work because it doesn't return the map bound instance.
                * We need to get that instance ourselves manually.
                */
            ActivityOfferingInfo ao = this.activityOfferingMap.get(activityOfferingId);

            if (ao == null)
                throw new DoesNotExistException("No ActivityOffering for id= " + activityOfferingId);

            ao.setStateKey(nextStateKey);

            return successStatus();

        } catch (Exception e) {
            throw new OperationFailedException("changeActivityOfferingState (id=" + activityOfferingId + ", nextStateKey=" + nextStateKey, e);
        }
    }

    @Override
    public StatusInfo changeRegistrationGroupState(
            String registrationGroupId,
            String nextStateKey,
             ContextInfo contextInfo)
            throws DoesNotExistException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        try {
            /*
                * get won't work because it doesn't return the map bound instance.
                * We need to get that instance ourselves manually.
                */
            RegistrationGroupInfo rg = this.registrationGroupMap.get(registrationGroupId);

            if (rg == null)
                throw new DoesNotExistException("No RegistrationGroup for id= " + registrationGroupId);

            rg.setStateKey(nextStateKey);

            return successStatus();

        } catch (Exception e) {
            throw new OperationFailedException("changeRegistrationGroupState (id=" + registrationGroupId + ", nextStateKey=" + nextStateKey, e);
        }
    }

    @Override
    public StatusInfo changeActivityOfferingClusterState(
            String activityOfferingClusterId,
            String nextStateKey,
             ContextInfo contextInfo)
            throws DoesNotExistException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        try {
            /*
                * get won't work because it doesn't return the map bound instance.
                * We need to get that instance ourselves manually.
                */
            ActivityOfferingClusterInfo aoc = this.activityOfferingClusterMap.get(activityOfferingClusterId);

            if (aoc == null)
                throw new DoesNotExistException("No ActivityOfferingCluster for id= " + activityOfferingClusterId);

            aoc.setStateKey(nextStateKey);

            return successStatus();

        } catch (Exception e) {
            throw new OperationFailedException("changeActivityOfferingClusterState (id=" + activityOfferingClusterId + ", nextStateKey=" + nextStateKey, e);
        }
    }

    @Override
    public StatusInfo changeSeatPoolDefinitionState(
            String seatPoolDefinitionId,
            String nextStateKey,
             ContextInfo contextInfo)
            throws DoesNotExistException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        try {
            /*
                * get won't work because it doesn't return the map bound instance.
                * We need to get that instance ourselves manually.
                */
            SeatPoolDefinitionInfo spd = this.seatPoolDefinitionMap.get(seatPoolDefinitionId);

            if (spd == null)
                throw new DoesNotExistException("No SeatPoolDefinition for id= " + seatPoolDefinitionId);

            spd.setStateKey(nextStateKey);

            return successStatus();

        } catch (Exception e) {
            throw new OperationFailedException("changeSeatPoolDefinitionState (id=" + seatPoolDefinitionId + ", nextStateKey=" + nextStateKey, e);
        }
    }

    @Override
    public List<RegistrationGroupInfo> getRegistrationGroupsByActivityOfferingCluster(
            String activityOfferingClusterId,
             ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        List<RegistrationGroupInfo> regGroups = new ArrayList<RegistrationGroupInfo>();

        for (RegistrationGroupInfo regGroup : this.registrationGroupMap.values()) {

            if (regGroup.getActivityOfferingClusterId().equals(activityOfferingClusterId))
                regGroups.add(regGroup);
        }

        return regGroups;
    }

    @Override
    public List<ActivityOfferingInfo> getActivityOfferingsByCluster(
            String activityOfferingClusterId,
             ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        List<ActivityOfferingInfo> aoList = new ArrayList<ActivityOfferingInfo>();

        ActivityOfferingClusterInfo aoc = getActivityOfferingCluster(activityOfferingClusterId, contextInfo);

        for (ActivityOfferingSetInfo aocSet : aoc.getActivityOfferingSets()) {

            List<ActivityOfferingInfo> setAos = getActivityOfferingsByIds(aocSet.getActivityOfferingIds(), contextInfo);

            aoList.addAll(setAos);
        }

        return aoList;
    }

    @Override
    public List<String> getActivityOfferingClustersIdsByFormatOffering(
            String formatOfferingId,
             ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        List<String> aocIdList = new ArrayList<String>();

        for (ActivityOfferingClusterInfo aoc : this.activityOfferingClusterMap.values()) {

            if (aoc.getFormatOfferingId().equals(formatOfferingId)) {
                aocIdList.add(aoc.getId());
            }
        }
        return aocIdList;

    }

    
    @Override
    public List<BulkStatusInfo> generateRegistrationGroupsForCluster(
            String activityOfferingClusterId,
             ContextInfo contextInfo)
            throws DoesNotExistException, 
            DataValidationErrorException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return businessLogic.generateRegistrationGroupsForCluster(activityOfferingClusterId, contextInfo);
    }

    @Override
    public List<String> searchForActivityOfferingClusterIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<ActivityOfferingClusterInfo> searchForActivityOfferingClusters(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<String> searchForFormatOfferingIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<FormatOfferingInfo> searchForFormatOfferings(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<ActivityOfferingInfo> getActivityOfferingsForSeatPoolDefinition(
            String seatPoolDefinitionId,
             ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        
        List<String> activityOfferingIds = this.activityOfferingToSeatPoolMap.get(seatPoolDefinitionId);

        return getActivityOfferingsByIds(activityOfferingIds, context);
    }

    

}
