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
 * Created by bobhurt on 2/2/12
 */
package org.kuali.student.enrollment.class2.acal.service.impl;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.inquiry.InquirableImpl;
import org.kuali.student.enrollment.acal.dto.HolidayCalendarInfo;
import org.kuali.student.enrollment.acal.dto.HolidayInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.class2.acal.form.HolidayCalendarForm;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.*;

import javax.xml.namespace.QName;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class HolidayCalendarViewHelperServiceInquirableImpl extends InquirableImpl {
    public final static String ACADEMIC_CALENDAR_KEY = "key";
    private transient AcademicCalendarService academicCalendarService;

//    public HolidayCalendarViewHelperServiceInquirableImpl() {
//        int x;
//        x = 0;
//    }

    @Override
    public HolidayCalendarInfo retrieveDataObject(Map<String, String> parameters) {
        HolidayCalendarInfo holidayCalendarInfo = null;

        //String academicCalendarKey = parameters.get(ACADEMIC_CALENDAR_KEY);
        String hcId = parameters.get("id");
        try{
            holidayCalendarInfo = getAcademicCalendarService().getHolidayCalendar(hcId, getContextInfo());
            return holidayCalendarInfo;
        } catch(Exception e) {
//        }catch (DoesNotExistException dnee){
//
//        }catch (InvalidParameterException ipe){
//
//        }catch (MissingParameterException mpe){
//
//        }catch (OperationFailedException ofe){
//
//        }catch (PermissionDeniedException pde){
        }
        return null;

    }

    public List<HolidayInfo> getHolidaysForHolidayCalendar(HolidayCalendarForm hcForm) throws Exception{
        HolidayCalendarInfo hcInfo = hcForm.getHolidayCalendarInfo();
        return getAcademicCalendarService().getHolidaysForHolidayCalendar(hcInfo.getId(), getContextInfo());
    }

    protected AcademicCalendarService getAcademicCalendarService() {
        if (academicCalendarService == null) {
            academicCalendarService = (AcademicCalendarService) GlobalResourceLoader.getService(new QName("http://student.kuali.org/wsdl/acal", "AcademicCalendarService"));
        }
        return academicCalendarService;
    }

    // TODO - where does context come from?
    private ContextInfo getContextInfo(){
        return new ContextInfo();
    }
}
