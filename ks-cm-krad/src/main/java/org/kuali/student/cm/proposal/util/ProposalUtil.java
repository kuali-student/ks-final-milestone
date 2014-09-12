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
 * Created by delyea on 8/22/14
 */
package org.kuali.student.cm.proposal.util;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.kew.api.KewApiConstants;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.uif.UifParameters;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.student.cm.common.util.CMUtils;
import org.kuali.student.cm.common.util.CurriculumManagementConstants;
import org.kuali.student.r1.common.rice.authorization.ProposalPermissionTypes;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * This class is a Util class designed to hold common proposal related code that should be shared across multiple classes
 *
 * @author Kuali Student Team
 */
public class ProposalUtil {

    /**
     * @return true if the user has the access of a Curriculum Specialist as defined by Kuali Identity Management
     * for the given document type
     */
    public static boolean isUserCurriculumSpecialist() {
        return isUserCurriculumSpecialist(CurriculumManagementConstants.DocumentTypeNames.CM_PARENT_DOCUMENT_TYPE);
    }

    /**
     * @return true if the user has the access of a Curriculum Specialist as defined by Kuali Identity Management
     * for the given document type
     */
    public static boolean isUserCurriculumSpecialist(String documentTypeName) {
        Map<String,String> permDetails = new HashMap();
        permDetails.put(KewApiConstants.DOCUMENT_TYPE_NAME_DETAIL, documentTypeName);
        // method below uses 'PermissionService.hasPermissionByTemplate' because KEW does not use role qualifiers for the Initiate Document permission
        return KimApiServiceLocator.getPermissionService().hasPermissionByTemplate(GlobalVariables.getUserSession().getPrincipalId(), ProposalPermissionTypes.CURRICULUM_SPECIALIST.getPermissionNamespace(), ProposalPermissionTypes.CURRICULUM_SPECIALIST.getPermissionTemplateName(), permDetails);
    }

    /**
     * Constructs a properties object with values that are common across all proposals
     */
    public static Properties getProposalUrlProperties(String methodToCall, String pageId, String workflowDocId) {
        Properties props = new Properties();
        props.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, methodToCall);
        props.put(KRADConstants.PARAMETER_COMMAND, KRADConstants.METHOD_DISPLAY_DOC_SEARCH_VIEW);
        props.put(KRADConstants.RETURN_LOCATION_PARAMETER, CMUtils.getCMHomeUrl());

        if (StringUtils.isNotBlank(pageId)) {
            props.put(UifParameters.PAGE_ID, pageId);
        }
        props.put(KRADConstants.PARAMETER_DOC_ID, workflowDocId);

        return props;
    }

}
