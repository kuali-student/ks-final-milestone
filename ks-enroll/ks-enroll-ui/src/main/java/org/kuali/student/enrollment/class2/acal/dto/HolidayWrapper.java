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
 */
package org.kuali.student.enrollment.class2.acal.dto;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.kuali.student.enrollment.acal.dto.HolidayInfo;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class HolidayWrapper extends TimeSetWrapper {
    private String typeName;
    private HolidayInfo holidayInfo;

    public HolidayWrapper(){}

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public HolidayInfo getHolidayInfo() {
        return holidayInfo;
    }

    public void setHolidayInfo(HolidayInfo holidayInfo) {
        this.holidayInfo = holidayInfo;
    }

    //This is for UI display purpose
    public String getIsNonInstructional(){
        if (holidayInfo != null){
            return StringUtils.capitalize(BooleanUtils.toStringYesNo(!holidayInfo.getIsInstructionalDay()));
        }
        return StringUtils.capitalize(BooleanUtils.toStringYesNo(true));
    }
}
