package org.kuali.student.enrollment.class2.grading.assembler;

import org.kuali.student.enrollment.grading.dto.GradeValuesGroupInfo;
import org.kuali.student.r2.common.assembler.AssemblyException;
import org.kuali.student.r2.common.assembler.DTOAssembler;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValueInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;

import java.util.List;

public class GradeValuesGroupAssembler implements DTOAssembler<GradeValuesGroupInfo, ResultValuesGroupInfo> {

	@Override
    public GradeValuesGroupInfo assemble(ResultValuesGroupInfo resultValuesGroupInfo, ContextInfo context) throws AssemblyException {
        if(resultValuesGroupInfo != null){
            GradeValuesGroupInfo gradeValuesGroupInfo = new GradeValuesGroupInfo();
            resultValuesGroupInfo.setKey(resultValuesGroupInfo.getKey());
            gradeValuesGroupInfo.setName(resultValuesGroupInfo.getName());
            gradeValuesGroupInfo.setDescr(resultValuesGroupInfo.getDescr());
            gradeValuesGroupInfo.setTypeKey(resultValuesGroupInfo.getTypeKey());
            gradeValuesGroupInfo.setStateKey(resultValuesGroupInfo.getStateKey());
            gradeValuesGroupInfo.setMeta(resultValuesGroupInfo.getMeta());
            gradeValuesGroupInfo.setAttributes(resultValuesGroupInfo.getAttributes());
            gradeValuesGroupInfo.setResultValueRange(resultValuesGroupInfo.getResultValueRange());

            return gradeValuesGroupInfo;
        } else {
            return null;
        }
    }

    public GradeValuesGroupInfo assemble(ResultValuesGroupInfo resultValuesGroupInfo, List<ResultValueInfo> resultValueInfos, ContextInfo context) throws AssemblyException {
         GradeValuesGroupInfo gradeValuesGroupInfo = assemble(resultValuesGroupInfo,context);
         gradeValuesGroupInfo.setResultValueInfos(resultValueInfos);
         return gradeValuesGroupInfo;
    }

    @Override
    public ResultValuesGroupInfo disassemble(GradeValuesGroupInfo businessDTO, ContextInfo context) throws AssemblyException {
        throw new UnsupportedOperationException("Method not implemented."); // TODO implement method
    }

 }
