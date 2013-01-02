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
package org.kuali.student.r2.lum.program.service.assembler;

import java.util.List;

import org.apache.log4j.Logger;
import org.kuali.student.r2.lum.course.service.assembler.CourseAssembler;
import org.kuali.student.r2.lum.service.assembler.CluAssemblerUtils;
import org.kuali.student.r1.common.assembly.BOAssembler;
import org.kuali.student.r1.common.assembly.BaseDTOAssemblyNode;
import org.kuali.student.r1.common.assembly.BaseDTOAssemblyNode.NodeOperation;
import org.kuali.student.r2.lum.course.service.assembler.CourseAssembler;
import org.kuali.student.r2.lum.program.dto.assembly.ProgramAtpAssembly;
import org.kuali.student.r2.lum.program.dto.assembly.ProgramBasicOrgAssembly;
import org.kuali.student.r2.lum.program.dto.assembly.ProgramCodeAssembly;
import org.kuali.student.r2.lum.program.dto.assembly.ProgramIdentifierAssembly;
import org.kuali.student.r2.lum.program.dto.assembly.ProgramPublicationAssembly;
import org.kuali.student.r2.lum.program.dto.assembly.ProgramRequirementAssembly;
import org.kuali.student.r2.common.assembler.AssemblyException;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.lum.clu.dto.CluInfo;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.r2.lum.course.dto.LoDisplayInfo;
import org.kuali.student.r2.lum.program.dto.CoreProgramInfo;
import org.kuali.student.r2.lum.program.dto.assembly.ProgramCommonAssembly;

/**
 * @author KS
 *
 */
public class CoreProgramAssembler implements BOAssembler<CoreProgramInfo, CluInfo> {
    final static Logger LOG = Logger.getLogger(CourseAssembler.class);

    private CluService cluService;
    private ProgramAssemblerUtils programAssemblerUtils;
    private CluAssemblerUtils cluAssemblerUtils;


    @Override
    public CoreProgramInfo assemble(CluInfo baseDTO, CoreProgramInfo businessDTO, boolean shallowBuild, ContextInfo contextInfo) throws AssemblyException, PermissionDeniedException {

        CoreProgramInfo cpInfo = (null != businessDTO) ? businessDTO : new CoreProgramInfo();

        // Copy all the data from the clu to the coreprogram
        programAssemblerUtils.assembleBasics((baseDTO),  (ProgramCommonAssembly) cpInfo, contextInfo);
        programAssemblerUtils.assembleIdentifiers(baseDTO, (ProgramIdentifierAssembly) cpInfo);
        programAssemblerUtils.assembleBasicAdminOrgs(baseDTO, (ProgramBasicOrgAssembly) cpInfo);
        programAssemblerUtils.assembleAtps(baseDTO, (ProgramAtpAssembly) cpInfo);
        
        programAssemblerUtils.assembleLuCodes(baseDTO, (ProgramCodeAssembly) cpInfo);
        
        programAssemblerUtils.assemblePublications(baseDTO, (ProgramPublicationAssembly) cpInfo, contextInfo);

        cpInfo.setDescr(baseDTO.getDescr());
        cpInfo.setVersion(baseDTO.getVersionInfo());
        
        if (!shallowBuild) {
        	programAssemblerUtils.assembleRequirements(baseDTO, (ProgramRequirementAssembly) cpInfo, contextInfo);
        	cpInfo.setLearningObjectives(cluAssemblerUtils.assembleLos(baseDTO.getId(), shallowBuild,contextInfo));
        }
        
        return cpInfo;
    }


    @Override
    public BaseDTOAssemblyNode<CoreProgramInfo, CluInfo> disassemble(CoreProgramInfo businessDTO, NodeOperation operation, ContextInfo contextInfo) throws AssemblyException, InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException {
    	BaseDTOAssemblyNode<CoreProgramInfo, CluInfo> result = new BaseDTOAssemblyNode<CoreProgramInfo, CluInfo>(this);
    	
    	if (businessDTO == null) {
			// FIXME Unsure now if this is an exception or just return null or
			// empty assemblyNode
		    LOG.error("CoreProgram to disassemble is null!");
			throw new AssemblyException("CoreProgram can not be null");
		}

		CluInfo clu;
		try {
			clu = (NodeOperation.UPDATE == operation) ? cluService.getClu(businessDTO.getId(),contextInfo) : new CluInfo();
        } catch (Exception e) {
			throw new AssemblyException("Error getting existing learning unit during CoreProgram update", e);
        } 
        
        boolean stateChanged = NodeOperation.UPDATE == operation && businessDTO.getStateKey() != null && !businessDTO.getStateKey().equals(businessDTO.getStateKey());
        
        programAssemblerUtils.disassembleBasics(clu, (ProgramCommonAssembly) businessDTO);
        if (businessDTO.getId() == null)
            businessDTO.setId(clu.getId());
        programAssemblerUtils.disassembleIdentifiers(clu, (ProgramIdentifierAssembly) businessDTO, operation);
        programAssemblerUtils.disassembleAdminOrgs(clu, (ProgramBasicOrgAssembly) businessDTO, operation);
        programAssemblerUtils.disassembleAtps(clu, (ProgramAtpAssembly) businessDTO, operation);
        programAssemblerUtils.disassembleLuCodes(clu, (ProgramCodeAssembly) businessDTO, operation);
        programAssemblerUtils.disassemblePublications(clu, (ProgramPublicationAssembly) businessDTO, operation, result, contextInfo);
        
        if(businessDTO.getProgramRequirements() != null && !businessDTO.getProgramRequirements().isEmpty()) {
        	programAssemblerUtils.disassembleRequirements(clu, (ProgramRequirementAssembly) businessDTO, operation, result, stateChanged,contextInfo);
        }
        
        if (businessDTO.getLearningObjectives() != null) {
            disassembleLearningObjectives(businessDTO, operation, result);
        }
        
        clu.setDescr(businessDTO.getDescr());
        
		// Add the Clu to the result
		result.setNodeData(clu);
		result.setOperation(operation);
		result.setBusinessDTORef(businessDTO);
		return result;

    }

    private void disassembleLearningObjectives(CoreProgramInfo core, NodeOperation operation, BaseDTOAssemblyNode<CoreProgramInfo, CluInfo> result) throws AssemblyException, InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException {
    	try {
            List<BaseDTOAssemblyNode<?, ?>> loResults = cluAssemblerUtils.disassembleLos(core.getId(), core.getStateKey(),  (List<LoDisplayInfo>) core.getLearningObjectives() , operation,new ContextInfo());
            if (loResults != null) {
                result.getChildNodes().addAll(loResults);
            }
        } catch (DoesNotExistException e) {
        } catch (Exception e) {
            throw new AssemblyException("Error while disassembling los", e);
        }
    }
    
    // Spring setter
    public void setCluService(CluService cluService) {
        this.cluService = cluService;
    }

    public void setProgramAssemblerUtils(ProgramAssemblerUtils programAssemblerUtils) {
        this.programAssemblerUtils = programAssemblerUtils;
    }

    public void setCluAssemblerUtils(CluAssemblerUtils cluAssemblerUtils) {
        this.cluAssemblerUtils = cluAssemblerUtils;
    }

}
