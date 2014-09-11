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
 * Created by venkat on 9/10/14
 */
package org.kuali.student.cm.uif.modifier;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krad.uif.component.Component;
import org.kuali.rice.krad.uif.container.CollectionGroup;
import org.kuali.rice.krad.uif.field.DataField;
import org.kuali.rice.krad.uif.layout.GridLayoutManager;
import org.kuali.rice.krad.uif.layout.StackedLayoutManager;
import org.kuali.rice.krad.uif.lifecycle.ViewLifecycleUtils;
import org.kuali.rice.krad.uif.modifier.ComponentModifierBase;
import org.kuali.student.cm.course.form.ViewCourseForm;
import org.kuali.student.cm.course.form.renderHelpers.ActivityInfoCompareRenderHelper;
import org.kuali.student.cm.course.form.wrapper.ActivityInfoWrapper;
import org.kuali.student.cm.course.form.wrapper.FormatInfoWrapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Kuali Student Team
 */
public class CourseFormatCollectionModifier extends ComponentModifierBase {

    @Override
    public void performModification(Object model, Component component) {

        if ((component != null) && !(component instanceof CollectionGroup)) {
            throw new IllegalArgumentException(
                    "CourseFormatCollectionModifier only support CollectionGroup components, found type: " + component.getClass());
        }

        if (component == null) {
            return;
        }

        CollectionGroup group = (CollectionGroup)component;

        if (!(((StackedLayoutManager)group.getLayoutManager()).getLineGroupPrototype().getLayoutManager() instanceof GridLayoutManager)) {
            throw new RuntimeException("This modifier suppports only Grid Layout manager prototype.");
        }

        List<DataField> itemFields = ViewLifecycleUtils.getElementsOfTypeDeep(group, DataField.class);

        List<String> hiddenProperties = new ArrayList<>();
        hiddenProperties.add("renderHelper.hightlightRow");

        for (DataField field : itemFields) {
            field.setAdditionalHiddenPropertyNames(hiddenProperties);
        }

        ViewCourseForm form = (ViewCourseForm)model;

        List<FormatInfoWrapper> courseFormats = form.getCourseInfoWrapper().getReviewProposalDisplay().getCourseLogisticsSection().getFormatInfoWrappers();
        List<FormatInfoWrapper> compareFormats = form.getCompareCourseInfoWrapper().getReviewProposalDisplay().getCourseLogisticsSection().getFormatInfoWrappers();

        int formatIndex = 0;
        for (FormatInfoWrapper format : courseFormats) {
            int activityIndex = 0;
            for (ActivityInfoWrapper activity : format.getActivities()){
                boolean hightlightRow = false;
                ActivityInfoCompareRenderHelper renderHelper = new ActivityInfoCompareRenderHelper();
                ActivityInfoWrapper compareActivity = getActivityInfo(compareFormats,formatIndex,activityIndex);
                if (!compareActivity(activity,compareActivity)){
                    hightlightRow = true;
                }
                activityIndex++;

                renderHelper.setHightlightRow(hightlightRow);
                activity.setRenderHelper(renderHelper);
            }

            formatIndex++;
        }

        //This document load on client look for the highlight=true hidden fields and adds the highlighter css to it's closest tr
        group.setOnDocumentReadyScript("jQuery(\"input[name$='renderHelper.hightlightRow'][value=true]\").closest(\"tr\").addClass(\"cm-compare-highlighter\");");

    }

    protected ActivityInfoWrapper getActivityInfo(List<FormatInfoWrapper> formats,int formatIndex, int activityIndex) {

        if (formats.isEmpty() || formats.size() <= formatIndex){
            return null;
        }

        FormatInfoWrapper format = formats.get(formatIndex);

        if (format.getActivities().isEmpty() || format.getActivities().size() <= activityIndex) {
            return null;
        }
        return format.getActivities().get(activityIndex);
    }

    protected boolean compareActivity(ActivityInfoWrapper activity1, ActivityInfoWrapper activity2){
        if (activity1 == null || activity2 == null){
            return false;
        }
        if (StringUtils.equals(activity1.getContactHours(),activity2.getContactHours()) &&
            StringUtils.equals(activity1.getDurationCount(),activity2.getDurationCount()) &&
            StringUtils.equals(activity1.getAnticipatedClassSize().toString(),activity2.getAnticipatedClassSize().toString())){
            return true;
        }
        return false;
    }

    @Override
    public Set<Class<? extends Component>> getSupportedComponents() {
        Set<Class<? extends Component>> components = new HashSet<Class<? extends Component>>();
        components.add(CollectionGroup.class);

        return components;
    }

}
