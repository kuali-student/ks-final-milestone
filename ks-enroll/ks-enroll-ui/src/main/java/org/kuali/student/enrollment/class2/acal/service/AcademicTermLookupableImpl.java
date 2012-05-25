package org.kuali.student.enrollment.class2.acal.service;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.rice.core.api.criteria.Predicate;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.lookup.LookupableImpl;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.web.form.LookupForm;
import org.kuali.student.enrollment.acal.constants.AcademicCalendarServiceConstants;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.class2.acal.util.CalendarSearchViewHelperUtil;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.LocaleInfo;

import javax.xml.namespace.QName;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static org.kuali.rice.core.api.criteria.PredicateFactory.*;


public class AcademicTermLookupableImpl  extends LookupableImpl {

    private transient AcademicCalendarService academicCalendarService;
    private ContextInfo contextInfo;
    final Logger logger = Logger.getLogger(AcademicTermLookupableImpl.class);

    /*
    public final static String NAME = "name";
    public final static String START_DATE = "startDate";
    public final static String END_DATE = "endDate";
    public final static String CALENDAR_TYPE = "atpType";
    */
    @Override
    protected List<?> getSearchResults(LookupForm lookupForm, Map<String, String> fieldValues, boolean unbounded) {

        List<TermInfo> rList = null;
        String name = fieldValues.get("code");
        String year = fieldValues.get("startDate");

        try{
            rList = CalendarSearchViewHelperUtil.searchForTerms(name, year, getContextInfo(), getAcademicCalendarService());

            //rList = searchForTerms(name, year, getContextInfo());
        }   catch (Exception ex){
            logger.error("Error in AcademicTermLookupableImpl searching for term. name[" + name +"] year["+year +"]", ex);
        }



        return rList;


    }
                                             /*
    private CalendarSearchViewHelperService getViewHelperService(LookupForm form){
        if (form.getView().getViewHelperServiceClass() != null){
            return (CalendarSearchViewHelperService)form.getView().getViewHelperService();
        } else {
            return (CalendarSearchViewHelperService)form.getPostedView().getViewHelperService();
        }
    }

    public List<TermInfo> searchForTerms(String name, String year,ContextInfo context)throws Exception {

        List<TermInfo> termInfoList = new ArrayList<TermInfo>();

        QueryByCriteria.Builder query = buildQueryByCriteria(name,year,"Term");

        List<TermInfo> terms = getAcademicCalendarService().searchForTerms(query.build(),context);
        for (TermInfo term : terms) {
            termInfoList.add(term);
        }

        return termInfoList;

    }

    private QueryByCriteria.Builder buildQueryByCriteria(String name, String year,String typeKey){

        QueryByCriteria.Builder qBuilder = QueryByCriteria.Builder.create();
        List<Predicate> pList = new ArrayList<Predicate>();
        Predicate p;

        qBuilder.setPredicates();
        if (StringUtils.isNotBlank(name)){
            p = like(NAME, "%" + name + "%");
            pList.add(p);
        }

        if (StringUtils.isNotBlank(year)){
            try {
                //FIXME: Find some better way to check the year
                Predicate startDatePredicate = and(greaterThanOrEqual(START_DATE, new SimpleDateFormat("MM/dd/yyyy").parse("01/01/" + year)),
                        lessThanOrEqual(START_DATE, new SimpleDateFormat("MM/dd/yyyy").parse("12/31/" + year)));


                Predicate endDatePredicate = and(greaterThanOrEqual(END_DATE, new SimpleDateFormat("MM/dd/yyyy").parse("01/01/" + year)),
                        lessThanOrEqual(END_DATE, new SimpleDateFormat("MM/dd/yyyy").parse("12/31/" + year)));

                pList.add(or(startDatePredicate, endDatePredicate));

            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }

        if (StringUtils.equalsIgnoreCase(typeKey, "Term")){
            p = notIn(CALENDAR_TYPE,AcademicCalendarServiceConstants.ACADEMIC_CALENDAR_TYPE_KEY,AcademicCalendarServiceConstants.HOLIDAY_CALENDAR_TYPE_KEY);
        }else{
            p = equal(CALENDAR_TYPE,typeKey);
        }

        pList.add(p);

        if (!pList.isEmpty()){
            Predicate[] preds = new Predicate[pList.size()];
            pList.toArray(preds);
            qBuilder.setPredicates(and(preds));
        }
        return qBuilder;
    }
      */
    protected AcademicCalendarService getAcademicCalendarService() {
         if(academicCalendarService == null) {
             academicCalendarService = (AcademicCalendarService) GlobalResourceLoader.getService(new QName(AcademicCalendarServiceConstants.NAMESPACE, AcademicCalendarServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return this.academicCalendarService;
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

}
