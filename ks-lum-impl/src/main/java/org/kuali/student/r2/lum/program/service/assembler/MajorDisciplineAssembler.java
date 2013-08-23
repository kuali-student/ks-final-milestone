/*
 * Copyright 2007 The Kuali Foundation Licensed under the Educational Community License, Version 1.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a
 * copy of the License at http://www.opensource.org/licenses/ecl1.php Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
package org.kuali.student.r2.lum.program.service.assembler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.kuali.student.r2.lum.course.service.assembler.CourseAssembler;
import org.kuali.student.r2.lum.service.assembler.CluAssemblerUtils;
import org.kuali.student.r1.common.assembly.BOAssembler;
import org.kuali.student.r1.common.assembly.BaseDTOAssemblyNode;
import org.kuali.student.r1.common.assembly.BaseDTOAssemblyNode.NodeOperation;
import org.kuali.student.r2.common.dto.AmountInfo;
import org.kuali.student.r2.common.dto.DtoConstants;
import org.kuali.student.r2.lum.clu.dto.CluCluRelationInfo;
import org.kuali.student.r2.lum.course.service.assembler.CourseAssembler;
import org.kuali.student.r2.lum.program.dto.assembly.ProgramAtpAssembly;
import org.kuali.student.r2.lum.program.dto.assembly.ProgramBasicOrgAssembly;
import org.kuali.student.r2.lum.program.dto.assembly.ProgramCodeAssembly;
import org.kuali.student.r2.lum.program.dto.assembly.ProgramCredentialAssembly;
import org.kuali.student.r2.lum.program.dto.assembly.ProgramFullOrgAssembly;
import org.kuali.student.r2.lum.program.dto.assembly.ProgramIdentifierAssembly;
import org.kuali.student.r2.lum.program.dto.assembly.ProgramPublicationAssembly;
import org.kuali.student.r2.lum.program.dto.assembly.ProgramRequirementAssembly;
import org.kuali.student.r2.common.assembler.AssemblyException;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.lum.clu.dto.CluInfo;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.r2.lum.program.dto.CoreProgramInfo;
import org.kuali.student.r2.lum.program.dto.MajorDisciplineInfo;
import org.kuali.student.r2.lum.program.dto.ProgramVariationInfo;
import org.kuali.student.r2.lum.program.dto.assembly.ProgramCommonAssembly;

/**
 * @author KS TODO - Much of this should be shared with ProgramVariationAssembler (and probably other Program Assemblers to come). AssemblerUtils?
 */
public class MajorDisciplineAssembler implements BOAssembler<MajorDisciplineInfo, CluInfo> {
    final static Logger LOG = Logger.getLogger(CourseAssembler.class);

    private CluService cluService;

    private ProgramVariationAssembler programVariationAssembler;
    private CoreProgramAssembler coreProgramAssembler;
    private CluAssemblerUtils cluAssemblerUtils;
    private ProgramAssemblerUtils programAssemblerUtils;

    @Override
    public MajorDisciplineInfo assemble(CluInfo baseDTO, MajorDisciplineInfo businessDTO, boolean shallowBuild, ContextInfo contextInfo) throws AssemblyException, PermissionDeniedException {

        MajorDisciplineInfo mdInfo = (null != businessDTO) ? businessDTO : new MajorDisciplineInfo();

        // Copy all the data from the clu to the majordiscipline
        programAssemblerUtils.assembleBasics(baseDTO, (ProgramCommonAssembly) mdInfo, contextInfo);
        programAssemblerUtils.assembleIdentifiers(baseDTO, (ProgramIdentifierAssembly) mdInfo);
        programAssemblerUtils.assembleBasicAdminOrgs(baseDTO, (ProgramBasicOrgAssembly) mdInfo);
        programAssemblerUtils.assembleFullOrgs(baseDTO, (ProgramFullOrgAssembly) mdInfo);
        programAssemblerUtils.assembleAtps(baseDTO, (ProgramAtpAssembly) mdInfo);
        programAssemblerUtils.assembleLuCodes(baseDTO, (ProgramCodeAssembly) mdInfo);

        mdInfo.setIntensity((null != baseDTO.getIntensity()) ? baseDTO.getIntensity().getUnitTypeKey() : null);
        mdInfo.setStdDuration(baseDTO.getStdDuration());
        mdInfo.setPublishedInstructors(baseDTO.getInstructors());
        mdInfo.setCampusLocations(baseDTO.getCampusLocations());
        mdInfo.setAccreditingAgencies(baseDTO.getAccreditations());
        mdInfo.setEffectiveDate(baseDTO.getEffectiveDate());
        mdInfo.setDescr(baseDTO.getDescr());
        mdInfo.setVersion(baseDTO.getVersion());
        mdInfo.setNextReviewPeriod(baseDTO.getNextReviewPeriod());

        if (!shallowBuild) {
            programAssemblerUtils.assembleRequirements(baseDTO, (ProgramRequirementAssembly) mdInfo, contextInfo);
            mdInfo.setCredentialProgramId(programAssemblerUtils.getCredentialProgramID(baseDTO.getId(), contextInfo));
            mdInfo.setResultOptions(programAssemblerUtils.assembleResultOptions(baseDTO.getId(), contextInfo));
            mdInfo.setLearningObjectives(cluAssemblerUtils.assembleLos(baseDTO.getId(), shallowBuild, contextInfo));
            mdInfo.setVariations(assembleVariations(baseDTO.getId(), shallowBuild, contextInfo));
            mdInfo.setOrgCoreProgram(assembleCoreProgram(baseDTO.getId(), shallowBuild, contextInfo));
            programAssemblerUtils.assemblePublications(baseDTO, (ProgramPublicationAssembly) mdInfo, contextInfo);
        }

        return mdInfo;
    }

    private CoreProgramInfo assembleCoreProgram(String cluId, boolean shallowBuild, ContextInfo contextInfo) throws AssemblyException {
        CoreProgramInfo coreProgramInfo = null;
        try {
            List<CluInfo> corePrograms = cluService.getRelatedClusByCluAndRelationType(cluId, ProgramAssemblerConstants.HAS_CORE_PROGRAM, contextInfo);
            // TODO - is it an error if there's more than one core program? on
            if (corePrograms.size() == 1) {
                coreProgramInfo = coreProgramAssembler.assemble(corePrograms.get(0), null, shallowBuild, contextInfo);
            } else if (corePrograms.size() > 1) {
                throw new AssemblyException(new DataValidationErrorException("MajorDiscipline has more than one associated Core Program"));
            }
        } catch (Exception e) {
            throw new AssemblyException(e);
        }
        return coreProgramInfo;
    }

    private List<ProgramVariationInfo> assembleVariations(String cluId, boolean shallowBuild, ContextInfo contextInfo) throws AssemblyException {
        List<ProgramVariationInfo> variations = new ArrayList<ProgramVariationInfo>();

        try {
            Map<String, CluCluRelationInfo> currentRelations = programAssemblerUtils.getCluCluActiveRelations(cluId, ProgramAssemblerConstants.HAS_PROGRAM_VARIATION, contextInfo);
            if (currentRelations != null && !currentRelations.isEmpty()) {
                for (String variationId : currentRelations.keySet()) {
                    CluInfo variationClu = cluService.getClu(variationId, contextInfo);
                    variations.add(programVariationAssembler.assemble(variationClu, null, shallowBuild, contextInfo));
                }
            }
        } catch (Exception e) {
            throw new AssemblyException(e);
        }
        return variations;
    }

    @Override
    public BaseDTOAssemblyNode<MajorDisciplineInfo, CluInfo> disassemble(MajorDisciplineInfo businessDTO, NodeOperation operation, ContextInfo contextInfo) throws AssemblyException {
        if (businessDTO == null) {
            LOG.error("Major for  disassemble is null!");
            throw new AssemblyException("Major cannot be null");
        }

        // TODO IDs for objects w/o ids

        BaseDTOAssemblyNode<MajorDisciplineInfo, CluInfo> result = new BaseDTOAssemblyNode<MajorDisciplineInfo, CluInfo>(this);

        CluInfo clu;
        try {
            clu = (NodeOperation.UPDATE == operation) ? cluService.getClu(businessDTO.getId(), contextInfo) : new CluInfo();
        } catch (Exception e) {
            throw new AssemblyException("Error getting existing learning unit during major update", e);
        }

        boolean stateChanged = NodeOperation.UPDATE == operation && businessDTO.getStateKey() != null && !businessDTO.getStateKey().equals(clu.getStateKey());

        programAssemblerUtils.disassembleBasics(clu, (ProgramCommonAssembly) businessDTO);
        if (businessDTO.getId() == null)
            businessDTO.setId(clu.getId());
        programAssemblerUtils.disassembleLuCodes(clu, (ProgramCodeAssembly) businessDTO, operation);
        programAssemblerUtils.disassembleAdminOrgs(clu, (ProgramBasicOrgAssembly) businessDTO, operation);
        programAssemblerUtils.disassembleAtps(clu, (ProgramAtpAssembly) businessDTO, operation);
        programAssemblerUtils.disassembleIdentifiers(clu, (ProgramIdentifierAssembly) businessDTO, operation);
        programAssemblerUtils.disassemblePublications(clu, (ProgramPublicationAssembly) businessDTO, operation, result, contextInfo);

        if (businessDTO.getProgramRequirements() != null && !businessDTO.getProgramRequirements().isEmpty()) {
            programAssemblerUtils.disassembleRequirements(clu, (ProgramRequirementAssembly) businessDTO, operation, result, stateChanged, contextInfo);
        }

        if (businessDTO.getVariations() != null && !businessDTO.getVariations().isEmpty()) {
            try {
                disassembleVariations(businessDTO, operation, result, contextInfo);
            } catch (Exception e) {
                throw new AssemblyException("Error diassembling Variations during major update", e);
            }
        }
        if (businessDTO.getOrgCoreProgram() != null) {
            disassembleCoreProgram(businessDTO, operation, result, contextInfo);
        }
        if (businessDTO.getCredentialProgramId() != null) {
            disassembleCredentialProgram(businessDTO, operation, result, contextInfo);
        }
        if (businessDTO.getResultOptions() != null) {
            disassembleResultOptions(businessDTO, operation, result, contextInfo);
        }
        if (businessDTO.getLearningObjectives() != null) {
            disassembleLearningObjectives(businessDTO, operation, result, contextInfo);
        }

        AmountInfo intensity = new AmountInfo();
        intensity.setUnitTypeKey(businessDTO.getIntensity());
        clu.setIntensity(intensity);
        clu.setStdDuration(businessDTO.getStdDuration());
        clu.setInstructors(businessDTO.getPublishedInstructors());

        clu.setNextReviewPeriod(businessDTO.getNextReviewPeriod());
        clu.setEffectiveDate(businessDTO.getEffectiveDate());

        clu.setCampusLocations(businessDTO.getCampusLocations());
        clu.setDescr(businessDTO.getDescr());

        clu.setAccreditations(businessDTO.getAccreditingAgencies());
        clu.setNextReviewPeriod(businessDTO.getNextReviewPeriod());
        clu.setStateKey(businessDTO.getStateKey());
        
        // Add the Clu to the result
        result.setNodeData(clu);
        result.setOperation(operation);
        result.setBusinessDTORef(businessDTO);

        return result;
    }

    private void disassembleLearningObjectives(MajorDisciplineInfo major, NodeOperation operation, BaseDTOAssemblyNode<MajorDisciplineInfo, CluInfo> result, ContextInfo contextInfo) throws AssemblyException {
        try {
            List<BaseDTOAssemblyNode<?, ?>> loResults = cluAssemblerUtils.disassembleLos(major.getId(), major.getStateKey(), major.getLearningObjectives(), operation, contextInfo);
            if (loResults != null) {
                result.getChildNodes().addAll(loResults);
            }
        } catch (DoesNotExistException e) {} catch (Exception e) {
            throw new AssemblyException("Error while disassembling los", e);
        }
    }

    private void disassembleResultOptions(MajorDisciplineInfo major, NodeOperation operation, BaseDTOAssemblyNode<MajorDisciplineInfo, CluInfo> result, ContextInfo contextInfo) throws AssemblyException {
        // TODO Check for ProgramAssemblerConstants.CERTIFICATE_RESULTS too

        BaseDTOAssemblyNode<?, ?> degreeResults = cluAssemblerUtils.disassembleCluResults(major.getId(), major.getStateKey(), major.getResultOptions(), operation, ProgramAssemblerConstants.DEGREE_RESULTS, "Result options", "Result option", contextInfo);
        if (degreeResults != null) {
            result.getChildNodes().add(degreeResults);
        }
    }

    private void disassembleCredentialProgram(MajorDisciplineInfo major, NodeOperation operation, BaseDTOAssemblyNode<MajorDisciplineInfo, CluInfo> result, ContextInfo contextInfo) throws AssemblyException {

        List<BaseDTOAssemblyNode<?, ?>> credentialResults;
        try {
            credentialResults = programAssemblerUtils.disassembleCredentialProgram((ProgramCredentialAssembly) major, operation, ProgramAssemblerConstants.HAS_MAJOR_PROGRAM, contextInfo);
            if (credentialResults != null) {
                result.getChildNodes().addAll(credentialResults);
            }
        } catch (Exception e) {
            throw new AssemblyException("Error while disassembling Credential program", e);
        }
    }

    private void disassembleVariations(MajorDisciplineInfo major, NodeOperation operation, BaseDTOAssemblyNode<MajorDisciplineInfo, CluInfo> result, ContextInfo contextInfo) throws AssemblyException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        Map<String, CluCluRelationInfo> currentRelations = null;
        List<BaseDTOAssemblyNode<?, ?>> nodes = new ArrayList<BaseDTOAssemblyNode<?, ?>>();

        if (!NodeOperation.CREATE.equals(operation)) {
            currentRelations = programAssemblerUtils.getCluCluActiveRelations(major.getId(), ProgramAssemblerConstants.HAS_PROGRAM_VARIATION, contextInfo);
        }

        // Loop through all the variations in this MD
        for (ProgramVariationInfo variation : major.getVariations()) {
            BaseDTOAssemblyNode<?, ?> variationNode;
            variation.setStateKey(major.getStateKey());
            try {
                if (NodeOperation.UPDATE.equals(operation) && variation.getId() != null && (currentRelations != null && currentRelations.containsKey(variation.getId()))) {
                    // If the relationship already exists update it
                    // remove this entry from the map so we can tell what needs to be deleted at the end
                    variationNode = programVariationAssembler.disassemble(variation, operation, contextInfo);
                    if (variationNode != null)
                        nodes.add(variationNode);
                    currentRelations.remove(variation.getId());
                } else if (!NodeOperation.DELETE.equals(operation)) {
                    // the variation does not exist, so create variation & cluclurelation
                    variationNode = programVariationAssembler.disassemble(variation, NodeOperation.CREATE, contextInfo);
                    if (variationNode != null)
                        nodes.add(variationNode);
                    programAssemblerUtils.addCreateRelationNode(major.getId(), variation.getId(), ProgramAssemblerConstants.HAS_PROGRAM_VARIATION, nodes);
                }
            } catch (Exception e) {
                throw new AssemblyException("Error while disassembling Variation", e);
            }
        }

        // Now any leftover variation ids are no longer needed, so suspend them
        if (currentRelations != null && currentRelations.size() > 0) {
            programAssemblerUtils.addSuspendedRelationNodes(currentRelations, nodes);
            addInactivateVariationNodes(currentRelations, nodes, contextInfo);
        }

        result.getChildNodes().addAll(nodes);
    }

    private void addInactivateVariationNodes(Map<String, CluCluRelationInfo> currentRelations, List<BaseDTOAssemblyNode<?, ?>> nodes, ContextInfo contextInfo) throws AssemblyException {
        for (String variationId : currentRelations.keySet()) {
            CluInfo variationClu;
            try {
                variationClu = cluService.getClu(variationId, contextInfo);
                ProgramVariationInfo delVariation = programVariationAssembler.assemble(variationClu, null, true, contextInfo);
                delVariation.setStateKey(DtoConstants.STATE_SUSPENDED);
                BaseDTOAssemblyNode<?, ?> variationNode = programVariationAssembler.disassemble(delVariation, NodeOperation.UPDATE, contextInfo);
                if (variationNode != null)
                    nodes.add(variationNode);
            } catch (Exception e) {
                throw new AssemblyException("Error while disassembling variation, deactivateVariations", e);
            }
        }
    }

    private void disassembleCoreProgram(MajorDisciplineInfo major, NodeOperation operation, BaseDTOAssemblyNode<MajorDisciplineInfo, CluInfo> result, ContextInfo contextInfo) throws AssemblyException {

        BaseDTOAssemblyNode<?, ?> coreResults;
        try {
            major.getOrgCoreProgram().setStateKey(major.getStateKey());
            coreResults = coreProgramAssembler.disassemble((CoreProgramInfo) major.getOrgCoreProgram(), operation, contextInfo);
            if (coreResults != null) {
                result.getChildNodes().add(coreResults);
            }
        } catch (Exception e) {
            throw new AssemblyException("Error while disassembling Core program", e);
        }
    }

    // Setters for Spring
    public void setCluService(CluService cluService) {
        this.cluService = cluService;
    }

    public void setProgramVariationAssembler(ProgramVariationAssembler programVariationAssembler) {
        this.programVariationAssembler = programVariationAssembler;
    }

    public ProgramVariationAssembler getProgramVariationAssembler() {
        return programVariationAssembler;
    }

    public void setCoreProgramAssembler(CoreProgramAssembler coreProgramAssembler) {
        this.coreProgramAssembler = coreProgramAssembler;
    }

    public void setCluAssemblerUtils(CluAssemblerUtils cluAssemblerUtils) {
        this.cluAssemblerUtils = cluAssemblerUtils;
    }

    public void setProgramAssemblerUtils(ProgramAssemblerUtils programAssemblerUtils) {
        this.programAssemblerUtils = programAssemblerUtils;
    }

}
