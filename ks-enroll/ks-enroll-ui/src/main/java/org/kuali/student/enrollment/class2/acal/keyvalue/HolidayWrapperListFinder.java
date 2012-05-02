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
        Date endDate = acalForm.getAcademicCalendarInfo().getEndDate();
        SimpleDateFormat simpleDateformat = new SimpleDateFormat("yyyy");
        List<HolidayCalendarInfo> holidayCalendarInfoList = new ArrayList<HolidayCalendarInfo>();
        //when there's no user input on acalInfo startDate and endDate, set startDate equal to current date.
        // Therefore, it uses the current year to pull out available official HC list
        if (startDate == null && endDate == null ) {
            startDate = new Date();
        }

        //When an user only inputs the startDate or endDate, use the year information from either startDate or
        // endDate field to pull out available official HC List
        if ((startDate != null && endDate == null) || (startDate == null && endDate != null) ){
            Integer theStartYear;
            if (startDate != null)
                theStartYear = new Integer(simpleDateformat.format(startDate));
            else
                theStartYear = new Integer(simpleDateformat.format(endDate));
            holidayCalendarInfoList = _buildOfficialHolidayCalendarInfoList (theStartYear);
        }

        //When the user inputs both startDate and endDate,
        if (startDate != null && endDate != null)  {
            Integer theStartYear = new Integer(simpleDateformat.format(startDate));
            Integer theEndYear = new Integer(simpleDateformat.format(endDate));
            if (theEndYear <= theStartYear){
                //only query HC based on theStartYear
                holidayCalendarInfoList = _buildOfficialHolidayCalendarInfoList (theStartYear);   
                
            }else{
                for (int year=theStartYear.intValue(); year<=theEndYear.intValue(); year++ ){
                    try{
                        holidayCalendarInfoList.addAll(_buildOfficialHolidayCalendarInfoList(new Integer(year)));
                    }catch (Exception e){
                        //ToDo:
                    }
                }
            }
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
    private List<HolidayCalendarInfo> _buildOfficialHolidayCalendarInfoList (Integer theStartYear){
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
            System.out.println("call AcademicCalendarService.getHolidayCalendarsByStartYear(startYear, context), and get InvalidParameterException:  "+ipe.toString());
        }catch (MissingParameterException mpe){
            System.out.println("call AcademicCalendarService.getHolidayCalendarsByStartYear(startYear, context), and get MissingParameterException:  "+mpe.toString());
        }catch (OperationFailedException ofe){
            System.out.println("call AcademicCalendarService.getHolidayCalendarsByStartYear(startYear, context), and get OperationFailedException:  "+ofe.toString());
        }catch (PermissionDeniedException pde){
            System.out.println("call AcademicCalendarService.getHolidayCalendarsByStartYear(startYear, context), and get PermissionDeniedException:  "+pde.toString());
        }
        return  hcOfficialList;
        
    }

    public AcademicCalendarService getAcalService() {
        if(acalService == null) {
            acalService = (AcademicCalendarService) GlobalResourceLoader.getService(new QName(CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "acal", "AcademicCalendarService"));
        }
        return this.acalService;
    }
}
