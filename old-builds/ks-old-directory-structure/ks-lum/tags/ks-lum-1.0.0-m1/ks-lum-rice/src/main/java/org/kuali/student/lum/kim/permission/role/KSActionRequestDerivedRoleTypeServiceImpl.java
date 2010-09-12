/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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
package org.kuali.student.lum.kim.permission.role;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.kew.dto.ActionRequestDTO;
import org.kuali.rice.kew.dto.DocumentDetailDTO;
import org.kuali.rice.kew.exception.WorkflowException;
import org.kuali.rice.kew.service.KEWServiceLocator;
import org.kuali.rice.kew.service.WorkflowUtility;
import org.kuali.rice.kew.util.KEWConstants;
import org.kuali.rice.kim.bo.Role;
import org.kuali.rice.kim.bo.impl.KimAttributes;
import org.kuali.rice.kim.bo.role.dto.RoleMembershipInfo;
import org.kuali.rice.kim.bo.types.dto.AttributeSet;
import org.kuali.rice.kim.service.KIMServiceLocator;
import org.kuali.rice.kim.service.support.impl.KimDerivedRoleTypeServiceBase;
import org.kuali.rice.student.bo.KualiStudentKimAttributes;

/**
 * @author delyea
 *
 */
public class KSActionRequestDerivedRoleTypeServiceImpl extends KimDerivedRoleTypeServiceBase {
	private static final String NON_AD_HOC_APPROVE_REQUEST_RECIPIENT_ROLE_NAME = "Non-Ad Hoc Approve Request Recipient";
	private static final String APPROVE_REQUEST_RECIPIENT_ROLE_NAME = "Approve Request Recipient";
	private static final String ACKNOWLEDGE_REQUEST_RECIPIENT_ROLE_NAME = "Acknowledge Request Recipient";
	private static final String FYI_REQUEST_RECIPIENT_ROLE_NAME = "FYI Request Recipient";

	{
		requiredAttributes.add( KimAttributes.DOCUMENT_NUMBER );
		requiredAttributes.add( KimAttributes.DOCUMENT_TYPE_NAME );
		requiredAttributes.add( KualiStudentKimAttributes.QUALIFICATION_PROPOSAL_ID );
	}

	protected Long getDocumentNumber(AttributeSet qualification) throws WorkflowException {
		// first check for a valid document id passed in
		String documentId = qualification.get(KimAttributes.DOCUMENT_NUMBER);
		if (StringUtils.isNotEmpty(documentId)) {
			return Long.valueOf(documentId);
		}
		// if no document id passed in get the document via the clu id and document type name
		String documentTypeName = qualification.get(KimAttributes.DOCUMENT_TYPE_NAME);
		String appId = qualification.get(KualiStudentKimAttributes.QUALIFICATION_PROPOSAL_ID);
		DocumentDetailDTO docDetail = getWorkflowUtility().getDocumentDetailFromAppId(documentTypeName, appId);
		if (docDetail == null) {
			throw new RuntimeException("No valid document instance found for document type name '" + documentTypeName + "' and Application Id '" + appId + "'");
		}
		return docDetail.getRouteHeaderId();
	}

	protected void addMember(Map<String,List<ActionRequestDTO>> requestsByPrincipalId, String principalId, ActionRequestDTO actionRequest) {
		if (!requestsByPrincipalId.containsKey(principalId)) {
			requestsByPrincipalId.put(principalId, new ArrayList<ActionRequestDTO>());
		}
		requestsByPrincipalId.get(principalId).add(actionRequest);
	}

	/* (non-Javadoc)
	 * @see org.kuali.rice.kew.role.service.impl.ActionRequestDerivedRoleTypeServiceImpl#getRoleMembersFromApplicationRole(java.lang.String, java.lang.String, org.kuali.rice.kim.bo.types.dto.AttributeSet)
	 */
	@Override
	public List<RoleMembershipInfo> getRoleMembersFromApplicationRole(
			String namespaceCode, String roleName, AttributeSet qualification) {
		// validate required attributs
		validateRequiredAttributesAgainstReceived(qualification);
		List<RoleMembershipInfo> members = new ArrayList<RoleMembershipInfo>();
		try {
			// check for valid qualification data to check
			if (qualification != null && !qualification.isEmpty()) {
				// get all action requests for the document id given
				ActionRequestDTO[] actionRequests = getWorkflowUtility().getAllActionRequests(getDocumentNumber(qualification));
				Map<String,List<ActionRequestDTO>> requestsByPrincipalId = new HashMap<String, List<ActionRequestDTO>>();
				// build a map by principal id of action requests for the document
	            for (ActionRequestDTO actionRequest: actionRequests) {
	            	// if the request has a principal id
	            	if (actionRequest.getPrincipalId() != null) {
	            		addMember(requestsByPrincipalId, actionRequest.getPrincipalId(), actionRequest);
	            	}
	            	// if the request is a group request
	            	else if (actionRequest.isGroupRequest()) {
	            		for (String principalId : KIMServiceLocator.getGroupService().getMemberPrincipalIds(actionRequest.getGroupId())) {
		            		addMember(requestsByPrincipalId, principalId, actionRequest);
						}
	            	}
	            }
	            for (Map.Entry<String, List<ActionRequestDTO>> mapEntry : requestsByPrincipalId.entrySet()) {
					if (containsActivatedRequest(roleName, mapEntry.getValue().toArray(new ActionRequestDTO[]{}))) {
		                members.add( new RoleMembershipInfo(null/*roleId*/, null, mapEntry.getKey(), Role.PRINCIPAL_MEMBER_TYPE, null) );
					}
				}
			}
			return members;
		} catch (WorkflowException e) {
			throw new RuntimeException("Unable to load route header", e);
		}
	}

	/* (non-Javadoc)
	 * @see org.kuali.rice.kew.role.service.impl.ActionRequestDerivedRoleTypeServiceImpl#hasApplicationRole(java.lang.String, java.util.List, java.lang.String, java.lang.String, org.kuali.rice.kim.bo.types.dto.AttributeSet)
	 */
	@Override
	public boolean hasApplicationRole(String principalId,
			List<String> groupIds, String namespaceCode, String roleName,
			AttributeSet qualification) {
		validateRequiredAttributesAgainstReceived(qualification);
		try {
			if ( (qualification != null && !qualification.isEmpty()) && StringUtils.isNumeric( qualification.get(KimAttributes.DOCUMENT_NUMBER) ) ) {
				ActionRequestDTO[] actionRequests = getWorkflowUtility().getActionRequests(getDocumentNumber(qualification), null, principalId);
				return containsActivatedRequest(roleName, actionRequests);
			}
			return false;
		} catch (WorkflowException e) {
			throw new RuntimeException("Unable to load route header", e);
		}
	}

	protected boolean containsActivatedRequest(String roleName, ActionRequestDTO[] actionRequests) {
		if (APPROVE_REQUEST_RECIPIENT_ROLE_NAME.equals(roleName) || NON_AD_HOC_APPROVE_REQUEST_RECIPIENT_ROLE_NAME.equals(roleName)) {
			for ( ActionRequestDTO ar : actionRequests ) {
				if ( ar.getActionRequested().equals( KEWConstants.ACTION_REQUEST_APPROVE_REQ )
						&& ar.getStatus().equals( KEWConstants.ACTION_REQUEST_ACTIVATED ) ) {
					return APPROVE_REQUEST_RECIPIENT_ROLE_NAME.equals(roleName) || (NON_AD_HOC_APPROVE_REQUEST_RECIPIENT_ROLE_NAME.equals(roleName) && !ar.isAdHocRequest());
				}
			}
		}
		else if (ACKNOWLEDGE_REQUEST_RECIPIENT_ROLE_NAME.equals(roleName)) {
			for ( ActionRequestDTO ar : actionRequests ) {
				if ( ar.getActionRequested().equals( KEWConstants.ACTION_REQUEST_ACKNOWLEDGE_REQ ) 
					&& ar.getStatus().equals( KEWConstants.ACTION_REQUEST_ACTIVATED ) ) {
					return true;
				}
			}
		}
		else if (FYI_REQUEST_RECIPIENT_ROLE_NAME.equals(roleName)) {
			for ( ActionRequestDTO ar : actionRequests ) {
				if ( ar.getActionRequested().equals( KEWConstants.ACTION_REQUEST_FYI_REQ ) 
					&& ar.getStatus().equals( KEWConstants.ACTION_REQUEST_ACTIVATED ) ) {
					return true;
				}
			}
		}
		return false;
	}

	protected WorkflowUtility getWorkflowUtility() {
		return KEWServiceLocator.getWorkflowUtilityService();
	}
}
