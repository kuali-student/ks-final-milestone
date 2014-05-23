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
import org.kuali.student.r2.core.comment.dto.CommentInfo;

/**
 *
 * @author Kuali Student Team
 */
public class CommentWrapper implements DTOWrapper {

    protected CommentInfo commentInfo;
    protected RenderHelper renderHelper;

    public CommentWrapper(){
        commentInfo = new CommentInfo();
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

    public RenderHelper getRenderHelper() {
        return renderHelper;
    }

    public void setRenderHelper(RenderHelper renderHelper) {
        this.renderHelper = renderHelper;
    }

    public class CreateCourseRenderHelper implements RenderHelper {

        protected String user;
        protected String dateTime;

        public CreateCourseRenderHelper(){

        }

        public String getDateTime() {
            return dateTime;
        }

        public void setDateTime(String dateTime) {
            this.dateTime = dateTime;
        }

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }
    }
}
