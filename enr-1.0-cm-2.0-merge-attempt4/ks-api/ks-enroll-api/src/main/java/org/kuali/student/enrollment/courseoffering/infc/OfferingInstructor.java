/*
 * Copyright 2010 The Kuali Foundation 
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
 
import org.kuali.student.r2.common.infc.HasId;
import org.kuali.student.r2.common.infc.TypeStateEntity;

/**
 * Information about a potential instructor for a LUI (learning unit
 * instance). These are built from Lui Person Relations with
 * instructor type
 */ 

public interface OfferingInstructor 
    extends TypeStateEntity, HasId {
        
    /**
     * Unique identifier for a person record.
     *
     * @name Person Id
     * @impl lpr.personId
     */
    public String getPersonId();
    
    /**
     * Percentage involvement of the instructor in the activity.
     *
     * @name Percentage Effort
     * @impl maps to lpr.commitmentPercent
     */
    public Float getPercentageEffort();

    /**
     * Name of the instructor.
     *
     * @name Person Name
     * @impl Get from Person Service
     * @readOnly
     */
    public String getPersonName();
}
