/*
 * Copyright 2012 The Kuali Foundation 
 *
 * Licensed under the Educational Community License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.enrollment.courseoffering.infc;

import java.util.List;

/**
 * The Registration Template lists the allowable combinations of
 * activity offerings to constrain or generate RegistrationGroups.
 *
 * @author tom
 */

public interface RegistrationGroupTemplate {

    /**
     * Gets the course offering Id to which this template applies.
     *
     * @name Course Offering Id
     */
    public String getCourseOfferingId();

    /**
     * Gets the activity offering Id combinations. Each list within
     * this list contains a list of Activity Offering Ids.
     *
     * This rule says that a RegistrationGroup comprising of a single
     * activity offering Id from each and every list within the
     * activity offering cominations should be created.
     *
     * @name Activity Offering combinations
     */
    public List<List<? extends String>> getActivityOfferingCombinations();
}
