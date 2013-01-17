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

import org.apache.commons.lang.BooleanUtils;
import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.uif.UifParameters;
import org.kuali.rice.krad.uif.view.HistoryEntry;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.GrowlMessage;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.UrlFactory;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingConstants;
import org.kuali.student.enrollment.uif.form.KSUifForm;

import java.util.List;
import java.util.Map;
import java.util.Properties;

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

    /**
     * This method is used to generate JSON string of the customized breadcrumb. The generated JSON string will
     * be set to KSUifForm.breadCrumbJSON, which will be used by jQuery to construct the customized breadcrumb to
     * replace the default KRAD one.
     *
     *
     * @param form     object of any form that extends KSUifForm, which extends UifFormBase. Before passing the form
     *                 to this method, KSUifForm.breadCrumbItemsMap needs to be initialized. It is a LinkedHashMap with
     *                 the key as the breadcrumb item title and the value as the KRAD pageId of the page that a user will
     *                 be directed to after clicking on the breadcrumb item. The entrySet needs to be pushed into the
     *                 breadCrumbItemsMap in the order that the breadcrumb items are displayed. e.g. the following
     *                 breadCrumbItemsMap is passed to the method.
     *
     *                 { "Home"->"",
     *                   "Rollover Details"->"selectTermForRolloverDetails"
     *                   "Spring 2015 Course Offerings"->""
     *                 }
     *
     *                 The generated breadcrumb will be: Home >> Rollover Details >> Spring 2015 Course Offerings
     *                 Please note:
     *                 1. If a pageId is not assigned to the Home item, the default KS home url will be generated.
     *                    In this case, unless you really want to use a different url for the Home breadcrumb item,
     *                    just set the value to emplty.
     *                 2. The last item, e.g.Spring 2015 Course Offerings, is for the current screen. If hyperlink
     *                    is not expected, which is most of the case, just set the value to empty.
     *
     */
    public static <T extends KSUifForm> void constructBreadCrumbs(T form) {
        int mapIndex = 0;
        String JSONString = "";
        String breadCrumbItemUrl = "";

        for (Map.Entry<String, String> entry : form.getBreadCrumbItemsMap().entrySet()) {
            if (entry.getValue()!=null && !entry.getValue().isEmpty()) {
                Properties urlParameters = new Properties();
                urlParameters.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, UifConstants.MethodToCallNames.START);
                urlParameters.put(UifParameters.VIEW_ID, form.getViewId());
                urlParameters.put(UifParameters.PAGE_ID, entry.getValue());
                urlParameters.put(UifConstants.UrlParams.SHOW_HOME, BooleanUtils.toStringTrueFalse(false));
                urlParameters.put(UifConstants.UrlParams.FORM_KEY, form.getFormKey());
                if(form.getFormHistory() != null)  {
                    HistoryEntry tempCurrent = form.getFormHistory().getCurrent();
                    form.getFormHistory().setCurrent(null);
                    urlParameters.put(UifConstants.UrlParams.HISTORY, form.getFormHistory().getHistoryParameterString());
                    //set the current back to form
                    form.getFormHistory().setCurrent(tempCurrent);
                }

                //strip the controller path from the form.formPostUrl
                String[] formPostUrlElements = form.getFormPostUrl().split("/");
                String controllerPath = formPostUrlElements[formPostUrlElements.length-1];

                //contruct the url for this breadcrumb item
                breadCrumbItemUrl = UrlFactory.parameterizeUrl(controllerPath, urlParameters);


                if (mapIndex == 0) {
                    JSONString = "{\"" + CourseOfferingConstants.BREADCRUMB_JSON_ROOT_KEY + "\": {\"" + entry.getKey() + "\": \"" + breadCrumbItemUrl + "\",";
                } else if (mapIndex == (form.getBreadCrumbItemsMap().entrySet().size()-1)) {
                    JSONString += "\"" + entry.getKey() + "\":\"" + breadCrumbItemUrl + "\"}}";
                } else {
                    JSONString += "\"" + entry.getKey() + "\":\"" + breadCrumbItemUrl + "\",";
                }
            } else {
                if (mapIndex == 0) {
                    breadCrumbItemUrl = form.getFormHistory().getHomewardPath().get(0).getUrl();
                    JSONString = "{\"" + CourseOfferingConstants.BREADCRUMB_JSON_ROOT_KEY + "\": {\"" + entry.getKey() + "\": \"" + breadCrumbItemUrl + "\",";
                } else if (mapIndex == (form.getBreadCrumbItemsMap().entrySet().size()-1)) {
                    breadCrumbItemUrl = "";
                    JSONString += "\"" + entry.getKey() + "\":\"" + breadCrumbItemUrl + "\"}}";
                } else {
                    breadCrumbItemUrl = "";
                    JSONString += "\"" + entry.getKey() + "\":\"" + breadCrumbItemUrl + "\",";
                }
            }

            mapIndex++;
        }

        form.setBreadCrumbJSON(JSONString);
    }
}
