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
 * Created by venkat on 7/28/14
 */
package org.kuali.student.cm.decision.form.wrapper;

import org.kuali.student.r2.core.comment.dto.CommentInfo;

/**
 *
 * @author Kuali Student Team
 */
public class CMDecisionWrapper implements Comparable<CMDecisionWrapper> {

    private String actor;
    private String decision;
    private String date;
    private String rationale;

    protected CommentInfo comment;

    public CMDecisionWrapper(){

    }

    public CMDecisionWrapper(CommentInfo comment){
        this.comment = comment;
    }

    /**
     * Gets the value of actor
     *
     * @return the value of actor
     */
    public String getActor() {
        return this.actor;
    }

    /**
     * Sets the value of actor
     *
     * @param argActor Value to assign to this.actor
     */
    public void setActor(final String argActor) {
        this.actor = argActor;
    }

    /**
     * Gets the value of decision
     *
     * @return the value of decision
     */
    public String getDecision() {
        return this.decision;
    }

    /**
     * Sets the value of decision
     *
     * @param argDecision Value to assign to this.decision
     */
    public void setDecision(final String argDecision) {
        this.decision = argDecision;
    }

    /**
     * Gets the value of date
     *
     * @return the value of date
     */
    public String getDate() {
        return this.date;
    }

    /**
     * Sets the value of date
     *
     * @param argDate Value to assign to this.date
     */
    public void setDate(final String argDate) {
        this.date = argDate;
    }

    /**
     * Gets the value of rationale
     *
     * @return the value of rationale
     */
    public String getRationale() {
        return this.rationale;
    }

    /**
     * Sets the value of rationale
     *
     * @param argRationale Value to assign to this.rationale
     */
    public void setRationale(final String argRationale) {
        this.rationale = argRationale;
    }
    
    public CommentInfo getComment() {
        return comment;
    }

    public void setComment(CommentInfo comment) {
        this.comment = comment;
    }

    @Override
    public int compareTo(CMDecisionWrapper o) {
        if (o.getComment() != null && getComment() != null &&
            o.getComment().getMeta() != null &&
            o.getComment().getMeta().getCreateTime() != null &&
            getComment().getMeta() != null &&
            getComment().getMeta().getCreateTime() != null ) {
            return o.getComment().getMeta().getCreateTime().compareTo(getComment().getMeta().getCreateTime()) ;
        } else {
            return 0;
        }
    }
}
