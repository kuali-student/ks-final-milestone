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

import org.apache.log4j.Logger;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krms.builder.ComponentBuilder;
import org.kuali.rice.krms.util.KRMSConstants;
import org.kuali.rice.krms.util.PropositionTreeUtil;
import org.kuali.student.enrollment.class1.krms.dto.FEPropositionEditor;
import org.kuali.student.enrollment.class2.courseoffering.util.ActivityOfferingConstants;
import org.kuali.student.r2.common.util.date.DateFormatters;
import org.kuali.student.r2.core.constants.KSKRMSServiceConstants;
import org.kuali.student.r2.core.scheduling.util.SchedulingServiceUtil;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Kuali Student Team
 */
public class TimeSlotsComponentBuilder implements ComponentBuilder<FEPropositionEditor> {

    private final static Logger LOG = Logger.getLogger(TimeSlotsComponentBuilder.class);

    @Override
    public List<String> getComponentIds() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void resolveTermParameters(FEPropositionEditor propositionEditor, Map<String, String> termParameters) {

        String weekDay = termParameters.get(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_TIMESLOT_WEEKDAY_STRING);
        String startTime = termParameters.get(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_TIMESLOT_START);
        String endTime = termParameters.get(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_TIMESLOT_END);

        if (weekDay != null) {
            propositionEditor.setWeekdays(weekDay);


        }
        if (startTime != null) {
            Date timeForDisplay = new Date(Long.parseLong(startTime));
            String formatStartTime = DateFormatters.HOUR_MINUTE_AM_PM_TIME_FORMATTER.format(timeForDisplay);
            propositionEditor.setStartTime(org.apache.commons.lang.StringUtils.substringBefore(formatStartTime, " "));
            propositionEditor.setStartTimeAMPM(org.apache.commons.lang.StringUtils.substringAfter(formatStartTime, " "));

        }
        if (endTime != null) {

            Date timeForDisplay = new Date(Long.parseLong(endTime));
            String formatEndTime = DateFormatters.HOUR_MINUTE_AM_PM_TIME_FORMATTER.format(timeForDisplay);
            propositionEditor.setEndTime(org.apache.commons.lang.StringUtils.substringBefore(formatEndTime, " "));
            propositionEditor.setEndTimeAMPM(org.apache.commons.lang.StringUtils.substringAfter(formatEndTime, " "));
        }
    }

    @Override
    public Map<String, String> buildTermParameters(FEPropositionEditor propositionEditor) {
        Map<String, String> termParameters = new HashMap<String, String>();
        try {


            if (propositionEditor.getWeekdays() != null) {

                termParameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_TIMESLOT_WEEKDAY_STRING, propositionEditor.getWeekdays());
            }

            if (propositionEditor.getStartTime() != null) {
                String startTimeAMPM = new StringBuilder(propositionEditor.getStartTime()).append(" ").append(propositionEditor.getStartTimeAMPM()).toString();
                long startTimeMillis = this.parseTimeToMillis(startTimeAMPM);
                String startTime = String.valueOf(startTimeMillis);
                termParameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_TIMESLOT_START, startTime);
            }

            if (propositionEditor.getEndTime() != null) {

                String endTimeAMPM = new StringBuilder(propositionEditor.getEndTime()).append(" ").append(propositionEditor.getEndTimeAMPM()).toString();
                long endTimeMillis = this.parseTimeToMillis(endTimeAMPM);
                String endTime = String.valueOf(endTimeMillis);
                termParameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_TIMESLOT_END, endTime);
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
        if (propositionEditor.getEndTime() == null || propositionEditor.getEndTime().isEmpty()) {
            String propName = PropositionTreeUtil.getBindingPath(propositionEditor, "endTime");
            GlobalVariables.getMessageMap().putError(propName, KRMSConstants.KRMS_MSG_ERROR_RDL_ENDTIME);
        }
        if (propositionEditor.getEndTimeAMPM() == null || propositionEditor.getEndTimeAMPM().isEmpty()) {
            String propName = PropositionTreeUtil.getBindingPath(propositionEditor, "endTimeAMPM");
            GlobalVariables.getMessageMap().putError(propName, KRMSConstants.KRMS_MSG_ERROR_RDL_ENDTIME_AMPM);
        }

        //Only perform this validation once all other have passed.
        if(!GlobalVariables.getMessageMap().hasErrors()) {
            if (compareTime(propositionEditor.getStartTime(), propositionEditor.getStartTimeAMPM(), propositionEditor.getEndTime(), propositionEditor.getEndTimeAMPM())) {
                GlobalVariables.getMessageMap().putErrorForSectionId(KRMSConstants.KRMS_PROPOSITION_DETAILSECTION_ID, ActivityOfferingConstants.MSG_ERROR_INVALID_START_TIME);
            }
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
