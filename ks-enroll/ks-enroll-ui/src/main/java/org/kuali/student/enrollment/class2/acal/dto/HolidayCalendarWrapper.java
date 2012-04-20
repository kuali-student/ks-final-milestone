package org.kuali.student.enrollment.class2.acal.dto;



import org.kuali.student.enrollment.acal.dto.HolidayCalendarInfo;
import org.kuali.student.enrollment.acal.dto.HolidayInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class HolidayCalendarWrapper {
    private static final long serialVersionUID = 1L;

    private String id;
    private String adminOrgName;
    private String stateName;
    private String acalStartYear;

    private HolidayCalendarInfo holidayCalendarInfo;
    private List<HolidayWrapper> holidays;

    public HolidayCalendarWrapper(){
        holidays = new ArrayList<HolidayWrapper>();
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }
    /**
     * @param id the id to set. It equals to holidayCalendarInfo.getId()
     */
    public void setId(String id) {
        this.id = id;
    }
    
    public HolidayCalendarInfo getHolidayCalendarInfo(){
        return holidayCalendarInfo;
    }
    
    public void setHolidayCalendarInfo (HolidayCalendarInfo holidayCalendarInfo){
        this.holidayCalendarInfo = holidayCalendarInfo;
    }

    public String getAdminOrgName() {
        return adminOrgName;
    }
    public void setAdminOrgName(String adminOrgName) {
        this.adminOrgName = adminOrgName;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }
    
    public List<HolidayWrapper> getHolidays() {
        Collections.sort(holidays);
        return holidays;
    }

    public void setHolidays(List<HolidayWrapper> holidays){
        this.holidays = holidays;
    }
    
    public String getAcalStartYear(){
        return acalStartYear;
    }
    
    public void setAcalStartYear(String acalStartYear) {
        this.acalStartYear = acalStartYear;
    }
    
}
