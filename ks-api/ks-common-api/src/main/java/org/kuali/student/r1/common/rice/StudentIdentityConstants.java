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

package org.kuali.student.r1.common.rice;

import java.util.HashSet;
import java.util.Set;

/**
 * @author delyea
 * 
 */
public class StudentIdentityConstants {

    public static final String SYSTEM_USER_PRINCIPAL_NAME = "ks";

    public static final String QUALIFICATION_KS_PROPOSAL_ID = "ksProposalIdQualificationKey";

    public static final String QUALIFICATION_PROPOSAL_REF_TYPE = "referenceType.clu.proposal";
    public static final Set<String> QUALIFICATION_PROPOSAL_ID_REF_TYPES = new HashSet<String>();
    static {
        // this must contain all proposal reference types
        QUALIFICATION_PROPOSAL_ID_REF_TYPES.add(QUALIFICATION_PROPOSAL_REF_TYPE);
    }

    public static final String QUALIFICATION_KEW_OBJECT_ID = "kualiStudentObjectWorkflowId";
    public static final String QUALIFICATION_KEW_OBJECT_TYPE = "kualiStudentObjectWorkflowType";

    // this must be equal to Rice constant KimAttributes.DOCUMENT_NUMBER
    public static final String DOCUMENT_NUMBER = "documentNumber";

    // this must be equal to Rice constant KimAttributes.DOCUMENT_TYPE_NAME
    // TODO: It seems wrong to include DOCUMENT_TYPE_NAME in an identity constants class
    public static final String DOCUMENT_TYPE_NAME = "documentTypeName";

    // this must be equal to Rice constant KimAttributes.ROUTE_STATUS_CODE
    public static final String ROUTE_STATUS_CODE = "routeStatusCode";

    // this must be equal to Rice constant KimAttributes.ROUTE_NODE_NAME
    public static final String ROUTE_NODE_NAME = "routeNodeName";

    public static final String KS_REFERENCE_TYPE_KEY = "ksReferenceTypeKey";

    public static final String QUALIFICATION_DATA_ID = "dataId";

    public static final String SCREEN_COMPONENT = "screenComponent";
    
    public static final String KSCM_ADMIN_ROLE_NAME = "Kuali Student CM Admin";
    
    public static final String KSCM_USER_ROLE_NAME = "Kuali Student CM User";
    
    public static final String KS_NAMESPACE_CD = "KS-SYS";

    public static final String PERMISSION_TEMPLATE_NAMESPACE_COMMENTS = KS_NAMESPACE_CD;
    public static final String PERMISSION_TEMPLATE_NAME_COMMENTS_ADD = "Add a Comment";
    public static final String PERMISSION_TEMPLATE_NAME_COMMENTS_EDIT = "Edit a Comment";
    public static final String PERMISSION_TEMPLATE_NAME_COMMENTS_DELETE = "Delete a Comment";

}
