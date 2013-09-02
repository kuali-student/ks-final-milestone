package org.kuali.student.r2.core.acal.service.assembler;

import org.kuali.student.r2.core.acal.dto.AcalEventInfo;
import org.kuali.student.r2.common.assembler.AssemblyException;
import org.kuali.student.r2.common.assembler.DTOAssembler;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.core.atp.dto.MilestoneInfo;
import org.kuali.student.r2.core.atp.service.AtpService;

public class AcalEventAssembler  implements DTOAssembler<AcalEventInfo, MilestoneInfo> {
    
    private AtpService atpService;
    
    @Override
    public AcalEventInfo assemble(MilestoneInfo milestoneInfo, ContextInfo context) throws AssemblyException {
        
        if (milestoneInfo == null) {
            return null;
        }

        AcalEventInfo acalEventInfo = new AcalEventInfo();
        acalEventInfo.setId(milestoneInfo.getId());
        acalEventInfo.setName(milestoneInfo.getName());
        acalEventInfo.setDescr(milestoneInfo.getDescr());

        acalEventInfo.setStartDate(milestoneInfo.getStartDate());
        acalEventInfo.setEndDate(milestoneInfo.getEndDate());
        acalEventInfo.setIsAllDay(milestoneInfo.getIsAllDay());
        acalEventInfo.setIsDateRange(milestoneInfo.getIsDateRange());

        acalEventInfo.setStateKey(milestoneInfo.getStateKey());
        acalEventInfo.setTypeKey(milestoneInfo.getTypeKey());

        acalEventInfo.setMeta(milestoneInfo.getMeta());
        acalEventInfo.setAttributes(milestoneInfo.getAttributes());

        return acalEventInfo;
    }

    @Override
    public MilestoneInfo disassemble(AcalEventInfo acalEventInfo, ContextInfo context) throws AssemblyException {
        
        if (acalEventInfo == null) {
            return null;
        }

        MilestoneInfo msInfo = new MilestoneInfo();

        msInfo.setId(acalEventInfo.getId());
        msInfo.setName(acalEventInfo.getName());
        msInfo.setDescr(acalEventInfo.getDescr());

        msInfo.setStartDate(acalEventInfo.getStartDate());
        msInfo.setEndDate(acalEventInfo.getEndDate());
        msInfo.setIsAllDay(acalEventInfo.getIsAllDay());
        msInfo.setIsDateRange(acalEventInfo.getIsDateRange());
        msInfo.setIsInstructionalDay(false);//default to false
        msInfo.setIsRelative(false);

        msInfo.setStateKey(acalEventInfo.getStateKey());
        msInfo.setTypeKey(acalEventInfo.getTypeKey());

        msInfo.setMeta(acalEventInfo.getMeta());
        msInfo.setAttributes(acalEventInfo.getAttributes());

        return msInfo;
    }

    public AtpService getAtpService() {
        return atpService;
    }

    public void setAtpService(AtpService atpService) {
        this.atpService = atpService;
    }
}
