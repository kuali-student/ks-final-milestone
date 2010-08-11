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
import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.core.assembly.BOAssembler;
import org.kuali.student.core.assembly.BaseDTOAssemblyNode;
import org.kuali.student.core.assembly.BaseDTOAssemblyNode.NodeOperation;
import org.kuali.student.core.assembly.data.AssemblyException;
import org.kuali.student.core.dto.AmountInfo;
import org.kuali.student.core.exceptions.DataValidationErrorException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.lum.course.service.assembler.CourseAssembler;
import org.kuali.student.lum.lu.dto.CluIdentifierInfo;
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
        programAssemblerUtils.assembleOrgs(clu, mdInfo);
        programAssemblerUtils.assembleAtps(clu, mdInfo);
        programAssemblerUtils.assembleLuCodes(clu, mdInfo);
        programAssemblerUtils.assemblePublicationInfo(clu, mdInfo);
        programAssemblerUtils.assembleRequirements(clu, mdInfo);

        mdInfo.setCredentialProgramId(programAssemblerUtils.assembleCredentialProgramIDs(clu.getId(), ProgramAssemblerConstants.HAS_MAJOR_PROGRAM));
        mdInfo.setResultOptions(programAssemblerUtils.assembleResultOptions(clu.getId(), ProgramAssemblerConstants.CERTIFICATE_RESULTS));
        mdInfo.setLearningObjectives(cluAssemblerUtils.assembleLearningObjectives(clu.getId(), shallowBuild));
        mdInfo.setVariations(assembleVariations(clu.getId(), shallowBuild));
        mdInfo.setOrgCoreProgram(assembleCoreProgram(clu.getId(), shallowBuild));

        mdInfo.setIntensity((null != clu.getIntensity()) ? clu.getIntensity().getUnitType() : null);
        mdInfo.setStdDuration(clu.getStdDuration());
        mdInfo.setPublishedInstructors(clu.getInstructors());
        mdInfo.setCampusLocations(clu.getCampusLocations());        
        mdInfo.setAccreditingAgencies(clu.getAccreditations());
        mdInfo.setEffectiveDate(clu.getEffectiveDate());

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
    	
		BaseDTOAssemblyNode<MajorDisciplineInfo, CluInfo> result = new BaseDTOAssemblyNode<MajorDisciplineInfo, CluInfo>(
				this);
		
		CluInfo clu;
		try {
			clu = (NodeOperation.UPDATE == operation) ? luService.getClu(major.getId()) : new CluInfo();
        } catch (Exception e) {
			throw new AssemblyException("Error getting existing learning unit during major update", e);
        } 
        
        AmountInfo intensity = new AmountInfo();
        intensity.setUnitType(major.getIntensity());
		clu.setIntensity(intensity);
        clu.setStdDuration(major.getStdDuration());
        clu.setReferenceURL(major.getReferenceURL());
        clu.setInstructors(major.getPublishedInstructors());

        if (major.getVariations() != null && !major.getVariations().isEmpty()) {
            disassembleVariations(major, clu, operation, result);
        }

        disassembleCodes(clu, major);

        BaseDTOAssemblyNode<?, ?> resultOptions = cluAssemblerUtils.disassembleCluResults(
                clu.getId(), major.getState(), major.getResultOptions(), operation, ProgramAssemblerConstants.DEGREE_RESULTS, "Result options", "Result option");
        result.getChildNodes().add(resultOptions);

        clu.setExpectedFirstAtp(major.getStartTerm());
        clu.setLastAtp(major.getEndTerm());
        clu.setLastAdmitAtp(major.getEndProgramEntryTerm());
        clu.setNextReviewPeriod(major.getNextReviewPeriod());
        clu.setEffectiveDate(major.getEffectiveDate());

        disassembleIdentifiers(clu, major);
 
        clu.setDescr(major.getDescr());
//        disassembleCatalogDescr(major, clu);

        //TODO catalog publication targets

        try {
    		List<BaseDTOAssemblyNode<?, ?>> loResults;
    		loResults = cluAssemblerUtils.disassembleLos(clu.getId(), major.getState(),  major.getLearningObjectives(), operation);
            result.getChildNodes().addAll(loResults);
        } catch (DoesNotExistException e) {
        } catch (Exception e) {
            throw new AssemblyException("Error while disassembling los", e);
        }

        clu.setCampusLocations(major.getCampusLocations());

        //TODO orgCoreProgram
        //TODO programRequirements
//        clu.setAccreditations(major.getAccreditingAgencies());
//        disassembleOrgs(major, clu);

        clu.setAttributes(major.getAttributes());

        clu.setMetaInfo(major.getMetaInfo());
        clu.setType(major.getType());
        clu.setState(major.getState());
        clu.setId(UUIDHelper.genStringUUID(major.getId()));
        if (null == major.getId()) {
            major.setId(clu.getId());
        }

		// Add the Clu to the result
		result.setNodeData(clu);
		result.setOperation(operation);
		result.setBusinessDTORef(major);

	    
    	return result;
    }

    private void disassembleVariations(MajorDisciplineInfo major, CluInfo clu, NodeOperation operation, BaseDTOAssemblyNode<MajorDisciplineInfo, CluInfo> result) throws AssemblyException {

        for (ProgramVariationInfo variation : major.getVariations()) {
            BaseDTOAssemblyNode<?,?> variationResults;
            try {
                variationResults = programVariationAssembler.disassemble(variation, operation);
                result.getChildNodes().add(variationResults);
            } catch (Exception e) {
                throw new AssemblyException("Error while disassembling Variation", e);
            }
        }
    }

    private void disassembleIdentifiers(CluInfo clu, MajorDisciplineInfo major) {
        CluIdentifierInfo id1 = new CluIdentifierInfo();
        id1.setCode(major.getCode());
        id1.setLongName(major.getLongTitle());
        id1.setShortName(major.getShortTitle());
        clu.setOfficialIdentifier(id1);

        if (major.getTranscriptTitle() != null ) {
            CluIdentifierInfo id2 = new CluIdentifierInfo();
            id2.setCode(major.getCode());
            id2.setShortName(major.getTranscriptTitle());
            id2.setType(ProgramAssemblerConstants.TRANSCRIPT);
            clu.getAlternateIdentifiers().add(id1);
        }
        if (major.getDiplomaTitle() != null ) {
            CluIdentifierInfo id2 = new CluIdentifierInfo();
            id2.setCode(major.getCode());
            id2.setShortName(major.getDiplomaTitle());
            id2.setType(ProgramAssemblerConstants.DIPLOMA);
            clu.getAlternateIdentifiers().add(id1);
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
