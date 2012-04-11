package org.kuali.student.enrollment.class2.acal.service.assembler;

import org.kuali.student.enrollment.acal.dto.KeyDateInfo;
import org.kuali.student.r2.common.assembler.AssemblyException;
import org.kuali.student.r2.common.assembler.DTOAssembler;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.core.atp.dto.MilestoneInfo;

public class KeyDateAssembler implements DTOAssembler<KeyDateInfo, MilestoneInfo> {

    @Override
    public KeyDateInfo assemble(MilestoneInfo baseDTO, ContextInfo context) throws AssemblyException {
        KeyDateInfo keyDateInfo = new KeyDateInfo();

        keyDateInfo.setAttributes(baseDTO.getAttributes());
        keyDateInfo.setDescr(baseDTO.getDescr());
        keyDateInfo.setEndDate(baseDTO.getEndDate());
        keyDateInfo.setId(baseDTO.getId());
        keyDateInfo.setIsAllDay(baseDTO.getIsAllDay());
        keyDateInfo.setIsDateRange(baseDTO.getIsDateRange());
        keyDateInfo.setIsRelativeToKeyDate(baseDTO.getIsRelative());
        keyDateInfo.setMeta(baseDTO.getMeta());
        keyDateInfo.setName(baseDTO.getName());
        keyDateInfo.setRelativeAnchorKeyDateId (baseDTO.getRelativeAnchorMilestoneId());
        keyDateInfo.setStartDate(baseDTO.getStartDate());
        keyDateInfo.setStateKey(baseDTO.getStateKey());
        keyDateInfo.setTypeKey(baseDTO.getTypeKey());

        return keyDateInfo;
    }

    @Override
    public MilestoneInfo disassemble(KeyDateInfo businessDTO, ContextInfo context) throws AssemblyException {
        MilestoneInfo milestone = new MilestoneInfo();
        
        milestone.setAttributes(businessDTO.getAttributes());
        milestone.setDescr(businessDTO.getDescr() );
        milestone.setEndDate(businessDTO.getEndDate());
        milestone.setId(businessDTO.getId());
        milestone.setIsAllDay(businessDTO.getIsAllDay());
        milestone.setIsDateRange(businessDTO.getIsDateRange());
        milestone.setIsRelative(businessDTO.getIsRelativeToKeyDate());
        milestone.setIsInstructionalDay(false);
        milestone.setMeta(businessDTO.getMeta());
        milestone.setName(businessDTO.getName());
        milestone.setRelativeAnchorMilestoneId(businessDTO.getRelativeAnchorKeyDateId());
        milestone.setStartDate(businessDTO.getStartDate());
        milestone.setStateKey(businessDTO.getStateKey());
        milestone.setTypeKey(milestone.getTypeKey());
        
        return milestone;
    }
}
