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
package org.kuali.student.enrollment.courseseatcount.dto;

import org.kuali.student.enrollment.courseseatcount.infc.SeatCount;

import java.util.Date;

/**
 * This is the structure that represents the seat counts.  Should some be unavailable, then
 * value can be set to null.
 *
 * @author Kuali Student Team
 */
public class SeatCountInfo implements SeatCount {
    private Date timestamp;
    private String luiId;
    private String luiTypeKey;

    private Integer totalSeats;
    private Integer usedSeats;
    private Integer availableSeats;

    @Override
    public Integer getTotalSeats() {
        return totalSeats;
    }

    @Override
    public Integer getUsedSeats() {
        return usedSeats;
    }

    @Override
    public Integer getAvailableSeats() {
        return availableSeats;
    }

    @Override
    public Date getTimestamp() {
        return timestamp;
    }

    @Override
    public String getLuiId() {
        return luiId;
    }

    @Override
    public String getLuiTypeKey() {
        return luiTypeKey;
    }

    public void setTotalSeats(Integer total) {
        totalSeats = total;
    }

    public void setUsedSeats(Integer used) {
        usedSeats = used;
    }

    public void setAvailableSeats(Integer available) {
        availableSeats = available;
    }

    public void setLuiId(String luiId) {
        this.luiId = luiId;
    }

    public void setLuiTypeKey(String luiTypeKey) {
        this.luiTypeKey = luiTypeKey;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
