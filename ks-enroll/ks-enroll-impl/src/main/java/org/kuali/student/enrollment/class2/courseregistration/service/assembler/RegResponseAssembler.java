package org.kuali.student.enrollment.class2.courseregistration.service.assembler;

import org.kuali.student.enrollment.courseregistration.dto.RegResponseInfo;
import org.kuali.student.enrollment.lpr.dto.LqrTransactionInfo;
import org.kuali.student.r2.common.assembler.DTOAssembler;
import org.kuali.student.r2.common.dto.ContextInfo;

public class RegResponseAssembler implements DTOAssembler<RegResponseInfo, LqrTransactionInfo> {

    @Override
    public RegResponseInfo assemble(LqrTransactionInfo baseDTO, ContextInfo context) {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public LqrTransactionInfo disassemble(RegResponseInfo businessDTO, ContextInfo context) {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

}
