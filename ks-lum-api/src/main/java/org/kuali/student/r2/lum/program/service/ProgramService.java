/*
 * Copyright 2009 The Kuali Foundation Licensed under the Educational Community
 * License, Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.opensource.org/licenses/ecl1.php Unless required by applicable law
 * or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.kuali.student.r2.lum.program.service;

import java.util.Date;
import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.r1.common.dictionary.service.DictionaryService;
import org.kuali.student.r2.core.search.service.SearchService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.IllegalVersionSequencingException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;

import org.kuali.student.r2.common.util.constants.ProgramServiceConstants;
import org.kuali.student.r2.core.versionmanagement.service.VersionManagementService;
import org.kuali.student.r2.lum.program.dto.CredentialProgramInfo;
import org.kuali.student.r2.lum.program.dto.HonorsProgramInfo;
import org.kuali.student.r2.lum.program.dto.MajorDisciplineInfo;
import org.kuali.student.r2.lum.program.dto.MinorDisciplineInfo;

import org.kuali.student.r2.lum.program.dto.CoreProgramInfo;
import org.kuali.student.r2.lum.program.dto.ProgramRequirementInfo;
import org.kuali.student.r2.lum.program.dto.ProgramVariationInfo;
import org.kuali.student.r2.lum.program.dto.TrackInfo;

/**
 * The Program Service allows for the creation and management of programs.
 * 
 * @version 2.0
 * @Author sambitpa@usc.edu
 */
@WebService(name = "ProgramService", targetNamespace = ProgramServiceConstants.PROGRAM_NAMESPACE)
// TODO CHECK THESE VALUES
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface ProgramService extends  SearchService,  VersionManagementService, DictionaryService
{

    /**
     * Retrieves a CredentialProgram
     * 
     * @param credentialProgramId Unique Id of the CredentialProgram. Maps to
     *            cluId
     * @return the created Credential Program
     * @throws DoesNotExistException CredentialProgram does not exist
     * @throws InvalidParameterException invalid Credential Program
     * @throws MissingParameterException missing Credential Program
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     */
    public CredentialProgramInfo getCredentialProgram(@WebParam(name = "credentialProgramId") String credentialProgramId, @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of CredentialPrograms corresponding to the given list of
     * CredentialProgram Ids
     * 
     * @param credentialProgramIds list of CredentialPrograms to be retrieved
     * @param contextInfo Context information containing the principalId and
     *            locale information about the caller of service operation
     * @return list of CredentialProgram
     * @throws DoesNotExistException an commentKey in list not found
     * @throws InvalidParameterException invalid commentKey
     * @throws MissingParameterException commentIds, contextInfo not specified
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<CredentialProgramInfo> getCredentialProgramsByIds(@WebParam(name = "credentialProgramIds") List<String> credentialProgramIds, @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Validates a Credential Program against its data dictionary
     * 
     * @param validationType identifier of the extent of validation
     * @param credentialProgramInfo Credential Program information to be tested
     * @return results from performing the validation
     * @throws InvalidParameterException invalid validationTypeKey, cluInfo
     * @throws MissingParameterException missing validationTypeKey, cluInfo
     * @throws OperationFailedException unable to complete request
     */
    public List<ValidationResultInfo> validateCredentialProgram(@WebParam(name = "validationType") String validationType,
            @WebParam(name = "credentialProgramInfo") CredentialProgramInfo credentialProgramInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException, OperationFailedException;

    /**
     * Creates a Credential Program
     * 
     * @param credentialProgramInfo credentialProgramInfo
     * @return the created Credential Program
     * @throws AlreadyExistsException The Credential Program already exists
     * @throws DataValidationErrorException One or more values invalid for this
     *             operation
     * @throws InvalidParameterException invalid Credential Program
     * @throws MissingParameterException missing Credential Program
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public CredentialProgramInfo createCredentialProgram(@WebParam(name = "credentialProgramTypeKey") String credentialProgramTypeKey,
            @WebParam(name = "credentialProgramInfo") CredentialProgramInfo credentialProgramInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws AlreadyExistsException,
            DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Creates a new Credential Program version based on the current Credential
     * Program
     * 
     * @param credentialProgramId identifier for the Credential Program to be
     *            versioned
     * @param versionComment comment for the current version
     * @return the new versioned Credential Program information
     * @throws DoesNotExistException Credential Program does not exist
     * @throws InvalidParameterException invalid credentialProgramId
     * @throws MissingParameterException invalid credentialProgramId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws VersionMismatchException The action was attempted on an out of
     *             date version
     * @throws DataValidationErrorException
     * @throws ReadOnlyException 
     */
    public CredentialProgramInfo createNewCredentialProgramVersion(@WebParam(name = "credentialProgramId") String credentialProgramId, @WebParam(name = "versionComment") String versionComment,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, VersionMismatchException, DataValidationErrorException, ReadOnlyException;

    /**
     * Sets a specific version of the Credential Program as current. The
     * sequence number must be greater than the existing current Credential
     * Program version. This will truncate the current version's end date to the
     * currentVersionStart param. If a Major exists which is set to become
     * current in the future, that Major's currentVersionStart and
     * CurrentVersionEnd will be nullified. The currentVersionStart must be in
     * the future to prevent changing historic data.
     * 
     * @param credentialProgramId Version Specific Id of the Credential Program
     * @param currentVersionStart Date when this Credential Program becomes
     *            current. Must be in the future and be after the most current
     *            Credential Program's start date.
     * @return status of the operation (success or failure)
     * @throws DoesNotExistException Credential Program for credentialProgramId
     *             does not exist
     * @throws InvalidParameterException invalid credentialProgramId,
     *             currentVersionStart
     * @throws MissingParameterException invalid credentialProgramId
     * @throws IllegalVersionSequencingException a Credential Program with
     *             higher sequence number from the one provided is marked
     *             current
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws DataValidationErrorException 
     */
    public StatusInfo setCurrentCredentialProgramVersion(@WebParam(name = "credentialProgramId") String credentialProgramId, @WebParam(name = "currentVersionStart") Date currentVersionStart,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, IllegalVersionSequencingException,
            OperationFailedException, PermissionDeniedException, DataValidationErrorException;

    /**
     * Updates a Credential Program
     * 
     * @param credentialProgramInfo credentialProgramInfo
     * @return updated Credential Program
     * @throws DataValidationErrorException One or more values invalid for this
     *             operation
     * @throws DoesNotExistException Credential Program not found
     * @throws InvalidParameterException invalid Credential Program
     * @throws MissingParameterException missing Credential Program
     * @throws VersionMismatchException The action was attempted on an out of
     *             date version
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public CredentialProgramInfo updateCredentialProgram(@WebParam(name = "credentialProgramId") String credentialProgramId,
            @WebParam(name = "credentialProgramInfo") CredentialProgramInfo credentialProgramInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException,
            DoesNotExistException, InvalidParameterException, MissingParameterException, VersionMismatchException, OperationFailedException, PermissionDeniedException;

    /**
     * Deletes a Credential Program
     * 
     * @param credentialProgramId identifier for credentialProgramId.Maps to
     *            cluId
     * @return status of the operation (success or failure)
     * @throws DoesNotExistException Credential Program does not exist
     * @throws InvalidParameterException invalid credentialProgramId
     * @throws MissingParameterException invalid credentialProgramId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteCredentialProgram(@WebParam(name = "credentialProgramId") String credentialProgramId, @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Searches for CredentialProgramIds that meet the given search criteria.
     *
     * @param criteria the search criteria
     * @param contextInfo information containing the principalId and locale information about the caller of the service operation
     * @return a list of CredentialProgram identifiers matching the criteria
     * @throws InvalidParameterException criteria or contextInfo is not valid
     * @throws MissingParameterException criteria or or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> searchForCredentialProgramIds(@WebParam(name = "criteria") QueryByCriteria criteria,
                                                @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Searches for CredentialPrograms that meet the given search criteria.
     *
     * @param criteria the search criteria
     * @param contextInfo information containing the principalId and locale information about the caller of the service operation
     * @return a list of CredentialPrograms matching the criteria
     * @throws InvalidParameterException criteria or contextInfo is not valid
     * @throws MissingParameterException criteria or or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<CredentialProgramInfo> searchForCredentialPrograms(@WebParam(name = "criteria") QueryByCriteria criteria,
                                                              @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a MajorDiscipline
     * 
     * @param majorDisciplineId Unique Id of the MajorDiscipline. Maps to cluId
     * @return the created MajorDiscipline
     * @throws DoesNotExistException MajorDiscipline does not exist
     * @throws InvalidParameterException invalid MajorDiscipline
     * @throws MissingParameterException missing MajorDiscipline
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public MajorDisciplineInfo getMajorDiscipline(@WebParam(name = "majorDisciplineId") String majorDisciplineId, @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of MajorDiscipline corresponding to the given list of
     * major discipline Ids
     * 
     * @param majorDisciplineIds list of MajorDisciplines to be retrieved
     * @param contextInfo Context information containing the principalId and
     *            locale information about the caller of service operation
     * @return list of MajorDiscipline
     * @throws DoesNotExistException an commentKey in list not found
     * @throws InvalidParameterException invalid commentKey
     * @throws MissingParameterException commentIds, contextInfo not specified
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<MajorDisciplineInfo> getMajorDisciplinesByIds(@WebParam(name = "majorDisciplineIds") List<String> majorDisciplineIds, @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves the list of Major Discipline identifiers a given Credential
     * Program Type.
     * 
     * @param programType Type of Credential Program
     * @return List of Major Discipline identifiers for the given Credential
     *         Program Type
     * @throws DoesNotExistException program type not found
     * @throws InvalidParameterException invalid program type
     * @throws MissingParameterException program type is not specified
     * @throws OperationFailedException unable to complete request
     */
    public List<String> getMajorDisciplineIdsByCredentialProgramType(@WebParam(name = "programType") String programType,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves a list of program variations for a particular major
     * @param majorDisciplineId Major Discipline Identifier
     * @return list of program variations associated with the specified Major
     * @throws DoesNotExistException Major not found
     * @throws InvalidParameterException invalid majorDisciplineId
     * @throws MissingParameterException majorDisciplineId not specified
     * @throws OperationFailedException unable to complete request
     */
    public List<ProgramVariationInfo> getProgramVariationsByMajorDiscipline(@WebParam(name = "majorDisciplineId") String majorDisciplineId,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException;

    /**
     * Validates a Major discipline against its data dictionary
     * 
     * @param validationType identifier of the extent of validation
     * @param majorDisciplineInfo Major discipline information to be tested
     * @return results from performing the validation
     * @throws InvalidParameterException invalid validationTypeKey, cluInfo
     * @throws MissingParameterException missing validationTypeKey, cluInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException 
     */
    public List<ValidationResultInfo> validateMajorDiscipline(@WebParam(name = "validationType") String validationType,
            @WebParam(name = "majorDisciplineInfo") MajorDisciplineInfo majorDisciplineInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Creates a Major Discipline Program
     * 
     * @param majorDisciplineInfo majorDisciplineInfo
     * @return the created Major Discipline
     * @throws AlreadyExistsException The Major already exists
     * @throws DataValidationErrorException One or more values invalid for this
     *             operation
     * @throws InvalidParameterException invalid Major
     * @throws MissingParameterException missing Major
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public MajorDisciplineInfo createMajorDiscipline(@WebParam(name = "majorDisciplineTypeKey") String majorDisciplineTypeKey,
            @WebParam(name = "majorDisciplineInfo") MajorDisciplineInfo majorDisciplineInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws AlreadyExistsException,
            DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Updates a Major Discipline
     * 
     * @param majorDisciplineInfo majorDisciplineInfo
     * @return updated Major Discipline
     * @throws DataValidationErrorException One or more values invalid for this
     *             operation
     * @throws DoesNotExistException Major not found
     * @throws InvalidParameterException invalid Major
     * @throws MissingParameterException missing Major
     * @throws VersionMismatchException The action was attempted on an out of
     *             date version
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public MajorDisciplineInfo updateMajorDiscipline(@WebParam(name = "majorDisciplineId") String majorDisciplineId, @WebParam(name = "majorDisciplineInfo") MajorDisciplineInfo majorDisciplineInfo,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException,
            VersionMismatchException, OperationFailedException, PermissionDeniedException;

    /**
     * Deletes a Major Discipline
     * 
     * @param majorDisciplineId identifier for majorDisciplineId.Maps to cluId
     * @return status of the operation (success or failure)
     * @throws DoesNotExistException Major does not exist
     * @throws InvalidParameterException invalid majorDisciplineId
     * @throws MissingParameterException invalid majorDisciplineId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteMajorDiscipline(@WebParam(name = "majorDisciplineId") String majorDisciplineId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Creates a new Major Discipline version based on the current Major
     * 
     * @param majorDisciplineId identifier for the Major Discipline to be
     *            versioned
     * @param versionComment comment for the current version
     * @return the new versioned Major Discipline information
     * @throws DoesNotExistException Major does not exist
     * @throws InvalidParameterException invalid majorDisciplineId
     * @throws MissingParameterException invalid majorDisciplineId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws VersionMismatchException The action was attempted on an out of
     *             date version
     * @throws DataValidationErrorException
     * @throws ReadOnlyException 
     */
    public MajorDisciplineInfo createNewMajorDisciplineVersion(@WebParam(name = "majorDisciplineId") String majorDisciplineId, @WebParam(name = "versionComment") String versionComment,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, VersionMismatchException, DataValidationErrorException, ReadOnlyException;

    /**
     * Searches for MajorDisciplineIds that meet the given search criteria.
     *
     * @param criteria the search criteria
     * @param contextInfo information containing the principalId and locale information about the caller of the service operation
     * @return a list of MajorDiscipline identifiers matching the criteria
     * @throws InvalidParameterException criteria or contextInfo is not valid
     * @throws MissingParameterException criteria or or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> searchForMajorDisciplineIds(@WebParam(name = "criteria") QueryByCriteria criteria,
                                                    @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Searches for MajorDisciplines that meet the given search criteria.
     *
     * @param criteria the search criteria
     * @param contextInfo information containing the principalId and locale information about the caller of the service operation
     * @return a list of MajorDisciplines matching the criteria
     * @throws InvalidParameterException criteria or contextInfo is not valid
     * @throws MissingParameterException criteria or or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<MajorDisciplineInfo> searchForMajorDisciplines(@WebParam(name = "criteria") QueryByCriteria criteria,
                                                                @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;


    /**
     * Retrieves a HonorsProgram
     * 
     * @param honorsProgramId Unique Id of the HonorsProgram. Maps to cluId
     * @return the created Honors Program
     * @throws DoesNotExistException HonorsProgram does not exist
     * @throws InvalidParameterException invalid Honors Program
     * @throws MissingParameterException missing Honors Program
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public HonorsProgramInfo getHonorsProgram(@WebParam(name = "honorsProgramId") String honorsProgramId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * 
     * Retrieves a list of honors program by Ids
     * 
     * @param honorsProgramIds
     * @param contextInfo
     * @return
     * @throws DoesNotExistException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public List<HonorsProgramInfo> getHonorsProgramsByIds(@WebParam(name = "honorsProgramIds") List<String> honorsProgramIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves the list of Honors Program Program a given Credential Program
     * Type.
     * 
     * @param programType Type of Credential Program
     * @return List of Honors Programs for the given Credential Program Type
     * @throws DoesNotExistException program type not found
     * @throws InvalidParameterException invalid program type
     * @throws MissingParameterException program type is not specified
     * @throws OperationFailedException unable to complete request
     */
    public List<String> getHonorProgramIdsByCredentialProgramType(@WebParam(name = "programType") String programType, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Validates a Honors Program against its data dictionary
     * 
     * @param validationType identifier of the extent of validation
     * @param honorsProgramInfo Honors Program information to be tested
     * @return results from performing the validation
     * @throws InvalidParameterException invalid validationTypeKey, cluInfo
     * @throws MissingParameterException missing validationTypeKey, cluInfo
     * @throws OperationFailedException unable to complete request
     */
    public List<ValidationResultInfo> validateHonorsProgram(@WebParam(name = "validationType") String validationType, @WebParam(name = "honorsProgramInfo") HonorsProgramInfo honorsProgramInfo,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Creates a Honors Program
     * 
     * @param honorsProgramInfo honorsProgramInfo
     * @return the created Honors Program
     * @throws AlreadyExistsException The Honors Program already exists
     * @throws DataValidationErrorException One or more values invalid for this
     *             operation
     * @throws InvalidParameterException invalid Honors Program
     * @throws MissingParameterException missing Honors Program
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public HonorsProgramInfo createHonorsProgram(@WebParam(name = "honorsProgramTypeKey") String honorsProgramTypeKey, @WebParam(name = "honorsProgramInfo") HonorsProgramInfo honorsProgramInfo,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException;

    /**
     * Updates a Honors Program
     * 
     * @param honorsProgramInfo honorsProgramInfo
     * @return updated Honors Program
     * @throws DataValidationErrorException One or more values invalid for this
     *             operation
     * @throws DoesNotExistException Honors Program not found
     * @throws InvalidParameterException invalid Honors Program
     * @throws MissingParameterException missing Honors Program
     * @throws VersionMismatchException The action was attempted on an out of
     *             date version
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public HonorsProgramInfo updateHonorsProgram(@WebParam(name = "honorsProgramId") String honorsProgramId, @WebParam(name = "honorsProgramTypeKey") String honorsProgramTypeKey,
            @WebParam(name = "honorsProgramInfo") HonorsProgramInfo honorsProgramInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException,
            DoesNotExistException, InvalidParameterException, MissingParameterException, VersionMismatchException, OperationFailedException, PermissionDeniedException;

    /**
     * Deletes a Honors Program
     * 
     * @param honorsProgramId identifier for honorsProgramId.Maps to cluId
     * @return status of the operation (success or failure)
     * @throws DoesNotExistException Honors Program does not exist
     * @throws InvalidParameterException invalid honorsProgramId
     * @throws MissingParameterException invalid honorsProgramId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteHonorsProgram(@WebParam(name = "honorsProgramId") String honorsProgramId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Searches for HonorsProgramIds that meet the given search criteria.
     *
     * @param criteria the search criteria
     * @param contextInfo information containing the principalId and locale information about the caller of the service operation
     * @return a list of HonorsProgram identifiers matching the criteria
     * @throws InvalidParameterException criteria or contextInfo is not valid
     * @throws MissingParameterException criteria or or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> searchForHonorsProgramIds(@WebParam(name = "criteria") QueryByCriteria criteria,
                                                      @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Searches for HonorsPrograms that meet the given search criteria.
     *
     * @param criteria the search criteria
     * @param contextInfo information containing the principalId and locale information about the caller of the service operation
     * @return a list of HonorsPrograms matching the criteria
     * @throws InvalidParameterException criteria or contextInfo is not valid
     * @throws MissingParameterException criteria or or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<HonorsProgramInfo> searchForHonorsPrograms(@WebParam(name = "criteria") QueryByCriteria criteria,
                                                           @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a Core Program
     * 
     * @param coreProgramId Unique Id of the Core Program. Maps to cluId
     * @return the Core Program
     * @throws DoesNotExistException Program Requirement does not exist
     * @throws InvalidParameterException invalid Program Requirement
     * @throws MissingParameterException missing Program Requirement
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public CoreProgramInfo getCoreProgram(@WebParam(name = "coreProgramId") String coreProgramId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * 
     * Retrieves a list of core program by Ids
     * 
     * @param coreProgramIds
     * @param contextInfo
     * @return
     * @throws DoesNotExistException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public List<CoreProgramInfo> getCoreProgramsByIds(@WebParam(name = "coreProgramIds") List<String> coreProgramIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Validates a Core Program against its data dictionary
     * 
     * @param validationType identifier of the extent of validation
     * @param coreProgramInfo Core Program information to be tested
     * @return results from performing the validation
     * @throws InvalidParameterException invalid validationTypeKey, cluInfo
     * @throws MissingParameterException missing validationTypeKey, cluInfo
     * @throws OperationFailedException unable to complete request
     */
    public List<ValidationResultInfo> validateCoreProgram(@WebParam(name = "validationType") String validationType, @WebParam(name = "coreProgramInfo") CoreProgramInfo coreProgramInfo,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Creates a Core Program
     * 
     * @param coreProgramInfo coreProgramInfo
     * @return the created Core Program
     * @throws AlreadyExistsException The Core Program already exists
     * @throws DataValidationErrorException One or more values invalid for this
     *             operation
     * @throws InvalidParameterException invalid Core Program
     * @throws MissingParameterException missing Core Program
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public CoreProgramInfo createCoreProgram(@WebParam(name = "coreProgramTypeKey") String coreProgramTypeKey, @WebParam(name = "coreProgramInfo") CoreProgramInfo coreProgramInfo,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, DoesNotExistException;

    /**
     * Creates a new Core Program version based on the current Core Program
     * 
     * @param coreProgramId identifier for the Core Program to be versioned
     * @param versionComment comment for the current version
     * @return the new versioned Core Program information
     * @throws DoesNotExistException Core Program does not exist
     * @throws InvalidParameterException invalid coreProgramId
     * @throws MissingParameterException invalid coreProgramId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws VersionMismatchException The action was attempted on an out of
     *             date version
     * @throws DataValidationErrorException
     * @throws ReadOnlyException 
     */
    public CoreProgramInfo createNewCoreProgramVersion(@WebParam(name = "coreProgramId") String coreProgramId, @WebParam(name = "versionComment") String versionComment,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, VersionMismatchException, DataValidationErrorException, ReadOnlyException;

    /**
     * Sets a specific version of the Core Program as current. The sequence
     * number must be greater than the existing current Core Program version.
     * This will truncate the current version's end date to the
     * currentVersionStart param. If a Major exists which is set to become
     * current in the future, that Major's currentVersionStart and
     * CurrentVersionEnd will be nullified. The currentVersionStart must be in
     * the future to prevent changing historic data.
     * 
     * @param coreProgramId Version Specific Id of the Core Program
     * @param currentVersionStart Date when this Core Program becomes current.
     *            Must be in the future and be after the most current major's
     *            start date.
     * @return status of the operation (success or failure)
     * @throws DoesNotExistException Core Program for coreProgramId does not
     *             exist
     * @throws InvalidParameterException invalid coreProgramId,
     *             currentVersionStart
     * @throws MissingParameterException invalid coreProgramId
     * @throws IllegalVersionSequencingException a CoreProgram with higher
     *             sequence number from the one provided is marked current
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws DataValidationErrorException 
     */
    public StatusInfo setCurrentCoreProgramVersion(@WebParam(name = "coreProgramId") String coreProgramId, @WebParam(name = "currentVersionStart") Date currentVersionStart,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, IllegalVersionSequencingException,
            OperationFailedException, PermissionDeniedException, DataValidationErrorException;

    /**
     * Updates a Core Program
     * 
     * @param coreProgramInfo coreProgramInfo
     * @return updated Core Program
     * @throws DataValidationErrorException One or more values invalid for this
     *             operation
     * @throws DoesNotExistException Core Program not found
     * @throws InvalidParameterException invalid Core Program
     * @throws MissingParameterException missing Core Program
     * @throws VersionMismatchException The action was attempted on an out of
     *             date version
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public CoreProgramInfo updateCoreProgram(@WebParam(name = "coreProgramId") String coreProgramId, @WebParam(name = "coreProgramTypeKey") String coreProgramTypeKey,
            @WebParam(name = "coreProgramInfo") CoreProgramInfo coreProgramInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException, VersionMismatchException, OperationFailedException, PermissionDeniedException;

    /**
     * Deletes a Core Program
     * 
     * @param coreProgramId identifier for coreProgramId.Maps to cluId
     * @return status of the operation (success or failure)
     * @throws DoesNotExistException Core Program does not exist
     * @throws InvalidParameterException invalid coreProgramId
     * @throws MissingParameterException invalid coreProgramId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteCoreProgram(@WebParam(name = "coreProgramId") String coreProgramId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Searches for CoreProgramIds that meet the given search criteria.
     *
     * @param criteria the search criteria
     * @param contextInfo information containing the principalId and locale information about the caller of the service operation
     * @return a list of CoreProgram identifiers matching the criteria
     * @throws InvalidParameterException criteria or contextInfo is not valid
     * @throws MissingParameterException criteria or or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> searchForCoreProgramIds(@WebParam(name = "criteria") QueryByCriteria criteria,
                                                       @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Searches for CorePrograms that meet the given search criteria.
     *
     * @param criteria the search criteria
     * @param contextInfo information containing the principalId and locale information about the caller of the service operation
     * @return a list of CorePrograms matching the criteria
     * @throws InvalidParameterException criteria or contextInfo is not valid
     * @throws MissingParameterException criteria or or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<CoreProgramInfo> searchForCorePrograms(@WebParam(name = "criteria") QueryByCriteria criteria,
                                                                     @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a ProgramRequirement
     * 
     * @param programRequirementId Unique Id of the ProgramRequirement. Maps to
     *            cluId
     * @return the Program Requirement
     * @throws DoesNotExistException Program Requirement does not exist
     * @throws InvalidParameterException invalid Program Requirement
     * @throws MissingParameterException missing Program Requirement
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public ProgramRequirementInfo getProgramRequirement(@WebParam(name = "programRequirementId") String programRequirementId, @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * 
     * Retrieves a list of ProgramRequirements by Ids
     * 
     * @param programRequirementIds
     * @param contextInfo
     * @return
     * @throws DoesNotExistException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public List<ProgramRequirementInfo> getProgramRequirementsByIds(@WebParam(name = "programRequirementIds") List<String> programRequirementIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Validates a Program Requirement against its data dictionary
     * 
     * @param validationType identifier of the extent of validation
     * @param programRequirementInfo Program Requirement information to be
     *            tested
     * @return results from performing the validation
     * @throws InvalidParameterException invalid validationTypeKey, cluInfo
     * @throws MissingParameterException missing validationTypeKey, cluInfo
     * @throws OperationFailedException unable to complete request
     */
    public List<ValidationResultInfo> validateProgramRequirement(@WebParam(name = "validationType") String validationType,
            @WebParam(name = "programRequirementInfo") ProgramRequirementInfo programRequirementInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException, OperationFailedException;

    /**
     * Creates a Program Requirement
     * 
     * @param programRequirementInfo programRequirementInfo
     * @return the created Program Requirement
     * @throws AlreadyExistsException The Program Requirement already exists
     * @throws DataValidationErrorException One or more values invalid for this
     *             operation
     * @throws InvalidParameterException invalid Program Requirement
     * @throws MissingParameterException missing Program Requirement
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public ProgramRequirementInfo createProgramRequirement(@WebParam(name = "programRequirementTypeKey") String programRequirementTypeKey,
            @WebParam(name = "programRequirementInfo") ProgramRequirementInfo programRequirementInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws AlreadyExistsException,
            DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Updates a Program Requirement
     * 
     * @param programRequirementInfo programRequirementInfo
     * @return updated Program Requirement
     * @throws DataValidationErrorException One or more values invalid for this
     *             operation
     * @throws DoesNotExistException Program Requirement not found
     * @throws InvalidParameterException invalid Program Requirement
     * @throws MissingParameterException missing Program Requirement
     * @throws VersionMismatchException The action was attempted on an out of
     *             date version
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public ProgramRequirementInfo updateProgramRequirement(@WebParam(name = "programRequirementId") String programRequirementId,
            @WebParam(name = "programRequirementTypeKey") String programRequirementTypeKey, @WebParam(name = "programRequirementInfo") ProgramRequirementInfo programRequirementInfo,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException,
            VersionMismatchException, OperationFailedException, PermissionDeniedException;

    /**
     * Deletes a Program Requirement
     * 
     * @param programRequirementId identifier for programRequirementId.Maps to
     *            cluId
     * @return status of the operation (success or failure)
     * @throws DoesNotExistException Program Requirement does not exist
     * @throws InvalidParameterException invalid programRequirementId
     * @throws MissingParameterException invalid programRequirementId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteProgramRequirement(@WebParam(name = "programRequirementId") String programRequirementId, @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;
    /**
     * Searches for ProgramRequirementIds that meet the given search criteria.
     *
     * @param criteria the search criteria
     * @param contextInfo information containing the principalId and locale information about the caller of the service operation
     * @return a list of ProgramRequirement identifiers matching the criteria
     * @throws InvalidParameterException criteria or contextInfo is not valid
     * @throws MissingParameterException criteria or or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> searchForProgramRequirementIds(@WebParam(name = "criteria") QueryByCriteria criteria,
                                                       @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Searches for ProgramRequirements that meet the given search criteria.
     *
     * @param criteria the search criteria
     * @param contextInfo information containing the principalId and locale information about the caller of the service operation
     * @return a list of ProgramRequirements matching the criteria
     * @throws InvalidParameterException criteria or contextInfo is not valid
     * @throws MissingParameterException criteria or or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<ProgramRequirementInfo> searchForProgramRequirements(@WebParam(name = "criteria") QueryByCriteria criteria,
                                                                     @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Sets a specific version of the Major as current. The sequence number must
     * be greater than the existing current Major version. This will truncate
     * the current version's end date to the currentVersionStart param. If a
     * Major exists which is set to become current in the future, that Major's
     * currentVersionStart and CurrentVersionEnd will be nullified. The
     * currentVersionStart must be in the future to prevent changing historic
     * data.
     * 
     * @param majorDisciplineId Version Specific Id of the Major Discipline
     * @param currentVersionStart Date when this Major becomes current. Must be
     *            in the future and be after the most current major's start
     *            date.
     * @return status of the operation (success or failure)
     * @throws DoesNotExistException Major for majorVersionId does not exist
     * @throws InvalidParameterException invalid majorVersionId,
     *             currentVersionStart
     * @throws MissingParameterException invalid majorVersionId
     * @throws IllegalVersionSequencingException a Major with higher sequence
     *             number from the one provided is marked current
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws DataValidationErrorException 
     */
    public StatusInfo setCurrentMajorDisciplineVersion(@WebParam(name = "majorDisciplineId") String majorDisciplineId, @WebParam(name = "currentVersionStart") Date currentVersionStart,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, IllegalVersionSequencingException,
            OperationFailedException, PermissionDeniedException, DataValidationErrorException;

    /**
     * Retrieves a MinorDiscipline
     * 
     * @param minorDisciplineId Unique Id of the MinorDiscipline. Maps to cluId
     * @return the created MinorDiscipline
     * @throws DoesNotExistException MinorDiscipline does not exist
     * @throws InvalidParameterException invalid MinorDiscipline
     * @throws MissingParameterException missing MinorDiscipline
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public MinorDisciplineInfo getMinorDiscipline(@WebParam(name = "minorDisciplineId") String minorDisciplineId, @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of minorDisciplines from a list of minorDiscipline Ids.
     * The returned list may be in any order and if duplicate Ids are supplied, a unique set may or may not be returned.
     *
     * @param minorDisciplineIds a list of minorDiscipline identifiers
     * @param contextInfo     Context information containing the principalId and
     *                        locale information about the caller of service
     *                        operation
     * @return CluLoRelationInfo        a list of minorDisciplines
     * @throws DoesNotExistException    an minorDisciplineId in the list was not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException minorDisciplineIds, an Id in the minorDisciplineIds, or contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<MinorDisciplineInfo> getMinorDisciplinesByIds(@WebParam(name = "minorDisciplineIds") List<String> minorDisciplineIds, @WebParam(name = "contextInfo") ContextInfo contextInfo )
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of MinorDiscipline Ids by MinorDiscipline Type.
     * @param minorDisciplineTypeKey an identifier for a MinorDiscipline Type
     * @param contextInfo information containing the principalId and locale information about the caller of the service operation
     * @return a list of MinorDiscipline identifiers matching minorDisciplineTypeKey or an empty list if none found
     * @throws InvalidParameterException minorDisciplineTypeKey or contextInfo is not valid
     * @throws MissingParameterException minorDisciplineTypeKey or context is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> getMinorDisciplineIdsByType(@WebParam(name = "minorDisciplineTypeKey") String minorDisciplineTypeKey,
                                                    @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves the list of Minor Discipline Program a given Credential Program
     * Type.
     * 
     * @param programType Type of Credential Program
     * @return List of Minor Disciplines for the given Credential Program Type
     * @throws DoesNotExistException program type not found
     * @throws InvalidParameterException invalid program type
     * @throws MissingParameterException program type is not specified
     * @throws OperationFailedException unable to complete request
     */
    public List<String> getMinorsByCredentialProgramType(@WebParam(name = "programType") String programType, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Validates a Minor discipline against its data dictionary
     *
     * @param validationType identifier of the extent of validation
     * @param minorDisciplineInfo Minor discipline information to be tested
     * @return results from performing the validation
     * @throws InvalidParameterException invalid validationTypeKey, cluInfo
     * @throws MissingParameterException missing validationTypeKey, cluInfo
     * @throws OperationFailedException unable to complete request
     */
    public List<ValidationResultInfo> validateMinorDiscipline(@WebParam(name = "validationType") String validationType,
                                                              @WebParam(name = "minorDisciplineInfo") MinorDisciplineInfo minorDisciplineInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException, OperationFailedException;

    /**
     * Creates a Minor Discipline Program
     *
     * @param minorDisciplineInfo minorDisciplineInfo
     * @return the created Minor Discipline
     * @throws AlreadyExistsException The Minor already exists
     * @throws DataValidationErrorException One or more values invalid for this
     *             operation
     * @throws InvalidParameterException invalid Minor
     * @throws MissingParameterException missing Minor
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public MinorDisciplineInfo createMinorDiscipline(@WebParam(name = "minorDisciplineTypeKey") String minorDisciplineTypeKey,
                                                     @WebParam(name = "minorDisciplineInfo") MinorDisciplineInfo minorDisciplineInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws AlreadyExistsException,
            DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Updates a Minor Discipline
     *
     * @param minorDisciplineInfo minorDisciplineInfo
     * @return updated Minor Discipline
     * @throws DataValidationErrorException One or more values invalid for this
     *             operation
     * @throws DoesNotExistException Minor not found
     * @throws InvalidParameterException invalid Minor
     * @throws MissingParameterException missing Minor
     * @throws VersionMismatchException The action was attempted on an out of
     *             date version
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public MinorDisciplineInfo updateMinorDiscipline(@WebParam(name = "minorDisciplineId") String minorDisciplineId,
                                                     @WebParam(name = "minorDisciplineTypeKey") String minorDisciplineTypeKey,
                                                     @WebParam(name = "minorDisciplineInfo") MinorDisciplineInfo minorDisciplineInfo,
                                                     @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException,
            DoesNotExistException, InvalidParameterException, MissingParameterException, VersionMismatchException,
            OperationFailedException, PermissionDeniedException;

    /**
     * Deletes a Minor Discipline
     *
     * @param minorDisciplineId identifier for minorDisciplineId.Maps to cluId
     * @return status of the operation (success or failure)
     * @throws DoesNotExistException Minor does not exist
     * @throws InvalidParameterException invalid minorDisciplineId
     * @throws MissingParameterException invalid minorDisciplineId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteMinorDiscipline(@WebParam(name = "minorDisciplineId") String minorDisciplineId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     *
     * Creates a new Minor Discipline version based on the current Minor
     *
     * @param minorDisciplineId identifier for the Minor Discipline to be versioned
     * @param versionComment comment for the current version
     * @return the new versioned Minor Discipline information
     * @throws DoesNotExistException Minor does not exist
     * @throws InvalidParameterException invalid minorDisciplineId
     * @throws MissingParameterException invalid minorDisciplineId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws VersionMismatchException The action was attempted on an out of date version
     * @throws DataValidationErrorException
     * @throws ReadOnlyException
     */
    public MinorDisciplineInfo createNewMinorDisciplineVersion(@WebParam(name = "minorDisciplineId") String minorDisciplineId,
                                                               @WebParam(name = "versionComment") String versionComment,
                                                               @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, VersionMismatchException, DataValidationErrorException, ReadOnlyException;

    @Deprecated
    /**
     *
     * This method ...
     *
     * @param majorDisciplineId
     * @param contextInfo
     * @return
     * @throws DoesNotExistException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     */
    public List<ProgramVariationInfo> getVariationsByMajorDisciplineId(@WebParam(name="majorDisciplineId") String majorDisciplineId, @WebParam(name="contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException;

    /**
     * Searches for MinorDisciplineIds that meet the given search criteria.
     *
     * @param criteria the search criteria
     * @param contextInfo information containing the principalId and locale information about the caller of the service operation
     * @return a list of MinorDiscipline identifiers matching the criteria
     * @throws InvalidParameterException criteria or contextInfo is not valid
     * @throws MissingParameterException criteria or or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> searchForMinorDisciplineIds(@WebParam(name = "criteria") QueryByCriteria criteria,
                                                    @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Searches for MinorDisciplines that meet the given search criteria.
     *
     * @param criteria the search criteria
     * @param contextInfo information containing the principalId and locale information about the caller of the service operation
     * @return a list of MinorDisciplines matching the criteria
     * @throws InvalidParameterException criteria or contextInfo is not valid
     * @throws MissingParameterException criteria or or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<MinorDisciplineInfo> searchForMinorDisciplines(@WebParam(name = "criteria") QueryByCriteria criteria,
                                                               @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a Track
     *
     * @param trackId Unique Id of the Track. Maps to cluId
     * @return the created Track
     * @throws DoesNotExistException Track does not exist
     * @throws InvalidParameterException invalid Track
     * @throws MissingParameterException missing Track
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public TrackInfo getTrack(@WebParam(name = "trackId") String trackId, @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of tracks from a list of track Ids.
     * The returned list may be in any order and if duplicate Ids are supplied, a unique set may or may not be returned.
     *
     * @param trackIds a list of track identifiers
     * @param contextInfo     Context information containing the principalId and
     *                        locale information about the caller of service
     *                        operation
     * @return List<TrackInfo>       a list of tracks
     * @throws DoesNotExistException    an trackId in the list was not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException trackIds, an Id in the trackIds, or contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<TrackInfo> getTracksByIds(@WebParam(name = "trackIds") List<String> trackIds, @WebParam(name = "contextInfo") ContextInfo contextInfo )
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of Track Ids by Track Type.
     * @param trackTypeKey an identifier for a Track Type
     * @param contextInfo information containing the principalId and locale information about the caller of the service operation
     * @return a list of Track identifiers matching trackTypeKey or an empty list if none found
     * @throws InvalidParameterException trackTypeKey or contextInfo is not valid
     * @throws MissingParameterException trackTypeKey or context is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> getTrackIdsByType(@WebParam(name = "trackTypeKey") String trackTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of Tracks by Minor Id.
     * @param minorDisciplineId identifier of the MinorDiscipline
     * @param contextInfo information containing the principalId and locale information about the caller of the service operation
     * @return a list of Tracks associated with the specified Minor
     * @throws InvalidParameterException minorId or contextInfo is not valid
     * @throws MissingParameterException minorId or context is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<TrackInfo> getTracksByMinor(@WebParam(name = "minorDisciplineId") String minorDisciplineId, @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Creates a Track
     *
     * @param minorDisciplineId identifier of the MinorDiscipline
     * @param trackInfo trackInfo
     * @param contextInfo information containing the principalId and locale information about the caller of the service operation
     * @return the created Track
     * @throws AlreadyExistsException The Track already exists
     * @throws DataValidationErrorException One or more values invalid for this
     *             operation
     * @throws InvalidParameterException invalid Track
     * @throws MissingParameterException missing Track
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public TrackInfo createTrack(@WebParam(name = "minorDisciplineId") String minorDisciplineId,
                                 @WebParam(name = "trackTypeKey") String trackTypeKey,
                                                     @WebParam(name = "trackInfo") TrackInfo trackInfo,
                                                     @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws AlreadyExistsException,
            DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException;

    /**
     *
     * @param trackId
     * @param trackTypeKey
     * @param trackInfo
     * @param contextInfo information containing the principalId and locale information about the caller of the service operation
     * @return TrackInfo the Track
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws DoesNotExistException Track not found
     * @throws InvalidParameterException invalid Track
     * @throws MissingParameterException missing Track
     * @throws VersionMismatchException The action was attempted on an out of date version
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public TrackInfo updateTrack(@WebParam(name = "trackId") String trackId,
                                 @WebParam(name = "trackTypeKey") String trackTypeKey,
                                                     @WebParam(name = "trackInfo") TrackInfo trackInfo,
                                                     @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DataValidationErrorException,
            DoesNotExistException, InvalidParameterException, MissingParameterException, VersionMismatchException,
            OperationFailedException, PermissionDeniedException;

    /**
     * Deletes a Track
     *
     * @param trackId identifier for trackId.Maps to cluId
     * @param contextInfo information containing the principalId and locale information about the caller of the service operation
     * @return status of the operation (success or failure)
     * @throws DoesNotExistException Track does not exist
     * @throws InvalidParameterException invalid trackId
     * @throws MissingParameterException invalid trackId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteTrack(@WebParam(name = "trackId") String trackId,
                                            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException;

    /**
     * Validates a Track against its data dictionary
     *
     * @param minorDisciplineId identifier of the MinorDiscipline
     * @param validationType identifier of the extent of validation
     * @param trackInfo Track information to be tested
     * @param contextInfo information containing the principalId and locale information about the caller of the service operation
     * @return results from performing the validation
     * @throws InvalidParameterException invalid validationTypeKey, cluInfo
     * @throws MissingParameterException missing validationTypeKey, cluInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException
     */
    public List<ValidationResultInfo> validateTrack(@WebParam(name = "minorDisciplineId") String minorDisciplineId,
                                                    @WebParam(name = "validationType") String validationType,
                                                    @WebParam(name = "trackInfo") TrackInfo trackInfo,
                                                    @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Searches for TrackIds that meet the given search criteria.
     *
     * @param criteria the search criteria
     * @param contextInfo information containing the principalId and locale information about the caller of the service operation
     * @return a list of Track identifiers matching the criteria
     * @throws InvalidParameterException criteria or contextInfo is not valid
     * @throws MissingParameterException criteria or or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> searchForTrackIds(@WebParam(name = "criteria") QueryByCriteria criteria,
                                          @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Searches for Tracks that meet the given search criteria.
     *
     * @param criteria the search criteria
     * @param contextInfo information containing the principalId and locale information about the caller of the service operation
     * @return a list of Tracks matching the criteria
     * @throws InvalidParameterException criteria or contextInfo is not valid
     * @throws MissingParameterException criteria or or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<TrackInfo> searchForTracks(@WebParam(name = "criteria") QueryByCriteria criteria,
                                           @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

}