package org.kuali.student.enrollment.class2.acal.service.impl;


import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.inquiry.InquirableImpl;
import org.kuali.student.enrollment.acal.constants.AcademicCalendarServiceConstants;
import org.kuali.student.enrollment.acal.dto.HolidayCalendarInfo;
import org.kuali.student.enrollment.acal.dto.HolidayInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.class2.acal.dto.HolidayCalendarWrapper;
import org.kuali.student.enrollment.class2.acal.dto.HolidayWrapper;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.common.state.dto.StateInfo;
import org.kuali.student.r2.common.dto.TypeInfo;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HolidayCalendarWrapperInquiryViewHelperServiceImpl extends InquirableImpl {
    //	 public final static String ACADEMIC_CALENDAR_KEY = "academicCalendarInfo.key";
    public final static String HOLIDAY_CALENDAR_WRAPPER_KEY = "id";
    private transient AcademicCalendarService academicCalendarService;

    @Override
    public HolidayCalendarWrapper retrieveDataObject(Map<String, String> parameters) {
        ContextInfo context = new ContextInfo();

        HolidayCalendarWrapper holidayCalendarWrapper = new HolidayCalendarWrapper();
        List<HolidayWrapper> holidays = new ArrayList<HolidayWrapper>();
        try{
            //need to retrieve HolidayCalendarInfo and all Holidays to form the HolidayCalendarWrapper.
            String holidayCalendarKey = parameters.get(HOLIDAY_CALENDAR_WRAPPER_KEY);
            if(holidayCalendarKey == null){
                System.out.println(">>>holidayCalendarKey is null");
                return null;
            }
            else {
                System.out.println(">>>holidayCalendarKey ="+holidayCalendarKey);
            }
            HolidayCalendarInfo holidayCalendarInfo = getAcademicCalendarService().getHolidayCalendar(holidayCalendarKey, context);
            holidayCalendarWrapper.setHolidayCalendarInfo(holidayCalendarInfo);
            holidayCalendarWrapper.setId(holidayCalendarInfo.getId());
            holidayCalendarWrapper.setAdminOrgName(getAdminOrgNameById(holidayCalendarInfo.getAdminOrgId()));
            StateInfo hcState = getAcademicCalendarService().getHolidayCalendarState(holidayCalendarInfo.getStateKey(), context);
            holidayCalendarWrapper.setStateName(hcState.getName());

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
            return holidayCalendarWrapper;

        }catch (DoesNotExistException dnee){
            System.out.println("call getAcademicCalendarService().getHolidayCalendar(holidayCalendarId, context), and get DoesNotExistException:  "+dnee.toString());
        }catch (InvalidParameterException ipe){
            System.out.println("call getAcademicCalendarService().getHolidayCalendar(holidayCalendarId, context), and get InvalidParameterException:  "+ipe.toString());
        }catch (MissingParameterException mpe){
            System.out.println("call getAcademicCalendarService().getHolidayCalendar(holidayCalendarId, context), and get MissingParameterException:  "+mpe.toString());
        }catch (OperationFailedException ofe){
            System.out.println("call getAcademicCalendarService().getHolidayCalendar(holidayCalendarId, context), and get OperationFailedException:  "+ofe.toString());
        }catch (PermissionDeniedException pde){
            System.out.println("call getAcademicCalendarService().getHolidayCalendar(holidayCalendarId, context), and get PermissionDeniedException:  "+pde.toString());
        }
        return null;
    }

    private String getAdminOrgNameById(String id){
        //TODO: hard-coded for now, going to call OrgService
        String adminOrgName = null;
        Map<String, String> allHcOrgs = new HashMap<String, String>();
        allHcOrgs.put("102", "Registrar's Office");

        if(allHcOrgs.containsKey(id)){
            adminOrgName = allHcOrgs.get(id);
        }

        return adminOrgName;
    }

    protected AcademicCalendarService getAcademicCalendarService() {
        if(academicCalendarService == null) {
            academicCalendarService = (AcademicCalendarService) GlobalResourceLoader.getService(new QName(AcademicCalendarServiceConstants.NAMESPACE, AcademicCalendarServiceConstants.SERVICE_NAME_LOCAL_PART));
        }

        return academicCalendarService;
    }
}
