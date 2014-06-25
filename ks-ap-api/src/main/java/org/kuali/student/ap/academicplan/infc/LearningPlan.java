/*
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
 */
package org.kuali.student.ap.academicplan.infc;

import java.util.Date;

import org.kuali.student.r2.common.infc.IdEntity;

/**
 * Student's Learning Plan that contains a list of plan items
 *
 * @author Kuali Student Team (ks-collab@kuali.org)
 * @version 1.0 (Dev)
 */
public interface LearningPlan extends IdEntity {

    /**
     * Plan's associated student id
     * @name Student Id
     * @required
     */
    public String getStudentId();

    /**
     * indicates whether the student has shared this plan (e.g. for viewing by an advisor)
     * @name Shared
     */
    public Boolean getShared();

    /**
     * @name ProgramId
     */
	public String getProgramId();
	
	/**
     * @name effectiveDate
     */
    public Date getEffectiveDate();
    
	/**
     * @name expirationDate
     */
    public Date getExpirationDate();

}
