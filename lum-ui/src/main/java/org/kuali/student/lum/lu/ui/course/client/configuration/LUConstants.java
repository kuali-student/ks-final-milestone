/*
 * Copyright 2007 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.lum.lu.ui.course.client.configuration;

/**
 * This is a description of what this class does - hjohnson don't forget to fill this in. 
 * 
 *
 * 
 * @author Kuali Student Team (kuali-student@googlegroups.com)
 *
 */
public class LUConstants {
    
    //TODO These should probably be somewhere else as they are LUM wide constants - not just
    //     UI specific
    
    public final static String LU_TYPE_CREDIT_COURSE = "Course";
    
    // Valid states for Credit Course
    public final static String LU_STATE_PROPOSED = "Proposed"; // Should this be Draft
    public final static String LU_STATE_SUBMITTED = "Submitted";
    public final static String LU_STATE_WITHDRAWN = "Withdrawn";
    public final static String LU_STATE_APPROVED = "Approved";
    public final static String LU_STATE_NOT_APPROVED = "Not Approved";// Maybe Rejected would be a better value?
    public final static String LU_STATE_ACTIVATED = "Activated";
    public final static String LU_STATE_RETIRED = "Retired";

}
