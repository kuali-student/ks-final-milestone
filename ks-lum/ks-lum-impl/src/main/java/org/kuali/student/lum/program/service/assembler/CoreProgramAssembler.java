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

import java.util.List;

import org.apache.log4j.Logger;
import org.kuali.student.core.assembly.BOAssembler;
import org.kuali.student.core.assembly.BaseDTOAssemblyNode;
import org.kuali.student.core.assembly.BaseDTOAssemblyNode.NodeOperation;
import org.kuali.student.core.assembly.data.AssemblyException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.lum.course.service.assembler.CourseAssembler;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.service.LuService;
import org.kuali.student.lum.program.dto.CoreProgramInfo;
import org.kuali.student.lum.service.assembler.CluAssemblerUtils;

/**
 * @author KS
 *
 */
public class CoreProgramAssembler implements BOAssembler<CoreProgramInfo, CluInfo> {
    final static Logger LOG = Logger.getLogger(CourseAssembler.class);

    private LuService luService;
    private ProgramAssemblerUtils programAssemblerUtils;
    private CluAssemblerUtils cluAssemblerUtils;



    @Override
    public CoreProgramInfo assemble(CluInfo clu, CoreProgramInfo coreProgram, boolean shallowBuild) throws AssemblyException {

        CoreProgramInfo cpInfo = (null != coreProgram) ? coreProgram : new CoreProgramInfo();

        // Copy all the data from the clu to the coreprogram
        programAssemblerUtils.assembleBasics(clu, cpInfo);
        programAssemblerUtils.assembleIdentifiers(clu, cpInfo);
        programAssemblerUtils.assembleAdminOrgIds(clu, cpInfo);
        programAssemblerUtils.assembleAtps(clu, cpInfo);
        programAssemblerUtils.assembleLuCodes(clu, cpInfo);
        programAssemblerUtils.assembleRequirements(clu, cpInfo);
        programAssemblerUtils.assemblePublications(clu, cpInfo);

        cpInfo.setLearningObjectives(cluAssemblerUtils.assembleLos(clu.getId(), shallowBuild));

        cpInfo.setDescr(clu.getDescr());
        cpInfo.setVersionInfo(clu.getVersionInfo());
        
        return cpInfo;
    }

    @Override
    public BaseDTOAssemblyNode<CoreProgramInfo, CluInfo> disassemble(CoreProgramInfo core, NodeOperation operation) throws AssemblyException {
    	BaseDTOAssemblyNode<CoreProgramInfo, CluInfo> result = new BaseDTOAssemblyNode<CoreProgramInfo, CluInfo>(this);
    	
    	if (core == null) {
			// FIXME Unsure now if this is an exception or just return null or
			// empty assemblyNode
		    LOG.error("CoreProgram to disassemble is null!");
			throw new AssemblyException("CoreProgram can not be null");
		}

		CluInfo clu;
		try {
			clu = (NodeOperation.UPDATE == operation) ? luService.getClu(core.getId()) : new CluInfo();
        } catch (Exception e) {
			throw new AssemblyException("Error getting existing learning unit during CoreProgram update", e);
        } 
        
        programAssemblerUtils.disassembleBasics(clu, core, operation);
        if (core.getId() == null)
        	core.setId(clu.getId());
        programAssemblerUtils.disassembleIdentifiers(clu, core, operation);
        programAssemblerUtils.disassembleAdminOrgs(clu, core, operation);
        programAssemblerUtils.disassembleAtps(clu, core, operation);    
        programAssemblerUtils.disassembleLuCodes(clu, core, operation);
        programAssemblerUtils.disassemblePublications(clu, core, operation, result);
        
        if(core.getProgramRequirements() != null && !core.getProgramRequirements().isEmpty()) {
        	programAssemblerUtils.disassembleRequirements(clu, core, operation, result);
        }
        
        if (core.getLearningObjectives() != null) {
            disassembleLearningObjectives(core, operation, result);
        }
        
        clu.setDescr(core.getDescr());
        
		// Add the Clu to the result
		result.setNodeData(clu);
		result.setOperation(operation);
		result.setBusinessDTORef(core);
		return result;

    }

    private void disassembleLearningObjectives(CoreProgramInfo core, NodeOperation operation, BaseDTOAssemblyNode<CoreProgramInfo, CluInfo> result) throws AssemblyException {
        try {
            List<BaseDTOAssemblyNode<?, ?>> loResults = cluAssemblerUtils.disassembleLos(core.getId(), core.getState(),  core.getLearningObjectives(), operation);
            if (loResults != null) {
                result.getChildNodes().addAll(loResults);
            }
        } catch (DoesNotExistException e) {
        } catch (Exception e) {
            throw new AssemblyException("Error while disassembling los", e);
        }
    }
    
    // Spring setter
    public void setLuService(LuService luService) {
        this.luService = luService;
    }

    public void setProgramAssemblerUtils(ProgramAssemblerUtils programAssemblerUtils) {
        this.programAssemblerUtils = programAssemblerUtils;
    }

    public void setCluAssemblerUtils(CluAssemblerUtils cluAssemblerUtils) {
        this.cluAssemblerUtils = cluAssemblerUtils;
    }
}
