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

import org.kuali.rice.krad.uif.component.Component;
import org.kuali.rice.krad.uif.service.ExpressionEvaluatorService;
import org.kuali.rice.krad.uif.view.InquiryView;
import org.kuali.rice.krad.uif.view.View;
import org.kuali.student.enrollment.uif.form.KSUifForm;

import java.util.Map;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class KSInquiryView extends InquiryView {
    private Map<String, Map<String,String>> breadCrumbConfigMap;

    public KSInquiryView() {

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

    public Map<String, Map<String,String>> getBreadCrumbConfigMap() {
        return breadCrumbConfigMap;
    }

    public void setBreadCrumbConfigMap(Map<String, Map<String,String>> breadCrumbConfigMap) {
        this.breadCrumbConfigMap = breadCrumbConfigMap;
    }
}
