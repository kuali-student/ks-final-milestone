package org.kuali.student.ap.framework.context;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: Kamal
 * Date: 11/4/11
 * Time: 10:22 AM
 */
public class CourseSearchConstants {


    public static final String COURSE_SEARCH_RESULT_PAGE = "course_search_result";

    public static final String GEN_EDU_REQUIREMENTS_PREFIX = "course.genEdRequirement.";

    public static final Pattern TERM_PATTERN = Pattern.compile("([a-zA-Z]+)[\\s]+[0-9][0-9]([0-9][0-9])");

    public static final String SUBJECT_AREA = "kuali.lu.subjectArea";

    public static final String CAMPUS_LOCATION = "kuali.org.College";

    public static final String DEGREE_CREDIT_ID_SUFFIX = "kuali.creditType.credit.degree.";

    public static final String ORG_QUERY_SEARCH_BY_TYPE_REQUEST = "org.search.orgInfoByType";

    public static final String ORG_QUERY_SEARCH_SUBJECT_AREAS = "org.search.orgCurriculum";

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

    // Search Keys
    public static final String COURSE_SEARCH_TYPE_DESCRIPTION = "ksap.lu.search.description";
    public static final String COURSE_SEARCH_TYPE_CO_DESCRIPTION = "ksap.lu.search.offering.description";
    public static final String COURSE_SEARCH_TYPE_TITLE = "ksap.lu.search.title";
    public static final String COURSE_SEARCH_TYPE_CO_TITLE = "ksap.lu.search.offering.title";
    public static final String COURSE_SEARCH_TYPE_DIVISION = "ksap.lu.search.division";
    public static final String COURSE_SEARCH_TYPE_DIVISIONANDCODE = "ksap.lu.search.divisionAndCode";
    public static final String COURSE_SEARCH_TYPE_DIVISIONANDLEVEL = "ksap.lu.search.divisionAndLevel";
    public static final String COURSE_SEARCH_TYPE_EXACTCODE = "ksap.lu.search.exactCode";
    public static final String COURSE_SEARCH_TYPE_EXACTLEVEL = "ksap.lu.search.exactLevel";
    public static final String COURSE_SEARCH_TYPE_COURSECODE = "ksap.lu.search.courseCode";

    // Search Params
    public static final String COURSE_SEARCH_PARAM_DIVISION = "division";
    public static final String COURSE_SEARCH_PARAM_LEVEL = "level";
    public static final String COURSE_SEARCH_PARAM_CODE = "code";
    public static final String COURSE_SEARCH_PARAM_TERMLIST = "termList";
    public static final String COURSE_SEARCH_PARAM_QUERYTEXT = "queryText";


}
