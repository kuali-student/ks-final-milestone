/**
 * 
 */
package org.kuali.student.lum.workflow;

import java.util.Iterator;
import java.util.List;

import javax.xml.namespace.QName;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.kew.api.KewApiConstants;
import org.kuali.rice.kew.api.action.ActionTaken;
import org.kuali.rice.kew.framework.postprocessor.ActionTakenEvent;
import org.kuali.rice.kew.framework.postprocessor.DocumentRouteStatusChange;
import org.kuali.rice.kew.framework.postprocessor.IDocumentEvent;
import org.kuali.student.r1.core.proposal.dto.ProposalInfo;
import org.kuali.student.r1.core.statement.dto.ReqComponentInfo;
import org.kuali.student.r1.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.r1.lum.lu.LUConstants;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.DtoConstants;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.service.CourseService;
import org.springframework.transaction.annotation.Transactional;

/**
 * A base post processor class for Course document types in Workflow.
 *
 */
@Transactional(readOnly=true, rollbackFor={Throwable.class})
public class CoursePostProcessorBase extends KualiStudentPostProcessorBase {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(CoursePostProcessorBase.class);

    private CourseService courseService;
    private CourseStateChangeServiceImpl courseStateChangeService;

    /**
     *    This method changes the state of the course when a Withdraw action is processed on a proposal.
     *    For create and modify proposals, a new clu was created which needs to be cancelled via
     *    setting it to "not approved."
      *    
     *    For retirement proposals, a clu is never actually created, therefore we don't update the clu at
     *    all if it is withdrawn.
     *       
     *    @param actionTakenEvent - contains the docId, the action taken (code "d"), the principalId which submitted it, etc
     *    @param proposalInfo - The proposal object being withdrawn 
     */   
    @Override
    protected void processWithdrawActionTaken(ActionTakenEvent actionTakenEvent, ProposalInfo proposalInfo) throws Exception {
        
        if (proposalInfo != null){
            String proposalDocType=proposalInfo.getType();      
            // The current two proposal docTypes which being withdrawn will cause a course to be 
            // disapproved are Create and Modify (because a new DRAFT version is created when these 
            // proposals are submitted.)
            if ( LUConstants.PROPOSAL_TYPE_COURSE_CREATE.equals(proposalDocType)
                    ||  LUConstants.PROPOSAL_TYPE_COURSE_MODIFY.equals(proposalDocType)) {
                LOG.info("Will set CLU state to '"
                        + DtoConstants.STATE_NOT_APPROVED + "'");
                // Get Clu
                CourseInfo courseInfo = getCourseService().getCourse(
                        getCourseId(proposalInfo), ContextUtils.getContextInfo());
                // Update Clu
                updateCourse(actionTakenEvent, DtoConstants.STATE_NOT_APPROVED,
                        courseInfo, proposalInfo);
            } 
            // Retire proposal is the only proposal type at this time which will not require a 
            // change to the clu if withdrawn.
                        else if ( LUConstants.PROPOSAL_TYPE_COURSE_RETIRE.equals(proposalDocType)) {
                LOG.info("Withdrawing a retire proposal with ID'" + proposalInfo.getId() 
                        + ", will not change any CLU state as there is no new CLU object to set.");
            }
        } else {
            LOG.info("Proposal Info is null when a withdraw proposal action was taken, doing nothing.");
        }
    }

    @Override
    protected boolean processCustomActionTaken(ActionTakenEvent actionTakenEvent, ActionTaken actionTaken, ProposalInfo proposalInfo) throws Exception {
        String cluId = getCourseId(proposalInfo);
        CourseInfo courseInfo = getCourseService().getCourse(cluId, ContextUtils.getContextInfo());
        // submit, blanket approve action taken comes through here.        
        updateCourse(actionTakenEvent, null, courseInfo, proposalInfo);
        return true;
    }

    /**
     * This method takes a clu proposal, determines what the "new state"
     * of the clu should be, then routes the clu I, and the new state
     * to CourseStateChangeServiceImpl.java
     */
     @Override   
     protected boolean processCustomRouteStatusChange(DocumentRouteStatusChange statusChangeEvent, ProposalInfo proposalInfo) throws Exception {

         String courseId = getCourseId(proposalInfo);        
         String prevEndTermAtpId = proposalInfo.getAttributes().get("prevEndTerm");
         
         // Get the current "existing" courseInfo
         CourseInfo courseInfo = getCourseService().getCourse(courseId, ContextUtils.getContextInfo());
         
         // Get the new state the course should now change to        
         String newCourseState = getCluStateForRouteStatus(courseInfo.getStateKey(), statusChangeEvent.getNewRouteStatus(), proposalInfo.getType());
         
         //Use the state change service to update to active and update preceding versions
         if (newCourseState != null){
             if(DtoConstants.STATE_ACTIVE.equals(newCourseState)){     
                 
                 // Change the state using the effective date as the version start date
                 // update course and save it for retire if state = retire           
                 getCourseStateChangeService().changeState(courseId, newCourseState, prevEndTermAtpId, ContextUtils.getContextInfo());
             } else
                 
                 // Retire By Proposal will come through here, extra data will need 
                 // to be copied from the proposalInfo to the courseInfo fields before 
                 // the save happens.            
                 if(DtoConstants.STATE_RETIRED.equals(newCourseState)){
                     retireCourseByProposalCopyAndSave(newCourseState, courseInfo, proposalInfo);
                     getCourseStateChangeService().changeState(courseId, newCourseState, prevEndTermAtpId, ContextUtils.getContextInfo());
             }
               else{ // newCourseState of null comes here, is this desired?
                 updateCourse(statusChangeEvent, newCourseState, courseInfo, proposalInfo);
             }                  
         }
         return true;
     }
     
     /**
      * 
      * 
      * In this method, the proposal object fields are copied to the cluInfo object
      * fields to pass validation. This method copies data from the custom Retire
      * By Proposal proposalInfo Object Fields into the courseInfo object so that upon save it will
      * pass validation.
      * 
      * Admin Retire and Retire by Proposal both end up here.
      * 
      * This Route will get you here, Route Statuses:
      * 'S' Saved 
      * 'R' Enroute 
      * 'A' Approved - After final approve, status is set to 'A'  
      * 'P' Processed - During this run through coursepostprocessorbase, assuming 
      * doctype is Retire, we end up here.  
      * 
      * @param courseState - used to confirm state is retired
     * @param courseInfo - course object we are updating
     * @param proposalInfo - proposal object which has the on-screen fields we are copying from
      */
     protected void retireCourseByProposalCopyAndSave(String courseState, CourseInfo courseInfo, ProposalInfo proposalInfo) throws Exception {
         
         // Copy the data to the object - 
         // These Proposal Attribs need to go back to courseInfo Object 
         // to pass validation.
         if (DtoConstants.STATE_RETIRED.equals(courseState)){
             if ((proposalInfo != null) && (proposalInfo.getAttributes() != null))
             {
             String rationale = proposalInfo.getRationale();
             String proposedEndTerm = proposalInfo.getAttributes().get("proposedEndTerm");          
             String proposedLastTermOffered = proposalInfo.getAttributes().get("proposedLastTermOffered");
             String proposedLastCourseCatalogYear = proposalInfo.getAttributes().get("proposedLastCourseCatalogYear");
                   
             courseInfo.setEndTerm(proposedEndTerm);             
             courseInfo.getAttributes().add(new AttributeInfo("retirementRationale", rationale));
             courseInfo.getAttributes().add(new AttributeInfo("lastTermOffered", proposedLastTermOffered));
             courseInfo.getAttributes().add(new AttributeInfo("lastPublicationYear", proposedLastCourseCatalogYear));
             
               // lastTermOffered is a special case field, as it is required upon retire state
               // but not required for submit.  Therefore it is possible for a user to submit a retire proposal
               // without this field filled out, then when the course gets approved, and the state changes to RETIRED
               // validation would fail and the proposal will then go into exception routing.  
               // We can't simply make lastTermOffered a required field as it is not a desired field  
               // on the course proposal screen.
               //              
               // So in the case of lastTermOffered being null when a course is retired,
               // Just copy the "proposalInfo.proposedEndTerm" value (required for saves, so it will be filled out) 
               // into "courseInfo.lastTermOffered" to pass validation.   
               if ((proposalInfo!=null) && (courseInfo!=null) && 
                         (courseInfo.getAttributeValue("lastTermOffered")==null)) {
                    courseInfo.getAttributes().add(new AttributeInfo("lastTermOffered", proposalInfo.getAttributes().get("proposedEndTerm")));
               }
             }
         }
         // Save the Data to the DB
         getCourseService().updateCourse(courseInfo.getId(), courseInfo, ContextUtils.getContextInfo());
     }

    protected String getCourseId(ProposalInfo proposalInfo) throws OperationFailedException {
        if (proposalInfo.getProposalReference().size() != 1) {
            LOG.error("Found " + proposalInfo.getProposalReference().size() + " CLU objects linked to proposal with proposalId='" + proposalInfo.getId() + "'. Must have exactly 1 linked.");
            throw new OperationFailedException("Found " + proposalInfo.getProposalReference().size() + " CLU objects linked to proposal with docId='" + proposalInfo.getWorkflowId() + "' and proposalId='" + proposalInfo.getId() + "'. Must have exactly 1 linked.");
        }
        return proposalInfo.getProposalReference().get(0);
    }

    /** This method returns the state a clu should go to, based on 
     *  the Proposal's docType and the newWorkflow StatusCode 
     *  which are passed in.
     * 
     * @param currentCluState - the current state set on the CLU
     * @param newWorkflowStatusCode - the new route status code that is getting set on the workflow document
     * @param docType - The doctype of the proposal which kicked off this workflow.
     * @return the CLU state to set or null if the CLU does not need it's state changed
     */
    protected String getCluStateForRouteStatus(String currentCluState, String newWorkflowStatusCode, String docType) {
        if (LUConstants.PROPOSAL_TYPE_COURSE_RETIRE.equals(docType)) {
            // This is for Retire Proposal, Course State should remain active for
            // all other route statuses.            
            if (KewApiConstants.ROUTE_HEADER_PROCESSED_CD.equals(newWorkflowStatusCode)){
                return DtoConstants.STATE_RETIRED;
            }   
            return null;  // returning null indicates no change in course state required
        } else {
            //  The following is for Create, Modify, and Admin Modify proposals.    
            if (StringUtils.equals(KewApiConstants.ROUTE_HEADER_SAVED_CD, newWorkflowStatusCode)) {
                return getCourseStateFromNewState(currentCluState, DtoConstants.STATE_DRAFT);
            } else if (KewApiConstants.ROUTE_HEADER_CANCEL_CD .equals(newWorkflowStatusCode)) {
                return getCourseStateFromNewState(currentCluState, DtoConstants.STATE_NOT_APPROVED);
            } else if (KewApiConstants.ROUTE_HEADER_ENROUTE_CD.equals(newWorkflowStatusCode)) {
                return getCourseStateFromNewState(currentCluState, DtoConstants.STATE_DRAFT);
            } else if (KewApiConstants.ROUTE_HEADER_DISAPPROVED_CD.equals(newWorkflowStatusCode)) {
                /* current requirements state that on a Withdraw (which is a KEW Disapproval) the 
                 * CLU state should be submitted so no special handling required here
                 */
                return getCourseStateFromNewState(currentCluState, DtoConstants.STATE_NOT_APPROVED);
            } else if (KewApiConstants.ROUTE_HEADER_PROCESSED_CD.equals(newWorkflowStatusCode)) {
                return getCourseStateFromNewState(currentCluState, DtoConstants.STATE_ACTIVE);
            } else if (KewApiConstants.ROUTE_HEADER_EXCEPTION_CD.equals(newWorkflowStatusCode)) {
                return getCourseStateFromNewState(currentCluState, DtoConstants.STATE_DRAFT);
            } else {
                // no status to set
                return null;
            }
        }
    }

    /**
     * Default behavior is to return the <code>newCluState</code> variable only if it differs from the
     * <code>currentCluState</code> value. Otherwise <code>null</code> will be returned.
     */
    protected String getCourseStateFromNewState(String currentCourseState, String newCourseState) {
        if (LOG.isInfoEnabled()) {
            LOG.info("current CLU state is '" + currentCourseState + "' and new CLU state will be '" + newCourseState + "'");
        }
        return getStateFromNewState(currentCourseState, newCourseState);
    }

    @Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
    protected void updateCourse(IDocumentEvent iDocumentEvent, String courseState, CourseInfo courseInfo, ProposalInfo proposalInfo) throws Exception {
        // only change the state if the course is not currently set to that state
        boolean requiresSave = false;
        if (courseState != null) {
            if (LOG.isInfoEnabled()) {
                LOG.info("Setting state '" + courseState + "' on CLU with cluId='" + courseInfo.getId() + "'");
            }
            courseInfo.setStateKey(courseState);
            requiresSave = true;
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("Running preProcessCluSave with cluId='" + courseInfo.getId() + "'");
        }
        requiresSave |= preProcessCourseSave(iDocumentEvent, courseInfo);

        if (requiresSave) {
            getCourseService().updateCourse(courseInfo.getId(), courseInfo, ContextUtils.getContextInfo());
            
            //For a newly approved course (w/no prior active versions), make the new course the current version.
            if (DtoConstants.STATE_ACTIVE.equals(courseState) && courseInfo.getVersionInfo().getCurrentVersionStart() == null){
            	// TODO: set states of other approved courses to superseded                
                
            	// if current version's state is not active then we can set this course as the active course
            	//if (!DtoConstants.STATE_ACTIVE.equals(getCourseService().getCourse(getCourseService().getCurrentVersion(CourseServiceConstants.COURSE_NAMESPACE_URI, courseInfo.getVersionInfo().getVersionIndId()).getId()).getState())) { 
            		getCourseService().setCurrentCourseVersion(courseInfo.getId(), null, ContextUtils.getContextInfo());
            	//}
            }
            
            List<StatementTreeViewInfo> statementTreeViewInfos = courseService.getCourseStatements(courseInfo.getId(), null, null, ContextUtils.getContextInfo());
            if(statementTreeViewInfos!=null){
	            statementTreeViewInfoStateSetter(courseInfo.getStateKey(), statementTreeViewInfos.iterator());
	            
	            for(Iterator<StatementTreeViewInfo> it = statementTreeViewInfos.iterator(); it.hasNext();)

	        		courseService.updateCourseStatement(courseInfo.getId(), courseState, it.next(), ContextUtils.getContextInfo());
            }
        }
        
    }

    protected boolean preProcessCourseSave(IDocumentEvent iDocumentEvent, CourseInfo courseInfo) {
        return false;
    }

    protected CourseService getCourseService() {
        if (this.courseService == null) {
            this.courseService = (CourseService) GlobalResourceLoader.getService(new QName("http://student.kuali.org/wsdl/course","CourseService")); 
        }
        return this.courseService;
    }
    protected CourseStateChangeServiceImpl getCourseStateChangeService() {
        if (this.courseStateChangeService == null) {
            this.courseStateChangeService = new CourseStateChangeServiceImpl();
            this.courseStateChangeService.setCourseService(getCourseService());
        }
        return this.courseStateChangeService;
    }    
    /*
     * Recursively set state for StatementTreeViewInfo
     * TODO: We are not able to reuse the code in CourseStateUtil for dependency reason.
     */   
    public void statementTreeViewInfoStateSetter(String courseState, Iterator<StatementTreeViewInfo> itr) {
    	while(itr.hasNext()) {
        	StatementTreeViewInfo statementTreeViewInfo = (StatementTreeViewInfo)itr.next();
        	statementTreeViewInfo.setState(courseState);
        	List<ReqComponentInfo> reqComponents = statementTreeViewInfo.getReqComponents();
        	for(Iterator<ReqComponentInfo> it = reqComponents.iterator(); it.hasNext();)
        		it.next().setState(courseState);

        	statementTreeViewInfoStateSetter(courseState, statementTreeViewInfo.getStatements().iterator());
        }
    }
}
