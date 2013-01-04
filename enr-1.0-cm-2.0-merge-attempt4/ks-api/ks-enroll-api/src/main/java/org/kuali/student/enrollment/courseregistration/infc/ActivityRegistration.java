/*
 * Copyright 2011 The Kuali Foundation 
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

package org.kuali.student.enrollment.courseregistration.infc;

import org.kuali.student.r2.common.infc.Relationship;

/**
 * An ActivityRegistration represents a single ActivityOffering within
 * the CourseOffering for which the student
 * registered. ActivityRegistrations are created as a result of a
 * student registration (submisison of a RegistrationRequest) to a
 * RegistrationGroup.
 *
 * An ActivityRegistration will be generated for every
 * ActivityOffering defined in the RegistrationGroup.
 * 
 * @author Kuali Student Team
 */

public interface ActivityRegistration 
    extends Relationship {

    /**
     * The person Id for this registration.
     * 
     * @name Student Id
     * @readOnly
     * @required
     * @impl Lpr.personId
     */
    public String getStudentId();

    /**
     * The ActivityOffering Id.
     * 
     * @name ActivityOffering Id
     * @readOnly
     * @required
     * @impl Lpr.luiId
     */
    public String getActivityOfferingId();
}
