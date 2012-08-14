package org.kuali.student.r2.lum.program.service.assembler;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.kuali.student.r2.lum.service.assembler.CluAssemblerUtils;
import org.kuali.student.r1.common.assembly.BOAssembler;
import org.kuali.student.r1.common.assembly.BaseDTOAssemblyNode;
import org.kuali.student.r1.common.assembly.BaseDTOAssemblyNode.NodeOperation;
import org.kuali.student.r1.lum.lu.dto.CluCluRelationInfo;
import org.kuali.student.r2.lum.program.dto.assembly.ProgramAtpAssembly;
import org.kuali.student.r2.lum.program.dto.assembly.ProgramBasicOrgAssembly;
import org.kuali.student.r2.lum.program.dto.assembly.ProgramCodeAssembly;
import org.kuali.student.r2.lum.program.dto.assembly.ProgramIdentifierAssembly;
import org.kuali.student.r2.lum.program.dto.assembly.ProgramRequirementAssembly;
import org.kuali.student.r2.common.assembler.AssemblyException;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.lum.clu.dto.AdminOrgInfo;
import org.kuali.student.r2.lum.clu.dto.CluInfo;
import org.kuali.student.r2.lum.clu.dto.LuCodeInfo;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.r2.lum.course.dto.LoDisplayInfo;
import org.kuali.student.r2.lum.program.dto.CredentialProgramInfo;
import org.kuali.student.r2.lum.program.dto.assembly.ProgramCommonAssembly;

public class CredentialProgramAssembler implements BOAssembler<CredentialProgramInfo, CluInfo> {
    final static Logger LOG = Logger.getLogger(CredentialProgramAssembler.class);

    private ProgramAssemblerUtils programAssemblerUtils;
    private CluAssemblerUtils cluAssemblerUtils;
    private CluService cluService;


    @Override
    public CredentialProgramInfo assemble(CluInfo baseDTO, CredentialProgramInfo businessDTO, boolean shallowBuild, ContextInfo contextInfo) throws AssemblyException {

        CredentialProgramInfo cpInfo = (null != businessDTO) ? businessDTO : new CredentialProgramInfo();

        // Copy all the data from the clu to the credential program
        if (!ProgramAssemblerConstants.CREDENTIAL_PROGRAM_TYPES.contains(baseDTO.getTypeKey())) {
            throw new AssemblyException("CredentialProgramAssembler.assemble() called for Clu of incorrect type: " + baseDTO.getTypeKey());
        }
        cpInfo.setTypeKey(baseDTO.getTypeKey());
        cpInfo.setDescr(baseDTO.getDescr());
        cpInfo.setVersion(baseDTO.getVersionInfo());

        programAssemblerUtils.assembleBasics(baseDTO, (ProgramCommonAssembly) cpInfo, contextInfo);
        programAssemblerUtils.assembleIdentifiers(baseDTO, (ProgramIdentifierAssembly) cpInfo);
        if (null != baseDTO.getOfficialIdentifier().getLevel()) {
            cpInfo.setProgramLevel(baseDTO.getOfficialIdentifier().getLevel());
        }
        programAssemblerUtils.assembleBasicAdminOrgs(baseDTO, (ProgramBasicOrgAssembly) cpInfo);
        for (AdminOrgInfo org : baseDTO.getAdminOrgs()) {
            if (ProgramAssemblerConstants.INSTITUTION.equals(org.getTypeKey())) {
                cpInfo.setInstitution(org);
            }
        }
        programAssemblerUtils.assembleAtps(baseDTO, (ProgramAtpAssembly) cpInfo);
        
        if (baseDTO.getLuCodes() != null) {
            for (LuCodeInfo codeInfo : baseDTO.getLuCodes()) {
                if (ProgramAssemblerConstants.UNIVERSITY_CLASSIFICATION.equals(codeInfo.getType())) {
                    cpInfo.setUniversityClassification(codeInfo.getValue());
                } 
            }
        }
        
        if (!shallowBuild) {
	        programAssemblerUtils.assembleRequirements(baseDTO, (ProgramRequirementAssembly) cpInfo, contextInfo);
	        cpInfo.setLearningObjectives(cluAssemblerUtils.assembleLos(baseDTO.getId(), shallowBuild,contextInfo)); 
	        cpInfo.setResultOptions(programAssemblerUtils.assembleResultOptions(baseDTO.getId(),contextInfo));
        }
        
        try {

        	 cpInfo.setCoreProgramIds(cluService.getRelatedCluIdsByCluAndRelationType(baseDTO.getId(), ProgramAssemblerConstants.HAS_CORE_PROGRAM, contextInfo));
        } catch (Exception e) {
            throw new AssemblyException(e);
        }

        return cpInfo;
    }


    @Override
    public BaseDTOAssemblyNode<CredentialProgramInfo, CluInfo> disassemble(CredentialProgramInfo businessDTO, NodeOperation operation, ContextInfo contextInfo) throws AssemblyException {

        BaseDTOAssemblyNode<CredentialProgramInfo, CluInfo> result = new BaseDTOAssemblyNode<CredentialProgramInfo, CluInfo>(this);

        if (businessDTO == null) {
            // FIXME Unsure now if this is an exception or just return null or
            // empty assemblyNode
            LOG.error("credentialProgram to disassemble is null!");
            throw new AssemblyException("credentialProgram can not be null");
        }

        CluInfo clu;
        try {
            clu = (NodeOperation.UPDATE == operation) ? cluService.getClu(businessDTO.getId(), contextInfo) : new CluInfo();
        } catch (Exception e) {
			throw new AssemblyException("Error getting existing learning unit during CoreProgram update", e);
        } 
        
        boolean stateChanged = NodeOperation.UPDATE == operation && businessDTO.getStateKey() != null && !businessDTO.getStateKey().equals(clu.getStateKey());
        
        programAssemblerUtils.disassembleBasics(clu, (ProgramCommonAssembly) businessDTO);
        if (businessDTO.getId() == null)
            businessDTO.setId(clu.getId());
        programAssemblerUtils.disassembleIdentifiers(clu, (ProgramIdentifierAssembly) businessDTO, operation);
        programAssemblerUtils.disassembleAdminOrgs(clu, (ProgramBasicOrgAssembly) businessDTO, operation);
        programAssemblerUtils.disassembleAtps(clu, (ProgramAtpAssembly) businessDTO, operation);
        programAssemblerUtils.disassembleLuCodes(clu, (ProgramCodeAssembly) businessDTO, operation);

        if (businessDTO.getProgramRequirements() != null && !businessDTO.getProgramRequirements().isEmpty()) {
            programAssemblerUtils.disassembleRequirements(clu, (ProgramRequirementAssembly) businessDTO, operation, result, stateChanged, contextInfo);
        }

        if (businessDTO.getResultOptions() != null) {
            disassembleResultOptions(businessDTO, operation, result, contextInfo);
        }

        if (businessDTO.getLearningObjectives() != null) {
            disassembleLearningObjectives(businessDTO, operation, result,contextInfo);
        }

        clu.setDescr(businessDTO.getDescr());
        clu.setTypeKey(businessDTO.getCredentialProgramType());



        if (businessDTO.getCoreProgramIds() != null && businessDTO.getCoreProgramIds().size() > 0) {
            disassembleCorePrograms(businessDTO, operation, result, contextInfo);
        }

        // Add the Clu to the result
        result.setNodeData(clu);
        result.setOperation(operation);
        result.setBusinessDTORef(businessDTO);
        return result;
    }

    private void disassembleResultOptions(CredentialProgramInfo credential, NodeOperation operation, BaseDTOAssemblyNode<CredentialProgramInfo, CluInfo> result, ContextInfo contextInfo) throws AssemblyException {
        BaseDTOAssemblyNode<?, ?> resultOptions = cluAssemblerUtils.disassembleCluResults(credential.getId(), credential.getStateKey(), credential.getResultOptions(), operation, ProgramAssemblerConstants.DEGREE_RESULTS, "Result options", "Result option", contextInfo);
        if (resultOptions != null) {
            result.getChildNodes().add(resultOptions);
        }
    }

    private void disassembleLearningObjectives(CredentialProgramInfo credential, NodeOperation operation, BaseDTOAssemblyNode<CredentialProgramInfo, CluInfo> result,ContextInfo contextInfo) throws AssemblyException {
    	try {
            List<BaseDTOAssemblyNode<?, ?>> loResults = cluAssemblerUtils.disassembleLos(credential.getId(), credential.getStateKey(), (List<LoDisplayInfo>) credential.getLearningObjectives(), operation,contextInfo);
            if (loResults != null) {
                result.getChildNodes().addAll(loResults);
            }
        } catch (DoesNotExistException e) {
        } catch (Exception e) {
            throw new AssemblyException("Error while disassembling los", e);
        } 
    }

    private void disassembleCorePrograms(CredentialProgramInfo credential, NodeOperation operation, BaseDTOAssemblyNode<CredentialProgramInfo, CluInfo> result, ContextInfo contextInfo) throws AssemblyException {
        List<BaseDTOAssemblyNode<?, ?>> coreResults;

        try {
            Map<String, String> currentRelations = null;

            if (!NodeOperation.CREATE.equals(operation)) {
                currentRelations = programAssemblerUtils.getCluCluRelations(credential.getId(), ProgramAssemblerConstants.HAS_CORE_PROGRAM, contextInfo );
            }

            for (String coreProgramId : credential.getCoreProgramIds()) {
                coreResults = programAssemblerUtils.addAllRelationNodes(credential.getId(), coreProgramId, ProgramAssemblerConstants.HAS_CORE_PROGRAM, operation, currentRelations);
                if (coreResults != null && coreResults.size() > 0) {
                    result.getChildNodes().addAll(coreResults);
                }
            }

            if (currentRelations != null && currentRelations.size() > 0) {
                for (Map.Entry<String, String> entry : currentRelations.entrySet()) {
                    // Create a new relation with the id of the relation we want to
                    // delete
                    CluCluRelationInfo relationToDelete = new CluCluRelationInfo();
                    relationToDelete.setId(entry.getValue());
                    BaseDTOAssemblyNode<Object, CluCluRelationInfo> relationToDeleteNode = new BaseDTOAssemblyNode<Object, CluCluRelationInfo>(
                            null);
                    relationToDeleteNode.setNodeData(relationToDelete);
                    relationToDeleteNode.setOperation(NodeOperation.DELETE);
                    result.getChildNodes().add(relationToDeleteNode);
                }
            }
        } catch (Exception e) {
            throw new AssemblyException("Error while disassembling Core programs", e);
        }
    }

    public void setProgramAssemblerUtils(ProgramAssemblerUtils programAssemblerUtils) {
        this.programAssemblerUtils = programAssemblerUtils;
    }

    public void setCluService(CluService cluService) {
        this.cluService = cluService;
    }

    public void setCluAssemblerUtils(CluAssemblerUtils cluAssemblerUtils) {
        this.cluAssemblerUtils = cluAssemblerUtils;
    }


}
