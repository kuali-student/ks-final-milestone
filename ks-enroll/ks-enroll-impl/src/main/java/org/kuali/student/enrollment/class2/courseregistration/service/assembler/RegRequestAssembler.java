package org.kuali.student.enrollment.class2.courseregistration.service.assembler;

import java.util.List;

import org.kuali.student.enrollment.courseregistration.dto.RegRequestInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegRequestItemInfo;
import org.kuali.student.enrollment.lpr.dto.LqrTransactionInfo;
import org.kuali.student.enrollment.lpr.dto.LprTransactionItemInfo;
import org.kuali.student.r2.common.assembler.DTOAssembler;
import org.kuali.student.r2.common.dto.ContextInfo;

public class RegRequestAssembler implements DTOAssembler<RegRequestInfo, LqrTransactionInfo> {

    @Override
    public RegRequestInfo assemble(LqrTransactionInfo baseDTO, ContextInfo context) {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public LqrTransactionInfo disassemble(RegRequestInfo businessDTO, ContextInfo context) {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    public List<LqrTransactionInfo> disassembleList(List<RegRequestInfo> businessDTOs, ContextInfo context) {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }
    
    public List<RegRequestInfo> assembleList(List<LqrTransactionInfo> businessDTOs, ContextInfo context) {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    public LprTransactionItemInfo disassembleItem(RegRequestItemInfo regRequestItem, ContextInfo context) {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }
    
}
