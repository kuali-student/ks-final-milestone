package org.kuali.student.enrollment.class2.acal.service.assembler;


import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.infc.AtpAssembler;
import org.kuali.student.r2.core.atp.dto.AtpInfo;

public class TermAssembler implements AtpAssembler<TermInfo, AtpInfo>{
     
    @Override
    public TermInfo assemble(AtpInfo atp, ContextInfo context) {
        if(atp != null){
            TermInfo term = new TermInfo();
            term.setKey(atp.getKey());
            term.setName(atp.getName());
            term.setDescr(atp.getDescr());
            term.setStartDate(atp.getStartDate());
            term.setEndDate(atp.getEndDate());
            term.setTypeKey(atp.getTypeKey());
            term.setStateKey(atp.getStateKey());
            term.setMeta(atp.getMeta());
            term.setAttributes(atp.getAttributes());
            
            return term;
        }
        else
            return null;
    }

    @Override
    public AtpInfo disassemble(TermInfo term, ContextInfo context) {
        AtpInfo atp = new AtpInfo();
        atp.setKey(term.getKey());
        atp.setKey(term.getKey());
        atp.setName(term.getName());
        atp.setDescr(term.getDescr());
        atp.setStartDate(term.getStartDate());
        atp.setEndDate(term.getEndDate());
        atp.setTypeKey(term.getTypeKey());
        atp.setStateKey(term.getStateKey());
        atp.setMeta(term.getMeta());
        atp.setAttributes(term.getAttributes());

        //TODO: acal to term relation (AtpAtpRelation)
        return atp;
    }

}
