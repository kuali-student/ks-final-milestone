/**
 * Copyright 2012 The Kuali Foundation
 *
 * Licensed under the the Educational Community License, Version 1.0
 * (the "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingAdminDisplayInfo;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingAdminDisplayInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupTemplateInfo;
import org.kuali.student.enrollment.courseoffering.dto.SeatPoolDefinitionInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.r2.common.dto.ContextInfo;
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
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.core.type.dto.TypeInfo;

/**
 * 
 * A mock implementation of the course offering service that is mocked at the
 * class 2 level.
 * 
 * @author ocleirig
 * 
 */
public class CourseOfferingServiceClass2MockImpl implements
		CourseOfferingService {

	private Map<String, CourseOfferingInfo> courseOfferingMap = new HashMap<String, CourseOfferingInfo>();
	private Map<String, FormatOfferingInfo> formatOfferingMap = new HashMap<String, FormatOfferingInfo>();
	private Map<String, ActivityOfferingInfo> activityOfferingMap = new HashMap<String, ActivityOfferingInfo>();

	private Map<String, RegistrationGroupInfo> regFroupMap = new HashMap<String, RegistrationGroupInfo>();

	private Map<String, SeatPoolDefinitionInfo> seatPoolMap = new HashMap<String, SeatPoolDefinitionInfo>();

	/**
	 * 
	 */
	public CourseOfferingServiceClass2MockImpl() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kuali.student.enrollment.courseoffering.service.CourseOfferingService
	 * #getCourseOfferingAdminDisplay(java.lang.String,
	 * org.kuali.student.r2.common.dto.ContextInfo)
	 */
	@Override
	public CourseOfferingAdminDisplayInfo getCourseOfferingAdminDisplay(
			String courseOfferingId, ContextInfo contextInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kuali.student.enrollment.courseoffering.service.CourseOfferingService
	 * #getCourseOfferingAdminDisplaysByIds(java.util.List,
	 * org.kuali.student.r2.common.dto.ContextInfo)
	 */
	@Override
	public List<CourseOfferingAdminDisplayInfo> getCourseOfferingAdminDisplaysByIds(
			List<String> courseOfferingIds, ContextInfo contextInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kuali.student.enrollment.courseoffering.service.CourseOfferingService
	 * #getActivityOfferingAdminDisplay(java.lang.String,
	 * org.kuali.student.r2.common.dto.ContextInfo)
	 */
	@Override
	public ActivityOfferingAdminDisplayInfo getActivityOfferingAdminDisplay(
			String activityOfferingId, ContextInfo contextInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kuali.student.enrollment.courseoffering.service.CourseOfferingService
	 * #getActivityOfferingAdminDisplaysByIds(java.util.List,
	 * org.kuali.student.r2.common.dto.ContextInfo)
	 */
	@Override
	public List<ActivityOfferingAdminDisplayInfo> getActivityOfferingAdminDisplaysByIds(
			List<String> activityOfferingIds, ContextInfo contextInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kuali.student.enrollment.courseoffering.service.CourseOfferingService
	 * #getActivityOfferingAdminDisplaysForCourseOffering(java.lang.String,
	 * org.kuali.student.r2.common.dto.ContextInfo)
	 */
	@Override
	public List<ActivityOfferingAdminDisplayInfo> getActivityOfferingAdminDisplaysForCourseOffering(
			String courseOfferingId, ContextInfo contextInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kuali.student.enrollment.courseoffering.service.CourseOfferingService
	 * #getCourseOfferingType(java.lang.String,
	 * org.kuali.student.r2.common.dto.ContextInfo)
	 */
	@Override
	public TypeInfo getCourseOfferingType(String courseOfferingTypeKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kuali.student.enrollment.courseoffering.service.CourseOfferingService
	 * #getCourseOfferingTypes(org.kuali.student.r2.common.dto.ContextInfo)
	 */
	@Override
	public List<TypeInfo> getCourseOfferingTypes(ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kuali.student.enrollment.courseoffering.service.CourseOfferingService
	 * #getInstructorTypesForCourseOfferingType(java.lang.String,
	 * org.kuali.student.r2.common.dto.ContextInfo)
	 */
	@Override
	public List<TypeInfo> getInstructorTypesForCourseOfferingType(
			String courseOfferingTypeKey, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kuali.student.enrollment.courseoffering.service.CourseOfferingService
	 * #getCourseOffering(java.lang.String,
	 * org.kuali.student.r2.common.dto.ContextInfo)
	 */
	@Override
	public CourseOfferingInfo getCourseOffering(String courseOfferingId,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {

		validateParameter(courseOfferingId);

		CourseOfferingInfo co = courseOfferingMap.get(courseOfferingId);

		validateDoesNotExist(co);

		return co;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kuali.student.enrollment.courseoffering.service.CourseOfferingService
	 * #getCourseOfferingsByIds(java.util.List,
	 * org.kuali.student.r2.common.dto.ContextInfo)
	 */
	@Override
	public List<CourseOfferingInfo> getCourseOfferingsByIds(
			List<String> courseOfferingIds, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kuali.student.enrollment.courseoffering.service.CourseOfferingService
	 * #getCourseOfferingsByCourse(java.lang.String,
	 * org.kuali.student.r2.common.dto.ContextInfo)
	 */
	@Override
	public List<CourseOfferingInfo> getCourseOfferingsByCourse(String courseId,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kuali.student.enrollment.courseoffering.service.CourseOfferingService
	 * #getCourseOfferingsByCourseAndTerm(java.lang.String, java.lang.String,
	 * org.kuali.student.r2.common.dto.ContextInfo)
	 */
	@Override
	public List<CourseOfferingInfo> getCourseOfferingsByCourseAndTerm(
			String courseId, String termId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kuali.student.enrollment.courseoffering.service.CourseOfferingService
	 * #getCourseOfferingIdsByTerm(java.lang.String, java.lang.Boolean,
	 * org.kuali.student.r2.common.dto.ContextInfo)
	 */
	@Override
	public List<String> getCourseOfferingIdsByTerm(String termId,
			Boolean useIncludedTerm, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kuali.student.enrollment.courseoffering.service.CourseOfferingService
	 * #getCourseOfferingIdsByTermAndSubjectArea(java.lang.String,
	 * java.lang.String, org.kuali.student.r2.common.dto.ContextInfo)
	 */
	@Override
	public List<String> getCourseOfferingIdsByTermAndSubjectArea(String termId,
			String subjectArea, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kuali.student.enrollment.courseoffering.service.CourseOfferingService
	 * #getCourseOfferingsByTermAndInstructor(java.lang.String,
	 * java.lang.String, org.kuali.student.r2.common.dto.ContextInfo)
	 */
	@Override
	public List<CourseOfferingInfo> getCourseOfferingsByTermAndInstructor(
			String termId, String instructorId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kuali.student.enrollment.courseoffering.service.CourseOfferingService
	 * #getCourseOfferingIdsByTermAndUnitsContentOwner(java.lang.String,
	 * java.lang.String, org.kuali.student.r2.common.dto.ContextInfo)
	 */
	@Override
	public List<String> getCourseOfferingIdsByTermAndUnitsContentOwner(
			String termId, String unitsContentOwnerId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kuali.student.enrollment.courseoffering.service.CourseOfferingService
	 * #getCourseOfferingIdsByType(java.lang.String,
	 * org.kuali.student.r2.common.dto.ContextInfo)
	 */
	@Override
	public List<String> getCourseOfferingIdsByType(String typeKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kuali.student.enrollment.courseoffering.service.CourseOfferingService
	 * #
	 * getValidCanonicalCourseToCourseOfferingOptionKeys(org.kuali.student.r2.common
	 * .dto.ContextInfo)
	 */
	@Override
	public List<String> getValidCanonicalCourseToCourseOfferingOptionKeys(
			ContextInfo context) throws InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException, ReadOnlyException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kuali.student.enrollment.courseoffering.service.CourseOfferingService
	 * #getValidRolloverOptionKeys(org.kuali.student.r2.common.dto.ContextInfo)
	 */
	@Override
	public List<String> getValidRolloverOptionKeys(ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			ReadOnlyException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kuali.student.enrollment.courseoffering.service.CourseOfferingService
	 * #createCourseOffering(java.lang.String, java.lang.String,
	 * java.lang.String,
	 * org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo,
	 * java.util.List, org.kuali.student.r2.common.dto.ContextInfo)
	 */
	@Override
	public CourseOfferingInfo createCourseOffering(String courseId,
			String termId, String courseOfferingTypeKey,
			CourseOfferingInfo courseOfferingInfo, List<String> optionKeys,
			ContextInfo context) throws DoesNotExistException,
			DataValidationErrorException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException, ReadOnlyException {

		courseOfferingMap.put(courseId, courseOfferingInfo);

		// should actually be the lui id here
		courseOfferingInfo.setId(courseId);
		
		// course id is the lui.cluId (canonical course id)
		courseOfferingInfo.setCourseId(courseId);

		courseOfferingInfo.setTermId(termId);
		
		courseOfferingInfo.setTypeKey(courseOfferingTypeKey);
		
		courseOfferingInfo.setStateKey(LuiServiceConstants.COURSE_OFFERING_PROCESS_STATE_KEYS[0]);
		
		
		return courseOfferingInfo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kuali.student.enrollment.courseoffering.service.CourseOfferingService
	 * #rolloverCourseOffering(java.lang.String, java.lang.String,
	 * java.util.List, org.kuali.student.r2.common.dto.ContextInfo)
	 */
	@Override
	public CourseOfferingInfo rolloverCourseOffering(
			String sourceCourseOfferingId, String targetTermId,
			List<String> optionKeys, ContextInfo context)
			throws AlreadyExistsException, DoesNotExistException,
			DataValidationErrorException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException, ReadOnlyException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kuali.student.enrollment.courseoffering.service.CourseOfferingService
	 * #updateCourseOffering(java.lang.String,
	 * org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo,
	 * org.kuali.student.r2.common.dto.ContextInfo)
	 */
	@Override
	public CourseOfferingInfo updateCourseOffering(String courseOfferingId,
			CourseOfferingInfo courseOfferingInfo, ContextInfo context)
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			ReadOnlyException, VersionMismatchException {
		validateParameter(courseOfferingId);

		getCourseOffering(courseOfferingId, context);

		this.courseOfferingMap.put(courseOfferingId, courseOfferingInfo);
		
		return courseOfferingInfo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kuali.student.enrollment.courseoffering.service.CourseOfferingService
	 * #updateCourseOfferingFromCanonical(java.lang.String, java.util.List,
	 * org.kuali.student.r2.common.dto.ContextInfo)
	 */
	@Override
	public CourseOfferingInfo updateCourseOfferingFromCanonical(
			String courseOfferingId, List<String> optionKeys,
			ContextInfo context) throws DataValidationErrorException,
			DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException, VersionMismatchException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kuali.student.enrollment.courseoffering.service.CourseOfferingService
	 * #deleteCourseOffering(java.lang.String,
	 * org.kuali.student.r2.common.dto.ContextInfo)
	 */
	@Override
	public StatusInfo deleteCourseOffering(String courseOfferingId,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			DependentObjectsExistException {

		StatusInfo status = new StatusInfo();

		if (courseOfferingMap.remove(courseOfferingId) != null) {
			status.setSuccess(true);
		} else {
			status.setSuccess(false);
			status.setMessage("deleteActivityFailed");
		}

		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kuali.student.enrollment.courseoffering.service.CourseOfferingService
	 * #deleteCourseOfferingCascaded(java.lang.String,
	 * org.kuali.student.r2.common.dto.ContextInfo)
	 */
	@Override
	public StatusInfo deleteCourseOfferingCascaded(String courseOfferingId,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		try {
			return deleteCourseOffering(courseOfferingId, context);
		} catch (DependentObjectsExistException e) {
			throw new OperationFailedException("", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kuali.student.enrollment.courseoffering.service.CourseOfferingService
	 * #validateCourseOffering(java.lang.String,
	 * org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo,
	 * org.kuali.student.r2.common.dto.ContextInfo)
	 */
	@Override
	public List<ValidationResultInfo> validateCourseOffering(
			String validationType, CourseOfferingInfo courseOfferingInfo,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kuali.student.enrollment.courseoffering.service.CourseOfferingService
	 * #validateCourseOfferingFromCanonical(org.kuali.student.enrollment.
	 * courseoffering.dto.CourseOfferingInfo, java.util.List,
	 * org.kuali.student.r2.common.dto.ContextInfo)
	 */
	@Override
	public List<ValidationResultInfo> validateCourseOfferingFromCanonical(
			CourseOfferingInfo courseOfferingInfo, List<String> optionKeys,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kuali.student.enrollment.courseoffering.service.CourseOfferingService
	 * #getFormatOffering(java.lang.String,
	 * org.kuali.student.r2.common.dto.ContextInfo)
	 */
	@Override
	public FormatOfferingInfo getFormatOffering(String formatOfferingId,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {

		validateParameter(formatOfferingId);

		FormatOfferingInfo fo = formatOfferingMap.get(formatOfferingId);

		validateDoesNotExist(fo);

		return fo;
	}

	private void validateDoesNotExist(Object result)
			throws DoesNotExistException {

		if (result == null)
			throw new DoesNotExistException();
	}

	private void validateParameter(String param)
			throws InvalidParameterException {

		if (param == null)
			throw new InvalidParameterException();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kuali.student.enrollment.courseoffering.service.CourseOfferingService
	 * #getFormatOfferingsByCourseOffering(java.lang.String,
	 * org.kuali.student.r2.common.dto.ContextInfo)
	 */
	@Override
	public List<FormatOfferingInfo> getFormatOfferingsByCourseOffering(
			String courseOfferingId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kuali.student.enrollment.courseoffering.service.CourseOfferingService
	 * #createFormatOffering(java.lang.String, java.lang.String,
	 * java.lang.String,
	 * org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo,
	 * org.kuali.student.r2.common.dto.ContextInfo)
	 */
	@Override
	public FormatOfferingInfo createFormatOffering(String courseOfferingId,
			String formatId, String formatOfferingType,
			FormatOfferingInfo formatOfferingInfo, ContextInfo context)
			throws DoesNotExistException, DataValidationErrorException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			ReadOnlyException {

		formatOfferingMap.put(formatId, formatOfferingInfo);

		return formatOfferingInfo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kuali.student.enrollment.courseoffering.service.CourseOfferingService
	 * #updateFormatOffering(java.lang.String,
	 * org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo,
	 * org.kuali.student.r2.common.dto.ContextInfo)
	 */
	@Override
	public FormatOfferingInfo updateFormatOffering(String formatOfferingId,
			FormatOfferingInfo formatOfferingInfo, ContextInfo context)
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			ReadOnlyException, VersionMismatchException {
		validateParameter(formatOfferingId);

		getFormatOffering(formatOfferingId, context);

		this.formatOfferingMap.put(formatOfferingId, formatOfferingInfo);
		
		return formatOfferingInfo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kuali.student.enrollment.courseoffering.service.CourseOfferingService
	 * #validateFormatOffering(java.lang.String,
	 * org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo,
	 * org.kuali.student.r2.common.dto.ContextInfo)
	 */
	@Override
	public List<ValidationResultInfo> validateFormatOffering(
			String validationType, FormatOfferingInfo formatOfferingInfo,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kuali.student.enrollment.courseoffering.service.CourseOfferingService
	 * #deleteFormatOffering(java.lang.String,
	 * org.kuali.student.r2.common.dto.ContextInfo)
	 */
	@Override
	public StatusInfo deleteFormatOffering(String formatOfferingId,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			DependentObjectsExistException {

		StatusInfo status = new StatusInfo();

		if (formatOfferingMap.remove(formatOfferingId) != null) {
			status.setSuccess(true);
		} else {
			status.setSuccess(false);
			status.setMessage("deleteFormatFailed");
		}

		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kuali.student.enrollment.courseoffering.service.CourseOfferingService
	 * #deleteFormatOfferingCascaded(java.lang.String,
	 * org.kuali.student.r2.common.dto.ContextInfo)
	 */
	@Override
	public StatusInfo deleteFormatOfferingCascaded(String formatOfferingId,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		try {
			return deleteFormatOffering(formatOfferingId, context);
		} catch (DependentObjectsExistException e) {
			throw new OperationFailedException(
					"deleteFormatOfferingCascaded Failed.", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kuali.student.enrollment.courseoffering.service.CourseOfferingService
	 * #getActivityOfferingType(java.lang.String,
	 * org.kuali.student.r2.common.dto.ContextInfo)
	 */
	@Override
	public TypeInfo getActivityOfferingType(String activityOfferingTypeKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kuali.student.enrollment.courseoffering.service.CourseOfferingService
	 * #getActivityOfferingTypes(org.kuali.student.r2.common.dto.ContextInfo)
	 */
	@Override
	public List<TypeInfo> getActivityOfferingTypes(ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kuali.student.enrollment.courseoffering.service.CourseOfferingService
	 * #getActivityOfferingTypesForActivityType(java.lang.String,
	 * org.kuali.student.r2.common.dto.ContextInfo)
	 */
	@Override
	public List<TypeInfo> getActivityOfferingTypesForActivityType(
			String activityTypeKey, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kuali.student.enrollment.courseoffering.service.CourseOfferingService
	 * #getInstructorTypesForActivityOfferingType(java.lang.String,
	 * org.kuali.student.r2.common.dto.ContextInfo)
	 */
	@Override
	public List<TypeInfo> getInstructorTypesForActivityOfferingType(
			String activityOfferingTypeKey, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kuali.student.enrollment.courseoffering.service.CourseOfferingService
	 * #getActivityOffering(java.lang.String,
	 * org.kuali.student.r2.common.dto.ContextInfo)
	 */
	@Override
	public ActivityOfferingInfo getActivityOffering(String activityOfferingId,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		validateParameter(activityOfferingId);

		ActivityOfferingInfo ao = activityOfferingMap.get(activityOfferingId);

		validateDoesNotExist(ao);

		return ao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kuali.student.enrollment.courseoffering.service.CourseOfferingService
	 * #getActivityOfferingsByIds(java.util.List,
	 * org.kuali.student.r2.common.dto.ContextInfo)
	 */
	@Override
	public List<ActivityOfferingInfo> getActivityOfferingsByIds(
			List<String> activityOfferingIds, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kuali.student.enrollment.courseoffering.service.CourseOfferingService
	 * #getActivityOfferingsByCourseOffering(java.lang.String,
	 * org.kuali.student.r2.common.dto.ContextInfo)
	 */
	@Override
	public List<ActivityOfferingInfo> getActivityOfferingsByCourseOffering(
			String courseOfferingId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kuali.student.enrollment.courseoffering.service.CourseOfferingService
	 * #getActivityOfferingsByFormatOffering(java.lang.String,
	 * org.kuali.student.r2.common.dto.ContextInfo)
	 */
	@Override
	public List<ActivityOfferingInfo> getActivityOfferingsByFormatOffering(
			String formatOfferingId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kuali.student.enrollment.courseoffering.service.CourseOfferingService
	 * #getActivityOfferingsByFormatOfferingWithoutRegGroup(java.lang.String,
	 * org.kuali.student.r2.common.dto.ContextInfo)
	 */
	@Override
	public List<ActivityOfferingInfo> getActivityOfferingsByFormatOfferingWithoutRegGroup(
			String formatOfferingId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kuali.student.enrollment.courseoffering.service.CourseOfferingService
	 * #createActivityOffering(java.lang.String, java.lang.String,
	 * java.lang.String,
	 * org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo,
	 * org.kuali.student.r2.common.dto.ContextInfo)
	 */
	@Override
	public ActivityOfferingInfo createActivityOffering(String formatOfferingId,
			String activityId, String activityOfferingTypeKey,
			ActivityOfferingInfo activityOfferingInfo, ContextInfo context)
			throws DoesNotExistException, DataValidationErrorException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			ReadOnlyException {

		activityOfferingMap.put(activityId, activityOfferingInfo);

		return activityOfferingInfo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kuali.student.enrollment.courseoffering.service.CourseOfferingService
	 * #copyActivityOffering(java.lang.String,
	 * org.kuali.student.r2.common.dto.ContextInfo)
	 */
	@Override
	public ActivityOfferingInfo copyActivityOffering(String activityOfferingId,
			ContextInfo context) throws DoesNotExistException,
			DataValidationErrorException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException, ReadOnlyException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kuali.student.enrollment.courseoffering.service.CourseOfferingService
	 * #generateActivityOfferings(java.lang.String, java.lang.String,
	 * java.lang.Integer, org.kuali.student.r2.common.dto.ContextInfo)
	 */
	@Override
	public List<ActivityOfferingInfo> generateActivityOfferings(
			String formatOfferingId, String activityOfferingType,
			Integer quantity, ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kuali.student.enrollment.courseoffering.service.CourseOfferingService
	 * #updateActivityOffering(java.lang.String,
	 * org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo,
	 * org.kuali.student.r2.common.dto.ContextInfo)
	 */
	@Override
	public ActivityOfferingInfo updateActivityOffering(
			String activityOfferingId,
			ActivityOfferingInfo activityOfferingInfo, ContextInfo context)
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			VersionMismatchException, ReadOnlyException {

		validateParameter(activityOfferingId);

		getActivityOffering(activityOfferingId, context);

		this.activityOfferingMap.put(activityOfferingId, activityOfferingInfo);
		
		return activityOfferingInfo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kuali.student.enrollment.courseoffering.service.CourseOfferingService
	 * #deleteActivityOffering(java.lang.String,
	 * org.kuali.student.r2.common.dto.ContextInfo)
	 */
	@Override
	public StatusInfo deleteActivityOffering(String activityOfferingId,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			DependentObjectsExistException {

		StatusInfo status = new StatusInfo();

		if (activityOfferingMap.remove(activityOfferingId) != null) {
			status.setSuccess(true);
		} else {
			status.setSuccess(false);
			status.setMessage("deleteActivityFailed");
		}

		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kuali.student.enrollment.courseoffering.service.CourseOfferingService
	 * #validateActivityOffering(java.lang.String,
	 * org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo,
	 * org.kuali.student.r2.common.dto.ContextInfo)
	 */
	@Override
	public List<ValidationResultInfo> validateActivityOffering(
			String validationType, ActivityOfferingInfo activityOfferingInfo,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kuali.student.enrollment.courseoffering.service.CourseOfferingService
	 * #calculateInClassContactHoursForTerm(java.lang.String,
	 * org.kuali.student.r2.common.dto.ContextInfo)
	 */
	@Override
	public Float calculateInClassContactHoursForTerm(String activityOfferingId,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kuali.student.enrollment.courseoffering.service.CourseOfferingService
	 * #calculateOutofClassContactHoursForTerm(java.lang.String,
	 * org.kuali.student.r2.common.dto.ContextInfo)
	 */
	@Override
	public Float calculateOutofClassContactHoursForTerm(
			String activityOfferingId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kuali.student.enrollment.courseoffering.service.CourseOfferingService
	 * #calculateTotalContactHoursForTerm(java.lang.String,
	 * org.kuali.student.r2.common.dto.ContextInfo)
	 */
	@Override
	public Float calculateTotalContactHoursForTerm(String activityOfferingId,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kuali.student.enrollment.courseoffering.service.CourseOfferingService
	 * #getRegistrationGroup(java.lang.String,
	 * org.kuali.student.r2.common.dto.ContextInfo)
	 */
	@Override
	public RegistrationGroupInfo getRegistrationGroup(
			String registrationGroupId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {

		validateParameter(registrationGroupId);

		RegistrationGroupInfo rg = regFroupMap.get(registrationGroupId);

		validateDoesNotExist(rg);

		return rg;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kuali.student.enrollment.courseoffering.service.CourseOfferingService
	 * #getRegistrationGroupsByIds(java.util.List,
	 * org.kuali.student.r2.common.dto.ContextInfo)
	 */
	@Override
	public List<RegistrationGroupInfo> getRegistrationGroupsByIds(
			List<String> registrationGroupIds, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kuali.student.enrollment.courseoffering.service.CourseOfferingService
	 * #getRegistrationGroupsForCourseOffering(java.lang.String,
	 * org.kuali.student.r2.common.dto.ContextInfo)
	 */
	@Override
	public List<RegistrationGroupInfo> getRegistrationGroupsForCourseOffering(
			String courseOfferingId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kuali.student.enrollment.courseoffering.service.CourseOfferingService
	 * #getRegistrationGroupsWithActivityOfferings(java.util.List,
	 * org.kuali.student.r2.common.dto.ContextInfo)
	 */
	@Override
	public List<RegistrationGroupInfo> getRegistrationGroupsWithActivityOfferings(
			List<String> activityOfferingIds, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kuali.student.enrollment.courseoffering.service.CourseOfferingService
	 * #getRegistrationGroupsByFormatOffering(java.lang.String,
	 * org.kuali.student.r2.common.dto.ContextInfo)
	 */
	@Override
	public List<RegistrationGroupInfo> getRegistrationGroupsByFormatOffering(
			String formatOfferingId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kuali.student.enrollment.courseoffering.service.CourseOfferingService
	 * #createRegistrationGroup(java.lang.String, java.lang.String,
	 * org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo,
	 * org.kuali.student.r2.common.dto.ContextInfo)
	 */
	@Override
	public RegistrationGroupInfo createRegistrationGroup(
			String formatOfferingId, String registrationGroupType,
			RegistrationGroupInfo registrationGroupInfo, ContextInfo context)
			throws DoesNotExistException, DataValidationErrorException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			ReadOnlyException {

		registrationGroupInfo.setId(formatOfferingId + "-"
				+ registrationGroupType);

		regFroupMap.put(registrationGroupInfo.getId(), registrationGroupInfo);

		return registrationGroupInfo;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kuali.student.enrollment.courseoffering.service.CourseOfferingService
	 * #generateRegistrationGroupsForFormatOffering(java.lang.String,
	 * org.kuali.student.r2.common.dto.ContextInfo)
	 */
	@Override
	public List<RegistrationGroupInfo> generateRegistrationGroupsForFormatOffering(
			String formatOfferingId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kuali.student.enrollment.courseoffering.service.CourseOfferingService
	 * #generateRegistrationGroupsForTemplate(java.lang.String,
	 * org.kuali.student.r2.common.dto.ContextInfo)
	 */
	@Override
	public List<RegistrationGroupInfo> generateRegistrationGroupsForTemplate(
			String registrationGroupTemplateId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kuali.student.enrollment.courseoffering.service.CourseOfferingService
	 * #updateRegistrationGroup(java.lang.String,
	 * org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo,
	 * org.kuali.student.r2.common.dto.ContextInfo)
	 */
	@Override
	public RegistrationGroupInfo updateRegistrationGroup(
			String registrationGroupId,
			RegistrationGroupInfo registrationGroupInfo, ContextInfo context)
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			ReadOnlyException, VersionMismatchException {
	
		validateParameter(registrationGroupId);

		getRegistrationGroup(registrationGroupId, context);

		this.regFroupMap.put(registrationGroupId, registrationGroupInfo);
		
		return registrationGroupInfo;
	}
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kuali.student.enrollment.courseoffering.service.CourseOfferingService
	 * #deleteRegistrationGroup(java.lang.String,
	 * org.kuali.student.r2.common.dto.ContextInfo)
	 */
	@Override
	public StatusInfo deleteRegistrationGroup(String registrationGroupId,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {

		StatusInfo status = new StatusInfo();

		if (regFroupMap.remove(registrationGroupId) != null)
			status.setSuccess(true);
		else {
			status.setSuccess(false);
			status.setMessage("deleteRegistrationGroup Failed");
		}

		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kuali.student.enrollment.courseoffering.service.CourseOfferingService
	 * #deleteRegistrationGroupsByFormatOffering(java.lang.String,
	 * org.kuali.student.r2.common.dto.ContextInfo)
	 */
	@Override
	public StatusInfo deleteRegistrationGroupsByFormatOffering(
			String formatOfferingId, ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kuali.student.enrollment.courseoffering.service.CourseOfferingService
	 * #deleteGeneratedRegistrationGroupsByFormatOffering(java.lang.String,
	 * org.kuali.student.r2.common.dto.ContextInfo)
	 */
	@Override
	public StatusInfo deleteGeneratedRegistrationGroupsByFormatOffering(
			String formatOfferingId, ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kuali.student.enrollment.courseoffering.service.CourseOfferingService
	 * #deleteGeneratedRegistrationGroupsForTemplate(java.lang.String,
	 * org.kuali.student.r2.common.dto.ContextInfo)
	 */
	@Override
	public StatusInfo deleteGeneratedRegistrationGroupsForTemplate(
			String registrationGroupTemplateId, ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kuali.student.enrollment.courseoffering.service.CourseOfferingService
	 * #validateRegistrationGroup(java.lang.String,
	 * org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo,
	 * org.kuali.student.r2.common.dto.ContextInfo)
	 */
	@Override
	public List<ValidationResultInfo> validateRegistrationGroup(
			String validationType, RegistrationGroupInfo registrationGroupInfo,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kuali.student.enrollment.courseoffering.service.CourseOfferingService
	 * #getRegistrationGroupTemplate(java.lang.String,
	 * org.kuali.student.r2.common.dto.ContextInfo)
	 */
	@Override
	public RegistrationGroupTemplateInfo getRegistrationGroupTemplate(
			String registrationGroupTemplateId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kuali.student.enrollment.courseoffering.service.CourseOfferingService
	 * #validateRegistrationGroupTemplate(java.lang.String,
	 * org.kuali.student.enrollment
	 * .courseoffering.dto.RegistrationGroupTemplateInfo,
	 * org.kuali.student.r2.common.dto.ContextInfo)
	 */
	@Override
	public List<ValidationResultInfo> validateRegistrationGroupTemplate(
			String validationTypeKey,
			RegistrationGroupTemplateInfo registrationGroupTemplateInfo,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kuali.student.enrollment.courseoffering.service.CourseOfferingService
	 * #
	 * createRegistrationGroupTemplate(org.kuali.student.enrollment.courseoffering
	 * .dto.RegistrationGroupTemplateInfo,
	 * org.kuali.student.r2.common.dto.ContextInfo)
	 */
	@Override
	public RegistrationGroupTemplateInfo createRegistrationGroupTemplate(
			RegistrationGroupTemplateInfo registrationGroupTemplateInfo,
			ContextInfo context) throws DataValidationErrorException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			ReadOnlyException {

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kuali.student.enrollment.courseoffering.service.CourseOfferingService
	 * #updateRegistrationGroupTemplate(java.lang.String,
	 * org.kuali.student.enrollment
	 * .courseoffering.dto.RegistrationGroupTemplateInfo,
	 * org.kuali.student.r2.common.dto.ContextInfo)
	 */
	@Override
	public RegistrationGroupTemplateInfo updateRegistrationGroupTemplate(
			String registrationGroupTemplateId,
			RegistrationGroupTemplateInfo registrationGroupTemplateInfo,
			ContextInfo context) throws DataValidationErrorException,
			DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException, ReadOnlyException,
			VersionMismatchException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kuali.student.enrollment.courseoffering.service.CourseOfferingService
	 * #deleteRegistrationGroupTemplate(java.lang.String,
	 * org.kuali.student.r2.common.dto.ContextInfo)
	 */
	@Override
	public StatusInfo deleteRegistrationGroupTemplate(
			String registrationGroupTemplateId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kuali.student.enrollment.courseoffering.service.CourseOfferingService
	 * #getSeatPoolDefinition(java.lang.String,
	 * org.kuali.student.r2.common.dto.ContextInfo)
	 */
	@Override
	public SeatPoolDefinitionInfo getSeatPoolDefinition(
			String seatPoolDefinitionId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kuali.student.enrollment.courseoffering.service.CourseOfferingService
	 * #getSeatPoolDefinitionsForActivityOffering(java.lang.String,
	 * org.kuali.student.r2.common.dto.ContextInfo)
	 */
	@Override
	public List<SeatPoolDefinitionInfo> getSeatPoolDefinitionsForActivityOffering(
			String activityOfferingId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kuali.student.enrollment.courseoffering.service.CourseOfferingService
	 * #
	 * createSeatPoolDefinition(org.kuali.student.enrollment.courseoffering.dto.
	 * SeatPoolDefinitionInfo, org.kuali.student.r2.common.dto.ContextInfo)
	 */
	@Override
	public SeatPoolDefinitionInfo createSeatPoolDefinition(
			SeatPoolDefinitionInfo seatPoolDefinitionInfo, ContextInfo context)
			throws DataValidationErrorException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException, ReadOnlyException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kuali.student.enrollment.courseoffering.service.CourseOfferingService
	 * #updateSeatPoolDefinition(java.lang.String,
	 * org.kuali.student.enrollment.courseoffering.dto.SeatPoolDefinitionInfo,
	 * org.kuali.student.r2.common.dto.ContextInfo)
	 */
	@Override
	public SeatPoolDefinitionInfo updateSeatPoolDefinition(
			String seatPoolDefinitionId,
			SeatPoolDefinitionInfo seatPoolDefinitionInfo, ContextInfo context)
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			ReadOnlyException, VersionMismatchException {
		validateParameter(seatPoolDefinitionId);

		getSeatPoolDefinition(seatPoolDefinitionId, context);

		this.seatPoolMap.put(seatPoolDefinitionId, seatPoolDefinitionInfo);
		
		return seatPoolDefinitionInfo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kuali.student.enrollment.courseoffering.service.CourseOfferingService
	 * #validateSeatPoolDefinition(java.lang.String,
	 * org.kuali.student.enrollment.courseoffering.dto.SeatPoolDefinitionInfo,
	 * org.kuali.student.r2.common.dto.ContextInfo)
	 */
	@Override
	public List<ValidationResultInfo> validateSeatPoolDefinition(
			String validationTypeKey,
			SeatPoolDefinitionInfo seatPoolDefinitionInfo, ContextInfo context)
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			VersionMismatchException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kuali.student.enrollment.courseoffering.service.CourseOfferingService
	 * #deleteSeatPoolDefinition(java.lang.String,
	 * org.kuali.student.r2.common.dto.ContextInfo)
	 */
	@Override
	public StatusInfo deleteSeatPoolDefinition(String seatPoolDefinitionId,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {

		StatusInfo status = new StatusInfo();

		if (seatPoolMap.remove(seatPoolDefinitionId) != null)
			status.setSuccess(true);
		else {
			status.setSuccess(false);
			status.setMessage("deleteSeatPoolDefinition Failed");
		}

		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kuali.student.enrollment.courseoffering.service.CourseOfferingService
	 * #
	 * searchForCourseOfferings(org.kuali.rice.core.api.criteria.QueryByCriteria
	 * , org.kuali.student.r2.common.dto.ContextInfo)
	 */
	@Override
	public List<CourseOfferingInfo> searchForCourseOfferings(
			QueryByCriteria criteria, ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kuali.student.enrollment.courseoffering.service.CourseOfferingService
	 * #
	 * searchForCourseOfferingIds(org.kuali.rice.core.api.criteria.QueryByCriteria
	 * , org.kuali.student.r2.common.dto.ContextInfo)
	 */
	@Override
	public List<String> searchForCourseOfferingIds(QueryByCriteria criteria,
			ContextInfo context) throws InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kuali.student.enrollment.courseoffering.service.CourseOfferingService
	 * #
	 * searchForActivityOfferings(org.kuali.rice.core.api.criteria.QueryByCriteria
	 * , org.kuali.student.r2.common.dto.ContextInfo)
	 */
	@Override
	public List<ActivityOfferingInfo> searchForActivityOfferings(
			QueryByCriteria criteria, ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kuali.student.enrollment.courseoffering.service.CourseOfferingService
	 * #
	 * searchForActivityOfferingIds(org.kuali.rice.core.api.criteria.QueryByCriteria
	 * , org.kuali.student.r2.common.dto.ContextInfo)
	 */
	@Override
	public List<String> searchForActivityOfferingIds(QueryByCriteria criteria,
			ContextInfo context) throws InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kuali.student.enrollment.courseoffering.service.CourseOfferingService
	 * #
	 * searchForRegistrationGroups(org.kuali.rice.core.api.criteria.QueryByCriteria
	 * , org.kuali.student.r2.common.dto.ContextInfo)
	 */
	@Override
	public List<RegistrationGroupInfo> searchForRegistrationGroups(
			QueryByCriteria criteria, ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kuali.student.enrollment.courseoffering.service.CourseOfferingService
	 * #searchForRegistrationGroupIds(org.kuali.rice.core.api.criteria.
	 * QueryByCriteria, org.kuali.student.r2.common.dto.ContextInfo)
	 */
	@Override
	public List<String> searchForRegistrationGroupIds(QueryByCriteria criteria,
			ContextInfo context) throws InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kuali.student.enrollment.courseoffering.service.CourseOfferingService
	 * #
	 * searchForSeatpoolDefinitions(org.kuali.rice.core.api.criteria.QueryByCriteria
	 * , org.kuali.student.r2.common.dto.ContextInfo)
	 */
	@Override
	public List<SeatPoolDefinitionInfo> searchForSeatpoolDefinitions(
			QueryByCriteria criteria, ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.kuali.student.enrollment.courseoffering.service.CourseOfferingService
	 * #searchForSeatpoolDefinitionIds(org.kuali.rice.core.api.criteria.
	 * QueryByCriteria, org.kuali.student.r2.common.dto.ContextInfo)
	 */
	@Override
	public List<String> searchForSeatpoolDefinitionIds(
			QueryByCriteria criteria, ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

}
