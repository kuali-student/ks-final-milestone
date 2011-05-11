/*
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.lum.program.service.assembler;

import static org.apache.commons.lang.StringUtils.isEmpty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.assembly.BaseDTOAssemblyNode;
import org.kuali.student.common.assembly.BaseDTOAssemblyNode.NodeOperation;
import org.kuali.student.common.assembly.data.AssemblyException;
import org.kuali.student.common.dto.DtoConstants;
import org.kuali.student.common.dto.RichTextInfo;
import org.kuali.student.common.exceptions.DoesNotExistException;
import org.kuali.student.common.exceptions.InvalidParameterException;
import org.kuali.student.common.exceptions.MissingParameterException;
import org.kuali.student.common.exceptions.OperationFailedException;
import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.lum.lu.dto.AdminOrgInfo;
import org.kuali.student.lum.lu.dto.CluCluRelationInfo;
import org.kuali.student.lum.lu.dto.CluIdentifierInfo;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.dto.CluPublicationInfo;
import org.kuali.student.lum.lu.dto.CluResultInfo;
import org.kuali.student.lum.lu.dto.FieldInfo;
import org.kuali.student.lum.lu.dto.LuCodeInfo;
import org.kuali.student.lum.lu.service.LuService;
import org.kuali.student.lum.program.dto.CredentialProgramInfo;
import org.kuali.student.lum.program.dto.assembly.ProgramAtpAssembly;
import org.kuali.student.lum.program.dto.assembly.ProgramBasicOrgAssembly;
import org.kuali.student.lum.program.dto.assembly.ProgramCodeAssembly;
import org.kuali.student.lum.program.dto.assembly.ProgramCommonAssembly;
import org.kuali.student.lum.program.dto.assembly.ProgramCredentialAssembly;
import org.kuali.student.lum.program.dto.assembly.ProgramFullOrgAssembly;
import org.kuali.student.lum.program.dto.assembly.ProgramIdentifierAssembly;
import org.kuali.student.lum.program.dto.assembly.ProgramPublicationAssembly;
import org.kuali.student.lum.program.dto.assembly.ProgramRequirementAssembly;
import org.kuali.student.lum.service.assembler.CluAssemblerUtils;

public class ProgramAssemblerUtils {

    private LuService luService;
    private CluAssemblerUtils cluAssemblerUtils;

     /**
     * Copy basic values from clu to program
     *
     * @param clu
     * @param program
     * @return
     * @throws AssemblyException
     */
     public ProgramCommonAssembly assembleBasics(CluInfo clu, ProgramCommonAssembly program) throws AssemblyException {

         if (program instanceof CredentialProgramInfo) {
             ((CredentialProgramInfo)program).setCredentialProgramType(clu.getType());
         }
         else {
             program.setType(clu.getType());
         }
         program.setState(clu.getState());
         program.setMetaInfo(clu.getMetaInfo());
         program.setAttributes(clu.getAttributes());
         program.setId(clu.getId());

         return program;
     }

    /**
     * Copy basic values from program to clu
     *
     * @param clu
     * @param program
     * @param operation
     * @return
     * @throws AssemblyException
     */
    public CluInfo disassembleBasics(CluInfo clu, ProgramCommonAssembly program) throws AssemblyException {

        if (program instanceof CredentialProgramInfo) {
            clu.setType (((CredentialProgramInfo)program).getCredentialProgramType());
        }
        else {
            clu.setType(program.getType());
        }
        clu.setId(UUIDHelper.genStringUUID(program.getId()));
        
        // Default 
        clu.setState(program.getState());
        clu.setMetaInfo(program.getMetaInfo());
        clu.setAttributes(program.getAttributes());
        return clu;

    }

    //TODO maybe this should be in CluAssemblerUtils??
    public ProgramRequirementAssembly assembleRequirements(CluInfo clu, ProgramRequirementAssembly program) throws AssemblyException {

        try {
            List<String> requirements = luService.getRelatedCluIdsByCluId(clu.getId(), ProgramAssemblerConstants.HAS_PROGRAM_REQUIREMENT);
            if (requirements != null && requirements.size() > 0) {
                program.setProgramRequirements(requirements);
            }
        }
        catch (Exception e)
        {
            throw new AssemblyException("Error assembling program requirements", e);
        }

        return program;
    }

    //TODO  maybe this should be in CluAssemblerUtils??
    public CluInfo disassembleRequirements(CluInfo clu, ProgramRequirementAssembly program, NodeOperation operation, BaseDTOAssemblyNode<?, ?> result, boolean stateChanged) throws AssemblyException {
        try {
            List<String> requirements = program.getProgramRequirements ();

            if (requirements != null && !requirements.isEmpty()) {
            	if (stateChanged){
            		addUpdateRequirementStateNodes(requirements, program.getState(), result);
            	}
            	
               	Map<String, String> currentRelations = null;

                if (!NodeOperation.CREATE.equals(operation)) {
                	currentRelations = getCluCluRelations(clu.getId(), ProgramAssemblerConstants.HAS_PROGRAM_REQUIREMENT);
                }
                
    	    	for (String requirementId : requirements){
    	    		List<BaseDTOAssemblyNode<?, ?>> reqResults = addAllRelationNodes(clu.getId(), requirementId, ProgramAssemblerConstants.HAS_PROGRAM_REQUIREMENT, operation, currentRelations);
    	            if (reqResults != null && reqResults.size()> 0) {
    	                result.getChildNodes().addAll(reqResults);
    	            }
    	    	}
    	    	
    	        if(currentRelations != null && currentRelations.size() > 0){
	    	        for (Map.Entry<String, String> entry : currentRelations.entrySet()) {
	    	            // Create a new relation with the id of the relation we want to
	    	            // delete
	    	            CluCluRelationInfo relationToDelete = new CluCluRelationInfo();
	    	            relationToDelete.setId( entry.getValue() );
	    	            BaseDTOAssemblyNode<Object, CluCluRelationInfo> relationToDeleteNode = new BaseDTOAssemblyNode<Object, CluCluRelationInfo>(
	    	                    null);
	    	            relationToDeleteNode.setNodeData(relationToDelete);
	    	            relationToDeleteNode.setOperation(NodeOperation.DELETE);
	    	            result.getChildNodes().add(relationToDeleteNode);
	    	        }
    	        }
            }
	    } catch (Exception e) {
	        throw new AssemblyException("Error while disassembling program requirements", e);
	    }
	    
        return clu;

    }

        
    /**
     * This adds nodes to update the state for the requirement clu
     * @param state
     * @param result
     * @throws OperationFailedException 
     * @throws MissingParameterException 
     * @throws InvalidParameterException 
     */
    private void addUpdateRequirementStateNodes(List<String> requirements, String state, BaseDTOAssemblyNode<?, ?> result) throws InvalidParameterException, MissingParameterException, OperationFailedException {
    	for (String requirementId:requirements){
    		try {
	    		CluInfo requirementClu = luService.getClu(requirementId);
	            requirementClu.setState(state);
	            BaseDTOAssemblyNode<Object, CluInfo> reqCluNode = new BaseDTOAssemblyNode<Object, CluInfo>(null);
	            reqCluNode.setNodeData(requirementClu);
	            reqCluNode.setOperation(NodeOperation.UPDATE);
	            result.getChildNodes().add(reqCluNode);
    		} catch (DoesNotExistException e){
    			//Don't need to update what doesn't exist
    		}
    	}
	}

	/**
     * Copy identifier values from clu to program
     *
     * @param clu
     * @param o
     * @return
     * @throws AssemblyException
     */
    public ProgramIdentifierAssembly assembleIdentifiers(CluInfo clu, ProgramIdentifierAssembly program) throws AssemblyException {

        if (clu.getOfficialIdentifier() != null) {
            if (clu.getOfficialIdentifier().getShortName() != null) {
                program.setShortTitle(clu.getOfficialIdentifier().getShortName());
            }
            if (clu.getOfficialIdentifier().getLongName() != null) {
                program.setLongTitle(clu.getOfficialIdentifier().getLongName());
            }
            if (clu.getOfficialIdentifier().getCode() != null) {
                program.setCode(clu.getOfficialIdentifier().getCode());
            }
        }
        if (clu.getAlternateIdentifiers() != null) {
            for (CluIdentifierInfo cluIdInfo : clu.getAlternateIdentifiers()) {
                String idInfoType = cluIdInfo.getType();
                if (ProgramAssemblerConstants.TRANSCRIPT.equals(idInfoType)) {
                    program.setTranscriptTitle(cluIdInfo.getShortName());
                } else if (ProgramAssemblerConstants.DIPLOMA.equals(idInfoType)) {
                    program.setDiplomaTitle(cluIdInfo.getShortName());
                }
            }
        }
        return program;
    }


    /**
     * Copy identifier values from program to clu
     *
     * @param clu
     * @param program
     * @param operation
     * @return
     * @throws AssemblyException
     */
    public CluInfo disassembleIdentifiers(CluInfo clu, ProgramIdentifierAssembly program, NodeOperation operation) throws AssemblyException {

        CluIdentifierInfo official = null != clu.getOfficialIdentifier() ? clu.getOfficialIdentifier() : new CluIdentifierInfo();

        official.setCode(program.getCode());
        official.setLongName(program.getLongTitle());
        official.setShortName(program.getShortTitle());
        official.setState(program.getState());
        // gotta be this type
        official.setType(ProgramAssemblerConstants.OFFICIAL);

        if (program instanceof CredentialProgramInfo) {
            CredentialProgramInfo cred = (CredentialProgramInfo)program;
            official.setLevel(cred.getProgramLevel());
        }

        clu.setOfficialIdentifier(official);

        //Remove any existing diploma or transcript alt identifiers
        CluIdentifierInfo diplomaInfo = null;
        CluIdentifierInfo transcriptInfo = null;
        for(Iterator<CluIdentifierInfo> iter = clu.getAlternateIdentifiers().iterator();iter.hasNext();){
            CluIdentifierInfo cluIdentifier = iter.next();
            if (ProgramAssemblerConstants.DIPLOMA.equals(cluIdentifier.getType())) {
                diplomaInfo = cluIdentifier;
                diplomaInfo.setState(program.getState());
            } else if (ProgramAssemblerConstants.TRANSCRIPT.equals(cluIdentifier.getType())) {
                transcriptInfo = cluIdentifier;
                transcriptInfo.setState(program.getState());
            }
        }

        if (program.getDiplomaTitle() != null) {
            if (diplomaInfo == null) {
                diplomaInfo = new CluIdentifierInfo();
                diplomaInfo.setState(program.getState());
                clu.getAlternateIdentifiers().add(diplomaInfo);
            }
            diplomaInfo.setCode(official.getCode());
            diplomaInfo.setShortName(program.getDiplomaTitle());
            diplomaInfo.setType(ProgramAssemblerConstants.DIPLOMA);
        }

        if (program.getTranscriptTitle() != null) {
            if (transcriptInfo == null) {
                transcriptInfo = new CluIdentifierInfo();
                transcriptInfo.setState(program.getState());
                clu.getAlternateIdentifiers().add(transcriptInfo);
            }
            transcriptInfo.setCode(official.getCode());
            transcriptInfo.setShortName(program.getTranscriptTitle());
            transcriptInfo.setType(ProgramAssemblerConstants.TRANSCRIPT);
        }
        return clu;
    }

    /**
     * Copy Lu Codes from clu to program
     *
     * @param clu
     * @param program
     * @return
     * @throws AssemblyException
     */
    public ProgramCodeAssembly assembleLuCodes(CluInfo clu, ProgramCodeAssembly program) throws AssemblyException {

        if (clu.getLuCodes() != null) {
            for (LuCodeInfo codeInfo : clu.getLuCodes()) {
                if (ProgramAssemblerConstants.CIP_2000.equals(codeInfo.getType())) {
                    program.setCip2000Code(codeInfo.getValue());
                } else if (ProgramAssemblerConstants.CIP_2010.equals(codeInfo.getType())) {
                    program.setCip2010Code(codeInfo.getValue());
                } else if (ProgramAssemblerConstants.HEGIS.equals(codeInfo.getType())) {
                    program.setHegisCode(codeInfo.getValue());
                } else if (ProgramAssemblerConstants.UNIVERSITY_CLASSIFICATION.equals(codeInfo.getType())) {
                    program.setUniversityClassification(codeInfo.getValue());
                } else if (ProgramAssemblerConstants.SELECTIVE_ENROLLMENT.equals(codeInfo.getType())) {
                    program.setSelectiveEnrollmentCode(codeInfo.getValue());
                }
            }
        }

        return program;
    }


    /**
     * Copy Lu Codes from program to clu
     *
     * @param clu
     * @param program
     * @param operation
     * @throws AssemblyException
     */
    public CluInfo disassembleLuCodes(CluInfo clu, ProgramCodeAssembly program, NodeOperation operation) throws AssemblyException {

        clu.setLuCodes(new ArrayList<LuCodeInfo>());

        addLuCodeFromProgram(ProgramAssemblerConstants.CIP_2000, program.getCip2000Code(), clu.getLuCodes());
        addLuCodeFromProgram(ProgramAssemblerConstants.CIP_2010, program.getCip2010Code(), clu.getLuCodes());
        addLuCodeFromProgram(ProgramAssemblerConstants.HEGIS, program.getHegisCode(), clu.getLuCodes());
        addLuCodeFromProgram(ProgramAssemblerConstants.UNIVERSITY_CLASSIFICATION, program.getUniversityClassification(), clu.getLuCodes());
        addLuCodeFromProgram(ProgramAssemblerConstants.SELECTIVE_ENROLLMENT, program.getSelectiveEnrollmentCode(), clu.getLuCodes());

        return clu;

    }

    /**
     * Copy AdminOrg id's from clu's AdminOrgInfo's to program
     *
     * @param clu
     * @param program
     * @return
     * @throws AssemblyException
     */
    public ProgramBasicOrgAssembly assembleBasicAdminOrgs(CluInfo clu, ProgramBasicOrgAssembly program) throws AssemblyException {

        if (clu.getAdminOrgs() != null) {
            clearProgramAdminOrgs(program);
            for (AdminOrgInfo cluOrg : clu.getAdminOrgs()) {
                if (cluOrg.getType().equals(ProgramAssemblerConstants.CURRICULUM_OVERSIGHT_DIVISION)) {
                    program.getDivisionsContentOwner().add(cluOrg.getOrgId());
                }
                else if (cluOrg.getType().equals(ProgramAssemblerConstants.STUDENT_OVERSIGHT_DIVISION)) {
                    program.getDivisionsStudentOversight().add(cluOrg.getOrgId())  ;
                }
                else if (cluOrg.getType().equals(ProgramAssemblerConstants.CURRICULUM_OVERSIGHT_UNIT)) {
                    program.getUnitsContentOwner().add(cluOrg.getOrgId())  ;
                }
                else if (cluOrg.getType().equals(ProgramAssemblerConstants.STUDENT_OVERSIGHT_UNIT)) {
                    program.getUnitsStudentOversight().add(cluOrg.getOrgId())  ;
                }
            }
        }
        return program;
    }

    public ProgramFullOrgAssembly assembleFullOrgs(CluInfo clu, ProgramFullOrgAssembly program) throws AssemblyException {

        clearFullAdminOrgs(program);
        for (AdminOrgInfo cluOrg : clu.getAdminOrgs()) {
            if (cluOrg.getType().equals(ProgramAssemblerConstants.DEPLOYMENT_DIVISION)) {
                program.getDivisionsDeployment().add(cluOrg.getOrgId())  ;
            }
            else if (cluOrg.getType().equals(ProgramAssemblerConstants.FINANCIAL_RESOURCES_DIVISION)) {
                program.getDivisionsFinancialResources().add(cluOrg.getOrgId())  ;
            }
            else if (cluOrg.getType().equals(ProgramAssemblerConstants.FINANCIAL_CONTROL_DIVISION)) {
                program.getDivisionsFinancialControl().add(cluOrg.getOrgId())  ;
            }
            else if (cluOrg.getType().equals(ProgramAssemblerConstants.DEPLOYMENT_UNIT)) {
                program.getUnitsDeployment().add(cluOrg.getOrgId())  ;
            }
            else if (cluOrg.getType().equals(ProgramAssemblerConstants.FINANCIAL_RESOURCES_UNIT)) {
                program.getUnitsFinancialResources().add(cluOrg.getOrgId())  ;
            }
            else if (cluOrg.getType().equals(ProgramAssemblerConstants.FINANCIAL_CONTROL_UNIT)) {
                program.getUnitsFinancialControl().add(cluOrg.getOrgId())  ;
            }
        }
        return program;
    }

    private void clearProgramAdminOrgs(ProgramBasicOrgAssembly program) {
        program.setDivisionsContentOwner(new ArrayList<String>());
        program.setDivisionsStudentOversight(new ArrayList<String>());
        program.setUnitsContentOwner(new ArrayList<String>());
        program.setUnitsStudentOversight(new ArrayList<String>());
    }

    private void clearFullAdminOrgs(ProgramFullOrgAssembly program) {
        program.setDivisionsDeployment(new ArrayList<String>());
        program.setDivisionsFinancialResources(new ArrayList<String>());
        program.setDivisionsFinancialControl(new ArrayList<String>());
        program.setUnitsDeployment(new ArrayList<String>());
        program.setUnitsFinancialResources(new ArrayList<String>());
        program.setUnitsFinancialControl(new ArrayList<String>());
    }

    /**
     * Copy AdminOrg values from program to clu
     *
     * @param clu
     * @param o
     * @param operation
     */
    public CluInfo disassembleAdminOrgs(CluInfo clu, ProgramBasicOrgAssembly program, NodeOperation operation){

        // clear out all old admin orgs
        clu.setAdminOrgs(new ArrayList<AdminOrgInfo>());

        newBuildAdminOrgs(clu,  program.getDivisionsContentOwner(), ProgramAssemblerConstants.CURRICULUM_OVERSIGHT_DIVISION);
        newBuildAdminOrgs(clu, program.getDivisionsStudentOversight(), ProgramAssemblerConstants.STUDENT_OVERSIGHT_DIVISION );
        newBuildAdminOrgs(clu,  program.getUnitsContentOwner(), ProgramAssemblerConstants.CURRICULUM_OVERSIGHT_UNIT);
        newBuildAdminOrgs(clu, program.getUnitsStudentOversight(), ProgramAssemblerConstants.STUDENT_OVERSIGHT_UNIT );
        if (program instanceof CredentialProgramInfo) {
            List<String> institutionOrgs = new ArrayList<String>();
            institutionOrgs.add(((CredentialProgramInfo)program).getInstitution().getOrgId());
            newBuildAdminOrgs(clu, institutionOrgs , ProgramAssemblerConstants.INSTITUTION) ;
        }
        if (program instanceof ProgramFullOrgAssembly) {
            ProgramFullOrgAssembly fullOrg = (ProgramFullOrgAssembly) program;
            newBuildAdminOrgs(clu, fullOrg.getDivisionsDeployment(), ProgramAssemblerConstants.DEPLOYMENT_DIVISION);
            newBuildAdminOrgs(clu, fullOrg.getDivisionsFinancialResources(), ProgramAssemblerConstants.FINANCIAL_RESOURCES_DIVISION);
            newBuildAdminOrgs(clu, fullOrg.getDivisionsFinancialControl(), ProgramAssemblerConstants.FINANCIAL_CONTROL_DIVISION);
            newBuildAdminOrgs(clu, fullOrg.getUnitsDeployment(), ProgramAssemblerConstants.DEPLOYMENT_UNIT);
            newBuildAdminOrgs(clu, fullOrg.getUnitsFinancialResources(), ProgramAssemblerConstants.FINANCIAL_RESOURCES_UNIT);
            newBuildAdminOrgs(clu, fullOrg.getUnitsFinancialControl(), ProgramAssemblerConstants.FINANCIAL_CONTROL_UNIT);

        }
        return clu;

    }

    private CluInfo newBuildAdminOrgs(CluInfo clu,  List<String> orgIds, String type) {

        if (null != orgIds) {
            for (String orgId : orgIds) {
                AdminOrgInfo subjectOrg = new AdminOrgInfo();
                subjectOrg.setType(type);
                subjectOrg.setOrgId(orgId);
                clu.getAdminOrgs().add(subjectOrg);
            }
        }
        return clu;
    }

    /**
     * Copy result option values from clu to program
     *
     * @param cluId
     * @param resultType
     * @return
     * @throws AssemblyException
     */
    public List<String> assembleResultOptions(String cluId) throws AssemblyException {
        List<String> resultOptions = null;
        try{
            List<CluResultInfo> cluResults = luService.getCluResultByClu(cluId);

            List<String> resultTypes = new ArrayList<String>();
            resultTypes.add(ProgramAssemblerConstants.DEGREE_RESULTS);
            resultTypes.add(ProgramAssemblerConstants.CERTIFICATE_RESULTS);

            resultOptions = cluAssemblerUtils.assembleCluResults(resultTypes, cluResults);

        } catch (DoesNotExistException e){
        } catch (Exception e) {
            throw new AssemblyException("Error getting major results", e);
        }
        return resultOptions;
    }

    /**
     * Copy atp values  from clu to program
     *
     * @param clu
     * @param program
     * @return
     * @throws AssemblyException
     */
    public ProgramAtpAssembly assembleAtps(CluInfo clu, ProgramAtpAssembly program) throws AssemblyException {

        if (clu.getExpectedFirstAtp() != null) {
            program.setStartTerm(clu.getExpectedFirstAtp());
        }
        if (clu.getLastAtp() != null) {
            program.setEndTerm(clu.getLastAtp());
        }
        if (clu.getLastAdmitAtp() != null) {
            program.setEndProgramEntryTerm(clu.getLastAdmitAtp());
        }
        return program;
    }


    /**
     * Copy atp values from Program to clu
     *
     * @param clu
     * @param program
     * @return
     * @throws AssemblyException
     */
    public CluInfo disassembleAtps(CluInfo clu, ProgramAtpAssembly program, NodeOperation operation) throws AssemblyException {

        clu.setExpectedFirstAtp(program.getStartTerm());
        clu.setLastAtp(program.getEndTerm());
        clu.setLastAdmitAtp(program.getEndProgramEntryTerm());

        return clu;

    }

    /**
     * Copy publication values from clu to program
     *
     * @param clu
     * @param program
     * @return
     * @throws AssemblyException
     */
    public ProgramPublicationAssembly assemblePublications(CluInfo clu, ProgramPublicationAssembly program) throws AssemblyException {


        if (clu.getReferenceURL() != null) {
            program.setReferenceURL(clu.getReferenceURL());
        }

        try {
            List<CluPublicationInfo> cluPublications = luService.getCluPublicationsByCluId(clu.getId());

            List<String> targets = new ArrayList<String>();

            for (CluPublicationInfo cluPublication : cluPublications) {
                if (cluPublication.getType().equals(ProgramAssemblerConstants.CATALOG)) {
                    assembleCatalogDescr(program, cluPublication);
                }
                else {
                    targets.add(cluPublication.getType());
                }
            }

            if (targets != null && !targets.isEmpty()) {
                program.setCatalogPublicationTargets(targets);
            }
        } catch (DoesNotExistException e) {
        } catch (InvalidParameterException e) {
        } catch (MissingParameterException e) {
        } catch (OperationFailedException e) {
            throw new AssemblyException("Error getting publication targets", e);
        }
        return program;
    }

    private void assembleCatalogDescr(ProgramPublicationAssembly program, CluPublicationInfo cluPublication)  {

        for (FieldInfo fieldInfo : cluPublication.getVariants()) {
            if (fieldInfo.getId().equals(ProgramAssemblerConstants.CATALOG_DESCR)) {
                RichTextInfo desc = new RichTextInfo();
                desc.setPlain(fieldInfo.getValue());
                desc.setFormatted(fieldInfo.getValue());
                program.setCatalogDescr(desc);
                break;
            }
        }
    }

     private List<BaseDTOAssemblyNode<?, ?>> disassembleCatalogDescr(ProgramPublicationAssembly program,  NodeOperation operation) throws AssemblyException {

         List<BaseDTOAssemblyNode<?, ?>> results = new ArrayList<BaseDTOAssemblyNode<?, ?>>();

         CluPublicationInfo currentPubInfo = null;

         try {

             // if not create get current catalog descr
             if (!NodeOperation.CREATE.equals(operation)) {
                 List<CluPublicationInfo> pubs = luService.getCluPublicationsByCluId(program.getId());
                 for (CluPublicationInfo pubInfo : pubs) {
                     if (pubInfo.getType().equals(ProgramAssemblerConstants.CATALOG)) {
                         currentPubInfo = pubInfo;
                     }
                 }
             }

             if (program.getCatalogDescr() != null) {
                 //  If this is a create then create new catalog descr
                 if (NodeOperation.CREATE == operation
                         || (NodeOperation.UPDATE == operation && currentPubInfo == null )) {
                     // the description does not exist, so create
                     CluPublicationInfo pubInfo = buildCluPublicationInfo(program.getId(), ProgramAssemblerConstants.CATALOG);
                     pubInfo.setState(program.getState());
                     FieldInfo variant = new FieldInfo();
                     variant.setId(ProgramAssemblerConstants.CATALOG_DESCR);
                     variant.setValue(program.getCatalogDescr() .getPlain());
                     pubInfo.getVariants().add(variant);

                     BaseDTOAssemblyNode<Object, CluPublicationInfo> pubNode = new BaseDTOAssemblyNode<Object, CluPublicationInfo>(
                             null);
                     pubNode.setNodeData(pubInfo);
                     pubNode.setOperation(NodeOperation.CREATE);

                     results.add(pubNode);
                 } else if (NodeOperation.UPDATE == operation
                         && currentPubInfo != null) {

                     CluPublicationInfo pubInfo = currentPubInfo;
                     pubInfo.setState(program.getState());
                     for (FieldInfo fieldInfo : pubInfo.getVariants()) {
                         if (fieldInfo.getId().equals(ProgramAssemblerConstants.CATALOG_DESCR)) {
                             fieldInfo.setValue(program.getCatalogDescr() .getPlain());
                             break;
                         }
                     }

                     BaseDTOAssemblyNode<Object, CluPublicationInfo> pubNode = new BaseDTOAssemblyNode<Object, CluPublicationInfo>(
                             null);
                     pubNode.setNodeData(pubInfo);
                     pubNode.setOperation(NodeOperation.UPDATE);

                     results.add(pubNode);
                     
                 }
                 else if (NodeOperation.DELETE == operation  )  {

                     deletePublicationInfo(results, currentPubInfo);
                 }
             }
         } catch (Exception e) {
             throw new AssemblyException(e);
         }
         return results;
    }

    private void deletePublicationInfo(List<BaseDTOAssemblyNode<?, ?>> results, CluPublicationInfo currentPubInfo) {
        CluPublicationInfo descrToDelete = new CluPublicationInfo();
        descrToDelete.setId(currentPubInfo.getId());
        BaseDTOAssemblyNode<Object, CluPublicationInfo> pubToDeleteNode = new BaseDTOAssemblyNode<Object, CluPublicationInfo>(
                null);
        pubToDeleteNode.setNodeData(descrToDelete);
        pubToDeleteNode.setOperation(NodeOperation.DELETE);
        results.add(pubToDeleteNode);
    }

    /**
     * Copy publication values from program to clu
     *
     * @param clu
     * @param program
     * @param operation
     * @return
     * @throws AssemblyException
     */
    public CluInfo disassemblePublications(CluInfo clu, ProgramPublicationAssembly program, NodeOperation operation, BaseDTOAssemblyNode<?, ?> result) throws AssemblyException {

        clu.setReferenceURL(program.getReferenceURL());
        clu.setState(program.getState());

        List<BaseDTOAssemblyNode<?, ?>> targetResults = disassemblePublicationTargets(program, operation);
        if (targetResults != null && targetResults.size()> 0) {
            result.getChildNodes().addAll(targetResults);
        }

        List<BaseDTOAssemblyNode<?, ?>> descrResults = disassembleCatalogDescr(program, operation) ;
        if (descrResults != null && descrResults.size()> 0) {
            result.getChildNodes().addAll(descrResults);
        }

        return clu;

    }

    /**
     * Copy credential program id value from program to clu
     *
     * @param clu
     * @param o
     * @param operation
     * @return
     * @throws AssemblyException
     */
    public List<BaseDTOAssemblyNode<?,?>>  disassembleCredentialProgram(ProgramCredentialAssembly program, NodeOperation operation, String relationType) throws AssemblyException {

        List<BaseDTOAssemblyNode<?, ?>> results = new ArrayList<BaseDTOAssemblyNode<?, ?>>();

        try {
            CluInfo credentialClu = luService.getClu(program.getCredentialProgramId());
        } catch (DoesNotExistException e) {
        } catch (Exception e) {
            throw new AssemblyException("Credential Clu does not exist for " + program.getCredentialProgramId());
        }

        Map<String, String> currentRelations = new HashMap<String, String>();

        if (!NodeOperation.CREATE.equals(operation)) {
            try {
                List<CluCluRelationInfo> cluRelations = luService.getCluCluRelationsByClu(program.getId());
                for (CluCluRelationInfo cluRelation : cluRelations) {
                    if (relationType.equals(cluRelation.getType()) ) {
                        currentRelations.put(cluRelation.getRelatedCluId(), cluRelation.getId());
                    }
                }
            } catch (DoesNotExistException e) {
            } catch (InvalidParameterException e) {
            } catch (MissingParameterException e) {
            } catch (OperationFailedException e) {
                throw new AssemblyException("Error getting related clus", e);
            }
        }


        //  If this is a create then vreate new relation
        if (NodeOperation.CREATE == operation
                || (NodeOperation.UPDATE == operation && !currentRelations.containsKey(program.getCredentialProgramId()) )) {
            // the relation does not exist, so create
            CluCluRelationInfo relation = new CluCluRelationInfo();
            relation.setCluId(program.getCredentialProgramId());
            relation.setRelatedCluId(program.getId());
            relation.setType(relationType);
            // We are hard coding this to active since relations can only be active/inactive
            // DO NOT propagate states such as DRAFT etc to the relations.
            relation.setState(DtoConstants.STATE_ACTIVE);

            BaseDTOAssemblyNode<Object, CluCluRelationInfo> relationNode = new BaseDTOAssemblyNode<Object, CluCluRelationInfo>(
                    null);
            relationNode.setNodeData(relation);
            relationNode.setOperation(NodeOperation.CREATE);

            results.add(relationNode);
        } else if (NodeOperation.UPDATE == operation
                && currentRelations.containsKey(program.getCredentialProgramId())) {
            // If the relationship already exists update it

            // remove this entry from the map so we can tell what needs to
            // be deleted at the end
            currentRelations.remove(program.getCredentialProgramId());
        } else if (NodeOperation.DELETE == operation
                && currentRelations.containsKey(program.getId()))  {
            // Delete the Format and its relation
            CluCluRelationInfo relationToDelete = new CluCluRelationInfo();
            relationToDelete.setId( currentRelations.get(program.getId()) );
            BaseDTOAssemblyNode<Object, CluCluRelationInfo> relationToDeleteNode = new BaseDTOAssemblyNode<Object, CluCluRelationInfo>(
                    null);
            relationToDeleteNode.setNodeData(relationToDelete);
            relationToDeleteNode.setOperation(NodeOperation.DELETE);
            results.add(relationToDeleteNode);

            // remove this entry from the map so we can tell what needs to
            // be deleted at the end
            currentRelations.remove(program.getId());
        }

        if(currentRelations != null && currentRelations.size() > 0){
	        for (Map.Entry<String, String> entry : currentRelations.entrySet()) {
	            // Create a new relation with the id of the relation we want to
	            // delete
	            CluCluRelationInfo relationToDelete = new CluCluRelationInfo();
	            relationToDelete.setId( entry.getValue() );
	            BaseDTOAssemblyNode<Object, CluCluRelationInfo> relationToDeleteNode = new BaseDTOAssemblyNode<Object, CluCluRelationInfo>(
	                    null);
	            relationToDeleteNode.setNodeData(relationToDelete);
	            relationToDeleteNode.setOperation(NodeOperation.DELETE);
	            results.add(relationToDeleteNode);
	        }
        }
        return results;
    }

    public List<BaseDTOAssemblyNode<?, ?>> addRelationNodes(String cluId, String relatedCluId, String relationType, NodeOperation operation)throws AssemblyException{
    	Map<String, String> currentRelations = null;
    	List<BaseDTOAssemblyNode<?, ?>> results = new ArrayList<BaseDTOAssemblyNode<?, ?>>();

        if (!NodeOperation.CREATE.equals(operation)) {
        	currentRelations = getCluCluRelations(cluId, relationType);
        }

        //  If this is a create then vreate new relation
        if (NodeOperation.CREATE == operation
                || (NodeOperation.UPDATE == operation && !currentRelations.containsKey(relatedCluId) )) {
            // the relation does not exist, so create
        	addCreateRelationNode(cluId, relatedCluId, relationType, results);
        } else if (NodeOperation.UPDATE == operation
                && currentRelations.containsKey(relatedCluId)) {
            // If the relationship already exists update it

            // remove this entry from the map so we can tell what needs to
            // be deleted at the end
            currentRelations.remove(relatedCluId);
        } else if (NodeOperation.DELETE == operation
                && currentRelations.containsKey(relatedCluId))  {
            // Delete the Format and its relation
        	addDeleteRelationNodes(currentRelations, results);

            // remove this entry from the map so we can tell what needs to
            // be deleted at the end
            currentRelations.remove(relatedCluId);
        }
        
        if(currentRelations != null && currentRelations.size() > 0){
	        for (Map.Entry<String, String> entry : currentRelations.entrySet()) {
	            // Create a new relation with the id of the relation we want to
	            // delete
	            CluCluRelationInfo relationToDelete = new CluCluRelationInfo();
	            relationToDelete.setId( entry.getValue() );
	            BaseDTOAssemblyNode<Object, CluCluRelationInfo> relationToDeleteNode = new BaseDTOAssemblyNode<Object, CluCluRelationInfo>(
	                    null);
	            relationToDeleteNode.setNodeData(relationToDelete);
	            relationToDeleteNode.setOperation(NodeOperation.DELETE);
	            results.add(relationToDeleteNode);
	        }
        }
        return results;
    }
    public List<BaseDTOAssemblyNode<?, ?>> addAllRelationNodes(String cluId, String relatedCluId, String relationType, NodeOperation operation, Map<String, String> currentRelations)throws AssemblyException{
    	List<BaseDTOAssemblyNode<?, ?>> results = new ArrayList<BaseDTOAssemblyNode<?, ?>>();

		if (NodeOperation.CREATE == operation
		        || (NodeOperation.UPDATE == operation && !currentRelations.containsKey(relatedCluId) )) {
		    // the relation does not exist, so create
			addCreateRelationNode(cluId, relatedCluId, relationType, results);
		} else if (NodeOperation.UPDATE == operation
		        && currentRelations.containsKey(relatedCluId)) {
		    // If the relationship already exists update it
		
		    // remove this entry from the map so we can tell what needs to
		    // be deleted at the end
		    currentRelations.remove(relatedCluId);
		} else if (NodeOperation.DELETE == operation
		        && currentRelations.containsKey(relatedCluId))  {
		    // Delete the Format and its relation
			addDeleteRelationNodes(currentRelations, results);
		
		    // remove this entry from the map so we can tell what needs to
		    // be deleted at the end
		    currentRelations.remove(relatedCluId);
		}
        
        return results;
    }
    public Map<String, String> getCluCluRelations(String cluId, String relationType) throws AssemblyException{
        Map<String, String> currentRelations = new HashMap<String, String>();

            try {
                List<CluCluRelationInfo> cluRelations = luService.getCluCluRelationsByClu(cluId);
               
                for (CluCluRelationInfo cluRelation : cluRelations) {
                    if (relationType.equals(cluRelation.getType())) {
                        currentRelations.put(cluRelation.getRelatedCluId(), cluRelation.getId());
                    }
                }
            } catch (DoesNotExistException e) {
            } catch (InvalidParameterException e) {
            } catch (MissingParameterException e) {
            } catch (OperationFailedException e) {
                throw new AssemblyException("Error getting related clus", e);
            }

            return currentRelations;
    }
    
    public Map<String, CluCluRelationInfo> getCluCluActiveRelations(String cluId, String relationType) throws AssemblyException{
        Map<String, CluCluRelationInfo> currentRelations = new HashMap<String, CluCluRelationInfo>();

            try {
                List<CluCluRelationInfo> cluRelations = luService.getCluCluRelationsByClu(cluId);

                for (CluCluRelationInfo cluRelation : cluRelations) {
                    if (relationType.equals(cluRelation.getType()) && (!cluRelation.getState().isEmpty() && cluRelation.getState().equalsIgnoreCase(DtoConstants.STATE_ACTIVE))) {
                        currentRelations.put(cluRelation.getRelatedCluId(), cluRelation);
                    }
                }
            } catch (DoesNotExistException e) {
            } catch (InvalidParameterException e) {
            } catch (MissingParameterException e) {
            } catch (OperationFailedException e) {
                throw new AssemblyException("Error getting related clus", e);
            }

            return currentRelations;
    }

    public void addCreateRelationNode(String cluId, String relatedCluId, String relationType, List<BaseDTOAssemblyNode<?, ?>> results){
        CluCluRelationInfo relation = new CluCluRelationInfo();
        relation.setCluId(cluId);
        relation.setRelatedCluId(relatedCluId);
        relation.setType(relationType);
        
        // Relations can only be in state Active or Inactive
        // DO NOT set state on relations to Draft, Approved, etc
        // We will default to Active
        relation.setState(DtoConstants.STATE_ACTIVE);

        BaseDTOAssemblyNode<Object, CluCluRelationInfo> relationNode = new BaseDTOAssemblyNode<Object, CluCluRelationInfo>(
                null);
        relationNode.setNodeData(relation);
        relationNode.setOperation(NodeOperation.CREATE);

        results.add(relationNode);

    }

    public void addDeleteRelationNodes(Map<String, String> currentRelations, List<BaseDTOAssemblyNode<?, ?>> results){
        for (Map.Entry<String, String> entry : currentRelations.entrySet()) {
            // Create a new relation with the id of the relation we want to
            // delete
            CluCluRelationInfo relationToDelete = new CluCluRelationInfo();
            relationToDelete.setId( entry.getValue() );
            BaseDTOAssemblyNode<Object, CluCluRelationInfo> relationToDeleteNode = new BaseDTOAssemblyNode<Object, CluCluRelationInfo>(
                    null);
            relationToDeleteNode.setNodeData(relationToDelete);
            relationToDeleteNode.setOperation(NodeOperation.DELETE);
            results.add(relationToDeleteNode);
        }
    }
    
    public void addInactiveRelationNodes(Map<String, CluCluRelationInfo> currentRelations, List<BaseDTOAssemblyNode<?, ?>> results){
        for (Map.Entry<String, CluCluRelationInfo> entry : currentRelations.entrySet()) {
            CluCluRelationInfo inactiveRelation = new CluCluRelationInfo();
            inactiveRelation = entry.getValue();
            inactiveRelation.setState(DtoConstants.STATE_INACTIVE);
            BaseDTOAssemblyNode<Object, CluCluRelationInfo> inactiveRelationNode = new BaseDTOAssemblyNode<Object, CluCluRelationInfo>(
                    null);
            inactiveRelationNode.setNodeData(inactiveRelation);
            inactiveRelationNode.setOperation(NodeOperation.UPDATE);
            results.add(inactiveRelationNode);
        }
    }

    private void addLuCodeFromProgram(String type, String value, List<LuCodeInfo> list) throws AssemblyException {

        if (value != null && !value.isEmpty()) {
            LuCodeInfo code = new LuCodeInfo();
            code.setType(type);
            code.setValue(value);
            code.setAttributes(new HashMap<String, String>());
            list.add(code);
        }
    }

    /**
     * Copy publications from program to clu
     *
     * @param clu
     * @param o
     * @param operation
     * @return
     * @throws AssemblyException
          */
    private List<BaseDTOAssemblyNode<?, ?>> disassemblePublicationTargets(ProgramPublicationAssembly program,  NodeOperation operation) throws AssemblyException {

        List<BaseDTOAssemblyNode<?, ?>> results = new ArrayList<BaseDTOAssemblyNode<?, ?>>();

        Map<String, CluPublicationInfo> currentPubs = new HashMap<String, CluPublicationInfo>();
        if (!NodeOperation.CREATE.equals(operation)) {

            // Get the current publications and put them in a map
            try {
                List<CluPublicationInfo> cluPubs = luService.getCluPublicationsByCluId(program.getId());
                for(CluPublicationInfo cluPub : cluPubs){
                    cluPub.setState(program.getState());
                    if (!cluPub.getType().equals(ProgramAssemblerConstants.CATALOG)) {
                        currentPubs.put(cluPub.getType(), cluPub);                        
                    }
                }
            } catch (DoesNotExistException e) {
            } catch (Exception e) {
                throw new AssemblyException("Error finding publications");
            }
        }

        if (program.getCatalogPublicationTargets() != null && !program.getCatalogPublicationTargets().isEmpty()) {
            for (String publicationType : program.getCatalogPublicationTargets()) {
                //  If this is a create then create new publication
                if (NodeOperation.CREATE == operation
                        || (NodeOperation.UPDATE == operation && !currentPubs.containsKey(publicationType) )) {
                    // the publication does not exist, so create
                    CluPublicationInfo pubInfo = buildCluPublicationInfo(program.getId(), publicationType);
                    
                    // Set the publication type state to match the program state.
                    pubInfo.setState(program.getState());
                    BaseDTOAssemblyNode<Object, CluPublicationInfo> pubNode = new BaseDTOAssemblyNode<Object, CluPublicationInfo>(
                            null);
                    pubNode.setNodeData(pubInfo);
                    pubNode.setOperation(NodeOperation.CREATE);

                    results.add(pubNode);
                } else if (NodeOperation.UPDATE == operation
                        && currentPubs.containsKey(publicationType)) {
                    // Update the state of an existing pub info to the
                    // program state. To do this we need to remove the publication
                    // type and create a new node with the update operation
                    CluPublicationInfo pubInfo = currentPubs.remove(publicationType);
                    pubInfo.setState(program.getState());
                    BaseDTOAssemblyNode<Object, CluPublicationInfo> pubNode = new BaseDTOAssemblyNode<Object, CluPublicationInfo>(
                               null);
                    pubNode.setNodeData(pubInfo);
                    pubNode.setOperation(NodeOperation.UPDATE);
                    results.add(pubNode);
                 } else if (NodeOperation.DELETE == operation
                        && currentPubs.containsKey(publicationType))  {

                    CluPublicationInfo pubToDelete = new CluPublicationInfo();
                    pubToDelete.setId(publicationType);
                    BaseDTOAssemblyNode<Object, CluPublicationInfo> pubToDeleteNode = new BaseDTOAssemblyNode<Object, CluPublicationInfo>(
                            null);
                    pubToDeleteNode.setNodeData(pubToDelete);
                    pubToDeleteNode.setOperation(NodeOperation.DELETE);
                    results.add(pubToDeleteNode);

                    currentPubs.remove(publicationType);
                }
            }
        }


        for (Map.Entry<String, CluPublicationInfo> entry : currentPubs.entrySet()) {
            // Create a new relation with the id of the relation we want to
            // delete
            deletePublicationInfo(results, entry.getValue());
        }

        return results;
    }

    private CluPublicationInfo buildCluPublicationInfo(String programId, String publicationType) throws AssemblyException {

        CluPublicationInfo pubInfo = new CluPublicationInfo();
        pubInfo.setType(publicationType);
        pubInfo.setCluId(programId);

        return pubInfo;
    }

    // Spring setters
    public void setLuService(LuService luService) {
        this.luService = luService;
    }

    public void setCluAssemblerUtils(CluAssemblerUtils cluAssemblerUtils) {
        this.cluAssemblerUtils = cluAssemblerUtils;
    }

    public String getCredentialProgramID(String cluId) throws AssemblyException {

        List<String> credentialProgramIDs = null;
        try {
            credentialProgramIDs = luService.getCluIdsByRelation(cluId, ProgramAssemblerConstants.HAS_MAJOR_PROGRAM);
        } catch (Exception e) {
            throw new AssemblyException(e);
        }
        // Can a Program have more than one Credential Program?
        // TODO - do we need to validate that?
        if (null == credentialProgramIDs || credentialProgramIDs.isEmpty()) {
            throw new AssemblyException("Program with ID == " + cluId + " has no Credential Program associated with it.");
        } else if (credentialProgramIDs.size() > 1) {
            throw new AssemblyException("Program with ID == " + cluId + " has more than one Credential Program associated with it.");
        }
        return credentialProgramIDs.get(0);
    }
   
}
