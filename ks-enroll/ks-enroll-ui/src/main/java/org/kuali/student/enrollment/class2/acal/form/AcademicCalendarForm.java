/* Copyright 2011 The Kuali Foundation
 * 
 * Licensed under the Educational Community License, Version 1.0 (the
 * "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.opensource.org/licenses/ecl1.php
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.kuali.student.enrollment.class2.acal.form;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.text.SimpleDateFormat;

import org.kuali.rice.krad.web.form.UifFormBase;

import org.kuali.student.enrollment.acal.dto.AcademicCalendarInfo;
//import org.kuali.student.enrollment.acal.dto.AcalEventInfo;
import org.kuali.student.enrollment.class2.acal.dto.AcademicTermWrapper;
import org.kuali.student.enrollment.class2.acal.dto.AcalEventWrapper;
/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */

public class AcademicCalendarForm extends UifFormBase {
    private static final long serialVersionUID = 4898118410378641665L;

    private AcademicCalendarInfo academicCalendarInfo;
    private String updateTimeString;
    private List<AcalEventWrapper> events;

    private List<AcademicTermWrapper> termWrapperList;

    public AcademicCalendarForm() {
        super();
        termWrapperList = new ArrayList<AcademicTermWrapper>();
        events = new ArrayList<AcalEventWrapper>();
    }

    public AcademicCalendarInfo getAcademicCalendarInfo() {
        return academicCalendarInfo;
    }

    public void setAcademicCalendarInfo(AcademicCalendarInfo academicCalendarInfo) {
        this.academicCalendarInfo = academicCalendarInfo;
    }

    public void setTermWrapperList(List<AcademicTermWrapper> termWrapperList) {
        this.termWrapperList = termWrapperList;
    }

    public List<AcademicTermWrapper> getTermWrapperList() {
        return termWrapperList;
    }

    public String getUpdateTimeString(){
        updateTimeString = new String("");
        if (getAcademicCalendarInfo() == null){
            return updateTimeString;
        }
        else {
            Date updateTime = academicCalendarInfo.getMeta().getUpdateTime();
            if (updateTime != null){
                updateTimeString = "Last saved at "+new SimpleDateFormat("MMM/dd/yyyy HH:mm:ss").format(updateTime);
            }
            return updateTimeString;
        }
    }

    public List<AcalEventWrapper> getEvents() {
        return events;
    }

    public void setEvents(List<AcalEventWrapper> events) {
        this.events = events;
    }

}
