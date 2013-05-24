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
import org.apache.commons.lang.time.DateFormatUtils;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.r2.core.acal.dto.AcademicCalendarInfo;
import org.kuali.student.enrollment.class2.acal.dto.AcademicTermWrapper;
import org.kuali.student.enrollment.class2.acal.dto.AcalEventWrapper;
import org.kuali.student.enrollment.class2.acal.dto.HolidayCalendarWrapper;
import org.kuali.student.enrollment.class2.acal.util.CalendarConstants;
import org.kuali.student.r2.core.constants.AcademicCalendarServiceConstants;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This is the model class for Academic Calendar.
 *
 * @author Kuali Student Team
 */
public class AcademicCalendarForm extends UifFormBase {

    private AcademicCalendarInfo academicCalendarInfo;
    private AcademicCalendarInfo copyFromAcal;
    private String adminOrgName;
    private String updateTimeString;

    private List<AcalEventWrapper> events;
    private List<HolidayCalendarWrapper> holidayCalendarList;
    private List<AcademicTermWrapper> termWrapperList;

    //used by copying
    private boolean newCalendar;
    private boolean officialCalendar;

    private String defaultTabToShow;

    private List<AcalEventWrapper> eventsToDeleteOnSave;
    private List<AcademicTermWrapper> termsToDeleteOnSave;
    private boolean reload;

    //Temporarily add the following two fields to overcome DD validation on addLine problem
    private boolean addLineValid;
    private String validationJSONString;

    public String getValidationJSONString() {
        return validationJSONString;
    }

    public void setValidationJSONString(String validationJSONString) {
        this.validationJSONString = validationJSONString;
    }

    public boolean isAddLineValid() {
        return addLineValid;
    }

    public void setAddLineValid(boolean addLineValid) {
        this.addLineValid = addLineValid;
    }

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
        addLineValid = true;
        validationJSONString = new String();
    }

    /**
     * Returns an associated acal.
     *
     * @return the acal which is currently in use at the view
     */
    public AcademicCalendarInfo getAcademicCalendarInfo() {
        return academicCalendarInfo;
    }

    /**
     * New Acal or editing an existing acal object
     *
     * @param academicCalendarInfo current acal in use
     */
    public void setAcademicCalendarInfo(AcademicCalendarInfo academicCalendarInfo) {
        this.academicCalendarInfo = academicCalendarInfo;
    }

    /**
     * See <code>setCopyFromAcal()</code>
     *
     * @return acal which is the source for the copy
     */
    public AcademicCalendarInfo getCopyFromAcal() {
        return copyFromAcal;
    }

    /**
     * Sets the recent acal, from which create a new one
     *
     * @param copyFromAcal source acal for copy
     */
    public void setCopyFromAcal(AcademicCalendarInfo copyFromAcal) {
        this.copyFromAcal = copyFromAcal;
    }

    /**
     * Organization for the acal
     *
     * @return org name
     */
    public String getAdminOrgName() {
        return adminOrgName;
    }

    /**
     * Organization involved with an acal.
     *
     * @param adminOrgName organization name
     */
    public void setAdminOrgName(String adminOrgName) {
        this.adminOrgName = adminOrgName;
    }

    /**
     * List of holiday calendars associated with an academic calendar.
     *
     * @param holidayCalendarList list of hcals
     */
    public void setHolidayCalendarList(List<HolidayCalendarWrapper> holidayCalendarList) {
        this.holidayCalendarList = holidayCalendarList;
    }

    /**
     * Returns all the associated Holiday Calendars
     *
     * @return all the hcal associated with the acal
     */
    public List<HolidayCalendarWrapper> getHolidayCalendarList() {
        return holidayCalendarList;
    }

    /**
     * This is a list of terms associated with an academic calendar.
     *
     * @param termWrapperList list of terms associated with the acal
     */
    public void setTermWrapperList(List<AcademicTermWrapper> termWrapperList) {
        this.termWrapperList = termWrapperList;
    }

    /**
     * Returns all the associated terms.
     *
     * @return list of terms associated with the acal
     */
    public List<AcademicTermWrapper> getTermWrapperList() {
        return termWrapperList;
    }

    /**
     * Returns the last updated time stamp. It's being called from the xml and by KRAD. So, there would not be any
     * java references to this method.
     *
     * @return
     */
    @SuppressWarnings("unused")
    public String getLastUpdatedTime(){
        updateTimeString = StringUtils.EMPTY;
        if (StringUtils.isNotBlank(getAcademicCalendarInfo().getId())){
            Date updateTime = academicCalendarInfo.getMeta().getUpdateTime();
            if (updateTime != null){
                updateTimeString = DateFormatUtils.format(updateTime,CalendarConstants.DATE_FORMAT);
            }
        }
        return updateTimeString;
    }

    /**
     * Returns a list of Events
     *
     * @return list of acal events
     */
    public List<AcalEventWrapper> getEvents() {
        return events;
    }

    /**
     * This is a list of events associated with an academic calendar.
     *
     * @param events list of events
     */
    public void setEvents(List<AcalEventWrapper> events) {
        this.events = events;
    }

    /**
     * See <code>setNewCalendar()</code>. (Used in view xml).
     *
     * @return true if it's a new calendar
     */
    @SuppressWarnings("unused")
    public boolean isNewCalendar() {
        return newCalendar;
    }

    public void setNewCalendar(boolean newCalendar) {
        this.newCalendar = newCalendar;
    }

    /**
     * See <code>setOfficialCalendar()</code>. (Used in view xml).
     *
     * @return true if it's a new calendar
     */
    @SuppressWarnings("unused")
    public boolean isOfficialCalendar() {
        return officialCalendar;
    }

    /**
     * Sets whether the acal is official or not
     *
     * @param officialCalendar true or false
     */
    public void setOfficialCalendar(boolean officialCalendar) {
        this.officialCalendar = officialCalendar;
    }

    /**
     * See setTermsToDeleteOnSave()
     *
     * @return
     */
    public List<AcademicTermWrapper> getTermsToDeleteOnSave() {
        return termsToDeleteOnSave;
    }

    /**
     * This holds all the terms which needs to be deleted on Save. When user deletes terms from the ui,
     * it'll be added to this collection and all the terms will be deleted on Acal Save action.
     *
     * @param termsToDeleteOnSave
     */
    public void setTermsToDeleteOnSave(List<AcademicTermWrapper> termsToDeleteOnSave) {
        this.termsToDeleteOnSave = termsToDeleteOnSave;
    }

    /**
     * See <code>setDefaultTabToShow()</code> (Used in xml)
     *
     * @return 'info' to show information tab or 'term' to open term term on calendar open.
     */
    @SuppressWarnings("unused")
    public String getDefaultTabToShow() {
        return defaultTabToShow;
    }

    /**
     * This is to set which tab should be selected when user edits.
     * If user selects editing term, then we need to display the term tab. By default, it shows the Info tab.
     * Allowed values are <p>info</p> for info tab and <p>term</p> for term tab. Passing any other invalid
     * data displays the info tab.
     *
     * @param defaultTabToShow
     */
     public void setDefaultTabToShow(String defaultTabToShow) {
        this.defaultTabToShow = defaultTabToShow;
     }

    /**
     * This returns the index of tab to be selected when user edits. This value will be passed in as template actions.
     * (Used in xml)
     *
     * @return 1 for term tab and 0 for info tab
     */
    @SuppressWarnings("unused")
    public int getDefaultSelectedTabIndex() {
        if (StringUtils.equals(defaultTabToShow,CalendarConstants.ACAL_TERM_TAB)){
            return 1;
        }
        return 0;
    }

    /**
     * This method returns whether the Academic Calendar is Official or not. Based on this flag, delete button will be displayed.
     * (Used in xml)
     *
     * @return true if acal official
     */
    @SuppressWarnings("unused")
    public boolean isOfficialUI(){
        if (academicCalendarInfo != null){
            return StringUtils.equals(AcademicCalendarServiceConstants.ACADEMIC_CALENDAR_OFFICIAL_STATE_KEY,academicCalendarInfo.getStateKey());
        }
        return false;
    }

    /**
     *
     * @return true if the user needs to load the latest version
     */
    @SuppressWarnings("unused")
    public boolean isReload() {
        return reload;
    }

    public void setReload(boolean reload) {
        this.reload = reload;
    }

    /**
     * Resets the form data.
     *
     */
    public void reset(){
        setAcademicCalendarInfo(new AcademicCalendarInfo());
        setOfficialCalendar(false);
        getView().setReadOnly(false);
        setEvents(new ArrayList<AcalEventWrapper>());
        setHolidayCalendarList(new ArrayList<HolidayCalendarWrapper>());
        setTermsToDeleteOnSave(new ArrayList<AcademicTermWrapper>());
        setTermWrapperList(new ArrayList<AcademicTermWrapper>());
        setNewCalendar(false);
    }

}
