package org.kuali.student.enrollment.class2.acal.service;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.criteria.Predicate;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.lookup.LookupableImpl;
import org.kuali.rice.krad.uif.UifParameters;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.UrlFactory;
import org.kuali.rice.krad.web.form.LookupForm;
import org.kuali.student.enrollment.acal.constants.AcademicCalendarServiceConstants;
import org.kuali.student.enrollment.acal.dto.AcademicCalendarInfo;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.mock.utilities.TestHelper;

import javax.xml.namespace.QName;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.kuali.rice.core.api.criteria.PredicateFactory.*;

public class AcademicTermLookupableImpl  extends LookupableImpl {

    public final static String TERM_TYPE = "typeKey";
    public final static String TERM_NAME = "name";
    public final static String TERM_START_DATE = "startDate";
    public final static String TERM_END_DATE = "endDate";
    public final static String TERM_STATE = "stateKey";

 	private transient AcademicCalendarService academicCalendarService;

    @Override
    protected List<?> getSearchResults(LookupForm lookupForm, Map<String, String> fieldValues, boolean unbounded) {

    	TermInfo termInfo = null;
    	List<TermInfo> termInfoList = new ArrayList<TermInfo>();
        List<AcademicCalendarInfo> acalInfoList = new ArrayList<AcademicCalendarInfo>();

    	String termName = fieldValues.get(TERM_NAME);
        String termType = fieldValues.get(TERM_TYPE);
        String termState = fieldValues.get(TERM_STATE);
        String termStartDate = fieldValues.get(TERM_START_DATE);
        String termEndDate = fieldValues.get(TERM_END_DATE);

    	ContextInfo context = new ContextInfo();


        QueryByCriteria.Builder qBuilder = QueryByCriteria.Builder.create();
        List<Predicate> pList = new ArrayList<Predicate>();
        Predicate p;

        qBuilder.setPredicates();
        if (StringUtils.isNotBlank(termName)){
            p = equal(TERM_NAME, termName);
    		pList.add(p);
        }

        if (StringUtils.isNotBlank(termType)){
            p = equal("atpType", termType);
    		pList.add(p);
        }

        if (StringUtils.isNotBlank(termState)){
            p = equal("atpState", termState);
    		pList.add(p);
        }

        if (StringUtils.isNotBlank(termStartDate)){
            try {
                p = greaterThanOrEqual(TERM_START_DATE, new SimpleDateFormat("MM/dd/yyyy").parse(termStartDate));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            pList.add(p);
        }

        if (StringUtils.isNotBlank(termEndDate)){
            try{
                p = lessThanOrEqual(TERM_END_DATE,  new SimpleDateFormat("MM/dd/yyyy").parse(termEndDate));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
    		pList.add(p);
        }

        if (!pList.isEmpty()){
            Predicate[] preds = new Predicate[pList.size()];
            pList.toArray(preds);
            qBuilder.setPredicates(and(preds));
        }

        try {
            termInfoList = getAcademicCalendarService().searchForTerms(qBuilder.build(),context);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return termInfoList;
    }


    @Override
    public boolean allowsMaintenanceNewOrCopyAction() {
        return false;
    }

    @Override
    protected String getActionUrlHref(LookupForm lookupForm, Object dataObject, String methodToCall,
            List<String> pkNames) {

        if (dataObject == null){
            return StringUtils.EMPTY;
        }

        TermInfo term = (TermInfo)dataObject;

        String acalId = null;
        try {
            List<AcademicCalendarInfo> atps = getAcademicCalendarService().getAcademicCalendarsForTerm(term.getId(), TestHelper.getContext1());
            if (!atps.isEmpty()){
                acalId = atps.get(0).getId();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (StringUtils.isBlank(acalId)){
           return StringUtils.EMPTY;
        }

        Properties props = new Properties();
        props.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, "start");
        props.put("id",acalId);
        props.put(UifParameters.VIEW_ID, "academicCalendarEditView");

        if (StringUtils.isNotBlank(lookupForm.getReturnLocation())) {
            props.put(KRADConstants.RETURN_LOCATION_PARAMETER, lookupForm.getReturnLocation());
        }

        return UrlFactory.parameterizeUrl("academicCalendar", props);
    }

    protected AcademicCalendarService getAcademicCalendarService() {
         if(academicCalendarService == null) {
             academicCalendarService = (AcademicCalendarService) GlobalResourceLoader.getService(new QName(AcademicCalendarServiceConstants.NAMESPACE, AcademicCalendarServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return this.academicCalendarService;
    }

}
