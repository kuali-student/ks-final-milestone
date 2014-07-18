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
 * Created by venkat on 5/23/14
 */
package org.kuali.student.enrollment.class2.courseoffering.form;

import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.core.comment.dto.CommentInfo;

import java.util.Date;

/**
 *
 * @author Kuali Student Team
 * DTOWrapper
 */
public class KSCommentWrapper implements Comparable<KSCommentWrapper> {

    protected CommentInfo commentInfo;
    private String creatorName;
    private String createdDate;
    private String lastEditorName;
    private String lastEditedDate;
    private boolean edited;
    private String commentTextUI;
    private String deletedCommentCreatorId;
    private String deletedCommentCreatedDate;
    private String deletedCommentLastEditorId;
    private String deletedCommentLastEditedDate;
    private String deletedCommentText;
//    protected CreateCourseRenderHelper renderHelper;

    public KSCommentWrapper(){
        commentInfo = new CommentInfo();
        commentInfo.setCommentText(new RichTextInfo());
//        renderHelper = new CreateCourseRenderHelper();
    }

    public CommentInfo getCommentInfo() {
        return commentInfo;
    }

    public void setCommentInfo(CommentInfo commentInfo) {
        this.commentInfo = commentInfo;
    }

    public String getLastEditorName() {
        return lastEditorName;
    }

    public void setLastEditorName(String lastEditorName) {
        this.lastEditorName = lastEditorName;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastEditedDate() {
        return lastEditedDate;
    }

    public void setLastEditedDate(String lastEditedDate) {
        this.lastEditedDate = lastEditedDate;
    }

    public boolean isEdited() {
        return edited;
    }

    public void setEdited(boolean edited) {
        this.edited = edited;
    }

    public String getCommentTextUI() {
        return commentTextUI;
    }

    public void setCommentTextUI(String commentTextUI) {
        this.commentTextUI = commentTextUI;
    }

    public String getDeletedCommentCreatorId() {
        return deletedCommentCreatorId;
    }

    public void setDeletedCommentCreatorId(String deletedCommentCreatorId) {
        this.deletedCommentCreatorId = deletedCommentCreatorId;
    }

    public String getDeletedCommentCreatedDate() {
        return deletedCommentCreatedDate;
    }

    public void setDeletedCommentCreatedDate(String deletedCommentCreatedDate) {
        this.deletedCommentCreatedDate = deletedCommentCreatedDate;
    }

    public String getDeletedCommentLastEditorId() {
        return deletedCommentLastEditorId;
    }

    public void setDeletedCommentLastEditorId(String deletedCommentLastEditorId) {
        this.deletedCommentLastEditorId = deletedCommentLastEditorId;
    }

    public String getDeletedCommentLastEditedDate() {
        return deletedCommentLastEditedDate;
    }

    public void setDeletedCommentLastEditedDate(String deletedCommentLastEditedDate) {
        this.deletedCommentLastEditedDate = deletedCommentLastEditedDate;
    }

    public String getDeletedCommentText() {
        return deletedCommentText;
    }

    public void setDeletedCommentText(String deletedCommentText) {
        this.deletedCommentText = deletedCommentText;
    }

    @Override
    public int compareTo(KSCommentWrapper o) {
        if (o.getCommentInfo().getMeta() != null &&
            o.getCommentInfo().getMeta().getCreateTime() != null &&
            getCommentInfo().getMeta() != null &&
            getCommentInfo().getMeta().getCreateTime() != null ) {
            return o.getCommentInfo().getMeta().getCreateTime().compareTo(getCommentInfo().getMeta().getCreateTime()) ;
        } else {
            return 0;
        }
    }
}
