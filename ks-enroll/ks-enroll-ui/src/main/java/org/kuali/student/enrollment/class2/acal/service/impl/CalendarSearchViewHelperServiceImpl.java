package org.kuali.student.enrollment.class2.acal.service.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.criteria.Predicate;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.uif.UifParameters;
import org.kuali.rice.krad.uif.service.impl.ViewHelperServiceImpl;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.UrlFactory;
import org.kuali.student.enrollment.acal.constants.AcademicCalendarServiceConstants;
import org.kuali.student.enrollment.acal.dto.AcademicCalendarInfo;
import org.kuali.student.enrollment.acal.dto.HolidayCalendarInfo;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.class2.acal.service.CalendarSearchViewHelperService;
import org.kuali.student.enrollment.class2.acal.util.CalendarConstants;
import org.kuali.student.r2.common.dto.ContextInfo;

import javax.xml.namespace.QName;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.kuali.rice.core.api.criteria.PredicateFactory.*;
import static org.kuali.rice.core.api.criteria.PredicateFactory.greaterThanOrEqual;

public class CalendarSearchViewHelperServiceImpl extends ViewHelperServiceImpl implements CalendarSearchViewHelperService {

    private transient AcademicCalendarService academicCalendarService;

    public final static String NAME = "name";
    public final static String START_DATE = "startDate";
    public final static String END_DATE = "endDate";


    public List<TermInfo> searchForTerms(String name, String year,ContextInfo context)throws Exception {

    	List<TermInfo> termInfoList = new ArrayList<TermInfo>();

        QueryByCriteria.Builder query = buildQueryByCriteria(name,year);

        List<TermInfo> terms = getAcademicCalendarService().searchForTerms(query.build(),context);
        for (TermInfo term : terms) {
            if (!StringUtils.equals(term.getTypeKey(),AcademicCalendarServiceConstants.HOLIDAY_CALENDAR_TYPE_KEY) &&
                !StringUtils.equals(term.getTypeKey(),AcademicCalendarServiceConstants.ACADEMIC_CALENDAR_TYPE_KEY)){
                //Just make sure it has associated Acal (with some test data, acal would be missing for a term)
                List<AcademicCalendarInfo> atps = getAcademicCalendarService().getAcademicCalendarsForTerm(term.getId(), context);
                if (!atps.isEmpty()){
                    termInfoList.add(term);
                }
            }
        }

        return termInfoList;


    }

    public List<AcademicCalendarInfo> searchForAcademicCalendars(String name, String year,ContextInfo context)throws Exception {

        List<AcademicCalendarInfo> acalInfoList = new ArrayList<AcademicCalendarInfo>();

        QueryByCriteria.Builder query = buildQueryByCriteria(name,year);

        List<AcademicCalendarInfo> acals = getAcademicCalendarService().searchForAcademicCalendars(query.build(), context);
        for (AcademicCalendarInfo acal : acals) {
            if (StringUtils.equals(acal.getTypeKey(),AcademicCalendarServiceConstants.ACADEMIC_CALENDAR_TYPE_KEY)){
                acalInfoList.add(acal);
            }
        }

        return acalInfoList;


    }

    public List<HolidayCalendarInfo> searchForHolidayCalendars(String name, String year,ContextInfo context)throws Exception {

        List<HolidayCalendarInfo> hCals = new ArrayList<HolidayCalendarInfo>();

        QueryByCriteria.Builder query = buildQueryByCriteria(name,year);

        List<HolidayCalendarInfo> hcs = getAcademicCalendarService().searchForHolidayCalendars(query.build(), context);
        for (HolidayCalendarInfo hc : hcs) {
            if (StringUtils.equals(hc.getTypeKey(),AcademicCalendarServiceConstants.HOLIDAY_CALENDAR_TYPE_KEY)){
                hCals.add(hc);
            }
        }

        return hCals;


    }

    private QueryByCriteria.Builder buildQueryByCriteria(String name, String year){

        QueryByCriteria.Builder qBuilder = QueryByCriteria.Builder.create();
        List<Predicate> pList = new ArrayList<Predicate>();
        Predicate p;

        qBuilder.setPredicates();
        if (StringUtils.isNotBlank(name)){
            p = like(NAME, name);
    		pList.add(p);
        }

        if (StringUtils.isNotBlank(year)){
            try {
                //FIXME: Find some better option to check the year
                Predicate startDatePredicate = and(greaterThanOrEqual(START_DATE, new SimpleDateFormat("MM/dd/yyyy").parse("01/01/" + year)),
                                                   lessThanOrEqual(START_DATE, new SimpleDateFormat("MM/dd/yyyy").parse("12/31/" + year)));


                Predicate endDatePredicate = and(greaterThanOrEqual(END_DATE, new SimpleDateFormat("MM/dd/yyyy").parse("01/01/" + year)),
                                                lessThanOrEqual(END_DATE, new SimpleDateFormat("MM/dd/yyyy").parse("12/31/" + year)));

                pList.add(or(startDatePredicate, endDatePredicate));

            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }

        if (!pList.isEmpty()){
            Predicate[] preds = new Predicate[pList.size()];
            pList.toArray(preds);
            qBuilder.setPredicates(and(preds));
        }
        return qBuilder;
    }

    public Properties buildTermURLParameters(TermInfo term,String methodToCall,ContextInfo context){

        String acalId = null;
        try {
            List<AcademicCalendarInfo> atps = getAcademicCalendarService().getAcademicCalendarsForTerm(term.getId(), context);
            if (!atps.isEmpty()){
                acalId = atps.get(0).getId();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Properties props = new Properties();
        props.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, methodToCall);
        props.put("id",acalId);
        props.put(UifParameters.VIEW_ID, CalendarConstants.ACAL_EDIT_VIEW);

        return props;

    }

    public Properties buildACalURLParameters(AcademicCalendarInfo acal,String methodToCall,ContextInfo context){

        Properties props = new Properties();
        props.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, methodToCall);
        props.put("id",acal.getId());
        props.put(UifParameters.VIEW_ID, CalendarConstants.ACAL_EDIT_VIEW);

        return props;

    }

    public Properties buildHCalURLParameters(HolidayCalendarInfo hcInfo,String methodToCall,ContextInfo context){

        Properties props = new Properties();
        props.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, methodToCall);
        props.put("id",hcInfo.getId());
        props.put(UifParameters.VIEW_ID, CalendarConstants.HOLIDAYCALENDAR_EDITPAGE);

        return props;

    }

    protected AcademicCalendarService getAcademicCalendarService() {
         if(academicCalendarService == null) {
             academicCalendarService = (AcademicCalendarService) GlobalResourceLoader.getService(new QName(AcademicCalendarServiceConstants.NAMESPACE, AcademicCalendarServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return this.academicCalendarService;
    }
}
