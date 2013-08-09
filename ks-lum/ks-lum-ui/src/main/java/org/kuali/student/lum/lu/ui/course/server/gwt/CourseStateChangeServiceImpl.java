package org.kuali.student.lum.lu.ui.course.server.gwt;

import java.util.Iterator;
import java.util.List;

import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.DtoConstants;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.CircularReferenceException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.core.versionmanagement.dto.VersionDisplayInfo;
import org.kuali.student.r1.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.lum.common.server.StatementUtil;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.service.CourseService;
import org.kuali.student.r2.lum.util.constants.CourseServiceConstants;
import org.springframework.transaction.annotation.Transactional;

@Transactional(noRollbackFor = { DoesNotExistException.class }, rollbackFor = { Throwable.class })
public class CourseStateChangeServiceImpl {
	private CourseService courseService;

	/**
	 * Change the state of a course to a new state
	 * 
	 * @param courseId id of course
	 * @param newState the new state for the course
	 * @param prevEndTermAtpId the current version end date of course
	 * @return
	 * @throws Exception
	 */
	public StatusInfo changeState(String courseId, String newState,	String prevEndTermAtpId,ContextInfo contextInfo) throws Exception {

		CourseInfo courseInfo = courseService.getCourse(courseId,contextInfo);

		StatusInfo ret = new StatusInfo();
		try {
			if (newState.equals(DtoConstants.STATE_ACTIVE)) {
				activateCourse(courseInfo, prevEndTermAtpId,contextInfo);
			} else if (newState.equals(DtoConstants.STATE_RETIRED)) {
				retireCourse(courseInfo,contextInfo);
			}

			ret.setSuccess(new Boolean(true));
		} catch (Exception e) {
			ret.setSuccess(new Boolean(false));
			ret.setMessage(e.getMessage());
		}

		return ret;
	}

	/**
	 * Activate a course version. Only course with a state of "Approved" can be activated.
	 * 
	 * @param courseToActivate
	 * @param prevEndTermAtpId the end term we set on the current version
	 */
	protected void activateCourse(CourseInfo courseToActivate, String prevEndTermAtpId,ContextInfo contextInfo) throws Exception{
    	CourseInfo currVerCourse = getCurrentVersionOfCourse(courseToActivate,contextInfo);
    	String existingState = courseToActivate.getStateKey();
		String currVerState = currVerCourse.getStateKey();
		boolean isCurrVer = (courseToActivate.getId().equals(currVerCourse.getId()));
		
		if (existingState.equals(DtoConstants.STATE_DRAFT)) {
			// since this is approved if isCurrVer we can assume there are no previously active versions to deal with
			if (isCurrVer) {
				// setstate for thisVerCourse and setCurrentVersion(courseId)
				updateCourseVersionStates(courseToActivate, DtoConstants.STATE_ACTIVE, currVerCourse, null, true, prevEndTermAtpId,contextInfo);
			} else if (currVerState.equals(DtoConstants.STATE_ACTIVE) ||
					currVerState.equals(DtoConstants.STATE_SUSPENDED)) {
				updateCourseVersionStates(courseToActivate, DtoConstants.STATE_ACTIVE, currVerCourse, DtoConstants.STATE_SUPERSEDED, true, prevEndTermAtpId,contextInfo);
			}
		}
	}
	
	/**
	 * Retire a course version. Only course with a state of "Active" or "Suspended" can be retired
	 * 
	 * @param courseToRetire the course to retire
	 */
	protected void retireCourse(CourseInfo courseToRetire,ContextInfo contextInfo) throws Exception{
    	String existingState = courseToRetire.getStateKey();		
		
    	if (existingState.equals(DtoConstants.STATE_ACTIVE) || existingState.equals(DtoConstants.STATE_SUSPENDED)){
    		courseToRetire.setStateKey(DtoConstants.STATE_RETIRED);

    		courseService.updateCourse(courseToRetire.getId(),courseToRetire, contextInfo);
			updateStatementTreeViewInfoState(courseToRetire,contextInfo);
    	}
	}
	
	/**
	 * Get the current version of course from another version of course
	 * 
	 *
	 */
	protected CourseInfo getCurrentVersionOfCourse(CourseInfo course,ContextInfo contextInfo)
			throws Exception {
		// Get version independent id of course
		String verIndId = course.getVersion().getVersionIndId();

		// Get id of current version of course given the versionindependen id
		VersionDisplayInfo curVerDisplayInfo = courseService.getCurrentVersion(
			CourseServiceConstants.COURSE_NAMESPACE_URI, verIndId, contextInfo);
		String curVerId = curVerDisplayInfo.getId();

		// Return the current version of the course
		CourseInfo currVerCourse = courseService.getCourse(curVerId,contextInfo);

		return currVerCourse;
	}

	/**
	 * Based on null values, updates states of thisVerCourse and currVerCourse
	 * and sets thisVerCourse as the current version. Attempts to rollback
	 * transaction on exception.
	 * 
	 * @param thisVerCourse
	 *            this is the version that the user selected to change the state
	 * @param thisVerNewState
	 *            this is state that the user selected to change thisVerCourse
	 *            to
	 * @param currVerCourse
	 *            this is the current version of the course
	 *            (currentVersionStartDt <= now && currentVersionEndDt > now)
	 * @param currVerNewState
	 *            this is the state that we need to set the current version to.
	 *            Set to null to not update the currVerCourse state.
	 * @param makeCurrent
	 *            if true we'll set thisVerCourse as the current version.
	 * @param prevEndTermAtpId
	 *            the end term for the previous version to end on
	 * @throws Exception
	 */
	@Transactional(readOnly = false)
	private void updateCourseVersionStates(CourseInfo thisVerCourse,
			String thisVerNewState, CourseInfo currVerCourse,
			String currVerNewState, boolean makeCurrent,
			String prevEndTermAtpId,ContextInfo contextInfo) throws Exception {
		String thisVerPrevState = thisVerCourse.getStateKey();

		// if already current, will throw error if you try to make the current
		// version the current version.
		boolean isCurrent = thisVerCourse.getId().equals(currVerCourse.getId());
		if(!makeCurrent || !isCurrent || !thisVerCourse.getVersion().getSequenceNumber().equals(1)){
			makeCurrent &= !isCurrent;
		}

		if (thisVerNewState == null) {
			throw new InvalidParameterException("new state cannot be null");
		} else {
			thisVerCourse.setStateKey(thisVerNewState);
			courseService.updateCourse(thisVerCourse.getId(),thisVerCourse,contextInfo);
			updateStatementTreeViewInfoState(thisVerCourse,contextInfo);
		}

		// won't get called if previous exception was thrown
		if (currVerNewState != null) {
			currVerCourse.setStateKey(currVerNewState);
			if(currVerCourse.getEndTerm()==null){
				currVerCourse.setEndTerm(prevEndTermAtpId);
			}
			courseService.updateCourse(currVerCourse.getId(),currVerCourse,contextInfo);
			updateStatementTreeViewInfoState(currVerCourse,contextInfo);
		}

		if (makeCurrent == true) {
			courseService.setCurrentCourseVersion(thisVerCourse.getId(),
					null,contextInfo);
		}

		// for all draft and approved courses set the state to superseded.
		// we should only need to evaluated versions with sequence number
		// higher than previous active course. If the course you're
		// activating is the current course check all versions.
		if (thisVerPrevState.equals(DtoConstants.STATE_APPROVED)
				&& thisVerNewState.equals(DtoConstants.STATE_ACTIVE)) {

			List<VersionDisplayInfo> versions =  courseService.getVersions(
				CourseServiceConstants.COURSE_NAMESPACE_URI, thisVerCourse
						.getVersion().getVersionIndId(), contextInfo);
			Long startSeq = new Long(1);

			if (!isCurrent && (currVerCourse.getId() != thisVerCourse.getId())) {
				startSeq = currVerCourse.getVersion().getSequenceNumber() + 1;
			}

			for (VersionDisplayInfo versionInfo : versions) {
				if (versionInfo.getSequenceNumber() >= startSeq) {
					CourseInfo otherCourse = courseService
							.getCourse(""+versionInfo.getId(),contextInfo);
					if (otherCourse.getStateKey().equals(
							DtoConstants.STATE_APPROVED)
							|| otherCourse.getStateKey().equals(
									DtoConstants.STATE_SUBMITTED)
							|| otherCourse.getStateKey().equals(
									DtoConstants.STATE_DRAFT)) {
						otherCourse.setStateKey(DtoConstants.STATE_SUPERSEDED);
						courseService.updateCourse(otherCourse.getId(),otherCourse,contextInfo);
						updateStatementTreeViewInfoState(otherCourse,contextInfo);
					}
				}
			}
		}

	}

	public void setCourseService(CourseService courseService) {
		this.courseService = courseService;
	}

	/**
	 * This method will load all the statements in a course from the course web
	 * service, recursively update the state of each statement in the statement
	 * tree, and save the update statements back to the web service.
	 * 
	 * 
	 * @param courseInfo
	 *            The course to update (call setState() in this object to set
	 *            the state)
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 * @throws DataValidationErrorException
	 * @throws CircularReferenceException
	 * @throws VersionMismatchException
	 */
	public void updateStatementTreeViewInfoState(CourseInfo courseInfo,ContextInfo contextInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException, DataValidationErrorException,
			CircularReferenceException, VersionMismatchException {

		// Call course web service to get all requirements/statements for this
		// course
		List<StatementTreeViewInfo> statementTreeViewInfos = courseService
				.getCourseStatements(courseInfo.getId(), null, null,contextInfo);

		if (statementTreeViewInfos != null){
			// Recursively update state on all requirements/statements in the tree
			for (Iterator<StatementTreeViewInfo> it = statementTreeViewInfos
					.iterator(); it.hasNext();)
				StatementUtil.updateStatementTreeViewInfoState(courseInfo
						.getStateKey(), it.next());
	
			// Call the course web service and update the requirement/statement tree
			// with the new state
			for (Iterator<StatementTreeViewInfo> it = statementTreeViewInfos
					.iterator(); it.hasNext();)
				courseService.updateCourseStatement(courseInfo.getId(),null, it.next(),contextInfo);
		}
	}

}
