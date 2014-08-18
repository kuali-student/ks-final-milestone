package org.kuali.student.cm.course.auth;

import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.krad.document.Document;

/**
 * View Authorizer class for use with Kuali Student documents that use proposals
 */
public interface CurriculumManagementViewAuthorizer {

    public boolean canWithdraw(Document document, Person user);

}
