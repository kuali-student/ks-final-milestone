package org.kuali.student.enrollment.class2.acal.dto;

import org.kuali.student.enrollment.acal.dto.AcalEventInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.util.constants.AtpServiceConstants;

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
        }else{
           setAcalEventInfo(acalEventInfo);
        }

        getAcalEventInfo().setStateKey(AtpServiceConstants.MILESTONE_DRAFT_STATE_KEY);
    }

//    public void copy(AcalEventInfo acalEventInfo){
//           AcalEventInfo newEventInfo = new AcalEventInfo();
//           newEventInfo.setTypeKey(acalEventInfo.getTypeKey());
//           newEventInfo.setIsDateRange(acalEventInfo.getIsDateRange());
//           newEventInfo.setIsAllDay(acalEventInfo.getIsAllDay());
//           setDateRange(acalEventInfo.getIsDateRange());
//           setAllDay(acalEventInfo.getIsAllDay());
//           setAcalEventInfo(newEventInfo);
//           setEventTypeKey(acalEventInfo.getTypeKey());
//           try {
//               TypeInfo type = getTypeService().getType(acalEventInfo.getTypeKey(), getContextInfo());
//               setEventTypeName(type.getName());
//           }catch (Exception e){
//               //TODO
//           }
//           setStartDate(acalEventInfo.getStartDate());
//           setEndDate(acalEventInfo.getEndDate());
//
//        //Copy only start/end time
//        if (!isAllDay()){
//            DateFormat dfm = new SimpleDateFormat("hh:mm");
//
//            setStartTime(dfm.format(acalEventInfo.getStartDate()));
//            setEndTime(dfm.format(acalEventInfo.getEndDate()));
//
//            dfm = new SimpleDateFormat("a");
//            setStartTimeAmPm(dfm.format(acalEventInfo.getStartDate()));
//            setEndTimeAmPm(dfm.format(acalEventInfo.getEndDate()));
//
//        }
//    }

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
