package org.kuali.student.enrollment.class2.courseoffering.rule;

import org.kuali.rice.core.api.util.RiceKeyConstants;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.rules.MaintenanceDocumentRuleBase;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingWrapper;

public class ActivityOfferingRule  extends MaintenanceDocumentRuleBase {

    @Override
    protected boolean processGlobalSaveDocumentBusinessRules(MaintenanceDocument document) {
        return true;
    }

    @Override
    protected boolean isDocumentValidForSave(MaintenanceDocument maintenanceDocument) {
        boolean result = super.isDocumentValidForSave(maintenanceDocument);

        if (result){
            ActivityOfferingWrapper wrapper = (ActivityOfferingWrapper)maintenanceDocument.getNewMaintainableObject().getDataObject();
            if (wrapper.isColocatedAO() && wrapper.getColocatedActivities().isEmpty()){
                GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM,"Colocated AOs are missing");
                result = false;
            }
        }

        return result;
    }
}
