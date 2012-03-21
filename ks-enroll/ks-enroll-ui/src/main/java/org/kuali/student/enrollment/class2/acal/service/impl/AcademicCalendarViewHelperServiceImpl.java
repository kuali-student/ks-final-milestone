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

import java.text.SimpleDateFormat;
import java.util.*;
import javax.xml.namespace.QName;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.joda.time.DateTime;
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
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.common.util.constants.AtpServiceConstants;
import org.kuali.student.r2.common.util.constants.TypeServiceConstants;
import org.kuali.student.r2.core.state.dto.StateInfo;
import org.kuali.student.r2.core.type.dto.TypeInfo;
import org.kuali.student.r2.core.type.dto.TypeTypeRelationInfo;
import org.kuali.student.r2.core.type.service.TypeService;
import org.kuali.student.test.utilities.TestHelper;


/**
 * This class implement ViewHelperServiceImpl for  all AcademicCalendar views
 *
 * @author Kuali Student Team
 */
public class AcademicCalendarViewHelperServiceImpl extends ViewHelperServiceImpl implements AcademicCalendarViewHelperService {

    private AcademicCalendarService acalService;
    private ContextInfo contextInfo;
    private TypeService typeService;

    public HolidayCalendarInfo createHolidayCalendar(HolidayCalendarForm hcForm) throws Exception{
        HolidayCalendarInfo hcInfo = hcForm.getHolidayCalendarInfo();
        hcInfo.setStateKey(AtpServiceConstants.ATP_DRAFT_STATE_KEY);
        hcInfo.setTypeKey(AcademicCalendarServiceConstants.HOLIDAY_CALENDAR_TYPE_KEY);
        hcInfo.setDescr(CommonUtils.buildDesc("no description"));
        HolidayCalendarInfo createdHc = getAcalService().createHolidayCalendar(AcademicCalendarServiceConstants.HOLIDAY_CALENDAR_TYPE_KEY, hcInfo, getContextInfo());
        return createdHc;
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

    public HolidayCalendarInfo copyHolidayCalendar(HolidayCalendarForm form) throws Exception {
        HolidayCalendarInfo newHCInfo =
                getAcalService().copyHolidayCalendar( form.getHolidayCalendarInfo().getId(),
                                                      form.getNewCalendarStartDate(),
                                                      form.getNewCalendarEndDate(), getContextInfo());
        if (null != newHCInfo) {
            newHCInfo.setName(form.getNewCalendarName());
        }
        return newHCInfo;
    }


    public HolidayCalendarInfo updateHolidayCalendar(HolidayCalendarForm hcForm) throws Exception{
        HolidayCalendarInfo hcInfo = hcForm.getHolidayCalendarInfo();

        HolidayCalendarInfo updatedHc = getAcalService().updateHolidayCalendar(hcInfo.getId(), hcInfo, getContextInfo());

        return updatedHc;
    }

    public List<HolidayWrapper> getHolidaysForHolidayCalendar(HolidayCalendarForm hcForm) throws Exception{
        HolidayCalendarInfo hcInfo = hcForm.getHolidayCalendarInfo();
        List<HolidayInfo> holidayInfos = getAcalService().getHolidaysForHolidayCalendar(hcInfo.getId(), getContextInfo());
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
        HolidayWrapper holiday = new HolidayWrapper();
        holiday.setHolidayInfo(holidayInfo);
        holiday.setTypeName(getHolidayTypeName(holidayInfo.getTypeKey()));
        CommonUtils.assembleTimeSet(holiday, holidayInfo.getStartDate(), holidayInfo.getEndDate());

        return holiday;
    }

    public String getHolidayTypeName(String holidayTypeKey) throws Exception {
        TypeInfo typeInfo = getAcalService().getHolidayType(holidayTypeKey, getContextInfo());
        return typeInfo.getName();
    }

    public void createHoliday(String holidayCalendarId, String holidayTypeKey, HolidayWrapper holiday) throws Exception {
        HolidayInfo holidayInfo = holiday.getHolidayInfo();
        holiday.setTypeName(getHolidayTypeName(holidayInfo.getTypeKey()));

        holidayInfo.setStateKey(AtpServiceConstants.MILESTONE_DRAFT_STATE_KEY);
        holidayInfo.setDescr(CommonUtils.buildDesc("no description"));

        disassembleHolidayTime(holiday, holidayInfo);

        HolidayInfo createdHoliday = getAcalService().createHoliday(holidayCalendarId, holidayTypeKey, holidayInfo, getContextInfo());
        holiday.setHolidayInfo(createdHoliday);
    }

    public void updateHoliday(String holidayId, HolidayWrapper holiday) throws Exception {
        HolidayInfo holidayInfo = holiday.getHolidayInfo();
        holiday.setTypeName(getHolidayTypeName(holidayInfo.getTypeKey()));
        disassembleHolidayTime(holiday, holidayInfo);
        getAcalService().updateHoliday(holidayId, holidayInfo, getContextInfo());
        holiday.setHolidayInfo(getAcalService().getHoliday(holidayId, getContextInfo()));
    }

    private void disassembleHolidayTime(HolidayWrapper holiday, HolidayInfo holidayInfo) throws Exception {
        holidayInfo.setStartDate(CommonUtils.getStartDate(holiday));
        holidayInfo.setEndDate(CommonUtils.getEndDate(holiday));
    }

    public void deleteHoliday(String holidayId) throws Exception{
        getAcalService().deleteHoliday(holidayId, getContextInfo());
    }

    public String getHolidayCalendarState(String holidayCalendarStateKey) throws Exception{
        StateInfo hcState = getAcalService().getHolidayCalendarState(holidayCalendarStateKey, getContextInfo());
        return hcState.getName();
    }

    public void deleteHolidayCalendar(String holidayCalendarId) throws Exception{
        List<HolidayInfo> holidayInfos = getAcalService().getHolidaysForHolidayCalendar(holidayCalendarId, getContextInfo());

        //delete hc
        getAcalService().deleteHolidayCalendar(holidayCalendarId, getContextInfo());

        //delete holidays
         if(holidayInfos != null &&  !holidayInfos.isEmpty()){
            for(HolidayInfo holiday : holidayInfos){
                deleteHoliday(holiday.getId());
            }
        }
    }

    public void populateHolidayTypes(InputField field, HolidayCalendarForm hcForm){
        boolean isAddLine = BooleanUtils.toBoolean((Boolean)field.getContext().get(UifConstants.ContextVariableNames.IS_ADD_LINE));
        if (!isAddLine) {
            return;
        }

        List<KeyValue> keyValues = new ArrayList<KeyValue>();

        //Hard code "Select holiday type"
        ConcreteKeyValue topKeyValue = new ConcreteKeyValue();
        topKeyValue.setKey("");
        topKeyValue.setValue("Select holiday type");
        keyValues.add(topKeyValue);

        try {
            List<TypeInfo> types = getAcalService().getHolidayTypes(getContextInfo());
            for (TypeInfo type : types) {
                ConcreteKeyValue keyValue = new ConcreteKeyValue();
                keyValue.setKey(type.getKey());
                keyValue.setValue(type.getName());
                keyValues.add(keyValue);
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
               AcalEventInfo newEventInfo = new AcalEventInfo();
               newEventInfo.setTypeKey(orgEventInfo.getTypeKey());
               newEventInfo.setIsDateRange(orgEventInfo.getIsDateRange());
               newEventInfo.setIsAllDay(orgEventInfo.getIsAllDay());
               AcalEventWrapper newEvent= new AcalEventWrapper();
               newEvent.setAcalEventInfo(newEventInfo);
               newEvent.setEventType(orgEventInfo.getTypeKey());
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
                  KeyDateInfo newKeyDateInfo = new KeyDateInfo();
                  newKeyDateInfo.setTypeKey(orgKeyDateInfo.getTypeKey());
                  newKeyDateInfo.setIsDateRange(orgKeyDateInfo.getIsDateRange());
                  newKeyDateInfo.setIsAllDay(orgKeyDateInfo.getIsAllDay());
                  KeyDateWrapper keyDateWrapper = new KeyDateWrapper(newKeyDateInfo);
                  type = getTypeService().getType(orgKeyDateInfo.getTypeKey(),context);
                  keyDateWrapper.setTypeInfo(type);
                  keyDateWrapper.setKeyDateNameUI(type.getName());
                  keyDateWrapper.setKeyDateType(orgKeyDateInfo.getTypeKey());

                  List<TypeTypeRelationInfo> registrationRelations = getTypeService().getTypeTypeRelationsByOwnerType(CalendarConstants.KEY_DATE_GROUP_TYPE_REGISTRATION_PERIOD,null,context);
                  List<TypeTypeRelationInfo> curriculumRelations = getTypeService().getTypeTypeRelationsByOwnerType(CalendarConstants.KEY_DATE_GROUP_TYPE_CURRICULUM,null,context);

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
        AcalEventWrapper event  = new AcalEventWrapper();
        event.setAcalEventInfo(acalEventInfo);
        event.setEventType(acalEventInfo.getTypeKey());
        Date startDate = acalEventInfo.getStartDate();
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");
        if (startDate !=null) {
            String startDateFullString = formatter.format(startDate);
            String[] timeStr = startDateFullString.split(" ");
            event.setStartDate(new SimpleDateFormat("MM/dd/yyyy").parse(timeStr[0]));
            if (!"12:00".equals(timeStr[1])){
                event.setStartTime(timeStr[1]);
            }
            event.setStartTimeAmPm(timeStr[2].toLowerCase());
        }
        Date endDate = acalEventInfo.getEndDate();
        if (endDate !=null) {
            String endDateFullString = formatter.format(endDate);
            String[] timeStr = endDateFullString.split(" ");
            event.setEndDate(new SimpleDateFormat("MM/dd/yyyy").parse(timeStr[0]));
            if (!"12:00".equals(timeStr[1])){
                event.setEndTime(timeStr[1]);
            }
            event.setEndTimeAmPm(timeStr[2].toLowerCase());

        }
        return event;
    }

    private AcalEventInfo assembleEventInfoFromWrapper(AcalEventWrapper eventWrapper) throws Exception{
        AcalEventInfo eventInfo = eventWrapper.getAcalEventInfo();
        //create dummy descr for db MilestoneEntity.plain is not nullable
        RichTextInfo rti = new RichTextInfo();
        rti.setPlain(eventWrapper.getEventType());
        eventInfo.setDescr(rti);
        eventInfo.setStateKey(AtpServiceConstants.MILESTONE_DRAFT_STATE_KEY);
        eventInfo.setTypeKey(eventWrapper.getEventType());

        Date startDate = eventWrapper.getStartDate();
        Date endDate = eventWrapper.getEndDate();
        String startTime =  eventWrapper.getStartTime();
        String endTime =   eventWrapper.getEndTime();
        if (endDate == null && !endTime.isEmpty())
            throw new Exception ("End Time can't be associated with an empty End Date.");

        if (endDate == null)  {
            eventInfo.setIsDateRange(false);
        }else if(startDate.compareTo(endDate)>0)  {
            throw new Exception("Error: Start Date can't be date after the End Date.");
        }else if (startDate.compareTo(endDate) == 0) {
            eventInfo.setIsDateRange(false);
            endDate = null;
        }else if (startDate.compareTo(endDate)<0)    {
            eventInfo.setIsDateRange(true);
        }
        
        if (startTime.isEmpty() & endTime.isEmpty())  {
            eventInfo.setIsAllDay(true);
        }else if (!startTime.isEmpty()) {
            eventInfo.setIsAllDay(false);
        }

        if(startDate != null && !startTime.isEmpty())  {
            String fullStartDateString = new SimpleDateFormat("MM/dd/yyyy").format(startDate);
            fullStartDateString = fullStartDateString.concat(" "+startTime+" "+eventWrapper.getStartTimeAmPm());
            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");
            Date fullStartDate = formatter.parse(fullStartDateString);
            eventInfo.setStartDate(fullStartDate);
        }
        else if (startTime.isEmpty()){
            eventInfo.setStartDate(eventWrapper.getStartDate());
        }

        if (endDate == null) {
            //DB schema does not allow a null value for the endDate -- therefore set EndDate = StartDate
            eventInfo.setEndDate(eventWrapper.getStartDate());
        }
        else if (endDate != null && endTime.isEmpty()) {
            eventInfo.setEndDate(eventWrapper.getEndDate());
        }
        else {
            String fullEndDateString = new SimpleDateFormat("MM/dd/yyyy").format(endDate);
            fullEndDateString = fullEndDateString.concat(" "+endTime+" "+eventWrapper.getEndTimeAmPm());
            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");
            Date fullEndDate = formatter.parse(fullEndDateString);
            eventInfo.setEndDate(fullEndDate);
        }

        return eventInfo;
    }

    protected boolean performAddLineValidation(View view, CollectionGroup collectionGroup, Object model,
                                               Object addLine) {
        boolean isValid = true;
        if (addLine instanceof AcalEventWrapper){
            AcalEventWrapper newEvent = (AcalEventWrapper)addLine;
            if (!checkEvent(newEvent))
                return false;

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
//        return (newEvent.getAcalEventInfo().getTypeKey().equals(sourceEvent.getAcalEventInfo().getTypeKey()) &&
//                newEvent.getStartDate().equals(sourceEvent.getStartDate()));
        return (newEvent.getEventType().equals(sourceEvent.getEventType()));
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
                List<TypeTypeRelationInfo> types = getTypeService().getTypeTypeRelationsByOwnerType(groupWrapper.getKeyDateGroupType(),"kuali.atp.atp.relation.associated",getContextInfo());
                for (TypeTypeRelationInfo relationInfo : types) {
                    TypeInfo type = getTypeService().getType(relationInfo.getRelatedTypeKey(),contextInfo);
                    if (!existingKeyDateTypes.contains(type.getKey())){
                        keyValues.add(new ConcreteKeyValue(relationInfo.getRelatedTypeKey(), type.getName()));
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        ((SelectControl) field.getControl()).setOptions(keyValues);

    }

    public void populateKeyDateGroupTypes(InputField field, AcademicCalendarForm acalForm) {

        List keyValues = new ArrayList();

        //TODO
        //Use getKeyDateTypesForTermType

        keyValues.add(new ConcreteKeyValue("", ""));
        keyValues.add(new ConcreteKeyValue("kuali.milestone.type.group.keydate","Registration Period"));
        keyValues.add(new ConcreteKeyValue("kuali.milestone.type.group.curriculum","Curriculum"));

        ((SelectControl) field.getControl()).setOptions(keyValues);

    }

    public void validateTerm(List<AcademicTermWrapper> termWrapper, ContextInfo context) throws Exception {
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
                            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, "error.enroll.term.dupliateName",""+ NumberUtils.min(new int[]{index1,index2}),""+NumberUtils.max(new int[]{index1,index2}));
                        }
                    }
                }

                for(KeyDatesGroupWrapper keyDatesGroupWrapper : wrapper.getKeyDatesGroupWrappers()){
                    for(KeyDateWrapper keyDateWrapper : keyDatesGroupWrapper.getKeydates()){

                        //Check keydates start/end date/time filled out based on the flag
                        if (keyDateWrapper.isDateRange() && keyDateWrapper.getEndDate() == null){
                            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, "error.enroll.keydate.endDate.empty",wrapper.getTermNameForUI(),keyDateWrapper.getKeyDateNameUI());
                        }
                        if (!keyDateWrapper.isAllDay() &&
                                (StringUtils.isBlank(keyDateWrapper.getStartTime()) ||
                                 StringUtils.isBlank(keyDateWrapper.getEndTime()) ||
                                 StringUtils.isBlank(keyDateWrapper.getStartTimeAmPm()) ||
                                 StringUtils.isBlank(keyDateWrapper.getEndTimeAmPm()))){
                             GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, "error.enroll.keydate.time.empty",wrapper.getTermNameForUI(),keyDateWrapper.getKeyDateNameUI());
                        }

                    }
                }
            }

        }
    }

    public void populateInstructionalDays(List<AcademicTermWrapper> termWrapperList,ContextInfo context)
    throws Exception {
         for (AcademicTermWrapper termWrapper : termWrapperList) {
            if (termWrapper.getKeyDatesGroupWrappers() != null){
                for (KeyDatesGroupWrapper keyDatesGroupWrapper : termWrapper.getKeyDatesGroupWrappers()) {
                     if (keyDatesGroupWrapper.getKeydates() != null){
                         for (KeyDateWrapper keydate : keyDatesGroupWrapper.getKeydates()) {
                             if (StringUtils.equals(keydate.getKeyDateType(),AtpServiceConstants.MILESTONE_INSTRUCTIONAL_PERIOD_TYPE_KEY) &&
                                 termWrapper.getTermInfo() != null && StringUtils.isNotBlank(termWrapper.getTermInfo().getId())){
                                 int instructionalDays = getAcalService().getInstructionalDaysForTerm(termWrapper.getTermInfo().getId(),context);
                                 termWrapper.setInstructionalDays(instructionalDays);
                                 break;
                             }
                         }
                     }

                }
            }
        }
    }

    public void saveTerm(AcademicTermWrapper termWrapper, String acalId, ContextInfo context) throws Exception {

        boolean isNewTerm = false;
        if (termWrapper.getTermInfo() == null){
            TermInfo newTerm = new TermInfo();
            termWrapper.setTermInfo(newTerm);
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
            TermInfo newTerm = getAcalService().createTerm(termWrapper.getTermType(),term,context);
            termWrapper.setTermInfo(getAcalService().getTerm(newTerm.getId(),context));
            getAcalService().addTermToAcademicCalendar(acalId,termWrapper.getTermInfo().getId(),context);
        }else{
            TermInfo updatedTerm = getAcalService().updateTerm(term.getId(),term,context);
            termWrapper.setTermInfo(getAcalService().getTerm(updatedTerm.getId(),context));
        }

        //Keydates
        if (termWrapper.getKeyDatesGroupWrappers() != null){
            for (KeyDatesGroupWrapper groupWrapper : termWrapper.getKeyDatesGroupWrappers()){
                for (KeyDateWrapper keyDateWrapper : groupWrapper.getKeydates()) {
                    boolean isNewKeyDate = false;
                    if (keyDateWrapper.getKeyDateInfo() == null){
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
                    keyDate.setStartDate(keyDateWrapper.getStartDate());
                    keyDate.setEndDate(keyDateWrapper.getEndDate());
                    keyDate.setIsAllDay(keyDateWrapper.isAllDay());
                    keyDate.setIsDateRange(keyDateWrapper.isDateRange());

                    if (!keyDateWrapper.isAllDay()){
                        keyDate.setStartDate(updateTime(keyDate.getStartDate(),keyDateWrapper.getStartTime(),keyDateWrapper.getStartTimeAmPm()));
                        keyDate.setEndDate(updateTime(keyDate.getEndDate(),keyDateWrapper.getEndTime(),keyDateWrapper.getEndTimeAmPm()));
                    }

                    if (!keyDateWrapper.isDateRange()){
                        keyDate.setEndDate(null);
                    }

                    if (isNewKeyDate){
                        KeyDateInfo newKeyDate = getAcalService().createKeyDate(termWrapper.getTermInfo().getId(),keyDate.getTypeKey(),keyDate,context);
                        keyDateWrapper.setKeyDateInfo(getAcalService().getKeyDate(newKeyDate.getId(),context));
                    } else {
                        KeyDateInfo updatedKeyDate = getAcalService().updateKeyDate(keyDate.getId(), keyDate, context);
                        keyDateWrapper.setKeyDateInfo(getAcalService().getKeyDate(updatedKeyDate.getId(),context));
                    }
                }
            }
        }


        for (KeyDatesGroupWrapper keyDatesGroupWrapper : termWrapper.getKeyDatesGroupWrappers()) {
            for (KeyDateWrapper keyDateWrapper : keyDatesGroupWrapper.getKeydates()) {
                if (keyDateWrapper.getKeyDateInfo().getTypeKey().equals(AtpServiceConstants.MILESTONE_INSTRUCTIONAL_PERIOD_TYPE_KEY)) {

                }
            }
        }


        //FIXME: Have to fix the exception thrown when there are not keydates added
        try{
//            termWrapper.setInstructionalDays(getAcalService().getInstructionalDaysForTerm(termWrapper.getTermInfo().getId(),context));
        }catch(Exception e){
//            e.printStackTrace();

       }

    }

    private Date updateTime(Date date,String time,String amPm){

        //FIXME: Use Joda DateTime

        // Get Calendar object set to the date and time of the given Date object
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        // Set time fields to zero
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

        return cal.getTime();
    }

    public void setTermOfficial(AcademicTermWrapper termWrapper, String acalId, ContextInfo context) throws Exception{
        saveTerm(termWrapper, acalId, context);

        TermInfo term = termWrapper.getTermInfo();
        term.setStateKey(AtpServiceConstants.ATP_OFFICIAL_STATE_KEY);
        term = getAcalService().updateTerm(term.getId(),term,context);

        termWrapper.setTermInfo(getAcalService().getTerm(term.getId(),context));

        if (termWrapper.getKeyDatesGroupWrappers() != null){
            for (KeyDatesGroupWrapper groupWrapper : termWrapper.getKeyDatesGroupWrappers()){
                for (KeyDateWrapper keyDateWrapper : groupWrapper.getKeydates()) {
                     keyDateWrapper.getKeyDateInfo().setStateKey(AtpServiceConstants.MILESTONE_OFFICIAL_STATE_KEY);
                     KeyDateInfo updatedKeyDate = getAcalService().updateKeyDate(keyDateWrapper.getKeyDateInfo().getId(),keyDateWrapper.getKeyDateInfo(),context);
                     keyDateWrapper.setKeyDateInfo(getAcalService().getKeyDate(updatedKeyDate.getId(),context));
                }
            }
        }

    }

    public void deleteTerm(List<AcademicTermWrapper> termWrapperList,int selectedIndex, String acalId, ContextInfo context) throws Exception{
        AcademicTermWrapper termWrapper = termWrapperList.get(selectedIndex);
        if (termWrapper.getTermInfo() != null){
            if (termWrapper.getKeyDatesGroupWrappers() != null){
                for (KeyDatesGroupWrapper groupWrapper : termWrapper.getKeyDatesGroupWrappers()){
                    for (KeyDateWrapper keyDateWrapper : groupWrapper.getKeydates()) {
                        if (keyDateWrapper.getKeyDateInfo() != null){
                            getAcalService().deleteKeyDate(keyDateWrapper.getKeyDateInfo().getId(),context);
                        }
                    }
                }
            }
            getAcalService().deleteTerm(termWrapper.getTermInfo().getId(), context);
        }
        termWrapperList.remove(selectedIndex);
    }

    public void deleteKeyDateGroup(AcademicTermWrapper termWrapper,int selectedIndex,ContextInfo context) throws Exception {
        KeyDatesGroupWrapper keydateGroup = termWrapper.getKeyDatesGroupWrappers().get(selectedIndex);
        if (keydateGroup != null){
            for (int index = 0; index < keydateGroup.getKeydates().size();index++) {
                deleteKeyDate(keydateGroup,index++,context);
            }
            termWrapper.getKeyDatesGroupWrappers().remove(keydateGroup);
        }

    }

    public void deleteKeyDate(KeyDatesGroupWrapper keyDatesGroup,int selectedIndex,ContextInfo context) throws Exception{
        KeyDateWrapper keydate = keyDatesGroup.getKeydates().get(selectedIndex);
        if (keydate.getKeyDateInfo() != null){
            getAcalService().deleteKeyDate(keydate.getKeyDateInfo().getId(),context);
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
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }else if (addLine instanceof KeyDatesGroupWrapper){
            KeyDatesGroupWrapper group = (KeyDatesGroupWrapper)addLine;
            try {
                TypeInfo termType = getTypeService().getType(group.getKeyDateGroupType(),TestHelper.getContext1());
                group.setKeyDateGroupNameUI(termType.getName());
                group.setTypeInfo(termType);
            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
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
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

        }else if (addLine instanceof HolidayCalendarWrapper){
            HolidayCalendarWrapper inputLine = (HolidayCalendarWrapper)addLine;
            List<HolidayWrapper> holidays = new ArrayList<HolidayWrapper>();
            try {
                System.out.println("HC id =" +inputLine.getId());

                HolidayCalendarInfo hcInfo = getAcalService().getHolidayCalendar(inputLine.getId(), getContextInfo());

                inputLine.setHolidayCalendarInfo(hcInfo);
                inputLine.setAdminOrgName(getAdminOrgNameById(hcInfo.getAdminOrgId()));
                StateInfo hcState = getAcalService().getHolidayCalendarState(hcInfo.getStateKey(), getContextInfo());
                inputLine.setStateName(hcState.getName());
                try {
                    List<HolidayInfo> holidayInfoList = getAcalService().getHolidaysForHolidayCalendar(hcInfo.getId(), getContextInfo());
                    for(HolidayInfo holidayInfo : holidayInfoList){
                        HolidayWrapper holiday = new HolidayWrapper();
                        holiday.setHolidayInfo(holidayInfo);
                        TypeInfo typeInfo = getAcalService().getHolidayType(holidayInfo.getTypeKey(), getContextInfo());
                        holiday.setTypeName(typeInfo.getName());
                        holidays.add(holiday);
                    }
                    inputLine.setHolidays(holidays);
                }catch (DoesNotExistException dnee){
                    System.out.println("call getAcademicCalendarService().getHolidaysForHolidayCalendar(holidayCalendarId, context), and get DoesNotExistException:  "+dnee.toString());
                }catch (InvalidParameterException ipe){
                    System.out.println("call getAcademicCalendarService().getHolidaysForHolidayCalendar(holidayCalendarId, context), and get InvalidParameterException:  "+ipe.toString());
                }catch (MissingParameterException mpe){
                    System.out.println("call getAcademicCalendarService().getHolidaysForHolidayCalendar(holidayCalendarId, context), and get MissingParameterException:  "+mpe.toString());
                }catch (OperationFailedException ofe){
                    System.out.println("call getAcademicCalendarService().getHolidaysForHolidayCalendar(holidayCalendarId, context), and get OperationFailedException:  "+ofe.toString());
                }catch (PermissionDeniedException pde){
                    System.out.println("call getAcademicCalendarService().getHolidaysForHolidayCalendar(holidayCalendarId, context), and get PermissionDeniedException:  "+pde.toString());
                }
            }catch (DoesNotExistException dnee){
                System.out.println("call getAcademicCalendarService().getHolidayCalendar(holidayCalendarId, context), and get DoesNotExistException:  "+dnee.toString());
            }catch (InvalidParameterException ipe){
                System.out.println("call getAcademicCalendarService().getHolidayCalendar(holidayCalendarId, context), and get InvalidParameterException:  "+ipe.toString());
            }catch (MissingParameterException mpe){
                System.out.println("call getAcademicCalendarService().getHolidayCalendar(holidayCalendarId, context), and get MissingParameterException:  "+mpe.toString());
            }catch (OperationFailedException ofe){
                System.out.println("call getAcademicCalendarService().getHolidayCalendar(holidayCalendarId, context), and get OperationFailedException:  "+ofe.toString());
            }catch (PermissionDeniedException pde){
                System.out.println("call getAcademicCalendarService().getHolidayCalendar(holidayCalendarId, context), and get PermissionDeniedException:  "+pde.toString());
            }


        }else if (addLine instanceof KeyDateWrapper){
            KeyDateWrapper keydate = (KeyDateWrapper)addLine;
            try {
                TypeInfo type = getTypeService().getType(keydate.getKeyDateType(),TestHelper.getContext1());
                keydate.setKeyDateNameUI(type.getName());
                keydate.setTypeInfo(type);
            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        } else if (addLine instanceof HolidayWrapper){
            HolidayWrapper holiday = (HolidayWrapper)addLine;
            try {
                holiday.setTypeName(getHolidayTypeName(holiday.getHolidayInfo().getTypeKey()));
            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        } else {
            super.processBeforeAddLine(view, collectionGroup, model, addLine);
        }
    }

    public List<AcademicTermWrapper> loadTerms(String acalId,ContextInfo context){

        List<AcademicTermWrapper> termWrappers = new ArrayList();

        try {
            List<TermInfo> termInfos = getAcalService().getTermsForAcademicCalendar(acalId, context);
            for (TermInfo termInfo : termInfos) {
                TypeInfo type = getAcalService().getTermType(termInfo.getTypeKey(),context);
                AcademicTermWrapper termWrapper = new AcademicTermWrapper(termInfo);
                termWrapper.setTypeInfo(type);
                termWrapper.setTermNameForUI(type.getName());

                //Populate keydates
                List<KeyDateInfo> keydateList = getAcalService().getKeyDatesForTerm(termInfo.getId(),context);

                TypeInfo registrationGroup = getTypeService().getType(CalendarConstants.KEY_DATE_GROUP_TYPE_REGISTRATION_PERIOD,context);
                TypeInfo curriculumGroup = getTypeService().getType(CalendarConstants.KEY_DATE_GROUP_TYPE_CURRICULUM,context);

                KeyDatesGroupWrapper registrationWrapper = new KeyDatesGroupWrapper(CalendarConstants.KEY_DATE_GROUP_TYPE_REGISTRATION_PERIOD,registrationGroup.getName());
                KeyDatesGroupWrapper curriculumWrapper = new KeyDatesGroupWrapper(CalendarConstants.KEY_DATE_GROUP_TYPE_CURRICULUM,curriculumGroup.getName());

                for (KeyDateInfo keyDateInfo : keydateList) {
                    KeyDateWrapper keyDateWrapper = new KeyDateWrapper(keyDateInfo);
                    type = getTypeService().getType(keyDateInfo.getTypeKey(),context);
                    keyDateWrapper.setTypeInfo(type);
                    keyDateWrapper.setKeyDateNameUI(type.getName());

                    List<TypeTypeRelationInfo> registrationRelations = getTypeService().getTypeTypeRelationsByOwnerType(CalendarConstants.KEY_DATE_GROUP_TYPE_REGISTRATION_PERIOD,null,context);
                    List<TypeTypeRelationInfo> curriculumRelations = getTypeService().getTypeTypeRelationsByOwnerType(CalendarConstants.KEY_DATE_GROUP_TYPE_CURRICULUM,null,context);

                    if (isRelationExists(registrationRelations,keyDateInfo.getTypeKey())){
                        registrationWrapper.getKeydates().add(keyDateWrapper);
                    }else if (isRelationExists(curriculumRelations,keyDateInfo.getTypeKey())){
                        curriculumWrapper.getKeydates().add(keyDateWrapper);
                    }
                }

                if (!registrationWrapper.getKeydates().isEmpty()){
                    termWrapper.getKeyDatesGroupWrappers().add(registrationWrapper);
                }

                if (!curriculumWrapper.getKeydates().isEmpty()){
                    termWrapper.getKeyDatesGroupWrappers().add(curriculumWrapper);
                }

                termWrappers.add(termWrapper);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return termWrappers;


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

    private ContextInfo getContextInfo() {
        if (null == contextInfo) {
            //TODO - get real ContextInfo
            contextInfo = TestHelper.getContext1();
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
