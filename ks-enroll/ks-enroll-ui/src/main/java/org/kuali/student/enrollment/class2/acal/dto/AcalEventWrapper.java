package org.kuali.student.enrollment.class2.acal.dto;

import org.kuali.student.enrollment.acal.dto.AcalEventInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.core.constants.AtpServiceConstants;

public class AcalEventWrapper extends TimeSetWrapper{

    private AcalEventInfo acalEventInfo;
    private String eventTypeKey;
    private String eventTypeName;

    public AcalEventWrapper() {
        acalEventInfo = new AcalEventInfo();
        setAllDay(false);
        setDateRange(true);
        acalEventInfo.setStateKey(AtpServiceConstants.MILESTONE_DRAFT_STATE_KEY);
    }

    public AcalEventWrapper(AcalEventInfo acalEventInfo,boolean isCopy){
        this.setStartDate(acalEventInfo.getStartDate());
        this.setEndDate(acalEventInfo.getEndDate());
        this.setAllDay(acalEventInfo.getIsAllDay());
        this.setDateRange(acalEventInfo.getIsDateRange());
        this.setEventTypeKey(acalEventInfo.getTypeKey());

        buildDateAndTime();

        if (isCopy){
            setAcalEventInfo(new AcalEventInfo());
            RichTextInfo rti = new RichTextInfo();
            rti.setPlain(getAcalEventInfo().getTypeKey());
            getAcalEventInfo().setDescr(rti);
            getAcalEventInfo().setStateKey(AtpServiceConstants.MILESTONE_DRAFT_STATE_KEY);
        }else{
           setAcalEventInfo(acalEventInfo);
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

    //This is for UI display purpose
    public String getStartDateUI(){
        return formatStartDateUI(acalEventInfo.getStartDate());
    }

    //This is for UI display purpose
    public String getEndDateUI(){
        return formatEndDateUI(acalEventInfo.getEndDate());
    }


}
