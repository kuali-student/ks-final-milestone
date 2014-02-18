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
package org.kuali.student.core.person.service.decorators;

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

public class PersonServiceDecorator implements PersonService {
    
    private PersonService nextDecorator;

    public PersonService getNextDecorator() {
        return nextDecorator;
    }

    public void setNextDecorator(PersonService nextDecorator) {
        this.nextDecorator = nextDecorator;
    }
    
    

    @Override
    public PersonInfo getPerson(String personId, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().getPerson(personId, contextInfo);
    }

    @Override
    public List<PersonInfo> getPersonsByIds(List<String> personIds, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().getPersonsByIds(personIds, contextInfo);
    }

    @Override
    public List<String> getPersonIdsByType(String personTypeKey, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().getPersonIdsByType(personTypeKey, contextInfo);
    }

    @Override
    public List<String> searchForPersonIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().searchForPersonIds(criteria, contextInfo);
    }

    @Override
    public List<PersonInfo> searchForPersons(QueryByCriteria criteria, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().searchForPersons(criteria, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validatePerson(String validationTypeKey, String personTypeKey, PersonInfo personInfo,
            ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().validatePerson(validationTypeKey, personTypeKey, personInfo, contextInfo);
    }

    @Override
    public PersonInfo createPerson(String personTypeKey, PersonInfo personInfo, ContextInfo contextInfo) throws
            DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException {
        return getNextDecorator().createPerson(personTypeKey, personInfo, contextInfo);
    }

    @Override
    public PersonInfo updatePerson(String personId, PersonInfo personInfo, ContextInfo contextInfo) throws
            DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException,
            VersionMismatchException {
        return getNextDecorator().updatePerson(personId, personInfo, contextInfo);
    }

    @Override
    public StatusInfo deletePerson(String personId, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().deletePerson(personId, contextInfo);
    }

    @Override
    public PersonNameInfo getPersonName(String personNameId, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().getPersonName(personNameId, contextInfo);
    }

    @Override
    public List<PersonNameInfo> getPersonNamesByIds(List<String> personNameIds, ContextInfo contextInfo) throws
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().getPersonNamesByIds(personNameIds, contextInfo);
    }

    @Override
    public List<String> getPersonNameIdsByType(String personNameTypeKey, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().getPersonNameIdsByType(personNameTypeKey, contextInfo);
    }

    @Override
    public List<String> searchForPersonNameIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().searchForPersonNameIds(criteria, contextInfo);
    }

    @Override
    public List<PersonNameInfo> searchForPersonNames(QueryByCriteria criteria, ContextInfo contextInfo) throws
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().searchForPersonNames(criteria, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validatePersonName(String validationTypeKey, String personNameTypeKey, String personId,
            PersonNameInfo personNameInfo, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().validatePersonName(validationTypeKey, personNameTypeKey, personId, personNameInfo, contextInfo);
    }

    @Override
    public PersonNameInfo createPersonName(String personNameTypeKey, String personId, PersonNameInfo personNameInfo,
            ContextInfo contextInfo) throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException {
        return getNextDecorator().createPersonName(personNameTypeKey, personId, personNameInfo, contextInfo);
    }

    @Override
    public PersonNameInfo updatePersonName(String personNameId, PersonNameInfo personNameInfo, ContextInfo contextInfo) throws
            DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException,
            VersionMismatchException {
        return getNextDecorator().updatePersonName(personNameId, personNameInfo, contextInfo);
    }

    @Override
    public StatusInfo deletePersonName(String personNameId, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().deletePersonName(personNameId, contextInfo);
    }

    @Override
    public List<PersonNameInfo> getPersonNamesByPerson(String personId, ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().getPersonNamesByPerson(personId, contextInfo);
    }

    @Override
    public PersonIdentifierInfo getPersonIdentifier(String personIdentifierId, ContextInfo contextInfo) throws
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().getPersonIdentifier(personIdentifierId, contextInfo);
    }

    @Override
    public List<PersonIdentifierInfo> getPersonIdentifiersByIds(List<String> personIdentifierIds, ContextInfo contextInfo) throws
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().getPersonIdentifiersByIds(personIdentifierIds, contextInfo);
    }

    @Override
    public List<String> getPersonIdentifierIdsByType(String personIdentifierTypeKey, ContextInfo contextInfo) throws
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().getPersonIdentifierIdsByType(personIdentifierTypeKey, contextInfo);
    }

    @Override
    public List<String> searchForPersonIdentifierIds(QueryByCriteria criteria, ContextInfo contextInfo) throws
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().searchForPersonIdentifierIds(criteria, contextInfo);
    }

    @Override
    public List<PersonIdentifierInfo> searchForPersonIdentifiers(QueryByCriteria criteria, ContextInfo contextInfo) throws
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().searchForPersonIdentifiers(criteria, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validatePersonIdentifier(String validationTypeKey, String personIdentifierTypeKey,
            String personId, PersonIdentifierInfo personIdentifierInfo, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().validatePersonIdentifier(validationTypeKey, personIdentifierTypeKey, personId, personIdentifierInfo,
                contextInfo);
    }

    @Override
    public PersonIdentifierInfo createPersonIdentifier(String personIdentifierTypeKey, String personId,
            PersonIdentifierInfo personIdentifierInfo, ContextInfo contextInfo) throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException {
        return getNextDecorator().createPersonIdentifier(personIdentifierTypeKey, personId, personIdentifierInfo, contextInfo);
    }

    @Override
    public PersonIdentifierInfo updatePersonIdentifier(String personIdentifierId, PersonIdentifierInfo personIdentifierInfo,
            ContextInfo contextInfo) throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException,
            VersionMismatchException {
        return getNextDecorator().updatePersonIdentifier(personIdentifierId, personIdentifierInfo, contextInfo);
    }

    @Override
    public StatusInfo deletePersonIdentifier(String personIdentifierId, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().deletePersonIdentifier(personIdentifierId, contextInfo);
    }

    @Override
    public List<PersonIdentifierInfo> getPersonIdentifiersByPerson(String personId, ContextInfo contextInfo) throws
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().getPersonIdentifiersByPerson(personId, contextInfo);
    }

    @Override
    public List<PersonIdentifierInfo> getPersonIdentifiersByIdentifier(String personIdentifierTypeKey, String identifier,
            ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().getPersonIdentifiersByIdentifier(personIdentifierTypeKey, identifier, contextInfo);
    }

    @Override
    public PersonBioDemographicsInfo getPersonBioDemographics(String personBioDemographicsId, ContextInfo contextInfo) throws
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().getPersonBioDemographics(personBioDemographicsId, contextInfo);
    }

    @Override
    public List<PersonBioDemographicsInfo> getPersonBioDemographicsByIds(List<String> personBioDemographicsIds,
            ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().getPersonBioDemographicsByIds(personBioDemographicsIds, contextInfo);
    }

    @Override
    public List<String> getPersonBioDemographicsIdsByType(String personIdentifierTypeKey, ContextInfo contextInfo) throws
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().getPersonBioDemographicsIdsByType(personIdentifierTypeKey, contextInfo);
    }

    @Override
    public List<String> searchForPersonBioDemographicsIds(QueryByCriteria criteria, ContextInfo contextInfo) throws
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().searchForPersonBioDemographicsIds(criteria, contextInfo);
    }

    @Override
    public List<PersonBioDemographicsInfo> searchForPersonBioDemographics(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().searchForPersonBioDemographics(criteria, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validatePersonBioDemographics(String validationTypeKey, String personIdentifierTypeKey,
            String personId, PersonBioDemographicsInfo personBioDemographicsInfo, ContextInfo contextInfo) throws
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().validatePersonBioDemographics(validationTypeKey, personIdentifierTypeKey, personId,
                personBioDemographicsInfo, contextInfo);
    }

    @Override
    public PersonBioDemographicsInfo createPersonBioDemographics(String personIdentifierTypeKey, String personId,
            PersonBioDemographicsInfo personBioDemographicsInfo, ContextInfo contextInfo) throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException {
        return getNextDecorator().createPersonBioDemographics(personIdentifierTypeKey, personId, personBioDemographicsInfo, contextInfo);
    }

    @Override
    public PersonBioDemographicsInfo updatePersonBioDemographics(String personBioDemographicsId,
            PersonBioDemographicsInfo personBioDemographicsInfo, ContextInfo contextInfo) throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException,
            VersionMismatchException {
        return getNextDecorator().updatePersonBioDemographics(personBioDemographicsId, personBioDemographicsInfo, contextInfo);
    }

    @Override
    public StatusInfo deletePersonBioDemographics(String personBioDemographicsId, ContextInfo contextInfo) throws
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().deletePersonBioDemographics(personBioDemographicsId, contextInfo);
    }

    @Override
    public PersonBioDemographicsInfo getPersonBioDemographicsByPerson(String personId, ContextInfo contextInfo) throws
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().getPersonBioDemographicsByPerson(personId, contextInfo);
    }

    @Override
    public PersonAffiliationInfo getPersonAffiliation(String personAffiliationId, ContextInfo contextInfo) throws
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().getPersonAffiliation(personAffiliationId, contextInfo);
    }

    @Override
    public List<PersonAffiliationInfo> getPersonAffiliationsByIds(List<String> personAffiliationIds, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().getPersonAffiliationsByIds(personAffiliationIds, contextInfo);
    }

    @Override
    public List<String> getPersonAffiliationIdsByType(String personAffiliationTypeKey, ContextInfo contextInfo) throws
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().getPersonAffiliationIdsByType(personAffiliationTypeKey, contextInfo);
    }

    @Override
    public List<String> searchForPersonAffiliationIds(QueryByCriteria criteria, ContextInfo contextInfo) throws
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().searchForPersonAffiliationIds(criteria, contextInfo);
    }

    @Override
    public List<PersonAffiliationInfo> searchForPersonAffiliations(QueryByCriteria criteria, ContextInfo contextInfo) throws
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().searchForPersonAffiliations(criteria, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validatePersonAffiliation(String validationTypeKey, String personAffiliationTypeKey,
            String personId, String organizationId, PersonAffiliationInfo personAffiliationInfo, ContextInfo contextInfo) throws
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().validatePersonAffiliation(validationTypeKey, personAffiliationTypeKey, personId, organizationId,
                personAffiliationInfo, contextInfo);
    }

    @Override
    public PersonAffiliationInfo createPersonAffiliation(String personAffiliationTypeKey, String personId, String organizationId,
            PersonAffiliationInfo personAffiliationInfo, ContextInfo contextInfo) throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException {
        return getNextDecorator().createPersonAffiliation(personAffiliationTypeKey, personId, organizationId, personAffiliationInfo,
                contextInfo);
    }

    @Override
    public PersonAffiliationInfo updatePersonAffiliation(String personAffiliationId, PersonAffiliationInfo personAffiliationInfo,
            ContextInfo contextInfo) throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException,
            VersionMismatchException {
        return getNextDecorator().updatePersonAffiliation(personAffiliationId, personAffiliationInfo, contextInfo);
    }

    @Override
    public StatusInfo deletePersonAffiliation(String personAffiliationId, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().deletePersonAffiliation(personAffiliationId, contextInfo);
    }

    @Override
    public List<PersonAffiliationInfo> getPersonAffiliationsByPerson(String personId, ContextInfo contextInfo) throws
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().getPersonAffiliationsByPerson(personId, contextInfo);
    }

    @Override
    public List<PersonInfo> getActivePeopleMatchingNameFragmentAndAffiliation(String nameFragment, String personAffiliationTypeKey,
            String organizationId, ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().getActivePeopleMatchingNameFragmentAndAffiliation(nameFragment, personAffiliationTypeKey,
                organizationId, contextInfo);
    }

    @Override
    public String getInstitutionalAffiliationOrganizationId(ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return getNextDecorator().getInstitutionalAffiliationOrganizationId(contextInfo);
    }
    

}
