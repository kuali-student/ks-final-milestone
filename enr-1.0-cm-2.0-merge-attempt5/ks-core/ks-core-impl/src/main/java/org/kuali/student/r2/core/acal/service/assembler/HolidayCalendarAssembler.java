package org.kuali.student.r2.core.acal.service.assembler;

import java.util.ArrayList;
import org.kuali.student.r2.core.acal.dto.HolidayCalendarInfo;
import org.kuali.student.r2.common.assembler.AssemblyException;
import org.kuali.student.r2.common.assembler.DTOAssembler;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.kuali.student.r2.core.atp.dto.AtpInfo;

public class HolidayCalendarAssembler implements DTOAssembler<HolidayCalendarInfo, AtpInfo> {

    @Override
    public HolidayCalendarInfo assemble(AtpInfo atp, ContextInfo context) throws AssemblyException {
        if (atp != null) {
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
            for (AttributeInfo attr : atp.getAttributes()) {
                if (attr.getKey().equals(AtpServiceConstants.CAMPUS_LOCATION)) {
                    holidayCalendarInfo.getCampusKeys().add(attr.getValue());
                } else {
                    holidayCalendarInfo.getAttributes().add(attr);
                }
            }
            return holidayCalendarInfo;
        } else {
            return null;
        }
    }

    @Override
    public AtpInfo disassemble(HolidayCalendarInfo holidayCalendarInfo, ContextInfo context) throws AssemblyException {
        if (holidayCalendarInfo != null) {
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
            atp.getAttributes().addAll(holidayCalendarInfo.getAttributes());
            // TODO: fix this essentially flawed mechanism -- that happens because of the assembler structure
//            it really should take in as a paraemter the existing ATP, if there is one
//            and find/match the campus keys there and update the set of attributes not replacing them
//            doing it this way we lose the original id of attribute on update 
            // the making it hard (wrong?) to store it later
            for (String campusKey : holidayCalendarInfo.getCampusKeys()) {
                AttributeInfo attr = new AttributeInfo();
                attr.setKey(AtpServiceConstants.CAMPUS_LOCATION);
                attr.setValue(campusKey);
                atp.getAttributes().add(attr);
            }
            return atp;
        }

        return null;
    }
}
