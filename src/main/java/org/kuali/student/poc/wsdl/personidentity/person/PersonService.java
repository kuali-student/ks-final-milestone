package org.kuali.student.poc.wsdl.personidentity.person;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.kuali.student.poc.wsdl.personidentity.exceptions.AlreadyExistsException;
import org.kuali.student.poc.wsdl.personidentity.exceptions.DisabledIdentifierException;
import org.kuali.student.poc.wsdl.personidentity.exceptions.DoesNotExistException;
import org.kuali.student.poc.wsdl.personidentity.exceptions.InvalidParameterException;
import org.kuali.student.poc.wsdl.personidentity.exceptions.MissingParameterException;
import org.kuali.student.poc.wsdl.personidentity.exceptions.OperationFailedException;
import org.kuali.student.poc.wsdl.personidentity.exceptions.PermissionDeniedException;
import org.kuali.student.poc.wsdl.personidentity.exceptions.ReadOnlyException;
import org.kuali.student.poc.xsd.personidentity.person.dto.AttributeDefinitionDTO;
import org.kuali.student.poc.xsd.personidentity.person.dto.AttributeSetDTO;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonDTO;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonIdDTO;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonInfoDTO;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonTypeDTO;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonTypeInfoDTO;

@WebService(name = "PersonService", targetNamespace = "http://student.kuali.org/poc/wsdl/personidentity/person")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface PersonService {
	
	 // Setup Operations
	/**
	 * Retrieves the list of person types known by this service.
	 * @return list of person types
	 * @throws OperationFailedException
	 */
	@WebMethod
	public List<PersonTypeDTO> findPersonTypes() throws OperationFailedException;

	/**
	 * Retrieves the list of person types which can be created by this service.
	 * @return  list of person types
	 * @throws OperationFailedException
	 */
	@WebMethod
	public List<PersonTypeDTO> findCreatablePersonTypes()
			throws OperationFailedException;

	/*
	 * 
	 * public List<AttributeSetDTO> findPersonAttributeSetTypes() throws
	 * OperationFailedException;
	 * 
	 * public List<AttributeSetDTO> findPersonAttributeSetTypesForPersonType(
	 * PersonTypeInfoDTO personTypeInfo) throws DoesNotExistException,
	 * InvalidParameterException, MissingParameterException,
	 * OperationFailedException;
	 */
	
	/**
	 * Retrieves all available information about a person.
	 * @param personId identifier of the person to be retrieved
	 * @return all information available through the person service
	 * @throws DoesNotExistException
	 * @throws DisabledIdentifierException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 */
	public PersonDTO fetchFullPersonInfo(@WebParam(name = "personId") long personId) throws DoesNotExistException,
			DisabledIdentifierException, InvalidParameterException, MissingParameterException,
			OperationFailedException;

	@WebMethod
	public PersonDTO fetchPersonInfoByPersonType(@WebParam(name = "personId")
	Long personId, @WebParam(name = "personType")
	PersonTypeInfoDTO personType) throws DoesNotExistException,
			DisabledIdentifierException, InvalidParameterException,
			MissingParameterException, OperationFailedException;

	/*
	 * public PersonDTO fetchPersonInfoByPersonAttributeSetTypes(PersonIdDTO
	 * personId, List<AttributeSetDTO> personAttributeSetTypeList) throws
	 * DoesNotExistException, DisabledIdentifierException,
	 * InvalidParameterException, MissingParameterException,
	 * OperationFailedException;
	 * 
	 * public List<AttributeSetDTO>
	 * findPersonAttributeSetTypesForPerson(PersonIdDTO personId) throws
	 * DoesNotExistException, DisabledIdentifierException,
	 * InvalidParameterException, MissingParameterException,
	 * OperationFailedException;
	 * 
	 * public List<PersonTypeDTO> findPersonTypesForPerson(PersonIdDTO
	 * personId) throws DoesNotExistException, DisabledIdentifierException,
	 * InvalidParameterException, MissingParameterException,
	 * OperationFailedException;
	 * 
	 * public List<Long> findPersonIdsForPersonType(PersonTypeInfoDTO
	 * personType, Criteria<PersonDTO> personFilter) throws
	 * DoesNotExistException, InvalidParameterException,
	 * MissingParameterException, OperationFailedException;
	 * 
	 * public boolean isPersonType(PersonIdDTO personId, PersonTypeInfoDTO
	 * personType) throws DoesNotExistException, DisabledIdentifierException,
	 * InvalidParameterException, MissingParameterException,
	 * OperationFailedException;
	 */

	// validatePersonInfoForPersonType
	// fetchReplacementPersonId
	//
	// searchForPersonIds
	// searchForPeople
	// searchForPeopleByPersonType
	// searchForPeopleByPersonAttributeSetType
	//
	@WebMethod
	public long createPerson(@WebParam(name = "person")
	PersonInfoDTO person, @WebParam(name = "personTypes")
	List<PersonTypeInfoDTO> personTypes) throws AlreadyExistsException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException;

	@WebMethod
	public boolean updatePerson(@WebParam(name = "person")
	PersonDTO person) throws DoesNotExistException,
			DisabledIdentifierException, InvalidParameterException,
			MissingParameterException, ReadOnlyException,
			OperationFailedException, PermissionDeniedException;

	@WebMethod
	public boolean deletePerson(@WebParam(name = "personId")
	PersonIdDTO personId) throws DoesNotExistException,
			DisabledIdentifierException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException;

	// assignPersonType
	// removePersonType

	@WebMethod
	public long createAttributeDefinition(
			@WebParam(name = "attributeDefinition")
			AttributeDefinitionDTO attributeDefinition)
			throws AlreadyExistsException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException;

	@WebMethod
	public long createPersonType(@WebParam(name = "personType")
	PersonTypeDTO personType) throws AlreadyExistsException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException;

	@WebMethod
	public long createPersonTypeInfo(@WebParam(name = "personTypeInfo")
	PersonTypeInfoDTO personTypeInfo) throws AlreadyExistsException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException;

	@WebMethod
	public long createPersonAttributeSetType(
			@WebParam(name = "attributeSetDTO")
			AttributeSetDTO attributeSetDTO) throws AlreadyExistsException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException;

}
