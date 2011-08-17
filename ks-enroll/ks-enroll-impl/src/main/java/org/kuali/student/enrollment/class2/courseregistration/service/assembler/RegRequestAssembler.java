package org.kuali.student.enrollment.class2.courseregistration.service.assembler;

import java.util.List;

import org.kuali.student.enrollment.courseregistration.dto.RegRequestInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegRequestItemInfo;
import org.kuali.student.enrollment.lpr.dto.LPRTransactionInfo;
import org.kuali.student.enrollment.lpr.dto.LPRTransactionItemInfo;
import org.kuali.student.r2.common.assembler.DTOAssembler;
import org.kuali.student.r2.common.dto.ContextInfo;

public class RegRequestAssembler implements DTOAssembler<RegRequestInfo, LPRTransactionInfo> {

    @Override
    public RegRequestInfo assemble(LPRTransactionInfo baseDTO, ContextInfo context) {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public LPRTransactionInfo disassemble(RegRequestInfo businessDTO, ContextInfo context) {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    public List<LPRTransactionInfo> disassembleList(List<RegRequestInfo> businessDTOs, ContextInfo context) {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }
    
    public List<RegRequestInfo> assembleList(List<LPRTransactionInfo> businessDTOs, ContextInfo context) {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    public LPRTransactionItemInfo disassembleItem(RegRequestItemInfo regRequestItem, ContextInfo context) {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }
    
}
