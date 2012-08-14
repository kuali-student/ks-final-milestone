package org.kuali.student.enrollment.class2.acal.service.assembler;


import org.apache.commons.httpclient.util.DateUtil;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.r2.common.assembler.AssemblyException;
import org.kuali.student.r2.common.assembler.DTOAssembler;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.kuali.student.r2.core.atp.dto.AtpInfo;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TermAssembler implements DTOAssembler<TermInfo, AtpInfo>{

    private static final String YEAR_ONLY_FORMAT_STRING = "yyyy";

    private static Map<String, Integer> termTypeCodeMap;

    static {
        Map<String, Integer> map = new HashMap<String, Integer>(4);

        map.put(AtpServiceConstants.ATP_WINTER_TYPE_KEY, 1);
        map.put(AtpServiceConstants.ATP_SPRING_TYPE_KEY, 2);
        map.put(AtpServiceConstants.ATP_SUMMER_TYPE_KEY, 3);
        map.put(AtpServiceConstants.ATP_FALL_TYPE_KEY, 4);

        termTypeCodeMap = Collections.unmodifiableMap(map);
    }

    @Override
    public TermInfo assemble(AtpInfo atp, ContextInfo context) throws AssemblyException {
        if(atp != null){
            TermInfo term = new TermInfo();
            term.setId(atp.getId());
            term.setName(atp.getName());
            term.setDescr(atp.getDescr());
            term.setCode(atp.getCode());
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
    public AtpInfo disassemble(TermInfo term, ContextInfo context) throws AssemblyException{
        AtpInfo atp = new AtpInfo();
        atp.setId(term.getId());
        atp.setName(term.getName());
        atp.setDescr(term.getDescr());
        atp.setCode(buildAtpCodeForTerm(term));
        atp.setStartDate(term.getStartDate());
        atp.setEndDate(term.getEndDate());
        atp.setTypeKey(term.getTypeKey());
        atp.setStateKey(term.getStateKey());
        atp.setMeta(term.getMeta());
        atp.setAttributes(term.getAttributes());

        return atp;
    }

    /**
     * Generate the value for atp code based on formula agreed on here: https://jira.kuali.org/browse/KSENROLL-1146
     *
     * @param term The term to use for data in code generation
     *
     * @return the Atp code for the term
     */
    public static String buildAtpCodeForTerm(TermInfo term) {

        // if the term is not of a type that is handled by the defined formula, return null, since the value for the atp code is undefined at that point
        if(!termTypeCodeMap.containsKey(term.getTypeKey())) {
            return null;
        }

        StringBuilder result = new StringBuilder(DateUtil.formatDate(term.getStartDate(), YEAR_ONLY_FORMAT_STRING));

        result.append(termTypeCodeMap.get(term.getTypeKey()).toString());

        return result.toString();
    }

}
