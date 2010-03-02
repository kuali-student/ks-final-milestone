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
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.kew.dto.DocumentDetailDTO;
import org.kuali.rice.kew.exception.WorkflowException;
import org.kuali.rice.kew.role.service.impl.RouteLogDerivedRoleTypeServiceImpl;
import org.kuali.rice.kew.service.KEWServiceLocator;
import org.kuali.rice.kew.service.WorkflowUtility;
import org.kuali.rice.kim.bo.Role;
import org.kuali.rice.kim.bo.impl.KimAttributes;
import org.kuali.rice.kim.bo.role.dto.RoleMembershipInfo;
import org.kuali.rice.kim.bo.types.dto.AttributeSet;
import org.kuali.rice.student.bo.KualiStudentKimAttributes;

/**
 *
 */
public class KSRouteLogDerivedRoleTypeServiceImpl extends RouteLogDerivedRoleTypeServiceImpl {
    private static org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(KSRouteLogDerivedRoleTypeServiceImpl.class);

    public static final String INITIATOR_ROLE_NAME = "Initiator";
    public static final String INITIATOR_OR_REVIEWER_ROLE_NAME = "Initiator or Reviewer";
    public static final String ROUTER_ROLE_NAME = "Router";

    private boolean checkFutureRequests = false;

    protected void addDocumentNumberToQualification(AttributeSet qualification) {
		// if qualification already has document id then no need to look it up
		if (!qualification.containsKey(KimAttributes.DOCUMENT_NUMBER)) {
			// document id is not contained inside qualification so look it up using clu id and document type name
			if (!qualification.containsKey(KualiStudentKimAttributes.QUALIFICATION_PROPOSAL_ID)) {
				throw new RuntimeException("Cannot find qualification for key '" + KualiStudentKimAttributes.QUALIFICATION_PROPOSAL_ID + "'");
			}
			try {
				String documentTypeName = qualification.get(KimAttributes.DOCUMENT_TYPE_NAME);
				String appId = qualification.get(KualiStudentKimAttributes.QUALIFICATION_PROPOSAL_ID);
				DocumentDetailDTO docDetail = KEWServiceLocator.getWorkflowUtilityService().getDocumentDetailFromAppId(documentTypeName, appId);
				if (docDetail == null) {
					throw new RuntimeException("No valid document instance found for document type name '" + documentTypeName + "' and Application Id '" + appId + "'");
				}
				qualification.put(KimAttributes.DOCUMENT_NUMBER, Long.toString(docDetail.getRouteHeaderId()));
			} catch (WorkflowException e) {
				String errorMessage = "Workflow Exception: " + e.getLocalizedMessage();
				LOG.error(errorMessage, e);
				throw new RuntimeException(e);
			}
		}
	}

	public boolean isCheckFutureRequests() {
		return checkFutureRequests;
	}

	public void setCheckFutureRequests(boolean checkFutureRequests) {
		this.checkFutureRequests = checkFutureRequests;
	}

	/**
	 *	- qualifier is document number
	 *	- the roles that will be of this type are KR-WKFLW Initiator and KR-WKFLW Initiator or Reviewer, KR-WKFLW Router
	 *	- only the initiator of the document in question gets the KR-WKFLW Initiator role
	 *	- user who routed the document according to the route log should get the KR-WKFLW Router role
	 *	- users who are authorized by the route log, 
	 *		i.e. initiators, people who have taken action, people with a pending action request, 
	 *		or people who will receive an action request for the document in question get the KR-WKFLW Initiator or Reviewer Role 
	 * 
	 * @see org.kuali.rice.kim.service.support.impl.KimRoleTypeServiceBase#getRoleMembersFromApplicationRole(String, String, AttributeSet)
	 */
	@Override
    public List<RoleMembershipInfo> getRoleMembersFromApplicationRole(String namespaceCode, String roleName, AttributeSet qualification) {
		addDocumentNumberToQualification(qualification);
		validateRequiredAttributesAgainstReceived(qualification);
		List<RoleMembershipInfo> members = new ArrayList<RoleMembershipInfo>();		
		if(qualification!=null && !qualification.isEmpty()){
			String documentNumber = qualification.get(KimAttributes.DOCUMENT_NUMBER);
			if (StringUtils.isNotBlank(documentNumber)) {
				Long documentNumberLong = Long.parseLong(documentNumber);
				try{
					if (INITIATOR_ROLE_NAME.equals(roleName)) {
					    String principalId = getWorkflowUtility().getDocumentInitiatorPrincipalId(documentNumberLong);
	                    members.add( new RoleMembershipInfo(null/*roleId*/, null, principalId, Role.PRINCIPAL_MEMBER_TYPE, null) );
					} else if(INITIATOR_OR_REVIEWER_ROLE_NAME.equals(roleName)) {
						String[] ids = getWorkflowUtility().getPrincipalIdsInRouteLog(documentNumberLong, isCheckFutureRequests());
//					    List<String> ids = getWorkflowUtility().getPrincipalIdsInRouteLog(documentNumberLong, true);
						if (ids != null) {
						    for ( String id : ids ) {
						    	if ( StringUtils.isNotBlank(id) ) {
						    		members.add( new RoleMembershipInfo(null/*roleId*/, null, id, Role.PRINCIPAL_MEMBER_TYPE, null) );
						    	}
						    }
						}
					} else if(ROUTER_ROLE_NAME.equals(roleName)) {
					    String principalId = getWorkflowUtility().getDocumentRoutedByPrincipalId(documentNumberLong);
	                    members.add( new RoleMembershipInfo(null/*roleId*/, null, principalId, Role.PRINCIPAL_MEMBER_TYPE, null) );
					}
				} catch(WorkflowException wex){
					throw new RuntimeException(
					"Error in getting principal Ids in route log for document number: "+documentNumber+" :"+wex.getLocalizedMessage(),wex);
				}
			}
		}
		return members;
	}

	/***
	 * @see org.kuali.rice.kim.service.support.impl.KimRoleTypeServiceBase#hasApplicationRole(java.lang.String, java.util.List, java.lang.String, java.lang.String, org.kuali.rice.kim.bo.types.dto.AttributeSet)
	 */
	@Override
	public boolean hasApplicationRole(
			String principalId, List<String> groupIds, String namespaceCode, String roleName, AttributeSet qualification){
		addDocumentNumberToQualification(qualification);
		validateRequiredAttributesAgainstReceived(qualification);
        boolean isUserInRouteLog = false;
		if(qualification!=null && !qualification.isEmpty()){
			String documentNumber = qualification.get(KimAttributes.DOCUMENT_NUMBER);
			try {
				Long documentNumberLong = Long.parseLong(documentNumber);
				if (INITIATOR_ROLE_NAME.equals(roleName)){
					isUserInRouteLog = principalId.equals(getWorkflowUtility().getDocumentInitiatorPrincipalId(documentNumberLong));
				} else if(INITIATOR_OR_REVIEWER_ROLE_NAME.equals(roleName)){
					isUserInRouteLog = getWorkflowUtility().isUserInRouteLog(documentNumberLong, principalId, isCheckFutureRequests());
				} else if(ROUTER_ROLE_NAME.equals(roleName)){
					isUserInRouteLog = principalId.equals(getWorkflowUtility().getDocumentRoutedByPrincipalId(documentNumberLong));
				}
			} catch (NumberFormatException e) {
				throw new RuntimeException("Invalid (non-numeric) document number: "+documentNumber,e);
			} catch (WorkflowException wex) {
				throw new RuntimeException("Error in determining whether the principal Id: "+principalId+" is in route log " +
						"for document number: "+documentNumber+" :"+wex.getLocalizedMessage(),wex);
			}
		}
		return isUserInRouteLog;
	}

	protected WorkflowUtility getWorkflowUtility() {
		return KEWServiceLocator.getWorkflowUtilityService();
	}

}
