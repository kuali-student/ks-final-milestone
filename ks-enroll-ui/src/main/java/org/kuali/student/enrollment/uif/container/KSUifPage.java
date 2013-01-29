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
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class KSUifPage extends PageGroup {
    private Map<String, Map<String,String>> breadCrumbConfigMap;

    public KSUifPage() {
    }

    @Override
    public void performInitialization(View view, Object model) {
        super.performInitialization(view, model);
        KSUifForm form = (KSUifForm) model;
        if (form.getPageId()!=null && form.getBreadCrumbItemsMap()!=null
            && form.getBreadCrumbItemsMap().get(form.getPageId())!=null && !form.getBreadCrumbItemsMap().get(form.getPageId()).isEmpty()) {
            Map<String, String> breadCrumbItemsMapReplaced = new LinkedHashMap<String, String>(form.getBreadCrumbItemsMap().get(form.getPageId()).size());
            for (Map.Entry<String, String> entry : form.getBreadCrumbItemsMap().get(form.getPageId()).entrySet()) {
                if (hasExpression(entry.getKey())) {
                    String mapKeyEvaluated = (String) evaluateExpression(form, form.getPostedView().getContext(), entry.getKey());
                    breadCrumbItemsMapReplaced.put(mapKeyEvaluated, entry.getValue());
                } else {
                    breadCrumbItemsMapReplaced.put(entry.getKey(), entry.getValue());
                }
            }
            form.getBreadCrumbItemsMap().put(form.getPageId(), breadCrumbItemsMapReplaced);
            KSUifUtils.constructBreadCrumbs(form);
        }

    }

    @Override
    public void performApplyModel(View view, Object model, Component parent) {
        super.performApplyModel(view, model, parent);
        KSUifForm form = (KSUifForm) model;
        if (breadCrumbConfigMap!=null && !breadCrumbConfigMap.isEmpty()) {
            form.setBreadCrumbItemsMap(breadCrumbConfigMap);
        }
    }

    public Object evaluateExpression(Object contextObject, Map<String, Object> evaluationParameters,
                                     String expressionStr) {
        StandardEvaluationContext context = new StandardEvaluationContext(contextObject);
        context.setVariables(evaluationParameters);
        addCustomFunctions(context);

        // if expression contains placeholders remove before evaluating
        if (StringUtils.startsWith(expressionStr, UifConstants.EL_PLACEHOLDER_PREFIX) && StringUtils.endsWith(
                expressionStr, UifConstants.EL_PLACEHOLDER_SUFFIX)) {
            expressionStr = StringUtils.removeStart(expressionStr, UifConstants.EL_PLACEHOLDER_PREFIX);
            expressionStr = StringUtils.removeEnd(expressionStr, UifConstants.EL_PLACEHOLDER_SUFFIX);
        }

        ExpressionParser parser = new SpelExpressionParser();
        Object result = null;
        try {
            Expression expression = parser.parseExpression(expressionStr);

            result = expression.getValue(context);
        } catch (Exception e) {
            throw new RuntimeException("Exception evaluating expression: " + expressionStr, e);
        }

        return result;
    }

    protected void addCustomFunctions(StandardEvaluationContext context) {
        try {
            // TODO: possibly reflect ExpressionFunctions and add automatically
            context.registerFunction("isAssignableFrom", ExpressionFunctions.class.getDeclaredMethod("isAssignableFrom",
                    new Class[]{Class.class, Class.class}));
            context.registerFunction("empty", ExpressionFunctions.class.getDeclaredMethod("empty",
                    new Class[]{Object.class}));
            context.registerFunction("emptyList", ExpressionFunctions.class.getDeclaredMethod("emptyList",
                    new Class[]{List.class}));
            context.registerFunction("listContains", ExpressionFunctions.class.getDeclaredMethod("listContains",
                    new Class[]{List.class, Object[].class}));
            context.registerFunction("getName", ExpressionFunctions.class.getDeclaredMethod("getName",
                    new Class[]{Class.class}));
            context.registerFunction("getParm", ExpressionFunctions.class.getDeclaredMethod("getParm",
                    new Class[]{String.class, String.class, String.class}));
            context.registerFunction("getParmInd", ExpressionFunctions.class.getDeclaredMethod("getParmInd",
                    new Class[]{String.class, String.class, String.class}));
            context.registerFunction("hasPerm", ExpressionFunctions.class.getDeclaredMethod("hasPerm",
                    new Class[]{String.class, String.class}));
            context.registerFunction("hasPermDtls", ExpressionFunctions.class.getDeclaredMethod("hasPermDtls",
                    new Class[]{String.class, String.class, Map.class, Map.class}));
            context.registerFunction("hasPermTmpl", ExpressionFunctions.class.getDeclaredMethod("hasPermTmpl",
                    new Class[]{String.class, String.class, Map.class, Map.class}));
            context.registerFunction("sequence", ExpressionFunctions.class.getDeclaredMethod("sequence",
                    new Class[]{String.class}));
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Custom function for el expressions not found: " + e.getMessage(), e);
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


}
