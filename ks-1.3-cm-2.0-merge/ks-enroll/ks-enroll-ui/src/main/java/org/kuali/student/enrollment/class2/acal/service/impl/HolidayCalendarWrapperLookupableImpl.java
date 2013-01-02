package org.kuali.student.enrollment.class2.acal.service.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.lookup.LookupableImpl;
import org.kuali.rice.krad.web.form.LookupForm;
import org.kuali.student.enrollment.acal.constants.AcademicCalendarServiceConstants;
import org.kuali.student.enrollment.acal.dto.HolidayCalendarInfo;
import org.kuali.student.enrollment.acal.dto.HolidayInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.class2.acal.dto.HolidayCalendarWrapper;
import org.kuali.student.enrollment.class2.acal.dto.HolidayWrapper;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.kuali.student.r2.common.dto.TypeInfo;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class HolidayCalendarWrapperLookupableImpl extends LookupableImpl {

    public final static String ACADEMIC_CALENDAR_START_YEAR_KEY = "acalStartYear";
    private transient AcademicCalendarService academicCalendarService;


    @Override
    protected List<?> getSearchResults(LookupForm lookupForm, Map<String, String> fieldValues, boolean unbounded) {

        List<HolidayCalendarWrapper> holidayCalendarWrapperList = new ArrayList<HolidayCalendarWrapper>();
        List<HolidayCalendarInfo> holidayCalendarInfoList = new ArrayList<HolidayCalendarInfo>();
        List<HolidayWrapper> holidays = new ArrayList<HolidayWrapper>();

        Integer theStartYear = new Integer(fieldValues.get(ACADEMIC_CALENDAR_START_YEAR_KEY));
        ContextInfo context = new ContextInfo();
        try{
            holidayCalendarInfoList = getAcademicCalendarService().getHolidayCalendarsByStartYear(theStartYear, context);
            for(HolidayCalendarInfo holidayCalendarInfo:holidayCalendarInfoList){
                if (StringUtils.equals(holidayCalendarInfo.getStateKey(), AtpServiceConstants.ATP_OFFICIAL_STATE_KEY)){
                    HolidayCalendarWrapper holidayCalendarWrapper = new HolidayCalendarWrapper();
                    holidayCalendarWrapper.setHolidayCalendarInfo(holidayCalendarInfo);
                    holidayCalendarWrapper.setId(holidayCalendarInfo.getId());
                    holidayCalendarWrapper.setAcalStartYear(theStartYear.toString());
                    try {
                        List<HolidayInfo> holidayInfoList = getAcademicCalendarService().getHolidaysForHolidayCalendar(holidayCalendarInfo.getId(), context);
                        for(HolidayInfo holidayInfo : holidayInfoList){
                            HolidayWrapper holiday = new HolidayWrapper();
                            holiday.setHolidayInfo(holidayInfo);
                            TypeInfo typeInfo = getAcademicCalendarService().getHolidayType(holidayInfo.getTypeKey(), context);
                            holiday.setTypeName(typeInfo.getName());
                            holidays.add(holiday);
                        }
                        holidayCalendarWrapper.setHolidays(holidays);
                    }catch (DoesNotExistException dnee){
                        System.out.println("call getAcademicCalendarService().getHolidaysForHolidayCalendar(holidayCalendarId, context), and get DoesNotExistException:  "+dnee.toString());
                    }catch (InvalidParameterException ipe){
                        System.out.println("call getAcademicCalendarService().getHolidaysForHolidayCalendar(holidayCalendarId, context), and get InvalidParameterException:  "+ipe.toString());
                    }catch (MissingParameterException mpe){
                        System.out.println("call getAcademicCalendarService().getHolidaysForHolidayCalendar(holidayCalendarId, context), and get MissingParameterException:  "+mpe.toString());
                    }catch (OperationFailedException ofe){
                        System.out.println("call getAcademicCalendarService().getHolidaysForHolidayCalendar(holidayCalendarId, context), and get OperationFailedException:  "+ofe.toString());
                    }catch (PermissionDeniedException pde){
                        System.out.println("call getAcademicCalendarService().getHolidaysForHolidayCalendar(holidayCalendarId, context), and get PermissionDeniedException:  "+pde.toString());
                    }
                    holidayCalendarWrapperList.add(holidayCalendarWrapper);
                }
            }
            
            return holidayCalendarWrapperList;

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

