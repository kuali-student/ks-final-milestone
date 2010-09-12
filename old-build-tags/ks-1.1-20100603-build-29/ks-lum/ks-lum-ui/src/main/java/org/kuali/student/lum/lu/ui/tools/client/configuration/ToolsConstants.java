/**
 * Copyright 2010 The Kuali Foundation Licensed under the
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

package org.kuali.student.lum.lu.ui.tools.client.configuration;



/**
 * This is a description of what this class does - hjohnson don't forget to fill this in. 
 * 
 *
 * 
 * @author Kuali Student Team (kuali-student@googlegroups.com)
 *
 */
public class ToolsConstants {

	public static String SECTION_CREATE_CLU_SET = "New CLU Set";
	
    // Message keys for top-level section label lookup
    public static final String NEW_CLU_SET_LABEL_KEY = "cluSetInformation";
    public static final String NEW_CLU_SET = "cluSetNew";
    public static final String NEW_CLU_SET_INFO = "cluSetNewInformation";
    public static final String NEW_CLU_SET_CONTENT = "cluSetContent";
    public static final String NEW_CLU_SET_CONTENT_COURSE = "cluSetCourses";
    public static final String NEW_CLU_SET_CONTENT_RANGE = "cluSetCourseRange";
    public static final String NEW_CLU_SET_CONTENT_CLUSET = "clusetSubCluSet";
    public static final String EDIT_CLU_SET = "cluSetEditCluSet";
    public static final String VIEW_CLU_SET = "cluSetViewCluSet";
    public static final String EDIT_CLU_SET_INFO = "cluSetEditInformation";
    public static final String ORGANIZATION = "cluSetOrganization";
    public static final String TITLE = "cluSetTitle";
    public static final String DESCRIPTION = "cluSetDescription";
    public static final String EFFECTIVE_DATE = "cluSetEffectiveDate";
    public static final String EXPIRATION_DATE = "cluSetExpirationDate";
    public static final String CONTENT = "cluSetContent";
    public static final String CONTENT_DIRECTIONS = "contentDirections";
    public static final String CLU_SET_NAME ="cluSetName";
    
    // Field Keys
    public static final String SEARCH_CLU_SET = "search/findCluSet";
    public static final String SEARCH_COURSE = "search/findCourse";
    public static final String CLU_SET_ID = "cluset/id";
    public static final String CLU_SET_ORGANIZATION_FIELD = "cluset/organization";
    public static final String CLU_SET_NAME_FIELD = "cluset/name";
    public static final String CLU_SET_DESCRIPTION_FIELD = "cluset/description";
    public static final String CLU_SET_EFF_DATE_FIELD = "cluset/effectiveDate";
    public static final String CLU_SET_EXP_DATE_FIELD = "cluset/expirationDate";
    public static final String CLU_SET_CLUS_FIELD = "cluset/clus";
    public static final String CLU_SET_CLU_SETS_FIELD = "cluset/clusets";
    public static final String CLU_SET_CLU_SET_RANGE_FIELD = "cluset/clusetRange";
    public static final String CLU_SET_CLU_SET_RANGE_EDIT_FIELD = "cluset/clusetRangeEdit";
    public static final String CLU_SET_CLUSET_RANGE_VIEW_DETAILS_FIELD = "cluset/cluSetRangeViewDetails";
    public static final String CLU_SET_TYPE_FIELD = "cluset/type";
    
    // Swappable Section Keys
    public static final String CLU_SET_SWAP_CLUS = "clus";
    public static final String CLU_SET_SWAP_CLU_SETS = "clusets";
    public static final String CLU_SET_SWAP_CLU_SET_RANGE = "clusetRange";
    
    
    // widget constants
    public static final String CONTENT_BY_APPROVED_COURSES = CLU_SET_CLUS_FIELD;
    public static final String CONTENT_BY_PROPOSED_COURSES = "byProposedCourses";
    public static final String CONTENT_BY_COURSE_RANGE= "byCourseRange";
    public static final String CONTENT_BY_CLU_SETS = CLU_SET_CLU_SETS_FIELD;
//    public static final Map<String, Map<String, String>> editOptionProperties;
//    public static final String EDIT_OPTION_ADD_ITEM_LABEL = "EDIT_OPTION_ADD_ITEM_LABEL";
//    static {
//        editOptionProperties = new HashMap<String, Map<String, String>>();
//
//        Map<String, String> approvedCoursesProps = new HashMap<String, String>(); 
//        approvedCoursesProps.put(EDIT_OPTION_ADD_ITEM_LABEL, "Add Course");
//        editOptionProperties.put(CONTENT_BY_APPROVED_COURSES, approvedCoursesProps);
//
//        Map<String, String> approvedCoursesProps = new HashMap<String, String>(); 
//        approvedCoursesProps.put(EDIT_OPTION_ADD_ITEM_LABEL, "Add Course");
//        editOptionProperties.put(CONTENT_BY_APPROVED_COURSES, approvedCoursesProps);
//    }
    
}

