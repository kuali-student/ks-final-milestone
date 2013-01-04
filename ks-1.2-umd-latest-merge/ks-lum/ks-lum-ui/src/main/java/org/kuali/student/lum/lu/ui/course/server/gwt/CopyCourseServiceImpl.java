package org.kuali.student.lum.lu.ui.course.server.gwt;

import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.kuali.student.common.assembly.data.Data;
import org.kuali.student.common.dto.CurrencyAmountInfo;
import org.kuali.student.common.dto.DtoConstants;
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
import org.kuali.student.common.ui.client.service.DataSaveResult;
import org.kuali.student.common.ui.server.gwt.DataService;
import org.kuali.student.core.proposal.dto.ProposalInfo;
import org.kuali.student.core.proposal.service.ProposalService;
import org.kuali.student.core.statement.dto.ReqCompFieldInfo;
import org.kuali.student.core.statement.dto.ReqComponentInfo;
import org.kuali.student.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.core.statement.service.StatementService;
import org.kuali.student.lum.course.dto.ActivityInfo;
import org.kuali.student.lum.course.dto.CourseCrossListingInfo;
import org.kuali.student.lum.course.dto.CourseFeeInfo;
import org.kuali.student.lum.course.dto.CourseInfo;
import org.kuali.student.lum.course.dto.CourseJointInfo;
import org.kuali.student.lum.course.dto.CourseRevenueInfo;
import org.kuali.student.lum.course.dto.CourseVariationInfo;
import org.kuali.student.lum.course.dto.FormatInfo;
import org.kuali.student.lum.course.dto.LoDisplayInfo;
import org.kuali.student.lum.course.service.CourseService;
import org.kuali.student.lum.lrc.dto.ResultComponentInfo;
import org.kuali.student.lum.lu.LUConstants;
import org.kuali.student.lum.lu.dto.AffiliatedOrgInfo;
import org.kuali.student.lum.lu.dto.CluSetInfo;
import org.kuali.student.lum.lu.service.LuService;
import org.kuali.student.lum.statement.typekey.ReqComponentFieldTypes;
import org.springframework.transaction.annotation.Transactional;
@Transactional(noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
public class CopyCourseServiceImpl {
	final static Logger LOG = Logger.getLogger(CopyCourseServiceImpl.class);
	
	private DataService courseDataService;
	private DataService courseProposalDataService;
	private CourseService courseService;
	private LuService luService;
	private StatementService statementService;
	private ProposalService proposalService;
	
	private String defaultDocumentType=LUConstants.PROPOSAL_TYPE_COURSE_CREATE;
	private String defaultState=DtoConstants.STATE_DRAFT;
	
	private List<String> ignoreProperties;
	
	public DataSaveResult createCopyCourse(String originalCluId) throws Exception {
		//Copy the course and use the data service to return
		CourseInfo copiedCourse = copyCourse(originalCluId);
		
		DataSaveResult result = new DataSaveResult();
		result.setValue(courseDataService.getData(copiedCourse.getId()));
		return result;
	}

	public DataSaveResult createCopyCourseProposal(String originalProposalId) throws Exception {
		//Copy the proposal and use the data service to return
		ProposalInfo copiedProposal = copyProposal(originalProposalId);
		
		//Grab the data object so it is transformed
		Data data = courseProposalDataService.getData(copiedProposal.getId());
		
		//Save it so that it goes through the filters and creates workflow
		return courseProposalDataService.saveData(data);
	}
	
	private CourseInfo copyCourse(String originalCluId) throws Exception{
		//Copy the course
		return copyCourse(originalCluId, null, defaultState, ignoreProperties, statementService, luService, courseService);
	}
	private ProposalInfo copyProposal(String originalProposalId) throws Exception{
		try {
			//Get the original Proposal
			ProposalInfo originalProposal = proposalService.getProposal(originalProposalId);
			
			//Copy the course from the original Proposal
			String originalCluId = originalProposal.getProposalReference().get(0);
			CourseInfo copiedCourse = copyCourse(originalCluId);
			
			//Clear ids and set the reference to the copied course
			originalProposal.setId(null);
			originalProposal.setWorkflowId(null);
			originalProposal.setState(defaultState);
			originalProposal.setType(defaultDocumentType);
			originalProposal.getProposalReference().set(0, copiedCourse.getId());
			originalProposal.getProposerOrg().clear();
			originalProposal.getProposerPerson().clear();
            originalProposal.setName(null);
			
			//Create the proposal
			ProposalInfo copiedProposal = proposalService.createProposal(defaultDocumentType, originalProposal);
			
			return copiedProposal;
			
		} catch (Exception e) {
			LOG.error("Error copying proposal id:" + originalProposalId, e);
			throw e;
		}
	}

	private void resetIds(CourseInfo course) {
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
		//Clear result component ids
		for(ResultComponentInfo result:course.getCreditOptions()){
			result.setId(null);
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

	private void resetLoRecursively(LoDisplayInfo lo){
		//Clear out all the Lo ids recursively
		lo.getLoInfo().setId(null);
		for(LoDisplayInfo nestedLo:lo.getLoDisplayInfoList()){
			resetLoRecursively(nestedLo);
		}
	}

	private void clearStatementTreeViewIds(
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
	private void clearStatementTreeViewIdsRecursively(StatementTreeViewInfo statementTreeView, String newState,LuService luService) throws OperationFailedException{
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

    private void copyStatements(String originalCluId, String newCluId, String newState,
			StatementService statementService, LuService luService, CourseService courseService) throws OperationFailedException, DoesNotExistException, InvalidParameterException, MissingParameterException, PermissionDeniedException, DataValidationErrorException {
		//Get the course statements
		List<StatementTreeViewInfo> statementTreeViews = courseService.getCourseStatements(originalCluId,null,null);
		
		//Clear out the ids and create causing a copy to be made
		if(statementTreeViews!=null){
			clearStatementTreeViewIds(statementTreeViews,newState,luService);
			
			for(StatementTreeViewInfo statementTreeView:statementTreeViews){
				courseService.createCourseStatement(newCluId, statementTreeView);
			}
		}
	}
	
	private CourseInfo copyCourse(String originalCluId, String newCluId, String newState, List<String> ignoreProperties, StatementService statementService, LuService luService, CourseService courseService) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, AlreadyExistsException, DataValidationErrorException, VersionMismatchException, CircularRelationshipException, DependentObjectsExistException, UnsupportedActionException{
		CourseInfo originalCourse = courseService.getCourse(originalCluId);
		resetIds(originalCourse);
		originalCourse.setCourseTitle("Copy of "+originalCourse.getCourseTitle());
		//Default the newState to the existing course state if no state was set.
		//State should never be null
		if(newState==null){
			newState = originalCourse.getState();
		}
		
		originalCourse.setId(newCluId);
		originalCourse.setState(newState);
        originalCourse.setPilotCourse(false);
		
		//Loop through the ignore properties and null out the values
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

	public void setCourseService(CourseService courseService) {
		this.courseService = courseService;
	}

	public void setLuService(LuService luService) {
		this.luService = luService;
	}

	public void setStatementService(StatementService statementService) {
		this.statementService = statementService;
	}

	public void setProposalService(ProposalService proposalService) {
		this.proposalService = proposalService;
	}

	public void setCourseDataService(DataService courseDataService) {
		this.courseDataService = courseDataService;
	}

	public void setCourseProposalDataService(DataService courseProposalDataService) {
		this.courseProposalDataService = courseProposalDataService;
	}

	public void setDefaultDocumentType(String defaultDocumentType) {
		this.defaultDocumentType = defaultDocumentType;
	}

	public void setDefaultState(String defaultState) {
		this.defaultState = defaultState;
	}

	public void setIgnoreProperties(List<String> ignoreProperties) {
		this.ignoreProperties = ignoreProperties;
	}
	
}
