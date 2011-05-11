package org.kuali.student.lum.lu.ui.course.server.gwt;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.kuali.student.common.dto.DtoConstants;
import org.kuali.student.common.dto.StatusInfo;
import org.kuali.student.common.exceptions.CircularReferenceException;
import org.kuali.student.common.exceptions.DataValidationErrorException;
import org.kuali.student.common.exceptions.DoesNotExistException;
import org.kuali.student.common.exceptions.InvalidParameterException;
import org.kuali.student.common.exceptions.MissingParameterException;
import org.kuali.student.common.exceptions.OperationFailedException;
import org.kuali.student.common.exceptions.PermissionDeniedException;
import org.kuali.student.common.exceptions.VersionMismatchException;
import org.kuali.student.common.versionmanagement.dto.VersionDisplayInfo;
import org.kuali.student.core.statement.dto.ReqComponentInfo;
import org.kuali.student.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.lum.common.server.StatementUtil;
import org.kuali.student.lum.course.dto.CourseInfo;
import org.kuali.student.lum.course.service.CourseService;
import org.kuali.student.lum.course.service.CourseServiceConstants;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.springframework.transaction.annotation.Transactional;

@Transactional(noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
public class CourseStateChangeServiceImpl {
    private CourseService courseService;
    
   public StatusInfo changeState(String courseId, String newState, Date currentVersionStart) throws Exception {
    	
    	CourseInfo thisVerCourse = courseService.getCourse(courseId);
    	String prevState = thisVerCourse.getState();
		String verIndId = thisVerCourse.getVersionInfo().getVersionIndId();
		VersionDisplayInfo curVerDisplayInfo = courseService.getCurrentVersion(CourseServiceConstants.COURSE_NAMESPACE_URI, verIndId);
		String curVerId = curVerDisplayInfo.getId();
		CourseInfo currVerCourse = courseService.getCourse(curVerId);
		String currVerState = currVerCourse.getState();
		boolean isCurrVer = (courseId.equals(currVerCourse.getId()));

		StatusInfo ret = new StatusInfo();
		try {
			if (newState.equals(DtoConstants.STATE_ACTIVE)) {
				if (prevState.equals(DtoConstants.STATE_APPROVED)) {
					// since this is approved if isCurrVer we can assume there are no previously active versions to deal with
					if (isCurrVer) {
						// setstate for thisVerCourse and setCurrentVersion(courseId)
						updateCourseVersionStates(thisVerCourse, newState, currVerCourse, null, true, currentVersionStart);
					} else if (currVerState.equals(DtoConstants.STATE_ACTIVE) ||
							currVerState.equals(DtoConstants.STATE_INACTIVE)) {
						updateCourseVersionStates(thisVerCourse, newState, currVerCourse, DtoConstants.STATE_SUPERSEDED, true, currentVersionStart);
					}					

				} else if (prevState.equals(DtoConstants.STATE_INACTIVE)) {

				}
			}
			ret.setSuccess(new Boolean(true));
		} catch (Exception e) {
			ret.setSuccess(new Boolean(false));
			ret.setMessage(e.getMessage());
		}

		
		return ret;
    }

    /**
     * Based on null values, updates states of thisVerCourse and currVerCourse and sets thisVerCourse as the current version.  Attempts to rollback transaction on exception.
     *
     * @param thisVerCourse this is the version that the user selected to change the state
     * @param thisVerNewState this is state that the user selected to change thisVerCourse to
     * @param currVerCourse this is the current version of the course (currentVersionStartDt <= now && currentVersionEndDt > now)
     * @param currVerNewState this is the state that we need to set the current version to.  Set to null to not update the currVerCourse state.
     * @param makeCurrent if true we'll set thisVerCourse as the current version.
     * @param currentVersionStart the start date for the new current version to start on and the old current version to end on.  Set to null to use now as the start date.
     * @throws Exception
     */
    @Transactional(readOnly = false)
    private void updateCourseVersionStates (CourseInfo thisVerCourse, String thisVerNewState, CourseInfo currVerCourse, String currVerNewState, boolean makeCurrent, Date currentVersionStart) throws Exception {
    	String thisVerPrevState = thisVerCourse.getState();
    	String currVerPrevState = currVerCourse.getState();
    	
    	// if already current, will throw error if you try to make the current version the current version.
    	boolean isCurrent = thisVerCourse.getId().equals(currVerCourse.getId());
    	makeCurrent &= !isCurrent;        	
    	
    	if (thisVerNewState == null) {
    		throw new InvalidParameterException("new state cannot be null");
    	} else {
    		thisVerCourse.setState(thisVerNewState);
    		courseService.updateCourse(thisVerCourse);
    		updateStatementTreeViewInfoState(thisVerCourse); 		
    	}

    	// won't get called if previous exception was thrown
    	if (currVerNewState != null) {
    		currVerCourse.setState(currVerNewState);
    		courseService.updateCourse(currVerCourse);
    		updateStatementTreeViewInfoState(currVerCourse);
    	}

    	if (makeCurrent == true) {
    		courseService.setCurrentCourseVersion(thisVerCourse.getId(), currentVersionStart);
    	}
    	
    	// for all draft and approved courses set the state to superseded.
    	// we should only need to evaluated versions with sequence number
    	// higher than previous active course.  If the course you're 
    	// activating is the current course check all versions. 
    	if (thisVerPrevState.equals(DtoConstants.STATE_APPROVED) &&
    			thisVerNewState.equals(DtoConstants.STATE_ACTIVE)) {

    		List<VersionDisplayInfo> versions = courseService.getVersions(CourseServiceConstants.COURSE_NAMESPACE_URI, thisVerCourse.getVersionInfo().getVersionIndId());		
    		Long startSeq = new Long(1);
    		
			if (!isCurrent && (currVerCourse.getId() != thisVerCourse.getId())) {
				startSeq = currVerCourse.getVersionInfo().getSequenceNumber() + 1;
			}
			
			for (VersionDisplayInfo versionInfo: versions) {
    			if (versionInfo.getSequenceNumber() >= startSeq) {
    				CourseInfo otherCourse = courseService.getCourse(versionInfo.getId());
    				if (otherCourse.getState().equals(DtoConstants.STATE_APPROVED) ||
    						otherCourse.getState().equals(DtoConstants.STATE_SUBMITTED) ||
    						otherCourse.getState().equals(DtoConstants.STATE_DRAFT)) {
    					otherCourse.setState(DtoConstants.STATE_SUPERSEDED);
    					courseService.updateCourse(otherCourse);
    					updateStatementTreeViewInfoState(otherCourse);	
    				}
    			}
    		}  		
    	}

    }
    
    

	public void setCourseService(CourseService courseService) {
		this.courseService = courseService;
	}
	
	/**
	 * This method will load all the statements in a course from the course web service,
	 * recursively update the state of each statement in the statement tree,
	 * and save the update statements back to the web service.
	 * 
	 * 
	 * @param courseInfo The course to update (call setState() in this object to set the state)
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 * @throws DataValidationErrorException
	 * @throws CircularReferenceException
	 * @throws VersionMismatchException
	 */
	public void updateStatementTreeViewInfoState(CourseInfo courseInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DataValidationErrorException, CircularReferenceException, VersionMismatchException {
		
		// Call course web service to get all requirements/statements for this course 
		List<StatementTreeViewInfo> statementTreeViewInfos = courseService.getCourseStatements(courseInfo.getId(), null, null);
		
		// Recursively update state on all requirements/statements in the tree
		for(Iterator<StatementTreeViewInfo> it = statementTreeViewInfos.iterator(); it.hasNext();)
			StatementUtil.updateStatementTreeViewInfoState(courseInfo.getState(), it.next());
	 
		// Call the course web service and update the requirement/statement tree with the new state
		for(Iterator<StatementTreeViewInfo> it = statementTreeViewInfos.iterator(); it.hasNext();)
			courseService.updateCourseStatement(courseInfo.getId(), it.next());	
	}
	
}
