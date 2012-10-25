package org.kuali.student.enrollment.class2.acal.service;

import org.kuali.rice.krad.uif.field.InputField;
import org.kuali.rice.krad.uif.service.ViewHelperService;
import org.kuali.student.enrollment.acal.dto.AcademicCalendarInfo;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.class2.acal.dto.AcademicTermWrapper;
import org.kuali.student.enrollment.class2.acal.dto.AcalEventWrapper;
import org.kuali.student.enrollment.class2.acal.dto.KeyDatesGroupWrapper;
import org.kuali.student.enrollment.class2.acal.form.AcademicCalendarForm;
import org.kuali.student.r2.common.dto.ContextInfo;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Date: 1/24/12
 * Time: 11:56 AM
 * To change this template use File | Settings | File Templates.
 */
public interface AcademicCalendarViewHelperService extends ViewHelperService {

    //Acal
    public AcademicCalendarInfo createAcademicCalendar(AcademicCalendarForm acalForm) throws Exception;

    public void deleteAcademicCalendar(String academicCalendarId) throws Exception;

    public AcademicCalendarInfo getLatestAcademicCalendar() throws Exception;

    public void copyToCreateAcademicCalendar(AcademicCalendarForm form) throws Exception;

    public List<AcalEventWrapper> populateEventWrappers(AcademicCalendarForm acalForm) throws Exception;

    public AcalEventWrapper createEvent(String acalId, AcalEventWrapper event) throws Exception;

    public AcalEventWrapper updateEvent(String eventId, AcalEventWrapper event) throws Exception;

    public void deleteEvent(String eventId) throws Exception;

    //Terms
    public void saveTerm(AcademicTermWrapper termWrapper, String acalId,boolean isOfficial) throws Exception;

//    public void setTermOfficial(AcademicTermWrapper termWrapper, String acalId) throws Exception;

    public void deleteTerm(List<AcademicTermWrapper> termWrapper,int selectedIndex, String acalId) throws Exception;

    public void deleteKeyDate(KeyDatesGroupWrapper keyDatesGroup,int selectedIndex) throws Exception;

    public void populateKeyDateTypes(InputField field, AcademicCalendarForm acalForm);

    public void deleteKeyDateGroup(AcademicTermWrapper termWrapper,int selectedIndex) throws Exception;

    public void populateKeyDateGroupTypes(InputField field, AcademicCalendarForm acalForm) throws Exception;

    public void populateInstructionalDays(List<AcademicTermWrapper> termWrapperList)throws Exception;

    public void populateInstructionalDays(AcademicTermWrapper termWrapper) throws Exception;

    public List<AcademicTermWrapper> populateTermWrappers(String acalId, boolean isCopy);

    public void validateAcademicCalendar(AcademicCalendarForm acalForm);

    public void validateTerm(List<AcademicTermWrapper> termWrapper,int termToValidateIndex,AcademicCalendarInfo acal);

    public void populateAcademicCalendarDefaults(AcademicCalendarForm acalForm);

    public AcademicTermWrapper populateTermWrapper(TermInfo termInfo, boolean isCopy);

    public List<TermInfo> getTermsByTypeAndCode(String type, String code) throws Exception;

    public AcademicCalendarService getAcalService();

    public ContextInfo getContextInfo();

}
