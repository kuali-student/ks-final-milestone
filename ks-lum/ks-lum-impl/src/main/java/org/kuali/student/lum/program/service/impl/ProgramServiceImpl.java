package org.kuali.student.lum.program.service.impl;

import java.util.List;

import javax.jws.WebService;

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
import org.kuali.student.core.validation.dto.ValidationResultInfo;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.service.LuService;
import org.kuali.student.lum.program.dto.CredentialProgramInfo;
import org.kuali.student.lum.program.dto.HonorsProgramInfo;
import org.kuali.student.lum.program.dto.LuTypeInfo;
import org.kuali.student.lum.program.dto.MajorDisciplineInfo;
import org.kuali.student.lum.program.dto.MinorDisciplineInfo;
import org.kuali.student.lum.program.dto.ProgramRequirementInfo;
import org.kuali.student.lum.program.dto.ProgramVariationInfo;
import org.kuali.student.lum.program.service.ProgramService;
import org.kuali.student.lum.program.service.assembler.MajorDisciplineAssembler;
import org.kuali.student.lum.program.service.assembler.MajorDisciplineDataGenerator;
import org.springframework.transaction.annotation.Transactional;

@WebService(endpointInterface = "org.kuali.student.lum.program.service.ProgramService", serviceName = "ProgramService", portName = "ProgramService", targetNamespace = "http://student.kuali.org/wsdl/program")
@Transactional(rollbackFor = {Throwable.class})
public class ProgramServiceImpl implements ProgramService {
    final static Logger LOG = Logger.getLogger(ProgramServiceImpl.class);

    private LuService luService;
    private Validator validator;
    private BusinessServiceMethodInvoker programServiceMethodInvoker;
    private DictionaryService dictionaryService;
    private SearchManager searchManager;
    private MajorDisciplineAssembler majorDisciplineAssembler;
    private ProgramRequirementAssembler programRequirementAssembler;

    @Override
    public CredentialProgramInfo createCredentialProgram(
            CredentialProgramInfo credentialProgramInfo)
            throws AlreadyExistsException, DataValidationErrorException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
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
        // TODO Auto-generated method stub
        return null;
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
            LOG.error("Error disassembling Major Discipline", e);
            throw new OperationFailedException("Error disassembling Major Discipline");
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
        // TODO Auto-generated method stub
        return null;
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

            StatusInfo status = new StatusInfo();
            status.setSuccess(true);
            return status;

        } catch (AssemblyException e) {
            LOG.error("Error disassembling course", e);
            throw new OperationFailedException("Error disassembling course");
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
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CredentialProgramInfo getCredentialProgram(String credentialProgramId)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
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

        CluInfo clu = luService.getClu(majorDisciplineId);

        MajorDisciplineInfo majorDiscipline = null;
        try {
            majorDiscipline = majorDisciplineAssembler.assemble(clu, null, false);
        } catch (AssemblyException e) {
            LOG.error("Error assembling course", e);
            throw new OperationFailedException("Error assembling course");
        }

        // return majorDiscipline;
        try {
            return new MajorDisciplineDataGenerator().getMajorDisciplineInfoTestData();
        } catch (Exception e) {
            return null;
        }
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
    public ProgramRequirementInfo getProgramRequirement(String programRequirementId) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {

        checkForMissingParameter(programRequirementId, "programRequirementId");

        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<ProgramVariationInfo> getVariationsByMajorDisciplineId(
            String majorDisciplineId) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CredentialProgramInfo updateCredentialProgram(
            CredentialProgramInfo credentialProgramInfo)
            throws DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            VersionMismatchException, OperationFailedException,
            PermissionDeniedException {
        // TODO Auto-generated method stub
        return null;
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
            LOG.error("Error disassembling course", e);
            throw new OperationFailedException("Error disassembling course");
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
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<ValidationResultInfo> validateCredentialProgram(
            String validationType, CredentialProgramInfo credentialProgramInfo)
            throws InvalidParameterException,
            MissingParameterException, OperationFailedException {
        // TODO Auto-generated method stub
        return null;
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
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ObjectStructureDefinition getObjectStructure(String objectTypeKey) {
        return dictionaryService.getObjectStructure(objectTypeKey);
    }

    @Override
    public List<String> getObjectTypes() {
        // TODO Auto-generated method stub
        return null;
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

    /**
     * @param param
     * @param paramName
     * @throws MissingParameterException
     */
    @SuppressWarnings("unused")
    // TODO - will we be using this?
    private void checkForEmptyList(Object param, String paramName)
            throws MissingParameterException {
        if (param != null && param instanceof List<?> && ((List<?>) param).size() == 0) {
            throw new MissingParameterException(paramName + " can not be an empty list");
        }
    }

    // TODO - when CRUD for a second ProgramInfo is implemented, pull common code up from its process*() and this

    private MajorDisciplineInfo processMajorDisciplineInfo(MajorDisciplineInfo majorDisciplineInfo, NodeOperation operation) throws AssemblyException {

        BaseDTOAssemblyNode<MajorDisciplineInfo, CluInfo> results = majorDisciplineAssembler.disassemble(majorDisciplineInfo, operation);

        // Use the results to make the appropriate service calls here
        try {
            programServiceMethodInvoker.invokeServiceCalls(results);
        } catch (Exception e) {
            throw new AssemblyException(e);
        }
        return results.getBusinessDTORef();
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

    public void setProgramRequirementAssembler(ProgramRequirementAssembler programRequirementAssembler) {
        this.programRequirementAssembler = programRequirementAssembler;
    }

    public void setProgramServiceMethodInvoker(BusinessServiceMethodInvoker serviceMethodInvoker) {
        this.programServiceMethodInvoker = serviceMethodInvoker;
    }

    public void setValidator(Validator validator) {
        this.validator = validator;
    }
}
