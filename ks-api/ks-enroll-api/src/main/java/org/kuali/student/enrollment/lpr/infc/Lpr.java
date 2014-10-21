/*
 * Copyright 2011 The Kuali Foundation 
 *
 * Licensed under the Educational Community License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0 
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.enrollment.lpr.infc;

import java.util.List;
import org.kuali.rice.core.api.util.type.KualiDecimal;

import org.kuali.student.r2.common.infc.Relationship;

/**
 * Detailed information about a single LUI to Person Relation. This is
 * used to link together a learning unit instance and a person in such
 * widely defined domains as a student registering in a course or an
 * instructor being assigned to advise students in a particular
 * program.
 */

public interface Lpr 
    extends Relationship {
        
    /**
     * Unique identifier for a Learning Unit Instance (LUI).
     * 
     * @name LUI Id
     * @required
     */
    public String getLuiId();

    /**
     * Unique identifier for a person record.
     * 
     * @name Person Id
     * @required
     */
    public String getPersonId();

    /**
     * The ATP for the LPR.
     * 
     * This the is the ATP that governs the creation and management of
     * this LPR.  Typically is will be the same as the ATP associated
     * with the LUI but does not have to be.
     * 
     * @name ATP Id
     * @required
     */
    public String getAtpId();

    /**
     * The Id of the "master" LPR. Often LPRs are created by virtue of
     * another LPR.  This field contains the id of that "master" lpr.
     * If null then this is the master.
     * 
     * @name Master LPR Id
     */
    public String getMasterLprId();

    /**
     * Valid range should be between 0 and 100.00 inclusive.
     * 
     * @name Commitment Percent
     * @impl Although this is a string it is expected to be parsable
     * as a floating point number because it is stored in the database
     * as a number so it can be easily queried This was done because
     * of the rounding problems with many Floating implementations
     */
    public KualiDecimal getCommitmentPercent();

    /**
     * The keys of the result values groups to be applied to this LPR
     * 
     * For example, setting the grading option to pass/fail or the
     * credits to 3 for this course.
     * 
     * @name Result Values Group Keys
     */
    public List<String> getResultValuesGroupKeys();

    /**
     * The code of the crosslisted course.
     *
     * For example if ENGL255 is actual course crosslisted with WMST255,
     * when WMST255 is added id will be the one of ENGL255, and code
     * WMST255 will be saved in crossList field (for displaying purpose)
     *
     * @name Cross Listed Code
     */
    public String getCrossListedCode();
}
