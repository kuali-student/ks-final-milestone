/**
 * Copyright 2012 The Kuali Foundation Licensed under the
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
 * Created by Charles on 6/28/12
 */
package org.kuali.student.enrollment.courseoffering.dto;

import org.kuali.student.enrollment.courseoffering.infc.CourseOffering;
import org.kuali.student.enrollment.courseoffering.infc.HasPropertyMap;

import java.util.HashMap;
import java.util.Map;

/**
 * Allows other things to be returned beside CourseOfferingInfo.
 *
 * @author Kuali Student Team
 */
public class CourseOfferingInfoExtended extends CourseOfferingInfo implements HasPropertyMap {
    public static final String ACTIVITY_OFFERINGS_CREATED = "activityOfferingsCreated";
    private Map<String, Object> properties = new HashMap<String, Object>();

    public CourseOfferingInfoExtended(CourseOffering courseOffering) {
        super(courseOffering);
    }

    @Override
    public Map<String, Object> getProperties() {
        return properties;
    }
}
