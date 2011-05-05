package org.kuali.student.lum.course.service.impl;

import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.kuali.student.common.exceptions.AlreadyExistsException;
import org.kuali.student.common.exceptions.CircularRelationshipException;
import org.kuali.student.common.exceptions.DataValidationErrorException;
import org.kuali.student.common.exceptions.DependentObjectsExistException;
import org.kuali.student.common.exceptions.DoesNotExistException;
import org.kuali.student.common.exceptions.InvalidParameterException;
import org.kuali.student.common.exceptions.MissingParameterException;
import org.kuali.student.common.exceptions.OperationFailedException;
import org.kuali.student.common.exceptions.PermissionDeniedException;
import org.kuali.student.common.exceptions.UnsupportedActionException;
import org.kuali.student.common.exceptions.VersionMismatchException;
import org.kuali.student.core.statement.dto.ReqCompFieldInfo;
import org.kuali.student.core.statement.dto.ReqComponentInfo;
import org.kuali.student.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.core.statement.service.StatementService;
import org.kuali.student.lum.course.dto.ActivityInfo;
import org.kuali.student.lum.course.dto.CourseInfo;
import org.kuali.student.lum.course.dto.CourseJointInfo;
import org.kuali.student.lum.course.dto.FormatInfo;
import org.kuali.student.lum.course.dto.LoDisplayInfo;
import org.kuali.student.lum.course.service.CourseService;
import org.kuali.student.lum.lu.dto.CluSetInfo;
import org.kuali.student.lum.lu.service.LuService;
import org.kuali.student.lum.statement.typekey.ReqComponentFieldTypes;

public class CourseServiceUtils {
	public static void resetIds(CourseInfo course) {
		//Clear/Reset Joint info ids
		for(CourseJointInfo joint:course.getJoints()){
			joint.setRelationId(null);
		}
		//Clear Los
		for(LoDisplayInfo lo:course.getCourseSpecificLOs()){
			resetLoRecursively(lo);
		}
		//Clear format/activity ids
		for(FormatInfo format:course.getFormats()){
			format.setId(null);
			for(ActivityInfo activity:format.getActivities()){
				activity.setId(null);
			}
		}
	}

	private static void resetLoRecursively(LoDisplayInfo lo){
		//Clear out all the Lo ids recursively
		lo.getLoInfo().setId(null);
		for(LoDisplayInfo nestedLo:lo.getLoDisplayInfoList()){
			resetLoRecursively(nestedLo);
		}
	}

	private static void clearStatementTreeViewIds(
			List<StatementTreeViewInfo> statementTreeViews, String newState, LuService luService) throws OperationFailedException {
		//Clear out all statement ids recursively
		for(StatementTreeViewInfo statementTreeView:statementTreeViews){
			clearStatementTreeViewIdsRecursively(statementTreeView, newState, luService);
		}
	}

	/**
	 * Clears out ids recursively and also copies adhock clusets
	 * @param statementTreeView
	 * @param luService
	 * @throws OperationFailedException
	 */
	private static void clearStatementTreeViewIdsRecursively(StatementTreeViewInfo statementTreeView, String newState,LuService luService) throws OperationFailedException{
		statementTreeView.setId(null);
		statementTreeView.setState(newState);
		
		//clear out all the nested requirement components
		for(ReqComponentInfo reqComp:statementTreeView.getReqComponents()){
			reqComp.setId(null);
			reqComp.setState(newState);
			for(ReqCompFieldInfo field:reqComp.getReqCompFields()){
				field.setId(null);
				//copy any clusets that are adhoc'd and set the field value to the new cluset
				if(ReqComponentFieldTypes.COURSE_CLUSET_KEY.getId().equals(field.getType())||
				   ReqComponentFieldTypes.PROGRAM_CLUSET_KEY.getId().equals(field.getType())||
				   ReqComponentFieldTypes.CLUSET_KEY.getId().equals(field.getType())){
					try {
						CluSetInfo cluSet = luService.getCluSetInfo(field.getValue());
						cluSet.setId(null);
						cluSet.setState(newState);
						//Clear clu ids if membership info exists, they will be re-added based on membership info 
						if (cluSet.getMembershipQuery() != null){
							cluSet.getCluIds().clear();
							cluSet.getCluSetIds().clear();
						}
						cluSet = luService.createCluSet(cluSet.getType(), cluSet);
						field.setValue(cluSet.getId());
					} catch (Exception e) {
						throw new OperationFailedException("Error copying clusets.", e);
					}
				}
				
			}
		}
		//recurse through nested statements
		for(StatementTreeViewInfo child: statementTreeView.getStatements()){
			clearStatementTreeViewIdsRecursively(child,newState,luService);
		}
	}

	public static void copyStatements(String originalCluId, String newCluId, String newState,
			StatementService statementService, LuService luService, CourseService courseService) throws OperationFailedException, DoesNotExistException, InvalidParameterException, MissingParameterException, PermissionDeniedException, DataValidationErrorException {
		List<StatementTreeViewInfo> statementTreeViews = courseService.getCourseStatements(originalCluId,null,null);
		
		clearStatementTreeViewIds(statementTreeViews,newState,luService);
		
		for(StatementTreeViewInfo statementTreeView:statementTreeViews){
			courseService.createCourseStatement(newCluId, statementTreeView);
		}
	}
	
	public static CourseInfo copyCourse(String originalCluId, String newCluId, String newState, String[] ignoreProperties, StatementService statementService, LuService luService, CourseService courseService) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, AlreadyExistsException, DataValidationErrorException, VersionMismatchException, CircularRelationshipException, DependentObjectsExistException, UnsupportedActionException{
		CourseInfo originalCourse = courseService.getCourse(originalCluId);
		resetIds(originalCourse);
		
		//Default the newState to the existing course state if no state was set.
		//State should never be null
		if(newState==null){
			newState = originalCourse.getState();
		}
		
		originalCourse.setId(newCluId);
		originalCourse.setState(newState);
		
		if(ignoreProperties!=null){
			for(String property:ignoreProperties){
				try {
					PropertyUtils.setProperty(originalCourse, property, null);
				} catch (Exception e) {
					throw new InvalidParameterException("Ignore property is invalid and is causing an exception.",e);
				}
			}
		}
		
		CourseInfo newCourse = courseService.createCourse(originalCourse);
		copyStatements(originalCluId, newCourse.getId(), newState, statementService, luService, courseService);
		return newCourse;
	}
	
}
