/* Copyright 2011 The Kuali Foundation
 * 
 * Licensed under the Educational Community License, Version 1.0 (the
 * "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.opensource.org/licenses/ecl1.php
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.kuali.student.enrollment.class2.acal.form;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.enrollment.acal.dto.AcademicCalendarInfo;
import org.kuali.student.enrollment.class2.acal.dto.AcademicTermWrapper;
import org.kuali.student.enrollment.class2.acal.dto.AcalEventWrapper;
import org.kuali.student.enrollment.class2.acal.dto.HolidayCalendarWrapper;
import org.kuali.student.enrollment.class2.acal.service.AcademicCalendarViewHelperService;
import org.kuali.student.enrollment.class2.acal.util.CalendarConstants;
import org.kuali.student.r2.common.dto.ContextInfo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */

public class AcademicCalendarForm extends UifFormBase {
    private static final long serialVersionUID = 4898118410378641665L;

    private AcademicCalendarInfo academicCalendarInfo;
    private AcademicCalendarInfo orgAcalInfo;
    private String adminOrgName;
    private String updateTimeString;

    private List<AcalEventWrapper> events;
    private List<HolidayCalendarWrapper> holidayCalendarList;
    private List<AcademicTermWrapper> termWrapperList;

    //used by copying
    private boolean newCalendar;
    private boolean officialCalendar;

    //This is useful when user edit term from calendar search. User should see the term tab. By default, info tab
    private String defaultTabToShow;

    private List<AcalEventWrapper> eventsToDeleteOnSave;
    private List<AcademicTermWrapper> termsToDeleteOnSave;

    public AcademicCalendarForm() {
        super();
        academicCalendarInfo = new AcademicCalendarInfo();
        termWrapperList = new ArrayList<AcademicTermWrapper>();
        events = new ArrayList<AcalEventWrapper>();
        holidayCalendarList = new ArrayList<HolidayCalendarWrapper>();
        newCalendar = true;
        officialCalendar = false;
        defaultTabToShow = CalendarConstants.ACAL_INFO_TAB;
        eventsToDeleteOnSave = new ArrayList<AcalEventWrapper>();
        termsToDeleteOnSave = new ArrayList<AcademicTermWrapper>();
    }

    public AcademicCalendarInfo getAcademicCalendarInfo() {
        return academicCalendarInfo;
    }

    public void setAcademicCalendarInfo(AcademicCalendarInfo academicCalendarInfo) {
        this.academicCalendarInfo = academicCalendarInfo;
    }

    public AcademicCalendarInfo getOrgAcalInfo() {
        return orgAcalInfo;
    }

    public void setOrgAcalInfo(AcademicCalendarInfo orgAcalInfo) {
        this.orgAcalInfo = orgAcalInfo;
    }

    public String getAdminOrgName() {
        return adminOrgName;
    }

    public void setAdminOrgName(String adminOrgName) {
        this.adminOrgName = adminOrgName;
    }

    public void setHolidayCalendarList(List<HolidayCalendarWrapper> holidayCalendarList) {
        this.holidayCalendarList = holidayCalendarList;
    }

    public List<HolidayCalendarWrapper> getHolidayCalendarList() {
        return holidayCalendarList;
    }

    public void setTermWrapperList(List<AcademicTermWrapper> termWrapperList) {
        this.termWrapperList = termWrapperList;
    }

    public List<AcademicTermWrapper> getTermWrapperList() {
        return termWrapperList;
    }

    public String getUpdateTimeString(){
        updateTimeString = new String("");
        if (getAcademicCalendarInfo() == null ||
            getAcademicCalendarInfo().getId()== null ||
            getAcademicCalendarInfo().getId().isEmpty()){
            return updateTimeString;
        }
        else {
            Date updateTime = academicCalendarInfo.getMeta().getUpdateTime();
            if (updateTime != null){
                updateTimeString = "Last saved at "+new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(updateTime);
            }
            return updateTimeString;
        }
    }

    public List<AcalEventWrapper> getEvents() {
        return events;
    }

    public void setEvents(List<AcalEventWrapper> events) {
        this.events = events;
    }

    public boolean isNewCalendar() {
        return newCalendar;
    }

    public void setNewCalendar(boolean newCalendar) {
        this.newCalendar = newCalendar;
    }

    public boolean isOfficialCalendar() {
        return officialCalendar;
    }

    public void setOfficialCalendar(boolean officialCalendar) {
        this.officialCalendar = officialCalendar;
    }

    public String getDefaultTabToShow() {
        return defaultTabToShow;
    }

    public void setDefaultTabToShow(String defaultTabToShow) {
        this.defaultTabToShow = defaultTabToShow;
    }

    public List<AcalEventWrapper> getEventsToDeleteOnSave() {
        return eventsToDeleteOnSave;
    }

    public void setEventsToDeleteOnSave(List<AcalEventWrapper> eventsToDeleteOnSave) {
        this.eventsToDeleteOnSave = eventsToDeleteOnSave;
    }

    public List<AcademicTermWrapper> getTermsToDeleteOnSave() {
        return termsToDeleteOnSave;
    }

    public void setTermsToDeleteOnSave(List<AcademicTermWrapper> termsToDeleteOnSave) {
        this.termsToDeleteOnSave = termsToDeleteOnSave;
    }

    public int getDefaultSelectedTabIndex() {
        if (StringUtils.equals(defaultTabToShow,CalendarConstants.ACAL_TERM_TAB)){
            return 1;
        }
        return 0;
    }

    public void reset(){
        setAcademicCalendarInfo(new AcademicCalendarInfo());
        setOfficialCalendar(false);
        getView().setReadOnly(false);
        setEvents(new ArrayList<AcalEventWrapper>());
        setHolidayCalendarList(new ArrayList<HolidayCalendarWrapper>());
        setEventsToDeleteOnSave(new ArrayList<AcalEventWrapper>());
        setTermsToDeleteOnSave(new ArrayList<AcademicTermWrapper>());
        setTermWrapperList(new ArrayList<AcademicTermWrapper>());
        setNewCalendar(false);
    }

    public AcademicCalendarViewHelperService getViewHelperService(){
        if (getView() != null && getView().getViewHelperServiceClass() != null){
            return (AcademicCalendarViewHelperService)getView().getViewHelperService();
        }else{
            return (AcademicCalendarViewHelperService)getPostedView().getViewHelperService();
        }
    }

    public ContextInfo getContextInfo(){
        return getViewHelperService().getContextInfo();
    }

}
