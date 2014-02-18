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

import java.util.Date;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.core.person.dto.PersonAffiliationInfo;
import org.kuali.student.core.person.dto.PersonBioDemographicsInfo;
import org.kuali.student.core.person.dto.PersonIdentifierInfo;
import org.kuali.student.core.person.dto.PersonInfo;
import org.kuali.student.core.person.dto.PersonNameInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.junit.Assert.assertEquals;
import org.kuali.student.common.test.util.RichTextTester;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:person-test-with-map-context.xml"})
public class TestPersonServiceImplConformanceExtendedCrud extends TestPersonServiceImplConformanceBaseCrud {

	// ========================================
    // DTO FIELD SPECIFIC METHODS
    // ========================================
    // ****************************************************
    //           PersonInfo
    // ****************************************************
    /*
     A method to set the fields for a Person in a 'test create' section prior to calling the 'create' operation.
     */
    public void testCrudPerson_setDTOFieldsForTestCreate(PersonInfo expected) {
        expected.setTypeKey("typeKey01");
        expected.setStateKey("stateKey01");
        expected.setName("name01");
        expected.setDescr(RichTextHelper.buildRichTextInfo("descr01", "descr01"));
    }

    /*
     A method to test the fields for a Person. This is called after:
     - creating a DTO, where actual is the DTO returned by the create operation, and expected is the dto passed in to the create operation
     - reading a DTO after creating it, and actual is the read DTO, and expected is the dto that was created
     - updating a DTO, where actual is DTO returned by the update operation, and expected is the dto that was passed in to the update operation
     */
    public void testCrudPerson_testDTOFieldsForTestCreateUpdate(PersonInfo expected, PersonInfo actual) {
        assertEquals(expected.getTypeKey(), actual.getTypeKey());
        assertEquals(expected.getStateKey(), actual.getStateKey());
        assertEquals(expected.getName(), actual.getName());
        new RichTextTester().check(expected.getDescr(), actual.getDescr());
    }

    /*
     A method to set the fields for a Person in a 'test update' section prior to calling the 'update' operation.
     */
    public void testCrudPerson_setDTOFieldsForTestUpdate(PersonInfo expected) {
        expected.setTypeKey("typeKey_Updated");
        expected.setStateKey("stateKey_Updated");
        expected.setName("name_Updated");
        expected.setDescr(RichTextHelper.buildRichTextInfo("descr_Updated", "descr_Updated"));
    }

    /*
     A method to test the fields for a Person after an update operation, followed by a read operation,
     where actual is the DTO returned by the read operation, and expected is the dto returned by the update operation.
     */
    public void testCrudPerson_testDTOFieldsForTestReadAfterUpdate(PersonInfo expected, PersonInfo actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getTypeKey(), actual.getTypeKey());
        assertEquals(expected.getStateKey(), actual.getStateKey());
        assertEquals(expected.getName(), actual.getName());
        new RichTextTester().check(expected.getDescr(), actual.getDescr());
    }

    /*
     A method to set the fields for a Person in the 'test read after update' section.
     This dto is another (second) dto object being created for other tests.
     */
    public void testCrudPerson_setDTOFieldsForTestReadAfterUpdate(PersonInfo expected) {
        expected.setName("name_Updated");
    }

    // ****************************************************
    //           PersonNameInfo
    // ****************************************************
    /*
     A method to set the fields for a PersonName in a 'test create' section prior to calling the 'create' operation.
     */
    public void testCrudPersonName_setDTOFieldsForTestCreate(PersonNameInfo expected) {
        expected.setTypeKey("typeKey01");
        expected.setStateKey("stateKey01");
        expected.setPersonId("personId01");
        expected.setNamePrefix("namePrefix01");
        expected.setNameTitle("nameTitle01");
        expected.setFirstName("firstName01");
        expected.setMiddleName("middleName01");
        expected.setLastName("lastName01");
        expected.setNameSuffix("nameSuffix01");
        expected.setNameChangedDate(new Date());
    }

    /*
     A method to test the fields for a PersonName. This is called after:
     - creating a DTO, where actual is the DTO returned by the create operation, and expected is the dto passed in to the create operation
     - reading a DTO after creating it, and actual is the read DTO, and expected is the dto that was created
     - updating a DTO, where actual is DTO returned by the update operation, and expected is the dto that was passed in to the update operation
     */
    public void testCrudPersonName_testDTOFieldsForTestCreateUpdate(PersonNameInfo expected, PersonNameInfo actual) {
        assertEquals(expected.getTypeKey(), actual.getTypeKey());
        assertEquals(expected.getStateKey(), actual.getStateKey());
        assertEquals(expected.getPersonId(), actual.getPersonId());
        assertEquals(expected.getNamePrefix(), actual.getNamePrefix());
        assertEquals(expected.getNameTitle(), actual.getNameTitle());
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getMiddleName(), actual.getMiddleName());
        assertEquals(expected.getLastName(), actual.getLastName());
        assertEquals(expected.getNameSuffix(), actual.getNameSuffix());
        assertEquals(expected.getNameChangedDate(), actual.getNameChangedDate());
    }

    /*
     A method to set the fields for a PersonName in a 'test update' section prior to calling the 'update' operation.
     */
    public void testCrudPersonName_setDTOFieldsForTestUpdate(PersonNameInfo expected) {
        expected.setTypeKey("typeKey_Updated");
        expected.setStateKey("stateKey_Updated");
        expected.setPersonId("personId_Updated");
        expected.setNamePrefix("namePrefix_Updated");
        expected.setNameTitle("nameTitle_Updated");
        expected.setFirstName("firstName_Updated");
        expected.setMiddleName("middleName_Updated");
        expected.setLastName("lastName_Updated");
        expected.setNameSuffix("nameSuffix_Updated");
        expected.setNameChangedDate(new Date(System.currentTimeMillis() + 1000));
    }

    /*
     A method to test the fields for a PersonName after an update operation, followed by a read operation,
     where actual is the DTO returned by the read operation, and expected is the dto returned by the update operation.
     */
    public void testCrudPersonName_testDTOFieldsForTestReadAfterUpdate(PersonNameInfo expected, PersonNameInfo actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getTypeKey(), actual.getTypeKey());
        assertEquals(expected.getStateKey(), actual.getStateKey());
        assertEquals(expected.getPersonId(), actual.getPersonId());
        assertEquals(expected.getNamePrefix(), actual.getNamePrefix());
        assertEquals(expected.getNameTitle(), actual.getNameTitle());
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getMiddleName(), actual.getMiddleName());
        assertEquals(expected.getLastName(), actual.getLastName());
        assertEquals(expected.getNameSuffix(), actual.getNameSuffix());
        assertEquals(expected.getNameChangedDate(), actual.getNameChangedDate());
    }

    /*
     A method to set the fields for a PersonName in the 'test read after update' section.
     This dto is another (second) dto object being created for other tests.
     */
    public void testCrudPersonName_setDTOFieldsForTestReadAfterUpdate(PersonNameInfo expected) {
        expected.setPersonId("personId_Updated");
        expected.setNamePrefix("namePrefix_Updated");
        expected.setNameTitle("nameTitle_Updated");
        expected.setFirstName("firstName_Updated");
        expected.setMiddleName("middleName_Updated");
        expected.setLastName("lastName_Updated");
        expected.setNameSuffix("nameSuffix_Updated");
        expected.setNameChangedDate(new Date(System.currentTimeMillis() + 1000));
    }

    // ****************************************************
    //           PersonIdentifierInfo
    // ****************************************************
    /*
     A method to set the fields for a PersonIdentifier in a 'test create' section prior to calling the 'create' operation.
     */
    public void testCrudPersonIdentifier_setDTOFieldsForTestCreate(PersonIdentifierInfo expected) {
        expected.setTypeKey("typeKey01");
        expected.setStateKey("stateKey01");
        expected.setPersonId("personId01");
        expected.setIdentifier("identifier01");
    }

    /*
     A method to test the fields for a PersonIdentifier. This is called after:
     - creating a DTO, where actual is the DTO returned by the create operation, and expected is the dto passed in to the create operation
     - reading a DTO after creating it, and actual is the read DTO, and expected is the dto that was created
     - updating a DTO, where actual is DTO returned by the update operation, and expected is the dto that was passed in to the update operation
     */
    public void testCrudPersonIdentifier_testDTOFieldsForTestCreateUpdate(PersonIdentifierInfo expected,
            PersonIdentifierInfo actual) {
        assertEquals(expected.getTypeKey(), actual.getTypeKey());
        assertEquals(expected.getStateKey(), actual.getStateKey());
        assertEquals(expected.getPersonId(), actual.getPersonId());
        assertEquals(expected.getIdentifier(), actual.getIdentifier());
    }

    /*
     A method to set the fields for a PersonIdentifier in a 'test update' section prior to calling the 'update' operation.
     */
    public void testCrudPersonIdentifier_setDTOFieldsForTestUpdate(PersonIdentifierInfo expected) {
        expected.setTypeKey("typeKey_Updated");
        expected.setStateKey("stateKey_Updated");
        expected.setPersonId("personId_Updated");
        expected.setIdentifier("identifier_Updated");
    }

    /*
     A method to test the fields for a PersonIdentifier after an update operation, followed by a read operation,
     where actual is the DTO returned by the read operation, and expected is the dto returned by the update operation.
     */
    public void testCrudPersonIdentifier_testDTOFieldsForTestReadAfterUpdate(PersonIdentifierInfo expected,
            PersonIdentifierInfo actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getTypeKey(), actual.getTypeKey());
        assertEquals(expected.getStateKey(), actual.getStateKey());
        assertEquals(expected.getPersonId(), actual.getPersonId());
        assertEquals(expected.getIdentifier(), actual.getIdentifier());
    }

    /*
     A method to set the fields for a PersonIdentifier in the 'test read after update' section.
     This dto is another (second) dto object being created for other tests.
     */
    public void testCrudPersonIdentifier_setDTOFieldsForTestReadAfterUpdate(PersonIdentifierInfo expected) {
        expected.setPersonId("personId_Updated");
        expected.setIdentifier("identifier_Updated");
    }

    // ****************************************************
    //           PersonBioDemographicsInfo
    // ****************************************************
    /*
     A method to set the fields for a PersonBioDemographics in a 'test create' section prior to calling the 'create' operation.
     */
    public void testCrudPersonBioDemographics_setDTOFieldsForTestCreate(PersonBioDemographicsInfo expected) {
        expected.setTypeKey("typeKey01");
        expected.setStateKey("stateKey01");
        expected.setPersonId("personId01");
        expected.setDeceasedDate(new Date(System.currentTimeMillis() + 90000l));
        expected.setBirthDate(new Date(System.currentTimeMillis() - 90000l));
        expected.setGenderCode("genderCode01");
    }

    /*
     A method to test the fields for a PersonBioDemographics. This is called after:
     - creating a DTO, where actual is the DTO returned by the create operation, and expected is the dto passed in to the create operation
     - reading a DTO after creating it, and actual is the read DTO, and expected is the dto that was created
     - updating a DTO, where actual is DTO returned by the update operation, and expected is the dto that was passed in to the update operation
     */
    public void testCrudPersonBioDemographics_testDTOFieldsForTestCreateUpdate(PersonBioDemographicsInfo expected,
            PersonBioDemographicsInfo actual) {
        assertEquals(expected.getTypeKey(), actual.getTypeKey());
        assertEquals(expected.getStateKey(), actual.getStateKey());
        assertEquals(expected.getPersonId(), actual.getPersonId());
        expected.setDeceasedDate(new Date(System.currentTimeMillis() + 80000l));
        expected.setBirthDate(new Date(System.currentTimeMillis() - 80000l));
        assertEquals(expected.getGenderCode(), actual.getGenderCode());
    }

    /*
     A method to set the fields for a PersonBioDemographics in a 'test update' section prior to calling the 'update' operation.
     */
    public void testCrudPersonBioDemographics_setDTOFieldsForTestUpdate(PersonBioDemographicsInfo expected) {
        expected.setTypeKey("typeKey_Updated");
        expected.setStateKey("stateKey_Updated");
        expected.setPersonId("personId_Updated");
        expected.setDeceasedDate(new Date(System.currentTimeMillis() + 70000l));
        expected.setBirthDate(new Date(System.currentTimeMillis() - 70000l));
        expected.setGenderCode("genderCode_Updated");
    }

    /*
     A method to test the fields for a PersonBioDemographics after an update operation, followed by a read operation,
     where actual is the DTO returned by the read operation, and expected is the dto returned by the update operation.
     */
    public void testCrudPersonBioDemographics_testDTOFieldsForTestReadAfterUpdate(PersonBioDemographicsInfo expected,
            PersonBioDemographicsInfo actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getTypeKey(), actual.getTypeKey());
        assertEquals(expected.getStateKey(), actual.getStateKey());
        assertEquals(expected.getPersonId(), actual.getPersonId());
        expected.setDeceasedDate(new Date(System.currentTimeMillis() + 70000l));
        expected.setBirthDate(new Date(System.currentTimeMillis() - 70000l));
        assertEquals(expected.getGenderCode(), actual.getGenderCode());
    }

    /*
     A method to set the fields for a PersonBioDemographics in the 'test read after update' section.
     This dto is another (second) dto object being created for other tests.
     */
    public void testCrudPersonBioDemographics_setDTOFieldsForTestReadAfterUpdate(PersonBioDemographicsInfo expected) {
        expected.setPersonId("personId_Updated");
        expected.setDeceasedDate(new Date(System.currentTimeMillis() + 90000l));
        expected.setBirthDate(new Date(System.currentTimeMillis() - 90000l));
        expected.setGenderCode("genderCode_Updated");
    }

    // ****************************************************
    //           PersonAffiliationInfo
    // ****************************************************
    /*
     A method to set the fields for a PersonAffiliation in a 'test create' section prior to calling the 'create' operation.
     */
    public void testCrudPersonAffiliation_setDTOFieldsForTestCreate(PersonAffiliationInfo expected) {
        expected.setTypeKey("typeKey01");
        expected.setStateKey("stateKey01");
        expected.setEffectiveDate(new Date(System.currentTimeMillis() - 90000l));
        expected.setExpirationDate(new Date(System.currentTimeMillis() + 90000l));
        expected.setPersonId("personId01");
        expected.setOrganizationId("organizationId01");
    }

    /*
     A method to test the fields for a PersonAffiliation. This is called after:
     - creating a DTO, where actual is the DTO returned by the create operation, and expected is the dto passed in to the create operation
     - reading a DTO after creating it, and actual is the read DTO, and expected is the dto that was created
     - updating a DTO, where actual is DTO returned by the update operation, and expected is the dto that was passed in to the update operation
     */
    public void testCrudPersonAffiliation_testDTOFieldsForTestCreateUpdate(PersonAffiliationInfo expected,
            PersonAffiliationInfo actual) {
        assertEquals(expected.getTypeKey(), actual.getTypeKey());
        assertEquals(expected.getStateKey(), actual.getStateKey());
        expected.setEffectiveDate(new Date(System.currentTimeMillis() - 90000l));
        expected.setExpirationDate(new Date(System.currentTimeMillis() + 90000l));
        assertEquals(expected.getPersonId(), actual.getPersonId());
        assertEquals(expected.getOrganizationId(), actual.getOrganizationId());
    }

    /*
     A method to set the fields for a PersonAffiliation in a 'test update' section prior to calling the 'update' operation.
     */
    public void testCrudPersonAffiliation_setDTOFieldsForTestUpdate(PersonAffiliationInfo expected) {
        expected.setTypeKey("typeKey_Updated");
        expected.setStateKey("stateKey_Updated");
        expected.setEffectiveDate(new Date(System.currentTimeMillis() - 90000l));
        expected.setExpirationDate(new Date(System.currentTimeMillis() + 90000l));
        expected.setPersonId("personId_Updated");
        expected.setOrganizationId("organizationId_Updated");
    }

    /*
     A method to test the fields for a PersonAffiliation after an update operation, followed by a read operation,
     where actual is the DTO returned by the read operation, and expected is the dto returned by the update operation.
     */
    public void testCrudPersonAffiliation_testDTOFieldsForTestReadAfterUpdate(PersonAffiliationInfo expected,
            PersonAffiliationInfo actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getTypeKey(), actual.getTypeKey());
        assertEquals(expected.getStateKey(), actual.getStateKey());
        expected.setEffectiveDate(new Date(System.currentTimeMillis() - 90000l));
        expected.setExpirationDate(new Date(System.currentTimeMillis() + 90000l));
        assertEquals(expected.getPersonId(), actual.getPersonId());
        assertEquals(expected.getOrganizationId(), actual.getOrganizationId());
    }

    /*
     A method to set the fields for a PersonAffiliation in the 'test read after update' section.
     This dto is another (second) dto object being created for other tests.
     */
    public void testCrudPersonAffiliation_setDTOFieldsForTestReadAfterUpdate(PersonAffiliationInfo expected) {

        expected.setEffectiveDate(new Date(System.currentTimeMillis() - 90000l));
        expected.setExpirationDate(new Date(System.currentTimeMillis() + 90000l));
        expected.setPersonId("personId_Updated");
        expected.setOrganizationId("organizationId_Updated");
    }

    // ========================================
    // SERVICE OPS NOT TESTED IN BASE TEST CLASS
    // ========================================
    /* Method Name: searchForPersonIds */
    @Test
    public void test_searchForPersonIds()
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
    }

    /* Method Name: searchForPersons */
    @Test
    public void test_searchForPersons()
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
    }

    /* Method Name: validatePerson */
    @Test
    public void test_validatePerson()
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
    }

    /* Method Name: searchForPersonNameIds */
    @Test
    public void test_searchForPersonNameIds()
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
    }

    /* Method Name: searchForPersonNames */
    @Test
    public void test_searchForPersonNames()
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
    }

    /* Method Name: validatePersonName */
    @Test
    public void test_validatePersonName()
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
    }

    /* Method Name: getPersonNamesByPerson */
    @Test
    public void test_getPersonNamesByPerson()
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
    }

    /* Method Name: searchForPersonIdentifierIds */
    @Test
    public void test_searchForPersonIdentifierIds()
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
    }

    /* Method Name: searchForPersonIdentifiers */
    @Test
    public void test_searchForPersonIdentifiers()
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
    }

    /* Method Name: validatePersonIdentifier */
    @Test
    public void test_validatePersonIdentifier()
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
    }

    /* Method Name: getPersonIdentifiersByPerson */
    @Test
    public void test_getPersonIdentifiersByPerson()
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
    }

    /* Method Name: getPersonIdentifiersByIdentifier */
    @Test
    public void test_getPersonIdentifiersByIdentifier()
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
    }

    /* Method Name: searchForPersonBioDemographicsIds */
    @Test
    public void test_searchForPersonBioDemographicsIds()
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
    }

    /* Method Name: searchForPersonBioDemographics */
    @Test
    public void test_searchForPersonBioDemographics()
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
    }

    /* Method Name: validatePersonBioDemographics */
    @Test
    public void test_validatePersonBioDemographics()
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
    }

    /* Method Name: searchForPersonAffiliationIds */
    @Test
    public void test_searchForPersonAffiliationIds()
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
    }

    /* Method Name: searchForPersonAffiliations */
    @Test
    public void test_searchForPersonAffiliations()
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
    }

    /* Method Name: validatePersonAffiliation */
    @Test
    public void test_validatePersonAffiliation()
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
    }

    /* Method Name: getPersonAffiliationsByPerson */
    @Test
    public void test_getPersonAffiliationsByPerson()
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
    }

    /* Method Name: getActivePeopleMatchingNameFragmentAndAffiliation */
    @Test
    public void test_getActivePeopleMatchingNameFragmentAndAffiliation()
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
    }

    /* Method Name: getIntitutionalAffiliationOrganizationId */
    @Test
    public void test_getIntitutionalAffiliationOrganizationId()
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
    }

}
