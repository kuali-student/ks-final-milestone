/**
 * Copyright 2012 The Kuali Foundation Licensed under the
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
 * Created by Mezba mahtab on 8/6/12
 */
package org.kuali.student.r2.core.scheduling.infc;

import org.kuali.student.r2.common.infc.IdEntity;

import java.util.List;

/**
 * Represents information about a ScheduleTransaction.
 *
 * @author Mezba Mahtab
 * @author Sri komandur@uw.edu
 */
public interface ScheduleTransaction extends IdEntity {
    /**
     *  Schedule Request identifier
     *
     * @name Schedule Request Id
     * @required
     * @readOnly
     */
    public String getScheduleRequestId();

    /**
     * Existing Schedule id
     * @impl Used for release and swap operation
     * @name Existing Schedule Id
     */
    public String getExistingScheduleId();

    /**
     * Schedule Batch identifier
     *
     * @name Schedule Batch id
     */
    public String getScheduleBatchId ();

    /**
     * The Schedule Request Id.
     *
     * @name Schedule Id
     */
    public String getScheduleId();

    /**
     * Status of the schedule request
     *
     * @name Status Message
     *
     */
    public String getStatusMessage();

    /**
     * The Schedule Request Component Ids. These provide a list of preferences.
     *
     * @name Schedule Request Component Ids
     */
    public List<? extends ScheduleRequestComponent> getScheduleRequestComponents();
}
