/*
 * Copyright 2011 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may	obtain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.enrollment.lui.infc;


import java.util.List;

import org.kuali.student.enrollment.lui.dto.LuiInstructorInfo;
import org.kuali.student.r2.common.infc.HasEffectiveDates;
import org.kuali.student.r2.common.infc.IdEntity;

/**
 * Detailed information about a single LUI.
 */
public interface Lui extends IdEntity, HasEffectiveDates {

    /**
     * Name: Lui Code
     * Code identifier/name for the LUI. This is typically used
     * human readable form (e.g. ENGL 100 section 123).
     */
    public String getLuiCode();
  

    /**
     * Name: Clu Id
     * Unique identifier for the Canonical Learning Unit (CLU) of which this is an instance.
     */
    public String getCluId();
 
    /**
     * Name: ATP Key
     * Unique identifier for the Academic Time Period (ATP) for which this instance is offered.
     */
    public String getAtpKey();
 
    /**
     * Identifies the department and/subject code of the course as reflected in the clu catalog.
     * This should correspond to the value in the canonical clu.
     * @name Study Subject Area
     */
    public String getStudySubjectArea();

    /**
     * Total maximum number of "seats" or enrollment slots that can be filled for the lui. 
     * @name Maximum Enrollment
     */    
    public Integer getMaximumEnrollment();
    
    /**
     * Total minimum number of seats that must be filled for the lui 
     * @name Minimum  Enrollment
     */
    public Integer getMinimumEnrollment();

    /**
     * Instructors for the Lui
     * @name Instructors
     */
    public List<LuiInstructorInfo> getInstructors();
    
}

