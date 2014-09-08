/**
 * Copyright 2014 The Kuali Foundation Licensed under the
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
 *
 * Created by chongzhu on 5/28/14
 */
package org.kuali.student.lum.kim.role.type;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.kns.kim.role.DerivedRoleTypeServiceBase;
import org.kuali.student.lum.kim.KimIdentityServiceConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.comment.dto.CommentInfo;
import org.kuali.student.r2.core.comment.service.CommentService;
import org.kuali.student.r2.core.constants.CommentServiceConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.xml.namespace.QName;
import java.util.List;
import java.util.Map;

/**
 /**
 * This determines if user should get the correct role for adding, editing, or deleting a comment
 *
 * @author Kuali Student Team
 */
public class CommentAuthorDerivedRoleTypeServiceImpl extends
        DerivedRoleTypeServiceBase {
    private static final Logger LOG = LoggerFactory.getLogger(CommentAuthorDerivedRoleTypeServiceImpl.class);

    private CommentService commentService;

    @Override
    public boolean hasDerivedRole(String principalId,
                                  List<String> groupIds, String namespaceCode, String roleName,
                                  Map<String,String> qualification) {
        ContextInfo context = new ContextInfo();

        if(StringUtils.isEmpty(principalId)) {
            LOG.warn("The principalId is empty.");
            return false;
        }

        String commentId = qualification.get(KimIdentityServiceConstants.COMMENT_ID_QUALIFICATION);

        // check to see what comment role types that principalID has
        CommentInfo commentInfo;
        try {
            commentInfo = getCommentService().getComment(commentId, context);
        } catch (DoesNotExistException e) {
            throw new RuntimeException("comment [id=" + commentId + "] does not exist.", e);
        } catch (InvalidParameterException e) {
            throw new RuntimeException("Invalid comment [id=" + commentId + "].", e);
        } catch (MissingParameterException e) {
            throw new RuntimeException("Missing comment [id=" + commentId + "].", e);
        } catch (OperationFailedException e) {
            throw new RuntimeException("Loading comment [id=" + commentId + "] error.", e);
        } catch (PermissionDeniedException e) {
            throw new RuntimeException("Loading comment [id=" + commentId + "] error.", e);
        }

        if(principalId.equalsIgnoreCase(commentInfo.getCommenterId())) {
            return true;
        }
        return false;
    }



    protected CommentService getCommentService() {
        if (commentService == null) {
            commentService = (CommentService) GlobalResourceLoader.getService(new QName(CommentServiceConstants.NAMESPACE, CommentService.class.getSimpleName()));
        }
        return commentService;
    }


}
