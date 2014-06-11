/**
 * Copyright 2005-2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.enrollment.class1.krms.dto;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krms.api.repository.proposition.PropositionDefinitionContract;
import org.kuali.rice.krms.dto.PropositionEditor;
import org.kuali.student.lum.lu.ui.krms.dto.LUPropositionEditor;

/**
 * @author Kuali Student Team
 */
public class FEPropositionEditor extends LUPropositionEditor {

    private static final long serialVersionUID = 1L;
    private String weekdays;
    private String startTime;
    private String startTimeAMPM;
    private String endTime;
    private String endTimeAMPM;

    public FEPropositionEditor(){
        super();
    }

    public FEPropositionEditor(PropositionDefinitionContract definition) {
        super(definition);
    }

    public void clear(){
        super.clear();
        this.weekdays= null;
        this.startTime = null;
        this.endTime = null;
    }
    public String getWeekdays() {
        return weekdays;
    }

    public void setWeekdays(String weekdays) {
        this.weekdays = weekdays;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStartTimeAMPM() {
        return startTimeAMPM;
    }

    public void setStartTimeAMPM(String startTimeAMPM) {
        this.startTimeAMPM = startTimeAMPM;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getEndTimeAMPM() {
        return endTimeAMPM;
    }

    public void setEndTimeAMPM(String endTimeAMPM) {
        this.endTimeAMPM = endTimeAMPM;
    }

    public String getTimePeriodToDisplay() {
        String timeString = StringUtils.EMPTY;
        if(this.getStartTime() != null){
            timeString = this.getStartTime() + " " + this.getStartTimeAMPM();
        }
        if(this.getEndTime() != null){
            timeString += "-" + this.getEndTime() + " " + this.getEndTimeAMPM();
        }

        return timeString;
    }

    @Override
    protected PropositionEditor createPropositionEditor(PropositionDefinitionContract definition){
        return new FEPropositionEditor(definition);
    }

}
