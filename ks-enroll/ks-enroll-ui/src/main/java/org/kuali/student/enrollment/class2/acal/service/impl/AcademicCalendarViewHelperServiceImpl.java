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
 */
package org.kuali.student.enrollment.class2.acal.service.impl;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.kuali.rice.core.api.criteria.Predicate;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.uif.container.CollectionGroup;
import org.kuali.rice.krad.uif.control.SelectControl;
import org.kuali.rice.krad.uif.field.InputField;
import org.kuali.rice.krad.uif.service.impl.ViewHelperServiceImpl;
import org.kuali.rice.krad.uif.util.ObjectPropertyUtils;
import org.kuali.rice.krad.uif.view.View;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.student.enrollment.acal.constants.AcademicCalendarServiceConstants;
import org.kuali.student.enrollment.acal.dto.*;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.class2.acal.dto.*;
import org.kuali.student.enrollment.class2.acal.form.AcademicCalendarForm;
import org.kuali.student.enrollment.class2.acal.form.HolidayCalendarForm;
import org.kuali.student.enrollment.class2.acal.service.AcademicCalendarViewHelperService;
import org.kuali.student.enrollment.class2.acal.util.CalendarConstants;
import org.kuali.student.enrollment.class2.acal.util.CommonUtils;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.LocaleInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.kuali.student.r2.common.util.constants.TypeServiceConstants;
import org.kuali.student.r2.common.state.dto.StateInfo;
import org.kuali.student.r2.common.dto.TypeInfo;
import org.kuali.student.r2.common.type.service.TypeService;

import javax.xml.namespace.QName;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.kuali.rice.core.api.criteria.PredicateFactory.*;


/**
 * This class implement ViewHelperServiceImpl for  all AcademicCalendar views
 *
 * @author Kuali Student Team
 */
public class AcademicCalendarViewHelperServiceImpl extends ViewHelperServiceImpl implements AcademicCalendarViewHelperService {

    private AcademicCalendarService acalService;
    private ContextInfo contextInfo;
    private TypeService typeService;

    private List<TypeInfo> holidayTypes;
    private Map<String, List<TypeInfo>> typesByGroupTypeMap = new HashMap<String, List<TypeInfo>>();


    public void saveHolidayCalendar(HolidayCalendarForm hcForm) throws Exception{

        //Save holiday calendar
        HolidayCalendarInfo hcInfo = hcForm.getHolidayCalendarInfo();
        if (StringUtils.isBlank(hcInfo.getStateKey())){
            hcInfo.setStateKey(AtpServiceConstants.ATP_DRAFT_STATE_KEY);
        }
        hcInfo.setTypeKey(AcademicCalendarServiceConstants.HOLIDAY_CALENDAR_TYPE_KEY);
        hcInfo.setDescr(CommonUtils.buildDesc("no description"));

        if (StringUtils.isBlank(hcInfo.getId())){
            HolidayCalendarInfo createdHCal = getAcalService().createHolidayCalendar(AcademicCalendarServiceConstants.HOLIDAY_CALENDAR_TYPE_KEY, hcInfo, getContextInfo());
            createdHCal = getAcalService().getHolidayCalendar(createdHCal.getId(),getContextInfo());
            hcForm.setHolidayCalendarInfo(createdHCal);
        }else{
            HolidayCalendarInfo updatedHCal = getAcalService().updateHolidayCalendar(hcInfo.getId(), hcInfo, getContextInfo());
            updatedHCal = getAcalService().getHolidayCalendar(updatedHCal.getId(),getContextInfo());
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
            holidayInfo.setStateKey(AtpServiceConstants.MILESTONE_DRAFT_STATE_KEY);
            holidayInfo.setDescr(CommonUtils.buildDesc("no description"));
            holidayInfo.setIsAllDay(holidayWrapper.isAllDay());
            holidayInfo.setIsInstructionalDay(holidayWrapper.isInstructional());
            holidayInfo.setStartDate(holidayWrapper.getStartDate());
            holidayInfo.setName(holidayWrapper.getTypeName());
            holidayInfo.setStartDate(getStartDateWithUpdatedTime(holidayWrapper,true));
            setHolidayEndDate(holidayWrapper);

            if (StringUtils.isBlank(holidayInfo.getId())){
                storedHolidayInfo = getAcalService().createHoliday(hcForm.getHolidayCalendarInfo().getId(), holidayWrapper.getTypeKey(), holidayInfo, getContextInfo());
            }else{
                storedHolidayInfo = getAcalService().updateHoliday(holidayInfo.getId(),holidayInfo, getContextInfo());
            }
            holidayWrapper.setHolidayInfo(storedHolidayInfo);
            newHolidayIdList.add(storedHolidayInfo.getId());
        }

        if ( ! StringUtils.isBlank(hcInfo.getId())) { // calendar already exists
            // remove all old holidays that are not contained in the list of new holidays
            List<HolidayInfo> oldHolidayList =
                    getAcalService().getHolidaysForHolidayCalendar(hcInfo.getId(), getContextInfo());
            for (HolidayInfo oldHoliday : oldHolidayList) {
                if ( ! newHolidayIdList.contains(oldHoliday.getId())) {
                    getAcalService().deleteHoliday(oldHoliday.getId(), getContextInfo());
                }
            }
        }
    }

    public HolidayCalendarInfo getHolidayCalendar(String hcId) throws Exception{
        HolidayCalendarInfo retrievedHc = getAcalService().getHolidayCalendar(hcId, getContextInfo());
        return retrievedHc;
    }

    public HolidayCalendarInfo getNewestHolidayCalendar() throws Exception {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        List<HolidayCalendarInfo> holidayCalendarInfoList =
                getAcalService().getHolidayCalendarsByStartYear(currentYear, getContextInfo());
        if ((null == holidayCalendarInfoList) || holidayCalendarInfoList.isEmpty()) {
            holidayCalendarInfoList =
                    getAcalService().getHolidayCalendarsByStartYear((currentYear - 1), getContextInfo());
        }

        if ((null == holidayCalendarInfoList) || (holidayCalendarInfoList.size() == 0)) {
            return null;
        }
        else {
            //TODO - if > 1 result, find calendar with latest end date?
            return holidayCalendarInfoList.get(holidayCalendarInfoList.size() - 1);
        }
    }

    public List<HolidayWrapper> getHolidayWrappersForHolidayCalendar(String holidayCalendarId) throws Exception {
        List<HolidayInfo> holidayInfos =
                getAcalService().getHolidaysForHolidayCalendar(holidayCalendarId, getContextInfo());
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
        TypeInfo typeInfo = getAcalService().getHolidayType(holidayTypeKey, getContextInfo());
        return typeInfo.getName();
    }

    public void deleteHoliday(int selectedIndex,HolidayCalendarForm hcForm) throws Exception{
        hcForm.getHolidays().remove(selectedIndex);
    }

    public String getHolidayCalendarState(String holidayCalendarStateKey) throws Exception{
        StateInfo hcState = getAcalService().getHolidayCalendarState(holidayCalendarStateKey, getContextInfo());
        return hcState.getName();
    }

    public void deleteHolidayCalendar(String holidayCalendarId) throws Exception{
        //delete hc
        getAcalService().deleteHolidayCalendar(holidayCalendarId, getContextInfo());
    }

    public void populateHolidayTypes(InputField field, HolidayCalendarForm hcForm){

        boolean isAddLine = BooleanUtils.toBoolean((Boolean)field.getContext().get(UifConstants.ContextVariableNames.IS_ADD_LINE));
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

    public AcademicCalendarInfo createAcademicCalendar(AcademicCalendarForm acalForm) throws Exception{
        AcademicCalendarInfo acalInfo = acalForm.getAcademicCalendarInfo();
        acalInfo.setStateKey(AcademicCalendarServiceConstants.ACADEMIC_CALENDAR_DRAFT_STATE_KEY);
        acalInfo.setTypeKey(AcademicCalendarServiceConstants.ACADEMIC_CALENDAR_TYPE_KEY);
        RichTextInfo rti = new RichTextInfo();
        rti.setPlain(acalInfo.getName());
        acalInfo.setDescr(rti);
        AcademicCalendarInfo newAcal = getAcalService().createAcademicCalendar(AcademicCalendarServiceConstants.ACADEMIC_CALENDAR_TYPE_KEY, acalInfo, getContextInfo());
        return newAcal;
    }

    public void deleteAcademicCalendar(String academicCalendarId) throws Exception {
        getAcalService().deleteAcademicCalendar(academicCalendarId, getContextInfo());
    }

    public AcademicCalendarInfo getLatestAcademicCalendar() throws Exception {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        List<AcademicCalendarInfo> academicCalendarInfoList =
                getAcalService().getAcademicCalendarsByStartYear(currentYear, getContextInfo());
        if ((null == academicCalendarInfoList) || academicCalendarInfoList.isEmpty()) {
            academicCalendarInfoList =
                    getAcalService().getAcademicCalendarsByStartYear((currentYear - 1), getContextInfo());
        }

        if ((null == academicCalendarInfoList) || (academicCalendarInfoList.size() == 0)) {
            return null;
        }
        else {
            //TODO - if > 1 result, find calendar with latest end date?
            return academicCalendarInfoList.get(academicCalendarInfoList.size() - 1);
        }
    }

    public void copyToCreateAcademicCalendar(AcademicCalendarForm form) throws Exception {

           AcademicCalendarInfo orgAcalInfo = form.getOrgAcalInfo();

           if (orgAcalInfo == null || StringUtils.isBlank(orgAcalInfo.getId())){
               throw new RuntimeException("ACal Info doesn't exists to copy.");
           }

           // 1. copy over events
           List<AcalEventInfo> orgEventInfoList= getAcalService().getAcalEventsForAcademicCalendar(orgAcalInfo.getId(), getContextInfo());
           List<AcalEventWrapper> newEventList = new ArrayList<AcalEventWrapper>();
           for (AcalEventInfo orgEventInfo : orgEventInfoList){
               AcalEventWrapper newEvent= new AcalEventWrapper(orgEventInfo,true);
               try {
                   TypeInfo type = getTypeService().getType(orgEventInfo.getTypeKey(), getContextInfo());
                   newEvent.setEventTypeName(type.getName());
               }catch (Exception e){
                   //TODO
               }
               newEventList.add(newEvent);
           }
           form.setEvents(newEventList);

          // 2. copy over terms
          List<AcademicTermWrapper> newTermList = populateTermWrappers(orgAcalInfo.getId(), true);
          form.setTermWrapperList(newTermList);

    }

    public List<AcalEventWrapper> populateEventWrappers(AcademicCalendarForm acalForm) throws Exception {

        AcademicCalendarInfo acalInfo = acalForm.getAcademicCalendarInfo();
        List<AcalEventInfo> eventInfos = getAcalService().getAcalEventsForAcademicCalendar(acalInfo.getId(), getContextInfo());
        List<AcalEventWrapper> events = new ArrayList<AcalEventWrapper>();

        for (AcalEventInfo eventInfo: eventInfos) {
            AcalEventWrapper event  = new AcalEventWrapper(eventInfo,false);
            TypeInfo type = getTypeService().getType(event.getEventTypeKey(), getContextInfo());
            event.setEventTypeName(type.getName());
            events.add(event);
        }
        return events;
    }

    public List<HolidayCalendarWrapper> loadHolidayCalendars(AcademicCalendarInfo acalInfo) throws Exception {
        List<HolidayCalendarWrapper> holidayCalendarWrapperList = new ArrayList<HolidayCalendarWrapper>();
        List<String> hcIds = acalInfo.getHolidayCalendarIds();
        if (hcIds != null && !hcIds.isEmpty()){
            for (String hcId : hcIds){
                HolidayCalendarWrapper holidayCalendarWrapper =  getHolidayCalendarWrapper (hcId);
                holidayCalendarWrapperList.add(holidayCalendarWrapper);
            }
        }
        return holidayCalendarWrapperList;        
    }
    
    private HolidayCalendarWrapper getHolidayCalendarWrapper(String hcId){
        ContextInfo context = getContextInfo();

        HolidayCalendarWrapper holidayCalendarWrapper = new HolidayCalendarWrapper();
        List<HolidayWrapper> holidays = new ArrayList<HolidayWrapper>();
        try{
            //need to retrieve HolidayCalendarInfo and all Holidays to form the HolidayCalendarWrapper.
            HolidayCalendarInfo holidayCalendarInfo = getAcalService().getHolidayCalendar(hcId, context);
            holidayCalendarWrapper.setHolidayCalendarInfo(holidayCalendarInfo);
            holidayCalendarWrapper.setId(holidayCalendarInfo.getId());
            holidayCalendarWrapper.setAdminOrgName(getAdminOrgNameById(holidayCalendarInfo.getAdminOrgId()));
            StateInfo hcState = getAcalService().getHolidayCalendarState(holidayCalendarInfo.getStateKey(), context);
            holidayCalendarWrapper.setStateName(hcState.getName());

                List<HolidayInfo> holidayInfoList = getAcalService().getHolidaysForHolidayCalendar(holidayCalendarInfo.getId(), context);
                for(HolidayInfo holidayInfo : holidayInfoList){
                    HolidayWrapper holiday = new HolidayWrapper(holidayInfo);
                    TypeInfo typeInfo = getAcalService().getHolidayType(holidayInfo.getTypeKey(), context);
                    holiday.setTypeName(typeInfo.getName());
                    holidays.add(holiday);
                }
                holidayCalendarWrapper.setHolidays(holidays);
        }catch (Exception e){
            throw new RuntimeException(e);
        }

        return holidayCalendarWrapper;

    }


    public AcalEventWrapper createEvent(String acalId, AcalEventWrapper event) throws Exception{
        AcalEventInfo eventInfo = assembleEventInfoFromWrapper(event);
        AcalEventInfo createdEventInfo = getAcalService().createAcalEvent(acalId, eventInfo.getTypeKey(), eventInfo, getContextInfo());
        event.setAcalEventInfo(createdEventInfo);
        return event;
    }

    public AcalEventWrapper updateEvent(String eventId, AcalEventWrapper event) throws Exception {
        AcalEventInfo eventInfo = assembleEventInfoFromWrapper(event);
        getAcalService().updateAcalEvent(eventId, eventInfo, getContextInfo());
        AcalEventInfo updatedEventInfo = getAcalService().getAcalEvent(eventId, getContextInfo());
        event.setAcalEventInfo(updatedEventInfo);
        return event;
    }

    public void deleteEvent(String eventId) throws Exception {
        getAcalService().deleteAcalEvent(eventId, getContextInfo());
    }

    private AcalEventInfo assembleEventInfoFromWrapper(AcalEventWrapper eventWrapper) throws Exception{
        AcalEventInfo eventInfo = eventWrapper.getAcalEventInfo();

        RichTextInfo rti = new RichTextInfo();
        rti.setPlain(eventWrapper.getEventTypeKey());
        eventInfo.setDescr(rti);
        eventInfo.setStateKey(AtpServiceConstants.MILESTONE_DRAFT_STATE_KEY);
        eventInfo.setTypeKey(eventWrapper.getEventTypeKey());
        eventInfo.setStartDate(eventWrapper.getStartDate());
        eventInfo.setIsAllDay(eventWrapper.isAllDay());
        eventInfo.setStartDate(getStartDateWithUpdatedTime(eventWrapper,true));
        setEventEndDate(eventWrapper);
        return eventInfo;
    }

    protected boolean performAddLineValidation(View view, CollectionGroup collectionGroup, Object model, Object addLine) {
        if (model instanceof AcademicCalendarForm){
            if (addLine instanceof HolidayCalendarWrapper){
                AcademicCalendarForm form = (AcademicCalendarForm)model;
                for(HolidayCalendarWrapper holidayCalendarWrapper : form.getHolidayCalendarList()){
                    if (StringUtils.equals(holidayCalendarWrapper.getId(),((HolidayCalendarWrapper) addLine).getId())){
                        GlobalVariables.getMessageMap().putError("newCollectionLines['holidayCalendarList'].id",
                                CalendarConstants.MSG_ERROR_DUPLICATE_HCAL,
                                holidayCalendarWrapper.getHolidayCalendarInfo().getName());
                        return false;
                    }
                }
            }
            else if (addLine instanceof KeyDatesGroupWrapper) {
                KeyDatesGroupWrapper keydateGroup = (KeyDatesGroupWrapper) addLine;
                if(StringUtils.isEmpty(keydateGroup.getKeyDateGroupType())) {
                    GlobalVariables.getMessageMap().putErrorForSectionId("acal-term-keydatesgroup", CalendarConstants.MSG_ERROR_KEY_DATE_TYPE_REQUIRED);
                    return false;
                }
            }
            else if (addLine instanceof KeyDateWrapper) {
                KeyDateWrapper keydate = (KeyDateWrapper)addLine;
                if(StringUtils.isEmpty(keydate.getKeyDateType())) {
                    GlobalVariables.getMessageMap().putErrorForSectionId( "acal-term-keydates",
                            CalendarConstants.MSG_ERROR_KEY_DATE_TYPE_REQUIRED);
                    return false;
                }
                if (!isValidTimeSetWrapper(keydate, keydate.getKeyDateNameUI(), collectionGroup.getAddLineBindingInfo().getBindingPath())) {
                    return false;
                }
            }
            else if (addLine instanceof AcalEventWrapper) {
                AcalEventWrapper eventWrapper = (AcalEventWrapper)addLine;
                if (!isValidTimeSetWrapper(eventWrapper, eventWrapper.getEventTypeName(),
                        "newCollectionLines['events']")) {
                    return false;
                }
            }
        }
        else
        if (model instanceof HolidayCalendarForm) {
            if (addLine instanceof HolidayWrapper) {
                HolidayWrapper holidayWrapper = (HolidayWrapper)addLine;
                if (!isValidTimeSetWrapper(holidayWrapper, holidayWrapper.getTypeName(),
                        "newCollectionLines['holidays']")) {
                    return false;
                }
            }
        }

        return super.performAddLineValidation(view, collectionGroup, model, addLine);
    }

    public void populateKeyDateTypes(InputField field, AcademicCalendarForm acalForm) {

        boolean isAddLine = BooleanUtils.toBoolean((Boolean)field.getContext().get(UifConstants.ContextVariableNames.IS_ADD_LINE));
        if (!isAddLine) {
            return;
        }

        List<KeyValue> keyValues = new ArrayList<KeyValue>();
        keyValues.add(new ConcreteKeyValue("", "Select Keydate Type"));

        CollectionGroup collectionGroup = (CollectionGroup)field.getContext().get(UifConstants.ContextVariableNames.PARENT);
        KeyDatesGroupWrapper groupWrapper = ObjectPropertyUtils.getPropertyValue(acalForm,collectionGroup.getBindingInfo().getBindByNamePrefix());

        if (StringUtils.isNotBlank(groupWrapper.getKeyDateGroupType())){
            try {
                List<TypeInfo> types = getTypesForGroupType(groupWrapper.getKeyDateGroupType());
                for (TypeInfo type : types) {
                    if (!groupWrapper.isKeyDateExists(type.getKey())){
                        keyValues.add(new ConcreteKeyValue(type.getKey(), type.getName()));
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        ((SelectControl) field.getControl()).setOptions(keyValues);

    }

    public void populateKeyDateGroupTypes(InputField field, AcademicCalendarForm acalForm) {

        boolean isAddLine = BooleanUtils.toBoolean((Boolean)field.getContext().get(UifConstants.ContextVariableNames.IS_ADD_LINE));
        if (!isAddLine) {
            return;
        }

        List<KeyValue> keyValues = new ArrayList<KeyValue>();
        keyValues.add(new ConcreteKeyValue("", "Select Keydate Group Type"));

        CollectionGroup collectionGroup = (CollectionGroup)field.getContext().get(UifConstants.ContextVariableNames.COLLECTION_GROUP);
        AcademicTermWrapper termWrapper = ObjectPropertyUtils.getPropertyValue(acalForm,collectionGroup.getBindingInfo().getBindByNamePrefix());

        try {
            List<TypeInfo> keyDateGroupTypes = getAcalService().getKeyDateTypesForTermType(termWrapper.getTermType(),getContextInfo());
            for (TypeInfo keyDateGroupType : keyDateGroupTypes) {
                if (!termWrapper.isKeyDateGroupExists(keyDateGroupType.getKey())){
                    keyValues.add(new ConcreteKeyValue(keyDateGroupType.getKey(),keyDateGroupType.getName()));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        ((SelectControl) field.getControl()).setOptions(keyValues);

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

            // NOTE: next 2 edits not needed if KRAD validation is working properly
            /*if (holiday.isDateRange() && (null == holiday.getEndDate())) {
                // KRAD 2.0 bug where endDate not filled but gets prior value anyway; gets past endDate edit
                GlobalVariables.getMessageMap().putErrorForSectionId( "holidays["+index+"].endDate",
                        CalendarConstants.MSG_ERROR_DATE_END_REQUIRED, holiday.getTypeName());
            }*/
            if (!holiday.isAllDay()) { // time fields are enabled and can be filled in
                if (!StringUtils.isEmpty(holiday.getStartTime()) && StringUtils.isEmpty(holiday.getStartTimeAmPm())) {
                    GlobalVariables.getMessageMap().putError( "holidays["+index+"].startTimeAmPm",
                            CalendarConstants.MSG_ERROR_TIME_START_AMPM_REQUIRED, holiday.getTypeName());
                }
                if (!StringUtils.isEmpty(holiday.getEndTime()) && StringUtils.isEmpty(holiday.getEndTimeAmPm())) {
                    GlobalVariables.getMessageMap().putError( "holidays["+index+"].endTimeAmPm",
                            CalendarConstants.MSG_ERROR_TIME_END_AMPM_REQUIRED, holiday.getTypeName());
                }
            }
        }

    }

    public void populateHolidayCalendarDefaults(HolidayCalendarForm hcForm){

        for (HolidayWrapper holidayWrapper : hcForm.getHolidays()) {
            holidayWrapper.getHolidayInfo().setStartDate(getStartDateWithUpdatedTime(holidayWrapper,false));
            setHolidayEndDate(holidayWrapper);
        }
    }

    public void populateAcademicCalendarDefaults(AcademicCalendarForm acalForm){

        for (AcalEventWrapper eventWrapper : acalForm.getEvents()) {
            eventWrapper.getAcalEventInfo().setStartDate(getStartDateWithUpdatedTime(eventWrapper,false));
            setEventEndDate(eventWrapper);
        }

        for (AcademicTermWrapper academicTermWrapper : acalForm.getTermWrapperList()) {
            for (KeyDatesGroupWrapper keyDatesGroupWrapper : academicTermWrapper.getKeyDatesGroupWrappers()){
                for(KeyDateWrapper keyDateWrapper : keyDatesGroupWrapper.getKeydates()){
                    keyDateWrapper.getKeyDateInfo().setStartDate(getStartDateWithUpdatedTime(keyDateWrapper,false));
                    setKeyDateEndDate(keyDateWrapper);
                }
            }
        }
    }

    public void validateAcademicCalendar(AcademicCalendarForm acalForm){

        AcademicCalendarInfo acal = acalForm.getAcademicCalendarInfo();

        //Validate Acal Name for duplication
        if (!isValidAcalName(acalForm.getAcademicCalendarInfo())){
            GlobalVariables.getMessageMap().putError("academicCalendarInfo.name", "error.enroll.calendar.duplicateName");
        }

        if (!CommonUtils.isValidDateRange(acal.getStartDate(),acal.getEndDate())){
            GlobalVariables.getMessageMap().putErrorForSectionId("KS-AcademicCalendar-MetaSection", "error.enroll.daterange.invalid","Calendar",CommonUtils.formatDate(acal.getStartDate()),CommonUtils.formatDate(acal.getEndDate()));
        }

        //Validate Events
        for (AcalEventWrapper eventWrapper : acalForm.getEvents()) {
            if (!CommonUtils.isDateWithinRange(acal.getStartDate(),acal.getEndDate(),eventWrapper.getStartDate()) ||
                !CommonUtils.isDateWithinRange(acal.getStartDate(),acal.getEndDate(),eventWrapper.getEndDate())){
                GlobalVariables.getMessageMap().putWarningForSectionId("acal-info-event", "error.enroll.event.dateNotInAcal",eventWrapper.getEventTypeName());
            }
            if (eventWrapper.isDateRange() && !CommonUtils.isValidDateRange(eventWrapper.getStartDate(),eventWrapper.getEndDate())){
                GlobalVariables.getMessageMap().putWarningForSectionId("acal-info-event", "error.enroll.daterange.invalid",eventWrapper.getEventTypeName(),CommonUtils.formatDate(eventWrapper.getStartDate()),CommonUtils.formatDate(eventWrapper.getEndDate()));
            }
        }

        //Validate Terms and keydates
        validateTerms(acalForm.getTermWrapperList(),acal);

    }

    private boolean isValidAcalName(AcademicCalendarInfo acal){

        QueryByCriteria.Builder qBuilder = QueryByCriteria.Builder.create();
        List<Predicate> pList = new ArrayList<Predicate>();

        Predicate p = equal("atpType",AcademicCalendarServiceConstants.ACADEMIC_CALENDAR_TYPE_KEY);
        pList.add(p);

        p = equalIgnoreCase("name", acal.getName());
        pList.add(p);

        Predicate[] preds = new Predicate[pList.size()];
        pList.toArray(preds);
        qBuilder.setPredicates(and(preds));

        try {
            List<AcademicCalendarInfo> acals = getAcalService().searchForAcademicCalendars(qBuilder.build(),getContextInfo());
            boolean valid = acals.isEmpty();
            //Make sure it's not the same Acal which is being edited by the user
            if (!valid && StringUtils.isNotBlank(acal.getId())){
                for (AcademicCalendarInfo academicCalendarInfo : acals) {
                    if (!StringUtils.equals(academicCalendarInfo.getId(),acal.getId())){
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
            List<HolidayCalendarInfo> hcals = getAcalService().searchForHolidayCalendars(qBuilder.build(), getContextInfo());
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

    // NOTE: edits here should not be needed if KRAD validation is working properly...
    private boolean isValidTimeSetWrapper(TimeSetWrapper wrapper, String wrapperName, String lineName) {
        boolean isValid = true;

        // KRAD 2.2.0-M1 can handle endDate, but acal not currently using it because of addLine bug
        if (wrapper.isDateRange() && (null == wrapper.getEndDate())) {
            GlobalVariables.getMessageMap().putError(lineName+".endDate",
                    CalendarConstants.MSG_ERROR_DATE_END_REQUIRED, wrapperName);
            isValid = false;
        }

        if (!wrapper.isAllDay()) { // time fields are enabled and can be filled in
            if (!StringUtils.isEmpty(wrapper.getStartTime()) && StringUtils.isEmpty(wrapper.getStartTimeAmPm())) {
                GlobalVariables.getMessageMap().putError(lineName+".startTimeAmPm",
                        CalendarConstants.MSG_ERROR_TIME_START_AMPM_REQUIRED, wrapperName);
                isValid = false;
            }
            if (!StringUtils.isEmpty(wrapper.getEndTime()) && StringUtils.isEmpty(wrapper.getEndTimeAmPm())) {
                GlobalVariables.getMessageMap().putError(lineName+".endTimeAmPm",
                        CalendarConstants.MSG_ERROR_TIME_END_AMPM_REQUIRED, wrapperName);
                isValid = false;
            }
        }

        return isValid;
    }

    private void validateTerms(List<AcademicTermWrapper> termWrapper,AcademicCalendarInfo acal) {
        for (int index=0; index < termWrapper.size(); index++) {
            validateTerm(termWrapper,index,acal);
        }
    }

    public void validateTerm(List<AcademicTermWrapper> termWrapper,int termToValidateIndex,AcademicCalendarInfo acal) {

        AcademicTermWrapper termWrapperToValidate = termWrapper.get(termToValidateIndex);

        int index2 = 0;
        //Validate duplicate term name
        for (AcademicTermWrapper wrapper : termWrapper) {
            index2++;
            if (wrapper != termWrapperToValidate){
                if (StringUtils.equalsIgnoreCase(wrapper.getName(),termWrapperToValidate.getName())){
                    GlobalVariables.getMessageMap().putErrorForSectionId("acal-term", "error.enroll.term.duplicateName",""+ NumberUtils.min(new int[]{termToValidateIndex,index2}),""+NumberUtils.max(new int[]{termToValidateIndex,index2}));
                }
            }
        }

        if (!CommonUtils.isValidDateRange(termWrapperToValidate.getStartDate(),termWrapperToValidate.getEndDate())){
            GlobalVariables.getMessageMap().putErrorForSectionId("acal-term", "error.enroll.daterange.invalid",termWrapperToValidate.getName(),CommonUtils.formatDate(termWrapperToValidate.getStartDate()),CommonUtils.formatDate(termWrapperToValidate.getEndDate()));
        }

        if (!CommonUtils.isDateWithinRange(acal.getStartDate(),acal.getEndDate(),termWrapperToValidate.getStartDate()) ||
            !CommonUtils.isDateWithinRange(acal.getStartDate(),acal.getEndDate(),termWrapperToValidate.getEndDate())){
            GlobalVariables.getMessageMap().putWarningForSectionId("acal-term", "error.enroll.term.dateNotInAcal",termWrapperToValidate.getName());
        }

        for (KeyDatesGroupWrapper keyDatesGroupWrapper : termWrapperToValidate.getKeyDatesGroupWrappers()){
            for(KeyDateWrapper keyDateWrapper : keyDatesGroupWrapper.getKeydates()){

                if (keyDateWrapper.isDateRange() && !CommonUtils.isValidDateRange(keyDateWrapper.getStartDate(),keyDateWrapper.getEndDate())){
                    GlobalVariables.getMessageMap().putWarningForSectionId("acal-term", "error.enroll.daterange.invalid",keyDateWrapper.getKeyDateNameUI(),CommonUtils.formatDate(keyDateWrapper.getStartDate()),CommonUtils.formatDate(keyDateWrapper.getEndDate()));
                }

                if (!CommonUtils.isDateWithinRange(termWrapperToValidate.getStartDate(),termWrapperToValidate.getEndDate(),keyDateWrapper.getStartDate()) ||
                    !CommonUtils.isDateWithinRange(termWrapperToValidate.getStartDate(),termWrapperToValidate.getEndDate(),keyDateWrapper.getEndDate())){
                    GlobalVariables.getMessageMap().putWarningForSectionId("acal-term-keydates", "error.enroll.keydate.dateNotInTerm",keyDateWrapper.getKeyDateNameUI(),termWrapperToValidate.getName());
                }
            }
        }

    }

    public void populateInstructionalDays(List<AcademicTermWrapper> termWrapperList)
    throws Exception {
         for (AcademicTermWrapper termWrapper : termWrapperList) {
             populateInstructionalDays(termWrapper);
        }
    }

    public void populateInstructionalDays(AcademicTermWrapper termWrapper)
    throws Exception {
        if (termWrapper.getKeyDatesGroupWrappers() != null){
            for (KeyDatesGroupWrapper keyDatesGroupWrapper : termWrapper.getKeyDatesGroupWrappers()) {
                 if (keyDatesGroupWrapper.getKeydates() != null){
                     for (KeyDateWrapper keydate : keyDatesGroupWrapper.getKeydates()) {
                         if (StringUtils.equals(keydate.getKeyDateType(),AtpServiceConstants.MILESTONE_INSTRUCTIONAL_PERIOD_TYPE_KEY) &&
                             termWrapper.getTermInfo() != null && StringUtils.isNotBlank(termWrapper.getTermInfo().getId())){
                             int instructionalDays = getAcalService().getInstructionalDaysForTerm(termWrapper.getTermInfo().getId(),getContextInfo());
                             termWrapper.setInstructionalDays(instructionalDays);
                             break;
                         }
                     }
                 }

            }
        }
    }

    private QueryByCriteria buildQueryByCriteriaForTerm(String type, String code){
        List<Predicate> predicates = new ArrayList<Predicate>();

        predicates.add(PredicateFactory.equal("atpType", type));
        predicates.add(PredicateFactory.equalIgnoreCase("atpCode", code));

        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(predicates.toArray(new Predicate[predicates.size()]));
        QueryByCriteria qbc = qbcBuilder.build();

        return qbc;
    }

    public List<TermInfo> getTermsByTypeAndCode(String type, String code)throws Exception {

        List<TermInfo> termInfoList = new ArrayList<TermInfo>();

        QueryByCriteria qbc = buildQueryByCriteriaForTerm(type, code);

        List<TermInfo> terms = getAcalService().searchForTerms(qbc, getContextInfo());
        for (TermInfo term : terms) {
            termInfoList.add(term);
        }

        return termInfoList;

    }

    public void saveTerm(AcademicTermWrapper termWrapper, String acalId,boolean isOfficial) throws Exception {

        TermInfo term = termWrapper.getTermInfo();

        term.setEndDate(termWrapper.getEndDate());
        term.setStartDate(termWrapper.getStartDate());
        term.setName(termWrapper.getName());
        term.setTypeKey(termWrapper.getTermType());

        if (isOfficial){
           termWrapper.getTermInfo().setStateKey(AtpServiceConstants.ATP_OFFICIAL_STATE_KEY);
        }

        if (termWrapper.isNew()){
            TermInfo newTerm = getAcalService().createTerm(termWrapper.getTermType(),term,getContextInfo());
            termWrapper.setTermInfo(getAcalService().getTerm(newTerm.getId(),getContextInfo()));
            getAcalService().addTermToAcademicCalendar(acalId,termWrapper.getTermInfo().getId(),getContextInfo());
        }else{
            TermInfo updatedTerm = getAcalService().updateTerm(term.getId(),term,getContextInfo());
            termWrapper.setTermInfo(getAcalService().getTerm(updatedTerm.getId(),getContextInfo()));
        }

        for (KeyDateWrapper keyDateWrapper : termWrapper.getKeyDatesToDeleteOnSave()){
            getAcalService().deleteKeyDate(keyDateWrapper.getKeyDateInfo().getId(),getContextInfo());
        }

        //Keydates
        if (termWrapper.getKeyDatesGroupWrappers() != null){
            for (KeyDatesGroupWrapper groupWrapper : termWrapper.getKeyDatesGroupWrappers()){
                for (KeyDateWrapper keyDateWrapper : groupWrapper.getKeydates()) {

                    if (isOfficial){
                        keyDateWrapper.getKeyDateInfo().setStateKey(AtpServiceConstants.MILESTONE_OFFICIAL_STATE_KEY);
                    }

                    KeyDateInfo keyDate = keyDateWrapper.getKeyDateInfo();

                    keyDate.setTypeKey(keyDateWrapper.getKeyDateType());
                    //Add by Bonnie
                    keyDate.setName(keyDateWrapper.getKeyDateNameUI());
                    keyDate.setStartDate(keyDateWrapper.getStartDate());
                    keyDate.setEndDate(keyDateWrapper.getEndDate());
                    keyDate.setIsAllDay(keyDateWrapper.isAllDay());
                    keyDate.setStartDate(getStartDateWithUpdatedTime(keyDateWrapper,true));
                    setKeyDateEndDate(keyDateWrapper);

                    if (keyDateWrapper.isNew()){
                        KeyDateInfo newKeyDate = getAcalService().createKeyDate(termWrapper.getTermInfo().getId(),keyDate.getTypeKey(),keyDate,getContextInfo());
                        keyDateWrapper.setKeyDateInfo(getAcalService().getKeyDate(newKeyDate.getId(),getContextInfo()));
                    } else {
                        KeyDateInfo updatedKeyDate = getAcalService().updateKeyDate(keyDate.getId(), keyDate, getContextInfo());
                        keyDateWrapper.setKeyDateInfo(getAcalService().getKeyDate(updatedKeyDate.getId(),getContextInfo()));
                    }
                }
            }
        }

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
        if (holidayWrapper.isAllDay()) {
            holidayWrapper.getHolidayInfo().setIsDateRange(holidayWrapper.isDateRange());
        }
        else {
            // dateRange in db is true if end date OR end time != start date/time
            holidayWrapper.getHolidayInfo().setIsDateRange(true);
        }
        Date endDateToInfo = timeSetWrapperEndDate(holidayWrapper);
        holidayWrapper.getHolidayInfo().setEndDate(endDateToInfo);
    }

    private void setEventEndDate(AcalEventWrapper eventWrapper) {
        if (eventWrapper.isAllDay()) {
            eventWrapper.getAcalEventInfo().setIsDateRange(eventWrapper.isDateRange());
        }
        else {
            // dateRange in db is true if end date OR end time != start date/time
            eventWrapper.getAcalEventInfo().setIsDateRange(true);
        }
        Date endDateToInfo = timeSetWrapperEndDate(eventWrapper);
        eventWrapper.getAcalEventInfo().setEndDate(endDateToInfo);
    }

    private void setKeyDateEndDate(KeyDateWrapper keyDateWrapper) {
        if (keyDateWrapper.isAllDay()) {
            keyDateWrapper.getKeyDateInfo().setIsDateRange(keyDateWrapper.isDateRange());
        }
        else {
            // dateRange in db is true if end date OR end time != start date/time
            keyDateWrapper.getKeyDateInfo().setIsDateRange(true);
        }
        Date endDateToInfo = timeSetWrapperEndDate(keyDateWrapper);
        keyDateWrapper.getKeyDateInfo().setEndDate(endDateToInfo);
    }

    private Date timeSetWrapperEndDate(TimeSetWrapper timeSetWrapper) {
        Date endDateToInfo;

        if (timeSetWrapper.isAllDay()) {
            if (timeSetWrapper.isDateRange()) {
                //just clearing out any time already set in end date
                endDateToInfo = CommonUtils.getDateWithTime(timeSetWrapper.getEndDate(),"00:00",StringUtils.EMPTY);
            }
            else {
                endDateToInfo = null;
                timeSetWrapper.setEndDate(null);
            }

            // set the UI time & am/pm fields to null in case they just had values:
            timeSetWrapper.setStartTime(null);
            timeSetWrapper.setStartTimeAmPm(null);
            timeSetWrapper.setEndTime(null);
            timeSetWrapper.setEndTimeAmPm(null);
        }
        else {
            String endTime = timeSetWrapper.getEndTime();
            String endTimeAmPm = timeSetWrapper.getEndTimeAmPm();
            Date endDate = timeSetWrapper.getEndDate();

            //If it's not date range, then set
            if (!timeSetWrapper.isDateRange()){
                endDate = timeSetWrapper.getStartDate();
                timeSetWrapper.setEndDate(null);
            }

            if (StringUtils.isBlank(endTime)){
                endTime = CalendarConstants.DEFAULT_END_TIME;
                endTimeAmPm = "PM";
                timeSetWrapper.setEndTime(endTime);
                timeSetWrapper.setEndTimeAmPm(endTimeAmPm);
            }

            endDateToInfo = CommonUtils.getDateWithTime(endDate,endTime,endTimeAmPm);
        }

        return endDateToInfo;
    }

    public void deleteTerm(List<AcademicTermWrapper> termWrapperList,int selectedIndex, String acalId) throws Exception{
        AcademicTermWrapper termWrapper = termWrapperList.get(selectedIndex);
        if (StringUtils.isNotBlank(termWrapper.getTermInfo().getId())){
            if (termWrapper.getKeyDatesGroupWrappers() != null){
                for (KeyDatesGroupWrapper groupWrapper : termWrapper.getKeyDatesGroupWrappers()){
                    for (KeyDateWrapper keyDateWrapper : groupWrapper.getKeydates()) {
                        if (StringUtils.isNotBlank(keyDateWrapper.getKeyDateInfo().getId())){
                            getAcalService().deleteKeyDate(keyDateWrapper.getKeyDateInfo().getId(),getContextInfo());
                        }
                    }
                }
            }
            getAcalService().deleteTerm(termWrapper.getTermInfo().getId(), getContextInfo());
        }
        termWrapperList.remove(selectedIndex);
    }

    public void deleteKeyDateGroup(AcademicTermWrapper termWrapper,int selectedIndex) throws Exception {
        KeyDatesGroupWrapper keydateGroup = termWrapper.getKeyDatesGroupWrappers().get(selectedIndex);
        if (keydateGroup != null){
            for (int index = 0; index < keydateGroup.getKeydates().size();index++) {
                deleteKeyDate(keydateGroup,index++);
            }
            termWrapper.getKeyDatesGroupWrappers().remove(keydateGroup);
        }

    }

    public void deleteKeyDate(KeyDatesGroupWrapper keyDatesGroup,int selectedIndex) throws Exception{
        KeyDateWrapper keydate = keyDatesGroup.getKeydates().get(selectedIndex);
        if (StringUtils.isNotBlank(keydate.getKeyDateInfo().getId())){
            getAcalService().deleteKeyDate(keydate.getKeyDateInfo().getId(),getContextInfo());
        }
        keyDatesGroup.getKeydates().remove(selectedIndex);
    }

    protected void processBeforeAddLine(View view, CollectionGroup collectionGroup, Object model, Object addLine) {
        if (addLine instanceof AcademicTermWrapper){
            AcademicTermWrapper newLine = (AcademicTermWrapper)addLine;
            try {
                TypeInfo termType = getAcalService().getTermType(((AcademicTermWrapper) addLine).getTermType(),getContextInfo());

                newLine.setTermNameForUI(termType.getName());
                SimpleDateFormat simpleDateformat=new SimpleDateFormat("yyyy");
                newLine.setName(termType.getName() + " " + simpleDateformat.format(newLine.getStartDate()));
                newLine.setTypeInfo(termType);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }else if (addLine instanceof KeyDatesGroupWrapper){
            KeyDatesGroupWrapper group = (KeyDatesGroupWrapper)addLine;
            if(StringUtils.isNotEmpty(group.getKeyDateGroupType())) {
                try {
                    TypeInfo termType = getTypeService().getType(group.getKeyDateGroupType(),getContextInfo());
                    group.setKeyDateGroupNameUI(termType.getName());
                    group.setTypeInfo(termType);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }else if (addLine instanceof HolidayCalendarInfo) {
            HolidayCalendarInfo inputLine = (HolidayCalendarInfo)addLine;
            try {
                System.out.println("HC id =" +inputLine.getId());

                HolidayCalendarInfo exists = getAcalService().getHolidayCalendar(inputLine.getId(), getContextInfo());

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
                HolidayCalendarInfo hcInfo = getAcalService().getHolidayCalendar(inputLine.getId(), getContextInfo());
                inputLine.setHolidayCalendarInfo(hcInfo);
                inputLine.setAdminOrgName(getAdminOrgNameById(hcInfo.getAdminOrgId()));
                StateInfo hcState = getAcalService().getHolidayCalendarState(hcInfo.getStateKey(), getContextInfo());
                inputLine.setStateName(hcState.getName());
                List<HolidayInfo> holidayInfoList = getAcalService().getHolidaysForHolidayCalendar(hcInfo.getId(), getContextInfo());
                for(HolidayInfo holidayInfo : holidayInfoList){
                    HolidayWrapper holiday = new HolidayWrapper(holidayInfo);
                    TypeInfo typeInfo = getAcalService().getHolidayType(holidayInfo.getTypeKey(), getContextInfo());
                    holiday.setTypeName(typeInfo.getName());
                    holidays.add(holiday);
                }
                inputLine.setHolidays(holidays);
            }catch (Exception e){
                throw new RuntimeException(e);
            }

        }else if (addLine instanceof AcalEventWrapper){
            AcalEventWrapper acalEventWrapper = (AcalEventWrapper)addLine;
            try {
                TypeInfo type = getTypeService().getType(acalEventWrapper.getEventTypeKey(), getContextInfo());
                acalEventWrapper.setEventTypeName(type.getName());
            }catch (Exception e) {
                throw new RuntimeException(e);
            }
            if (!CommonUtils.isValidDateRange(acalEventWrapper.getStartDate(),acalEventWrapper.getEndDate())){
               GlobalVariables.getMessageMap().putWarningForSectionId("acal-info-event","error.enroll.daterange.invalid",acalEventWrapper.getEventTypeName(),CommonUtils.formatDate(acalEventWrapper.getStartDate()),CommonUtils.formatDate(acalEventWrapper.getEndDate()));
            }
        }else if (addLine instanceof KeyDateWrapper){
            KeyDateWrapper keydate = (KeyDateWrapper)addLine;
            try {
                if(StringUtils.isNotEmpty(keydate.getKeyDateType())) {
                    TypeInfo type = getTypeService().getType(keydate.getKeyDateType(),getContextInfo());
                    keydate.setKeyDateNameUI(type.getName());
                    keydate.setTypeInfo(type);
                    if (!CommonUtils.isValidDateRange(keydate.getStartDate(),keydate.getEndDate())){
                        GlobalVariables.getMessageMap().putWarningForSectionId("acal-term-keydates", "error.enroll.daterange.invalid",keydate.getKeyDateNameUI(),CommonUtils.formatDate(keydate.getStartDate()),CommonUtils.formatDate(keydate.getEndDate()));
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if (addLine instanceof HolidayWrapper){
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

    public List<AcademicTermWrapper> populateTermWrappers(String acalId, boolean isCopy){

        List<AcademicTermWrapper> termWrappers = new ArrayList<AcademicTermWrapper>();

        try {
            List<TermInfo> termInfos = getAcalService().getTermsForAcademicCalendar(acalId, getContextInfo());
            for (TermInfo termInfo : termInfos) {
                AcademicTermWrapper termWrapper = populateTermWrapper(termInfo, isCopy);
                termWrappers.add(termWrapper);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return termWrappers;
    }

    public AcademicTermWrapper populateTermWrapper(TermInfo termInfo, boolean isCopy){
        try{

            TypeInfo type = getAcalService().getTermType(termInfo.getTypeKey(),getContextInfo());

            AcademicTermWrapper termWrapper = new AcademicTermWrapper(termInfo,isCopy);
            termWrapper.setTypeInfo(type);
            termWrapper.setTermNameForUI(type.getName());
            if (isCopy){
                termWrapper.setName(type.getName());
            }

            //Populate keydates
            List<KeyDateInfo> keydateList = getAcalService().getKeyDatesForTerm(termInfo.getId(),getContextInfo());
            List<TypeInfo> keyDateTypes = getTypeService().getAllowedTypesForType(termInfo.getTypeKey(),getContextInfo());

            Map<String,KeyDatesGroupWrapper> keyDateGroup = new HashMap<String,KeyDatesGroupWrapper>();

            for (KeyDateInfo keyDateInfo : keydateList) {
                KeyDateWrapper keyDateWrapper = new KeyDateWrapper(keyDateInfo,isCopy);
                type = getTypeService().getType(keyDateInfo.getTypeKey(),getContextInfo());
                keyDateWrapper.setTypeInfo(type);
                keyDateWrapper.setKeyDateNameUI(type.getName());

                addKeyDateGroup(keyDateTypes,keyDateWrapper,keyDateGroup);
            }

            for (KeyDatesGroupWrapper group : keyDateGroup.values()) {
                if (!group.getKeydates().isEmpty()){
                    termWrapper.getKeyDatesGroupWrappers().add(group);
                }
            }

            return termWrapper;
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    private void addKeyDateGroup(List<TypeInfo> keyDateTypes,KeyDateWrapper keyDateWrapper,Map<String,KeyDatesGroupWrapper> keyDateGroup){
        for (TypeInfo keyDateType : keyDateTypes) {
            try {
                // TODO
                List<TypeInfo> allowedTypes = getTypesForGroupType(keyDateType.getKey());
                for (TypeInfo allowedType : allowedTypes) {
                    if (StringUtils.equals(allowedType.getKey(),keyDateWrapper.getKeyDateType())){
                        KeyDatesGroupWrapper keyDatesGroup = keyDateGroup.get(keyDateType.getKey());
                        if (keyDatesGroup == null){
                            keyDatesGroup = new KeyDatesGroupWrapper(keyDateType.getKey(),keyDateType.getName());
                            keyDateGroup.put(keyDateType.getKey(),keyDatesGroup);
                        }
                        keyDatesGroup.getKeydates().add(keyDateWrapper);
                        break;
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public AcademicCalendarService getAcalService() {
           if(acalService == null) {
             acalService = (AcademicCalendarService) GlobalResourceLoader.getService(new QName(AcademicCalendarServiceConstants.NAMESPACE, AcademicCalendarServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return this.acalService;
    }

    public TypeService getTypeService() {
           if(typeService == null) {
             typeService = (TypeService) GlobalResourceLoader.getService(new QName(TypeServiceConstants.NAMESPACE, TypeServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return this.typeService;
    }

    public ContextInfo getContextInfo() {
        if (null == contextInfo) {
            contextInfo = new ContextInfo();
            contextInfo.setAuthenticatedPrincipalId(GlobalVariables.getUserSession().getPrincipalId());
            contextInfo.setPrincipalId(GlobalVariables.getUserSession().getPrincipalId());
            LocaleInfo localeInfo = new LocaleInfo();
            localeInfo.setLocaleLanguage(Locale.getDefault().getLanguage());
            localeInfo.setLocaleRegion(Locale.getDefault().getCountry());
            contextInfo.setLocale(localeInfo);
        }
        return contextInfo;
    }

    private String getAdminOrgNameById(String id){
        //TODO: hard-coded for now, going to call OrgService
        String adminOrgName = null;
        Map<String, String> allHcOrgs = new HashMap<String, String>();
        allHcOrgs.put("102", "Registrar's Office");

        if(allHcOrgs.containsKey(id)){
            adminOrgName = allHcOrgs.get(id);
        }

        return adminOrgName;
    }

    public List<TypeInfo> getHolidayTypes() throws InvalidParameterException, MissingParameterException, PermissionDeniedException, OperationFailedException {
        if(holidayTypes == null) {
            holidayTypes = Collections.unmodifiableList(getAcalService().getHolidayTypes(getContextInfo()));
        }
        return holidayTypes;
    }

    public List<TypeInfo> getTypesForGroupType(String groupTypeKey) throws InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException {

        List<TypeInfo> types = typesByGroupTypeMap.get(groupTypeKey);
        if(types == null) {
            types = Collections.unmodifiableList(getTypeService().getTypesForGroupType(groupTypeKey, getContextInfo()));
            typesByGroupTypeMap.put(groupTypeKey, types);
        }
        return types;
    }
}
