package org.kuali.student.ap.framework.context.support;

import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.enrollment.courseofferingset.service.CourseOfferingSetService;
import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
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
import org.kuali.student.r2.common.util.date.KSDateTimeFormatter;
import org.kuali.student.r2.core.acal.dto.AcademicCalendarInfo;
import org.kuali.student.r2.core.acal.service.AcademicCalendarService;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.class1.type.dto.TypeTypeRelationInfo;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.kuali.student.r2.core.constants.TypeServiceConstants;

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
    private TypeService typeService;

    //private static String principalId = TermAndCalDataLoader.class.getSimpleName();

    public TermAndCalDataLoader() {

    }

    public TermAndCalDataLoader(AtpService atpService, AcademicCalendarService acalService, CourseOfferingSetService courseOfferingSetService, TypeService typeService) {
        this.atpService = atpService;
        this.acalService = acalService;
        this.courseOfferingSetService = courseOfferingSetService;
        this.typeService = typeService;
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

        loadAtp("kuali.atp.Fall3123", "Fall 3123", DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.parse("08/01/3123"), DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.parse("12/11/3123"), AtpServiceConstants.ATP_FALL_TYPE_KEY, AtpServiceConstants.ATP_OFFICIAL_STATE_KEY, "Fall 3123");
        loadAtp("kuali.atp.Spring3123", "Spring 3123", DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.parse("02/01/3123"), DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.parse("05/11/3123"), AtpServiceConstants.ATP_SPRING_TYPE_KEY, AtpServiceConstants.ATP_OFFICIAL_STATE_KEY, "Spring 3123");
        loadAtp("kuali.atp.Winter3123", "Winter 3123", DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.parse("12/11/3123"), DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.parse("01/11/3124"), AtpServiceConstants.ATP_WINTER_TYPE_KEY, AtpServiceConstants.ATP_OFFICIAL_STATE_KEY, "Winter 3123");

        loadTypeTypeRelation(AtpServiceConstants.ATP_TERM_GROUPING_TYPE_KEY, 0, AtpServiceConstants.ATP_FALL_TYPE_KEY,"kuali.type.type.relation.state.active", TypeServiceConstants.TYPE_TYPE_RELATION_GROUP_TYPE_KEY);
        loadTypeTypeRelation(AtpServiceConstants.ATP_TERM_GROUPING_TYPE_KEY, 0, AtpServiceConstants.ATP_SPRING_TYPE_KEY,"kuali.type.type.relation.state.active", TypeServiceConstants.TYPE_TYPE_RELATION_GROUP_TYPE_KEY);
        loadTypeTypeRelation(AtpServiceConstants.ATP_TERM_GROUPING_TYPE_KEY, 0, AtpServiceConstants.ATP_WINTER_TYPE_KEY,"kuali.type.type.relation.state.active", TypeServiceConstants.TYPE_TYPE_RELATION_GROUP_TYPE_KEY);
        loadType("Term Group", "", "http://student.kuali.org/wsdl/atp/AtpInfo", "http://student.kuali.org/wsdl/atp/AtpService", AtpServiceConstants.ATP_TERM_GROUPING_TYPE_KEY);
        loadType("Fall Term", "", "http://student.kuali.org/wsdl/atp/AtpInfo", "http://student.kuali.org/wsdl/atp/AtpService", AtpServiceConstants.ATP_FALL_TYPE_KEY);
        loadType("Spring Term", "", "http://student.kuali.org/wsdl/atp/AtpInfo", "http://student.kuali.org/wsdl/atp/AtpService", AtpServiceConstants.ATP_SPRING_TYPE_KEY);
        loadType("Winter Term", "", "http://student.kuali.org/wsdl/atp/AtpInfo", "http://student.kuali.org/wsdl/atp/AtpService", AtpServiceConstants.ATP_WINTER_TYPE_KEY);
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

    private void loadTypeTypeRelation(String ownerId, int rank, String relatedId, String state, String type) throws PermissionDeniedException, DataValidationErrorException, InvalidParameterException, ReadOnlyException, OperationFailedException, MissingParameterException, DoesNotExistException {
        TypeTypeRelationInfo relation = new TypeTypeRelationInfo();
        relation.setOwnerTypeKey(ownerId);
        relation.setRank(rank);
        relation.setRelatedTypeKey(relatedId);
        relation.setStateKey(state);
        relation.setTypeKey(type);
        typeService.createTypeTypeRelation(type, ownerId, relatedId, relation, KsapFrameworkServiceLocator.getContext().getContextInfo());

    }

    private void loadType(String name, String descr, String refObj, String service, String type) throws PermissionDeniedException, DataValidationErrorException, InvalidParameterException, ReadOnlyException, OperationFailedException, MissingParameterException, DoesNotExistException, AlreadyExistsException {
        TypeInfo relation = new TypeInfo();
        relation.setName(name);
        relation.setDescr(new RichTextInfo(descr,descr));
        relation.setRefObjectUri(refObj);
        relation.setServiceUri(service);
        relation.setKey(type);
        typeService.createType(type, relation, KsapFrameworkServiceLocator.getContext().getContextInfo());
    }
}
