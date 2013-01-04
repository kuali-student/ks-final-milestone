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
package org.kuali.student.enrollment.courseofferingset.infc;

import java.util.Date;
import org.kuali.student.r2.common.infc.IdEntity;

/**
 * Set of Offered Courses, Soc.
 * 
 * Identifies a set of courses that are offered within a term.
 * 
 * @author nwright
 */
public interface Soc
        extends IdEntity {

    /**
     * Academic term of the courses in the course offering set
     * 
     * @name Term Id
     * @readOnly
     * @required
     * @impl set during the #createSoc
     */
    public String getTermId();

    /**
     * Subject Area of the courses in the course offering set
     * 
     * This is the first part of the course number, for example "ENG" in "ENG 101"
     * 
     * 
     * THIS IS A PLACEHOLDER FOR DEFINING DEPARTMENTAL SOCS
     * NOTE: if a course is cross-listed it is not clear yet if it appears in 
     * Socs for both subject areas or just the one main one for the course.
     * 
     * @name Subject Area
     */
    @Deprecated
    public String getSubjectArea();

    /**
     * The Organization id of the content owner of the courses in this course offering 
     * set.
     *  
     * THIS IS A PLACEHOLDER FOR DEFINING DEPARTMENTAL SOCS
     * NOTE: should this be a list of org ids not just one?
     * 
     * @name Units Content Owner
     */
    @Deprecated
    public String getUnitsContentOwnerId();

    /**
     * Date of the last time this SOC was submitted to the scheduler
     * 
     * @name Last Scheduling Run Completed
     * @readOnly
     * @impl calculated based on the scheduling batch
     */
    public Date getLastSchedulingRunStarted();
    
    /**
     * Date the SOC scheduling run completed
     * 
     * @name Last Scheduling Run Completed
     * @readOnly
     * @impl calculated from schedule response for the soc
     */
    public Date getLastSchedulingRunCompleted();


    /**
     * Date this SOC was submitted for publishing
     * 
     * @name Publishing Started
     * @readOnly
     * @impl Calculated ????
     */
    public Date getPublishingStarted();
    
    /**
     * Date the publishing process was initiated
     * 
     * @name Publishing Completed
     * @readOnly
     * @impl calculated ?????
     */
    public Date getPublishingCompleted();

}
