package org.kuali.student.enrollment.class2.acal.service.assembler;

import org.kuali.student.enrollment.acal.dto.HolidayInfo;
import org.kuali.student.r2.common.assembler.AssemblyException;
import org.kuali.student.r2.common.assembler.DTOAssembler;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.core.atp.dto.MilestoneInfo;

public class HolidayAssembler  implements DTOAssembler<HolidayInfo, MilestoneInfo> {

    @Override
    public HolidayInfo assemble(MilestoneInfo milestoneInfo, ContextInfo context) throws AssemblyException {

        if (milestoneInfo == null){
            return null;
        }

        HolidayInfo holidayInfo = new HolidayInfo();
        holidayInfo.setId(milestoneInfo.getId());
        holidayInfo.setName(milestoneInfo.getName());
        holidayInfo.setDescr(milestoneInfo.getDescr());

        holidayInfo.setStartDate(milestoneInfo.getStartDate());
        holidayInfo.setEndDate(milestoneInfo.getEndDate());
        holidayInfo.setIsAllDay(milestoneInfo.getIsAllDay());
        holidayInfo.setIsDateRange(milestoneInfo.getIsDateRange());
        holidayInfo.setIsInstructionalDay(milestoneInfo.getIsInstructionalDay());
        holidayInfo.setStateKey(milestoneInfo.getStateKey());
        holidayInfo.setTypeKey(milestoneInfo.getTypeKey());

        holidayInfo.setMeta(milestoneInfo.getMeta());
        holidayInfo.setAttributes(milestoneInfo.getAttributes());

        return holidayInfo;
    }

    @Override
    public MilestoneInfo disassemble(HolidayInfo holidayInfo, ContextInfo context) throws AssemblyException {

        if (holidayInfo == null){
            return null;
        }

        MilestoneInfo msInfo = new MilestoneInfo();

        msInfo.setId(holidayInfo.getId());
        msInfo.setName(holidayInfo.getName());
        msInfo.setDescr(holidayInfo.getDescr());

        msInfo.setStartDate(holidayInfo.getStartDate());
        msInfo.setEndDate(holidayInfo.getEndDate());

        msInfo.setIsAllDay(holidayInfo.getIsAllDay());
        msInfo.setIsDateRange(holidayInfo.getIsDateRange());
        msInfo.setIsInstructionalDay(holidayInfo.getIsInstructionalDay());
        msInfo.setIsRelative(false);

        msInfo.setStateKey(holidayInfo.getStateKey());
        msInfo.setTypeKey(holidayInfo.getTypeKey());

        msInfo.setMeta(holidayInfo.getMeta());
        msInfo.setAttributes(holidayInfo.getAttributes());

        return msInfo;
    }
}
