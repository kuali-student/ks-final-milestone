package org.kuali.student.enrollment.class2.acal.keyvalue;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.student.enrollment.acal.dto.HolidayCalendarInfo;
import org.kuali.student.enrollment.acal.dto.HolidayInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.class2.acal.dto.HolidayCalendarWrapper;
import org.kuali.student.enrollment.class2.acal.dto.HolidayWrapper;
import org.kuali.student.enrollment.class2.acal.form.AcademicCalendarForm;
import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.common.util.constants.AtpServiceConstants;
import org.kuali.student.r2.core.type.dto.TypeInfo;

import javax.xml.namespace.QName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;


public class HolidayWrapperListFinder extends UifKeyValuesFinderBase implements Serializable {

    private static final long serialVersionUID = 1L;
    private transient AcademicCalendarService acalService;

    @Override
    public List<KeyValue> getKeyValues(ViewModel model) {

        List<KeyValue> keyValues = new ArrayList<KeyValue>();
        AcademicCalendarForm acalForm = (AcademicCalendarForm)model;
        Date startDate = acalForm.getAcademicCalendarInfo().getStartDate(); 
        if (startDate == null) {
            startDate = new Date();
        }

        if (startDate != null){
            SimpleDateFormat simpleDateformat = new SimpleDateFormat("yyyy");
            Integer theStartYear = new Integer(simpleDateformat.format(startDate));
            ContextInfo context = new ContextInfo();
            try{
                List<HolidayCalendarInfo> holidayCalendarInfoList = getAcalService().getHolidayCalendarsByStartYear(theStartYear, context);
                for(HolidayCalendarInfo holidayCalendarInfo:holidayCalendarInfoList){
                    ConcreteKeyValue keyValue = new ConcreteKeyValue();
                    keyValue.setKey(holidayCalendarInfo.getId());
                    keyValue.setValue(holidayCalendarInfo.getName());
                    keyValues.add(keyValue);
                }
                return keyValues;

            }catch (InvalidParameterException ipe){
                System.out.println("call AcademicCalendarService.getHolidayCalendarsByStartYear(startYear, context), and get InvalidParameterException:  "+ipe.toString());
            }catch (MissingParameterException mpe){
                System.out.println("call AcademicCalendarService.getHolidayCalendarsByStartYear(startYear, context), and get MissingParameterException:  "+mpe.toString());
            }catch (OperationFailedException ofe){
                System.out.println("call AcademicCalendarService.getHolidayCalendarsByStartYear(startYear, context), and get OperationFailedException:  "+ofe.toString());
            }catch (PermissionDeniedException pde){
                System.out.println("call AcademicCalendarService.getHolidayCalendarsByStartYear(startYear, context), and get PermissionDeniedException:  "+pde.toString());
            }
        }

        return keyValues;
    }

    public AcademicCalendarService getAcalService() {
        if(acalService == null) {
            acalService = (AcademicCalendarService) GlobalResourceLoader.getService(new QName(CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "acal", "AcademicCalendarService"));
        }
        return this.acalService;
    }
}
