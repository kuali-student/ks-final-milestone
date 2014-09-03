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
 * Created by delyea on 8/29/14
 */
package org.kuali.student.cm.proposal.service.impl;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.tree.Tree;
import org.kuali.rice.kew.api.KewApiConstants;
import org.kuali.rice.kew.api.KewApiServiceLocator;
import org.kuali.rice.kew.api.WorkflowDocument;
import org.kuali.rice.kew.api.WorkflowDocumentFactory;
import org.kuali.rice.kew.api.action.ActionRequest;
import org.kuali.rice.kew.api.action.ActionTaken;
import org.kuali.rice.kew.api.document.WorkflowDocumentService;
import org.kuali.rice.kew.framework.postprocessor.ActionTakenEvent;
import org.kuali.rice.kew.framework.postprocessor.DocumentRouteLevelChange;
import org.kuali.rice.kew.framework.postprocessor.DocumentRouteStatusChange;
import org.kuali.rice.kew.framework.postprocessor.IDocumentEvent;
import org.kuali.rice.kim.api.identity.principal.Principal;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.uif.container.Container;
import org.kuali.rice.krad.uif.util.ObjectPropertyUtils;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;
import org.kuali.rice.krms.api.repository.agenda.AgendaDefinition;
import org.kuali.rice.krms.api.repository.agenda.AgendaItemDefinition;
import org.kuali.rice.krms.api.repository.reference.ReferenceObjectBinding;
import org.kuali.rice.krms.dto.AgendaEditor;
import org.kuali.rice.krms.dto.PropositionEditor;
import org.kuali.rice.krms.dto.RuleEditor;
import org.kuali.rice.krms.dto.TemplateInfo;
import org.kuali.rice.krms.dto.TermParameterEditor;
import org.kuali.rice.krms.service.RuleViewHelperService;
import org.kuali.rice.krms.service.impl.RuleEditorMaintainableImpl;
import org.kuali.rice.krms.tree.RuleViewTreeBuilder;
import org.kuali.rice.krms.tree.node.CompareTreeNode;
import org.kuali.rice.krms.util.NaturalLanguageHelper;
import org.kuali.student.cm.common.util.CurriculumManagementConstants;
import org.kuali.student.cm.course.controller.CourseController;
import org.kuali.student.cm.course.service.impl.CourseRuleViewHelperServiceImpl;
import org.kuali.student.cm.proposal.form.wrapper.ProposalElementsWrapper;
import org.kuali.student.cm.proposal.form.wrapper.SupportingDocumentInfoWrapper;
import org.kuali.student.cm.proposal.service.ProposalMaintainable;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.core.krms.tree.KSRuleViewTreeBuilder;
import org.kuali.student.core.rice.authorization.DocumentCollaboratorHelper;
import org.kuali.student.lum.lu.ui.krms.dto.LUAgendaEditor;
import org.kuali.student.lum.lu.ui.krms.dto.LURuleEditor;
import org.kuali.student.lum.lu.ui.krms.tree.LURuleViewTreeBuilder;
import org.kuali.student.r1.common.rice.StudentIdentityConstants;
import org.kuali.student.r1.common.rice.StudentProposalRiceConstants;
import org.kuali.student.r1.common.rice.authorization.ProposalPermissionTypes;
import org.kuali.student.r1.core.personsearch.service.impl.PersonSearchServiceImpl;
import org.kuali.student.r1.core.personsearch.service.impl.QuickViewByGivenName;
import org.kuali.student.r1.core.proposal.ProposalConstants;
import org.kuali.student.r1.core.workflow.dto.CollaboratorWrapper;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.util.AttributeHelper;
import org.kuali.student.r2.core.constants.DocumentServiceConstants;
import org.kuali.student.r2.core.constants.ProposalServiceConstants;
import org.kuali.student.r2.core.document.dto.DocumentBinaryInfo;
import org.kuali.student.r2.core.document.dto.DocumentHeaderDisplayInfo;
import org.kuali.student.r2.core.document.dto.DocumentInfo;
import org.kuali.student.r2.core.document.dto.RefDocRelationInfo;
import org.kuali.student.r2.core.document.service.DocumentService;
import org.kuali.student.r2.core.proposal.dto.ProposalInfo;
import org.kuali.student.r2.core.proposal.service.ProposalService;
import org.kuali.student.r2.core.search.dto.SearchParamInfo;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;
import org.kuali.student.r2.core.search.service.SearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Interface class for common Proposal methods
 */
public abstract class ProposalMaintainableImpl extends RuleEditorMaintainableImpl implements ProposalMaintainable, RuleViewHelperService {
    private static final Logger LOG = LoggerFactory.getLogger(ProposalMaintainableImpl.class);

    private transient ProposalService proposalService;

    private transient DocumentService documentService;

    private transient SearchService searchService;

    private transient KSRuleViewTreeBuilder viewTreeBuilder;

    private transient NaturalLanguageHelper nlHelper;

    private RuleViewHelperService ruleViewHelperService = new CourseRuleViewHelperServiceImpl();

    public ProposalInfo getProposalInfo() {
        return ((ProposalElementsWrapper)getDataObject()).getProposalInfo();
    }

    public void retrieveDataObject() {
        try {
            ProposalElementsWrapper dataObject = (ProposalElementsWrapper) getDataObject();
            ProposalInfo proposal = getProposalService().getProposalByWorkflowId(getDocumentNumber(), ContextUtils.createDefaultContextInfo());
            dataObject.setProposalInfo(proposal);
        } catch (Exception e) {
            throw new RuntimeException("Caught Exception while populating Proposal data", e);
        }

    }

    /**
     * @see org.kuali.student.cm.proposal.service.ProposalMaintainable#copyProposal(String)
     */
    public ProposalElementsWrapper copyProposal(String sourceProposalId) throws Exception {
        // fetch the proposal via the given id
        ProposalInfo sourceProposal = getProposalService().getProposal(sourceProposalId, ContextUtils.createDefaultContextInfo());
        if (sourceProposal == null) {
            throw new RuntimeException("Cannot find proposal for id: " + sourceProposalId);
        }
        // use the proposal to call the abstract method which is implemented by sub classes
        ProposalElementsWrapper targetProposalElementsWrapper = copyWrapperObjectsToProposal(sourceProposal);

        ProposalInfo targetProposal = new ProposalInfo();
        targetProposal.setName("Copy of " + sourceProposal.getName());
        targetProposalElementsWrapper.setProposalInfo(targetProposal);

        return targetProposalElementsWrapper;
    }

    public abstract ProposalElementsWrapper copyWrapperObjectsToProposal(ProposalInfo sourceProposal) throws Exception;

    /**
     * @see org.kuali.student.cm.proposal.service.ProposalMaintainable#updateReview()
     */
    public void updateReview() {
        ProposalElementsWrapper proposalElementsWrapper = (ProposalElementsWrapper) getDataObject();
        updateReview(proposalElementsWrapper, true);
    }

    protected void updateReview(ProposalElementsWrapper proposalElementsWrapper, boolean shouldRepopulateRemoteData) {
        updateSupportingDocumentsForReviewPages(proposalElementsWrapper);
        copySupportingDocumentsToReviewPages(proposalElementsWrapper);
        updateRemoteDataElementsForReviewPages(proposalElementsWrapper, shouldRepopulateRemoteData);
    }

    protected void updateSupportingDocumentsForReviewPages(ProposalElementsWrapper proposalElementsWrapper) {
        proposalElementsWrapper.getReviewProposalDisplay().getSupportingDocumentsSection().getSupportingDocuments().clear();
        for (SupportingDocumentInfoWrapper supportingDoc : proposalElementsWrapper.getSupportingDocs()) {
            if (supportingDoc.isNewDto() && supportingDoc.getDocumentUpload() != null) {
                proposalElementsWrapper.getReviewProposalDisplay().getSupportingDocumentsSection().getSupportingDocuments().add(supportingDoc);
            }
        }
    }

    protected void copySupportingDocumentsToReviewPages(ProposalElementsWrapper proposalElementsWrapper) {
        proposalElementsWrapper.getReviewProposalDisplay().getSupportingDocumentsSection().getSupportingDocuments().clear();
        for (SupportingDocumentInfoWrapper supportingDocumentInfoWrapper : proposalElementsWrapper.getSupportingDocs()) {
            SupportingDocumentInfoWrapper supportingDocReviewObj = new SupportingDocumentInfoWrapper();
            supportingDocReviewObj.setDocumentId(supportingDocumentInfoWrapper.getDocumentId());
            supportingDocReviewObj.setDescription(supportingDocumentInfoWrapper.getDescription());
            supportingDocReviewObj.setDocumentName(supportingDocumentInfoWrapper.getDocumentName());
            proposalElementsWrapper.getReviewProposalDisplay().getSupportingDocumentsSection().getSupportingDocuments().add(supportingDocReviewObj);
        }

    }

    protected void updateRemoteDataElementsForReviewPages(ProposalElementsWrapper proposalElementsWrapper, boolean shouldRepopulateRemoteData) {
        // update collaborator section
        proposalElementsWrapper.getReviewProposalDisplay().getCollaboratorSection().getCollaboratorWrappers().clear();
        if (shouldRepopulateRemoteData) {
            updateCollaborators(proposalElementsWrapper);
        }
        try {
            for (CollaboratorWrapper collaboratorWrapper : proposalElementsWrapper.getCollaboratorWrappers()) {
                // need to clone the object because we're hacking the use of the 'action' and 'permission' fields on the CollaboratorWrapper object
                CollaboratorWrapper reviewPageCollaboratorWrapper = collaboratorWrapper.clone();
                StudentProposalRiceConstants.ActionRequestType actionRequestType = StudentProposalRiceConstants.ActionRequestType.getByCode(reviewPageCollaboratorWrapper.getAction());
                if (actionRequestType == null) {
                    throw new RuntimeException("Cannot find valid action request type for code: " + reviewPageCollaboratorWrapper.getAction());
                }
                reviewPageCollaboratorWrapper.setAction(actionRequestType.getActionRequestLabel());

                ProposalPermissionTypes proposalPermissionType = ProposalPermissionTypes.getByCode(reviewPageCollaboratorWrapper.getPermission());
                if (proposalPermissionType == null) {
                    throw new RuntimeException("Cannot find valid permission type for code: " + reviewPageCollaboratorWrapper.getPermission());
                }
                if (ProposalPermissionTypes.EDIT.equals(proposalPermissionType)) {
                    reviewPageCollaboratorWrapper.setPermission(ProposalPermissionTypes.EDIT.getLabel() + ", " + ProposalPermissionTypes.ADD_COMMENT.getLabel() + ", " + ProposalPermissionTypes.OPEN.getLabel());
                } else if (ProposalPermissionTypes.ADD_COMMENT.equals(proposalPermissionType)) {
                    reviewPageCollaboratorWrapper.setPermission(ProposalPermissionTypes.ADD_COMMENT.getLabel() + ", " + ProposalPermissionTypes.OPEN.getLabel());
                } else if (ProposalPermissionTypes.OPEN.equals(proposalPermissionType)) {
                    reviewPageCollaboratorWrapper.setPermission(ProposalPermissionTypes.OPEN.getLabel());
                } else {
                    throw new RuntimeException("Invalid Collaboration Permission Type Used: " + proposalPermissionType.getCode());
                }
                proposalElementsWrapper.getReviewProposalDisplay().getCollaboratorSection().getCollaboratorWrappers().add(reviewPageCollaboratorWrapper);
            }
        } catch (Exception e) {
            LOG.error("Error setting up Collaborators", e);
            throw new RuntimeException(e);
        }
    }

    /*
        This method is copied from KualiStudentPostProcessorBase
    */
    @Override
    public void doActionTaken(ActionTakenEvent actionTakenEvent) throws Exception {
        ActionTaken actionTaken = actionTakenEvent.getActionTaken();
        String actionTakeCode = actionTakenEvent.getActionTaken().getActionTaken().getCode();
        // on a save action we may not have access to the proposal object because the transaction may not have committed
        if (!StringUtils.equals(KewApiConstants.ROUTE_HEADER_SAVED_CD, actionTakeCode)) {
            if (actionTaken == null) {
                throw new RuntimeException("No action taken found for document id " + actionTakenEvent.getDocumentId());
            }
            ProposalInfo proposalInfo = getProposalService().getProposalByWorkflowId(actionTakenEvent.getDocumentId().toString(), ContextUtils.createDefaultContextInfo());
            if (StringUtils.equals(KewApiConstants.ACTION_TAKEN_SU_DISAPPROVED_CD, actionTakeCode)) {
                // the custom method below is needed for the unique problem of the states being set for a Withdraw action in KS
                processSuperUserDisapproveActionTaken(actionTakenEvent, actionTaken, proposalInfo);
            }
            // only attempt to remove the adhoc permission if the action taken was not an adhoc revocation as an
            //    as an adhoc revocation will have already removed the adhoc permissions for edit access
            else if (!StringUtils.equals(KewApiConstants.ACTION_TAKEN_ADHOC_REVOKED_CD, actionTakeCode)) {
                List<ActionRequest> actionRequests = getWorkflowDocumentService().getRootActionRequests(actionTakenEvent.getDocumentId());
                for (ActionRequest actionRequest : actionRequests) {
                    // for now we only care about adhoc requests to individual users (aka collaborators)
                    if (actionRequest.isAdHocRequest() && actionRequest.isUserRequest()) {
                        // only process actions taken on adhoc requests if the request is satisfied by the action taken
                        if ((actionRequest.getActionTaken() != null) && (StringUtils.equals(actionTaken.getId(), actionRequest.getActionTaken().getId()))) {
                            processActionTakenOnAdhocRequest(actionTakenEvent, actionRequest);
                        }
                    }
                }
            }
            processCustomActionTaken(actionTakenEvent, actionTaken, proposalInfo);
        } else {
            processCustomSaveActionTaken(actionTakenEvent, actionTaken);
        }
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

    /**
     * This method allows a custom implementation of the Withdraw action.
     *
     * @param actionTakenEvent - contains the docId, the action taken (code "d"), the principalId which submitted it, etc
     * @param proposalInfo     - The proposal object being withdrawn
     */
    protected void processWithdrawActionTaken(ActionTakenEvent actionTakenEvent, ProposalInfo proposalInfo) throws Exception {
        // do nothing
    }

    protected void processActionTakenOnAdhocRequest(ActionTakenEvent actionTakenEvent, ActionRequest actionRequest) throws Exception {
        ProposalInfo proposalInfo = getProposalService().getProposalByWorkflowId(actionTakenEvent.getDocumentId(), ContextUtils.createDefaultContextInfo());
        WorkflowDocument doc = WorkflowDocumentFactory.loadDocument(getPrincipalIdForSystemUser(), proposalInfo.getWorkflowId());
        LOG.info("Clearing EDIT permissions added via adhoc requests to principal id: {}", actionRequest.getPrincipalId());
        removeEditAdhocPermissions(actionRequest.getPrincipalId(), doc);
    }

    protected void processCustomActionTaken(ActionTakenEvent actionTakenEvent, ActionTaken actionTaken, ProposalInfo proposalInfo) throws Exception {
        // do nothing
    }

    protected void processCustomSaveActionTaken(ActionTakenEvent actionTakenEvent, ActionTaken actionTaken) throws Exception {
        // do nothing
    }


    /*
        This method is copied from KualiStudentPostProcessorBase
    */
    @Override
    public void doRouteLevelChange(DocumentRouteLevelChange documentRouteLevelChange) throws Exception {
        ProposalInfo proposalInfo = getProposalService().getProposalByWorkflowId(documentRouteLevelChange.getDocumentId(), ContextUtils.getContextInfo());

        // if this is the initial route then clear only edit permissions as per KSLUM-192
        if (StringUtils.equals(StudentProposalRiceConstants.DEFAULT_WORKFLOW_DOCUMENT_START_NODE_NAME, documentRouteLevelChange.getOldNodeName())) {
            // remove edit perm for all adhoc action requests to a user for the route node we just exited
            WorkflowDocument doc = WorkflowDocumentFactory.loadDocument(getPrincipalIdForSystemUser(), documentRouteLevelChange.getDocumentId());
            for (ActionRequest actionRequest : doc.getRootActionRequests()) {
                // we only care about the actionRequest if it is an adHoc request to a user since that's the only way collaborators are currently added
                if (actionRequest.isAdHocRequest() && actionRequest.isUserRequest() &&
                        StringUtils.equals(documentRouteLevelChange.getOldNodeName(), actionRequest.getNodeName())) {
                    try {
                        LOG.info("Clearing EDIT permissions added via adhoc requests to principal id: {}", actionRequest.getPrincipalId());
                        removeEditAdhocPermissions(actionRequest.getPrincipalId(), doc);
                    } catch (OperationFailedException e) {
                        LOG.error("Could not remove edit access for collaborator '{}' on doc id '{}'", actionRequest.getPrincipalId(), doc.getDocumentId());
                        throw new RuntimeException("Could not remove edit access for collaborator '" + actionRequest.getPrincipalId() + " on doc id '" + doc.getDocumentId() + "'", e);
                    }
                }
            }
        } else {
            LOG.warn("Will not clear any permissions added via adhoc requests during route level change to '{}' on doc id '{}'", documentRouteLevelChange.getNewNodeName(), documentRouteLevelChange.getDocumentId());
        }
        processCustomRouteLevelChange(documentRouteLevelChange, proposalInfo);
    }

    protected void processCustomRouteLevelChange(
            DocumentRouteLevelChange documentRouteLevelChange,
            ProposalInfo proposalInfo) throws Exception {
        //Update the proposal with the new node name
        new AttributeHelper(proposalInfo.getAttributes()).put("workflowNode", documentRouteLevelChange.getNewNodeName());
        getProposalService().updateProposal(proposalInfo.getId(), proposalInfo, ContextUtils.createDefaultContextInfo());
    }

    /*
        This method is copied from KualiStudentPostProcessorBase
    */
    @Override
    public void doRouteStatusChange(DocumentRouteStatusChange documentRouteStatusChange) throws Exception {
        // if document is transitioning from INITIATED to SAVED then transaction prevents us from retrieving the proposal
        if (StringUtils.equals(KewApiConstants.ROUTE_HEADER_INITIATED_CD, documentRouteStatusChange.getOldRouteStatus()) &&
                StringUtils.equals(KewApiConstants.ROUTE_HEADER_SAVED_CD, documentRouteStatusChange.getNewRouteStatus())) {
            // assume the proposal status is already correct
            processCustomRouteStatusSavedStatusChange(documentRouteStatusChange);
        } else {
            ProposalInfo proposalInfo = getProposalService().getProposalByWorkflowId(documentRouteStatusChange.getDocumentId(), ContextUtils.createDefaultContextInfo());
            // update the proposal state if the proposalState value is not null (allows for clearing of the state)
            String proposalState = getProposalStateForRouteStatus(proposalInfo.getState(), documentRouteStatusChange.getNewRouteStatus());
            updateProposal(documentRouteStatusChange, proposalState, proposalInfo);
            processCustomRouteStatusChange(documentRouteStatusChange, proposalInfo);
        }
    }

    protected void processCustomRouteStatusSavedStatusChange(DocumentRouteStatusChange statusChangeEvent) throws Exception {
        // do nothing but allow override
    }

    /**
     * This method is where child classes can do operations
     */
    protected void processCustomRouteStatusChange(DocumentRouteStatusChange statusChangeEvent, ProposalInfo proposalInfo) throws Exception {
        // do nothing
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

    /**
     * Helper method called when post processing should remove the Edit permission given to proposal Collaborators
     *
     * @param principalId id of the user who needs to be removed
     * @param doc the document from which the person's access should be removed
     */
    protected void removeEditAdhocPermissions(String principalId, WorkflowDocument doc) throws OperationFailedException {
        DocumentCollaboratorHelper.removeEditAdhocPermission(principalId, doc.getDocumentTypeName(), doc.getApplicationDocumentId());
    }

    /**
     * Helper method called when post processing should remove the Comment permission given to proposal Collaborators
     *
     * @param principalId id of the user who needs to be removed
     * @param doc the document from which the person's access should be removed
     */
    protected void removeCommentAdhocPermissions(String principalId, WorkflowDocument doc) throws OperationFailedException {
        DocumentCollaboratorHelper.removeCommentAdhocPermission(principalId, doc.getDocumentTypeName(), doc.getApplicationDocumentId());
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

    protected void updateProposal(IDocumentEvent iDocumentEvent, String proposalState, ProposalInfo proposalInfo) throws Exception {
        LOG.info("Setting state '{}' on Proposal with docId='{}' and proposalId='{}'", proposalState, proposalInfo.getWorkflowId(), proposalInfo.getId());
        boolean requiresSave = false;
        if (proposalState != null) {
            proposalInfo.setStateKey(proposalState);
            requiresSave = true;
        }
        requiresSave |= preProcessProposalSave(iDocumentEvent, proposalInfo);
        if (requiresSave) {
            getProposalService().updateProposal(proposalInfo.getId(), proposalInfo, ContextUtils.createDefaultContextInfo());
        }
    }

    protected boolean preProcessProposalSave(IDocumentEvent iDocumentEvent, ProposalInfo proposalInfo) {
        // do nothing
        return false;
    }

    /**
     */
    public List<CollaboratorWrapper> getCollaboratorWrappersSuggest(String textBoxName) {
        List<CollaboratorWrapper> listCollaboratorWrappers = new ArrayList<CollaboratorWrapper>();

        List<SearchParamInfo> queryParamValueList = new ArrayList<SearchParamInfo>();

        SearchParamInfo displayNameParam = new SearchParamInfo();
        displayNameParam.setKey(QuickViewByGivenName.NAME_PARAM);
        displayNameParam.getValues().add(textBoxName);
        queryParamValueList.add(displayNameParam);

        SearchRequestInfo searchRequest = new SearchRequestInfo();
        searchRequest.setSearchKey(QuickViewByGivenName.SEARCH_TYPE);
        searchRequest.setParams(queryParamValueList);
        searchRequest.setStartAt(0);
        searchRequest.setNeededTotalResults(false);
        searchRequest.setSortColumn(QuickViewByGivenName.DISPLAY_NAME_RESULT);

        SearchResultInfo searchResult = null;
        try {
            searchResult = getSearchService().search(searchRequest, ContextUtils.createDefaultContextInfo());
            for (SearchResultRowInfo result : searchResult.getRows()) {
                List<SearchResultCellInfo> cells = result.getCells();
                CollaboratorWrapper theCollaboratorWrapper = new CollaboratorWrapper();
                for (SearchResultCellInfo cell : cells) {
                    if (QuickViewByGivenName.GIVEN_NAME_RESULT.equals(cell.getKey())) {
                        theCollaboratorWrapper.setGivenName(cell.getValue());
                    } else if (QuickViewByGivenName.PERSON_ID_RESULT.equals(cell.getKey())) {
                        theCollaboratorWrapper.setPrincipalId(cell.getValue());
                    } else if (QuickViewByGivenName.ENTITY_ID_RESULT.equals(cell.getKey())) {
                        theCollaboratorWrapper.setEntityId(cell.getValue());
                    } else if (QuickViewByGivenName.PRINCIPAL_NAME_RESULT.equals(cell.getKey())) {
                        theCollaboratorWrapper.setPrincipalName(cell.getValue());
                    } else if (QuickViewByGivenName.DISPLAY_NAME_RESULT.equals(cell.getKey())) {
                        theCollaboratorWrapper.setDisplayName(cell.getValue());
                    }
                }
                listCollaboratorWrappers.add(theCollaboratorWrapper);
            }
        } catch (Exception e) {
            LOG.error("Error retrieving Personel search List", e);
            //throw new RuntimeException();
        }

        return listCollaboratorWrappers;
    }

    /**
     * Method will first attempt to return the proposal title and if that is unavailable it will return the KRAD Document
     * Header description. If they end result of both of those is a blank string, this method will return "New Proposal"
     * because if the method returns a blank string, the KRAD Maintenance Framework will attempt to default the return
     * value to something that will be invalid for CM.
     */
    @Override
    public String getDocumentTitle(MaintenanceDocument document) {
        String docTitle;
        ProposalInfo proposalInfo = getProposalInfo();
        if (proposalInfo != null) {
            LOG.debug("returning proposal title as doc title");
            docTitle = proposalInfo.getName();
        } else {
            LOG.debug("returning document header description as doc title");
            docTitle = document.getDocumentHeader().getDocumentDescription();
        }
        if (StringUtils.isBlank(docTitle)) {
            LOG.warn("doc title is blank... will return default value");
            // returning this because the Maint Framework will default this to an invalid value
            return "New Proposal";
        }
        LOG.debug("returning doc title value '{}'", docTitle);
        return docTitle;
    }

    @SuppressWarnings("deprecation")
    @Override
    protected boolean performAddLineValidation(ViewModel viewModel, Object newLine, String collectionId,
                                               String collectionPath) {
        if (newLine instanceof CollaboratorWrapper) {
            if (viewModel instanceof MaintenanceDocumentForm) {
                MaintenanceDocumentForm modelForm = (MaintenanceDocumentForm) viewModel;
                ProposalElementsWrapper proposalElementsWrapper = (ProposalElementsWrapper) modelForm.getDocument().getNewMaintainableObject().getDataObject();
                for (CollaboratorWrapper collaboratorAuthor : proposalElementsWrapper.getCollaboratorWrappers()) {
                    if (StringUtils.isBlank(collaboratorAuthor.getDisplayName())) {
                        return false; //already have a blank line
                    }
                }
            }
            return true;
        } else if (newLine instanceof SupportingDocumentInfoWrapper) {
            MaintenanceDocumentForm modelForm = (MaintenanceDocumentForm) viewModel;
            ProposalElementsWrapper proposalElementsWrapper = (ProposalElementsWrapper) modelForm.getDocument().getNewMaintainableObject().getDataObject();
            for (SupportingDocumentInfoWrapper doc : proposalElementsWrapper.getSupportingDocs()) {
                populateSupportingDocBytes(doc);
            }
        }
        // is there better way to do the rule stuff? Left it here because this class extends the rule class
        return ((CourseRuleViewHelperServiceImpl) getRuleViewHelperService()).performAddLineValidation(viewModel, newLine, collectionId, collectionPath);
    }

    /**
     * This method transfers the content from MultipartFile to a byte array. MultipartFile's content will be
     * cleared out once the request
     *
     * @param supportingDoc
     */
    public void populateSupportingDocBytes(SupportingDocumentInfoWrapper supportingDoc) {
        try {
            if (supportingDoc.isNewDto() && supportingDoc.getUploadedDoc() == null && supportingDoc.getDocumentUpload() != null && supportingDoc.getDocumentUpload().getBytes() != null) {
                supportingDoc.setDocumentName(supportingDoc.getDocumentUpload().getOriginalFilename());
                supportingDoc.setUploadedDoc(supportingDoc.getDocumentUpload().getBytes());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void processAfterAddLine(ViewModel model, Object lineObject, String collectionId, String collectionPath,
                                    boolean isValidLine) {
        ((CourseRuleViewHelperServiceImpl) getRuleViewHelperService()).processAfterAddLine(model, lineObject, collectionId, collectionPath, isValidLine);
    }

    @Override
    public void processCollectionDeleteLine(ViewModel model, String collectionId, String collectionPath, int lineIndex) {
        MaintenanceDocumentForm maintenanceForm = (MaintenanceDocumentForm) model;
        MaintenanceDocument document = maintenanceForm.getDocument();
        ProposalElementsWrapper proposalElementsWrapper = (ProposalElementsWrapper) document.getNewMaintainableObject().getDataObject();

        Collection<Object> collection = ObjectPropertyUtils.getPropertyValue(model, collectionPath);
        if (collection == null) {
            logAndThrowRuntime(CurriculumManagementConstants.MessageKeys.ERROR_UNABLE_TO_GET_COLLECTION_PROPERTY + collectionPath);
        }

        if (collection instanceof List) {
            Object deleteLine = ((List<Object>) collection).get(lineIndex);
            // if deleted line is CollaboratorWrapper then add the actionRequestId to the list that should be removed
            if ((deleteLine != null) && (deleteLine instanceof CollaboratorWrapper)) {
                CollaboratorWrapper wrapper = (CollaboratorWrapper) deleteLine;
                String actionRequestId = wrapper.getActionRequestId();
                if (StringUtils.isNotBlank(actionRequestId)) {
                    proposalElementsWrapper.getDeletedCollaboratorWrapperActionRequestIds().add(actionRequestId);
                    // if person is also listed as an author then remove the listing
                    if (wrapper.isAuthor()) {
                        proposalElementsWrapper.getProposalInfo().getProposerPerson().remove(wrapper.getPrincipalId());
                    }
                }
            } else if ((deleteLine != null) && (deleteLine instanceof SupportingDocumentInfoWrapper)) {
                SupportingDocumentInfoWrapper supportingDocumentInfoWrapper = (SupportingDocumentInfoWrapper) deleteLine;
                supportingDocumentInfoWrapper.setDocumentUpload(null);
                if (!supportingDocumentInfoWrapper.isNewDto()) {
                    proposalElementsWrapper.getSupportingDocsToDelete().add(supportingDocumentInfoWrapper);
                }
                for (SupportingDocumentInfoWrapper doc : proposalElementsWrapper.getSupportingDocs()) {
                    populateSupportingDocBytes(doc);
                }
            }
        }
        super.processCollectionDeleteLine(model, collectionId, collectionPath, lineIndex);
    }

    @Override
    public void processAfterNew(MaintenanceDocument document, Map<String, String[]> requestParameters) {
        ProposalElementsWrapper proposalElementsWrapper = (ProposalElementsWrapper)getDataObject();

        // Initialize Author & Collaborator
        if (proposalElementsWrapper.getCollaboratorWrappers().isEmpty()) {
            proposalElementsWrapper.getCollaboratorWrappers().add(new CollaboratorWrapper());
        }

        // Initialize Supporting Documents
        if (proposalElementsWrapper.getSupportingDocs().isEmpty()) {
            proposalElementsWrapper.getSupportingDocs().add(new SupportingDocumentInfoWrapper());
        }

        if (requestParameters.get(CourseController.UrlParams.USE_CURRICULUM_REVIEW) != null &&
                requestParameters.get(CourseController.UrlParams.USE_CURRICULUM_REVIEW).length != 0) {
            Boolean isUseReviewProcess = BooleanUtils.toBoolean(requestParameters.get(CourseController.UrlParams.USE_CURRICULUM_REVIEW)[0]);
            proposalElementsWrapper.getUiHelper().setUseReviewProcess(isUseReviewProcess);
        }
    }

    protected void updateCollaborators(ProposalElementsWrapper proposalElementsWrapper) {
        ProposalInfo proposalInfo = proposalElementsWrapper.getProposalInfo();
        if (proposalInfo == null){
            return;
        }
        try {
            proposalElementsWrapper.getCollaboratorWrappers().clear();
            for (CollaboratorWrapper collaboratorWrapper : DocumentCollaboratorHelper.getCollaborators(proposalInfo.getWorkflowId(), proposalInfo.getId(), StudentIdentityConstants.QUALIFICATION_PROPOSAL_REF_TYPE)) {
                String displayName = collaboratorWrapper.getLastName() + ", " + collaboratorWrapper.getFirstName() + " (" + collaboratorWrapper.getPrincipalId() + ")";
                collaboratorWrapper.setDisplayName(displayName);
                // if person is listed as a proposer person in proposalInfo, list them as an author in the collaborators section
                if (proposalInfo.getProposerPerson().contains(collaboratorWrapper.getPrincipalId())) {
                    collaboratorWrapper.setAuthor(true);
                }
                proposalElementsWrapper.getCollaboratorWrappers().add(collaboratorWrapper);
            }
        } catch (Exception e) {
            LOG.error("Error updating Collaborators", e);
            throw new RuntimeException(e);
        }
    }

    protected void populateCollaborators() {
        updateCollaborators((ProposalElementsWrapper) getDataObject());
    }

    protected void populateSupportingDocuments() {

        ProposalElementsWrapper proposalElementsWrapper = (ProposalElementsWrapper) getDataObject();

        try {
            if (StringUtils.isNotBlank(proposalElementsWrapper.getProposalInfo().getId())) {
                List<DocumentHeaderDisplayInfo> documentInfoList = getSupportingDocumentService().getDocumentHeaderDisplay(
                        proposalElementsWrapper.getProposalInfo().getId(),
                        CurriculumManagementConstants.DEFAULT_DOC_TYPE_KEY,
                        ContextUtils.createDefaultContextInfo());
                proposalElementsWrapper.getSupportingDocs().clear();
                SupportingDocumentInfoWrapper supportingDocumentInfoWrapper = null;
                for (final DocumentHeaderDisplayInfo documentHeaderDisplayInfo : documentInfoList) {
                    supportingDocumentInfoWrapper = new SupportingDocumentInfoWrapper();
                    supportingDocumentInfoWrapper.setDocumentId(documentHeaderDisplayInfo.getDocumentId());
                    supportingDocumentInfoWrapper.setDocumentName(documentHeaderDisplayInfo.getFileName());
                    supportingDocumentInfoWrapper.setDescription(documentHeaderDisplayInfo.getDescription());
                    proposalElementsWrapper.getSupportingDocs().add(supportingDocumentInfoWrapper);
                }
                // Initialize Supporting Documents if it is found empty
                if (proposalElementsWrapper.getSupportingDocs().isEmpty()) {
                    proposalElementsWrapper.getSupportingDocs().add(new SupportingDocumentInfoWrapper());
                }
            }
        } catch (Exception ex) {
            LOG.error("Error occurred while loading the supporting documents" + ex);
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void saveDataObject() {
        ProposalElementsWrapper proposalElementsWrapper = (ProposalElementsWrapper) getDataObject();
        if (proposalElementsWrapper.getProposalInfo().getWorkflowId() != null) {
            ProposalInfo proposalInfo = proposalElementsWrapper.getProposalInfo();
            // first delete the collaborators that the user requested to be deleted
            try {
                for (String actionRequestId : proposalElementsWrapper.getDeletedCollaboratorWrapperActionRequestIds()) {
                    DocumentCollaboratorHelper.removeCollaborator(proposalInfo.getWorkflowId(), proposalInfo.getId(), actionRequestId);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            // if successful then clear the 'to be removed' collaborator list
            proposalElementsWrapper.getDeletedCollaboratorWrapperActionRequestIds().clear();

            // add in any new collaborators created
            if (StringUtils.isNotBlank(proposalInfo.getWorkflowId())) {
                for (CollaboratorWrapper collaboratorWrapper : proposalElementsWrapper.getCollaboratorWrappers()) {
                    // only save the collaborator if the actionRequestId is blank indicating new
                    if (StringUtils.isBlank(collaboratorWrapper.getActionRequestId())) {
                        String displayName = collaboratorWrapper.getDisplayName();
                        if (StringUtils.isBlank(displayName)) {
                            continue;
                        }
                        String principalName = displayName.substring(displayName.indexOf("(") + 1, displayName.length() - 1);
                        if (StringUtils.isBlank(principalName)) {
                            throw new RuntimeException("Cannot find principal name from display name: " + displayName);
                        }
                        collaboratorWrapper.setPrincipalName(principalName);
//                        collaboratorWrapper.setPrincipalId(principalId.toUpperCase());
                        Principal principal = KimApiServiceLocator.getIdentityService().getPrincipalByPrincipalName(principalName);
                        collaboratorWrapper.setPrincipalId(principal.getPrincipalId());
                        if (principal == null) {
                            throw new RuntimeException("Cannot find principal for principal name:" + principalName);
                        }
                        try {
                            DocumentCollaboratorHelper.addCollaborator(proposalInfo.getWorkflowId(), proposalInfo.getId(), principal.getPrincipalId(), collaboratorWrapper.getPermission(), collaboratorWrapper.getAction(), true, null, null);
                            if (collaboratorWrapper.isAuthor()) {
                                proposalInfo.getProposerPerson().add(collaboratorWrapper.getPrincipalId());
                            }
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        }
        try {
            saveProposal();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        /**
         * After saving a proposal, add all the supporting documents
         */
        persistSupportingDocuments(proposalElementsWrapper);

        super.saveDataObject();
        proposalElementsWrapper.setAgendaDirty(false);
    }

    protected void saveProposal() throws Exception {
        ProposalElementsWrapper proposalElementsWrapper = (ProposalElementsWrapper) getDataObject();

        ProposalInfo proposal = proposalElementsWrapper.getProposalInfo();
        proposal.setWorkflowId(getDocumentNumber());
        // check if the proposal has been previously saved
        if (StringUtils.isBlank(proposal.getId())) {
            // no previous save, so set up proposal as a new proposal
            proposal.setStateKey(ProposalConstants.PROPOSAL_STATE_SAVED);     // remove proposal constant, try to use KualiStudentPostProcessorBase
            String proposalTypeKey = getProposalTypeKey();
            proposal.setTypeKey(proposalTypeKey);
            proposal.setProposalReferenceType(getProposalReferenceType());
            proposal.getProposalReference().add(getProposalReference());
            proposal.getProposerOrg().clear();
            proposal.getProposerPerson().clear();
            proposal = getProposalService().createProposal(proposalTypeKey, proposal, ContextUtils.createDefaultContextInfo());
        } else {
            proposal = getProposalService().updateProposal(proposal.getId(), proposal, ContextUtils.createDefaultContextInfo());
        }

        proposalElementsWrapper.setProposalInfo(proposal);
    }

    protected abstract String getProposalTypeKey();

    protected abstract String getProposalReferenceType();

    protected abstract String getProposalReference();

    /**
     * Add the supporting documents and create the ref doc relations between document and proposal.
     *
     * @param proposalElementsWrapper
     */
    protected void persistSupportingDocuments(ProposalElementsWrapper proposalElementsWrapper) {

        for (SupportingDocumentInfoWrapper supportingDoc : proposalElementsWrapper.getSupportingDocs()) {

            populateSupportingDocBytes(supportingDoc);

            if (supportingDoc.isNewDto() && supportingDoc.getUploadedDoc() != null) {
                DocumentInfo toAdd = new DocumentInfo();
                toAdd.setFileName(supportingDoc.getDocumentUpload().getOriginalFilename());
                RichTextInfo desc = new RichTextInfo();
                desc.setPlain(supportingDoc.getDescription());
                desc.setFormatted(supportingDoc.getDescription());
                toAdd.setDescr(desc);

                toAdd.setStateKey(CurriculumManagementConstants.STATE_KEY_ACTIVE);
                // This will always be set to doc type as we accept various document types
                toAdd.setTypeKey(CurriculumManagementConstants.DEFAULT_DOC_TYPE_KEY);
                toAdd.setName(toAdd.getFileName());
                DocumentBinaryInfo documentBinaryInfo = new DocumentBinaryInfo();

                try {
                    documentBinaryInfo.setBinary(new String(Base64.encodeBase64(supportingDoc.getUploadedDoc())));
                    toAdd.setDocumentBinary(documentBinaryInfo);
                    // Save the uploaded document
                    DocumentInfo doc = getSupportingDocumentService().createDocument(
                            CurriculumManagementConstants.DEFAULT_DOC_TYPE_KEY,
                            CurriculumManagementConstants.DEFAULT_DOC_CATEGORY_KEY,
                            toAdd, ContextUtils.createDefaultContextInfo());

                    // Now relate the document to the proposal
                    RefDocRelationInfo docRelation = new RefDocRelationInfo();
                    docRelation.setRefObjectTypeKey(CurriculumManagementConstants.REF_DOC_RELATION_PROPOSAL_TYPE);
                    docRelation.setRefObjectId(proposalElementsWrapper.getProposalInfo().getId());
                    docRelation.setStateKey(CurriculumManagementConstants.STATE_KEY_ACTIVE);
                    docRelation.setTypeKey(CurriculumManagementConstants.REF_DOC_RELATION_TYPE_KEY);
                    docRelation.setDocumentId(doc.getId());

                    getSupportingDocumentService().createRefDocRelation(CurriculumManagementConstants.REF_DOC_RELATION_PROPOSAL_TYPE,
                            proposalElementsWrapper.getProposalInfo().getId(),
                            toAdd.getId(),
                            CurriculumManagementConstants.REF_DOC_RELATION_TYPE_KEY,
                            docRelation,
                            ContextUtils.createDefaultContextInfo());
                    supportingDoc.setDocumentId(doc.getId());
                    supportingDoc.setDocumentName(toAdd.getFileName());
                    //Free up memory
                    supportingDoc.setDocumentUpload(null);
                    supportingDoc.setUploadedDoc(null);

                } catch (Exception ex) {
                    LOG.error("Unable to add supporting document to the proposal for file: " +
                            supportingDoc.getDocumentUpload().getName(), ex);
                    throw new RuntimeException("Error persisting supporting document.", ex);
                }
            }
        }

        /**
         * Now, remove all the docs marked for delete by user
         */
        for (SupportingDocumentInfoWrapper docToDelete : proposalElementsWrapper.getSupportingDocsToDelete()) {
            try {
                // Deletes the document and its Ref doc relations
                List<RefDocRelationInfo> refDocRelationInfoList = getSupportingDocumentService().getRefDocRelationsByDocument(docToDelete.getDocumentId(), ContextUtils.createDefaultContextInfo());
                for (RefDocRelationInfo refDocRelationInfo : refDocRelationInfoList) {
                    getSupportingDocumentService().deleteRefDocRelation(refDocRelationInfo.getId(), ContextUtils.createDefaultContextInfo());
                }
                getSupportingDocumentService().deleteDocument(docToDelete.getDocumentId(), ContextUtils.createDefaultContextInfo());
                proposalElementsWrapper.getSupportingDocs().remove(docToDelete);
            } catch (Exception ex) {
                LOG.warn("Unable to delete document: " + docToDelete.getDocumentName(), ex);
                throw new RuntimeException(ex);
            }
        }

        proposalElementsWrapper.getSupportingDocsToDelete().clear();
    }

    /**
     * Populates the wrapper objects used on documents that utilize proposals.
     *
     * @param proposalElementsWrapper The wrapper to populate.
     */
    public void populateWrapperData(ProposalElementsWrapper proposalElementsWrapper) throws Exception {
        populateCollaborators();
        if (proposalElementsWrapper.getSupportingDocs().isEmpty()) {
            populateSupportingDocuments();
        }

    }


    @Override
    public void addCustomContainerComponents(ViewModel model, Container container) {
        ((CourseRuleViewHelperServiceImpl) getRuleViewHelperService()).addCustomContainerComponents(model, container);
    }

    @Override
    public Boolean validateProposition(PropositionEditor proposition) {
        return getRuleViewHelperService().validateProposition(proposition);
    }

    @Override
    public void resetDescription(PropositionEditor proposition) {
        getRuleViewHelperService().resetDescription(proposition);
    }

    @Override
    public void configurePropositionForType(PropositionEditor proposition) {
        getRuleViewHelperService().configurePropositionForType(proposition);
    }

    @Override
    public TemplateInfo getTemplateForType(String type) {
        return getRuleViewHelperService().getTemplateForType(type);
    }

    @Override
    public void refreshInitTrees(RuleEditor rule) {
        getRuleViewHelperService().refreshInitTrees(rule);
    }

    @Override
    public void refreshViewTree(RuleEditor rule) {
        getRuleViewHelperService().refreshViewTree(rule);
    }

    @Override
    public Tree<CompareTreeNode, String> buildCompareTree(RuleEditor original, RuleEditor compare) {
        return getRuleViewHelperService().buildCompareTree(original, compare);
    }

    @Override
    public Tree<CompareTreeNode, String> buildMultiViewTree(RuleEditor coRuleEditor, RuleEditor cluRuleEditor) {
        return getRuleViewHelperService().buildMultiViewTree(coRuleEditor, cluRuleEditor);
    }

    @Override
    public Boolean compareRules(RuleEditor original) {
        return getRuleViewHelperService().compareRules(original);
    }

    @Override
    public void finPropositionEditor(PropositionEditor propositionEditor) {
        getRuleViewHelperService().finPropositionEditor(propositionEditor);
    }

    @Override
    public PropositionEditor copyProposition(PropositionEditor proposition) {
        return getRuleViewHelperService().copyProposition(proposition);
    }

    @Override
    public PropositionEditor createCompoundPropositionBoStub(PropositionEditor existing, boolean addNewChild) {
        return getRuleViewHelperService().createCompoundPropositionBoStub(existing, addNewChild);
    }

    @Override
    public void setTypeForCompoundOpCode(PropositionEditor proposition, String compoundOpCode) {
        getRuleViewHelperService().setTypeForCompoundOpCode(proposition, compoundOpCode);
    }

    @Override
    public PropositionEditor createSimplePropositionBoStub(PropositionEditor sibling) {
        return getRuleViewHelperService().createSimplePropositionBoStub(sibling);
    }

    @Override
    public Boolean compareProposition(PropositionEditor original, PropositionEditor compare) {
        return getRuleViewHelperService().compareProposition(original, compare);
    }

    @Override
    public Boolean compareCompoundProposition(List<PropositionEditor> original, List<PropositionEditor> compare) {
        return getRuleViewHelperService().compareCompoundProposition(original, compare);
    }

    @Override
    public Boolean compareTerm(List<TermParameterEditor> original, List<TermParameterEditor> compare) {
        return getRuleViewHelperService().compareTerm(original, compare);
    }

    @Override
    public void buildActions(final RuleEditor arg0) {
        getRuleViewHelperService().buildActions(arg0);
    }

    @Override
    public Boolean validateRule(final RuleEditor arg0) {
        return getRuleViewHelperService().validateRule(arg0);
    }

    public abstract String getViewTypeNameForProposal();

    @Override
    public String getViewTypeName() {
        return getViewTypeNameForProposal();
    }

    /**
     * This method was overriden from the RuleEditorMaintainableImpl to create an EnrolAgendaEditor instead of
     * an AgendaEditor.
     *
     * @param agendaId
     * @return EnrolAgendaEditor.
     */
    @Override
    protected AgendaEditor getAgendaEditor(String agendaId) {
        AgendaDefinition agenda = this.getRuleManagementService().getAgenda(agendaId);
        return new LUAgendaEditor(agenda);
    }

    /**
     * Retrieves all the rules from the agenda tree and create a list of ruleeditor objects.
     * <p/>
     * Also initialize the proposition editors for each rule recursively and set natural language for the view trees.
     *
     * @param agendaItem
     * @return
     */
    @Override
    protected List<RuleEditor> getRuleEditorsFromTree(AgendaItemDefinition agendaItem, boolean initProps) {

        List<RuleEditor> rules = new ArrayList<RuleEditor>();
        if (agendaItem.getRule() != null) {

            //Build the ruleEditor
            RuleEditor ruleEditor = new LURuleEditor(agendaItem.getRule());

            //Initialize the Proposition tree
            if (initProps) {
                this.initPropositionEditor(ruleEditor.getPropositionEditor());
                ruleEditor.setViewTree(this.getViewTreeBuilder().buildTree(ruleEditor));
            }

            //Add rule to list on agenda
            rules.add(ruleEditor);
        }

        if (agendaItem.getWhenTrue() != null) {
            rules.addAll(getRuleEditorsFromTree(agendaItem.getWhenTrue(), initProps));
        }

        return rules;
    }

    public abstract List<ReferenceObjectBinding> getParentRefObjectsForProposal(String refObjectId);

    @Override
    public List<ReferenceObjectBinding> getParentRefOjbects(String refObjectId) {
        return getParentRefObjectsForProposal(refObjectId);
    }

    protected WorkflowDocumentService getWorkflowDocumentService() {
        return KewApiServiceLocator.getWorkflowDocumentService();
    }

    protected ProposalService getProposalService() {
        if (proposalService == null) {
            proposalService = (ProposalService) GlobalResourceLoader.getService(new QName(ProposalServiceConstants.NAMESPACE, ProposalServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return proposalService;
    }

    protected DocumentService getSupportingDocumentService() {
        if (documentService == null) {
            documentService = (DocumentService) GlobalResourceLoader.getService(new QName(DocumentServiceConstants.NAMESPACE, "DocumentService"));
        }
        return documentService;
    }

    protected SearchService getSearchService() {
        if (searchService == null) {
            searchService = GlobalResourceLoader.getService(new QName(PersonSearchServiceImpl.NAMESPACE_PERSONSEACH, PersonSearchServiceImpl.PERSONSEACH_SERVICE_NAME_LOCAL_PART));
        }
        return searchService;
    }

    protected RuleViewHelperService getRuleViewHelperService() {
        return ruleViewHelperService;
    }

    protected RuleViewTreeBuilder getViewTreeBuilder() {
        if (this.viewTreeBuilder == null) {
            viewTreeBuilder = new LURuleViewTreeBuilder();
            viewTreeBuilder.setNlHelper(this.getNLHelper());
        }
        return viewTreeBuilder;
    }

    protected NaturalLanguageHelper getNLHelper() {
        if (this.nlHelper == null) {
            nlHelper = new NaturalLanguageHelper();
            nlHelper.setRuleManagementService(this.getRuleManagementService());
        }
        return nlHelper;
    }

}
