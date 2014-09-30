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
 * Created by Charles on 9/24/2014
 */
package org.kuali.student.enrollment.coursewaitlist2.infc;

import org.kuali.student.r2.common.infc.HasEffectiveDates;
import org.kuali.student.r2.common.infc.IdNamelessEntity;

import java.util.List;

/**
 * Interface represents state/maxSize for AO waitlist config.
 *
 * @author Kuali Student Team
 */
public interface ActivityOfferingWaitListConfig extends IdNamelessEntity, HasEffectiveDates {

    /**
     * A unique identifier for the state of this object.
     * This value should not be changed directly.
     * Instead, it should be set using the appropriate change state method in service.
     *
     * @name State Key
     * @readOnly
     * @required
     */
    @Override
    String getStateKey();

    /**
     * The format offering Ids associated with this CourseWaitList.
     * @name Format Offering Ids
     */
    List<String> getFormatOfferingIds();

    /**
     * The activity offering Ids associated with this CourseWaitList.
     * @name Activity Offering Ids
     */
    List<String> getActivityOfferingIds();

    /**
     * The maximum size of this CourseWaitList.  This value will always be positive.
     * Zero represents an unlimited size.
     * @name Max Size
     */
    Integer getMaxSize();
}
