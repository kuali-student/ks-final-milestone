package org.kuali.student.enrollment.class2.courseregistration.service.assembler;

import org.kuali.student.enrollment.courseregistration.dto.RegResponseInfo;
import org.kuali.student.enrollment.lpr.dto.LPRTransactionInfo;
import org.kuali.student.r2.common.assembler.DTOAssembler;
import org.kuali.student.r2.common.dto.ContextInfo;

public class RegResponseAssembler implements DTOAssembler<RegResponseInfo, LPRTransactionInfo> {

    @Override
    public RegResponseInfo assemble(LPRTransactionInfo baseDTO, ContextInfo context) {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public LPRTransactionInfo disassemble(RegResponseInfo businessDTO, ContextInfo context) {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

}
