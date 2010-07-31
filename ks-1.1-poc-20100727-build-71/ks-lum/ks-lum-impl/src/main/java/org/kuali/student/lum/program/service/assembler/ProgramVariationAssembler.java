/*
 * Copyright 2007 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.lum.program.service.assembler;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.kuali.student.core.assembly.BOAssembler;
import org.kuali.student.core.assembly.BaseDTOAssemblyNode;
import org.kuali.student.core.assembly.BaseDTOAssemblyNode.NodeOperation;
import org.kuali.student.core.assembly.data.AssemblyException;
import org.kuali.student.lum.course.service.assembler.CourseAssembler;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.dto.CluResultInfo;
import org.kuali.student.lum.lu.dto.LuCodeInfo;
import org.kuali.student.lum.lu.dto.ResultOptionInfo;
import org.kuali.student.lum.lu.service.LuService;
import org.kuali.student.lum.program.dto.ProgramVariationInfo;

/**
 * @author KS
 *
 */
public class ProgramVariationAssembler implements BOAssembler<ProgramVariationInfo, CluInfo> {
    final static Logger LOG = Logger.getLogger(CourseAssembler.class);

    private LuService luService;

    @Override
    public ProgramVariationInfo assemble(CluInfo clu, ProgramVariationInfo majorDiscipline, boolean shallowBuild) throws AssemblyException {

        ProgramVariationInfo pvInfo = (null != majorDiscipline) ? majorDiscipline : new ProgramVariationInfo();

        // Copy all the data from the clu to the programvariation
        pvInfo.setIntensity((null != clu.getIntensity()) ? clu.getIntensity().getUnitType() : null);
        pvInfo.setReferenceURL((null != clu.getReferenceURL()) ? clu.getReferenceURL() : null);

        pvInfo.setCode(clu.getOfficialIdentifier().getCode());
        List<LuCodeInfo> luCodes = clu.getLuCodes();
        for (LuCodeInfo codeInfo : luCodes) {
            if (ProgramAssemblerConstants.CIP_2000.equals(codeInfo.getId())) {
                pvInfo.setCipCode(codeInfo.getValue());
            } else if (ProgramAssemblerConstants.HEGIS.equals(codeInfo.getId())) {
                pvInfo.setHegisCode(codeInfo.getValue());
            } else if (ProgramAssemblerConstants.UNIVERSITY_CLASSIFICATION.equals(codeInfo.getId())) {
                pvInfo.setUniversityClassification(codeInfo.getValue());
            } else if (ProgramAssemblerConstants.SELECTIVE_ENROLLMENT.equals(codeInfo.getId())) {
                pvInfo.setSelectiveEnrollmentCode(codeInfo.getValue());
            }
        }
        List<String> resultStrs = new ArrayList<String>();
        try {
            List<CluResultInfo> rInfos = luService.getCluResultByClu(clu.getId());
            for (CluResultInfo rInfo : rInfos) {
                for (ResultOptionInfo optionInfo : rInfo.getResultOptions()) {
                    resultStrs.add(optionInfo.getDesc().getPlain());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            pvInfo.setResultOptions(resultStrs);
        }
        

        return pvInfo;
    }

    @Override
    public BaseDTOAssemblyNode<ProgramVariationInfo, CluInfo> disassemble(ProgramVariationInfo businessDTO, NodeOperation operation) throws AssemblyException {
        return null;
    }

    // Spring setter
    public void setLuService(LuService luService) {
        this.luService = luService;
    }
}
