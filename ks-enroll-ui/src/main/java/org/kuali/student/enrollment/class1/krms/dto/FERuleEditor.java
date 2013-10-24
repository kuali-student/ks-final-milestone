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
import org.kuali.rice.krms.api.repository.action.ActionDefinitionContract;
import org.kuali.rice.krms.api.repository.proposition.PropositionDefinitionContract;
import org.kuali.rice.krms.api.repository.rule.RuleDefinitionContract;
import org.kuali.rice.krms.dto.ActionEditor;
import org.kuali.rice.krms.dto.PropositionEditor;
import org.kuali.rice.krms.dto.RuleEditor;
import org.kuali.rice.krms.dto.RuleTypeInfo;
import org.kuali.student.enrollment.class1.krms.util.EnrolKRMSConstants;
import org.kuali.student.lum.lu.ui.krms.dto.LUPropositionEditor;
import org.kuali.student.lum.lu.ui.krms.dto.LURuleEditor;
import org.kuali.student.r2.common.util.date.DateFormatters;
import org.kuali.student.r2.core.constants.KSKRMSServiceConstants;
import org.kuali.student.r2.core.room.dto.BuildingInfo;
import org.kuali.student.r2.core.room.dto.RoomInfo;

import java.util.Date;
import java.util.Map;

/**
 * @author Kuali Student Team
 */
public class FERuleEditor extends LURuleEditor {

    private static final long serialVersionUID = 1L;

    private String day;
    private String startTime;
    private String startTimeAMPM;
    private String endTime;
    private String endTimeAMPM;

    private boolean tba;

    private BuildingInfo building;
    private RoomInfo room;

    public FERuleEditor(){
        super();
    }

    public FERuleEditor(RuleDefinitionContract definition){
        super(definition);
    }

    public FERuleEditor(String key, boolean dummy, RuleTypeInfo ruleTypeInfo) {
        super(key, dummy, ruleTypeInfo);
    }

    public ActionEditor getActionForType(String typeId){
        for(ActionDefinitionContract action : this.getActions()){
            if(action.getTypeId().equals(typeId)){
                return (ActionEditor) action;
            }
        }
        return null;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
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

    public boolean isTba() {
        return tba;
    }

    public void setTba(boolean tba) {
        this.tba = tba;
    }

    public BuildingInfo getBuilding() {
        if(building==null){
            building = new BuildingInfo();
        }
        return building;
    }

    public void setBuilding(BuildingInfo building) {
        this.building = building;
    }

    public RoomInfo getRoom() {
        if(room==null){
            room = new RoomInfo();
        }
        return room;
    }

    public void setRoom(RoomInfo room) {
        this.room = room;
    }

    public String getDayToDisplay() {
        if(this.getDay()!=null){
            return "Day " + day;
        }

        return StringUtils.EMPTY;
    }

    public String getTimePeriodToDisplay() {
        String timeString = StringUtils.EMPTY;
        if (this.isTba()) {
            timeString = EnrolKRMSConstants.KSKRMS_RULE_TBA_UI;
        }else {
            if(this.getStartTime() != null){
                timeString = this.getStartTime() + " " + this.getStartTimeAMPM();
            }
            if(this.getEndTime() != null){
                timeString += "-" + this.getEndTime() + " " + this.getEndTimeAMPM();
            }
        }

        return timeString;
    }

    @Override
    protected PropositionEditor createPropositionEditor(PropositionDefinitionContract definition){
        return new FEPropositionEditor(definition);
    }

}
