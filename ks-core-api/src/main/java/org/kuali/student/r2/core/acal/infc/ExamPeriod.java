/**
 * Copyright 2013 The Kuali Foundation Licensed under the
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
 * Created by mahtabme on 7/17/13
 */
package org.kuali.student.r2.core.acal.infc;

import org.kuali.student.r2.common.infc.IdEntity;

import java.util.Date;

/**
 * Information about an ExamPeriod.
 *
 * An ExamPeriod is the time period during which an ExamOffering may be offered.
 *
 * @author Mezba
 * @since Wed July 17, 2013
 */
public interface ExamPeriod extends IdEntity {

    /**
     * Gets a display code for this ExamPeriod.
     */
    public String getCode();

    /**
     * The starting date and time of the ExamPeriod. This
     * may not bound milestones associated with this time period, but
     * instead indicates the time period proper.
     */
    public Date getStartDate();

    /**
     * The ending date and time of the ExamPeriod. This may
     * not bound milestones associated with this time period, but
     * instead indicates the time period proper.
     */
    public Date getEndDate();

    /**
     * The Adminsitrative organization responsible for maintaining the ExamPeriod.
     */
    public String getAdminOrgId();
}
