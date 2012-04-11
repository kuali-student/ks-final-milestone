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
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.core.api.util.RiceKeyConstants;
import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.uif.container.CollectionGroup;
import org.kuali.rice.krad.uif.control.SelectControl;
import org.kuali.rice.krad.uif.field.InputField;
import org.kuali.rice.krad.uif.service.impl.ViewHelperServiceImpl;
import org.kuali.rice.krad.uif.util.ObjectPropertyUtils;
import org.kuali.rice.krad.uif.view.View;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.student.common.util.UUIDHelper;
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
import org.kuali.student.r2.common.util.constants.AtpServiceConstants;
import org.kuali.student.r2.common.util.constants.TypeServiceConstants;
import org.kuali.student.r2.core.state.dto.StateInfo;
import org.kuali.student.r2.core.type.dto.TypeInfo;
import org.kuali.student.r2.core.type.dto.TypeTypeRelationInfo;
import org.kuali.student.r2.core.type.service.TypeService;
import org.kuali.student.mock.utilities.TestHelper;

import javax.xml.namespace.QName;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.kuali.rice.core.api.criteria.PredicateFactory.equalIgnoreCase;


/**
 * This class implement ViewHelperServiceImpl for  all AcademicCalendar views
 *
 * @author Kuali Student Team
 */
public class AcademicCalendarViewHelperServiceImpl extends ViewHelperServiceImpl implements AcademicCalendarViewHelperService {

    private AcademicCalendarService acalService;
    private ContextInfo contextInfo;
    private TypeService typeService;

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

        for (HolidayWrapper holidayWrapper : holidays){

            HolidayInfo holidayInfo = holidayWrapper.getHolidayInfo();
            holidayWrapper.setTypeName(getHolidayTypeName(holidayWrapper.getTypeKey()));
            holidayInfo.setStateKey(AtpServiceConstants.MILESTONE_DRAFT_STATE_KEY);
            holidayInfo.setDescr(CommonUtils.buildDesc("no description"));
            holidayInfo.setIsAllDay(holidayWrapper.isAllDay());
            holidayInfo.setIsInstructionalDay(holidayWrapper.isInstructional());
            holidayInfo.setIsDateRange(holidayWrapper.isDateRange());
            holidayInfo.setStartDate(holidayWrapper.getStartDate());
            holidayInfo.setEndDate(holidayWrapper.getEndDate());
            holidayInfo.setName(holidayWrapper.getTypeName());
            holidayInfo.setStartDate(getStartDateWithUpdatedTime(holidayWrapper,true));
            holidayInfo.setEndDate(getEndDateWithUpdatedTime(holidayWrapper));

            if (StringUtils.isBlank(holidayInfo.getId())){
                HolidayInfo createdHoliday = getAcalService().createHoliday(hcForm.getHolidayCalendarInfo().getId(), holidayWrapper.getTypeKey(), holidayInfo, getContextInfo());
                holidayWrapper.setHolidayInfo(getAcalService().getHoliday(createdHoliday.getId(),getContextInfo()));
            }else{
                HolidayInfo updatedHoliday = getAcalService().updateHoliday(holidayInfo.getId(),holidayInfo, getContextInfo());
                holidayWrapper.setHolidayInfo(getAcalService().getHoliday(updatedHoliday.getId(),getContextInfo()));
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
        HolidayInfo holidayInfo = hcForm.getHolidays().get(selectedIndex).getHolidayInfo();
        if (StringUtils.isNotBlank(holidayInfo.getId())){
            getAcalService().deleteHoliday(holidayInfo.getId(), getContextInfo());
        }
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
            List<TypeInfo> types = getAcalService().getHolidayTypes(getContextInfo());
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
        //create dummy descr for db AtpEntity.plain is not nullable
        RichTextInfo rti = new RichTextInfo();
        rti.setPlain(acalInfo.getName());
        acalInfo.setDescr(rti);
        AcademicCalendarInfo newAcal = getAcalService().createAcademicCalendar(AcademicCalendarServiceConstants.ACADEMIC_CALENDAR_TYPE_KEY, acalInfo, getContextInfo());
        return newAcal;
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

    public AcademicCalendarInfo copyToCreateAcademicCalendar(AcademicCalendarForm form) throws Exception {
           AcademicCalendarInfo orgAcalInfo = form.getOrgAcalInfo();
           AcademicCalendarInfo newAcalInfo = form.getAcademicCalendarInfo();
           ContextInfo context = getContextInfo();
        
           // 1. copy over events                     
           List<AcalEventInfo> orgEventInfoList= getAcalService().getAcalEventsForAcademicCalendar(orgAcalInfo.getId(), getContextInfo());
           List<AcalEventWrapper> newEventList = new ArrayList<AcalEventWrapper>();
           for (AcalEventInfo orgEventInfo : orgEventInfoList){
               AcalEventWrapper newEvent= new AcalEventWrapper();
               newEvent.copy(orgEventInfo);
               newEventList.add(newEvent);
           }
           form.setEvents(newEventList);          

          // 2. copy over terms
          List<TermInfo> orgTermInfoList = getAcalService().getTermsForAcademicCalendar(orgAcalInfo.getId(), context);
          List<AcademicTermWrapper> newTermList = new ArrayList<AcademicTermWrapper>();
          for(TermInfo orgTermInfo : orgTermInfoList){              
              TermInfo newTermInfo = new TermInfo();
              newTermInfo.setTypeKey(orgTermInfo.getTypeKey());
              AcademicTermWrapper newTermWrapper = new AcademicTermWrapper(newTermInfo);
              newTermWrapper.setTermType(orgTermInfo.getTypeKey());
              TypeInfo type = getAcalService().getTermType(newTermInfo.getTypeKey(),context);
              newTermWrapper.setTypeInfo(type);
              newTermWrapper.setTermNameForUI(type.getName());

              //Populate keydates and copy over
              List<KeyDateInfo> keydateList = getAcalService().getKeyDatesForTerm(orgTermInfo.getId(),context);

              TypeInfo registrationGroup = getTypeService().getType(CalendarConstants.KEY_DATE_GROUP_TYPE_REGISTRATION_PERIOD,context);
              TypeInfo curriculumGroup = getTypeService().getType(CalendarConstants.KEY_DATE_GROUP_TYPE_CURRICULUM,context);

              KeyDatesGroupWrapper registrationWrapper = new KeyDatesGroupWrapper(CalendarConstants.KEY_DATE_GROUP_TYPE_REGISTRATION_PERIOD,registrationGroup.getName());
              KeyDatesGroupWrapper curriculumWrapper = new KeyDatesGroupWrapper(CalendarConstants.KEY_DATE_GROUP_TYPE_CURRICULUM,curriculumGroup.getName());

              for (KeyDateInfo orgKeyDateInfo : keydateList) {
                  KeyDateWrapper keyDateWrapper = new KeyDateWrapper();
                  keyDateWrapper.copy(orgKeyDateInfo);
                  type = getTypeService().getType(orgKeyDateInfo.getTypeKey(),context);
                  keyDateWrapper.setTypeInfo(type);
                  keyDateWrapper.setKeyDateNameUI(type.getName());

                  List<TypeTypeRelationInfo> registrationRelations = getTypeService().getTypeTypeRelationsByOwnerAndType(CalendarConstants.KEY_DATE_GROUP_TYPE_REGISTRATION_PERIOD,null,context);
                  List<TypeTypeRelationInfo> curriculumRelations = getTypeService().getTypeTypeRelationsByOwnerAndType(CalendarConstants.KEY_DATE_GROUP_TYPE_CURRICULUM,null,context);

                  if (isRelationExists(registrationRelations,orgKeyDateInfo.getTypeKey())){
                      registrationWrapper.getKeydates().add(keyDateWrapper);
                  }else if (isRelationExists(curriculumRelations,orgKeyDateInfo.getTypeKey())){
                      curriculumWrapper.getKeydates().add(keyDateWrapper);
                  }
              }

              if (!registrationWrapper.getKeydates().isEmpty()){
                  newTermWrapper.getKeyDatesGroupWrappers().add(registrationWrapper);
              }

              if (!curriculumWrapper.getKeydates().isEmpty()){
                  newTermWrapper.getKeyDatesGroupWrappers().add(curriculumWrapper);
              }

              newTermList.add(newTermWrapper);
          }
         form.setTermWrapperList(newTermList);

          // 3. do NOT copy over any AC-HC relationship
//        AcademicCalendarInfo newAcalInfo =
//                getAcalService().copyAcademicCalendar(form.getAcademicCalendarInfo().getId(), form.getNewCalendarStartDate(),
//                        form.getNewCalendarEndDate(), getContextInfo());
//        if (null != newAcalInfo) {
//            newAcalInfo.setName(form.getNewCalendarName());
//        }
        return newAcalInfo;
    }

    public List<AcalEventWrapper> getEventsForAcademicCalendar(AcademicCalendarForm acalForm) throws Exception {
        AcademicCalendarInfo acalInfo = acalForm.getAcademicCalendarInfo();
        List<AcalEventInfo> eventInfos = getAcalService().getAcalEventsForAcademicCalendar(acalInfo.getId(), getContextInfo());
        List<AcalEventWrapper> events = new ArrayList<AcalEventWrapper>();
        for (AcalEventInfo eventInfo: eventInfos) {
            AcalEventWrapper event = assembleEventWrapperFromEventInfo(eventInfo);
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

    private AcalEventWrapper assembleEventWrapperFromEventInfo (AcalEventInfo acalEventInfo) throws Exception {
        AcalEventWrapper event  = new AcalEventWrapper(acalEventInfo);
        TypeInfo type = getTypeService().getType(event.getEventTypeKey(), getContextInfo());
        event.setEventTypeName(type.getName());
        return event;
    }

    private AcalEventInfo assembleEventInfoFromWrapper(AcalEventWrapper eventWrapper) throws Exception{
        AcalEventInfo eventInfo = eventWrapper.getAcalEventInfo();

        //create dummy descr for db MilestoneEntity.plain is not nullable
        RichTextInfo rti = new RichTextInfo();
        rti.setPlain(eventWrapper.getEventTypeKey());
        eventInfo.setDescr(rti);
        eventInfo.setStateKey(AtpServiceConstants.MILESTONE_DRAFT_STATE_KEY);
        eventInfo.setTypeKey(eventWrapper.getEventTypeKey());
        eventInfo.setStartDate(eventWrapper.getStartDate());
        eventInfo.setEndDate(eventWrapper.getEndDate());
        eventInfo.setIsAllDay(eventWrapper.isAllDay());
        eventInfo.setIsDateRange(eventWrapper.isDateRange());
        eventInfo.setStartDate(getStartDateWithUpdatedTime(eventWrapper,true));
        eventInfo.setEndDate(getEndDateWithUpdatedTime(eventWrapper));
        return eventInfo;
    }

    protected boolean performAddLineValidation(View view, CollectionGroup collectionGroup, Object model,
                                               Object addLine) {
        boolean isValid = true;
        if (addLine instanceof AcalEventWrapper){
            AcalEventWrapper newEvent = (AcalEventWrapper)addLine;
//            if (!checkEvent(newEvent))
//                return false;

            if (model instanceof AcademicCalendarForm){
                AcademicCalendarForm acalForm = (AcademicCalendarForm) model;
                List<AcalEventWrapper> events = acalForm.getEvents();
                if(events != null && !events.isEmpty()){
                    for(AcalEventWrapper event : events){
                        if(isDuplicateEvent(newEvent, event)){
                            //TODO:change to  putError, when error reload fixed
                            GlobalVariables.getMessageMap().putInfo(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM, "ERROR: The adding event is already in the collection.");
                            return false;
                        }
                    }
                }
            }
        }
        else{
            isValid = super.performAddLineValidation(view, collectionGroup, model, addLine);
        }
        return isValid;
    }



    /**
     * Make sure
     * 1) the eventType is not empty
     * 2) the start date is later than the end date
     * Set IsDateRange and IsAllDay based on the input
     */
    private boolean checkEvent(AcalEventWrapper event) {
        boolean valid = true;
        Date startDate = event.getStartDate();
        Date endDate = event.getEndDate();
        String startTime = event.getStartTime();
        String endTime = event.getEndTime();
        AcalEventInfo acalEventInfo = event.getAcalEventInfo();

        if (endDate == null)  {
            acalEventInfo.setIsDateRange(false);

            if(StringUtils.isBlank(startTime)){
                acalEventInfo.setIsAllDay(true);
            }
        }
        else {
            int timeDiff = startDate.compareTo(endDate);
            if(timeDiff > 0) {
                //TODO:change to  putError, when error reload fixed
                GlobalVariables.getMessageMap().putInfo(KRADConstants.GLOBAL_ERRORS,
                        RiceKeyConstants.ERROR_CUSTOM, "ERROR: The adding event start date should not be later than the end date.");
                return false;
            }else if (timeDiff == 0 ) {
                acalEventInfo.setIsDateRange(false);
            }else {
                acalEventInfo.setIsDateRange(true);
            }

            if (StringUtils.isBlank(startTime) & StringUtils.isBlank(endTime)) {
                acalEventInfo.setIsAllDay(true);
            }else if(StringUtils.isNotEmpty(startTime)){
                acalEventInfo.setIsAllDay(false);
            }
        }

        return valid;
    }

    private boolean isDuplicateEvent(AcalEventWrapper newEvent, AcalEventWrapper sourceEvent){
        return (newEvent.getEventTypeKey().equals(sourceEvent.getEventTypeKey()));
    }

    public void populateKeyDateTypes(InputField field, AcademicCalendarForm acalForm) {

        //As the keydate type select wont display for all the colletion lines, skip if it's not add row.
        boolean isAddLine = BooleanUtils.toBoolean((Boolean)field.getContext().get(UifConstants.ContextVariableNames.IS_ADD_LINE));
        if (!isAddLine) {
            return;
        }

        List keyValues = new ArrayList();
        keyValues.add(new ConcreteKeyValue("", ""));

        CollectionGroup collectionGroup = (CollectionGroup)field.getContext().get(UifConstants.ContextVariableNames.PARENT);
        KeyDatesGroupWrapper groupWrapper = ObjectPropertyUtils.getPropertyValue(acalForm,collectionGroup.getBindingInfo().getBindByNamePrefix());

        List<String> existingKeyDateTypes = new ArrayList();
        for(KeyDateWrapper keyDateWrapper : groupWrapper.getKeydates()){
            existingKeyDateTypes.add(keyDateWrapper.getKeyDateType());
        }

        if (groupWrapper != null && StringUtils.isNotBlank(groupWrapper.getKeyDateGroupType())){
            try {
                List<TypeInfo> types = getTypeService().getTypesForGroupType(groupWrapper.getKeyDateGroupType(), getContextInfo());
                for (TypeInfo type : types) {
                    if (!existingKeyDateTypes.contains(type.getKey())){
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

        //As the keydate type select wont display for all the colletion lines, skip if it's not add row.
        boolean isAddLine = BooleanUtils.toBoolean((Boolean)field.getContext().get(UifConstants.ContextVariableNames.IS_ADD_LINE));
        if (!isAddLine) {
            return;
        }

        List keyValues = new ArrayList();
        keyValues.add(new ConcreteKeyValue("", ""));

        CollectionGroup collectionGroup = (CollectionGroup)field.getContext().get(UifConstants.ContextVariableNames.COLLECTION_GROUP);
        AcademicTermWrapper termWrapper = ObjectPropertyUtils.getPropertyValue(acalForm,collectionGroup.getBindingInfo().getBindByNamePrefix());

        try {
            List<TypeInfo> keyDateGroupTypes = getAcalService().getKeyDateTypesForTermType(termWrapper.getTermType(),getContextInfo());
            for (TypeInfo keyDateGroupType : keyDateGroupTypes) {
                keyValues.add(new ConcreteKeyValue(keyDateGroupType.getKey(),keyDateGroupType.getName()));
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
            GlobalVariables.getMessageMap().putErrorForSectionId("KS-HolidayCalendar-MetaSection", "error.enroll.daterange.invalid","Calendar",CommonUtils.formatDate(hcInfo.getStartDate()),CommonUtils.formatDate(hcInfo.getEndDate()));
        }

        //Validate Events
        for (HolidayWrapper holiday : hcForm.getHolidays()) {
            if (!CommonUtils.isDateWithinRange(hcInfo.getStartDate(),hcInfo.getEndDate(),holiday.getStartDate()) ||
                !CommonUtils.isDateWithinRange(hcInfo.getStartDate(),hcInfo.getEndDate(),holiday.getEndDate())){
                GlobalVariables.getMessageMap().putInfoForSectionId("KS-HolidayCalendar-HolidaySection", "error.enroll.holiday.dateNotInHcal",holiday.getTypeName());
            }
        }

    }

    public void populateHolidayCalendarDefaults(HolidayCalendarForm hcForm){

        for (HolidayWrapper holidayWrapper : hcForm.getHolidays()) {
            holidayWrapper.getHolidayInfo().setStartDate(getStartDateWithUpdatedTime(holidayWrapper,false));
            holidayWrapper.getHolidayInfo().setEndDate(getEndDateWithUpdatedTime(holidayWrapper));
        }
    }

    public void populateAcademicCalendarDefaults(AcademicCalendarForm acalForm){

        for (AcalEventWrapper eventWrapper : acalForm.getEvents()) {
            eventWrapper.getAcalEventInfo().setStartDate(getStartDateWithUpdatedTime(eventWrapper,false));
            eventWrapper.getAcalEventInfo().setEndDate(getEndDateWithUpdatedTime(eventWrapper));
        }

        for (AcademicTermWrapper academicTermWrapper : acalForm.getTermWrapperList()) {
            for (KeyDatesGroupWrapper keyDatesGroupWrapper : academicTermWrapper.getKeyDatesGroupWrappers()){
                for(KeyDateWrapper keyDateWrapper : keyDatesGroupWrapper.getKeydates()){
                    keyDateWrapper.getKeyDateInfo().setStartDate(getStartDateWithUpdatedTime(keyDateWrapper,false));
                    keyDateWrapper.getKeyDateInfo().setEndDate(getEndDateWithUpdatedTime(keyDateWrapper));
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
                GlobalVariables.getMessageMap().putInfoForSectionId("acal-info-event", "error.enroll.event.dateNotInAcal",eventWrapper.getEventTypeName());
            }
        }

        //Validate Terms and keydates
        validateTerms(acalForm.getTermWrapperList(),acal);

    }

    private boolean isValidAcalName(AcademicCalendarInfo acal){

        QueryByCriteria.Builder qBuilder = QueryByCriteria.Builder.create();
        qBuilder.setPredicates(equalIgnoreCase("name", acal.getName()));
        try {
            List<AcademicCalendarInfo> acals = getAcalService().searchForAcademicCalendars(qBuilder.build(),getContextInfo());
            boolean valid = acals.isEmpty();
            //Make sure it's not the same Acal which is being edited by the user
            if (!valid && StringUtils.isNotBlank(acal.getId())){
                for (AcademicCalendarInfo academicCalendarInfo : acals) {
                    if (StringUtils.equals(academicCalendarInfo.getTypeKey(),AcademicCalendarServiceConstants.ACADEMIC_CALENDAR_TYPE_KEY)){
                        if (!StringUtils.equals(academicCalendarInfo.getId(),acal.getId())){
                            valid = false;
                            break;
                        }
                    }
                    valid = true;
                }
            }else{
                valid = true;
            }
            return valid;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isValidHcalName(HolidayCalendarInfo hcal){

        QueryByCriteria.Builder qBuilder = QueryByCriteria.Builder.create();
        qBuilder.setPredicates(equalIgnoreCase("name", hcal.getName()));
        try {
            List<HolidayCalendarInfo> hcals = getAcalService().searchForHolidayCalendars(qBuilder.build(), getContextInfo());
            boolean valid = hcals.isEmpty();
            //Make sure it's not the same Hcal which is being edited by the user
            if (!valid && StringUtils.isNotBlank(hcal.getId())){
                for (HolidayCalendarInfo hc : hcals) {
                    if (StringUtils.equals(hc.getTypeKey(),AcademicCalendarServiceConstants.HOLIDAY_CALENDAR_TYPE_KEY)){
                        if (!StringUtils.equals(hc.getId(),hcal.getId())){
                            valid = false;
                            break;
                        }
                    }
                    valid = true;
                }
            }else{
                valid = true;
            }
            return valid;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void validateTerms(List<AcademicTermWrapper> termWrapper,AcademicCalendarInfo acal) {
        int index1 = 0;
        for (AcademicTermWrapper academicTermWrapper : termWrapper) {
            index1++;
            int index2 = 0;
            //Validate duplicate term name
            for (AcademicTermWrapper wrapper : termWrapper) {
                index2++;
                if (wrapper != academicTermWrapper){
                    if (StringUtils.equalsIgnoreCase(wrapper.getName(),academicTermWrapper.getName())){
                        if (index1 < index2){
                            GlobalVariables.getMessageMap().putErrorForSectionId("acal-term", "error.enroll.term.duplicateName",""+ NumberUtils.min(new int[]{index1,index2}),""+NumberUtils.max(new int[]{index1,index2}));
                        }
                    }
                }
            }

            if (!CommonUtils.isValidDateRange(academicTermWrapper.getStartDate(),academicTermWrapper.getEndDate())){
                GlobalVariables.getMessageMap().putErrorForSectionId("acal-term", "error.enroll.daterange.invalid",academicTermWrapper.getName(),CommonUtils.formatDate(academicTermWrapper.getStartDate()),CommonUtils.formatDate(academicTermWrapper.getEndDate()));
            }

            if (!CommonUtils.isDateWithinRange(acal.getStartDate(),acal.getEndDate(),academicTermWrapper.getStartDate()) ||
                !CommonUtils.isDateWithinRange(acal.getStartDate(),acal.getEndDate(),academicTermWrapper.getEndDate())){
                GlobalVariables.getMessageMap().putErrorForSectionId("acal-term", "error.enroll.term.dateNotInAcal",academicTermWrapper.getName());
            }

            for (KeyDatesGroupWrapper keyDatesGroupWrapper : academicTermWrapper.getKeyDatesGroupWrappers()){
                for(KeyDateWrapper keyDateWrapper : keyDatesGroupWrapper.getKeydates()){
                    if (!CommonUtils.isDateWithinRange(academicTermWrapper.getStartDate(),academicTermWrapper.getEndDate(),keyDateWrapper.getStartDate()) ||
                        !CommonUtils.isDateWithinRange(academicTermWrapper.getStartDate(),academicTermWrapper.getEndDate(),keyDateWrapper.getEndDate())){
                        GlobalVariables.getMessageMap().putInfoForSectionId("acal-term-keydates", "error.enroll.keydate.dateNotInTerm",keyDateWrapper.getKeyDateNameUI(),academicTermWrapper.getName());
                    }
                }
            }

        }
    }

    public void populateInstructionalDays(List<AcademicTermWrapper> termWrapperList)
    throws Exception {
         for (AcademicTermWrapper termWrapper : termWrapperList) {
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
    }

    public void saveTerm(AcademicTermWrapper termWrapper, String acalId) throws Exception {

        boolean isNewTerm = false;
        if (StringUtils.isBlank(termWrapper.getTermInfo().getId())){
            TermInfo newTerm = termWrapper.getTermInfo();
            newTerm.setStateKey(AtpServiceConstants.ATP_DRAFT_STATE_KEY);
            newTerm.setId(UUIDHelper.genStringUUID());
            RichTextInfo desc = new RichTextInfo();
            desc.setPlain("Test");
            newTerm.setDescr(desc);
            isNewTerm = true;
        }

        TermInfo term = termWrapper.getTermInfo();

        term.setEndDate(termWrapper.getEndDate());
        term.setStartDate(termWrapper.getStartDate());
        term.setName(termWrapper.getName());
        term.setTypeKey(termWrapper.getTermType());

        if (isNewTerm){
            TermInfo newTerm = getAcalService().createTerm(termWrapper.getTermType(),term,getContextInfo());
            termWrapper.setTermInfo(getAcalService().getTerm(newTerm.getId(),getContextInfo()));
            getAcalService().addTermToAcademicCalendar(acalId,termWrapper.getTermInfo().getId(),getContextInfo());
        }else{
            TermInfo updatedTerm = getAcalService().updateTerm(term.getId(),term,getContextInfo());
            termWrapper.setTermInfo(getAcalService().getTerm(updatedTerm.getId(),getContextInfo()));
        }

        //Keydates
        if (termWrapper.getKeyDatesGroupWrappers() != null){
            for (KeyDatesGroupWrapper groupWrapper : termWrapper.getKeyDatesGroupWrappers()){
                for (KeyDateWrapper keyDateWrapper : groupWrapper.getKeydates()) {
                    boolean isNewKeyDate = false;
                    if (StringUtils.isBlank(keyDateWrapper.getKeyDateInfo().getId())){
                        isNewKeyDate = true;
                        KeyDateInfo keyDate = new KeyDateInfo();
                        keyDate.setStateKey(AtpServiceConstants.MILESTONE_DRAFT_STATE_KEY);
                        keyDate.setId(UUIDHelper.genStringUUID());
                        RichTextInfo desc = new RichTextInfo();
                        desc.setPlain("Test");
                        keyDate.setDescr(desc);
                        keyDateWrapper.setKeyDateInfo(keyDate);
                    }

                    KeyDateInfo keyDate = keyDateWrapper.getKeyDateInfo();

                    keyDate.setTypeKey(keyDateWrapper.getKeyDateType());
                    //Add by Bonnie
                    keyDate.setName(keyDateWrapper.getKeyDateNameUI());
                    keyDate.setStartDate(keyDateWrapper.getStartDate());
                    keyDate.setEndDate(keyDateWrapper.getEndDate());
                    keyDate.setIsAllDay(keyDateWrapper.isAllDay());
                    keyDate.setIsDateRange(keyDateWrapper.isDateRange());
                    keyDate.setStartDate(getStartDateWithUpdatedTime(keyDateWrapper,true));
                    keyDate.setEndDate(getEndDateWithUpdatedTime(keyDateWrapper));

                    if (isNewKeyDate){
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
                return updateTime(timeSetWrapper.getStartDate(),startTime,startTimeApPm);
            }else{
                timeSetWrapper.setStartTime("12:00");
                timeSetWrapper.setStartTimeAmPm("AM");
                return updateTime(timeSetWrapper.getStartDate(),timeSetWrapper.getStartTime(),timeSetWrapper.getStartTimeAmPm());
            }
//                    return updateTime(timeSetWrapper.getStartDate(),startTime,startTimeApPm);
        }else{
//            timeSetWrapper.setStartTime("12:00");
//            timeSetWrapper.setStartTimeAmPm("AM");
            return timeSetWrapper.getStartDate();
        }

    }

    private Date getEndDateWithUpdatedTime(TimeSetWrapper timeSetWrapper){
        if (!timeSetWrapper.isAllDay()){
            String endTime = timeSetWrapper.getEndTime();
            String endTimeApPm = timeSetWrapper.getEndTimeAmPm();
            Date endDate = timeSetWrapper.getEndDate();
            //If it's not date range..
            if (!timeSetWrapper.isDateRange()){
                endDate = timeSetWrapper.getStartDate();
                timeSetWrapper.setEndDate(endDate);
            }
            if (StringUtils.isBlank(endTime)){
                endTime = CalendarConstants.DEFAULT_END_TIME;
                endTimeApPm = "PM";
                timeSetWrapper.setEndTime(endTime);
                timeSetWrapper.setEndTimeAmPm(endTimeApPm);
            }
            return updateTime(endDate,endTime,endTimeApPm);
        }else{
            if (timeSetWrapper.isDateRange()){
                //just clearing out any time already set in end date
                return updateTime(timeSetWrapper.getEndDate(),"00:00",StringUtils.EMPTY );
            }else{
                return updateTime(timeSetWrapper.getStartDate(),"00:00",StringUtils.EMPTY );
            }
        }
    }

    private Date updateTime(Date date,String time,String amPm) {

        // Get Calendar object set to the date and time of the given Date object
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        // Set time fields to zero
        if (StringUtils.isNotBlank(time)){
            cal.set(Calendar.HOUR, Integer.parseInt(StringUtils.substringBefore(time,":")));
            cal.set(Calendar.MINUTE, Integer.parseInt(StringUtils.substringAfter(time,":")));
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            if (StringUtils.isNotBlank(amPm)){
                if (StringUtils.equalsIgnoreCase(amPm,"am")){
                    cal.set(Calendar.AM_PM,Calendar.AM);
                }else {
                    cal.set(Calendar.AM_PM,Calendar.PM);
                }
            }
        }

        return cal.getTime();
    }

    public void setTermOfficial(AcademicTermWrapper termWrapper, String acalId) throws Exception{
        saveTerm(termWrapper, acalId);

        TermInfo term = termWrapper.getTermInfo();
        term.setStateKey(AtpServiceConstants.ATP_OFFICIAL_STATE_KEY);
        term = getAcalService().updateTerm(term.getId(),term,getContextInfo());

        termWrapper.setTermInfo(getAcalService().getTerm(term.getId(),getContextInfo()));

        if (termWrapper.getKeyDatesGroupWrappers() != null){
            for (KeyDatesGroupWrapper groupWrapper : termWrapper.getKeyDatesGroupWrappers()){
                for (KeyDateWrapper keyDateWrapper : groupWrapper.getKeydates()) {
                     keyDateWrapper.getKeyDateInfo().setStateKey(AtpServiceConstants.MILESTONE_OFFICIAL_STATE_KEY);
                     KeyDateInfo updatedKeyDate = getAcalService().updateKeyDate(keyDateWrapper.getKeyDateInfo().getId(),keyDateWrapper.getKeyDateInfo(),getContextInfo());
                     keyDateWrapper.setKeyDateInfo(getAcalService().getKeyDate(updatedKeyDate.getId(),getContextInfo()));
                }
            }
        }

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
                TypeInfo termType = getAcalService().getTermType(((AcademicTermWrapper) addLine).getTermType(),TestHelper.getContext1());

                newLine.setTermNameForUI(termType.getName());
                SimpleDateFormat simpleDateformat=new SimpleDateFormat("yyyy");
                newLine.setName(termType.getName() + " " + simpleDateformat.format(newLine.getStartDate()));
                newLine.setTypeInfo(termType);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }else if (addLine instanceof KeyDatesGroupWrapper){
            KeyDatesGroupWrapper group = (KeyDatesGroupWrapper)addLine;
            try {
                TypeInfo termType = getTypeService().getType(group.getKeyDateGroupType(),getContextInfo());
                group.setKeyDateGroupNameUI(termType.getName());
                group.setTypeInfo(termType);
            } catch (Exception e) {
                throw new RuntimeException(e);
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
                TypeInfo type = getTypeService().getType(keydate.getKeyDateType(),TestHelper.getContext1());
                keydate.setKeyDateNameUI(type.getName());
                keydate.setTypeInfo(type);
                if (!CommonUtils.isValidDateRange(keydate.getStartDate(),keydate.getEndDate())){
                    GlobalVariables.getMessageMap().putWarningForSectionId("acal-term-keydates", "error.enroll.daterange.invalid",keydate.getKeyDateNameUI(),CommonUtils.formatDate(keydate.getStartDate()),CommonUtils.formatDate(keydate.getEndDate()));
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

    public List<AcademicTermWrapper> loadTerms(String acalId){

        List<AcademicTermWrapper> termWrappers = new ArrayList();

        try {
            List<TermInfo> termInfos = getAcalService().getTermsForAcademicCalendar(acalId, getContextInfo());
            for (TermInfo termInfo : termInfos) {
                TypeInfo type = getAcalService().getTermType(termInfo.getTypeKey(),getContextInfo());
                AcademicTermWrapper termWrapper = new AcademicTermWrapper(termInfo);
                termWrapper.setTypeInfo(type);
                termWrapper.setTermNameForUI(type.getName());

                //Populate keydates
                List<KeyDateInfo> keydateList = getAcalService().getKeyDatesForTerm(termInfo.getId(),getContextInfo());
                List<TypeInfo> keyDateTypes = getTypeService().getAllowedTypesForType(termInfo.getTypeKey(),getContextInfo());

                Map<String,KeyDatesGroupWrapper> keyDateGroup = new HashMap();

                for (KeyDateInfo keyDateInfo : keydateList) {
                    KeyDateWrapper keyDateWrapper = new KeyDateWrapper(keyDateInfo);
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

                termWrappers.add(termWrapper);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return termWrappers;
    }

    private void addKeyDateGroup(List<TypeInfo> keyDateTypes,KeyDateWrapper keyDateWrapper,Map<String,KeyDatesGroupWrapper> keyDateGroup){
        for (TypeInfo keyDateType : keyDateTypes) {
            try {
                List<TypeInfo> allowedTypes = getTypeService().getTypesForGroupType(keyDateType.getKey(),getContextInfo());
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

    private boolean isRelationExists(List<TypeTypeRelationInfo> registrationRelations,String keyDateType){
        for (TypeTypeRelationInfo registrationRelation : registrationRelations) {
            if (StringUtils.equals(registrationRelation.getRelatedTypeKey(), keyDateType)){
                return true;
            }
        }
        return false;
    }

    public AcademicCalendarService getAcalService() {
           if(acalService == null) {
             acalService = (AcademicCalendarService) GlobalResourceLoader.getService(new QName(AcademicCalendarServiceConstants.NAMESPACE, AcademicCalendarServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return this.acalService;
    }

    public TypeService getTypeService() {
           if(typeService == null) {
             typeService = (TypeService) GlobalResourceLoader.getService(new QName(TypeServiceConstants.NAMESPACE, "TypeService"));
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
}
