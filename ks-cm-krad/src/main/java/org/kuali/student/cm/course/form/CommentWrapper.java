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
package org.kuali.student.cm.course.form;

import org.apache.commons.lang.StringUtils;
import org.kuali.student.cm.uif.util.DTOWrapper;
import org.kuali.student.cm.uif.util.RenderHelper;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.core.comment.dto.CommentInfo;

/**
 *
 * @author Kuali Student Team
 */
public class CommentWrapper implements DTOWrapper, Comparable<CommentWrapper> {

    protected CommentInfo commentInfo;
    protected CreateCourseRenderHelper renderHelper;

    public CommentWrapper(){
        commentInfo = new CommentInfo();
        commentInfo.setCommentText(new RichTextInfo());
        renderHelper = new CreateCourseRenderHelper();
    }

    @Override
    public boolean isNewDto() {
        if (commentInfo == null || StringUtils.isBlank(commentInfo.getId())){
            return true;
        } else {
            return false;
        }
    }

    public CommentInfo getCommentInfo() {
        return commentInfo;
    }

    public void setCommentInfo(CommentInfo commentInfo) {
        this.commentInfo = commentInfo;
    }

    public CreateCourseRenderHelper getRenderHelper() {
        return renderHelper;
    }

    public void setRenderHelper(CreateCourseRenderHelper renderHelper) {
        this.renderHelper = renderHelper;
    }

    @Override
    public int compareTo(CommentWrapper o) {
        if (o.getCommentInfo().getMeta() != null &&
            o.getCommentInfo().getMeta().getCreateTime() != null &&
            getCommentInfo().getMeta() != null &&
            getCommentInfo().getMeta().getCreateTime() != null ) {
            return o.getCommentInfo().getMeta().getCreateTime().compareTo(getCommentInfo().getMeta().getCreateTime()) ;
        } else {
            return 0;
        }
    }

    public class CreateCourseRenderHelper implements RenderHelper {

        protected String user;
        protected String creationTime;
        protected boolean editInProgress = false;
        protected boolean canEdit = false;
        protected boolean canDelete = false;

        public CreateCourseRenderHelper(){

        }

        public String getCreationTime() {
            return creationTime;
        }

        public void setCreationTime(String creationTime) {
            this.creationTime = creationTime;
        }

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public boolean isEditInProgress() {
            return editInProgress;
        }

        public void setEditInProgress(boolean editInProgress) {
            this.editInProgress = editInProgress;
        }

        public boolean isCanEdit() {
            return canEdit;
        }

        public void setCanEdit(boolean canEdit) {
            this.canEdit = canEdit;
        }

        public boolean isCanDelete() {
            return canDelete;
        }

        public void setCanDelete(boolean canDelete) {
            this.canDelete = canDelete;
        }

    }
}
