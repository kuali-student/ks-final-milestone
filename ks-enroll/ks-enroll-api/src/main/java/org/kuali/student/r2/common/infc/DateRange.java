/*
 * Copyright 2011 The Kuali Foundation Licensed under the Educational
 * Community License, Version 2.0 (the "License"); you may not use
 * this file except in compliance with the License. You may obtain a
 * copy of the License at http://www.osedu.org/licenses/ECL-2.0 Unless
 * required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.r2.common.infc;

import java.util.Date;


/**
 * A range of two dates.
 *
 * @author tom
 */
public interface DateRange {

    /**
     * Name: Start
     * Get sthe start date of the range.
     */
    public Date getStart();
    public void setStart(Date start);

    /**
     * Name: End
     * Gets the end date of the range.
     */
    public Date getEnd();
    public void setEnd(Date end);
}
