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

import java.util.List;

public interface RegistrationResponse {

    /**
     * The Registration Request Id.
     * 
     * @name Registration Request Id
     * @readOnly
     * @required
     */
    public String getRegistrationRequestId();

    /**
     * The registration submission error status. 
     *
     * If false, the registration was successful resulting in a
     * CourseRegistration or a Waitlist entry. 
     *
     * If the registration submission failed, and no
     * CourseRegistrations or Waitlist entries resulted from the
     * transaction.
     *
     * @name Has Failed Status
     */
    public Boolean getHasFailed();

    /**
     * The registration submission messages. 
     *
     * Returns a list of status messages, typically in failure, from a
     * registration submission.
     *
     * @name Registration Messages
     */
    public List<String> getMessages();

    /**
     * The individual registration response items.
     * 
     * @name Registration Response Items
     */
    public List<? extends RegistrationResponseItem> getRegistrationResponseItems();
}
