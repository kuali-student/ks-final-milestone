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

package org.kuali.rice.student.permission;

import org.junit.Test;
import org.kuali.rice.kew.api.WorkflowDocument;
import org.kuali.rice.kew.api.WorkflowDocumentFactory;
import org.kuali.rice.kew.api.action.ActionRequestType;
import org.kuali.rice.kew.api.document.DocumentStatus;
import org.kuali.rice.kim.api.KimConstants;
import org.kuali.rice.kim.api.permission.PermissionService;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.student.StudentStandaloneTestBase;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

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
		PermissionService permService = KimApiServiceLocator.getPermissionService();
		for (Map.Entry<String, Boolean> entry : existingPermissions.entrySet()) {
			if ( (entry.getValue() != null) && (entry.getValue().booleanValue()) ) {
                                Map<String,String> result = new LinkedHashMap<String,String> ();
                                result.put(KimConstants.AttributeConstants.DOCUMENT_NUMBER, ""+documentId);
				assertTrue("Principal Id '" + principalId + "' should have permission '" + entry.getKey() + "'", permService.isAuthorized(principalId, permissionNamespace, entry.getKey(), result));
			}
			else {
                                Map<String,String> result = new LinkedHashMap<String,String> ();
                                result.put(KimConstants.AttributeConstants.DOCUMENT_NUMBER, ""+documentId);
				assertFalse("Principal Id '" + principalId + "' should not have permission '" + entry.getKey() + "'", permService.isAuthorized(principalId, permissionNamespace, entry.getKey(), result));
			}
		}
	}

	@Test public void testOpenPermission() throws Exception {
		String documentTypeName = "BasicPermissionsTestDocument";
		Map<String,Boolean> hasPermissionByPermissionName = new HashMap<String,Boolean>();

		String principalId = "testuser1";
		WorkflowDocument doc = WorkflowDocumentFactory.createDocument(principalId, documentTypeName);
		doc.saveDocument("");

		// verify testuser1 has correct permissions as initiator
		principalId = "testuser1";
		doc = WorkflowDocumentFactory.createDocument(principalId, doc.getDocumentId());
		hasPermissionByPermissionName.put(PERMISSIONS_NAME_OPEN_DOCUMENT, Boolean.TRUE);
		hasPermissionByPermissionName.put(PERMISSIONS_NAME_COMMENT_ON_DOCUMENT, Boolean.FALSE);
		hasPermissionByPermissionName.put(PERMISSIONS_NAME_EDIT_DOCUMENT, Boolean.FALSE);
		verifyPermissions(principalId, ""+doc.getDocumentId(), hasPermissionByPermissionName);

		// send adhoc approve to 'testuser3'
		doc.adHocToPrincipal(ActionRequestType.APPROVE, "", "testuser3", "", true);
		
		// verify testuser2 has no permissions
		principalId = "testuser2";
		doc = WorkflowDocumentFactory.createDocument(principalId, doc.getDocumentId());
		hasPermissionByPermissionName.put(PERMISSIONS_NAME_OPEN_DOCUMENT, Boolean.FALSE);
		hasPermissionByPermissionName.put(PERMISSIONS_NAME_COMMENT_ON_DOCUMENT, Boolean.FALSE);
		hasPermissionByPermissionName.put(PERMISSIONS_NAME_EDIT_DOCUMENT, Boolean.FALSE);
		verifyPermissions(principalId, ""+doc.getDocumentId(), hasPermissionByPermissionName);

		// verify testuser3 has no permissions
		principalId = "testuser3";
		doc = WorkflowDocumentFactory.createDocument(principalId, doc.getDocumentId());
		hasPermissionByPermissionName.put(PERMISSIONS_NAME_OPEN_DOCUMENT, Boolean.TRUE);
		hasPermissionByPermissionName.put(PERMISSIONS_NAME_COMMENT_ON_DOCUMENT, Boolean.TRUE);
		hasPermissionByPermissionName.put(PERMISSIONS_NAME_EDIT_DOCUMENT, Boolean.TRUE);
		verifyPermissions(principalId, ""+doc.getDocumentId(), hasPermissionByPermissionName);

		doc = WorkflowDocumentFactory.createDocument(principalId, doc.getDocumentId());
		doc.route("");
		
		// verify testuser1 has correct permissions as initiator
		principalId = "testuser1";
		hasPermissionByPermissionName.put(PERMISSIONS_NAME_OPEN_DOCUMENT, Boolean.TRUE);
		hasPermissionByPermissionName.put(PERMISSIONS_NAME_COMMENT_ON_DOCUMENT, Boolean.FALSE);
		hasPermissionByPermissionName.put(PERMISSIONS_NAME_EDIT_DOCUMENT, Boolean.FALSE);
		verifyPermissions(principalId, ""+doc.getDocumentId(), hasPermissionByPermissionName);
		
		// verify testuser3 has correct permissions as router
		principalId = "testuser3";
		hasPermissionByPermissionName.put(PERMISSIONS_NAME_OPEN_DOCUMENT, Boolean.TRUE);
		hasPermissionByPermissionName.put(PERMISSIONS_NAME_COMMENT_ON_DOCUMENT, Boolean.FALSE);
		hasPermissionByPermissionName.put(PERMISSIONS_NAME_EDIT_DOCUMENT, Boolean.FALSE);
		verifyPermissions(principalId, ""+doc.getDocumentId(), hasPermissionByPermissionName);
		
		// verify fred has request for approval and correct permissions
		principalId = "fred";
		doc = WorkflowDocumentFactory.createDocument(principalId, doc.getDocumentId());
		assertTrue("Approval should be requested of user '" + principalId + "'", doc.isApprovalRequested());
		hasPermissionByPermissionName.put(PERMISSIONS_NAME_OPEN_DOCUMENT, Boolean.TRUE);
		hasPermissionByPermissionName.put(PERMISSIONS_NAME_COMMENT_ON_DOCUMENT, Boolean.TRUE);
		hasPermissionByPermissionName.put(PERMISSIONS_NAME_EDIT_DOCUMENT, Boolean.TRUE);
		verifyPermissions(principalId, ""+doc.getDocumentId(), hasPermissionByPermissionName);

		// verify doug has request for approval and correct permissions
		principalId = "doug";
		doc = WorkflowDocumentFactory.createDocument(principalId, doc.getDocumentId());
		assertTrue("FYI should be requested of user '" + principalId + "'", doc.isFYIRequested());
		hasPermissionByPermissionName.put(PERMISSIONS_NAME_OPEN_DOCUMENT, Boolean.TRUE);
		hasPermissionByPermissionName.put(PERMISSIONS_NAME_COMMENT_ON_DOCUMENT, Boolean.TRUE);
		hasPermissionByPermissionName.put(PERMISSIONS_NAME_EDIT_DOCUMENT, Boolean.FALSE);
		verifyPermissions(principalId, ""+doc.getDocumentId(), hasPermissionByPermissionName);
		
		// appprove the document as fred and re-verify his and doug's permissions
		principalId = "fred";
		doc = WorkflowDocumentFactory.createDocument(principalId, doc.getDocumentId());
		doc.approve("");

		// verify fred has no request for approval and correct permissions
		principalId = "fred";
		doc = WorkflowDocumentFactory.createDocument(principalId, doc.getDocumentId());
		assertFalse("Approval should be requested of user '" + principalId + "'", doc.isApprovalRequested());
		hasPermissionByPermissionName.put(PERMISSIONS_NAME_OPEN_DOCUMENT, Boolean.TRUE);
		hasPermissionByPermissionName.put(PERMISSIONS_NAME_COMMENT_ON_DOCUMENT, Boolean.FALSE);
		hasPermissionByPermissionName.put(PERMISSIONS_NAME_EDIT_DOCUMENT, Boolean.FALSE);
		verifyPermissions(principalId, ""+doc.getDocumentId(), hasPermissionByPermissionName);

		// verify doug still has request for FYI and correct permissions
		principalId = "doug";
		doc = WorkflowDocumentFactory.createDocument(principalId, doc.getDocumentId());
		assertTrue("FYI should be requested of user '" + principalId + "'", doc.isFYIRequested());
		hasPermissionByPermissionName.put(PERMISSIONS_NAME_OPEN_DOCUMENT, Boolean.TRUE);
		hasPermissionByPermissionName.put(PERMISSIONS_NAME_COMMENT_ON_DOCUMENT, Boolean.TRUE);
		hasPermissionByPermissionName.put(PERMISSIONS_NAME_EDIT_DOCUMENT, Boolean.FALSE);
		verifyPermissions(principalId, ""+doc.getDocumentId(), hasPermissionByPermissionName);
		
		// verify edna has request for Acknoweldge and correct permissions
		principalId = "edna";
		doc = WorkflowDocumentFactory.createDocument(principalId, doc.getDocumentId());
		assertTrue("Acknowledge should be requested of user '" + principalId + "'", doc.isAcknowledgeRequested());
		hasPermissionByPermissionName.put(PERMISSIONS_NAME_OPEN_DOCUMENT, Boolean.TRUE);
		hasPermissionByPermissionName.put(PERMISSIONS_NAME_COMMENT_ON_DOCUMENT, Boolean.TRUE);
		hasPermissionByPermissionName.put(PERMISSIONS_NAME_EDIT_DOCUMENT, Boolean.FALSE);
		verifyPermissions(principalId, ""+doc.getDocumentId(), hasPermissionByPermissionName);

		// route the document to PROCESSED
		principalId = "fran";
		doc = WorkflowDocumentFactory.createDocument(principalId, doc.getDocumentId());
		doc.approve("");
		principalId = "user1";
		doc = WorkflowDocumentFactory.createDocument(principalId, doc.getDocumentId());
		doc.approve("");

		// verify edna still has request for Acknoweldge and correct permissions
		principalId = "edna";
		doc = WorkflowDocumentFactory.createDocument(principalId, doc.getDocumentId());
                assertEquals (doc.getStatus(), DocumentStatus.PROCESSED);
		assertTrue("Acknowledge should be requested of user '" + principalId + "'", doc.isAcknowledgeRequested());
		hasPermissionByPermissionName.put(PERMISSIONS_NAME_OPEN_DOCUMENT, Boolean.TRUE);
		hasPermissionByPermissionName.put(PERMISSIONS_NAME_COMMENT_ON_DOCUMENT, Boolean.TRUE);
		hasPermissionByPermissionName.put(PERMISSIONS_NAME_EDIT_DOCUMENT, Boolean.FALSE);
		verifyPermissions(principalId, ""+doc.getDocumentId(), hasPermissionByPermissionName);

		// move document to FINAL
		doc.acknowledge("");

		// verify edna has no request and correct permissions
		principalId = "edna";
		doc = WorkflowDocumentFactory.createDocument(principalId, doc.getDocumentId());
                assertEquals (doc.getStatus(), DocumentStatus.FINAL);
		hasPermissionByPermissionName.put(PERMISSIONS_NAME_OPEN_DOCUMENT, Boolean.TRUE);
		hasPermissionByPermissionName.put(PERMISSIONS_NAME_COMMENT_ON_DOCUMENT, Boolean.FALSE);
		hasPermissionByPermissionName.put(PERMISSIONS_NAME_EDIT_DOCUMENT, Boolean.FALSE);
		verifyPermissions(principalId, ""+doc.getDocumentId(), hasPermissionByPermissionName);

		// verify doug still has request for FYI and correct permissions
		principalId = "doug";
		doc = WorkflowDocumentFactory.createDocument(principalId, doc.getDocumentId());
		assertTrue("FYI should be requested of user '" + principalId + "'", doc.isFYIRequested());
		hasPermissionByPermissionName.put(PERMISSIONS_NAME_OPEN_DOCUMENT, Boolean.TRUE);
		hasPermissionByPermissionName.put(PERMISSIONS_NAME_COMMENT_ON_DOCUMENT, Boolean.TRUE);
		hasPermissionByPermissionName.put(PERMISSIONS_NAME_EDIT_DOCUMENT, Boolean.FALSE);
		verifyPermissions(principalId, ""+doc.getDocumentId(), hasPermissionByPermissionName);

	}
}
