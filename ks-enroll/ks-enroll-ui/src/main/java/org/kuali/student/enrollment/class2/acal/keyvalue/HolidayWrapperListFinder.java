package org.kuali.student.enrollment.class2.acal.keyvalue;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.criteria.Predicate;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.student.enrollment.acal.constants.AcademicCalendarServiceConstants;
import org.kuali.student.enrollment.acal.dto.HolidayCalendarInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.class2.acal.form.AcademicCalendarForm;
import org.kuali.student.enrollment.common.util.ContextBuilder;
import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.common.util.constants.AtpServiceConstants;

import javax.xml.namespace.QName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;

import static org.kuali.rice.core.api.criteria.PredicateFactory.*;
import static org.kuali.rice.core.api.criteria.PredicateFactory.and;


public class HolidayWrapperListFinder extends UifKeyValuesFinderBase implements Serializable {

    private static final long serialVersionUID = 1L;
    private transient AcademicCalendarService acalService;

    public final static String START_DATE = "startDate";
    public final static String END_DATE = "endDate";
    public final static String HCAL_STATE = "atpState";
    public final static String HCAL_TYPE = "atpType";

    @Override
    public List<KeyValue> getKeyValues(ViewModel model) {

        List<KeyValue> keyValues = new ArrayList<KeyValue>();
        AcademicCalendarForm acalForm = (AcademicCalendarForm)model;
        Date startDate = acalForm.getAcademicCalendarInfo().getStartDate(); 
        Date endDate = acalForm.getAcademicCalendarInfo().getEndDate();
        SimpleDateFormat simpleDateformat = new SimpleDateFormat("yyyy");
        List<HolidayCalendarInfo> holidayCalendarInfoList = new ArrayList<HolidayCalendarInfo>();
        //when there's no user input on acalInfo startDate and endDate, set startDate equal to current date.
        // Therefore, it uses the current year to pull out available official HC list
        if (startDate == null && endDate == null ) {
            startDate = new Date();
        }

        //When the user inputs both startDate and endDate,
        if (startDate != null && endDate != null)  {
            QueryByCriteria qbc = buildQueryByCriteria(startDate, endDate);
            try{
                holidayCalendarInfoList = getAcalService().searchForHolidayCalendars(qbc, getContextInfo());
             } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        // When an user only inputs the startDate or endDate, use the year information from either startDate or
        // endDate field to pull out available official HC List
        else {
            Integer theStartYear;
            if (startDate != null)
                theStartYear = new Integer(simpleDateformat.format(startDate));
            else
                theStartYear = new Integer(simpleDateformat.format(endDate));
            holidayCalendarInfoList = buildOfficialHolidayCalendarInfoList(theStartYear);
        }
        
        for(HolidayCalendarInfo holidayCalendarInfo:holidayCalendarInfoList){
            ConcreteKeyValue keyValue = new ConcreteKeyValue();
            keyValue.setKey(holidayCalendarInfo.getId());
            keyValue.setValue(holidayCalendarInfo.getName());
            keyValues.add(keyValue);
        }
        return keyValues;


    }

    //Only return HCs that are official
    private List<HolidayCalendarInfo> buildOfficialHolidayCalendarInfoList(Integer theStartYear){
        List<HolidayCalendarInfo> hcList = new ArrayList<HolidayCalendarInfo>();
        List<HolidayCalendarInfo> hcOfficialList = new ArrayList<HolidayCalendarInfo>();
        try{
            hcList = getAcalService().getHolidayCalendarsByStartYear(theStartYear, new ContextInfo());
            for(HolidayCalendarInfo hc : hcList) {
                if (StringUtils.equals(hc.getStateKey(), AtpServiceConstants.ATP_OFFICIAL_STATE_KEY)){
                    hcOfficialList.add(hc);
                }
            }
        }catch (InvalidParameterException ipe){
            throw new RuntimeException(ipe);
        }catch (MissingParameterException mpe){
            throw new RuntimeException(mpe);
        }catch (OperationFailedException ofe){
            throw new RuntimeException(ofe);
        }catch (PermissionDeniedException pde){
            throw new RuntimeException(pde);
        }
        return  hcOfficialList;
        
    }

    private QueryByCriteria buildQueryByCriteria(Date startDate, Date endDate){
        List<Predicate> predicates = new ArrayList<Predicate>();

        Predicate startDatePredicate = and(greaterThanOrEqual(START_DATE, startDate),
                                                   lessThanOrEqual(START_DATE, endDate));

        Predicate endDatePredicate = and(greaterThanOrEqual(END_DATE, startDate),
                                                lessThanOrEqual(END_DATE, endDate));

        predicates.add(or(startDatePredicate, endDatePredicate));
        predicates.add(equal(HCAL_STATE, AtpServiceConstants.ATP_OFFICIAL_STATE_KEY));
        predicates.add(equal(HCAL_TYPE, AtpServiceConstants.ATP_HOLIDAY_CALENDAR_TYPE_KEY));

        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(predicates.toArray(new Predicate[predicates.size()]));
        QueryByCriteria qbc = qbcBuilder.build();

        return qbc;
    }

    public AcademicCalendarService getAcalService() {
        if(acalService == null) {
            acalService = (AcademicCalendarService) GlobalResourceLoader.getService(new QName(CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "acal", "AcademicCalendarService"));
        }
        return this.acalService;
    }

   public ContextInfo getContextInfo() {
        return ContextBuilder.loadContextInfo();
    }
}
