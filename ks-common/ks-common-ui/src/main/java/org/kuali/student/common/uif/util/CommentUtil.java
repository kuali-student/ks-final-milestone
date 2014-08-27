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
 * Created by David Yin on 7/23/14
 */
package org.kuali.student.common.uif.util;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.criteria.Predicate;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.student.common.uif.controller.KSCommentController;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.comment.service.CommentService;
import org.kuali.student.r2.core.constants.CommentServiceConstants;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;

/**
 * This class provides utility methods for Admin Comment
 *
 * @author Kuali Student Team
 */
public class CommentUtil {

    private static CommentService commentService;

    public static int getCommentsCount(String refObjectId, String refObjectUri, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException {

        List<Predicate> predicates = new ArrayList<Predicate>();
        predicates.add(PredicateFactory.equal("refObjectId", refObjectId));
        predicates.add(PredicateFactory.and(PredicateFactory.equal("refObjectUri", refObjectUri)));
        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(predicates.toArray(new Predicate[predicates.size()]));
        QueryByCriteria qbc = qbcBuilder.build();
        List<String> commentIds = getCommentService().searchForCommentIds(qbc, contextInfo);

        return commentIds.size();
    }

    public static CommentService getCommentService() {
        if (commentService == null) {
            commentService = (CommentService) GlobalResourceLoader.getService(new QName(CommentServiceConstants.NAMESPACE, CommentService.class.getSimpleName()));
        }
        return commentService;
    }

    public static String isValidText(String text, int minValue, int maxVisibleValue, int maxValue) {
        int textLength = 0;
        int textVisibleLength = 0;
        if (!StringUtils.isEmpty(text)) {
            textLength = text.length();
            String strippedHtml = text.replaceAll("\\<.*?>", "");
            textVisibleLength = strippedHtml.length();
        }

        if (textLength < minValue || textVisibleLength < minValue) {
            return KSCommentsConstants.KSCOMMENT_MSG_ERROR_EMPTY_TEXT_FIELD;
        } else if (textLength > maxValue || textVisibleLength > maxVisibleValue) {
            return KSCommentsConstants.KSCOMMENT_MSG_ERROR_TOO_LONG;
        }
        return KSCommentController.VALID_TEXT;
    }
}
