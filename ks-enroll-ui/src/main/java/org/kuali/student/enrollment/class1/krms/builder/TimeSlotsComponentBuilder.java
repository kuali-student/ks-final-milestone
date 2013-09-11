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
import org.kuali.rice.krms.builder.ComponentBuilder;
import org.kuali.student.enrollment.class1.krms.dto.FEPropositionEditor;
import org.kuali.student.r2.common.util.date.DateFormatters;
import org.kuali.student.r2.core.constants.KSKRMSServiceConstants;

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
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
