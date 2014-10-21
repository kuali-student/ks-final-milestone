package org.kuali.student.cm.uif.util;

import org.kuali.rice.krad.document.Document;

/**
 * Presentation Controller class for use with Curriculum Management classes
 */
public interface CurriculumManagementMaintenancePresentationController {

    /**
     * A Method to dictate whether the user can Withdraw the document from workflow. This is an action
     * that can be performed by a user with no active KEW action requested of them.
     */
    public boolean canWithdraw(Document document);

    /**
     * A Method to dictate whether the user can Add Collaborators to the document.
     */
    public boolean canAddCollaborators(Document document);

}
