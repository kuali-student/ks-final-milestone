/**
 * Copyright 2013 The Kuali Foundation Licensed under the
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
 * Created by vgadiyak on 5/21/13
 */
package org.kuali.rice.krad.web.form;

import org.kuali.rice.krad.util.GlobalVariables;

import javax.servlet.http.HttpServletRequest;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class KsUifFormBase extends UifFormBase {

    @Override
    public void postBind(HttpServletRequest request) {

        String growlMessage = request.getParameter("growlMessage");
        String growlMessageParam = request.getParameter("growlMessageParam");

        if (growlMessage != null) {
            if (growlMessageParam != null) {
                GlobalVariables.getMessageMap().addGrowlMessage("", growlMessage, growlMessageParam);
            } else {
                GlobalVariables.getMessageMap().addGrowlMessage("", growlMessage);
            }
        }

        super.postBind(request);
    }

}
