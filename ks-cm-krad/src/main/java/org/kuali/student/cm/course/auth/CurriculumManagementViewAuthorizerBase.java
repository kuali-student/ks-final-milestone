/**
 * Copyright 2014 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 *
 * Created by delyea on 8/18/14
 */
package org.kuali.student.cm.course.auth;

import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.krad.document.Document;
import org.kuali.rice.krad.document.DocumentAuthorizer;
import org.kuali.rice.krad.maintenance.MaintenanceViewAuthorizerBase;
import org.kuali.rice.krad.uif.view.View;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.rice.krad.web.form.DocumentFormBase;
import org.kuali.student.cm.common.util.CurriculumManagementConstants;

import java.util.Set;

/**
 * This class is to allow the Withdraw action in Curriculum Management to function as a standard Kuali Action
 *
 * @author Kuali Student Team
 */
public class CurriculumManagementViewAuthorizerBase extends MaintenanceViewAuthorizerBase implements CurriculumManagementViewAuthorizer {

    @Override
    public Set<String> getActionFlags(View view, ViewModel model, Person user, Set<String> actions) {
        Document document = ((DocumentFormBase) model).getDocument();

        if (LOG.isDebugEnabled()) {
            LOG.debug("calling DocumentAuthorizerBase.getDocumentActionFlags for document '"
                    + document.getDocumentNumber()
                    + "'. user '"
                    + user.getPrincipalName()
                    + "'");
        }

        actions = super.getActionFlags(view, model, user,actions);

        if (actions.contains(CurriculumManagementConstants.ActionFlags.KUALI_ACTION_CAN_WITHDRAW) && !canWithdraw(document, user)) {
            actions.remove(CurriculumManagementConstants.ActionFlags.KUALI_ACTION_CAN_WITHDRAW);
        }

        return actions;
    }

    /**
     * Method to plug in the view into the Document Authorizer class for Withdraw permission
     */
    public boolean canWithdraw(Document document, Person user) {
        DocumentAuthorizer documentAuthorizer = getDocumentAuthorizer();
        if (!(CurriculumManagementMaintenanceDocumentAuthorizerBase.class.isAssignableFrom(documentAuthorizer.getClass()))) {
            throw new RuntimeException("Document Authorizer class is not assignable to proper interface");
        }
        return ((CurriculumManagementMaintenanceDocumentAuthorizerBase)documentAuthorizer).canWithdraw(document, user);
    }

}
