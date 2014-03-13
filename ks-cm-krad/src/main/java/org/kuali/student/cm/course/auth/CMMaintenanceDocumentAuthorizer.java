package org.kuali.student.cm.course.auth;

import org.kuali.rice.kim.api.KimConstants;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.krad.document.Document;
import org.kuali.rice.krad.maintenance.MaintenanceDocumentAuthorizerBase;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.student.kim.permission.map.KimPermissionConstants;
import org.kuali.student.r2.core.constants.KSKRMSServiceConstants;

import java.util.Map;

public class CMMaintenanceDocumentAuthorizer extends MaintenanceDocumentAuthorizerBase {

    @Override
    public boolean canOpen(Document document, Person user) {
        Map<String, String> permDetails = getPermissionDetailValues(document);
        Map<String, String> roleQuals = getRoleQualification(document, user.getPrincipalId());
        boolean result = isAuthorizedByTemplate(document, KimPermissionConstants.KS_SYS_NAMESPACE,
                KimPermissionConstants.KS_OPEN_DOCUMENT_BY_DOC_TYPE_AND_ROUTE_STATUS, user.getPrincipalId());
        return result;
    }

    @Override
    public boolean canBlanketApprove(Document document, Person user) {
        return isAuthorizedByTemplate(document, KSKRMSServiceConstants.NAMESPACE_CODE,
                "Blanket Approve", user.getPrincipalId());
    }

    @Override
    public boolean canCancel(Document document, Person user) {
        return false;
    }

}
