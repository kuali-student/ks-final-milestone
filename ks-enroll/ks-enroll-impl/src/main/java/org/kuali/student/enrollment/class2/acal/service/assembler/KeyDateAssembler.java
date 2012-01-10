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
        keyDateInfo.setDescr(baseDTO.getDescr());
        keyDateInfo.setEndDate(baseDTO.getEndDate());
        return keyDateInfo;
    }

    @Override
    public MilestoneInfo disassemble(KeyDateInfo businessDTO, ContextInfo context) throws AssemblyException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

}
