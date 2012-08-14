/*
 * Copyright 2012 The Kuali Foundation Licensed under the
 *  Educational Community License, Version 2.0 (the "License"); you may
 *  not use this file except in compliance with the License. You may
 *  obtain a copy of the License at
 *
 *   http://www.osedu.org/licenses/ECL-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an "AS IS"
 *  BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 *  or implied. See the License for the specific language governing
 *  permissions and limitations under the License.
 */

package org.kuali.student.r2.common.dto;

import org.kuali.student.r2.common.infc.TimeOfDay;

//import javax.xml.bind.Element;
import javax.xml.bind.annotation.*;
//import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TimeOfDayInfo", propOrder = {"milliSeconds"})//, "_futureElements" }) TODO KSCM-372: Non-GWT translatable code})
public class TimeOfDayInfo implements TimeOfDay {

    @XmlElement
    private Long milliSeconds;
    
    //TODO KSCM-372: Non-GWT translatable code
    //@XmlAnyElement
    //private List<Element> _futureElements;

    public TimeOfDayInfo() {

    }

    public TimeOfDayInfo(TimeOfDay timeOfDay) {
        if(null != timeOfDay) {
            this.milliSeconds = timeOfDay.getMilliSeconds();
        }
    }

    @Override
    public Long getMilliSeconds() {
        return this.milliSeconds;
    }

    public void setMilliSeconds(Long milliSeconds) {
        this.milliSeconds = milliSeconds;
    }
}
