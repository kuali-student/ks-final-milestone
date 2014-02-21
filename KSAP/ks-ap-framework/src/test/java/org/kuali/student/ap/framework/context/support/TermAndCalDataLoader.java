package org.kuali.student.ap.framework.context.support;

import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.enrollment.courseofferingset.service.CourseOfferingSetService;
import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.common.util.date.DateFormatters;
import org.kuali.student.r2.core.acal.dto.AcademicCalendarInfo;
import org.kuali.student.r2.core.acal.service.AcademicCalendarService;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.constants.AtpServiceConstants;

import java.util.Calendar;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: chmaurer
 * Date: 12/17/13
 * Time: 11:54 AM
 * To change this template use File | Settings | File Templates.
 */
public class TermAndCalDataLoader {

    private AtpService atpService;
    private AcademicCalendarService acalService;
    private CourseOfferingSetService courseOfferingSetService;

    //private static String principalId = TermAndCalDataLoader.class.getSimpleName();

    public TermAndCalDataLoader() {

    }

    public TermAndCalDataLoader(AtpService atpService, AcademicCalendarService acalService, CourseOfferingSetService courseOfferingSetService) {
        this.atpService = atpService;
        this.acalService = acalService;
        this.courseOfferingSetService = courseOfferingSetService;
    }

    public void loadData() throws Exception {
        //To change body of implemented methods use File | Settings | File Templates.
        //loadAtp("ksapTestAtpId1", "ksapTestAtp1", "2000-01-01", "2100-12-31", AtpServiceConstants.ATP_ACADEMIC_CALENDAR_TYPE_KEY, AtpServiceConstants.ATP_DRAFT_STATE_KEY, "Desc 101");

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DAY_OF_YEAR, -1);
        Date yesterday = cal.getTime();

        cal.add(Calendar.DAY_OF_YEAR, 2);
        Date tomorrow = cal.getTime();

        cal.add(Calendar.DAY_OF_YEAR, 1);
        Date dayAfterTomorrow = cal.getTime();

        loadAtp("ksapAtpNow", "ksapAtpNow", yesterday, tomorrow, AtpServiceConstants.ATP_ACADEMIC_CALENDAR_TYPE_KEY, AtpServiceConstants.ATP_OFFICIAL_STATE_KEY, "Now Cal");

        loadAtp("ksapAtpNow2", "ksapAtpNow2", yesterday, tomorrow, AtpServiceConstants.ATP_FALL_TYPE_KEY, AtpServiceConstants.ATP_OFFICIAL_STATE_KEY, "Now Term");
        loadSoc("ksapAtpNow2");

        loadAtp("ksapAtpFuture", "ksapAtpFuture", tomorrow, dayAfterTomorrow, AtpServiceConstants.ATP_FALL_TYPE_KEY, AtpServiceConstants.ATP_OFFICIAL_STATE_KEY, "Next Term");
        loadSoc("ksapAtpFuture");

    }


    public void loadACal() throws DoesNotExistException, PermissionDeniedException, OperationFailedException, InvalidParameterException, ReadOnlyException, MissingParameterException, DataValidationErrorException {
        AcademicCalendarInfo acalInfo = new AcademicCalendarInfo();
        //acalInfo.set
        acalService.createAcademicCalendar("", null,
                KsapFrameworkServiceLocator.getContext().getContextInfo());
    }

    private void loadAtp(String id,
                         String name,
                         Date startDate,
                         Date endDate,
                         String type,
                         String state,
                         String descrPlain)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException,
            DataValidationErrorException, ReadOnlyException {
        AtpInfo atpInfo = new AtpInfo();
        atpInfo.setId(id);
        atpInfo.setName(name);
        atpInfo.setTypeKey(type);
        atpInfo.setStateKey(state);
        atpInfo.setStartDate(startDate);
        atpInfo.setEndDate(endDate);
        atpInfo.setDescr(new RichTextHelper().fromPlain(descrPlain));
        CommonServiceConstants.setIsIdAllowedOnCreate(KsapFrameworkServiceLocator.getContext().getContextInfo(), true);
        atpService.createAtp(atpInfo.getTypeKey(), atpInfo, KsapFrameworkServiceLocator.getContext().getContextInfo());
    }

    public void loadAtp(String id,
                        String name,
                        String startDate,
                        String endDate,
                        String type,
                        String state,
                        String descrPlain)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException,
            DataValidationErrorException, ReadOnlyException {
        loadAtp(id, name, DateFormatters.DEFAULT_DATE_FORMATTER.parse(startDate), DateFormatters.DEFAULT_DATE_FORMATTER.parse(endDate), type, state, descrPlain);
    }

    public void loadSoc(String termId) throws PermissionDeniedException, DataValidationErrorException, InvalidParameterException, ReadOnlyException, OperationFailedException, MissingParameterException, DoesNotExistException {

        SocInfo socInfo = new SocInfo();
        socInfo.setId(termId);
        socInfo.setTermId(termId);
        socInfo.setStateKey(CourseOfferingSetServiceConstants.PUBLISHED_SOC_STATE_KEY);
        socInfo.setTypeKey(CourseOfferingSetServiceConstants.MAIN_SOC_TYPE_KEY);

        courseOfferingSetService.createSoc(termId, socInfo.getTypeKey(), socInfo, KsapFrameworkServiceLocator.getContext().getContextInfo());
    }

}
