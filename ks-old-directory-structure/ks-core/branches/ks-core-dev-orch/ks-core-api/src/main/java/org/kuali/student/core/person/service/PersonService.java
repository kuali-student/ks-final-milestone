/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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

import java.util.Date;
import java.util.List;

import javax.jws.WebParam;

import org.kuali.student.core.dictionary.dto.ObjectStructure;
import org.kuali.student.core.dto.StatusInfo;
import org.kuali.student.core.enumerable.dto.EnumeratedValue;
import org.kuali.student.core.exceptions.AlreadyExistsException;
import org.kuali.student.core.exceptions.CircularReferenceException;
import org.kuali.student.core.exceptions.DataValidationErrorException;
import org.kuali.student.core.exceptions.DisabledIdentifierException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.exceptions.VersionMismatchException;
import org.kuali.student.core.person.dto.PersonInfo;
import org.kuali.student.core.person.dto.PersonReferenceIdInfo;
import org.kuali.student.core.person.dto.PersonRelationInfo;
import org.kuali.student.core.person.dto.PersonRelationTypeInfo;
import org.kuali.student.core.person.dto.PersonTypeInfo;
import org.kuali.student.core.person.dto.PersonUsageTypeInfo;
import org.kuali.student.core.search.dto.QueryParamValue;
import org.kuali.student.core.search.dto.Result;
import org.kuali.student.core.search.dto.SearchCriteriaTypeInfo;
import org.kuali.student.core.search.dto.SearchResultTypeInfo;
import org.kuali.student.core.search.dto.SearchTypeInfo;
import org.kuali.student.core.validation.dto.ValidationResultContainer;
 
public interface PersonService {
    /** 
     * Retrieves the list of object type identifiers known by this service. Example: cluInfo.
     * @return list of object type identifiers
     * @throws OperationFailedException unable to complete request
	 */
    public List<String> findObjectTypes() throws OperationFailedException;

    /** 
     * Retrieves the basic dictionary information about a particular object structure. Including all variations based on a certain type and state. Example: Given that a CLU is of type "Course" and in the state of "Proposed," tell which fields are read only, mandatory, not applicable, have enumerations available, etc.
     * @param objectTypeKey identifier of the object type
     * @return describes the fields for the input object type
     * @throws DoesNotExistException specified objectTypeKey not found
     * @throws InvalidParameterException invalid objectTypeKey
     * @throws MissingParameterException missing objectTypeKey
     * @throws OperationFailedException unable to complete request
	 */
    public ObjectStructure fetchObjectStructure(@WebParam(name="objectTypeKey")String objectTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the list of enumeration values for a particular enumeration with a certain context for a particular date. The values returned should be those where the supplied date is between the effective and expiration dates. Certain enumerations may not support this functionality.
     * @param enumerationKey identifier of the enumeration
     * @param contextType identifier of the enumeration context type
     * @param contextValue value of the enumeration context
     * @param contextDate date and time to get the enumeration for
     * @return list of enumerated codes and values
     * @throws DoesNotExistException enumerationKey not found
     * @throws InvalidParameterException invalid enumerationKey, contextType, contextValue, contextDate
     * @throws MissingParameterException missing enumerationKey, contextType, contextValue, contextDate
     * @throws OperationFailedException unable to complete request
	 */
    public List<EnumeratedValue> fetchEnumeration(@WebParam(name="enumerationKey")String enumerationKey, @WebParam(name="contextType")String contextType, @WebParam(name="contextValue")String contextValue, @WebParam(name="contextDate")Date contextDate) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the list of search types known by this service.
     * @return list of search type information
     * @throws OperationFailedException unable to complete request
	 */
    public List<SearchTypeInfo> findSearchTypes() throws OperationFailedException;

    /** 
     * Retrieves information about a particular search type.
     * @param searchTypeKey identifier of the search type
     * @return information on the search type
     * @throws DoesNotExistException specified searchTypeKey not found
     * @throws InvalidParameterException invalid searchTypeKey
     * @throws MissingParameterException searchTypeKey not specified
     * @throws OperationFailedException unable to complete request
	 */
    public SearchTypeInfo fetchSearchType(@WebParam(name="searchTypeKey")String searchTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the list of search types which return results in the specified format.
     * @param searchResultTypeKey identifier of the search result type
     * @return list of search type information
     * @throws DoesNotExistException specified searchResultTypeKey not found
     * @throws InvalidParameterException invalid searchResultTypeKey
     * @throws MissingParameterException searchResultTypeKey not specified
     * @throws OperationFailedException unable to complete request
	 */
    public List<SearchTypeInfo> findSearchTypesByResult(@WebParam(name="searchResultTypeKey")String searchResultTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the list of search types which use criteria in the specified format.
     * @param searchCriteriaTypeKey identifier of the search criteria
     * @return list of search type information
     * @throws DoesNotExistException specified searchCriteriaTypeKey not found
     * @throws InvalidParameterException invalid searchCriteriaTypeKey
     * @throws MissingParameterException searchCriteriaTypeKey not specified
     * @throws OperationFailedException unable to complete request
	 */
    public List<SearchTypeInfo> findSearchTypesByCriteria(@WebParam(name="searchCriteriaTypeKey")String searchCriteriaTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the list of search result types known by this service. Search result types describe the return structure for a search.
     * @return list of search result type information
     * @throws OperationFailedException unable to complete request
	 */
    public List<SearchResultTypeInfo> findSearchResultTypes() throws OperationFailedException;

    /** 
     * Retrieves information about a particular search result type. Search result types describe the return structure for a search.
     * @param searchResultTypeKey identifier of the search result type
     * @return information on the search result type
     * @throws DoesNotExistException specified searchResultTypeKey not found
     * @throws InvalidParameterException invalid searchResultTypeKey
     * @throws MissingParameterException searchResultTypeKey not specified
     * @throws OperationFailedException unable to complete request
	 */
    public SearchResultTypeInfo fetchSearchResultType(@WebParam(name="searchResultTypeKey")String searchResultTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the list of search criteria types known by this service.
     * @return list of search criteria type information
     * @throws OperationFailedException unable to complete request
	 */
    public List<SearchCriteriaTypeInfo> findSearchCriteriaTypes() throws OperationFailedException;

    /** 
     * Retrieves information about a particular search criteria type.
     * @param searchCriteriaTypeKey identifier of the search criteria type
     * @return information on the search criteria type
     * @throws DoesNotExistException specified searchCriteriaTypeKey not found
     * @throws InvalidParameterException invalid searchCriteriaTypeKey
     * @throws MissingParameterException searchCriteriaTypeKey not specified
     * @throws OperationFailedException unable to complete request
	 */
    public SearchCriteriaTypeInfo fetchSearchCriteriaType(@WebParam(name="searchCriteriaTypeKey")String searchCriteriaTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the list of person types known by this service
     * @return list of person types
     * @throws OperationFailedException unable to complete request
	 */
    public List<PersonTypeInfo> findPersonTypes() throws OperationFailedException;

    /** 
     * Retrieves the metadata about the specified person type
     * @param personTypeKey type of person
     * @return information about a personType
     * @throws DoesNotExistException personType not found
     * @throws InvalidParameterException invalid personTypeKey
     * @throws MissingParameterException missing personTypeKey
     * @throws OperationFailedException unable to complete request
	 */
    public PersonTypeInfo fetchPersonType(@WebParam(name="personTypeKey")String personTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the metadata for a list of person type keys
     * @param personTypeKeyList List of person types
     * @return information about a list of personTypes
     * @throws DoesNotExistException One or more personTypeKeys not found
     * @throws InvalidParameterException One or more invalid personTypeKeys
     * @throws MissingParameterException missing personTypeKeys
     * @throws OperationFailedException unable to complete request
	 */
    public List<PersonTypeInfo> findPersonTypesByKeyList(@WebParam(name="personTypeKeyList")List<String> personTypeKeyList) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the list of person types which can be created by this service
     * @return list of person types
     * @throws OperationFailedException unable to complete request
	 */
    public List<PersonTypeInfo> findCreatablePersonTypes() throws OperationFailedException;

    /** 
     * Retrieves the list of person usage types
     * @return list of person usage types
     * @throws OperationFailedException unable to complete request
	 */
    public List<PersonUsageTypeInfo> findPersonUsageTypes() throws OperationFailedException;

    /** 
     * Retrieves the metadata for a person usage type identifier
     * @param personUsageTypeKey person usage type to be retrieved
     * @return information about a personUsageType
     * @throws DoesNotExistException personUsageTypeKey not found
     * @throws InvalidParameterException invalid personUsageTypeKey
     * @throws MissingParameterException missing personUsageTypeKey
     * @throws OperationFailedException unable to complete request
	 */
    public PersonUsageTypeInfo findPersonUsageType(@WebParam(name="personUsageTypeKey")String personUsageTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the metadata for a list of person usage type identifiers
     * @param personUsageTypeKeyList List of person usage types to be retrieved
     * @return list of personUsageType information
     * @throws DoesNotExistException One or more personUsageTypeKeys not found
     * @throws InvalidParameterException One or more invalid personUsageTypeKeys
     * @throws MissingParameterException missing personUsageTypeKeyList
     * @throws OperationFailedException unable to complete request
	 */
    public List<PersonUsageTypeInfo> findPersonUsageTypesByKeyList(@WebParam(name="personUsageTypeKeyList")List<String> personUsageTypeKeyList) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the list of person usage types for the specified person type key
     * @param personTypeKey type of person
     * @return list of person usage types
     * @throws DoesNotExistException personType not found
     * @throws InvalidParameterException invalid personType
     * @throws MissingParameterException missing personType
     * @throws OperationFailedException unable to complete request
	 */
    public List<PersonUsageTypeInfo> findPersonUsageTypesByPersonType(@WebParam(name="personTypeKey")String personTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the list of person to person relationship type known by this service
     * @return list of person relation type information
     * @throws OperationFailedException unable to complete request
	 */
    public List<PersonRelationTypeInfo> findPersonPersonRelationTypes() throws OperationFailedException;

    /** 
     * Validates a person. Depending on the value of validationType, this validation could be limited to tests on just the current object and its directly contained sub-objects or expanded to perform all tests related to this object. If an identifier is present for the learning objective category (and/or one of its contained sub-objects) and a record is found for that identifier, the validation checks if the learning objective category can be shifted to the new values. If an identifier is not present or a record cannot be found for the identifier, it is assumed that the record does not exist and as such, the checks performed will be much shallower, typically mimicking those performed by setting the validationType to the current object.
     * @param validationType identifier of the extent of validation
     * @param personInfo person information to be tested.
     * @return results from performing the validation
     * @throws DoesNotExistException validationTypeKey not found
     * @throws InvalidParameterException invalid validationTypeKey, personInfo
     * @throws MissingParameterException missing validationTypeKey, personInfo
     * @throws OperationFailedException unable to complete request
	 */
    public List<ValidationResultContainer> validatePerson(@WebParam(name="validationType")String validationType, @WebParam(name="personInfo")PersonInfo personInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Validates a personPersonRelation. Depending on the value of validationType, this validation could be limited to tests on just the current object and its directly contained sub-objects or expanded to perform all tests related to this object. If an identifier is present for the learning objective category (and/or one of its contained sub-objects) and a record is found for that identifier, the validation checks if the learning objective category can be shifted to the new values. If an identifier is not present or a record cannot be found for the identifier, it is assumed that the record does not exist and as such, the checks performed will be much shallower, typically mimicking those performed by setting the validationType to the current object.
     * @param validationType identifier of the extent of validation
     * @param personRelationInfo personPersonRelation information to be tested.
     * @return results from performing the validation
     * @throws DoesNotExistException validationTypeKey not found
     * @throws InvalidParameterException invalid validationTypeKey, personRelationInfo
     * @throws MissingParameterException missing validationTypeKey, personRelationInfo
     * @throws OperationFailedException unable to complete request
	 */
    public List<ValidationResultContainer> validatePersonPersonRelation(@WebParam(name="validationType")String validationType, @WebParam(name="personRelationInfo")PersonRelationInfo personRelationInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves all available information about a person
     * @param personId identifier of the person to be retrieved
     * @return information about a person
     * @throws DoesNotExistException personId not found
     * @throws DisabledIdentifierException personId found but has been retired
     * @throws InvalidParameterException invalid personId
     * @throws MissingParameterException missing personId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public PersonInfo fetchPerson(@WebParam(name="personId")String personId) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves information about a person, returning only the information associated with the specified personType
     * @param personId identifier of the person to be retrieved
     * @param personTypeKey person type identifier
     * @return information about a person, limited to information associated with the specified personType
     * @throws DoesNotExistException personId, personTypeKey not found
     * @throws DisabledIdentifierException personId found but has been retired
     * @throws InvalidParameterException invalid personId, personTypeKey
     * @throws MissingParameterException missing personId, personTypeKey
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public PersonInfo fetchPersonByPersonType(@WebParam(name="personId")String personId, @WebParam(name="personTypeKey")String personTypeKey) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves information about a person for a given usage
     * @param personId identifier of the person to be retrieved
     * @param personUsageTypeKey usage type to be retrieved by
     * @return information about a person, limited to information associated with the specified usage
     * @throws DoesNotExistException personId, personUsageTypeKey not found
     * @throws DisabledIdentifierException personId found but has been retired
     * @throws InvalidParameterException invalid personId, personUsageTypeKey
     * @throws MissingParameterException missing personId, personUsageTypeKey
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public PersonInfo fetchPersonByUsage(@WebParam(name="personId")String personId, @WebParam(name="personUsageTypeKey")String personUsageTypeKey) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves information for a list of person Ids
     * @param personIdList List of identifiers of persons to be retrieved
     * @return list of person information
     * @throws DoesNotExistException One or more personIds not found
     * @throws DisabledIdentifierException One or more personIds found but have been retired
     * @throws InvalidParameterException One or more invalid personIds
     * @throws MissingParameterException missing personIdList
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public List<PersonInfo> findPersonInfoByIdList(@WebParam(name="personIdList")List<String> personIdList) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves a list of person type keys for a given person
     * @param personId identifier of the person
     * @return list of person type keys
     * @throws DoesNotExistException personId not found
     * @throws DisabledIdentifierException personId found but has been retired
     * @throws InvalidParameterException invalid personId
     * @throws MissingParameterException missing personId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public List<String> findPersonTypesByPerson(@WebParam(name="personId")String personId) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Checks if a person is of a specified type
     * @param personId identifier of the person
     * @param personTypeKey type of person
     * @return true if the person specified is of the specified type
     * @throws DoesNotExistException personId, personTypeKey not found
     * @throws DisabledIdentifierException personId found but has been retired
     * @throws InvalidParameterException invalid personId, personTypeKey
     * @throws MissingParameterException missing personId, personTypeKey
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public Boolean isPersonType(@WebParam(name="personId")String personId, @WebParam(name="personTypeKey")String personTypeKey) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Validates if the information collected for a person is complete and valid for the specified personType (usually called before attempting to assign a new personType) Maybe removed/changed dependent upon Dictionary-related operations.
     * @param personId identifier of the person
     * @param personTypeKey type of person
     * @return results of the specified validations (general status, lists of failed validation rules, etc.)
     * @throws DoesNotExistException personId, personTypeKey not found
     * @throws DisabledIdentifierException personId found but has been retired
     * @throws InvalidParameterException invalid personId, personTypeKey
     * @throws MissingParameterException missing personId, personTypeKey
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public ValidationResultContainer validatePersonInfoByPersonType(@WebParam(name="personId")String personId, @WebParam(name="personTypeKey")String personTypeKey) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves the replacement identifier for a person using the old person identifier specified.
     * @param oldPersonId identifier of the person
     * @return current identifier for the specified person
     * @throws DoesNotExistException oldPersonId not found
     * @throws InvalidParameterException invalid oldPersonId
     * @throws MissingParameterException missing oldPersonId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public String fetchReplacementPersonId(@WebParam(name="oldPersonId")String oldPersonId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves information about external identifiers for a specified person and type of identifier.
     * @param personId identifier of the person
     * @param personReferenceIdTypeKey identifier of the person reference identifier type
     * @return information about the external identifiers for the specified person and type of identifier
     * @throws DoesNotExistException personId, personReferenceIdTypeKey not found
     * @throws InvalidParameterException invalid personId, personReferenceIdTypeKey
     * @throws MissingParameterException missing personId, personReferenceIdTypeKey
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public PersonReferenceIdInfo findPersonReferenceIds(@WebParam(name="personId")String personId, @WebParam(name="personReferenceIdTypeKey")String personReferenceIdTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves information about all external identifiers for a specified person
     * @param personId identifier of the person
     * @return list of information about the external identifiers for the specified person
     * @throws DoesNotExistException personId not found
     * @throws InvalidParameterException invalid personId
     * @throws MissingParameterException missing personId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public List<PersonReferenceIdInfo> findAllPersonReferenceIdsByPerson(@WebParam(name="personId")String personId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves information about the internal person identifier for a specified external identifier
     * @param personReferenceId external identifier of the person
     * @param personReferenceIdTypeKey identifier of the person reference identifier type
     * @return person identifier
     * @throws DoesNotExistException personReferenceId, personReferenceIdTypeKey not found
     * @throws InvalidParameterException invalid personReferenceId, personReferenceIdTypeKey
     * @throws MissingParameterException missing personReferenceId, personReferenceIdTypeKey
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public String fetchPersonIdByReferenceId(@WebParam(name="personReferenceId")String personReferenceId, @WebParam(name="personReferenceIdTypeKey")String personReferenceIdTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves the specified relation
     * @param personRelationId identifier of the relationship
     * @return the specified person person relationship
     * @throws DoesNotExistException personRelationId, relationship not found
     * @throws InvalidParameterException invalid personRelationId
     * @throws MissingParameterException missing personRelationId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public PersonRelationInfo fetchPersonPersonRelation(@WebParam(name="personRelationId")String personRelationId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves all relations of the specified type where the specified person is involved in either side of the relation
     * @param personId identifier of the person
     * @param personRelationTypeKey type of relationship
     * @return list of relationships matching the specified type where the person is on either side
     * @throws DoesNotExistException personId, personRelationTypeKey not found
     * @throws InvalidParameterException invalid personId, personRelationTypeKey
     * @throws MissingParameterException missing personId, personRelationTypeKey
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public List<PersonRelationInfo> findPersonPersonRelations(@WebParam(name="personId")String personId, @WebParam(name="personRelationTypeKey")String personRelationTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves all identifiers for relations of the specified type where the specified person is involved in either side of the relation
     * @param personId identifier of the person
     * @param personRelationTypeKey type of relationship
     * @return list of identifiers for relationships matching the specified type where the person is on either side
     * @throws DoesNotExistException personId, personRelationTypeKey not found
     * @throws InvalidParameterException invalid personId, personRelationTypeKey
     * @throws MissingParameterException missing personId, personRelationTypeKey
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public List<String> findPersonPersonRelationIds(@WebParam(name="personId")String personId, @WebParam(name="personRelationTypeKey")String personRelationTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves all people with the specified relation type to this person
     * @param relatedPersonId identifier of the related person
     * @param personRelationTypeKey type of relationship
     * @return list of people with matching relationships
     * @throws DoesNotExistException relatedPersonId, personRelationTypeKey not found
     * @throws InvalidParameterException invalid relatedPersonId, personRelationTypeKey
     * @throws MissingParameterException missing relatedPersonId, personRelationTypeKey
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public List<PersonInfo> findPeopleByRelation(@WebParam(name="relatedPersonId")String relatedPersonId, @WebParam(name="personRelationTypeKey")String personRelationTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves all person identifiers with the specified relation type to this person
     * @param relatedPersonId identifier of the related person
     * @param personRelationTypeKey type of relationship
     * @return list of identifers of people with matching relationships
     * @throws DoesNotExistException relatedPersonId, personRelationTypeKey not found
     * @throws InvalidParameterException invalid relatedPersonId, personRelationTypeKey
     * @throws MissingParameterException missing relatedPersonId, personRelationTypeKey
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public List<String> findPersonIdsByRelation(@WebParam(name="relatedPersonId")String relatedPersonId, @WebParam(name="personRelationTypeKey")String personRelationTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves all people related to the person with the specified relation type
     * @param personId identifier of the person
     * @param personRelationTypeKey type of relationship
     * @return list of people with matching relationships
     * @throws DoesNotExistException personId, personRelationTypeKey not found
     * @throws InvalidParameterException invalid personId, personRelationTypeKey
     * @throws MissingParameterException missing personId, personRelationTypeKey
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public List<PersonInfo> findRelatedPeopleByPerson(@WebParam(name="personId")String personId, @WebParam(name="personRelationTypeKey")String personRelationTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves all person identifiers related to the person with the specified relation type
     * @param personId identifier of the person
     * @param personRelationTypeKey type of relationship
     * @return list of identifiers of people with matching relationships
     * @throws DoesNotExistException personId, personRelationTypeKey not found
     * @throws InvalidParameterException invalid personId, personRelationTypeKey
     * @throws MissingParameterException missing personId, personRelationTypeKey
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public List<String> findRelatedPersonIdsByPerson(@WebParam(name="personId")String personId, @WebParam(name="personRelationTypeKey")String personRelationTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves results in tabular form for the specified parameters.
     * @param searchTypeKey search identifier
     * @param queryParamValues list of values for search parameters
     * @return list of results from the query
     * @throws DoesNotExistException specified search type not found
     * @throws InvalidParameterException invalid searchTypeKey, queryParamValueList
     * @throws MissingParameterException searchTypeKey, queryParamValueList not specified
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public List<Result> searchForResults(@WebParam(name="searchTypeKey")String searchTypeKey, @WebParam(name="queryParamValues")List<QueryParamValue> queryParamValues) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Creates a person record
     * @param personTypeKey type of person being created
     * @param personInfo information required to create a person - may consist of name, source type, effective date, expiration date, etc.
     * @return the created person information
     * @throws AlreadyExistsException person already exists
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws InvalidParameterException invalid personTypeKey, personInfo
     * @throws MissingParameterException missing personTypeKey, personInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public PersonInfo createPerson(@WebParam(name="personTypeKey")String personTypeKey, @WebParam(name="personInfo")PersonInfo personInfo) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Updates a person record
     * @param personId identifier for person to be updated
     * @param personInfo information needed to update a person record
     * @return updated person information
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws DoesNotExistException personId not found
     * @throws DisabledIdentifierException personId found but has been retired
     * @throws InvalidParameterException invalid personId, personInfo
     * @throws MissingParameterException missing personId, personInfo
     * @throws VersionMismatchException The action was attempted on an out of date version.
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public PersonInfo updatePerson(@WebParam(name="personId")String personId, @WebParam(name="personInfo")PersonInfo personInfo) throws DataValidationErrorException, DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, VersionMismatchException, OperationFailedException, PermissionDeniedException;

    /** 
     * Deletes a person record.
     * @param personId identifier for person to be deleted
     * @return status of the operation (success or failure)
     * @throws DoesNotExistException person does not exist
     * @throws DisabledIdentifierException personId found but has been retired
     * @throws InvalidParameterException invalid personId
     * @throws MissingParameterException invalid personId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public StatusInfo deletePerson(@WebParam(name="personId")String personId) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Assigns a personType to a person record
     * @param personId identifier for person having a personType assigned
     * @param personTypeKey type of person being assigned
     * @return status of the operation
     * @throws AlreadyExistsException person already has specified personType associated
     * @throws DisabledIdentifierException personId found but has been retired
     * @throws InvalidParameterException invalid personId, personTypeKey
     * @throws MissingParameterException missing personId, personType
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public StatusInfo assignPersonType(@WebParam(name="personId")String personId, @WebParam(name="personTypeKey")String personTypeKey) throws AlreadyExistsException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Deassociates a personType from a person record
     * @param personId identifier for person having a personType removed
     * @param personTypeKey type of person being removed
     * @return status of the operation
     * @throws DisabledIdentifierException personId found but has been retired
     * @throws DoesNotExistException person, personType, or association does not exist
     * @throws InvalidParameterException invalid personId, personType
     * @throws MissingParameterException missing personId, personType
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public StatusInfo removePersonType(@WebParam(name="personId")String personId, @WebParam(name="personTypeKey")String personTypeKey) throws DisabledIdentifierException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Assigns an external identifier record to a person
     * @param personId identifier for person having a reference identifier assigned
     * @param personReferenceIdTypeKey type of the reference identifier being assigned
     * @param personReferenceId reference identifier being assigned
     * @param personReferenceIdInfo information about the reference identifier being assigned
     * @return information about the reference identifer just assigned
     * @throws AlreadyExistsException person already has specified reference identifier associated
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws DisabledIdentifierException personId found but has been retired
     * @throws InvalidParameterException invalid personId, personReferenceIdTypeKey, personReferenceId, personReferenceIdInfo
     * @throws MissingParameterException missing personId, personReferenceIdTypeKey, personReferenceId, personReferenceIdInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public PersonReferenceIdInfo assignReferenceId(@WebParam(name="personId")String personId, @WebParam(name="personReferenceIdTypeKey")String personReferenceIdTypeKey, @WebParam(name="personReferenceId")String personReferenceId, @WebParam(name="personReferenceIdInfo")PersonReferenceIdInfo personReferenceIdInfo) throws AlreadyExistsException, DataValidationErrorException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Updates information about an external identifier for a person
     * @param personId identifier for person having a reference identifier updated
     * @param personReferenceIdTypeKey type of reference identifier being updated
     * @param personReferenceId reference identifier being updated
     * @param personReferenceIdInfo information about the reference identifier being updated
     * @return information about the reference identifer just updated
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws DoesNotExistException person or reference identifier not found
     * @throws DisabledIdentifierException personId found but has been retired
     * @throws InvalidParameterException invalid personId, personReferenceIdTypeKey, personReferenceId, personReferenceIdInfo
     * @throws MissingParameterException missing personId, personReferenceIdTypeKey, personReferenceId, personReferenceIdInfo
     * @throws VersionMismatchException The action was attempted on an out of date version.
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public PersonReferenceIdInfo updateReferenceId(@WebParam(name="personId")String personId, @WebParam(name="personReferenceIdTypeKey")String personReferenceIdTypeKey, @WebParam(name="personReferenceId")String personReferenceId, @WebParam(name="personReferenceIdInfo")PersonReferenceIdInfo personReferenceIdInfo) throws DataValidationErrorException, DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, VersionMismatchException, OperationFailedException, PermissionDeniedException;

    /** 
     * Removes information about an external identifier for a person
     * @param personId identifier for person having a reference identifier updated
     * @param personReferenceIdTypeKey type of reference identifier being updated
     * @param personReferenceId reference identifier being updated
     * @return status of the operation
     * @throws DoesNotExistException person or reference identifier not found
     * @throws InvalidParameterException invalid personId, personReferenceIdTypeKey, personReferenceId
     * @throws MissingParameterException missing personId, personReferenceIdTypeKey, personReferenceId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public StatusInfo removeReferenceId(@WebParam(name="personId")String personId, @WebParam(name="personReferenceIdTypeKey")String personReferenceIdTypeKey, @WebParam(name="personReferenceId")String personReferenceId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Creates a person to person relationship
     * @param personId person identifier
     * @param relatedPersonId person identifier
     * @param personRelationTypeKey person to person relationship type
     * @param personRelationInfo information to create the person to person relationship
     * @return the created person to person relation information
     * @throws AlreadyExistsException personRelationship already exists
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws DoesNotExistException personId, relatedPersonId not found
     * @throws CircularReferenceException personId and relatedPersonId refer to the same person
     * @throws InvalidParameterException invalid personId, relatedPersonId, personRelationTypeKey, personRelationInfo
     * @throws MissingParameterException missing personId, relatedPersonId, personRelationTypeKey, personRelationInfo
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public PersonRelationInfo createPersonPersonRelation(@WebParam(name="personId")String personId, @WebParam(name="relatedPersonId")String relatedPersonId, @WebParam(name="personRelationTypeKey")String personRelationTypeKey, @WebParam(name="personRelationInfo")PersonRelationInfo personRelationInfo) throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, CircularReferenceException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Updates a person to person relationship
     * @param personRelationId identifier for person relationship to be updated
     * @param personRelationInfo changed information about a person relationship
     * @return updated person relation information
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws DoesNotExistException personRelationId not found
     * @throws InvalidParameterException invalid personRelationId, personRelationInfo
     * @throws MissingParameterException missing personRelationId, personRelationInfo
     * @throws VersionMismatchException The action was attempted on an out of date version.
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public PersonRelationInfo updatePersonPersonRelation(@WebParam(name="personRelationId")String personRelationId, @WebParam(name="personRelationInfo")PersonRelationInfo personRelationInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, VersionMismatchException, OperationFailedException, PermissionDeniedException;

    /** 
     * Deletes a person relationship.
     * @param personRelationId identifier for person relationship to be deleted
     * @return status of the operation (success or failure)
     * @throws DoesNotExistException person relationship does not exist
     * @throws InvalidParameterException invalid personRelationId
     * @throws MissingParameterException invalid personRelationId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public StatusInfo deletePersonPersonRelation(@WebParam(name="personRelationId")String personRelationId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

}
