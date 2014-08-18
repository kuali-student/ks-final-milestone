package org.kuali.student.cm.course.auth;

import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.krad.document.Document;
import org.kuali.rice.krad.maintenance.MaintenanceDocumentAuthorizerBase;
import org.kuali.student.kim.permission.map.KimPermissionConstants;
import org.kuali.student.r1.common.rice.authorization.ProposalPermissionTypes;

public class CurriculumManagementMaintenanceDocumentAuthorizerBase extends MaintenanceDocumentAuthorizerBase implements CurriculumManagementMaintenanceDocumentAuthorizer {

    public boolean canWithdraw(Document document, Person user) {
        return isAuthorizedByTemplate(document, ProposalPermissionTypes.WITHDRAW.getPermissionNamespace(),
                ProposalPermissionTypes.WITHDRAW.getPermissionTemplateName(), user.getPrincipalId());
    }

    @Override
    public boolean canOpen(Document document, Person user) {
        return isAuthorizedByTemplate(document, KimPermissionConstants.KS_SYS_NAMESPACE,
                KimPermissionConstants.KS_OPEN_DOCUMENT_BY_DOC_TYPE_AND_ROUTE_STATUS, user.getPrincipalId());
    }

}
