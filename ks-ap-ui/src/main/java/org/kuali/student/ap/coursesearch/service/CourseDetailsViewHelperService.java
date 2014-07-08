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
package org.kuali.student.ap.coursesearch.service;

import org.kuali.rice.krad.uif.service.ViewHelperService;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.ap.coursesearch.dataobject.ActivityOfferingDetailsWrapper;
import org.kuali.student.ap.coursesearch.dataobject.CourseOfferingDetailsWrapper;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.r2.core.acal.infc.Term;

import javax.json.JsonObjectBuilder;
import java.util.List;
import java.util.Map;

/**
 * ViewHelper class used to populate the data on the bottom half of the CourseDetails page (CourseSearchDetailsUI.xml)
 */
public interface CourseDetailsViewHelperService extends ViewHelperService {

    /**
     * Loads up the data objects on the form with data, according to the passed in courseId
     * @param form - Form to populate with data
     * @param courseId - Course ID used to look up the data to be populated on the form
     */
    public void loadCourseSectionDetails(UifFormBase form, String courseId) ;

    /**
     * Map a list of course offerings to a term
     * @param courseIds - Course IDs used to find the desired course offerings
     * @param terms - List of terms to use
     * @return - A map with the key as the termId and the value being a list of CourseOfferingDetailsWrapper
     */
    public Map<String, List<CourseOfferingDetailsWrapper>> processCourseOfferingsByTerm(List<String> courseIds, List<Term> terms) ;

    /**
     * Turn an ActivityOfferingInfo into an ActivityOfferingDetailsWrapper
     * @param ao - ActivityOfferingInfo object to harvest data from
     * @return - An ActivityOfferingDetailsWrapper which is a wrapper for an ActivityOfferingInfo
     */
    public ActivityOfferingDetailsWrapper convertAOInfoToWrapper(ActivityOfferingInfo ao) ;

    /**
     * Sort a list of terms.  Sorting algorithm will be provided by the implementation.
     *
     * @param terms     - List of Terms to be sorted
     * @return - A list of sorted terms
     */
    public List<Term> sortTerms(List<Term> terms);

    /**
     * Gets a filtered list of registration groups for a course offering that are considered valid
     *
     * @param courseOfferingId - Id of the course offering being filtered
     * @param additionalRestrictions - A map of additional restrictions to be used when filtering registration groups
     * @return A list of valid registration groups
     */
    public List<RegistrationGroupInfo> getValidRegGroups(String courseOfferingId, Map<Object, Object> additionalRestrictions);

    /**
     * Creates the json object needed in the add section event when dynamically updating the page.
     *
     * @param termId - id of the term of the course offering the added registration group is in
     * @param courseOfferingCode - Code of the course offering the added registration group is in
     * @param courseOfferingId - Id of the course offering the added registration group is in
     * @param activities - List of Activities being added
     * @param eventList - List of currently being build
     * @return Current list of events being build with the new event added
     */
    public JsonObjectBuilder createAddSectionEvent(String termId, String courseOfferingCode, String courseOfferingId, List<ActivityOfferingDetailsWrapper> activities, JsonObjectBuilder eventList);

    /**
     * Creates the json object needed in the filtering course offerings event when dynamically updating the page
     *
     * @param termId - Id of the term of the course offering being filtered
     * @param courseOfferingCode - Code of the course offering being filtered
     * @param formatOfferingId - Id of the format offering for the activity
     * @param regGroups - A list of valid registration groups
     * @param eventList - List of currently being build
     * @return  Current list of events being build with the new event added
     */
    public JsonObjectBuilder createFilterValidRegGroupsEvent(String termId, String courseOfferingCode, String formatOfferingId, List<RegistrationGroupInfo> regGroups, JsonObjectBuilder eventList);
}
