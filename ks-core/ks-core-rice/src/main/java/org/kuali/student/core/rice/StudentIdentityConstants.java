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

package org.kuali.student.core.rice;

/**
 * @author delyea
 *
 */
public class StudentIdentityConstants {

	public static final String SYSTEM_USER_PRINCIPAL_NAME = "KS";

	// role qualification keys
	public static final String QUALIFICATION_KEW_OBJECT_ID     = "kualiStudentObjectWorkflowId";
	public static final String QUALIFICATION_KEW_OBJECT_TYPE   = "kualiStudentObjectWorkflowType";

	// this must be equal to Rice constant KimAttributes.DOCUMENT_NUMBER
	public static final String DOCUMENT_NUMBER   				= "documentNumber";
	
	// this must be equal to Rice constant KimAttributes.DOCUMENT_TYPE_NAME 
	// TODO: It seems wrong to include DOCUMENT_TYPE_NAME in an identity constants class
	public static final String DOCUMENT_TYPE_NAME              = "documentTypeName";		
	
	public static final String QUALIFICATION_DATA_ID           = "dataId";
	
	//Permission Labels (Should these be moved to the PermissionType class?)
    public static final String VIEW = "View";
    public static final String COMMENT_VIEW = "Comment, View";
    public static final String EDIT_COMMENT_VIEW = "Edit, Comment, View";	
	
}
