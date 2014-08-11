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

package org.kuali.student.ap.framework.context;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class CourseSearchConstants {


    public static final String COURSE_SEARCH_RESULT_PAGE = "course_search_result";

    public static final String GEN_EDU_REQUIREMENTS_PREFIX = "course.genEdRequirement.";

    public static final Pattern TERM_PATTERN = Pattern.compile("([a-zA-Z]+)[\\s]+[0-9][0-9]([0-9][0-9])");

    public static final String SUBJECT_AREA = "kuali.lu.subjectArea";

    public static final String CAMPUS_LOCATION = "kuali.org.College";

    public static final String DEGREE_CREDIT_ID_SUFFIX = "kuali.creditType.credit.degree.";

    public static final String ORG_QUERY_SEARCH_BY_TYPE_REQUEST = "org.search.orgInfoByType";

    public static final String ORG_QUERY_SEARCH_SUBJECT_AREAS = "org.search.orgSubjectAreas";

    public static final String ORG_TYPE_PARAM = "org_queryParam_orgType";

    public static final String COURSE_SEARCH_FOR_COURSE_ID = "ksap.course.getcluid";

    public static final String SEARCH_REQUEST_SUBJECT_PARAM = "subject";

    public static final String SEARCH_REQUEST_NUMBER_PARAM = "number";

    public static final String SEARCH_REQUEST_LAST_SCHEDULED_PARAM = "lastScheduledTerm";

    /*Regex to Split Digits and alphabets Eg: COM 348 --> COM  348*/
    public static final String SPLIT_DIGITS_ALPHABETS = "(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)";

    /*Regex for validating the course Code eg: COM 301*/
    public static final String FORMATTED_COURSE_CODE_REGEX = "^[A-Z]{1}[A-Z &]{2,6}\\s[0-9]{3}$";

    /*Regex for validating the un-formatted courses eq:com131 or com    131 */
    public static final String UNFORMATTED_COURSE_CODE_REGEX = "^[a-zA-Z]{1}[a-zA-Z &]{2,7}[0-9]{3}$";

    public static final String COURSE_CODE_WITH_SECTION_REGEX = "^[A-Z]{1}[A-Z &]{2,6}\\s[0-9]{3}\\s[A-Z]{1}[A-Z0-9]{0,1}$";

    // MISC Search params/keys
    public static final String COURSE_SEARCH_DIVISION_SPACEALLOWED = "ks.ap.search.division.parse.allowspace";
    public static final String COURSE_SEARCH_SCALE_CREDIT_DEGREE = "kuali.result.scale.credit.degree";

    // Custom Search Keys
    public static final String KSAP_COURSE_SEARCH_KEY = "kuali.search.type.ksap.coursesearch";
    public static final String KSAP_COURSE_SEARCH_COURSEVERSIONIDS_BY_TERM_SCHEDULED_KEY = "kuali.search.type.ksap.coursesearch.versionid.by.term.scheduled";
    public static final String KSAP_COURSE_SEARCH_COURSEIDS_BY_TERM_OFFERED_KEY = "kuali.search.type.ksap.coursesearch.cluid.by.term.offered";
    public static final String KSAP_COURSE_SEARCH_OFFERED_REG_GROUP_IDS_BY_CO_ID_KEY = "kuali.search.type.ksap.offered.reg.group.ids.by.co.id";
    public static final String KSAP_COURSE_SEARCH_OFFERED_REG_GROUP_IDS_BY_AO_ID_KEY ="kuali.search.type.ksap.offered.reg.group.ids.by.ao.id";
    public static final String KSAP_COURSE_SEARCH_AO_IDS_BY_OFFERED_REG_GROUP_ID_KEY = "kuali.search.type.ksap.ao.ids.by.offered.reg.group.id";
    public static final String KSAP_COURSE_SEARCH_FO_IDS_BY_OFFERED_REG_GROUP_ID_KEY = "kuali.search.type.ksap.fo.ids.by.offered.reg.group.id";
    public static final String KSAP_COURSE_SEARCH_GENERAL_EDUCATION_VALUES_KEY = "kuali.search.type.ksap.coursesearch.general.education";
    public static final String KSAP_COURSE_SEARCH_COURSEIDS_BY_GENERAL_EDUCATION_KEY = "kuali.search.type.ksap.coursesearch.cluid.by.general.education";
    public static final String KSAP_COURSE_SEARCH_LU_BY_CLU_DESCRIPTION_KEY = "ksap.lu.search.description";
    public static final String KSAP_COURSE_SEARCH_LU_BY_LUI_DESCRIPTION_KEY = "ksap.lu.search.offering.description";
    public static final String KSAP_COURSE_SEARCH_LU_BY_CLU_TITLE_KEY = "ksap.lu.search.title";
    public static final String KSAP_COURSE_SEARCH_LU_BY_LUI_TITLE_KEY = "ksap.lu.search.offering.title";
    public static final String KSAP_COURSE_SEARCH_LU_BY_DIVISION_KEY = "ksap.lu.search.division";
    public static final String KSAP_COURSE_SEARCH_LU_BY_DIVISION_AND_CODE_KEY = "ksap.lu.search.divisionAndCode";
    public static final String KSAP_COURSE_SEARCH_LU_BY_DIVISION_AND_LEVEL_KEY = "ksap.lu.search.divisionAndLevel";
    public static final String KSAP_COURSE_SEARCH_LU_BY_CODE_KEY = "ksap.lu.search.exactCode";
    public static final String KSAP_COURSE_SEARCH_LU_BY_LEVEL_KEY = "ksap.lu.search.exactLevel";
    public static final String KSAP_COURSE_SEARCH_LU_BY_FULL_CODE_KEY = "ksap.lu.search.courseCode";
    public static final String KSAP_COURSE_SEARCH_COURSE_INFO_BY_ID_KEY = "ksap.course.info";
    public static final String KSAP_COURSE_SEARCH_COURSEID_TITLE_AND_STATUS_BY_SUBJ_CD_KEY = "kuali.search.type.ksap.search.course.title.id.status.by.subj.code";
    public static final String KSAP_COURSE_SEARCH_ALL_DIVISION_CODES_KEY ="kuali.search.type.ksap.search.all.division" +
            ".codes";

    // Custom Search Result Column Keys
    public static final class SearchResultColumns {
        public static final String CLU_ID = "cluId";
        public static final String REG_GROUP_ID = "registrationGroupId";
        public static final String ACTIVITY_OFFERING_ID = "activityOfferingId";
        public static final String FORMAT_OFFERING_ID = "formatOfferingId";
        public static final String CLU_SET_ID = "cluSetId";
        public static final String CLU_SET_NAME = "cluSetName";
        public static final String CLU_SET_ATTR_VALUE = "cluSetAttrValue";
        public static final String CLU_TITLE = "cluTitle";
        public static final String CLU_STATUS = "cluStatus";
        public static final String DIVISION_CODE = "divisionCode";
        public static final String COURSE_SUBJECT = "course.subject";
        public static final String COURSE_NUMBER = "course.number";
        public static final String COURSE_LEVEL = "course.level";
        public static final String COURSE_NAME = "course.name";
        public static final String COURSE_CODE = "course.code";
        public static final String COURSE_VERSION_INDEPENDENT_ID = "course.versionIndId";
        public static final String COURSE_CREDITS = "course.credits";
    }

    // Custom Search Parameter Keys
    public static final class SearchParameters {
        public static final String ATP_ID = "atpId";
        public static final String ATP_TYPE_KEY = "atpTypeKey";
        public static final String GENED_KEY = "genEdKey";
        public static final String COURSE_OFFERING_ID = "courseOfferingId";
        public static final String ACTIVITY_OFFERING_ID = "activityOfferingId";
        public static final String REG_GROUP_ID = "registrationGroupId";
        public static final String VERSION_IND_ID_LIST = "versionIdList";
        public static final String DIVISION = "division";
        public static final String CODE = "code";
        public static final String LEVEL = "level";
        public static final String QUERYTEXT = "queryText";
        public static final String ATP_ID_LIST = "atpIdList";
        public static final String COURSE_SUBJECT_PREFIX = "courseSubjectPrefix";  //e.g.  ENGL
        public static final String COURSE_SUBJECT_SUFFIX = "courseSubjectSuffix"; //e.g. 101
        public static final String START_DATE = "startDate";
        public static final String END_DATE = "endDate";
    }

    /**
     * Key to look up a configuration value to determine the sorted terms offered
     */
    public static final String TERMS_OFFERED_SORTED_KEY = "ks.ap.search.terms.offered.sorted";

}
