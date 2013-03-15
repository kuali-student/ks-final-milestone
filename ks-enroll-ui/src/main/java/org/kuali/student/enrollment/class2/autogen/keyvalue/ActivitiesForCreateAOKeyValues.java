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

package org.kuali.student.enrollment.class2.autogen.keyvalue;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.student.enrollment.class2.autogen.form.ARGCourseOfferingManagementForm;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingResourceLoader;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.lum.course.dto.ActivityInfo;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.dto.FormatInfo;
import org.kuali.student.r2.lum.course.service.CourseService;

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
        ARGCourseOfferingManagementForm coForm = (ARGCourseOfferingManagementForm) model;

        List<KeyValue> keyValues = new ArrayList<KeyValue>();

        keyValues.add(new ConcreteKeyValue("", "Select Activity Type"));
        String formatId = coForm.getFormatIdForNewAO();
        String courseId = coForm.getCurrentCourseOfferingWrapper().getCourseOfferingInfo().getCourseId();

        if(!StringUtils.isEmpty(formatId)) {
            try {
                CourseInfo course = getCourseService().getCourse(courseId, ContextUtils.getContextInfo());
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

                ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();

                List<ActivityInfo> activityInfos = foundFormat.getActivities();
                for (ActivityInfo activityInfo : activityInfos) {
                    TypeInfo activityType = getTypeService().getType(activityInfo.getTypeKey(), contextInfo);
                    keyValues.add(new ConcreteKeyValue(activityInfo.getId(), activityType.getName()));
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return keyValues;
    }

    protected CourseService getCourseService() {
        return CourseOfferingResourceLoader.loadCourseService();
    }

    protected TypeService getTypeService(){
        return CourseOfferingResourceLoader.loadTypeService();
    }
}
