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
import org.kuali.student.common.uif.util.GrowlIcon;
import org.kuali.student.common.uif.util.KSUifUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * This class provides a base form class for KS KRAD forms
 *
 * @author Kuali Student Team
 */
public class KsUifFormBase extends UifFormBase {

    @Override
    public void postBind(HttpServletRequest request) {

        String growlMessage = request.getParameter("growl.message");
        String temp = request.getParameter("growl.message.params");
        String[] growlMessageParams;
        if(temp!=null){
            growlMessageParams = temp.split(",");
        }
        else{
            growlMessageParams=new String[0];
        }

        if (growlMessage != null) {
              KSUifUtils.addGrowlMessageIcon(GrowlIcon.SUCCESS, growlMessage, growlMessageParams);
        }
        String sectionKey = request.getParameter("warning.message.section.id");
        String warningMessage = request.getParameter("warning.message");

        if (warningMessage != null)
        {
            GlobalVariables.getMessageMap().putWarningForSectionId(sectionKey,warningMessage);
        }

        super.postBind(request);
    }

}
