package org.kuali.student.enrollment.class2.population.rule;

import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.rules.MaintenanceDocumentRuleBase;

public class PopulationWrapperRule extends MaintenanceDocumentRuleBase {
    @Override
    protected boolean processGlobalSaveDocumentBusinessRules(MaintenanceDocument document) {
        return true;
    }
}
