package org.kuali.student.poc.wsdl.personidentity.person;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.kuali.student.poc.wsdl.personidentity.exceptions.AlreadyExistsException;
import org.kuali.student.poc.wsdl.personidentity.exceptions.CircularReferenceException;
import org.kuali.student.poc.wsdl.personidentity.exceptions.DisabledIdentifierException;
import org.kuali.student.poc.wsdl.personidentity.exceptions.DoesNotExistException;
import org.kuali.student.poc.wsdl.personidentity.exceptions.InvalidParameterException;
import org.kuali.student.poc.wsdl.personidentity.exceptions.MissingParameterException;
import org.kuali.student.poc.wsdl.personidentity.exceptions.OperationFailedException;
import org.kuali.student.poc.wsdl.personidentity.exceptions.PermissionDeniedException;
import org.kuali.student.poc.wsdl.personidentity.exceptions.ReadOnlyException;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonAttributeSetTypeDisplay;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonAttributeSetTypeInfo;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonAttributeTypeInfo;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonCreateInfo;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonCriteria;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonDisplay;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonInfo;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonRelationCreateInfo;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonRelationCriteria;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonRelationDisplay;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonRelationInfo;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonRelationTypeDisplay;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonRelationUpdateInfo;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonTypeDisplay;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonTypeInfo;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonUpdateInfo;
import org.kuali.student.poc.xsd.personidentity.person.dto.ValidationError;

@WebService(name = "PersonService", targetNamespace = "http://student.kuali.org/poc/wsdl/personidentity/person")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface PersonService {

	// Setup Operations
	/**
	 * Retrieves the list of person types known by this service.
	 * 
	 * @return list of person types
	 * @throws OperationFailedException
	 */
	@WebMethod
	public List<PersonTypeDisplay> findPersonTypes()
			throws OperationFailedException;

	/**
	 * Retrieves the list of person types which can be created by this service.
	 * 
	 * @return list of person types
	 * @throws OperationFailedException
	 */
	@WebMethod
	public List<PersonTypeDisplay> findCreatablePersonTypes()
			throws OperationFailedException;

	/**
	 * Retrieves the list of person attribute set types
	 * 
	 * @return list of person attribute set types
	 * @throws OperationFailedException
	 */
	@WebMethod
	public List<PersonAttributeSetTypeDisplay> findPersonAttributeSetTypes()
			throws OperationFailedException;

	/**
	 * Retrieves the list of person attribute sets for the specified person type
	 * key
	 * 
	 * @param personTypeKey
	 * @return list of person attribute set types
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 */
	@WebMethod
	public List<PersonAttributeSetTypeDisplay> findPersonAttributeSetTypesForPersonType(
			@WebParam(name = "personTypeKey")
			Long personTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException;

	/**
	 * Retrieves the metadata about the specified person type
	 * 
	 * @param personTypeKey
	 * @return information about a personType
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 */
	@WebMethod
	public PersonTypeInfo fetchPersonType(@WebParam(name = "personTypeKey")
	Long personTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException;

	/**
	 * Retrieves the metadata about the specified person attribute set type
	 * 
	 * @param personAttributeSetTypeKey
	 * @return information about a personAttributeSetType
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 */
	@WebMethod
	public PersonAttributeSetTypeInfo fetchPersonAttributeSetType(
			@WebParam(name = "personAttributeSetTypeKey")
			Long personAttributeSetTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException;

	/**
	 * Retrieves the list of person to person relationship type known by this
	 * service
	 * 
	 * @return list of person person relation types
	 * @throws OperationFailedException
	 */
	@WebMethod
	public List<PersonRelationTypeDisplay> fetchPersonRelationTypes()
			throws OperationFailedException;

	/**
	 * Retrieves all available information about a person.
	 * 
	 * @param personId
	 *            identifier of the person to be retrieved
	 * @return all information available through the person service
	 * @throws DoesNotExistException
	 * @throws DisabledIdentifierException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 */
	@WebMethod
	public PersonInfo fetchFullPersonInfo(@WebParam(name = "personId")
	Long personId) throws DoesNotExistException, DisabledIdentifierException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException;

	/**
	 * Retrieves information about a person, returning only the information
	 * associated with the specified personType. May be converted to return the
	 * person display list instead (limiting the variable returns to searches
	 * only)
	 * 
	 * @param personId
	 * @param personTypeKey
	 * @return information about a person, limited to information associated
	 *         with the specified personType
	 * @throws DoesNotExistException
	 * @throws DisabledIdentifierException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 */
	@WebMethod
	public PersonInfo fetchPersonInfoByPersonType(@WebParam(name = "personId")
	Long personId, @WebParam(name = "personTypeKey")
	Long personTypeKey) throws DoesNotExistException,
			DisabledIdentifierException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException;

	/**
	 * Retrieves information about a person through a list of person attribute
	 * sets May be converted to return the person display list instead (limiting
	 * the variable returns to searches only)
	 * 
	 * @param personId
	 * @param personAttributeSetTypeKeyList
	 * @return information about a person, limited to information associated
	 *         with the specified attribute set list
	 * @throws DoesNotExistException
	 * @throws DisabledIdentifierException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 */
	@WebMethod
	public PersonInfo fetchPersonInfoByPersonAttributeSetTypes(
			@WebParam(name = "personId")
			Long personId, @WebParam(name = "personAttributeSetTypeKeyList")
			List<Long> personAttributeSetTypeKeyList)
			throws DoesNotExistException, DisabledIdentifierException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException;

	/**
	 * Retrieves a list of which person attribute sets should be available for a
	 * given person
	 * 
	 * @param personId
	 * @return list of person attribute set type keys
	 * @throws DoesNotExistException
	 * @throws DisabledIdentifierException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 */
	@WebMethod
	public List<Long> findPersonAttributeSetTypesForPerson(
			@WebParam(name = "personId")
			Long personId) throws DoesNotExistException,
			DisabledIdentifierException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException;

	/**
	 * Retrieves a list of person type keys for a given person
	 * 
	 * @param personId
	 * @return list of person type keys
	 * @throws DoesNotExistException
	 * @throws DisabledIdentifierException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 */
	@WebMethod
	public List<Long> findPersonTypesForPerson(@WebParam(name = "personId")
	Long personId) throws DoesNotExistException, DisabledIdentifierException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException;

	/**
	 * Retrieves a list of person ids for a given person type key
	 * 
	 * @param personTypeKey
	 * @param personFilter
	 *            additional criteria used to narrow the list of results
	 * @return list of person identifiers
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 */
	@WebMethod
	public List<Long> findPersonIdsForPersonType(
			@WebParam(name = "personTypeKey")
			Long personTypeKey, @WebParam(name = "personFilter")
			PersonCriteria personFilter) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException;

	/**
	 * Checks if a person is of a specified type
	 * 
	 * @param personId
	 * @param personTypeKey
	 * @return true if the person specified is of the specified type
	 * @throws DoesNotExistException
	 * @throws DisabledIdentifierException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 */
	@WebMethod
	public boolean isPersonType(@WebParam(name = "personId")
	Long personId, @WebParam(name = "personTypeKey")
	Long personTypeKey) throws DoesNotExistException,
			DisabledIdentifierException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException;

	/**
	 * Validates if the information collected for a person is complete and valid
	 * for the specified personType (usually called before attempting to assign
	 * a new personType)
	 * 
	 * @param personId
	 * @param personTypeKey
	 * @return results of the specified validations (general status, lists of
	 *         failed validation rules, etc.)
	 * @throws DoesNotExistException
	 * @throws DisabledIdentifierException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 */
	@WebMethod
	public ValidationError validatePersonInfoForPersonType(@WebParam(name = "personId")
	Long personId, @WebParam(name = "personTypeKey")
	Long personTypeKey) throws DoesNotExistException,
			DisabledIdentifierException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException;

	/**
	 * Retrieves the replacement identifier for a person using the old person
	 * identifier specified.
	 * 
	 * @param personId
	 * @return current identifier for the specified person
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 */
	@WebMethod
	public Long fetchReplacementPersonId(@WebParam(name = "personId")
	Long personId) throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException;

	/**
	 * Retrieves all relations of the specified type where the specified person
	 * is involved in either side of the relation
	 * 
	 * @param personId
	 * @param personRelationTypeKey
	 * @return list of relationships matching the specified type where the
	 *         person is on either side
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 */
	@WebMethod
	public List<PersonRelationDisplay> findPersonRelations(
			@WebParam(name = "personId")
			Long personId, @WebParam(name = "personRelationTypeKey")
			Long personRelationTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException;

	/**
	 * Retrieves all identifiers for relations of the specified type where the
	 * specified person is involved in either side of the relation
	 * 
	 * @param personId
	 * @param personRelationTypeKey
	 * @return list of identifiers for people with relationships matching the
	 *         specified type where the person is on either side
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 */
	@WebMethod
	public List<Long> findPersonPersonRelationIds(@WebParam(name = "personId")
	Long personId, @WebParam(name = "personRelationTypeKey")
	Long personRelationTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException;

	/**
	 * Retrieves the specified relation
	 * 
	 * @param personRelationId
	 * @return the specified person person relationship
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 */
	@WebMethod
	public PersonRelationInfo fetchPersonRelation(
			@WebParam(name = "personRelationId")
			Long personRelationId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException;

	// Search ////////////////////////////////////////////////////////////////

	/**
	 * Retrieves identifiers of existing people matching the specified criteria.
	 * 
	 * @param personCriteria
	 *            criteria to be used for retrieval of multiple people, e.g. by
	 *            personType, by name, by birthdate
	 * @return List of person identifiers that match the supplied criteria
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 */
	@WebMethod
	public List<Long> searchForPersonIds(@WebParam(name = "personCriteria")
	PersonCriteria personCriteria) throws InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException;

	/**
	 * Retrieves basic attribute set for existing people matching the specified
	 * criteria. *What is in the basic attribute set?
	 * 
	 * @param personCriteria
	 *            criteria to be used for retrieval of multiple people, e.g. by
	 *            name, by birthdate
	 * @return List of Persons with data that match the supplied criteria. Data
	 *         is from the basic Attribute Set only
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 */
	@WebMethod
	public List<PersonInfo> searchForPeople(@WebParam(name = "personCriteria")
	PersonCriteria personCriteria) throws InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException;

	/**
	 * Retrieves appropriate Attribute Set(s) for existing people of the
	 * specified Person Type and matching other criteria. The information
	 * returned is determined by the Attribute Set(s) associated with the
	 * specified Person Type
	 * 
	 * @param personTypeKey
	 *            key for the person type used as both constraint and output
	 *            modifier
	 * @param personCriteria
	 *            criteria to be used for retrieval of multiple people, e.g. by
	 *            name, by birthdate
	 * @return List of Persons with data that match the supplied criteria. Data
	 *         returned is determined by Attribute Set(s) for this Person Type.
	 *         Only people of the specified person type are returned.
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 */
	@WebMethod
	public List<PersonInfo> searchForPeopleByPersonType(
			@WebParam(name = "personTypeKey")
			Long personTypeKey, @WebParam(name = "personCriteria")
			PersonCriteria personCriteria) throws InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException;

	/**
	 * Retrieves information from the selected Attribute Set for existing people
	 * with information in the specified Attribute Set Type and matching other
	 * criteria.
	 * 
	 * @param personAttributeSetTypeKey
	 *            key for the person attribute set type used as both a
	 *            constraint and output modifier
	 * @param personCriteria
	 *            criteria to be used for retrieval of multiple people, e.g. by
	 *            name, by birthdate
	 * @return List of Persons with data that match the supplied criteria. Data
	 *         returned is determined by selected Attribute Set. Only people
	 *         with the data in the Attribute Set is returned.
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 */
	@WebMethod
	public List<PersonInfo> searchForPeopleByPersonAttributeSetType(
			@WebParam(name = "personAttributeSetTypeKey")
			Long personAttributeSetTypeKey, @WebParam(name = "personCriteria")
			PersonCriteria personCriteria) throws InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException;

	/**
	 * Retrieves a list of person person relations meeting the specified
	 * criteria
	 * 
	 * @param personRelationCriteria
	 *            criteria to be used for finding relationships
	 * @return List of Person Person Relationships with data that match the
	 *         supplied criteria.
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 */
	@WebMethod
	public List<PersonRelationDisplay> searchForPersonRelations(
			@WebParam(name = "personRelationCriteria")
			PersonRelationCriteria personRelationCriteria)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException;

	/**
	 * Retrieves a list of people with relationships meeting the specified
	 * criteria
	 * 
	 * @param personRelationCriteria
	 *            criteria to be used for finding relationships
	 * @return List of people with relationships that match the supplied
	 *         criteria.
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 */
	@WebMethod
	public List<PersonDisplay> searchForPeopleByRelation(
			@WebParam(name = "personRelationCriteria")
			PersonRelationCriteria personRelationCriteria)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException;

	/**
	 * Retrieves a list of identifiers of people with relationships meeting the
	 * specified criteria
	 * 
	 * @param personRelationCriteria
	 *            criteria to be used for finding relationships
	 * @return List of identifiers of people with relationships that match the
	 *         supplied criteria.
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 */
	@WebMethod
	public List<Long> searchForPersonIdsByRelation(
			@WebParam(name = "personRelationCriteria")
			PersonRelationCriteria personRelationCriteria)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException;

	// Maintenance /////////////////////////////////////////////////////////////
	/**
	 * Creates a person record
	 * 
	 * @param personCreateInfo
	 *            type of person being created
	 * @param personTypeKeys
	 *            information required to create a person - may consist of name,
	 *            source type, effective date, expiration date, etc.
	 * @return identifier for the newly created person
	 * @throws AlreadyExistsException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 */
	@WebMethod
	public long createPerson(@WebParam(name = "personCreateInfo")
	PersonCreateInfo personCreateInfo, @WebParam(name = "personTypeKeys")
	List<Long> personTypeKeys) throws AlreadyExistsException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException;

	/**
	 * Updates a person record
	 * 
	 * @param personUpdateInfo
	 *            information needed to update a person record
	 * @param personId
	 * @return status of the operation (success or failure)
	 * @throws DoesNotExistException
	 * @throws DisabledIdentifierException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws ReadOnlyException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 */
	@WebMethod
	public boolean updatePerson(@WebParam(name = "personId")
	Long personId, @WebParam(name = "personUpdateInfo")
	PersonUpdateInfo personUpdateInfo) throws DoesNotExistException,
			DisabledIdentifierException, InvalidParameterException,
			MissingParameterException, ReadOnlyException,
			OperationFailedException, PermissionDeniedException;

	/**
	 * Deletes a person record.
	 * 
	 * @param personId
	 *            identifier for person to be deleted
	 * @return status of the operation (success or failure)
	 * @throws DoesNotExistException
	 * @throws DisabledIdentifierException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 */
	@WebMethod
	public boolean deletePerson(@WebParam(name = "personId")
	Long personId) throws DoesNotExistException, DisabledIdentifierException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException;

	/**
	 * Assigns a personType to a person record
	 * 
	 * @param personId
	 *            identifier for person having a personType assigned
	 * @param personTypeKey
	 *            type of person being assigned
	 * @return status of the operation (success or failure) - Why have this,
	 *         shouldn't it always succeed unless an exception is thrown, or is
	 *         it needed of in-out messages?
	 * @throws AlreadyExistsException
	 * @throws DisabledIdentifierException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 */
	@WebMethod
	public boolean assignPersonType(@WebParam(name = "personId")
	Long personId, @WebParam(name = "personTypeKey")
	Long personTypeKey) throws AlreadyExistsException,
			DisabledIdentifierException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException;

	/**
	 * Disassociates a personType from a person record
	 * 
	 * @param personId
	 *            identifier for person having a personType removed
	 * @param personTypeKey
	 *            type of person being removed
	 * @return status of the operation
	 * @throws DoesNotExistException
	 * @throws DisabledIdentifierException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 */
	@WebMethod
	public boolean removePersonType(@WebParam(name = "personId")
	Long personId, @WebParam(name = "personTypeKey")
	Long personTypeKey) throws DoesNotExistException,
			DisabledIdentifierException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException;

	/**
	 * Creates a person to person relationship
	 * 
	 * @param personId
	 * @param relatedPersonId
	 * @param personRelationTypeKey
	 * @param personRelationCreateInfo
	 * @return identifier for the newly created person relationship
	 * @throws AlreadyExistsException
	 * @throws DoesNotExistException
	 * @throws CircularReferenceException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 */
	@WebMethod
	public Long createPersonRelation(@WebParam(name = "personId")
	Long personId, @WebParam(name = "relatedPersonId")
	Long relatedPersonId, @WebParam(name = "personRelationTypeKey")
	Long personRelationTypeKey, @WebParam(name = "personRelationCreateInfo")
	PersonRelationCreateInfo personRelationCreateInfo)
			throws AlreadyExistsException, DoesNotExistException,
			CircularReferenceException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException;

	/**
	 * Updates a person to person relationship
	 * 
	 * @param personRelationId
	 *            identifier for person relationship to be updated
	 * @param personRelationUpdateInfo
	 *            changed information about a person relationship
	 * @return status of the operation (success or failure)
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws ReadOnlyException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 */
	@WebMethod
	public boolean updatePersonRelation(@WebParam(name = "personRelationId")
	Long personRelationId, @WebParam(name = "personRelationUpdateInfo")
	PersonRelationUpdateInfo personRelationUpdateInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, ReadOnlyException,
			OperationFailedException, PermissionDeniedException;

	/**
	 * Deletes a person relationship.
	 * 
	 * @param personRelationId
	 *            identifier for person relationship to be deleted
	 * @return status of the operation (success or failure)
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 */
	@WebMethod
	public boolean deletePersonRelation(@WebParam(name = "personRelationId")
	Long personRelationId) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException;

	// Additional maintenance methods /////////////////////////
	@WebMethod
	public long createAttributeDefinition(
			@WebParam(name = "attributeDefinition")
			PersonAttributeTypeInfo attributeDefinition)
			throws AlreadyExistsException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException;

	@WebMethod
	public long createPersonTypeInfo(@WebParam(name = "personTypeInfo")
	PersonTypeInfo personTypeInfo) throws AlreadyExistsException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException;

	@WebMethod
	public long createPersonAttributeSetType(
			@WebParam(name = "attributeSetDTO")
			PersonAttributeSetTypeInfo attributeSetDTO)
			throws AlreadyExistsException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException;

}
