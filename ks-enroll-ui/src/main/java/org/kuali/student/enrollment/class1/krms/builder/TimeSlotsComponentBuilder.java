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
package org.kuali.student.enrollment.class1.krms.builder;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krms.builder.ComponentBuilder;
import org.kuali.rice.krms.util.KRMSConstants;
import org.kuali.rice.krms.util.PropositionTreeUtil;
import org.kuali.student.enrollment.class1.krms.dto.FEPropositionEditor;
import org.kuali.student.r2.common.dto.TimeOfDayInfo;
import org.kuali.student.r2.common.util.TimeOfDayHelper;
import org.kuali.student.r2.common.util.date.DateFormatters;
import org.kuali.student.r2.core.constants.KSKRMSServiceConstants;
import org.kuali.student.r2.core.scheduling.util.SchedulingServiceUtil;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Kuali Student Team
 */
public class TimeSlotsComponentBuilder implements ComponentBuilder<FEPropositionEditor> {

    @Override
    public List<String> getComponentIds() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void resolveTermParameters(FEPropositionEditor propositionEditor, Map<String, String> termParameters) {

        String weekDay = termParameters.get(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_TIMESLOT_WEEKDAY_STRING);
        String startTime = termParameters.get(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_TIMESLOT_START);

        if (weekDay != null) {
            propositionEditor.setWeekdays(weekDay);
        }

        if (startTime != null) {
            TimeOfDayInfo start = TimeOfDayHelper.setMillis(Long.parseLong(startTime));
            String formatStartTime = TimeOfDayHelper.makeFormattedTimeForAOSchedules(start);
            propositionEditor.setStartTime(org.apache.commons.lang.StringUtils.substringBefore(formatStartTime, " "));
            propositionEditor.setStartTimeAMPM(org.apache.commons.lang.StringUtils.substringAfter(formatStartTime, " "));

        }
    }

    @Override
    public Map<String, String> buildTermParameters(FEPropositionEditor propositionEditor) {
        Map<String, String> termParameters = new HashMap<String, String>();
        try {

            if (propositionEditor.getWeekdays() != null) {
                termParameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_TIMESLOT_WEEKDAY_STRING, propositionEditor.getWeekdays());
            }

            if (StringUtils.isNotEmpty(propositionEditor.getStartTime())) {
                String startTimeAMPM = new StringBuilder(propositionEditor.getStartTime()).append(" ").append(propositionEditor.getStartTimeAMPM()).toString();
                TimeOfDayInfo startTimeOfDayInfo = TimeOfDayHelper.makeTimeOfDayInfoFromTimeString(startTimeAMPM);
                String startTime = String.valueOf(TimeOfDayHelper.getMillis(startTimeOfDayInfo));
                termParameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_TIMESLOT_START, startTime);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return termParameters;
    }


    private long parseTimeToMillis(final String time) throws ParseException {
        return DateFormatters.HOUR_MINUTE_AM_PM_TIME_FORMATTER.parse(time).getTime();
    }

    @Override
    public void onSubmit(FEPropositionEditor propositionEditor) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void validate(FEPropositionEditor propositionEditor) {

        //Validate days
        if (propositionEditor.getWeekdays() == null || propositionEditor.getWeekdays().isEmpty()) {
            String propName = PropositionTreeUtil.getBindingPath(propositionEditor, "weekdays");
            GlobalVariables.getMessageMap().putError(propName, KRMSConstants.KRMS_MSG_ERROR_RDL_WEEKDAYS);
        } else {
            List<Integer> weekdaysList = SchedulingServiceUtil.weekdaysString2WeekdaysList(propositionEditor.getWeekdays());
            if(weekdaysList.size() == 0){
                String propName = PropositionTreeUtil.getBindingPath(propositionEditor, "weekdays");
                GlobalVariables.getMessageMap().putError(propName, KRMSConstants.KRMS_MSG_ERROR_RDL_WEEKDAYS_INVALID);
            }
        }

        //Validate start time and end tiem
        if (propositionEditor.getStartTime() == null || propositionEditor.getStartTime().isEmpty()) {
            String propName = PropositionTreeUtil.getBindingPath(propositionEditor, "startTime");
            GlobalVariables.getMessageMap().putError(propName, KRMSConstants.KRMS_MSG_ERROR_RDL_STARTTIME);
        }
        if (propositionEditor.getStartTimeAMPM() == null || propositionEditor.getStartTimeAMPM().isEmpty()) {
            String propName = PropositionTreeUtil.getBindingPath(propositionEditor, "startTimeAMPM");
            GlobalVariables.getMessageMap().putError(propName, KRMSConstants.KRMS_MSG_ERROR_RDL_STARTTIME_AMPM);
        }
    }

    private boolean compareTime(String startTime, String startTimeAMPM, String endTime, String endTimeAMPM) {
        String sTimeAMPM = new StringBuilder(startTime).append(" ").append(startTimeAMPM).toString();
        String eTimeAMPM = new StringBuilder(endTime).append(" ").append(endTimeAMPM).toString();

        long sTime;
        long eTime;

        try {
            sTime = parseTimeToMillis(sTimeAMPM);
            eTime = parseTimeToMillis(eTimeAMPM);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        if(eTime <= sTime) {
            return true;
        }
        return false;
    }
}
