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


import org.kuali.student.common.test.mock.data.AbstractMockServicesAwareDataLoader;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.core.comment.dto.CommentInfo;
import org.kuali.student.r2.core.comment.service.CommentService;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class CommentDataLoader extends AbstractMockServicesAwareDataLoader {

    @Resource
    private CommentService commentService;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");


    public static final String ACTIVE_COMMENT_STATE = "org.kuali.comment.state.active";
    public static final String SCHEDULING_COMMENT_TYPE = "org.kuali.comment.type.scheduling";
    public static final String REF_OBJECT_ONE_COMMENT_TYPE = "refObjectOneCommentType";
    public static final String REF_OBJECT_TWO_COMMENT_TYPE = "refObjectTwoCommentType";

    @Override
    protected void initializeData() throws Exception {
        createComments();
    }

    public void createComments() throws ParseException, DoesNotExistException, PermissionDeniedException,
            OperationFailedException, InvalidParameterException, ReadOnlyException,
            MissingParameterException, DataValidationErrorException {

        for (int i = 0; i < 5; i++) {
            CommentInfo comment = new CommentInfo();
            comment.setCommentText(RichTextHelper.buildRichTextInfo("Plain TEXT", "FORMATTED TEXT"));
            comment.setCommenterId("1");
            comment.setEffectiveDate(dateFormat.parse("20100610"));
            comment.setExpirationDate(dateFormat.parse("21100111"));
            comment.setStateKey(ACTIVE_COMMENT_STATE);
            comment.setTypeKey(SCHEDULING_COMMENT_TYPE);
            comment.setReferenceId(String.valueOf(i + 1));
            comment.setReferenceTypeKey(REF_OBJECT_ONE_COMMENT_TYPE);
            commentService.createComment(comment.getReferenceId(), comment.getReferenceTypeKey(), comment.getTypeKey(), comment, context);
        }

        for(int i = 0; i < 2; i++) {
            for (int j = 4; j < 10; j++) {
                CommentInfo comment = new CommentInfo();
                comment.setCommentText(RichTextHelper.buildRichTextInfo("Plain TEXT", "FORMATTED TEXT"));
                comment.setCommenterId("2");
                comment.setEffectiveDate(dateFormat.parse("20100610"));
                comment.setExpirationDate(dateFormat.parse("21100111"));
                comment.setStateKey(ACTIVE_COMMENT_STATE);
                comment.setTypeKey(SCHEDULING_COMMENT_TYPE);
                comment.setReferenceId(String.valueOf(j + 1));
                comment.setReferenceTypeKey(REF_OBJECT_TWO_COMMENT_TYPE);
                commentService.createComment(comment.getReferenceId(), comment.getReferenceTypeKey(), comment.getTypeKey(), comment, context);
            }
        }
    }
}
