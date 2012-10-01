/**
 * Copyright 2011 The Kuali Foundation Licensed under the
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
 */

package org.kuali.student.enrollment.class2.courseoffering.keyvalue;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.student.enrollment.class2.courseoffering.form.CourseOfferingManagementForm;
import org.kuali.student.enrollment.class2.courseoffering.service.impl.CourseOfferingManagementViewHelperServiceImpl;
import org.kuali.student.lum.course.dto.ActivityInfo;
import org.kuali.student.lum.course.dto.CourseInfo;
import org.kuali.student.lum.course.dto.FormatInfo;
import org.kuali.student.r2.core.type.dto.TypeInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class retrieves Activities based on the selection of a Format, and returns a key-value pair list of
 * the Activity Type name and the Activity Id
 *
 * @author andrewlubbers
 *
 */
public class ActivitiesForCreateAOKeyValues extends UifKeyValuesFinderBase implements Serializable {


    @Override
    public List<KeyValue> getKeyValues(ViewModel model) {
        CourseOfferingManagementForm coForm = (CourseOfferingManagementForm) model;
        CourseOfferingManagementViewHelperServiceImpl helperService = ((CourseOfferingManagementViewHelperServiceImpl)coForm.getPostedView().getViewHelperService());

        List<KeyValue> keyValues = new ArrayList<KeyValue>();

        keyValues.add(new ConcreteKeyValue("", "Select Activity Type"));
        String formatId = coForm.getFormatIdForNewAO();
        String courseId = coForm.getTheCourseOffering().getCourseId();

        if(!StringUtils.isEmpty(formatId)) {
            try {
                CourseInfo course = helperService.getCourseService().getCourse(courseId);
                FormatInfo foundFormat = null;
                for (FormatInfo info : course.getFormats()) {
                    if (info.getId().equals(formatId)) {
                        foundFormat = info;
                        break;
                    }
                }

                if(foundFormat == null) {
                    throw new RuntimeException("No FormatInfo found with id " + formatId + " in course " + courseId);
                }

                List<ActivityInfo> activityInfos = foundFormat.getActivities();
                for (ActivityInfo activityInfo : activityInfos) {
                    TypeInfo activityType = helperService.getTypeService().getType(activityInfo.getActivityType(), helperService.getContextInfo());
                    keyValues.add(new ConcreteKeyValue(activityInfo.getId(), activityType.getName()));
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return keyValues;
    }
}
