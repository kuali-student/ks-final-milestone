/*
 * Copyright 2014 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.core.person.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.common.UUIDHelper;
import org.kuali.student.common.mock.MockService;
import org.kuali.student.core.person.dto.PersonAffiliationInfo;
import org.kuali.student.core.person.dto.PersonBioDemographicsInfo;
import org.kuali.student.core.person.dto.PersonIdentifierInfo;
import org.kuali.student.core.person.dto.PersonInfo;
import org.kuali.student.core.person.dto.PersonNameInfo;
import org.kuali.student.core.person.service.PersonService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.MetaInfo;
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

public class PersonServiceMapImpl implements MockService, PersonService {
	// cache variable 
    // The LinkedHashMap is just so the values come back in a predictable order
    private Map<String, PersonInfo> personMap = new LinkedHashMap<String, PersonInfo>();
    private Map<String, PersonNameInfo> personNameMap = new LinkedHashMap<String, PersonNameInfo>();
    private Map<String, PersonIdentifierInfo> personIdentifierMap = new LinkedHashMap<String, PersonIdentifierInfo>();
    private Map<String, PersonBioDemographicsInfo> personBioDemographicsMap = new LinkedHashMap<String, PersonBioDemographicsInfo>();
    private Map<String, PersonAffiliationInfo> personAffiliationMap = new LinkedHashMap<String, PersonAffiliationInfo>();

    @Override
    public void clear() {
        this.personMap.clear();
        this.personNameMap.clear();
        this.personIdentifierMap.clear();
        this.personBioDemographicsMap.clear();
        this.personAffiliationMap.clear();
    }

    @Override
    public PersonInfo getPerson(String personId, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // GET_BY_ID
        if (!this.personMap.containsKey(personId)) {
            throw new DoesNotExistException(personId);
        }
        return new PersonInfo(this.personMap.get(personId));
    }

    @Override
    public List<PersonInfo> getPersonsByIds(List<String> personIds, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // GET_BY_IDS
        List<PersonInfo> list = new ArrayList<PersonInfo>();
        for (String id : personIds) {
            list.add(this.getPerson(id, contextInfo));
        }
        return list;
    }

    @Override
    public List<String> getPersonIdsByType(String personTypeKey, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // GET_IDS_BY_TYPE
        List<String> list = new ArrayList<String>();
        for (PersonInfo info : personMap.values()) {
            if (personTypeKey.equals(info.getTypeKey())) {
                list.add(info.getId());
            }
        }
        return list;
    }

    @Override
    public List<String> searchForPersonIds(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // UNKNOWN
        throw new OperationFailedException("searchForPersonIds has not been implemented");
    }

    @Override
    public List<PersonInfo> searchForPersons(QueryByCriteria criteria, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // UNKNOWN
        throw new OperationFailedException("searchForPersons has not been implemented");
    }

    @Override
    public List<ValidationResultInfo> validatePerson(String validationTypeKey, String personTypeKey, PersonInfo personInfo,
            ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // VALIDATE
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    public PersonInfo createPerson(String personTypeKey, PersonInfo personInfo, ContextInfo contextInfo)
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException {
        // CREATE
        if (!personTypeKey.equals(personInfo.getTypeKey())) {
            throw new InvalidParameterException("The type parameter does not match the type on the info object");
        }
        PersonInfo copy = new PersonInfo(personInfo);
        if (copy.getId() == null) {
            copy.setId(UUIDHelper.genStringUUID());
        }
        copy.setMeta(newMeta(contextInfo));
        personMap.put(copy.getId(), copy);
        return new PersonInfo(copy);
    }

    @Override
    public PersonInfo updatePerson(String personId, PersonInfo personInfo, ContextInfo contextInfo)
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException,
            VersionMismatchException {
        // UPDATE
        if (!personId.equals(personInfo.getId())) {
            throw new InvalidParameterException("The id parameter does not match the id on the info object");
        }
        PersonInfo copy = new PersonInfo(personInfo);
        PersonInfo old = this.getPerson(personInfo.getId(), contextInfo);
        if (!old.getMeta().getVersionInd().equals(copy.getMeta().getVersionInd())) {
            throw new VersionMismatchException(old.getMeta().getVersionInd());
        }
        copy.setMeta(updateMeta(copy.getMeta(), contextInfo));
        this.personMap.put(personInfo.getId(), copy);
        return new PersonInfo(copy);
    }

    @Override
    public StatusInfo deletePerson(String personId, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // DELETE
        if (this.personMap.remove(personId) == null) {
            throw new OperationFailedException(personId);
        }
        return newStatus();
    }

    @Override
    public PersonNameInfo getPersonName(String personNameId, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // GET_BY_ID
        if (!this.personNameMap.containsKey(personNameId)) {
            throw new DoesNotExistException(personNameId);
        }
        return new PersonNameInfo(this.personNameMap.get(personNameId));
    }

    @Override
    public List<PersonNameInfo> getPersonNamesByIds(List<String> personNameIds, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // GET_BY_IDS
        List<PersonNameInfo> list = new ArrayList<PersonNameInfo>();
        for (String id : personNameIds) {
            list.add(this.getPersonName(id, contextInfo));
        }
        return list;
    }

    @Override
    public List<String> getPersonNameIdsByType(String personNameTypeKey, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // GET_IDS_BY_TYPE
        List<String> list = new ArrayList<String>();
        for (PersonNameInfo info : personNameMap.values()) {
            if (personNameTypeKey.equals(info.getTypeKey())) {
                list.add(info.getId());
            }
        }
        return list;
    }

    @Override
    public List<String> searchForPersonNameIds(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // UNKNOWN
        throw new OperationFailedException("searchForPersonNameIds has not been implemented");
    }

    @Override
    public List<PersonNameInfo> searchForPersonNames(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // UNKNOWN
        throw new OperationFailedException("searchForPersonNames has not been implemented");
    }

    @Override
    public List<ValidationResultInfo> validatePersonName(String validationTypeKey, String personNameTypeKey, String personId,
            PersonNameInfo personNameInfo, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // VALIDATE
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    public PersonNameInfo createPersonName(String personNameTypeKey, String personId, PersonNameInfo personNameInfo,
            ContextInfo contextInfo)
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException {
        // CREATE
        if (!personNameTypeKey.equals(personNameInfo.getTypeKey())) {
            throw new InvalidParameterException("The type parameter does not match the type on the info object");
        }
        // TODO: check the rest of the readonly fields that are specified on the create to make sure they match the info object
        PersonNameInfo copy = new PersonNameInfo(personNameInfo);
        if (copy.getId() == null) {
            copy.setId(UUIDHelper.genStringUUID());
        }
        copy.setMeta(newMeta(contextInfo));
        personNameMap.put(copy.getId(), copy);
        return new PersonNameInfo(copy);
    }

    @Override
    public PersonNameInfo updatePersonName(String personNameId, PersonNameInfo personNameInfo, ContextInfo contextInfo)
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException,
            VersionMismatchException {
        // UPDATE
        if (!personNameId.equals(personNameInfo.getId())) {
            throw new InvalidParameterException("The id parameter does not match the id on the info object");
        }
        PersonNameInfo copy = new PersonNameInfo(personNameInfo);
        PersonNameInfo old = this.getPersonName(personNameInfo.getId(), contextInfo);
        if (!old.getMeta().getVersionInd().equals(copy.getMeta().getVersionInd())) {
            throw new VersionMismatchException(old.getMeta().getVersionInd());
        }
        copy.setMeta(updateMeta(copy.getMeta(), contextInfo));
        this.personNameMap.put(personNameInfo.getId(), copy);
        return new PersonNameInfo(copy);
    }

    @Override
    public StatusInfo deletePersonName(String personNameId, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // DELETE
        if (this.personNameMap.remove(personNameId) == null) {
            throw new OperationFailedException(personNameId);
        }
        return newStatus();
    }

    @Override
    public List<PersonNameInfo> getPersonNamesByPerson(String personId, ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // GET_INFOS_BY_OTHER
        List<PersonNameInfo> list = new ArrayList<PersonNameInfo>();
        for (PersonNameInfo info : personNameMap.values()) {
            if (personId.equals(info.getPersonId())) {
                list.add(new PersonNameInfo(info));
            }
        }
        return list;
    }

    @Override
    public PersonIdentifierInfo getPersonIdentifier(String personIdentifierId, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // GET_BY_ID
        if (!this.personIdentifierMap.containsKey(personIdentifierId)) {
            throw new DoesNotExistException(personIdentifierId);
        }
        return new PersonIdentifierInfo(this.personIdentifierMap.get(personIdentifierId));
    }

    @Override
    public List<PersonIdentifierInfo> getPersonIdentifiersByIds(List<String> personIdentifierIds, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // GET_BY_IDS
        List<PersonIdentifierInfo> list = new ArrayList<PersonIdentifierInfo>();
        for (String id : personIdentifierIds) {
            list.add(this.getPersonIdentifier(id, contextInfo));
        }
        return list;
    }

    @Override
    public List<String> getPersonIdentifierIdsByType(String personIdentifierTypeKey, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // GET_IDS_BY_TYPE
        List<String> list = new ArrayList<String>();
        for (PersonIdentifierInfo info : personIdentifierMap.values()) {
            if (personIdentifierTypeKey.equals(info.getTypeKey())) {
                list.add(info.getId());
            }
        }
        return list;
    }

    @Override
    public List<String> searchForPersonIdentifierIds(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // UNKNOWN
        throw new OperationFailedException("searchForPersonIdentifierIds has not been implemented");
    }

    @Override
    public List<PersonIdentifierInfo> searchForPersonIdentifiers(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // UNKNOWN
        throw new OperationFailedException("searchForPersonIdentifiers has not been implemented");
    }

    @Override
    public List<ValidationResultInfo> validatePersonIdentifier(String validationTypeKey, String personIdentifierTypeKey,
            String personId, PersonIdentifierInfo personIdentifierInfo, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // VALIDATE
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    public PersonIdentifierInfo createPersonIdentifier(String personIdentifierTypeKey, String personId,
            PersonIdentifierInfo personIdentifierInfo, ContextInfo contextInfo)
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException {
        // CREATE
        if (!personIdentifierTypeKey.equals(personIdentifierInfo.getTypeKey())) {
            throw new InvalidParameterException("The type parameter does not match the type on the info object");
        }
        // TODO: check the rest of the readonly fields that are specified on the create to make sure they match the info object
        PersonIdentifierInfo copy = new PersonIdentifierInfo(personIdentifierInfo);
        if (copy.getId() == null) {
            copy.setId(UUIDHelper.genStringUUID());
        }
        copy.setMeta(newMeta(contextInfo));
        personIdentifierMap.put(copy.getId(), copy);
        return new PersonIdentifierInfo(copy);
    }

    @Override
    public PersonIdentifierInfo updatePersonIdentifier(String personIdentifierId, PersonIdentifierInfo personIdentifierInfo,
            ContextInfo contextInfo)
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException,
            VersionMismatchException {
        // UPDATE
        if (!personIdentifierId.equals(personIdentifierInfo.getId())) {
            throw new InvalidParameterException("The id parameter does not match the id on the info object");
        }
        PersonIdentifierInfo copy = new PersonIdentifierInfo(personIdentifierInfo);
        PersonIdentifierInfo old = this.getPersonIdentifier(personIdentifierInfo.getId(), contextInfo);
        if (!old.getMeta().getVersionInd().equals(copy.getMeta().getVersionInd())) {
            throw new VersionMismatchException(old.getMeta().getVersionInd());
        }
        copy.setMeta(updateMeta(copy.getMeta(), contextInfo));
        this.personIdentifierMap.put(personIdentifierInfo.getId(), copy);
        return new PersonIdentifierInfo(copy);
    }

    @Override
    public StatusInfo deletePersonIdentifier(String personIdentifierId, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // DELETE
        if (this.personIdentifierMap.remove(personIdentifierId) == null) {
            throw new OperationFailedException(personIdentifierId);
        }
        return newStatus();
    }

    @Override
    public List<PersonIdentifierInfo> getPersonIdentifiersByPerson(String personId, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // GET_INFOS_BY_OTHER
        List<PersonIdentifierInfo> list = new ArrayList<PersonIdentifierInfo>();
        for (PersonIdentifierInfo info : personIdentifierMap.values()) {
            if (personId.equals(info.getPersonId())) {
                list.add(new PersonIdentifierInfo(info));
            }
        }
        return list;
    }

    @Override
    public List<PersonIdentifierInfo> getPersonIdentifiersByIdentifier(String personIdentifierTypeKey, String identifier,
            ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // GET_INFOS_BY_OTHER
        List<PersonIdentifierInfo> list = new ArrayList<PersonIdentifierInfo>();
        for (PersonIdentifierInfo info : personIdentifierMap.values()) {
            if (personIdentifierTypeKey.equals(info.getTypeKey())) {
                if (identifier.equals(info.getIdentifier())) {
                    list.add(new PersonIdentifierInfo(info));
                }
            }
        }
        return list;
    }

    @Override
    public PersonBioDemographicsInfo getPersonBioDemographics(String personBioDemographicsId, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // GET_BY_ID
        if (!this.personBioDemographicsMap.containsKey(personBioDemographicsId)) {
            throw new DoesNotExistException(personBioDemographicsId);
        }
        return new PersonBioDemographicsInfo(this.personBioDemographicsMap.get(personBioDemographicsId));
    }

    @Override
    public List<PersonBioDemographicsInfo> getPersonBioDemographicsByIds(List<String> personBioDemographicsIds,
            ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // GET_BY_IDS
        List<PersonBioDemographicsInfo> list = new ArrayList<PersonBioDemographicsInfo>();
        for (String id : personBioDemographicsIds) {
            list.add(this.getPersonBioDemographics(id, contextInfo));
        }
        return list;
    }

    @Override
    public List<String> getPersonBioDemographicsIdsByType(String personIdentifierTypeKey, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // GET_IDS_BY_TYPE
        List<String> list = new ArrayList<String>();
        for (PersonBioDemographicsInfo info : personBioDemographicsMap.values()) {
            if (personIdentifierTypeKey.equals(info.getTypeKey())) {
                list.add(info.getId());
            }
        }
        return list;
    }

    @Override
    public List<String> searchForPersonBioDemographicsIds(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // UNKNOWN
        throw new OperationFailedException("searchForPersonBioDemographicsIds has not been implemented");
    }

    @Override
    public List<PersonBioDemographicsInfo> searchForPersonBioDemographics(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // UNKNOWN
        throw new OperationFailedException("searchForPersonBioDemographics has not been implemented");
    }

    @Override
    public List<ValidationResultInfo> validatePersonBioDemographics(String validationTypeKey, String personIdentifierTypeKey,
            String personId, PersonBioDemographicsInfo personBioDemographicsInfo, ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // VALIDATE
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    public PersonBioDemographicsInfo createPersonBioDemographics(String personIdentifierTypeKey, String personId,
            PersonBioDemographicsInfo personBioDemographicsInfo, ContextInfo contextInfo)
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException {
        // CREATE
        if (!personIdentifierTypeKey.equals(personBioDemographicsInfo.getTypeKey())) {
            throw new InvalidParameterException("The type parameter does not match the type on the info object");
        }
        // TODO: check the rest of the readonly fields that are specified on the create to make sure they match the info object
        PersonBioDemographicsInfo copy = new PersonBioDemographicsInfo(personBioDemographicsInfo);
        if (copy.getId() == null) {
            copy.setId(UUIDHelper.genStringUUID());
        }
        copy.setMeta(newMeta(contextInfo));
        personBioDemographicsMap.put(copy.getId(), copy);
        return new PersonBioDemographicsInfo(copy);
    }

    @Override
    public PersonBioDemographicsInfo updatePersonBioDemographics(String personBioDemographicsId,
            PersonBioDemographicsInfo PersonBioDemographicInfo, ContextInfo contextInfo)
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException,
            VersionMismatchException {
        // UPDATE
        if (!personBioDemographicsId.equals(PersonBioDemographicInfo.getId())) {
            throw new InvalidParameterException("The id parameter does not match the id on the info object");
        }
        PersonBioDemographicsInfo copy = new PersonBioDemographicsInfo(PersonBioDemographicInfo);
        PersonBioDemographicsInfo old = this.getPersonBioDemographics(PersonBioDemographicInfo.getId(), contextInfo);
        if (!old.getMeta().getVersionInd().equals(copy.getMeta().getVersionInd())) {
            throw new VersionMismatchException(old.getMeta().getVersionInd());
        }
        copy.setMeta(updateMeta(copy.getMeta(), contextInfo));
        this.personBioDemographicsMap.put(PersonBioDemographicInfo.getId(), copy);
        return new PersonBioDemographicsInfo(copy);
    }

    @Override
    public StatusInfo deletePersonBioDemographics(String personBioDemographicsId, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // DELETE
        if (this.personBioDemographicsMap.remove(personBioDemographicsId) == null) {
            throw new OperationFailedException(personBioDemographicsId);
        }
        return newStatus();
    }

    @Override
    public PersonBioDemographicsInfo getPersonBioDemographicsByPerson(String personId, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // GET_BY_ID
        if (!this.personBioDemographicsMap.containsKey(personId)) {
            throw new OperationFailedException(personId);
        }
        return new PersonBioDemographicsInfo(this.personBioDemographicsMap.get(personId));
    }

    @Override
    public PersonAffiliationInfo getPersonAffiliation(String personAffiliationId, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // GET_BY_ID
        if (!this.personAffiliationMap.containsKey(personAffiliationId)) {
            throw new DoesNotExistException(personAffiliationId);
        }
        return new PersonAffiliationInfo(this.personAffiliationMap.get(personAffiliationId));
    }

    @Override
    public List<PersonAffiliationInfo> getPersonAffiliationsByIds(List<String> personAffiliationIds, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // GET_BY_IDS
        List<PersonAffiliationInfo> list = new ArrayList<PersonAffiliationInfo>();
        for (String id : personAffiliationIds) {
            list.add(this.getPersonAffiliation(id, contextInfo));
        }
        return list;
    }

    @Override
    public List<String> getPersonAffiliationIdsByType(String personAffiliationTypeKey, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // GET_IDS_BY_TYPE
        List<String> list = new ArrayList<String>();
        for (PersonAffiliationInfo info : personAffiliationMap.values()) {
            if (personAffiliationTypeKey.equals(info.getTypeKey())) {
                list.add(info.getId());
            }
        }
        return list;
    }

    @Override
    public List<String> searchForPersonAffiliationIds(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // UNKNOWN
        throw new OperationFailedException("searchForPersonAffiliationIds has not been implemented");
    }

    @Override
    public List<PersonAffiliationInfo> searchForPersonAffiliations(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // UNKNOWN
        throw new OperationFailedException("searchForPersonAffiliations has not been implemented");
    }

    @Override
    public List<ValidationResultInfo> validatePersonAffiliation(String validationTypeKey, String personAffiliationTypeKey,
            String personId, String organizationId, PersonAffiliationInfo personAffiliationInfo, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // VALIDATE
        return new ArrayList<ValidationResultInfo>();
    }

    @Override
    public PersonAffiliationInfo createPersonAffiliation(String personAffiliationTypeKey, String personId, String organizationId,
            PersonAffiliationInfo personAffiliationInfo, ContextInfo contextInfo)
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException {
        // CREATE
        if (!personAffiliationTypeKey.equals(personAffiliationInfo.getTypeKey())) {
            throw new InvalidParameterException("The type parameter does not match the type on the info object");
        }
        // TODO: check the rest of the readonly fields that are specified on the create to make sure they match the info object
        PersonAffiliationInfo copy = new PersonAffiliationInfo(personAffiliationInfo);
        if (copy.getId() == null) {
            copy.setId(UUIDHelper.genStringUUID());
        }
        copy.setMeta(newMeta(contextInfo));
        personAffiliationMap.put(copy.getId(), copy);
        return new PersonAffiliationInfo(copy);
    }

    @Override
    public PersonAffiliationInfo updatePersonAffiliation(String personAffiliationId, PersonAffiliationInfo personAffiliationInfo,
            ContextInfo contextInfo)
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException,
            VersionMismatchException {
        // UPDATE
        if (!personAffiliationId.equals(personAffiliationInfo.getId())) {
            throw new InvalidParameterException("The id parameter does not match the id on the info object");
        }
        PersonAffiliationInfo copy = new PersonAffiliationInfo(personAffiliationInfo);
        PersonAffiliationInfo old = this.getPersonAffiliation(personAffiliationInfo.getId(), contextInfo);
        if (!old.getMeta().getVersionInd().equals(copy.getMeta().getVersionInd())) {
            throw new VersionMismatchException(old.getMeta().getVersionInd());
        }
        copy.setMeta(updateMeta(copy.getMeta(), contextInfo));
        this.personAffiliationMap.put(personAffiliationInfo.getId(), copy);
        return new PersonAffiliationInfo(copy);
    }

    @Override
    public StatusInfo deletePersonAffiliation(String personAffiliationId, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // DELETE
        if (this.personAffiliationMap.remove(personAffiliationId) == null) {
            throw new OperationFailedException(personAffiliationId);
        }
        return newStatus();
    }

    @Override
    public List<PersonAffiliationInfo> getPersonAffiliationsByPerson(String personId, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // GET_INFOS_BY_OTHER
        List<PersonAffiliationInfo> list = new ArrayList<PersonAffiliationInfo>();
        for (PersonAffiliationInfo info : personAffiliationMap.values()) {
            if (personId.equals(info.getPersonId())) {
                list.add(new PersonAffiliationInfo(info));
            }
        }
        return list;
    }

    @Override
    public List<PersonInfo> getActivePeopleMatchingNameFragmentAndAffiliation(String nameFragment, String personAffiliationTypeKey,
            String organizationId, ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // UNKNOWN
        throw new OperationFailedException("getActivePeopleMatchingNameFragmentAndAffiliation has not been implemented");
    }

    @Override
    public String getInstitutionalAffiliationOrganizationId(ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // UNKNOWN
        throw new OperationFailedException("getIntitutionalAffiliationOrganizationId has not been implemented");
    }

    private StatusInfo newStatus() {
        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);
        return status;
    }

    private MetaInfo newMeta(ContextInfo context) {
        MetaInfo meta = new MetaInfo();
        meta.setCreateId(context.getPrincipalId());
        meta.setCreateTime(new Date());
        meta.setUpdateId(context.getPrincipalId());
        meta.setUpdateTime(meta.getCreateTime());
        meta.setVersionInd("0");
        return meta;
    }

    private MetaInfo updateMeta(MetaInfo old, ContextInfo context) {
        MetaInfo meta = new MetaInfo(old);
        meta.setUpdateId(context.getPrincipalId());
        meta.setUpdateTime(new Date());
        meta.setVersionInd((Integer.parseInt(meta.getVersionInd()) + 1) + "");
        return meta;
    }

}
