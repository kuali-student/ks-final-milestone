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
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krms.util.KRMSConstants;
import org.kuali.rice.krms.util.PropositionTreeUtil;
import org.kuali.student.lum.lu.ui.krms.dto.CluSetInformation;
import org.kuali.student.lum.lu.ui.krms.dto.LUPropositionEditor;
import org.kuali.student.r2.common.util.date.DateFormatters;
import org.kuali.student.r2.core.search.dto.SearchParamInfo;
import org.kuali.student.r2.lum.clu.dto.MembershipQueryInfo;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Kuali Student Team
 */
public class CluSetRangeHelper implements Serializable {

    private String subjectCode;
    private String courseNumberRange;
    private String learningObjective;
    private Date effectiveFrom;
    private Date effectiveTo;

    private String searchByCourseRange = COURSE_RANGE_COURSE_NUMBER;

    public static final String COURSE_RANGE_COURSE_NUMBER = "1";
    public static final String COURSE_RANGE_LEARNING_OBJECTIVES = "2";
    public static final String COURSE_RANGE_EFFECTIVE_DATE = "3";

    public static final String CLU_SEARCH_MOSTCURRENT = "lu.search.mostCurrent.union";
    public static final String CLU_SEARCH_PARM_DIV = "lu.queryParam.luOptionalDivision";
    public static final String CLU_SEARCH_PARM_RANGE = "lu.queryParam.luOptionalCrsNoRange";

    public static final String LO_SEARCH_LODESC = "lo.search.loByDescCrossSearch";
    public static final String LO_SEARCH_PARM_LODESC = "lo.queryParam.loDescPlain";
    public static final String LO_SEARCH_PARM_LUTYPE = "lu.queryParam.luOptionalType";

    public static final String CLU_SEARCH_GENERIC = "lu.search.generic";
    public static final String CLU_SEARCH_PARM_DATE1 = "lu.queryParam.luOptionalEffectiveDate1";
    public static final String CLU_SEARCH_PARM_DATE2 = "lu.queryParam.luOptionalEffectiveDate2";

    // Do not use DateFormatters' DateTimeFormat as it cannot format timezones.
    public static DateFormat sdf = new SimpleDateFormat(DateFormatters.QUERY_SERVICE_TIMESTAMP_FORMAT);

    public CluSetRangeHelper() {
        this.reset();
    }

    public void reset() {
        this.subjectCode = StringUtils.EMPTY;
        this.courseNumberRange = StringUtils.EMPTY;
        this.learningObjective = StringUtils.EMPTY;
        this.effectiveFrom = new Date();
        this.effectiveTo = new Date();
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getCourseNumberRange() {
        return courseNumberRange;
    }

    public void setCourseNumberRange(String courseNumberRange) {
        this.courseNumberRange = courseNumberRange;
    }

    public String getLearningObjective() {
        return learningObjective;
    }

    public void setLearningObjective(String learningObjective) {
        this.learningObjective = learningObjective;
    }

    public Date getEffectiveFrom() {
        return effectiveFrom;
    }

    public void setEffectiveFrom(Date effectiveFrom) {
        this.effectiveFrom = effectiveFrom;
    }

    public Date getEffectiveTo() {
        return effectiveTo;
    }

    public void setEffectiveTo(Date effectiveTo) {
        this.effectiveTo = effectiveTo;
    }

    public String getSearchByCourseRange() {
        return searchByCourseRange;
    }

    public void setSearchByCourseRange(String searchByCourseRange) {
        this.searchByCourseRange = searchByCourseRange;
    }

    /**
     * Create a new membership query, set the parameters
     * based on the RangeHelper attributes and return the new membershipquery.
     *
     * @return
     */
    public MembershipQueryInfo buildMembershipQuery() {

        MembershipQueryInfo membershipQueryInfo = new MembershipQueryInfo();
        if (this.getSearchByCourseRange().equals(CluSetRangeHelper.COURSE_RANGE_COURSE_NUMBER)) {
            buildCourseRangeQuery(membershipQueryInfo);
        } else if (this.getSearchByCourseRange().equals(CluSetRangeHelper.COURSE_RANGE_LEARNING_OBJECTIVES)) {
            buildLearningObjectiveQuery(membershipQueryInfo);
        } else if (this.getSearchByCourseRange().equals(CluSetRangeHelper.COURSE_RANGE_EFFECTIVE_DATE)) {
            buildEffectiveDateQuery(membershipQueryInfo);
        }

        return membershipQueryInfo;
    }

    /**
     * Set the range label based on the query type.
     */
    public String buildLabelFromQuery(MembershipQueryInfo membershipQueryInfo) {

        if (membershipQueryInfo != null) {
            List<SearchParamInfo> params = membershipQueryInfo.getQueryParamValues();
            if (membershipQueryInfo.getSearchTypeKey().equals(CluSetRangeHelper.CLU_SEARCH_MOSTCURRENT)) {
                return buildCourseRangeLabel(this.getParmValue(params, CluSetRangeHelper.CLU_SEARCH_PARM_DIV),
                        this.getParmValue(params, CluSetRangeHelper.CLU_SEARCH_PARM_RANGE));
            } else if (membershipQueryInfo.getSearchTypeKey().equals(CluSetRangeHelper.LO_SEARCH_LODESC)) {
                return buildLearningObjectiveLabel(this.getParmValue(params, CluSetRangeHelper.LO_SEARCH_PARM_LODESC));
            } else if (membershipQueryInfo.getSearchTypeKey().equals(CluSetRangeHelper.CLU_SEARCH_GENERIC)) {
                return buildEffectiveDateLabel(this.getParmValue(params, CluSetRangeHelper.CLU_SEARCH_PARM_DATE1),
                        this.getParmValue(params, CluSetRangeHelper.CLU_SEARCH_PARM_DATE2));
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
    private void buildCourseRangeQuery(MembershipQueryInfo membershipQueryInfo) {
        membershipQueryInfo.setSearchTypeKey(CluSetRangeHelper.CLU_SEARCH_MOSTCURRENT);

        List<SearchParamInfo> queryParams = new ArrayList<SearchParamInfo>();
        queryParams.add(createSearchParam(CluSetRangeHelper.CLU_SEARCH_PARM_DIV, this.getSubjectCode()));
        queryParams.add(createSearchParam(CluSetRangeHelper.CLU_SEARCH_PARM_RANGE, this.getCourseNumberRange()));
        queryParams.add(CluInformationHelper.getApprovedStateSearchParam());
        membershipQueryInfo.setQueryParamValues(queryParams);
    }

    /**
     * Reset the Label based on a Course Range Query.
     */
    private String buildCourseRangeLabel(String subjectCode, String courseNumberRange) {
        subjectCode = StringEscapeUtils.escapeHtml(subjectCode);
        courseNumberRange = StringEscapeUtils.escapeHtml(courseNumberRange);
        return "<b>Subject Code:</b> " + subjectCode + " <b>Course Number Range:</b> " + courseNumberRange + " <b>State:</b> Approved";
    }

    /**
     * Build a new Learning Objective Query.
     *
     * @param membershipQueryInfo
     */
    private void buildLearningObjectiveQuery(MembershipQueryInfo membershipQueryInfo) {
        membershipQueryInfo.setSearchTypeKey(CluSetRangeHelper.LO_SEARCH_LODESC);

        List<SearchParamInfo> queryParams = new ArrayList<SearchParamInfo>();
        queryParams.add(createSearchParam(CluSetRangeHelper.LO_SEARCH_PARM_LODESC, this.getLearningObjective()));
        queryParams.add(createSearchParam(CluSetRangeHelper.LO_SEARCH_PARM_LUTYPE, "kuali.lu.type.CreditCourse"));
        membershipQueryInfo.setQueryParamValues(queryParams);
    }

    /**
     * Reset the Label based on a Learning Objective Query.
     */
    private String buildLearningObjectiveLabel(String learningObjective) {
        learningObjective = StringEscapeUtils.escapeHtml(learningObjective);
        return "<b>Learning Objective:</b> " + learningObjective;
    }

    /**
     * Build a new Effective Date Query.
     *
     * @param membershipQueryInfo
     */
    private void buildEffectiveDateQuery(MembershipQueryInfo membershipQueryInfo) {
        membershipQueryInfo.setSearchTypeKey(CluSetRangeHelper.CLU_SEARCH_GENERIC);

        List<SearchParamInfo> queryParams = new ArrayList<SearchParamInfo>();
        String date1 = DateFormatters.QUERY_SERVICE_TIMESTAMP_FORMATTER.format(this.getEffectiveFrom());
        queryParams.add(createSearchParam(CluSetRangeHelper.CLU_SEARCH_PARM_DATE1, date1));

        String date2 = DateFormatters.QUERY_SERVICE_TIMESTAMP_FORMATTER.format(this.getEffectiveTo());
        queryParams.add(createSearchParam(CluSetRangeHelper.CLU_SEARCH_PARM_DATE2, date2));
        membershipQueryInfo.setQueryParamValues(queryParams);
    }

    /**
     * Reset the Label based on an Effective Date Query.
     */
    public String buildEffectiveDateLabel(String effectiveFrom, String effectiveTo) {
        try {
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
    private SearchParamInfo createSearchParam(String key, String value) {
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
    public boolean validateCourseRange(LUPropositionEditor propositionEditor) {
        if (this.getSearchByCourseRange().equals(COURSE_RANGE_COURSE_NUMBER)) {
            return true;
        } else if (this.getSearchByCourseRange().equals(COURSE_RANGE_LEARNING_OBJECTIVES)) {
            if (this.getLearningObjective().isEmpty()){
                String propName = PropositionTreeUtil.getBindingPath(propositionEditor, "cluSet.rangeHelper.learningObjective");
                GlobalVariables.getMessageMap().putError(propName, LUKRMSConstants.KSKRMS_MSG_ERROR_LEARNINGOBJECTIVE_EMPTY);
                return false;
            }
            return true;
        } else if (this.getSearchByCourseRange().equals(COURSE_RANGE_EFFECTIVE_DATE)) {
            if (this.getEffectiveFrom().after(this.getEffectiveTo())) {
                String propName = PropositionTreeUtil.getBindingPath(propositionEditor, "cluSet.rangeHelper.effectiveTo");
                GlobalVariables.getMessageMap().putError(propName, LUKRMSConstants.KSKRMS_MSG_ERROR_COURSERANGE_DATES_OVERLAP);
                return false;
            }
            return true;
        }
        return true;
    }


}
