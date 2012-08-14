/**
 * Copyright 2011 The Kuali Foundation Licensed under the Educational
 * Community License, Version 2.0 (the "License"); you may not use
 * this file except in compliance with the License. You may obtain a
 * copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.r2.core.scheduling.infc;

import org.kuali.student.r2.common.infc.IdNamelessEntity;


/**
 * Information about a Schedule Response.
 *
 * @author tom
 * @since Thu Nov 3 14:22:34 EDT 2011
 */ 

public interface ScheduleResponse extends IdNamelessEntity {

    /**
     *  Batch response identifier
     *
     *  @name Batch Response Id
     */
    public String getBatchResponseId();

    /**
     * The Schedule Request Id.
     *
     * @name Schedule Request Id
     * @required
     * @readonly
     */
    public String getScheduleRequestId();

    /**
     * The Schedule Request Id.
     *
     * @name Schedule Id
     * @required
     * @readonly
     */
    public String getScheduleId();

    /**
     * Status of the schedule request
     *
     * @name Status Message
     *
     */
    public String getStatusMessage();
}
