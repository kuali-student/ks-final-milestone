/*
 * Copyright 2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Lic+ense is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.r2.core.comment.service.impl;


import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.kuali.student.common.test.util.AttributeTester;
import org.kuali.student.common.test.util.MetaTester;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.IdNamelessEntityInfo;
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
import org.kuali.student.r2.core.comment.dto.CommentInfo;
import org.kuali.student.r2.core.comment.service.CommentService;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


@Transactional
public abstract class TestCommentServiceImplConformanceBaseCrud {

    // ====================
    // SETUP
    // ====================

    @Resource
    public CommentService testService;

    public CommentService getCommentService() {
        return testService;
    }

    public void setCommentService(CommentService service) {
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
    //           CommentInfo
    // ****************************************************
    @Test
    public void testCrudComment() throws Exception {
        // -------------------------------------
        // test create
        // -------------------------------------
        CommentInfo expected = new CommentInfo();

        // METHOD TO SET DTO FIELDS HERE FOR TEST CREATE
        testCrudComment_setDTOFieldsForTestCreate(expected);

        new AttributeTester().add2ForCreate(expected.getAttributes());

        // code to create actual
        CommentInfo actual = testService.createComment(expected.getReferenceId(), expected.getReferenceTypeKey(), expected.getTypeKey(), expected, contextInfo);

        assertNotNull(actual.getId());
        check(expected, actual);

        // METHOD TO TEST DTO FIELDS HERE FOR TEST CREATE
        testCrudComment_testDTOFieldsForTestCreateUpdate(expected, actual);

        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterCreate(actual.getMeta());

        // -------------------------------------
        // test read
        // -------------------------------------
        expected = actual;
        actual = testService.getComment(actual.getId(), contextInfo);
        assertEquals(expected.getId(), actual.getId());
        check(expected, actual);

        // INSERT CODE FOR TESTING MORE DTO FIELDS HERE
        testCrudComment_testDTOFieldsForTestCreateUpdate(expected, actual);

        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterGet(expected.getMeta(), actual.getMeta());

        // -------------------------------------
        // test update
        // -------------------------------------
        CommentInfo original = new CommentInfo(actual);
        expected = new CommentInfo(actual);

        expected.setStateKey(expected.getState() + "_Updated");

        // METHOD TO INSERT CODE TO UPDATE DTO FIELDS HERE
        testCrudComment_setDTOFieldsForTestUpdate(expected);

        new AttributeTester().delete1Update1Add1ForUpdate(expected.getAttributes());
        // code to update
        actual = testService.updateComment(expected.getId(), expected, contextInfo);

        assertEquals(expected.getId(), actual.getId());
        check(expected, actual);

        // METHOD TO INSERT CODE FOR TESTING DTO FIELDS HERE
        testCrudComment_testDTOFieldsForTestCreateUpdate(expected, actual);

        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterUpdate(expected.getMeta(), actual.getMeta());

        // -------------------------------------
        // test read after update
        // -------------------------------------

        expected = actual;
        // code to get actual
        actual = testService.getComment(actual.getId(), contextInfo);

        assertEquals(expected.getId(), actual.getId());
        check(expected, actual);

        // INSERT METHOD CODE FOR TESTING DTO FIELDS HERE
        testCrudComment_testDTOFieldsForTestReadAfterUpdate(expected, actual);

        new AttributeTester().check(expected.getAttributes(), actual.getAttributes());
        new MetaTester().checkAfterGet(expected.getMeta(), actual.getMeta());

        CommentInfo alphaDTO = actual;

        // create a 2nd DTO
        CommentInfo betaDTO = new CommentInfo();

        // METHOD TO INSERT CODE TO SET MORE DTO FIELDS HERE
        testCrudComment_setDTOFieldsForTestReadAfterUpdate(betaDTO);

        betaDTO.setTypeKey("typeKeyBeta");
        betaDTO.setStateKey("stateKeyBeta");
        betaDTO = testService.createComment(betaDTO.getReferenceId(), betaDTO.getReferenceTypeKey(), betaDTO.getTypeKey(), betaDTO, contextInfo);

        // -------------------------------------
        // test bulk get with no ids supplied
        // -------------------------------------

        List<String> commentIds = new ArrayList<String>();
        // code to get DTO by Ids
        List<CommentInfo> records = testService.getCommentsByIds(commentIds, contextInfo);

        assertEquals(commentIds.size(), records.size());
        assertTrue(commentIds.isEmpty());

        // -------------------------------------
        // test bulk get
        // -------------------------------------
        commentIds = new ArrayList<String>();
        commentIds.add(alphaDTO.getId());
        commentIds.add(betaDTO.getId());
        // code to get DTO by Ids
        records = testService.getCommentsByIds(commentIds, contextInfo);

        assertEquals(commentIds.size(), records.size());
        for (CommentInfo record : records) {
            if (!commentIds.remove(record.getId())) {
                fail(record.getId());
            }
        }
        assertTrue(commentIds.isEmpty());

        // -------------------------------------
        // test get by type
        // -------------------------------------
        // code to get by specific type "typeKey01"
        commentIds = testService.getCommentIdsByType("typeKey01", contextInfo);

        assertEquals(1, commentIds.size());
        assertEquals(alphaDTO.getId(), commentIds.get(0));

        // test get by other type
        // code to get by specific type "typeKeyBeta"
        commentIds = testService.getCommentIdsByType("typeKeyBeta", contextInfo);

        assertEquals(1, commentIds.size());
        assertEquals(betaDTO.getId(), commentIds.get(0));

        // -------------------------------------
        // test delete
        // -------------------------------------

        StatusInfo status = testService.deleteComment(actual.getId(), contextInfo);

        assertNotNull(status);
        assertTrue(status.getIsSuccess());
        try {
            testService.getComment(actual.getId(), contextInfo);
            fail("Did not receive DoesNotExistException when attempting to get already-deleted entity");
        } catch (DoesNotExistException dnee) {
            assertNotNull(dnee.getMessage());
            assertEquals(actual.getId(), dnee.getMessage());
        }

    }

    /*
        A method to set the fields for a Comment in a 'test create' section prior to calling the 'create' operation.
    */
    public abstract void testCrudComment_setDTOFieldsForTestCreate(CommentInfo expected) throws ParseException;

    /*
        A method to test the fields for a Comment. This is called after:
        - creating a DTO, where actual is the DTO returned by the create operation, and expected is the dto passed in to the create operation
        - reading a DTO after creating it, and actual is the read DTO, and expected is the dto that was created
        - updating a DTO, where actual is DTO returned by the update operation, and expected is the dto that was passed in to the update operation
    */
    public abstract void testCrudComment_testDTOFieldsForTestCreateUpdate(CommentInfo expected, CommentInfo actual);

    /*
        A method to set the fields for a Comment in a 'test update' section prior to calling the 'update' operation.
    */
    public abstract void testCrudComment_setDTOFieldsForTestUpdate(CommentInfo expected) throws ParseException;

    /*
        A method to test the fields for a Comment after an update operation, followed by a read operation,
        where actual is the DTO returned by the read operation, and expected is the dto returned by the update operation.
    */
    public abstract void testCrudComment_testDTOFieldsForTestReadAfterUpdate(CommentInfo expected, CommentInfo actual);

    /*
        A method to set the fields for a Comment in the 'test read after update' section.
        This dto is another (second) dto object being created for other tests.
    */
    public abstract void testCrudComment_setDTOFieldsForTestReadAfterUpdate(CommentInfo expected) throws ParseException;


    // ========================================
    // SERVICE OPS TESTED IN BASE TEST CLASS
    // ========================================

	/*
		The following methods are tested as part of CRUD operations for this service's DTOs:
			getComment
			getCommentsByIds
			getCommentIdsByType
			createComment
			updateComment
			deleteComment
	*/

    // ========================================
    // SERVICE OPS NOT TESTED IN BASE TEST CLASS
    // ========================================

    /* Method Name: getCommentsByReferenceAndType */
    @Test
    public abstract void test_getCommentsByReferenceAndType() throws Exception;

    /* Method Name: searchForCommentIds */
    @Test
    public abstract void test_searchForCommentIds()
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /* Method Name: searchForComments */
    @Test
    public abstract void test_searchForComments()
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /* Method Name: deleteCommentsByReference */
    @Test
    public abstract void test_deleteCommentsByReference() throws Exception;

    /* Method Name: validateComment */
    @Test
    public abstract void test_validateComment()
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;


    public void check(IdNamelessEntityInfo expected, IdNamelessEntityInfo actual) {
        assertEquals(expected.getTypeKey(), actual.getTypeKey());
        assertEquals(expected.getStateKey(), actual.getStateKey());
    }

}


