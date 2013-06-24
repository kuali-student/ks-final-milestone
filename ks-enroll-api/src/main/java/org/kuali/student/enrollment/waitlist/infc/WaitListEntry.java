/**
 * Copyright 2013 The Kuali Foundation
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
package org.kuali.student.enrollment.waitlist.infc;

import org.kuali.student.r2.common.infc.Relationship;
import org.kuali.student.r2.common.infc.RichText;

import java.util.Date;
import java.util.List;

/**
 * Relates a Student to a WaitList.  Essentially these entries are used to represent all students on a WaitList
 *
 * These entries are assigned a well defined position that may be changed at a later time.
 * The lowest position is one.  The highest position is equal to the number of entries on the associated WaitList.
 */
public interface WaitListEntry extends Relationship {


    /**
     * A unique identifier for the state of this object.
     * This value should not be changed directly.
     * Instead, it should be set using the appropriate change state method in service.
     *
     * @name State Key
     * @required
     */
    @Override
    String getStateKey();

    /**
     * @return the WaitList that this entry belongs to.
     * @name Wait List Id
     * @readonly
     * @required
     */
    String getWaitListId();

    /**
     *
     * @return the id of the student associated with this entry (The student that is on the WaitList)
     * @name Student Id
     * @readonly
     * @required
     */
    String getStudentId();

    /**
     * This value should match one of the offerings that the associated wait list is attached to
     * @return  the idea of the Offering that this entry is associated with.
     * @name Offering Id
     * @required
     */
    String getOfferingId();

    /**
     * This field is considered read-only
     * and may only be changed through the relevant WaitListService operations.
     * @return  This entries position on the WaitList.
     * @name Position
     */
    Integer getPosition();

    /**
     *
     * @return The last time that this entry checked-in on the WaitList.
     * @name  Last Check-in
     */
    Date getLastCheckIn();

    /**
     *
     * @return A reference to any rules that this entry was not able to pass
     * @name Hold List Rule Ids
     */
    List<String> getHoldListRuleIds();
}
