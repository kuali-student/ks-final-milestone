package org.kuali.student.poc.personidentity.person.service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.jws.WebService;

import org.kuali.student.poc.common.util.EhCacheHelper;
import org.kuali.student.poc.common.ws.exceptions.AlreadyExistsException;
import org.kuali.student.poc.common.ws.exceptions.CircularReferenceException;
import org.kuali.student.poc.common.ws.exceptions.DisabledIdentifierException;
import org.kuali.student.poc.common.ws.exceptions.DoesNotExistException;
import org.kuali.student.poc.common.ws.exceptions.InvalidParameterException;
import org.kuali.student.poc.common.ws.exceptions.MissingParameterException;
import org.kuali.student.poc.common.ws.exceptions.OperationFailedException;
import org.kuali.student.poc.common.ws.exceptions.PermissionDeniedException;
import org.kuali.student.poc.common.ws.exceptions.ReadOnlyException;
import org.kuali.student.poc.personidentity.person.PersonService;
import org.kuali.student.poc.personidentity.person.dao.PersonDAO;
import org.kuali.student.poc.personidentity.person.dto.PersonAttributeSetTypeDisplay;
import org.kuali.student.poc.personidentity.person.dto.PersonAttributeSetTypeInfo;
import org.kuali.student.poc.personidentity.person.dto.PersonAttributeTypeInfo;
import org.kuali.student.poc.personidentity.person.dto.PersonCreateInfo;
import org.kuali.student.poc.personidentity.person.dto.PersonCriteria;
import org.kuali.student.poc.personidentity.person.dto.PersonDisplay;
import org.kuali.student.poc.personidentity.person.dto.PersonDisplayDTO;
import org.kuali.student.poc.personidentity.person.dto.PersonInfo;
import org.kuali.student.poc.personidentity.person.dto.PersonRelationCreateInfo;
import org.kuali.student.poc.personidentity.person.dto.PersonRelationCriteria;
import org.kuali.student.poc.personidentity.person.dto.PersonRelationDisplay;
import org.kuali.student.poc.personidentity.person.dto.PersonRelationInfo;
import org.kuali.student.poc.personidentity.person.dto.PersonRelationTypeDisplay;
import org.kuali.student.poc.personidentity.person.dto.PersonRelationUpdateInfo;
import org.kuali.student.poc.personidentity.person.dto.PersonTypeDisplay;
import org.kuali.student.poc.personidentity.person.dto.PersonTypeInfo;
import org.kuali.student.poc.personidentity.person.dto.PersonUpdateInfo;
import org.kuali.student.poc.personidentity.person.dto.ValidationError;
import org.springframework.transaction.annotation.Transactional;

@WebService(endpointInterface = "org.kuali.student.poc.personidentity.person.PersonService", serviceName = "PersonService", portName = "PersonService", targetNamespace = "http://student.kuali.org/poc/wsdl/personidentity/person")
@Transactional
public class PersonServiceCacheDelegate implements PersonService {
	private PersonService target;
	private EhCacheHelper ehCacheHelper;
	private final String personInfoCacheName = "PersonInfo";
	private Logger log = Logger.getLogger("CacheDelegate");
	public PersonServiceCacheDelegate() {
		super();
		ehCacheHelper = new EhCacheHelper();
		ehCacheHelper.createCache(personInfoCacheName);
	}

	public PersonServiceCacheDelegate(PersonService clientTarget) {
		this();
		this.target = clientTarget;
	}

	public PersonServiceCacheDelegate(PersonService clientTarget,
			String configurationFileName) {
		ehCacheHelper = new EhCacheHelper(configurationFileName);
		ehCacheHelper.createCache(personInfoCacheName);
		this.target = clientTarget;
	}

	/**
	 * @return the personDAO
	 */
	public PersonDAO getPersonDAO() {
		return ((PersonServiceImpl) target).getPersonDAO();
	}

	/**
	 * @param personDAO
	 *            the personDAO to set
	 */
	public void setPersonDAO(PersonDAO personDAO) {
		if (target == null) {
			target = new PersonServiceImpl();
		}
		((PersonServiceImpl) target).setPersonDAO(personDAO);
	}

	/**
	 * @param personId
	 * @param personTypeKey
	 * @return
	 * @throws AlreadyExistsException
	 * @throws DisabledIdentifierException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 * @see org.kuali.student.poc.personidentity.person.PersonService#assignPersonType(java.lang.String,
	 *      java.lang.String)
	 */
	public boolean assignPersonType(String personId, String personTypeKey)
			throws AlreadyExistsException, DisabledIdentifierException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		return target.assignPersonType(personId, personTypeKey);
	}

	/**
	 * @param attributeDefinition
	 * @return
	 * @throws AlreadyExistsException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 * @see org.kuali.student.poc.personidentity.person.PersonService#createAttributeDefinition(org.kuali.student.poc.personidentity.person.dto.PersonAttributeTypeInfo)
	 */
	public String createAttributeDefinition(
			PersonAttributeTypeInfo attributeDefinition)
			throws AlreadyExistsException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		return target.createAttributeDefinition(attributeDefinition);
	}

	/**
	 * @param personCreateInfo
	 * @param personTypeKeys
	 * @return
	 * @throws AlreadyExistsException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 * @see org.kuali.student.poc.personidentity.person.PersonService#createPerson(org.kuali.student.poc.personidentity.person.dto.PersonCreateInfo,
	 *      java.util.List)
	 */
	public String createPerson(PersonCreateInfo personCreateInfo,
			List<String> personTypeKeys) throws AlreadyExistsException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		return target.createPerson(personCreateInfo, personTypeKeys);
	}

	/**
	 * @param attributeSetDTO
	 * @return
	 * @throws AlreadyExistsException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 * @see org.kuali.student.poc.personidentity.person.PersonService#createPersonAttributeSetType(org.kuali.student.poc.personidentity.person.dto.PersonAttributeSetTypeInfo)
	 */
	public String createPersonAttributeSetType(
			PersonAttributeSetTypeInfo attributeSetDTO)
			throws AlreadyExistsException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		return target.createPersonAttributeSetType(attributeSetDTO);
	}

	/**
	 * @param personId
	 * @param relatedPersonId
	 * @param personRelationTypeKey
	 * @param personRelationCreateInfo
	 * @return
	 * @throws AlreadyExistsException
	 * @throws DoesNotExistException
	 * @throws CircularReferenceException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 * @see org.kuali.student.poc.personidentity.person.PersonService#createPersonRelation(java.lang.String,
	 *      java.lang.String, java.lang.String,
	 *      org.kuali.student.poc.personidentity.person.dto.PersonRelationCreateInfo)
	 */
	public String createPersonRelation(String personId, String relatedPersonId,
			String personRelationTypeKey,
			PersonRelationCreateInfo personRelationCreateInfo)
			throws AlreadyExistsException, DoesNotExistException,
			CircularReferenceException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		return target.createPersonRelation(personId, relatedPersonId,
				personRelationTypeKey, personRelationCreateInfo);
	}

	/**
	 * @param personTypeInfo
	 * @return
	 * @throws AlreadyExistsException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 * @see org.kuali.student.poc.personidentity.person.PersonService#createPersonTypeInfo(org.kuali.student.poc.personidentity.person.dto.PersonTypeInfo)
	 */
	public String createPersonTypeInfo(PersonTypeInfo personTypeInfo)
			throws AlreadyExistsException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		return target.createPersonTypeInfo(personTypeInfo);
	}

	/**
	 * @param personId
	 * @return
	 * @throws DoesNotExistException
	 * @throws DisabledIdentifierException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 * @see org.kuali.student.poc.personidentity.person.PersonService#deletePerson(java.lang.String)
	 */
	public boolean deletePerson(String personId) throws DoesNotExistException,
			DisabledIdentifierException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		boolean result = target.deletePerson(personId);
		ehCacheHelper.evictCacheElement(personInfoCacheName, personId);
		return result;
	}

	/**
	 * @param personRelationId
	 * @return
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 * @see org.kuali.student.poc.personidentity.person.PersonService#deletePersonRelation(java.lang.String)
	 */
	public boolean deletePersonRelation(String personRelationId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		return target.deletePersonRelation(personRelationId);
	}

	/**
	 * @param personId
	 * @return
	 * @throws DoesNotExistException
	 * @throws DisabledIdentifierException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 * @see org.kuali.student.poc.personidentity.person.PersonService#fetchFullPersonInfo(java.lang.String)
	 */
	public PersonInfo fetchFullPersonInfo(String personId)
			throws DoesNotExistException, DisabledIdentifierException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		// Look in the cache
		PersonInfo result = (PersonInfo) ehCacheHelper.getCacheElementValue(
				personInfoCacheName, personId.toString());
		if (result == null) {
			if (target instanceof PersonServiceImpl) {
				log.info("Calling Service for uncached person on server");
			}else{
				log.info("Calling Service for uncached person on client");
			}
			// If the cache does not have it
			result = target.fetchFullPersonInfo(personId);
			ehCacheHelper.saveOrUpdateCacheElement(personInfoCacheName,
					personId, result);
		}
		return result;
	}

	/**
	 * @param personAttributeSetTypeKey
	 * @return
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @see org.kuali.student.poc.personidentity.person.PersonService#fetchPersonAttributeSetType(java.lang.String)
	 */
	public PersonAttributeSetTypeInfo fetchPersonAttributeSetType(
			String personAttributeSetTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		return target.fetchPersonAttributeSetType(personAttributeSetTypeKey);
	}

	/**
	 * @param personId
	 * @return
	 * @throws DoesNotExistException
	 * @throws DisabledIdentifierException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 * @see org.kuali.student.poc.personidentity.person.PersonService#fetchPersonDisplay(java.lang.String)
	 */
	public PersonDisplay fetchPersonDisplay(String personId)
			throws DoesNotExistException, DisabledIdentifierException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		return target.fetchPersonDisplay(personId);
	}

	/**
	 * @param personId
	 * @param personAttributeSetTypeKeyList
	 * @return
	 * @throws DoesNotExistException
	 * @throws DisabledIdentifierException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 * @see org.kuali.student.poc.personidentity.person.PersonService#fetchPersonInfoByPersonAttributeSetTypes(java.lang.String,
	 *      java.util.List)
	 */
	public PersonInfo fetchPersonInfoByPersonAttributeSetTypes(String personId,
			List<String> personAttributeSetTypeKeyList)
			throws DoesNotExistException, DisabledIdentifierException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		return target.fetchPersonInfoByPersonAttributeSetTypes(personId,
				personAttributeSetTypeKeyList);
	}

	/**
	 * @param personId
	 * @param personTypeKey
	 * @return
	 * @throws DoesNotExistException
	 * @throws DisabledIdentifierException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 * @see org.kuali.student.poc.personidentity.person.PersonService#fetchPersonInfoByPersonType(java.lang.String,
	 *      java.lang.String)
	 */
	public PersonInfo fetchPersonInfoByPersonType(String personId,
			String personTypeKey) throws DoesNotExistException,
			DisabledIdentifierException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		return target.fetchPersonInfoByPersonType(personId, personTypeKey);
	}

	/**
	 * @param personRelationId
	 * @return
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 * @see org.kuali.student.poc.personidentity.person.PersonService#fetchPersonRelation(java.lang.String)
	 */
	public PersonRelationInfo fetchPersonRelation(String personRelationId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		return target.fetchPersonRelation(personRelationId);
	}

	/**
	 * @return
	 * @throws OperationFailedException
	 * @see org.kuali.student.poc.personidentity.person.PersonService#fetchPersonRelationTypes()
	 */
	public List<PersonRelationTypeDisplay> fetchPersonRelationTypes()
			throws OperationFailedException {
		return target.fetchPersonRelationTypes();
	}

	/**
	 * @param personTypeKey
	 * @return
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @see org.kuali.student.poc.personidentity.person.PersonService#fetchPersonType(java.lang.String)
	 */
	public PersonTypeInfo fetchPersonType(String personTypeKey)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		return target.fetchPersonType(personTypeKey);
	}

	/**
	 * @param personId
	 * @return
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 * @see org.kuali.student.poc.personidentity.person.PersonService#fetchReplacementPersonId(java.lang.String)
	 */
	public String fetchReplacementPersonId(String personId)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		return target.fetchReplacementPersonId(personId);
	}

	/**
	 * @return
	 * @throws OperationFailedException
	 * @see org.kuali.student.poc.personidentity.person.PersonService#findCreatablePersonTypes()
	 */
	public List<PersonTypeDisplay> findCreatablePersonTypes()
			throws OperationFailedException {
		return target.findCreatablePersonTypes();
	}

	/**
	 * @param personIdList
	 * @return
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @see org.kuali.student.poc.personidentity.person.PersonService#findPeopleByPersonIds(java.util.List)
	 */
	public List<PersonInfo> findPeopleByPersonIds(List<String> personIdList)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		List<PersonInfo> results = new ArrayList<PersonInfo>();
		List<String> uncachedPersonIdList = new ArrayList<String>();
		for (String personId : personIdList) {
			// Look in the cache
			PersonInfo personInfo = (PersonInfo) ehCacheHelper
					.getCacheElementValue(personInfoCacheName, personId
							.toString());
			if (personInfo == null) {
				uncachedPersonIdList.add(personId);
			} else {
				results.add(personInfo);
			}
		}
		if (uncachedPersonIdList.size() > 0) {
			if (target instanceof PersonServiceImpl) {
				log.info("Calling Service for uncached list of people on server");
			}else{
				log.info("Calling Service for uncached list of people on client");
			}
			List<PersonInfo> uncachedResults = target
					.findPeopleByPersonIds(uncachedPersonIdList);
			if (uncachedResults != null) {
				for (PersonInfo personInfo : uncachedResults) {
					// Add to the cache and add to results
					results.add(personInfo);
					ehCacheHelper.saveOrUpdateCacheElement(personInfoCacheName,
							personInfo.getPersonId(), personInfo);
				}
			}
		}
		return results;
	}

	/**
	 * @param personIdList
	 * @return
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @see org.kuali.student.poc.personidentity.person.PersonService#findPeopleDisplayByPersonIds(java.util.List)
	 */
	public List<PersonDisplay> findPeopleDisplayByPersonIds(
			List<String> personIdList) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		return target.findPeopleDisplayByPersonIds(personIdList);
	}

	/**
	 * @return
	 * @throws OperationFailedException
	 * @see org.kuali.student.poc.personidentity.person.PersonService#findPersonAttributeSetTypes()
	 */
	public List<PersonAttributeSetTypeDisplay> findPersonAttributeSetTypes()
			throws OperationFailedException {
		return target.findPersonAttributeSetTypes();
	}

	/**
	 * @param personId
	 * @return
	 * @throws DoesNotExistException
	 * @throws DisabledIdentifierException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 * @see org.kuali.student.poc.personidentity.person.PersonService#findPersonAttributeSetTypesForPerson(java.lang.String)
	 */
	public List<String> findPersonAttributeSetTypesForPerson(String personId)
			throws DoesNotExistException, DisabledIdentifierException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		return target.findPersonAttributeSetTypesForPerson(personId);
	}

	/**
	 * @param personTypeKey
	 * @return
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @see org.kuali.student.poc.personidentity.person.PersonService#findPersonAttributeSetTypesForPersonType(java.lang.String)
	 */
	public List<PersonAttributeSetTypeDisplay> findPersonAttributeSetTypesForPersonType(
			String personTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		return target.findPersonAttributeSetTypesForPersonType(personTypeKey);
	}

	/**
	 * @param personTypeKey
	 * @param personFilter
	 * @return
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 * @see org.kuali.student.poc.personidentity.person.PersonService#findPersonIdsForPersonType(java.lang.String,
	 *      org.kuali.student.poc.personidentity.person.dto.PersonCriteria)
	 */
	public List<String> findPersonIdsForPersonType(String personTypeKey,
			PersonCriteria personFilter) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		return target.findPersonIdsForPersonType(personTypeKey, personFilter);
	}

	/**
	 * @param personId
	 * @param personRelationTypeKey
	 * @return
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 * @see org.kuali.student.poc.personidentity.person.PersonService#findPersonPersonRelationIds(java.lang.String,
	 *      java.lang.String)
	 */
	public List<String> findPersonPersonRelationIds(String personId,
			String personRelationTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		return target.findPersonPersonRelationIds(personId,
				personRelationTypeKey);
	}

	/**
	 * @param personId
	 * @param personRelationTypeKey
	 * @return
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 * @see org.kuali.student.poc.personidentity.person.PersonService#findPersonRelations(java.lang.String,
	 *      java.lang.String)
	 */
	public List<PersonRelationDisplay> findPersonRelations(String personId,
			String personRelationTypeKey) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		return target.findPersonRelations(personId, personRelationTypeKey);
	}

	/**
	 * @return
	 * @throws OperationFailedException
	 * @see org.kuali.student.poc.personidentity.person.PersonService#findPersonTypes()
	 */
	public List<PersonTypeDisplay> findPersonTypes()
			throws OperationFailedException {
		return target.findPersonTypes();
	}

	/**
	 * @param personId
	 * @return
	 * @throws DoesNotExistException
	 * @throws DisabledIdentifierException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 * @see org.kuali.student.poc.personidentity.person.PersonService#findPersonTypesForPerson(java.lang.String)
	 */
	public List<String> findPersonTypesForPerson(String personId)
			throws DoesNotExistException, DisabledIdentifierException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		return target.findPersonTypesForPerson(personId);
	}

	/**
	 * @param personId
	 * @param personTypeKey
	 * @return
	 * @throws DoesNotExistException
	 * @throws DisabledIdentifierException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 * @see org.kuali.student.poc.personidentity.person.PersonService#isPersonType(java.lang.String,
	 *      java.lang.String)
	 */
	public boolean isPersonType(String personId, String personTypeKey)
			throws DoesNotExistException, DisabledIdentifierException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		return target.isPersonType(personId, personTypeKey);
	}

	/**
	 * @param personId
	 * @param personTypeKey
	 * @return
	 * @throws DoesNotExistException
	 * @throws DisabledIdentifierException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 * @see org.kuali.student.poc.personidentity.person.PersonService#removePersonType(java.lang.String,
	 *      java.lang.String)
	 */
	public boolean removePersonType(String personId, String personTypeKey)
			throws DoesNotExistException, DisabledIdentifierException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		return target.removePersonType(personId, personTypeKey);
	}

	/**
	 * @param personCriteria
	 * @return
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 * @see org.kuali.student.poc.personidentity.person.PersonService#searchForPeople(org.kuali.student.poc.personidentity.person.dto.PersonCriteria)
	 */
	public List<PersonInfo> searchForPeople(PersonCriteria personCriteria)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		return target.searchForPeople(personCriteria);
	}

	/**
	 * @param personAttributeSetTypeKey
	 * @param personCriteria
	 * @return
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 * @see org.kuali.student.poc.personidentity.person.PersonService#searchForPeopleByPersonAttributeSetType(java.lang.String,
	 *      org.kuali.student.poc.personidentity.person.dto.PersonCriteria)
	 */
	public List<PersonInfo> searchForPeopleByPersonAttributeSetType(
			String personAttributeSetTypeKey, PersonCriteria personCriteria)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		return target.searchForPeopleByPersonAttributeSetType(
				personAttributeSetTypeKey, personCriteria);
	}

	/**
	 * @param personTypeKey
	 * @param personCriteria
	 * @return
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 * @see org.kuali.student.poc.personidentity.person.PersonService#searchForPeopleByPersonType(java.lang.String,
	 *      org.kuali.student.poc.personidentity.person.dto.PersonCriteria)
	 */
	public List<PersonInfo> searchForPeopleByPersonType(String personTypeKey,
			PersonCriteria personCriteria) throws InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		return target
				.searchForPeopleByPersonType(personTypeKey, personCriteria);
	}

	/**
	 * @param personRelationCriteria
	 * @return
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 * @see org.kuali.student.poc.personidentity.person.PersonService#searchForPeopleByRelation(org.kuali.student.poc.personidentity.person.dto.PersonRelationCriteria)
	 */
	public List<PersonDisplay> searchForPeopleByRelation(
			PersonRelationCriteria personRelationCriteria)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		return target.searchForPeopleByRelation(personRelationCriteria);
	}

	/**
	 * @param personCriteria
	 * @return
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 * @see org.kuali.student.poc.personidentity.person.PersonService#searchForPersonIds(org.kuali.student.poc.personidentity.person.dto.PersonCriteria)
	 */
	public List<String> searchForPersonIds(PersonCriteria personCriteria)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		return target.searchForPersonIds(personCriteria);
	}

	/**
	 * @param personRelationCriteria
	 * @return
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 * @see org.kuali.student.poc.personidentity.person.PersonService#searchForPersonIdsByRelation(org.kuali.student.poc.personidentity.person.dto.PersonRelationCriteria)
	 */
	public List<String> searchForPersonIdsByRelation(
			PersonRelationCriteria personRelationCriteria)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		return target.searchForPersonIdsByRelation(personRelationCriteria);
	}

	/**
	 * @param personRelationCriteria
	 * @return
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 * @see org.kuali.student.poc.personidentity.person.PersonService#searchForPersonRelations(org.kuali.student.poc.personidentity.person.dto.PersonRelationCriteria)
	 */
	public List<PersonRelationDisplay> searchForPersonRelations(
			PersonRelationCriteria personRelationCriteria)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		return target.searchForPersonRelations(personRelationCriteria);
	}

	/**
	 * @param personCriteria
	 * @return
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 */
	public List<PersonDisplayDTO> searchForPersonDisplayDTOs (
			PersonCriteria personCriteria) throws InvalidParameterException,
	MissingParameterException, OperationFailedException,
	PermissionDeniedException {
		return target.searchForPersonDisplayDTOs(personCriteria);
	}

	/**
	 * @param personId
	 * @param personUpdateInfo
	 * @return
	 * @throws DoesNotExistException
	 * @throws DisabledIdentifierException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws ReadOnlyException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 * @see org.kuali.student.poc.personidentity.person.PersonService#updatePerson(java.lang.String,
	 *      org.kuali.student.poc.personidentity.person.dto.PersonUpdateInfo)
	 */
	public boolean updatePerson(String personId,
			PersonUpdateInfo personUpdateInfo) throws DoesNotExistException,
			DisabledIdentifierException, InvalidParameterException,
			MissingParameterException, ReadOnlyException,
			OperationFailedException, PermissionDeniedException {
		boolean result = target.updatePerson(personId, personUpdateInfo);
		// invalidate the cache
		if (target instanceof PersonServiceImpl) {
			log.info("Invalidating Person Cache on server");
		}else{
			log.info("Invalidating Person Cache on client");
		}
		ehCacheHelper.evictCacheElement(personInfoCacheName, personId);
		return result;
	}

	/**
	 * @param personRelationId
	 * @param personRelationUpdateInfo
	 * @return
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws ReadOnlyException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 * @see org.kuali.student.poc.personidentity.person.PersonService#updatePersonRelation(java.lang.String,
	 *      org.kuali.student.poc.personidentity.person.dto.PersonRelationUpdateInfo)
	 */
	public boolean updatePersonRelation(String personRelationId,
			PersonRelationUpdateInfo personRelationUpdateInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, ReadOnlyException,
			OperationFailedException, PermissionDeniedException {
		return target.updatePersonRelation(personRelationId,
				personRelationUpdateInfo);
	}

	/**
	 * @param personId
	 * @param personTypeKey
	 * @return
	 * @throws DoesNotExistException
	 * @throws DisabledIdentifierException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 * @see org.kuali.student.poc.personidentity.person.PersonService#validatePersonInfoForPersonType(java.lang.String,
	 *      java.lang.String)
	 */
	public ValidationError validatePersonInfoForPersonType(String personId,
			String personTypeKey) throws DoesNotExistException,
			DisabledIdentifierException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		return target.validatePersonInfoForPersonType(personId, personTypeKey);
	}
}
