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
 * Created by venkat on 7/24/14
 */
package org.kuali.student.cm.common.util;

import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.util.KRADConstants;

/**
 * This class has some common util methods available across CM.
 *
 * @author Kuali Student Team
 */
public class CMUtils {

    /**
     * This method returns the url for CM Home.
     *
     * @return url for CM home view
     */
    public static String getCMHomeUrl(){

        String cmHomeControllerMapping = CurriculumManagementConstants.ControllerRequestMappings.CM_HOME.replaceFirst("/", "");

        StringBuilder cmHomeUrl = new StringBuilder(cmHomeControllerMapping);
        cmHomeUrl.append("?" + KRADConstants.DISPATCH_REQUEST_PARAMETER + "=").append(KRADConstants.START_METHOD);
        cmHomeUrl.append("&" + UifConstants.UrlParams.VIEW_ID + "=").append(CurriculumManagementConstants.CourseViewIds.CM_HOME_VIEW);

        return cmHomeUrl.toString();
    }
}
