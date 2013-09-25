package org.kuali.student.enrollment.class2.acal.service.impl;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.lookup.LookupableImpl;
import org.kuali.rice.krad.web.form.LookupForm;
import org.kuali.student.r2.core.acal.dto.HolidayCalendarInfo;
import org.kuali.student.r2.core.acal.dto.HolidayInfo;
import org.kuali.student.r2.core.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.class2.acal.dto.HolidayCalendarWrapper;
import org.kuali.student.enrollment.class2.acal.dto.HolidayWrapper;
import org.kuali.student.enrollment.class2.acal.util.CommonUtils;
import org.kuali.student.enrollment.class2.courseoffering.service.impl.ManageSOCViewHelperServiceImpl;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.core.constants.AcademicCalendarServiceConstants;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.constants.AtpServiceConstants;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class HolidayCalendarWrapperLookupableImpl extends LookupableImpl {
    private static final long serialVersionUID = 1L;
    private final static Logger LOG = Logger.getLogger(ManageSOCViewHelperServiceImpl.class);
    private final static String exceptionComment1 = "call getAcademicCalendarService().getHolidaysForHolidayCalendar(holidayCalendarId, context), and get ";
    private final static String exceptionComment2 = "call getAcademicCalendarService().getHolidayCalendarsByStartYear(startYear, context), and get ";

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
                        CommonUtils.logDebugMsg(LOG, exceptionComment1+dnee.toString());
                    }catch (InvalidParameterException ipe){
                        CommonUtils.logDebugMsg(LOG, exceptionComment1+ipe.toString());
                    }catch (MissingParameterException mpe){
                        CommonUtils.logDebugMsg(LOG, exceptionComment1+mpe.toString());
                    }catch (OperationFailedException ofe){
                        CommonUtils.logDebugMsg(LOG, exceptionComment1+ofe.toString());
                    }catch (PermissionDeniedException pde){
                        CommonUtils.logDebugMsg(LOG, exceptionComment1+pde.toString());
                    }
                    holidayCalendarWrapperList.add(holidayCalendarWrapper);
                }
            }
            
            return holidayCalendarWrapperList;

        }catch (InvalidParameterException ipe){
            CommonUtils.logDebugMsg(LOG, exceptionComment2+ipe.toString());
        }catch (MissingParameterException mpe){
            CommonUtils.logDebugMsg(LOG, exceptionComment2+mpe.toString());
        }catch (OperationFailedException ofe){
            CommonUtils.logDebugMsg(LOG, exceptionComment2+ofe.toString());
        }catch (PermissionDeniedException pde){
            CommonUtils.logDebugMsg(LOG, exceptionComment2+pde.toString());
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

