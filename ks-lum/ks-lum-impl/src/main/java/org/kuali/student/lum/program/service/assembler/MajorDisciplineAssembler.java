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
import org.kuali.student.core.dto.AmountInfo;
import org.kuali.student.core.exceptions.DataValidationErrorException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.lum.course.service.assembler.CourseAssembler;
import org.kuali.student.lum.lu.dto.CluCluRelationInfo;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.dto.LuCodeInfo;
import org.kuali.student.lum.lu.service.LuService;
import org.kuali.student.lum.program.dto.CoreProgramInfo;
import org.kuali.student.lum.program.dto.MajorDisciplineInfo;
import org.kuali.student.lum.program.dto.ProgramVariationInfo;
import org.kuali.student.lum.service.assembler.CluAssemblerUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * @author KS TODO - Much of this should be shared with ProgramVariationAssembler (and probably other Program Assemblers to
 *         come). AssemblerUtils?
 */
public class MajorDisciplineAssembler implements BOAssembler<MajorDisciplineInfo, CluInfo> {
    final static Logger LOG = Logger.getLogger(CourseAssembler.class);

    private LuService luService;

    private ProgramVariationAssembler programVariationAssembler;
    private CoreProgramAssembler coreProgramAssembler;
    private CluAssemblerUtils cluAssemblerUtils;
    private ProgramAssemblerUtils programAssemblerUtils;

    @Override
    public MajorDisciplineInfo assemble(CluInfo clu, MajorDisciplineInfo majorDiscipline, boolean shallowBuild) throws AssemblyException {

        MajorDisciplineInfo mdInfo = (null != majorDiscipline) ? majorDiscipline : new MajorDisciplineInfo();

        // Copy all the data from the clu to the majordiscipline
        programAssemblerUtils.assembleBasics(clu, mdInfo);
        programAssemblerUtils.assembleIdentifiers(clu, mdInfo);
        programAssemblerUtils.assembleAdminOrgs(clu, mdInfo);
        programAssemblerUtils.assembleAtps(clu, mdInfo);
        programAssemblerUtils.assembleLuCodes(clu, mdInfo);
        programAssemblerUtils.assemblePublicationInfo(clu, mdInfo);
        programAssemblerUtils.assembleRequirements(clu, mdInfo);

        mdInfo.setIntensity((null != clu.getIntensity()) ? clu.getIntensity().getUnitType() : null);
        mdInfo.setStdDuration(clu.getStdDuration());
        mdInfo.setPublishedInstructors(clu.getInstructors());
        mdInfo.setCampusLocations(clu.getCampusLocations());        
        mdInfo.setAccreditingAgencies(clu.getAccreditations());
        mdInfo.setEffectiveDate(clu.getEffectiveDate());
        mdInfo.setDescr(clu.getDescr());

        if (!shallowBuild) {
            mdInfo.setCredentialProgramId(programAssemblerUtils.assembleCredentialProgramIDs(clu.getId(), ProgramAssemblerConstants.HAS_MAJOR_PROGRAM));
            mdInfo.setResultOptions(programAssemblerUtils.assembleResultOptions(clu.getId(), ProgramAssemblerConstants.CERTIFICATE_RESULTS));
            mdInfo.setLearningObjectives(cluAssemblerUtils.assembleLearningObjectives(clu.getId(), shallowBuild));
            mdInfo.setVariations(assembleVariations(clu.getId(), shallowBuild));
            mdInfo.setOrgCoreProgram(assembleCoreProgram(clu.getId(), shallowBuild));
        }
        
       return mdInfo;
    }

    private void disassembleCodes(CluInfo clu, MajorDisciplineInfo major) {

        //TODO This is good for create but need to handle updates too!!

        if (major.getCip2000Code() != null) {
            LuCodeInfo luCode = buildLuCode(ProgramAssemblerConstants.CIP_2000, major.getCip2000Code());
            clu.getLuCodes().add(luCode);
        }
        if (major.getCip2010Code() != null) {
            LuCodeInfo luCode = buildLuCode(ProgramAssemblerConstants.CIP_2010, major.getCip2010Code());
            clu.getLuCodes().add(luCode);
        }
        if (major.getHegisCode() != null) {
            LuCodeInfo luCode = buildLuCode(ProgramAssemblerConstants.HEGIS, major.getHegisCode() );
            clu.getLuCodes().add(luCode);
        }
        if (major.getUniversityClassification() != null) {
            LuCodeInfo luCode = buildLuCode(ProgramAssemblerConstants.UNIVERSITY_CLASSIFICATION, major.getUniversityClassification() );
            clu.getLuCodes().add(luCode);
        }
        if (major.getSelectiveEnrollmentCode() != null) {
            LuCodeInfo luCode = buildLuCode(ProgramAssemblerConstants.SELECTIVE_ENROLLMENT, major.getSelectiveEnrollmentCode() );
            clu.getLuCodes().add(luCode);
        }

    }

    private LuCodeInfo buildLuCode(String type, String value) {
        LuCodeInfo luCode = new LuCodeInfo();
        luCode.setType(type);
        luCode.setValue(value);
        return luCode;
    }

    private CoreProgramInfo assembleCoreProgram(String cluId, boolean shallowBuild) throws AssemblyException {
        CoreProgramInfo coreProgramInfo = null;
        try {
            List<CluInfo> corePrograms = luService.getRelatedClusByCluId(cluId, ProgramAssemblerConstants.HAS_CORE_PROGRAM);
            // TODO - is it an error if there's more than one core program?
            if (corePrograms.size() == 1) {
                coreProgramInfo = coreProgramAssembler.assemble(corePrograms.get(0), null, shallowBuild);
            } else if (corePrograms.size() > 1) {
                throw new AssemblyException(new DataValidationErrorException("MajorDiscipline has more than one associated Core Program"));
            }
        } catch (Exception e) {
            throw new AssemblyException(e);
        }
        return coreProgramInfo;
    }

    private List<ProgramVariationInfo> assembleVariations(String cluId, boolean shallowBuild) throws AssemblyException {
        List<String> variationIds;
        List<ProgramVariationInfo> variations = new ArrayList<ProgramVariationInfo>();
        try {
            variationIds = luService.getRelatedCluIdsByCluId(cluId, ProgramAssemblerConstants.HAS_PROGRAM_VARIATION);

            for (String variationId : variationIds) {
                CluInfo variationClu = luService.getClu(variationId);
                variations.add(programVariationAssembler.assemble(variationClu, null, shallowBuild));
            }
        } catch (Exception e) {
            throw new AssemblyException(e);
        }
        return variations;
    }

    @Override
    public BaseDTOAssemblyNode<MajorDisciplineInfo, CluInfo> disassemble(MajorDisciplineInfo major, NodeOperation operation) throws AssemblyException {
		if (major == null) {
		    LOG.error("Major for  disassemble is null!");
			throw new AssemblyException("Major cannot be null");
		}

        //TODO   IDs for objects w/o ids

		BaseDTOAssemblyNode<MajorDisciplineInfo, CluInfo> result = new BaseDTOAssemblyNode<MajorDisciplineInfo, CluInfo>(
				this);
		
		CluInfo clu;
		try {
			clu = (NodeOperation.UPDATE == operation) ? luService.getClu(major.getId()) : new CluInfo();
        } catch (Exception e) {
			throw new AssemblyException("Error getting existing learning unit during major update", e);
        } 
        
        programAssemblerUtils.disassembleBasics(clu, major, operation);
        if (major.getId() == null)
            major.setId(clu.getId());
        programAssemblerUtils.disassembleLuCodes(clu, major, operation);
        programAssemblerUtils.disassembleAdminOrgs(clu, major, operation);
        programAssemblerUtils.disassembleAtps(clu, major, operation);
        programAssemblerUtils.disassembleIdentifiers(clu, major, operation);
        programAssemblerUtils.disassemblePublicationInfo(clu, major, operation);


        if (major.getVariations() != null && !major.getVariations().isEmpty()) {
            disassembleVariations(major, operation, result);
        }
        if (major.getOrgCoreProgram() != null ) {
            disassembleCoreProgram(major, operation, result);
        }
        if (major.getCredentialProgramId() != null) {
            disassembleCredentialProgram(major, operation, result);
        }
        if (major.getResultOptions() != null) {
            disassembleResultOptions(major, operation, result);
        }
        if (major.getLearningObjectives() != null) {
            disassembleLearningObjectives(major, operation, result);
        }

        AmountInfo intensity = new AmountInfo();
        intensity.setUnitType(major.getIntensity());
		clu.setIntensity(intensity);
        clu.setStdDuration(major.getStdDuration());
        clu.setInstructors(major.getPublishedInstructors());

        clu.setNextReviewPeriod(major.getNextReviewPeriod());
        clu.setEffectiveDate(major.getEffectiveDate());

        clu.setCampusLocations(major.getCampusLocations());
        clu.setDescr(major.getDescr());

        //TODO programRequirements
        clu.setAccreditations(major.getAccreditingAgencies());

		// Add the Clu to the result
		result.setNodeData(clu);
		result.setOperation(operation);
		result.setBusinessDTORef(major);

    	return result;
    }

    private void disassembleLearningObjectives(MajorDisciplineInfo major, NodeOperation operation, BaseDTOAssemblyNode<MajorDisciplineInfo, CluInfo> result) throws AssemblyException {
        try {
            List<BaseDTOAssemblyNode<?, ?>> loResults = cluAssemblerUtils.disassembleLos(major.getId(), major.getState(),  major.getLearningObjectives(), operation);
            if (loResults != null) {
                result.getChildNodes().addAll(loResults);
            }
        } catch (DoesNotExistException e) {
        } catch (Exception e) {
            throw new AssemblyException("Error while disassembling los", e);
        }
    }

    private void disassembleResultOptions(MajorDisciplineInfo major, NodeOperation operation, BaseDTOAssemblyNode<MajorDisciplineInfo, CluInfo> result) throws AssemblyException {
        BaseDTOAssemblyNode<?, ?> resultOptions = cluAssemblerUtils.disassembleCluResults(
                major.getId(), major.getState(), major.getResultOptions(), operation, ProgramAssemblerConstants.DEGREE_RESULTS, "Result options", "Result option");
        if (resultOptions != null) {
            result.getChildNodes().add(resultOptions);           
        }
    }

    private void disassembleCredentialProgram(MajorDisciplineInfo major, NodeOperation operation, BaseDTOAssemblyNode<MajorDisciplineInfo, CluInfo> result) throws AssemblyException {
        List<BaseDTOAssemblyNode<?, ?>> results = new ArrayList<BaseDTOAssemblyNode<?, ?>>();
        CluInfo credentialClu = null;

        // id
        try {
            credentialClu = luService.getClu(major.getCredentialProgramId());
        } catch (DoesNotExistException e) {
        } catch (Exception e) {
            throw new AssemblyException("Credential Clu does not exist for " + major.getCredentialProgramId());
        }

        //TODO check for update and delete

        // Create the relationship and add it as well
        CluCluRelationInfo relation = new CluCluRelationInfo();
        relation.setCluId(major.getId());
        relation.setRelatedCluId(credentialClu.getId());// this
        // should
        // already
        // be set even if
        // it's a create
        relation.setType(ProgramAssemblerConstants.HAS_CORE_PROGRAM);
        relation.setState(major.getState());

        BaseDTOAssemblyNode<MajorDisciplineInfo, CluCluRelationInfo> relationNode = new BaseDTOAssemblyNode<MajorDisciplineInfo, CluCluRelationInfo>(
                null);
        relationNode.setNodeData(relation);
        relationNode.setOperation(NodeOperation.CREATE);

        results.add(relationNode);

        result.getChildNodes().addAll(results);


    }

    private void disassembleVariations(MajorDisciplineInfo major, NodeOperation operation, BaseDTOAssemblyNode<MajorDisciplineInfo, CluInfo> result) throws AssemblyException {

        for (ProgramVariationInfo variation : major.getVariations()) {
            BaseDTOAssemblyNode<?,?> variationResults;
            try {
                variationResults = programVariationAssembler.disassemble(variation, operation);
                if (variationResults != null) {
                    result.getChildNodes().add(variationResults);
                }
            } catch (Exception e) {
                throw new AssemblyException("Error while disassembling Variation", e);
            }
        }
    }

    private void disassembleCoreProgram(MajorDisciplineInfo major, NodeOperation operation, BaseDTOAssemblyNode<MajorDisciplineInfo, CluInfo> result) throws AssemblyException {

        BaseDTOAssemblyNode<?,?> coreResults;
        try {
            coreResults = coreProgramAssembler.disassemble(major.getOrgCoreProgram(), operation);
            if (coreResults != null) {
                result.getChildNodes().add(coreResults);
            }
        } catch (Exception e) {
            throw new AssemblyException("Error while disassembling Core program", e);
        }
    }

    // Setters for Spring
    public void setLuService(LuService luService) {
        this.luService = luService;
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
