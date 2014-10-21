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
package org.kuali.student.enrollment.class2.acal.keyvalue;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.criteria.Predicate;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.student.common.util.ContextBuilder;
import org.kuali.student.enrollment.class2.acal.dto.HolidayCalendarWrapper;
import org.kuali.student.enrollment.class2.acal.form.AcademicCalendarForm;
import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.date.DateFormatters;
import org.kuali.student.r2.core.acal.dto.HolidayCalendarInfo;
import org.kuali.student.r2.core.acal.service.AcademicCalendarService;
import org.kuali.student.r2.core.constants.AtpServiceConstants;

import javax.xml.namespace.QName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.kuali.rice.core.api.criteria.PredicateFactory.and;
import static org.kuali.rice.core.api.criteria.PredicateFactory.equal;
import static org.kuali.rice.core.api.criteria.PredicateFactory.greaterThanOrEqual;
import static org.kuali.rice.core.api.criteria.PredicateFactory.lessThanOrEqual;

/**
 * This is used to display all the official holiday calendars for an academic calendar.
 *
 * @author Kuali Student Team
 */
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

        List<HolidayCalendarInfo> holidayCalendarInfoList = null;
        //when there's no user input on acalInfo startDate and endDate, return an empty list of calendars
        if (startDate == null && endDate == null ) {
            holidayCalendarInfoList = Collections.emptyList();
        }
        //When the user inputs both startDate and endDate,
        else if (startDate != null && endDate != null)  {
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
                theStartYear = Integer.valueOf(DateFormatters.DEFULT_YEAR_FORMATTER.format(startDate));
            else
                theStartYear = Integer.valueOf(DateFormatters.DEFULT_YEAR_FORMATTER.format(endDate));
            holidayCalendarInfoList = buildOfficialHolidayCalendarInfoList(theStartYear);
        }

        // build a list of already added holiday calendars to avoid including them in the returned list
        Collection<String> addedHolidayCalendarIds = new ArrayList<String>(acalForm.getHolidayCalendarList().size());
        for(HolidayCalendarWrapper hcw : acalForm.getHolidayCalendarList()) {
            addedHolidayCalendarIds.add(hcw.getId());
        }

        if (holidayCalendarInfoList != null && holidayCalendarInfoList.isEmpty()){
            ConcreteKeyValue keyValue = new ConcreteKeyValue();
            keyValue.setKey("");
            keyValue.setValue("No Holiday Calendar available");
            keyValues.add(keyValue);
        }else{
            ConcreteKeyValue keyValue = new ConcreteKeyValue();
            keyValue.setKey("");
            keyValue.setValue("");
            keyValues.add(keyValue);
            //FindBugs - 'if' logic needs to be checked...
            for(HolidayCalendarInfo holidayCalendarInfo:holidayCalendarInfoList) {
                if (addedHolidayCalendarIds.contains(holidayCalendarInfo.getId())) {
                    continue;
                }
                keyValue = new ConcreteKeyValue();
                keyValue.setKey(holidayCalendarInfo.getId());
                keyValue.setValue(holidayCalendarInfo.getName());
                keyValues.add(keyValue);
            }

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

    /**
     * This builds a criteria that will detect any Overlap between a
     * HCAL and an ACAL.
     *
     * It uses the basic date overlap logic:
     * hcalStart <= acalEnd && hcalEnd >= acalStart
     *
     * @param startDate Acal Start Date
     * @param endDate   Acal End Date
     * @return
     */
    private QueryByCriteria buildQueryByCriteria(Date startDate, Date endDate){
        List<Predicate> predicates = new ArrayList<Predicate>();

        Predicate startDatePredicate = and(greaterThanOrEqual(END_DATE, startDate),
                                                   lessThanOrEqual(START_DATE, endDate));

        predicates.add(startDatePredicate);
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
