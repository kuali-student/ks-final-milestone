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
package org.kuali.rice.student.permission;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.kuali.rice.kew.service.WorkflowDocument;
import org.kuali.rice.kew.util.KEWConstants;
import org.kuali.rice.kim.bo.impl.KimAttributes;
import org.kuali.rice.kim.bo.types.dto.AttributeSet;
import org.kuali.rice.kim.service.KIMServiceLocator;
import org.kuali.rice.kim.service.PermissionService;
import org.kuali.rice.student.StudentStandaloneTestBase;

/**
 * Test case to verify permissions work properly
 * 
 * @author delyea
 */
public class BasicPermissionsTest extends StudentStandaloneTestBase {

	protected static final String PERMISSIONS_NAME_OPEN_DOCUMENT = "Open Document";
	protected static final String PERMISSIONS_NAME_COMMENT_ON_DOCUMENT = "Comment on Document";
	protected static final String PERMISSIONS_NAME_EDIT_DOCUMENT = "Edit Document";

	@Override
	public void setUp() throws Exception {
		super.setUp();
		loadXmlFile("BasicPermissionsTestConfig.xml");
	}

	protected void verifyPermissions(String principalId, String documentId, Map<String,Boolean> existingPermissions) {
		String permissionNamespace = "KS-LUM";
		PermissionService permService = KIMServiceLocator.getPermissionService();
		for (Map.Entry<String, Boolean> entry : existingPermissions.entrySet()) {
			if ( (entry.getValue() != null) && (entry.getValue().booleanValue()) ) {
				assertTrue("Principal Id '" + principalId + "' should have permission '" + entry.getKey() + "'", permService.isAuthorized(principalId, permissionNamespace, entry.getKey(), null, new AttributeSet(KimAttributes.DOCUMENT_NUMBER, ""+documentId)));
			}
			else {
				assertFalse("Principal Id '" + principalId + "' should not have permission '" + entry.getKey() + "'", permService.isAuthorized(principalId, permissionNamespace, entry.getKey(), null, new AttributeSet(KimAttributes.DOCUMENT_NUMBER, ""+documentId)));
			}
		}
	}

	@Test public void testOpenPermission() throws Exception {
		String documentTypeName = "BasicPermissionsTestDocument";
		Map<String,Boolean> hasPermissionByPermissionName = new HashMap<String,Boolean>();

		String principalId = "testuser1";
		WorkflowDocument doc = new WorkflowDocument(principalId, documentTypeName);
		doc.saveDocument("");

		// verify testuser1 has correct permissions as initiator
		principalId = "testuser1";
		doc = new WorkflowDocument(principalId, doc.getRouteHeaderId());
		hasPermissionByPermissionName.put(PERMISSIONS_NAME_OPEN_DOCUMENT, Boolean.TRUE);
		hasPermissionByPermissionName.put(PERMISSIONS_NAME_COMMENT_ON_DOCUMENT, Boolean.FALSE);
		hasPermissionByPermissionName.put(PERMISSIONS_NAME_EDIT_DOCUMENT, Boolean.FALSE);
		verifyPermissions(principalId, ""+doc.getRouteHeaderId(), hasPermissionByPermissionName);

		// send adhoc approve to 'testuser3'
		doc.adHocRouteDocumentToPrincipal(KEWConstants.ACTION_REQUEST_APPROVE_REQ, "", "testuser3", "", true);
		
		// verify testuser2 has no permissions
		principalId = "testuser2";
		doc = new WorkflowDocument(principalId, doc.getRouteHeaderId());
		hasPermissionByPermissionName.put(PERMISSIONS_NAME_OPEN_DOCUMENT, Boolean.FALSE);
		hasPermissionByPermissionName.put(PERMISSIONS_NAME_COMMENT_ON_DOCUMENT, Boolean.FALSE);
		hasPermissionByPermissionName.put(PERMISSIONS_NAME_EDIT_DOCUMENT, Boolean.FALSE);
		verifyPermissions(principalId, ""+doc.getRouteHeaderId(), hasPermissionByPermissionName);

		// verify testuser3 has no permissions
		principalId = "testuser3";
		doc = new WorkflowDocument(principalId, doc.getRouteHeaderId());
		hasPermissionByPermissionName.put(PERMISSIONS_NAME_OPEN_DOCUMENT, Boolean.TRUE);
		hasPermissionByPermissionName.put(PERMISSIONS_NAME_COMMENT_ON_DOCUMENT, Boolean.TRUE);
		hasPermissionByPermissionName.put(PERMISSIONS_NAME_EDIT_DOCUMENT, Boolean.TRUE);
		verifyPermissions(principalId, ""+doc.getRouteHeaderId(), hasPermissionByPermissionName);

		doc = new WorkflowDocument(principalId, doc.getRouteHeaderId());
		doc.routeDocument("");
		
		// verify testuser1 has correct permissions as initiator
		principalId = "testuser1";
		hasPermissionByPermissionName.put(PERMISSIONS_NAME_OPEN_DOCUMENT, Boolean.TRUE);
		hasPermissionByPermissionName.put(PERMISSIONS_NAME_COMMENT_ON_DOCUMENT, Boolean.FALSE);
		hasPermissionByPermissionName.put(PERMISSIONS_NAME_EDIT_DOCUMENT, Boolean.FALSE);
		verifyPermissions(principalId, ""+doc.getRouteHeaderId(), hasPermissionByPermissionName);
		
		// verify testuser3 has correct permissions as router
		principalId = "testuser3";
		hasPermissionByPermissionName.put(PERMISSIONS_NAME_OPEN_DOCUMENT, Boolean.TRUE);
		hasPermissionByPermissionName.put(PERMISSIONS_NAME_COMMENT_ON_DOCUMENT, Boolean.FALSE);
		hasPermissionByPermissionName.put(PERMISSIONS_NAME_EDIT_DOCUMENT, Boolean.FALSE);
		verifyPermissions(principalId, ""+doc.getRouteHeaderId(), hasPermissionByPermissionName);
		
		// verify fred has request for approval and correct permissions
		principalId = "fred";
		doc = new WorkflowDocument(principalId, doc.getRouteHeaderId());
		assertTrue("Approval should be requested of user '" + principalId + "'", doc.isApprovalRequested());
		hasPermissionByPermissionName.put(PERMISSIONS_NAME_OPEN_DOCUMENT, Boolean.TRUE);
		hasPermissionByPermissionName.put(PERMISSIONS_NAME_COMMENT_ON_DOCUMENT, Boolean.TRUE);
		hasPermissionByPermissionName.put(PERMISSIONS_NAME_EDIT_DOCUMENT, Boolean.TRUE);
		verifyPermissions(principalId, ""+doc.getRouteHeaderId(), hasPermissionByPermissionName);

		// verify doug has request for approval and correct permissions
		principalId = "doug";
		doc = new WorkflowDocument(principalId, doc.getRouteHeaderId());
		assertTrue("FYI should be requested of user '" + principalId + "'", doc.isFYIRequested());
		hasPermissionByPermissionName.put(PERMISSIONS_NAME_OPEN_DOCUMENT, Boolean.TRUE);
		hasPermissionByPermissionName.put(PERMISSIONS_NAME_COMMENT_ON_DOCUMENT, Boolean.TRUE);
		hasPermissionByPermissionName.put(PERMISSIONS_NAME_EDIT_DOCUMENT, Boolean.FALSE);
		verifyPermissions(principalId, ""+doc.getRouteHeaderId(), hasPermissionByPermissionName);
		
		// appprove the document as fred and re-verify his and doug's permissions
		principalId = "fred";
		doc = new WorkflowDocument(principalId, doc.getRouteHeaderId());
		doc.approve("");

		// verify fred has no request for approval and correct permissions
		principalId = "fred";
		doc = new WorkflowDocument(principalId, doc.getRouteHeaderId());
		assertFalse("Approval should be requested of user '" + principalId + "'", doc.isApprovalRequested());
		hasPermissionByPermissionName.put(PERMISSIONS_NAME_OPEN_DOCUMENT, Boolean.TRUE);
		hasPermissionByPermissionName.put(PERMISSIONS_NAME_COMMENT_ON_DOCUMENT, Boolean.FALSE);
		hasPermissionByPermissionName.put(PERMISSIONS_NAME_EDIT_DOCUMENT, Boolean.FALSE);
		verifyPermissions(principalId, ""+doc.getRouteHeaderId(), hasPermissionByPermissionName);

		// verify doug still has request for FYI and correct permissions
		principalId = "doug";
		doc = new WorkflowDocument(principalId, doc.getRouteHeaderId());
		assertTrue("FYI should be requested of user '" + principalId + "'", doc.isFYIRequested());
		hasPermissionByPermissionName.put(PERMISSIONS_NAME_OPEN_DOCUMENT, Boolean.TRUE);
		hasPermissionByPermissionName.put(PERMISSIONS_NAME_COMMENT_ON_DOCUMENT, Boolean.TRUE);
		hasPermissionByPermissionName.put(PERMISSIONS_NAME_EDIT_DOCUMENT, Boolean.FALSE);
		verifyPermissions(principalId, ""+doc.getRouteHeaderId(), hasPermissionByPermissionName);
		
		// verify edna has request for Acknoweldge and correct permissions
		principalId = "edna";
		doc = new WorkflowDocument(principalId, doc.getRouteHeaderId());
		assertTrue("Acknowledge should be requested of user '" + principalId + "'", doc.isAcknowledgeRequested());
		hasPermissionByPermissionName.put(PERMISSIONS_NAME_OPEN_DOCUMENT, Boolean.TRUE);
		hasPermissionByPermissionName.put(PERMISSIONS_NAME_COMMENT_ON_DOCUMENT, Boolean.TRUE);
		hasPermissionByPermissionName.put(PERMISSIONS_NAME_EDIT_DOCUMENT, Boolean.FALSE);
		verifyPermissions(principalId, ""+doc.getRouteHeaderId(), hasPermissionByPermissionName);

		// route the document to PROCESSED
		principalId = "fran";
		doc = new WorkflowDocument(principalId, doc.getRouteHeaderId());
		doc.approve("");
		principalId = "user1";
		doc = new WorkflowDocument(principalId, doc.getRouteHeaderId());
		doc.approve("");

		// verify edna still has request for Acknoweldge and correct permissions
		principalId = "edna";
		doc = new WorkflowDocument(principalId, doc.getRouteHeaderId());
		assertTrue("", doc.stateIsProcessed());
		assertTrue("Acknowledge should be requested of user '" + principalId + "'", doc.isAcknowledgeRequested());
		hasPermissionByPermissionName.put(PERMISSIONS_NAME_OPEN_DOCUMENT, Boolean.TRUE);
		hasPermissionByPermissionName.put(PERMISSIONS_NAME_COMMENT_ON_DOCUMENT, Boolean.TRUE);
		hasPermissionByPermissionName.put(PERMISSIONS_NAME_EDIT_DOCUMENT, Boolean.FALSE);
		verifyPermissions(principalId, ""+doc.getRouteHeaderId(), hasPermissionByPermissionName);

		// move document to FINAL
		doc.acknowledge("");

		// verify edna has no request and correct permissions
		principalId = "edna";
		doc = new WorkflowDocument(principalId, doc.getRouteHeaderId());
		assertTrue("Doc should be FINAL", doc.stateIsFinal());
		hasPermissionByPermissionName.put(PERMISSIONS_NAME_OPEN_DOCUMENT, Boolean.TRUE);
		hasPermissionByPermissionName.put(PERMISSIONS_NAME_COMMENT_ON_DOCUMENT, Boolean.FALSE);
		hasPermissionByPermissionName.put(PERMISSIONS_NAME_EDIT_DOCUMENT, Boolean.FALSE);
		verifyPermissions(principalId, ""+doc.getRouteHeaderId(), hasPermissionByPermissionName);

		// verify doug still has request for FYI and correct permissions
		principalId = "doug";
		doc = new WorkflowDocument(principalId, doc.getRouteHeaderId());
		assertTrue("FYI should be requested of user '" + principalId + "'", doc.isFYIRequested());
		hasPermissionByPermissionName.put(PERMISSIONS_NAME_OPEN_DOCUMENT, Boolean.TRUE);
		hasPermissionByPermissionName.put(PERMISSIONS_NAME_COMMENT_ON_DOCUMENT, Boolean.TRUE);
		hasPermissionByPermissionName.put(PERMISSIONS_NAME_EDIT_DOCUMENT, Boolean.FALSE);
		verifyPermissions(principalId, ""+doc.getRouteHeaderId(), hasPermissionByPermissionName);

	}
}
