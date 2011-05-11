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

package org.kuali.student.r2.core.queue.infc;

import java.util.Date;
import org.kuali.student.r2.common.infc.IdEntity;


/**
 * Information about a Queue Entry.
 *
 * @Author tom
 * @Since Tue May 10 14:22:34 EDT 2011
 */ 

public interface QueueEntry extends IdEntity {

    /**
     * Name: Queue Id
     * The Id of the Queue.
     */
    public String getQueueId();

    /**
     * Name: Reference Id
     * A reference Id for the object in this position.
     */
    public String getRefId();

    /**
     * Name: Date
     * The time this entry was created.
     */
    public Date getDate();

    /**
     * Name: Position
     * The position of this entry.
     */
    public Integer getPosition();
}
