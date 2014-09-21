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
package org.kuali.student.cm.course.modifiers;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krad.uif.component.Component;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;
import org.kuali.student.cm.course.form.ViewCourseForm;
import org.kuali.student.cm.course.form.wrapper.ActivityInfoWrapper;
import org.kuali.student.cm.course.form.wrapper.CourseInfoWrapper;
import org.kuali.student.cm.course.form.wrapper.FormatInfoWrapper;

import java.util.List;

/**
 *
 * @author Kuali Student Team
 */
public class ViewCourseFormatCollectionModifier extends ViewCourseCollectionModifierBase {

    @Override
    public void performCollectionCompare(Object model, Component component) {

        List<FormatInfoWrapper> courseFormats;
        List<FormatInfoWrapper> compareFormats;

        if (model instanceof ViewCourseForm){
            ViewCourseForm form = (ViewCourseForm)model;
            courseFormats = form.getCourseInfoWrapper().getReviewProposalDisplay().getCourseLogisticsSection().getFormatInfoWrappers();
            compareFormats = form.getCompareCourseInfoWrapper().getReviewProposalDisplay().getCourseLogisticsSection().getFormatInfoWrappers();
        } else if (model instanceof MaintenanceDocumentForm){
            MaintenanceDocumentForm form = (MaintenanceDocumentForm)model;
            courseFormats = ((CourseInfoWrapper)form.getDocument().getNewMaintainableObject().getDataObject()).getReviewProposalDisplay().getCourseLogisticsSection().getFormatInfoWrappers();
            compareFormats = ((CourseInfoWrapper)form.getDocument().getOldMaintainableObject().getDataObject()).getReviewProposalDisplay().getCourseLogisticsSection().getFormatInfoWrappers();
        } else {
            throw new RuntimeException("Does not support");
        }



        int formatIndex = 0;
        for (FormatInfoWrapper format : courseFormats) {
            int activityIndex = 0;
            for (ActivityInfoWrapper activity : format.getActivities()){
                ActivityInfoWrapper compareActivity = getActivityInfo(compareFormats,formatIndex,activityIndex);
                if (!compareActivity(activity,compareActivity)){
                    activity.setHightlightRow(true);
                    if (compareActivity != null){
                        compareActivity.setHightlightRow(true);
                    }
                }
                activityIndex++;

            }

            if (compareFormats.get(formatIndex).getActivities().size() > format.getActivities().size()){
                while (compareFormats.get(formatIndex).getActivities().size() > activityIndex) {
                    compareFormats.get(formatIndex).getActivities().get(activityIndex).setHightlightRow(true);
                    activityIndex++;
                }
            }

            formatIndex++;
        }

        if (compareFormats.size() > courseFormats.size()) {
            while (compareFormats.size() > formatIndex) {
                for (ActivityInfoWrapper activity : compareFormats.get(formatIndex).getActivities()){
                    activity.setHightlightRow(true);
                }
                formatIndex++;
            }
        }
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



}
