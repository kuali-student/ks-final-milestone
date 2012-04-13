package org.kuali.student.enrollment.class2.acal.dto;

import org.kuali.student.enrollment.acal.dto.AcalEventInfo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class AcalEventWrapper extends TimeSetWrapper{

    private AcalEventInfo acalEventInfo;
    private String eventTypeKey;
    private String eventTypeName;

    public AcalEventWrapper() {
        acalEventInfo = new AcalEventInfo();
        setAllDay(false);
        setDateRange(true);
    }

    public AcalEventWrapper(AcalEventInfo acalEventInfo){
        this.setAcalEventInfo(acalEventInfo);
        this.setStartDate(acalEventInfo.getStartDate());
        this.setEndDate(acalEventInfo.getEndDate());
        this.setAllDay(acalEventInfo.getIsAllDay());
        this.setDateRange(acalEventInfo.getIsDateRange());
        this.setEventTypeKey(acalEventInfo.getTypeKey());

        buildDateAndTime();

    }

    public void copy(AcalEventInfo acalEventInfo){
           AcalEventInfo newEventInfo = new AcalEventInfo();
           newEventInfo.setTypeKey(acalEventInfo.getTypeKey());
           newEventInfo.setIsDateRange(acalEventInfo.getIsDateRange());
           newEventInfo.setIsAllDay(acalEventInfo.getIsAllDay());
           setDateRange(acalEventInfo.getIsDateRange());
           setAllDay(acalEventInfo.getIsAllDay());
           setAcalEventInfo(newEventInfo);
           setEventTypeKey(acalEventInfo.getTypeKey());
           setStartDate(null);
           setEndDate(null);

        //Copy only start/end time
        if (!isAllDay()){
            DateFormat dfm = new SimpleDateFormat("hh:mm");

            setStartTime(dfm.format(acalEventInfo.getStartDate()));
            setEndTime(dfm.format(acalEventInfo.getEndDate()));

            dfm = new SimpleDateFormat("a");
            setStartTimeAmPm(dfm.format(acalEventInfo.getStartDate()));
            setEndTimeAmPm(dfm.format(acalEventInfo.getEndDate()));

        }
    }

    public AcalEventInfo getAcalEventInfo(){
        return acalEventInfo;
    }

    public void setAcalEventInfo(AcalEventInfo acalEventInfo) {
        this.acalEventInfo =  acalEventInfo;
    }

    public String getEventTypeKey() {
        return eventTypeKey;
    }

    public void setEventTypeKey(String eventTypeKey) {
        this.eventTypeKey = eventTypeKey;        
    }
    
    public String getEventTypeName() {
        return eventTypeName;
    }
    
    public void setEventTypeName(String eventTypeName) {
        this.eventTypeName = eventTypeName;
    }


}
