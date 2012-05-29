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
 * Created by Mezba Mahtab on 5/29/12
 */
package org.kuali.student.r2.core.class1.scheduling.service.impl;

import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.TimeOfDayInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.core.scheduling.constants.SchedulingServiceConstants;
import org.kuali.student.r2.core.scheduling.dto.TimeSlotInfo;
import org.kuali.student.r2.core.scheduling.infc.TimeSlot;
import org.kuali.student.r2.core.scheduling.service.SchedulingService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * This class loads the test data for testing an implementation of SchedulingService.
 *
 * @author Mezba Mahtab, May 29, 2012.
 */
public class SchedulingServiceDataLoader {

    public SchedulingServiceDataLoader() {
    }

    public SchedulingServiceDataLoader (SchedulingService schedulingService) {
        this.schedulingService = schedulingService;
    }

    public SchedulingService getSchedulingService() {
        return schedulingService;
    }

    public void setSchedulingService(SchedulingService schedulingService) {
        this.schedulingService = schedulingService;
    }
    private SchedulingService schedulingService;
    private static String principalId = SchedulingServiceDataLoader.class.getSimpleName();

    public void loadData ()
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException {
        // days of week M W F
        List<Integer> DOW_M_W_F= new ArrayList<Integer>();
        DOW_M_W_F.add(Calendar.MONDAY);
        DOW_M_W_F.add(Calendar.WEDNESDAY);
        DOW_M_W_F.add(Calendar.FRIDAY);
        // days of week T TH
        List<Integer> DOW_T_TH = new ArrayList<Integer>();
        DOW_T_TH.add(Calendar.TUESDAY);
        DOW_T_TH.add(Calendar.THURSDAY);

        // test data
        loadTimeSlotInfo("1", SchedulingServiceConstants.TIME_SLOT_STATE_STANDARD_KEY, SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_KEY, DOW_M_W_F, new Long (8 * 60 * 60 * 1000), new Long (8 * 60 * 60 * 1000 + 50 * 60 * 1000));
        loadTimeSlotInfo("2", SchedulingServiceConstants.TIME_SLOT_STATE_STANDARD_KEY, SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_KEY, DOW_M_W_F, new Long (8 * 60 * 60 * 1000), new Long (8 * 60 * 60 * 1000 + 70 * 60 * 1000));
        loadTimeSlotInfo("3", SchedulingServiceConstants.TIME_SLOT_STATE_STANDARD_KEY, SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_KEY, DOW_T_TH, new Long (8 * 60 * 60 * 1000), new Long (8 * 60 * 60 * 1000 + 50 * 60 * 1000));
        loadTimeSlotInfo("4", SchedulingServiceConstants.TIME_SLOT_STATE_STANDARD_KEY, SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_KEY, DOW_T_TH, new Long (8 * 60 * 60 * 1000), new Long (8 * 60 * 60 * 1000 + 70 * 60 * 1000));
        loadTimeSlotInfo("5", SchedulingServiceConstants.TIME_SLOT_STATE_STANDARD_KEY, SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_KEY, DOW_M_W_F, new Long (10 * 60 * 60 * 1000), new Long (10 * 60 * 60 * 1000 + 50 * 60 * 1000));
        loadTimeSlotInfo("6", SchedulingServiceConstants.TIME_SLOT_STATE_STANDARD_KEY, SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_KEY, DOW_M_W_F, new Long (10 * 60 * 60 * 1000), new Long (10 * 60 * 60 * 1000 + 70 * 60 * 1000));
        loadTimeSlotInfo("7", SchedulingServiceConstants.TIME_SLOT_STATE_STANDARD_KEY, SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_KEY, DOW_T_TH, new Long (10 * 60 * 60 * 1000), new Long (10 * 60 * 60 * 1000 + 50 * 60 * 1000));
        loadTimeSlotInfo("8", SchedulingServiceConstants.TIME_SLOT_STATE_STANDARD_KEY, SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_KEY, DOW_T_TH, new Long (10 * 60 * 60 * 1000), new Long (10 * 60 * 60 * 1000 + 70 * 60 * 1000));
        loadTimeSlotInfo("9", SchedulingServiceConstants.TIME_SLOT_STATE_STANDARD_KEY, SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_KEY, DOW_M_W_F, new Long (13 * 60 * 60 * 1000), new Long (13 * 60 * 60 * 1000 + 50 * 60 * 1000));
        loadTimeSlotInfo("10", SchedulingServiceConstants.TIME_SLOT_STATE_STANDARD_KEY, SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_KEY, DOW_M_W_F, new Long (13 * 60 * 60 * 1000), new Long (13 * 60 * 60 * 1000 + 70 * 60 * 1000));
        loadTimeSlotInfo("11", SchedulingServiceConstants.TIME_SLOT_STATE_STANDARD_KEY, SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_KEY, DOW_T_TH, new Long (13 * 60 * 60 * 1000), new Long (13 * 60 * 60 * 1000 + 50 * 60 * 1000));
        loadTimeSlotInfo("12", SchedulingServiceConstants.TIME_SLOT_STATE_STANDARD_KEY, SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_KEY, DOW_T_TH, new Long (13 * 60 * 60 * 1000), new Long (13 * 60 * 60 * 1000 + 70 * 60 * 1000));
        loadTimeSlotInfo("13", SchedulingServiceConstants.TIME_SLOT_STATE_STANDARD_KEY, SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_KEY, DOW_M_W_F, new Long (15 * 60 * 60 * 1000), new Long (15 * 60 * 60 * 1000 + 50 * 60 * 1000));
        loadTimeSlotInfo("14", SchedulingServiceConstants.TIME_SLOT_STATE_STANDARD_KEY, SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_KEY, DOW_M_W_F, new Long (15 * 60 * 60 * 1000), new Long (15 * 60 * 60 * 1000 + 70 * 60 * 1000));
        loadTimeSlotInfo("15", SchedulingServiceConstants.TIME_SLOT_STATE_STANDARD_KEY, SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_KEY, DOW_T_TH, new Long (15 * 60 * 60 * 1000), new Long (15 * 60 * 60 * 1000 + 50 * 60 * 1000));
        loadTimeSlotInfo("16", SchedulingServiceConstants.TIME_SLOT_STATE_STANDARD_KEY, SchedulingServiceConstants.TIME_SLOT_TYPE_ACTIVITY_OFFERING_KEY, DOW_T_TH, new Long (15 * 60 * 60 * 1000), new Long (15 * 60 * 60 * 1000 + 70 * 60 * 1000));
    }

    private void loadTimeSlotInfo (String ts_id, String stateKey, String typeKey, List<Integer> weekdays, Long startTimeInMillisecs, Long endTimeInMillisecs)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException {
        TimeSlotInfo ts = new TimeSlotInfo();
        ts.setId(ts_id);
        ts.setWeekdays(weekdays);
        TimeOfDayInfo startTime = new TimeOfDayInfo();
        startTime.setMilliSeconds(startTimeInMillisecs);
        ts.setStartTime(startTime);
        TimeOfDayInfo endTime = new TimeOfDayInfo();
        endTime.setMilliSeconds(endTimeInMillisecs);
        ts.setEndTime(endTime);
        ts.setStateKey(stateKey);
        ts.setTypeKey(typeKey);
        ContextInfo contextInfo = new ContextInfo();
        contextInfo.setPrincipalId(principalId);
        contextInfo.setCurrentDate(new Date());
        CommonServiceConstants.setIsIdAllowedOnCreate(contextInfo, true);
        schedulingService.createTimeSlot(typeKey, ts, contextInfo);
    }

    public static String ts2Str (TimeSlot ts) {
        String toRet = ts.getId() + ", ";
        for (Integer day : ts.getWeekdays()) {
            switch (day) {
                case Calendar.MONDAY:
                    toRet += "M";
                    break;
                case Calendar.TUESDAY:
                    toRet += "T";
                    break;
                case Calendar.WEDNESDAY:
                    toRet += "W";
                    break;
                case Calendar.THURSDAY:
                    toRet += "TH";
                    break;
                case Calendar.FRIDAY:
                    toRet += "F";
                    break;
                case Calendar.SATURDAY:
                    toRet += "SA";
                    break;
                case Calendar.SUNDAY:
                    toRet += "SU";
                    break;
                default:
            }
            toRet += " ";
        }
        toRet += ", " + ts.getStartTime().getMilliSeconds() + ", " + ts.getEndTime().getMilliSeconds();
        return toRet;
    }
}
