package org.kuali.student.enrollment.class2.acal.service.impl;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.lookup.LookupableImpl;
import org.kuali.rice.krad.web.form.LookupForm;
import org.kuali.student.enrollment.acal.constants.AcademicCalendarServiceConstants;
import org.kuali.student.enrollment.acal.dto.HolidayCalendarInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//Core slice class.
@Deprecated
public class HolidayCalendarLookupableImpl  extends LookupableImpl {

    public final static String ACADEMIC_CALENDAR_YEAR_KEY = "year";
    private transient AcademicCalendarService academicCalendarService;


    @Override
    protected List<?> getSearchResults(LookupForm lookupForm, Map<String, String> fieldValues, boolean unbounded) {

        List<HolidayCalendarInfo> holidayCalendarInfoList = new ArrayList<HolidayCalendarInfo>();

        Integer academicCalendarYear = new Integer("2011");
        ContextInfo context = new ContextInfo();
        try{
            holidayCalendarInfoList = getAcademicCalendarService().getHolidayCalendarsByStartYear(academicCalendarYear, context);
            return holidayCalendarInfoList;
        }catch (InvalidParameterException ipe){
            System.out.println("call getAcademicCalendarService().getHolidayCalendarsByStartYear(startYear, context), and get InvalidParameterException:  "+ipe.toString());
        }catch (MissingParameterException mpe){
            System.out.println("call getAcademicCalendarService().getHolidayCalendarsByStartYear(startYear, context), and get MissingParameterException:  "+mpe.toString());
        }catch (OperationFailedException ofe){
            System.out.println("call getAcademicCalendarService().getHolidayCalendarsByStartYear(startYear, context), and get OperationFailedException:  "+ofe.toString());
        }catch (PermissionDeniedException pde){
            System.out.println("call getAcademicCalendarService().getHolidayCalendarsByStartYear(startYear, context), and get PermissionDeniedException:  "+pde.toString());
        }
        return null;

    }

    protected AcademicCalendarService getAcademicCalendarService() {
        if(academicCalendarService == null) {
            academicCalendarService = (AcademicCalendarService) GlobalResourceLoader.getService(new QName(AcademicCalendarServiceConstants.NAMESPACE, AcademicCalendarServiceConstants.SERVICE_NAME_LOCAL_PART));
        }

        return academicCalendarService;
    }
}


