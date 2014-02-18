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
import java.util.List;
import javax.annotation.Resource;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.kuali.student.common.test.util.IdEntityTester;
import org.kuali.student.core.person.dto.PersonAffiliationInfo;
import org.kuali.student.core.person.dto.PersonBioDemographicsInfo;
import org.kuali.student.core.person.dto.PersonIdentifierInfo;
import org.kuali.student.core.person.dto.PersonInfo;
import org.kuali.student.core.person.dto.PersonNameInfo;
import org.kuali.student.core.person.service.PersonService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DependentObjectsExistException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.kuali.student.common.test.util.AttributeTester;
import org.kuali.student.common.test.util.IdNamelessEntityTester;
import org.kuali.student.common.test.util.MetaTester;
import org.kuali.student.common.test.util.RelationshipTester;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"classpath:person-test-with-map-context.xml"})
public abstract class TestPersonServiceImplConformanceBaseCrud {

    // ====================
    // SETUP
    // ====================
    @Resource
    public PersonService testService;

    public PersonService getPersonService() {
        return testService;
    }

    public void setPersonService(PersonService service) {
        testService = service;
    }

    public ContextInfo contextInfo = null;
    public static String principalId = "123";

    @Before
    public void setUp() {
        principalId = "123";
        contextInfo = new ContextInfo();
        contextInfo.setPrincipalId(principalId);
    }

	// ====================
    // TESTING
    // ====================
    // ****************************************************
    //           PersonInfo
    // ****************************************************
    @Test
    public void testCrudPerson()
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException,
            VersionMismatchException,
            DependentObjectsExistException {
        // -------------------------------------
        // test create
        // -------------------------------------
        PersonInfo expected = new PersonInfo();

        // METHOD TO SET DTO FIELDS HERE FOR TEST CREATE
        testCrudPerson_setDTOFieldsForTestCreate(expected);

        new AttributeTester().add2ForCreate(expected.getAttributes());

        // code to create actual
        PersonInfo actual = testService.createPerson(expected.getTypeKey(), expected, contextInfo);

        assertNotNull(actual.getId());
        new IdEntityTester().check(expected, actual);

        // METHOD TO TEST DTO FIELDS HERE FOR TEST CREATE
        testCrudPerson_testDTOFieldsForTestCreateUpdate(expected, actual);

        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterCreate(actual.getMeta());

        // -------------------------------------
        // test read
        // -------------------------------------
        expected = actual;
        actual = testService.getPerson(actual.getId(), contextInfo);
        assertEquals(expected.getId(), actual.getId());
        new IdEntityTester().check(expected, actual);

        // INSERT CODE FOR TESTING MORE DTO FIELDS HERE
        testCrudPerson_testDTOFieldsForTestCreateUpdate(expected, actual);

        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterGet(expected.getMeta(), actual.getMeta());

        // -------------------------------------
        // test update
        // -------------------------------------
        PersonInfo original = new PersonInfo(actual);
        expected = new PersonInfo(actual);

        expected.setStateKey(expected.getState() + "_Updated");

        // METHOD TO INSERT CODE TO UPDATE DTO FIELDS HERE
        testCrudPerson_setDTOFieldsForTestUpdate(expected);

        new AttributeTester().delete1Update1Add1ForUpdate(expected.getAttributes());
        // code to update
        actual = testService.updatePerson(expected.getId(), expected, contextInfo);

        assertEquals(expected.getId(), actual.getId());
        new IdEntityTester().check(expected, actual);

        // METHOD TO INSERT CODE FOR TESTING DTO FIELDS HERE
        testCrudPerson_testDTOFieldsForTestCreateUpdate(expected, actual);

        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterUpdate(expected.getMeta(), actual.getMeta());

        // Test that VersionMissmatchException's are being detected
        boolean exception = false;
        try {
            testService.updatePerson(original.getId(), original, contextInfo);
        } catch (VersionMismatchException e) {
            exception = true;
        }

        Assert.assertTrue("VersionMissmatchException was not detected!", exception);

        // -------------------------------------
        // test read after update
        // -------------------------------------
        expected = actual;
        // code to get actual
        actual = testService.getPerson(actual.getId(), contextInfo);

        assertEquals(expected.getId(), actual.getId());
        new IdEntityTester().check(expected, actual);

        // INSERT METHOD CODE FOR TESTING DTO FIELDS HERE
        testCrudPerson_testDTOFieldsForTestReadAfterUpdate(expected, actual);

        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterGet(expected.getMeta(), actual.getMeta());

        PersonInfo alphaDTO = actual;

        // create a 2nd DTO
        PersonInfo betaDTO = new PersonInfo();

        // METHOD TO INSERT CODE TO SET MORE DTO FIELDS HERE
        testCrudPerson_setDTOFieldsForTestReadAfterUpdate(betaDTO);

        betaDTO.setTypeKey("typeKeyBeta");
        betaDTO.setStateKey("stateKeyBeta");
        betaDTO = testService.createPerson(betaDTO.getTypeKey(), betaDTO, contextInfo);

        // -------------------------------------
        // test bulk get with no ids supplied
        // -------------------------------------
        List<String> personIds = new ArrayList<String>();
        // code to get DTO by Ids
        List<PersonInfo> records = testService.getPersonsByIds(personIds, contextInfo);

        assertEquals(personIds.size(), records.size());
        assertEquals(0, personIds.size());

        // -------------------------------------
        // test bulk get
        // -------------------------------------
        personIds = new ArrayList<String>();
        personIds.add(alphaDTO.getId());
        personIds.add(betaDTO.getId());
        // code to get DTO by Ids
        records = testService.getPersonsByIds(personIds, contextInfo);

        assertEquals(personIds.size(), records.size());
        for (PersonInfo record : records) {
            if (!personIds.remove(record.getId())) {
                fail(record.getId());
            }
        }
        assertEquals(0, personIds.size());

        // -------------------------------------
        // test get by type
        // -------------------------------------
        // code to get by specific type "typeKey01" 
        personIds = testService.getPersonIdsByType("typeKey_Updated", contextInfo);

        assertEquals(1, personIds.size());
        assertEquals(alphaDTO.getId(), personIds.get(0));

        // test get by other type
        // code to get by specific type "typeKeyBeta" 
        personIds = testService.getPersonIdsByType("typeKeyBeta", contextInfo);

        assertEquals(1, personIds.size());
        assertEquals(betaDTO.getId(), personIds.get(0));

        // -------------------------------------
        // test delete
        // -------------------------------------
        StatusInfo status = testService.deletePerson(actual.getId(), contextInfo);

        assertNotNull(status);
        assertTrue(status.getIsSuccess());
        try {
            PersonInfo record = testService.getPerson(actual.getId(), contextInfo);
            fail("Did not receive DoesNotExistException when attempting to get already-deleted entity");
        } catch (DoesNotExistException dnee) {
            // expected
        }

    }

    /*
     A method to set the fields for a Person in a 'test create' section prior to calling the 'create' operation.
     */
    public abstract void testCrudPerson_setDTOFieldsForTestCreate(PersonInfo expected);

    /*
     A method to test the fields for a Person. This is called after:
     - creating a DTO, where actual is the DTO returned by the create operation, and expected is the dto passed in to the create operation
     - reading a DTO after creating it, and actual is the read DTO, and expected is the dto that was created
     - updating a DTO, where actual is DTO returned by the update operation, and expected is the dto that was passed in to the update operation
     */
    public abstract void testCrudPerson_testDTOFieldsForTestCreateUpdate(PersonInfo expected, PersonInfo actual);

    /*
     A method to set the fields for a Person in a 'test update' section prior to calling the 'update' operation.
     */
    public abstract void testCrudPerson_setDTOFieldsForTestUpdate(PersonInfo expected);

    /*
     A method to test the fields for a Person after an update operation, followed by a read operation,
     where actual is the DTO returned by the read operation, and expected is the dto returned by the update operation.
     */
    public abstract void testCrudPerson_testDTOFieldsForTestReadAfterUpdate(PersonInfo expected, PersonInfo actual);

    /*
     A method to set the fields for a Person in the 'test read after update' section.
     This dto is another (second) dto object being created for other tests.
     */
    public abstract void testCrudPerson_setDTOFieldsForTestReadAfterUpdate(PersonInfo expected);

    // ****************************************************
    //           PersonNameInfo
    // ****************************************************
    @Test
    public void testCrudPersonName()
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException,
            VersionMismatchException,
            DependentObjectsExistException {
        // -------------------------------------
        // test create
        // -------------------------------------
        PersonNameInfo expected = new PersonNameInfo();

        // METHOD TO SET DTO FIELDS HERE FOR TEST CREATE
        testCrudPersonName_setDTOFieldsForTestCreate(expected);

        new AttributeTester().add2ForCreate(expected.getAttributes());

        // code to create actual
        PersonNameInfo actual = testService.createPersonName(expected.getTypeKey(), expected.getId(), expected, contextInfo);

        assertNotNull(actual.getId());
        new IdNamelessEntityTester().check(expected, actual);

        // METHOD TO TEST DTO FIELDS HERE FOR TEST CREATE
        testCrudPersonName_testDTOFieldsForTestCreateUpdate(expected, actual);

        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterCreate(actual.getMeta());

        // -------------------------------------
        // test read
        // -------------------------------------
        expected = actual;
        actual = testService.getPersonName(actual.getId(), contextInfo);
        assertEquals(expected.getId(), actual.getId());
        new IdNamelessEntityTester().check(expected, actual);

        // INSERT CODE FOR TESTING MORE DTO FIELDS HERE
        testCrudPersonName_testDTOFieldsForTestCreateUpdate(expected, actual);

        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterGet(expected.getMeta(), actual.getMeta());

        // -------------------------------------
        // test update
        // -------------------------------------
        PersonNameInfo original = new PersonNameInfo(actual);
        expected = new PersonNameInfo(actual);

        expected.setStateKey(expected.getState() + "_Updated");

        // METHOD TO INSERT CODE TO UPDATE DTO FIELDS HERE
        testCrudPersonName_setDTOFieldsForTestUpdate(expected);

        new AttributeTester().delete1Update1Add1ForUpdate(expected.getAttributes());
        // code to update
        actual = testService.updatePersonName(expected.getId(), expected, contextInfo);

        assertEquals(expected.getId(), actual.getId());
        new IdNamelessEntityTester().check(expected, actual);

        // METHOD TO INSERT CODE FOR TESTING DTO FIELDS HERE
        testCrudPersonName_testDTOFieldsForTestCreateUpdate(expected, actual);

        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterUpdate(expected.getMeta(), actual.getMeta());

        // Test that VersionMissmatchException's are being detected
        boolean exception = false;
        try {
            testService.updatePersonName(original.getId(), original, contextInfo);
        } catch (VersionMismatchException e) {
            exception = true;
        }

        Assert.assertTrue("VersionMissmatchException was not detected!", exception);

        // -------------------------------------
        // test read after update
        // -------------------------------------
        expected = actual;
        // code to get actual
        actual = testService.getPersonName(actual.getId(), contextInfo);

        assertEquals(expected.getId(), actual.getId());
        new IdNamelessEntityTester().check(expected, actual);

        // INSERT METHOD CODE FOR TESTING DTO FIELDS HERE
        testCrudPersonName_testDTOFieldsForTestReadAfterUpdate(expected, actual);

        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterGet(expected.getMeta(), actual.getMeta());

        PersonNameInfo alphaDTO = actual;

        // create a 2nd DTO
        PersonNameInfo betaDTO = new PersonNameInfo();

        // METHOD TO INSERT CODE TO SET MORE DTO FIELDS HERE
        testCrudPersonName_setDTOFieldsForTestReadAfterUpdate(betaDTO);

        betaDTO.setTypeKey("typeKeyBeta");
        betaDTO.setStateKey("stateKeyBeta");
        betaDTO = testService.createPersonName(betaDTO.getTypeKey(), betaDTO.getId(), betaDTO, contextInfo);

        // -------------------------------------
        // test bulk get with no ids supplied
        // -------------------------------------
        List<String> personNameIds = new ArrayList<String>();
        // code to get DTO by Ids
        List<PersonNameInfo> records = testService.getPersonNamesByIds(personNameIds, contextInfo);

        assertEquals(personNameIds.size(), records.size());
        assertEquals(0, personNameIds.size());

        // -------------------------------------
        // test bulk get
        // -------------------------------------
        personNameIds = new ArrayList<String>();
        personNameIds.add(alphaDTO.getId());
        personNameIds.add(betaDTO.getId());
        // code to get DTO by Ids
        records = testService.getPersonNamesByIds(personNameIds, contextInfo);

        assertEquals(personNameIds.size(), records.size());
        for (PersonNameInfo record : records) {
            if (!personNameIds.remove(record.getId())) {
                fail(record.getId());
            }
        }
        assertEquals(0, personNameIds.size());

        // -------------------------------------
        // test get by type
        // -------------------------------------
        // code to get by specific type "typeKey01" 
        personNameIds = testService.getPersonNameIdsByType("typeKey_Updated", contextInfo);

        assertEquals(1, personNameIds.size());
        assertEquals(alphaDTO.getId(), personNameIds.get(0));

        // test get by other type
        // code to get by specific type "typeKeyBeta" 
        personNameIds = testService.getPersonNameIdsByType("typeKeyBeta", contextInfo);

        assertEquals(1, personNameIds.size());
        assertEquals(betaDTO.getId(), personNameIds.get(0));

        // -------------------------------------
        // test delete
        // -------------------------------------
        StatusInfo status = testService.deletePersonName(actual.getId(), contextInfo);

        assertNotNull(status);
        assertTrue(status.getIsSuccess());
        try {
            PersonNameInfo record = testService.getPersonName(actual.getId(), contextInfo);
            fail("Did not receive DoesNotExistException when attempting to get already-deleted entity");
        } catch (DoesNotExistException dnee) {
            // expected
        }

    }

    /*
     A method to set the fields for a PersonName in a 'test create' section prior to calling the 'create' operation.
     */
    public abstract void testCrudPersonName_setDTOFieldsForTestCreate(PersonNameInfo expected);

    /*
     A method to test the fields for a PersonName. This is called after:
     - creating a DTO, where actual is the DTO returned by the create operation, and expected is the dto passed in to the create operation
     - reading a DTO after creating it, and actual is the read DTO, and expected is the dto that was created
     - updating a DTO, where actual is DTO returned by the update operation, and expected is the dto that was passed in to the update operation
     */
    public abstract void testCrudPersonName_testDTOFieldsForTestCreateUpdate(PersonNameInfo expected, PersonNameInfo actual);

    /*
     A method to set the fields for a PersonName in a 'test update' section prior to calling the 'update' operation.
     */
    public abstract void testCrudPersonName_setDTOFieldsForTestUpdate(PersonNameInfo expected);

    /*
     A method to test the fields for a PersonName after an update operation, followed by a read operation,
     where actual is the DTO returned by the read operation, and expected is the dto returned by the update operation.
     */
    public abstract void testCrudPersonName_testDTOFieldsForTestReadAfterUpdate(PersonNameInfo expected, PersonNameInfo actual);

    /*
     A method to set the fields for a PersonName in the 'test read after update' section.
     This dto is another (second) dto object being created for other tests.
     */
    public abstract void testCrudPersonName_setDTOFieldsForTestReadAfterUpdate(PersonNameInfo expected);

    // ****************************************************
    //           PersonIdentifierInfo
    // ****************************************************
    @Test
    public void testCrudPersonIdentifier()
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException,
            VersionMismatchException,
            DependentObjectsExistException {
        // -------------------------------------
        // test create
        // -------------------------------------
        PersonIdentifierInfo expected = new PersonIdentifierInfo();

        // METHOD TO SET DTO FIELDS HERE FOR TEST CREATE
        testCrudPersonIdentifier_setDTOFieldsForTestCreate(expected);

        new AttributeTester().add2ForCreate(expected.getAttributes());

        // code to create actual
        PersonIdentifierInfo actual = testService.createPersonIdentifier(expected.getTypeKey(), expected.getId(), expected,
                contextInfo);

        assertNotNull(actual.getId());
        new IdNamelessEntityTester().check(expected, actual);

        // METHOD TO TEST DTO FIELDS HERE FOR TEST CREATE
        testCrudPersonIdentifier_testDTOFieldsForTestCreateUpdate(expected, actual);

        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterCreate(actual.getMeta());

        // -------------------------------------
        // test read
        // -------------------------------------
        expected = actual;
        actual = testService.getPersonIdentifier(actual.getId(), contextInfo);
        assertEquals(expected.getId(), actual.getId());
        new IdNamelessEntityTester().check(expected, actual);

        // INSERT CODE FOR TESTING MORE DTO FIELDS HERE
        testCrudPersonIdentifier_testDTOFieldsForTestCreateUpdate(expected, actual);

        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterGet(expected.getMeta(), actual.getMeta());

        // -------------------------------------
        // test update
        // -------------------------------------
        PersonIdentifierInfo original = new PersonIdentifierInfo(actual);
        expected = new PersonIdentifierInfo(actual);

        expected.setStateKey(expected.getState() + "_Updated");

        // METHOD TO INSERT CODE TO UPDATE DTO FIELDS HERE
        testCrudPersonIdentifier_setDTOFieldsForTestUpdate(expected);

        new AttributeTester().delete1Update1Add1ForUpdate(expected.getAttributes());
        // code to update
        actual = testService.updatePersonIdentifier(expected.getId(), expected, contextInfo);

        assertEquals(expected.getId(), actual.getId());
        new IdNamelessEntityTester().check(expected, actual);

        // METHOD TO INSERT CODE FOR TESTING DTO FIELDS HERE
        testCrudPersonIdentifier_testDTOFieldsForTestCreateUpdate(expected, actual);

        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterUpdate(expected.getMeta(), actual.getMeta());

        // Test that VersionMissmatchException's are being detected
        boolean exception = false;
        try {
            testService.updatePersonIdentifier(original.getId(), original, contextInfo);
        } catch (VersionMismatchException e) {
            exception = true;
        }

        Assert.assertTrue("VersionMissmatchException was not detected!", exception);

        // -------------------------------------
        // test read after update
        // -------------------------------------
        expected = actual;
        // code to get actual
        actual = testService.getPersonIdentifier(actual.getId(), contextInfo);

        assertEquals(expected.getId(), actual.getId());
        new IdNamelessEntityTester().check(expected, actual);

        // INSERT METHOD CODE FOR TESTING DTO FIELDS HERE
        testCrudPersonIdentifier_testDTOFieldsForTestReadAfterUpdate(expected, actual);

        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterGet(expected.getMeta(), actual.getMeta());

        PersonIdentifierInfo alphaDTO = actual;

        // create a 2nd DTO
        PersonIdentifierInfo betaDTO = new PersonIdentifierInfo();

        // METHOD TO INSERT CODE TO SET MORE DTO FIELDS HERE
        testCrudPersonIdentifier_setDTOFieldsForTestReadAfterUpdate(betaDTO);

        betaDTO.setTypeKey("typeKeyBeta");
        betaDTO.setStateKey("stateKeyBeta");
        betaDTO = testService.createPersonIdentifier(betaDTO.getTypeKey(), betaDTO.getId(), betaDTO, contextInfo);

        // -------------------------------------
        // test bulk get with no ids supplied
        // -------------------------------------
        List<String> personIdentifierIds = new ArrayList<String>();
        // code to get DTO by Ids
        List<PersonIdentifierInfo> records = testService.getPersonIdentifiersByIds(personIdentifierIds, contextInfo);

        assertEquals(personIdentifierIds.size(), records.size());
        assertEquals(0, personIdentifierIds.size());

        // -------------------------------------
        // test bulk get
        // -------------------------------------
        personIdentifierIds = new ArrayList<String>();
        personIdentifierIds.add(alphaDTO.getId());
        personIdentifierIds.add(betaDTO.getId());
        // code to get DTO by Ids
        records = testService.getPersonIdentifiersByIds(personIdentifierIds, contextInfo);

        assertEquals(personIdentifierIds.size(), records.size());
        for (PersonIdentifierInfo record : records) {
            if (!personIdentifierIds.remove(record.getId())) {
                fail(record.getId());
            }
        }
        assertEquals(0, personIdentifierIds.size());

        // -------------------------------------
        // test get by type
        // -------------------------------------
        // code to get by specific type "typeKey01" 
        personIdentifierIds = testService.getPersonIdentifierIdsByType("typeKey_Updated", contextInfo);

        assertEquals(1, personIdentifierIds.size());
        assertEquals(alphaDTO.getId(), personIdentifierIds.get(0));

        // test get by other type
        // code to get by specific type "typeKeyBeta" 
        personIdentifierIds = testService.getPersonIdentifierIdsByType("typeKeyBeta", contextInfo);

        assertEquals(1, personIdentifierIds.size());
        assertEquals(betaDTO.getId(), personIdentifierIds.get(0));

        // -------------------------------------
        // test delete
        // -------------------------------------
        StatusInfo status = testService.deletePersonIdentifier(actual.getId(), contextInfo);

        assertNotNull(status);
        assertTrue(status.getIsSuccess());
        try {
            PersonIdentifierInfo record = testService.getPersonIdentifier(actual.getId(), contextInfo);
            fail("Did not receive DoesNotExistException when attempting to get already-deleted entity");
        } catch (DoesNotExistException dnee) {
            // expected
        }

    }

    /*
     A method to set the fields for a PersonIdentifier in a 'test create' section prior to calling the 'create' operation.
     */
    public abstract void testCrudPersonIdentifier_setDTOFieldsForTestCreate(PersonIdentifierInfo expected);

    /*
     A method to test the fields for a PersonIdentifier. This is called after:
     - creating a DTO, where actual is the DTO returned by the create operation, and expected is the dto passed in to the create operation
     - reading a DTO after creating it, and actual is the read DTO, and expected is the dto that was created
     - updating a DTO, where actual is DTO returned by the update operation, and expected is the dto that was passed in to the update operation
     */
    public abstract void testCrudPersonIdentifier_testDTOFieldsForTestCreateUpdate(PersonIdentifierInfo expected,
            PersonIdentifierInfo actual);

    /*
     A method to set the fields for a PersonIdentifier in a 'test update' section prior to calling the 'update' operation.
     */
    public abstract void testCrudPersonIdentifier_setDTOFieldsForTestUpdate(PersonIdentifierInfo expected);

    /*
     A method to test the fields for a PersonIdentifier after an update operation, followed by a read operation,
     where actual is the DTO returned by the read operation, and expected is the dto returned by the update operation.
     */
    public abstract void testCrudPersonIdentifier_testDTOFieldsForTestReadAfterUpdate(PersonIdentifierInfo expected,
            PersonIdentifierInfo actual);

    /*
     A method to set the fields for a PersonIdentifier in the 'test read after update' section.
     This dto is another (second) dto object being created for other tests.
     */
    public abstract void testCrudPersonIdentifier_setDTOFieldsForTestReadAfterUpdate(PersonIdentifierInfo expected);

    // ****************************************************
    //           PersonBioDemographicsInfo
    // ****************************************************
    @Test
    public void testCrudPersonBioDemographics()
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException,
            VersionMismatchException,
            DependentObjectsExistException {
        // -------------------------------------
        // test create
        // -------------------------------------
        PersonBioDemographicsInfo expected = new PersonBioDemographicsInfo();

        // METHOD TO SET DTO FIELDS HERE FOR TEST CREATE
        testCrudPersonBioDemographics_setDTOFieldsForTestCreate(expected);

        new AttributeTester().add2ForCreate(expected.getAttributes());

        // code to create actual
        PersonBioDemographicsInfo actual = testService.createPersonBioDemographics(expected.getTypeKey(), expected.getId(),
                expected, contextInfo);

        assertNotNull(actual.getId());
        new IdNamelessEntityTester().check(expected, actual);

        // METHOD TO TEST DTO FIELDS HERE FOR TEST CREATE
        testCrudPersonBioDemographics_testDTOFieldsForTestCreateUpdate(expected, actual);

        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterCreate(actual.getMeta());

        // -------------------------------------
        // test read
        // -------------------------------------
        expected = actual;
        actual = testService.getPersonBioDemographics(actual.getId(), contextInfo);
        assertEquals(expected.getId(), actual.getId());
        new IdNamelessEntityTester().check(expected, actual);

        // INSERT CODE FOR TESTING MORE DTO FIELDS HERE
        testCrudPersonBioDemographics_testDTOFieldsForTestCreateUpdate(expected, actual);

        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterGet(expected.getMeta(), actual.getMeta());

        // -------------------------------------
        // test update
        // -------------------------------------
        PersonBioDemographicsInfo original = new PersonBioDemographicsInfo(actual);
        expected = new PersonBioDemographicsInfo(actual);

        expected.setStateKey(expected.getState() + "_Updated");

        // METHOD TO INSERT CODE TO UPDATE DTO FIELDS HERE
        testCrudPersonBioDemographics_setDTOFieldsForTestUpdate(expected);

        new AttributeTester().delete1Update1Add1ForUpdate(expected.getAttributes());
        // code to update
        actual = testService.updatePersonBioDemographics(expected.getId(), expected, contextInfo);

        assertEquals(expected.getId(), actual.getId());
        new IdNamelessEntityTester().check(expected, actual);

        // METHOD TO INSERT CODE FOR TESTING DTO FIELDS HERE
        testCrudPersonBioDemographics_testDTOFieldsForTestCreateUpdate(expected, actual);

        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterUpdate(expected.getMeta(), actual.getMeta());

        // Test that VersionMissmatchException's are being detected
        boolean exception = false;
        try {
            testService.updatePersonBioDemographics(original.getId(), original, contextInfo);
        } catch (VersionMismatchException e) {
            exception = true;
        }

        Assert.assertTrue("VersionMissmatchException was not detected!", exception);

        // -------------------------------------
        // test read after update
        // -------------------------------------
        expected = actual;
        // code to get actual
        actual = testService.getPersonBioDemographics(actual.getId(), contextInfo);

        assertEquals(expected.getId(), actual.getId());
        new IdNamelessEntityTester().check(expected, actual);

        // INSERT METHOD CODE FOR TESTING DTO FIELDS HERE
        testCrudPersonBioDemographics_testDTOFieldsForTestReadAfterUpdate(expected, actual);

        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterGet(expected.getMeta(), actual.getMeta());

        PersonBioDemographicsInfo alphaDTO = actual;

        // create a 2nd DTO
        PersonBioDemographicsInfo betaDTO = new PersonBioDemographicsInfo();

        // METHOD TO INSERT CODE TO SET MORE DTO FIELDS HERE
        testCrudPersonBioDemographics_setDTOFieldsForTestReadAfterUpdate(betaDTO);

        betaDTO.setTypeKey("typeKeyBeta");
        betaDTO.setStateKey("stateKeyBeta");
        betaDTO = testService.createPersonBioDemographics(betaDTO.getTypeKey(), betaDTO.getId(), betaDTO, contextInfo);

        // -------------------------------------
        // test bulk get with no ids supplied
        // -------------------------------------
        List<String> personBioDemographicsIds = new ArrayList<String>();
        // code to get DTO by Ids
        List<PersonBioDemographicsInfo> records = this.testService.getPersonBioDemographicsByIds(personBioDemographicsIds,
                contextInfo);

        assertEquals(personBioDemographicsIds.size(), records.size());
        assertEquals(0, personBioDemographicsIds.size());

        // -------------------------------------
        // test bulk get
        // -------------------------------------
        personBioDemographicsIds = new ArrayList<String>();
        personBioDemographicsIds.add(alphaDTO.getId());
        personBioDemographicsIds.add(betaDTO.getId());
        // code to get DTO by Ids
        records = this.testService.getPersonBioDemographicsByIds(personBioDemographicsIds, contextInfo);

        assertEquals(personBioDemographicsIds.size(), records.size());
        for (PersonBioDemographicsInfo record : records) {
            if (!personBioDemographicsIds.remove(record.getId())) {
                fail(record.getId());
            }
        }
        assertEquals(0, personBioDemographicsIds.size());

        // -------------------------------------
        // test get by type
        // -------------------------------------
        // code to get by specific type "typeKey01" 
        personBioDemographicsIds = testService.getPersonBioDemographicsIdsByType("typeKey_Updated", contextInfo);

        assertEquals(1, personBioDemographicsIds.size());
        assertEquals(alphaDTO.getId(), personBioDemographicsIds.get(0));

        // test get by other type
        // code to get by specific type "typeKeyBeta" 
        personBioDemographicsIds = testService.getPersonBioDemographicsIdsByType("typeKeyBeta", contextInfo);

        assertEquals(1, personBioDemographicsIds.size());
        assertEquals(betaDTO.getId(), personBioDemographicsIds.get(0));

        // -------------------------------------
        // test delete
        // -------------------------------------
        StatusInfo status = testService.deletePersonBioDemographics(actual.getId(), contextInfo);

        assertNotNull(status);
        assertTrue(status.getIsSuccess());
        try {
            PersonBioDemographicsInfo record = testService.getPersonBioDemographics(actual.getId(), contextInfo);
            fail("Did not receive DoesNotExistException when attempting to get already-deleted entity");
        } catch (DoesNotExistException dnee) {
            // expected
        }

    }

    /*
     A method to set the fields for a PersonBioDemographics in a 'test create' section prior to calling the 'create' operation.
     */
    public abstract void testCrudPersonBioDemographics_setDTOFieldsForTestCreate(PersonBioDemographicsInfo expected);

    /*
     A method to test the fields for a PersonBioDemographics. This is called after:
     - creating a DTO, where actual is the DTO returned by the create operation, and expected is the dto passed in to the create operation
     - reading a DTO after creating it, and actual is the read DTO, and expected is the dto that was created
     - updating a DTO, where actual is DTO returned by the update operation, and expected is the dto that was passed in to the update operation
     */
    public abstract void testCrudPersonBioDemographics_testDTOFieldsForTestCreateUpdate(PersonBioDemographicsInfo expected,
            PersonBioDemographicsInfo actual);

    /*
     A method to set the fields for a PersonBioDemographics in a 'test update' section prior to calling the 'update' operation.
     */
    public abstract void testCrudPersonBioDemographics_setDTOFieldsForTestUpdate(PersonBioDemographicsInfo expected);

    /*
     A method to test the fields for a PersonBioDemographics after an update operation, followed by a read operation,
     where actual is the DTO returned by the read operation, and expected is the dto returned by the update operation.
     */
    public abstract void testCrudPersonBioDemographics_testDTOFieldsForTestReadAfterUpdate(PersonBioDemographicsInfo expected,
            PersonBioDemographicsInfo actual);

    /*
     A method to set the fields for a PersonBioDemographics in the 'test read after update' section.
     This dto is another (second) dto object being created for other tests.
     */
    public abstract void testCrudPersonBioDemographics_setDTOFieldsForTestReadAfterUpdate(PersonBioDemographicsInfo expected);

    // ****************************************************
    //           PersonAffiliationInfo
    // ****************************************************
    @Test
    public void testCrudPersonAffiliation()
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException,
            VersionMismatchException,
            DependentObjectsExistException {
        // -------------------------------------
        // test create
        // -------------------------------------
        PersonAffiliationInfo expected = new PersonAffiliationInfo();

        // METHOD TO SET DTO FIELDS HERE FOR TEST CREATE
        testCrudPersonAffiliation_setDTOFieldsForTestCreate(expected);

        new AttributeTester().add2ForCreate(expected.getAttributes());

        // code to create actual
        PersonAffiliationInfo actual = testService.createPersonAffiliation(expected.getTypeKey(), expected.getId(), expected.
                getId(), expected, contextInfo);

        assertNotNull(actual.getId());
        new RelationshipTester().check(expected, actual);

        // METHOD TO TEST DTO FIELDS HERE FOR TEST CREATE
        testCrudPersonAffiliation_testDTOFieldsForTestCreateUpdate(expected, actual);

        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterCreate(actual.getMeta());

        // -------------------------------------
        // test read
        // -------------------------------------
        expected = actual;
        actual = testService.getPersonAffiliation(actual.getId(), contextInfo);
        assertEquals(expected.getId(), actual.getId());
        new RelationshipTester().check(expected, actual);

        // INSERT CODE FOR TESTING MORE DTO FIELDS HERE
        testCrudPersonAffiliation_testDTOFieldsForTestCreateUpdate(expected, actual);

        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterGet(expected.getMeta(), actual.getMeta());

        // -------------------------------------
        // test update
        // -------------------------------------
        PersonAffiliationInfo original = new PersonAffiliationInfo(actual);
        expected = new PersonAffiliationInfo(actual);

        expected.setStateKey(expected.getState() + "_Updated");

        // METHOD TO INSERT CODE TO UPDATE DTO FIELDS HERE
        testCrudPersonAffiliation_setDTOFieldsForTestUpdate(expected);

        new AttributeTester().delete1Update1Add1ForUpdate(expected.getAttributes());
        // code to update
        actual = testService.updatePersonAffiliation(expected.getId(), expected, contextInfo);

        assertEquals(expected.getId(), actual.getId());
        new RelationshipTester().check(expected, actual);

        // METHOD TO INSERT CODE FOR TESTING DTO FIELDS HERE
        testCrudPersonAffiliation_testDTOFieldsForTestCreateUpdate(expected, actual);

        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterUpdate(expected.getMeta(), actual.getMeta());

        // Test that VersionMissmatchException's are being detected
        boolean exception = false;
        try {
            testService.updatePersonAffiliation(original.getId(), original, contextInfo);
        } catch (VersionMismatchException e) {
            exception = true;
        }

        Assert.assertTrue("VersionMissmatchException was not detected!", exception);

        // -------------------------------------
        // test read after update
        // -------------------------------------
        expected = actual;
        // code to get actual
        actual = testService.getPersonAffiliation(actual.getId(), contextInfo);

        assertEquals(expected.getId(), actual.getId());
        new RelationshipTester().check(expected, actual);

        // INSERT METHOD CODE FOR TESTING DTO FIELDS HERE
        testCrudPersonAffiliation_testDTOFieldsForTestReadAfterUpdate(expected, actual);

        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterGet(expected.getMeta(), actual.getMeta());

        PersonAffiliationInfo alphaDTO = actual;

        // create a 2nd DTO
        PersonAffiliationInfo betaDTO = new PersonAffiliationInfo();

        // METHOD TO INSERT CODE TO SET MORE DTO FIELDS HERE
        testCrudPersonAffiliation_setDTOFieldsForTestReadAfterUpdate(betaDTO);

        betaDTO.setTypeKey("typeKeyBeta");
        betaDTO.setStateKey("stateKeyBeta");
        betaDTO = testService.
                createPersonAffiliation(betaDTO.getTypeKey(), betaDTO.getId(), betaDTO.getId(), betaDTO, contextInfo);

        // -------------------------------------
        // test bulk get with no ids supplied
        // -------------------------------------
        List<String> personAffiliationIds = new ArrayList<String>();
        // code to get DTO by Ids
        List<PersonAffiliationInfo> records = testService.getPersonAffiliationsByIds(personAffiliationIds, contextInfo);

        assertEquals(personAffiliationIds.size(), records.size());
        assertEquals(0, personAffiliationIds.size());

        // -------------------------------------
        // test bulk get
        // -------------------------------------
        personAffiliationIds = new ArrayList<String>();
        personAffiliationIds.add(alphaDTO.getId());
        personAffiliationIds.add(betaDTO.getId());
        // code to get DTO by Ids
        records = testService.getPersonAffiliationsByIds(personAffiliationIds, contextInfo);

        assertEquals(personAffiliationIds.size(), records.size());
        for (PersonAffiliationInfo record : records) {
            if (!personAffiliationIds.remove(record.getId())) {
                fail(record.getId());
            }
        }
        assertEquals(0, personAffiliationIds.size());

        // -------------------------------------
        // test get by type
        // -------------------------------------
        // code to get by specific type "typeKey01" 
        personAffiliationIds = testService.getPersonAffiliationIdsByType("typeKey_Updated", contextInfo);

        assertEquals(1, personAffiliationIds.size());
        assertEquals(alphaDTO.getId(), personAffiliationIds.get(0));

        // test get by other type
        // code to get by specific type "typeKeyBeta" 
        personAffiliationIds = testService.getPersonAffiliationIdsByType("typeKeyBeta", contextInfo);

        assertEquals(1, personAffiliationIds.size());
        assertEquals(betaDTO.getId(), personAffiliationIds.get(0));

        // -------------------------------------
        // test delete
        // -------------------------------------
        StatusInfo status = testService.deletePersonAffiliation(actual.getId(), contextInfo);

        assertNotNull(status);
        assertTrue(status.getIsSuccess());
        try {
            PersonAffiliationInfo record = testService.getPersonAffiliation(actual.getId(), contextInfo);
            fail("Did not receive DoesNotExistException when attempting to get already-deleted entity");
        } catch (DoesNotExistException dnee) {
            // expected
        }

    }

    /*
     A method to set the fields for a PersonAffiliation in a 'test create' section prior to calling the 'create' operation.
     */
    public abstract void testCrudPersonAffiliation_setDTOFieldsForTestCreate(PersonAffiliationInfo expected);

    /*
     A method to test the fields for a PersonAffiliation. This is called after:
     - creating a DTO, where actual is the DTO returned by the create operation, and expected is the dto passed in to the create operation
     - reading a DTO after creating it, and actual is the read DTO, and expected is the dto that was created
     - updating a DTO, where actual is DTO returned by the update operation, and expected is the dto that was passed in to the update operation
     */
    public abstract void testCrudPersonAffiliation_testDTOFieldsForTestCreateUpdate(PersonAffiliationInfo expected,
            PersonAffiliationInfo actual);

    /*
     A method to set the fields for a PersonAffiliation in a 'test update' section prior to calling the 'update' operation.
     */
    public abstract void testCrudPersonAffiliation_setDTOFieldsForTestUpdate(PersonAffiliationInfo expected);

    /*
     A method to test the fields for a PersonAffiliation after an update operation, followed by a read operation,
     where actual is the DTO returned by the read operation, and expected is the dto returned by the update operation.
     */
    public abstract void testCrudPersonAffiliation_testDTOFieldsForTestReadAfterUpdate(PersonAffiliationInfo expected,
            PersonAffiliationInfo actual);

    /*
     A method to set the fields for a PersonAffiliation in the 'test read after update' section.
     This dto is another (second) dto object being created for other tests.
     */
    public abstract void testCrudPersonAffiliation_setDTOFieldsForTestReadAfterUpdate(PersonAffiliationInfo expected);

	// ========================================
    // SERVICE OPS TESTED IN BASE TEST CLASS
    // ========================================
    /*
     The following methods are tested as part of CRUD operations for this service's DTOs:
     getPerson
     getPersonsByIds
     getPersonIdsByType
     createPerson
     updatePerson
     deletePerson
     getPersonName
     getPersonNamesByIds
     getPersonNameIdsByType
     createPersonName
     updatePersonName
     deletePersonName
     getPersonIdentifier
     getPersonIdentifiersByIds
     getPersonIdentifierIdsByType
     createPersonIdentifier
     updatePersonIdentifier
     deletePersonIdentifier
     getPersonBioDemographics
     getPersonBioDemographicsByIds
     getPersonBioDemographicsIdsByType
     createPersonBioDemographics
     updatePersonBioDemographics
     deletePersonBioDemographics
     getPersonBioDemographicsByPerson
     getPersonAffiliation
     getPersonAffiliationsByIds
     getPersonAffiliationIdsByType
     createPersonAffiliation
     updatePersonAffiliation
     deletePersonAffiliation
     */
    // ========================================
    // SERVICE OPS NOT TESTED IN BASE TEST CLASS
    // ========================================
    /* Method Name: searchForPersonIds */
    @Test
    public abstract void test_searchForPersonIds()
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /* Method Name: searchForPersons */
    @Test
    public abstract void test_searchForPersons()
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /* Method Name: validatePerson */
    @Test
    public abstract void test_validatePerson()
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /* Method Name: searchForPersonNameIds */
    @Test
    public abstract void test_searchForPersonNameIds()
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /* Method Name: searchForPersonNames */
    @Test
    public abstract void test_searchForPersonNames()
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /* Method Name: validatePersonName */
    @Test
    public abstract void test_validatePersonName()
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /* Method Name: getPersonNamesByPerson */
    @Test
    public abstract void test_getPersonNamesByPerson()
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /* Method Name: searchForPersonIdentifierIds */
    @Test
    public abstract void test_searchForPersonIdentifierIds()
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /* Method Name: searchForPersonIdentifiers */
    @Test
    public abstract void test_searchForPersonIdentifiers()
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /* Method Name: validatePersonIdentifier */
    @Test
    public abstract void test_validatePersonIdentifier()
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /* Method Name: getPersonIdentifiersByPerson */
    @Test
    public abstract void test_getPersonIdentifiersByPerson()
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /* Method Name: getPersonIdentifiersByIdentifier */
    @Test
    public abstract void test_getPersonIdentifiersByIdentifier()
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /* Method Name: searchForPersonBioDemographicsIds */
    @Test
    public abstract void test_searchForPersonBioDemographicsIds()
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /* Method Name: searchForPersonBioDemographics */
    @Test
    public abstract void test_searchForPersonBioDemographics()
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /* Method Name: validatePersonBioDemographics */
    @Test
    public abstract void test_validatePersonBioDemographics()
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /* Method Name: searchForPersonAffiliationIds */
    @Test
    public abstract void test_searchForPersonAffiliationIds()
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /* Method Name: searchForPersonAffiliations */
    @Test
    public abstract void test_searchForPersonAffiliations()
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /* Method Name: validatePersonAffiliation */
    @Test
    public abstract void test_validatePersonAffiliation()
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /* Method Name: getPersonAffiliationsByPerson */
    @Test
    public abstract void test_getPersonAffiliationsByPerson()
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /* Method Name: getActivePeopleMatchingNameFragmentAndAffiliation */
    @Test
    public abstract void test_getActivePeopleMatchingNameFragmentAndAffiliation()
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /* Method Name: getIntitutionalAffiliationOrganizationId */
    @Test
    public abstract void test_getIntitutionalAffiliationOrganizationId()
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

}
