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
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.jws.WebParam;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.common.mock.MockService;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.acal.infc.Term;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.class2.courseoffering.service.RegistrationGroupCodeGenerator;
import org.kuali.student.enrollment.class2.courseoffering.service.decorators.R1CourseServiceHelper;
import org.kuali.student.enrollment.class2.courseoffering.service.transformer.CourseOfferingTransformer;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingAdminDisplayInfo;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingAdminDisplayInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.OfferingInstructorInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupTemplateInfo;
import org.kuali.student.enrollment.courseoffering.dto.SeatPoolDefinitionInfo;
import org.kuali.student.enrollment.courseoffering.infc.RegistrationGroup;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingServiceBusinessLogic;
import org.kuali.student.lum.course.dto.CourseInfo;
import org.kuali.student.lum.course.service.CourseService;
import org.kuali.student.r2.common.assembler.AssemblyException;
import org.kuali.student.r2.common.dto.ContextInfo;
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
import org.kuali.student.r2.common.util.constants.AtpServiceConstants;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.common.util.constants.TypeServiceConstants;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.type.dto.TypeInfo;
import org.kuali.student.r2.core.type.service.TypeService;

import edu.emory.mathcs.backport.java.util.Collections;
import org.kuali.student.r2.core.type.dto.TypeTypeRelationInfo;

public class CourseOfferingServiceMockImpl implements CourseOfferingService,
		MockService {

	private static Logger log = Logger
			.getLogger(CourseOfferingServiceMockImpl.class);

	@Resource
	private CourseService courseService;
	
	@Resource
	private AcademicCalendarService acalService;
	
	@Resource(name="coBusinessLogic")
	private CourseOfferingServiceBusinessLogic businessLogic;

	@Resource
	private TypeService typeService;
	
	@Override
	public void clear() {

		this.activityOfferingMap.clear();
		this.courseOfferingMap.clear();
		this.formatOfferingMap.clear();
		this.registrationGroupMap.clear();
		this.seatPoolDefinitionMap.clear();
		
		activityOfferingToSeatPoolMap.clear();
		
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
			String formatOfferingId, String registrationGroupType,
			RegistrationGroupInfo registrationGroupInfo,
			@WebParam(name = "context") ContextInfo context)
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
			copy.setId(registrationGroupMap.size() + "");
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
            copy.setId(courseOfferingMap.size() + "");
        }
        // TODO: move this logic to the calculation decorator do the persistence layer doesn't have this logic mixed in with it
        // copy from cannonical
        CourseInfo courseInfo = new R1CourseServiceHelper (courseService, acalService).getCourse(courseId);
        CourseOfferingTransformer coTransformer = new CourseOfferingTransformer();
        coTransformer.copyFromCanonical(courseInfo, courseOfferingInfo, optionKeys, context);
        copy.setMeta(newMeta(context));
        courseOfferingMap.put(copy.getId(), copy);
        System.out.println ("CourseOfferingMockImpl: created course offering: " + copy.getId () + "term=" + copy.getTermId() + " for course =" + copy.getCourseId());
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
	private Map<String, CourseOfferingInfo> courseOfferingMap = new LinkedHashMap<String, CourseOfferingInfo>();

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
			OperationFailedException {
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
			copy.setId(formatOfferingMap.size() + "");
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
			String formatOfferingId, ContextInfo context)
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
	public List<ActivityOfferingInfo> getActivityOfferingsByFormatOfferingWithoutRegGroup(
			String formatOfferingId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		throw new OperationFailedException(
				"getActivityOfferingTypesForActivityType has not been implemented");
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
			copy.setId(activityOfferingMap.size() + "");
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
		
		// delete activity offering
		try {
			return deleteActivityOffering(activityOfferingId, context);
		} catch (DependentObjectsExistException e) {
			throw new OperationFailedException("Dependent object still exists for Activity Offering with id = " + activityOfferingId, e);
		}
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
			if (rg.getCourseOfferingId().equals(courseOfferingId))
				regGroupList.add(rg);
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

			if (CollectionUtils.isEqualCollection(activityOfferingIds,
					rg.getActivityOfferingIds()))
				regGroupList.add(rg);
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
	public List<RegistrationGroupInfo> generateRegistrationGroupsForTemplate(
			String registrationGroupTemplateId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		throw new OperationFailedException(
				"generateRegistrationGroupsForFormatOffering has not been implemented");
	}

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

	@Override
	public StatusInfo deleteRegistrationGroupsByFormatOffering(
			String formatOfferingId, ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		
		List<RegistrationGroupInfo> rgs;
		try {
			rgs = getRegistrationGroupsByFormatOffering(formatOfferingId, context);
			
			for (RegistrationGroupInfo rg : rgs) {
				
				try {
					deleteRegistrationGroup(rg.getId(), context);
				} catch (DoesNotExistException e) {
					return failStatus();
				}
				
			}
			
		} catch (DoesNotExistException e1) {
			return failStatus();
		}
		
	
		
		return successStatus();
	}

	@Override
	public StatusInfo deleteGeneratedRegistrationGroupsByFormatOffering(
			String formatOfferingId, ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		
		List<RegistrationGroupInfo> rgs;
		try {
			rgs = getRegistrationGroupsByFormatOffering(formatOfferingId, context);
			
			for (RegistrationGroupInfo rg : rgs) {

				if (rg.getIsGenerated()) {
					try {
						deleteRegistrationGroup(rg.getId(), context);
					} catch (DoesNotExistException e) {
						return failStatus();
					}
				}

			}
			
		} catch (DoesNotExistException e1) {
			return failStatus();
		}
		
	
		
		return successStatus();
	}

	@Override
	public StatusInfo deleteGeneratedRegistrationGroupsForTemplate(
			String registrationGroupTemplateId, ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		throw new OperationFailedException("unsupported");
	}

	@Override
	public List<ValidationResultInfo> validateRegistrationGroup(
			String validationType, RegistrationGroupInfo registrationGroupInfo,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// validate
		// this is actually done at the ValidationDecorator layer
		return new ArrayList<ValidationResultInfo>();
	}

	@Override
	public RegistrationGroupTemplateInfo getRegistrationGroupTemplate(
			String registrationGroupTemplateId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// Registration Group Templates are out of scope for M4.
		throw new OperationFailedException(
				"getActivityOfferingTypesForActivityType has not been implemented");
	}

	@Override
	public List<ValidationResultInfo> validateRegistrationGroupTemplate(
			String validationTypeKey,
			RegistrationGroupTemplateInfo registrationGroupTemplateInfo,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// Registration Group Templates are out of scope for M4.
		return new ArrayList<ValidationResultInfo>();
	}

	@Override
	public RegistrationGroupTemplateInfo createRegistrationGroupTemplate(
			RegistrationGroupTemplateInfo registrationGroupTemplateInfo,
			ContextInfo context) throws DataValidationErrorException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			ReadOnlyException {
		// Registration Group Templates are out of scope for M4.
		throw new OperationFailedException("unsupported");
	}

	@Override
	public RegistrationGroupTemplateInfo updateRegistrationGroupTemplate(
			String registrationGroupTemplateId,
			RegistrationGroupTemplateInfo registrationGroupTemplateInfo,
			ContextInfo context) throws DataValidationErrorException,
			DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException, ReadOnlyException,
			VersionMismatchException {
		// Registration Group Templates are out of scope for M4.
		throw new OperationFailedException(
				"getActivityOfferingTypesForActivityType has not been implemented");
	}

	@Override
	public StatusInfo deleteRegistrationGroupTemplate(
			String registrationGroupTemplateId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// Registration Group Templates are out of scope for M4.
		throw new OperationFailedException(
				"getActivityOfferingTypesForActivityType has not been implemented");
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
	
		List<String>seatPoolIds = activityOfferingToSeatPoolMap.get(activityOfferingId);
		
		if (seatPoolIds == null)
			return new ArrayList<SeatPoolDefinitionInfo>();
		
		List<SeatPoolDefinitionInfo>seatPoolInfos = new ArrayList <SeatPoolDefinitionInfo>(seatPoolIds.size());
		
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
			copy.setId(seatPoolDefinitionMap.size() + "");
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
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			VersionMismatchException {
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
	public CourseOfferingAdminDisplayInfo getCourseOfferingAdminDisplay(
			String courseOfferingId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public List<CourseOfferingAdminDisplayInfo> getCourseOfferingAdminDisplaysByIds(
			List<String> courseOfferingIds, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		throw new OperationFailedException(
				"searchForSeatpoolDefinitionIds has not been implemented");
	}

	@Override
	public ActivityOfferingAdminDisplayInfo getActivityOfferingAdminDisplay(
			String activityOfferingId, ContextInfo contextInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		throw new UnsupportedOperationException("Not supported yet");
	}

	@Override
	public List<ActivityOfferingAdminDisplayInfo> getActivityOfferingAdminDisplaysByIds(
			List<String> activityOfferingIds, ContextInfo contextInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		throw new UnsupportedOperationException("Not supported yet");
	}

	@Override
	public List<ActivityOfferingAdminDisplayInfo> getActivityOfferingAdminDisplaysForCourseOffering(
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
	public CourseOfferingInfo rolloverCourseOffering(
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

	private Map<String, List<String>>activityOfferingToSeatPoolMap = new HashMap<String, List<String>>();
	
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
			throw new AlreadyExistsException("registration group (" + activityOfferingId + ") is already associated to seat pool definition ("+seatPoolDefinitionId+")");
		
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
		
		List<String>seatPoolIds = activityOfferingToSeatPoolMap.get(activityOfferingId);
		
		if (seatPoolIds.remove(seatPoolDefinitionId))
			return successStatus();
		else
			throw new DoesNotExistException("no seatpool association for spId=" + seatPoolDefinitionId + " and activityOfferingId = " + activityOfferingId);
		
	}

	@Override
	public List<RegistrationGroupInfo> generateRegistrationGroupsForFormatOffering(
			String formatOfferingId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException, AlreadyExistsException {
		
		return businessLogic.generateRegistrationGroupsForFormatOffering(formatOfferingId, context);
	}
	
	
	
	

    @Override
    public TermInfo getTerm(String termId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        TermInfo termInfo = new TermInfo();
        termInfo.setCode("20122");
        return termInfo;
    }

    @Override
    public List<TypeInfo> getTermTypes(ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;
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
}
