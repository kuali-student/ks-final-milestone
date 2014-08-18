package org.kuali.student.cm.course.auth;

import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.krad.document.Document;

/**
 * Document Authorizer class for CM that allows Withdraw permission to function as other standard Kuali actions
 */
public interface CurriculumManagementMaintenanceDocumentAuthorizer {

    /**
     * Determines via KIM Permission checks who can Withdraw a document
     */
    public boolean canWithdraw(Document document, Person user);

}
