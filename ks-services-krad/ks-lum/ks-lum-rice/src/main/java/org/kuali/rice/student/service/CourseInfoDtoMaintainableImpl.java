package org.kuali.rice.student.service;

import java.util.Map;

import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.KualiMaintainableImpl;

public class CourseInfoDtoMaintainableImpl extends KualiMaintainableImpl {

    /**
     * This overridden method ...
     * 
     * @see org.kuali.rice.kns.maintenance.KualiMaintainableImpl#retrieveObjectForEditOrCopy(org.kuali.rice.kns.document.MaintenanceDocument, java.util.Map)
     */
    @Override
    public Object retrieveObjectForEditOrCopy(MaintenanceDocument arg0, Map<String, String> arg1) {
        // TODO huangb - THIS METHOD NEEDS JAVADOCS
        return super.retrieveObjectForEditOrCopy(arg0, arg1);
    }

    /**
     * This overridden method ...
     * 
     * @see org.kuali.rice.kns.maintenance.KualiMaintainableImpl#saveBusinessObject()
     */
    @Override
    public void saveBusinessObject() {
        // TODO huangb - THIS METHOD NEEDS JAVADOCS
        super.saveBusinessObject();
    }

}
