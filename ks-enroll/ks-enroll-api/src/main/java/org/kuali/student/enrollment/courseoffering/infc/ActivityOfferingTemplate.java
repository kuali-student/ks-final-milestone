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
 * This template is to assist in the constraining and generation of
 * Activity Offerings. Presently, it only constrains the allowable
 * combinations of activity offering types.
 * 
 * @author tom
 */

public interface ActivityOfferingTemplate {

    /**
     * Gets the course offering Id to which this template applies.
     *
     * @name Course Offering Id
     */
    public String getCourseOfferingId();

    /**
     * List of activity offering options.
     *
     * @name Activity Offering Template Options
     */
    public List<? extends ActivityOfferingTemplateOption> getActivityOfferingTemplateOptions();
}
