package org.kuali.student.lum.program.service.impl;

import static org.apache.commons.collections.CollectionUtils.isNotEmpty;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.kuali.student.common.validator.Validator;
import org.kuali.student.core.assembly.BaseDTOAssemblyNode;
import org.kuali.student.core.assembly.BaseDTOAssemblyNode.NodeOperation;
import org.kuali.student.core.assembly.BusinessServiceMethodInvoker;
import org.kuali.student.core.assembly.data.AssemblyException;
import org.kuali.student.core.dictionary.dto.ObjectStructureDefinition;
import org.kuali.student.core.dictionary.service.DictionaryService;
import org.kuali.student.core.dto.StatusInfo;
import org.kuali.student.core.exceptions.AlreadyExistsException;
import org.kuali.student.core.exceptions.DataValidationErrorException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.exceptions.VersionMismatchException;
import org.kuali.student.core.search.dto.SearchCriteriaTypeInfo;
import org.kuali.student.core.search.dto.SearchRequest;
import org.kuali.student.core.search.dto.SearchResult;
import org.kuali.student.core.search.dto.SearchResultTypeInfo;
import org.kuali.student.core.search.dto.SearchTypeInfo;
import org.kuali.student.core.search.service.SearchManager;
import org.kuali.student.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.core.statement.service.StatementService;
import org.kuali.student.core.validation.dto.ValidationResultInfo;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.dto.LuTypeInfo;
import org.kuali.student.lum.lu.service.LuService;
import org.kuali.student.lum.program.dto.CoreProgramInfo;
import org.kuali.student.lum.program.dto.CredentialProgramInfo;
import org.kuali.student.lum.program.dto.HonorsProgramInfo;
import org.kuali.student.lum.program.dto.MajorDisciplineInfo;
import org.kuali.student.lum.program.dto.MinorDisciplineInfo;
import org.kuali.student.lum.program.dto.ProgramRequirementInfo;
import org.kuali.student.lum.program.dto.ProgramVariationInfo;
import org.kuali.student.lum.program.service.ProgramService;
import org.kuali.student.lum.program.service.assembler.CoreProgramAssembler;
import org.kuali.student.lum.program.service.assembler.CredentialProgramAssembler;
import org.kuali.student.lum.program.service.assembler.MajorDisciplineAssembler;
import org.kuali.student.lum.program.service.assembler.ProgramAssemblerConstants;
import org.springframework.transaction.annotation.Transactional;

@Transactional(noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
public class ProgramServiceImpl implements ProgramService {
    final static Logger LOG = Logger.getLogger(ProgramServiceImpl.class);

    private LuService luService;
    private Validator validator;
    private BusinessServiceMethodInvoker programServiceMethodInvoker;
    private DictionaryService dictionaryService;
    private SearchManager searchManager;
    private MajorDisciplineAssembler majorDisciplineAssembler;
    private ProgramRequirementAssembler programRequirementAssembler;
    private CredentialProgramAssembler credentialProgramAssembler;
    private CoreProgramAssembler coreProgramAssembler;
    private StatementService statementService;

    @Override
    public CredentialProgramInfo createCredentialProgram(
            CredentialProgramInfo credentialProgramInfo)
            throws AlreadyExistsException, DataValidationErrorException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {

        if (credentialProgramInfo == null) {
            throw new MissingParameterException("CredentialProgramInfo can not be null");
        }

        // Validate
        List<ValidationResultInfo> validationResults = validateCredentialProgram("OBJECT", credentialProgramInfo);
        if (null != validationResults && validationResults.size() > 0) {
            throw new DataValidationErrorException("Validation error!", validationResults);
        }

        try {
            return processCredentialProgramInfo(credentialProgramInfo, NodeOperation.CREATE);
        } catch (AssemblyException e) {
            LOG.error("Error disassembling Major Discipline", e);
            throw new OperationFailedException("Error disassembling Major Discipline");
        }
    }

    @Override
    public HonorsProgramInfo createHonorsProgram(
            HonorsProgramInfo honorsProgramInfo) throws AlreadyExistsException,
            DataValidationErrorException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ProgramRequirementInfo createProgramRequirement(
            ProgramRequirementInfo programRequirementInfo)
            throws AlreadyExistsException, DataValidationErrorException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(programRequirementInfo, "programRequirementInfo");

        // Validate
        List<ValidationResultInfo> validationResults = validateProgramRequirement("OBJECT", programRequirementInfo);
        if (isNotEmpty(validationResults)) {
        	throw new DataValidationErrorException("Validation error!", validationResults);
        }

        try {
            return processProgramRequirement(programRequirementInfo, NodeOperation.CREATE);
        } catch (AssemblyException e) {
            LOG.error("Error disassembling Program Requirement", e);
            throw new OperationFailedException("Error disassembling Program Requirement", e);
        }
    }

    @Override
    public MajorDisciplineInfo createMajorDiscipline(
            MajorDisciplineInfo majorDisciplineInfo)
            throws AlreadyExistsException, DataValidationErrorException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {

        if (majorDisciplineInfo == null) {
            throw new MissingParameterException("MajorDisciplineInfo can not be null");
        }

        // Validate
        List<ValidationResultInfo> validationResults = validateMajorDiscipline("OBJECT", majorDisciplineInfo);
        if (null != validationResults && validationResults.size() > 0) {
            throw new DataValidationErrorException("Validation error!", validationResults);
        }

        try {
            return processMajorDisciplineInfo(majorDisciplineInfo, NodeOperation.CREATE);
        } catch (AssemblyException e) {
            LOG.error("Error creating Major Discipline", e);
            throw new OperationFailedException("Error creating Major Discipline");
        }
    }

    @Override
    public MinorDisciplineInfo createMinorDiscipline(
            MinorDisciplineInfo minorDisciplineInfo)
            throws AlreadyExistsException, DataValidationErrorException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public StatusInfo deleteCredentialProgram(String credentialProgramId)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        try {
        	CredentialProgramInfo credentialProgram = getCredentialProgram(credentialProgramId);

            processCredentialProgramInfo(credentialProgram, NodeOperation.DELETE);

            return getStatus();

        } catch (AssemblyException e) {
            LOG.error("Error disassembling CredentialProgram", e);
            throw new OperationFailedException("Error disassembling CredentialProgram");
        }
    }

    @Override
    public StatusInfo deleteHonorsProgram(String honorsProgramId)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public StatusInfo deleteMajorDiscipline(String majorDisciplineId)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        try {
            MajorDisciplineInfo majorDiscipline = getMajorDiscipline(majorDisciplineId);

            processMajorDisciplineInfo(majorDiscipline, NodeOperation.DELETE);

            return getStatus();

        } catch (AssemblyException e) {
            LOG.error("Error disassembling MajorDiscipline", e);
            throw new OperationFailedException("Error disassembling MajorDiscipline");
        }
    }

    @Override
    public StatusInfo deleteMinorDiscipline(String minorDisciplineId)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public StatusInfo deleteProgramRequirement(String programRequirementId)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
    	checkForMissingParameter(programRequirementId, "programRequirementId");
        try {
        	ProgramRequirementInfo programRequirement = getProgramRequirement(programRequirementId, null, null);

        	processProgramRequirement(programRequirement, NodeOperation.DELETE);

            return getStatus();

        } catch (AssemblyException e) {
            LOG.error("Error disassembling MajorDiscipline", e);
            throw new OperationFailedException("Error disassembling ProgramRequirement", e);
        }

    }

    @Override
    public CredentialProgramInfo getCredentialProgram(String credentialProgramId)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {

    	CredentialProgramInfo credentialProgramInfo = null;

        try {
            CluInfo clu = luService.getClu(credentialProgramId);

            if ( ! ProgramAssemblerConstants.CREDENTIAL_PROGRAM_TYPES.contains(clu.getType()) ) {
                throw new DoesNotExistException("Specified CLU is not a Credential Program");
            }

            credentialProgramInfo = credentialProgramAssembler.assemble(clu, null, false);
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

    @Override
    public LuTypeInfo getCredentialProgramType(String credentialProgramTypeKey)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<LuTypeInfo> getCredentialProgramTypes()
            throws OperationFailedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<String> getHonorsByCredentialProgramType(String programType)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public HonorsProgramInfo getHonorsProgram(String honorsProgramId)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public MajorDisciplineInfo getMajorDiscipline(String majorDisciplineId)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {


        MajorDisciplineInfo majorDiscipline = null;

        try {
            CluInfo clu = luService.getClu(majorDisciplineId);
            if ( ! ProgramAssemblerConstants.MAJOR_DISCIPLINE.equals(clu.getType()) ) {
                throw new DoesNotExistException("Specified CLU is not a Major Discipline");
            }
            majorDiscipline = majorDisciplineAssembler.assemble(clu, null, false);
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

	@Override
	public List<String> getMajorIdsByCredentialProgramType(String programType)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MinorDisciplineInfo getMinorDiscipline(String minorDisciplineId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getMinorsByCredentialProgramType(String programType)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProgramRequirementInfo getProgramRequirement(String programRequirementId, String nlUsageTypeKey, String language) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {

		checkForMissingParameter(programRequirementId, "programRequirementId");

		CluInfo clu = luService.getClu(programRequirementId);
		if (!ProgramAssemblerConstants.PROGRAM_REQUIREMENT.equals(clu.getType())) {
			throw new DoesNotExistException("Specified CLU is not a Program Requirement");
		}
		try {
			ProgramRequirementInfo progReqInfo = programRequirementAssembler.assemble(clu, null, false);
			StatementTreeViewInfo statement = progReqInfo.getStatement();
			if (nlUsageTypeKey != null && language != null) {
				statement.setNaturalLanguageTranslation(statementService.getNaturalLanguageForStatement(statement.getId(), nlUsageTypeKey, language));
			}
			return progReqInfo;
		} catch (AssemblyException e) {
            LOG.error("Error assembling program requirement", e);
            throw new OperationFailedException("Error assembling program requirement: " + e.getMessage(), e);
		}
	}

	@Override
	public List<ProgramVariationInfo> getVariationsByMajorDisciplineId(
			String majorDisciplineId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
    	List<ProgramVariationInfo> pvInfos = new ArrayList<ProgramVariationInfo>();

    	try {
    			List<CluInfo> clus = luService.getRelatedClusByCluId(majorDisciplineId, ProgramAssemblerConstants.HAS_PROGRAM_VARIATION);

		        if(clus != null && clus.size() > 0){
		        	for(CluInfo clu : clus){
		        		ProgramVariationInfo pvInfo = majorDisciplineAssembler.getProgramVariationAssembler().assemble(clu, null, false);
		        		if(pvInfo != null){
		        			pvInfos.add(pvInfo);
		        		}
		        	}
		        }
		    } catch (AssemblyException e) {
		        LOG.error("Error assembling ProgramVariation", e);
		        throw new OperationFailedException("Error assembling ProgramVariation");
		    }

        return pvInfos;
    }

    @Override
    public CredentialProgramInfo updateCredentialProgram(
            CredentialProgramInfo credentialProgramInfo)
            throws DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            VersionMismatchException, OperationFailedException,
            PermissionDeniedException {

        if (credentialProgramInfo == null) {
            throw new MissingParameterException("CredentialProgramInfo can not be null");
        }

        // Validate
        List<ValidationResultInfo> validationResults = validateCredentialProgram("OBJECT", credentialProgramInfo);
        if (null != validationResults && validationResults.size() > 0) {
            throw new DataValidationErrorException("Validation error!", validationResults);
        }

        try {

            return processCredentialProgramInfo(credentialProgramInfo, NodeOperation.UPDATE);

        } catch (AssemblyException e) {
            LOG.error("Error disassembling majorDiscipline", e);
            throw new OperationFailedException("Error disassembling majorDiscipline");
        }
    }

    @Override
    public HonorsProgramInfo updateHonorsProgram(
            HonorsProgramInfo honorsProgramInfo)
            throws DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            VersionMismatchException, OperationFailedException,
            PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public MajorDisciplineInfo updateMajorDiscipline(
            MajorDisciplineInfo majorDisciplineInfo)
            throws DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            VersionMismatchException, OperationFailedException,
            PermissionDeniedException {

        if (majorDisciplineInfo == null) {
            throw new MissingParameterException("MajorDisciplineInfo can not be null");
        }

        // Validate
        List<ValidationResultInfo> validationResults = validateMajorDiscipline("OBJECT", majorDisciplineInfo);
        if (null != validationResults && validationResults.size() > 0) {
            throw new DataValidationErrorException("Validation error!", validationResults);
        }

        try {

            return processMajorDisciplineInfo(majorDisciplineInfo, NodeOperation.UPDATE);

        } catch (AssemblyException e) {
            LOG.error("Error disassembling majorDiscipline", e);
            throw new OperationFailedException("Error disassembling majorDiscipline");
        }
    }

    @Override
    public MinorDisciplineInfo updateMinorDiscipline(
            MinorDisciplineInfo minorDisciplineInfo)
            throws DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            VersionMismatchException, OperationFailedException,
            PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ProgramRequirementInfo updateProgramRequirement(
            ProgramRequirementInfo programRequirementInfo)
            throws DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            VersionMismatchException, OperationFailedException,
            PermissionDeniedException {
    	checkForMissingParameter(programRequirementInfo, "programRequirementInfo");
    	deleteProgramRequirement(programRequirementInfo.getId());
    	ProgramRequirementInfo newOne;
		try {
			newOne = createProgramRequirement(programRequirementInfo);
		} catch (AlreadyExistsException e) {
			throw new InvalidParameterException();
		}
    	return newOne;
    }

    @Override
    public List<ValidationResultInfo> validateCredentialProgram(
            String validationType, CredentialProgramInfo credentialProgramInfo)
            throws InvalidParameterException,
            MissingParameterException, OperationFailedException {

        ObjectStructureDefinition objStructure = this.getObjectStructure(CredentialProgramInfo.class.getName());
        List<ValidationResultInfo> validationResults = validator.validateObject(credentialProgramInfo, objStructure);

        return validationResults;
    }

    @Override
    public List<ValidationResultInfo> validateHonorsProgram(
            String validationType, HonorsProgramInfo honorsProgramInfo)
            throws InvalidParameterException,
            MissingParameterException, OperationFailedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<ValidationResultInfo> validateMajorDiscipline(
            String validationType, MajorDisciplineInfo majorDisciplineInfo)
            throws InvalidParameterException,
            MissingParameterException, OperationFailedException {

        ObjectStructureDefinition objStructure = this.getObjectStructure(MajorDisciplineInfo.class.getName());
        List<ValidationResultInfo> validationResults = validator.validateObject(majorDisciplineInfo, objStructure);

        return validationResults;
    }

    @Override
    public List<ValidationResultInfo> validateMinorDiscipline(
            String validationType, MinorDisciplineInfo minorDisciplineInfo)
            throws InvalidParameterException,
            MissingParameterException, OperationFailedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<ValidationResultInfo> validateProgramRequirement(
            String validationType, ProgramRequirementInfo programRequirementInfo)
            throws InvalidParameterException,
            MissingParameterException, OperationFailedException {

        ObjectStructureDefinition objStructure = this.getObjectStructure(ProgramRequirementInfo.class.getName());
        List<ValidationResultInfo> validationResults = validator.validateObject(programRequirementInfo, objStructure);

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
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<SearchCriteriaTypeInfo> getSearchCriteriaTypes()
            throws OperationFailedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public SearchResultTypeInfo getSearchResultType(String searchResultTypeKey)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<SearchResultTypeInfo> getSearchResultTypes()
            throws OperationFailedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public SearchTypeInfo getSearchType(String searchTypeKey)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<SearchTypeInfo> getSearchTypes()
            throws OperationFailedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<SearchTypeInfo> getSearchTypesByCriteria(
            String searchCriteriaTypeKey) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<SearchTypeInfo> getSearchTypesByResult(
            String searchResultTypeKey) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public SearchResult search(SearchRequest searchRequest)
            throws MissingParameterException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Check for missing parameter and throw localized exception if missing
     *
     * @param param
     * @param parameter name
     * @throws MissingParameterException
     */
    private void checkForMissingParameter(Object param, String paramName)
            throws MissingParameterException {
        if (param == null) {
            throw new MissingParameterException(paramName + " can not be null");
        }
    }

    // TODO - when CRUD for a second ProgramInfo is implemented, pull common code up from its process*() and this

    private MajorDisciplineInfo processMajorDisciplineInfo(MajorDisciplineInfo majorDisciplineInfo, NodeOperation operation) throws AssemblyException {

        BaseDTOAssemblyNode<MajorDisciplineInfo, CluInfo> results = majorDisciplineAssembler.disassemble(majorDisciplineInfo, operation);
        invokeServiceCalls(results);
        return results.getBusinessDTORef();
    }

    private CredentialProgramInfo processCredentialProgramInfo(CredentialProgramInfo credentialProgramInfo, NodeOperation operation) throws AssemblyException {

        BaseDTOAssemblyNode<CredentialProgramInfo, CluInfo> results = credentialProgramAssembler.disassemble(credentialProgramInfo, operation);
        invokeServiceCalls(results);
        return results.getBusinessDTORef();
    }

    private ProgramRequirementInfo processProgramRequirement(ProgramRequirementInfo programRequirementInfo, NodeOperation operation) throws AssemblyException {
        BaseDTOAssemblyNode<ProgramRequirementInfo, CluInfo> results = programRequirementAssembler.disassemble(programRequirementInfo, operation);
        invokeServiceCalls(results);
        return results.getBusinessDTORef();
    }

	private void invokeServiceCalls(BaseDTOAssemblyNode<?, CluInfo> results) throws AssemblyException{
        // Use the results to make the appropriate service calls here
        try {
            programServiceMethodInvoker.invokeServiceCalls(results);
        } catch (Exception e) {
            throw new AssemblyException(e);
        }
    }

    //Spring setters. Used by spring container to inject corresponding dependencies.

    public void setLuService(LuService luService) {
        this.luService = luService;
    }

    public void setDictionaryService(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    public void setSearchManager(SearchManager searchManager) {
        this.searchManager = searchManager;
    }

    public void setMajorDisciplineAssembler(MajorDisciplineAssembler majorDisciplineAssembler) {
        this.majorDisciplineAssembler = majorDisciplineAssembler;
    }

	public void setCredentialProgramAssembler(
			CredentialProgramAssembler credentialProgramAssembler) {
		this.credentialProgramAssembler = credentialProgramAssembler;
	}

	public void setProgramRequirementAssembler(ProgramRequirementAssembler programRequirementAssembler) {
        this.programRequirementAssembler = programRequirementAssembler;
    }

    public void setProgramServiceMethodInvoker(BusinessServiceMethodInvoker serviceMethodInvoker) {
        this.programServiceMethodInvoker = serviceMethodInvoker;
    }

    public void setValidator(Validator validator) {
        this.validator = validator;
    }

	public void setCoreProgramAssembler(CoreProgramAssembler coreProgramAssembler) {
		this.coreProgramAssembler = coreProgramAssembler;
	}

	public StatementService getStatementService() {
		return statementService;
	}

	public void setStatementService(StatementService statementService) {
		this.statementService = statementService;
	}

	private StatusInfo getStatus(){
        StatusInfo status = new StatusInfo();
        status.setSuccess(true);
        return status;
	}

    private CoreProgramInfo processCoreProgramInfo(CoreProgramInfo coreProgramInfo, NodeOperation operation) throws AssemblyException {

        BaseDTOAssemblyNode<CoreProgramInfo, CluInfo> results = coreProgramAssembler.disassemble(coreProgramInfo, operation);
        invokeServiceCalls(results);
        return results.getBusinessDTORef();
    }

    @Override
    public CoreProgramInfo createCoreProgram(CoreProgramInfo coreProgramInfo) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (coreProgramInfo == null) {
            throw new MissingParameterException("CoreProgramInfo can not be null");
        }

        // Validate
        List<ValidationResultInfo> validationResults = validateCoreProgram("OBJECT", coreProgramInfo);
        if (null != validationResults && validationResults.size() > 0) {
            throw new DataValidationErrorException("Validation error!", validationResults);
        }

        try {
            return processCoreProgramInfo(coreProgramInfo, NodeOperation.CREATE);
        } catch (AssemblyException e) {
            LOG.error("Error disassembling CoreProgram", e);
            throw new OperationFailedException("Error disassembling CoreProgram");
        }
    }

    @Override
    public StatusInfo deleteCoreProgram(String coreProgramId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        try {
        	CoreProgramInfo coreProgramInfo = getCoreProgram(coreProgramId);

            processCoreProgramInfo(coreProgramInfo, NodeOperation.DELETE);

            return getStatus();

        } catch (AssemblyException e) {
            LOG.error("Error disassembling CoreProgram", e);
            throw new OperationFailedException("Error disassembling CoreProgram");
        }
    }

    @Override
    public CoreProgramInfo getCoreProgram(String coreProgramId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
    	CoreProgramInfo coreProgramInfo = null;

        try {
            CluInfo clu = luService.getClu(coreProgramId);
            if ( ! ProgramAssemblerConstants.CORE_PROGRAM.equals(clu.getType()) ) {
                throw new DoesNotExistException("Specified CLU is not a CoreProgram");
            }
            coreProgramInfo = coreProgramAssembler.assemble(clu, null, false);
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
    public CoreProgramInfo updateCoreProgram(CoreProgramInfo coreProgramInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, VersionMismatchException, OperationFailedException, PermissionDeniedException {
        if (coreProgramInfo == null) {
            throw new MissingParameterException("CoreProgramInfo can not be null");
        }

        // Validate
        List<ValidationResultInfo> validationResults = validateCoreProgram("OBJECT", coreProgramInfo);
        if (null != validationResults && validationResults.size() > 0) {
            throw new DataValidationErrorException("Validation error!", validationResults);
        }

        try {

            return processCoreProgramInfo(coreProgramInfo, NodeOperation.UPDATE);

        } catch (AssemblyException e) {
            LOG.error("Error disassembling CoreProgram", e);
            throw new OperationFailedException("Error disassembling CoreProgram");
        }
    }

    @Override
    public List<ValidationResultInfo> validateCoreProgram(String validationType, CoreProgramInfo coreProgramInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException {
        ObjectStructureDefinition objStructure = this.getObjectStructure(CoreProgramInfo.class.getName());
        List<ValidationResultInfo> validationResults = validator.validateObject(coreProgramInfo, objStructure);

        return validationResults;
    }

}
