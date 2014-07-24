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
 * Created by delyea on 3/18/14
 */
package org.kuali.student.cm.course.util;

import org.kuali.rice.kew.api.KewApiConstants;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.student.cm.common.util.CurriculumManagementConstants;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is a Util class designed to hold common code that should be shared across multiple classes
 *
 * @author Kuali Student Team
 */
public class CourseProposalUtil {

    /**
     * @return true if the user has the access of a Curriculum Specialist as defined by Kuali Identity Management
     */
    public static boolean isUserCurriculumSpecialist() {
        Map<String,String> permDetails = new HashMap<String, String>();
        permDetails.put(KewApiConstants.DOCUMENT_TYPE_NAME_DETAIL, CurriculumManagementConstants.DocumentTypeNames.CourseProposal.COURSE_CREATE_ADMIN);
        return KimApiServiceLocator.getPermissionService().hasPermissionByTemplate(GlobalVariables.getUserSession().getPrincipalId(), KRADConstants.KUALI_RICE_SYSTEM_NAMESPACE, KewApiConstants.INITIATE_PERMISSION, permDetails);
    }

    public static String getCMHomeUrl(){

        String cmHomeControllerMapping = CurriculumManagementConstants.ControllerRequestMappings.CM_HOME.replaceFirst("/", "");

        StringBuilder cmHomeUrl = new StringBuilder(cmHomeControllerMapping);
        cmHomeUrl.append("?" + KRADConstants.DISPATCH_REQUEST_PARAMETER + "=").append(KRADConstants.START_METHOD);
        cmHomeUrl.append("&" + UifConstants.UrlParams.VIEW_ID + "=").append(CurriculumManagementConstants.CourseViewPageIds.CM_HOME_VIEW);

        return cmHomeUrl.toString();
    }

}
