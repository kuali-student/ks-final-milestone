package org.kuali.student.enrollment.main.view;

import org.kuali.rice.krad.maintenance.MaintenanceDocumentAuthorizerBase;
import org.kuali.rice.krad.maintenance.MaintenanceDocumentBase;
import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingEditWrapper;
import org.kuali.student.enrollment.class2.courseoffering.service.impl.ActivityOfferingMaintainableImpl;
import org.kuali.student.enrollment.class2.courseoffering.service.impl.CourseOfferingEditMaintainableImpl;

import java.util.Map;

/**
 * Custom Authorizer that resolves qualifiers from the model. This will need to be refactored and made more generic in
 * the future (configuration of mapping from model object graph to role qualifiers, maybe using beanutils
 * (object.field1[3].field2->qualifierId)
 */
public class KsMaintenanceAuthorizerBase extends MaintenanceDocumentAuthorizerBase {
    @Override
    protected void addRoleQualification(Object primaryDataObjectOrDocument, Map<String, String> attributes) {
        if (primaryDataObjectOrDocument !=null && primaryDataObjectOrDocument instanceof MaintenanceDocumentBase) {
            MaintenanceDocumentBase document = (MaintenanceDocumentBase)primaryDataObjectOrDocument;
            if(document.getOldMaintainableObject() instanceof CourseOfferingEditMaintainableImpl){
                CourseOfferingEditMaintainableImpl theForm = (CourseOfferingEditMaintainableImpl) document.getOldMaintainableObject();
                CourseOfferingEditWrapper wrapper = (CourseOfferingEditWrapper) theForm.getDataObject();
                if(wrapper.getAdminOrg() != null){
                    attributes.putAll(wrapper.getAdminOrg());
                }
                if(wrapper.getCourseOfferingInfo()!=null){
                    attributes.put("subjectArea",wrapper.getCourseOfferingInfo().getSubjectArea());
                }
            }
            if(document.getOldMaintainableObject() instanceof ActivityOfferingMaintainableImpl){
                ActivityOfferingMaintainableImpl theForm = (ActivityOfferingMaintainableImpl)document.getOldMaintainableObject();
                ActivityOfferingWrapper wrapper = (ActivityOfferingWrapper)theForm.getDataObject();
                if(wrapper.getAdminOrg()!=null){
                    attributes.put("offeringAdminOrgId",wrapper.getAdminOrg());
                }
                if(wrapper.getCourse() != null){
                    attributes.put("subjectArea",wrapper.getCourse().getSubjectArea());
                }
            }
        }

        super.addRoleQualification(primaryDataObjectOrDocument, attributes);
    }


}
