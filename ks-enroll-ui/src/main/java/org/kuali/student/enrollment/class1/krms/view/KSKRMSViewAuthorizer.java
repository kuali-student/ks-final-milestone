package org.kuali.student.enrollment.class1.krms.view;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.kim.api.KimConstants;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.krad.uif.element.Action;
import org.kuali.rice.krad.uif.view.View;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;
import org.kuali.student.enrollment.class1.krms.dto.CORuleManagementWrapper;
import org.kuali.student.enrollment.main.view.KsViewAuthorizerBase;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: SW Genis
 * Date: 2013/07/26
 * Time: 10:05 AM
 * To change this template use File | Settings | File Templates.
 */
public class KSKRMSViewAuthorizer extends KsViewAuthorizerBase {

    public boolean canPerformAction(View view, ViewModel model, Action action, String actionEvent,
                                    String actionId, Person user) {
        // check action authz flag is set
        if (!action.getActionSecurity().isPerformActionAuthz()) {
            return true;
        }

        MaintenanceDocumentForm maintenanceDocumentForm = (MaintenanceDocumentForm) model;
        CORuleManagementWrapper wrapper = (CORuleManagementWrapper) maintenanceDocumentForm.getDocument().getNewMaintainableObject().getDataObject();

        Map<String,String> permissionDetails = new HashMap<String,String>();
        Map<String,String> roleQualifications = new HashMap<String,String>();

        String socState = StringUtils.lowerCase(wrapper.getContextBar().getTermSocState());

        roleQualifications.put("offeringAdminOrgId", wrapper.getAdminOrg());

        permissionDetails.put("socState", socState);
        permissionDetails.put("ruleType", action.getActionParameters().get("ruleType"));

        if (StringUtils.isNotBlank(actionEvent)) {
            permissionDetails.put(KimConstants.AttributeConstants.ACTION_EVENT, actionEvent);
        }

        return isAuthorizedByTemplate(view, action, model, KimConstants.PermissionTemplateNames.PERFORM_ACTION,
                user, permissionDetails, roleQualifications, false);
    }

}
