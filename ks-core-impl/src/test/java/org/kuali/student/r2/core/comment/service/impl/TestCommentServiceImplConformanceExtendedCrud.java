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
import java.text.SimpleDateFormat;
import java.util.List;
import javax.annotation.Resource;

import org.junit.After;
import org.junit.Test;
import org.kuali.student.common.test.util.RichTextTester;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.core.comment.dto.CommentInfo;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;


@Transactional
public abstract class TestCommentServiceImplConformanceExtendedCrud extends TestCommentServiceImplConformanceBaseCrud {


    @Resource
    private CommentDataLoader dataLoader;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

    @After
    public void tearDownExtended() throws Exception {
        dataLoader.afterTest();
    }


    // ========================================
    // DTO FIELD SPECIFIC METHODS
    // ========================================

    // ****************************************************
    //           CommentInfo
    // ****************************************************

    /*
        A method to set the fields for a Comment in a 'test create' section prior to calling the 'create' operation.
    */
    public void testCrudComment_setDTOFieldsForTestCreate(CommentInfo expected) throws ParseException {
        expected.setTypeKey("typeKey01");
        expected.setStateKey("stateKey01");
        expected.setCommentText(RichTextHelper.buildRichTextInfo("plain text updated", "formatted text!!!"));
        expected.setCommenterId("commenterId01");
        expected.setReferenceTypeKey("referenceTypeKey01");
        expected.setReferenceId("referenceId01");
        expected.setEffectiveDate(dateFormat.parse("20130611"));
        expected.setExpirationDate(dateFormat.parse("21000101"));
    }

    /*
        A method to test the fields for a Comment. This is called after:
        - creating a DTO, where actual is the DTO returned by the create operation, and expected is the dto passed in to the create operation
        - reading a DTO after creating it, and actual is the read DTO, and expected is the dto that was created
        - updating a DTO, where actual is DTO returned by the update operation, and expected is the dto that was passed in to the update operation
    */
    public void testCrudComment_testDTOFieldsForTestCreateUpdate(CommentInfo expected, CommentInfo actual) {
        assertEquals(expected.getTypeKey(), actual.getTypeKey());
        assertEquals(expected.getStateKey(), actual.getStateKey());
        new RichTextTester().check(expected.getCommentText(), expected.getCommentText());
        assertEquals(expected.getCommenterId(), actual.getCommenterId());
        assertEquals(expected.getReferenceTypeKey(), actual.getReferenceTypeKey());
        assertEquals(expected.getReferenceId(), actual.getReferenceId());
        assertEquals(expected.getEffectiveDate(), actual.getEffectiveDate());
        assertEquals(expected.getExpirationDate(), actual.getExpirationDate());
    }

    /*
        A method to set the fields for a Comment in a 'test update' section prior to calling the 'update' operation.
    */
    public void testCrudComment_setDTOFieldsForTestUpdate(CommentInfo expected) throws ParseException {
        expected.setStateKey("stateKey_Updated");
        expected.setCommentText(RichTextHelper.buildRichTextInfo("plain text updated", "formatted text updated!!!"));
        expected.setCommenterId("commenterId_Updated");
        expected.setEffectiveDate(dateFormat.parse("20100611"));
        expected.setExpirationDate(dateFormat.parse("21100101"));
    }

    /*
        A method to test the fields for a Comment after an update operation, followed by a read operation,
        where actual is the DTO returned by the read operation, and expected is the dto returned by the update operation.
    */
    public void testCrudComment_testDTOFieldsForTestReadAfterUpdate(CommentInfo expected, CommentInfo actual) {
        assertEquals(expected.getTypeKey(), actual.getTypeKey());
        assertEquals(expected.getStateKey(), actual.getStateKey());
        new RichTextTester().check(expected.getCommentText(), expected.getCommentText());
        assertEquals(expected.getCommenterId(), actual.getCommenterId());
        assertEquals(expected.getReferenceTypeKey(), actual.getReferenceTypeKey());
        assertEquals(expected.getReferenceId(), actual.getReferenceId());
        assertEquals(expected.getEffectiveDate(), actual.getEffectiveDate());
        assertEquals(expected.getExpirationDate(), actual.getExpirationDate());
    }

    /*
        A method to set the fields for a Comment in the 'test read after update' section.
        This dto is another (second) dto object being created for other tests.
    */
    public void testCrudComment_setDTOFieldsForTestReadAfterUpdate(CommentInfo expected) throws ParseException {
        expected.setStateKey("stateKey_Updated2");
        expected.setCommentText(RichTextHelper.buildRichTextInfo("plain text updated2", "formatted text updated2!!!"));
        expected.setCommenterId("commenterId_Updated2");
        expected.setReferenceTypeKey("referenceTypeKey05");
        expected.setReferenceId("referenceId06");
        expected.setEffectiveDate(dateFormat.parse("20100610"));
        expected.setExpirationDate(dateFormat.parse("21100111"));
    }


    // ========================================
    // SERVICE OPS NOT TESTED IN BASE TEST CLASS
    // ========================================

    /* Method Name: getCommentsByReferenceAndType */
    @Test
    public void test_getCommentsByReferenceAndType() throws Exception {
        loadData();

        List<CommentInfo> comments = testService.getCommentsByReferenceAndType("1", CommentDataLoader.REF_OBJECT_ONE_COMMENT_TYPE, contextInfo);
        assertEquals(1, comments.size());

        comments = testService.getCommentsByReferenceAndType("5", CommentDataLoader.REF_OBJECT_ONE_COMMENT_TYPE, contextInfo);
        assertEquals(1, comments.size());

        comments = testService.getCommentsByReferenceAndType("5", CommentDataLoader.REF_OBJECT_TWO_COMMENT_TYPE, contextInfo);
        assertEquals(2, comments.size());
    }

    /* Method Name: searchForCommentIds */
    @Test
    public void test_searchForCommentIds()
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
    }

    /* Method Name: searchForComments */
    @Test
    public void test_searchForComments()
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
    }

    /* Method Name: deleteCommentsByReference */
    @Test
    public void test_deleteCommentsByReference() throws Exception {
        loadData();

        List<CommentInfo> comments = testService.getCommentsByReferenceAndType("5", CommentDataLoader.REF_OBJECT_TWO_COMMENT_TYPE, contextInfo);
        assertEquals(2, comments.size());

        testService.deleteCommentsByReference("5", CommentDataLoader.REF_OBJECT_TWO_COMMENT_TYPE, contextInfo);

        comments = testService.getCommentsByReferenceAndType("5", CommentDataLoader.REF_OBJECT_TWO_COMMENT_TYPE, contextInfo);
        assertEquals(0, comments.size());
    }

    /* Method Name: validateComment */
    @Test
    public void test_validateComment()
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
    }

    private void loadData() throws Exception {
        dataLoader.beforeTest();
    }
}


