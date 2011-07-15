/*
 * Copyright 2011 The Kuali Foundation Licensed under the Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.osedu.org/licenses/ECL-2.0 Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing permissions and limitations under the License.
 */
package org.kuali.student.enrollment.lrr.infc;

import java.util.List;

import org.kuali.student.r2.common.infc.IdEntity;

/**
 * Information about the Learning Result Record Info. 
 */
public interface LearningResultRecord extends IdEntity {

    /**
     * @name Lui Person Relation Id LPR to which this LRR belongs to
     */
    public String getLprId();

    /**
     * @name Result Value Group Id Result Value Group to which the result value contained in this LRR belongs. The Id will
     * come from LRC service
     */
    public String getResultValueGroupId();

    /**
     * Result Value (Grade, Credits, etc). References a unique Id in the LRC service
     * @name Result Value Id
     */
    public String getResultValueId();

    /**
     * List of source record ids that were responsible in generating the result value. 
     * E.g Manual Entry, Articulation or Result transformation
     * @name Result Source
     */
    public List<String> getResultSourceIdList();
}
