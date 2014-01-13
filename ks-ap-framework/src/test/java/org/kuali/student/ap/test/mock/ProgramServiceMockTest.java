package org.kuali.student.ap.test.mock;

import org.kuali.student.r1.common.dictionary.dto.ObjectStructureDefinition;
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
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.versionmanagement.dto.VersionDisplayInfo;
import org.kuali.student.r2.lum.program.dto.CoreProgramInfo;
import org.kuali.student.r2.lum.program.dto.CredentialProgramInfo;
import org.kuali.student.r2.lum.program.dto.HonorsProgramInfo;
import org.kuali.student.r2.lum.program.dto.MajorDisciplineInfo;
import org.kuali.student.r2.lum.program.dto.MinorDisciplineInfo;
import org.kuali.student.r2.lum.program.dto.ProgramRequirementInfo;
import org.kuali.student.r2.lum.program.dto.ProgramVariationInfo;
import org.kuali.student.r2.lum.program.service.ProgramService;

import javax.jws.WebParam;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: johglove
 * Date: 11/19/13
 * Time: 10:04 AM
 * To change this template use File | Settings | File Templates.
 */
public class ProgramServiceMockTest implements ProgramService {
    /**
     * Retrieves a CredentialProgram
     *
     * @param credentialProgramId Unique Id of the CredentialProgram. Maps to
     *                            cluId
     * @return the created Credential Program
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          CredentialProgram does not exist
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid Credential Program
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing Credential Program
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *
     */
    @Override
    public CredentialProgramInfo getCredentialProgram(@WebParam(name = "credentialProgramId") String credentialProgramId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves a list of CredentialPrograms corresponding to the given list of
     * CredentialProgram Ids
     *
     * @param credentialProgramIds list of CredentialPrograms to be retrieved
     * @param contextInfo          Context information containing the principalId and
     *                             locale information about the caller of service operation
     * @return list of CredentialProgram
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          an commentKey in list not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid commentKey
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          commentIds, contextInfo not specified
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<CredentialProgramInfo> getCredentialProgramsByIds(@WebParam(name = "credentialProgramIds") List<String> credentialProgramIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Validates a Credential Program against its data dictionary
     *
     * @param validationType        identifier of the extent of validation
     * @param credentialProgramInfo Credential Program information to be tested
     * @return results from performing the validation
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid validationTypeKey, cluInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing validationTypeKey, cluInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     */
    @Override
    public List<ValidationResultInfo> validateCredentialProgram(@WebParam(name = "validationType") String validationType, @WebParam(name = "credentialProgramInfo") CredentialProgramInfo credentialProgramInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Creates a Credential Program
     *
     * @param credentialProgramInfo credentialProgramInfo
     * @return the created Credential Program
     * @throws org.kuali.student.r2.common.exceptions.AlreadyExistsException
     *          The Credential Program already exists
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException
     *          One or more values invalid for this
     *          operation
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid Credential Program
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing Credential Program
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public CredentialProgramInfo createCredentialProgram(@WebParam(name = "credentialProgramTypeKey") String credentialProgramTypeKey, @WebParam(name = "credentialProgramInfo") CredentialProgramInfo credentialProgramInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Creates a new Credential Program version based on the current Credential
     * Program
     *
     * @param credentialProgramId identifier for the Credential Program to be
     *                            versioned
     * @param versionComment      comment for the current version
     * @return the new versioned Credential Program information
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          Credential Program does not exist
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid credentialProgramId
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          invalid credentialProgramId
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     * @throws org.kuali.student.r2.common.exceptions.VersionMismatchException
     *          The action was attempted on an out of
     *          date version
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException
     *
     * @throws org.kuali.student.r2.common.exceptions.ReadOnlyException
     *
     */
    @Override
    public CredentialProgramInfo createNewCredentialProgramVersion(@WebParam(name = "credentialProgramId") String credentialProgramId, @WebParam(name = "versionComment") String versionComment, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException, DataValidationErrorException, ReadOnlyException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

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
     *                            current. Must be in the future and be after the most current
     *                            Credential Program's start date.
     * @return status of the operation (success or failure)
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          Credential Program for credentialProgramId
     *          does not exist
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid credentialProgramId,
     *          currentVersionStart
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          invalid credentialProgramId
     * @throws org.kuali.student.r2.common.exceptions.IllegalVersionSequencingException
     *          a Credential Program with
     *          higher sequence number from the one provided is marked
     *          current
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException
     *
     */
    @Override
    public StatusInfo setCurrentCredentialProgramVersion(@WebParam(name = "credentialProgramId") String credentialProgramId, @WebParam(name = "currentVersionStart") Date currentVersionStart, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, IllegalVersionSequencingException, OperationFailedException, PermissionDeniedException, DataValidationErrorException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Updates a Credential Program
     *
     * @param credentialProgramInfo credentialProgramInfo
     * @return updated Credential Program
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException
     *          One or more values invalid for this
     *          operation
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          Credential Program not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid Credential Program
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing Credential Program
     * @throws org.kuali.student.r2.common.exceptions.VersionMismatchException
     *          The action was attempted on an out of
     *          date version
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public CredentialProgramInfo updateCredentialProgram(@WebParam(name = "credentialProgramId") String credentialProgramId, @WebParam(name = "credentialProgramInfo") CredentialProgramInfo credentialProgramInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, VersionMismatchException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Deletes a Credential Program
     *
     * @param credentialProgramId identifier for credentialProgramId.Maps to
     *                            cluId
     * @return status of the operation (success or failure)
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          Credential Program does not exist
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid credentialProgramId
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          invalid credentialProgramId
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public StatusInfo deleteCredentialProgram(@WebParam(name = "credentialProgramId") String credentialProgramId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves a MajorDiscipline
     *
     * @param majorDisciplineId Unique Id of the MajorDiscipline. Maps to cluId
     * @return the created MajorDiscipline
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          MajorDiscipline does not exist
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid MajorDiscipline
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing MajorDiscipline
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public MajorDisciplineInfo getMajorDiscipline(@WebParam(name = "majorDisciplineId") String majorDisciplineId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves a list of MajorDiscipline corresponding to the given list of
     * major discipline Ids
     *
     * @param majorDisciplineIds list of MajorDisciplines to be retrieved
     * @param contextInfo        Context information containing the principalId and
     *                           locale information about the caller of service operation
     * @return list of MajorDiscipline
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          an commentKey in list not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid commentKey
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          commentIds, contextInfo not specified
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<MajorDisciplineInfo> getMajorDisciplinesByIds(@WebParam(name = "majorDisciplineIds") List<String> majorDisciplineIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves the list of Major Discipline identifiers a given Credential
     * Program Type.
     *
     * @param programType Type of Credential Program
     * @return List of Major Discipline identifiers for the given Credential
     *         Program Type
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          program type not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid program type
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          program type is not specified
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     */
    @Override
    public List<String> getMajorDisciplineIdsByCredentialProgramType(@WebParam(name = "programType") String programType, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves a list of program variations for a particular major
     *
     * @param majorDisciplineId Major Discipline Identifier
     * @return list of program variations associated with the specified Major
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          Major not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid majorDisciplineId
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          majorDisciplineId not specified
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     */
    @Override
    public List<ProgramVariationInfo> getProgramVariationsByMajorDiscipline(@WebParam(name = "majorDisciplineId") String majorDisciplineId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Validates a Major discipline against its data dictionary
     *
     * @param validationType      identifier of the extent of validation
     * @param majorDisciplineInfo Major discipline information to be tested
     * @return results from performing the validation
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid validationTypeKey, cluInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing validationTypeKey, cluInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *
     */
    @Override
    public List<ValidationResultInfo> validateMajorDiscipline(@WebParam(name = "validationType") String validationType, @WebParam(name = "majorDisciplineInfo") MajorDisciplineInfo majorDisciplineInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Creates a Major Discipline Program
     *
     * @param majorDisciplineInfo majorDisciplineInfo
     * @return the created Major Discipline
     * @throws org.kuali.student.r2.common.exceptions.AlreadyExistsException
     *          The Major already exists
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException
     *          One or more values invalid for this
     *          operation
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid Major
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing Major
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public MajorDisciplineInfo createMajorDiscipline(@WebParam(name = "majorDisciplineTypeKey") String majorDisciplineTypeKey, @WebParam(name = "majorDisciplineInfo") MajorDisciplineInfo majorDisciplineInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Updates a Major Discipline
     *
     * @param majorDisciplineInfo majorDisciplineInfo
     * @return updated Major Discipline
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException
     *          One or more values invalid for this
     *          operation
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          Major not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid Major
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing Major
     * @throws org.kuali.student.r2.common.exceptions.VersionMismatchException
     *          The action was attempted on an out of
     *          date version
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public MajorDisciplineInfo updateMajorDiscipline(@WebParam(name = "majorDisciplineId") String majorDisciplineId, @WebParam(name = "majorDisciplineInfo") MajorDisciplineInfo majorDisciplineInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, VersionMismatchException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Deletes a Major Discipline
     *
     * @param majorDisciplineId identifier for majorDisciplineId.Maps to cluId
     * @return status of the operation (success or failure)
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          Major does not exist
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid majorDisciplineId
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          invalid majorDisciplineId
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public StatusInfo deleteMajorDiscipline(@WebParam(name = "majorDisciplineId") String majorDisciplineId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Creates a new Major Discipline version based on the current Major
     *
     * @param majorDisciplineId identifier for the Major Discipline to be
     *                          versioned
     * @param versionComment    comment for the current version
     * @return the new versioned Major Discipline information
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          Major does not exist
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid majorDisciplineId
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          invalid majorDisciplineId
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     * @throws org.kuali.student.r2.common.exceptions.VersionMismatchException
     *          The action was attempted on an out of
     *          date version
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException
     *
     * @throws org.kuali.student.r2.common.exceptions.ReadOnlyException
     *
     */
    @Override
    public MajorDisciplineInfo createNewMajorDisciplineVersion(@WebParam(name = "majorDisciplineId") String majorDisciplineId, @WebParam(name = "versionComment") String versionComment, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException, DataValidationErrorException, ReadOnlyException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves a HonorsProgram
     *
     * @param honorsProgramId Unique Id of the HonorsProgram. Maps to cluId
     * @return the created Honors Program
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          HonorsProgram does not exist
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid Honors Program
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing Honors Program
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public HonorsProgramInfo getHonorsProgram(@WebParam(name = "honorsProgramId") String honorsProgramId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves a list of honors program by Ids
     *
     * @param honorsProgramIds
     * @param contextInfo
     * @return
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *
     */
    @Override
    public List<HonorsProgramInfo> getHonorsProgramsByIds(@WebParam(name = "honorsProgramIds") List<String> honorsProgramIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves the list of Honors Program Program a given Credential Program
     * Type.
     *
     * @param programType Type of Credential Program
     * @return List of Honors Programs for the given Credential Program Type
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          program type not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid program type
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          program type is not specified
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     */
    @Override
    public List<String> getHonorProgramIdsByCredentialProgramType(@WebParam(name = "programType") String programType, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Validates a Honors Program against its data dictionary
     *
     * @param validationType    identifier of the extent of validation
     * @param honorsProgramInfo Honors Program information to be tested
     * @return results from performing the validation
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid validationTypeKey, cluInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing validationTypeKey, cluInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     */
    @Override
    public List<ValidationResultInfo> validateHonorsProgram(@WebParam(name = "validationType") String validationType, @WebParam(name = "honorsProgramInfo") HonorsProgramInfo honorsProgramInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Creates a Honors Program
     *
     * @param honorsProgramInfo honorsProgramInfo
     * @return the created Honors Program
     * @throws org.kuali.student.r2.common.exceptions.AlreadyExistsException
     *          The Honors Program already exists
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException
     *          One or more values invalid for this
     *          operation
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid Honors Program
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing Honors Program
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public HonorsProgramInfo createHonorsProgram(@WebParam(name = "honorsProgramTypeKey") String honorsProgramTypeKey, @WebParam(name = "honorsProgramInfo") HonorsProgramInfo honorsProgramInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Updates a Honors Program
     *
     * @param honorsProgramInfo honorsProgramInfo
     * @return updated Honors Program
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException
     *          One or more values invalid for this
     *          operation
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          Honors Program not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid Honors Program
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing Honors Program
     * @throws org.kuali.student.r2.common.exceptions.VersionMismatchException
     *          The action was attempted on an out of
     *          date version
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public HonorsProgramInfo updateHonorsProgram(@WebParam(name = "honorsProgramId") String honorsProgramId, @WebParam(name = "honorsProgramTypeKey") String honorsProgramTypeKey, @WebParam(name = "honorsProgramInfo") HonorsProgramInfo honorsProgramInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, VersionMismatchException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Deletes a Honors Program
     *
     * @param honorsProgramId identifier for honorsProgramId.Maps to cluId
     * @return status of the operation (success or failure)
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          Honors Program does not exist
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid honorsProgramId
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          invalid honorsProgramId
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public StatusInfo deleteHonorsProgram(@WebParam(name = "honorsProgramId") String honorsProgramId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves a Core Program
     *
     * @param coreProgramId Unique Id of the Core Program. Maps to cluId
     * @return the Core Program
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          Program Requirement does not exist
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid Program Requirement
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing Program Requirement
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public CoreProgramInfo getCoreProgram(@WebParam(name = "coreProgramId") String coreProgramId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves a list of core program by Ids
     *
     * @param coreProgramIds
     * @param contextInfo
     * @return
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *
     */
    @Override
    public List<CoreProgramInfo> getCoreProgramsByIds(@WebParam(name = "coreProgramIds") List<String> coreProgramIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Validates a Core Program against its data dictionary
     *
     * @param validationType  identifier of the extent of validation
     * @param coreProgramInfo Core Program information to be tested
     * @return results from performing the validation
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid validationTypeKey, cluInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing validationTypeKey, cluInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     */
    @Override
    public List<ValidationResultInfo> validateCoreProgram(@WebParam(name = "validationType") String validationType, @WebParam(name = "coreProgramInfo") CoreProgramInfo coreProgramInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Creates a Core Program
     *
     * @param coreProgramInfo coreProgramInfo
     * @return the created Core Program
     * @throws org.kuali.student.r2.common.exceptions.AlreadyExistsException
     *          The Core Program already exists
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException
     *          One or more values invalid for this
     *          operation
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid Core Program
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing Core Program
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public CoreProgramInfo createCoreProgram(@WebParam(name = "coreProgramTypeKey") String coreProgramTypeKey, @WebParam(name = "coreProgramInfo") CoreProgramInfo coreProgramInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Creates a new Core Program version based on the current Core Program
     *
     * @param coreProgramId  identifier for the Core Program to be versioned
     * @param versionComment comment for the current version
     * @return the new versioned Core Program information
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          Core Program does not exist
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid coreProgramId
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          invalid coreProgramId
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     * @throws org.kuali.student.r2.common.exceptions.VersionMismatchException
     *          The action was attempted on an out of
     *          date version
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException
     *
     * @throws org.kuali.student.r2.common.exceptions.ReadOnlyException
     *
     */
    @Override
    public CoreProgramInfo createNewCoreProgramVersion(@WebParam(name = "coreProgramId") String coreProgramId, @WebParam(name = "versionComment") String versionComment, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException, DataValidationErrorException, ReadOnlyException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Sets a specific version of the Core Program as current. The sequence
     * number must be greater than the existing current Core Program version.
     * This will truncate the current version's end date to the
     * currentVersionStart param. If a Major exists which is set to become
     * current in the future, that Major's currentVersionStart and
     * CurrentVersionEnd will be nullified. The currentVersionStart must be in
     * the future to prevent changing historic data.
     *
     * @param coreProgramId       Version Specific Id of the Core Program
     * @param currentVersionStart Date when this Core Program becomes current.
     *                            Must be in the future and be after the most current major's
     *                            start date.
     * @return status of the operation (success or failure)
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          Core Program for coreProgramId does not
     *          exist
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid coreProgramId,
     *          currentVersionStart
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          invalid coreProgramId
     * @throws org.kuali.student.r2.common.exceptions.IllegalVersionSequencingException
     *          a CoreProgram with higher
     *          sequence number from the one provided is marked current
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException
     *
     */
    @Override
    public StatusInfo setCurrentCoreProgramVersion(@WebParam(name = "coreProgramId") String coreProgramId, @WebParam(name = "currentVersionStart") Date currentVersionStart, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, IllegalVersionSequencingException, OperationFailedException, PermissionDeniedException, DataValidationErrorException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Updates a Core Program
     *
     * @param coreProgramInfo coreProgramInfo
     * @return updated Core Program
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException
     *          One or more values invalid for this
     *          operation
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          Core Program not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid Core Program
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing Core Program
     * @throws org.kuali.student.r2.common.exceptions.VersionMismatchException
     *          The action was attempted on an out of
     *          date version
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public CoreProgramInfo updateCoreProgram(@WebParam(name = "coreProgramId") String coreProgramId, @WebParam(name = "coreProgramTypeKey") String coreProgramTypeKey, @WebParam(name = "coreProgramInfo") CoreProgramInfo coreProgramInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, VersionMismatchException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Deletes a Core Program
     *
     * @param coreProgramId identifier for coreProgramId.Maps to cluId
     * @return status of the operation (success or failure)
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          Core Program does not exist
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid coreProgramId
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          invalid coreProgramId
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public StatusInfo deleteCoreProgram(@WebParam(name = "coreProgramId") String coreProgramId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves a ProgramRequirement
     *
     * @param programRequirementId Unique Id of the ProgramRequirement. Maps to
     *                             cluId
     * @return the Program Requirement
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          Program Requirement does not exist
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid Program Requirement
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing Program Requirement
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public ProgramRequirementInfo getProgramRequirement(@WebParam(name = "programRequirementId") String programRequirementId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves a list of ProgramRequirements by Ids
     *
     * @param programRequirementIds
     * @param contextInfo
     * @return
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *
     */
    @Override
    public List<ProgramRequirementInfo> getProgramRequirementsByIds(@WebParam(name = "programRequirementIds") List<String> programRequirementIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Validates a Program Requirement against its data dictionary
     *
     * @param validationType         identifier of the extent of validation
     * @param programRequirementInfo Program Requirement information to be
     *                               tested
     * @return results from performing the validation
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid validationTypeKey, cluInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing validationTypeKey, cluInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     */
    @Override
    public List<ValidationResultInfo> validateProgramRequirement(@WebParam(name = "validationType") String validationType, @WebParam(name = "programRequirementInfo") ProgramRequirementInfo programRequirementInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Creates a Program Requirement
     *
     * @param programRequirementInfo programRequirementInfo
     * @return the created Program Requirement
     * @throws org.kuali.student.r2.common.exceptions.AlreadyExistsException
     *          The Program Requirement already exists
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException
     *          One or more values invalid for this
     *          operation
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid Program Requirement
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing Program Requirement
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public ProgramRequirementInfo createProgramRequirement(@WebParam(name = "programRequirementTypeKey") String programRequirementTypeKey, @WebParam(name = "programRequirementInfo") ProgramRequirementInfo programRequirementInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Updates a Program Requirement
     *
     * @param programRequirementInfo programRequirementInfo
     * @return updated Program Requirement
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException
     *          One or more values invalid for this
     *          operation
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          Program Requirement not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid Program Requirement
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing Program Requirement
     * @throws org.kuali.student.r2.common.exceptions.VersionMismatchException
     *          The action was attempted on an out of
     *          date version
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public ProgramRequirementInfo updateProgramRequirement(@WebParam(name = "programRequirementId") String programRequirementId, @WebParam(name = "programRequirementTypeKey") String programRequirementTypeKey, @WebParam(name = "programRequirementInfo") ProgramRequirementInfo programRequirementInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, VersionMismatchException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Deletes a Program Requirement
     *
     * @param programRequirementId identifier for programRequirementId.Maps to
     *                             cluId
     * @return status of the operation (success or failure)
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          Program Requirement does not exist
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid programRequirementId
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          invalid programRequirementId
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public StatusInfo deleteProgramRequirement(@WebParam(name = "programRequirementId") String programRequirementId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Sets a specific version of the Major as current. The sequence number must
     * be greater than the existing current Major version. This will truncate
     * the current version's end date to the currentVersionStart param. If a
     * Major exists which is set to become current in the future, that Major's
     * currentVersionStart and CurrentVersionEnd will be nullified. The
     * currentVersionStart must be in the future to prevent changing historic
     * data.
     *
     * @param majorDisciplineId   Version Specific Id of the Major Discipline
     * @param currentVersionStart Date when this Major becomes current. Must be
     *                            in the future and be after the most current major's start
     *                            date.
     * @return status of the operation (success or failure)
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          Major for majorVersionId does not exist
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid majorVersionId,
     *          currentVersionStart
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          invalid majorVersionId
     * @throws org.kuali.student.r2.common.exceptions.IllegalVersionSequencingException
     *          a Major with higher sequence
     *          number from the one provided is marked current
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException
     *
     */
    @Override
    public StatusInfo setCurrentMajorDisciplineVersion(@WebParam(name = "majorDisciplineId") String majorDisciplineId, @WebParam(name = "currentVersionStart") Date currentVersionStart, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, IllegalVersionSequencingException, OperationFailedException, PermissionDeniedException, DataValidationErrorException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves a MinorDiscipline
     *
     * @param minorDisciplineId Unique Id of the MinorDiscipline. Maps to cluId
     * @return the created MinorDiscipline
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          MinorDiscipline does not exist
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid MinorDiscipline
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing MinorDiscipline
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public MinorDisciplineInfo getMinorDiscipline(@WebParam(name = "minorDisciplineId") String minorDisciplineId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves the list of Minor Discipline Program a given Credential Program
     * Type.
     *
     * @param programType Type of Credential Program
     * @return List of Minor Disciplines for the given Credential Program Type
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          program type not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid program type
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          program type is not specified
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     */
    @Override
    public List<String> getMinorsByCredentialProgramType(@WebParam(name = "programType") String programType, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Validates a Minor discipline against its data dictionary
     *
     * @param validationType      identifier of the extent of validation
     * @param minorDisciplineInfo Minor discipline information to be tested
     * @return results from performing the validation
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid validationTypeKey, cluInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing validationTypeKey, cluInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     */
    @Override
    public List<ValidationResultInfo> validateMinorDiscipline(@WebParam(name = "validationType") String validationType, @WebParam(name = "minorDisciplineInfo") MinorDisciplineInfo minorDisciplineInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Creates a Minor Discipline Program
     *
     * @param minorDisciplineInfo minorDisciplineInfo
     * @return the created Minor Discipline
     * @throws org.kuali.student.r2.common.exceptions.AlreadyExistsException
     *          The Minor already exists
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException
     *          One or more values invalid for this
     *          operation
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid Minor
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing Minor
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public MinorDisciplineInfo createMinorDiscipline(@WebParam(name = "minorDisciplineTypeKey") String minorDisciplineTypeKey, @WebParam(name = "minorDisciplineInfo") MinorDisciplineInfo minorDisciplineInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Updates a Minor Discipline
     *
     * @param minorDisciplineInfo minorDisciplineInfo
     * @return updated Minor Discipline
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException
     *          One or more values invalid for this
     *          operation
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          Minor not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid Minor
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing Minor
     * @throws org.kuali.student.r2.common.exceptions.VersionMismatchException
     *          The action was attempted on an out of
     *          date version
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public MinorDisciplineInfo updateMinorDiscipline(@WebParam(name = "minorDisciplineId") String minorDisciplineId, @WebParam(name = "minorDisciplineTypeKey") String minorDisciplineTypeKey, @WebParam(name = "minorDisciplineInfo") MinorDisciplineInfo minorDisciplineInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, VersionMismatchException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Deletes a Minor Discipline
     *
     * @param minorDisciplineId identifier for minorDisciplineId.Maps to cluId
     * @return status of the operation (success or failure)
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          Minor does not exist
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid minorDisciplineId
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          invalid minorDisciplineId
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public StatusInfo deleteMinorDiscipline(@WebParam(name = "minorDisciplineId") String minorDisciplineId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<ProgramVariationInfo> getVariationsByMajorDisciplineId(@WebParam(name = "majorDisciplineId") String majorDisciplineId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<String> getObjectTypes() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public ObjectStructureDefinition getObjectStructure(@WebParam(name = "objectTypeKey") String objectTypeKey) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves the list of search types known by this service.
     *
     * @param contextInfo information containing the principalId and locale
     *                    information about the caller of service operation
     * @return list of search type information
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          contextInfo is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          contextInfo is missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     */
    @Override
    public List<TypeInfo> getSearchTypes(@WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves information about a particular search type.
     *
     * @param searchTypeKey identifier of the search type
     * @param contextInfo   information containing the principalId and locale
     *                      information about the caller of service operation
     * @return information on the search type
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          specified searchTypeKey not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          contextInfo is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          searchTypeKey or contextInfo is missing
     *          or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     */
    @Override
    public TypeInfo getSearchType(@WebParam(name = "searchTypeKey") String searchTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Performs a search.
     *
     * @param searchRequestInfo the search request
     * @param contextInfo       information containing the principalId and locale
     *                          information about the caller of service operation
     * @return the results of the search
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          contextInfo is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          searchRequestInfo or contextInfo is
     *          missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public SearchResultInfo search(SearchRequestInfo searchRequestInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws MissingParameterException, InvalidParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves a list of versions associated with the objectId.
     *
     * @param refObjectUri reference object type URI
     * @param refObjectId  reference object Id
     * @param contextInfo  context information containing the principalId and
     *                     locale information about the caller of service
     *                     operation
     * @return a list of versions
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          refObjectUri or refObjectId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          refObjectUri, refObjectId or
     *          contextInfo is missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public List<VersionDisplayInfo> getVersions(@WebParam(name = "refObjectUri") String refObjectUri, @WebParam(name = "refObjectId") String refObjectId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves first version associated with the objectId.
     *
     * @param refObjectUri reference object type URI
     * @param refObjectId  reference object Id
     * @param contextInfo  context information containing the principalId and
     *                     locale information about the caller of service
     *                     operation
     * @return first version
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          refObjectUri or refObjectId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          contextInfo is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          refObjectUri, refObjectId or
     *          contextInfo is missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public VersionDisplayInfo getFirstVersion(@WebParam(name = "refObjectUri") String refObjectUri, @WebParam(name = "refObjectId") String refObjectId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves latest version associated with the objectId. This is not always
     * the current version.  A current version is what is being used by the
     * system right now, but there could be draft version created after the
     * current version.
     *
     * @param refObjectUri reference object type URI
     * @param refObjectId  reference object Id
     * @param contextInfo  context information containing the principalId and
     *                     locale information about the caller of service
     *                     operation
     * @return latest version
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          specified refObjectId and refObjectUri
     *          not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          contextInfo is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          refObjectUri, refObjectId or
     *          contextInfo is missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public VersionDisplayInfo getLatestVersion(@WebParam(name = "refObjectUri") String refObjectUri, @WebParam(name = "refObjectId") String refObjectId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves current version associated with the objectId.
     *
     * @param refObjectUri reference object type URI
     * @param refObjectId  reference object Id
     * @param contextInfo  context information containing the principalId and
     *                     locale information about the caller of service
     *                     operation
     * @return current version
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          specified refObjectUri and refObjectId
     *          not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          contextInfo is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          refObjectUri, refObjectId or
     *          contextInfo is missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public VersionDisplayInfo getCurrentVersion(@WebParam(name = "refObjectUri") String refObjectUri, @WebParam(name = "refObjectId") String refObjectId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves the version associated with the objectId and the sequence
     * number.
     *
     * @param refObjectUri reference object type URI
     * @param refObjectId  reference object Id
     * @param sequence     sequence number
     * @param contextInfo  context information containing the principalId and
     *                     locale information about the caller of service
     *                     operation
     * @return version matching the sequence
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          specified refObjectUri, refObjectId or
     *          sequence not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          sequence or contextInfo is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          refObjectUri, refObjectId, sequence or
     *          contextInfo is missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public VersionDisplayInfo getVersionBySequenceNumber(@WebParam(name = "refObjectUri") String refObjectUri, @WebParam(name = "refObjectId") String refObjectId, @WebParam(name = "sequence") Long sequence, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves the current version associated with the objectId on a given
     * date.
     *
     * @param refObjectUri reference object type URI
     * @param refObjectId  reference object Id
     * @param date         date
     * @param contextInfo  context information containing the principalId and
     *                     locale information about the caller of service
     *                     operation
     * @return current version on given date
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          specified refObjectId and refObjectUri
     *          not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          date or contextInfo is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          refObjectUri, refObjectId, date or
     *          contextInfo is missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public VersionDisplayInfo getCurrentVersionOnDate(@WebParam(name = "refObjectUri") String refObjectUri, @WebParam(name = "refObjectId") String refObjectId, @WebParam(name = "date") Date date, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves the current version associated with the objectId in a given
     * date range
     *
     * @param refObjectUri reference object type URI
     * @param refObjectId  reference object Id
     * @param from         from date
     * @param to           to date
     * @param contextInfo  context information containing the principalId and
     *                     locale information about the caller of service
     *                     operation
     * @return current version in given date range
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          specified refObjectId and refObjectUri
     *          not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          from, to or contextInfo is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          refObjectUri, refObjectId, from, to or
     *          contextInfo is missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public List<VersionDisplayInfo> getVersionsInDateRange(@WebParam(name = "refObjectUri") String refObjectUri, @WebParam(name = "refObjectId") String refObjectId, @WebParam(name = "from") Date from, @WebParam(name = "to") Date to, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
