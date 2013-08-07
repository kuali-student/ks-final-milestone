package org.kuali.student.enrollment.class2.acal.service.impl;


import org.apache.log4j.Logger;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.inquiry.InquirableImpl;
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
import org.kuali.student.r2.core.class1.state.dto.StateInfo;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HolidayCalendarWrapperInquiryViewHelperServiceImpl extends InquirableImpl {
    private static final long serialVersionUID = 1L;
    private final static Logger LOG = Logger.getLogger(ManageSOCViewHelperServiceImpl.class);
    private final static String exceptionComment1 = "call getAcademicCalendarService().getHolidaysForHolidayCalendar(holidayCalendarId, context), and get ";
    private final static String exceptionComment2 = "call getAcademicCalendarService().getHolidayCalendar(holidayCalendarId, context), and get ";
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
                CommonUtils.logDebugMsg(LOG, ">>>holidayCalendarKey is null");
                return null;
            }
            else {
                CommonUtils.logDebugMsg(LOG, ">>>holidayCalendarKey ="+holidayCalendarKey);
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
                CommonUtils.logDebugMsg(LOG, exceptionComment1 + "DoesNotExistException:      " + dnee.toString());
            }catch (InvalidParameterException ipe){
                CommonUtils.logDebugMsg(LOG, exceptionComment1 + "InvalidParameterException:  " + ipe.toString());
            }catch (MissingParameterException mpe){
                CommonUtils.logDebugMsg(LOG, exceptionComment1 + "MissingParameterException:  " + mpe.toString());
            }catch (OperationFailedException ofe){
                CommonUtils.logDebugMsg(LOG, exceptionComment1 + "OperationFailedException:   " + ofe.toString());
            }catch (PermissionDeniedException pde){
                CommonUtils.logDebugMsg(LOG, exceptionComment1 + "PermissionDeniedException:  " + pde.toString());
            }
            return holidayCalendarWrapper;

        }catch (DoesNotExistException dnee){
            CommonUtils.logDebugMsg(LOG, exceptionComment2 + "DoesNotExistException:      " + dnee.toString());
        }catch (InvalidParameterException ipe){
            CommonUtils.logDebugMsg(LOG, exceptionComment2 + "InvalidParameterException:  " + ipe.toString());
        }catch (MissingParameterException mpe){
            CommonUtils.logDebugMsg(LOG, exceptionComment2 + "MissingParameterException:  " + mpe.toString());
        }catch (OperationFailedException ofe){
            CommonUtils.logDebugMsg(LOG, exceptionComment2 + "OperationFailedException:   " + ofe.toString());
        }catch (PermissionDeniedException pde){
            CommonUtils.logDebugMsg(LOG, exceptionComment2 + "PermissionDeniedException:  " + pde.toString());
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
