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
import java.util.List;
import java.util.Date;
import javax.xml.namespace.QName;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.uif.service.impl.ViewHelperServiceImpl;

import org.kuali.student.enrollment.acal.dto.*;
import org.kuali.student.enrollment.class2.acal.dto.AcalEventWrapper;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.util.constants.AtpServiceConstants;
import org.kuali.student.enrollment.acal.constants.AcademicCalendarServiceConstants;
import org.kuali.student.enrollment.acal.dto.HolidayCalendarInfo;
import org.kuali.student.enrollment.acal.dto.HolidayInfo;
import org.kuali.student.enrollment.acal.dto.AcademicCalendarInfo;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.class2.acal.dto.AcademicTermWrapper;
import org.kuali.student.enrollment.class2.acal.form.HolidayCalendarForm;
import org.kuali.student.enrollment.class2.acal.form.AcademicCalendarForm;
import org.kuali.student.enrollment.class2.acal.service.AcademicCalendarViewHelperService;


/**
 * This class implement ViewHelperServiceImpl for  all AcademicCalendar views
 *
 * @author Kuali Student Team
 */
public class AcademicCalendarViewHelperServiceImpl extends ViewHelperServiceImpl implements AcademicCalendarViewHelperService {
    private AcademicCalendarService acalService;

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

    public AcalEventWrapper createEvent(String acalId, AcalEventWrapper event) throws Exception{
        AcalEventInfo eventInfo = assembleEventInfo (event);
        AcalEventInfo createdEventInfo = getAcalService().createAcalEvent(acalId, eventInfo.getTypeKey(), eventInfo, getContextInfo());
        event.setAcalEventInfo(createdEventInfo);
        return event;
    }

    private AcalEventInfo assembleEventInfo(AcalEventWrapper eventWrapper) throws Exception{
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

    public void saveTerm(AcademicCalendarForm academicCalendarForm,ContextInfo context) throws Exception {
        //Create Term
         List<AcademicTermWrapper> termWrappers = academicCalendarForm.getTermWrapperList();

        for (AcademicTermWrapper termWrapper : termWrappers) {
            TermInfo termInfo = termWrapper.getTermInfo();
            termInfo.setStartDate(termWrapper.getStartDate());
            termInfo.setEndDate(termWrapper.getEndDate());
            termInfo.setName(termWrapper.getName());
            termInfo.setTypeKey(termWrapper.getTermType());

            TermInfo term = getAcalService().updateTerm(termInfo.getId(), termInfo, context);

    //        int instructionalDays = getAcalService().getInstructionalDaysForTerm(term.getId(),context);
    //        academicTermForm.setInstructionalDays(instructionalDays);
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

    private ContextInfo getContextInfo(){
        return new ContextInfo();
    }
}
