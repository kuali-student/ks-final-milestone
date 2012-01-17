package org.kuali.student.enrollment.class2.acal.service.decorators;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.time.FastDateFormat;
import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.enrollment.acal.dto.AcademicCalendarInfo;
import org.kuali.student.enrollment.acal.dto.AcalEventInfo;
import org.kuali.student.enrollment.acal.dto.HolidayCalendarInfo;
import org.kuali.student.enrollment.acal.dto.HolidayInfo;
import org.kuali.student.enrollment.acal.dto.KeyDateInfo;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.util.constants.AtpServiceConstants;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AcademicCalendarServiceCalculationDecorator extends AcademicCalendarServiceDecorator {

    @Override
    public AcademicCalendarInfo copyAcademicCalendar(String academicCalendarId, Date startDate, Date endDate, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        AcademicCalendarInfo templateAcademicCalendar = getAcademicCalendar(academicCalendarId, contextInfo);
        AcademicCalendarInfo academicCalendar = new AcademicCalendarInfo(templateAcademicCalendar);

        academicCalendar.setId(null);
        academicCalendar.setStateKey(AtpServiceConstants.ATP_DRAFT_STATE_KEY);
        academicCalendar.setName(templateAcademicCalendar.getName());
        academicCalendar.setDescr(new RichTextInfo(templateAcademicCalendar.getDescr()));
        academicCalendar.setTypeKey(templateAcademicCalendar.getTypeKey());
        academicCalendar.setHolidayCalendarIds(copyHolidayCalendars(templateAcademicCalendar, contextInfo));
        academicCalendar.setStartDate(startDate);
        academicCalendar.setEndDate(endDate);

        try {
            academicCalendar = createAcademicCalendar(academicCalendar.getTypeKey(), academicCalendar, contextInfo);
        } catch (DataValidationErrorException e) {
            throw new OperationFailedException("Could not create AcademicCalendar '" + academicCalendar.getId() + "'", e);
        } catch (ReadOnlyException e) {
            throw new OperationFailedException("Could not create AcademicCalendar '" + academicCalendar.getId() + "'", e);
        }

        Map<String, KeyDateInfo> oldDatesToNewDates = new HashMap<String, KeyDateInfo>();

        List<TermInfo> templateTerms = getTermsForAcademicCalendar(templateAcademicCalendar.getId(), contextInfo);
        for (TermInfo templateTerm : templateTerms) {
            // TODO properly generate new key
            String termId = templateTerm.getId() + "." + RandomStringUtils.randomAlphanumeric(4);
            TermInfo term;
            term = copyTerm(templateTerm.getId(), termId, oldDatesToNewDates, contextInfo);
            try {
                addTermToAcademicCalendar(academicCalendar.getId(), term.getId(), contextInfo);
            } catch (AlreadyExistsException e) {
                throw new OperationFailedException("AlreadyExistsException  thrown from addTermToAcademicCalendar :" + e.getMessage());
            }
        }

        copyAcalEvents(templateAcademicCalendar.getId(),academicCalendar.getId(),contextInfo);

        return academicCalendar;
    }

    private List<String> copyHolidayCalendars(AcademicCalendarInfo academicCalendar,ContextInfo contextInfo)
            throws OperationFailedException, InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException {

        List<String> newHolidayCalendarIds = new ArrayList<String>();

        if (academicCalendar.getHolidayCalendarIds().isEmpty()){
            return newHolidayCalendarIds;
        }

        List<HolidayCalendarInfo> holidayCalendarInfos = getHolidayCalendarsByIds(academicCalendar.getHolidayCalendarIds(),contextInfo);

        for (HolidayCalendarInfo templateHolidayCalendar : holidayCalendarInfos) {
            HolidayCalendarInfo holidayCalendar = new HolidayCalendarInfo(templateHolidayCalendar);
            holidayCalendar.setId(null);
            holidayCalendar.setStateKey(AtpServiceConstants.ATP_DRAFT_STATE_KEY);
            holidayCalendar.setTypeKey(templateHolidayCalendar.getTypeKey());
            holidayCalendar.setDescr(new RichTextInfo(templateHolidayCalendar.getDescr()));
            holidayCalendar.setName(templateHolidayCalendar.getName());

            try{
                try {
                    holidayCalendar = createHolidayCalendar(AtpServiceConstants.ATP_HOLIDAY_CALENDAR_TYPE_KEY,holidayCalendar,contextInfo);
                } catch (ReadOnlyException e) {
                    throw new OperationFailedException("Exception creating Holiday Calendar.", e);
                }
                newHolidayCalendarIds.add(holidayCalendar.getId());
            }catch(DataValidationErrorException e){
                throw new OperationFailedException("Could not create HolidayCalendar",e);
            }

            List<HolidayInfo> holidays = getHolidaysForHolidayCalendar(templateHolidayCalendar.getId(),contextInfo);
            for (HolidayInfo holidayInfo : holidays) {
                HolidayInfo newHoliday = new HolidayInfo(holidayInfo);
                newHoliday.setId(UUIDHelper.genStringUUID());
                newHoliday.setDescr(new RichTextInfo(holidayInfo.getDescr()));
                newHoliday.setName(holidayInfo.getName());
                newHoliday.setTypeKey(newHoliday.getTypeKey());
                newHoliday.setStateKey(AtpServiceConstants.MILESTONE_DRAFT_STATE_KEY);

                try{
                    newHoliday = createHoliday(holidayCalendar.getId(),holidayInfo.getTypeKey(),newHoliday,contextInfo);
                    calculateHolidayEffectiveDates(newHoliday, holidayCalendar, contextInfo);
                } catch (DataValidationErrorException e) {
                    throw new OperationFailedException("Error creating holiday",e);
                } catch (ReadOnlyException e) {
                    throw new OperationFailedException("Error creating holiday",e);
                }
            }
        }

        return newHolidayCalendarIds;
    }

    private TermInfo copyTerm(String templateTermId, String newTermId, Map<String, KeyDateInfo> templateDatesToNewDates, ContextInfo context) throws InvalidParameterException,
            MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException {
        TermInfo templateTerm = getTerm(templateTermId, context);

        TermInfo term = new TermInfo(templateTerm);
        // TODO properly generate new key
        term.setId(null);
        term.setStateKey(AtpServiceConstants.ATP_DRAFT_STATE_KEY);
        term.setName(templateTerm.getName());
        term.setDescr(new RichTextInfo(templateTerm.getDescr()));
        term.setTypeKey(templateTerm.getTypeKey());

        try {
            term = createTerm(term.getTypeKey(), term, context);
        } catch (DataValidationErrorException e) {
            throw new OperationFailedException("Could not create Term '" + term.getId() + "'", e);
        } catch (ReadOnlyException e) {
            throw new OperationFailedException("Could not create Term '" + term.getId() + "'", e);
        }

        /*
         * Copy KeyDates of Term TODO Currently cannot reuse keydates in the
         * acal service, but the design concept was that a term and subterm may
         * share dates. A mapping of all KeyDates to their newly created
         * counterparts is used to determine whether a new KeyDate has already
         * been created. However, it is not being used at this time and a new
         * KeyDate will be created for each relationship.
         */
        List<KeyDateInfo> templateKeyDates = getKeyDatesForTerm(templateTermId, context);
        for (KeyDateInfo templateKeyDate : templateKeyDates) {
            KeyDateInfo keyDate = templateDatesToNewDates.get(templateKeyDate.getId());
            keyDate = null; // TODO Disabling usage of mapping until service
                            // supports the reuse of dates

            if (null == keyDate) {
                keyDate = new KeyDateInfo(templateKeyDate);
                // TODO properly generate new key
                keyDate.setId(templateKeyDate.getId() + "." + RandomStringUtils.randomAlphanumeric(4));
                keyDate.setStateKey(AtpServiceConstants.MILESTONE_DRAFT_STATE_KEY);
                keyDate.setName(templateKeyDate.getName());
                keyDate.setDescr(new RichTextInfo(templateKeyDate.getDescr()));
                keyDate.setTypeKey(templateKeyDate.getTypeKey());

                try {
                    // TODO Need a way to only create a KeyDate in order to associate it with multiple Terms
                    createKeyDate(term.getId(), keyDate.getId(), keyDate, context);
                    // TODO calculate keyDate effective dates
                    templateDatesToNewDates.put(templateKeyDate.getId(), keyDate);
                } catch (DataValidationErrorException e) {
                    throw new OperationFailedException("Could not create KeyDate '" + keyDate.getId() + "'", e);
                } catch (ReadOnlyException e) {
                    throw new OperationFailedException("ReadOnlyException " + keyDate.getId() + "'", e);
                }

            }
            // TODO Need a way to associate a KeyDate with multiple Terms
        }

        // Recursive call to copy subTerms
        List<TermInfo> templateSubTerms = getContainingTerms(templateTermId, context);
        for (TermInfo templateSubTerm : templateSubTerms) {
            // TODO properly generate new key
            String subTermId = templateSubTerm.getId() + "." + RandomStringUtils.randomAlphanumeric(4);
            TermInfo subTerm = copyTerm(templateSubTerm.getId(), subTermId, templateDatesToNewDates, context);
            try {
                addTermToTerm(term.getId(), subTerm.getId(), context);
            } catch (AlreadyExistsException e) {
                throw new OperationFailedException("AlreadyExistsException : " + e.getMessage());
            }
        }

        return term;
    }

    private void copyAcalEvents(String templateAcalId,String newAcalId,ContextInfo contextInfo) throws OperationFailedException {

        List<AcalEventInfo> acalEventInfos;

        try {
            acalEventInfos = getAcalEventsForAcademicCalendar(templateAcalId,contextInfo);
        } catch (Exception e) {
            throw new OperationFailedException("Error getting Acal Events",e);
        }

        for (AcalEventInfo acalEventInfo : acalEventInfos) {
             AcalEventInfo newAcalEventInfo = new AcalEventInfo(acalEventInfo);
            newAcalEventInfo.setId(UUIDHelper.genStringUUID());
            newAcalEventInfo.setDescr(new RichTextInfo(acalEventInfo.getDescr()));
            newAcalEventInfo.setName(acalEventInfo.getName());
            newAcalEventInfo.setStateKey(AtpServiceConstants.MILESTONE_DRAFT_STATE_KEY);
            newAcalEventInfo.setTypeKey(acalEventInfo.getTypeKey());

            try{
                createAcalEvent(newAcalId,acalEventInfo.getTypeKey(),newAcalEventInfo,contextInfo);
            }catch(Exception e){
                throw new OperationFailedException("Error creating AcalEvent",e);
            }

        }
    }

    private void calculateHolidayEffectiveDates(HolidayInfo holiday, HolidayCalendarInfo holidayCalendar, ContextInfo contextInfo) throws OperationFailedException {
        String typeKey = holiday.getTypeKey();
        if (AtpServiceConstants.MILESTONE_LABOR_DAY_TYPE_KEY.equals(typeKey)) {
            List<Integer> years = new ArrayList<Integer>();
            FastDateFormat format = FastDateFormat.getInstance("yyyy");
            int calStartYear = Integer.parseInt(format.format(holidayCalendar.getStartDate()));
            int calEndYear = Integer.parseInt(format.format(holidayCalendar.getEndDate()));
            for (int i = calStartYear; i <= calEndYear; i ++) {
                years.add(i);
            }

            boolean holidayFound = false;
            for (Integer year : years) {
                Date laborDayStartTime = getLaborDayForYear(year);
                Calendar cal = Calendar.getInstance();
                cal.setTime(laborDayStartTime);
                cal.add(Calendar.DATE, 1);
                Date laborDayEndTime = cal.getTime();

                if (timespanOccursDuringTimespan(laborDayStartTime, laborDayEndTime, holidayCalendar.getStartDate(), holidayCalendar.getEndDate())) {
                    holidayFound = true;

                    // populate holiday
                    holiday.setStartDate(laborDayStartTime);
                    holiday.setEndDate(laborDayEndTime);
                    holiday.setIsAllDay(true);
                    holiday.setIsDateRange(false);

                    // TODO the milestone is being populated with the first possible occurance - it is possible the ATP could span more than a years time
                    break;
                }
            }

            if (!holidayFound) {
                throw new OperationFailedException("Could not calculate holiday for ATP. Holiday: '" + typeKey +"' ATP: '" + holidayCalendar.getId() + "'");
            }
        }
    }

    private Date getLaborDayForYear(int year) {
        Calendar cal = new GregorianCalendar(year, Calendar.SEPTEMBER, 1);
        if (Calendar.MONDAY != cal.get(Calendar.DAY_OF_WEEK)) {
            int daysUntil = (Calendar.MONDAY - cal.get(Calendar.DAY_OF_MONTH) + 7) % 7;
            cal.add(Calendar.DATE, daysUntil);
        }
        return cal.getTime();
    }

    private boolean timespanOccursDuringTimespan(Date startTime1, Date endTime1, Date startTime2, Date endTime2) {
        if (!startTime1.before(startTime2) && startTime1.before(endTime2)) {
            // holiday starts as/after ATP starts (fully contained or extends past ATP end)
            return true;
        }
        if (!endTime1.before(startTime2) && !endTime1.after(endTime2)) {
            // holiday ends while ATP is in progress
            return true;
        }
        if (!startTime1.after(startTime2) && !endTime1.before(endTime2)) {
            // holiday encompasses ATP
            return true;
        }
        return false;
    }

}
