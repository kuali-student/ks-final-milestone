/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.core.comment.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.kuali.student.common.test.spring.AbstractServiceTest;
import org.kuali.student.common.test.spring.Client;
import org.kuali.student.common.test.spring.Dao;
import org.kuali.student.common.test.spring.Daos;
import org.kuali.student.common.test.spring.PersistenceFileLocation;
import org.kuali.student.core.comment.dto.CommentInfo;
import org.kuali.student.core.comment.dto.CommentTypeInfo;
import org.kuali.student.core.comment.dto.TagInfo;
import org.kuali.student.core.comment.dto.TagTypeInfo;
import org.kuali.student.core.comment.service.CommentService;
import org.kuali.student.core.dto.MetaInfo;
import org.kuali.student.core.dto.ReferenceTypeInfo;
import org.kuali.student.core.dto.RichTextInfo;
import org.kuali.student.core.dto.StatusInfo;
import org.kuali.student.core.exceptions.AlreadyExistsException;
import org.kuali.student.core.exceptions.DataValidationErrorException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;

/**
 * This is a description of what this class does - lindholm don't forget to fill this in.
 *
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 *
 */
@Daos( { @Dao(value = "org.kuali.student.core.comment.dao.impl.CommentDaoImpl",testSqlFile="classpath:ks-comment.sql" /*, testDataFile = "classpath:test-beans.xml"*/) })
@PersistenceFileLocation("classpath:META-INF/comment-persistence.xml")
public class TestCommentServiceImpl extends AbstractServiceTest {
	final Logger LOG = Logger.getLogger(TestCommentServiceImpl.class);
    @Client(value = "org.kuali.student.core.comment.service.impl.CommentServiceImpl",additionalContextFile="classpath:comment-additional-context.xml")
    public CommentService client;


    @Test
    public void testClient() {
        assertNotNull(client);
    }

    @Test
    public void testCommentCrud() throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
    	CommentInfo commentInfo = new CommentInfo();
    	RichTextInfo commentText = new RichTextInfo();
    	MetaInfo metaInfo = new MetaInfo();
    	metaInfo.setCreateId("Id1");
    	metaInfo.setCreateTime(new Date());
    	
    	commentText.setPlain("created Comment text");
    	commentText.setFormatted("<p>created Comment html</p>");
    	commentInfo.setCommentText(commentText);
    	commentInfo.setEffectiveDate(new Date());
    	commentInfo.setExpirationDate(new Date());
    	commentInfo.setType("commentType.type2");
    	CommentInfo commentInfo2 = client.addComment("REF-4", "referenceType.type1", commentInfo);

    	assertEquals(commentInfo.getCommentText().getPlain(), commentInfo2.getCommentText().getPlain());
       	assertEquals(commentInfo.getCommentText().getFormatted(), commentInfo2.getCommentText().getFormatted());
       	assertEquals(commentInfo.getType(), commentInfo2.getType());
       	assertEquals(commentInfo.getEffectiveDate(), commentInfo2.getEffectiveDate());
       	assertEquals(commentInfo.getExpirationDate(), commentInfo2.getExpirationDate());
       	assertNotNull(commentInfo2.getMetaInfo().getCreateTime());

    	RichTextInfo commentText2 = new RichTextInfo();
    	commentText2.setPlain("created Comment text2");
    	commentText2.setFormatted("<p>created Comment Html2</p>");
    	commentInfo2.setCommentText(commentText2);
    	CommentInfo commentInfo3 = client.updateComment("REF-99", "referenceType.type1", commentInfo2);
    	assertEquals(commentInfo2.getCommentText().getPlain(), commentInfo3.getCommentText().getPlain());
       	assertEquals(commentInfo2.getCommentText().getFormatted(), commentInfo3.getCommentText().getFormatted());
       	assertEquals(commentInfo2.getType(), commentInfo3.getType());

       	CommentInfo commentInfo4 = client.getComment(commentInfo3.getId());
    	assertEquals(commentInfo4.getCommentText().getPlain(), commentInfo3.getCommentText().getPlain());
       	assertEquals(commentInfo4.getCommentText().getFormatted(), commentInfo3.getCommentText().getFormatted());
       	assertEquals(commentInfo4.getType(), commentInfo3.getType());

       	StatusInfo statusInfo = client.removeComment(commentInfo4.getId(), commentInfo.getReferenceId(), commentInfo4.getReferenceTypeKey());
       	assertTrue(statusInfo.getSuccess());

       	try {
			statusInfo = client.removeComment(commentInfo4.getId(), commentInfo.getReferenceId(), commentInfo4.getReferenceTypeKey());
			assertTrue(false);
       	} catch (DoesNotExistException e) {
			assertTrue(true);
		}

       	try {
			client.getComment(commentInfo3.getId());
			assertTrue(false);
		} catch (DoesNotExistException e) {
			assertTrue(true);
		}
    }

    @Test
    public void testValidateComment() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
    	//FIXME validation needs to be tested, this code doesn't work and I need to refactor
//    	CommentInfo commentInfo = new CommentInfo();
//    	RichTextInfo commentText = new RichTextInfo();
//    	commentText.setFormatted("<p>comment&gt;!@#02h%$</p>");
//    	commentText.setPlain("comment");
//    	commentInfo.setCommentText(commentText);
//    	commentInfo.setReferenceTypeKey("referenceKey");
//    	commentInfo.setReferenceId("referenceId");
//    	commentInfo.setEffectiveDate(new Date());
//    	commentInfo.setExpirationDate(new Date());
//
//    	commentInfo.setType("kuali.org.Comment");
//    	commentInfo.setState("active");
//
//    	List<DictValidationResultContainer> validations = client.validateComment("", commentInfo);
//	    for (DictValidationResultContainer validationResult : validations) {
//            assertTrue(validationResult.isOk());
//        }
//
//	    commentInfo = new CommentInfo();
//    	commentText.setFormatted("<p>comment&!%$*</p>");
//    	commentText.setPlain("comment&!%$");
//    	commentInfo.setCommentText(commentText);
//    	commentInfo.setReferenceTypeKey("referenceKey&!%$");
//    	commentInfo.setReferenceId("referenceId&!%$");
//    	commentInfo.setType("kuali.org.Comment");
//    	commentInfo.setState("active");
//
//    	validations = client.validateComment("", commentInfo);
//	    for (DictValidationResultContainer validationResult : validations) {
//	    	if (!(validationResult.getElement().equals("effectiveDate") ||
//	    			validationResult.getElement().equals("expirationDate"))) {
//	    		assertFalse(validationResult.isOk());
//	    	}
//        }
    }

    @Test
    public void testCommentsRemove() throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
    	CommentInfo commentInfo = new CommentInfo();
    	RichTextInfo commentText = new RichTextInfo();
    	commentText.setPlain("created Comment text");
    	commentText.setFormatted("<p>created Comment html</p>");
    	commentInfo.setCommentText(commentText);
    	commentInfo.setType("commentType.type2");

    	CommentInfo ci1 = client.addComment("REF-COMMENT-99", "referenceType.type1", commentInfo);
    	CommentInfo ci2 = client.addComment("REF-COMMENT-98", "referenceType.type1", commentInfo);
    	CommentInfo ci3 = client.addComment("REF-COMMENT-99", "referenceType.type1", commentInfo);
    	
    	List<CommentInfo> comments = client.getComments("REF-COMMENT-99", "referenceType.type1");
    	assertNotNull(comments);

    	try {
			StatusInfo si = client.removeComments("REF-COMMENT-99");
			assertTrue(si.getSuccess());
		} catch (DoesNotExistException e) {
			assertTrue(false);
		}
		try {
			client.getComment(ci1.getId());
			assertTrue(false);
		} catch (DoesNotExistException e) {
			assertTrue(true);
		}

		try {
			client.getComment(ci2.getId());
			assertTrue(true);
		} catch (DoesNotExistException e) {
			assertTrue(true);
		}

		try {
			client.getComment(ci3.getId());
			assertTrue(false);
		} catch (DoesNotExistException e) {
			assertTrue(true);
		}

    }

    @Test
    public void testGetComment() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        CommentInfo commentInfo = client.getComment("COMMENT-1");
        assertNotNull(commentInfo);

        List<CommentInfo> comments = client.getComments("REF-2", "referenceType.type2");
        assertNotNull(comments);
        try {
            commentInfo = client.getComment(null);
            assertTrue(false);
        } catch (MissingParameterException e) {
            assertTrue(true);
        }
        try {
            commentInfo = client.getComment("xxx-1");
            assertTrue(false);
        } catch (DoesNotExistException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testGetCommentTypes() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
    	List<CommentTypeInfo> commentTypeInfos = client.getCommentTypesForReferenceType("referenceType.type1");
    	assertEquals(2, commentTypeInfos.size());
    }

    @Test
    public void testGetTag() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        TagInfo tagInfo = client.getTag("Comment-TAG-1");
        assertNotNull(tagInfo);
        try {
            tagInfo = client.getTag(null);
            assertTrue(false);
        } catch (MissingParameterException e) {
            assertTrue(true);
        }
        try {
            tagInfo = client.getTag("xxx-1");
            assertTrue(false);
        } catch (DoesNotExistException e) {
            assertTrue(true);
        }


        List<TagInfo> tagInfos1 = client.getTags("REF-1", "referenceType.type1");
        assertNotNull(tagInfos1);

        List<TagInfo> tagInfos2 = client.getTagsByType("REF-1", "referenceType.type1","tagType.default");
        assertNotNull(tagInfos2);

    }


    @Test
    public void testGetTagType() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        List<TagTypeInfo> tagTypeInfos = client.getTagTypes();
        assertNotNull(tagTypeInfos);

    }

    @Test
    public void testCreateDeleteTag() throws ParseException, DataValidationErrorException, AlreadyExistsException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException{
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        TagInfo tagInfo= new TagInfo();

//        tagInfo.setId("Comment-TAG-3");
        tagInfo.setNamespace("UnitedStates3");
        tagInfo.setPredicate("era3");
        tagInfo.setValue("20thCentury");
        tagInfo.setEffectiveDate(df.parse("20090101"));
        tagInfo.setExpirationDate(df.parse("21001231"));
        tagInfo.setReferenceId("");
        tagInfo.setReferenceTypeKey("");
        tagInfo.setType("tagType.default");

        TagInfo createdTagInfo = client.addTag("REF-4", "referenceType.type1", tagInfo);
        try {
            TagInfo tagInfoTest = client.getTag(createdTagInfo.getId());
            assertEquals(tagInfoTest.getId(), createdTagInfo.getId());
        } catch (DoesNotExistException e) {
            LOG.error(e);
        }

        assertEquals("UnitedStates3",createdTagInfo.getNamespace());
        assertEquals("tagType.default", createdTagInfo.getType());
        assertEquals("20thCentury",createdTagInfo.getValue());
        assertEquals("era3",createdTagInfo.getPredicate());
        assertEquals(df.parse("20090101"),createdTagInfo.getEffectiveDate());
        assertEquals(df.parse("21001231"),createdTagInfo.getExpirationDate());

     // now test remove (and clean up changes made)
        StatusInfo si;
        String tagRefId = createdTagInfo.getReferenceId();
        String tagRefType = createdTagInfo.getReferenceTypeKey();
        try {
            si = client.removeTag(null, tagRefId, tagRefType);
            assertTrue(si.getSuccess());
        } catch (DoesNotExistException e) {
            fail("CommentService.removeTag() failed removing just-created Tag");
        }

        try {
            client.removeTag(null, tagRefId, tagRefType);
            fail("CommentService.removeTag() of a deleted Comment did not throw DoesNotExistException as expected");
        } catch (DoesNotExistException e) {
        }

    }

    @Test
    public void testCreateDeleteTags() throws ParseException, DataValidationErrorException, AlreadyExistsException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException{
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        TagInfo tagInfo= new TagInfo();

//        tagInfo.setId("Comment-TAG-3");
        tagInfo.setNamespace("UnitedStates3");
        tagInfo.setPredicate("era3");
        tagInfo.setValue("20thCentury");
        tagInfo.setEffectiveDate(df.parse("20090101"));
        tagInfo.setExpirationDate(df.parse("21001231"));
        tagInfo.setReferenceId("");
        tagInfo.setReferenceTypeKey("");
        tagInfo.setType("tagType.default");

        client.addTag("REF-12", "referenceType.type1", tagInfo);
        client.addTag("REF-12", "referenceType.type1", tagInfo);
        client.addTag("REF-12", "referenceType.type1", tagInfo);

        List<TagInfo> tags = client.getTags("REF-12", "referenceType.type1");
        assertNotNull(tags);

     // now test remove multiple tags linked to the same reference(and clean up changes made)
        StatusInfo si;
        String tagRefId = "REF-12";
        String tagRefType = "REF-TYPE-0";
        try {
            si = client.removeTags(tagRefId);
            assertTrue(si.getSuccess());
        } catch (DoesNotExistException e) {
            fail("CommentService.removeTags() failed removing just-created Tags");
        }

        try {
            client.removeTag(null, tagRefId, tagRefType);
            fail("CommentService.removeTags() of a deleted Comment did not throw DoesNotExistException as expected");
        } catch (DoesNotExistException e) {
        }

    }

    @Test
    public void testGetReferenceTypes() throws OperationFailedException {
    	List<ReferenceTypeInfo> referenceTypes = client.getReferenceTypes();
    	assertEquals(2, referenceTypes.size());
    }
}
