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
package org.kuali.student.enrollment.coursewaitlist.infc;

import org.kuali.student.r2.common.infc.Relationship;

import java.util.Date;

/**
 * Relates a Student to a CourseWaitList.  Essentially these entries are used to represent all students on a course wait list
 *
 * These entries are assigned a well defined position that may be changed at a later time.
 * The lowest position is one.  The highest position is equal to the number of entries on the associated CourseWaitList.
 */
public interface CourseWaitListEntry extends Relationship {


    /**
     * A unique identifier for the state of this object.
     * This value should not be changed directly.
     * Instead, it should be set using the appropriate change state method in service.
     *
     * @name State Key
     * @readOnly
     * @required
     */
    @Override
    String getStateKey();

    /**
     * The Id of the CourseWaitList that this entry belongs to.
     * @name Course Wait List Id
     * @readOnly
     * @required
     */
    String getCourseWaitListId();

    /**
     *
     * The Id of the student associated with this entry (The student that is on the WaitList)
     * @name Student Id
     * @readOnly
     * @required
     */
    String getStudentId();

    /**
     * The Id of the RegistrationRequestItem that this entry is associated with.
     * @name RegistrationRequestItem Id
     * @required
     */
    String getRegistrationRequestItemId();

    /**
     * This entry's position on the WaitList.
     * @name Position
     */
    Integer getPosition();

    /**
     * The last time that the student checked-in on this entry.
     * @name  Last Check-in
     */
    Date getLastCheckIn();
}
