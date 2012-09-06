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

import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.rice.krad.web.form.InquiryForm;
import org.kuali.rice.krad.web.form.MaintenanceForm;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingCreateWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingEditWrapper;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingResourceLoader;
import org.kuali.student.enrollment.class2.courseoffering.util.ViewHelperUtil;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.lum.course.dto.CourseInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Build key-value pairs for the Grade Roster Level dropdown when creating Format Offerings
 *
 * @author andrewlubbers
 */
public class GradeRosterLevelKeyValues extends UifKeyValuesFinderBase implements Serializable {

    private static TypeService typeService;

    @Override
    public List<KeyValue> getKeyValues(ViewModel model) {
        Object dataObject = null;
        if (model instanceof MaintenanceForm) {
            MaintenanceForm form = (MaintenanceForm)model;
            dataObject = form.getDocument().getNewMaintainableObject().getDataObject();
        } else if (model instanceof InquiryForm) {
            InquiryForm form = (InquiryForm)model;
            dataObject = form.getDataObject();
        }

        CourseInfo course;

        if(dataObject instanceof CourseOfferingCreateWrapper) {
            CourseOfferingCreateWrapper cWrapper = (CourseOfferingCreateWrapper) dataObject;
            course = cWrapper.getCourse();
        }
        else if (dataObject instanceof CourseOfferingEditWrapper) {
            CourseOfferingEditWrapper cWrapper = (CourseOfferingEditWrapper) dataObject;
            course = cWrapper.getCourse();
        }
        else {
            return Collections.emptyList();
        }

        List<KeyValue> results = new ArrayList<KeyValue>();

        // Always include an option for Course
        results.add(new ConcreteKeyValue(LuiServiceConstants.COURSE_OFFERING_TYPE_KEY, CourseOfferingConstants.FORMAT_OFFERING_GRADE_ROSTER_LEVEL_COURSE_DISPLAY));

        results.addAll(ViewHelperUtil.collectActivityTypeKeyValues(course, getTypeService(), ContextUtils.createDefaultContextInfo()));

        return results;
    }



    private static TypeService getTypeService() {
        if(typeService == null) {
            typeService = CourseOfferingResourceLoader.loadTypeService();
        }

        return typeService;
    }
}
