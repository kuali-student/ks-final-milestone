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
 * Created by delyea on 3/10/14
 */
package org.kuali.student.cm.course.auth;

import org.kuali.rice.core.api.exception.RiceRuntimeException;
import org.kuali.rice.kim.api.KimConstants;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.krad.lookup.LookupViewAuthorizerBase;
import org.kuali.rice.krad.uif.view.View;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.KRADUtils;
import org.kuali.rice.krad.web.form.LookupForm;
import org.kuali.student.kim.permission.map.KimPermissionConstants;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is used to override the permissions for the Proposal Lookup
 *
 * @author Kuali Student Team
 */
public class ProposalLookupViewAuthorizerBase extends LookupViewAuthorizerBase {

    /**
     * Overriding this method because we want to ignore permissions with "KR-NS" template namespace with "Look Up Records" template name.
     * We are only using the "KS-SYS" template namespace with the "Open View" template name permissions.
     *
     * @param view
     * @param model
     * @param user
     * @return
     */
    @Override
    public boolean canOpenView(View view, ViewModel model, Person user) {
        Map<String, String> additionalPermissionDetails = new HashMap<String, String>();
        additionalPermissionDetails.put(KimConstants.AttributeConstants.NAMESPACE_CODE, view.getNamespaceCode());
        additionalPermissionDetails.put(KimConstants.AttributeConstants.VIEW_ID, model.getViewId());

        if (permissionExistsByTemplate(model, KRADConstants.KRAD_NAMESPACE,
                KimConstants.PermissionTemplateNames.OPEN_VIEW, additionalPermissionDetails)) {
            return isAuthorizedByTemplate(model, KRADConstants.KRAD_NAMESPACE,
                    KimConstants.PermissionTemplateNames.OPEN_VIEW, user.getPrincipalId(), additionalPermissionDetails,
                    null);
        }

        return false;
    }

    /**
     * Overriden because document initiation should not occur on the lookup for Course Proposals
     * and because default implementation looks up the document type based on the dataObject class
     * which is invalid for Course Proposalss (since there are multiple document types used).
     *
     * @see LookupViewAuthorizerBase#canInitiateDocument(org.kuali.rice.krad.web.form.LookupForm, org.kuali.rice.kim.api.identity.Person)
     *
     * @return will always return false
     */
    @Override
    public boolean canInitiateDocument(LookupForm lookupForm, Person user) {
        return false;
    }

}
