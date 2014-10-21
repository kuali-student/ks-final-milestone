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
package org.kuali.student.ap.coursesearch.dataobject;

import org.kuali.student.ap.coursesearch.util.CollectionListPropertyEditor;
import org.kuali.student.ap.coursesearch.util.CourseDetailsUtil;

import java.util.List;
import java.util.Map;

/**
 * Common implementation for wrappers that need to hold requisite information
 */
public class WrapperWithRequisites {

    private List<String> courseRequisites;
    private Map<String, List<String>> courseRequisitesMap;

    public List<String> getCourseRequisites() {
        return courseRequisites;
    }

    public void setCourseRequisites(List<String> courseRequisites) {
        this.courseRequisites = courseRequisites;
    }

    public Map<String, List<String>> getCourseRequisitesMap() {
        return courseRequisitesMap;
    }

    public void setCourseRequisitesMap(Map<String, List<String>> courseRequisitesMap) {
        this.courseRequisitesMap = courseRequisitesMap;
    }

    /**
     * Determine if there are any requisites by checking the individual parts.
     * There are some other parts that we ignore.
     * @return True if none of the parts of interest exist, false otherwise
     */
    public boolean displayNone() {
        boolean hasPre = courseRequisitesMap.containsKey(CourseDetailsUtil.PREREQUISITE_KEY);
        boolean hasCo = courseRequisitesMap.containsKey(CourseDetailsUtil.COREQUISITE_KEY);
        boolean hasAnti = courseRequisitesMap.containsKey(CourseDetailsUtil.ANTIREQUISITE_KEY);

        return !hasPre && !hasCo && !hasAnti;
    }

    public String getCourseRequisitesForUI() {
        CollectionListPropertyEditor editor = new CollectionListPropertyEditor();
        editor.setValue(this.getCourseRequisites());
        return editor.getAsText();
    }

    private String getRequisitesForUI(String key) {
        String returnValue = null;
        if (courseRequisitesMap.containsKey(key)) {
            CollectionListPropertyEditor editor = new CollectionListPropertyEditor();
            editor.setValue(courseRequisitesMap.get(key));
            returnValue =  key + ": " + editor.getAsText();
        }
        return returnValue;
    }

    public String getPreRequisitesForUI() {
        return getRequisitesForUI(CourseDetailsUtil.PREREQUISITE_KEY);
    }

    public String getCoRequisitesForUI() {
        return getRequisitesForUI(CourseDetailsUtil.COREQUISITE_KEY);
    }

    public String getAntiRequisitesForUI() {
        return getRequisitesForUI(CourseDetailsUtil.ANTIREQUISITE_KEY);
    }

}
