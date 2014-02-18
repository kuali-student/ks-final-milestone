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

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang.time.DateUtils;
import org.kuali.rice.core.api.criteria.Predicate;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.kim.api.KimConstants;
import org.kuali.rice.kim.api.identity.CodedAttribute;
import org.kuali.rice.kim.api.identity.IdentityService;
import org.kuali.rice.kim.api.identity.affiliation.EntityAffiliation;
import org.kuali.rice.kim.api.identity.affiliation.EntityAffiliationType;
import org.kuali.rice.kim.api.identity.entity.Entity;
import org.kuali.rice.kim.api.identity.entity.EntityDefault;
import org.kuali.rice.kim.api.identity.entity.EntityDefaultQueryResults;
import org.kuali.rice.kim.api.identity.entity.EntityQueryResults;
import org.kuali.rice.kim.api.identity.external.EntityExternalIdentifier;
import org.kuali.rice.kim.api.identity.external.EntityExternalIdentifierType;
import org.kuali.rice.kim.api.identity.name.EntityName;
import org.kuali.rice.kim.api.identity.personal.EntityBioDemographics;
import org.kuali.rice.kim.api.identity.personal.EntityBioDemographicsContract;
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

public class PersonServiceKimImpl implements PersonService {

    private IdentityService identityService;

    public IdentityService getIdentityService() {
        return identityService;
    }

    public void setIdentityService(IdentityService identityService) {
        this.identityService = identityService;
    }

    @Override
    public PersonInfo getPerson(String personId, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        EntityDefault entityDefault = this.identityService.getEntityDefault(personId);
        if (entityDefault == null) {
            throw new DoesNotExistException(personId);
        }
        PersonInfo info = this._entityDefault2PersonInfo(entityDefault, contextInfo);
        return info;
    }

    private PersonInfo _entityDefault2PersonInfo(EntityDefault entityDefault, ContextInfo contextInfo) {
        PersonInfo info = new PersonInfo();
        info.setId(entityDefault.getEntityId());
        info.setTypeKey(PersonServiceConstants.PERSON_TYPE_KIM_BACKED_KEY);
        if (entityDefault.isActive()) {
            info.setStateKey(PersonServiceConstants.PERSON_STATE_ACTIVE_KEY);
        } else {
            info.setStateKey(PersonServiceConstants.PERSON_STATE_INACTIVE_KEY);
        }

        MetaInfo metaInfo = new MetaInfo();
        if (entityDefault.getName() == null) {
            info.setName("*** no default name  ***");
            metaInfo.setVersionInd("0");
        } else {
            info.setName(entityDefault.getName().getCompositeName());
            metaInfo.setVersionInd(entityDefault.getName().getVersionNumber() + "");
            if (entityDefault.getName().getNameChangedDate() != null) {
                metaInfo.setUpdateTime(entityDefault.getName().getNameChangedDate().toDate());
            }
        }
        info.setMeta(metaInfo);
        return info;
    }

    private PersonInfo _entity2PersonInfo(Entity entity, ContextInfo contextInfo) {
        PersonInfo info = new PersonInfo();
        info.setId(entity.getId());
        info.setTypeKey(PersonServiceConstants.PERSON_TYPE_KIM_BACKED_KEY);
        if (entity.isActive()) {
            info.setStateKey(PersonServiceConstants.PERSON_STATE_ACTIVE_KEY);
        } else {
            info.setStateKey(PersonServiceConstants.PERSON_STATE_INACTIVE_KEY);
        }
        if (entity.getDefaultName() == null) {
            info.setName("*** no default name ****");
        } else {
            info.setName(entity.getDefaultName().getCompositeName());
        }
        return info;
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
        if (!personTypeKey.equals(PersonServiceConstants.PERSON_TYPE_KIM_BACKED_KEY)) {
            return Collections.EMPTY_LIST;
        }
        List<String> list = new ArrayList<String>();
        QueryByCriteria criteria = null;
        EntityDefaultQueryResults results = this.identityService.findEntityDefaults(criteria);
        for (EntityDefault entityDefault : results.getResults()) {
            list.add(entityDefault.getEntityId());
        }
        return list;
    }

    @Override
    public List<String> searchForPersonIds(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // this should really take in criteria couched in terms of person service and then translate
        // that criteria into a critiera couched into terms of the EntityDefault
        List<String> list = new ArrayList<String>();
        EntityDefaultQueryResults results = this.identityService.findEntityDefaults(criteria);
        for (EntityDefault entityDefault : results.getResults()) {
            list.add(entityDefault.getEntityId());
        }
        return list;
    }

    @Override
    public List<PersonInfo> searchForPersons(QueryByCriteria criteria, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // this should really take in criteria couched in terms of person service and then translate
        // that criteria into a critiera couched into terms of the EntityDefault
        List<PersonInfo> list = new ArrayList<PersonInfo>();
        EntityDefaultQueryResults results = this.identityService.findEntityDefaults(criteria);
        for (EntityDefault entityDefault : results.getResults()) {
            PersonInfo info = this._entityDefault2PersonInfo(entityDefault, contextInfo);
            list.add(info);
        }
        return list;
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
        if (!personTypeKey.equals(PersonServiceConstants.PERSON_TYPE_KIM_BACKED_KEY)) {
            throw new InvalidParameterException("Only people with types of "
                    + PersonServiceConstants.PERSON_TYPE_KIM_BACKED_KEY
                    + " are supported in this implementation");
        }
        if (personInfo.getDescr() != null) {
            throw new InvalidParameterException("storing a description of a person is not supported in this implementation");
        }

        Entity.Builder bldr = Entity.Builder.create();
        this._personInfo2Entity(personInfo, contextInfo, bldr);
        Entity entity = bldr.build();
        Entity created = this.identityService.createEntity(entity);
        PersonInfo info = this._entity2PersonInfo(created, contextInfo);
        return info;
    }

    private void _personInfo2Entity(PersonInfo info, ContextInfo contextInfo, Entity.Builder bldr) {
        EntityName.Builder nameBldr = EntityName.Builder.create();
        bldr.setId(info.getId());
        CodedAttribute nameType = this.identityService.getNameType(KimConstants.NameTypes.PRIMARY);
        nameBldr.setNameType(CodedAttribute.Builder.create(nameType));
        nameBldr.setCompositeName(info.getName());
        NameParser parser = new NameParser();
        String[] parts = parser.parseName(info.getName());
        nameBldr.setNameTitle(parts[NameParser.TITLE]);
        nameBldr.setFirstName(parts[NameParser.FIRST_NAME]);
        nameBldr.setMiddleName(parts[NameParser.MIDDLE_NAME]);
        nameBldr.setLastName(parts[NameParser.LAST_NAME]);
        nameBldr.setNameSuffix(parts[NameParser.SUFFIX]);
        nameBldr.setFirstName(parts[NameParser.FIRST_NAME]);
        nameBldr.setActive(true);
        nameBldr.setDefaultValue(true);
        bldr.setNames(Arrays.asList(nameBldr));
        if (info.getStateKey().equals(PersonServiceConstants.PERSON_STATE_ACTIVE_KEY)) {
            bldr.setActive(true);
        } else {
            bldr.setActive(false);
        }
        if (info.getMeta() != null) {
            bldr.setVersionNumber(Long.parseLong(info.getMeta().getVersionInd()));
        }
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
        if (!personInfo.getTypeKey().equals(PersonServiceConstants.PERSON_TYPE_KIM_BACKED_KEY)) {
            throw new InvalidParameterException("Only people with types of "
                    + PersonServiceConstants.PERSON_TYPE_KIM_BACKED_KEY
                    + " are supported in this implementation");
        }
        PersonInfo old = this.getPerson(personInfo.getId(), contextInfo);
        if (!old.getMeta().getVersionInd().equals(personInfo.getMeta().getVersionInd())) {
            throw new VersionMismatchException(old.getMeta().getVersionInd());
        }
        Entity entity = this.identityService.getEntity(personId);

        Entity.Builder bldr = Entity.Builder.create(entity);
        this._personInfo2Entity(personInfo, contextInfo, bldr);
        entity = bldr.build();
        Entity updated = this.identityService.updateEntity(entity);
        PersonInfo info = this._entity2PersonInfo(updated, contextInfo);
        return info;
    }

    @Override
    public StatusInfo deletePerson(String personId, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        PersonInfo info = this.getPerson(personId, contextInfo);
        throw new OperationFailedException(
                "This implementation does not support deleting entities.  Consider chaging the state to inactive instead");
    }

    @Override
    public PersonNameInfo getPersonName(String personNameId, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        List<Predicate> predicates = new ArrayList<Predicate>();
        predicates.add(PredicateFactory.equal("names.id", personNameId));
        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(predicates.toArray(new Predicate[predicates.size()]));
        QueryByCriteria qbc = qbcBuilder.build();
        EntityQueryResults results = this.identityService.findEntities(qbc);
        if (results.getResults().isEmpty()) {
            throw new DoesNotExistException(personNameId);
        }
        if (results.getResults().size() > 1) {
            throw new OperationFailedException("Too many results match " + personNameId);
        }
        for (Entity entity : results.getResults()) {
            for (EntityName entityName : entity.getNames()) {
                if (entityName.getId().equals(personNameId)) {
                    PersonNameInfo info = this._nameEntity2PersonNameInfo(entityName, contextInfo);
                    return info;
                }
            }
        }
        throw new OperationFailedException("person name entity not found despite finding entity " + personNameId);
    }

    private PersonNameInfo _nameEntity2PersonNameInfo(EntityName entityName, ContextInfo contextInfo) {
        PersonNameInfo info = new PersonNameInfo();
        info.setId(entityName.getId());
        info.setPersonId(entityName.getEntityId());
        info.setTypeKey(PersonServiceConstants.PERSON_NAME_TYPE_PREFIX + entityName.getNameType().getCode().toLowerCase());
        if (entityName.isActive()) {
            info.setStateKey(PersonServiceConstants.PERSON_NAME_STATE_ACTIVE_KEY);
        } else {
            info.setStateKey(PersonServiceConstants.PERSON_NAME_STATE_INACTIVE_KEY);
        }
        info.setNameTitle(entityName.getNameTitle());
        info.setNamePrefix(entityName.getNamePrefix());
        info.setFirstName(entityName.getFirstName());
        info.setMiddleName(entityName.getMiddleName());
        info.setLastName(entityName.getLastName());
        info.setNameSuffix(entityName.getNameSuffix());
        info.setCompositeName(entityName.getCompositeName());
        if (entityName.getNameChangedDate() != null) {
            info.setNameChangedDate(entityName.getNameChangedDate().toDate());
        }
        MetaInfo meta = new MetaInfo();
        meta.setVersionInd(entityName.getVersionNumber() + "");
        info.setMeta(meta);
        return info;
    }

    private void _personNameInfo2NameEntity(PersonNameInfo info, ContextInfo contextInfo, EntityName.Builder bldr) {
        bldr.setId(info.getId());
        bldr.setEntityId(info.getPersonId());
        bldr.setNamePrefix(info.getNamePrefix());
        bldr.setFirstName(info.getFirstName());
        bldr.setMiddleName(info.getMiddleName());
        bldr.setLastName(info.getLastName());
        bldr.setNameSuffix(info.getNameSuffix());
        bldr.setCompositeName(info.getCompositeName());
        String kimType = info.getTypeKey().substring(PersonServiceConstants.PERSON_NAME_TYPE_PREFIX.length());
        CodedAttribute nameType = this.identityService.getNameType(kimType);
        bldr.setNameType(CodedAttribute.Builder.create(nameType));
        if (info.getStateKey().equals(PersonServiceConstants.PERSON_NAME_STATE_ACTIVE_KEY)) {
            bldr.setActive(true);
        } else {
            bldr.setActive(false);
        }
        bldr.setVersionNumber(Long.parseLong(info.getMeta().getVersionInd()));
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
        List<Predicate> predicates = new ArrayList<Predicate>();
        String kimType = personNameTypeKey.substring(PersonServiceConstants.PERSON_NAME_TYPE_PREFIX.length());
        predicates.add(PredicateFactory.equal("names.type.code", kimType));
        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(predicates.toArray(new Predicate[predicates.size()]));
        QueryByCriteria qbc = qbcBuilder.build();
        EntityQueryResults results = this.identityService.findEntities(qbc);
        for (Entity entity : results.getResults()) {
            for (EntityName entityName : entity.getNames()) {
                if (entityName.getNameType().getCode().equals(kimType)) {
                    list.add(entityName.getId());
                }
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
        throw new OperationFailedException("searchForPersonNames has not been implemented");
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
        EntityName.Builder bldr = EntityName.Builder.create();
        this._personNameInfo2NameEntity(personNameInfo, contextInfo, bldr);
        EntityName created = this.identityService.addNameToEntity(bldr.build());
        PersonNameInfo createdInfo = this._nameEntity2PersonNameInfo(created, contextInfo);
        return createdInfo;
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

        List<Predicate> predicates = new ArrayList<Predicate>();
        predicates.add(PredicateFactory.equal("names.id", personNameId));
        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(predicates.toArray(new Predicate[predicates.size()]));
        QueryByCriteria qbc = qbcBuilder.build();
        EntityQueryResults results = this.identityService.findEntities(qbc);
        if (results.getResults().isEmpty()) {
            throw new DoesNotExistException(personNameId);
        }
        if (results.getResults().size() > 1) {
            throw new OperationFailedException("Too many results match " + personNameId);
        }
        for (Entity entity : results.getResults()) {
            for (EntityName entityName : entity.getNames()) {
                if (entityName.getId().equals(personNameId)) {
                    PersonNameInfo original = this._nameEntity2PersonNameInfo(entityName, contextInfo);
                    EntityName.Builder bldr = EntityName.Builder.create(entityName);
                    this._personNameInfo2NameEntity(original, contextInfo, bldr);
                    entityName = identityService.updateName(entityName);
                    PersonNameInfo info = this._nameEntity2PersonNameInfo(entityName, contextInfo);
                    return info;
                }
            }
        }
        throw new OperationFailedException("person name entity not found despite finding entity " + personNameId);
    }

    @Override
    public StatusInfo deletePersonName(String personNameId, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // DELETE
        PersonNameInfo info = this.getPersonName(personNameId, contextInfo);
        throw new OperationFailedException(
                "This implementation does not support deleting names.  Consider chaging the state to inactive instead");
    }

    @Override
    public List<PersonNameInfo> getPersonNamesByPerson(String personId, ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        Entity entity = this.identityService.getEntity(personId);
        if (entity == null) {
            return Collections.EMPTY_LIST;
        }
        // GET_INFOS_BY_OTHER
        List<PersonNameInfo> list = new ArrayList<PersonNameInfo>();
        for (EntityName entityName : entity.getNames()) {
            PersonNameInfo info = this._nameEntity2PersonNameInfo(entityName, contextInfo);
            list.add(info);
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
        List<Predicate> predicates = new ArrayList<Predicate>();
        predicates.add(PredicateFactory.equal("externalIdentifiers.id", personIdentifierId));
        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(predicates.toArray(new Predicate[predicates.size()]));
        QueryByCriteria qbc = qbcBuilder.build();
        EntityQueryResults results = this.identityService.findEntities(qbc);
        if (results.getResults().isEmpty()) {
            throw new DoesNotExistException(personIdentifierId);
        }
        if (results.getResults().size() > 1) {
            throw new OperationFailedException("Too many results match " + personIdentifierId);
        }
        for (Entity entity : results.getResults()) {
            for (EntityExternalIdentifier entityExternalIdentifier : entity.getExternalIdentifiers()) {
                if (entityExternalIdentifier.getId().equals(personIdentifierId)) {
                    PersonIdentifierInfo info = this._entityExternalIdentifier2PersonIdentifierInfo(entityExternalIdentifier,
                            contextInfo);
                    return info;
                }
            }
        }
        throw new OperationFailedException("external identifier entity not found despite finding entity " + personIdentifierId);
    }

    private PersonIdentifierInfo _entityExternalIdentifier2PersonIdentifierInfo(EntityExternalIdentifier entityExternalIdentifier,
            ContextInfo contextInfo) {
        PersonIdentifierInfo info = new PersonIdentifierInfo();
        info.setId(entityExternalIdentifier.getId());
        info.setPersonId(entityExternalIdentifier.getEntityId());
        info.setTypeKey(PersonServiceConstants.PERSON_IDENTIFIER_TYPE_PREFIX + entityExternalIdentifier.
                getExternalIdentifierType().getCode().toLowerCase());
        info.setStateKey(PersonServiceConstants.PERSON_IDENTIFIER_STATE_ACTIVE_KEY);
        info.setIdentifier(entityExternalIdentifier.getExternalId());
        MetaInfo meta = new MetaInfo();
        meta.setVersionInd(entityExternalIdentifier.getVersionNumber() + "");
        info.setMeta(meta);
        return info;
    }

    private void _personIdentifierInfo2EntityExternalIdentifier(PersonIdentifierInfo info, ContextInfo contextInfo,
            EntityExternalIdentifier.Builder bldr) {
        bldr.setId(info.getId());
        bldr.setEntityId(info.getPersonId());
        bldr.setExternalId(info.getIdentifier());
        String kimType = info.getTypeKey().substring(PersonServiceConstants.PERSON_IDENTIFIER_TYPE_PREFIX.length());
        EntityExternalIdentifierType nameType = this.identityService.getExternalIdentifierType(kimType);
        bldr.setExternalIdentifierType(EntityExternalIdentifierType.Builder.create(nameType));
        bldr.setVersionNumber(Long.parseLong(info.getMeta().getVersionInd()));
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
        List<Predicate> predicates = new ArrayList<Predicate>();
        String kimType = personIdentifierTypeKey.substring(PersonServiceConstants.PERSON_IDENTIFIER_TYPE_PREFIX.length());
        predicates.add(PredicateFactory.equal("externalIdentifiers.externalIdentifierTypeCode", kimType));
        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(predicates.toArray(new Predicate[predicates.size()]));
        QueryByCriteria qbc = qbcBuilder.build();
        EntityQueryResults results = this.identityService.findEntities(qbc);
        for (Entity entity : results.getResults()) {
            for (EntityName entityName : entity.getNames()) {
                if (entityName.getNameType().getCode().equals(kimType)) {
                    list.add(entityName.getId());
                }
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
        EntityExternalIdentifier.Builder bldr = EntityExternalIdentifier.Builder.create();
        this._personIdentifierInfo2EntityExternalIdentifier(personIdentifierInfo, contextInfo, bldr);
        EntityExternalIdentifier created = this.identityService.addExternalIdentifierToEntity(bldr.build());
        PersonIdentifierInfo createdInfo = this._entityExternalIdentifier2PersonIdentifierInfo(created, contextInfo);
        return createdInfo;
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
        List<Predicate> predicates = new ArrayList<Predicate>();
        predicates.add(PredicateFactory.equal("externalIdentifiers.id", personIdentifierId));
        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(predicates.toArray(new Predicate[predicates.size()]));
        QueryByCriteria qbc = qbcBuilder.build();
        EntityQueryResults results = this.identityService.findEntities(qbc);
        if (results.getResults().isEmpty()) {
            throw new DoesNotExistException(personIdentifierId);
        }
        if (results.getResults().size() > 1) {
            throw new OperationFailedException("Too many results match " + personIdentifierId);
        }
        for (Entity entity : results.getResults()) {
            for (EntityExternalIdentifier entityExternalIdentifier : entity.getExternalIdentifiers()) {
                if (entityExternalIdentifier.getId().equals(personIdentifierId)) {
                    PersonIdentifierInfo original = this._entityExternalIdentifier2PersonIdentifierInfo(entityExternalIdentifier,
                            contextInfo);
                    EntityExternalIdentifier.Builder bldr = EntityExternalIdentifier.Builder.create(entityExternalIdentifier);
                    this._personIdentifierInfo2EntityExternalIdentifier(original, contextInfo, bldr);
                    entityExternalIdentifier = identityService.updateExternalIdentifier(entityExternalIdentifier);
                    PersonIdentifierInfo info = this._entityExternalIdentifier2PersonIdentifierInfo(entityExternalIdentifier,
                            contextInfo);
                    return info;
                }
            }
        }
        throw new OperationFailedException("person name entity not found despite finding entity " + personIdentifierId);
    }

    @Override
    public StatusInfo deletePersonIdentifier(String personIdentifierId, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // DELETE
        PersonIdentifierInfo info = this.getPersonIdentifier(personIdentifierId, contextInfo);
        throw new OperationFailedException(
                "This implementation does not support deleting external identifiers.");
    }

    @Override
    public List<PersonIdentifierInfo> getPersonIdentifiersByPerson(String personId, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        Entity entity = this.identityService.getEntity(personId);
        if (entity == null) {
            return Collections.EMPTY_LIST;
        }
        // GET_INFOS_BY_OTHER
        List<PersonIdentifierInfo> list = new ArrayList<PersonIdentifierInfo>();
        for (EntityExternalIdentifier entityExternalIdentifiers : entity.getExternalIdentifiers()) {
            PersonIdentifierInfo info = this.
                    _entityExternalIdentifier2PersonIdentifierInfo(entityExternalIdentifiers, contextInfo);
            list.add(info);
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
        List<Predicate> predicates = new ArrayList<Predicate>();
        String kimType = personIdentifierTypeKey.substring(PersonServiceConstants.PERSON_IDENTIFIER_TYPE_PREFIX.length());
        predicates.add(PredicateFactory.equal("externalIdentifiers.externalIdentifierTypeCode", kimType));
        predicates.add(PredicateFactory.equal("externalIdentifiers.externalId", identifier));
        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(predicates.toArray(new Predicate[predicates.size()]));
        QueryByCriteria qbc = qbcBuilder.build();
        EntityQueryResults results = this.identityService.findEntities(qbc);
        for (Entity entity : results.getResults()) {
            for (EntityExternalIdentifier entityExternalIdentifier : entity.getExternalIdentifiers()) {
                if (entityExternalIdentifier.getExternalIdentifierTypeCode().equals(kimType)) {
                    if (entityExternalIdentifier.getExternalId().equals(identifier)) {
                        PersonIdentifierInfo info = this._entityExternalIdentifier2PersonIdentifierInfo(entityExternalIdentifier,
                                contextInfo);
                        list.add(info);
                    }
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
        List<Predicate> predicates = new ArrayList<Predicate>();
        predicates.add(PredicateFactory.equal("bioDemographics.objectId", personBioDemographicsId));
        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(predicates.toArray(new Predicate[predicates.size()]));
        QueryByCriteria qbc = qbcBuilder.build();
        EntityQueryResults results = this.identityService.findEntities(qbc);
        if (results.getResults().isEmpty()) {
            throw new DoesNotExistException(personBioDemographicsId);
        }
        if (results.getResults().size() > 1) {
            throw new OperationFailedException("Too many results match " + personBioDemographicsId);
        }
        for (Entity entity : results.getResults()) {
            EntityBioDemographics entityBioDemographics = entity.getBioDemographics();
            if (entityBioDemographics.getObjectId().equals(personBioDemographicsId)) {
                PersonBioDemographicsInfo info = this._entityBioDemographics2PersonBioDemographicsInfo(entityBioDemographics,
                        contextInfo);
                return info;
            }
        }
        throw new OperationFailedException(
                "person bio demographics entity not found despite finding entity " + personBioDemographicsId);
    }

    private PersonBioDemographicsInfo _entityBioDemographics2PersonBioDemographicsInfo(EntityBioDemographics entityBioDemographics,
            ContextInfo contextInfo) {
        PersonBioDemographicsInfo info = new PersonBioDemographicsInfo();
        info.setId(entityBioDemographics.getObjectId());
        info.setPersonId(entityBioDemographics.getEntityId());
        info.setTypeKey(PersonServiceConstants.PERSON_BIO_DEMOGRAPHICS_TYPE_KEY);
        info.setStateKey(PersonServiceConstants.PERSON_BIO_DEMOGRAPHICS_STATE_ACTIVE_KEY);
        Date birthDate = null;
        try {
            birthDate = DateUtils.parseDate(entityBioDemographics.getDeceasedDate(),
                    new String[]{EntityBioDemographicsContract.BIRTH_DATE_FORMAT});
        } catch (ParseException e) {
            throw new IllegalArgumentException("Failed to parse birth date", e);

        }
        info.setBirthDate(birthDate);
        Date deceasedDate = null;
        try {
            deceasedDate = DateUtils.parseDate(entityBioDemographics.getDeceasedDate(),
                    new String[]{EntityBioDemographicsContract.DECEASED_DATE_FORMAT});
        } catch (ParseException e) {
            throw new IllegalArgumentException("Failed to parse deceased date", e);
        }
        info.setDeceasedDate(deceasedDate);
        info.setGenderCode(entityBioDemographics.getGenderCode());
        MetaInfo meta = new MetaInfo();
        meta.setVersionInd(entityBioDemographics.getVersionNumber() + "");
        info.setMeta(meta);
        return info;
    }

    private void _personBioDemographicsInfo2EntityBioDemographics(PersonBioDemographicsInfo info, ContextInfo contextInfo,
            EntityBioDemographics.Builder bldr) {
        bldr.setObjectId(info.getId());
        bldr.setEntityId(info.getPersonId());
        bldr.setBirthDate(info.getBirthDate());
        bldr.setDeceasedDate(info.getDeceasedDate());
        bldr.setGenderCode(info.getGenderCode());
        bldr.setVersionNumber(Long.parseLong(info.getMeta().getVersionInd()));
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
        List<Predicate> predicates = new ArrayList<Predicate>();
        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(predicates.toArray(new Predicate[predicates.size()]));
        QueryByCriteria qbc = qbcBuilder.build();
        EntityQueryResults results = this.identityService.findEntities(qbc);
        for (Entity entity : results.getResults()) {
            for (EntityName entityName : entity.getNames()) {
                list.add(entityName.getId());
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
        if (!personIdentifierTypeKey.equals(PersonServiceConstants.PERSON_BIO_DEMOGRAPHICS_TYPE_KEY)) {
            throw new InvalidParameterException(
                    "Only type=" + PersonServiceConstants.PERSON_BIO_DEMOGRAPHICS_TYPE_KEY + " is supported");
        }
        EntityBioDemographics.Builder bldr = EntityBioDemographics.Builder.create(personId, personBioDemographicsInfo.
                getGenderCode());
        this._personBioDemographicsInfo2EntityBioDemographics(personBioDemographicsInfo, contextInfo, bldr);
        EntityBioDemographics created = this.identityService.addBioDemographicsToEntity(bldr.build());
        PersonBioDemographicsInfo createdInfo = this._entityBioDemographics2PersonBioDemographicsInfo(created, contextInfo);
        return createdInfo;
    }

    @Override
    public PersonBioDemographicsInfo updatePersonBioDemographics(String personBioDemographicsId,
            PersonBioDemographicsInfo personBioDemographicInfo, ContextInfo contextInfo)
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException,
            VersionMismatchException {
        // UPDATE
        if (!personBioDemographicsId.equals(personBioDemographicInfo.getId())) {
            throw new InvalidParameterException("The id parameter does not match the id on the info object");
        }
        List<Predicate> predicates = new ArrayList<Predicate>();
        predicates.add(PredicateFactory.equal("bioDemographics.objectId", personBioDemographicsId));
        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(predicates.toArray(new Predicate[predicates.size()]));
        QueryByCriteria qbc = qbcBuilder.build();
        EntityQueryResults results = this.identityService.findEntities(qbc);
        if (results.getResults().isEmpty()) {
            throw new DoesNotExistException(personBioDemographicsId);
        }
        if (results.getResults().size() > 1) {
            throw new OperationFailedException("Too many results match " + personBioDemographicsId);
        }
        for (Entity entity : results.getResults()) {
            EntityBioDemographics entityBioDemographics = entity.getBioDemographics();
            if (entityBioDemographics.getObjectId().equals(personBioDemographicsId)) {
                EntityBioDemographics.Builder bldr = EntityBioDemographics.Builder.create(entityBioDemographics);
                this._personBioDemographicsInfo2EntityBioDemographics(personBioDemographicInfo, contextInfo, bldr);
                EntityBioDemographics updated = this.identityService.updateBioDemographics(bldr.build());
                PersonBioDemographicsInfo updatedInfo = this.
                        _entityBioDemographics2PersonBioDemographicsInfo(updated, contextInfo);
                return updatedInfo;
            }
        }
        throw new OperationFailedException(
                "person bio demographics entity not found despite finding entity " + personBioDemographicsId);
    }

    @Override
    public StatusInfo deletePersonBioDemographics(String personBioDemographicsId, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // DELETE
        throw new OperationFailedException("delete of bio demographics is not supported at this time");
    }

    @Override
    public PersonBioDemographicsInfo getPersonBioDemographicsByPerson(String personId, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // GET_BY_ID
        Entity entity = this.identityService.getEntity(personId);
        if (entity == null) {
            throw new DoesNotExistException(personId);
        }
        EntityBioDemographics entityBioDemographics = entity.getBioDemographics();
        if (entityBioDemographics == null) {
            throw new DoesNotExistException (personId);
        }
        PersonBioDemographicsInfo info = this._entityBioDemographics2PersonBioDemographicsInfo(entityBioDemographics,
                contextInfo);
        return info;
    }

    @Override
    public PersonAffiliationInfo getPersonAffiliation(String personAffiliationId, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // GET_BY_ID
        List<Predicate> predicates = new ArrayList<Predicate>();
        predicates.add(PredicateFactory.equal("affiliations.id", personAffiliationId));
        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(predicates.toArray(new Predicate[predicates.size()]));
        QueryByCriteria qbc = qbcBuilder.build();
        EntityQueryResults results = this.identityService.findEntities(qbc);
        if (results.getResults().isEmpty()) {
            throw new DoesNotExistException(personAffiliationId);
        }
        if (results.getResults().size() > 1) {
            throw new OperationFailedException("Too many results match " + personAffiliationId);
        }
        for (Entity entity : results.getResults()) {
            for (EntityAffiliation entityAffiliation : entity.getAffiliations()) {
                if (entityAffiliation.getId().equals(personAffiliationId)) {
                    PersonAffiliationInfo info = this._entityAffiliation2PersonAffiliationInfo(entityAffiliation,
                            contextInfo);
                    return info;
                }
            }
        }
        throw new OperationFailedException("external identifier entity not found despite finding entity " + personAffiliationId);
    }

    private PersonAffiliationInfo _entityAffiliation2PersonAffiliationInfo(EntityAffiliation entityAffiliation,
            ContextInfo contextInfo) {
        PersonAffiliationInfo info = new PersonAffiliationInfo();
        info.setId(entityAffiliation.getId());
        info.setPersonId(entityAffiliation.getEntityId());
        info.setTypeKey(PersonServiceConstants.PERSON_AFFILIATION_TYPE_PREFIX + entityAffiliation.
                getAffiliationType().getCode().toLowerCase());
        if (entityAffiliation.isActive()) {
            info.setStateKey(PersonServiceConstants.PERSON_AFFILIATION_STATE_ACTIVE_KEY);
        } else {
            info.setStateKey(PersonServiceConstants.PERSON_AFFILIATION_STATE_INACTIVE_KEY);
        }
        try {
            info.setOrganizationId(this.getInstitutionalAffiliationOrganizationId(contextInfo));
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex);
        }
        MetaInfo meta = new MetaInfo();
        meta.setVersionInd(entityAffiliation.getVersionNumber() + "");
        info.setMeta(meta);
        return info;
    }

    private void _personAffiliationInfo2EntityAffiliation(PersonAffiliationInfo info, ContextInfo contextInfo,
            EntityAffiliation.Builder bldr) {
        bldr.setId(info.getId());
        bldr.setEntityId(info.getPersonId());
        String kimType = info.getTypeKey().substring(PersonServiceConstants.PERSON_AFFILIATION_TYPE_PREFIX.length());
        EntityAffiliationType nameType = this.identityService.getAffiliationType(kimType);
        bldr.setAffiliationType(EntityAffiliationType.Builder.create(nameType));
        if (info.getStateKey().equals(PersonServiceConstants.PERSON_AFFILIATION_STATE_ACTIVE_KEY)) {
            bldr.setActive(true);
        } else if (info.getStateKey().equals(PersonServiceConstants.PERSON_AFFILIATION_STATE_INACTIVE_KEY)) {
            bldr.setActive(false);
        } else {
            throw new IllegalArgumentException("unknown/unhandled state key " + info.getStateKey());
        }
        bldr.setVersionNumber(Long.parseLong(info.getMeta().getVersionInd()));
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
        List<Predicate> predicates = new ArrayList<Predicate>();
        predicates.add(PredicateFactory.equal("affiliations.affiliationType.code", personAffiliationTypeKey));
        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(predicates.toArray(new Predicate[predicates.size()]));
        QueryByCriteria qbc = qbcBuilder.build();
        EntityQueryResults results = this.identityService.findEntities(qbc);
        if (results.getResults().isEmpty()) {
            return Collections.EMPTY_LIST;
        }

        List<String> list = new ArrayList<String>();
        for (Entity entity : results.getResults()) {
            for (EntityAffiliation entityAffiliation : entity.getAffiliations()) {
                if (entityAffiliation.getAffiliationType().getCode().equals(personAffiliationTypeKey)) {
                    list.add(entityAffiliation.getId());
                }
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
        if (!personId.equals(personAffiliationInfo.getPersonId())) {
            throw new InvalidParameterException("The person Id parameter does not match the person id on the info object");
        }
        if (!organizationId.equals(personAffiliationInfo.getOrganizationId())) {
            throw new InvalidParameterException(
                    "The origanization id parameter does not match the organization id on the info object");
        }
        if (!organizationId.equals(this.getInstitutionalAffiliationOrganizationId(contextInfo))) {
            throw new OperationFailedException("only institutional organization affiliations are supported " + organizationId);
        }
        Entity entity = this.identityService.getEntity(personId);
        if (entity == null) {
            throw new DoesNotExistException(personId);
        }
        EntityAffiliation.Builder bldr = EntityAffiliation.Builder.create();
        this._personAffiliationInfo2EntityAffiliation(personAffiliationInfo, contextInfo, bldr);
        EntityAffiliation entityAffiliation = this.identityService.addAffiliationToEntity(bldr.build());
        PersonAffiliationInfo info = this._entityAffiliation2PersonAffiliationInfo(entityAffiliation, contextInfo);
        return info;
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
        List<Predicate> predicates = new ArrayList<Predicate>();
        predicates.add(PredicateFactory.equal("affiliations.id", personAffiliationId));
        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(predicates.toArray(new Predicate[predicates.size()]));
        QueryByCriteria qbc = qbcBuilder.build();
        EntityQueryResults results = this.identityService.findEntities(qbc);
        if (results.getResults().isEmpty()) {
            throw new DoesNotExistException(personAffiliationId);
        }
        if (results.getResults().size() > 1) {
            throw new OperationFailedException("Too many results match " + personAffiliationId);
        }
        for (Entity entity : results.getResults()) {
            for (EntityAffiliation entityAffiliation : entity.getAffiliations()) {
                if (entityAffiliation.getId().equals(personAffiliationId)) {
                    EntityAffiliation.Builder bldr = EntityAffiliation.Builder.create(entityAffiliation);
                    this._personAffiliationInfo2EntityAffiliation(personAffiliationInfo, contextInfo, bldr);
                    entityAffiliation = this.identityService.updateAffiliation(bldr.build());
                    PersonAffiliationInfo info = this._entityAffiliation2PersonAffiliationInfo(entityAffiliation, contextInfo);
                    return info;
                }
            }
        }
        throw new OperationFailedException("external identifier entity not found despite finding entity " + personAffiliationId);
    }

    @Override
    public StatusInfo deletePersonAffiliation(String personAffiliationId, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        // DELETE
        throw new OperationFailedException("delete is not supported try inactivate");
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
        Entity entity = this.identityService.getEntity(personId);
        if (entity == null) {
            throw new DoesNotExistException(personId);
        }
        for (EntityAffiliation entityAffiliation : entity.getAffiliations()) {
            PersonAffiliationInfo info = this._entityAffiliation2PersonAffiliationInfo(entityAffiliation, contextInfo);
            list.add(info);
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
        // This should be overridden by an implementing institution to point to the org id that points to their institution
        // in the org service
        return "1";
    }

}
