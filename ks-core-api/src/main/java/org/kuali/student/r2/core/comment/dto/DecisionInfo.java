/**
 * Copyright 2005-2013 The Kuali Foundation
 * 
 * Licensed under the Educational Community License, Version 1.0 (the
 * "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.opensource.org/licenses/ecl1.php
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.kuali.student.r2.core.comment.dto;


/**
 *
 * @author OpenCollab/rSmart KRAD CM Conversion Alliance!
 */
public class DecisionInfo implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String actor;
    private String decision;
    private String date;
    private String rationale;
    
    
    /**
     * Gets the value of id
     *
     * @return the value of id
     */
    public String getId() {
        return this.id;
    }

    /**
     * Sets the value of id
     *
     * @param argId Value to assign to this.id
     */
    public void setId(final String argId) {
        this.id = argId;
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

}
