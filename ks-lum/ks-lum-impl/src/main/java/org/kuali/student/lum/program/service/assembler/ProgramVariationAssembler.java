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
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.core.assembly.BOAssembler;
import org.kuali.student.core.assembly.BaseDTOAssemblyNode;
import org.kuali.student.core.assembly.BaseDTOAssemblyNode.NodeOperation;
import org.kuali.student.core.assembly.data.AssemblyException;
import org.kuali.student.core.dto.AmountInfo;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.lum.course.service.assembler.CourseAssemblerConstants;
import org.kuali.student.lum.lu.dto.AdminOrgInfo;
import org.kuali.student.lum.lu.dto.CluIdentifierInfo;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.dto.CluResultInfo;
import org.kuali.student.lum.lu.dto.LuCodeInfo;
import org.kuali.student.lum.lu.dto.ResultOptionInfo;
import org.kuali.student.lum.lu.service.LuService;
import org.kuali.student.lum.program.dto.ProgramVariationInfo;
import org.kuali.student.lum.service.assembler.CluAssemblerUtils;

/**
 * @author KS
 *
 */
public class ProgramVariationAssembler implements BOAssembler<ProgramVariationInfo, CluInfo> {
    final static Logger LOG = Logger.getLogger(ProgramVariationAssembler.class);

    private LuService luService;
    private CluAssemblerUtils cluAssemblerUtils;

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
                pvInfo.setCip2000Code(codeInfo.getValue());
            } else if (ProgramAssemblerConstants.CIP_2010.equals(codeInfo.getId())) {
                pvInfo.setCip2010Code(codeInfo.getValue());
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
    public BaseDTOAssemblyNode<ProgramVariationInfo, CluInfo> disassemble(ProgramVariationInfo variation, NodeOperation operation) throws AssemblyException {
    	BaseDTOAssemblyNode<ProgramVariationInfo, CluInfo> result = new BaseDTOAssemblyNode<ProgramVariationInfo, CluInfo>(
				this);
    	
    	if (variation == null) {
			// FIXME Unsure now if this is an exception or just return null or
			// empty assemblyNode
		    LOG.error("Variation to disassemble is null!");
			throw new AssemblyException("Variation can not be null");
		}

		CluInfo clu;
		try {
			clu = (NodeOperation.UPDATE == operation) ? luService.getClu(variation.getId()) : new CluInfo();
        } catch (Exception e) {
			throw new AssemblyException("Error getting existing learning unit during variation update", e);
        } 
        
		// Create the id if it's not there already(important for creating relations)
		clu.setId(UUIDHelper.genStringUUID(variation.getId()));
		if (null == variation.getId()) {
			variation.setId(clu.getId());
		}
		
		clu.setType(variation.getType());
		clu.setState(variation.getState());
		clu.setMetaInfo(variation.getMetaInfo());
		clu.setAttributes(variation.getAttributes());
		
		AmountInfo intensity = new AmountInfo();
		intensity.setUnitType(variation.getIntensity());
		clu.setIntensity(intensity);
		
		clu.setReferenceURL(variation.getReferenceURL());
		
		CluIdentifierInfo identifier = new CluIdentifierInfo();
		identifier.setCode(variation.getCode());
		identifier.setLongName(variation.getLongTitle());
		identifier.setShortName(variation.getShortTitle());
		clu.setOfficialIdentifier(identifier);
		
		disassembleLuCode(clu, variation);
		
		BaseDTOAssemblyNode<?, ?> resultOptions = cluAssemblerUtils.disassembleCluResults(
				clu.getId(), variation.getState(), variation.getResultOptions(), operation, ProgramAssemblerConstants.DEGREE_RESULTS, "Result options", "Result option");
		result.getChildNodes().add(resultOptions);
		
		clu.setExpectedFirstAtp(variation.getStartTerm());
		clu.setLastAtp(variation.getEndTerm());
		clu.setLastAdmitAtp(variation.getEndProgramEntryTerm());
		clu.setEffectiveDate(variation.getEffectiveDate());
			
		disassembleAlternateIdentifiers(clu, variation);
		
		clu.setDescr(variation.getDescr());
		
		//TODO: catalogDescr
	
        try {
    		List<BaseDTOAssemblyNode<?, ?>> loResults;
    		loResults = cluAssemblerUtils.disassembleLos(clu.getId(), variation.getState(), variation.getLearningObjectives(), operation);
            result.getChildNodes().addAll(loResults);
        } catch (DoesNotExistException e) {
        } catch (Exception e) {
            throw new AssemblyException("Error while disassembling los", e);
        }
        
		clu.setCampusLocations(variation.getCampusLocations());
		
		//TODO: programRequirements
		
		//TODO: add AdminOrg and wait for CluInfo.adminOrgs
		disassembleAdminOrg(clu, variation);
		
		// Add the Clu to the result
		result.setNodeData(clu);
		result.setOperation(operation);
		result.setBusinessDTORef(variation);
		return result;
    	
    }

    private void disassembleAdminOrg(CluInfo clu, ProgramVariationInfo variation){
		List<AdminOrgInfo> orgs = new ArrayList<AdminOrgInfo>();
		for(AdminOrgInfo org:variation.getDivisionsContentOwner()){
			if(org.getType().equals(ProgramAssemblerConstants.CONTENT_OWNER_DIVISION)){
				orgs.add(org);
			}
		}
		clu.getAdminOrgs().addAll(orgs);
		
		orgs = new ArrayList<AdminOrgInfo>();
		for (AdminOrgInfo org : variation.getDivisionsStudentOversight()) {
			if(org.getType().equals(ProgramAssemblerConstants.STUDENT_OVERSIGHT_DIVISION)){
				orgs.add(org);
			}
		}
		clu.getAdminOrgs().addAll(orgs);

		orgs = new ArrayList<AdminOrgInfo>();
		for (AdminOrgInfo org : variation.getDivisionsDeployment()) {
			if(org.getType().equals(ProgramAssemblerConstants.DEPLOYMENT_DIVISION)){
				orgs.add(org);
			}
		}
		clu.getAdminOrgs().addAll(orgs);
		
		orgs = new ArrayList<AdminOrgInfo>();
		for (AdminOrgInfo org : variation.getDivisionsFinancialResources()) {
			if(org.getType().equals(ProgramAssemblerConstants.FINANCIAL_RESOURCES_DIVISION)){
				orgs.add(org);
			}
		}
		clu.getAdminOrgs().addAll(orgs);
		
		orgs = new ArrayList<AdminOrgInfo>();
		for (AdminOrgInfo org : variation.getDivisionsFinancialControl()) {
			if(org.getType().equals(ProgramAssemblerConstants.FINANCIAL_CONTROL_DIVISION)){
				orgs.add(org);
			}
		}
		clu.getAdminOrgs().addAll(orgs);
		
		orgs = new ArrayList<AdminOrgInfo>();
		for (AdminOrgInfo org : variation.getUnitsContentOwner()) {
			if(org.getType().equals(ProgramAssemblerConstants.CONTENT_OWNER_UNIT)){
				orgs.add(org);
			}
		}
		clu.getAdminOrgs().addAll(orgs);
		///
		orgs = new ArrayList<AdminOrgInfo>();
		for (AdminOrgInfo org : variation.getUnitsStudentOversight()) {
			if(org.getType().equals(ProgramAssemblerConstants.STUDENT_OVERSIGHT_UNIT)){
				orgs.add(org);
			}
		}
		clu.getAdminOrgs().addAll(orgs);
		
		orgs = new ArrayList<AdminOrgInfo>();
		for (AdminOrgInfo org : variation.getUnitsDeployment()) {
			if(org.getType().equals(ProgramAssemblerConstants.DEPLOYMENT_UNIT)){
				orgs.add(org);
			}
		}
		clu.getAdminOrgs().addAll(orgs);
		
		orgs = new ArrayList<AdminOrgInfo>();
		for (AdminOrgInfo org : variation.getUnitsFinancialResources()) {
			if(org.getType().equals(ProgramAssemblerConstants.FINANCIAL_RESOURCES_UNIT)){
				orgs.add(org);
			}
		}
		clu.getAdminOrgs().addAll(orgs);
		
		orgs = new ArrayList<AdminOrgInfo>();
		for (AdminOrgInfo org : variation.getUnitsFinancialControl()) {
			if(org.getType().equals(ProgramAssemblerConstants.FINANCIAL_CONTROL_UNIT)){
				orgs.add(org);
			}
		}
		clu.getAdminOrgs().addAll(orgs);
		
    }
 
    private void disassembleAlternateIdentifiers(CluInfo clu, ProgramVariationInfo variation){
		//Add TranscriptTitle
    	boolean alreadyHadTranscript = false;
		for(Iterator<CluIdentifierInfo> altIdentIterator = clu.getAlternateIdentifiers().iterator();altIdentIterator.hasNext();){
			CluIdentifierInfo altIdent = altIdentIterator.next();
			if(ProgramAssemblerConstants.TRANSCRIPT.equals(altIdent.getType())){
				alreadyHadTranscript = true;
				break;
			}
		}
		if(!alreadyHadTranscript){
			CluIdentifierInfo cluIdentInfo = new CluIdentifierInfo();
			cluIdentInfo.setShortName(variation.getTranscriptTitle());
			cluIdentInfo.setType(ProgramAssemblerConstants.TRANSCRIPT);
			clu.getAlternateIdentifiers().add(cluIdentInfo);
		}

		//Add DiplomaTitle
    	boolean alreadyHadDiploma = false;
		for(Iterator<CluIdentifierInfo> altIdentIterator = clu.getAlternateIdentifiers().iterator();altIdentIterator.hasNext();){
			CluIdentifierInfo altIdent = altIdentIterator.next();
			if(ProgramAssemblerConstants.DIPLOMA.equals(altIdent.getType())){
				alreadyHadDiploma = true;
				break;
			}
		}
		if(!alreadyHadDiploma){
			CluIdentifierInfo cluIdentInfo = new CluIdentifierInfo();
			cluIdentInfo.setShortName(variation.getDiplomaTitle());
			cluIdentInfo.setType(ProgramAssemblerConstants.DIPLOMA);
			clu.getAlternateIdentifiers().add(cluIdentInfo);
		}
    }
    private void disassembleLuCode(CluInfo clu, ProgramVariationInfo variation){
		//add CIP_2000
		boolean alreadyHadCIP2000 = false;
		for(Iterator<LuCodeInfo> luCodeIterator = clu.getLuCodes().iterator();luCodeIterator.hasNext();){
			LuCodeInfo luCode = luCodeIterator.next();
			if(ProgramAssemblerConstants.CIP_2000.equals(luCode.getType())){
				alreadyHadCIP2000 = true;
				break;
			}
		}
		if(!alreadyHadCIP2000){
			LuCodeInfo luCode = new LuCodeInfo();
			luCode.setType(ProgramAssemblerConstants.CIP_2000);
			luCode.setValue(variation.getCip2000Code());
			clu.getLuCodes().add(luCode);
		}	
		
		//add CIP_2010
		boolean alreadyHadCIP2010 = false;
		for(Iterator<LuCodeInfo> luCodeIterator = clu.getLuCodes().iterator();luCodeIterator.hasNext();){
			LuCodeInfo luCode = luCodeIterator.next();
			if(ProgramAssemblerConstants.CIP_2010.equals(luCode.getType())){
				alreadyHadCIP2010 = true;
				break;
			}
		}
		if(!alreadyHadCIP2010){
			LuCodeInfo luCode = new LuCodeInfo();
			luCode.setType(ProgramAssemblerConstants.CIP_2010);
			luCode.setValue(variation.getCip2010Code());
			clu.getLuCodes().add(luCode);
		}		
		
		//add HEGIS
		boolean alreadyHadHEGIS = false;
		for(Iterator<LuCodeInfo> luCodeIterator = clu.getLuCodes().iterator();luCodeIterator.hasNext();){
			LuCodeInfo luCode = luCodeIterator.next();
			if(ProgramAssemblerConstants.HEGIS.equals(luCode.getType())){
				alreadyHadCIP2000 = true;
				break;
			}
		}
		if(!alreadyHadHEGIS){
			LuCodeInfo luCode = new LuCodeInfo();
			luCode.setType(ProgramAssemblerConstants.HEGIS);
			luCode.setValue(variation.getHegisCode());
			clu.getLuCodes().add(luCode);
		}
		
		//add UNIVERSITY_CLASSIFICATION
		boolean alreadyHadUniversityClassification = false;
		for(Iterator<LuCodeInfo> luCodeIterator = clu.getLuCodes().iterator();luCodeIterator.hasNext();){
			LuCodeInfo luCode = luCodeIterator.next();
			if(ProgramAssemblerConstants.UNIVERSITY_CLASSIFICATION.equals(luCode.getType())){
				alreadyHadUniversityClassification = true;
				break;
			}
		}
		if(!alreadyHadUniversityClassification){
			LuCodeInfo luCode = new LuCodeInfo();
			luCode.setType(ProgramAssemblerConstants.UNIVERSITY_CLASSIFICATION);
			luCode.setValue(variation.getUniversityClassification());
			clu.getLuCodes().add(luCode);
		}				
		
		//add SELECTIVE_ENROLLMENT
		boolean alreadySelectiveEnrollment = false;
		for(Iterator<LuCodeInfo> luCodeIterator = clu.getLuCodes().iterator();luCodeIterator.hasNext();){
			LuCodeInfo luCode = luCodeIterator.next();
			if(ProgramAssemblerConstants.SELECTIVE_ENROLLMENT.equals(luCode.getType())){
				alreadySelectiveEnrollment = true;
				break;
			}
		}
		if(!alreadySelectiveEnrollment){
			LuCodeInfo luCode = new LuCodeInfo();
			luCode.setType(ProgramAssemblerConstants.SELECTIVE_ENROLLMENT);
			luCode.setValue(variation.getSelectiveEnrollmentCode());
			clu.getLuCodes().add(luCode);
		}			
		   	
    }
    // Spring setter
    public void setLuService(LuService luService) {
        this.luService = luService;
    }
    
    public void setCluAssemblerUtils(CluAssemblerUtils cluAssemblerUtils) {
        this.cluAssemblerUtils = cluAssemblerUtils;
    }
}
