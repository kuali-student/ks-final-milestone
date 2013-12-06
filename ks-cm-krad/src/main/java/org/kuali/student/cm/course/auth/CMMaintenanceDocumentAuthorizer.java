package org.kuali.student.cm.course.auth;

import org.kuali.rice.kim.api.KimConstants;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.krad.document.Document;
import org.kuali.rice.krad.maintenance.MaintenanceDocumentAuthorizerBase;
import org.kuali.student.r2.core.constants.KSKRMSServiceConstants;

public class CMMaintenanceDocumentAuthorizer extends MaintenanceDocumentAuthorizerBase {

    @Override
    public boolean canOpen(Document document, Person user) {
        return isAuthorizedByTemplate(document, KSKRMSServiceConstants.NAMESPACE_CODE,
                KimConstants.PermissionTemplateNames.OPEN_DOCUMENT, user.getPrincipalId());
    }

}
