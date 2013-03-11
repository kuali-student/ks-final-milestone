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
 * Created by David Yin on 1/31/13
 */
package org.kuali.student.enrollment.uif.view;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.uif.component.Component;
import org.kuali.rice.krad.uif.service.ExpressionEvaluatorService;
import org.kuali.rice.krad.uif.view.FormView;
import org.kuali.rice.krad.uif.view.View;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.enrollment.uif.form.KSUifForm;
import org.kuali.student.enrollment.uif.form.KSUifMaintenanceDocumentForm;
import org.kuali.student.enrollment.uif.util.KSUifUtils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * This class extends FormView and can be used to hold any properties that can be
 * shared among all the forms that extend it.
 *
 * @author Kuali Student Team
 */
public class KSUifFormView extends FormView {
    private Map<String, Map<String,String>> breadCrumbConfigMap;
    private transient ExpressionEvaluatorService expressionEvaluatorService;

    public KSUifFormView() {

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
        KSUifForm form = (KSUifForm) model;

        //if the breadCrumbConfigMap property is set by XML configuration, set this map
        //to the form property breadCrumbItemsMap, which will hold breadcrumb item map
        //for all the pages that has customized breadcrumbs configured in the current view
        if (breadCrumbConfigMap != null && !breadCrumbConfigMap.isEmpty()) {
            form.setBreadCrumbItemsMap(breadCrumbConfigMap);

            //If the form property breadCrumbItemsMap is set (there is a customized breadcrumb for the current page),
            //construct the breadcrumb JSON string
            if (form.getPageId() != null && form.getBreadCrumbItemsMap() != null && form.getBreadCrumbItemsMap().get(form.getPageId()) != null
                    && !form.getBreadCrumbItemsMap().get(form.getPageId()).isEmpty()) {
                Map<String, String> breadCrumbItemsMapReplaced = new LinkedHashMap<String, String>(form.getBreadCrumbItemsMap().get(form.getPageId()).size());

                // Iterates the breadcrumb map for the current page and evaluate any expressions in map keys
                for (Map.Entry<String, String> entry : form.getBreadCrumbItemsMap().get(form.getPageId()).entrySet()) {
                    if (KSUifUtils.hasExpression(entry.getKey())) {
                        String mapKeyEvaluated = "";
                        if (StringUtils.startsWith(entry.getKey(), UifConstants.EL_PLACEHOLDER_PREFIX) && StringUtils.endsWith(
                                entry.getKey(), UifConstants.EL_PLACEHOLDER_SUFFIX) && (StringUtils.countMatches(
                                entry.getKey(), UifConstants.EL_PLACEHOLDER_PREFIX) == 1)) {
                            mapKeyEvaluated = (String) getExpressionEvaluatorService().evaluateExpression(form, form.getView().getContext(), entry.getKey());
                        } else {
                            // treat as string template
                            mapKeyEvaluated = getExpressionEvaluatorService().evaluateExpressionTemplate(form, form.getView().getContext(), entry.getKey());
                        }
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
