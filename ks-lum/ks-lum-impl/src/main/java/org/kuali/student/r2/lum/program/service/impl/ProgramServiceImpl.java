package org.kuali.student.r2.lum.program.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.kuali.student.common.conversion.util.R1R2ConverterUtil;
import org.kuali.student.r2.lum.course.service.impl.CourseServiceUtils;
import org.kuali.student.r2.lum.program.service.assembler.CoreProgramAssembler;
import org.kuali.student.r2.lum.program.service.assembler.CredentialProgramAssembler;
import org.kuali.student.r2.lum.program.service.assembler.MajorDisciplineAssembler;
import org.kuali.student.r2.lum.program.service.assembler.ProgramAssemblerConstants;
import org.kuali.student.r1.common.assembly.BOAssembler;
import org.kuali.student.r1.common.assembly.BaseDTOAssemblyNode;
import org.kuali.student.r1.common.assembly.BaseDTOAssemblyNode.NodeOperation;
import org.kuali.student.r1.common.assembly.BusinessServiceMethodInvoker;
import org.kuali.student.r1.common.dictionary.dto.DataType;
import org.kuali.student.r1.common.dictionary.dto.ObjectStructureDefinition;
import org.kuali.student.r1.common.dictionary.service.DictionaryService;
import org.kuali.student.r2.common.dto.DtoConstants;
import org.kuali.student.r1.common.search.dto.SearchCriteriaTypeInfo;
import org.kuali.student.r1.common.search.dto.SearchRequest;
import org.kuali.student.r1.common.search.dto.SearchResult;
import org.kuali.student.r1.common.search.dto.SearchResultTypeInfo;
import org.kuali.student.r1.common.search.dto.SearchTypeInfo;
import org.kuali.student.r1.common.search.service.SearchManager;
import org.kuali.student.r1.common.validator.ServerDateParser;
import org.kuali.student.r1.common.validator.ValidatorUtils;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.document.dto.RefDocRelationInfo;
import org.kuali.student.r2.core.document.service.DocumentService;
import org.kuali.student.r1.lum.statement.typekey.ReqComponentFieldTypes;
import org.kuali.student.r2.common.assembler.AssemblyException;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.CircularReferenceException;
import org.kuali.student.r2.common.exceptions.CircularRelationshipException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DependentObjectsExistException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.IllegalVersionSequencingException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.UnsupportedActionException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.util.constants.ProgramServiceConstants;
import org.kuali.student.r2.common.validator.Validator;
import org.kuali.student.r2.common.validator.ValidatorFactory;
import org.kuali.student.r1.core.statement.dto.ReqCompFieldInfo;
import org.kuali.student.r1.core.statement.dto.ReqComponentInfo;
import org.kuali.student.r1.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.r2.core.versionmanagement.dto.VersionDisplayInfo ;
import org.kuali.student.r2.lum.clu.dto.CluCluRelationInfo;
import org.kuali.student.r2.lum.clu.dto.CluInfo;
import org.kuali.student.r2.lum.clu.dto.CluSetInfo;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.r2.lum.course.dto.LoDisplayInfo;
import org.kuali.student.r2.lum.course.infc.LoDisplay;
import org.kuali.student.r2.lum.program.dto.CoreProgramInfo;
import org.kuali.student.r2.lum.program.dto.CredentialProgramInfo;
import org.kuali.student.r2.lum.program.dto.HonorsProgramInfo;
import org.kuali.student.r2.lum.program.dto.MajorDisciplineInfo;
import org.kuali.student.r2.lum.program.dto.MinorDisciplineInfo;
import org.kuali.student.r2.lum.program.dto.ProgramRequirementInfo;
import org.kuali.student.r2.lum.program.dto.ProgramVariationInfo;
import org.kuali.student.r2.lum.program.service.ProgramService;
import org.kuali.student.r2.lum.util.constants.CluServiceConstants;
import org.springframework.transaction.annotation.Transactional;

public class ProgramServiceImpl implements ProgramService{
	final static Logger LOG = Logger.getLogger(ProgramServiceImpl.class);

    private CluService cluService;
    private ValidatorFactory validatorFactory;
    private BusinessServiceMethodInvoker programServiceMethodInvoker;
    private DictionaryService dictionaryService;
    private SearchManager searchManager;
    private MajorDisciplineAssembler majorDisciplineAssembler;
    private ProgramRequirementAssembler programRequirementAssembler;
    private CredentialProgramAssembler credentialProgramAssembler;
    private CoreProgramAssembler coreProgramAssembler;
//    private StatementService statementService;
    private AtpService atpService;
    private DocumentService documentService;
    

    @Override
    @Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
	public CredentialProgramInfo createCredentialProgram( String credentialProgramTypeKey,
                                                          CredentialProgramInfo credentialProgramInfo,
                                                          ContextInfo contextInfo) throws AlreadyExistsException,
            DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        checkForMissingParameter(credentialProgramInfo, "CredentialProgramInfo");

        // Validate
        List<ValidationResultInfo> validationResults = validateCredentialProgram("OBJECT", credentialProgramInfo,contextInfo);
        if (ValidatorUtils.hasErrors(validationResults)) {
            throw new DataValidationErrorException("Validation error!", validationResults);
        }

        try {
            return processCredentialProgramInfo(credentialProgramInfo, NodeOperation.CREATE,contextInfo);
        } catch (AssemblyException e) {
            LOG.error("Error disassembling Credential Program", e);
            throw new OperationFailedException("Error disassembling Credential Program");
        }
    }

    @Override
    @Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
    public HonorsProgramInfo createHonorsProgram(String honorsProgramTypeKey,  HonorsProgramInfo honorsProgramInfo,
                                                 ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("createHonorsProgram");
        //return null;
    }

    @Override
    @Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
    public ProgramRequirementInfo createProgramRequirement( String programRequirementTypeKey,
                                                            ProgramRequirementInfo programRequirementInfo,  ContextInfo contextInfo) throws AlreadyExistsException,
            DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(programRequirementInfo, "programRequirementInfo");

        // Validate
        List<ValidationResultInfo> validationResults = validateProgramRequirement("OBJECT", programRequirementInfo,contextInfo);
        if (ValidatorUtils.hasErrors(validationResults)) {
        	throw new DataValidationErrorException("Validation error!", validationResults);
        }

        try {
            return processProgramRequirement(programRequirementInfo, NodeOperation.CREATE,contextInfo);
        } catch (AssemblyException e) {
            LOG.error("Error disassembling Program Requirement", e);
            throw new OperationFailedException("Error disassembling Program Requirement", e);
        }
    }

    @Override
    @Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
	public MajorDisciplineInfo createMajorDiscipline( String majorDisciplineTypeKey,
                                                      MajorDisciplineInfo majorDisciplineInfo,
                                                      ContextInfo contextInfo) throws AlreadyExistsException,
            DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        checkForMissingParameter(majorDisciplineInfo, "MajorDisciplineInfo");

        // Validate
        List<ValidationResultInfo> validationResults = validateMajorDiscipline("OBJECT", majorDisciplineInfo,contextInfo);
        if (ValidatorUtils.hasErrors(validationResults)) {
            throw new DataValidationErrorException("Validation error!", validationResults);
        }

        try {
            return processMajorDisciplineInfo(majorDisciplineInfo, NodeOperation.CREATE,contextInfo);
        } catch (AssemblyException e) {
            LOG.error("Error creating Major Discipline", e);
            throw new OperationFailedException("Error creating Major Discipline");
        }
    }
    
    @Override
	@Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
	public MajorDisciplineInfo createNewMajorDisciplineVersion(String majorDisciplineId,  String versionComment,
                                                               ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, VersionMismatchException, DataValidationErrorException,ReadOnlyException {
		//step one, get the original
		VersionDisplayInfo currentVersion = cluService.getCurrentVersion(CluServiceConstants.CLU_NAMESPACE_URI, majorDisciplineId,contextInfo);
		MajorDisciplineInfo originalMajorDiscipline = getMajorDiscipline(currentVersion.getId(),contextInfo);

		//Version the Clu
		CluInfo newVersionClu = cluService.createNewCluVersion(majorDisciplineId, versionComment,contextInfo);

		try {
	        BaseDTOAssemblyNode<MajorDisciplineInfo, CluInfo> results;

	        //Integrate changes into the original. (should this just be just the id?)
			majorDisciplineAssembler.assemble(newVersionClu, originalMajorDiscipline, true,contextInfo);

			//Clear Ids from the original so it will make a copy and do other processing
			processCopy(originalMajorDiscipline, currentVersion.getId(),contextInfo);
           
            // Since we are creating a new version, update the requirements and statement
			// tree and set the state to Draft
            List<String> programRequirementIds = originalMajorDiscipline.getProgramRequirements();
            updateRequirementsState(programRequirementIds, DtoConstants.STATE_DRAFT,contextInfo);
            
			//Disassemble the new major discipline
			results =  majorDisciplineAssembler.disassemble(originalMajorDiscipline, NodeOperation.UPDATE,contextInfo);
			
			// Use the results to make the appropriate service calls here
			programServiceMethodInvoker.invokeServiceCalls(results, contextInfo);

			return results.getBusinessDTORef();
		} catch(AssemblyException e) {
			throw new OperationFailedException("Error creating new MajorDiscipline version",e);
		} catch (AlreadyExistsException e) {
			throw new OperationFailedException("Error creating new MajorDiscipline version",e);
		} catch (DependentObjectsExistException e) {
			throw new OperationFailedException("Error creating new MajorDiscipline version",e);
		} catch (CircularRelationshipException e) {
			throw new OperationFailedException("Error creating new MajorDiscipline version",e);
		} catch (UnsupportedActionException e) {
			throw new OperationFailedException("Error creating new MajorDiscipline version",e);
		} catch (CircularReferenceException e) {
			throw new OperationFailedException("Error creating new MajorDiscipline version",e);
		}
	}
    
    /**
     * This method will update the requirement state.
     * <p>
     * Note that it uses StatementUtil to update the statement tree.
     * 
     * @param programRequirementIds
     * @param newState
     * @throws Exception
     */
    private void updateRequirementsState(List<String> programRequirementIds, String newState,ContextInfo contextInfo) throws DoesNotExistException,
        InvalidParameterException, MissingParameterException,
        OperationFailedException, PermissionDeniedException,  VersionMismatchException, DataValidationErrorException  {

        /*
         * WARNING: This is an exact copy of the method from ProgramStateChangeServiceImpl.
         * We had to copy it because we cannot reference classes in the 
         * org.kuali.student.lum.program.server
         * 
         * TODO: find a place to put a shared StatementUtil 
         */
        if (programRequirementIds != null) { 
            for (String programRequirementId : programRequirementIds) {
    
                // Get program requirement from the program service
                ProgramRequirementInfo programRequirementInfo = getProgramRequirement(programRequirementId, contextInfo);
    
                // Look in the requirement for the statement tree
                StatementTreeViewInfo statementTree = R1R2ConverterUtil.convert(programRequirementInfo.getStatement(), new StatementTreeViewInfo()) ;
    
                // And recursively update the entire tree with the new state
                updateStatementTreeViewInfoState(newState, statementTree);
    
                // Update the state of the requirement object
                programRequirementInfo.setStateKey(newState);
    
                // The write the requirement back to the program service
                updateProgramRequirement(programRequirementInfo.getId(), programRequirementInfo.getTypeKey(), programRequirementInfo, contextInfo);
    
            }
        }
    }
    
    /**
     * This method will recursively set the state of all statements in the tree.
     * <p>
     * WARNING: you must call the statement service in order to update statements.
     * <p>
     * 
     * @param state is the state we should set all statements in the tree to
     * @param statementTreeViewInfo the tree of statements
     * @throws Exception
     */
    private static void updateStatementTreeViewInfoState(String state, StatementTreeViewInfo statementTreeViewInfo) {
       /*
        * WARNING: This is a copy of the method from StatementUtil.  We had to copy it because 
        * we cannot reference the common.server package from this class.
        * 
        * TODO: find a place to put a shared StatementUtil 
        */
        
        // Set the state on the statement tree itself
        statementTreeViewInfo.setState(state);
         
        // Get all the requirements components for this statement
        List<ReqComponentInfo> reqComponents = statementTreeViewInfo.getReqComponents();
        
        // Loop over requirements and set the state for each requirement
        for(Iterator<ReqComponentInfo> it = reqComponents.iterator(); it.hasNext();)
            it.next().setState(state);
        
        // Loop over each statement and set the state for each statement (recursively calling this method)
        for(Iterator<StatementTreeViewInfo> itr = statementTreeViewInfo.getStatements().iterator(); itr.hasNext();)
            updateStatementTreeViewInfoState(state, (StatementTreeViewInfo)itr.next());
    }
    
	/**
	 * Recurses through the statement tree and clears out ids so the tree can be copied.
	 * Also creates copies of clusets since they are single use
	 * 
	 * @param statementTreeView
	 * @throws OperationFailedException
	 * @see CourseServiceUtils (This is duplicate code because of the weird dependencies cause by program being in its own module)
	 */
	private void clearStatementTreeViewIdsRecursively(StatementTreeViewInfo statementTreeView,ContextInfo contextInfo) throws OperationFailedException{
		if(statementTreeView!=null){
			statementTreeView.setId(null);
			for(ReqComponentInfo reqComp:statementTreeView.getReqComponents()){
				reqComp.setId(null);
				for(ReqCompFieldInfo field:reqComp.getReqCompFields()){
					field.setId(null);
					//copy any clusets that are adhoc'd and set the field value to the new cluset
					if(ReqComponentFieldTypes.COURSE_CLUSET_KEY.getId().equals(field.getType())||
					   ReqComponentFieldTypes.PROGRAM_CLUSET_KEY.getId().equals(field.getType())||
					   ReqComponentFieldTypes.CLUSET_KEY.getId().equals(field.getType())){
						try {
							CluSetInfo cluSet = cluService.getCluSet(field.getValue(),contextInfo);
							cluSet.setId(null);
							//Clear clu ids if membership info exists, they will be re-added based on membership info 
							if (cluSet.getMembershipQuery() != null){
								cluSet.getCluIds().clear();
								cluSet.getCluSetIds().clear();
							}
							cluSet = cluService.createCluSet(cluSet.getTypeKey(), cluSet,contextInfo);
							field.setValue(cluSet.getId());
						} catch (Exception e) {
							throw new OperationFailedException("Error copying clusets.", e);
						}
					}
					
				}
			}
			//Recurse through the children
			for(StatementTreeViewInfo child: statementTreeView.getStatements()){
				clearStatementTreeViewIdsRecursively(child,contextInfo);
			}
		}
	}

	/**
     * Clears out any ids so that a subsequent call to create will copy complex structures. 
     * Also updates VersionInfo on variations to match VersionInfo on parent.
     * 
     * @param majorDiscipline
	 * @throws PermissionDeniedException 
	 * @throws OperationFailedException 
	 * @throws MissingParameterException 
	 * @throws InvalidParameterException 
	 * @throws DoesNotExistException 
	 * @throws DataValidationErrorException 
	 * @throws AlreadyExistsException 
	 * @throws VersionMismatchException 
	 * @throws CircularRelationshipException 
     */
    private void processCopy(MajorDisciplineInfo majorDiscipline,String originalId,ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, AlreadyExistsException, DataValidationErrorException, VersionMismatchException, CircularRelationshipException,ReadOnlyException {
		//Clear Terms (needs to be set on new version anyway so this forces the issue)
    	majorDiscipline.setStartTerm(null);
    	majorDiscipline.setEndTerm(null);
    	majorDiscipline.setEndProgramEntryTerm(null);
    	majorDiscipline.getAttributes().remove("endInstAdmitTerm");
    	
    	//Clear Los
		for(LoDisplayInfo lo:majorDiscipline.getLearningObjectives()){
			resetLoRecursively(lo);
		}
		//Clear OrgCoreProgram
		if(majorDiscipline.getOrgCoreProgram()!=null){
			majorDiscipline.getOrgCoreProgram().setId(null);
		
			if(majorDiscipline.getOrgCoreProgram().getLearningObjectives()!=null){
				for(LoDisplay lo:majorDiscipline.getOrgCoreProgram().getLearningObjectives()){
					resetLoRecursively((LoDisplayInfo)lo);
				}
			}
		}
		//Clear Variations
		for(ProgramVariationInfo variation:majorDiscipline.getVariations()){
			//Clear Terms (needs to be set on new version anyway so this forces the issue)
	    	variation.setStartTerm(null);
	    	variation.setEndTerm(null);
	    	variation.setEndProgramEntryTerm(null);
	    	variation.getAttributes().remove("endInstAdmitTerm");
	    	
			//Create new variation version
		   	String variationVersionIndId = variation.getVersion().getVersionIndId();
			CluInfo newVariationClu = cluService.createNewCluVersion(variationVersionIndId, "Variation version for MajorDiscipline version " + majorDiscipline.getVersion().getSequenceNumber(),contextInfo);
			
			//Create relation b/w new major discipline and new variation
			CluCluRelationInfo relation = new CluCluRelationInfo();
	        relation.setCluId(majorDiscipline.getId());
	        relation.setRelatedCluId(newVariationClu.getId());
	        relation.setTypeKey(ProgramAssemblerConstants.HAS_PROGRAM_VARIATION);
	        
	        // Relations can only be ACTIVE or Suspended
	        // We will set to ACTIVE for now
	        relation.setStateKey(DtoConstants.STATE_ACTIVE);
			cluService.createCluCluRelation(relation.getCluId(), relation.getRelatedCluId(), relation.getTypeKey(), relation,contextInfo);
	        
			//Set variation id & versionInfo to new variation clu
			variation.setId(newVariationClu.getId());
			variation.setMeta(newVariationClu.getMeta());
						
			//Set state to parent program's state
			variation.setStateKey(majorDiscipline.getStateKey());
			//Clear Los
			for(LoDisplay lo:variation.getLearningObjectives()){
				resetLoRecursively((LoDisplayInfo)lo);
			}
			//Copy Requirements for variation
			copyProgramRequirements(variation.getProgramRequirements(),majorDiscipline.getStateKey(),contextInfo);
		}
		
		//Copy requirements for majorDiscipline
		copyProgramRequirements(majorDiscipline.getProgramRequirements(),majorDiscipline.getStateKey(),contextInfo);

		//Copy documents(create new relations to the new version)
		List<RefDocRelationInfo> docRelations = documentService.getRefDocRelationsByRef("kuali.org.RefObjectType.ProposalInfo", originalId, contextInfo);
		if(docRelations!=null){
			for(RefDocRelationInfo docRelation:docRelations){
				docRelation.setId(null);
				docRelation.setRefObjectId(majorDiscipline.getId());
				documentService.createRefDocRelation("kuali.org.RefObjectType.ProposalInfo", majorDiscipline.getId(), docRelation.getDocumentId(), docRelation.getTypeKey(), docRelation, contextInfo);
			}
		}
	}

	private void processCopy(CredentialProgramInfo originaCredentialProgram,
			String originalId,ContextInfo contextInfo) throws OperationFailedException, AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, PermissionDeniedException, DoesNotExistException,ReadOnlyException {
		//Clear Terms (needs to be set on new version anyway so this forces the issue)
		originaCredentialProgram.setStartTerm(null);
		originaCredentialProgram.setEndTerm(null);
		originaCredentialProgram.setEndProgramEntryTerm(null);
		
		//Clear Los
		if (originaCredentialProgram.getLearningObjectives() != null){
			for(LoDisplay lo:originaCredentialProgram.getLearningObjectives()){
				resetLoRecursively((LoDisplayInfo)lo);
			}
		}

		//Copy requirements for majorDiscipline
		copyProgramRequirements(originaCredentialProgram.getProgramRequirements(),originaCredentialProgram.getStateKey(),contextInfo);

		//Copy documents(create new relations to the new version)
		List<RefDocRelationInfo> docRelations = documentService.getRefDocRelationsByRef("kuali.org.RefObjectType.ProposalInfo", originalId, contextInfo);
		if(docRelations!=null){
			for(RefDocRelationInfo docRelation:docRelations){
				docRelation.setId(null);
				docRelation.setRefObjectId(originaCredentialProgram.getId());
				documentService.createRefDocRelation("kuali.org.RefObjectType.ProposalInfo", originaCredentialProgram.getId(), docRelation.getDocumentId(), docRelation.getTypeKey(), docRelation, contextInfo);
			}
		}
	}
    
    private void processCopy(CoreProgramInfo originalCoreProgram, String originalId,ContextInfo contextInfo) throws OperationFailedException, AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, PermissionDeniedException, DoesNotExistException, ReadOnlyException {
		//Clear Terms (needs to be set on new version anyway so this forces the issue)
    	originalCoreProgram.setStartTerm(null);
    	originalCoreProgram.setEndTerm(null);
    	originalCoreProgram.setEndProgramEntryTerm(null);
		
    	//Clear Los
		for(LoDisplay lo:originalCoreProgram.getLearningObjectives()){
			resetLoRecursively((LoDisplayInfo)lo);
		}
		//Copy requirements for majorDiscipline
		copyProgramRequirements(originalCoreProgram.getProgramRequirements(),originalCoreProgram.getStateKey(),contextInfo);

		//Copy documents(create new relations to the new version)
		List<RefDocRelationInfo> docRelations = documentService.getRefDocRelationsByRef("kuali.org.RefObjectType.ProposalInfo", originalId, contextInfo);
		if(docRelations!=null){
			for(RefDocRelationInfo docRelation:docRelations){
				docRelation.setId(null);
				docRelation.setRefObjectId(originalCoreProgram.getId());
				documentService.createRefDocRelation("kuali.org.RefObjectType.ProposalInfo", originalCoreProgram.getId(), docRelation.getDocumentId(), docRelation.getTypeKey(), docRelation, contextInfo);
			}
		}
	}
    
    /**
     * Copy requirements (these exist external to the program save process and are referenced by id)
     * @param originalProgramRequirementIds
     * @param state
     * @throws OperationFailedException
     * @throws AlreadyExistsException
     * @throws DataValidationErrorException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws PermissionDeniedException
     * @throws DoesNotExistException
     */
    private void copyProgramRequirements(List<String> originalProgramRequirementIds,String state,ContextInfo contextInfo) throws OperationFailedException, AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, PermissionDeniedException, DoesNotExistException{
		if (originalProgramRequirementIds == null) {
		    return;
		}
        
        //Pull out the current requirement ids to be replaced by the ids of the new copies 
		List<String> programRequirementIds = new ArrayList<String>(originalProgramRequirementIds);
		originalProgramRequirementIds.clear();
		
		for(String programRequirementId:programRequirementIds){
			//Grab the original 
			ProgramRequirementInfo programRequirementInfo = getProgramRequirement(programRequirementId, contextInfo);
			//Clear the id
			programRequirementInfo.setId(null);
			
			programRequirementInfo.setStateKey(state);
			//Clear statement tree ids
			clearStatementTreeViewIdsRecursively(R1R2ConverterUtil.convert(programRequirementInfo.getStatement(), new StatementTreeViewInfo()),contextInfo);
			//Clear learning objectives
			for(LoDisplayInfo lo:programRequirementInfo.getLearningObjectives()){
				resetLoRecursively(lo);
			}
			//Create the new copy
			ProgramRequirementInfo createdProgramRequirement = createProgramRequirement(programRequirementInfo.getTypeKey(),programRequirementInfo,contextInfo);
			//add the copy's id back to the majorDiscipline's list of requirements
			originalProgramRequirementIds.add(createdProgramRequirement.getId());
		}
    }
    
	/**
	 * Recursively clears out the ids in a Lo and in its child Los
	 * @param lo
	 */
	private void resetLoRecursively(LoDisplayInfo lo){
		lo.getLoInfo().setId(null);
		for(LoDisplayInfo nestedLo:lo.getLoDisplayInfoList()){
			resetLoRecursively(nestedLo);
		}
	}

	@Override
	@Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
	public StatusInfo setCurrentMajorDisciplineVersion(
			String majorDisciplineId, Date currentVersionStart,ContextInfo contextInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, IllegalVersionSequencingException,
			OperationFailedException, PermissionDeniedException, DataValidationErrorException {
		StatusInfo status = cluService.setCurrentCluVersion(majorDisciplineId, currentVersionStart,contextInfo);
		
		//Update the variations to be current as well
		List<ProgramVariationInfo> variationList = getVariationsByMajorDisciplineId(majorDisciplineId,contextInfo);
		for (ProgramVariationInfo variationInfo:variationList){
			String variationId = variationInfo.getId();
			//If null set to current (non-null value means version is first and is already current)
			
			if (variationInfo.getVersion().getCurrentVersionStart() == null){
				cluService.setCurrentCluVersion(variationId, currentVersionStart,contextInfo);
			}
		}
		
		return status;
	}

	@Override
    @Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
	public MinorDisciplineInfo createMinorDiscipline( String minorDisciplineTypeKey,
                                                      MinorDisciplineInfo minorDisciplineInfo,  ContextInfo contextInfo) throws AlreadyExistsException,
            DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("createMinorDiscipline");
        //return null;
    }

    @Override
    @Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
	public StatusInfo deleteCredentialProgram(String credentialProgramId,  ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

//        try {
//        	CredentialProgramInfo credentialProgram = getCredentialProgram(credentialProgramId);
//
//            processCredentialProgramInfo(credentialProgram, NodeOperation.DELETE);
//
//            return getStatus();
//
//        } catch (AssemblyException e) {
//            LOG.error("Error disassembling CredentialProgram", e);
//            throw new OperationFailedException("Error disassembling CredentialProgram");
//        }
    	throw new OperationFailedException("Deletion of CredentialProgram is not supported."); 
    }

    @Override
    @Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
	public StatusInfo deleteHonorsProgram( String honorsProgramId, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("deleteHonorsProgram");
        //return null;
    }

    @Override
    @Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
	public StatusInfo deleteMajorDiscipline(String majorDisciplineId,ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        try {
            MajorDisciplineInfo majorDiscipline = getMajorDiscipline(majorDisciplineId,contextInfo);

            processMajorDisciplineInfo(majorDiscipline, NodeOperation.DELETE,contextInfo);

            return getStatus();

        } catch (AssemblyException e) {
            LOG.error("Error disassembling MajorDiscipline", e);
            throw new OperationFailedException("Error disassembling MajorDiscipline");
        }
    }

    @Override
    @Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
	public StatusInfo deleteMinorDiscipline(String minorDisciplineId,ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        throw new UnsupportedOperationException("deleteMinorDiscipline");
        //return null;
    }

    @Override
    @Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
	public StatusInfo deleteProgramRequirement(String programRequirementId,ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
    	checkForMissingParameter(programRequirementId, "programRequirementId");
        try {
        	ProgramRequirementInfo programRequirement = getProgramRequirement(programRequirementId, contextInfo);

        	processProgramRequirement(programRequirement, NodeOperation.DELETE, contextInfo);

            return getStatus();

        } catch (AssemblyException e) {
            LOG.error("Error disassembling MajorDiscipline", e);
            throw new OperationFailedException("Error disassembling ProgramRequirement", e);
        }

    }

    @Override
    @Transactional(readOnly=true)
    public CredentialProgramInfo getCredentialProgram(String credentialProgramId,ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {

    	CredentialProgramInfo credentialProgramInfo = null;

        try {
            CluInfo clu = cluService.getClu(credentialProgramId,contextInfo);

            if ( ! ProgramAssemblerConstants.CREDENTIAL_PROGRAM_TYPES.contains(clu.getTypeKey()) ) {
                throw new DoesNotExistException("Specified CLU is not a Credential Program");
            }
            ;
            credentialProgramInfo = credentialProgramAssembler.assemble(clu, null, false,contextInfo);
        } catch (AssemblyException e) {
            LOG.error("Error assembling CredentialProgram", e);
            throw new OperationFailedException("Error assembling CredentialProgram");
        }
        return credentialProgramInfo;

		// comment out the above, and uncomment below to get auto-generated data
        // (and vice-versa)
//		try {
//			return new CredentialProgramDataGenerator(ProgramAssemblerConstants.BACCALAUREATE_PROGRAM).getCPTestData();
//		} catch (Exception e) {
//			return null;
//		}
    }

//    @Override
//    public LuTypeInfo getCredentialProgramType(String credentialProgramTypeKey, ContextInfo contextInfo)
//            throws DoesNotExistException, InvalidParameterException,
//            MissingParameterException, OperationFailedException {
//        // TODO Auto-generated method stub
//        return null;
//    }

//    @Override
//    public List<LuTypeInfo> getCredentialProgramTypes(ContextInfo contextInfo)
//            throws OperationFailedException {
//        // TODO Auto-generated method stub
//        return null;
//    }

//    @Override
//    public List<String> getHonorsByCredentialProgramType(String programType, ContextInfo contextInfo)
//            throws DoesNotExistException, InvalidParameterException,
//            MissingParameterException, OperationFailedException {
//        // TODO Auto-generated method stub
//        return null;
//    }

    @Override
    public HonorsProgramInfo getHonorsProgram(String honorsProgramId, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("getHonorsProgram");
        //return null;
    }

    @Override
    @Transactional(readOnly=true)
    public MajorDisciplineInfo getMajorDiscipline(String majorDisciplineId,ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {


        MajorDisciplineInfo majorDiscipline = null;

        try {
            CluInfo clu = cluService.getClu(majorDisciplineId,contextInfo);
            if ( ! ProgramAssemblerConstants.MAJOR_DISCIPLINE.equals(clu.getTypeKey()) ) {
                throw new DoesNotExistException("Specified CLU is not a Major Discipline");
            }
            majorDiscipline = majorDisciplineAssembler.assemble(clu, null, false,contextInfo);
        } catch (AssemblyException e) {
            LOG.error("Error assembling MajorDiscipline", e);
            throw new OperationFailedException("Error assembling MajorDiscipline");
        }
        return majorDiscipline;
		// comment out the above, and uncomment below to get auto-generated data
        // (and vice-versa)
//		try {
//			return new MajorDisciplineDataGenerator().getMajorDisciplineInfoTestData();
//		} catch (Exception e) {
//			return null;
//		}
	}

//	@Override
//	public List<String> getMajorIdsByCredentialProgramType(String programType, ContextInfo contextInfo)
//			throws DoesNotExistException, InvalidParameterException,
//			MissingParameterException, OperationFailedException {
//		// TODO Auto-generated method stub
//		return null;
//	}

	@Override
	public MinorDisciplineInfo getMinorDiscipline(String minorDisciplineId,ContextInfo contextInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
	    throw new UnsupportedOperationException("getMinorDiscipline");
		//return null;
	}

	@Override
	public List<String> getMinorsByCredentialProgramType(String programType,ContextInfo contextInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
	    throw new UnsupportedOperationException("getMinorsByCredentialProgramType");
		//return null;
	}

	@Override
    @Transactional(readOnly=true)
	public ProgramRequirementInfo getProgramRequirement(String programRequirementId, ContextInfo contextInfo) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException

    {

		checkForMissingParameter(programRequirementId, "programRequirementId");

		CluInfo clu = cluService.getClu(programRequirementId,contextInfo);
		if (!ProgramAssemblerConstants.PROGRAM_REQUIREMENT.equals(clu.getTypeKey())) {
			throw new DoesNotExistException("Specified CLU is not a Program Requirement");
		}
		try {
			ProgramRequirementInfo progReqInfo = programRequirementAssembler.assemble(clu, null, false, contextInfo);
			return progReqInfo;
		} catch (AssemblyException e) {
            LOG.error("Error assembling program requirement", e);
            throw new OperationFailedException("Error assembling program requirement: " + e.getMessage(), e);
		}
	}

	@Override
    @Transactional(readOnly=true)
	public List<ProgramVariationInfo> getVariationsByMajorDisciplineId(
            String majorDisciplineId, ContextInfo contextInfo) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
    	List<ProgramVariationInfo> pvInfos = new ArrayList<ProgramVariationInfo>();

    	try {
    			List<CluInfo> clus = cluService.getRelatedClusByCluAndRelationType(majorDisciplineId, ProgramAssemblerConstants.HAS_PROGRAM_VARIATION,contextInfo);

		        if(clus != null && clus.size() > 0){
		        	for(CluInfo clu : clus){
		        		ProgramVariationInfo pvInfo = majorDisciplineAssembler.getProgramVariationAssembler().assemble(clu, null, false,contextInfo);
		        		if(pvInfo != null){
		        			pvInfos.add(pvInfo);
		        		}
		        	}
		        }
		    } catch (AssemblyException e) {
		        LOG.error("Error assembling ProgramVariation", e);
		        throw new OperationFailedException("Error assembling ProgramVariation");
		    } catch (PermissionDeniedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

        return pvInfos;
    }

    @Override
    @Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
	public CredentialProgramInfo updateCredentialProgram(
	        String credentialProgramId, CredentialProgramInfo credentialProgramInfo, ContextInfo contextInfo)
            throws DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            VersionMismatchException, OperationFailedException,
            PermissionDeniedException {

        checkForMissingParameter(credentialProgramInfo, "CredentialProgramInfo");

        // Validate
        List<ValidationResultInfo> validationResults = validateCredentialProgram("OBJECT", credentialProgramInfo,contextInfo);
        if (ValidatorUtils.hasErrors(validationResults)) {
            throw new DataValidationErrorException("Validation error!", validationResults);
        }

        try {

            return processCredentialProgramInfo(credentialProgramInfo, NodeOperation.UPDATE,contextInfo);

        } catch (AssemblyException e) {
            LOG.error("Error disassembling Credential Program", e);
            throw new OperationFailedException("Error disassembling Credential Program");
        }
    }

//    @Override
//    @Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
//	public HonorsProgramInfo updateHonorsProgram(
//            HonorsProgramInfo honorsProgramInfo, ContextInfo contextInfo)
//            throws DataValidationErrorException, DoesNotExistException,
//            InvalidParameterException, MissingParameterException,
//            VersionMismatchException, OperationFailedException,
//            PermissionDeniedException {
//        // TODO Auto-generated method stub
//        return null;
//    }

    @Override
    @Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
	public MajorDisciplineInfo updateMajorDiscipline(String majorDisciplineId,
            MajorDisciplineInfo majorDisciplineInfo, ContextInfo contextInfo)
            throws DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            VersionMismatchException, OperationFailedException,
            PermissionDeniedException {

        checkForMissingParameter(majorDisciplineInfo, "MajorDisciplineInfo");

        // Validate
        List<ValidationResultInfo> validationResults = validateMajorDiscipline("OBJECT", majorDisciplineInfo,contextInfo);
        if (ValidatorUtils.hasErrors(validationResults)) {
            throw new DataValidationErrorException("Validation error!", validationResults);
        }

        try {

            return processMajorDisciplineInfo(majorDisciplineInfo, NodeOperation.UPDATE,contextInfo);

        } catch (AssemblyException e) {
            LOG.error("Error disassembling majorDiscipline", e);
            throw new OperationFailedException("Error disassembling majorDiscipline");
        }
    }

    @Override
    @Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
	public MinorDisciplineInfo updateMinorDiscipline(String minorDisciplineId, String minorDisciplineTypeKey, MinorDisciplineInfo minorDisciplineInfo, ContextInfo contextInfo)
            throws DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            VersionMismatchException, OperationFailedException,
            PermissionDeniedException {
        throw new UnsupportedOperationException("updateMinorDiscipline");
        //return null;
    }

    @Override
    @Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
	public ProgramRequirementInfo updateProgramRequirement(String programRequirementId, String programRequirementTypeKey, 
            ProgramRequirementInfo programRequirementInfo, ContextInfo contextInfo)
            throws DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            VersionMismatchException, OperationFailedException,
            PermissionDeniedException {
    	checkForMissingParameter(programRequirementInfo, "programRequirementInfo");
        // Validate
        List<ValidationResultInfo> validationResults = validateProgramRequirement("OBJECT", programRequirementInfo,contextInfo);
        if (ValidatorUtils.hasErrors(validationResults)) {
        	throw new DataValidationErrorException("Validation error!", validationResults);
        }

        try {
			return processProgramRequirement(programRequirementInfo, NodeOperation.UPDATE,contextInfo);
		} catch (AssemblyException e) {
			throw new OperationFailedException("Unable to update ProgramRequirement", e);
		}
    }

    @Override
    public List<ValidationResultInfo> validateCredentialProgram(
            String validationType, CredentialProgramInfo credentialProgramInfo,ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException, OperationFailedException {

        List<ValidationResultInfo> validationResults = new ArrayList<ValidationResultInfo>();
//        if ( ! ProgramAssemblerConstants.DRAFT.equals(credentialProgramInfo.getState()) ) {
            ObjectStructureDefinition objStructure = this.getObjectStructure(CredentialProgramInfo.class.getName());
            Validator validator = validatorFactory.getValidator();
            validationResults.addAll(validator.validateObject(credentialProgramInfo, objStructure,contextInfo));
//        }

        return validationResults;
    }

    @Override
    public List<ValidationResultInfo> validateHonorsProgram(
            String validationType, HonorsProgramInfo honorsProgramInfo,ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new UnsupportedOperationException("validateHonorsProgram");
        //return null;
    }

    @Override
    public List<ValidationResultInfo> validateMajorDiscipline(
            String validationType, MajorDisciplineInfo majorDisciplineInfo,ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException, OperationFailedException,PermissionDeniedException {

        List<ValidationResultInfo> validationResults = new ArrayList<ValidationResultInfo>();
//        if ( ! ProgramAssemblerConstants.DRAFT.equalsIgnoreCase(majorDisciplineInfo.getState()) ) {
            ObjectStructureDefinition objStructure = this.getObjectStructure(MajorDisciplineInfo.class.getName());
            Validator validator = validatorFactory.getValidator();
            validationResults.addAll(validator.validateObject(majorDisciplineInfo, objStructure,contextInfo));
//        }
        validateMajorDisciplineAtps(majorDisciplineInfo,validationResults,contextInfo);
        return validationResults;
    }

    @Override
    public List<ValidationResultInfo> validateMinorDiscipline(
            String validationType, MinorDisciplineInfo minorDisciplineInfo,ContextInfo contextInfo   )
            throws InvalidParameterException,
            MissingParameterException, OperationFailedException {
        throw new UnsupportedOperationException("validateMinorDiscipline");
        //return null ;
    }

    @Override
    public List<ValidationResultInfo> validateProgramRequirement(
            String validationType, ProgramRequirementInfo programRequirementInfo,ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException, OperationFailedException {

        ObjectStructureDefinition objStructure = this.getObjectStructure(ProgramRequirementInfo.class.getName());
        Validator validator = validatorFactory.getValidator();
        List<ValidationResultInfo> validationResults = validator.validateObject(programRequirementInfo, objStructure,contextInfo);

        return validationResults;
    }


    @Override
    public ObjectStructureDefinition getObjectStructure(String objectTypeKey) {
        return dictionaryService.getObjectStructure(objectTypeKey);
    }


   @Override
    public List<String> getObjectTypes() {
        return dictionaryService.getObjectTypes();
    }

    @Override
    public SearchCriteriaTypeInfo getSearchCriteriaType(
            String searchCriteriaTypeKey) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException {
        throw new UnsupportedOperationException("getSearchCriteriaType");
        //return null;
    }

    @Override
    public List<SearchCriteriaTypeInfo> getSearchCriteriaTypes()
            throws OperationFailedException {
        throw new UnsupportedOperationException("getSearchCriteriaTypes");
        //return null;
    }

    @Override
    public SearchResultTypeInfo getSearchResultType(String searchResultTypeKey)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException {
        throw new UnsupportedOperationException("getSearchResultType");
        //return null;
    }

    @Override
    public List<SearchResultTypeInfo> getSearchResultTypes()
            throws OperationFailedException {
        throw new UnsupportedOperationException("getSearchResultTypes");
        //return null;
    }

    @Override
    public SearchTypeInfo getSearchType(String searchTypeKey)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException {
        throw new UnsupportedOperationException("getSearchType");
        //return null;
    }

    @Override
    public List<SearchTypeInfo> getSearchTypes()
            throws OperationFailedException {
        throw new UnsupportedOperationException("getSearchTypes");
        //return null;
    }

    @Override
    public List<SearchTypeInfo> getSearchTypesByCriteria(
            String searchCriteriaTypeKey) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException {
        throw new UnsupportedOperationException("getSearchTypesByCriteria");
        //return null;
    }

    @Override
    public List<SearchTypeInfo> getSearchTypesByResult(
            String searchResultTypeKeyo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException{
        throw new UnsupportedOperationException("getSearchTypesByResult");
        //return null;
    }

//    @Override
//    public SearchResult search(SearchRequest searchRequest, SearchableDao dao) throws MissingParameterException {
//        // TODO Auto-generated method stub
//        return null;
//    }

    /**
     * Check for missing parameter and throw localized exception if missing
     *
     * @param param
     * @param paramName
     * @throws MissingParameterException
     */
    private void checkForMissingParameter(Object param, String paramName)
            throws MissingParameterException {
        if (param == null) {
            throw new MissingParameterException(paramName + " can not be null");
        }
    }

    // TODO - when CRUD for a second ProgramInfo is implemented, pull common code up from its process*() and this

    private MajorDisciplineInfo processMajorDisciplineInfo(MajorDisciplineInfo majorDisciplineInfo, NodeOperation operation, ContextInfo contextInfo) throws AssemblyException {

        BaseDTOAssemblyNode<MajorDisciplineInfo, CluInfo> results = majorDisciplineAssembler.disassemble(majorDisciplineInfo, operation, contextInfo);
        invokeServiceCalls(results, contextInfo);
        return results.getBusinessDTORef();
    }

    private CredentialProgramInfo processCredentialProgramInfo(CredentialProgramInfo credentialProgramInfo, NodeOperation operation, ContextInfo contextInfo) throws AssemblyException {

        BaseDTOAssemblyNode<CredentialProgramInfo, CluInfo> results = credentialProgramAssembler.disassemble(credentialProgramInfo, operation, contextInfo);
        invokeServiceCalls(results, contextInfo);
        return results.getBusinessDTORef();
    }

    private ProgramRequirementInfo processProgramRequirement(ProgramRequirementInfo programRequirementInfo, NodeOperation operation, ContextInfo contextInfo) throws AssemblyException {
    	BOAssembler<ProgramRequirementInfo, CluInfo> passAlong;
        BaseDTOAssemblyNode<ProgramRequirementInfo, CluInfo> results = programRequirementAssembler.disassemble(programRequirementInfo, operation, contextInfo);
        invokeServiceCalls(results, contextInfo);
        return results.getBusinessDTORef();
    }

	private void invokeServiceCalls(BaseDTOAssemblyNode<?, CluInfo> results, ContextInfo contextInfo) throws AssemblyException{
        // Use the results to make the appropriate service calls here
        try {
            programServiceMethodInvoker.invokeServiceCalls(results, contextInfo);
        } catch (AssemblyException e) {
        	throw e;
        } catch (Exception e) {
            throw new AssemblyException(e);
        }
    }

    //Spring setters. Used by spring container to inject corresponding dependencies.

    public void setCluService(CluService cluService) {
        this.cluService = cluService;
    }

    public CluService getCluService() {
		return cluService;
	}

	public void setDictionaryService(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    public DictionaryService getDictionaryService() {
		return dictionaryService;
	}

    public void setSearchManager(SearchManager searchManager) {
        this.searchManager = searchManager;
    }
    
	public SearchManager getSearchManager() {
		return searchManager;
	}

	public void setMajorDisciplineAssembler(MajorDisciplineAssembler majorDisciplineAssembler) {
        this.majorDisciplineAssembler = majorDisciplineAssembler;
    }

	public MajorDisciplineAssembler getMajorDisciplineAssembler() {
		return majorDisciplineAssembler;
	}

	public void setCredentialProgramAssembler(
			CredentialProgramAssembler credentialProgramAssembler) {
		this.credentialProgramAssembler = credentialProgramAssembler;
	}

	public CredentialProgramAssembler getCredentialProgramAssembler() {
		return credentialProgramAssembler;
	}

	public void setProgramRequirementAssembler(ProgramRequirementAssembler programRequirementAssembler) {
        this.programRequirementAssembler = programRequirementAssembler;
    }

    public ProgramRequirementAssembler getProgramRequirementAssembler() {
		return programRequirementAssembler;
	}

	public void setProgramServiceMethodInvoker(BusinessServiceMethodInvoker serviceMethodInvoker) {
        this.programServiceMethodInvoker = serviceMethodInvoker;
    }

    public BusinessServiceMethodInvoker getProgramServiceMethodInvoker() {
		return programServiceMethodInvoker;
	}

	public void setValidatorFactory(ValidatorFactory validatorFactory) {
        this.validatorFactory = validatorFactory;
    }

	public ValidatorFactory getValidatorFactory() {
		return validatorFactory;
	}
	
	public void setCoreProgramAssembler(CoreProgramAssembler coreProgramAssembler) {
		this.coreProgramAssembler = coreProgramAssembler;
	}

	public CoreProgramAssembler getCoreProgramAssembler() {
		return coreProgramAssembler;
	}

	private StatusInfo getStatus(){
        StatusInfo status = new StatusInfo();
        status.setSuccess(true);
        return status;
	}

    private CoreProgramInfo processCoreProgramInfo(CoreProgramInfo coreProgramInfo, NodeOperation operation,ContextInfo contextInfo) throws AssemblyException, InvalidParameterException, MissingParameterException, DoesNotExistException, OperationFailedException, PermissionDeniedException {
        BaseDTOAssemblyNode<CoreProgramInfo, CluInfo> results = coreProgramAssembler.disassemble(coreProgramInfo, operation, contextInfo);
        invokeServiceCalls(results, contextInfo);
        return results.getBusinessDTORef();
    }

    @Override
    @Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
	public CoreProgramInfo createCoreProgram(String coreProgramTypeKey, CoreProgramInfo coreProgramInfo,ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
        checkForMissingParameter(coreProgramInfo, "CoreProgramInfo");
        
        // Validate
        List<ValidationResultInfo> validationResults = validateCoreProgram("OBJECT", coreProgramInfo, contextInfo );
        if (ValidatorUtils.hasErrors(validationResults)) {
            throw new DataValidationErrorException("Validation error!", validationResults);
        }

        try {
            return processCoreProgramInfo(coreProgramInfo, NodeOperation.CREATE,contextInfo);
        } catch (AssemblyException e) {
            LOG.error("Error disassembling CoreProgram", e);
            throw new OperationFailedException("Error disassembling CoreProgram");
        }
    }

	@Override
	@Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
	public CoreProgramInfo createNewCoreProgramVersion(
			String coreProgramId, String versionComment,ContextInfo contextInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException, VersionMismatchException,
			DataValidationErrorException, ReadOnlyException {
		//step one, get the original
		VersionDisplayInfo currentVersion = cluService.getCurrentVersion(CluServiceConstants.CLU_NAMESPACE_URI, coreProgramId,contextInfo);
		CoreProgramInfo originalCoreProgram = getCoreProgram(currentVersion.getId(),contextInfo);

		//Version the Clu
		CluInfo newVersionClu = cluService.createNewCluVersion(coreProgramId, versionComment,contextInfo);

		try {
	        BaseDTOAssemblyNode<CoreProgramInfo, CluInfo> results;

	        //Integrate changes into the original. (should this just be just the id?)
			coreProgramAssembler.assemble(newVersionClu, originalCoreProgram, true, contextInfo);
			
			//Clear Ids from the original so it will make a copy and do other processing
			processCopy(originalCoreProgram, currentVersion.getId(),contextInfo);

			//Disassemble the new
			results = coreProgramAssembler.disassemble(originalCoreProgram, NodeOperation.UPDATE, contextInfo);

			// Use the results to make the appropriate service calls here
			programServiceMethodInvoker.invokeServiceCalls(results, contextInfo);

			return results.getBusinessDTORef();
		} catch(AssemblyException e) {
			throw new OperationFailedException("Error creating new MajorDiscipline version",e);
		} catch (AlreadyExistsException e) {
			throw new OperationFailedException("Error creating new MajorDiscipline version",e);
		} catch (DependentObjectsExistException e) {
			throw new OperationFailedException("Error creating new MajorDiscipline version",e);
		} catch (CircularRelationshipException e) {
			throw new OperationFailedException("Error creating new MajorDiscipline version",e);
		} catch (UnsupportedActionException e) {
			throw new OperationFailedException("Error creating new MajorDiscipline version",e);
		} catch (CircularReferenceException e) {
			throw new OperationFailedException("Error creating new MajorDiscipline version",e);
		}
	}
    


	@Override
    @Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
	public StatusInfo deleteCoreProgram(String coreProgramId, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException{
//        try {
//        	CoreProgramInfo coreProgramInfo = getCoreProgram(coreProgramId);
//
//            processCoreProgramInfo(coreProgramInfo, NodeOperation.DELETE);
//
//            return getStatus();
//
//        } catch (AssemblyException e) {
//            LOG.error("Error disassembling CoreProgram", e);
//            throw new OperationFailedException("Error disassembling CoreProgram");
//        }
    	throw new OperationFailedException("Deletion of CoreProgram is not supported."); 
    }

    @Override
    @Transactional(readOnly=true)
    public CoreProgramInfo getCoreProgram(String coreProgramId,ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
    	CoreProgramInfo coreProgramInfo = null;

        try {
            CluInfo clu = cluService.getClu(coreProgramId,contextInfo);
            if ( ! ProgramAssemblerConstants.CORE_PROGRAM.equals(clu.getTypeKey()) ) {
                throw new DoesNotExistException("Specified CLU is not a CoreProgram");
            }
            coreProgramInfo = coreProgramAssembler.assemble(clu, null, false, contextInfo);
        } catch (AssemblyException e) {
            LOG.error("Error assembling CoreProgram", e);
            throw new OperationFailedException("Error assembling CoreProgram");
        }
        return coreProgramInfo;
		// comment out the above, and uncomment below to get auto-generated data
        // (and vice-versa)
//		try {
//			return new CoreProgramDataGenerator().getCoreProgramInfoTestData();
//		} catch (Exception e) {
//			return null;
//		}
    }

    @Override
    @Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
	public CoreProgramInfo updateCoreProgram( String coreProgramId,  String coreProgramTypeKey,
                                              CoreProgramInfo coreProgramInfo,  ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException, VersionMismatchException, OperationFailedException, PermissionDeniedException{
        checkForMissingParameter(coreProgramInfo, "CoreProgramInfo");
        
        // Validate
        List<ValidationResultInfo> validationResults = validateCoreProgram("OBJECT", coreProgramInfo,contextInfo);
        if (ValidatorUtils.hasErrors(validationResults)) {
            throw new DataValidationErrorException("Validation error!", validationResults);
        }

        try {

            return processCoreProgramInfo(coreProgramInfo, NodeOperation.UPDATE,contextInfo);

        } catch (AssemblyException e) {
            LOG.error("Error disassembling CoreProgram", e);
            throw new OperationFailedException("Error disassembling CoreProgram");
        }
    }

    @Override
    public List<ValidationResultInfo> validateCoreProgram( String validationType,  CoreProgramInfo coreProgramInfo,
                                                           ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException{
        List<ValidationResultInfo> validationResults = new ArrayList<ValidationResultInfo>();
//        if ( ! ProgramAssemblerConstants.DRAFT.equals(coreProgramInfo.getState()) ) {
	        ObjectStructureDefinition objStructure = this.getObjectStructure(CoreProgramInfo.class.getName());
	        Validator validator = validatorFactory.getValidator();
            validationResults.addAll(validator.validateObject(coreProgramInfo, objStructure,contextInfo));
//        }
        return validationResults;
    }
        
        
	@Override
	@Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
	public CredentialProgramInfo createNewCredentialProgramVersion( String credentialProgramId,
                                                                    String versionComment,
                                                                    ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, VersionMismatchException, DataValidationErrorException, ReadOnlyException {
		//step one, get the original
		VersionDisplayInfo currentVersion = cluService.getCurrentVersion(CluServiceConstants.CLU_NAMESPACE_URI, credentialProgramId,contextInfo);
		CredentialProgramInfo originaCredentialProgram = getCredentialProgram(currentVersion.getId(),contextInfo);

		//Version the Clu
		CluInfo newVersionClu = cluService.createNewCluVersion(credentialProgramId, versionComment,contextInfo);

		try {

	        BaseDTOAssemblyNode<CredentialProgramInfo, CluInfo> results;

	        //Integrate changes into the original. (should this just be just the id?)
	        
			credentialProgramAssembler.assemble(newVersionClu, originaCredentialProgram, true, contextInfo);

			//Clear Ids from the original so it will make a copy and do other processing

			processCopy(originaCredentialProgram, currentVersion.getId(),contextInfo);

			//Disassemble the new -- Convert the R2 to R1 before it is passed....
			results = credentialProgramAssembler.disassemble(originaCredentialProgram, NodeOperation.UPDATE, contextInfo);

			// Use the results to make the appropriate service calls here
			programServiceMethodInvoker.invokeServiceCalls(results, contextInfo);
			
			//Here we get a R1 object that is then convert to and R2 object and then returned via the return statement below.
			return results.getBusinessDTORef();
		} catch(AssemblyException e) {
			throw new OperationFailedException("Error creating new MajorDiscipline version",e);
		} catch (AlreadyExistsException e) {
			throw new OperationFailedException("Error creating new MajorDiscipline version",e);
		} catch (DependentObjectsExistException e) {
			throw new OperationFailedException("Error creating new MajorDiscipline version",e);
		} catch (CircularRelationshipException e) {
			throw new OperationFailedException("Error creating new MajorDiscipline version",e);
		} catch (UnsupportedActionException e) {
			throw new OperationFailedException("Error creating new MajorDiscipline version",e);
		} catch (CircularReferenceException e) {
			throw new OperationFailedException("Error creating new MajorDiscipline version",e);
		}
	}


	@Override
	@Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
	public StatusInfo setCurrentCoreProgramVersion( String coreProgramId,  Date currentVersionStart,
                                                    ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, IllegalVersionSequencingException,
            OperationFailedException, PermissionDeniedException, DataValidationErrorException{
		StatusInfo status = cluService.setCurrentCluVersion(coreProgramId, currentVersionStart,contextInfo);
		
		return status;
	}

	@Override
	@Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
	public StatusInfo setCurrentCredentialProgramVersion( String credentialProgramId,  Date currentVersionStart,
                                                          ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, IllegalVersionSequencingException,
            OperationFailedException, PermissionDeniedException, DataValidationErrorException {
		StatusInfo status = cluService.setCurrentCluVersion(credentialProgramId, currentVersionStart,contextInfo);
		
		return status;
	}

	@Override
    @Transactional(readOnly=true)
	public VersionDisplayInfo getCurrentVersion(String refObjectTypeURI,
			String refObjectId,ContextInfo contextInfo) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		if(ProgramServiceConstants.PROGRAM_NAMESPACE_MAJOR_DISCIPLINE_URI.equals(refObjectTypeURI)){
			return cluService.getCurrentVersion(CluServiceConstants.CLU_NAMESPACE_URI, refObjectId,contextInfo);
		}
		throw new InvalidParameterException("Object type: " + refObjectTypeURI + " is not known to this implementation");
	}

	@Override
    @Transactional(readOnly=true)
	public VersionDisplayInfo getCurrentVersionOnDate(String refObjectTypeURI,
                                                      String refObjectId, Date date, ContextInfo contextInfo) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		if(ProgramServiceConstants.PROGRAM_NAMESPACE_MAJOR_DISCIPLINE_URI.equals(refObjectTypeURI)){
			return cluService.getCurrentVersionOnDate(CluServiceConstants.CLU_NAMESPACE_URI, refObjectId, date,contextInfo);
		}
		throw new InvalidParameterException("Object type: " + refObjectTypeURI + " is not known to this implementation");
	}

	@Override
    @Transactional(readOnly=true)
	public VersionDisplayInfo getFirstVersion(String refObjectTypeURI,
                                              String refObjectId, ContextInfo contextInfo) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		if(ProgramServiceConstants.PROGRAM_NAMESPACE_MAJOR_DISCIPLINE_URI.equals(refObjectTypeURI)){
			return cluService.getFirstVersion(CluServiceConstants.CLU_NAMESPACE_URI, refObjectId,contextInfo);
		}
		throw new InvalidParameterException("Object type: " + refObjectTypeURI + " is not known to this implementation");

	}

	@Override
    @Transactional(readOnly=true)
	public VersionDisplayInfo getLatestVersion(String refObjectTypeURI,
                                               String refObjectId, ContextInfo contextInfo) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		if(ProgramServiceConstants.PROGRAM_NAMESPACE_MAJOR_DISCIPLINE_URI.equals(refObjectTypeURI)){
			return cluService.getLatestVersion(CluServiceConstants.CLU_NAMESPACE_URI, refObjectId,contextInfo);
		}
		throw new InvalidParameterException("Object type: " + refObjectTypeURI + " is not known to this implementation");

	}

	@Override
    @Transactional(readOnly=true)
	public VersionDisplayInfo getVersionBySequenceNumber(
            String refObjectTypeURI, String refObjectId, Long sequence, ContextInfo contextInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		if(ProgramServiceConstants.PROGRAM_NAMESPACE_MAJOR_DISCIPLINE_URI.equals(refObjectTypeURI)){
			return cluService.getVersionBySequenceNumber(CluServiceConstants.CLU_NAMESPACE_URI, refObjectId, sequence,contextInfo);
		}
		throw new InvalidParameterException("Object type: " + refObjectTypeURI + " is not known to this implementation");
	}

	@Override
    @Transactional(readOnly=true)
	public List<VersionDisplayInfo> getVersions(String refObjectTypeURI,
			String refObjectId,ContextInfo contextInfo) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		if(ProgramServiceConstants.PROGRAM_NAMESPACE_MAJOR_DISCIPLINE_URI.equals(refObjectTypeURI)){
			return cluService.getVersions(CluServiceConstants.CLU_NAMESPACE_URI, refObjectId,contextInfo);
		}
		throw new InvalidParameterException("Object type: " + refObjectTypeURI + " is not known to this implementation");
	}

	@Override
    @Transactional(readOnly=true)
	public List<VersionDisplayInfo> getVersionsInDateRange(
            String refObjectTypeURI, String refObjectId, Date from, Date to, ContextInfo contextInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		if(ProgramServiceConstants.PROGRAM_NAMESPACE_MAJOR_DISCIPLINE_URI.equals(refObjectTypeURI)){
			return cluService.getVersionsInDateRange(CluServiceConstants.CLU_NAMESPACE_URI, refObjectId, from, to,contextInfo);
		}
		throw new InvalidParameterException("Object type: " + refObjectTypeURI + " is not known to this implementation");
	}

	public void setAtpService(AtpService atpService) {
		this.atpService = atpService;
	}

	public AtpService getAtpService() {
		return atpService;
	}

	private void validateMajorDisciplineAtps(MajorDisciplineInfo majorDisciplineInfo, List<ValidationResultInfo> validationResults,ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		String startTerm = majorDisciplineInfo.getStartTerm();

        String endInstAdmitTerm = majorDisciplineInfo.getAttributeValue("endInstAdmitTerm");
		if(endInstAdmitTerm != null) {
            compareAtps(startTerm, endInstAdmitTerm, validationResults, "End Inst Admin Term", "endInstAdmitTerm",contextInfo);
		}
		if(!isEmpty(majorDisciplineInfo.getEndProgramEntryTerm())){
			compareAtps(startTerm, majorDisciplineInfo.getEndTerm(), validationResults, "End Program Entry Term", "endProgramEntryTerm",contextInfo);
		}
		
		if(!isEmpty(majorDisciplineInfo.getEndTerm())){
			compareAtps(startTerm, majorDisciplineInfo.getEndTerm(), validationResults, "End Program Enroll Term", "endTerm",contextInfo);
		}		
		
		List<ProgramVariationInfo> variations = majorDisciplineInfo.getVariations();
		if(variations != null && !variations.isEmpty()){
			int idx = 0;
			for(ProgramVariationInfo variation : variations){
				validateVariationAtps(variation, validationResults, idx,contextInfo);
				idx ++;
			}
		}
	}
	
	//FIXME, this validation should be moved into a custom validation class + configuration
	private void validateVariationAtps(ProgramVariationInfo programVariationInfo, List<ValidationResultInfo> validationResults, int idx,ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException,PermissionDeniedException{
		
		String startTerm = programVariationInfo.getStartTerm();
        String endInstAdmitTerm = programVariationInfo.getAttributeValue("endInstAdmitTerm");
		if(endInstAdmitTerm != null) {
            compareAtps(startTerm, endInstAdmitTerm, validationResults, "End Inst Admin Term",  "variations/" + idx + "/endInstAdmitTerm",contextInfo);
		}
	
		if(!isEmpty(programVariationInfo.getEndProgramEntryTerm())){
			compareAtps(startTerm, programVariationInfo.getEndProgramEntryTerm(), validationResults, "End Program Entry Term", "variations/" + idx + "/endProgramEntryTerm",contextInfo);
		}
		
		if(!isEmpty(programVariationInfo.getEndTerm())){
			compareAtps(startTerm, programVariationInfo.getEndTerm(), validationResults, "End Program Enroll Term", "variations/" + idx + "/endTerm",contextInfo);
		}
	}
	
	private AtpInfo getAtpInfo(String atpKey,ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,PermissionDeniedException{
		if(atpKey==null){
			return null;
		}
		return atpService.getAtp(atpKey, contextInfo);
	}
	//FIXME error should return using message service and not static text
	private void compareAtps(String aptKey1, String aptKey2, List<ValidationResultInfo> validationResults, String field, String path,ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException{
		AtpInfo atpInfo1 = null;
		AtpInfo atpInfo2 = null;
		
		try{
			atpInfo1 = getAtpInfo(aptKey1,contextInfo);
			atpInfo2 = getAtpInfo(aptKey2,contextInfo);
		}catch(DoesNotExistException e){}
		
		if(atpInfo1 != null && atpInfo1 != null){
			if(atpInfo1.getStartDate()!= null && atpInfo2.getStartDate() != null){			
				boolean compareResult = ValidatorUtils.compareValues(atpInfo2.getStartDate(), atpInfo1.getStartDate(), DataType.DATE, "greater_than_equal", true, new ServerDateParser());
				if(!compareResult){
					ValidationResultInfo vri = new ValidationResultInfo();
					vri.setElement(path);
					vri.setError(field + " should be greater than Start Term");
					validationResults.add(vri);
				}
			}
		}
			
	}
	
	private boolean isEmpty(String value){
		return value == null || (value != null && "".equals(value));
	}

	public void setDocumentService(DocumentService documentService) {
		this.documentService = documentService;
	}

	public DocumentService getDocumentService() {
		return documentService;
	}

	@Override
	public SearchResult search(SearchRequest searchRequest
			) throws MissingParameterException {
	    throw new UnsupportedOperationException("ProgramService.search");//also unimplemented in trunk
		//return null;
	}

	@Override
	public List<CredentialProgramInfo> getCredentialProgramsByIds(
			List<String> credentialProgramIds, ContextInfo contextInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
	    throw new UnsupportedOperationException("getCredentialProgramsByIds");
		//return null;
	}

	@Override
	public List<MajorDisciplineInfo> getMajorDisciplinesByIds(
			List<String> majorDisciplineIds, ContextInfo contextInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
	    throw new UnsupportedOperationException("getMajorDisciplinesByIds");
		//return null;
	}

	@Override
	public List<String> getMajorDisciplineIdsByCredentialProgramType(
			String programType, ContextInfo contextInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
	    throw new UnsupportedOperationException("getMajorDisciplineIdsByCredentialProgramType");
		//return null;
	}

	@Override
	public List<HonorsProgramInfo> getHonorsProgramsByIds(
			List<String> honorsProgramIds, ContextInfo contextInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
	    throw new UnsupportedOperationException("getHonorsProgramsByIds");
		//return null;
	}

	@Override
	public List<String> getHonorProgramIdsByCredentialProgramType(
			String programType, ContextInfo contextInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
	    throw new UnsupportedOperationException("getHonorProgramIdsByCredentialProgramType");
		//return null;
	}

	@Override
	public HonorsProgramInfo updateHonorsProgram(String honorsProgramId,
			String honorsProgramTypeKey, HonorsProgramInfo honorsProgramInfo,
			ContextInfo contextInfo) throws DataValidationErrorException,
			DoesNotExistException, InvalidParameterException,
			MissingParameterException, VersionMismatchException,
			OperationFailedException, PermissionDeniedException {
	    throw new UnsupportedOperationException("updateHonorsProgram");
		//return null;
	}

	@Override
	public List<CoreProgramInfo> getCoreProgramsByIds(
			List<String> coreProgramIds, ContextInfo contextInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
	    throw new UnsupportedOperationException("getCoreProgramsByIds");
		//return null;
	}

	@Override
	public List<ProgramRequirementInfo> getProgramRequirementsByIds(
			List<String> programRequirementIds, ContextInfo contextInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
	    throw new UnsupportedOperationException("getProgramRequirementsByIds");
		//return null;
	}

	@Override
	public List<ProgramVariationInfo> getProgramVariationsByMajorDiscipline(
			String majorDisciplineId, ContextInfo contextInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
	    throw new UnsupportedOperationException("getProgramVariationsByMajorDiscipline");
		//return null;
	}

}
