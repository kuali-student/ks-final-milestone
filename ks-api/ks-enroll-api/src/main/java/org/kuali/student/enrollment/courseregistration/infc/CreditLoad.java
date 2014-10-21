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

package org.kuali.student.enrollment.courseregistration.infc;

import org.kuali.rice.core.api.util.type.KualiDecimal;

/**
 * This summarizes the credit load for a student.
 */

public interface CreditLoad {

    /**
     * The person Id for the credit summary.
     * 
     * @name Student Id
     */
    public String getStudentId();

    /**
     * The existing credit load for the student in the current term.
     * 
     * @name Credit Load
     */
    public KualiDecimal getCreditLoad();

    /**
     * The maxmimum credit limit for the student in the current term.
     * 
     * @name Credit Limit
     */
    public KualiDecimal getCreditLimit();

    /**
     * The minimum credits allowed for the student in the current term.
     * 
     * @name Credit Minimum
     */
    public KualiDecimal getCreditMinimum();

    /**
     * The additional credits from the RegistrationRequest. For drops,
     * this amount can be negative.
     * 
     * @name Additional Credits
     */
    public KualiDecimal getAdditionalCredits();
}
