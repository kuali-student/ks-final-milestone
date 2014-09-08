/**
 * Copyright 2005-2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.lum.lu.ui.krms.util;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krms.util.PropositionTreeUtil;
import org.kuali.student.lum.common.client.lu.LUUIConstants;
import org.kuali.student.lum.lu.ui.krms.dto.CluSetRangeInformation;
import org.kuali.student.lum.lu.ui.krms.dto.CluSetRangeWrapper;
import org.kuali.student.lum.lu.ui.krms.dto.LUPropositionEditor;
import org.kuali.student.r2.common.util.date.DateFormatters;
import org.kuali.student.r2.core.search.dto.SearchParamInfo;
import org.kuali.student.r2.lum.clu.dto.MembershipQueryInfo;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Kuali Student Team
 */
public class CluSetRangeHelper implements Serializable {

    public static final String COURSE_RANGE_COURSE_NUMBER = "1";
    public static final String COURSE_RANGE_LEARNING_OBJECTIVES = "2";
    public static final String COURSE_RANGE_EFFECTIVE_DATE = "3";

    public static final String COURSE_RANGE_COURSE_NUMBER_LBL = "Course Number Range";
    public static final String COURSE_RANGE_LEARNING_OBJECTIVE_LBL = "Learning Objective";
    public static final String COURSE_RANGE_EFFECTIVE_DATE_LBL = "Effective Date Range";

    public static final String CLU_SEARCH_MOSTCURRENT = "lu.search.mostCurrent.union";
    public static final String CLU_SEARCH_PARM_DIV = "lu.queryParam.luOptionalDivision";
    public static final String CLU_SEARCH_PARM_RANGE = "lu.queryParam.luOptionalCrsNoRange";

    public static final String LO_SEARCH_LODESC = "lo.search.loByDescCrossSearch";
    public static final String LO_SEARCH_PARM_LODESC = "lo.queryParam.loDescPlain";
    public static final String LO_SEARCH_PARM_LUTYPE = "lu.queryParam.luOptionalType";

    public static final String CLU_SEARCH_GENERIC = "lu.search.generic";
    public static final String CLU_SEARCH_PARM_DATE1 = "lu.queryParam.luOptionalEffectiveDate1";
    public static final String CLU_SEARCH_PARM_DATE2 = "lu.queryParam.luOptionalEffectiveDate2";

    /**
     * Create a new membership query, set the parameters
     * based on the RangeHelper attributes and return the new membershipquery.
     *
     * @return
     */
    public static MembershipQueryInfo buildMembershipQuery(CluSetRangeWrapper range) {

        MembershipQueryInfo membershipQueryInfo = new MembershipQueryInfo();
        if (CluSetRangeHelper.COURSE_RANGE_COURSE_NUMBER.equals(range.getSearchByCourseRange())) {
            buildCourseRangeQuery(membershipQueryInfo, range);
        } else if (CluSetRangeHelper.COURSE_RANGE_LEARNING_OBJECTIVES.equals(range.getSearchByCourseRange())) {
            buildLearningObjectiveQuery(membershipQueryInfo, range);
        } else if (CluSetRangeHelper.COURSE_RANGE_EFFECTIVE_DATE.equals(range.getSearchByCourseRange())) {
            buildEffectiveDateQuery(membershipQueryInfo, range);
        }

        return membershipQueryInfo;
    }

    /**
     * Set the range label based on the query type.
     */
    public static String buildLabelFromQuery(MembershipQueryInfo membershipQueryInfo) {

        if (membershipQueryInfo != null) {
            List<SearchParamInfo> params = membershipQueryInfo.getQueryParamValues();
            if (membershipQueryInfo.getSearchTypeKey().equals(CluSetRangeHelper.CLU_SEARCH_MOSTCURRENT)) {
                return buildCourseRangeLabel(getParmValue(params, CluSetRangeHelper.CLU_SEARCH_PARM_DIV),
                        getParmValue(params, CluSetRangeHelper.CLU_SEARCH_PARM_RANGE));
            } else if (membershipQueryInfo.getSearchTypeKey().equals(CluSetRangeHelper.LO_SEARCH_LODESC)) {
                return buildLearningObjectiveLabel(getParmValue(params, CluSetRangeHelper.LO_SEARCH_PARM_LODESC));
            } else if (membershipQueryInfo.getSearchTypeKey().equals(CluSetRangeHelper.CLU_SEARCH_GENERIC)) {
                return buildEffectiveDateLabel(getParmValue(params, CluSetRangeHelper.CLU_SEARCH_PARM_DATE1),
                        getParmValue(params, CluSetRangeHelper.CLU_SEARCH_PARM_DATE2));
            }
        }
        return StringUtils.EMPTY;

    }

    /**
     * Static helper method to retrieve the search parameter value for the given key.
     *
     * @param params
     * @param key
     * @return
     */
    public static String getParmValue(List<SearchParamInfo> params, String key) {
        for (SearchParamInfo param : params) {
            if (param.getKey().equals(key)) {
                for (String value : param.getValues()) {
                    return value;
                }
            }
        }
        return null;
    }

    /**
     * Build a new Course Range Query.
     *
     * @param membershipQueryInfo
     */
    private static void buildCourseRangeQuery(MembershipQueryInfo membershipQueryInfo, CluSetRangeWrapper range) {
        membershipQueryInfo.setSearchTypeKey(CluSetRangeHelper.CLU_SEARCH_MOSTCURRENT);

        List<SearchParamInfo> queryParams = new ArrayList<SearchParamInfo>();
        queryParams.add(createSearchParam(CluSetRangeHelper.CLU_SEARCH_PARM_DIV, range.getSubjectCode()));
        queryParams.add(createSearchParam(CluSetRangeHelper.CLU_SEARCH_PARM_RANGE, range.getCourseNumberRange()));
        queryParams.add(CluSearchUtil.getApprovedStateSearchParam());
        membershipQueryInfo.setQueryParamValues(queryParams);
    }

    /**
     * Reset the Label based on a Course Range Query.
     */
    private static String buildCourseRangeLabel(String subjectCode, String courseNumberRange) {
        subjectCode = StringEscapeUtils.escapeHtml(subjectCode);
        courseNumberRange = StringEscapeUtils.escapeHtml(courseNumberRange);
        return "<b>Subject Code:</b> " + subjectCode + " <b>Course Number Range:</b> " + courseNumberRange + " <b>State:</b> Approved";
    }

    /**
     * Build a new Learning Objective Query.
     *
     * @param membershipQueryInfo
     */
    private static void buildLearningObjectiveQuery(MembershipQueryInfo membershipQueryInfo, CluSetRangeWrapper range) {
        membershipQueryInfo.setSearchTypeKey(CluSetRangeHelper.LO_SEARCH_LODESC);

        List<SearchParamInfo> queryParams = new ArrayList<SearchParamInfo>();
        queryParams.add(createSearchParam(CluSetRangeHelper.LO_SEARCH_PARM_LODESC, range.getLearningObjective()));
        queryParams.add(createSearchParam(CluSetRangeHelper.LO_SEARCH_PARM_LUTYPE, LUUIConstants.CLU_TYPE_CREDIT_COURSE));
        membershipQueryInfo.setQueryParamValues(queryParams);
    }

    /**
     * Reset the Label based on a Learning Objective Query.
     */
    private static String buildLearningObjectiveLabel(String learningObjective) {
        learningObjective = StringEscapeUtils.escapeHtml(learningObjective);
        return "<b>Learning Objective:</b> " + learningObjective;
    }

    /**
     * Build a new Effective Date Query.
     *
     * @param membershipQueryInfo
     */
    private static void buildEffectiveDateQuery(MembershipQueryInfo membershipQueryInfo, CluSetRangeWrapper range) {
        membershipQueryInfo.setSearchTypeKey(CluSetRangeHelper.CLU_SEARCH_GENERIC);

        List<SearchParamInfo> queryParams = new ArrayList<SearchParamInfo>();
        String date1 = DateFormatters.QUERY_SERVICE_TIMESTAMP_FORMATTER.format(range.getEffectiveFrom());
        queryParams.add(createSearchParam(CluSetRangeHelper.CLU_SEARCH_PARM_DATE1, date1));

        String date2 = DateFormatters.QUERY_SERVICE_TIMESTAMP_FORMATTER.format(range.getEffectiveTo());
        queryParams.add(createSearchParam(CluSetRangeHelper.CLU_SEARCH_PARM_DATE2, date2));
        membershipQueryInfo.setQueryParamValues(queryParams);
    }

    /**
     * Reset the Label based on an Effective Date Query.
     */
    public static String buildEffectiveDateLabel(String effectiveFrom, String effectiveTo) {
        try {
            // Do not use DateFormatters' DateTimeFormat as it cannot format timezones.
            DateFormat sdf = new SimpleDateFormat(DateFormatters.QUERY_SERVICE_TIMESTAMP_FORMAT);

            String fromDate = DateFormatters.DEFAULT_DATE_FORMATTER.format(sdf.parse(effectiveFrom));
            fromDate = StringEscapeUtils.escapeHtml(fromDate);

            String toDate = DateFormatters.DEFAULT_DATE_FORMATTER.format(sdf.parse(effectiveTo));
            toDate = StringEscapeUtils.escapeHtml(toDate);

            return "<b>Effective From:</b> " + fromDate + " <b>Effective To:</b> " + toDate;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param key
     * @param value
     * @return
     */
    private static SearchParamInfo createSearchParam(String key, String value) {
        SearchParamInfo param = new SearchParamInfo();
        param.setKey(key);

        List<String> values = new ArrayList<String>();
        values.add(value);
        param.setValues(values);

        return param;
    }

    /**
     * validate Course Range.
     *
     * @param propositionEditor
     */
    public static boolean validateCourseRange(LUPropositionEditor propositionEditor, CluSetRangeWrapper range) {
        if (range.getSearchByCourseRange().equals(COURSE_RANGE_COURSE_NUMBER)) {
            return true;
        } else if (range.getSearchByCourseRange().equals(COURSE_RANGE_LEARNING_OBJECTIVES)) {
            if (range.getLearningObjective().isEmpty()){
                String propName = PropositionTreeUtil.getBindingPath(propositionEditor, "cluSet.rangeHelper.learningObjective");
                GlobalVariables.getMessageMap().putError(propName, LUKRMSConstants.KSKRMS_MSG_ERROR_LEARNINGOBJECTIVE_EMPTY);
                return false;
            }
            return true;
        } else if (range.getSearchByCourseRange().equals(COURSE_RANGE_EFFECTIVE_DATE)) {
            if (range.getEffectiveFrom().after(range.getEffectiveTo())) {
                String propName = PropositionTreeUtil.getBindingPath(propositionEditor, "cluSet.rangeHelper.effectiveTo");
                GlobalVariables.getMessageMap().putError(propName, LUKRMSConstants.KSKRMS_MSG_ERROR_COURSERANGE_DATES_OVERLAP);
                return false;
            }
            return true;
        }
        return true;
    }

    /**
     * validate Courses in Range.
     *
     * @param propositionEditor
     */
    public static boolean validateCoursesInRange(LUPropositionEditor propositionEditor, CluSetRangeWrapper range,
                                          CluSetRangeInformation cluSetRange) {
        if (cluSetRange.getClusInRange().isEmpty()) {
            if (range.getSearchByCourseRange().equals(COURSE_RANGE_COURSE_NUMBER)) {
                String propName = PropositionTreeUtil.getBindingPath(propositionEditor, "cluSet.rangeHelper.courseNumberRange");
                GlobalVariables.getMessageMap().putError(propName, LUKRMSConstants.KSKRMS_MSG_ERROR_COURSESINRANGE_EMPTY, COURSE_RANGE_COURSE_NUMBER_LBL);
            } else if (range.getSearchByCourseRange().equals(COURSE_RANGE_LEARNING_OBJECTIVES)) {
                String propName = PropositionTreeUtil.getBindingPath(propositionEditor, "cluSet.rangeHelper.learningObjective");
                GlobalVariables.getMessageMap().putError(propName, LUKRMSConstants.KSKRMS_MSG_ERROR_COURSESINRANGE_EMPTY, COURSE_RANGE_LEARNING_OBJECTIVE_LBL);
            } else if (range.getSearchByCourseRange().equals(COURSE_RANGE_EFFECTIVE_DATE)) {
                String propName = PropositionTreeUtil.getBindingPath(propositionEditor, "cluSet.rangeHelper.effectiveTo");
                GlobalVariables.getMessageMap().putError(propName, LUKRMSConstants.KSKRMS_MSG_ERROR_COURSESINRANGE_EMPTY, COURSE_RANGE_EFFECTIVE_DATE_LBL);
            }
            return false;
        }
        return true;
    }


}
