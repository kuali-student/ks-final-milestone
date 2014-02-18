/*
 * Copyright 2013 The Kuali Foundation Licensed under the
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
package org.kuali.student.core.person.service;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.core.person.dto.PersonInfo;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.util.List;
import javax.jws.WebParam;
import org.kuali.student.core.person.dto.PersonAffiliationInfo;
import org.kuali.student.core.person.dto.PersonBioDemographicsInfo;
import org.kuali.student.core.person.dto.PersonIdentifierInfo;
import org.kuali.student.core.person.dto.PersonNameInfo;

/**
 * Person Service provides access to people.
 */
@WebService(name = "PersonService", targetNamespace = PersonServiceNamespace.NAMESPACE)
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface PersonService {

    /**
     * Retrieves a Person by a Person ID
     *
     * @param personId the Person ID
     * @param contextInfo Context information containing the principalId and locale information about the caller of service
     * operation
     * @return the Person
     * @throws DoesNotExistException personId not found
     * @throws InvalidParameterException invalid personId or contextInfo
     * @throws MissingParameterException personId or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public PersonInfo getPerson(@WebParam(name = "personId") String personId,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of persons by a list of Person IDs
     *
     * @param personIds a list of Person IDs
     * @param contextInfo Context information containing the principalId and locale information about the caller of service
     * operation
     * @return a List of People
     * @throws DoesNotExistException personId not found
     * @throws InvalidParameterException invalid personId or contextInfo
     * @throws MissingParameterException personId or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<PersonInfo> getPersonsByIds(@WebParam(name = "personIds") List<String> personIds,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of Person Ids by person type.
     *
     * @param personTypeKey the personTypeKey to search by
     * @param contextInfo Context information containing the principalId and locale information about the caller of service
     * operation
     * @return a List of Person IDs
     * @throws DoesNotExistException personId not found
     * @throws InvalidParameterException invalid personId or contextInfo
     * @throws MissingParameterException personId or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> getPersonIdsByType(@WebParam(name = "personTypeKey") String personTypeKey,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Searches for Person Ids based on the criteria and returns a list of Person identifiers which match the search criteria.
     *
     * @param criteria the search criteria
     * @param contextInfo Context information containing the principalId and locale information about the caller of service
     * operation
     * @return a List of Person IDs
     * @throws InvalidParameterException invalid criteria or contextInfo
     * @throws MissingParameterException personId or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> searchForPersonIds(@WebParam(name = "criteria") QueryByCriteria criteria,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Searches for persons based on the criteria and returns a list of persons which match the search criteria.
     *
     * @param criteria the search criteria
     * @param contextInfo Context information containing the principalId and locale information about the caller of service
     * operation
     * @return a List of People
     * @throws InvalidParameterException invalid personId or contextInfo
     * @throws MissingParameterException personId or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<PersonInfo> searchForPersons(@WebParam(name = "criteria") QueryByCriteria criteria,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Validates a Person. If an identifier is present for the Person and a record is found for that identifier, the validation
     * checks if the Person can be updated to the new values. If an identifier is not present or a record does not exist, the
     * validation checks if the person with the given data can be created.
     *
     * @param validationTypeKey the identifier for the validation Type
     * @param personTypeKey the identifier for the person type key to be validated
     * @param personInfo the person to be validated
     * @param contextInfo Context information containing the principalId and locale information about the caller of service
     * operation
     * @return a list of validation results or an empty list if validation succeeded
     * @throws DoesNotExistException validationTypeKey or personTypeKey is not found
     * @throws InvalidParameterException personInfo or contextInfo is not valid
     * @throws MissingParameterException validationTypeKey, personTypeKey, personInfo, or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<ValidationResultInfo> validatePerson(@WebParam(name = "validationTypeKey") String validationTypeKey,
            @WebParam(name = "personTypeKey") String personTypeKey,
            @WebParam(name = "personInfo") PersonInfo personInfo,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Creates a new Person. The Person Id, Type, and Meta information may not be set in the supplied data.
     *
     * @param personTypeKey the identifier for the person type to be validated
     * @param personInfo the person to be validated
     * @param contextInfo Context information containing the principalId and locale information about the caller of service
     * operation
     * @return the new Person
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException personTypeKey does not exist or is not supported
     * @throws InvalidParameterException personInfo or contextInfo is not valid
     * @throws MissingParameterException personTypeKey, personInfo, or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     * @throws ReadOnlyException an attempt at supplying information designated as read only
     */
    public PersonInfo createPerson(@WebParam(name = "personTypeKey") String personTypeKey,
            PersonInfo personInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws
            DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException;

    /**
     * Updates an existing Person. The Person Id, Type, and Meta information may not be changed.
     *
     * @param personId the identifier for the Person to be updated
     * @param personInfo the person to be validated
     * @param contextInfo Context information containing the principalId and locale information about the caller of service
     * operation
     * @return the updated Person
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException personId is not found
     * @throws InvalidParameterException personId, personInfo or contextInfo is not valid
     * @throws MissingParameterException personId, personInfo, or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     * @throws ReadOnlyException an attempt at supplying information designated as read only
     * @throws VersionMismatchException if someone else has updated this person record since you fetched the version you are
     * updating.
     */
    public PersonInfo updatePerson(@WebParam(name = "personId") String personId,
            @WebParam(name = "personInfo") PersonInfo personInfo,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws
            DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException,
            VersionMismatchException;

    /**
     * Deletes an existing Person and all it's related parts
     *
     * @param personId the identifier for the person to be deleted
     * @param contextInfo Context information containing the principalId and locale information about the caller of service
     * operation
     * @return the status of the delete operation. This must always be true.
     * @throws DoesNotExistException personInfo is not found
     * @throws InvalidParameterException personInfo or contextInfo is not valid
     * @throws MissingParameterException personInfo or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public StatusInfo deletePerson(@WebParam(name = "personId") String personId,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    ////
    //// person name methods
    //// 
    /**
     * Retrieves a Person Name by a Person Name ID
     *
     * @param personNameId the Person Name ID
     * @param contextInfo Context information containing the principalId and locale information about the caller of service
     * operation
     * @return the PersonName
     * @throws DoesNotExistException personId not found
     * @throws InvalidParameterException invalid personId or contextInfo
     * @throws MissingParameterException personId or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public PersonNameInfo getPersonName(@WebParam(name = "personNameId") String personNameId,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of person names by a list of Person Name IDs
     *
     * @param personNameIds a list of PersonName IDs
     * @param contextInfo Context information containing the principalId and locale information about the caller of service
     * operation
     * @return a List of PersonNames
     * @throws DoesNotExistException personNameId not found
     * @throws InvalidParameterException invalid personNameId or contextInfo
     * @throws MissingParameterException personNameId or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<PersonNameInfo> getPersonNamesByIds(@WebParam(name = "personNameIds") List<String> personNameIds,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of Person Name Ids by person type.
     *
     * @param personNameTypeKey the person name Type Key to search by
     * @param contextInfo Context information containing the principalId and locale information about the caller of service
     * operation
     * @return a List of PersonName IDs
     * @throws DoesNotExistException personNameId not found
     * @throws InvalidParameterException invalid personNameId or contextInfo
     * @throws MissingParameterException personNameId or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> getPersonNameIdsByType(@WebParam(name = "personNameTypeKey") String personNameTypeKey,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Searches for Person Name Ids based on the criteria and returns a list of Person Name identifiers which match the search
     * criteria.
     *
     * @param criteria the search criteria
     * @param contextInfo Context information containing the principalId and locale information about the caller of service
     * operation
     * @return a List of PersonName IDs
     * @throws InvalidParameterException invalid criteria or contextInfo
     * @throws MissingParameterException criteria or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> searchForPersonNameIds(@WebParam(name = "criteria") QueryByCriteria criteria,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Searches for person names based on the criteria and returns a list of persons which match the search criteria.
     *
     * @param criteria the search criteria
     * @param contextInfo Context information containing the principalId and locale information about the caller of service
     * operation
     * @return a List of PersonNames
     * @throws InvalidParameterException invalid criteria or contextInfo
     * @throws MissingParameterException criteria or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<PersonNameInfo> searchForPersonNames(@WebParam(name = "criteria") QueryByCriteria criteria,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Validates a Person Name. If an identifier is present for the PersonName and a record is found for that identifier, the
     * validation checks if the PersonName can be updated to the new values. If an identifier is not present or a record does not
     * exist, the validation checks if the person with the given data can be created.
     *
     * @param validationTypeKey the identifier for the validation Type
     * @param personNameTypeKey the identifier for the person name type to be validated
     * @param personId id of person for whom this name is being applied
     * @param personNameInfo the person to be validated
     * @param contextInfo Context information containing the principalId and locale information about the caller of service
     * operation
     * @return a list of validation results or an empty list if validation succeeded
     * @throws DoesNotExistException validationTypeKey or personNameTypeKey is not found
     * @throws InvalidParameterException personNameInfo or contextInfo is not valid
     * @throws MissingParameterException validationTypeKey, personNameTypeKey, personNameInfo, or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<ValidationResultInfo> validatePersonName(@WebParam(name = "validationTypeKey") String validationTypeKey,
            @WebParam(name = "personNameTyhpeKey") String personNameTypeKey,
            @WebParam(name = "personId") String personId,
            @WebParam(name = "personInfo") PersonNameInfo personNameInfo,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Creates a new Person Name. The PersonName Id, Type, and Meta information may not be set in the supplied data.
     *
     * @param personNameTypeKey the identifier for the person name Type to assign to this name object
     * @param personId id of the person to whom this name is attached
     * @param personNameInfo the person to be validated
     * @param contextInfo Context information containing the principalId and locale information about the caller of service
     * operation
     * @return the new PersonName
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException personNameType does not exist or is not supported
     * @throws InvalidParameterException personNameInfo or contextInfo is not valid
     * @throws MissingParameterException personNameType, personNameInfo, or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     * @throws ReadOnlyException an attempt at supplying information designated as read only
     */
    public PersonNameInfo createPersonName(@WebParam(name = "personNameTypeKey") String personNameTypeKey,
            @WebParam(name = "personId") String personId,
            @WebParam(name = "personNameInfo") PersonNameInfo personNameInfo,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws
            DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException;

    /**
     * Updates an existing Person Name. The PersonName Id, Type, and Meta information may not be changed.
     *
     * @param personNameId the identifier for the PersonName to be updated
     * @param personNameInfo the person to be validated
     * @param contextInfo Context information containing the principalId and locale information about the caller of service
     * operation
     * @return the updated PersonName
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException personNameId is not found
     * @throws InvalidParameterException personNameId, personNanmeInfo or contextInfo is not valid
     * @throws MissingParameterException personNameId, personNameInfo, or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     * @throws ReadOnlyException an attempt at supplying information designated as read only
     * @throws VersionMismatchException if someone else has updated this person record since you fetched the version you are
     * updating.
     */
    public PersonNameInfo updatePersonName(@WebParam(name = "personNamed") String personNameId,
            @WebParam(name = "personNameInfo") PersonNameInfo personNameInfo,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws
            DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException,
            VersionMismatchException;

    /**
     * Deletes an existing Person Name.
     *
     * @param personNameId the identifier for the person name record to be deleted
     * @param contextInfo Context information containing the principalId and locale information about the caller of service
     * operation
     * @return the status of the delete operation. This must always be true.
     * @throws DoesNotExistException personNameInfo is not found
     * @throws InvalidParameterException personNameInfo or contextInfo is not valid
     * @throws MissingParameterException personNameInfo or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public StatusInfo deletePersonName(@WebParam(name = "personNameId") String personNameId,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Get names for a person
     *
     * @param personId the identifier for the person
     * @param contextInfo Context information containing the principalId and locale information about the caller of service
     * operation
     * @return list of names for that person
     * @throws InvalidParameterException personId or contextInfo is not valid
     * @throws MissingParameterException personId or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<PersonNameInfo> getPersonNamesByPerson(@WebParam(name = "personId") String personId,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    ////
    //// person identifiers
    ////
    /**
     * Retrieves a Person Identifier by a Person Identifier ID
     *
     * @param personIdentifierId the Person Identifier ID
     * @param contextInfo Context information containing the principalId and locale information about the caller of service
     * operation
     * @return the PersonIdentifier
     * @throws DoesNotExistException personIdentifierId not found
     * @throws InvalidParameterException invalid personIdentifierId or contextInfo
     * @throws MissingParameterException personIdentifierId or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public PersonIdentifierInfo getPersonIdentifier(@WebParam(name = "personIdentifierId") String personIdentifierId,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of person identifiers by a list of Person Identifier IDs
     *
     * @param personIdentifierIds a list of Person Identifier IDs
     * @param contextInfo Context information containing the principalId and locale information about the caller of service
     * operation
     * @return a List of Person Identifiers
     * @throws DoesNotExistException personIdentifierId not found
     * @throws InvalidParameterException invalid personIdentifierId or contextInfo
     * @throws MissingParameterException personIdentifierId or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<PersonIdentifierInfo> getPersonIdentifiersByIds(
            @WebParam(name = "personIdentifierIds") List<String> personIdentifierIds,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of Person Identifier Ids by person type.
     *
     * @param personIdentifierTypeKey the personIdentifier Type Key to search by
     * @param contextInfo Context information containing the principalId and locale information about the caller of service
     * operation
     * @return a List of PersonIdentifier IDs
     * @throws DoesNotExistException personIdentifierTypeKey not found
     * @throws InvalidParameterException invalid personIdentifierTypeKey or contextInfo
     * @throws MissingParameterException personIdentifierTypeKey or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> getPersonIdentifierIdsByType(@WebParam(name = "personIdentifierTypeKey") String personIdentifierTypeKey,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Searches for Person Identifier Ids based on the criteria and returns a list of Person Identifier identifiers which match
     * the search criteria.
     *
     * @param criteria the search criteria
     * @param contextInfo Context information containing the principalId and locale information about the caller of service
     * operation
     * @return a List of PersonIdentifier IDs
     * @throws InvalidParameterException invalid criteria or contextInfo
     * @throws MissingParameterException criteria or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> searchForPersonIdentifierIds(@WebParam(name = "criteria") QueryByCriteria criteria,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Searches for person identifiers based on the criteria and returns a list of persons which match the search criteria.
     *
     * @param criteria the search criteria
     * @param contextInfo Context information containing the principalId and locale information about the caller of service
     * operation
     * @return a List of Person Identifiers
     * @throws InvalidParameterException invalid criteria or contextInfo
     * @throws MissingParameterException criteria or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<PersonIdentifierInfo> searchForPersonIdentifiers(@WebParam(name = "criteria") QueryByCriteria criteria,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Validates a PersonIdentifier. If an identifier is present for the Person Identifier and a record is found for that
     * identifier, the validation checks if the PersonIdentifier can be updated to the new values. If an identifier is not present
     * or a record does not exist, the validation checks if the person with the given data can be created.
     *
     * @param validationTypeKey the identifier for the validation Type
     * @param personIdentifierTypeKey the identifier for the person identifier type to be validated
     * @param personId id of person for whom this identifier is being applied
     * @param personIdentifierInfo the person to be validated
     * @param contextInfo Context information containing the principalId and locale information about the caller of service
     * operation
     * @return a list of validation results or an empty list if validation succeeded
     * @throws DoesNotExistException validationTypeKey or personIdentifierTypeKey is not found
     * @throws InvalidParameterException personIdentifierInfo or contextInfo is not valid
     * @throws MissingParameterException validationTypeKey, personIdentifierTypeKey, personIdentifierInfo, or contextInfo is
     * missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<ValidationResultInfo> validatePersonIdentifier(@WebParam(name = "validationTypeKey") String validationTypeKey,
            @WebParam(name = "personIdentifierTypeKey") String personIdentifierTypeKey,
            @WebParam(name = "personId") String personId,
            @WebParam(name = "personIdentifierInfo") PersonIdentifierInfo personIdentifierInfo,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Creates a new Person Identifier. The Person Identifier Id, Type, and Meta information may not be set in the supplied data.
     *
     * @param personIdentifierTypeKey the identifier for the person identifier Type to assign to this identifier object
     * @param personId id of the person to whom this identifier is attached
     * @param personIdentifierInfo the person to be validated
     * @param contextInfo Context information containing the principalId and locale information about the caller of service
     * operation
     * @return the new PersonIdentifier
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException personIdentifierType does not exist or is not supported
     * @throws InvalidParameterException personInfo or contextInfo is not valid
     * @throws MissingParameterException personIdentifierType, personIdentifierInfo, or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     * @throws ReadOnlyException an attempt at supplying information designated as read only
     */
    public PersonIdentifierInfo createPersonIdentifier(@WebParam(name = "personIdentifierTypeKey") String personIdentifierTypeKey,
            @WebParam(name = "personId") String personId,
            @WebParam(name = "personIdentifierInfo") PersonIdentifierInfo personIdentifierInfo,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws
            DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException;

    /**
     * Updates an existing Person Identifier. The Person Identifier Id, Type, and Meta information may not be changed.
     *
     * @param personIdentifierId the identifier for the PersonIdentifier to be updated
     * @param personIdentifierInfo the person to be validated
     * @param contextInfo Context information containing the principalId and locale information about the caller of service
     * operation
     * @return the updated PersonIdentifier
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException personId is not found
     * @throws InvalidParameterException personId, personIdentifierInfo or contextInfo is not valid
     * @throws MissingParameterException personId, personIdentifierInfo, or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     * @throws ReadOnlyException an attempt at supplying information designated as read only
     * @throws VersionMismatchException if someone else has updated this person record since you fetched the version you are
     * updating.
     */
    public PersonIdentifierInfo updatePersonIdentifier(@WebParam(name = "personIdentifierId") String personIdentifierId,
            @WebParam(name = "personIdentifierInfo") PersonIdentifierInfo personIdentifierInfo,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws
            DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException,
            VersionMismatchException;

    /**
     * Deletes an existing Person Identifier.
     *
     * @param personIdentifierId the identifier for the person identifier record to be deleted
     * @param contextInfo Context information containing the principalId and locale information about the caller of service
     * operation
     * @return the status of the delete operation. This must always be true.
     * @throws DoesNotExistException personIdentifierId is not found
     * @throws InvalidParameterException personIdentifierId or contextInfo is not valid
     * @throws MissingParameterException personIdentifierId or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public StatusInfo deletePersonIdentifier(@WebParam(name = "personIdentifierId") String personIdentifierId,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Get person identifiers for a person
     *
     * @param personId the identifier for the person
     * @param contextInfo Context information containing the principalId and locale information about the caller of service
     * operation
     * @return list of identifiers for that person
     * @throws DoesNotExistException personId is not found
     * @throws InvalidParameterException personId or contextInfo is not valid
     * @throws MissingParameterException personId or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<PersonIdentifierInfo> getPersonIdentifiersByPerson(@WebParam(name = "personId") String personId,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Get PersonIdentifiers by identifier
     *
     * Note: Some identifiers may not be unique
     *
     * @param identifier the identifier for the person
     * @param personIdentifierTypeKey the type of the identifier to be searched
     * @param contextInfo Context information containing the principalId and locale information about the caller of service
     * operation
     * @return a list of identifiers matching the specific identifier
     * @throws InvalidParameterException personIdentifierTypeKey or contextInfo is not valid
     * @throws MissingParameterException personIdentifierTypeKey or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<PersonIdentifierInfo> getPersonIdentifiersByIdentifier(
            @WebParam(name = "personIdentifierTypeKey") String personIdentifierTypeKey,
            @WebParam(name = "identifier") String identifier,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    ////
    //// person bio demographics
    ////
    /**
     * Retrieves a Person Bio Demographic by a Person Bio Demographic ID
     *
     * @param personBioDemographicsId the Person Bio Demographic ID
     * @param contextInfo Context information containing the principalId and locale information about the caller of service
     * operation
     * @return the PersonIdentifier
     * @throws DoesNotExistException personBioDemographicsId not found
     * @throws InvalidParameterException invalid personBioDemographicsId or contextInfo
     * @throws MissingParameterException personBioDemographicsId or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public PersonBioDemographicsInfo getPersonBioDemographics(
            @WebParam(name = "personBioDemographicsId") String personBioDemographicsId,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of person identifiers by a list of Person Bio Demographic IDs
     *
     * @param personBioDemographicsIds a list of Person Bio Demographic IDs
     * @param contextInfo Context information containing the principalId and locale information about the caller of service
     * operation
     * @return a List of Person Bio Demographics
     * @throws DoesNotExistException personBioDemographicsId not found
     * @throws InvalidParameterException invalid personBioDemographicsIds or contextInfo
     * @throws MissingParameterException personBioDemographicsIds or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<PersonBioDemographicsInfo> getPersonBioDemographicsByIds(
            @WebParam(name = "personBioDemographicsIds") List<String> personBioDemographicsIds,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of Person Bio Demographic Ids by person type.
     *
     * @param personIdentifierTypeKey the personIdentifier Type Key to search by
     * @param contextInfo Context information containing the principalId and locale information about the caller of service
     * operation
     * @return a List of PersonBioDemographics IDs
     * @throws DoesNotExistException personIdentifierTypeKey not found
     * @throws InvalidParameterException invalid personIdentifierTypeKey or contextInfo
     * @throws MissingParameterException personIdentifierTypeKey or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> getPersonBioDemographicsIdsByType(
            @WebParam(name = "personIdentifierTypeKey") String personIdentifierTypeKey,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Searches for Person Bio Demographic Ids based on the criteria and returns a list of Person Bio Demographic identifiers
     * which match the search criteria.
     *
     * @param criteria the search criteria
     * @param contextInfo Context information containing the principalId and locale information about the caller of service
     * operation
     * @return a List of PersonBioDemographics IDs
     * @throws InvalidParameterException invalid criteria or contextInfo
     * @throws MissingParameterException criteria or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> searchForPersonBioDemographicsIds(@WebParam(name = "criteria") QueryByCriteria criteria,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Searches for person identifiers based on the criteria and returns a list of persons which match the search criteria.
     *
     * @param criteria the search criteria
     * @param contextInfo Context information containing the principalId and locale information about the caller of service
     * operation
     * @return a List of Person Bio Demographics
     * @throws InvalidParameterException invalid criteria or contextInfo
     * @throws MissingParameterException criteria or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<PersonBioDemographicsInfo> searchForPersonBioDemographics(@WebParam(name = "criteria") QueryByCriteria criteria,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Validates a PersonBioDemographics. If an identifier is present for the Person Bio Demographic and a record is found for
     * that identifier, the validation checks if the PersonBioDemographics can be updated to the new values. If an identifier is
     * not present or a record does not exist, the validation checks if the person with the given data can be created.
     *
     * @param validationTypeKey the identifier for the validation Type
     * @param personIdentifierTypeKey the identifier for the person identifier type to be validated
     * @param personId id of person for whom this identifier is being applied
     * @param personBioDemographicsInfo the person to be validated
     * @param contextInfo Context information containing the principalId and locale information about the caller of service
     * operation
     * @return a list of validation results or an empty list if validation succeeded
     * @throws InvalidParameterException personBioDemographicsInfo or contextInfo is not valid
     * @throws MissingParameterException validationTypeKey, personIdentifierTypeKey, personInfo, or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<ValidationResultInfo> validatePersonBioDemographics(@WebParam(name = "validationTypeKey") String validationTypeKey,
            @WebParam(name = "personIdentifierTypeKey") String personIdentifierTypeKey,
            @WebParam(name = "personId") String personId,
            @WebParam(name = "personBioDemographicsInfo") PersonBioDemographicsInfo personBioDemographicsInfo,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Creates a new Person Bio Demographic. The Person Bio Demographic Id, Type, and Meta information may not be set in the
     * supplied data.
     *
     * @param personIdentifierTypeKey the identifier for the person identifier Type to assign to this identifier object
     * @param personId id of the person to whom this identifier is attached
     * @param personBioDemographicsInfo the person to be validated
     * @param contextInfo Context information containing the principalId and locale information about the caller of service
     * operation
     * @return the new PersonBioDemographics
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException personIdentifierType does not exist or is not supported
     * @throws InvalidParameterException personBioDemographicsInfo or contextInfo is not valid
     * @throws MissingParameterException personIdentifierType, personBioDemographicsInfo, or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     * @throws ReadOnlyException an attempt at supplying information designated as read only
     */
    public PersonBioDemographicsInfo createPersonBioDemographics(
            @WebParam(name = "personIdentifierTypeKey") String personIdentifierTypeKey,
            @WebParam(name = "personId") String personId,
            @WebParam(name = "personBioDemographicsInfo") PersonBioDemographicsInfo personBioDemographicsInfo,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws
            DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException;

    /**
     * Updates an existing Person Bio Demographic. The Person Bio Demographic Id, Type, and Meta information may not be changed.
     *
     * @param personBioDemographicsId the identifier for the PersonBioDemographics to be updated
     * @param personBioDemographicsInfo the person to be validated
     * @param contextInfo Context information containing the principalId and locale information about the caller of service
     * operation
     * @return the updated PersonBioDemographics
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException personBioDemographicsId is not found
     * @throws InvalidParameterException personBioDemographicsId, personBioDemographicsInfo or contextInfo is not valid
     * @throws MissingParameterException personBioDemographicsId, personBioDemographicsInfo, or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     * @throws ReadOnlyException an attempt at supplying information designated as read only
     * @throws VersionMismatchException if someone else has updated this person record since you fetched the version you are
     * updating.
     */
    public PersonBioDemographicsInfo updatePersonBioDemographics(
            @WebParam(name = "personBioDemographicsId") String personBioDemographicsId,
            @WebParam(name = "personBioDemographicsInfo") PersonBioDemographicsInfo personBioDemographicsInfo,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws
            DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException,
            VersionMismatchException;

    /**
     * Deletes an existing Person Bio Demographic.
     *
     * @param personBioDemographicsId the identifier for the person identifier record to be deleted
     * @param contextInfo Context information containing the principalId and locale information about the caller of service
     * operation
     * @return the status of the delete operation. This must always be true.
     * @throws DoesNotExistException personInfo is not found
     * @throws InvalidParameterException personInfo or contextInfo is not valid
     * @throws MissingParameterException personInfo or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public StatusInfo deletePersonBioDemographics(@WebParam(name = "personBioDemographicsId") String personBioDemographicsId,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Get person bio demographics for a person
     *
     * @param personId the identifier for the person
     * @param contextInfo Context information containing the principalId and locale information about the caller of service
     * operation
     * @return the person's demo graphics
     * @throws DoesNotExistException personInfo is not found or person has no bio demographics
     * @throws InvalidParameterException personInfo or contextInfo is not valid
     * @throws MissingParameterException personInfo or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public PersonBioDemographicsInfo getPersonBioDemographicsByPerson(@WebParam(name = "personId") String personId,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    ////
    //// person affiliations
    ////
    /**
     * Retrieves a Person Affiliation by a Person Affiliation ID
     *
     * @param personAffiliationId the Person Affiliation ID
     * @param contextInfo Context information containing the principalId and locale information about the caller of service
     * operation
     * @return the PersonAffiliation
     * @throws DoesNotExistException personAffiliationId not found
     * @throws InvalidParameterException invalid personAffiliationId or contextInfo
     * @throws MissingParameterException personAffiliationId or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public PersonAffiliationInfo getPersonAffiliation(@WebParam(name = "personAffiliationId") String personAffiliationId,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of person identifiers by a list of Person Affiliation IDs
     *
     * @param personAffiliationIds a list of Person Affiliation IDs
     * @param contextInfo Context information containing the principalId and locale information about the caller of service
     * operation
     * @return a List of Person Affiliations
     * @throws DoesNotExistException personAffiliationId not found
     * @throws InvalidParameterException invalid personAffiliationId or contextInfo
     * @throws MissingParameterException personAffiliationId or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<PersonAffiliationInfo> getPersonAffiliationsByIds(
            @WebParam(name = "personAffiliationIds") List<String> personAffiliationIds,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of Person Affiliation Ids by person type.
     *
     * @param personAffiliationTypeKey the personAffiliation Type Key to search by
     * @param contextInfo Context information containing the principalId and locale information about the caller of service
     * operation
     * @return a List of PersonAffiliation IDs
     * @throws DoesNotExistException personAffiliationTypeKey not found
     * @throws InvalidParameterException invalid personAffiliationTypeKey or contextInfo
     * @throws MissingParameterException personAffiliationTypeKey or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> getPersonAffiliationIdsByType(@WebParam(name = "personAffiliationTypeKey") String personAffiliationTypeKey,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Searches for Person Affiliation Ids based on the criteria and returns a list of Person Affiliations which match the search
     * criteria.
     *
     * @param criteria the search criteria
     * @param contextInfo Context information containing the principalId and locale information about the caller of service
     * operation
     * @return a List of PersonAffiliation IDs
     * @throws InvalidParameterException invalid criteria or contextInfo
     * @throws MissingParameterException criteria or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> searchForPersonAffiliationIds(@WebParam(name = "criteria") QueryByCriteria criteria,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Searches for person affiliations based on the criteria and returns a list of persons which match the search criteria.
     *
     * @param criteria the search criteria
     * @param contextInfo Context information containing the principalId and locale information about the caller of service
     * operation
     * @return a List of Person Affiliations
     * @throws InvalidParameterException invalid criteria or contextInfo
     * @throws MissingParameterException criteria or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<PersonAffiliationInfo> searchForPersonAffiliations(@WebParam(name = "criteria") QueryByCriteria criteria,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Validates a PersonAffiliation. If an affiliation is present for the Person Affiliation and a record is found for that
     * affiliation, the validation checks if the PersonAffiliation can be updated to the new values. If an affiliation is not
     * present or a record does not exist, the validation checks if the person with the given data can be created.
     *
     * @param validationTypeKey the affiliation for the validation Type
     * @param personAffiliationTypeKey the affiliation for the person affiliation type to be validated
     * @param personId id of person for whom this affiliation is being applied
     * @param organizationId id of organizational unit for whom this affiliation is being applied
     * @param personAffiliationInfo the person to be validated
     * @param contextInfo Context information containing the principalId and locale information about the caller of service
     * operation
     * @return a list of validation results or an empty list if validation succeeded
     * @throws DoesNotExistException validationTypeKey or personAffiliationTypeKey is not found
     * @throws InvalidParameterException personAffiliationInfo or contextInfo is not valid
     * @throws MissingParameterException validationTypeKey, personAffiliationTypeKey, personAffiliationInfo, or contextInfo is
     * missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<ValidationResultInfo> validatePersonAffiliation(@WebParam(name = "validationTypeKey") String validationTypeKey,
            @WebParam(name = "personAffiliationTypeKey") String personAffiliationTypeKey,
            @WebParam(name = "personId") String personId,
            @WebParam(name = "organizationId") String organizationId,
            @WebParam(name = "personAffiliationInfo") PersonAffiliationInfo personAffiliationInfo,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Creates a new Person Affiliation. The Person Affiliation Id, Type, and Meta information may not be set in the supplied
     * data.
     *
     * @param personAffiliationTypeKey the affiliation for the person affiliation Type to assign to this affiliation object
     * @param personId id of the person to whom this affiliation is attached
     * @param organizationId id of organizational unit for whom this affiliation is being applied
     * @param personAffiliationInfo the person to be validated
     * @param contextInfo Context information containing the principalId and locale information about the caller of service
     * operation
     * @return the new PersonAffiliation
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException personAffiliationType does not exist or is not supported
     * @throws InvalidParameterException personInfo or contextInfo is not valid
     * @throws MissingParameterException personAffiliationType, personAffiliationInfo, or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     * @throws ReadOnlyException an attempt at supplying information designated as read only
     */
    public PersonAffiliationInfo createPersonAffiliation(
            @WebParam(name = "personAffiliationTypeKey") String personAffiliationTypeKey,
            @WebParam(name = "personId") String personId,
            @WebParam(name = "organizationId") String organizationId,
            @WebParam(name = "personAffiliationInfo") PersonAffiliationInfo personAffiliationInfo,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws
            DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException;

    /**
     * Updates an existing Person Affiliation. The Person Affiliation Id, Type, and Meta information may not be changed.
     *
     * @param personAffiliationId the affiliation for the PersonAffiliation to be updated
     * @param personAffiliationInfo the person to be validated
     * @param contextInfo Context information containing the principalId and locale information about the caller of service
     * operation
     * @return the updated PersonAffiliation
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException personId is not found
     * @throws InvalidParameterException personId, personAffiliationInfo or contextInfo is not valid
     * @throws MissingParameterException personId, personAffiliationInfo, or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     * @throws ReadOnlyException an attempt at supplying information designated as read only
     * @throws VersionMismatchException if someone else has updated this person record since you fetched the version you are
     * updating.
     */
    public PersonAffiliationInfo updatePersonAffiliation(@WebParam(name = "personAffiliationId") String personAffiliationId,
            @WebParam(name = "personAffiliationInfo") PersonAffiliationInfo personAffiliationInfo,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws
            DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException,
            VersionMismatchException;

    /**
     * Deletes an existing Person Affiliation.
     *
     * @param personAffiliationId the affiliation for the person affiliation record to be deleted
     * @param contextInfo Context information containing the principalId and locale information about the caller of service
     * operation
     * @return the status of the delete operation. This must always be true.
     * @throws DoesNotExistException personAffiliationId is not found
     * @throws InvalidParameterException personAffiliationId or contextInfo is not valid
     * @throws MissingParameterException personAffiliationId or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public StatusInfo deletePersonAffiliation(@WebParam(name = "personAffiliationId") String personAffiliationId,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Get person affiliations for a person
     *
     * @param personId the affiliation for the person
     * @param contextInfo Context information containing the principalId and locale information about the caller of service
     * operation
     * @return list of affiliations for that person
     * @throws DoesNotExistException personId is not found
     * @throws InvalidParameterException personId or contextInfo is not valid
     * @throws MissingParameterException personId or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<PersonAffiliationInfo> getPersonAffiliationsByPerson(@WebParam(name = "personId") String personId,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Get active people matching a name name fragment with the specified affiliation to a particular organization.
     * 
     * "Active" can be institutionally configured but is intended to select just those whose affiliations are current.
     * I.e. the store my contain many old students who graduated years ago but this method should filter them out.
     *
     * @param nameFragment to match the name
     * @param personAffiliationTypeKey the type of the affiliation to be searched
     * @param organizationId the affiliation for the person
     * @param contextInfo Context information containing the principalId and locale information about the caller of service
     * operation
     * @return a list of person ids matching the specific affiliation
     * @throws InvalidParameterException personAffiliationTypeKey or contextInfo is not valid
     * @throws MissingParameterException personAffiliationTypeKey or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<PersonInfo> getActivePeopleMatchingNameFragmentAndAffiliation(
            @WebParam(name = "nameFragment") String nameFragment,
            @WebParam(name = "personAffiliationTypeKey") String personAffiliationTypeKey,
            @WebParam(name = "organizationId") String organizationId,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;
    

    /**
     * Get the Organization Id to use for Institutional affiliations
     * 
     * This is sort of a default organization id to use when querying affiliations.
     *
     * @param contextInfo Context information containing the principalId and locale information about the caller of service
     * operation
     * @return a list of person ids matching the specific affiliation
     * @throws InvalidParameterException personAffiliationTypeKey or contextInfo is not valid
     * @throws MissingParameterException personAffiliationTypeKey or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public String getInstitutionalAffiliationOrganizationId(
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;
}
