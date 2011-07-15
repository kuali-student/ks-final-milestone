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
import org.kuali.student.r2.common.infc.TimeAmount;
import org.kuali.student.r2.lum.lu.dto.FeeInfo;
import org.kuali.student.r2.lum.lu.dto.RevenueInfo;
import org.kuali.student.r2.lum.lu.infc.Expenditure;
import org.kuali.student.r2.lum.lu.infc.Fee;
import org.kuali.student.r2.lum.lu.infc.Revenue;

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
    
    /**
     * Name of the course used in the college catalog.
     * @name Course Title
     */
    public String getStudyTitle();

    /**
     * Organization(s) that is responsible for the delivery - and all associated logistics - of the Lui
     * @name Units Deployment
     */
    public List<String> getUnitsDeployment();
    
    /**
     * Organization(s) that is responsible for the academic content of the Lui as approved in its canonical form
     * @name Units Content Owner
     */
    public List<String> getUnitsContentOwner();

    /******** Assessment Information ***************/
    /**
     * The options/scales that indicate the allowable grades that can be awarded.
     * If the value is set here then the Clu must have a grading option set on the
     * canonical activity. 
     * 
     * ResultValuesGroup will contain grade values valid for this course offering
     * 
     * @name: Grading Options
     */
    public List<String> getGradingOptions();
    
    /**
     * Fees associated with the course offering. 
     * @name Fees
     */    
    public List<FeeInfo> getFees();
    
    /**
     * Organization that receives the revenue associated with the course
     * @name Revenues
     */
    public List<RevenueInfo> getRevenues();
    
    
    /**
     * Organization that incurs the cost associated with the course
     * @name Expenditure
     */
    public Expenditure getExpenditure();

    /**
     * Indicates whether a RegistrationGroup has a waitlist
     * @name Has Waitlist
     */
    public Boolean getHasWaitlist();

    /**
     * Indicates the type of waitlist as it relates to processing students on and off
     * @name Waitlist TypeKey
     */
    public String getWaitlistTypeKey();

    /**
     * Maximum number of students to be allowed on the wait list
     * @name Waitlist Maximum
     */
    public Integer getWaitlistMaximum();    
    
    /**
     * Indicates if the waitlist requires checkin
     * @name Is Waitlist Checkin Required
     */
    public Boolean getIsWaitlistCheckinRequired();
    
    /**
     * Frequency for the waitlist checkin
     * @name Waitlist Checkin Frequency 
     */
    public TimeAmount getWaitlistCheckinFrequency();
}