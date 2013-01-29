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
 * Created by David Yin on 1/23/13
 */
package org.kuali.student.enrollment.uif.container;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.uif.component.Component;
import org.kuali.rice.krad.uif.container.Group;
import org.kuali.rice.krad.uif.container.PageGroup;
import org.kuali.rice.krad.uif.service.ExpressionEvaluatorService;
import org.kuali.rice.krad.uif.service.ViewService;
import org.kuali.rice.krad.uif.util.ExpressionFunctions;
import org.kuali.rice.krad.uif.view.FormView;
import org.kuali.rice.krad.uif.view.View;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.enrollment.class2.courseoffering.form.CourseOfferingRolloverManagementForm;
import org.kuali.student.enrollment.uif.form.KSUifForm;
import org.kuali.student.enrollment.uif.util.KSUifUtils;
import org.springframework.beans.factory.config.TypedStringValue;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * KS page class that extends PageGroup to perform the customized breadcrumb generation.
 *
 * @author Kuali Student Team
 */
public class KSUifPage extends PageGroup {
    private Map<String, Map<String,String>> breadCrumbConfigMap;
    private transient ExpressionEvaluatorService expressionEvaluatorService;

    public KSUifPage() {
    }

    /**
     * During the page initialization, the overriden method firstly evaluates if there is
     * a customized breadcrumb configured for the current page by checking the form property
     * breadCrumbItemsMap. If there is, it iterates the breadCrumbItemsMap to evaluate any
     * expressions in the map key by calling the rice service. And then it calls the utility
     * method to generate the breadcrumb JSON string used to rewrite the default KRAD breadcrumb
     * for the current page.
     *
     *
     * @param view - view instance being rendered
     * @param model - Object containing the view data
     */
    @Override
    public void performInitialization(View view, Object model) {
        super.performInitialization(view, model);
        KSUifForm form = (KSUifForm) model;

        //If the form property breadCrumbItemsMap is set (there is a customized breadcrumb for the current page),
        //construct the breadcrumb JSON string
        if (form.getPageId()!=null && form.getBreadCrumbItemsMap()!=null
            && form.getBreadCrumbItemsMap().get(form.getPageId())!=null && !form.getBreadCrumbItemsMap().get(form.getPageId()).isEmpty()) {
            Map<String, String> breadCrumbItemsMapReplaced = new LinkedHashMap<String, String>(form.getBreadCrumbItemsMap().get(form.getPageId()).size());

            // Iterates the breadcrumb map for the current page and evaluate any expressions in map keys
            for (Map.Entry<String, String> entry : form.getBreadCrumbItemsMap().get(form.getPageId()).entrySet()) {
                if (hasExpression(entry.getKey())) {
                    String mapKeyEvaluated = (String)getExpressionEvaluatorService().evaluateExpression(form, form.getPostedView().getContext(), entry.getKey());
                    breadCrumbItemsMapReplaced.put(mapKeyEvaluated, entry.getValue());
                } else {
                    breadCrumbItemsMapReplaced.put(entry.getKey(), entry.getValue());
                }
            }
            form.getBreadCrumbItemsMap().put(form.getPageId(), breadCrumbItemsMapReplaced);

            //generate the breadcrumb JSON string for the current page.
            KSUifUtils.constructBreadCrumbs(form);
        }

    }

    /**
     * During the phase of applying model to page, the overriden method firstly evaluates if
     * page property breadCrumbConfigMap is set by the XML configuration. If it is, it means
     * this is the entry page of the view and the method will set breadCrumbConfigMap to the
     * form property breadCrumbItemsMap, which will be used for breadcrumb JSON string generation.
     *
     * @param view - view instance being rendered
     * @param model - Object containing the view data
     * @param parent - parent component
     */
    @Override
    public void performApplyModel(View view, Object model, Component parent) {
        super.performApplyModel(view, model, parent);

        //if the breadCrumbConfigMap property is set by XML configuration, set this map
        //to the form property breadCrumbItemsMap, which will hold breadcrumb item map
        //for all the pages that has customized breadcrumbs configured in the current view
        if (breadCrumbConfigMap!=null && !breadCrumbConfigMap.isEmpty()) {
            KSUifForm form = (KSUifForm) model;
            form.setBreadCrumbItemsMap(breadCrumbConfigMap);
        }
    }

    /**
     * Checks whether the given property value is of String type, and if so whether it contains the expression
     * placholder(s)
     *
     * @param propertyValue - value to check for expressions
     * @return boolean true if the property value contains expression(s), false if it does not
     */
    protected boolean hasExpression(Object propertyValue) {
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
    protected String getStringValue(Object value) {
        if (value instanceof TypedStringValue) {
            TypedStringValue typedStringValue = (TypedStringValue) value;
            return typedStringValue.getValue();
        } else if (value instanceof String) {
            return (String) value;
        }

        return null;
    }

    public Map<String, Map<String,String>> getBreadCrumbConfigMap() {
        return breadCrumbConfigMap;
    }

    public void setBreadCrumbConfigMap(Map<String, Map<String,String>> breadCrumbConfigMap) {
        this.breadCrumbConfigMap = breadCrumbConfigMap;
    }

    protected ExpressionEvaluatorService getExpressionEvaluatorService() {
        if (this.expressionEvaluatorService == null) {
            this.expressionEvaluatorService = KRADServiceLocatorWeb.getExpressionEvaluatorService();
        }

        return this.expressionEvaluatorService;
    }



}
