package org.kuali.student.enrollment.class2.appointment.service.impl;

import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.lookup.LookupForm;
import org.kuali.rice.krad.lookup.LookupableImpl;
import org.kuali.student.r2.core.acal.dto.KeyDateInfo;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.class2.appointment.dto.AppointmentWindowWrapper;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.core.constants.AcademicCalendarServiceConstants;
import org.kuali.student.r2.common.util.date.DateFormatters;
import org.kuali.student.r2.core.appointment.constants.AppointmentServiceConstants;
import org.kuali.student.r2.core.appointment.dto.AppointmentWindowInfo;
import org.kuali.student.r2.core.appointment.service.AppointmentService;
import org.kuali.student.r2.core.class1.type.dto.TypeTypeRelationInfo;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.kuali.student.r2.core.constants.PopulationServiceConstants;
import org.kuali.student.r2.core.constants.TypeServiceConstants;
import org.kuali.student.r2.core.population.dto.PopulationInfo;
import org.kuali.student.r2.core.population.service.PopulationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class AppointmentWindowWrapperLookupableImpl extends LookupableImpl {
    private static final Logger LOG = LoggerFactory.getLogger(AppointmentWindowWrapperLookupableImpl.class);
    private static final long serialVersionUID = 1L;
    private transient AppointmentService appointmentService;
    private transient AcademicCalendarService academicCalendarService;
    private transient TypeService typeService;
    private transient PopulationService populationService;


    public final static String TERM_TYPE_KEY = "termType";
    public final static String TERM_YEAR_KEY = "termYear";

    @Override
    public List<?> performSearch(LookupForm lookupForm, Map<String, String> searchCriteria, boolean bounded) {

        List<AppointmentWindowWrapper> windowWrapperList;
        String termTypeKey = searchCriteria.get(TERM_TYPE_KEY);
        String termYear = searchCriteria.get(TERM_YEAR_KEY);
        try {
            List<KeyDateInfo> periods = _searchPeriods(termTypeKey, termYear);
            if (periods == null || periods.isEmpty()) {
                return null;
            }
            windowWrapperList = _loadWindows(periods);
        } catch (Exception e) {
            LOG.warn("Error calling _loadWindows", e);
            return null;
        }

        return windowWrapperList;
    }

    private List<KeyDateInfo> _searchPeriods(String termTypeKey, String termYear) throws Exception {

        //Parse the year to a date and the next year's date to compare against the startTerm
        Date minBoundDate = DateFormatters.DEFULT_YEAR_FORMATTER.parse(termYear);
        Date maxBoundDate = DateFormatters.DEFULT_YEAR_FORMATTER.parse(Integer.toString(Integer.parseInt(termYear) + 1));

        //Build up a term search criteria
        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(PredicateFactory.and(
                PredicateFactory.equal("atpType", termTypeKey),
                PredicateFactory.greaterThanOrEqual("startDate", minBoundDate),
                PredicateFactory.lessThan("startDate", maxBoundDate)));

        QueryByCriteria criteria = qbcBuilder.build();

        //Perform Term Search with Service Call
        AcademicCalendarService academicCalendarService = getAcalService();
        List<TermInfo> terms = academicCalendarService.searchForTerms(criteria, null);

        //Check for exceptions
        if (terms == null || terms.isEmpty()) {
            return null; //Nothing found
        }

        if (terms.size() > 1) {
            LOG.error("Too many terms!");
        }

        int firstTermInfo = 0;
        TermInfo term = terms.get(firstTermInfo);

        //Get the milestones and filter out anything that is not registration period
        List<KeyDateInfo> keyDates = academicCalendarService.getKeyDatesForTerm(term.getId(), null);
        if (keyDates != null) {

            //Get the valid period types
            List<TypeTypeRelationInfo> milestoneTypeRelations = getTypeService().getTypeTypeRelationsByOwnerAndType(AtpServiceConstants.MILESTONE_REGISTRATION_PERIOD_GROUP_TYPE_KEY, TypeServiceConstants.TYPE_TYPE_RELATION_GROUP_TYPE_KEY, new ContextInfo());
            List<String> validMilestoneTypes = new ArrayList<String>();
            for (TypeTypeRelationInfo milestoneTypeRelation : milestoneTypeRelations) {
                validMilestoneTypes.add(milestoneTypeRelation.getRelatedTypeKey());
            }

            //Add in only valid milestones that are registration periods
            List<KeyDateInfo> periodMilestones = new ArrayList<KeyDateInfo>();
            for (KeyDateInfo keyDate : keyDates) {
                if (validMilestoneTypes.contains(keyDate.getTypeKey())) {
                    periodMilestones.add(keyDate);
                }
            }
            return periodMilestones;
        }
        return null;

    }

    private List<AppointmentWindowWrapper> _loadWindows(List<KeyDateInfo> periods) throws Exception {
        List<AppointmentWindowWrapper> windowWrapperList = new ArrayList<AppointmentWindowWrapper>();
        for (KeyDateInfo period : periods) {
            List<AppointmentWindowInfo> windows = getAppointmentService().getAppointmentWindowsByPeriod(period.getId(), new ContextInfo());
            if (windows != null) {
                for (AppointmentWindowInfo window : windows) {

                    //Look up the population
                    PopulationInfo population = getPopulationService().getPopulation(window.getAssignedPopulationId(), new ContextInfo());

                    AppointmentWindowWrapper windowWrapper = new AppointmentWindowWrapper();

                    windowWrapper.setAppointmentWindowInfo(window);
                    windowWrapper.setId(window.getId());
                    windowWrapper.setWindowName(window.getName());
                    windowWrapper.setPeriodKey(window.getPeriodMilestoneId());
                    windowWrapper.setPeriodName(period.getName());

                    windowWrapper.setAssignedPopulationName(population.getName());
                    windowWrapper.setWindowTypeKey(window.getTypeKey());

                    windowWrapper.setStartDate(window.getStartDate());
                    windowWrapper.setStartTime(_parseTime(window.getStartDate()));
                    windowWrapper.setStartTimeAmPm(_parseAmPm(window.getStartDate()));

                    windowWrapper.setEndDate(window.getEndDate());
                    windowWrapper.setEndTime(_parseTime(window.getEndDate()));
                    windowWrapper.setEndTimeAmPm(_parseAmPm(window.getEndDate()));

                    windowWrapperList.add(windowWrapper);
                }
            }
        }
        return windowWrapperList;

    }

    private String _parseAmPm(Date date) {
        if (date == null) {
            return null;
        }
        return DateFormatters.AM_PM_TIME_FORMATTER.format(date);
    }

    private String _parseTime(Date date) {
        if (date == null) {
            return null;
        }
        return DateFormatters.HOUR_MINUTE_TIME_FORMATTER.format(date);
    }

    public AcademicCalendarService getAcalService() {
        if (academicCalendarService == null) {
            academicCalendarService = (AcademicCalendarService) GlobalResourceLoader.getService(new QName(AcademicCalendarServiceConstants.NAMESPACE, AcademicCalendarServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return this.academicCalendarService;
    }


    public AppointmentService getAppointmentService() {
        if (appointmentService == null) {
            appointmentService = (AppointmentService) GlobalResourceLoader.getService(new QName(AppointmentServiceConstants.NAMESPACE, AppointmentServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return appointmentService;
    }


    public TypeService getTypeService() {
        if (typeService == null) {
            typeService = (TypeService) GlobalResourceLoader.getService(new QName(TypeServiceConstants.NAMESPACE, TypeServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return this.typeService;
    }

    public PopulationService getPopulationService() {
        if (populationService == null) {
            populationService = (PopulationService) GlobalResourceLoader.getService(new QName(PopulationServiceConstants.NAMESPACE, "PopulationMockService")); // TODO: Fix with real service
        }
        return populationService;
    }

}
