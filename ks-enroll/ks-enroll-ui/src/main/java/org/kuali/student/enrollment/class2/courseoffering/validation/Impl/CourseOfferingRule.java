package org.kuali.student.enrollment.class2.courseoffering.validation.impl;

import org.kuali.rice.krad.document.MaintenanceDocument;
import org.kuali.rice.krad.rules.MaintenanceDocumentRuleBase;
import org.kuali.rice.krad.util.GlobalVariables;

/**
 * Created by IntelliJ IDEA.
 * User: huangb
 * Date: 9/20/11
 * Time: 6:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class CourseOfferingRule extends MaintenanceDocumentRuleBase {

    /**
     * @see org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase#processCustomRouteDocumentBusinessRules(org.kuali.rice.kns.document.MaintenanceDocument)
     */
    @Override
    protected boolean processCustomRouteDocumentBusinessRules(MaintenanceDocument document) {
        boolean isValid = true;
        isValid &= super.processCustomRouteDocumentBusinessRules(document);
        isValid &= GlobalVariables.getMessageMap().hasNoErrors();

        return isValid;


    }
}
