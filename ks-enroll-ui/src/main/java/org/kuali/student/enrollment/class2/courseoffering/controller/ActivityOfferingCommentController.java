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
 */
package org.kuali.student.enrollment.class2.courseoffering.controller;

import org.kuali.rice.kim.api.KimConstants;
import org.kuali.rice.kim.api.identity.PersonService;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.student.enrollment.class2.courseoffering.form.KSCommentForm;
import org.kuali.student.enrollment.class2.courseoffering.form.KSCommentWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

//import org.kuali.student.cm.course.form.CMCommentForm;
//import org.kuali.student.cm.course.form.KSCommentWrapper;

/**
 * This class handles the comment functionality for a proposal.
 *
 * @author Kuali Student Team
 */
@Controller
@RequestMapping(value = "/activityOfferingComment")
public class ActivityOfferingCommentController extends KSCommentController{

    private static final Logger LOG = LoggerFactory.getLogger(ActivityOfferingCommentController.class);
    protected PersonService personService;

    protected boolean isOperationPermitted(final int operation, KSCommentForm form){
        Map<String,String> permissionDetails = new HashMap<String,String>();
        Map<String,String> roleQualifications = new HashMap<String,String>();

//        permissionDetails.put(KimConstants.AttributeConstants.ACTION_EVENT, "ksAddComment");
        String socStateKey = form.getSocState();
        String socState = socStateKey==null?null:socStateKey.substring(socStateKey.lastIndexOf('.')+1);
        permissionDetails.put("socState", socState);

        roleQualifications.put("subjectArea", form.getSubjectArea());
        roleQualifications.put("offeringAdminOrgId", form.getOfferingAdminOrgId());

        boolean isSuccessfull = false;
        switch(operation){
            case OPERATION_ADD:
                permissionDetails.put(KimConstants.AttributeConstants.ACTION_EVENT, "ksAddComment");
                break;
            case OPERATION_DELETE:
                permissionDetails.put(KimConstants.AttributeConstants.ACTION_EVENT, "ksDeleteComment");
                break;
            case OPERATION_EDIT:
                permissionDetails.put(KimConstants.AttributeConstants.ACTION_EVENT, "ksEditComment");
                break;
            case OPERATION_VIEW:
//                isSuccessfull = canViewComment();
                break;
        }
        return KimApiServiceLocator.getPermissionService().isAuthorizedByTemplate(GlobalVariables.getUserSession().getPrincipalId(), "KS-ENR", KimConstants.PermissionTemplateNames.PERFORM_ACTION, permissionDetails, roleQualifications);
    }
}
