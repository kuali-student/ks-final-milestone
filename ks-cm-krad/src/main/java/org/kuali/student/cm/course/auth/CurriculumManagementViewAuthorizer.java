package org.kuali.student.cm.course.auth;

import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.krad.document.Document;

/**
 * View Authorizer class for use with Kuali Student documents that use proposals
 */
public interface CurriculumManagementViewAuthorizer {

    /**
     * A Method to dictate whether the user can Withdraw the document from Workflow.
     */
    public boolean canWithdraw(Document document, Person user);

    /**
     * A Method to dictate whether the user can Add Collaborators to the document.
     */
    public boolean canAddCollaborators(Document document, Person user);

}
