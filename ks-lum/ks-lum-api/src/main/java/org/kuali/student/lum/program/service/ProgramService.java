/*
 * Copyright 2009 The Kuali Foundation
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
package org.kuali.student.lum.program.service;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

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
import org.kuali.student.core.search.service.SearchService;
import org.kuali.student.core.validation.dto.ValidationResultInfo;

import org.kuali.student.lum.program.dto.CredentialProgramInfo;
import org.kuali.student.lum.program.dto.HonorsProgramInfo;
import org.kuali.student.lum.program.dto.LuTypeInfo;
import org.kuali.student.lum.program.dto.MajorDisciplineInfo;
import org.kuali.student.lum.program.dto.MinorDisciplineInfo;
import org.kuali.student.lum.program.dto.ProgramRequirementInfo;
import org.kuali.student.lum.program.dto.ProgramVariationInfo;

/**
 *
 * @Author KSContractMojo
 * @Author Li Pan
 * @Since Wed Jun 30 14:55:41 PDT 2010
 * @See <a href="https://test.kuali.org/confluence/display/KULSTU/Program+Service">ProgramService</>
 *
 */
@WebService(name = "ProgramService", targetNamespace = "http://student.kuali.org/wsdl/program") // TODO CHECK THESE VALUES
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface ProgramService extends DictionaryService, SearchService{ 
    /** 
     * Retrieves the list of credential program types
     * @return list of credential program type information
     * @throws OperationFailedException unable to complete request
	 */
    public List<LuTypeInfo> getCredentialProgramTypes() throws OperationFailedException;

    /** 
     * Retrieves information about a credential program type
     * @param credentialProgramTypeKey Key of the Credential Program Type
     * @return information about a Credential Program Type
     * @throws DoesNotExistException credentialProgramType not found
     * @throws InvalidParameterException invalid credentialProgramType
     * @throws MissingParameterException missing credentialProgramType
     * @throws OperationFailedException unable to complete request
	 */
    public LuTypeInfo getCredentialProgramType(@WebParam(name="credentialProgramTypeKey")String credentialProgramTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves a CredentialProgram
     * @param credentialProgramId Unique Id of the CredentialProgram. Maps to cluId
     * @return the created Credential Program
     * @throws DoesNotExistException CredentialProgram does not exist
     * @throws InvalidParameterException invalid Credential Program
     * @throws MissingParameterException missing Credential Program
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public CredentialProgramInfo getCredentialProgram(@WebParam(name="credentialProgramId")String credentialProgramId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves a MajorDiscipline
     * @param majorDisciplineId Unique Id of the MajorDiscipline. Maps to cluId
     * @return the created MajorDiscipline
     * @throws DoesNotExistException MajorDiscipline does not exist
     * @throws InvalidParameterException invalid MajorDiscipline
     * @throws MissingParameterException missing MajorDiscipline
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public MajorDisciplineInfo getMajorDiscipline(@WebParam(name="majorDisciplineId")String majorDisciplineId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves the list of Major Discipline identifiers a given Credential Program Type.
     * @param programType Type of Credential Program
     * @return List of Major Discipline identifiers for the given Credential Program Type
     * @throws DoesNotExistException program type not found
     * @throws InvalidParameterException invalid program type
     * @throws MissingParameterException program type is not specified
     * @throws OperationFailedException unable to complete request
	 */
    public List<String> getMajorIdsByCredentialProgramType(@WebParam(name="programType")String programType) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves a list of program variations for a particular major
     * @param majorDisciplineId Major Discipline Identifier
     * @return list of program variations associated with the specified Major
     * @throws DoesNotExistException Major not found
     * @throws InvalidParameterException invalid majorDisciplineId
     * @throws MissingParameterException majorDisciplineId not specified
     * @throws OperationFailedException unable to complete request
	 */
    public List<ProgramVariationInfo> getVariationsByMajorDisciplineId(@WebParam(name="majorDisciplineId")String majorDisciplineId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves a MinorDiscipline
     * @param minorDisciplineId Unique Id of the MinorDiscipline. Maps to cluId
     * @return the created MinorDiscipline
     * @throws DoesNotExistException MinorDiscipline does not exist
     * @throws InvalidParameterException invalid MinorDiscipline
     * @throws MissingParameterException missing MinorDiscipline
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public MinorDisciplineInfo getMinorDiscipline(@WebParam(name="minorDisciplineId")String minorDisciplineId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves the list of Minor Discipline Program a given Credential Program Type.
     * @param programType Type of Credential Program
     * @return List of Minor Disciplines for the given Credential Program Type
     * @throws DoesNotExistException program type not found
     * @throws InvalidParameterException invalid program type
     * @throws MissingParameterException program type is not specified
     * @throws OperationFailedException unable to complete request
	 */
    public List<String> getMinorsByCredentialProgramType(@WebParam(name="programType")String programType) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves a HonorsProgram
     * @param honorsProgramId Unique Id of the HonorsProgram. Maps to cluId
     * @return the created Honors Program
     * @throws DoesNotExistException HonorsProgram does not exist
     * @throws InvalidParameterException invalid Honors Program
     * @throws MissingParameterException missing Honors Program
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public HonorsProgramInfo getHonorsProgram(@WebParam(name="honorsProgramId")String honorsProgramId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves the list of Honors Program Program a given Credential Program Type.
     * @param programType Type of Credential Program
     * @return List of Honors Programs for the given Credential Program Type
     * @throws DoesNotExistException program type not found
     * @throws InvalidParameterException invalid program type
     * @throws MissingParameterException program type is not specified
     * @throws OperationFailedException unable to complete request
	 */
    public List<String> getHonorsByCredentialProgramType(@WebParam(name="programType")String programType) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves a ProgramRequirement
     * @param programRequirementId Unique Id of the ProgramRequirement. Maps to cluId
     * @return the created Program Requirement
     * @throws DoesNotExistException Program Requirement does not exist
     * @throws InvalidParameterException invalid Program Requirement
     * @throws MissingParameterException missing Program Requirement
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public ProgramRequirementInfo getProgramRequirement(@WebParam(name="programRequirementId")String programRequirementId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Creates a Credential Program
     * @param credentialProgramInfo credentialProgramInfo
     * @return the created Credential Program
     * @throws AlreadyExistsException The Credential Program already exists
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws InvalidParameterException invalid Credential Program
     * @throws MissingParameterException missing Credential Program
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public CredentialProgramInfo createCredentialProgram(@WebParam(name="credentialProgramInfo")CredentialProgramInfo credentialProgramInfo) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Updates a Credential Program
     * @param credentialProgramInfo credentialProgramInfo
     * @return updated Credential Program
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws DoesNotExistException Credential Program not found
     * @throws InvalidParameterException invalid Credential Program
     * @throws MissingParameterException missing Credential Program
     * @throws VersionMismatchException The action was attempted on an out of date version
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public CredentialProgramInfo updateCredentialProgram(@WebParam(name="credentialProgramInfo")CredentialProgramInfo credentialProgramInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, VersionMismatchException, OperationFailedException, PermissionDeniedException;

    /** 
     * Deletes a Credential Program
     * @param credentialProgramId identifier for credentialProgramId.Maps to cluId
     * @return status of the operation (success or failure)
     * @throws DoesNotExistException Credential Program does not exist
     * @throws InvalidParameterException invalid credentialProgramId
     * @throws MissingParameterException invalid credentialProgramId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public StatusInfo deleteCredentialProgram(@WebParam(name="credentialProgramId")String credentialProgramId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Validates a Credential Program against its data dictionary
     * @param validationType identifier of the extent of validation
     * @param credentialProgramInfo Credential Program information to be tested
     * @return results from performing the validation
     * @throws DoesNotExistException validationTypeKey not found
     * @throws InvalidParameterException invalid validationTypeKey, cluInfo
     * @throws MissingParameterException missing validationTypeKey, cluInfo
     * @throws OperationFailedException unable to complete request
	 */
    public List<ValidationResultInfo> validateCredentialProgram(@WebParam(name="validationType")String validationType, @WebParam(name="credentialProgramInfo")CredentialProgramInfo credentialProgramInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Creates a Major Discipline Program
     * @param majorDisciplineInfo majorDisciplineInfo
     * @return the created Major Discipline
     * @throws AlreadyExistsException The Major already exists
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws InvalidParameterException invalid Major
     * @throws MissingParameterException missing Major
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public MajorDisciplineInfo createMajorDiscipline(@WebParam(name="majorDisciplineInfo")MajorDisciplineInfo majorDisciplineInfo) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Updates a Major Discipline
     * @param majorDisciplineInfo majorDisciplineInfo
     * @return updated Major Discipline
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws DoesNotExistException Major not found
     * @throws InvalidParameterException invalid Major
     * @throws MissingParameterException missing Major
     * @throws VersionMismatchException The action was attempted on an out of date version
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public MajorDisciplineInfo updateMajorDiscipline(@WebParam(name="majorDisciplineInfo")MajorDisciplineInfo majorDisciplineInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, VersionMismatchException, OperationFailedException, PermissionDeniedException;

    /** 
     * Deletes a Major Discipline
     * @param majorDisciplineId identifier for majorDisciplineId.Maps to cluId
     * @return status of the operation (success or failure)
     * @throws DoesNotExistException Major does not exist
     * @throws InvalidParameterException invalid majorDisciplineId
     * @throws MissingParameterException invalid majorDisciplineId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public StatusInfo deleteMajorDiscipline(@WebParam(name="majorDisciplineId")String majorDisciplineId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Validates a Major discipline against its data dictionary
     * @param validationType identifier of the extent of validation
     * @param majorDisciplineInfo Major discipline information to be tested
     * @return results from performing the validation
     * @throws DoesNotExistException validationTypeKey not found
     * @throws InvalidParameterException invalid validationTypeKey, cluInfo
     * @throws MissingParameterException missing validationTypeKey, cluInfo
     * @throws OperationFailedException unable to complete request
	 */
    public List<ValidationResultInfo> validateMajorDiscipline(@WebParam(name="validationType")String validationType, @WebParam(name="majorDisciplineInfo")MajorDisciplineInfo majorDisciplineInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Creates a Minor Discipline Program
     * @param minorDisciplineInfo minorDisciplineInfo
     * @return the created Minor Discipline
     * @throws AlreadyExistsException The Minor already exists
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws InvalidParameterException invalid Minor
     * @throws MissingParameterException missing Minor
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public MinorDisciplineInfo createMinorDiscipline(@WebParam(name="minorDisciplineInfo")MinorDisciplineInfo minorDisciplineInfo) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Updates a Minor Discipline
     * @param minorDisciplineInfo minorDisciplineInfo
     * @return updated Minor Discipline
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws DoesNotExistException Minor not found
     * @throws InvalidParameterException invalid Minor
     * @throws MissingParameterException missing Minor
     * @throws VersionMismatchException The action was attempted on an out of date version
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public MinorDisciplineInfo updateMinorDiscipline(@WebParam(name="minorDisciplineInfo")MinorDisciplineInfo minorDisciplineInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, VersionMismatchException, OperationFailedException, PermissionDeniedException;

    /** 
     * Deletes a Minor Discipline
     * @param minorDisciplineId identifier for minorDisciplineId.Maps to cluId
     * @return status of the operation (success or failure)
     * @throws DoesNotExistException Minor does not exist
     * @throws InvalidParameterException invalid minorDisciplineId
     * @throws MissingParameterException invalid minorDisciplineId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public StatusInfo deleteMinorDiscipline(@WebParam(name="minorDisciplineId")String minorDisciplineId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Validates a Minor discipline against its data dictionary
     * @param validationType identifier of the extent of validation
     * @param minorDisciplineInfo Minor discipline information to be tested
     * @return results from performing the validation
     * @throws DoesNotExistException validationTypeKey not found
     * @throws InvalidParameterException invalid validationTypeKey, cluInfo
     * @throws MissingParameterException missing validationTypeKey, cluInfo
     * @throws OperationFailedException unable to complete request
	 */
    public List<ValidationResultInfo> validateMinorDiscipline(@WebParam(name="validationType")String validationType, @WebParam(name="minorDisciplineInfo")MinorDisciplineInfo minorDisciplineInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Creates a Honors Program
     * @param honorsProgramInfo honorsProgramInfo
     * @return the created Honors Program
     * @throws AlreadyExistsException The Honors Program already exists
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws InvalidParameterException invalid Honors Program
     * @throws MissingParameterException missing Honors Program
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public HonorsProgramInfo createHonorsProgram(@WebParam(name="honorsProgramInfo")HonorsProgramInfo honorsProgramInfo) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Updates a Honors Program
     * @param honorsProgramInfo honorsProgramInfo
     * @return updated Honors Program
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws DoesNotExistException Honors Program not found
     * @throws InvalidParameterException invalid Honors Program
     * @throws MissingParameterException missing Honors Program
     * @throws VersionMismatchException The action was attempted on an out of date version
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public HonorsProgramInfo updateHonorsProgram(@WebParam(name="honorsProgramInfo")HonorsProgramInfo honorsProgramInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, VersionMismatchException, OperationFailedException, PermissionDeniedException;

    /** 
     * Deletes a Honors Program
     * @param honorsProgramId identifier for honorsProgramId.Maps to cluId
     * @return status of the operation (success or failure)
     * @throws DoesNotExistException Honors Program does not exist
     * @throws InvalidParameterException invalid honorsProgramId
     * @throws MissingParameterException invalid honorsProgramId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public StatusInfo deleteHonorsProgram(@WebParam(name="honorsProgramId")String honorsProgramId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Validates a Honors Program against its data dictionary
     * @param validationType identifier of the extent of validation
     * @param honorsProgramInfo Honors Program information to be tested
     * @return results from performing the validation
     * @throws DoesNotExistException validationTypeKey not found
     * @throws InvalidParameterException invalid validationTypeKey, cluInfo
     * @throws MissingParameterException missing validationTypeKey, cluInfo
     * @throws OperationFailedException unable to complete request
	 */
    public List<ValidationResultInfo> validateHonorsProgram(@WebParam(name="validationType")String validationType, @WebParam(name="honorsProgramInfo")HonorsProgramInfo honorsProgramInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Creates a Program Requirement
     * @param programRequirementInfo programRequirementInfo
     * @return the created Program Requirement
     * @throws AlreadyExistsException The Program Requirement already exists
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws InvalidParameterException invalid Program Requirement
     * @throws MissingParameterException missing Program Requirement
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public ProgramRequirementInfo createProgramRequirement(@WebParam(name="programRequirementInfo")ProgramRequirementInfo programRequirementInfo) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Updates a Program Requirement
     * @param programRequirementInfo programRequirementInfo
     * @return updated Program Requirement
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws DoesNotExistException Program Requirement not found
     * @throws InvalidParameterException invalid Program Requirement
     * @throws MissingParameterException missing Program Requirement
     * @throws VersionMismatchException The action was attempted on an out of date version
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public ProgramRequirementInfo updateProgramRequirement(@WebParam(name="programRequirementInfo")ProgramRequirementInfo programRequirementInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, VersionMismatchException, OperationFailedException, PermissionDeniedException;

    /** 
     * Deletes a Program Requirement
     * @param programRequirementId identifier for programRequirementId.Maps to cluId
     * @return status of the operation (success or failure)
     * @throws DoesNotExistException Program Requirement does not exist
     * @throws InvalidParameterException invalid programRequirementId
     * @throws MissingParameterException invalid programRequirementId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public StatusInfo deleteProgramRequirement(@WebParam(name="programRequirementId")String programRequirementId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Validates a Program Requirement against its data dictionary
     * @param validationType identifier of the extent of validation
     * @param programRequirementInfo Program Requirement information to be tested
     * @return results from performing the validation
     * @throws DoesNotExistException validationTypeKey not found
     * @throws InvalidParameterException invalid validationTypeKey, cluInfo
     * @throws MissingParameterException missing validationTypeKey, cluInfo
     * @throws OperationFailedException unable to complete request
	 */
    public List<ValidationResultInfo> validateProgramRequirement(@WebParam(name="validationType")String validationType, @WebParam(name="programRequirementInfo")ProgramRequirementInfo programRequirementInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

}