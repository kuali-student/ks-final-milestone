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
import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.core.assembly.BOAssembler;
import org.kuali.student.core.assembly.BaseDTOAssemblyNode;
import org.kuali.student.core.assembly.BaseDTOAssemblyNode.NodeOperation;
import org.kuali.student.core.assembly.data.AssemblyException;
import org.kuali.student.core.dto.AmountInfo;
import org.kuali.student.core.dto.RichTextInfo;
import org.kuali.student.core.exceptions.DataValidationErrorException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.lum.course.service.assembler.CourseAssembler;
import org.kuali.student.lum.lo.service.LearningObjectiveService;
import org.kuali.student.lum.lu.dto.AdminOrgInfo;
import org.kuali.student.lum.lu.dto.CluIdentifierInfo;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.dto.CluPublicationInfo;
import org.kuali.student.lum.lu.dto.CluResultInfo;
import org.kuali.student.lum.lu.dto.FieldInfo;
import org.kuali.student.lum.lu.dto.LuCodeInfo;
import org.kuali.student.lum.lu.service.LuService;
import org.kuali.student.lum.program.dto.CoreProgramInfo;
import org.kuali.student.lum.program.dto.MajorDisciplineInfo;
import org.kuali.student.lum.program.dto.ProgramVariationInfo;
import org.kuali.student.lum.service.assembler.CluAssemblerUtils;


/**
 * @author KS TODO - Much of this should be shared with ProgramVariationAssembler (and probably other Program Assemblers to
 *         come). AssemblerUtils?
 */
public class MajorDisciplineAssembler implements BOAssembler<MajorDisciplineInfo, CluInfo> {
    final static Logger LOG = Logger.getLogger(CourseAssembler.class);

    private LuService luService;
    private LearningObjectiveService loService;

    private ProgramVariationAssembler programVariationAssembler;
    private CoreProgramAssembler coreProgramAssembler;
    private CluAssemblerUtils cluAssemblerUtils;

    @Override
    public MajorDisciplineInfo assemble(CluInfo clu, MajorDisciplineInfo majorDiscipline, boolean shallowBuild) throws AssemblyException {

        MajorDisciplineInfo mdInfo = (null != majorDiscipline) ? majorDiscipline : new MajorDisciplineInfo();

        // Copy all the data from the clu to the majordiscipline
        mdInfo.setIntensity((null != clu.getIntensity()) ? clu.getIntensity().getUnitType() : null);
        mdInfo.setStdDuration(clu.getStdDuration());
        mdInfo.setReferenceURL((null != clu.getReferenceURL()) ? clu.getReferenceURL() : null);
        mdInfo.setPublishedInstructors(clu.getInstructors());        
        mdInfo.setCredentialProgramId(assembleCredentialProgramID(clu.getId()));

        mdInfo.setVariations(assembleVariations(clu.getId(), shallowBuild));
        mdInfo.setCode(clu.getOfficialIdentifier().getCode());
        assembleCodes(mdInfo, clu);

        mdInfo.setResultOptions(assembleResultOptions(clu.getId()));

        mdInfo.setStartTerm(clu.getExpectedFirstAtp());
        mdInfo.setEndTerm(clu.getLastAtp());
        mdInfo.setEndProgramEntryTerm(clu.getLastAdmitAtp());        
        mdInfo.setNextReviewPeriod(clu.getNextReviewPeriod());
        mdInfo.setEffectiveDate(clu.getEffectiveDate());

        assembleTitles(mdInfo, clu);
        mdInfo.setDescr(clu.getDescr());
        mdInfo.setCatalogDescr(assembleCatalogDescr(clu.getId()));
//TODO        mdInfo.setCatalogPublicationTargets(clu.getPublicationInfo());
        mdInfo.setLearningObjectives(cluAssemblerUtils.assembleLearningObjectives(clu.getId(), shallowBuild));
        mdInfo.setCampusLocations(clu.getCampusLocations());
        
        mdInfo.setOrgCoreProgram(assembleCoreProgram(clu.getId(), shallowBuild));
        //TODO   mdInfo.setProgramRequirements(???);
        // TODO - https://test.kuali.org/confluence/display/KULSTU/majorDisciplineInfo+Structure says ....orgId
        mdInfo.setAccreditingAgencies(clu.getAccreditations());

        assembleOrgs(mdInfo, clu.getAdminOrgs());

        mdInfo.setAttributes(clu.getAttributes());
        mdInfo.setMetaInfo(clu.getMetaInfo());
        mdInfo.setType(clu.getType());
        mdInfo.setState(clu.getState());
        mdInfo.setId(clu.getId());
        return mdInfo;
    }

    private List<String> assembleResultOptions(String cluId) throws AssemblyException {
        List<String> resultOptions = null;
        try{
            List<CluResultInfo> cluResults = luService.getCluResultByClu(cluId);

            //TODO Just one result type here?
            resultOptions = cluAssemblerUtils.assembleCluResults(ProgramAssemblerConstants.CERTIFICATE_RESULTS, cluResults);

        } catch (DoesNotExistException e){
        } catch (Exception e) {
            throw new AssemblyException("Error getting major results", e);
        }
        return resultOptions;
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

    private String assembleCredentialProgramID(String cluId) throws AssemblyException {
        List<String> credentialProgramIDs = null;
        try {
            credentialProgramIDs = luService.getCluIdsByRelation(cluId, ProgramAssemblerConstants.HAS_MAJOR_PROGRAM);
        } catch (Exception e) {
            throw new AssemblyException(e);
        }
        // Can a MajorDiscipline have more than one Credential Program?
        // TODO - do we need to validate that?
        if (null == credentialProgramIDs || credentialProgramIDs.size() == 0) {
            throw new AssemblyException("MajorDiscipline with ID == " + cluId + " has no Credential Program associated with it.");
        } else if (credentialProgramIDs.size() > 1) {
            throw new AssemblyException("MajorDiscipline with ID == " + cluId + " has more than one Credential Program associated with it.");
        }

        return credentialProgramIDs.get(0);
    }

    private void assembleCodes(MajorDisciplineInfo majorDiscipline, CluInfo clu) {
        List<LuCodeInfo> luCodes = clu.getLuCodes();
        for (LuCodeInfo codeInfo : luCodes) {
            if (ProgramAssemblerConstants.CIP_2000.equals(codeInfo.getType())) {
                majorDiscipline.setCip2000Code(codeInfo.getValue());
            } else if (ProgramAssemblerConstants.CIP_2010.equals(codeInfo.getType())) {
                majorDiscipline.setCip2010Code(codeInfo.getValue());
            } else if (ProgramAssemblerConstants.HEGIS.equals(codeInfo.getType())) {
                majorDiscipline.setHegisCode(codeInfo.getValue());
            } else if (ProgramAssemblerConstants.UNIVERSITY_CLASSIFICATION.equals(codeInfo.getType())) {
                majorDiscipline.setUniversityClassification(codeInfo.getValue());
            } else if (ProgramAssemblerConstants.SELECTIVE_ENROLLMENT.equals(codeInfo.getType())) {
                majorDiscipline.setSelectiveEnrollmentCode(codeInfo.getValue());
            }
        }
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


    private void assembleTitles(MajorDisciplineInfo mdInfo, CluInfo clu) {
        mdInfo.setShortTitle(clu.getOfficialIdentifier().getShortName());
        mdInfo.setLongTitle(clu.getOfficialIdentifier().getLongName());

        for (CluIdentifierInfo cluIdInfo : clu.getAlternateIdentifiers()) {
            String idInfoType = cluIdInfo.getType();
            if (ProgramAssemblerConstants.TRANSCRIPT.equals(idInfoType)) {
                mdInfo.setTranscriptTitle(cluIdInfo.getShortName());
            } else if (ProgramAssemblerConstants.DIPLOMA.equals(idInfoType)) {
                mdInfo.setDiplomaTitle(cluIdInfo.getShortName());
            }
        }
    }

    private RichTextInfo assembleCatalogDescr(String cluId) throws AssemblyException {
//        RichTextInfo returnInfo = new RichTextInfo();
//        try {
//            List<CluPublicationInfo> pubs = luService.getCluPublicationsByCluId(cluId);
//            for (CluPublicationInfo pubInfo : pubs) {
//                for (FieldInfo fieldInfo : pubInfo.getVariants()) {
//                    if (fieldInfo.getId().equals(ProgramAssemblerConstants.CLU_INFO + "." + ProgramAssemblerConstants.DESCR)) {
//                        returnInfo.setPlain(fieldInfo.getValue());
//                        return returnInfo; // or break to a label to avoid multiple return points
//                    }
//                }
//            }
//        } catch (Exception e) {
//            throw new AssemblyException(e);
//        }
//        return returnInfo;
        return null;
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

    private void assembleOrgs(MajorDisciplineInfo mdInfo, List<AdminOrgInfo> orgInfos) {
        
        for (AdminOrgInfo orgInfo : orgInfos) {
            AdminOrgInfo mdOrg = buildOrg(orgInfo);
            if (orgInfo.getType().equals(ProgramAssemblerConstants.CURRICULUM_OVERSIGHT_DIVISION)) {
                if (mdInfo.getDivisionsContentOwner() == null)
                    mdInfo.setDivisionsContentOwner(new ArrayList<AdminOrgInfo>());
                mdInfo.getDivisionsContentOwner().add(mdOrg);               
            }
            else if (orgInfo.getType().equals(ProgramAssemblerConstants.STUDENT_OVERSIGHT_DIVISION)) {
                if (mdInfo.getDivisionsStudentOversight() == null)
                    mdInfo.setDivisionsStudentOversight(new ArrayList<AdminOrgInfo>());
                mdInfo.getDivisionsStudentOversight().add(mdOrg);
            }
            else if (orgInfo.getType().equals(ProgramAssemblerConstants.DEPLOYMENT_DIVISION)) {
                if (mdInfo.getDivisionsDeployment() == null)
                    mdInfo.setDivisionsDeployment(new ArrayList<AdminOrgInfo>());
                mdInfo.getDivisionsDeployment().add(mdOrg);
            }
            else if (orgInfo.getType().equals(ProgramAssemblerConstants.FINANCIAL_RESOURCES_DIVISION)) {
                if (mdInfo.getDivisionsFinancialResources() == null)
                    mdInfo.setDivisionsFinancialResources(new ArrayList<AdminOrgInfo>());
                mdInfo.getDivisionsFinancialResources().add(mdOrg);
            }
            else if (orgInfo.getType().equals(ProgramAssemblerConstants.FINANCIAL_CONTROL_DIVISION)) {
                if (mdInfo.getDivisionsFinancialControl() == null)
                    mdInfo.setDivisionsFinancialControl(new ArrayList<AdminOrgInfo>());
                mdInfo.getDivisionsFinancialControl().add(mdOrg);
            }
            else if (orgInfo.getType().equals(ProgramAssemblerConstants.CURRICULUM_OVERSIGHT_UNIT)) {
                if (mdInfo.getUnitsContentOwner() == null)
                    mdInfo.setUnitsContentOwner(new ArrayList<AdminOrgInfo>());
                mdInfo.getUnitsContentOwner().add(mdOrg);
            }
            else if (orgInfo.getType().equals(ProgramAssemblerConstants.STUDENT_OVERSIGHT_UNIT)) {
                if (mdInfo.getUnitsStudentOversight() == null)
                    mdInfo.setUnitsStudentOversight(new ArrayList<AdminOrgInfo>());
                mdInfo.getUnitsStudentOversight().add(mdOrg);
            }
            else if (orgInfo.getType().equals(ProgramAssemblerConstants.DEPLOYMENT_UNIT)) {
                if (mdInfo.getUnitsDeployment() == null)
                    mdInfo.setUnitsDeployment(new ArrayList<AdminOrgInfo>());
                mdInfo.getUnitsDeployment().add(mdOrg);
            }
            else if (orgInfo.getType().equals(ProgramAssemblerConstants.FINANCIAL_RESOURCES_UNIT)) {
                if (mdInfo.getUnitsFinancialResources() == null)
                    mdInfo.setUnitsFinancialResources(new ArrayList<AdminOrgInfo>());
                mdInfo.getUnitsFinancialResources().add(mdOrg);
            }
            else if (orgInfo.getType().equals(ProgramAssemblerConstants.FINANCIAL_CONTROL_UNIT)) {
                if (mdInfo.getUnitsFinancialControl() == null)
                    mdInfo.setUnitsFinancialControl(new ArrayList<AdminOrgInfo>());
                mdInfo.getUnitsFinancialControl().add(mdOrg);
            }
        }
    }



    private AdminOrgInfo buildLuCode(AdminOrgInfo orgInfo) {
        AdminOrgInfo mdOrg = new AdminOrgInfo();
        mdOrg.setId(orgInfo.getId());
        mdOrg.setOrgId(orgInfo.getId());
        mdOrg.setPrimary(false);
        mdOrg.setType(orgInfo.getType());
        return mdOrg;
    }
    private AdminOrgInfo buildOrg(AdminOrgInfo orgInfo) {
        AdminOrgInfo mdOrg = new AdminOrgInfo();
        mdOrg.setId(orgInfo.getId());
        mdOrg.setOrgId(orgInfo.getId());
        mdOrg.setPrimary(false);
        mdOrg.setType(orgInfo.getType());
        return mdOrg;
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

    public void setLoService(LearningObjectiveService loService) {
        this.loService = loService;
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
}
