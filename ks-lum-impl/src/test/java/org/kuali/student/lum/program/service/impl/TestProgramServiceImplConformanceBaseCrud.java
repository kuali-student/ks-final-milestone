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
package org.kuali.student.lum.program.service.impl;


import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.common.test.util.AttributeTester;
import org.kuali.student.common.test.util.IdNamelessEntityTester;
import org.kuali.student.common.test.util.MetaTester;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DependentObjectsExistException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.IllegalVersionSequencingException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.lum.program.dto.CoreProgramInfo;
import org.kuali.student.r2.lum.program.dto.CredentialProgramInfo;
import org.kuali.student.r2.lum.program.dto.HonorsProgramInfo;
import org.kuali.student.r2.lum.program.dto.MajorDisciplineInfo;
import org.kuali.student.r2.lum.program.dto.MinorDisciplineInfo;
import org.kuali.student.r2.lum.program.dto.ProgramRequirementInfo;
import org.kuali.student.r2.lum.program.dto.TrackInfo;
import org.kuali.student.r2.lum.program.service.ProgramService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:program-map-service-test-context.xml"})
public abstract class TestProgramServiceImplConformanceBaseCrud {

    // ====================
    // SETUP
    // ====================

    @Resource
    public ProgramService testService;
    public ProgramService getProgramService() { return testService; }
    public void setProgramService(ProgramService service) { testService = service; }

    public ContextInfo contextInfo = null;
    public static String principalId = "123";

    @Before
    public void setUp()
    {
        principalId = "123";
        contextInfo = new ContextInfo();
        contextInfo.setPrincipalId(principalId);
    }

    // ====================
    // TESTING
    // ====================

    // ****************************************************
    //           CredentialProgramInfo
    // ****************************************************
    @Test
    public void testCrudCredentialProgram()
            throws AlreadyExistsException, DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException,
            VersionMismatchException,
            DependentObjectsExistException
    {
        // -------------------------------------
        // test create
        // -------------------------------------
        CredentialProgramInfo expected = new CredentialProgramInfo ();

        // METHOD TO SET DTO FIELDS HERE FOR TEST CREATE
        testCrudCredentialProgram_setDTOFieldsForTestCreate (expected);

        new AttributeTester().add2ForCreate(expected.getAttributes());

        // code to create actual
        CredentialProgramInfo actual = testService.createCredentialProgram ( expected.getTypeKey(), expected, contextInfo);

        assertNotNull(actual.getId());
        new IdNamelessEntityTester().check(expected, actual);

        // METHOD TO TEST DTO FIELDS HERE FOR TEST CREATE
        testCrudCredentialProgram_testDTOFieldsForTestCreateUpdate (expected, actual);

        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterCreate(actual.getMeta());

        // -------------------------------------
        // test read
        // -------------------------------------
        expected = actual;
        actual = testService.getCredentialProgram ( actual.getId(), contextInfo);
        assertEquals(expected.getId(), actual.getId());
        new IdNamelessEntityTester().check(expected, actual);

        // INSERT CODE FOR TESTING MORE DTO FIELDS HERE
        testCrudCredentialProgram_testDTOFieldsForTestCreateUpdate (expected, actual);

        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterGet(expected.getMeta(), actual.getMeta());

        // -------------------------------------
        // test update
        // -------------------------------------
        CredentialProgramInfo original = new CredentialProgramInfo (actual);
        expected = new CredentialProgramInfo (actual);

        expected.setStateKey(expected.getState() + "_Updated");

        // METHOD TO INSERT CODE TO UPDATE DTO FIELDS HERE
        testCrudCredentialProgram_setDTOFieldsForTestUpdate (expected);

        new AttributeTester().delete1Update1Add1ForUpdate(expected.getAttributes());
        // code to update
        actual = testService.updateCredentialProgram ( expected.getId(), expected, contextInfo);

        assertEquals(expected.getId(), actual.getId());
        new IdNamelessEntityTester().check(expected, actual);

        // METHOD TO INSERT CODE FOR TESTING DTO FIELDS HERE
        testCrudCredentialProgram_testDTOFieldsForTestCreateUpdate (expected, actual);

        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterUpdate(expected.getMeta(), actual.getMeta());

        // Test that VersionMissmatchException's are being detected
        boolean exception = false;
        try {
            testService.updateCredentialProgram ( original.getId(), original, contextInfo);
        }
        catch (VersionMismatchException e) {
            exception = true;			}

        Assert.assertTrue("VersionMissmatchException was not detected!", exception);

        // -------------------------------------
        // test read after update
        // -------------------------------------

        expected = actual;
        // code to get actual
        actual = testService.getCredentialProgram ( actual.getId(), contextInfo);

        assertEquals(expected.getId(), actual.getId());
        new IdNamelessEntityTester().check(expected, actual);

        // INSERT METHOD CODE FOR TESTING DTO FIELDS HERE
        testCrudCredentialProgram_testDTOFieldsForTestReadAfterUpdate (expected, actual);

        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterGet(expected.getMeta(), actual.getMeta());

        CredentialProgramInfo alphaDTO = actual;

        // create a 2nd DTO
        CredentialProgramInfo betaDTO = new CredentialProgramInfo ();

        // METHOD TO INSERT CODE TO SET MORE DTO FIELDS HERE
        testCrudCredentialProgram_setDTOFieldsForTestReadAfterUpdate (betaDTO);

        betaDTO.setTypeKey("typeKeyBeta");
        betaDTO.setStateKey("stateKeyBeta");
        betaDTO = testService.createCredentialProgram ( betaDTO.getTypeKey(), betaDTO, contextInfo);

        // -------------------------------------
        // test bulk get with no ids supplied
        // -------------------------------------

        List<String> credentialProgramIds = new ArrayList<String>();
        // code to get DTO by Ids
        List<CredentialProgramInfo> records = testService.getCredentialProgramsByIds ( credentialProgramIds, contextInfo);

        assertEquals(credentialProgramIds.size(), records.size());
        assertEquals(0, credentialProgramIds.size());

        // -------------------------------------
        // test bulk get
        // -------------------------------------
        credentialProgramIds = new ArrayList<String>();
        credentialProgramIds.add(alphaDTO.getId());
        credentialProgramIds.add(betaDTO.getId());
        // code to get DTO by Ids
        records = testService.getCredentialProgramsByIds ( credentialProgramIds, contextInfo);

        assertEquals(credentialProgramIds.size(), records.size());
        for (CredentialProgramInfo record : records)
        {
            if (!credentialProgramIds.remove(record.getId()))
            {
                fail(record.getId());
            }
        }
        assertEquals(0, credentialProgramIds.size());

        // -------------------------------------
        // test get by type
        // -------------------------------------
        // code to get by specific type "typeKey01"
        credentialProgramIds = testService.getCredentialProgramIdsByType ("typeKey_Updated", contextInfo);

        assertEquals(1, credentialProgramIds.size());
        assertEquals(alphaDTO.getId(), credentialProgramIds.get(0));

        // test get by other type
        // code to get by specific type "typeKeyBeta"
        credentialProgramIds = testService.getCredentialProgramIdsByType ("typeKeyBeta", contextInfo);

        assertEquals(1, credentialProgramIds.size());
        assertEquals(betaDTO.getId(), credentialProgramIds.get(0));

        // -------------------------------------
        // test delete
        // -------------------------------------

        StatusInfo status = testService.deleteCredentialProgram ( actual.getId(), contextInfo);

        assertNotNull(status);
        assertTrue(status.getIsSuccess());
        try
        {
            CredentialProgramInfo record = testService.getCredentialProgram ( actual.getId(), contextInfo);
            fail("Did not receive DoesNotExistException when attempting to get already-deleted entity");
        }
        catch (DoesNotExistException dnee)
        {
            // expected
        }

    }

    /*
        A method to set the fields for a CredentialProgram in a 'test create' section prior to calling the 'create' operation.
    */
    public abstract void testCrudCredentialProgram_setDTOFieldsForTestCreate(CredentialProgramInfo expected);

    /*
        A method to test the fields for a CredentialProgram. This is called after:
        - creating a DTO, where actual is the DTO returned by the create operation, and expected is the dto passed in to the create operation
        - reading a DTO after creating it, and actual is the read DTO, and expected is the dto that was created
        - updating a DTO, where actual is DTO returned by the update operation, and expected is the dto that was passed in to the update operation
    */
    public abstract void testCrudCredentialProgram_testDTOFieldsForTestCreateUpdate(CredentialProgramInfo expected, CredentialProgramInfo actual);

    /*
        A method to set the fields for a CredentialProgram in a 'test update' section prior to calling the 'update' operation.
    */
    public abstract void testCrudCredentialProgram_setDTOFieldsForTestUpdate(CredentialProgramInfo expected);

    /*
        A method to test the fields for a CredentialProgram after an update operation, followed by a read operation,
        where actual is the DTO returned by the read operation, and expected is the dto returned by the update operation.
    */
    public abstract void testCrudCredentialProgram_testDTOFieldsForTestReadAfterUpdate(CredentialProgramInfo expected, CredentialProgramInfo actual);

    /*
        A method to set the fields for a CredentialProgram in the 'test read after update' section.
        This dto is another (second) dto object being created for other tests.
    */
    public abstract void testCrudCredentialProgram_setDTOFieldsForTestReadAfterUpdate(CredentialProgramInfo expected);


    // ****************************************************
    //           MajorDisciplineInfo
    // ****************************************************
    @Test
    public void testCrudMajorDiscipline()
            throws AlreadyExistsException, DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException,
            VersionMismatchException,
            DependentObjectsExistException
    {
        // -------------------------------------
        // test create
        // -------------------------------------
        MajorDisciplineInfo expected = new MajorDisciplineInfo ();

        // METHOD TO SET DTO FIELDS HERE FOR TEST CREATE
        testCrudMajorDiscipline_setDTOFieldsForTestCreate (expected);

        new AttributeTester().add2ForCreate(expected.getAttributes());

        // code to create actual
        MajorDisciplineInfo actual = testService.createMajorDiscipline ( expected.getTypeKey(), expected, contextInfo);

        assertNotNull(actual.getId());
        new IdNamelessEntityTester().check(expected, actual);

        // METHOD TO TEST DTO FIELDS HERE FOR TEST CREATE
        testCrudMajorDiscipline_testDTOFieldsForTestCreateUpdate (expected, actual);

        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterCreate(actual.getMeta());

        // -------------------------------------
        // test read
        // -------------------------------------
        expected = actual;
        actual = testService.getMajorDiscipline ( actual.getId(), contextInfo);
        assertEquals(expected.getId(), actual.getId());
        new IdNamelessEntityTester().check(expected, actual);

        // INSERT CODE FOR TESTING MORE DTO FIELDS HERE
        testCrudMajorDiscipline_testDTOFieldsForTestCreateUpdate (expected, actual);

        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterGet(expected.getMeta(), actual.getMeta());

        // -------------------------------------
        // test update
        // -------------------------------------
        MajorDisciplineInfo original = new MajorDisciplineInfo (actual);
        expected = new MajorDisciplineInfo (actual);

        expected.setStateKey(expected.getState() + "_Updated");

        // METHOD TO INSERT CODE TO UPDATE DTO FIELDS HERE
        testCrudMajorDiscipline_setDTOFieldsForTestUpdate (expected);

        new AttributeTester().delete1Update1Add1ForUpdate(expected.getAttributes());
        // code to update
        actual = testService.updateMajorDiscipline ( expected.getId(), expected, contextInfo);

        assertEquals(expected.getId(), actual.getId());
        new IdNamelessEntityTester().check(expected, actual);

        // METHOD TO INSERT CODE FOR TESTING DTO FIELDS HERE
        testCrudMajorDiscipline_testDTOFieldsForTestCreateUpdate (expected, actual);

        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterUpdate(expected.getMeta(), actual.getMeta());

        // Test that VersionMissmatchException's are being detected
        boolean exception = false;
        try {
            testService.updateMajorDiscipline ( original.getId(), original, contextInfo);
        }
        catch (VersionMismatchException e) {
            exception = true;			}

        Assert.assertTrue("VersionMissmatchException was not detected!", exception);

        // -------------------------------------
        // test read after update
        // -------------------------------------

        expected = actual;
        // code to get actual
        actual = testService.getMajorDiscipline ( actual.getId(), contextInfo);

        assertEquals(expected.getId(), actual.getId());
        new IdNamelessEntityTester().check(expected, actual);

        // INSERT METHOD CODE FOR TESTING DTO FIELDS HERE
        testCrudMajorDiscipline_testDTOFieldsForTestReadAfterUpdate (expected, actual);

        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterGet(expected.getMeta(), actual.getMeta());

        MajorDisciplineInfo alphaDTO = actual;

        // create a 2nd DTO
        MajorDisciplineInfo betaDTO = new MajorDisciplineInfo ();

        // METHOD TO INSERT CODE TO SET MORE DTO FIELDS HERE
        testCrudMajorDiscipline_setDTOFieldsForTestReadAfterUpdate (betaDTO);

        betaDTO.setTypeKey("typeKeyBeta");
        betaDTO.setStateKey("stateKeyBeta");
        betaDTO = testService.createMajorDiscipline ( betaDTO.getTypeKey(), betaDTO, contextInfo);

        // -------------------------------------
        // test bulk get with no ids supplied
        // -------------------------------------

        List<String> majorDisciplineIds = new ArrayList<String>();
        // code to get DTO by Ids
        List<MajorDisciplineInfo> records = testService.getMajorDisciplinesByIds ( majorDisciplineIds, contextInfo);

        assertEquals(majorDisciplineIds.size(), records.size());
        assertEquals(0, majorDisciplineIds.size());

        // -------------------------------------
        // test bulk get
        // -------------------------------------
        majorDisciplineIds = new ArrayList<String>();
        majorDisciplineIds.add(alphaDTO.getId());
        majorDisciplineIds.add(betaDTO.getId());
        // code to get DTO by Ids
        records = testService.getMajorDisciplinesByIds ( majorDisciplineIds, contextInfo);

        assertEquals(majorDisciplineIds.size(), records.size());
        for (MajorDisciplineInfo record : records)
        {
            if (!majorDisciplineIds.remove(record.getId()))
            {
                fail(record.getId());
            }
        }
        assertEquals(0, majorDisciplineIds.size());

        // -------------------------------------
        // test get by type
        // -------------------------------------
        // code to get by specific type "typeKey01"
        majorDisciplineIds = testService.getMajorDisciplineIdsByType ("typeKey_Updated", contextInfo);

        assertEquals(1, majorDisciplineIds.size());
        assertEquals(alphaDTO.getId(), majorDisciplineIds.get(0));

        // test get by other type
        // code to get by specific type "typeKeyBeta"
        majorDisciplineIds = testService.getMajorDisciplineIdsByType ("typeKeyBeta", contextInfo);

        assertEquals(1, majorDisciplineIds.size());
        assertEquals(betaDTO.getId(), majorDisciplineIds.get(0));

        // -------------------------------------
        // test delete
        // -------------------------------------

        StatusInfo status = testService.deleteMajorDiscipline ( actual.getId(), contextInfo);

        assertNotNull(status);
        assertTrue(status.getIsSuccess());
        try
        {
            MajorDisciplineInfo record = testService.getMajorDiscipline ( actual.getId(), contextInfo);
            fail("Did not receive DoesNotExistException when attempting to get already-deleted entity");
        }
        catch (DoesNotExistException dnee)
        {
            // expected
        }

    }

    /*
        A method to set the fields for a MajorDiscipline in a 'test create' section prior to calling the 'create' operation.
    */
    public abstract void testCrudMajorDiscipline_setDTOFieldsForTestCreate(MajorDisciplineInfo expected);

    /*
        A method to test the fields for a MajorDiscipline. This is called after:
        - creating a DTO, where actual is the DTO returned by the create operation, and expected is the dto passed in to the create operation
        - reading a DTO after creating it, and actual is the read DTO, and expected is the dto that was created
        - updating a DTO, where actual is DTO returned by the update operation, and expected is the dto that was passed in to the update operation
    */
    public abstract void testCrudMajorDiscipline_testDTOFieldsForTestCreateUpdate(MajorDisciplineInfo expected, MajorDisciplineInfo actual);

    /*
        A method to set the fields for a MajorDiscipline in a 'test update' section prior to calling the 'update' operation.
    */
    public abstract void testCrudMajorDiscipline_setDTOFieldsForTestUpdate(MajorDisciplineInfo expected);

    /*
        A method to test the fields for a MajorDiscipline after an update operation, followed by a read operation,
        where actual is the DTO returned by the read operation, and expected is the dto returned by the update operation.
    */
    public abstract void testCrudMajorDiscipline_testDTOFieldsForTestReadAfterUpdate(MajorDisciplineInfo expected, MajorDisciplineInfo actual);

    /*
        A method to set the fields for a MajorDiscipline in the 'test read after update' section.
        This dto is another (second) dto object being created for other tests.
    */
    public abstract void testCrudMajorDiscipline_setDTOFieldsForTestReadAfterUpdate(MajorDisciplineInfo expected);


    // ****************************************************
    //           HonorsProgramInfo
    // ****************************************************
    @Test
    public void testCrudHonorsProgram()
            throws AlreadyExistsException, DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException,
            VersionMismatchException,
            DependentObjectsExistException
    {
        // -------------------------------------
        // test create
        // -------------------------------------
        HonorsProgramInfo expected = new HonorsProgramInfo ();

        // METHOD TO SET DTO FIELDS HERE FOR TEST CREATE
        testCrudHonorsProgram_setDTOFieldsForTestCreate (expected);

        new AttributeTester().add2ForCreate(expected.getAttributes());

        // code to create actual
        HonorsProgramInfo actual = testService.createHonorsProgram ( expected.getTypeKey(), expected, contextInfo);

        assertNotNull(actual.getId());
        new IdNamelessEntityTester().check(expected, actual);

        // METHOD TO TEST DTO FIELDS HERE FOR TEST CREATE
        testCrudHonorsProgram_testDTOFieldsForTestCreateUpdate (expected, actual);

        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterCreate(actual.getMeta());

        // -------------------------------------
        // test read
        // -------------------------------------
        expected = actual;
        actual = testService.getHonorsProgram ( actual.getId(), contextInfo);
        assertEquals(expected.getId(), actual.getId());
        new IdNamelessEntityTester().check(expected, actual);

        // INSERT CODE FOR TESTING MORE DTO FIELDS HERE
        testCrudHonorsProgram_testDTOFieldsForTestCreateUpdate (expected, actual);

        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterGet(expected.getMeta(), actual.getMeta());

        // -------------------------------------
        // test update
        // -------------------------------------
        HonorsProgramInfo original = new HonorsProgramInfo (actual);
        expected = new HonorsProgramInfo (actual);

        expected.setStateKey(expected.getState() + "_Updated");

        // METHOD TO INSERT CODE TO UPDATE DTO FIELDS HERE
        testCrudHonorsProgram_setDTOFieldsForTestUpdate (expected);

        new AttributeTester().delete1Update1Add1ForUpdate(expected.getAttributes());
        // code to update
        actual = testService.updateHonorsProgram ( expected.getId(), expected.getTypeKey(), expected, contextInfo);

        assertEquals(expected.getId(), actual.getId());
        new IdNamelessEntityTester().check(expected, actual);

        // METHOD TO INSERT CODE FOR TESTING DTO FIELDS HERE
        testCrudHonorsProgram_testDTOFieldsForTestCreateUpdate (expected, actual);

        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterUpdate(expected.getMeta(), actual.getMeta());

        // Test that VersionMissmatchException's are being detected
        boolean exception = false;
        try {
            testService.updateHonorsProgram ( original.getId(), original.getTypeKey(), original, contextInfo);
        }
        catch (VersionMismatchException e) {
            exception = true;			}

        Assert.assertTrue("VersionMissmatchException was not detected!", exception);

        // -------------------------------------
        // test read after update
        // -------------------------------------

        expected = actual;
        // code to get actual
        actual = testService.getHonorsProgram ( actual.getId(), contextInfo);

        assertEquals(expected.getId(), actual.getId());
        new IdNamelessEntityTester().check(expected, actual);

        // INSERT METHOD CODE FOR TESTING DTO FIELDS HERE
        testCrudHonorsProgram_testDTOFieldsForTestReadAfterUpdate (expected, actual);

        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterGet(expected.getMeta(), actual.getMeta());

        HonorsProgramInfo alphaDTO = actual;

        // create a 2nd DTO
        HonorsProgramInfo betaDTO = new HonorsProgramInfo ();

        // METHOD TO INSERT CODE TO SET MORE DTO FIELDS HERE
        testCrudHonorsProgram_setDTOFieldsForTestReadAfterUpdate (betaDTO);

        betaDTO.setTypeKey("typeKeyBeta");
        betaDTO.setStateKey("stateKeyBeta");
        betaDTO = testService.createHonorsProgram ( betaDTO.getTypeKey(), betaDTO, contextInfo);

        // -------------------------------------
        // test bulk get with no ids supplied
        // -------------------------------------

        List<String> honorsProgramIds = new ArrayList<String>();
        // code to get DTO by Ids
        List<HonorsProgramInfo> records = testService.getHonorsProgramsByIds ( honorsProgramIds, contextInfo);

        assertEquals(honorsProgramIds.size(), records.size());
        assertEquals(0, honorsProgramIds.size());

        // -------------------------------------
        // test bulk get
        // -------------------------------------
        honorsProgramIds = new ArrayList<String>();
        honorsProgramIds.add(alphaDTO.getId());
        honorsProgramIds.add(betaDTO.getId());
        // code to get DTO by Ids
        records = testService.getHonorsProgramsByIds ( honorsProgramIds, contextInfo);

        assertEquals(honorsProgramIds.size(), records.size());
        for (HonorsProgramInfo record : records)
        {
            if (!honorsProgramIds.remove(record.getId()))
            {
                fail(record.getId());
            }
        }
        assertEquals(0, honorsProgramIds.size());

        // -------------------------------------
        // test get by type
        // -------------------------------------
        // code to get by specific type "typeKey01"
        honorsProgramIds = testService.getHonorsProgramIdsByType ("typeKey_Updated", contextInfo);

        assertEquals(1, honorsProgramIds.size());
        assertEquals(alphaDTO.getId(), honorsProgramIds.get(0));

        // test get by other type
        // code to get by specific type "typeKeyBeta"
        honorsProgramIds = testService.getHonorsProgramIdsByType ("typeKeyBeta", contextInfo);

        assertEquals(1, honorsProgramIds.size());
        assertEquals(betaDTO.getId(), honorsProgramIds.get(0));

        // -------------------------------------
        // test delete
        // -------------------------------------

        StatusInfo status = testService.deleteHonorsProgram ( actual.getId(), contextInfo);

        assertNotNull(status);
        assertTrue(status.getIsSuccess());
        try
        {
            HonorsProgramInfo record = testService.getHonorsProgram ( actual.getId(), contextInfo);
            fail("Did not receive DoesNotExistException when attempting to get already-deleted entity");
        }
        catch (DoesNotExistException dnee)
        {
            // expected
        }

    }

    /*
        A method to set the fields for a HonorsProgram in a 'test create' section prior to calling the 'create' operation.
    */
    public abstract void testCrudHonorsProgram_setDTOFieldsForTestCreate(HonorsProgramInfo expected);

    /*
        A method to test the fields for a HonorsProgram. This is called after:
        - creating a DTO, where actual is the DTO returned by the create operation, and expected is the dto passed in to the create operation
        - reading a DTO after creating it, and actual is the read DTO, and expected is the dto that was created
        - updating a DTO, where actual is DTO returned by the update operation, and expected is the dto that was passed in to the update operation
    */
    public abstract void testCrudHonorsProgram_testDTOFieldsForTestCreateUpdate(HonorsProgramInfo expected, HonorsProgramInfo actual);

    /*
        A method to set the fields for a HonorsProgram in a 'test update' section prior to calling the 'update' operation.
    */
    public abstract void testCrudHonorsProgram_setDTOFieldsForTestUpdate(HonorsProgramInfo expected);

    /*
        A method to test the fields for a HonorsProgram after an update operation, followed by a read operation,
        where actual is the DTO returned by the read operation, and expected is the dto returned by the update operation.
    */
    public abstract void testCrudHonorsProgram_testDTOFieldsForTestReadAfterUpdate(HonorsProgramInfo expected, HonorsProgramInfo actual);

    /*
        A method to set the fields for a HonorsProgram in the 'test read after update' section.
        This dto is another (second) dto object being created for other tests.
    */
    public abstract void testCrudHonorsProgram_setDTOFieldsForTestReadAfterUpdate(HonorsProgramInfo expected);


    // ****************************************************
    //           CoreProgramInfo
    // ****************************************************
    @Test
    public void testCrudCoreProgram()
            throws AlreadyExistsException, DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException,
            VersionMismatchException,
            DependentObjectsExistException
    {
        // -------------------------------------
        // test create
        // -------------------------------------
        CoreProgramInfo expected = new CoreProgramInfo ();

        // METHOD TO SET DTO FIELDS HERE FOR TEST CREATE
        testCrudCoreProgram_setDTOFieldsForTestCreate (expected);

        new AttributeTester().add2ForCreate(expected.getAttributes());

        // code to create actual
        CoreProgramInfo actual = testService.createCoreProgram ( expected.getTypeKey(), expected, contextInfo);

        assertNotNull(actual.getId());
        new IdNamelessEntityTester().check(expected, actual);

        // METHOD TO TEST DTO FIELDS HERE FOR TEST CREATE
        testCrudCoreProgram_testDTOFieldsForTestCreateUpdate (expected, actual);

        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterCreate(actual.getMeta());

        // -------------------------------------
        // test read
        // -------------------------------------
        expected = actual;
        actual = testService.getCoreProgram ( actual.getId(), contextInfo);
        assertEquals(expected.getId(), actual.getId());
        new IdNamelessEntityTester().check(expected, actual);

        // INSERT CODE FOR TESTING MORE DTO FIELDS HERE
        testCrudCoreProgram_testDTOFieldsForTestCreateUpdate (expected, actual);

        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterGet(expected.getMeta(), actual.getMeta());

        // -------------------------------------
        // test update
        // -------------------------------------
        CoreProgramInfo original = new CoreProgramInfo (actual);
        expected = new CoreProgramInfo (actual);

        expected.setStateKey(expected.getState() + "_Updated");

        // METHOD TO INSERT CODE TO UPDATE DTO FIELDS HERE
        testCrudCoreProgram_setDTOFieldsForTestUpdate (expected);

        new AttributeTester().delete1Update1Add1ForUpdate(expected.getAttributes());
        // code to update
        actual = testService.updateCoreProgram ( expected.getId(), expected.getTypeKey(), expected, contextInfo);

        assertEquals(expected.getId(), actual.getId());
        new IdNamelessEntityTester().check(expected, actual);

        // METHOD TO INSERT CODE FOR TESTING DTO FIELDS HERE
        testCrudCoreProgram_testDTOFieldsForTestCreateUpdate (expected, actual);

        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterUpdate(expected.getMeta(), actual.getMeta());

        // Test that VersionMissmatchException's are being detected
        boolean exception = false;
        try {
            testService.updateCoreProgram ( original.getId(), original.getTypeKey(), original, contextInfo);
        }
        catch (VersionMismatchException e) {
            exception = true;			}

        Assert.assertTrue("VersionMissmatchException was not detected!", exception);

        // -------------------------------------
        // test read after update
        // -------------------------------------

        expected = actual;
        // code to get actual
        actual = testService.getCoreProgram ( actual.getId(), contextInfo);

        assertEquals(expected.getId(), actual.getId());
        new IdNamelessEntityTester().check(expected, actual);

        // INSERT METHOD CODE FOR TESTING DTO FIELDS HERE
        testCrudCoreProgram_testDTOFieldsForTestReadAfterUpdate (expected, actual);

        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterGet(expected.getMeta(), actual.getMeta());

        CoreProgramInfo alphaDTO = actual;

        // create a 2nd DTO
        CoreProgramInfo betaDTO = new CoreProgramInfo ();

        // METHOD TO INSERT CODE TO SET MORE DTO FIELDS HERE
        testCrudCoreProgram_setDTOFieldsForTestReadAfterUpdate (betaDTO);

        betaDTO.setTypeKey("typeKeyBeta");
        betaDTO.setStateKey("stateKeyBeta");
        betaDTO = testService.createCoreProgram ( betaDTO.getTypeKey(), betaDTO, contextInfo);

        // -------------------------------------
        // test bulk get with no ids supplied
        // -------------------------------------

        List<String> coreProgramIds = new ArrayList<String>();
        // code to get DTO by Ids
        List<CoreProgramInfo> records = testService.getCoreProgramsByIds ( coreProgramIds, contextInfo);

        assertEquals(coreProgramIds.size(), records.size());
        assertEquals(0, coreProgramIds.size());

        // -------------------------------------
        // test bulk get
        // -------------------------------------
        coreProgramIds = new ArrayList<String>();
        coreProgramIds.add(alphaDTO.getId());
        coreProgramIds.add(betaDTO.getId());
        // code to get DTO by Ids
        records = testService.getCoreProgramsByIds ( coreProgramIds, contextInfo);

        assertEquals(coreProgramIds.size(), records.size());
        for (CoreProgramInfo record : records)
        {
            if (!coreProgramIds.remove(record.getId()))
            {
                fail(record.getId());
            }
        }
        assertEquals(0, coreProgramIds.size());

        // -------------------------------------
        // test get by type
        // -------------------------------------
        // code to get by specific type "typeKey01"
        coreProgramIds = testService.getCoreProgramIdsByType ("typeKey_Updated", contextInfo);

        assertEquals(1, coreProgramIds.size());
        assertEquals(alphaDTO.getId(), coreProgramIds.get(0));

        // test get by other type
        // code to get by specific type "typeKeyBeta"
        coreProgramIds = testService.getCoreProgramIdsByType ("typeKeyBeta", contextInfo);

        assertEquals(1, coreProgramIds.size());
        assertEquals(betaDTO.getId(), coreProgramIds.get(0));

        // -------------------------------------
        // test delete
        // -------------------------------------

        StatusInfo status = testService.deleteCoreProgram ( actual.getId(), contextInfo);

        assertNotNull(status);
        assertTrue(status.getIsSuccess());
        try
        {
            CoreProgramInfo record = testService.getCoreProgram ( actual.getId(), contextInfo);
            fail("Did not receive DoesNotExistException when attempting to get already-deleted entity");
        }
        catch (DoesNotExistException dnee)
        {
            // expected
        }

    }

    /*
        A method to set the fields for a CoreProgram in a 'test create' section prior to calling the 'create' operation.
    */
    public abstract void testCrudCoreProgram_setDTOFieldsForTestCreate(CoreProgramInfo expected);

    /*
        A method to test the fields for a CoreProgram. This is called after:
        - creating a DTO, where actual is the DTO returned by the create operation, and expected is the dto passed in to the create operation
        - reading a DTO after creating it, and actual is the read DTO, and expected is the dto that was created
        - updating a DTO, where actual is DTO returned by the update operation, and expected is the dto that was passed in to the update operation
    */
    public abstract void testCrudCoreProgram_testDTOFieldsForTestCreateUpdate(CoreProgramInfo expected, CoreProgramInfo actual);

    /*
        A method to set the fields for a CoreProgram in a 'test update' section prior to calling the 'update' operation.
    */
    public abstract void testCrudCoreProgram_setDTOFieldsForTestUpdate(CoreProgramInfo expected);

    /*
        A method to test the fields for a CoreProgram after an update operation, followed by a read operation,
        where actual is the DTO returned by the read operation, and expected is the dto returned by the update operation.
    */
    public abstract void testCrudCoreProgram_testDTOFieldsForTestReadAfterUpdate(CoreProgramInfo expected, CoreProgramInfo actual);

    /*
        A method to set the fields for a CoreProgram in the 'test read after update' section.
        This dto is another (second) dto object being created for other tests.
    */
    public abstract void testCrudCoreProgram_setDTOFieldsForTestReadAfterUpdate(CoreProgramInfo expected);


    // ****************************************************
    //           ProgramRequirementInfo
    // ****************************************************
    @Test
    public void testCrudProgramRequirement()
            throws AlreadyExistsException, DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException,
            VersionMismatchException,
            DependentObjectsExistException
    {
        // -------------------------------------
        // test create
        // -------------------------------------
        ProgramRequirementInfo expected = new ProgramRequirementInfo ();

        // METHOD TO SET DTO FIELDS HERE FOR TEST CREATE
        testCrudProgramRequirement_setDTOFieldsForTestCreate (expected);

        new AttributeTester().add2ForCreate(expected.getAttributes());

        // code to create actual
        ProgramRequirementInfo actual = testService.createProgramRequirement ( expected.getTypeKey(), expected, contextInfo);

        assertNotNull(actual.getId());
        new IdNamelessEntityTester().check(expected, actual);

        // METHOD TO TEST DTO FIELDS HERE FOR TEST CREATE
        testCrudProgramRequirement_testDTOFieldsForTestCreateUpdate (expected, actual);

        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterCreate(actual.getMeta());

        // -------------------------------------
        // test read
        // -------------------------------------
        expected = actual;
        actual = testService.getProgramRequirement ( actual.getId(), contextInfo);
        assertEquals(expected.getId(), actual.getId());
        new IdNamelessEntityTester().check(expected, actual);

        // INSERT CODE FOR TESTING MORE DTO FIELDS HERE
        testCrudProgramRequirement_testDTOFieldsForTestCreateUpdate (expected, actual);

        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterGet(expected.getMeta(), actual.getMeta());

        // -------------------------------------
        // test update
        // -------------------------------------
        ProgramRequirementInfo original = new ProgramRequirementInfo (actual);
        expected = new ProgramRequirementInfo (actual);

        expected.setStateKey(expected.getState() + "_Updated");

        // METHOD TO INSERT CODE TO UPDATE DTO FIELDS HERE
        testCrudProgramRequirement_setDTOFieldsForTestUpdate (expected);

        new AttributeTester().delete1Update1Add1ForUpdate(expected.getAttributes());
        // code to update
        actual = testService.updateProgramRequirement ( expected.getId(), expected.getTypeKey(), expected, contextInfo);

        assertEquals(expected.getId(), actual.getId());
        new IdNamelessEntityTester().check(expected, actual);

        // METHOD TO INSERT CODE FOR TESTING DTO FIELDS HERE
        testCrudProgramRequirement_testDTOFieldsForTestCreateUpdate (expected, actual);

        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterUpdate(expected.getMeta(), actual.getMeta());

        // Test that VersionMissmatchException's are being detected
        boolean exception = false;
        try {
            testService.updateProgramRequirement ( original.getId(), original.getTypeKey(), original, contextInfo);
        }
        catch (VersionMismatchException e) {
            exception = true;			}

        Assert.assertTrue("VersionMissmatchException was not detected!", exception);

        // -------------------------------------
        // test read after update
        // -------------------------------------

        expected = actual;
        // code to get actual
        actual = testService.getProgramRequirement ( actual.getId(), contextInfo);

        assertEquals(expected.getId(), actual.getId());
        new IdNamelessEntityTester().check(expected, actual);

        // INSERT METHOD CODE FOR TESTING DTO FIELDS HERE
        testCrudProgramRequirement_testDTOFieldsForTestReadAfterUpdate (expected, actual);

        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterGet(expected.getMeta(), actual.getMeta());

        ProgramRequirementInfo alphaDTO = actual;

        // create a 2nd DTO
        ProgramRequirementInfo betaDTO = new ProgramRequirementInfo ();

        // METHOD TO INSERT CODE TO SET MORE DTO FIELDS HERE
        testCrudProgramRequirement_setDTOFieldsForTestReadAfterUpdate (betaDTO);

        betaDTO.setTypeKey("typeKeyBeta");
        betaDTO.setStateKey("stateKeyBeta");
        betaDTO = testService.createProgramRequirement ( betaDTO.getTypeKey(), betaDTO, contextInfo);

        // -------------------------------------
        // test bulk get with no ids supplied
        // -------------------------------------

        List<String> programRequirementIds = new ArrayList<String>();
        // code to get DTO by Ids
        List<ProgramRequirementInfo> records = testService.getProgramRequirementsByIds ( programRequirementIds, contextInfo);

        assertEquals(programRequirementIds.size(), records.size());
        assertEquals(0, programRequirementIds.size());

        // -------------------------------------
        // test bulk get
        // -------------------------------------
        programRequirementIds = new ArrayList<String>();
        programRequirementIds.add(alphaDTO.getId());
        programRequirementIds.add(betaDTO.getId());
        // code to get DTO by Ids
        records = testService.getProgramRequirementsByIds ( programRequirementIds, contextInfo);

        assertEquals(programRequirementIds.size(), records.size());
        for (ProgramRequirementInfo record : records)
        {
            if (!programRequirementIds.remove(record.getId()))
            {
                fail(record.getId());
            }
        }
        assertEquals(0, programRequirementIds.size());

        // -------------------------------------
        // test get by type
        // -------------------------------------
        // code to get by specific type "typeKey01"
        programRequirementIds = testService.getProgramRequirementIdsByType ("typeKey_Updated", contextInfo);

        assertEquals(1, programRequirementIds.size());
        assertEquals(alphaDTO.getId(), programRequirementIds.get(0));

        // test get by other type
        // code to get by specific type "typeKeyBeta"
        programRequirementIds = testService.getProgramRequirementIdsByType ("typeKeyBeta", contextInfo);

        assertEquals(1, programRequirementIds.size());
        assertEquals(betaDTO.getId(), programRequirementIds.get(0));

        // -------------------------------------
        // test delete
        // -------------------------------------

        StatusInfo status = testService.deleteProgramRequirement ( actual.getId(), contextInfo);

        assertNotNull(status);
        assertTrue(status.getIsSuccess());
        try
        {
            ProgramRequirementInfo record = testService.getProgramRequirement ( actual.getId(), contextInfo);
            fail("Did not receive DoesNotExistException when attempting to get already-deleted entity");
        }
        catch (DoesNotExistException dnee)
        {
            // expected
        }

    }

    /*
        A method to set the fields for a ProgramRequirement in a 'test create' section prior to calling the 'create' operation.
    */
    public abstract void testCrudProgramRequirement_setDTOFieldsForTestCreate(ProgramRequirementInfo expected);

    /*
        A method to test the fields for a ProgramRequirement. This is called after:
        - creating a DTO, where actual is the DTO returned by the create operation, and expected is the dto passed in to the create operation
        - reading a DTO after creating it, and actual is the read DTO, and expected is the dto that was created
        - updating a DTO, where actual is DTO returned by the update operation, and expected is the dto that was passed in to the update operation
    */
    public abstract void testCrudProgramRequirement_testDTOFieldsForTestCreateUpdate(ProgramRequirementInfo expected, ProgramRequirementInfo actual);

    /*
        A method to set the fields for a ProgramRequirement in a 'test update' section prior to calling the 'update' operation.
    */
    public abstract void testCrudProgramRequirement_setDTOFieldsForTestUpdate(ProgramRequirementInfo expected);

    /*
        A method to test the fields for a ProgramRequirement after an update operation, followed by a read operation,
        where actual is the DTO returned by the read operation, and expected is the dto returned by the update operation.
    */
    public abstract void testCrudProgramRequirement_testDTOFieldsForTestReadAfterUpdate(ProgramRequirementInfo expected, ProgramRequirementInfo actual);

    /*
        A method to set the fields for a ProgramRequirement in the 'test read after update' section.
        This dto is another (second) dto object being created for other tests.
    */
    public abstract void testCrudProgramRequirement_setDTOFieldsForTestReadAfterUpdate(ProgramRequirementInfo expected);


    // ****************************************************
    //           MinorDisciplineInfo
    // ****************************************************
    @Test
    public void testCrudMinorDiscipline()
            throws AlreadyExistsException, DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException,
            VersionMismatchException,
            DependentObjectsExistException
    {
        // -------------------------------------
        // test create
        // -------------------------------------
        MinorDisciplineInfo expected = new MinorDisciplineInfo ();

        // METHOD TO SET DTO FIELDS HERE FOR TEST CREATE
        testCrudMinorDiscipline_setDTOFieldsForTestCreate (expected);

        new AttributeTester().add2ForCreate(expected.getAttributes());

        // code to create actual
        MinorDisciplineInfo actual = testService.createMinorDiscipline ( expected.getTypeKey(), expected, contextInfo);

        assertNotNull(actual.getId());
        new IdNamelessEntityTester().check(expected, actual);

        // METHOD TO TEST DTO FIELDS HERE FOR TEST CREATE
        testCrudMinorDiscipline_testDTOFieldsForTestCreateUpdate (expected, actual);

        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterCreate(actual.getMeta());

        // -------------------------------------
        // test read
        // -------------------------------------
        expected = actual;
        actual = testService.getMinorDiscipline ( actual.getId(), contextInfo);
        assertEquals(expected.getId(), actual.getId());
        new IdNamelessEntityTester().check(expected, actual);

        // INSERT CODE FOR TESTING MORE DTO FIELDS HERE
        testCrudMinorDiscipline_testDTOFieldsForTestCreateUpdate (expected, actual);

        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterGet(expected.getMeta(), actual.getMeta());

        // -------------------------------------
        // test update
        // -------------------------------------
        MinorDisciplineInfo original = new MinorDisciplineInfo (actual);
        expected = new MinorDisciplineInfo (actual);

        expected.setStateKey(expected.getState() + "_Updated");

        // METHOD TO INSERT CODE TO UPDATE DTO FIELDS HERE
        testCrudMinorDiscipline_setDTOFieldsForTestUpdate (expected);

        new AttributeTester().delete1Update1Add1ForUpdate(expected.getAttributes());
        // code to update
        actual = testService.updateMinorDiscipline ( expected.getId(), expected.getTypeKey(), expected, contextInfo);

        assertEquals(expected.getId(), actual.getId());
        new IdNamelessEntityTester().check(expected, actual);

        // METHOD TO INSERT CODE FOR TESTING DTO FIELDS HERE
        testCrudMinorDiscipline_testDTOFieldsForTestCreateUpdate (expected, actual);

        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterUpdate(expected.getMeta(), actual.getMeta());

        // Test that VersionMissmatchException's are being detected
        boolean exception = false;
        try {
            testService.updateMinorDiscipline ( original.getId(), original.getTypeKey(), original, contextInfo);
        }
        catch (VersionMismatchException e) {
            exception = true;			}

        Assert.assertTrue("VersionMissmatchException was not detected!", exception);

        // -------------------------------------
        // test read after update
        // -------------------------------------

        expected = actual;
        // code to get actual
        actual = testService.getMinorDiscipline ( actual.getId(), contextInfo);

        assertEquals(expected.getId(), actual.getId());
        new IdNamelessEntityTester().check(expected, actual);

        // INSERT METHOD CODE FOR TESTING DTO FIELDS HERE
        testCrudMinorDiscipline_testDTOFieldsForTestReadAfterUpdate (expected, actual);

        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterGet(expected.getMeta(), actual.getMeta());

        MinorDisciplineInfo alphaDTO = actual;

        // create a 2nd DTO
        MinorDisciplineInfo betaDTO = new MinorDisciplineInfo ();

        // METHOD TO INSERT CODE TO SET MORE DTO FIELDS HERE
        testCrudMinorDiscipline_setDTOFieldsForTestReadAfterUpdate (betaDTO);

        betaDTO.setTypeKey("typeKeyBeta");
        betaDTO.setStateKey("stateKeyBeta");
        betaDTO = testService.createMinorDiscipline ( betaDTO.getTypeKey(), betaDTO, contextInfo);

        // -------------------------------------
        // test bulk get with no ids supplied
        // -------------------------------------

        List<String> minorDisciplineIds = new ArrayList<String>();
        // code to get DTO by Ids
        List<MinorDisciplineInfo> records = testService.getMinorDisciplinesByIds ( minorDisciplineIds, contextInfo);

        assertEquals(minorDisciplineIds.size(), records.size());
        assertEquals(0, minorDisciplineIds.size());

        // -------------------------------------
        // test bulk get
        // -------------------------------------
        minorDisciplineIds = new ArrayList<String>();
        minorDisciplineIds.add(alphaDTO.getId());
        minorDisciplineIds.add(betaDTO.getId());
        // code to get DTO by Ids
        records = testService.getMinorDisciplinesByIds ( minorDisciplineIds, contextInfo);

        assertEquals(minorDisciplineIds.size(), records.size());
        for (MinorDisciplineInfo record : records)
        {
            if (!minorDisciplineIds.remove(record.getId()))
            {
                fail(record.getId());
            }
        }
        assertEquals(0, minorDisciplineIds.size());

        // -------------------------------------
        // test get by type
        // -------------------------------------
        // code to get by specific type "typeKey01"
        minorDisciplineIds = testService.getMinorDisciplineIdsByType ("typeKey_Updated", contextInfo);

        assertEquals(1, minorDisciplineIds.size());
        assertEquals(alphaDTO.getId(), minorDisciplineIds.get(0));

        // test get by other type
        // code to get by specific type "typeKeyBeta"
        minorDisciplineIds = testService.getMinorDisciplineIdsByType ("typeKeyBeta", contextInfo);

        assertEquals(1, minorDisciplineIds.size());
        assertEquals(betaDTO.getId(), minorDisciplineIds.get(0));

        // -------------------------------------
        // test delete
        // -------------------------------------

        StatusInfo status = testService.deleteMinorDiscipline ( actual.getId(), contextInfo);

        assertNotNull(status);
        assertTrue(status.getIsSuccess());
        try
        {
            MinorDisciplineInfo record = testService.getMinorDiscipline ( actual.getId(), contextInfo);
            fail("Did not receive DoesNotExistException when attempting to get already-deleted entity");
        }
        catch (DoesNotExistException dnee)
        {
            // expected
        }

    }

    /*
        A method to set the fields for a MinorDiscipline in a 'test create' section prior to calling the 'create' operation.
    */
    public abstract void testCrudMinorDiscipline_setDTOFieldsForTestCreate(MinorDisciplineInfo expected);

    /*
        A method to test the fields for a MinorDiscipline. This is called after:
        - creating a DTO, where actual is the DTO returned by the create operation, and expected is the dto passed in to the create operation
        - reading a DTO after creating it, and actual is the read DTO, and expected is the dto that was created
        - updating a DTO, where actual is DTO returned by the update operation, and expected is the dto that was passed in to the update operation
    */
    public abstract void testCrudMinorDiscipline_testDTOFieldsForTestCreateUpdate(MinorDisciplineInfo expected, MinorDisciplineInfo actual);

    /*
        A method to set the fields for a MinorDiscipline in a 'test update' section prior to calling the 'update' operation.
    */
    public abstract void testCrudMinorDiscipline_setDTOFieldsForTestUpdate(MinorDisciplineInfo expected);

    /*
        A method to test the fields for a MinorDiscipline after an update operation, followed by a read operation,
        where actual is the DTO returned by the read operation, and expected is the dto returned by the update operation.
    */
    public abstract void testCrudMinorDiscipline_testDTOFieldsForTestReadAfterUpdate(MinorDisciplineInfo expected, MinorDisciplineInfo actual);

    /*
        A method to set the fields for a MinorDiscipline in the 'test read after update' section.
        This dto is another (second) dto object being created for other tests.
    */
    public abstract void testCrudMinorDiscipline_setDTOFieldsForTestReadAfterUpdate(MinorDisciplineInfo expected);


    // ****************************************************
    //           TrackInfo
    // ****************************************************
    @Test
    public void testCrudTrack()
            throws AlreadyExistsException, DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException,
            VersionMismatchException,
            DependentObjectsExistException
    {
        // -------------------------------------
        // test create
        // -------------------------------------
        TrackInfo expected = new TrackInfo ();

        // METHOD TO SET DTO FIELDS HERE FOR TEST CREATE
        testCrudTrack_setDTOFieldsForTestCreate (expected);

        new AttributeTester().add2ForCreate(expected.getAttributes());

        // code to create actual
        TrackInfo actual = testService.createTrack ( expected.getId(), expected.getTypeKey(), expected, contextInfo);

        assertNotNull(actual.getId());
        new IdNamelessEntityTester().check(expected, actual);

        // METHOD TO TEST DTO FIELDS HERE FOR TEST CREATE
        testCrudTrack_testDTOFieldsForTestCreateUpdate (expected, actual);

        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterCreate(actual.getMeta());

        // -------------------------------------
        // test read
        // -------------------------------------
        expected = actual;
        actual = testService.getTrack ( actual.getId(), contextInfo);
        assertEquals(expected.getId(), actual.getId());
        new IdNamelessEntityTester().check(expected, actual);

        // INSERT CODE FOR TESTING MORE DTO FIELDS HERE
        testCrudTrack_testDTOFieldsForTestCreateUpdate (expected, actual);

        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterGet(expected.getMeta(), actual.getMeta());

        // -------------------------------------
        // test update
        // -------------------------------------
        TrackInfo original = new TrackInfo (actual);
        expected = new TrackInfo (actual);

        expected.setStateKey(expected.getState() + "_Updated");

        // METHOD TO INSERT CODE TO UPDATE DTO FIELDS HERE
        testCrudTrack_setDTOFieldsForTestUpdate (expected);

        new AttributeTester().delete1Update1Add1ForUpdate(expected.getAttributes());
        // code to update
        actual = testService.updateTrack ( expected.getId(), expected.getTypeKey(), expected, contextInfo);

        assertEquals(expected.getId(), actual.getId());
        new IdNamelessEntityTester().check(expected, actual);

        // METHOD TO INSERT CODE FOR TESTING DTO FIELDS HERE
        testCrudTrack_testDTOFieldsForTestCreateUpdate (expected, actual);

        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterUpdate(expected.getMeta(), actual.getMeta());

        // Test that VersionMissmatchException's are being detected
        boolean exception = false;
        try {
            testService.updateTrack ( original.getId(), original.getTypeKey(), original, contextInfo);
        }
        catch (VersionMismatchException e) {
            exception = true;			}

        Assert.assertTrue("VersionMissmatchException was not detected!", exception);

        // -------------------------------------
        // test read after update
        // -------------------------------------

        expected = actual;
        // code to get actual
        actual = testService.getTrack ( actual.getId(), contextInfo);

        assertEquals(expected.getId(), actual.getId());
        new IdNamelessEntityTester().check(expected, actual);

        // INSERT METHOD CODE FOR TESTING DTO FIELDS HERE
        testCrudTrack_testDTOFieldsForTestReadAfterUpdate (expected, actual);

        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterGet(expected.getMeta(), actual.getMeta());

        TrackInfo alphaDTO = actual;

        // create a 2nd DTO
        TrackInfo betaDTO = new TrackInfo ();

        // METHOD TO INSERT CODE TO SET MORE DTO FIELDS HERE
        testCrudTrack_setDTOFieldsForTestReadAfterUpdate (betaDTO);

        betaDTO.setTypeKey("typeKeyBeta");
        betaDTO.setStateKey("stateKeyBeta");
        betaDTO = testService.createTrack ( betaDTO.getId(), betaDTO.getTypeKey(), betaDTO, contextInfo);

        // -------------------------------------
        // test bulk get with no ids supplied
        // -------------------------------------

        List<String> trackIds = new ArrayList<String>();
        // code to get DTO by Ids
        List<TrackInfo> records = testService.getTracksByIds ( trackIds, contextInfo);

        assertEquals(trackIds.size(), records.size());
        assertEquals(0, trackIds.size());

        // -------------------------------------
        // test bulk get
        // -------------------------------------
        trackIds = new ArrayList<String>();
        trackIds.add(alphaDTO.getId());
        trackIds.add(betaDTO.getId());
        // code to get DTO by Ids
        records = testService.getTracksByIds ( trackIds, contextInfo);

        assertEquals(trackIds.size(), records.size());
        for (TrackInfo record : records)
        {
            if (!trackIds.remove(record.getId()))
            {
                fail(record.getId());
            }
        }
        assertEquals(0, trackIds.size());

        // -------------------------------------
        // test get by type
        // -------------------------------------
        // code to get by specific type "typeKey01"
        trackIds = testService.getTrackIdsByType ("typeKey_Updated", contextInfo);

        assertEquals(1, trackIds.size());
        assertEquals(alphaDTO.getId(), trackIds.get(0));

        // test get by other type
        // code to get by specific type "typeKeyBeta"
        trackIds = testService.getTrackIdsByType ("typeKeyBeta", contextInfo);

        assertEquals(1, trackIds.size());
        assertEquals(betaDTO.getId(), trackIds.get(0));

        // -------------------------------------
        // test delete
        // -------------------------------------

        StatusInfo status = testService.deleteTrack ( actual.getId(), contextInfo);

        assertNotNull(status);
        assertTrue(status.getIsSuccess());
        try
        {
            TrackInfo record = testService.getTrack ( actual.getId(), contextInfo);
            fail("Did not receive DoesNotExistException when attempting to get already-deleted entity");
        }
        catch (DoesNotExistException dnee)
        {
            // expected
        }

    }

    /*
        A method to set the fields for a Track in a 'test create' section prior to calling the 'create' operation.
    */
    public abstract void testCrudTrack_setDTOFieldsForTestCreate(TrackInfo expected);

    /*
        A method to test the fields for a Track. This is called after:
        - creating a DTO, where actual is the DTO returned by the create operation, and expected is the dto passed in to the create operation
        - reading a DTO after creating it, and actual is the read DTO, and expected is the dto that was created
        - updating a DTO, where actual is DTO returned by the update operation, and expected is the dto that was passed in to the update operation
    */
    public abstract void testCrudTrack_testDTOFieldsForTestCreateUpdate(TrackInfo expected, TrackInfo actual);

    /*
        A method to set the fields for a Track in a 'test update' section prior to calling the 'update' operation.
    */
    public abstract void testCrudTrack_setDTOFieldsForTestUpdate(TrackInfo expected);

    /*
        A method to test the fields for a Track after an update operation, followed by a read operation,
        where actual is the DTO returned by the read operation, and expected is the dto returned by the update operation.
    */
    public abstract void testCrudTrack_testDTOFieldsForTestReadAfterUpdate(TrackInfo expected, TrackInfo actual);

    /*
        A method to set the fields for a Track in the 'test read after update' section.
        This dto is another (second) dto object being created for other tests.
    */
    public abstract void testCrudTrack_setDTOFieldsForTestReadAfterUpdate(TrackInfo expected);


    // ========================================
    // SERVICE OPS TESTED IN BASE TEST CLASS
    // ========================================
	
	/*
		The following methods are tested as part of CRUD operations for this service's DTOs:
			getCredentialProgram
			getCredentialProgramIdsByType
			getCredentialProgramsByIds
			createCredentialProgram
			createNewCredentialProgramVersion
			updateCredentialProgram
			deleteCredentialProgram
			getMajorDiscipline
			getMajorDisciplineIdsByType
			getMajorDisciplinesByIds
			createMajorDiscipline
			updateMajorDiscipline
			deleteMajorDiscipline
			createNewMajorDisciplineVersion
			getHonorsProgram
			getHonorsProgramIdsByType
			getHonorsProgramsByIds
			createHonorsProgram
			updateHonorsProgram
			deleteHonorsProgram
			getCoreProgram
			getCoreProgramIdsByType
			getCoreProgramsByIds
			createCoreProgram
			createNewCoreProgramVersion
			updateCoreProgram
			deleteCoreProgram
			getProgramRequirement
			getProgramRequirementIdsByType
			getProgramRequirementsByIds
			createProgramRequirement
			updateProgramRequirement
			deleteProgramRequirement
			getMinorDiscipline
			getMinorDisciplinesByIds
			getMinorDisciplineIdsByType
			createMinorDiscipline
			updateMinorDiscipline
			deleteMinorDiscipline
			createNewMinorDisciplineVersion
			getTrack
			getTracksByIds
			getTrackIdsByType
			createTrack
			updateTrack
			deleteTrack
	*/

    // ========================================
    // SERVICE OPS NOT TESTED IN BASE TEST CLASS
    // ========================================

    /* Method Name: validateCredentialProgram */
    @Test
    public abstract void test_validateCredentialProgram()
            throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	;

    /* Method Name: setCurrentCredentialProgramVersion */
    @Test
    public abstract void test_setCurrentCredentialProgramVersion()
            throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,IllegalVersionSequencingException	,OperationFailedException	,PermissionDeniedException	,DataValidationErrorException	;

    /* Method Name: searchForCredentialProgramIds */
    @Test
    public abstract void test_searchForCredentialProgramIds()
            throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;

    /* Method Name: searchForCredentialPrograms */
    @Test
    public abstract void test_searchForCredentialPrograms()
            throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;

    /* Method Name: getMajorDisciplineIdsByCredentialProgramType */
    @Test
    public abstract void test_getMajorDisciplineIdsByCredentialProgramType()
            throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	;

    /* Method Name: getProgramVariationsByMajorDiscipline */
    @Test
    public abstract void test_getProgramVariationsByMajorDiscipline()
            throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	;

    /* Method Name: validateMajorDiscipline */
    @Test
    public abstract void test_validateMajorDiscipline()
            throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;

    /* Method Name: searchForMajorDisciplineIds */
    @Test
    public abstract void test_searchForMajorDisciplineIds()
            throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;

    /* Method Name: searchForMajorDisciplines */
    @Test
    public abstract void test_searchForMajorDisciplines()
            throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;

    /* Method Name: getHonorProgramIdsByCredentialProgramType */
    @Test
    public abstract void test_getHonorProgramIdsByCredentialProgramType()
            throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	;

    /* Method Name: validateHonorsProgram */
    @Test
    public abstract void test_validateHonorsProgram()
            throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	;

    /* Method Name: searchForHonorsProgramIds */
    @Test
    public abstract void test_searchForHonorsProgramIds()
            throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;

    /* Method Name: searchForHonorsPrograms */
    @Test
    public abstract void test_searchForHonorsPrograms()
            throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;

    /* Method Name: validateCoreProgram */
    @Test
    public abstract void test_validateCoreProgram()
            throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	;

    /* Method Name: setCurrentCoreProgramVersion */
    @Test
    public abstract void test_setCurrentCoreProgramVersion()
            throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,IllegalVersionSequencingException	,OperationFailedException	,PermissionDeniedException	,DataValidationErrorException	;

    /* Method Name: searchForCoreProgramIds */
    @Test
    public abstract void test_searchForCoreProgramIds()
            throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;

    /* Method Name: searchForCorePrograms */
    @Test
    public abstract void test_searchForCorePrograms()
            throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;

    /* Method Name: validateProgramRequirement */
    @Test
    public abstract void test_validateProgramRequirement()
            throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	;

    /* Method Name: searchForProgramRequirementIds */
    @Test
    public abstract void test_searchForProgramRequirementIds()
            throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;

    /* Method Name: searchForProgramRequirements */
    @Test
    public abstract void test_searchForProgramRequirements()
            throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;

    /* Method Name: setCurrentMajorDisciplineVersion */
    @Test
    public abstract void test_setCurrentMajorDisciplineVersion()
            throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,IllegalVersionSequencingException,OperationFailedException	,PermissionDeniedException	,DataValidationErrorException	;

    /* Method Name: getMinorsByCredentialProgramType */
    @Test
    public abstract void test_getMinorsByCredentialProgramType()
            throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	;

    /* Method Name: validateMinorDiscipline */
    @Test
    public abstract void test_validateMinorDiscipline()
            throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	;

    /* Method Name: getVariationsByMajorDisciplineId */
    @Test
    public abstract void test_getVariationsByMajorDisciplineId()
            throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	;

    /* Method Name: searchForMinorDisciplineIds */
    @Test
    public abstract void test_searchForMinorDisciplineIds()
            throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;

    /* Method Name: searchForMinorDisciplines */
    @Test
    public abstract void test_searchForMinorDisciplines()
            throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;

    /* Method Name: getTracksByMinor */
    @Test
    public abstract void test_getTracksByMinor()
            throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;

    /* Method Name: validateTrack */
    @Test
    public abstract void test_validateTrack()
            throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;

    /* Method Name: searchForTrackIds */
    @Test
    public abstract void test_searchForTrackIds()
            throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;

    /* Method Name: searchForTracks */
    @Test
    public abstract void test_searchForTracks()
            throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	;

}
