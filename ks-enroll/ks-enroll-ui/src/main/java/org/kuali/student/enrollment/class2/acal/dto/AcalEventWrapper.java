package org.kuali.student.enrollment.class2.acal.dto;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.student.enrollment.acal.dto.AcalEventInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.LocaleInfo;
import org.kuali.student.r2.common.util.constants.TypeServiceConstants;
import org.kuali.student.r2.core.type.service.TypeService;
import org.kuali.student.r2.core.type.dto.TypeInfo;

import javax.xml.namespace.QName;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

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
           try {
               TypeInfo type = getTypeService().getType(acalEventInfo.getTypeKey(), getContextInfo());
               setEventTypeName(type.getName());
           }catch (Exception e){
               //TODO
           }
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

    public TypeService getTypeService() {
        TypeService typeService = (TypeService) GlobalResourceLoader.getService(new QName(TypeServiceConstants.NAMESPACE, "TypeService"));          
        return typeService;
    }

    public ContextInfo getContextInfo() {
        ContextInfo contextInfo = new ContextInfo();
        contextInfo.setAuthenticatedPrincipalId(GlobalVariables.getUserSession().getPrincipalId());
        contextInfo.setPrincipalId(GlobalVariables.getUserSession().getPrincipalId());
        LocaleInfo localeInfo = new LocaleInfo();
        localeInfo.setLocaleLanguage(Locale.getDefault().getLanguage());
        localeInfo.setLocaleRegion(Locale.getDefault().getCountry());
        contextInfo.setLocale(localeInfo);
        
        return contextInfo;
    }


}
