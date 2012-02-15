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
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import javax.xml.namespace.QName;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.uif.container.CollectionGroup;
import org.kuali.rice.krad.uif.control.SelectControl;
import org.kuali.rice.krad.uif.field.InputField;
import org.kuali.rice.krad.uif.service.impl.ViewHelperServiceImpl;
import org.kuali.rice.krad.uif.view.View;
import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.enrollment.acal.constants.AcademicCalendarServiceConstants;
import org.kuali.student.enrollment.acal.dto.*;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.class2.acal.dto.AcademicTermWrapper;
import org.kuali.student.enrollment.class2.acal.dto.AcalEventWrapper;
import org.kuali.student.enrollment.class2.acal.dto.KeyDateWrapper;
import org.kuali.student.enrollment.class2.acal.form.AcademicCalendarForm;
import org.kuali.student.enrollment.class2.acal.form.HolidayCalendarForm;
import org.kuali.student.enrollment.class2.acal.service.AcademicCalendarViewHelperService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.util.constants.AtpServiceConstants;
import org.kuali.student.r2.core.type.dto.TypeInfo;
import org.kuali.student.test.utilities.TestHelper;


/**
 * This class implement ViewHelperServiceImpl for  all AcademicCalendar views
 *
 * @author Kuali Student Team
 */
public class AcademicCalendarViewHelperServiceImpl extends ViewHelperServiceImpl implements AcademicCalendarViewHelperService {
    private AcademicCalendarService acalService;
    private ContextInfo contextInfo;

    public AcademicCalendarService getAcalService() {
           if(acalService == null) {
             acalService = (AcademicCalendarService) GlobalResourceLoader.getService(new QName(AcademicCalendarServiceConstants.NAMESPACE, AcademicCalendarServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return this.acalService;
    }

    public HolidayCalendarInfo createHolidayCalendar(HolidayCalendarForm hcForm) throws Exception{
        HolidayCalendarInfo hcInfo = hcForm.getHolidayCalendarInfo();
        hcInfo.setStateKey(AtpServiceConstants.ATP_DRAFT_STATE_KEY);
        hcInfo.setTypeKey(AcademicCalendarServiceConstants.HOLIDAY_CALENDAR_TYPE_KEY);
        //create dummy descr for db AtpEntity.plain is not nullable
        RichTextInfo rti = new RichTextInfo();
        rti.setPlain(hcInfo.getName());
        hcInfo.setDescr(rti);
        HolidayCalendarInfo createdHc = getAcalService().createHolidayCalendar(AcademicCalendarServiceConstants.HOLIDAY_CALENDAR_TYPE_KEY, hcInfo, getContextInfo());
        return createdHc;
    }

    public HolidayCalendarInfo getHolidayCalendar(String hcId) throws Exception{
        HolidayCalendarInfo retrievedHc = getAcalService().getHolidayCalendar(hcId, getContextInfo());
        return retrievedHc;
    }

    public HolidayCalendarInfo updateHolidayCalendar(HolidayCalendarForm hcForm) throws Exception{
        HolidayCalendarInfo hcInfo = hcForm.getHolidayCalendarInfo();

        HolidayCalendarInfo updatedHc = getAcalService().updateHolidayCalendar(hcInfo.getId(), hcInfo, getContextInfo());

        return updatedHc;
    }
    public List<HolidayInfo> getHolidaysForHolidayCalendar(HolidayCalendarForm hcForm) throws Exception{
        HolidayCalendarInfo hcInfo = hcForm.getHolidayCalendarInfo();
        List<HolidayInfo> holidays = getAcalService().getHolidaysForHolidayCalendar(hcInfo.getId(), getContextInfo());

        return holidays;
    }

    public HolidayInfo createHoliday(String holidayCalendarId, String holidayTypeKey, HolidayInfo holidayInfo) throws Exception {
        holidayInfo.setStateKey(AtpServiceConstants.MILESTONE_DRAFT_STATE_KEY);
        HolidayInfo createdHoliday = getAcalService().createHoliday(holidayCalendarId, holidayTypeKey, holidayInfo, getContextInfo());
        return createdHoliday;
    }

    public HolidayInfo updateHoliday(String holidayId, HolidayInfo holidayInfo) throws Exception {
        getAcalService().updateHoliday(holidayId, holidayInfo, getContextInfo());
        return getAcalService().getHoliday(holidayId, getContextInfo());
    }

    public void deleteHoliday(String holidayId) throws Exception{
        getAcalService().deleteHoliday(holidayId, getContextInfo());
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

    public AcademicCalendarInfo getAcademicCalendar(String acalId) throws Exception {
        AcademicCalendarInfo acalInfo = getAcalService().getAcademicCalendar(acalId, getContextInfo());
        return acalInfo;
    }

    public AcademicCalendarInfo updateAcademicCalendar(AcademicCalendarForm acalForm) throws Exception{
        AcademicCalendarInfo acalInfo = acalForm.getAcademicCalendarInfo();
        AcademicCalendarInfo updatedAcalInfo = getAcalService().updateAcademicCalendar(acalInfo.getId(), acalInfo, getContextInfo());
        return updatedAcalInfo;
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
        AcalEventInfo eventInfo = assembleEventInfoFromWrapper (event);
        AcalEventInfo createdEventInfo = getAcalService().createAcalEvent(acalId, eventInfo.getTypeKey(), eventInfo, getContextInfo());
        event.setAcalEventInfo(createdEventInfo);
        return event;
    }

    public AcalEventWrapper updateEvent(String eventId, AcalEventWrapper event) throws Exception {
        AcalEventInfo eventInfo = assembleEventInfoFromWrapper(event);
        getAcalService().updateAcalEvent(eventId, eventInfo, getContextInfo());
        AcalEventInfo updatedEventInfo = getAcalService().getAcalEvent(eventId,getContextInfo());
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

    public void populateKeyDateTypes(InputField field, AcademicCalendarForm acalForm) {
        List keyValues = new ArrayList();
        keyValues.add(new ConcreteKeyValue("", ""));

        if (field.getContext().get(UifConstants.ContextVariableNames.LINE) != null) {
            keyValues.add(new ConcreteKeyValue(AcademicCalendarServiceConstants.REGISTRATION_PERIOD_TYPE_KEY,"Registration Period"));
            keyValues.add(new ConcreteKeyValue(AcademicCalendarServiceConstants.GRADING_PERIOD_END_TYPE_KEY,"Grading period"));
            keyValues.add(new ConcreteKeyValue(AcademicCalendarServiceConstants.DROP_DATE_TYPE_KEY,"Drop Date"));
            keyValues.add(new ConcreteKeyValue(AcademicCalendarServiceConstants.FINAL_EXAM_PERIOD_TYPE_KEY,"Final Exam Period"));
            ((SelectControl) field.getControl()).setOptions(keyValues);
//            GradeStudent student = (GradeStudent) field.getContext().get(UifConstants.ContextVariableNames.LINE);
//            for (ResultValueInfo option : student.getAvailabeGradingOptions()) {
//                keyValues.add(new ConcreteKeyValue(option.getKey(), option.getValue()));
//            }
//            ((SelectControl) field.getControl()).setOptions(keyValues);
        }

    }

    public void saveTerm(AcademicTermWrapper termWrapper,ContextInfo context) throws Exception {

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
        }else{
            TermInfo updatedTerm = getAcalService().updateTerm(term.getId(),term,context);
            termWrapper.setTermInfo(getAcalService().getTerm(updatedTerm.getId(),context));
        }

        if (termWrapper.getKeydates() != null){
            for (KeyDateWrapper keyDateWrapper : termWrapper.getKeydates()) {
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
                keyDate.setName("test");

                if (isNewKeyDate){
                    KeyDateInfo newKeyDate = getAcalService().createKeyDate(termWrapper.getTermInfo().getId(),keyDate.getTypeKey(),keyDate,context);
                    keyDateWrapper.setKeyDateInfo(getAcalService().getKeyDate(newKeyDate.getId(),context));
                } else {
                    KeyDateInfo updatedKeyDate = getAcalService().updateKeyDate(keyDate.getId(), keyDate, context);
                    keyDateWrapper.setKeyDateInfo(getAcalService().getKeyDate(updatedKeyDate.getId(),context));
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

    public void setTermOfficial(AcademicTermWrapper termWrapper,ContextInfo context) throws Exception{
        saveTerm(termWrapper,context);

        TermInfo term = termWrapper.getTermInfo();
        term.setStateKey(AtpServiceConstants.ATP_OFFICIAL_STATE_KEY);
        term = getAcalService().updateTerm(term.getId(),term,context);

        termWrapper.setTermInfo(getAcalService().getTerm(term.getId(),context));

        if (termWrapper.getKeydates() != null){
            for (KeyDateWrapper keyDateWrapper : termWrapper.getKeydates()) {
                 keyDateWrapper.getKeyDateInfo().setStateKey(AtpServiceConstants.MILESTONE_OFFICIAL_STATE_KEY);
                 KeyDateInfo updatedKeyDate = getAcalService().updateKeyDate(keyDateWrapper.getKeyDateInfo().getId(),keyDateWrapper.getKeyDateInfo(),context);
                 keyDateWrapper.setKeyDateInfo(getAcalService().getKeyDate(updatedKeyDate.getId(),context));
            }
        }

    }

    public void deleteTerm(List<AcademicTermWrapper> termWrapperList,int selectedIndex,ContextInfo context) throws Exception{
        AcademicTermWrapper termWrapper = termWrapperList.get(selectedIndex);
        if (termWrapper.getTermInfo() != null){
            if (termWrapper.getKeydates() != null){
                for (KeyDateWrapper keyDateWrapper : termWrapper.getKeydates()) {
                    if (keyDateWrapper.getKeyDateInfo() != null){
                        getAcalService().deleteKeyDate(keyDateWrapper.getKeyDateInfo().getId(),context);
                    }
                }
            }
            getAcalService().deleteTerm(termWrapper.getTermInfo().getId(), context);
        }
        termWrapperList.remove(selectedIndex);
    }

    public void deleteKeyDate(List<KeyDateWrapper> keyDateWrapperList,int selectedIndex,ContextInfo context) throws Exception{
        KeyDateWrapper keydate = keyDateWrapperList.get(selectedIndex);
        if (keydate.getKeyDateInfo() != null){
            getAcalService().deleteKeyDate(keydate.getKeyDateInfo().getId(),context);
        }
        keyDateWrapperList.remove(selectedIndex);
    }

    protected void processBeforeAddLine(View view, CollectionGroup collectionGroup, Object model, Object addLine) {
        if (addLine instanceof AcademicTermWrapper){
            AcademicTermWrapper newLine = (AcademicTermWrapper)addLine;
            try {
                TypeInfo termType = getAcalService().getTermType(((AcademicTermWrapper) addLine).getTermType(),TestHelper.getContext1());
                newLine.setTermNameForUI(termType.getName());
                SimpleDateFormat simpleDateformat=new SimpleDateFormat("yyyy");
                newLine.setName(termType.getName() + " " + simpleDateformat.format(newLine.getStartDate()));
            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }

    public void buildTerm(String termId,AcademicCalendarForm academicCalendarForm,ContextInfo context) throws Exception {

        List<AcademicTermWrapper> termWrappers = academicCalendarForm.getTermWrapperList();

        for (AcademicTermWrapper termWrapper : termWrappers) {
            TermInfo term = getAcalService().getTerm(termId, context);

            termWrapper.setTermInfo(term);
         termWrapper.setStartDate(term.getStartDate());
         termWrapper.setEndDate(term.getEndDate());
         termWrapper.setTermType(term.getTypeKey());
         termWrapper.setName(term.getName());
        }

        //Commented out for now as there are no keydates in ref data.
//         int instructionalDays = getAcalService().getInstructionalDaysForTerm(termId,context);
//         academicTermForm.setInstructionalDays(instructionalDays);
    }

    private ContextInfo getContextInfo() {
        if (null == contextInfo) {
            //TODO - get real ContextInfo
            contextInfo = TestHelper.getContext1();
        }
        return contextInfo;
    }
}
