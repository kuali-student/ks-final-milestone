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

import org.kuali.rice.krad.uif.component.Component;
import org.kuali.rice.krad.uif.container.Group;
import org.kuali.rice.krad.uif.container.PageGroup;
import org.kuali.rice.krad.uif.view.FormView;
import org.kuali.rice.krad.uif.view.View;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.enrollment.class2.courseoffering.form.CourseOfferingRolloverManagementForm;
import org.kuali.student.enrollment.uif.form.KSUifForm;
import org.kuali.student.enrollment.uif.util.KSUifUtils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class KSUifPage extends PageGroup {
    private Map<String,String> breadCrumbItems;
    private Map<String,String> mapKeyExpressionFeeder;

    public KSUifPage() {
    }

    @Override
    public void performInitialization(View view, Object model) {
        super.performInitialization(view, model);
        if (breadCrumbItems!=null && !breadCrumbItems.isEmpty()) {
            KSUifForm form = (KSUifForm) model;
            LinkedHashMap<String,String> breadCrumbItemsSorted = new LinkedHashMap<String, String>(breadCrumbItems);

            if (mapKeyExpressionFeeder!=null && !mapKeyExpressionFeeder.isEmpty()) {
                LinkedHashMap<String,String> breadCrumbItemsKeyReplaced = new LinkedHashMap<String, String>(breadCrumbItemsSorted.size());
                for (Map.Entry<String, String> entry : breadCrumbItemsSorted.entrySet()) {
                    for (Map.Entry<String, String> entryBreadCrumb : mapKeyExpressionFeeder.entrySet()) {
                        if (entry.getKey().equals(entryBreadCrumb.getKey())) {
                            breadCrumbItemsKeyReplaced.put(entryBreadCrumb.getValue(),entry.getValue());
                        } else {
                            breadCrumbItemsKeyReplaced.put(entry.getKey(),entry.getValue());
                        }
                    }
                }
                form.setBreadCrumbItemsMap(breadCrumbItemsKeyReplaced);
            } else {
                form.setBreadCrumbItemsMap(breadCrumbItemsSorted);
            }

            KSUifUtils.constructBreadCrumbs(form);
        }

    }


    public Map<String, String> getBreadCrumbItems() {
        return breadCrumbItems;
    }

    public void setBreadCrumbItems(Map<String, String> breadCrumbItems) {
        this.breadCrumbItems = breadCrumbItems;
    }

    public Map<String, String> getMapKeyExpressionFeeder() {
        return mapKeyExpressionFeeder;
    }

    public void setMapKeyExpressionFeeder(Map<String, String> mapKeyExpressionFeeder) {
        this.mapKeyExpressionFeeder = mapKeyExpressionFeeder;
    }
}
