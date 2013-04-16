/**
 * Copyright 2012 The Kuali Foundation Licensed under the
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
 * Created by bobhurt on 12/5/12
 */
package org.kuali.student.enrollment.uif.util;

import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.GrowlMessage;

/**
 * This class contains static utility methods related to Uif (KRAD)
 *
 * @author Kuali Student Team
 */
public class KSUifUtils {

    //test public static final String KS_GROWL_INFORMATION = "information";

    public static void addGrowlMessageIcon(GrowlIcon growlIcon, String messageKey, String... messageParameters) {
        GrowlMessage growlMessage = new GrowlMessage();
        growlMessage.setTitle("");
        growlMessage.setMessageKey(messageKey);
        growlMessage.setMessageParameters(messageParameters);
        /* //test
        switch( growlIcon ) {
            case ERROR:
                growlMessage.setTheme("error");
                break;
            case INFORMATION:
                growlMessage.setTheme("information");
                break;
            case SUCCESS:
                growlMessage.setTheme("success");
                break;
            case WARNING:
                growlMessage.setTheme("warning");
                break;
        }*/
        growlMessage.setTheme(growlIcon.name());
        GlobalVariables.getMessageMap().addGrowlMessage(growlMessage);
    }
}
