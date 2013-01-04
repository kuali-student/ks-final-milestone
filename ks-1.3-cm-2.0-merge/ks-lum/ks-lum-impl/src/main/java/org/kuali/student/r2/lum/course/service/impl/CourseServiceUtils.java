package org.kuali.student.r2.lum.course.service.impl;

import org.apache.commons.beanutils.PropertyUtils;
import org.kuali.student.r1.core.statement.dto.ReqCompFieldInfo;
import org.kuali.student.r1.core.statement.dto.ReqComponentInfo;
import org.kuali.student.r1.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.r1.core.statement.service.StatementService;
import org.kuali.student.r1.lum.statement.typekey.ReqComponentFieldTypes;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.CurrencyAmountInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.lum.clu.dto.AffiliatedOrgInfo;
import org.kuali.student.r2.lum.clu.dto.CluSetInfo;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.r2.lum.course.dto.*;
import org.kuali.student.r2.lum.course.service.CourseService;

import java.util.List;

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
		
		//Clear cross listing ids
		for(CourseCrossListingInfo crossListing:course.getCrossListings()){
			crossListing.setId(null);
		}
		//Clear Expenditures
		for(AffiliatedOrgInfo orgInfo:course.getExpenditure().getAffiliatedOrgs()){
			orgInfo.setId(null);
		}
		//Clear Fees
		for(CourseFeeInfo fee:course.getFees()){
			fee.setId(null);
			for(CurrencyAmountInfo feeAmount:fee.getFeeAmounts()){
				feeAmount.setId(null);
			}
		}
		//Clear revenue
		for(CourseRevenueInfo revenue:course.getRevenues()){
			revenue.setId(null);
			for(AffiliatedOrgInfo orgInfo:revenue.getAffiliatedOrgs()){
				orgInfo.setId(null);
			}
		}
		//Clear variation ids
		for(CourseVariationInfo variation:course.getVariations()){
			variation.setId(null);
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
			List<StatementTreeViewInfo> statementTreeViews, String newState, CluService cluService,ContextInfo contextInfo) throws OperationFailedException {
		//Clear out all statement ids recursively
		for(StatementTreeViewInfo statementTreeView:statementTreeViews){
			clearStatementTreeViewIdsRecursively(statementTreeView, newState, cluService,contextInfo);
		}
	}

	/**
	 * Clears out ids recursively and also copies adhock clusets
	 * @param statementTreeView
	 * @param cluService
	 * @throws OperationFailedException
	 */
	private static void clearStatementTreeViewIdsRecursively(StatementTreeViewInfo statementTreeView, String newState, CluService cluService,ContextInfo contextInfo) throws OperationFailedException{
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
						CluSetInfo cluSet = cluService.getCluSet(field.getValue(), contextInfo);
						cluSet.setId(null);
						cluSet.setStateKey(newState);
						//Clear clu ids if membership info exists, they will be re-added based on membership info
						if (cluSet.getMembershipQuery() != null){
							cluSet.getCluIds().clear();
							cluSet.getCluSetIds().clear();
						}
						cluSet = cluService.createCluSet(cluSet.getTypeKey(), cluSet, contextInfo);
						field.setValue(cluSet.getId());
					} catch (Exception e) {
						throw new OperationFailedException("Error copying clusets.", e);
					}
				}
				
			}
		}
		//recurse through nested statements
		for(StatementTreeViewInfo child: statementTreeView.getStatements()){
			clearStatementTreeViewIdsRecursively(child,newState, cluService,contextInfo);
		}
	}

	public static void copyStatements(String originalCluId, String newCluId, String newState,
			StatementService statementService, CluService cluService, CourseService courseService,ContextInfo contextInfo) throws OperationFailedException, DoesNotExistException, InvalidParameterException, MissingParameterException, PermissionDeniedException, DataValidationErrorException {
		List<StatementTreeViewInfo> statementTreeViews = courseService.getCourseStatements(originalCluId,null,null,contextInfo);
		
		clearStatementTreeViewIds(statementTreeViews, newState, cluService, contextInfo);
		
		for(StatementTreeViewInfo statementTreeView:statementTreeViews){
			courseService.createCourseStatement(newCluId, statementTreeView, contextInfo);
		}
	}
	
	public static CourseInfo copyCourse(String originalCluId, String newCluId, String newState, String[] ignoreProperties, StatementService statementService, CluService cluService, CourseService courseService,ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, AlreadyExistsException, DataValidationErrorException, VersionMismatchException, CircularRelationshipException, DependentObjectsExistException, UnsupportedActionException{
		CourseInfo originalCourse = courseService.getCourse(originalCluId,contextInfo);
		resetIds(originalCourse);
		
		//Default the newState to the existing course state if no state was set.
		//State should never be null
		if(newState==null){
			newState = originalCourse.getStateKey();
		}
		
		originalCourse.setId(newCluId);
		originalCourse.setStateKey(newState);
		
		if(ignoreProperties!=null){
			for(String property:ignoreProperties){
				try {
					PropertyUtils.setProperty(originalCourse, property, null);
				} catch (Exception e) {
					throw new InvalidParameterException("Ignore property is invalid and is causing an exception.",e);
				}
			}
		}
		
		CourseInfo newCourse = courseService.createCourse(originalCourse,contextInfo);
		copyStatements(originalCluId, newCourse.getId(), newState, statementService, cluService, courseService,contextInfo);
		return newCourse;
	}
	
}
