/*
 * Copyright 2010 The Kuali Foundation 
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

package org.kuali.student.enrollment.courseoffering.infc;

import org.kuali.student.r2.common.infc.ValidationResult;

import java.util.List;

/**
 * AOClusterVerifyResults is a  collection of information gleaned from the
 * verification of an ActivityOfferingCluster
 *
 * @author Sri komandur@uw.edu
 */
public interface AOClusterVerifyResults {

    /**
     * The number of registration groups that exist
     *
     * @name Existing RG Count
     */
    public Integer getExistingRGCount();

    /**
     * The number of valid registration groups that exist
     *
     * @name Valid RG Count
     * @impl Invalid RGs: time conflicts and canceled ActivityOfferings
     */
    public Integer getValidRGCount();

    /**
     * Indicates if regeneration is needed
     *
     * @name Is Regeneration Needed
     */
    public Boolean getIsRegenerationNeeded();

    /**
     * Validation result messages
     *
     * @name Validation Results
     */
    public List<? extends ValidationResult> getValidationResults();
}