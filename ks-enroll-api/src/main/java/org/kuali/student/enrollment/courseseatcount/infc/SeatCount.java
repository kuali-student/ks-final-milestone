/**
 * Copyright 2014 The Kuali Foundation Licensed under the
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
 * Created by Charles on 2/11/14
 */
package org.kuali.student.enrollment.courseseatcount.infc;

import java.util.Date;

/**
 * A list of counts associated with a Lui
 * Note that this is not a CRUD-able object.  It is used as the result of a query.
 *
 * @author Kuali Student Team
 */
public interface SeatCount {
    /**
     * Total number of seats
     * For activity offerings, this is max enrollment
     * @return An integer that represents the total number of seats for a LUI
     */
    Integer getTotalSeats();

    /**
     * Number of seats taken by registered students
     * @return An integer that represents the number of registered seats for a LUI
     */
    Integer getUsedSeats();

    /**
     * Number of unused seats.  In general, the sum of available and used
     * seats equal the total seats though that definition is most clear
     * for activity offerings
     * @return An integer that represents the number of registered seats for a LUI
     */
    Integer getAvailableSeats();

    /**
     * The approximate timestamp of when the seatcount was taken
     * @return A date object
     */
    Date getTimestamp();

    /**
     * The lui (mostly AO) associated with the seat count
     * @return A lui ID
     */
    String getLuiId();

    /**
     * The type key of the lui id
     * @return The type key of the lui id
     */
    String getLuiTypeKey();
}
