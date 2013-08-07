/*
 * Copyright 2013 The Kuali Foundation
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
package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import java.util.Arrays;
import java.util.List;

import org.kuali.student.enrollment.class2.courseoffering.service.impl.CourseOfferingServiceTestDataLoader.CourseOfferingCreationDetails;
import org.kuali.student.r2.common.util.constants.LuServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Kuali Student Team 
 *
 */
public class TestAutogenRegGroupUserStoryThreeCourseOfferingCreationDetails implements CourseOfferingCreationDetails {
    private static final Logger log = LoggerFactory
            .getLogger(TestAutogenRegGroupUserStoryThreeCourseOfferingCreationDetails.class);
    
        private String[] activityTypeKeys = new String[] {LuServiceConstants.COURSE_ACTIVITY_LECTURE_TYPE_KEY, LuServiceConstants.COURSE_ACTIVITY_LAB_TYPE_KEY};
        private String[] activtyOfferingTypeKeys = new String[] {LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY, LuiServiceConstants.LAB_ACTIVITY_OFFERING_TYPE_KEY};
        
        private int[][]aoMaxEnrollments = new int[][] { {100,35}, {100, 25}, {200, 45}, {300, 100}, {200, 200}, {300,300} };
        private String courseId;
        private String courseOfferingId;

        @Override
        public String getSubjectArea() {
            return "MATH";
        }
        
        @Override
        public int getNumberOfFormats() {
            return 1;
        }
        
        @Override
        public String getFormatOfferingName(int format) {
            return "Lecture/Lab";
        }
        
        @Override
        public String[] getFormatOfferingActivityTypeKeys(int format) {
            
            switch (format) {
            
                case 0:
                    return activtyOfferingTypeKeys;
            }
            
            return null;
        }
        
        @Override
        public String getCourseTitle() {
            return "Test User Story 3";
        }
        
        @Override
        public String getCourseDescription() {
            return "";
        }
        
        @Override
        public String getCourseCode() {
            return "MATH123";
        }
        
        @Override
        public List<String> getCanonicalActivityTypeKeys(int format) {
            switch (format) {
                
                case 0:
                    return Arrays.asList(activityTypeKeys);
            }
            
            return null;
        }

        /* (non-Javadoc)
         * @see org.kuali.student.enrollment.class2.courseoffering.service.impl.CourseOfferingServiceTestDataLoader.CourseOfferingCreationDetails#getActivityOfferingTypeKey(int, int)
         */
        @Override
        public String getActivityOfferingTypeKey(int format, String activtyType) {
            
            int activityIndex = Arrays.asList(activityTypeKeys).indexOf(activtyType);
            
            return activtyOfferingTypeKeys[activityIndex];
        }

        /* (non-Javadoc)
         * @see org.kuali.student.enrollment.class2.courseoffering.service.impl.CourseOfferingServiceTestDataLoader.CourseOfferingCreationDetails#getActivityOfferingCode(int, int)
         */
        @Override
        public String getActivityOfferingCode(int format, String activtyType, int activity) {
            // TODO Auto-generated method stub
            return String.valueOf(activity);
        }

        /* (non-Javadoc)
         * @see org.kuali.student.enrollment.class2.courseoffering.service.impl.CourseOfferingServiceTestDataLoader.CourseOfferingCreationDetails#getActivityOfferingName(int, int)
         */
        @Override
        public String getActivityOfferingName(int format, String activtyType, int activity) {
            
            switch (activity) {
                case 0:
                    return "Lecture";
                case 1:
                    return "Lab";
            }
            // should not happen.
            return null;
        }

        @Override
        public void storeCourseId(String id) {
            this.courseId = id;
        }

        @Override
        public void storeCourseOfferingId(String id) {

            this.courseOfferingId = id;
        }

        /**
         * @return the courseId
         */
        public String getCourseId() {
            return courseId;
        }

        /**
         * @return the courseOfferingId
         */
        public String getCourseOfferingId() {
            return courseOfferingId;
        }

        /* (non-Javadoc)
         * @see org.kuali.student.enrollment.class2.courseoffering.service.impl.CourseOfferingServiceTestDataLoader.CourseOfferingCreationDetails#getNumberOfActivityOfferings(int)
         */
        @Override
        public int getNumberOfActivityOfferings(int format, String activityType) {
            
            int aoIndex = -1;
            
            if (activityType.equals(LuServiceConstants.COURSE_ACTIVITY_LECTURE_TYPE_KEY))
                aoIndex = 0;
            else if (activityType.equals(LuServiceConstants.COURSE_ACTIVITY_LAB_TYPE_KEY))
                aoIndex = 1;
            
            if (aoIndex == -1)
                return 0;
            
            return aoMaxEnrollments[aoIndex].length;
            
        }

        @Override
        public int getActivityOfferingMaxEnrollment(int format, String activityType, int activity) {
            
            int aoIndex = -1;
            
            if (activityType.equals(LuServiceConstants.COURSE_ACTIVITY_LECTURE_TYPE_KEY))
                aoIndex = 0;
            else if (activityType.equals(LuServiceConstants.COURSE_ACTIVITY_LAB_TYPE_KEY))
                aoIndex = 1;
            
            if (aoIndex == -1)
                return -1;
            
            // else
            
            int enrollments[] = aoMaxEnrollments[aoIndex];
            
            if (activity >= enrollments.length)
                return -1;
            else
                return enrollments[activity];
        }
        
        
        
}
