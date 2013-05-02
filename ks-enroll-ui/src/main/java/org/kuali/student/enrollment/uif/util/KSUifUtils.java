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
import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.uif.UifParameters;
import org.kuali.rice.krad.uif.view.HistoryEntry;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.GrowlMessage;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.UrlFactory;
import org.kuali.rice.krad.web.controller.UifControllerHelper;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingConstants;
import org.kuali.student.enrollment.uif.form.KSUifForm;
import org.kuali.student.enrollment.uif.form.KSUifMaintenanceDocumentForm;
import org.springframework.beans.factory.config.TypedStringValue;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
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
     *                    just set the value to empty.
     *                 2. The last item, e.g.Spring 2015 Course Offerings, is for the current screen. If hyperlink
     *                    is not expected, which is most of the case, just set the value to empty.
     *
     */
    public static <T extends KSUifForm> void constructBreadCrumbs(T form) {
        int mapIndex = 0;
        String JSONString = "";
        String breadCrumbItemUrl = "";
        String controllerPath = "";

        if (form.getPageId() != null && form.getBreadCrumbItemsMap()!=null &&
                form.getBreadCrumbItemsMap().get(form.getPageId()) != null && !form.getBreadCrumbItemsMap().get(form.getPageId()).isEmpty()) {
            for (Map.Entry<String, String> entry : form.getBreadCrumbItemsMap().get(form.getPageId()).entrySet()) {
                //if the map value is set for a breadcrumb item, construct the url for this item
                if (entry.getValue() != null && !entry.getValue().isEmpty()) {
                    Properties urlParameters = new Properties();

                    String[] breadCrumbMapValueElements = entry.getValue().split(":");
                    //If view id for a breadcrumb item is specified in XML config and it is different to the view id of
                    //current page, which means that the breadcrumb item links to a different view
                    if (breadCrumbMapValueElements.length == 2 && !breadCrumbMapValueElements[0].equals(form.getViewId())) {
                        urlParameters.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, UifConstants.MethodToCallNames.START);
                        urlParameters.put(UifParameters.VIEW_ID, breadCrumbMapValueElements[0]);
                        urlParameters.put(UifParameters.PAGE_ID, breadCrumbMapValueElements[1]);
                        urlParameters.put(UifConstants.UrlParams.SHOW_HOME, BooleanUtils.toStringTrueFalse(false));
                        urlParameters.put(UifConstants.UrlParams.FORM_KEY, form.getPreviousFormsMap().get(breadCrumbMapValueElements[0]).get(CourseOfferingConstants.BREADCRUMB_PREVIOUS_FORMKEY_KEY));
                        urlParameters.put(UifConstants.UrlParams.HISTORY, form.getPreviousFormsMap().get(breadCrumbMapValueElements[0]).get(CourseOfferingConstants.BREADCRUMB_PREVIOUS_FORM_HISTORY_KEY));
                        controllerPath = form.getPreviousFormsMap().get(breadCrumbMapValueElements[0]).get(CourseOfferingConstants.BREADCRUMB_PREVIOUS_CONTROLLER_PATH_KEY);
                    } else {
                        urlParameters.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, UifConstants.MethodToCallNames.START);
                        urlParameters.put(UifParameters.VIEW_ID, form.getViewId());
                        urlParameters.put(UifParameters.PAGE_ID, entry.getValue());
                        urlParameters.put(UifConstants.UrlParams.SHOW_HOME, BooleanUtils.toStringTrueFalse(false));
                        urlParameters.put(UifConstants.UrlParams.FORM_KEY, form.getFormKey());
                        if (form.getFormHistory() != null) {
                            HistoryEntry tempCurrent = form.getFormHistory().getCurrent();
                            form.getFormHistory().setCurrent(null);
                            urlParameters.put(UifConstants.UrlParams.HISTORY, form.getFormHistory().getHistoryParameterString());
                            //set the current back to form
                            form.getFormHistory().setCurrent(tempCurrent);
                        }

                        //strip the controller path from the form.formPostUrl
                        String[] formPostUrlElements = form.getFormPostUrl().split("/");
                        controllerPath = formPostUrlElements[formPostUrlElements.length - 1];

                    }

                    //contruct the url for this breadcrumb item
                    breadCrumbItemUrl = UrlFactory.parameterizeUrl(controllerPath, urlParameters);

                    if (mapIndex == 0) {
                        JSONString = "{\"" + CourseOfferingConstants.BREADCRUMB_JSON_ROOT_KEY + "\": {\"" + entry.getKey() + "\": \"" + breadCrumbItemUrl + "\",";
                    } else if (mapIndex == (form.getBreadCrumbItemsMap().get(form.getPageId()).entrySet().size() - 1)) {
                        JSONString += "\"" + entry.getKey() + "\":\"" + breadCrumbItemUrl + "\"}}";
                    } else {
                        JSONString += "\"" + entry.getKey() + "\":\"" + breadCrumbItemUrl + "\",";
                    }
                } else {
                    if (mapIndex == 0) {
                        if (form.getFormHistory() != null) {
                            breadCrumbItemUrl = form.getFormHistory().getHomewardPath().get(0).getUrl();
                        } else if (form.getHomeUrl()!=null && !form.getHomeUrl().isEmpty()){
                            breadCrumbItemUrl = form.getHomeUrl();
                        }

                        JSONString = "{\"" + CourseOfferingConstants.BREADCRUMB_JSON_ROOT_KEY + "\": {\"" + entry.getKey() + "\": \"" + breadCrumbItemUrl + "\",";
                    } else if (mapIndex == (form.getBreadCrumbItemsMap().get(form.getPageId()).entrySet().size() - 1)) {
                        breadCrumbItemUrl = "";
                        JSONString += "\"" + entry.getKey() + "\":\"" + breadCrumbItemUrl + "\"}}";
                    } else {
                        breadCrumbItemUrl = "";
                        JSONString += "\"" + entry.getKey() + "\":\"" + breadCrumbItemUrl + "\",";
                    }
                }

                mapIndex++;
            }
        }

        //set the generated breadcrumb JSON string to the form
        form.setBreadCrumbJSON(JSONString);
    }

    /**
     * This method is used to generate JSON string of the customized breadcrumb. The generated JSON string will
     * be set to KSUifMaintenanceDocumentForm.breadCrumbJSON, which will be used by jQuery to construct the customized breadcrumb to
     * replace the default KRAD one.
     *
     *
     * @param form     object of any form that extends KSUifMaintenanceDocumentForm, which extends MaintenanceDocumentForm.
     *                 Before passing the form to this method, KSUifMaintenanceDocumentForm.breadCrumbItemsMap needs to be initialized.
     *                 It is a LinkedHashMap with the key as the breadcrumb item title and the value as the KRAD pageId of the page that a user will
     *                 be directed to after clicking on the breadcrumb item. The entrySet needs to be pushed into the
     *                 breadCrumbItemsMap in the order that the breadcrumb items are displayed. e.g. the following
     *                 breadCrumbItemsMap is passed to the method.
     *
     */
    public static <T extends KSUifMaintenanceDocumentForm> void constructBreadCrumbs(T form) {
        int mapIndex = 0;
        String JSONString = "";
        String breadCrumbItemUrl = "";
        String controllerPath = "";

        if (form.getPageId() != null && form.getBreadCrumbItemsMap()!=null &&
                form.getBreadCrumbItemsMap().get(form.getPageId()) != null && !form.getBreadCrumbItemsMap().get(form.getPageId()).isEmpty()) {
            for (Map.Entry<String, String> entry : form.getBreadCrumbItemsMap().get(form.getPageId()).entrySet()) {
                //if the map value is set for a breadcrumb item, construct the url for this item
                if (entry.getValue() != null && !entry.getValue().isEmpty()) {
                    Properties urlParameters = new Properties();

                    String[] breadCrumbMapValueElements = entry.getValue().split(":");
                    //If view id for a breadcrumb item is specified in XML config and it is different to the view id of
                    //current page, which means that the breadcrumb item links to a different view
                    if (breadCrumbMapValueElements.length == 2 && !breadCrumbMapValueElements[0].equals(form.getViewId())) {
                        urlParameters.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, UifConstants.MethodToCallNames.START);
                        urlParameters.put(UifParameters.VIEW_ID, breadCrumbMapValueElements[0]);
                        urlParameters.put(UifParameters.PAGE_ID, breadCrumbMapValueElements[1]);
                        urlParameters.put(UifConstants.UrlParams.SHOW_HOME, BooleanUtils.toStringTrueFalse(false));
                        urlParameters.put(UifConstants.UrlParams.FORM_KEY, form.getPreviousFormsMap().get(breadCrumbMapValueElements[0]).get(CourseOfferingConstants.BREADCRUMB_PREVIOUS_FORMKEY_KEY));
                        urlParameters.put(UifConstants.UrlParams.HISTORY, form.getPreviousFormsMap().get(breadCrumbMapValueElements[0]).get(CourseOfferingConstants.BREADCRUMB_PREVIOUS_FORM_HISTORY_KEY));
                        controllerPath = form.getPreviousFormsMap().get(breadCrumbMapValueElements[0]).get(CourseOfferingConstants.BREADCRUMB_PREVIOUS_CONTROLLER_PATH_KEY);
                    } else {
                        urlParameters.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, UifConstants.MethodToCallNames.START);
                        urlParameters.put(UifParameters.VIEW_ID, form.getViewId());
                        urlParameters.put(UifParameters.PAGE_ID, entry.getValue());
                        urlParameters.put(UifConstants.UrlParams.SHOW_HOME, BooleanUtils.toStringTrueFalse(false));
                        urlParameters.put(UifConstants.UrlParams.FORM_KEY, form.getFormKey());
                        if (form.getFormHistory() != null) {
                            HistoryEntry tempCurrent = form.getFormHistory().getCurrent();
                            form.getFormHistory().setCurrent(null);
                            urlParameters.put(UifConstants.UrlParams.HISTORY, form.getFormHistory().getHistoryParameterString());
                            //set the current back to form
                            form.getFormHistory().setCurrent(tempCurrent);
                        }

                        //strip the controller path from the form.formPostUrl
                        String[] formPostUrlElements = form.getFormPostUrl().split("/");
                        controllerPath = formPostUrlElements[formPostUrlElements.length - 1];

                    }

                    //contruct the url for this breadcrumb item
                    breadCrumbItemUrl = UrlFactory.parameterizeUrl(controllerPath, urlParameters);

                    if (mapIndex == 0) {
                        JSONString = "{\"" + CourseOfferingConstants.BREADCRUMB_JSON_ROOT_KEY + "\": {\"" + entry.getKey() + "\": \"" + breadCrumbItemUrl + "\",";
                    } else if (mapIndex == (form.getBreadCrumbItemsMap().get(form.getPageId()).entrySet().size() - 1)) {
                        JSONString += "\"" + entry.getKey() + "\":\"" + breadCrumbItemUrl + "\"}}";
                    } else {
                        JSONString += "\"" + entry.getKey() + "\":\"" + breadCrumbItemUrl + "\",";
                    }
                } else {
                    if (mapIndex == 0) {
                        if (form.getFormHistory() != null) {
                            breadCrumbItemUrl = form.getFormHistory().getHomewardPath().get(0).getUrl();
                        } else if (form.getHomeUrl()!=null && !form.getHomeUrl().isEmpty()){
                            breadCrumbItemUrl = form.getHomeUrl();
                        }

                        JSONString = "{\"" + CourseOfferingConstants.BREADCRUMB_JSON_ROOT_KEY + "\": {\"" + entry.getKey() + "\": \"" + breadCrumbItemUrl + "\",";
                    } else if (mapIndex == (form.getBreadCrumbItemsMap().get(form.getPageId()).entrySet().size() - 1)) {
                        breadCrumbItemUrl = "";
                        JSONString += "\"" + entry.getKey() + "\":\"" + breadCrumbItemUrl + "\"}}";
                    } else {
                        breadCrumbItemUrl = "";
                        JSONString += "\"" + entry.getKey() + "\":\"" + breadCrumbItemUrl + "\",";
                    }
                }

                mapIndex++;
            }
        }

        //set the generated breadcrumb JSON string to the form
        form.setBreadCrumbJSON(JSONString);
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
        if (request.getParameter(CourseOfferingConstants.BREADCRUMB_PREVIOUS_CONTROLLER_PATH_KEY)!=null || request.getParameter(CourseOfferingConstants.BREADCRUMB_PREVIOUS_VIEW_ID_KEY)!=null
                || request.getParameter(CourseOfferingConstants.BREADCRUMB_PREVIOUS_HOME_URL_KEY)!=null || request.getParameter(CourseOfferingConstants.BREADCRUMB_PREVIOUS_FORM_HISTORY_KEY)!=null
                || request.getParameter(CourseOfferingConstants.BREADCRUMB_PREVIOUS_FORMKEY_KEY)!=null) {
            Map<String, Map<String, String>> previousFormsMap = new HashMap<String, Map<String, String>>();
            Map<String, String> tempFormMap = new HashMap<String, String>();
            if (request.getParameter(CourseOfferingConstants.BREADCRUMB_PREVIOUS_CONTROLLER_PATH_KEY)!=null &&
                !request.getParameter(CourseOfferingConstants.BREADCRUMB_PREVIOUS_CONTROLLER_PATH_KEY).isEmpty()) {
                tempFormMap.put(CourseOfferingConstants.BREADCRUMB_PREVIOUS_CONTROLLER_PATH_KEY, request.getParameter(CourseOfferingConstants.BREADCRUMB_PREVIOUS_CONTROLLER_PATH_KEY));
            }
            if (request.getParameter(CourseOfferingConstants.BREADCRUMB_PREVIOUS_VIEW_ID_KEY)!=null &&
                !request.getParameter(CourseOfferingConstants.BREADCRUMB_PREVIOUS_VIEW_ID_KEY).isEmpty()) {
                tempFormMap.put(CourseOfferingConstants.BREADCRUMB_PREVIOUS_VIEW_ID_KEY, request.getParameter(CourseOfferingConstants.BREADCRUMB_PREVIOUS_VIEW_ID_KEY));
            }
            if (request.getParameter(CourseOfferingConstants.BREADCRUMB_PREVIOUS_HOME_URL_KEY)!=null &&
                !request.getParameter(CourseOfferingConstants.BREADCRUMB_PREVIOUS_HOME_URL_KEY).isEmpty()) {
                tempFormMap.put(CourseOfferingConstants.BREADCRUMB_PREVIOUS_HOME_URL_KEY, request.getParameter(CourseOfferingConstants.BREADCRUMB_PREVIOUS_HOME_URL_KEY));
                form.setHomeUrl(request.getParameter(CourseOfferingConstants.BREADCRUMB_PREVIOUS_HOME_URL_KEY));
            }
            if (request.getParameter(CourseOfferingConstants.BREADCRUMB_PREVIOUS_FORM_HISTORY_KEY)!=null &&
                !request.getParameter(CourseOfferingConstants.BREADCRUMB_PREVIOUS_FORM_HISTORY_KEY).isEmpty()) {
                tempFormMap.put(CourseOfferingConstants.BREADCRUMB_PREVIOUS_FORM_HISTORY_KEY, request.getParameter(CourseOfferingConstants.BREADCRUMB_PREVIOUS_FORM_HISTORY_KEY));
            }
            if (request.getParameter(CourseOfferingConstants.BREADCRUMB_PREVIOUS_FORMKEY_KEY)!=null &&
                !request.getParameter(CourseOfferingConstants.BREADCRUMB_PREVIOUS_FORMKEY_KEY).isEmpty()) {
                tempFormMap.put(CourseOfferingConstants.BREADCRUMB_PREVIOUS_FORMKEY_KEY, request.getParameter(CourseOfferingConstants.BREADCRUMB_PREVIOUS_FORMKEY_KEY));
            }
            previousFormsMap.put(request.getParameter(CourseOfferingConstants.BREADCRUMB_PREVIOUS_VIEW_ID_KEY), tempFormMap);
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
        if (request.getParameter(CourseOfferingConstants.BREADCRUMB_PREVIOUS_CONTROLLER_PATH_KEY)!=null || request.getParameter(CourseOfferingConstants.BREADCRUMB_PREVIOUS_VIEW_ID_KEY)!=null
                || request.getParameter(CourseOfferingConstants.BREADCRUMB_PREVIOUS_HOME_URL_KEY)!=null || request.getParameter(CourseOfferingConstants.BREADCRUMB_PREVIOUS_FORM_HISTORY_KEY)!=null
                || request.getParameter(CourseOfferingConstants.BREADCRUMB_PREVIOUS_FORMKEY_KEY)!=null) {
            Map<String, Map<String, String>> previousFormsMap = new HashMap<String, Map<String, String>>();
            Map<String, String> tempFormMap = new HashMap<String, String>();
            if (request.getParameter(CourseOfferingConstants.BREADCRUMB_PREVIOUS_CONTROLLER_PATH_KEY)!=null &&
                    !request.getParameter(CourseOfferingConstants.BREADCRUMB_PREVIOUS_CONTROLLER_PATH_KEY).isEmpty()) {
                tempFormMap.put(CourseOfferingConstants.BREADCRUMB_PREVIOUS_CONTROLLER_PATH_KEY, request.getParameter(CourseOfferingConstants.BREADCRUMB_PREVIOUS_CONTROLLER_PATH_KEY));
            }
            if (request.getParameter(CourseOfferingConstants.BREADCRUMB_PREVIOUS_VIEW_ID_KEY)!=null &&
                    !request.getParameter(CourseOfferingConstants.BREADCRUMB_PREVIOUS_VIEW_ID_KEY).isEmpty()) {
                tempFormMap.put(CourseOfferingConstants.BREADCRUMB_PREVIOUS_VIEW_ID_KEY, request.getParameter(CourseOfferingConstants.BREADCRUMB_PREVIOUS_VIEW_ID_KEY));
            }
            if (request.getParameter(CourseOfferingConstants.BREADCRUMB_PREVIOUS_HOME_URL_KEY)!=null &&
                    !request.getParameter(CourseOfferingConstants.BREADCRUMB_PREVIOUS_HOME_URL_KEY).isEmpty()) {
                tempFormMap.put(CourseOfferingConstants.BREADCRUMB_PREVIOUS_HOME_URL_KEY, request.getParameter(CourseOfferingConstants.BREADCRUMB_PREVIOUS_HOME_URL_KEY));
                form.setHomeUrl(request.getParameter(CourseOfferingConstants.BREADCRUMB_PREVIOUS_HOME_URL_KEY));
            }
            if (request.getParameter(CourseOfferingConstants.BREADCRUMB_PREVIOUS_FORM_HISTORY_KEY)!=null &&
                    !request.getParameter(CourseOfferingConstants.BREADCRUMB_PREVIOUS_FORM_HISTORY_KEY).isEmpty()) {
                tempFormMap.put(CourseOfferingConstants.BREADCRUMB_PREVIOUS_FORM_HISTORY_KEY, request.getParameter(CourseOfferingConstants.BREADCRUMB_PREVIOUS_FORM_HISTORY_KEY));
            }
            if (request.getParameter(CourseOfferingConstants.BREADCRUMB_PREVIOUS_FORMKEY_KEY)!=null &&
                    !request.getParameter(CourseOfferingConstants.BREADCRUMB_PREVIOUS_FORMKEY_KEY).isEmpty()) {
                tempFormMap.put(CourseOfferingConstants.BREADCRUMB_PREVIOUS_FORMKEY_KEY, request.getParameter(CourseOfferingConstants.BREADCRUMB_PREVIOUS_FORMKEY_KEY));
            }
            previousFormsMap.put(request.getParameter(CourseOfferingConstants.BREADCRUMB_PREVIOUS_VIEW_ID_KEY), tempFormMap);
            form.setPreviousFormsMap(previousFormsMap);

        }

    }

    /**
     * This method sets the Url params used for customized breadcrumb generation before a redirection happens.
     * Params of previous view will be set to urlParameters so that the view that a user is redirected to can use
     * those params to construct breadcrumb items that link to the previous view
     *
     * @param urlParameters - instance of Properties which contains Url params for redirection.
     * @param form - any form that extends KSUifForm.
     *
     */
    public static <T extends KSUifForm> void setBreadcrumbRedirectUrlParams (Properties urlParameters, T form) {
        if (form.getFormPostUrl()!=null && !form.getFormPostUrl().isEmpty()) {
            //strip the controller path from the form.formPostUrl
            String[] formPostUrlElements = form.getFormPostUrl().split("/");
            urlParameters.put(CourseOfferingConstants.BREADCRUMB_PREVIOUS_CONTROLLER_PATH_KEY, formPostUrlElements[formPostUrlElements.length - 1]);
        }
        if (form.getViewId()!=null && !form.getViewId().isEmpty()) {
            urlParameters.put(CourseOfferingConstants.BREADCRUMB_PREVIOUS_VIEW_ID_KEY, form.getViewId());
        }
        if (form.getFormHistory()!=null) {
            urlParameters.put(CourseOfferingConstants.BREADCRUMB_PREVIOUS_HOME_URL_KEY, form.getFormHistory().getHomewardPath().get(0).getUrl());
            urlParameters.put(CourseOfferingConstants.BREADCRUMB_PREVIOUS_FORM_HISTORY_KEY, form.getFormHistory().getHistoryParameterString());
        }
        if (form.getFormKey()!=null && !form.getFormKey().isEmpty()) {
            urlParameters.put(CourseOfferingConstants.BREADCRUMB_PREVIOUS_FORMKEY_KEY, form.getFormKey());
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
