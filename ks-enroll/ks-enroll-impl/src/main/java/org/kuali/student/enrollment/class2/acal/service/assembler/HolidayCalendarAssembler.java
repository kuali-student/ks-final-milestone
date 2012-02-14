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
            HolidayCalendarInfo holidayCalendarInfo = new HolidayCalendarInfo();
            holidayCalendarInfo.setId(atp.getId());
            holidayCalendarInfo.setName(atp.getName());
            holidayCalendarInfo.setAdminOrgId(atp.getAdminOrgId());
            holidayCalendarInfo.setDescr(atp.getDescr());
            holidayCalendarInfo.setStartDate(atp.getStartDate());
            holidayCalendarInfo.setEndDate(atp.getEndDate());
            holidayCalendarInfo.setTypeKey(atp.getTypeKey());
            holidayCalendarInfo.setStateKey(atp.getStateKey());
            holidayCalendarInfo.setMeta(atp.getMeta());
//            TODO: assemble CampusKeys as a dynamic attribute
//            use AtpServiceConstants.CAMPUS_KEY_DYNAMIC_ATTRIBUTE_KEY
            holidayCalendarInfo.setAttributes(atp.getAttributes());
      
            return holidayCalendarInfo;
        }
        else
            return null;
    }

    @Override
    public AtpInfo disassemble(HolidayCalendarInfo holidayCalendarInfo, ContextInfo context) throws AssemblyException{
        if (holidayCalendarInfo != null){
            AtpInfo atp = new AtpInfo();
            atp.setId(holidayCalendarInfo.getId());
            atp.setName(holidayCalendarInfo.getName());
            atp.setAdminOrgId(holidayCalendarInfo.getAdminOrgId());
            atp.setDescr(holidayCalendarInfo.getDescr());
            atp.setStartDate(holidayCalendarInfo.getStartDate());
            atp.setEndDate(holidayCalendarInfo.getEndDate());
            atp.setTypeKey(AtpServiceConstants.ATP_HOLIDAY_CALENDAR_TYPE_KEY);
            atp.setStateKey(holidayCalendarInfo.getStateKey());
            atp.setMeta(holidayCalendarInfo.getMeta());

//            TODO: disassemble CampusKeys as a dynamic attribute 
//            use AtpServiceConstants.CAMPUS_KEY_DYNAMIC_ATTRIBUTE_KEY
            List<AttributeInfo> attributes = (null != holidayCalendarInfo.getAttributes() ? holidayCalendarInfo.getAttributes() : new ArrayList<AttributeInfo>());

         
            atp.setAttributes(attributes);

            return atp;
        }

        return null;
    }




}
