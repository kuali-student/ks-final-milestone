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
package org.kuali.student.cm.comment.form;

import org.kuali.rice.krad.web.form.KsUifFormBase;
import org.kuali.student.cm.comment.form.wrapper.CMCommentWrapper;
import org.kuali.student.r2.core.proposal.dto.ProposalInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Form class to handle comments functionality.
 *
 * @author Kuali Student Team
 */
public class CMCommentForm extends KsUifFormBase {

    protected List<CMCommentWrapper> comments = new ArrayList<CMCommentWrapper>();
    protected String newComment;
    protected ProposalInfo proposal;
    protected CMCommentWrapper deletedComment;
    protected boolean canAddComment = true;

    public CMCommentForm(){

    }

    public List<CMCommentWrapper> getComments() {
        return comments;
    }

    public void setComments(List<CMCommentWrapper> comments) {
        this.comments = comments;
    }

    public ProposalInfo getProposal() {
        return proposal;
    }

    public void setProposal(ProposalInfo proposal) {
        this.proposal = proposal;
    }

    public String getNewComment() {
        return newComment;
    }

    public void setNewComment(String newComment) {
        this.newComment = newComment;
    }

    public CMCommentWrapper getDeletedComment() {
        return deletedComment;
    }

    public void setDeletedComment(CMCommentWrapper deletedComment) {
        this.deletedComment = deletedComment;
    }

    public boolean isCanAddComment() {
        return canAddComment;
    }

    public void setCanAddComment(boolean canAddComment) {
        this.canAddComment = canAddComment;
    }

}
