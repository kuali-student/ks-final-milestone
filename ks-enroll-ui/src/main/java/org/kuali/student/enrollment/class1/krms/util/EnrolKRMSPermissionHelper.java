/**
 * Copyright 2005-2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.enrollment.class1.krms.util;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.kim.api.KimConstants;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;
import org.kuali.rice.krms.dto.AgendaEditor;
import org.kuali.student.core.krms.util.KSKRMSPermissionHelper;
import org.kuali.student.enrollment.class1.krms.dto.AORuleManagementWrapper;
import org.kuali.student.enrollment.class1.krms.dto.CORuleManagementWrapper;
import org.kuali.student.enrollment.class2.autogen.form.ARGCourseOfferingManagementForm;
import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingWrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Kuali Student Team
 */
public class EnrolKRMSPermissionHelper extends KSKRMSPermissionHelper {

    public static void processActionPermissionForUser(MaintenanceDocumentForm document) {

        Map<String,String> permissionDetails = new HashMap<String,String>();
        Map<String,String> roleQualifications = new HashMap<String,String>();

        String socState = StringUtils.EMPTY;

        List<AgendaEditor> agendas = new ArrayList<AgendaEditor>();

        if(document.getDocument().getNewMaintainableObject().getDataObject() instanceof AORuleManagementWrapper) {
            AORuleManagementWrapper wrapper = (AORuleManagementWrapper) document.getDocument().getNewMaintainableObject().getDataObject();
            socState = StringUtils.lowerCase(wrapper.getContextBar().getTermSocState());
            roleQualifications.put("offeringAdminOrgId", wrapper.getAdminOrg());
            agendas = wrapper.getAgendas();
        } else if(document.getDocument().getNewMaintainableObject().getDataObject() instanceof CORuleManagementWrapper){
            CORuleManagementWrapper wrapper = (CORuleManagementWrapper) document.getDocument().getNewMaintainableObject().getDataObject();
            socState = StringUtils.lowerCase(wrapper.getContextBar().getTermSocState());
            roleQualifications.put("offeringAdminOrgId", wrapper.getAdminOrg());
            agendas = wrapper.getAgendas();
        }

        String principalId = GlobalVariables.getUserSession().getPerson().getPrincipalId();

        permissionDetails.put("socState", socState);
        permissionDetails.put(KimConstants.AttributeConstants.VIEW_ID, document.getViewId());

        processPermissionsForAgendaList(principalId, permissionDetails, roleQualifications, agendas, "KS-ENR");
    }

    public static void processManageCORequisiteLinkForUser(ARGCourseOfferingManagementForm form) {
        form.setRequisiteLink(false);

        String principalId = GlobalVariables.getUserSession().getPerson().getPrincipalId();

        String socState = StringUtils.lowerCase(form.getContextBar().getTermSocState());

        Map<String,String> permissionDetails = new HashMap<String,String>();

        permissionDetails.put("socState", socState);
        permissionDetails.put(KimConstants.AttributeConstants.VIEW_ID, form.getViewId());

        permissionDetails.put(KimConstants.AttributeConstants.ACTION_EVENT, "requisiteCOLink");
        if (getPermissionService().hasPermissionByTemplate(principalId, "KS-ENR", KimConstants.PermissionTemplateNames.PERFORM_ACTION, permissionDetails)) {
            form.setRequisiteLink(true);
        }
    }

    public static void processManageAORequisiteLinkForUser(ARGCourseOfferingManagementForm form, ActivityOfferingWrapper wrapper) {
        wrapper.setRequisiteLink(false);

        String principalId = GlobalVariables.getUserSession().getPerson().getPrincipalId();

        String socState = StringUtils.lowerCase(form.getContextBar().getTermSocState());

        Map<String,String> permissionDetails = new HashMap<String,String>();

        permissionDetails.put("socState", socState);
        permissionDetails.put(KimConstants.AttributeConstants.VIEW_ID, form.getViewId());

        permissionDetails.put(KimConstants.AttributeConstants.ACTION_EVENT, "requisiteAOLink");
        if (getPermissionService().hasPermissionByTemplate(principalId, "KS-ENR", KimConstants.PermissionTemplateNames.PERFORM_ACTION, permissionDetails)) {
            wrapper.setRequisiteLink(true);
        }
    }
}
