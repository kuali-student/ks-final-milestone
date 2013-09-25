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
 * Created by chongzhu on 10/24/12
 */
package org.kuali.student.enrollment.class2.acal.service.impl;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.kuali.rice.core.api.criteria.Predicate;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.core.api.util.RiceKeyConstants;
import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.uif.container.CollectionGroup;
import org.kuali.rice.krad.uif.control.SelectControl;
import org.kuali.rice.krad.uif.field.InputField;
import org.kuali.rice.krad.uif.view.View;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.student.enrollment.class2.acal.dto.HolidayCalendarWrapper;
import org.kuali.student.enrollment.class2.acal.dto.HolidayWrapper;
import org.kuali.student.enrollment.class2.acal.service.HolidayCalendarViewHelperService;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.util.date.DateFormatters;
import org.kuali.student.r2.core.acal.dto.HolidayCalendarInfo;
import org.kuali.student.r2.core.acal.dto.HolidayInfo;
import org.kuali.student.r2.core.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.class2.acal.dto.TimeSetWrapper;
import org.kuali.student.enrollment.class2.acal.form.HolidayCalendarForm;
import org.kuali.student.enrollment.class2.acal.util.CalendarConstants;
import org.kuali.student.enrollment.class2.acal.util.CommonUtils;
import org.kuali.student.common.uif.service.impl.KSViewHelperServiceImpl;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.constants.AcademicCalendarServiceConstants;
import org.kuali.student.r2.core.class1.state.dto.StateInfo;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.constants.AtpServiceConstants;

import javax.xml.namespace.QName;
import java.util.*;

import static org.kuali.rice.core.api.criteria.PredicateFactory.*;

/**
 * This class implements ViewHelperServiceImpl for  all HolidayCalendar views
 *
 * @author Kuali Student Team
 */
public class HolidayCalendarViewHelperServiceImpl extends KSViewHelperServiceImpl implements HolidayCalendarViewHelperService {

    private AcademicCalendarService acalService;
    private List<TypeInfo> holidayTypes;


    public void saveHolidayCalendar(HolidayCalendarForm hcForm,boolean isSetOfficial) throws Exception{

        ContextInfo contextInfo = createContextInfo();
        //Save holiday calendar
        HolidayCalendarInfo hcInfo = hcForm.getHolidayCalendarInfo();
        if (StringUtils.isBlank(hcInfo.getStateKey())){
            hcInfo.setStateKey(AtpServiceConstants.ATP_DRAFT_STATE_KEY);
        }
        hcInfo.setTypeKey(AcademicCalendarServiceConstants.HOLIDAY_CALENDAR_TYPE_KEY);
        hcInfo.setDescr(CommonUtils.buildDesc("no description"));

        if (StringUtils.isBlank(hcInfo.getId())){
            HolidayCalendarInfo createdHCal = getAcalService().createHolidayCalendar(AcademicCalendarServiceConstants.HOLIDAY_CALENDAR_TYPE_KEY, hcInfo, contextInfo);
            hcForm.setHolidayCalendarInfo(createdHCal);
        }else{
            HolidayCalendarInfo updatedHCal = getAcalService().updateHolidayCalendar(hcInfo.getId(), hcInfo, contextInfo);
            hcForm.setHolidayCalendarInfo(updatedHCal);
        }

        //Save holidays
        List<HolidayWrapper> holidays = hcForm.getHolidays();
        // save list of new holiday IDs here:
        List<String> newHolidayIdList = new ArrayList<String>(holidays.size());

        HolidayInfo holidayInfo, storedHolidayInfo;
        for (HolidayWrapper holidayWrapper : holidays){

            holidayInfo = holidayWrapper.getHolidayInfo();
            holidayWrapper.setTypeName(getHolidayTypeName(holidayWrapper.getTypeKey()));
            holidayInfo.setDescr(CommonUtils.buildDesc("no description"));
            holidayInfo.setIsAllDay(holidayWrapper.isAllDay());
            holidayInfo.setIsInstructionalDay(holidayWrapper.isInstructional());
            holidayInfo.setStartDate(holidayWrapper.getStartDate());
            holidayInfo.setName(holidayWrapper.getTypeName());
            holidayInfo.setStartDate(getStartDateWithUpdatedTime(holidayWrapper,true));
            holidayInfo.setTypeKey(holidayWrapper.getTypeKey());
            setHolidayEndDate(holidayWrapper);

            if (StringUtils.isBlank(holidayInfo.getId())){
                if (StringUtils.equals(hcForm.getHolidayCalendarInfo().getStateKey(),AtpServiceConstants.ATP_OFFICIAL_STATE_KEY)){
                    holidayInfo.setStateKey(AtpServiceConstants.MILESTONE_OFFICIAL_STATE_KEY);
                } else {
                    holidayInfo.setStateKey(AtpServiceConstants.MILESTONE_DRAFT_STATE_KEY);
                }
                storedHolidayInfo = getAcalService().createHoliday(hcForm.getHolidayCalendarInfo().getId(), holidayWrapper.getTypeKey(), holidayInfo, contextInfo);
            }else{
                storedHolidayInfo = getAcalService().updateHoliday(holidayInfo.getId(),holidayInfo, contextInfo);
            }
            holidayWrapper.setHolidayInfo(storedHolidayInfo);
            newHolidayIdList.add(storedHolidayInfo.getId());
            hcForm.setMeta(hcInfo.getMeta());
        }

        if ( ! StringUtils.isBlank(hcInfo.getId())) { // calendar already exists
            // remove all old holidays that are not contained in the list of new holidays
            List<HolidayInfo> oldHolidayList =
                    getAcalService().getHolidaysForHolidayCalendar(hcInfo.getId(), contextInfo);
            for (HolidayInfo oldHoliday : oldHolidayList) {
                if ( ! newHolidayIdList.contains(oldHoliday.getId())) {
                    getAcalService().deleteHoliday(oldHoliday.getId(), contextInfo);
                }
            }
        }

        if (isSetOfficial){
            StatusInfo statusInfo = null;
            try {
                statusInfo = getAcalService().changeHolidayCalendarState(hcForm.getHolidayCalendarInfo().getId(), AcademicCalendarServiceConstants.ACADEMIC_CALENDAR_OFFICIAL_STATE_KEY,createContextInfo());
                if (!statusInfo.getIsSuccess()){
                    GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_MESSAGES, RiceKeyConstants.ERROR_CUSTOM, statusInfo.getMessage());
                } else{
                    hcForm.setHolidayCalendarInfo(getAcalService().getHolidayCalendar(hcForm.getHolidayCalendarInfo().getId(),createContextInfo()));
                    for (HolidayWrapper holidayWrapper : hcForm.getHolidays()) {
                        holidayWrapper.setHolidayInfo(getAcalService().getHoliday(holidayWrapper.getHolidayInfo().getId(),createContextInfo()));
                    }
                }
            } catch (Exception e) {
                GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_MESSAGES, CalendarConstants.MessageKeys.ERROR_ACAL_SAVE_FAILED + " - " + e.getMessage());
            }
        }
    }

    public HolidayCalendarInfo getHolidayCalendar(String hcId) throws Exception{
        HolidayCalendarInfo retrievedHc = getAcalService().getHolidayCalendar(hcId, createContextInfo());
        return retrievedHc;
    }

    /**
     * Returns the most recently created calendar of the current year or past year.
     *
     * @return The found holiday calendar or null if no calendars found for the last 2 years.
     * @throws Exception
     */
    public HolidayCalendarInfo getNewestHolidayCalendar() throws Exception {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);

        // Get the list of holiday calendars for the current year
        List<HolidayCalendarInfo> holidayCalendarInfoList =
                getAcalService().getHolidayCalendarsByStartYear(currentYear, createContextInfo());

        // Check if calendars were found for this year. If not found search for calenders in the previous year
        if ((null == holidayCalendarInfoList) || holidayCalendarInfoList.isEmpty()) {
            holidayCalendarInfoList =
                    getAcalService().getHolidayCalendarsByStartYear((currentYear - 1), createContextInfo());
        }

        if ((null == holidayCalendarInfoList) || (holidayCalendarInfoList.size() == 0)) {

            // If no calendars are found for current or previous year return null.
            return null;
        }
        else {
            // If Calendars are found search through them to find the most recently created.
            // The number of calendars should be small so naive search possible.
            HolidayCalendarInfo newestCalendar =  holidayCalendarInfoList.get(0);
            for(HolidayCalendarInfo calendarTemp: holidayCalendarInfoList){
                // Compare the time when the calendars are created and pick the higher one (most recent).
                if(calendarTemp.getMeta().getCreateTime().compareTo(newestCalendar.getMeta().getCreateTime())>0){
                    newestCalendar = calendarTemp;
                }
            }

            return newestCalendar;
        }
    }

    public List<HolidayWrapper> getHolidayWrappersForHolidayCalendar(String holidayCalendarId) throws Exception {
        List<HolidayInfo> holidayInfos =
                getAcalService().getHolidaysForHolidayCalendar(holidayCalendarId, createContextInfo());
        return assembleHolidays(holidayInfos);
    }

    private List<HolidayWrapper> assembleHolidays (List<HolidayInfo> holidayInfos) throws Exception{
        List<HolidayWrapper> holidays = new ArrayList<HolidayWrapper>();
        if (holidayInfos != null && !holidayInfos.isEmpty()){
            for (HolidayInfo holidayInfo : holidayInfos) {
                HolidayWrapper holiday = assembleHoliday(holidayInfo);
                holidays.add(holiday);
            }
        }

        return holidays;
    }

    private HolidayWrapper assembleHoliday(HolidayInfo holidayInfo) throws Exception{
        HolidayWrapper holiday = new HolidayWrapper(holidayInfo);
//        holiday.setHolidayInfo(holidayInfo);
        holiday.setTypeName(getHolidayTypeName(holidayInfo.getTypeKey()));
//        CommonUtils.assembleTimeSet(holiday, holidayInfo.getStartDate(), holidayInfo.getEndDate());

        return holiday;
    }

    public String getHolidayTypeName(String holidayTypeKey) throws Exception {
        TypeInfo typeInfo = getAcalService().getHolidayType(holidayTypeKey, createContextInfo());
        return typeInfo.getName();
    }

    public void deleteHoliday(int selectedIndex,HolidayCalendarForm hcForm) throws Exception{
        hcForm.getHolidays().remove(selectedIndex);
    }

    public String getHolidayCalendarState(String holidayCalendarStateKey) throws Exception{
        StateInfo hcState = getAcalService().getHolidayCalendarState(holidayCalendarStateKey, createContextInfo());
        return hcState.getName();
    }

    public void deleteHolidayCalendar(String holidayCalendarId) throws Exception{
        //delete hc
        getAcalService().deleteHolidayCalendar(holidayCalendarId, createContextInfo());
    }

    /**
     * This method is being called by KRAD to populate holiday types drop down. There would be no reference
     * for this method in the code as it has it's reference at the xml
     *
     * @param field
     * @param hcForm
     */
    @SuppressWarnings("unused")
    public void populateHolidayTypes(InputField field, HolidayCalendarForm hcForm){

        boolean isAddLine = BooleanUtils.toBoolean((Boolean) field.getContext().get(UifConstants.ContextVariableNames.IS_ADD_LINE));
        if (!isAddLine) {
            return;
        }

        List<KeyValue> keyValues = new ArrayList<KeyValue>();
        List<String> alreadyAddedTypes = new ArrayList<String>();

        for (HolidayWrapper holidayWrapper : hcForm.getHolidays()) {
            alreadyAddedTypes.add(holidayWrapper.getTypeKey());
        }

        //Hard code "Select holiday type"
        ConcreteKeyValue topKeyValue = new ConcreteKeyValue();
        topKeyValue.setKey("");
        topKeyValue.setValue("Select holiday type");
        keyValues.add(topKeyValue);

        try {
            List<TypeInfo> types = getHolidayTypes();
            for (TypeInfo type : types) {
                if (!alreadyAddedTypes.contains(type.getKey())){
                    ConcreteKeyValue keyValue = new ConcreteKeyValue();
                    keyValue.setKey(type.getKey());
                    keyValue.setValue(type.getName());
                    keyValues.add(keyValue);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        ((SelectControl) field.getControl()).setOptions(keyValues);
    }


    public List<TypeInfo> getHolidayTypes() throws InvalidParameterException, MissingParameterException, PermissionDeniedException, OperationFailedException {
        if(holidayTypes == null) {
            holidayTypes = Collections.unmodifiableList(getAcalService().getHolidayTypes(createContextInfo()));
        }
        return holidayTypes;
    }

    private Date getStartDateWithUpdatedTime(TimeSetWrapper timeSetWrapper,boolean isSaveAction){
        //If start time not blank, set that with the date. If it's empty, just update with default
        if (!timeSetWrapper.isAllDay()){
            if (StringUtils.isNotBlank(timeSetWrapper.getStartTime())){
                String startTime = timeSetWrapper.getStartTime();
                String startTimeApPm = timeSetWrapper.getStartTimeAmPm();
                //On save to DB, have to replace 12AM to 00AM insead of DB considers as 12PM
                if (isSaveAction && StringUtils.startsWith(startTime,"12:") && StringUtils.equalsIgnoreCase(startTimeApPm,"am")){
                    startTime = StringUtils.replace(startTime,"12:","00:");
                }
                return CommonUtils.getDateWithTime(timeSetWrapper.getStartDate(),startTime,startTimeApPm);
            }else{
                timeSetWrapper.setStartTime("12:00");
                timeSetWrapper.setStartTimeAmPm("AM");
                return CommonUtils.getDateWithTime(timeSetWrapper.getStartDate(),
                        timeSetWrapper.getStartTime(),timeSetWrapper.getStartTimeAmPm());
            }
        }else{
            return timeSetWrapper.getStartDate();
        }

    }

    private void setHolidayEndDate(HolidayWrapper holidayWrapper) {
        holidayWrapper.getHolidayInfo().setIsDateRange(holidayWrapper.isDateRange());
        Date endDateToInfo = timeSetWrapperEndDate(holidayWrapper);
        holidayWrapper.getHolidayInfo().setEndDate(endDateToInfo);
    }

    private Date timeSetWrapperEndDate(TimeSetWrapper timeSetWrapper) {
        Date endDateToInfo;

        if (timeSetWrapper.isAllDay()) {
            if (timeSetWrapper.isDateRange()) {
                //just clearing out any time already set in end date
                endDateToInfo = CommonUtils.getDateWithTime(timeSetWrapper.getEndDate(), CalendarConstants.DEFAULT_END_TIME, "PM");
            }
            else {
                endDateToInfo = null;
                timeSetWrapper.setEndDate(null);
            }

            // set the UI time & am/pm fields to null in case they just had values:
            timeSetWrapper.setStartTime(null);
            timeSetWrapper.setStartTimeAmPm("AM");
            timeSetWrapper.setEndTime(null);
            timeSetWrapper.setEndTimeAmPm("AM");
        }
        else {
            if (timeSetWrapper.isDateRange()){
                String endTime = timeSetWrapper.getEndTime();
                String endTimeAmPm = timeSetWrapper.getEndTimeAmPm();
                Date endDate = timeSetWrapper.getEndDate();

                if (StringUtils.isBlank(endTime)){
                    endTime = CalendarConstants.DEFAULT_END_TIME;
                    endTimeAmPm = "PM";
                }
                timeSetWrapper.setEndTime(endTime);
                timeSetWrapper.setEndTimeAmPm(endTimeAmPm);

                endDateToInfo = CommonUtils.getDateWithTime(endDate, endTime, endTimeAmPm);
            } else {
                timeSetWrapper.setEndDate(null);
                timeSetWrapper.setEndTime(null);
                timeSetWrapper.setEndTimeAmPm("AM");
                endDateToInfo = null;
            }
        }

        return endDateToInfo;
    }

    public AcademicCalendarService getAcalService() {
        if(acalService == null) {
            acalService = (AcademicCalendarService) GlobalResourceLoader.getService(new QName(AcademicCalendarServiceConstants.NAMESPACE, AcademicCalendarServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return this.acalService;
    }


    public void validateHolidayCalendar(HolidayCalendarForm hcForm){
        HolidayCalendarInfo hcInfo = hcForm.getHolidayCalendarInfo();

        if (!isValidHcalName(hcInfo)){
            GlobalVariables.getMessageMap().putError("holidayCalendarInfo.name", "error.enroll.calendar.duplicateName");
        }

        if (!CommonUtils.isValidDateRange(hcInfo.getStartDate(),hcInfo.getEndDate())){
            GlobalVariables.getMessageMap().putErrorForSectionId("KS-HolidayCalendar-MetaSection",
                    "error.enroll.daterange.invalid", "Calendar",
                    CommonUtils.formatDate(hcInfo.getStartDate()), CommonUtils.formatDate(hcInfo.getEndDate()));
        }

        //Validate Events
        int index = -1;
        for (HolidayWrapper holiday : hcForm.getHolidays()) {
            ++index;

            if (!CommonUtils.isDateWithinRange(hcInfo.getStartDate(),hcInfo.getEndDate(),holiday.getStartDate()) ||
                    !CommonUtils.isDateWithinRange(hcInfo.getStartDate(),hcInfo.getEndDate(),holiday.getEndDate())){
                GlobalVariables.getMessageMap().putWarningForSectionId("KS-HolidayCalendar-HolidaySection",
                        "error.enroll.holiday.dateNotInHcal", holiday.getTypeName());
            }

            isValidTimeSetWrapper("KS-HolidayCalendar-HolidaySection", holiday.getTypeKey(), holiday, holiday.getTypeName());
        }

    }

    public void populateHolidayCalendarDefaults(HolidayCalendarForm hcForm){

        for (HolidayWrapper holidayWrapper : hcForm.getHolidays()) {
            holidayWrapper.getHolidayInfo().setStartDate(getStartDateWithUpdatedTime(holidayWrapper,false));
            setHolidayEndDate(holidayWrapper);
        }
    }

    private boolean isValidHcalName(HolidayCalendarInfo hcal){

        QueryByCriteria.Builder qBuilder = QueryByCriteria.Builder.create();
        List<Predicate> pList = new ArrayList<Predicate>();
        Predicate p = equal("atpType",AcademicCalendarServiceConstants.HOLIDAY_CALENDAR_TYPE_KEY);
        pList.add(p);

        p = equalIgnoreCase("name", hcal.getName());
        pList.add(p);

        Predicate[] preds = new Predicate[pList.size()];
        pList.toArray(preds);
        qBuilder.setPredicates(and(preds));

        try {
            List<HolidayCalendarInfo> hcals = getAcalService().searchForHolidayCalendars(qBuilder.build(), createContextInfo());
            boolean valid = hcals.isEmpty();
            //Make sure it's not the same Hcal which is being edited by the user
            if (!valid && StringUtils.isNotBlank(hcal.getId())){
                for (HolidayCalendarInfo hc : hcals) {
                    if (!StringUtils.equals(hc.getId(),hcal.getId())){
                        valid = false;
                        break;
                    }
                    valid = true;
                }
            }
            return valid;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    protected void processBeforeAddLine(View view, CollectionGroup collectionGroup, Object model, Object addLine) {
        ContextInfo contextInfo = createContextInfo();
        if (addLine instanceof HolidayCalendarInfo) {
            HolidayCalendarInfo inputLine = (HolidayCalendarInfo)addLine;
            try {
                System.out.println("HC id =" +inputLine.getId());

                HolidayCalendarInfo exists = getAcalService().getHolidayCalendar(inputLine.getId(), contextInfo);

                inputLine.setName(exists.getName());
                inputLine.setId(exists.getId());
                inputLine.setTypeKey(exists.getTypeKey());
                inputLine.setAdminOrgId(exists.getAdminOrgId());
                inputLine.setStartDate(exists.getStartDate());
                inputLine.setEndDate(exists.getEndDate());
            }catch (Exception e) {
                throw new RuntimeException(e);
            }

        }else if (addLine instanceof HolidayCalendarWrapper){
            HolidayCalendarWrapper inputLine = (HolidayCalendarWrapper)addLine;
            List<HolidayWrapper> holidays = new ArrayList<HolidayWrapper>();
            try {
                String holidayCalendarId = inputLine.getId();
                if (!StringUtils.isEmpty(holidayCalendarId)) {
                    HolidayCalendarInfo hcInfo = getAcalService().getHolidayCalendar(inputLine.getId(), contextInfo);
                    inputLine.setHolidayCalendarInfo(hcInfo);
                    inputLine.setAdminOrgName(CommonUtils.getAdminOrgNameById(hcInfo.getAdminOrgId()));
                    StateInfo hcState = getAcalService().getHolidayCalendarState(hcInfo.getStateKey(), contextInfo);
                    inputLine.setStateName(hcState.getName());
                    List<HolidayInfo> holidayInfoList = getAcalService().getHolidaysForHolidayCalendar(hcInfo.getId(), contextInfo);
                    for(HolidayInfo holidayInfo : holidayInfoList){
                        HolidayWrapper holiday = new HolidayWrapper(holidayInfo);
                        TypeInfo typeInfo = getAcalService().getHolidayType(holidayInfo.getTypeKey(), contextInfo);
                        holiday.setTypeName(typeInfo.getName());
                        holidays.add(holiday);
                    }
                    inputLine.setHolidays(holidays);
                }
            }catch (Exception e){
                throw new RuntimeException(e);
            }

        }  else if (addLine instanceof HolidayWrapper){
            HolidayWrapper holiday = (HolidayWrapper)addLine;
            try {
                holiday.setTypeName(getHolidayTypeName(holiday.getTypeKey()));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            if (!CommonUtils.isValidDateRange(holiday.getStartDate(),holiday.getEndDate())){
                GlobalVariables.getMessageMap().putWarningForSectionId("KS-HolidayCalendar-HolidaySection", "error.enroll.daterange.invalid",holiday.getTypeName(),CommonUtils.formatDate(holiday.getStartDate()),CommonUtils.formatDate(holiday.getEndDate()));
            }
        } else {
            super.processBeforeAddLine(view, collectionGroup, model, addLine);
        }
    }

    protected boolean performAddLineValidation(View view, CollectionGroup collectionGroup, Object model, Object addLine) {
        if (model instanceof HolidayCalendarForm) {
            if (addLine instanceof HolidayWrapper) {
                HolidayWrapper holidayWrapper = (HolidayWrapper)addLine;
                if (!isValidTimeSetWrapper(collectionGroup.getId(), holidayWrapper.getTypeKey(), holidayWrapper, holidayWrapper.getTypeName())) {
                    return false;
                }
            }
        }

        return super.performAddLineValidation(view, collectionGroup, model, addLine);
    }

    // NOTE: edits here should not be needed if KRAD validation is working properly...
    private boolean isValidTimeSetWrapper(String collectionGroupId, String KeyDateType, TimeSetWrapper wrapper, String wrapperName) {
        boolean isValid = true;

        if(StringUtils.isEmpty(KeyDateType)) {
            GlobalVariables.getMessageMap().putErrorForSectionId(collectionGroupId, CalendarConstants.MessageKeys.ERROR_KEY_DATE_TYPE_REQUIRED);
//            GlobalVariables.getMessageMap().putErrorForSectionId(lineName+".typeKey", CalendarConstants.MessageKeys.ERROR_KEY_DATE_TYPE_REQUIRED);
            return false;
        }

        // Start Date not null, Start Time null, End Date null, End Time not null - illegal
        if (wrapper.getStartDate()!=null && (wrapper.getStartTime()==null || StringUtils.isBlank(wrapper.getStartTime())) &&
                wrapper.getEndDate()==null && (wrapper.getEndTime()!=null && !StringUtils.isBlank(wrapper.getEndTime()))){
            GlobalVariables.getMessageMap().putErrorForSectionId(collectionGroupId, CalendarConstants.MessageKeys.ERROR_INVALID_DATE_TIME, wrapperName);
            return false;
        }
        // Start Date null, Start Time not null, different combinations of End Date and End Time - illegal
        else if (wrapper.getStartDate()==null && (wrapper.getStartTime()!=null && !StringUtils.isBlank(wrapper.getStartTime()))){
            GlobalVariables.getMessageMap().putErrorForSectionId(collectionGroupId, CalendarConstants.MessageKeys.ERROR_INVALID_DATE_TIME, wrapperName);
            return false;
        }
        // Start Date null, Start Time null, End Date null, End Time not null - illegal
        else if (wrapper.getStartDate()==null && (wrapper.getStartTime()==null || StringUtils.isBlank(wrapper.getStartTime())) &&
                wrapper.getEndDate()==null && (wrapper.getEndTime()!=null && !StringUtils.isBlank(wrapper.getEndTime()))){
            GlobalVariables.getMessageMap().putErrorForSectionId(collectionGroupId, CalendarConstants.MessageKeys.ERROR_INVALID_DATE_TIME, wrapperName);
            return false;
        }

        // Start Date and End Date could be null but put a warning
        if (wrapper.getStartDate()==null && (wrapper.getStartTime()==null || StringUtils.isBlank(wrapper.getStartTime())) &&
                wrapper.getEndDate()==null && (wrapper.getEndTime()==null || StringUtils.isBlank(wrapper.getEndTime()))){
            GlobalVariables.getMessageMap().putWarningForSectionId(collectionGroupId, CalendarConstants.MessageKeys.ERROR_KEY_DATE_START_DATE_REQUIRED, wrapperName);
        }

        if (wrapper.getStartDate()!=null && (wrapper.getStartTime()==null || StringUtils.isBlank(wrapper.getStartTime())) &&
                wrapper.getEndDate()==null && (wrapper.getEndTime()==null || StringUtils.isBlank(wrapper.getEndTime()))){
            wrapper.setAllDay(true);
            wrapper.setDateRange(false);
        } else if (wrapper.getStartDate()!=null && (wrapper.getStartTime()!=null && !StringUtils.isBlank(wrapper.getStartTime())) &&
                wrapper.getEndDate()==null && (wrapper.getEndTime()==null || StringUtils.isBlank(wrapper.getEndTime()))){
            wrapper.setAllDay(false);
            wrapper.setDateRange(false);
        } else if (wrapper.getStartDate()==null && (wrapper.getStartTime()==null || StringUtils.isBlank(wrapper.getStartTime())) &&
                wrapper.getEndDate()!=null && (wrapper.getEndTime()==null || StringUtils.isBlank(wrapper.getEndTime()))){
            wrapper.setStartDate(wrapper.getEndDate());
            wrapper.setEndDate(null);
            wrapper.setAllDay(true);
            wrapper.setDateRange(false);
        } else if (wrapper.getStartDate()==null && (wrapper.getStartTime()==null || StringUtils.isBlank(wrapper.getStartTime())) &&
                wrapper.getEndDate()!=null && (wrapper.getEndTime()!=null && !StringUtils.isBlank(wrapper.getEndTime()))){
            wrapper.setStartDate(wrapper.getEndDate());
            wrapper.setStartTime(wrapper.getEndTime());
            wrapper.setEndDate(null);
            wrapper.setEndTime(null);
            wrapper.setAllDay(false);
            wrapper.setDateRange(false);
        } else if (wrapper.getStartDate()!=null && (wrapper.getStartTime()==null || StringUtils.isBlank(wrapper.getStartTime())) &&
                wrapper.getEndDate()!=null && (wrapper.getEndTime()==null || StringUtils.isBlank(wrapper.getEndTime()))){
            wrapper.setAllDay(true);
            wrapper.setDateRange(true);
        } else if (wrapper.getStartDate()!=null && (wrapper.getStartTime()!=null && !StringUtils.isBlank(wrapper.getStartTime())) &&
                wrapper.getEndDate()!=null && (wrapper.getEndTime()==null || StringUtils.isBlank(wrapper.getEndTime()))){
            wrapper.setAllDay(false);
            wrapper.setDateRange(true);
            timeSetWrapperEndDate(wrapper);
        } else if (wrapper.getStartDate()!=null && (wrapper.getStartTime()==null || StringUtils.isBlank(wrapper.getStartTime())) &&
                wrapper.getEndDate()!=null && (wrapper.getEndTime()!=null && !StringUtils.isBlank(wrapper.getEndTime()))){
            wrapper.setAllDay(false);
            wrapper.setDateRange(true);
            getStartDateWithUpdatedTime(wrapper, false);
        } else if (wrapper.getStartDate()!=null && (wrapper.getStartTime()!=null && !StringUtils.isBlank(wrapper.getStartTime())) &&
                wrapper.getEndDate()!=null && (wrapper.getEndTime()!=null && !StringUtils.isBlank(wrapper.getEndTime()))){
            wrapper.setAllDay(false);
            wrapper.setDateRange(true);
        } else if (wrapper.getStartDate()!=null && (wrapper.getStartTime()!=null && !StringUtils.isBlank(wrapper.getStartTime())) &&
                wrapper.getEndDate()==null && (wrapper.getEndTime()!=null && !StringUtils.isBlank(wrapper.getEndTime()))){
            wrapper.setEndDate(wrapper.getStartDate());
            wrapper.setAllDay(false);
            wrapper.setDateRange(true);
        }

        // Start Date can't be later than End Date
        if (wrapper.getStartDate()!=null && wrapper.getEndDate()!=null){
            if ((wrapper.getStartTime()!=null && !StringUtils.isBlank(wrapper.getStartTime())) &&
                    (wrapper.getEndTime()!=null && !StringUtils.isBlank(wrapper.getEndTime()))) {
                Date startDate = getStartDateWithUpdatedTime(wrapper, false);
                Date endDate =  timeSetWrapperEndDate(wrapper);
                if (!CommonUtils.isValidDateRange(startDate, endDate)) {
                    GlobalVariables.getMessageMap().putErrorForSectionId(collectionGroupId, CalendarConstants.MessageKeys.ERROR_INVALID_DATE_RANGE, wrapperName, DateFormatUtils.format(startDate, DateFormatters.MONTH_DAY_YEAR_TIME_DATE_FORMAT), DateFormatUtils.format(endDate, DateFormatters.MONTH_DAY_YEAR_TIME_DATE_FORMAT));
                    return false;
                }
            } else {
                if (!CommonUtils.isValidDateRange(wrapper.getStartDate(), wrapper.getEndDate())) {
                    GlobalVariables.getMessageMap().putErrorForSectionId(collectionGroupId, CalendarConstants.MessageKeys.ERROR_INVALID_DATE_RANGE, wrapperName, CommonUtils.formatDate(wrapper.getStartDate()), CommonUtils.formatDate(wrapper.getEndDate()));
                    return false;
                }
            }
        }

        return isValid;
    }

}
