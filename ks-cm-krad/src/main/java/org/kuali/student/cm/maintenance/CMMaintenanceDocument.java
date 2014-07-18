/**
 * Copyright 2014 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 *
 * Created by venkat on 3/13/14
 */
package org.kuali.student.cm.maintenance;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.kew.api.KewApiConstants;
import org.kuali.rice.kew.api.KewApiServiceLocator;
import org.kuali.rice.kew.api.WorkflowDocument;
import org.kuali.rice.kew.api.WorkflowDocumentFactory;
import org.kuali.rice.kew.api.action.ActionRequest;
import org.kuali.rice.kew.api.action.ActionTaken;
import org.kuali.rice.kew.api.document.WorkflowDocumentService;
import org.kuali.rice.kew.framework.postprocessor.*;
import org.kuali.rice.kim.api.identity.principal.Principal;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.maintenance.MaintenanceDocumentBase;
import org.kuali.rice.krad.rules.rule.event.KualiDocumentEvent;
import org.kuali.rice.krad.rules.rule.event.SaveEvent;
import org.kuali.rice.krad.util.NoteType;
import org.kuali.rice.student.StudentWorkflowConstants;
import org.kuali.rice.student.bo.KualiStudentKimAttributes;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.lum.workflow.CourseStateChangeServiceImpl;
import org.kuali.student.r1.common.rice.StudentIdentityConstants;
import org.kuali.student.r1.core.proposal.ProposalConstants;
import org.kuali.student.r1.core.statement.dto.ReqComponentInfo;
import org.kuali.student.r1.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.DtoConstants;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.util.AttributeHelper;
import org.kuali.student.r2.core.proposal.dto.ProposalInfo;
import org.kuali.student.r2.core.proposal.service.ProposalService;
import org.kuali.student.r2.lum.clu.CLUConstants;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.service.CourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Entity;
import javax.xml.namespace.QName;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Document class for all CM maintenance documents which skips the xml serialization of <class>MaintainableImpl</class>
 * Also, on document save, the data will be stored in KS tables instead of serializing the data to KRNS_MAINT_DOC_T.
 * As we skipped the serialization, on retrieve, this class should take care of loading the data from KS tables
 * whenever we load the maintenace document. This happens at <method>processAfterRetrieve()</method> which should
 * be calling <method>CMMaintainable.retrieveDataObject()</method> to load the data.
 *
 * @author Kuali Student Team
 */

@Entity
public class CMMaintenanceDocument extends MaintenanceDocumentBase {

    private static final long serialVersionUID = -505085142412593315L;
    private static final Logger LOG = LoggerFactory.getLogger(CMMaintenanceDocument.class);

    private CourseService courseService;
    private CourseStateChangeServiceImpl courseStateChangeService;
    private ProposalService proposalService;

    public CMMaintenanceDocument() {
        super();
    }

    public CMMaintenanceDocument(String documentTypeName) {
        super(documentTypeName);
    }

    @Override
    public void processAfterRetrieve() {

        if (documentHeader == null || !documentHeader.hasWorkflowDocument()) {
            throw new RuntimeException("Document Header or workflow document is null");
        }

        String documentTypeName = documentHeader.getWorkflowDocument().getDocumentTypeName();

        Class clazz = getDocumentDictionaryService().getMaintainableClass(documentTypeName);

        if (!CMMaintainable.class.isAssignableFrom(clazz)) {
            throw new RuntimeException("Maintainable should be of CMMaintainable type");
        }

        try {

            Class<?> dataObjectClazz = getDocumentDictionaryService().getMaintenanceDataObjectClass(documentTypeName);

            /**
             * Null check needed here as DocumentServiceImpl.validateAndPersistDocument() calls this method after
             * save. In that case, it's not needed to create a new instance.
             */
            if (oldMaintainableObject == null) {
                oldMaintainableObject = (CMMaintainable) clazz.newInstance();
                oldMaintainableObject.setDataObject(dataObjectClazz.newInstance());
                oldMaintainableObject.setDataObjectClass(dataObjectClazz);
            }

            if (newMaintainableObject == null) {
                newMaintainableObject = (CMMaintainable) clazz.newInstance();
                newMaintainableObject.setDataObject(dataObjectClazz.newInstance());
                newMaintainableObject.setDataObjectClass(dataObjectClazz);
            }

        } catch (InstantiationException e) {
            throw new RuntimeException("Unable to initialize maintainables of type " + clazz.getName(), e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Unable to initialize maintainables of type " + clazz.getName(), e);
        }

        super.processAfterRetrieve();

        ((CMMaintainable) newMaintainableObject).retrieveDataObject();

    }

    @Override
    public void prepareForSave(KualiDocumentEvent event) {
        super.prepareForSave(event);

        if (event instanceof SaveEvent) {
            getNewMaintainableObject().saveDataObject();
        }
    }

    /**
     * These methods deal with (de)serialization of the MaintainableImpl.
     */
    @Override
    public void populateXmlDocumentContentsFromMaintainables() {
        xmlDocumentContents = StringUtils.EMPTY;
    }

    @Override
    public void populateMaintainablesFromXmlDocumentContents() {
        xmlDocumentContents = StringUtils.EMPTY;
    }

    /*
        This method is copied from KualiStudentPostProcessorBase
     */

    public void doActionTaken(ActionTakenEvent actionTakenEvent)  {
        boolean success = true;
        ActionTaken actionTaken = actionTakenEvent.getActionTaken();
        String actionTakeCode = actionTakenEvent.getActionTaken().getActionTaken().getCode();
        try{
        // on a save action we may not have access to the proposal object because the transaction may not have committed
        if (!StringUtils.equals(KewApiConstants.ROUTE_HEADER_SAVED_CD, actionTakeCode)) {
            ProposalInfo proposalInfo = getProposalService().getProposalByWorkflowId(actionTakenEvent.getDocumentId().toString(), ContextUtils.getContextInfo());
            if (actionTaken == null) {
                throw new RuntimeException("No action taken found for document id " + actionTakenEvent.getDocumentId());
            }
            if (StringUtils.equals(KewApiConstants.ACTION_TAKEN_SU_DISAPPROVED_CD, actionTakeCode)) {
                // the custom method below is needed for the unique problem of the states being set for a Withdraw action in KS
                processSuperUserDisapproveActionTaken(actionTakenEvent, actionTaken, proposalInfo);
            }
            // only attempt to remove the adhoc permission if the action taken was not an adhoc revocation
            else if (!StringUtils.equals(KewApiConstants.ACTION_TAKEN_ADHOC_REVOKED_CD, actionTakeCode)) {
                List<ActionRequest> actionRequests = getWorkflowDocumentService().getRootActionRequests(actionTakenEvent.getDocumentId());
                for (ActionRequest actionRequest : actionRequests) {
                    if (actionRequest.isAdHocRequest() && actionRequest.isUserRequest()) {
                        processActionTakenOnAdhocRequest(actionTakenEvent, actionRequest);
                    }
                }
            }
            success = processCustomActionTaken(actionTakenEvent, actionTaken, proposalInfo);
        } else {
            success = processCustomSaveActionTaken(actionTakenEvent, actionTaken);
        }
        }
        catch(Exception e){
            throw new RuntimeException("Error in action taken for document", e);
        }
      //  return new ProcessDocReport(success);
    }

    /*
       This method is copied from KualiStudentPostProcessorBase
    */

    protected boolean processCustomSaveActionTaken(ActionTakenEvent actionTakenEvent, ActionTaken actionTaken) throws Exception {
        // do nothing
        return true;
    }

    /*
       This method is copied from KualiStudentPostProcessorBase
    */

    protected void processActionTakenOnAdhocRequest(ActionTakenEvent actionTakenEvent, ActionRequest actionRequest) throws Exception {
        ProposalInfo proposalInfo = getProposalService().getProposalByWorkflowId(actionTakenEvent.getDocumentId(), ContextUtils.getContextInfo());
        WorkflowDocument doc = WorkflowDocumentFactory.loadDocument(getPrincipalIdForSystemUser(), proposalInfo.getWorkflowId());
        LOG.info("Clearing EDIT permissions added via adhoc requests to principal id: {}", actionRequest.getPrincipalId());
        removeEditAdhocPermissions(actionRequest.getPrincipalId(), doc);
    }

    /*
       This method is copied from KualiStudentPostProcessorBase
    */

    protected void processSuperUserDisapproveActionTaken(ActionTakenEvent actionTakenEvent, ActionTaken actionTaken, ProposalInfo proposalInfo) throws Exception {
        LOG.info("Action taken was 'Super User Disapprove' which is a 'Withdraw' in Kuali Student");
        LOG.info("Will set proposal state to '{}'", ProposalConstants.PROPOSAL_STATE_WITHDRAWN);
        updateProposal(actionTakenEvent, ProposalConstants.PROPOSAL_STATE_WITHDRAWN, proposalInfo);
        processWithdrawActionTaken(actionTakenEvent, proposalInfo);
    }

    /*
        This method is copied from KualiStudentPostProcessorBase
    */
    public void doRouteLevelChange(DocumentRouteLevelChange documentRouteLevelChange) {

        ProposalInfo proposalInfo = null;
        try{
        proposalInfo = getProposalService().getProposalByWorkflowId(documentRouteLevelChange.getDocumentId(), ContextUtils.getContextInfo());
        }
        catch(Exception e){
            throw new RuntimeException("Error in route level change ", e);
        }

        // if this is the initial route then clear only edit permissions as per KSLUM-192
        if (StringUtils.equals(StudentWorkflowConstants.DEFAULT_WORKFLOW_DOCUMENT_START_NODE_NAME, documentRouteLevelChange.getOldNodeName())) {
            // remove edit perm for all adhoc action requests to a user for the route node we just exited
            WorkflowDocument doc = WorkflowDocumentFactory.createDocument(getPrincipalIdForSystemUser(), proposalInfo.getType()/*documentRouteLevelChange.getDocumentId()*/);
            // TODO: evaluate group or role level changes by not using isUserRequest()
            for (ActionRequest actionRequest : doc.getRootActionRequests()) {
                if (actionRequest.isAdHocRequest() && actionRequest.isUserRequest() &&
                        StringUtils.equals(documentRouteLevelChange.getOldNodeName(), actionRequest.getNodeName())) {
                    LOG.info("Clearing EDIT permissions added via adhoc requests to principal id: {}", actionRequest.getPrincipalId());
                    removeEditAdhocPermissions(actionRequest.getPrincipalId(), doc);
                }
            }
        } else {
            LOG.warn("Will not clear any permissions added via adhoc requests");
        }
       // boolean success = processCustomRouteLevelChange(documentRouteLevelChange, proposalInfo);
    }

    /*
        This method is copied from KualiStudentPostProcessorBase
    */
    protected boolean processCustomRouteLevelChange(
            DocumentRouteLevelChange documentRouteLevelChange,
            ProposalInfo proposalInfo) throws Exception {
        //Update the proposal with the new node name
        new AttributeHelper(proposalInfo.getAttributes()).put("workflowNode", documentRouteLevelChange.getNewNodeName());
        getProposalService().updateProposal(proposalInfo.getId(), proposalInfo, ContextUtils.getContextInfo());
        return true;
    }

    /*
        This method is copied from KualiStudentPostProcessorBase
    */

    public void doRouteStatusChange(DocumentRouteStatusChange documentRouteStatusChange) {
        boolean success = true;
        // if document is transitioning from INITIATED to SAVED then transaction prevents us from retrieving the proposal
        if (StringUtils.equals(KewApiConstants.ROUTE_HEADER_INITIATED_CD, documentRouteStatusChange.getOldRouteStatus()) &&
                StringUtils.equals(KewApiConstants.ROUTE_HEADER_SAVED_CD, documentRouteStatusChange.getNewRouteStatus())) {
            // assume the proposal status is already correct
            try {
                success = processCustomRouteStatusSavedStatusChange(documentRouteStatusChange);
            } catch (Exception e) {
                throw new RuntimeException("Error in custom route status change ", e);
            }
        } else {
            try {
                ProposalInfo proposalInfo = getProposalService().getProposalByWorkflowId(documentRouteStatusChange.getDocumentId(), ContextUtils.getContextInfo());
                // update the proposal state if the proposalState value is not null (allows for clearing of the state)
                String proposalState = getProposalStateForRouteStatus(proposalInfo.getState(), documentRouteStatusChange.getNewRouteStatus());
                updateProposal(documentRouteStatusChange, proposalState, proposalInfo);
                success = processCustomRouteStatusChange(documentRouteStatusChange, proposalInfo);
            } catch (Exception e) {
                throw new RuntimeException("Error in custom route status change ", e);
            }
        }
    }

    protected boolean processCustomRouteStatusSavedStatusChange(DocumentRouteStatusChange statusChangeEvent) throws Exception {
        // do nothing but allow override
        return true;
    }

    /**
     * @param currentProposalState  - the current state set on the proposal
     * @param newWorkflowStatusCode - the new route status code that is getting set on the workflow document
     * @return the proposal state to set or null if the proposal does not need it's state changed
     */
    protected String getProposalStateForRouteStatus(String currentProposalState, String newWorkflowStatusCode) {
        if (StringUtils.equals(ProposalConstants.PROPOSAL_STATE_WITHDRAWN, currentProposalState)) {
            // if the current proposal state is Withdrawn... don't change the proposal state
            return null;
        }
        if (StringUtils.equals(KewApiConstants.ROUTE_HEADER_SAVED_CD, newWorkflowStatusCode)) {
            return getProposalStateFromNewState(currentProposalState, ProposalConstants.PROPOSAL_STATE_SAVED);
        } else if (KewApiConstants.ROUTE_HEADER_ENROUTE_CD.equals(newWorkflowStatusCode)) {
            return getProposalStateFromNewState(currentProposalState, ProposalConstants.PROPOSAL_STATE_ENROUTE);
        } else if (KewApiConstants.ROUTE_HEADER_CANCEL_CD.equals(newWorkflowStatusCode)) {
            return getProposalStateFromNewState(currentProposalState, ProposalConstants.PROPOSAL_STATE_CANCELLED);
        } else if (KewApiConstants.ROUTE_HEADER_DISAPPROVED_CD.equals(newWorkflowStatusCode)) {
            return getProposalStateFromNewState(currentProposalState, ProposalConstants.PROPOSAL_STATE_REJECTED);
        } else if (KewApiConstants.ROUTE_HEADER_PROCESSED_CD.equals(newWorkflowStatusCode)) {
            return getProposalStateFromNewState(currentProposalState, ProposalConstants.PROPOSAL_STATE_APPROVED);
        } else if (KewApiConstants.ROUTE_HEADER_EXCEPTION_CD.equals(newWorkflowStatusCode)) {
            return getProposalStateFromNewState(currentProposalState, ProposalConstants.PROPOSAL_STATE_EXCEPTION);
        } else {
            // no status to set
            return null;
        }
    }

    /**
     * Default behavior is to return the <code>newProposalState</code> variable only if it differs from the
     * <code>currentProposalState</code> value. Otherwise <code>null</code> will be returned.
     */
    protected String getProposalStateFromNewState(String currentProposalState, String newProposalState) {
        if (LOG.isInfoEnabled()) {
            LOG.info("current proposal state is '" + currentProposalState + "' and new proposal state will be '" + newProposalState + "'");
        }
        return getStateFromNewState(currentProposalState, newProposalState);
    }

    /**
     * Default behavior is to return the <code>newState</code> variable only if it differs from the
     * <code>currentState</code> value. Otherwise <code>null</code> will be returned.
     */
    protected String getStateFromNewState(String currentState, String newState) {
        if (StringUtils.equals(currentState, newState)) {
            LOG.info("returning null as current state and new state are both '{}'", currentState);
            return null;
        }
        return newState;
    }


    /*
        This method is copied from KualiStudentPostProcessorBase
     */

    protected void removeEditAdhocPermissions(String principalId, WorkflowDocument doc) {
        Map<String, String> qualifications = new LinkedHashMap<String, String>();
        qualifications.put(KualiStudentKimAttributes.DOCUMENT_TYPE_NAME, doc.getDocumentTypeName());
        qualifications.put(KualiStudentKimAttributes.QUALIFICATION_DATA_ID, doc.getApplicationDocumentId());
        KimApiServiceLocator.getRoleService().removePrincipalFromRole(principalId, StudentWorkflowConstants.ROLE_NAME_ADHOC_EDIT_PERMISSIONS_ROLE_NAMESPACE, StudentWorkflowConstants.ROLE_NAME_ADHOC_EDIT_PERMISSIONS_ROLE_NAME, qualifications);
    }

    /*
        This method is copied from KualiStudentPostProcessorBase
     */

    protected void removeCommentAdhocPermissions(String roleNamespace, String roleName, String principalId, WorkflowDocument doc) {
        Map<String, String> qualifications = new LinkedHashMap<String, String>();
        qualifications.put(KualiStudentKimAttributes.DOCUMENT_TYPE_NAME, doc.getDocumentTypeName());
        qualifications.put(KualiStudentKimAttributes.QUALIFICATION_DATA_ID, doc.getApplicationDocumentId());
        KimApiServiceLocator.getRoleService().removePrincipalFromRole(principalId, StudentWorkflowConstants.ROLE_NAME_ADHOC_ADD_COMMENT_PERMISSIONS_ROLE_NAMESPACE, StudentWorkflowConstants.ROLE_NAME_ADHOC_ADD_COMMENT_PERMISSIONS_ROLE_NAME, qualifications);
    }


    /*
        This method is copied from KualiStudentPostProcessorBase
     */

    protected String getPrincipalIdForSystemUser() {
        Principal principal = KimApiServiceLocator.getIdentityService().getPrincipalByPrincipalName(StudentIdentityConstants.SYSTEM_USER_PRINCIPAL_NAME);
        if (principal == null) {
            throw new RuntimeException("Cannot find Principal for principal name: " + StudentIdentityConstants.SYSTEM_USER_PRINCIPAL_NAME);
        }
        return principal.getPrincipalId();
    }

    /*
        This method is copied from KualiStudentPostProcessorBase
     */

    protected void updateProposal(IDocumentEvent iDocumentEvent, String proposalState, ProposalInfo proposalInfo) throws Exception {
        LOG.info("Setting state '{}' on Proposal with docId='{}' and proposalId='{}'", proposalState, proposalInfo.getWorkflowId(), proposalInfo.getId());
        boolean requiresSave = false;
        if (proposalState != null) {
            proposalInfo.setState(proposalState);
            requiresSave = true;
        }
        requiresSave |= preProcessProposalSave(iDocumentEvent, proposalInfo);
        if (requiresSave) {
            getProposalService().updateProposal(proposalInfo.getId(), proposalInfo, ContextUtils.getContextInfo());
        }
    }

    protected boolean preProcessProposalSave(IDocumentEvent iDocumentEvent, ProposalInfo proposalInfo) {
        return false;
    }

    /*
        This method is copied from KualiStudentPostProcessorBase
     */

    protected ProposalService getProposalService() {
        if (this.proposalService == null) {
            this.proposalService = (ProposalService) GlobalResourceLoader.getService(new QName("http://student.kuali.org/wsdl/proposal", "ProposalService"));
        }
        return this.proposalService;
    }

    /*
        This method is copied from KualiStudentPostProcessorBase
     */

    protected WorkflowDocumentService getWorkflowDocumentService() {
        return KewApiServiceLocator.getWorkflowDocumentService();
    }


    /**
     * This method changes the state of the course when a Withdraw action is processed on a proposal.
     * For create and modify proposals, a new clu was created which needs to be cancelled via
     * setting it to "not approved."
     * <p/>
     * For retirement proposals, a clu is never actually created, therefore we don't update the clu at
     * all if it is withdrawn.
     *
     * @param actionTakenEvent - contains the docId, the action taken (code "d"), the principalId which submitted it, etc
     * @param proposalInfo     - The proposal object being withdrawn
     */
    protected void processWithdrawActionTaken(ActionTakenEvent actionTakenEvent, ProposalInfo proposalInfo) throws Exception {

        if (proposalInfo != null) {
            String proposalDocType = proposalInfo.getType();
            // The current two proposal docTypes which being withdrawn will cause a course to be
            // disapproved are Create and Modify (because a new DRAFT version is created when these
            // proposals are submitted.)
            if (CLUConstants.PROPOSAL_TYPE_COURSE_CREATE.equals(proposalDocType)
                    || CLUConstants.PROPOSAL_TYPE_COURSE_MODIFY.equals(proposalDocType)) {
                LOG.info("Will set CLU state to '{}'", DtoConstants.STATE_NOT_APPROVED);
                // Get Clu
                CourseInfo courseInfo = getCourseService().getCourse(
                        getCourseId(proposalInfo), ContextUtils.getContextInfo());
                // Update Clu
                updateCourse(actionTakenEvent, DtoConstants.STATE_NOT_APPROVED,
                        courseInfo, proposalInfo);
            }
            // Retire proposal is the only proposal type at this time which will not require a
            // change to the clu if withdrawn.
            else if (CLUConstants.PROPOSAL_TYPE_COURSE_RETIRE.equals(proposalDocType)) {
                LOG.info("Withdrawing a retire proposal with ID'{}, will not change any CLU state as there is no new CLU object to set.",
                        proposalInfo.getId());
            }
        } else {
            LOG.info("Proposal Info is null when a withdraw proposal action was taken, doing nothing.");
        }
    }

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
    protected boolean processCustomRouteStatusChange(DocumentRouteStatusChange statusChangeEvent, ProposalInfo proposalInfo) throws Exception {

        String courseId = getCourseId(proposalInfo);
        String prevEndTermAtpId = new AttributeHelper(proposalInfo.getAttributes()).get("prevEndTerm");

        // Get the current "existing" courseInfo
        CourseInfo courseInfo = getCourseService().getCourse(courseId, ContextUtils.getContextInfo());

        // Get the new state the course should now change to
        String newCourseState = getCluStateForRouteStatus(courseInfo.getStateKey(), statusChangeEvent.getNewRouteStatus(), proposalInfo.getType());

        //Use the state change service to update to active and update preceding versions
        if (newCourseState != null) {
            if (DtoConstants.STATE_ACTIVE.equals(newCourseState)) {

                // Change the state using the effective date as the version start date
                // update course and save it for retire if state = retire
                getCourseStateChangeService().changeState(courseId, newCourseState, prevEndTermAtpId, ContextUtils.getContextInfo());
            } else

                // Retire By Proposal will come through here, extra data will need
                // to be copied from the proposalInfo to the courseInfo fields before
                // the save happens.
                if (DtoConstants.STATE_RETIRED.equals(newCourseState)) {
                    retireCourseByProposalCopyAndSave(newCourseState, courseInfo, proposalInfo);
                    getCourseStateChangeService().changeState(courseId, newCourseState, prevEndTermAtpId, ContextUtils.getContextInfo());
                } else { // newCourseState of null comes here, is this desired?
                    updateCourse(statusChangeEvent, newCourseState, courseInfo, proposalInfo);
                }
        }
        return true;
    }

    /**
     * In this method, the proposal object fields are copied to the cluInfo object
     * fields to pass validation. This method copies data from the custom Retire
     * By Proposal proposalInfo Object Fields into the courseInfo object so that upon save it will
     * pass validation.
     * <p/>
     * Admin Retire and Retire by Proposal both end up here.
     * <p/>
     * This Route will get you here, Route Statuses:
     * 'S' Saved
     * 'R' Enroute
     * 'A' Approved - After final approve, status is set to 'A'
     * 'P' Processed - During this run through coursepostprocessorbase, assuming
     * doctype is Retire, we end up here.
     *
     * @param courseState  - used to confirm state is retired
     * @param courseInfo   - course object we are updating
     * @param proposalInfo - proposal object which has the on-screen fields we are copying from
     */
    protected void retireCourseByProposalCopyAndSave(String courseState, CourseInfo courseInfo, ProposalInfo proposalInfo) throws Exception {

        // Copy the data to the object -
        // These Proposal Attribs need to go back to courseInfo Object
        // to pass validation.
        if (DtoConstants.STATE_RETIRED.equals(courseState)) {
            if ((proposalInfo != null) && (proposalInfo.getAttributes() != null)) {
                String rationale = null;
                if (proposalInfo.getRationale() != null) {
                    rationale = proposalInfo.getRationale().getPlain();
                }
                String proposedEndTerm = new AttributeHelper(proposalInfo.getAttributes()).get("proposedEndTerm");
                String proposedLastTermOffered = new AttributeHelper(proposalInfo.getAttributes()).get("proposedLastTermOffered");
                String proposedLastCourseCatalogYear = new AttributeHelper(proposalInfo.getAttributes()).get("proposedLastCourseCatalogYear");

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
                if ((proposalInfo != null) && (courseInfo != null)
                        && (courseInfo.getAttributeValue("lastTermOffered") == null)) {
                    courseInfo.getAttributes().add(new AttributeInfo("lastTermOffered", new AttributeHelper(proposalInfo.getAttributes()).get("proposedEndTerm")));
                }
            }
        }
        // Save the Data to the DB
        getCourseService().updateCourse(courseInfo.getId(), courseInfo, ContextUtils.getContextInfo());
    }

    protected String getCourseId(ProposalInfo proposalInfo) throws OperationFailedException {
        if (proposalInfo.getProposalReference().size() != 1) {
            String message = String.format("Found %s CLU objects linked to proposal with docId='%s' and proposalId='%s'. Must have exactly 1 linked.",
                    proposalInfo.getProposalReference().size(), proposalInfo.getWorkflowId(), proposalInfo.getId());
            LOG.error(message);
            throw new OperationFailedException(message);
        }
        return proposalInfo.getProposalReference().get(0);
    }

    /**
     * This method returns the state a clu should go to, based on
     * the Proposal's docType and the newWorkflow StatusCode
     * which are passed in.
     *
     * @param currentCluState       - the current state set on the CLU
     * @param newWorkflowStatusCode - the new route status code that is getting set on the workflow document
     * @param docType               - The doctype of the proposal which kicked off this workflow.
     * @return the CLU state to set or null if the CLU does not need it's state changed
     */
    protected String getCluStateForRouteStatus(String currentCluState, String newWorkflowStatusCode, String docType) {
        if (CLUConstants.PROPOSAL_TYPE_COURSE_RETIRE.equals(docType)) {
            // This is for Retire Proposal, Course State should remain active for
            // all other route statuses.
            if (KewApiConstants.ROUTE_HEADER_PROCESSED_CD.equals(newWorkflowStatusCode)) {
                return DtoConstants.STATE_RETIRED;
            }
            return null;  // returning null indicates no change in course state required
        } else {
            //  The following is for Create, Modify, and Admin Modify proposals.
            if (StringUtils.equals(KewApiConstants.ROUTE_HEADER_SAVED_CD, newWorkflowStatusCode)) {
                return getCourseStateFromNewState(currentCluState, DtoConstants.STATE_DRAFT);
            } else if (KewApiConstants.ROUTE_HEADER_CANCEL_CD.equals(newWorkflowStatusCode)) {
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
        LOG.info("current CLU state is '{}' and new CLU state will be '{}'", currentCourseState, newCourseState);
        return getStateFromNewState(currentCourseState, newCourseState);
    }

    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    protected void updateCourse(IDocumentEvent iDocumentEvent, String courseState, CourseInfo courseInfo, ProposalInfo proposalInfo) throws Exception {
        // only change the state if the course is not currently set to that state
        boolean requiresSave = false;
        if (courseState != null) {
            LOG.info("Setting state '{}' on CLU with cluId='{}'", courseState, courseInfo.getId());
            courseInfo.setStateKey(courseState);
            requiresSave = true;
        }
        LOG.info("Running preProcessCluSave with cluId='{}'", courseInfo.getId());
        requiresSave |= preProcessCourseSave(iDocumentEvent, courseInfo);

        if (requiresSave) {
            getCourseService().updateCourse(courseInfo.getId(), courseInfo, ContextUtils.getContextInfo());

            //For a newly approved course (w/no prior active versions), make the new course the current version.
            if (DtoConstants.STATE_ACTIVE.equals(courseState) && courseInfo.getVersion().getCurrentVersionStart() == null) {
                // TODO: set states of other approved courses to superseded

                // if current version's state is not active then we can set this course as the active course
                //if (!DtoConstants.STATE_ACTIVE.equals(getCourseService().getCourse(getCourseService().getCurrentVersion(CourseServiceConstants.COURSE_NAMESPACE_URI, courseInfo.getVersion().getVersionIndId()).getId()).getState())) {
                getCourseService().setCurrentCourseVersion(courseInfo.getId(), null, ContextUtils.getContextInfo());
                //}
            }

            List<StatementTreeViewInfo> statementTreeViewInfos = courseService.getCourseStatements(courseInfo.getId(), null, null, ContextUtils.getContextInfo());
            if (statementTreeViewInfos != null) {
                statementTreeViewInfoStateSetter(courseInfo.getStateKey(), statementTreeViewInfos.iterator());

                for (Iterator<StatementTreeViewInfo> it = statementTreeViewInfos.iterator(); it.hasNext(); )

                    courseService.updateCourseStatement(courseInfo.getId(), courseState, it.next(), ContextUtils.getContextInfo());
            }
        }

    }

    protected boolean preProcessCourseSave(IDocumentEvent iDocumentEvent, CourseInfo courseInfo) {
        return false;
    }

    protected CourseService getCourseService() {
        if (this.courseService == null) {
            this.courseService = (CourseService) GlobalResourceLoader.getService(new QName("http://student.kuali.org/wsdl/course", "CourseService"));
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
        while (itr.hasNext()) {
            StatementTreeViewInfo statementTreeViewInfo = (StatementTreeViewInfo) itr.next();
            statementTreeViewInfo.setState(courseState);
            List<ReqComponentInfo> reqComponents = statementTreeViewInfo.getReqComponents();
            for (Iterator<ReqComponentInfo> it = reqComponents.iterator(); it.hasNext(); )
                it.next().setState(courseState);

            statementTreeViewInfoStateSetter(courseState, statementTreeViewInfo.getStatements().iterator());
        }
    }

}
