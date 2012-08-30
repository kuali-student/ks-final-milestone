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
 * Created by Mezba Mahtab on 8/28/12
 */
package org.kuali.student.r2.core.scheduling.infc;

import org.kuali.student.r2.common.infc.DisplayObject;
import org.kuali.student.r2.common.infc.IdEntity;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestComponentDisplayInfo;

import java.util.List;

/**
 * This interface represents a reusable display object in the Scheduling Service for ScheduleRequests.
 *
 * @author Mezba Mahtab
 */
public interface ScheduleRequestDisplay extends IdEntity, DisplayObject {

    /**
     *  Reference object identifier
     *
     * @name Ref Object Id
     * @required
     * @readonly
     */
    public String getRefObjectId();

    /**
     * Referenced object type key
     *
     * @name Ref Object Type Key
     * @required
     * @readonly
     */
    public String getRefObjectTypeKey();

    /**
     * The Schedule Request Component Ids. These provide a list of preferences.
     *
     * @name Schedule Request Component Ids
     */
    public List<ScheduleRequestComponentDisplayInfo> getScheduleRequestComponentDisplays();
}
