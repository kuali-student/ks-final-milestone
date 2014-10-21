package org.kuali.student.core.rice.authorization;

import org.kuali.rice.kew.api.KewApiConstants;
import org.kuali.rice.kew.api.KewApiServiceLocator;
import org.kuali.rice.kew.api.WorkflowDocument;
import org.kuali.rice.kew.api.action.ActionRequest;
import org.kuali.rice.kew.api.action.ActionRequestStatus;
import org.kuali.rice.kew.api.action.ActionRequestType;
import org.kuali.rice.kew.api.action.ActionType;
import org.kuali.rice.kew.api.action.AdHocToPrincipal;
import org.kuali.rice.kew.api.action.DocumentActionParameters;
import org.kuali.rice.kew.api.action.DocumentActionResult;
import org.kuali.rice.kew.api.action.WorkflowDocumentActionsService;
import org.kuali.rice.kew.api.doctype.DocumentType;
import org.kuali.rice.kew.api.doctype.DocumentTypeService;
import org.kuali.rice.kew.api.document.DocumentDetail;
import org.kuali.rice.kew.api.document.WorkflowDocumentService;
import org.kuali.rice.kew.api.exception.WorkflowException;
import org.kuali.rice.kim.api.KimConstants;
import org.kuali.rice.kim.api.identity.IdentityService;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.permission.PermissionService;
import org.kuali.rice.kim.api.role.RoleService;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.document.DocumentAuthorizerBase;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.student.common.util.security.SecurityUtils;
import org.kuali.student.r1.common.rice.StudentIdentityConstants;
import org.kuali.student.r1.common.rice.StudentProposalRiceConstants;
import org.kuali.student.r1.common.rice.authorization.ProposalPermissionTypes;
import org.kuali.student.r1.core.workflow.dto.CollaboratorWrapper;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DocumentCollaboratorHelper implements Serializable {
    protected static IdentityService identityService;
    protected static RoleService roleService;
    private static WorkflowDocumentActionsService workflowDocumentActionsService;
    private static WorkflowDocumentService workflowDocumentService;
    private static DocumentTypeService documentTypeService;
    private static PermissionService permissionService;

    private static final long serialVersionUID = 1L;
    private final static Logger LOG = LoggerFactory.getLogger(CollaboratorHelperGwt.class);

    public static void addCollaborator(String docId, String dataId, String recipientPrincipalId, String selectedPermissionCode, String actionRequestTypeCode, boolean participationRequired, String actionRequestLabel, String actionRequestResponsibilityDescription) throws OperationFailedException {
        if (getWorkflowDocumentActionsService() == null) {
            throw new OperationFailedException("Workflow Service is unavailable");
        }

        //get a user name
        String currentUserPrincipalId = SecurityUtils.getCurrentUserId();

        StudentProposalRiceConstants.ActionRequestType actionRequestEnum = StudentProposalRiceConstants.ActionRequestType.getByCode(actionRequestTypeCode);
        if (actionRequestEnum == null) {
            throw new OperationFailedException("No valid action request type found for code: " + actionRequestTypeCode);
        }

        DocumentActionParameters docActionParams = DocumentActionParameters.Builder.create(docId, currentUserPrincipalId).build();
        ActionRequestType actionRequestType = ActionRequestType.fromCode(actionRequestTypeCode);
        AdHocToPrincipal.Builder ahtpBuilder = AdHocToPrincipal.Builder.create(actionRequestType, null, recipientPrincipalId);
        ahtpBuilder.setForceAction(participationRequired);
        ahtpBuilder.setRequestLabel(actionRequestLabel);
        ahtpBuilder.setResponsibilityDescription(actionRequestResponsibilityDescription);

        DocumentActionResult stdResp = null;
        if (StudentProposalRiceConstants.ActionRequestType.APPROVE.equals(actionRequestEnum)) {
            ahtpBuilder.setResponsibilityDescription(KewApiConstants.ACTION_REQUEST_APPROVE_REQ_LABEL);
            stdResp = getWorkflowDocumentActionsService().adHocToPrincipal(docActionParams, ahtpBuilder.build());
        } else if (StudentProposalRiceConstants.ActionRequestType.ACKNOWLEDGE.equals(actionRequestEnum)) {
            ahtpBuilder.setResponsibilityDescription(KewApiConstants.ACTION_REQUEST_ACKNOWLEDGE_REQ_LABEL);
            stdResp = getWorkflowDocumentActionsService().adHocToPrincipal(docActionParams, ahtpBuilder.build());
        } else if (StudentProposalRiceConstants.ActionRequestType.FYI.equals(actionRequestEnum)) {
            ahtpBuilder.setResponsibilityDescription(KewApiConstants.ACTION_REQUEST_FYI_REQ_LABEL);
            stdResp = getWorkflowDocumentActionsService().adHocToPrincipal(docActionParams, ahtpBuilder.build());
        } else {
            throw new OperationFailedException("Invalid action request type '"
                    + actionRequestEnum.getActionRequestLabel() + "'");
        }

        ProposalPermissionTypes selectedPermType = ProposalPermissionTypes.getByCode(selectedPermissionCode);
        if (selectedPermType == null) {
            throw new OperationFailedException("No valid permission type found for code: " + selectedPermissionCode);
        }
        if (ProposalPermissionTypes.EDIT.equals(selectedPermType)) {
            addRoleMember(StudentProposalRiceConstants.ROLE_NAME_ADHOC_EDIT_PERMISSIONS_ROLE_NAMESPACE, StudentProposalRiceConstants.ROLE_NAME_ADHOC_EDIT_PERMISSIONS_ROLE_NAME, docId, dataId, recipientPrincipalId);
            addRoleMember(StudentProposalRiceConstants.ROLE_NAME_ADHOC_ADD_COMMENT_PERMISSIONS_ROLE_NAMESPACE, StudentProposalRiceConstants.ROLE_NAME_ADHOC_ADD_COMMENT_PERMISSIONS_ROLE_NAME, docId, dataId, recipientPrincipalId);
        }
        else if (ProposalPermissionTypes.ADD_COMMENT.equals(selectedPermType)) {
            addRoleMember(StudentProposalRiceConstants.ROLE_NAME_ADHOC_ADD_COMMENT_PERMISSIONS_ROLE_NAMESPACE, StudentProposalRiceConstants.ROLE_NAME_ADHOC_ADD_COMMENT_PERMISSIONS_ROLE_NAME, docId, dataId, recipientPrincipalId);
        }
    }

    public static void removeCollaborator(String docId, String dataId, String actionRequestId) throws OperationFailedException {
        //get the current user
        String currentUserPrincipalId = SecurityUtils.getCurrentUserId();

        String recipientPrincipalId = null;
        List<ActionRequest> actionRequests = getWorkflowDocumentService().getRootActionRequests(docId);
        for (ActionRequest actionRequest : actionRequests) {
            if (actionRequestId.equals(actionRequest.getId().toString())) {
                recipientPrincipalId = actionRequest.getPrincipalId();
                break;
            }
        }
        if (recipientPrincipalId == null) {
            throw new OperationFailedException("Unable to find Principal ID for action request id: " + actionRequestId);
        }
        DocumentActionParameters docActionParams = DocumentActionParameters.Builder.create(docId, currentUserPrincipalId).build();
        DocumentActionResult stdResp = getWorkflowDocumentActionsService().revokeAdHocRequestById(
            docActionParams, actionRequestId);

        String docTypeName = getWorkflowDocumentService().getDocumentTypeName(docId);
        // remove principal from edit permission
        removeEditAdhocPermission(recipientPrincipalId, docTypeName, dataId);
        // remove principal from comment permission
        removeCommentAdhocPermission(recipientPrincipalId, docTypeName, dataId);
    }

    public static List<CollaboratorWrapper> getCollaborators(String docId, String dataId, String referenceTypeKey) throws OperationFailedException{
        //Check if there is no doc id
        if(docId==null){
            return Collections.<CollaboratorWrapper>emptyList();
        }
        LOG.info("Getting collaborators for docId: {}", docId);
        List<CollaboratorWrapper> collaborators = new ArrayList<CollaboratorWrapper>();
        List<ActionRequest> actionRequests = getWorkflowDocumentService().getRootActionRequests(docId);

        if(actionRequests!=null){
            for(ActionRequest actionRequest :actionRequests){
                if (actionRequest.isAdHocRequest()) {
                    // if action request is complete and action taken was a 'revoke action' we do not want to show the person
                    if (actionRequest.isDone() && (actionRequest.getActionTaken() != null) && ActionType.ADHOC_REQUEST_REVOKE.equals(actionRequest.getActionTaken().getActionTaken())) {
                        continue;
                    }

                    CollaboratorWrapper collaborator = new CollaboratorWrapper();

                    // fetch the person info using the actionRequest.principalId value to use in the authorizer calls
                    Person actionRequestPerson = KimApiServiceLocator.getPersonService().getPerson(actionRequest.getPrincipalId());
                    if (actionRequestPerson == null) {
                        throw new RuntimeException("Cannot find user for principal id: " + actionRequest.getPrincipalId());
                    }
                    collaborator.setPrincipalId(actionRequestPerson.getPrincipalId());
                    collaborator.setFirstName(actionRequestPerson.getFirstName());
                    collaborator.setLastName(actionRequestPerson.getLastName());

                    ActionRequestType requestType = actionRequest.getActionRequested();
                    collaborator.setAction(requestType.getCode());

                    // this below is VERY important because it is used as the defacto 'primary key' for the collaboratorWrapper objects
                    collaborator.setActionRequestId(actionRequest.getId());
                    collaborator.setActionRequestStatus(actionRequest.getStatus().getLabel());

                    // we can only revoke an actionRequest that has not been satisfied by an actionTaken
                    collaborator.setCanRevokeRequest(!actionRequest.isDone());

                    try {
                        Map<String,String> permissionDetails = new HashMap<String,String>();
                        buildPermissionDetails(permissionDetails, docId, referenceTypeKey, dataId);
                        Map<String,String> roleQualification = new HashMap<String,String>();
                        buildRoleQualification(roleQualification, docId, referenceTypeKey, dataId);

                        if(getPermissionService().isAuthorizedByTemplate(actionRequest.getPrincipalId(), ProposalPermissionTypes.EDIT.getPermissionNamespace(),
                                ProposalPermissionTypes.EDIT.getPermissionTemplateName(), permissionDetails, roleQualification)){
                            collaborator.setPermission(ProposalPermissionTypes.EDIT.getCode());
                        } else if (getPermissionService().isAuthorizedByTemplate(actionRequest.getPrincipalId(), ProposalPermissionTypes.ADD_COMMENT.getPermissionNamespace(),
                                ProposalPermissionTypes.ADD_COMMENT.getPermissionTemplateName(), permissionDetails, roleQualification)){
                            collaborator.setPermission(ProposalPermissionTypes.ADD_COMMENT.getCode());
                        } else if (getPermissionService().isAuthorizedByTemplate(actionRequest.getPrincipalId(), ProposalPermissionTypes.OPEN.getPermissionNamespace(),
                                ProposalPermissionTypes.OPEN.getPermissionTemplateName(), permissionDetails, roleQualification)){
                            collaborator.setPermission(ProposalPermissionTypes.OPEN.getCode());
                        }
                    } catch (WorkflowException e) {
                        throw new RuntimeException("Error checking collaborator permissions", e);
                    }

                    collaborators.add(collaborator);
                }
            }
        }

        Collections.sort(collaborators,
                new Comparator<CollaboratorWrapper>() {
                    public int compare(CollaboratorWrapper a, CollaboratorWrapper b) {
                        return a.getActionRequestId().compareToIgnoreCase(b.getActionRequestId());
                    }
                }
        );

        LOG.info("Returning collaborators: {}", collaborators);
        return collaborators;
    }

    protected static void buildRoleQualification(Map<String,String> qualification, String docId, String referenceTypeKey, String dataId) throws WorkflowException {
        qualification.put(StudentIdentityConstants.QUALIFICATION_DATA_ID, dataId);

        // add the Workflow data elements
        addWorkflowData(qualification, docId);
    }

    protected static void buildPermissionDetails(Map<String,String> details, String docId, String referenceTypeKey, String dataId) throws WorkflowException {
        details.put(StudentIdentityConstants.KS_REFERENCE_TYPE_KEY, referenceTypeKey);

        // add the Workflow data elements
        addWorkflowData(details, docId);
    }

    protected static void addWorkflowData(Map<String,String> data, String docId) throws WorkflowException {
        WorkflowDocument wd = KRADServiceLocatorWeb.getWorkflowDocumentService().loadWorkflowDocument(docId, GlobalVariables.getUserSession().getPerson());
        data.put(KimConstants.AttributeConstants.DOCUMENT_NUMBER, wd.getDocumentId());
        data.put(KimConstants.AttributeConstants.DOCUMENT_TYPE_NAME, wd.getDocumentTypeName());

        if (wd.isInitiated() || wd.isSaved()) {
            data.put(KimConstants.AttributeConstants.ROUTE_NODE_NAME, DocumentAuthorizerBase.PRE_ROUTING_ROUTE_NAME);
        } else {
            data.put(KimConstants.AttributeConstants.ROUTE_NODE_NAME,
                    KRADServiceLocatorWeb.getWorkflowDocumentService().getCurrentRouteNodeNames(wd));
        }

        data.put(KimConstants.AttributeConstants.ROUTE_STATUS_CODE, wd.getStatus().getCode());
    }

    private String getActionRequestStatusLabel(String key) {
        Map<String,String> newArStatusLabels = new HashMap<String,String>();
        newArStatusLabels.put(ActionRequestStatus.ACTIVATED.getCode(), "Active");
        newArStatusLabels.put(ActionRequestStatus.INITIALIZED.getCode(), "Pending");
        newArStatusLabels.put(ActionRequestStatus.DONE.getCode(), "Completed");
        return newArStatusLabels.get(key);
    }

    private static void addRoleMember(String roleNamespace, String roleName, String docId, String dataId, String recipientPrincipalId) throws OperationFailedException {
        DocumentDetail docDetail = getWorkflowDocumentService().getDocumentDetail(docId);
        DocumentType docType = getDocumentTypeService().getDocumentTypeById(docDetail.getDocument().getDocumentTypeId());
        Map<String,String> roleMemberQuals = new LinkedHashMap<String,String>();
        roleMemberQuals.put(StudentIdentityConstants.DOCUMENT_TYPE_NAME,docType.getName());
        roleMemberQuals.put(StudentIdentityConstants.QUALIFICATION_DATA_ID,dataId);
        getRoleService().assignPrincipalToRole(recipientPrincipalId, roleNamespace, roleName, roleMemberQuals);
    }

    public static void removeEditAdhocPermission(String recipientPrincipalId, String docTypeName, String dataId) throws OperationFailedException {
        removeRoleMember(recipientPrincipalId, StudentProposalRiceConstants.ROLE_NAME_ADHOC_EDIT_PERMISSIONS_ROLE_NAMESPACE, StudentProposalRiceConstants.ROLE_NAME_ADHOC_EDIT_PERMISSIONS_ROLE_NAME, docTypeName, dataId);
    }

    public static void removeCommentAdhocPermission(String recipientPrincipalId, String docTypeName, String dataId) throws OperationFailedException {
        removeRoleMember(recipientPrincipalId, StudentProposalRiceConstants.ROLE_NAME_ADHOC_ADD_COMMENT_PERMISSIONS_ROLE_NAMESPACE, StudentProposalRiceConstants.ROLE_NAME_ADHOC_ADD_COMMENT_PERMISSIONS_ROLE_NAME, docTypeName, dataId);
    }

    private static void removeRoleMember(String recipientPrincipalId, String roleNamespace, String roleName, String docTypeName, String dataId) throws OperationFailedException {
        Map<String,String> roleMemberQuals = new LinkedHashMap<>();
        roleMemberQuals.put(StudentIdentityConstants.DOCUMENT_TYPE_NAME, docTypeName);
        roleMemberQuals.put(StudentIdentityConstants.QUALIFICATION_DATA_ID, dataId);
        getRoleService().removePrincipalFromRole(recipientPrincipalId, roleNamespace, roleName, roleMemberQuals);
    }

    public static IdentityService getIdentityService() throws OperationFailedException {
        if (identityService == null) {
            identityService = KimApiServiceLocator.getIdentityService();
            if(identityService == null) {
                throw new OperationFailedException("unable to find valid identityService");
            }
        }
        return identityService;
    }

    public void setIdentityService(IdentityService identityService) {
        this.identityService = identityService;
    }

    public static RoleService getRoleService() throws OperationFailedException {
        if (roleService == null) {
            roleService = KimApiServiceLocator.getRoleService();
            if(roleService == null) {
                throw new OperationFailedException("unable to find valid roleService");
            }
        }
        return roleService;
    }

    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    public static WorkflowDocumentActionsService getWorkflowDocumentActionsService() throws OperationFailedException {

        if (workflowDocumentActionsService == null) {

            workflowDocumentActionsService = KewApiServiceLocator.getWorkflowDocumentActionsService();

            if (workflowDocumentActionsService == null) {
                throw new OperationFailedException(
                        "unable to find valid workflowDocumentActionsService");
            }
        }
        return workflowDocumentActionsService;
    }

    public void setWorkflowDocumentActionsService(WorkflowDocumentActionsService workflowDocumentActionsService) {
        this.workflowDocumentActionsService = workflowDocumentActionsService;
    }


    public static WorkflowDocumentService getWorkflowDocumentService() throws OperationFailedException {

        if (workflowDocumentService == null) {

            workflowDocumentService = KewApiServiceLocator.getWorkflowDocumentService();

            if (workflowDocumentService == null) {
                throw new OperationFailedException("unable to find valid workflowDocumentService");
            }

        }
        return workflowDocumentService;
    }

    public void setWorkflowDocumentService(WorkflowDocumentService workflowDocumentService) {
        this.workflowDocumentService = workflowDocumentService;
    }


    public static DocumentTypeService getDocumentTypeService() throws OperationFailedException {
        if (documentTypeService == null) {
            documentTypeService = KewApiServiceLocator.getDocumentTypeService();
            if(documentTypeService == null)  {
                throw new OperationFailedException("unable to find valid documentTypeService");
            }
        }
        return documentTypeService;
    }

    public void setDocumentTypeService(DocumentTypeService documentTypeService) {
        this.documentTypeService = documentTypeService;
    }

    public static PermissionService getPermissionService() throws OperationFailedException {
        if (permissionService == null) {
            permissionService = KimApiServiceLocator.getPermissionService();
            if(permissionService == null) {
                throw new OperationFailedException("unable to find valid permissionService");
            }
        }
        return permissionService;
    }

    public void setPermissionService(PermissionService permissionService) {
        this.permissionService = permissionService;
    }
}
