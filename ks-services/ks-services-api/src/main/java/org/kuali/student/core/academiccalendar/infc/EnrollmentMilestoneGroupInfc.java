/**
 * Copyright 2010 The Kuali Foundation Licensed under the
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
 */

package org.kuali.student.core.academiccalendar.infc;

import java.util.Date;
import org.kuali.student.common.infc.KeyEntity;


/**
 * A cluster of hardened dates pertinent to an academic term.
 *
 * @Author tom
 * @Since Tue Apr 05 14:22:34 EDT 2011
 */ 

public interface EnrollmentMilestoneGroupInfc extends KeyEntity {

    /**
     * Name: RegistrationPeriod
     * Gets the milestone for the registration period.
     *
     * @return a milestone
     */
    public MilestoneInfc getRegistrationPeriod();

    /**
     * Name: ClassPeriod
     * Gets the milestone for the range of dates when classes are
     * held.
     *
     * @return a milestone
     */
    public MilestoneInfc getClassPeriod();

    /**
     * Name: AddDate
     * Gets the milestone for the add date.
     *
     * @return a milestone
     */
    public MilestoneInfc getAddDate();

    /**
     * Name: DropDate
     * Gets the milestone for the drop date.
     *
     * @return a milestone
     */
    public MilestoneInfc getDropDate();

    /**
     * Name: FinalExamPeriod
     * Gets the milestone for the range of dates when finals
     * held.
     *
     * @return a milestone
     */
    public MilestoneInfc getFinalExamPeriod();
}
