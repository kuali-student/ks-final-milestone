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
 */
package org.kuali.student.r2.core.scheduling.util.impl;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.core.scheduling.dao.TimeSlotDao;
import org.kuali.student.r2.core.scheduling.util.TimeSlotCodeGenerator;

/**
 * This class generates unique code for a timeslot type. Actually, there is no field like
 * code in TimeSlot Entity. The code generated here goes to the name field for each
 * identifying a timeslot in Manage screen.
 *
 * @author Kuali Student Team
 */
public class TimeSlotCodeGeneratorImpl implements TimeSlotCodeGenerator {

    private TimeSlotDao timeSlotDao;
    private String initialCode;

    /**
     * This method generates the code for a timeslot type.
     *
     * @return
     */
    public String generateTimeSlotCode() throws OperationFailedException{

        String currentCode = timeSlotDao.getCurrentMaxTimeSlotCode();

        if (StringUtils.isBlank(currentCode)){
            if (!StringUtils.isBlank(getInitialCode())){
                return getInitialCode();
            } else {
                return "1";
            }
        } else if (NumberUtils.isDigits(currentCode)){
            Integer i = Integer.valueOf(currentCode);
            return String.valueOf(i + 1);
        } else {
            throw new OperationFailedException("TimeSlotCodeGeneratorImpl supports only numeric code.");
        }
    }

    public TimeSlotDao getTimeSlotDao() {
        return timeSlotDao;
    }

    public void setTimeSlotDao(TimeSlotDao timeSlotDao) {
        this.timeSlotDao = timeSlotDao;
    }

    public String getInitialCode() {
        return initialCode;
    }

    /**
     * This value will be used as initial value.
     *
     * @param initialCode
     */
    public void setInitialCode(String initialCode) {
        this.initialCode = initialCode;
    }
}
