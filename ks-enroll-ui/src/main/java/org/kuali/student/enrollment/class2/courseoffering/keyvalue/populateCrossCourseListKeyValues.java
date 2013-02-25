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
 * Created by chongzhu on 1/28/13
 */
package org.kuali.student.enrollment.class2.courseoffering.keyvalue;

import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingWrapper;
import org.kuali.student.r2.lum.course.dto.CourseCrossListingInfo;
import org.kuali.student.r2.lum.course.dto.CourseInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Kuali Student Team
 */
public class populateCrossCourseListKeyValues extends UifKeyValuesFinderBase implements Serializable {

    @Override
    public List<KeyValue> getKeyValues(ViewModel model) {
        MaintenanceDocumentForm coForm = (MaintenanceDocumentForm) model;
        CourseOfferingWrapper wrapper = (CourseOfferingWrapper)coForm.getDocument().getNewMaintainableObject().getDataObject();

        List<KeyValue> keyValues = new ArrayList<KeyValue>();
        CourseInfo courseOffering = wrapper.getCourse();
        if (courseOffering != null && courseOffering.getCrossListings() != null && courseOffering.getCrossListings().size() > 0) {

            // Always include an option for Course
            for (CourseCrossListingInfo courseInfo : courseOffering.getCrossListings()) {
                if(courseInfo.getCode() != null)   {
                    keyValues.add(new ConcreteKeyValue(courseInfo.getCode(), courseInfo.getCode()));
                }
            }
        }

        return keyValues;
    }
}
