package org.kuali.student.enrollment.main.view;

import org.kuali.rice.kim.api.KimConstants;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.krad.uif.view.View;
import org.kuali.rice.krad.uif.view.ViewAuthorizerBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.rice.krad.util.KRADConstants;

import org.kuali.student.enrollment.class2.courseoffering.form.CourseOfferingManagementForm;
//import org.kuali.rice.student.bo.KualiStudentKimAttributes;
import java.util.HashMap;
import java.util.Map;

public class KsViewAuthorizerBase extends ViewAuthorizerBase {

    @Override
    public boolean canOpenView(View view, ViewModel model, Person user) {
        Map<String, String> additionalPermissionDetails = new HashMap<String, String>();
        additionalPermissionDetails.put(KimConstants.AttributeConstants.NAMESPACE_CODE, view.getNamespaceCode());
        additionalPermissionDetails.put(KimConstants.AttributeConstants.VIEW_ID, model.getViewId());

        if (permissionExistsByTemplate(model, KRADConstants.KRAD_NAMESPACE,
                KimConstants.PermissionTemplateNames.OPEN_VIEW, additionalPermissionDetails)) {
            if (model instanceof CourseOfferingManagementForm) {
                CourseOfferingManagementForm theForm = (CourseOfferingManagementForm) model;
                String adminOrg = theForm.getAdminOrg();
                Map<String, String> additionalRoleQualifications = new HashMap<String, String>();
//                additionalRoleQualifications.put(KualiStudentKimAttributes.QUALIFICATION_ORG_ID,adminOrg);
                additionalRoleQualifications.put("org",adminOrg);
                return isAuthorizedByTemplate(model, KRADConstants.KRAD_NAMESPACE,
                        KimConstants.PermissionTemplateNames.OPEN_VIEW, user.getPrincipalId(), additionalPermissionDetails,
                        additionalRoleQualifications);

            }
            else
                return isAuthorizedByTemplate(model, KRADConstants.KRAD_NAMESPACE,
                    KimConstants.PermissionTemplateNames.OPEN_VIEW, user.getPrincipalId(), additionalPermissionDetails,
                    null);
        }

        return true;
    }
}
