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
package org.kuali.student.ap.coursesearch;

/**
 * Stores information on a course search result including the number of times it was seen during the search
 */
public class Hit implements Comparable<Hit>{
    public String courseID;
    public int count = 0;

    public Hit(String courseID) {
        this.courseID = courseID;
        count = 1;
    }

    @Override
    public boolean equals(Object other) {
        return courseID.equals(((Hit) other).courseID);
    }

    @Override
    public int hashCode() {
        return courseID.hashCode();
    }

    @Override
    public int compareTo(Hit y) {
        if (this == null)
            return -1;
        if (y == null)
            return 1;
        return y.count - this.count;
    }
}