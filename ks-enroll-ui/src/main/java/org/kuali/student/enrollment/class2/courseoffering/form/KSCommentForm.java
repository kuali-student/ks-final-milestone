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
 * Created by venkat on 6/9/14
 */
package org.kuali.student.enrollment.class2.courseoffering.form;

import org.kuali.rice.krad.web.form.KsUifFormBase;

import java.util.ArrayList;
import java.util.List;

/**
 * Form class to handle comments functionality.
 *
 * @author Kuali Student Team
 */
public class KSCommentForm extends KsUifFormBase {

    protected List<KSCommentWrapper> comments = new ArrayList<KSCommentWrapper>();
    private String commentText;
    private String referenceId;
    private String referenceType;
    private String referenceName;

    protected boolean canAddComment = true;

    public KSCommentForm(){

    }

    public List<KSCommentWrapper> getComments() {
        return comments;
    }

    public void setComments(List<KSCommentWrapper> comments) {
        this.comments = comments;
    }

    public boolean isCanAddComment() {
        return canAddComment;
    }

    public void setCanAddComment(boolean canAddComment) {
        this.canAddComment = canAddComment;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public String getReferenceType() {
        return referenceType;
    }

    public void setReferenceType(String referenceType) {
        this.referenceType = referenceType;
    }

    public String getReferenceName() {
        return referenceName;
    }

    public void setReferenceName(String referenceName) {
        this.referenceName = referenceName;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }
}
