package org.kuali.student.enrollment.main.view;

import org.kuali.rice.kim.api.KimConstants;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.krad.datadictionary.DocumentEntry;
import org.kuali.rice.krad.document.DocumentAuthorizer;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.maintenance.MaintenanceDocumentBase;
import org.kuali.rice.krad.maintenance.MaintenanceViewAuthorizerBase;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.kuali.rice.krad.uif.view.View;
import org.kuali.rice.krad.uif.view.DocumentView;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.KRADUtils;
import org.kuali.rice.krad.web.form.DocumentFormBase;
import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingEditWrapper;
import org.kuali.student.enrollment.class2.courseoffering.service.impl.ActivityOfferingMaintainableImpl;
import org.kuali.student.enrollment.class2.courseoffering.service.impl.CourseOfferingEditMaintainableImpl;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: huangb
 * Date: 1/14/13
 * Time: 3:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class KsMaintenanceViewAuthorizerBase extends MaintenanceViewAuthorizerBase {
    @Override
    protected void addRoleQualification(Object primaryDataObjectOrDocument, Map<String, String> attributes) {
        if (primaryDataObjectOrDocument !=null && primaryDataObjectOrDocument instanceof MaintenanceDocument) {
            MaintenanceDocument document = (MaintenanceDocument)primaryDataObjectOrDocument;
            if(document.getOldMaintainableObject() instanceof CourseOfferingEditMaintainableImpl){
                CourseOfferingEditMaintainableImpl theForm = (CourseOfferingEditMaintainableImpl) document.getOldMaintainableObject();
                CourseOfferingEditWrapper wrapper = (CourseOfferingEditWrapper) theForm.getDataObject();
                if(wrapper.getAdminOrg() != null){
                    attributes.putAll(wrapper.getAdminOrg());
                }
            }
            if(document.getOldMaintainableObject() instanceof ActivityOfferingMaintainableImpl){
                ActivityOfferingMaintainableImpl theForm = (ActivityOfferingMaintainableImpl)document.getOldMaintainableObject();
                ActivityOfferingWrapper wrapper = (ActivityOfferingWrapper)theForm.getDataObject();
                if(wrapper.getAdminOrg()!=null){
                    attributes.put("org",wrapper.getAdminOrg());
                }

            }
        }

        super.addRoleQualification(primaryDataObjectOrDocument, attributes);
    }

    @Override
    public boolean canOpenView(View view, ViewModel model, Person user) {
        Map<String, String> additionalPermissionDetails = new HashMap<String, String>();
        additionalPermissionDetails.put(KimConstants.AttributeConstants.NAMESPACE_CODE, view.getNamespaceCode());
        additionalPermissionDetails.put(KimConstants.AttributeConstants.VIEW_ID, model.getViewId());

        if (permissionExistsByTemplate(model, KRADConstants.KRAD_NAMESPACE,
                KimConstants.PermissionTemplateNames.OPEN_VIEW, additionalPermissionDetails)) {
            return isAuthorizedByTemplate(model, KRADConstants.KRAD_NAMESPACE,
                    KimConstants.PermissionTemplateNames.OPEN_VIEW, user.getPrincipalId(), additionalPermissionDetails,
                    null);
        }

        return true;
    }

}
