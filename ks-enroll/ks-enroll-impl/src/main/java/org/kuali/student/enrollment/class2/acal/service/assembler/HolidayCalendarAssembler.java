package org.kuali.student.enrollment.class2.acal.service.assembler;



import org.kuali.student.enrollment.acal.dto.HolidayCalendarInfo;
import org.kuali.student.r2.common.assembler.AssemblyException;
import org.kuali.student.r2.common.assembler.DTOAssembler;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.constants.AtpServiceConstants;
import org.kuali.student.r2.core.atp.dto.AtpInfo;

import java.util.ArrayList;
import java.util.List;

public class HolidayCalendarAssembler implements DTOAssembler<HolidayCalendarInfo, AtpInfo> {

    @Override
    public HolidayCalendarInfo assemble(AtpInfo atp, ContextInfo context) throws AssemblyException {
        if(atp != null){
            HolidayCalendarInfo campusCalendarInfo = new HolidayCalendarInfo();
            campusCalendarInfo.setId(atp.getKey());
            campusCalendarInfo.setName(atp.getName());
            campusCalendarInfo.setDescr(atp.getDescr());
            campusCalendarInfo.setStartDate(atp.getStartDate());
            campusCalendarInfo.setEndDate(atp.getEndDate());
            campusCalendarInfo.setTypeKey(atp.getTypeKey());
            campusCalendarInfo.setStateKey(atp.getStateKey());
            campusCalendarInfo.setMeta(atp.getMeta());
            campusCalendarInfo.setAttributes(atp.getAttributes());

       
            return campusCalendarInfo;
        }
        else
            return null;
    }

    @Override
    public AtpInfo disassemble(HolidayCalendarInfo holidayCalendarInfo, ContextInfo context) throws AssemblyException{
        if (holidayCalendarInfo != null){
            AtpInfo atp = new AtpInfo();
            atp.setKey(holidayCalendarInfo.getId());
            atp.setName(holidayCalendarInfo.getName());
            atp.setDescr(holidayCalendarInfo.getDescr());
            atp.setStartDate(holidayCalendarInfo.getStartDate());
            atp.setEndDate(holidayCalendarInfo.getEndDate());
            atp.setTypeKey(AtpServiceConstants.ATP_CAMPUS_CALENDAR_TYPE_KEY);
            atp.setStateKey(holidayCalendarInfo.getStateKey());
            atp.setMeta(holidayCalendarInfo.getMeta());

            List<AttributeInfo> attributes = (null != holidayCalendarInfo.getAttributes() ? holidayCalendarInfo.getAttributes() : new ArrayList<AttributeInfo>());

         
            atp.setAttributes(attributes);

            return atp;
        }

        return null;
    }




}
