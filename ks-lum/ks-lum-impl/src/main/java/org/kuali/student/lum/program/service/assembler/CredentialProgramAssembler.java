package org.kuali.student.lum.program.service.assembler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.kuali.student.core.assembly.BOAssembler;
import org.kuali.student.core.assembly.BaseDTOAssemblyNode;
import org.kuali.student.core.assembly.BaseDTOAssemblyNode.NodeOperation;
import org.kuali.student.core.assembly.data.AssemblyException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.lum.lu.dto.AdminOrgInfo;
import org.kuali.student.lum.lu.dto.CluCluRelationInfo;
import org.kuali.student.lum.lu.dto.CluIdentifierInfo;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.service.LuService;
import org.kuali.student.lum.program.dto.CoreProgramInfo;
import org.kuali.student.lum.program.dto.CredentialProgramInfo;
import org.kuali.student.lum.program.dto.MajorDisciplineInfo;
import org.kuali.student.lum.program.dto.ProgramVariationInfo;
import org.kuali.student.lum.service.assembler.CluAssemblerUtils;

public class CredentialProgramAssembler implements BOAssembler<CredentialProgramInfo, CluInfo>{
	final static Logger LOG = Logger.getLogger(CredentialProgramAssembler.class);

    private ProgramAssemblerUtils programAssemblerUtils;
    private CluAssemblerUtils cluAssemblerUtils;
    private LuService luService;

	@Override
    public CredentialProgramInfo assemble(CluInfo clu,
                                          CredentialProgramInfo credentialProgram,
                                          boolean shallowBuild)
	            throws AssemblyException {

        CredentialProgramInfo cpInfo = (null != credentialProgram) ? credentialProgram : new CredentialProgramInfo();

        // Copy all the data from the clu to the credential program
        if ( ! ProgramAssemblerConstants.CREDENTIAL_PROGRAM_TYPES.contains(clu.getType()) ) {
            throw new AssemblyException("CredentialProgramAssembler.assemble() called for Clu of incorrect type: " + clu.getType());
        }
        cpInfo.setCredentialProgramType(clu.getType());
        cpInfo.setDescr(clu.getDescr());

        programAssemblerUtils.assembleBasics(clu, cpInfo);
        programAssemblerUtils.assembleIdentifiers(clu, cpInfo);
        if (null != clu.getOfficialIdentifier().getLevel()) {
            cpInfo.setProgramLevel(clu.getOfficialIdentifier().getLevel());
        }
        programAssemblerUtils.assembleAdminOrgIds(clu, cpInfo);
        for (AdminOrgInfo org : clu.getAdminOrgs()) {
            if (ProgramAssemblerConstants.INSTITUTION.equals(org.getType())) {
                cpInfo.setInstitution(org);
            }
        }
        programAssemblerUtils.assembleLuCodes(clu, cpInfo);
        programAssemblerUtils.assembleRequirements(clu, cpInfo);
        try {
            cpInfo.setCoreProgramIds(luService.getRelatedCluIdsByCluId(clu.getId(), ProgramAssemblerConstants.HAS_CORE_PROGRAM));
        } catch (Exception e) {
            throw new AssemblyException(e);
        }

        return cpInfo;
	}

    @Override
	public BaseDTOAssemblyNode<CredentialProgramInfo, CluInfo> disassemble(
			CredentialProgramInfo credential, NodeOperation operation)
			throws AssemblyException {
    	
    	BaseDTOAssemblyNode<CredentialProgramInfo, CluInfo> result = new BaseDTOAssemblyNode<CredentialProgramInfo, CluInfo>(this);
    	
    	if (credential == null) {
			// FIXME Unsure now if this is an exception or just return null or
			// empty assemblyNode
		    LOG.error("credentialProgram to disassemble is null!");
			throw new AssemblyException("credentialProgram can not be null");
		}

		CluInfo clu;
		try {
			clu = (NodeOperation.UPDATE == operation) ? luService.getClu(credential.getId()) : new CluInfo();
        } catch (Exception e) {
			throw new AssemblyException("Error getting existing learning unit during CoreProgram update", e);
        } 
        
        programAssemblerUtils.disassembleBasics(clu, credential, operation);
        if (credential.getId() == null)
        	credential.setId(clu.getId());
        programAssemblerUtils.disassembleIdentifiers(clu, credential, operation);
        programAssemblerUtils.disassembleAdminOrgs(clu, credential, operation);
        programAssemblerUtils.disassembleAtps(clu, credential, operation);    
        programAssemblerUtils.disassembleLuCodes(clu, credential, operation);
        
        if(credential.getProgramRequirements() != null && !credential.getProgramRequirements().isEmpty()) {
        	programAssemblerUtils.disassembleRequirements(clu, credential, operation, result);
        }
 
        if (credential.getResultOptions() != null) {
            disassembleResultOptions(credential, operation, result);           
        }
        
        if (credential.getLearningObjectives() != null) {
            disassembleLearningObjectives(credential, operation, result);
        }
        
        clu.setDescr(credential.getDescr());
        clu.setType(credential.getCredentialProgramType());
 
        if (credential.getCoreProgramIds() != null && credential.getCoreProgramIds().size() > 0) {
        	disassembleCorePrograms(credential, operation, result);
        }
        
		// Add the Clu to the result
		result.setNodeData(clu);
		result.setOperation(operation);
		result.setBusinessDTORef(credential);
		return result;
	}

    private void disassembleResultOptions(CredentialProgramInfo credential, NodeOperation operation, BaseDTOAssemblyNode<CredentialProgramInfo, CluInfo> result) throws AssemblyException {
        BaseDTOAssemblyNode<?, ?> resultOptions = cluAssemblerUtils.disassembleCluResults(
        		credential.getId(), credential.getState(), credential.getResultOptions(), operation, ProgramAssemblerConstants.DEGREE_RESULTS, "Result options", "Result option");
        if (resultOptions != null) {
            result.getChildNodes().add(resultOptions);           
        }
    }
    
    private void disassembleLearningObjectives(CredentialProgramInfo credential, NodeOperation operation, BaseDTOAssemblyNode<CredentialProgramInfo, CluInfo> result) throws AssemblyException {
        try {
            List<BaseDTOAssemblyNode<?, ?>> loResults = cluAssemblerUtils.disassembleLos(credential.getId(), credential.getState(),  credential.getLearningObjectives(), operation);
            if (loResults != null) {
                result.getChildNodes().addAll(loResults);
            }
        } catch (DoesNotExistException e) {
        } catch (Exception e) {
            throw new AssemblyException("Error while disassembling los", e);
        }
    }
    
    private void disassembleCorePrograms(CredentialProgramInfo credential, NodeOperation operation, BaseDTOAssemblyNode<CredentialProgramInfo, CluInfo> result) throws AssemblyException{
    	List<BaseDTOAssemblyNode<?, ?>> coreResults;
    	
    	try{    
           	Map<String, String> currentRelations = null;

            if (!NodeOperation.CREATE.equals(operation)) {
            	currentRelations = programAssemblerUtils.getCluCluRelations(credential.getId(), ProgramAssemblerConstants.HAS_CORE_PROGRAM);
            }
            
	    	for (String coreProgramId : credential.getCoreProgramIds()){
	    		coreResults = programAssemblerUtils.addAllRelationNodes(credential.getId(), coreProgramId, ProgramAssemblerConstants.HAS_CORE_PROGRAM, operation, currentRelations);
	            if (coreResults != null && coreResults.size()> 0) {
	                result.getChildNodes().addAll(coreResults);
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
        } catch (Exception e) {
            throw new AssemblyException("Error while disassembling Core programs", e);
        }
    }
    
    public void setProgramAssemblerUtils(ProgramAssemblerUtils programAssemblerUtils) {
        this.programAssemblerUtils = programAssemblerUtils;
    }

    public void setLuService(LuService luService) {
        this.luService = luService;
    }
    
    public void setCluAssemblerUtils(CluAssemblerUtils cluAssemblerUtils) {
        this.cluAssemblerUtils = cluAssemblerUtils;
    }
    
}
