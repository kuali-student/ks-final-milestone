package org.kuali.student.poc.personidentity.person.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.jws.WebService;

import org.kuali.student.poc.personidentity.person.dao.Person;
import org.kuali.student.poc.personidentity.person.dao.PersonAttribute;
import org.kuali.student.poc.personidentity.person.dao.PersonAttributeSetType;
import org.kuali.student.poc.personidentity.person.dao.PersonAttributeType;
import org.kuali.student.poc.personidentity.person.dao.PersonCitizenship;
import org.kuali.student.poc.personidentity.person.dao.PersonDAO;
import org.kuali.student.poc.personidentity.person.dao.PersonName;
import org.kuali.student.poc.personidentity.person.dao.PersonReferenceId;
import org.kuali.student.poc.personidentity.person.dao.PersonType;
import org.kuali.student.poc.personidentity.person.dao.PersonalInformation;
import org.kuali.student.poc.wsdl.personidentity.exceptions.AlreadyExistsException;
import org.kuali.student.poc.wsdl.personidentity.exceptions.CircularReferenceException;
import org.kuali.student.poc.wsdl.personidentity.exceptions.DisabledIdentifierException;
import org.kuali.student.poc.wsdl.personidentity.exceptions.DoesNotExistException;
import org.kuali.student.poc.wsdl.personidentity.exceptions.InvalidParameterException;
import org.kuali.student.poc.wsdl.personidentity.exceptions.MissingParameterException;
import org.kuali.student.poc.wsdl.personidentity.exceptions.OperationFailedException;
import org.kuali.student.poc.wsdl.personidentity.exceptions.PermissionDeniedException;
import org.kuali.student.poc.wsdl.personidentity.exceptions.ReadOnlyException;
import org.kuali.student.poc.wsdl.personidentity.person.PersonService;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonAttributeSetTypeDisplay;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonAttributeSetTypeInfo;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonAttributeTypeInfo;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonCitizenshipInfo;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonCreateInfo;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonCriteria;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonDisplay;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonInfo;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonNameInfo;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonReferenceIdInfo;
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
import org.springframework.transaction.annotation.Transactional;

@WebService(endpointInterface="org.kuali.student.poc.wsdl.personidentity.person.PersonService",
		serviceName="PersonService",
		portName="PersonService",
		targetNamespace="http://student.kuali.org/poc/wsdl/personidentity/person")
@Transactional
public class PersonServiceImpl implements PersonService {

	private PersonDAO personDAO;

	/**
	 * @return the personDAO
	 */
	public PersonDAO getPersonDAO() {
		return personDAO;
	}

	/**
	 * @param personDAO
	 *            the personDAO to set
	 */
	public void setPersonDAO(PersonDAO personDAO) {
		this.personDAO = personDAO;
	}

	/*
	@Override
	public List<PersonTypeDTO> findPersonTypes()
			throws OperationFailedException {
		
		List<PersonTypeDTO> personTypeDTOList = new ArrayList<PersonTypeDTO>();
		List<PersonType> personTypeList = personDAO.findPersonTypes("%");
		for(PersonType personType : personTypeList)	{
			personTypeDTOList.add(toPersonTypeDTO(personType));
		}
		
		return personTypeDTOList;
	}
	
	@Override
	public List<PersonTypeDTO> findCreatablePersonTypes()
			throws OperationFailedException {
		
		return findPersonTypes();
	}
	
	@Override
	public PersonDTO fetchFullPersonInfo(long personId)
			throws DoesNotExistException, DisabledIdentifierException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {

		Person person = personDAO.lookupPerson(personId);
		
		if(person == null) {
			throw new DoesNotExistException();
		}
		
		return toPersonDTO(person);
	}
	

	@Override
	public long createPerson(PersonInfoDTO person, List<PersonTypeInfoDTO> personTypes)
			throws AlreadyExistsException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		
		//create a JPA entity
		Person personJPA = new Person();
		
		// First look up the personTypes
		Set<PersonType> personTypeSetJPA = new HashSet<PersonType>();
		Set<PersonAttributeType> attributeTypeSetJPA = new HashSet<PersonAttributeType>();

		//Find all the types we are adding to the person to be created
		for(PersonTypeInfoDTO personTypeInfo:personTypes){	
		
			//Look up the person types
			PersonType personTypeJPA =  personDAO.fetchPersonType(personTypeInfo.getId());
			
			//Add them to the list of person types
			personTypeSetJPA.add(personTypeJPA);
			
			//find all the attributes in the person type and add them to our set
			for(PersonAttributeSetType personAttributeSetType:personTypeJPA.getPersonAttributeSetTypes()){
				for(PersonAttributeType personAttributeType:personAttributeSetType.getPersonAttributeTypes()){
					attributeTypeSetJPA.add(personAttributeType);
				}
			}
		}
		
		//Add all the attribute types
		personJPA.getPersonTypes().addAll(personTypeSetJPA);
		
		//Add all of the attributes that were in the personTypes and exist on the person
		for(PersonAttributeType personAttributeType:attributeTypeSetJPA){
		
			//Check that the person being passed in actually has this attribute
			if(person.getAttribute(personAttributeType.getName())!=null){
			
				//Create a new attribute
				PersonAttribute personAttribute = new PersonAttribute();
				personAttribute.setPerson(personJPA);
				personAttribute.setPersonAttributeType(personAttributeType);
				personAttribute.setValue(person.getAttribute(personAttributeType.getName()));
				
				//Add the attribute to the person
				personJPA.getAttributes().add(personAttribute);
			}
			
		}

		//Copy the standard person fields
		personJPA.setDateOfBirth(person.getDob());
		personJPA.setConfidential(person.isConfidential());
		personJPA.setFirstName(person.getFirstName());
		personJPA.setLastName(person.getLastName());
		personJPA.setGender(person.getGender());
		
		//Set the DB specific fields
		personJPA.setUpdateDate(new Date());
		personJPA.setUpdatePerson(null);
		
		//Create the person
		personDAO.createPerson(personJPA);
		return personJPA.getId();
	}

	@Override
	public boolean deletePerson(PersonIdDTO personId) throws DoesNotExistException,
			DisabledIdentifierException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
	    Person person = personDAO.lookupPerson(personId.getId());
	    if(person != null) {
	        personDAO.deletePerson(person);
	        return true;
	    }
		return false;
	}

	@Override
	public boolean updatePerson(PersonDTO person) throws DoesNotExistException,
			DisabledIdentifierException, InvalidParameterException,
			MissingParameterException, ReadOnlyException,
			OperationFailedException, PermissionDeniedException {
        // Find the JPA entity
        Person personJPA = personDAO.lookupPerson(person.getId());
        
        // First look up the personTypes
        Set<PersonType> personTypeSetJPA = personJPA.getPersonTypes();
        Set<PersonAttributeType> attributeTypeSetJPA = new HashSet<PersonAttributeType>();

        //Find all the types we are adding to the person to be created
        for(PersonType personTypeJPA: personTypeSetJPA) {
            for(PersonAttributeSetType personAttributeSetType:personTypeJPA.getPersonAttributeSetTypes()){
                for(PersonAttributeType personAttributeType:personAttributeSetType.getPersonAttributeTypes()){
                    attributeTypeSetJPA.add(personAttributeType);
                }
            }
        }
        
        //Add all of the attributes that were in the personTypes and exist on the person
        for(PersonAttributeType personAttributeType:attributeTypeSetJPA){
        
            //Check that the person being passed in actually has this attribute
            if(person.getAttributes().containsKey(personAttributeType.getName())){
                String attributeValue = person.getAttribute(personAttributeType.getName());
                
                //I hate writing lookups
                //Find the actual entry in the attribute set
                PersonAttribute attribute = null;
                Set<PersonAttribute> attributes = personJPA.getAttributes();
                for (PersonAttribute personAttribute : attributes) {
                    if(personAttribute.getPersonAttributeType().getId().equals(personAttributeType.getId())) {
                        attribute = personAttribute;
                        break;
                    }
                }
                // if value is null, that means we're removing it. that's why we did containsKey up there instead of getAttribute()
                if(attributeValue == null) {
                    attributes.remove(attribute);
                    personDAO.deletePersonAttribute(attribute);
                } else {
                    //The problem here is that jpa allows for multi-valued attrs, whereas the dto has a map with 1:1
                    attribute.setValue(attributeValue);
                }
            }
            
        }

        //Copy the standard person fields
        personJPA.setDateOfBirth(person.getDob());
        personJPA.setConfidential(person.isConfidential());
        personJPA.setFirstName(person.getFirstName());
        personJPA.setLastName(person.getLastName());
        personJPA.setGender(person.getGender());
        
        //Set the DB specific fields
        personJPA.setUpdateDate(new Date());
        personJPA.setUpdatePerson(null);
        
        //Create the person
        personDAO.updatePerson(personJPA);
		return true;
	}

	@Override
	public long createAttributeDefinition(
			AttributeDefinitionDTO attributeDefinition)
			throws AlreadyExistsException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		//TODO exceptions
		PersonAttributeType personAttributeType = toPersonAttributeType(attributeDefinition,false);
		PersonAttributeType created = personDAO.createPersonAttributeType(personAttributeType);
		return created.getId();
	}



	@Override
	public long createPersonTypeInfo(PersonTypeInfoDTO personType)
			throws AlreadyExistsException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {

		PersonType personTypeJPA = new PersonType();
		personTypeJPA.setName(personType.getName());

		//FIXME Exceptions are not working because Transactions cause commits to occur outside of the dao
		//and jpa exceptions will not be thrown until they are commited.
		
		try {
			personTypeJPA = personDAO.createPersonType(personTypeJPA);
		}
		catch(javax.persistence.EntityExistsException ex) {
			throw new AlreadyExistsException();
		}
		
		return personTypeJPA.getId();
	}
	
	@Override
	public long createPersonAttributeSetType(AttributeSetDTO attributeSetDTO)
			throws AlreadyExistsException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		//TODO exceptions
		PersonAttributeSetType personAttributeSetType = toPersonAttributeSetType(attributeSetDTO,false);
		PersonAttributeSetType created = personDAO.createPersonAttributeSetType(personAttributeSetType);
		return created.getId();
	}
	
	@Override
	public long createPersonType(PersonTypeDTO personTypeDTO)
			throws AlreadyExistsException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		//TODO Exceptions
		PersonType personType = toPersonType(personTypeDTO,false);
		PersonType created = personDAO.createPersonType(personType);
		return created.getId();
	}
	@Override
	public PersonDTO fetchPersonInfoByPersonType(Long personId,
			PersonTypeInfoDTO personType) throws DoesNotExistException,
			DisabledIdentifierException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		//lookup the full person
		Person person = personDAO.lookupPerson(personId);
		
		//Filter out the unneeded person types
		for(Iterator<PersonType> i = person.getPersonTypes().iterator();i.hasNext();){
			PersonType currentPersonType = i.next();
			if(!currentPersonType.getId().equals(personType.getId())){
				i.remove();
			}
		}
		
		for(Iterator<PersonAttribute> i = person.getAttributes().iterator();i.hasNext();){
			PersonAttribute personAttribute = i.next();
			if(!personAttribute.getPersonAttributeType().getId().equals(personType.getId())){
				i.remove();
			}
		}
		
		return toPersonDTO(person);
	}
*/
	private PersonTypeInfo toPersonTypeInfo(PersonType personType){
		PersonTypeInfo personTypeInfo = new PersonTypeInfo();
		personTypeInfo.setId(personType.getId());
		personTypeInfo.setName(personType.getName());
		personTypeInfo.getAttributeSets().addAll(PersonAttributeSetTypeInfos(personType.getPersonAttributeSetTypes()));
		return personTypeInfo;
	}

	private Set<PersonAttributeSetTypeInfo> PersonAttributeSetTypeInfos(
			Set<PersonAttributeSetType> personAttributeSetTypes) {
		Set<PersonAttributeSetTypeInfo> personAttributeSetTypeInfos = new HashSet<PersonAttributeSetTypeInfo>(); 
		for(PersonAttributeSetType personAttributeSetType:personAttributeSetTypes){
			personAttributeSetTypeInfos.add(toPersonAttributeSetTypeInfo(personAttributeSetType));
		}
		return personAttributeSetTypeInfos;
	}

	private PersonAttributeSetTypeInfo toPersonAttributeSetTypeInfo(
			PersonAttributeSetType personAttributeSetType) {
		PersonAttributeSetTypeInfo personAttributeSetTypeInfo = new PersonAttributeSetTypeInfo();
		personAttributeSetTypeInfo.setId(personAttributeSetType.getId());
		personAttributeSetTypeInfo.setName(personAttributeSetType.getName());
		personAttributeSetTypeInfo.getAttributeTypes().addAll(toPersonAttributeTypeInfos(personAttributeSetType.getPersonAttributeTypes()));
		return personAttributeSetTypeInfo;
	}

	private Set<PersonAttributeTypeInfo> toPersonAttributeTypeInfos(
			Set<PersonAttributeType> personAttributeTypes) {
		Set<PersonAttributeTypeInfo> personAttributeTypeInfos = new HashSet<PersonAttributeTypeInfo>();
		for(PersonAttributeType personAttributeType:personAttributeTypes){
			personAttributeTypeInfos.add(toPersonAttributeTypeInfo(personAttributeType));
		}
		return personAttributeTypeInfos;
	}
	
	private PersonAttributeTypeInfo toPersonAttributeTypeInfo(
			PersonAttributeType personAttributeType) {
		PersonAttributeTypeInfo personAttributeTypeInfo = new PersonAttributeTypeInfo();
		personAttributeTypeInfo.setId(personAttributeType.getId());
		personAttributeTypeInfo.setLabel(personAttributeType.getDisplayLabel());
		personAttributeTypeInfo.setName(personAttributeType.getName());
		personAttributeTypeInfo.setType(personAttributeType.getType());
		return personAttributeTypeInfo;
	}
	
	private PersonAttributeType toPersonAttributeType(
			PersonAttributeTypeInfo attributeDefinition, boolean update) {
		PersonAttributeType personAttributeType = new PersonAttributeType();
		if(update||isNullOrZero(attributeDefinition.getId())){
			if(isNullOrZero(attributeDefinition.getId())){
				personAttributeType.setId(attributeDefinition.getId());
			}
			personAttributeType.setDisplayLabel(attributeDefinition.getLabel());
			personAttributeType.setName(attributeDefinition.getName());
			personAttributeType.setType(attributeDefinition.getType().toString());
		}else{
			return personDAO.fetchPersonAttributeType(attributeDefinition.getId());
		}
		return personAttributeType;
	}

	private PersonAttributeSetType toPersonAttributeSetType(
			PersonAttributeSetTypeInfo attributeSetType, boolean update) {
		PersonAttributeSetType personAttributeSetType = new PersonAttributeSetType();
		if(update||isNullOrZero(attributeSetType.getId())){
			if(!isNullOrZero(attributeSetType.getId())){
				personAttributeSetType.setId(attributeSetType.getId());
			}
			personAttributeSetType.setName(attributeSetType.getName());
			personAttributeSetType.setPersonAttributeTypes(toPersonAttributeTypes(attributeSetType.getAttributeTypes(), false));
		}else{
			return personDAO.fetchPersonAttributeSetType(attributeSetType.getId());
		}
		return personAttributeSetType;
	}

	private Set<PersonAttributeType> toPersonAttributeTypes(
			List<PersonAttributeTypeInfo> attributeTypes, boolean update) {
		Set<PersonAttributeType> personAttributeTypes = new HashSet<PersonAttributeType>();
		for(PersonAttributeTypeInfo attributeDefinition:attributeTypes){
			personAttributeTypes.add(toPersonAttributeType(attributeDefinition, update));
		}
		return personAttributeTypes;
	}
	
	private Set<PersonAttributeSetType> toPersonAttributeSetTypes(
			List<PersonAttributeSetTypeInfo> attributeSets, boolean update) {
		Set<PersonAttributeSetType> personAttributeSetTypes = new HashSet<PersonAttributeSetType>();
		for(PersonAttributeSetTypeInfo attributeSetType:attributeSets){
			personAttributeSetTypes.add(toPersonAttributeSetType(attributeSetType, update));
		}
		return personAttributeSetTypes;
	}

	private PersonType toPersonType(PersonTypeInfo personTypeInfo, boolean update) {
		PersonType personType = new PersonType();
		if(update||isNullOrZero(personTypeInfo.getId())){
			if(!isNullOrZero(personTypeInfo.getId())){
				personType.setId(personTypeInfo.getId());
			}
			personType.setName(personTypeInfo.getName());
			personType.setPersonAttributeSetTypes(toPersonAttributeSetTypes(personTypeInfo.getAttributeSets(), false));
		}else{
			return this.personDAO.fetchPersonType(personTypeInfo.getId());
		}
		return personType;
	}

	private PersonInfo toPersonInfo(Person person) {
		PersonInfo personInfo = new PersonInfo();
		
		//Copy fields
		personInfo.setPersonId(person.getId());
		if(person.getPersonalInformation() != null){
			personInfo.setBirthDate(person.getPersonalInformation().getDateOfBirth());
			personInfo.setGender(person.getPersonalInformation().getGender());
		}

		personInfo.getName();
		for(PersonName personName : person.getPersonNames()){
			PersonNameInfo personNameInfo = new PersonNameInfo();
			personNameInfo.setEffectiveEndDate(personName.getEffectiveEndDate());
			personNameInfo.setEffectiveStartDate(personName.getEffectiveStartDate());
			personNameInfo.setGivenName(personName.getGivenName());
			personNameInfo.setMiddleName(personName.getMiddleNames());
			personNameInfo.setNameType(personName.getNameType());
			personNameInfo.setSuffix(personName.getSuffix());
			personNameInfo.setSurname(personName.getSurname());
			personNameInfo.setPersonTitle(personName.getTitle());
			personInfo.getName().add(personNameInfo);
		}
		
		for(PersonCitizenship personCitizenship:person.getPersonCitizenships()){
			PersonCitizenshipInfo personCitizenshipInfo = new PersonCitizenshipInfo();
			personCitizenshipInfo.setCountryOfCitizenshipCode(personCitizenship.getCountryOfCitizenship());
			personCitizenshipInfo.setEffectiveEndDate(personCitizenship.getEffectiveEndDate());
			personCitizenshipInfo.setEffectiveStartDate(personCitizenship.getEffectiveStartDate());
			//personCitizenshipInfo.setPersonId(personCitizenship.getPerson().getId());
			personCitizenshipInfo.setUpdateTime(personCitizenshipInfo.getUpdateTime());
			personCitizenshipInfo.setUpdateUserComment(personCitizenshipInfo.getUpdateUserComment());
			personCitizenshipInfo.setUpdateUserId(personCitizenshipInfo.getUpdateUserId());
			//TODO - personInfo has only one citizenship while person (DAO) has a list
			personInfo.setCitizenship(personCitizenshipInfo);
		}

		personInfo.getReferenceId();
		for(PersonReferenceId personReferenceId:person.getPersonReferenceIds()){
			PersonReferenceIdInfo personReferenceIdInfo = new PersonReferenceIdInfo();
			personReferenceIdInfo.setOrganizationReferenceId(personReferenceIdInfo.getOrganizationReferenceId());
			personReferenceIdInfo.setReferenceId(personReferenceIdInfo.getReferenceId());
			personReferenceIdInfo.setRestrictedAccess(personReferenceId.isRestrictedAccess());
			personReferenceIdInfo.setUpdateTime(personReferenceId.getUpdateTime());
			personReferenceIdInfo.setUpdateUserComment(personReferenceId.getUpdateUserComment());
			personReferenceIdInfo.setUpdateUserId(personReferenceId.getUpdateUserId());
			personInfo.getReferenceId().add(personReferenceIdInfo);
		}
		
		//Copy/convert attributes to map
		personInfo.getAttributes();
		for(PersonAttribute attribute : person.getAttributes()) {
			personInfo.setAttribute(attribute.getPersonAttributeType().getName(), attribute.getValue());
		}

		return personInfo;
	}
	/*
	private PersonTypeInfoDTO toPersonTypeInfoDTO(PersonType personType) {
		PersonTypeInfoDTO personTypeInfoDTO = new PersonTypeInfoDTO();
		personTypeInfoDTO.setId(personType.getId());
		personTypeInfoDTO.setName(personType.getName());
		
		return personTypeInfoDTO;
	}*/
	
	private boolean isNullOrZero(Long id) {
		return id==null||Long.valueOf(0).equals(id);
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.poc.wsdl.personidentity.person.PersonService#assignPersonType(java.lang.Long, java.lang.Long)
	 */
	@Override
	public boolean assignPersonType(Long personId, Long personTypeKey)
			throws AlreadyExistsException, DisabledIdentifierException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		
		
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.poc.wsdl.personidentity.person.PersonService#createAttributeDefinition(org.kuali.student.poc.xsd.personidentity.person.dto.PersonAttributeTypeInfo)
	 */
	@Override
	public long createAttributeDefinition(
			PersonAttributeTypeInfo attributeDefinition)
			throws AlreadyExistsException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();

	}

	/* (non-Javadoc)
	 * @see org.kuali.student.poc.wsdl.personidentity.person.PersonService#createPerson(org.kuali.student.poc.xsd.personidentity.person.dto.PersonCreateInfo, java.util.List)
	 */
	@Override
	public long createPerson(PersonCreateInfo personCreateInfo,
			List<Long> personTypeKeys) throws AlreadyExistsException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		//create a JPA entity
		Person person = new Person();
		
		// First look up the personTypes
		Set<PersonType> personTypeSet = new HashSet<PersonType>();
		Set<PersonAttributeType> attributeTypeSet = new HashSet<PersonAttributeType>();

		//Find all the types we are adding to the person to be created
		for(Long personTypeKey:personTypeKeys){	
		
			//Look up the person types
			PersonType personType =  personDAO.fetchPersonType(personTypeKey);
			
			//Add them to the list of person types
			personTypeSet.add(personType);
			
			//find all the attributes in the person type and add them to our set
			for(PersonAttributeSetType personAttributeSetType:personType.getPersonAttributeSetTypes()){
				for(PersonAttributeType personAttributeType:personAttributeSetType.getPersonAttributeTypes()){
					attributeTypeSet.add(personAttributeType);
				}
			}
		}
		
		//Add all the attribute types
		person.getPersonTypes().addAll(personTypeSet);
		
		//Add all of the attributes that were in the personTypes and exist on the person
		for(PersonAttributeType personAttributeType:attributeTypeSet){
		
			//Check that the person being passed in actually has this attribute
			if(personCreateInfo.getAttribute(personAttributeType.getName())!=null){
			
				//Create a new attribute
				PersonAttribute personAttribute = new PersonAttribute();
				personAttribute.setPerson(person);
				personAttribute.setPersonAttributeType(personAttributeType);
				personAttribute.setValue(personCreateInfo.getAttribute(personAttributeType.getName()));
				
				//Add the attribute to the person
				person.getAttributes().add(personAttribute);
			}
			
		}

		//Copy the standard person fields
		PersonalInformation personalInformation = new PersonalInformation();
		personalInformation.setGender(personCreateInfo.getGender());
		personalInformation.setDateOfBirth(personCreateInfo.getBirthDate());
		personalInformation.setPerson(person);
		person.setPersonalInformation(personalInformation);
		
		PersonCitizenshipInfo citizenshipInfo = personCreateInfo.getCitizenship();
		if(citizenshipInfo!=null){
			PersonCitizenship citizenship = new PersonCitizenship();
			citizenship.setCountryOfCitizenship(citizenshipInfo.getCountryOfCitizenshipCode());
			citizenship.setEffectiveStartDate(citizenshipInfo.getEffectiveStartDate());
			citizenship.setEffectiveEndDate(citizenshipInfo.getEffectiveEndDate());
			citizenship.setPerson(person);
			Set<PersonCitizenship> personCitizenship = new HashSet<PersonCitizenship>();
			personCitizenship.add(citizenship);
			person.setPersonCitizenships(personCitizenship);
		}	
		
		for(PersonNameInfo nameInfo : personCreateInfo.getName()){
			PersonName personName = new PersonName();
			personName.setEffectiveEndDate(nameInfo.getEffectiveEndDate());
			personName.setEffectiveStartDate(nameInfo.getEffectiveStartDate());
			personName.setGivenName(nameInfo.getGivenName());
			personName.setMiddleNames(nameInfo.getMiddleName());
			personName.setNameType(nameInfo.getNameType());
			personName.setSuffix(nameInfo.getSuffix());
			personName.setSurname(nameInfo.getSurname());
			personName.setTitle(nameInfo.getPersonTitle());
			personName.setPerson(person);
			person.getPersonNames().add(personName);
		}
		
		for(PersonReferenceIdInfo personReferenceIdInfo:personCreateInfo.getReferenceId()){
			PersonReferenceId personReferenceId = new PersonReferenceId();
			personReferenceId.setOrganizationReferenceId(personReferenceIdInfo.getOrganizationReferenceId());
			personReferenceId.setReferenceId(personReferenceIdInfo.getReferenceId());
			personReferenceId.setRestrictedAccess(personReferenceIdInfo.isRestrictedAccess());
			personReferenceId.setUpdateTime(personReferenceIdInfo.getUpdateTime());
			personReferenceId.setUpdateUserComment(personReferenceIdInfo.getUpdateUserComment());
			personReferenceId.setUpdateUserId(personReferenceIdInfo.getUpdateUserId());
			personReferenceId.setPerson(person);
			person.getPersonReferenceIds().add(personReferenceId);
		}
		
		//Create the person
		Person created = personDAO.createPerson(person);
		return created.getId();
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.poc.wsdl.personidentity.person.PersonService#createPersonAttributeSetType(org.kuali.student.poc.xsd.personidentity.person.dto.PersonAttributeSetTypeInfo)
	 */
	@Override
	public long createPersonAttributeSetType(
			PersonAttributeSetTypeInfo attributeSetDTO)
			throws AlreadyExistsException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();

	}

	/* (non-Javadoc)
	 * @see org.kuali.student.poc.wsdl.personidentity.person.PersonService#createPersonRelation(java.lang.Long, java.lang.Long, java.lang.Long, org.kuali.student.poc.xsd.personidentity.person.dto.PersonRelationCreateInfo)
	 */
	@Override
	public Long createPersonRelation(Long personId, Long relatedPersonId,
			Long personRelationTypeKey,
			PersonRelationCreateInfo personRelationCreateInfo)
			throws AlreadyExistsException, DoesNotExistException,
			CircularReferenceException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();

	}

	/* (non-Javadoc)
	 * @see org.kuali.student.poc.wsdl.personidentity.person.PersonService#createPersonTypeInfo(org.kuali.student.poc.xsd.personidentity.person.dto.PersonTypeInfo)
	 */
	@Override
	public long createPersonTypeInfo(PersonTypeInfo personTypeInfo)
			throws AlreadyExistsException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		PersonType personType = toPersonType(personTypeInfo,false);
		PersonType created = personDAO.createPersonType(personType);
		return created.getId();

	}

	/* (non-Javadoc)
	 * @see org.kuali.student.poc.wsdl.personidentity.person.PersonService#deletePerson(java.lang.Long)
	 */
	@Override
	public boolean deletePerson(Long personId) throws DoesNotExistException,
			DisabledIdentifierException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();

	}

	/* (non-Javadoc)
	 * @see org.kuali.student.poc.wsdl.personidentity.person.PersonService#deletePersonRelation(java.lang.Long)
	 */
	@Override
	public boolean deletePersonRelation(Long personRelationId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();

	}

	/* (non-Javadoc)
	 * @see org.kuali.student.poc.wsdl.personidentity.person.PersonService#fetchFullPersonInfo(java.lang.Long)
	 */
	@Override
	public PersonInfo fetchFullPersonInfo(Long personId)
			throws DoesNotExistException, DisabledIdentifierException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		Person person = personDAO.lookupPerson(personId);
		return toPersonInfo(person);
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.poc.wsdl.personidentity.person.PersonService#fetchPersonAttributeSetType(java.lang.Long)
	 */
	@Override
	public PersonAttributeSetTypeInfo fetchPersonAttributeSetType(
			Long personAttributeSetTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();

	}

	/* (non-Javadoc)
	 * @see org.kuali.student.poc.wsdl.personidentity.person.PersonService#fetchPersonInfoByPersonAttributeSetTypes(java.lang.Long, java.util.List)
	 */
	@Override
	public PersonInfo fetchPersonInfoByPersonAttributeSetTypes(Long personId,
			List<Long> personAttributeSetTypeKeyList)
			throws DoesNotExistException, DisabledIdentifierException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();

	}

	/* (non-Javadoc)
	 * @see org.kuali.student.poc.wsdl.personidentity.person.PersonService#fetchPersonInfoByPersonType(java.lang.Long, java.lang.Long)
	 */
	@Override
	public PersonInfo fetchPersonInfoByPersonType(Long personId,
			Long personTypeKey) throws DoesNotExistException,
			DisabledIdentifierException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();

	}

	/* (non-Javadoc)
	 * @see org.kuali.student.poc.wsdl.personidentity.person.PersonService#fetchPersonRelation(java.lang.Long)
	 */
	@Override
	public PersonRelationInfo fetchPersonRelation(Long personRelationId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();

	}

	/* (non-Javadoc)
	 * @see org.kuali.student.poc.wsdl.personidentity.person.PersonService#fetchPersonRelationTypes()
	 */
	@Override
	public List<PersonRelationTypeDisplay> fetchPersonRelationTypes()
			throws OperationFailedException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();

	}

	/* (non-Javadoc)
	 * @see org.kuali.student.poc.wsdl.personidentity.person.PersonService#fetchPersonType(java.lang.Long)
	 */
	@Override
	public PersonTypeInfo fetchPersonType(Long personTypeKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		return this.toPersonTypeInfo(personDAO.fetchPersonType(personTypeKey));
	}



	/* (non-Javadoc)
	 * @see org.kuali.student.poc.wsdl.personidentity.person.PersonService#fetchReplacementPersonId(java.lang.Long)
	 */
	@Override
	public Long fetchReplacementPersonId(Long personId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();

	}

	/* (non-Javadoc)
	 * @see org.kuali.student.poc.wsdl.personidentity.person.PersonService#findCreatablePersonTypes()
	 */
	@Override
	public List<PersonTypeDisplay> findCreatablePersonTypes()
			throws OperationFailedException {
		return findPersonTypes();

	}

	/* (non-Javadoc)
	 * @see org.kuali.student.poc.wsdl.personidentity.person.PersonService#findPersonAttributeSetTypes()
	 */
	@Override
	public List<PersonAttributeSetTypeDisplay> findPersonAttributeSetTypes()
			throws OperationFailedException {

        List<PersonAttributeSetTypeDisplay> personAttrSetTypeDispList = 
               new ArrayList<PersonAttributeSetTypeDisplay>();
        List<PersonAttributeSetType> personAttrSetTypeList = personDAO.findPersonAttributeSetTypes("%");
        for(PersonAttributeSetType personAttrSetType : personAttrSetTypeList) {
            personAttrSetTypeDispList.add(toPersonAttributeSetTypeDisplay(personAttrSetType));
        }
        
        return personAttrSetTypeDispList;
	}
	
    private PersonAttributeSetTypeDisplay toPersonAttributeSetTypeDisplay(PersonAttributeSetType personAttrSetType) {
        PersonAttributeSetTypeDisplay personAttrSetTypeDisp = new PersonAttributeSetTypeDisplay();
        personAttrSetTypeDisp.setId(personAttrSetType.getId());
        personAttrSetTypeDisp.setName(personAttrSetType.getName());
        return personAttrSetTypeDisp;
    }
	

	/* (non-Javadoc)
	 * @see org.kuali.student.poc.wsdl.personidentity.person.PersonService#findPersonAttributeSetTypesForPerson(java.lang.Long)
	 */
	@Override
	public List<Long> findPersonAttributeSetTypesForPerson(Long personId)
			throws DoesNotExistException, DisabledIdentifierException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {

	    Set<Long> personAttrSetTypeIds = new HashSet<Long>();
	    
        Person person = personDAO.lookupPerson(personId);
        
        Set<PersonType> personTypes = person.getPersonTypes();
        for (PersonType personType : personTypes){
            Set<PersonAttributeSetType>  personAttrSetTypes = personType.getPersonAttributeSetTypes();
            for(PersonAttributeSetType personAttrSetType : personAttrSetTypes) {
                personAttrSetTypeIds.add(personAttrSetType.getId());
            }
        }

        return (new ArrayList<Long>(personAttrSetTypeIds));
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.poc.wsdl.personidentity.person.PersonService#findPersonAttributeSetTypesForPersonType(java.lang.Long)
	 */
	@Override
	public List<PersonAttributeSetTypeDisplay> findPersonAttributeSetTypesForPersonType(
			Long personTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {

	    PersonType personType = personDAO.fetchPersonType(personTypeKey);
	    Set<PersonAttributeSetType> personAttrSetTypeSet = personType.getPersonAttributeSetTypes();
	    
	    List<PersonAttributeSetTypeDisplay> personAttrSetTypeDispList = 
	        new ArrayList<PersonAttributeSetTypeDisplay>();
	    for (PersonAttributeSetType personAttrSetType : personAttrSetTypeSet){
	        personAttrSetTypeDispList.add(toPersonAttributeSetTypeDisplay(personAttrSetType)); 
	    }
	    
	    return personAttrSetTypeDispList;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.poc.wsdl.personidentity.person.PersonService#findPersonIdsForPersonType(java.lang.Long, org.kuali.student.poc.xsd.personidentity.person.dto.PersonCriteria)
	 */
	@Override
	public List<Long> findPersonIdsForPersonType(Long personTypeKey,
			PersonCriteria personFilter) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();

	}

	/* (non-Javadoc)
	 * @see org.kuali.student.poc.wsdl.personidentity.person.PersonService#findPersonPersonRelationIds(java.lang.Long, java.lang.Long)
	 */
	@Override
	public List<Long> findPersonPersonRelationIds(Long personId,
			Long personRelationTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();

	}

	/* (non-Javadoc)
	 * @see org.kuali.student.poc.wsdl.personidentity.person.PersonService#findPersonRelations(java.lang.Long, java.lang.Long)
	 */
	@Override
	public List<PersonRelationDisplay> findPersonRelations(Long personId,
			Long personRelationTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();

	}

	/* (non-Javadoc)
	 * @see org.kuali.student.poc.wsdl.personidentity.person.PersonService#findPersonTypes()
	 */
	@Override
	public List<PersonTypeDisplay> findPersonTypes()
			throws OperationFailedException {
		List<PersonTypeDisplay> personTypeInfoList = new ArrayList<PersonTypeDisplay>();
		List<PersonType> personTypeList = personDAO.findPersonTypes("%");
		for(PersonType personType : personTypeList)	{
			personTypeInfoList.add(toPersonTypeDisplay(personType));
		}
		
		return personTypeInfoList;
	}

	private PersonTypeDisplay toPersonTypeDisplay(PersonType personType) {
		PersonTypeDisplay personTypeDisplay = new PersonTypeDisplay();
		personTypeDisplay.setId(personType.getId());
		personTypeDisplay.setName(personType.getName());
		return personTypeDisplay;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.poc.wsdl.personidentity.person.PersonService#findPersonTypesForPerson(java.lang.Long)
	 */
	@Override
	public List<Long> findPersonTypesForPerson(Long personId)
			throws DoesNotExistException, DisabledIdentifierException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {

	    Person person = personDAO.lookupPerson(personId);
	    Set<PersonType> personTypeSet = person.getPersonTypes();
	    List<Long> personTypeIdList = new ArrayList<Long>();
	    for (PersonType personType:personTypeSet){
	        personTypeIdList.add(personType.getId());
	    }
	        
	    return personTypeIdList;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.poc.wsdl.personidentity.person.PersonService#isPersonType(java.lang.Long, java.lang.Long)
	 */
	@Override
	public boolean isPersonType(Long personId, Long personTypeKey)
			throws DoesNotExistException, DisabledIdentifierException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {

	    Person person = personDAO.lookupPerson(personId);

	    return person.getPersonTypes().contains(personTypeKey);
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.poc.wsdl.personidentity.person.PersonService#removePersonType(java.lang.Long, java.lang.Long)
	 */
	@Override
	public boolean removePersonType(Long personId, Long personTypeKey)
			throws DoesNotExistException, DisabledIdentifierException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();

	}

	/* (non-Javadoc)
	 * @see org.kuali.student.poc.wsdl.personidentity.person.PersonService#searchForPeople(org.kuali.student.poc.xsd.personidentity.person.dto.PersonCriteria)
	 */
	@Override
	public List<PersonInfo> searchForPeople(PersonCriteria personCriteria)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();

	}

	/* (non-Javadoc)
	 * @see org.kuali.student.poc.wsdl.personidentity.person.PersonService#searchForPeopleByPersonAttributeSetType(java.lang.Long, org.kuali.student.poc.xsd.personidentity.person.dto.PersonCriteria)
	 */
	@Override
	public List<PersonInfo> searchForPeopleByPersonAttributeSetType(
			Long personAttributeSetTypeKey, PersonCriteria personCriteria)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();

	}

	/* (non-Javadoc)
	 * @see org.kuali.student.poc.wsdl.personidentity.person.PersonService#searchForPeopleByPersonType(java.lang.Long, org.kuali.student.poc.xsd.personidentity.person.dto.PersonCriteria)
	 */
	@Override
	public List<PersonInfo> searchForPeopleByPersonType(Long personTypeKey,
			PersonCriteria personCriteria) throws InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();

	}

	/* (non-Javadoc)
	 * @see org.kuali.student.poc.wsdl.personidentity.person.PersonService#searchForPeopleByRelation(org.kuali.student.poc.xsd.personidentity.person.dto.PersonRelationCriteria)
	 */
	@Override
	public List<PersonDisplay> searchForPeopleByRelation(
			PersonRelationCriteria personRelationCriteria)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();

	}

	/* (non-Javadoc)
	 * @see org.kuali.student.poc.wsdl.personidentity.person.PersonService#searchForPersonIds(org.kuali.student.poc.xsd.personidentity.person.dto.PersonCriteria)
	 */
	@Override
	public List<Long> searchForPersonIds(PersonCriteria personCriteria)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();

	}

	/* (non-Javadoc)
	 * @see org.kuali.student.poc.wsdl.personidentity.person.PersonService#searchForPersonIdsByRelation(org.kuali.student.poc.xsd.personidentity.person.dto.PersonRelationCriteria)
	 */
	@Override
	public List<Long> searchForPersonIdsByRelation(
			PersonRelationCriteria personRelationCriteria)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();

	}

	/* (non-Javadoc)
	 * @see org.kuali.student.poc.wsdl.personidentity.person.PersonService#searchForPersonRelations(org.kuali.student.poc.xsd.personidentity.person.dto.PersonRelationCriteria)
	 */
	@Override
	public List<PersonRelationDisplay> searchForPersonRelations(
			PersonRelationCriteria personRelationCriteria)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();

	}

	/* (non-Javadoc)
	 * @see org.kuali.student.poc.wsdl.personidentity.person.PersonService#updatePerson(java.lang.Long, org.kuali.student.poc.xsd.personidentity.person.dto.PersonUpdateInfo)
	 */
	@Override
	public boolean updatePerson(Long personId, PersonUpdateInfo personUpdateInfo)
			throws DoesNotExistException, DisabledIdentifierException,
			InvalidParameterException, MissingParameterException,
			ReadOnlyException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();

	}

	/* (non-Javadoc)
	 * @see org.kuali.student.poc.wsdl.personidentity.person.PersonService#updatePersonRelation(java.lang.Long, org.kuali.student.poc.xsd.personidentity.person.dto.PersonRelationUpdateInfo)
	 */
	@Override
	public boolean updatePersonRelation(Long personRelationId,
			PersonRelationUpdateInfo personRelationUpdateInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, ReadOnlyException,
			OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();

	}

	/* (non-Javadoc)
	 * @see org.kuali.student.poc.wsdl.personidentity.person.PersonService#validatePersonInfoForPersonType(java.lang.Long, java.lang.Long)
	 */
	@Override
	public ValidationError validatePersonInfoForPersonType(Long personId,
			Long personTypeKey) throws DoesNotExistException,
			DisabledIdentifierException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();

	}




}
