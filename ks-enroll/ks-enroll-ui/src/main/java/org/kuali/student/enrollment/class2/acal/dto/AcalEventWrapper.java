package org.kuali.student.enrollment.class2.acal.dto;

import org.kuali.student.enrollment.acal.dto.AcalEventInfo;

public class AcalEventWrapper extends TimeSetWrapper{

    private AcalEventInfo acalEventInfo;
    private String eventType;

    public AcalEventWrapper() {
        acalEventInfo = new AcalEventInfo();
    }

    public AcalEventInfo getAcalEventInfo(){
        return acalEventInfo;
    }

    public void setAcalEventInfo(AcalEventInfo acalEventInfo) {
        this.acalEventInfo =  acalEventInfo;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

}
