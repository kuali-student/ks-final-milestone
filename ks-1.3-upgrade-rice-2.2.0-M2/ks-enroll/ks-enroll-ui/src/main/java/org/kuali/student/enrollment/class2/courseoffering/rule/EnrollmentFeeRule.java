package org.kuali.student.enrollment.class2.courseoffering.rule;

import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.rules.MaintenanceDocumentRuleBase;

public class EnrollmentFeeRule extends MaintenanceDocumentRuleBase {

    @Override
    protected boolean processGlobalSaveDocumentBusinessRules(MaintenanceDocument document) {
        return true;
    }
}
