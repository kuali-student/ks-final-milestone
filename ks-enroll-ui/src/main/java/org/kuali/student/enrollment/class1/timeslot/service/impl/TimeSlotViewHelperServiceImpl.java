package org.kuali.student.enrollment.class1.timeslot.service.impl;

import edu.emory.mathcs.backport.java.util.Arrays;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.student.common.uif.service.impl.KSViewHelperServiceImpl;
import org.kuali.student.enrollment.class1.timeslot.dto.TimeSlotWrapper;
import org.kuali.student.enrollment.class1.timeslot.service.TimeSlotViewHelperService;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingResourceLoader;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.TimeOfDayInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.core.scheduling.dto.TimeSlotInfo;
import org.kuali.student.r2.core.scheduling.service.SchedulingService;

import java.util.ArrayList;
import java.util.List;

/**
 * View helper for Manage Time Slots.
 */
public class TimeSlotViewHelperServiceImpl
        extends KSViewHelperServiceImpl implements TimeSlotViewHelperService {

    private ContextInfo contextInfo;
    private transient SchedulingService schedulingService;

    @Override
    public List<TimeSlotWrapper> findTimeSlots(List<String> timeSlotTypes)
            throws MissingParameterException, InvalidParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {

        List<TimeSlotWrapper> timeSlotWrappers = new ArrayList<TimeSlotWrapper>();

        for (String tsTypeKey : timeSlotTypes){

            List<String> timeSlotIds = getSchedulingService().getTimeSlotIdsByType(tsTypeKey,getContextInfo());
            List<TimeSlotInfo> timeSlotInfos = getSchedulingService().getTimeSlotsByIds(timeSlotIds,getContextInfo());

            for (TimeSlotInfo timeSlotInfo : timeSlotInfos){
                TimeSlotWrapper tsWrapper = new TimeSlotWrapper();
                tsWrapper.setTimeSlotInfo(timeSlotInfo);
                timeSlotWrappers.add(tsWrapper);
            }
        }


        return timeSlotWrappers;
    }

    private ContextInfo getContextInfo() {
        if (contextInfo == null) {
            contextInfo = ContextUtils.createDefaultContextInfo();
        }
        return contextInfo;
    }

    protected SchedulingService getSchedulingService() {
        if(schedulingService == null) {
            schedulingService = CourseOfferingResourceLoader.loadSchedulingService();
        }
        return schedulingService;
    }
}
