/**
 * Copyright 2010 The Kuali Foundation Licensed under the
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
 */

package org.kuali.student.lum.kim.role.type;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.kew.api.KewApiServiceLocator;
import org.kuali.rice.kew.api.action.ActionRequest;
import org.kuali.rice.kew.api.action.ActionRequestStatus;
import org.kuali.rice.kew.api.document.WorkflowDocumentService;
import org.kuali.rice.kew.api.exception.WorkflowException;
import org.kuali.rice.kim.api.KimConstants;
import org.kuali.rice.kim.api.role.RoleMembership;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.kns.kim.role.DerivedRoleTypeServiceBase;

import org.kuali.student.r1.common.rice.StudentIdentityConstants;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.lum.kim.KimQualificationHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 *
 */
public class KSActionRequestDerivedRoleTypeServiceImpl extends DerivedRoleTypeServiceBase {
    private static final Logger LOG = LoggerFactory.getLogger(KSActionRequestDerivedRoleTypeServiceImpl.class);

	private static final String APPROVE_REQUEST_RECIPIENT_ROLE_CONTENT = "Approve";
	private static final String ACKNOWLEDGE_REQUEST_RECIPIENT_ROLE_CONTENT = "Acknowledge";
	private static final String FYI_REQUEST_RECIPIENT_ROLE_CONTENT = "FYI";
        private static final String ACTION_REQUEST_INITIALIZED = ActionRequestStatus.INITIALIZED.getCode();
        private static final String ACTION_REQUEST_ACTIVATED = ActionRequestStatus.ACTIVATED.getCode();
        private static final String ACTION_REQUEST_DONE_STATE = ActionRequestStatus.DONE.getCode();

    protected Set<List<String>> newRequiredAttributes = new HashSet<List<String>>();

	protected enum REQUESTS_TYPES_TO_CHECK {
		BOTH, ADHOC_ONLY, NON_ADHOC_ONLY;
	}

	protected enum REQUESTS_STATUS_TO_CHECK {
		INITIALIZED(ACTION_REQUEST_INITIALIZED),
                ACTIVE(ACTION_REQUEST_ACTIVATED),
		DONE(ACTION_REQUEST_DONE_STATE);

		private String kewActionRequestStatusCode;

		private REQUESTS_STATUS_TO_CHECK(String kewActionRequestStatusCode) {
	        this.kewActionRequestStatusCode = kewActionRequestStatusCode;
        }

		public static REQUESTS_STATUS_TO_CHECK getByCode(String code) {
			for (REQUESTS_STATUS_TO_CHECK type : REQUESTS_STATUS_TO_CHECK.values()) {
				if (type.kewActionRequestStatusCode.equals(code)) {
					return type;
				}
			}
			return null;
		}
	}

	{
        // add document number as one required attribute set
		List<String> listOne = new ArrayList<String>();
		listOne.add( KimConstants.AttributeConstants.DOCUMENT_NUMBER );
		newRequiredAttributes.add(listOne);
        // add document type name and KEW application id as one required attribute set
		List<String> listTwo = new ArrayList<String>();
		listTwo.add( KimConstants.AttributeConstants.DOCUMENT_TYPE_NAME );
		listTwo.add( StudentIdentityConstants.QUALIFICATION_KEW_OBJECT_ID );
		newRequiredAttributes.add(listTwo);
        // add object id and object type as one required attribute set
		List<String> listThree = new ArrayList<String>();
		listThree.add( StudentIdentityConstants.QUALIFICATION_KEW_OBJECT_ID );
		listThree.add( StudentIdentityConstants.QUALIFICATION_KEW_OBJECT_TYPE );
		newRequiredAttributes.add(listThree);
        // add each proposal reference type as a required attribute set
        for (String proposalReferenceType : StudentIdentityConstants.QUALIFICATION_PROPOSAL_ID_REF_TYPES) {
            List<String> tempList = new ArrayList<String>();
            tempList.add( proposalReferenceType );
            newRequiredAttributes.add(tempList);
        }
	}

	/**
	 * The part about where the receivedAttributes list being empty does not return errors is copied from Rice base class.
	 *
	 * @see org.kuali.rice.kim.service.support.impl.KimTypeServiceBase#validateRequiredAttributesAgainstReceived(org.kuali.rice.kim.bo.types.dtoMap<String,String>)
	 **/
	@Override
	protected void validateRequiredAttributesAgainstReceived(Map<String,String> receivedAttributes){
		KimQualificationHelper.validateRequiredAttributesAgainstReceived(newRequiredAttributes, receivedAttributes, isCheckRequiredAttributes(), COMMA_SEPARATOR);
		super.validateRequiredAttributesAgainstReceived(receivedAttributes);
	}

    @Override
    public Map<String,String> translateInputAttributes(Map<String,String> qualification) {
        return KimQualificationHelper.translateInputAttributeSet(qualification, ContextUtils.getContextInfo());
    }

	protected String getDocumentNumber(Map<String,String> qualification) throws WorkflowException {
		// first check for a valid document id passed in
		String documentId = qualification.get( KimConstants.AttributeConstants.DOCUMENT_NUMBER );
        if (StringUtils.isNotEmpty(documentId)) {
            return documentId;
        } else {
            LOG.warn("Could not find workflow document id in qualification list: {}", qualification);
            return null;
        }
//		if (StringUtils.isNotEmpty(documentId)) {
//			return Long.valueOf(documentId);
//		}
//		// if no document id passed in get the document via the id and document type name
//		String documentTypeName = qualification.get( KimConstants.AttributeConstants.DOCUMENT_TYPE_NAME );
//		if (StringUtils.isEmpty(documentTypeName)) {
//			String ksObjectType = qualification.get( StudentIdentityConstants.QUALIFICATION_KEW_OBJECT_TYPE );
//			if (StringUtils.equals(ksObjectType, "referenceType.clu.proposal")) {
//	            documentTypeName = "kuali.proposal.type.course.create";
//			}
//		}
//		String appId = qualification.get( StudentIdentityConstants.QUALIFICATION_KEW_OBJECT_ID );
//		LOG.info("Checking for document id using document type '" + documentTypeName + "' and application id '" + appId + "' with qualifications: " + qualification.toString());
//		DocumentDetailDTO docDetail = getWorkflowUtility().getDocumentDetailFromAppId(documentTypeName, appId);
//		if (docDetail == null) {
//			LOG.info("No valid document instance found for document type name '" + documentTypeName + "' and Application Id '" + appId + "'");
//			return null;
////			throw new RuntimeException("No valid document instance found for document type name '" + documentTypeName + "' and Application Id '" + appId + "'");
//		}
//		return docDetail.getDocumentId();
	}

	protected void addMember(Map<String,List<ActionRequest>> requestsByPrincipalId, String principalId, ActionRequest actionRequest) {
		if (!requestsByPrincipalId.containsKey(principalId)) {
			requestsByPrincipalId.put(principalId, new ArrayList<ActionRequest>());
		}
		requestsByPrincipalId.get(principalId).add(actionRequest);
	}

	/* (non-Javadoc)
	 * @see org.kuali.rice.kew.role.service.impl.ActionRequestDerivedRoleTypeServiceImpl#getRoleMembersFromDerivedRole(java.lang.String, java.lang.String, org.kuali.rice.kim.bo.types.dto.Map<String,String>)
	 */
	@Override
	public List<RoleMembership> getRoleMembersFromDerivedRole(
			String namespaceCode, String roleName, Map<String,String> paramQualification) {
		// validate required attributes
		validateRequiredAttributesAgainstReceived(paramQualification);
		Map<String,String> qualification = translateInputAttributes(paramQualification);
		List<RoleMembership> members = new ArrayList<RoleMembership>();
		try {
			// check for valid qualification data to check
			String documentNumber = getDocumentNumber(qualification);
			if (documentNumber != null) {
				// get all action requests for the document id given
                            // TODO: RICE=M7 UPGRADE deal with WorkflowUtility being deprecated
				List<ActionRequest> actionRequests = getWorkflowDocumentService().getRootActionRequests(documentNumber);
				Map<String,List<ActionRequest>> requestsByPrincipalId = new HashMap<String, List<ActionRequest>>();
				// build a map by principal id of action requests for the document
	            for (ActionRequest actionRequest: actionRequests) {
	            	// if the request has a principal id
	            	if (actionRequest.getPrincipalId() != null) {
	            		addMember(requestsByPrincipalId, actionRequest.getPrincipalId(), actionRequest);
	            	}
	            	// if the request is a group request
	            	else if (actionRequest.isGroupRequest()) {
	            		for (String principalId : KimApiServiceLocator.getGroupService().getMemberPrincipalIds(actionRequest.getGroupId())) {
		            		addMember(requestsByPrincipalId, principalId, actionRequest);
						}
	            	}
	            }
	            for (Map.Entry<String, List<ActionRequest>> mapEntry : requestsByPrincipalId.entrySet()) {
					if (containsActivatedRequest(roleName, mapEntry.getValue())) {
		                members.add(RoleMembership.Builder.create(null/*roleId*/, null, mapEntry.getKey(), KimConstants.KimGroupMemberTypes.PRINCIPAL_MEMBER_TYPE, null).build() );
					}
				}
			}
			return members;
		} catch (WorkflowException e) {
			LOG.error("Workflow Error: ", e);
			throw new RuntimeException("Unable to load route header", e);
		}
	}

	/* (non-Javadoc)
	 * @see org.kuali.rice.kew.role.service.impl.ActionRequestDerivedRoleTypeServiceImpl#hasDerivedRole(java.lang.String, java.util.List, java.lang.String, java.lang.String, org.kuali.rice.kim.bo.types.dto.Map<String,String>)
	 */
	@Override
	public boolean hasDerivedRole(String principalId,
			List<String> groupIds, String namespaceCode, String roleName,
			Map<String,String> paramQualification) {
        validateRequiredAttributesAgainstReceived(paramQualification);
        Map<String,String> qualification = translateInputAttributes(paramQualification);
		try {
			String documentNumber = getDocumentNumber(qualification);
			if (documentNumber != null) {
				List<ActionRequest> actionRequests = getWorkflowDocumentService().getActionRequestsForPrincipalAtNode(documentNumber, null, principalId);
				return containsActivatedRequest(roleName, actionRequests);
			}
			return false;
		} catch (WorkflowException e) {
			LOG.error("Workflow Error: ", e);
			throw new RuntimeException("Unable to load route header", e);
		}
	}

	protected boolean containsActivatedRequest(String roleName, List<ActionRequest> actionRequests) {
		if (StringUtils.containsIgnoreCase(roleName, APPROVE_REQUEST_RECIPIENT_ROLE_CONTENT)) {
			for ( ActionRequest ar : actionRequests ) {
				if ( ar.isApprovalRequest() && verifyActionRequest(ar)) {
					return true;
				}
			}
		}
		else if (StringUtils.containsIgnoreCase(roleName, ACKNOWLEDGE_REQUEST_RECIPIENT_ROLE_CONTENT)) {
			for ( ActionRequest ar : actionRequests ) {
				if ( ar.isAcknowledgeRequest() && verifyActionRequest(ar)) {
					return true;
				}
			}
		}
		else if (StringUtils.containsIgnoreCase(roleName, FYI_REQUEST_RECIPIENT_ROLE_CONTENT)) {
			for ( ActionRequest ar : actionRequests ) {
				if ( ar.isFyiRequest() && verifyActionRequest(ar)) {
					return true;
				}
			}
		}
		return false;
	}

	protected boolean verifyActionRequest(ActionRequest ar) {
		REQUESTS_STATUS_TO_CHECK statusEnum = REQUESTS_STATUS_TO_CHECK.getByCode(ar.getStatus().getCode());
		if (getRequestStatusesToCheck().contains(statusEnum)) {
			if (ar.isAdHocRequest()) {
				return getRequestTypesToCheck().equals(REQUESTS_TYPES_TO_CHECK.BOTH) || getRequestTypesToCheck().equals(REQUESTS_TYPES_TO_CHECK.ADHOC_ONLY);
			}
			else {
				return getRequestTypesToCheck().equals(REQUESTS_TYPES_TO_CHECK.BOTH) || getRequestTypesToCheck().equals(REQUESTS_TYPES_TO_CHECK.NON_ADHOC_ONLY);
			}
		}
		return false;
	}

    /**
     * Returns true, as the Action Requests change often enough that role membership is highly volatile
     *
     * @see org.kuali.rice.kim.framework.role.RoleTypeService#dynamicRoleMembership(java.lang.String, java.lang.String)
     */
    @Override
    public boolean dynamicRoleMembership(String namespaceCode, String roleName) {
		return true;
	}

	protected REQUESTS_TYPES_TO_CHECK getRequestTypesToCheck() {
		return REQUESTS_TYPES_TO_CHECK.BOTH;
	}

	protected List<REQUESTS_STATUS_TO_CHECK> getRequestStatusesToCheck() {
		return Collections.singletonList(REQUESTS_STATUS_TO_CHECK.ACTIVE);
	}

	protected WorkflowDocumentService getWorkflowDocumentService() {
		return KewApiServiceLocator.getWorkflowDocumentService();
	}
}
