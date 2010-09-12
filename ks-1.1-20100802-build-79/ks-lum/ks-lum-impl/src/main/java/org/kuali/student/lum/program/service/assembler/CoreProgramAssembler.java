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

import org.apache.log4j.Logger;
import org.kuali.student.core.assembly.BOAssembler;
import org.kuali.student.core.assembly.BaseDTOAssemblyNode;
import org.kuali.student.core.assembly.BaseDTOAssemblyNode.NodeOperation;
import org.kuali.student.core.assembly.data.AssemblyException;
import org.kuali.student.lum.course.service.assembler.CourseAssembler;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.program.dto.CoreProgramInfo;
import org.kuali.student.lum.program.service.ProgramService;

/**
 * @author KS
 *
 */
public class CoreProgramAssembler implements BOAssembler<CoreProgramInfo, CluInfo> {
    final static Logger LOG = Logger.getLogger(CourseAssembler.class);

    ProgramService programService;

    @Override
    public CoreProgramInfo assemble(CluInfo clu, CoreProgramInfo coreProgram, boolean shallowBuild) throws AssemblyException {

        CoreProgramInfo cpInfo = (null != coreProgram) ? coreProgram : new CoreProgramInfo();

        // Copy all the data from the clu to the coreprogram

        // TODO - when requirements methods have been implemented
        // ProgramRequirementInfo reqInfo = programService.getProgramRequirement(clu.getId());
        // List<String> reqStrs = new ArrayList<String>();
        // reqStrs.add(reqInfo.getId());
        // cpInfo.setProgramRequirements(reqStrs);
        cpInfo.setId(clu.getId());
        cpInfo.setAttributes(clu.getAttributes());
        cpInfo.setMetaInfo(clu.getMetaInfo());
        cpInfo.setType(clu.getType());
        cpInfo.setState(clu.getState());
        return cpInfo;
    }

    @Override
    public BaseDTOAssemblyNode<CoreProgramInfo, CluInfo> disassemble(CoreProgramInfo businessDTO, NodeOperation operation) throws AssemblyException {
        return null;
    }

    // Spring setter
    public void setProgramService(ProgramService programService) {
        this.programService = programService;
    }
}
