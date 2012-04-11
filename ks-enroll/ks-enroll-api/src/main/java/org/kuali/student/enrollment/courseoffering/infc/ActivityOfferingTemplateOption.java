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

/**
 * This is a single option ofor an ActivityOfferingTemplate. 
 * It's also a placeholder for other activity offering constraint
 * and generation data that might be associated with a single
 * activity offering type.
 * 
 * @author tom
 */

public interface ActivityOfferingTemplateOption {

    /**
     * The activity offering type.
     *
     * @name Activity Offering Type Key
     * @required
     */
    public String getActivityOfferingTypeKey();
}
