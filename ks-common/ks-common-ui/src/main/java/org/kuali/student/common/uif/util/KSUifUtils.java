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
package org.kuali.student.common.uif.util;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.GrowlMessage;
import org.kuali.student.common.uif.form.KSUifForm;
import org.kuali.student.common.uif.form.KSUifMaintenanceDocumentForm;
import org.springframework.beans.factory.config.TypedStringValue;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * This class contains static utility methods related to Uif (KRAD)
 *
 * @author Kuali Student Team
 */
public class KSUifUtils {

    //test public static final String KS_GROWL_INFORMATION = "information";

    //Map keys for params of customized breadcrumb generation of multiple views
    private static final String BREADCRUMB_PREVIOUS_CONTROLLER_PATH_KEY = "previousControllerPath";
    private static final String BREADCRUMB_PREVIOUS_VIEW_ID_KEY = "previousViewId";
    private static final String BREADCRUMB_PREVIOUS_HOME_URL_KEY = "previousHomeUrl";
    private static final String BREADCRUMB_PREVIOUS_FORM_HISTORY_KEY = "previousFormHistory";
    private static final String BREADCRUMB_PREVIOUS_FORMKEY_KEY = "previousFormKey";

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

    /**
     * Populate the KSUifForm.previousFormsMap which holds information of the previous view where a user is
     * directed from in a multiple views senario. The map will be used to generate the customized breadcrumb.
     *
     * @param request - instance of HttpServletRequest
     * @param form - any form that extends KSUifForm
     *
     */
    public static <T extends KSUifForm> void populationPreviousFormsMap (HttpServletRequest request, T form) {
        if (request.getParameter(BREADCRUMB_PREVIOUS_CONTROLLER_PATH_KEY)!=null || request.getParameter(BREADCRUMB_PREVIOUS_VIEW_ID_KEY)!=null
                || request.getParameter(BREADCRUMB_PREVIOUS_HOME_URL_KEY)!=null || request.getParameter(BREADCRUMB_PREVIOUS_FORM_HISTORY_KEY)!=null
                || request.getParameter(BREADCRUMB_PREVIOUS_FORMKEY_KEY)!=null) {
            Map<String, Map<String, String>> previousFormsMap = new HashMap<String, Map<String, String>>();
            Map<String, String> tempFormMap = new HashMap<String, String>();
            if (request.getParameter(BREADCRUMB_PREVIOUS_CONTROLLER_PATH_KEY)!=null &&
                !request.getParameter(BREADCRUMB_PREVIOUS_CONTROLLER_PATH_KEY).isEmpty()) {
                tempFormMap.put(BREADCRUMB_PREVIOUS_CONTROLLER_PATH_KEY, request.getParameter(BREADCRUMB_PREVIOUS_CONTROLLER_PATH_KEY));
            }
            if (request.getParameter(BREADCRUMB_PREVIOUS_VIEW_ID_KEY)!=null &&
                !request.getParameter(BREADCRUMB_PREVIOUS_VIEW_ID_KEY).isEmpty()) {
                tempFormMap.put(BREADCRUMB_PREVIOUS_VIEW_ID_KEY, request.getParameter(BREADCRUMB_PREVIOUS_VIEW_ID_KEY));
            }
            if (request.getParameter(BREADCRUMB_PREVIOUS_HOME_URL_KEY)!=null &&
                !request.getParameter(BREADCRUMB_PREVIOUS_HOME_URL_KEY).isEmpty()) {
                tempFormMap.put(BREADCRUMB_PREVIOUS_HOME_URL_KEY, request.getParameter(BREADCRUMB_PREVIOUS_HOME_URL_KEY));
                form.setHomeUrl(request.getParameter(BREADCRUMB_PREVIOUS_HOME_URL_KEY));
            }
            if (request.getParameter(BREADCRUMB_PREVIOUS_FORM_HISTORY_KEY)!=null &&
                !request.getParameter(BREADCRUMB_PREVIOUS_FORM_HISTORY_KEY).isEmpty()) {
                tempFormMap.put(BREADCRUMB_PREVIOUS_FORM_HISTORY_KEY, request.getParameter(BREADCRUMB_PREVIOUS_FORM_HISTORY_KEY));
            }
            if (request.getParameter(BREADCRUMB_PREVIOUS_FORMKEY_KEY)!=null &&
                !request.getParameter(BREADCRUMB_PREVIOUS_FORMKEY_KEY).isEmpty()) {
                tempFormMap.put(BREADCRUMB_PREVIOUS_FORMKEY_KEY, request.getParameter(BREADCRUMB_PREVIOUS_FORMKEY_KEY));
            }
            previousFormsMap.put(request.getParameter(BREADCRUMB_PREVIOUS_VIEW_ID_KEY), tempFormMap);
            form.setPreviousFormsMap(previousFormsMap);

        }

    }

    /**
     * Populate the KSUifMaintenanceDocumentForm.previousFormsMap which holds information of the previous view where a user is
     * directed from in a multiple views senario. The map will be used to generate the customized breadcrumb.
     *
     * @param request - instance of HttpServletRequest
     * @param form - any form that extends KSUifMaintenanceDocumentForm
     *
     */
    public static <T extends KSUifMaintenanceDocumentForm> void populationPreviousFormsMap (HttpServletRequest request, T form) {
        if (request.getParameter(BREADCRUMB_PREVIOUS_CONTROLLER_PATH_KEY)!=null || request.getParameter(BREADCRUMB_PREVIOUS_VIEW_ID_KEY)!=null
                || request.getParameter(BREADCRUMB_PREVIOUS_HOME_URL_KEY)!=null || request.getParameter(BREADCRUMB_PREVIOUS_FORM_HISTORY_KEY)!=null
                || request.getParameter(BREADCRUMB_PREVIOUS_FORMKEY_KEY)!=null) {
            Map<String, Map<String, String>> previousFormsMap = new HashMap<String, Map<String, String>>();
            Map<String, String> tempFormMap = new HashMap<String, String>();
            if (request.getParameter(BREADCRUMB_PREVIOUS_CONTROLLER_PATH_KEY)!=null &&
                    !request.getParameter(BREADCRUMB_PREVIOUS_CONTROLLER_PATH_KEY).isEmpty()) {
                tempFormMap.put(BREADCRUMB_PREVIOUS_CONTROLLER_PATH_KEY, request.getParameter(BREADCRUMB_PREVIOUS_CONTROLLER_PATH_KEY));
            }
            if (request.getParameter(BREADCRUMB_PREVIOUS_VIEW_ID_KEY)!=null &&
                    !request.getParameter(BREADCRUMB_PREVIOUS_VIEW_ID_KEY).isEmpty()) {
                tempFormMap.put(BREADCRUMB_PREVIOUS_VIEW_ID_KEY, request.getParameter(BREADCRUMB_PREVIOUS_VIEW_ID_KEY));
            }
            if (request.getParameter(BREADCRUMB_PREVIOUS_HOME_URL_KEY)!=null &&
                    !request.getParameter(BREADCRUMB_PREVIOUS_HOME_URL_KEY).isEmpty()) {
                tempFormMap.put(BREADCRUMB_PREVIOUS_HOME_URL_KEY, request.getParameter(BREADCRUMB_PREVIOUS_HOME_URL_KEY));
            }
            if (request.getParameter(BREADCRUMB_PREVIOUS_FORM_HISTORY_KEY)!=null &&
                    !request.getParameter(BREADCRUMB_PREVIOUS_FORM_HISTORY_KEY).isEmpty()) {
                tempFormMap.put(BREADCRUMB_PREVIOUS_FORM_HISTORY_KEY, request.getParameter(BREADCRUMB_PREVIOUS_FORM_HISTORY_KEY));
            }
            if (request.getParameter(BREADCRUMB_PREVIOUS_FORMKEY_KEY)!=null &&
                    !request.getParameter(BREADCRUMB_PREVIOUS_FORMKEY_KEY).isEmpty()) {
                tempFormMap.put(BREADCRUMB_PREVIOUS_FORMKEY_KEY, request.getParameter(BREADCRUMB_PREVIOUS_FORMKEY_KEY));
            }
            previousFormsMap.put(request.getParameter(BREADCRUMB_PREVIOUS_VIEW_ID_KEY), tempFormMap);
        }

    }

    /**
     * Checks whether the given property value is of String type, and if so whether it contains the expression
     * placholder(s)
     *
     * @param propertyValue - value to check for expressions
     * @return boolean true if the property value contains expression(s), false if it does not
     */
    public static boolean hasExpression(Object propertyValue) {
        if (propertyValue != null) {
            // if value is string, check for el expression
            String strValue = getStringValue(propertyValue);
            if (strValue != null) {
                String elPlaceholder = StringUtils.substringBetween(strValue, UifConstants.EL_PLACEHOLDER_PREFIX,
                        UifConstants.EL_PLACEHOLDER_SUFFIX);
                if (StringUtils.isNotBlank(elPlaceholder)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Determines whether the given value is of String type and if so returns the string value
     *
     * @param value - object value to check
     * @return String string value for object or null if object is not a string type
     */
    protected static String getStringValue(Object value) {
        if (value instanceof TypedStringValue) {
            TypedStringValue typedStringValue = (TypedStringValue) value;
            return typedStringValue.getValue();
        } else if (value instanceof String) {
            return (String) value;
        }

        return null;
    }
}
