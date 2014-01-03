/**
 * Copyright 2012 The Kuali Foundation Licensed under the
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
 *
 */
package org.kuali.student.r2.core.class1.search;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.student.r2.common.class1.search.SearchServiceAbstractHardwiredImpl;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.common.util.date.DateFormatters;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;
import org.kuali.student.r2.core.search.util.SearchRequestHelper;
import org.kuali.student.r2.lum.util.constants.LrcServiceConstants;

import javax.persistence.TypedQuery;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This search is being used at manage co and schedule of classes.
 * With Manage co, it's always term and course code or subject area search.
 * But with schedule of classes, it's term with other criterias based at the request.
 *
 *
 * User: swedev
 * Date: 11/18/12
 * Time: 4:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class CourseOfferingManagementSearchImpl extends SearchServiceAbstractHardwiredImpl {



    private static final String OWNER_UI_SUFFIX = " (Owner)";

    private static final Logger LOGGER = Logger.getLogger(CourseOfferingManagementSearchImpl.class);

    public static final class SearchParameters {
        public static final String COURSE_CODE = "courseCode";
        public static final String SUBJECT_AREA = "subjectArea";
        public static final String ATP_ID = "atpId";
        public static final String CROSS_LIST_SEARCH_ENABLED = "crossListSearchEnabled";
        public static final String FILTER_CO_STATES = "filterCoStates";
        public static final String IS_EXACT_MATCH_CO_CODE_SEARCH = "isExactMatchSearch";
        public static final String CO_IDS = "coIds";
        public static final String DEPARTMENT_ID = "departmentId";
        public static final String DESCRIPTION = "description";
        /**
         * For Manage co, it's not needed to fetch the pass/fail and audit records. This is
         * needed for schedule of classes search. So, we turn it on in schedule of classses.
         */
        public static final String INCLUDE_PASSFAIL_AUDIT_HONORS_RESULTS = "includePassFailAuditAndHonorsResults";
    }

    public static final class SearchResultColumns {
        public static final String CODE = "courseOfferingCode";
        public static final String DESC = "courseOfferingDesc";
        public static final String LONG_NAME = "courseOfferingLongName";
        public static final String STATE = "courseOfferingState";
        public static final String CREDIT_OPTION = "courseOfferingCreditOption";
        public static final String GRADING_OPTION = "courseOfferingGradingOption";
        public static final String CO_ID = "courseOfferingId";
        public static final String SUBJECT_AREA = "subjectArea";
        public static final String IS_CROSS_LISTED = "isCrossListedCode";
        public static final String CROSS_LISTED_COURSES = "crossListedCodes";
        public static final String OWNER_CODE = "ownerCode";
        public static final String OWNER_ALIASES = "ownerAliases";
        public static final String DEPLOYMENT_ORG_ID = "deploymentOrgId";
        public static final String CREDIT_OPTION_NAME = "creditOptionName";
        public static final String GRADING_OPTION_NAME = "gradingOptionName";
        public static final String DESC_FORMATTED = "descFormatted";

        public static final String HAS_STUDENT_SELECTABLE_PASSFAIL = "hasStudentSelectablePassFail";
        public static final String CAN_AUDIT_COURSE = "canAuditCourse";
        public static final String IS_HONORS_COURSE = "isHonorsCourse";
    }

    public static final String DEFAULT_EFFECTIVE_DATE = "01/01/2012";

    public static final TypeInfo CO_MANAGEMENT_SEARCH;
    public static final TypeInfo COID_BY_TERM_AND_COURSE_CODE_SEARCH;

    public static final String CO_MANAGEMENT_SEARCH_KEY = "kuali.search.type.lui.courseOfferingManagementDisplay";
    public static final String COID_BY_TERM_AND_COURSE_CODE_SEARCH_SEARCH_KEY = "kuali.search.type.lui.courseOfferingIdByTermAndCourseCode";

    static {
        TypeInfo info = new TypeInfo();
        info.setKey(CO_MANAGEMENT_SEARCH_KEY);
        info.setName("Manage Course Offering Search");
        info.setDescr(new RichTextHelper().fromPlain("Return search results from manage course offering screen quickly"));

        try {
            info.setEffectiveDate(DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.parse("01/01/2012"));
        } catch ( IllegalArgumentException ex) {
            throw new RuntimeException("bad code");
        }
        CO_MANAGEMENT_SEARCH = info;

        info = new TypeInfo();
        info.setKey(COID_BY_TERM_AND_COURSE_CODE_SEARCH_SEARCH_KEY);
        info.setName("Course Offering Id Search By Term and course Code");
        info.setDescr(new RichTextHelper().fromPlain("User friendly search that uses the term and course code"));
        info.setEffectiveDate(DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.parse(DEFAULT_EFFECTIVE_DATE));

        COID_BY_TERM_AND_COURSE_CODE_SEARCH = info;
    }

    @Override
    public TypeInfo getSearchType() {
        return CO_MANAGEMENT_SEARCH;
    }

    @Override
    public TypeInfo getSearchType(String searchTypeKey, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException {
        if (CO_MANAGEMENT_SEARCH_KEY.equals(searchTypeKey)) {
            return CO_MANAGEMENT_SEARCH;
        }
        if (COID_BY_TERM_AND_COURSE_CODE_SEARCH_SEARCH_KEY.equals(searchTypeKey)) {
            return COID_BY_TERM_AND_COURSE_CODE_SEARCH;
        }
        throw new DoesNotExistException("No Search Type Found for key:"+searchTypeKey);
    }

    /**
     * This method returns all the Course Offerings in a specific term and with all other criteria passed into the request.
     * If <code>crossListSearchEnabled</code> flag is enabled, it returns all the related cross listed
     * courses (alternate identifiers) as well.
     *
     * <p>
     * For example, If ENGL100 has 2 cross listed courses HIST200,ART300. And, ENGL555 is a crosslisting
     * course of WMST100 in a term 20122. For a search like <i>atpId=20122 and courseCode=ENGL and crossListSearchEnabled=true</i>,
     * this method returns ENGL100 (with crosslisted courses HIST200 and ART300) and ENGL555 (with WMST100 as owner course).
     * </p>
     *
     * @param searchRequestInfo which has all the criteria needed for the search
     * @param contextInfo context Info
     * @return
     * @throws MissingParameterException if atp id and/or course code is blank
     * @throws OperationFailedException
     */
    @Override
    public SearchResultInfo search(SearchRequestInfo searchRequestInfo, ContextInfo contextInfo)
            throws MissingParameterException, OperationFailedException {

        SearchResultInfo resultInfo = null;

        if (StringUtils.equals(searchRequestInfo.getSearchKey(), CO_MANAGEMENT_SEARCH.getKey())) {
            resultInfo =  searchForCourseBySubjectCodeAndTerm(searchRequestInfo, contextInfo);
        }
        else if (StringUtils.equals(searchRequestInfo.getSearchKey(), COID_BY_TERM_AND_COURSE_CODE_SEARCH.getKey())) {
            resultInfo = searchForCourseOfferingIdByCourseCodeAndTerm(searchRequestInfo, contextInfo);
        }  else {
            throw new OperationFailedException("Unsupported search type: " + searchRequestInfo.getSearchKey());
        }

        return resultInfo;
    }

    protected SearchResultInfo searchForCourseOfferingIdByCourseCodeAndTerm(SearchRequestInfo searchRequestInfo, ContextInfo contextInfo)
            throws MissingParameterException, OperationFailedException
    {
        SearchRequestHelper requestHelper = new SearchRequestHelper(searchRequestInfo);

        String searchCourseCode = requestHelper.getParamAsString(SearchParameters.COURSE_CODE);
        String searchAtpId = requestHelper.getParamAsString(SearchParameters.ATP_ID);

        SearchResultInfo resultInfo = new SearchResultInfo();

        String queryStr = "SELECT" +
                "    ident.code," +
                "    ident.longName," +
                "    lui.luiState," +
                "    lui.id ";

        queryStr = queryStr +
                "    FROM" +
                "    LuiIdentifierEntity ident, " +
                "    LuiEntity lui ";

        queryStr = queryStr +
                "    WHERE" +
                "    lui.id = ident.lui.id" +
                "    AND lui.luiType = 'kuali.lui.type.course.offering'" +
                "    AND lui.atpId = :atpId " +
                "    AND ident.code = :courseCode ";

        queryStr = queryStr + " ORDER BY ident.code";

        TypedQuery<Object[]> query = getEntityManager().createQuery(queryStr, Object[].class);
        query.setParameter(SearchParameters.COURSE_CODE, searchCourseCode);
        query.setParameter(SearchParameters.ATP_ID, searchAtpId);
        List<Object[]> results = query.getResultList();

        for(Object[] resultRow : results){
            int i = 0;
            SearchResultRowInfo row = new SearchResultRowInfo();
            row.addCell(SearchResultColumns.CODE, (String)resultRow[i++]);
            row.addCell(SearchResultColumns.LONG_NAME, (String)resultRow[i++]);
            row.addCell(SearchResultColumns.STATE, (String)resultRow[i++]);
            row.addCell(SearchResultColumns.CO_ID, (String)resultRow[i++]);
            resultInfo.getRows().add(row);
        }

        return resultInfo;
    }

    protected SearchResultInfo searchForCourseBySubjectCodeAndTerm(SearchRequestInfo searchRequestInfo, ContextInfo contextInfo)
            throws MissingParameterException, OperationFailedException
    {

        long start = System.currentTimeMillis();

        SearchRequestHelper requestHelper = new SearchRequestHelper(searchRequestInfo);

        String searchCourseCode = requestHelper.getParamAsString(SearchParameters.COURSE_CODE);
        String searchSubjectArea = requestHelper.getParamAsString(SearchParameters.SUBJECT_AREA);
        String searchAtpId = requestHelper.getParamAsString(SearchParameters.ATP_ID);
        boolean enableCrossListSearch = BooleanUtils.toBoolean(requestHelper.getParamAsString(SearchParameters.CROSS_LIST_SEARCH_ENABLED));
        boolean includePassFailAndAuditRecords = BooleanUtils.toBoolean(requestHelper.getParamAsString(SearchParameters.INCLUDE_PASSFAIL_AUDIT_HONORS_RESULTS));

        /**
         * Build the search query based on the parameters.
         */
        String query = buildSearchQuery(requestHelper);

        /**
         * Get the results
         */
        long startQ1 = System.currentTimeMillis();
        List<Object[]> results = getEntityManager().createQuery(query).getResultList();
        LOGGER.info("*********BigQueryTime**********"+(System.currentTimeMillis()-startQ1) + " ms");

        SearchResultInfo resultInfo = new SearchResultInfo();
        resultInfo.setTotalResults(results.size());
        resultInfo.setStartAt(0);

        Map<String,String> luiIds2AlternateCodes = new HashMap<String, String>();
        Map<String, SearchResultCellInfo> luiIds2OrgCells = new HashMap<String, SearchResultCellInfo>();

        /**
         * This is used to combine together all the rows returned for a single CO. If includePassFailAndAuditRecords is
         * enabled, for a CO, duplicate rows are returned. If includePassFailAndAuditRecords is
         * not enabled, this map never been used as the search returns only unique records.
         */
        Map<String, SearchResultRowInfo> luiIds2ResultRow = new HashMap<String, SearchResultRowInfo>(results.size());

        /**
         * Process search result.
         */
        for (Object[] result : results) {
            buildSearchResultRow(result,searchSubjectArea,searchCourseCode,enableCrossListSearch,includePassFailAndAuditRecords,luiIds2ResultRow,luiIds2OrgCells,luiIds2AlternateCodes,resultInfo);
        }

        if (enableCrossListSearch){
            buildCrossListSearchResult(resultInfo,luiIds2AlternateCodes);
        }

        long end = System.currentTimeMillis();
        LOGGER.info("******TIME TAKEN TO SEARCH CO FOR (Subject Area=" + searchSubjectArea + ",Term=" + searchAtpId + ",Course=" + searchCourseCode + ")*************"+(end-start) + " ms");

        resultInfo.setStartAt(0);
        luiIds2AlternateCodes.clear();
        luiIds2OrgCells.clear();
        luiIds2ResultRow.clear();

        return resultInfo;
    }

    /**
     * This method builds the query to fetch the CourseOfferings based on the search parameters.
     *
     * @param requestHelper
     * @return
     * @throws MissingParameterException
     * @throws OperationFailedException 
     */
    private String buildSearchQuery(SearchRequestHelper requestHelper) throws MissingParameterException, OperationFailedException{

        String searchCourseCode = requestHelper.getParamAsString(SearchParameters.COURSE_CODE);
        String searchSubjectArea = requestHelper.getParamAsString(SearchParameters.SUBJECT_AREA);
        String searchAtpId = requestHelper.getParamAsString(SearchParameters.ATP_ID);
        boolean isExactMatchSearch = BooleanUtils.toBoolean(requestHelper.getParamAsString(SearchParameters.IS_EXACT_MATCH_CO_CODE_SEARCH));
        List<String> filterCOStates = requestHelper.getParamAsList(SearchParameters.FILTER_CO_STATES);
        List<String> coIds = requestHelper.getParamAsList(SearchParameters.CO_IDS);
        String departmentId = requestHelper.getParamAsString(SearchParameters.DEPARTMENT_ID);
        String description = requestHelper.getParamAsString(SearchParameters.DESCRIPTION);
        boolean includePassFailAuditAndHonorsResults = BooleanUtils.toBoolean(requestHelper.getParamAsString(SearchParameters.INCLUDE_PASSFAIL_AUDIT_HONORS_RESULTS));

        if (StringUtils.isBlank(searchAtpId)){
            throw new MissingParameterException("Term code is required to search course offerings");
        }

        String query = "SELECT" +
                "    ident.code," +
                "    ident.longName," +
                "    lui.luiState," +
                "    lrc_rvg1.id," +
                "    lrc_rvg2.id," +
                "    lui.id," +
                "    ident.division," +
                "    ident.type," +
                "    unitsDeployment.orgId," +
                "    lrc_rvg1.name," +
                "    lrc_rvg2.name, " +
                "    lui.formatted";

        if (includePassFailAuditAndHonorsResults){
            query = query + ", luiCodes.type , luiCodes.value ";
        }

        query = query +
                "    FROM" +
                "    LuiIdentifierEntity ident, " +
                "    LuiEntity lui ";

        /*
         If the department id is supplied, join KSEN_LUI_UNITS_CONT_OWNER
         */
        if (StringUtils.isNotBlank(departmentId)){
            query = query +
                    "    JOIN lui.luiContentOwner dept ";
        }

        if (includePassFailAuditAndHonorsResults){
            query = query +
                    "    LEFT JOIN lui.luiCodes luiCodes with luiCodes.type = 'kuali.lu.code.honorsOffering'  ";
        }

        query = query +
                "    LEFT JOIN lui.luiUnitsDeployment unitsDeployment, " +
                "    IN(lui.resultValuesGroupKeys) lui_rvg1," +
                "    ResultValuesGroupEntity lrc_rvg1, " +
                "    IN(lui.resultValuesGroupKeys) lui_rvg2," +
                "    ResultValuesGroupEntity lrc_rvg2 ";

        query = query +
                "    WHERE" +
                "    lui.id = ident.lui.id" +
                "    AND lui.luiType = 'kuali.lui.type.course.offering'" +
                "    AND lui.atpId = '" + searchAtpId + "' " +
                "    AND lrc_rvg1.id = lui_rvg1" +
                "    AND lrc_rvg1.resultScaleId = '" + LrcServiceConstants.RESULT_SCALE_KEY_CREDIT_DEGREE + "' " +
                "    AND lrc_rvg2.id = lui_rvg2" +
                "    AND lrc_rvg2.resultScaleId IN (" +
                "'" + LrcServiceConstants.RESULT_SCALE_KEY_GRADE_ADMIN + "'," +
                "'" + LrcServiceConstants.RESULT_SCALE_KEY_GRADE_LETTER + "'," +
                "'" + LrcServiceConstants.RESULT_SCALE_KEY_GRADE_LETTER_PLUS_MINUS + "'," +
                "'" + LrcServiceConstants.RESULT_SCALE_KEY_GRADE_PF + "'," +
                "'" + LrcServiceConstants.RESULT_SCALE_KEY_GRADE_PNP + "'," +
                "'" + LrcServiceConstants.RESULT_SCALE_KEY_GRADE_COMPLETED + "'," +
                "'" + LrcServiceConstants.RESULT_SCALE_KEY_GRADE_PERCENTAGE + "')";

        if (!includePassFailAuditAndHonorsResults){
            query = query +
                    //Exclude these two types that can cause duplicates.
                    // audit and passfail are moved into different fields, after that there can be only one grading option
                    // of Satisfactory, Letter, or Percentage
                    "    AND lrc_rvg2 NOT IN ('" + LrcServiceConstants.RESULT_GROUP_KEY_GRADE_AUDIT + "'," +
                    "'" + LrcServiceConstants.RESULT_GROUP_KEY_GRADE_PASSFAIL + "') ";
        }
        List<String> crosslistedLuiIds = getCrossListedLuiIds(searchCourseCode,searchSubjectArea,searchAtpId,isExactMatchSearch);
        query = query + getLuiIdentifierSubQuery(searchCourseCode,searchSubjectArea,isExactMatchSearch, crosslistedLuiIds);

        if (coIds != null && !coIds.isEmpty()){
            String coIdsAsString = "'" + StringUtils.join(coIds,"','") + "'";
            query = query + " AND lui.id in (" + coIdsAsString + ") ";
        }

        /**
         * Search by department
         */
        if (StringUtils.isNotBlank(departmentId)){
            query = query + "  AND dept = '" + departmentId + "'  ";
        }

        /*
         Search by description
         */
        if (StringUtils.isNotBlank(description)){
            query = query + "   AND (lui.plain LIKE '%" + description + "%' OR ident.longName LIKE '%" + description + "%') ";
        }

        /**
         * Filter results based on the Lui States
         */
        if (filterCOStates != null && !filterCOStates.isEmpty()){
            String filterCOStatesAsString = "'" + StringUtils.join(filterCOStates,"','") + "'";
            query = query + " AND lui.luiState in (" + filterCOStatesAsString + ") ";
        }

        query = query + " ORDER BY ident.code";

        return query;
    }

    /**
     * This method returns the subquery which joins the LuiEntity with LuiIdentifierEntity and search by
     * division or code.
     *
     * @param searchCourseCode
     * @param searchSubjectArea
     * @param isExactMatchSearch
     * @return
     */
    private String getLuiIdentifierSubQuery(String searchCourseCode, String searchSubjectArea,boolean isExactMatchSearch, List<String> crosslistIds){

        if (StringUtils.isBlank(searchCourseCode) && StringUtils.isBlank(searchSubjectArea)) {
            return StringUtils.EMPTY;
        }

        StringBuilder query = new StringBuilder();
        query.append(" AND ((");

        String coCodeSearchString = "";
        if (StringUtils.isNotBlank(searchCourseCode)){
            if (isExactMatchSearch){
                coCodeSearchString = " ident.code = '" + searchCourseCode + "' ";
            } else {
                coCodeSearchString = " ident.code LIKE '" + searchCourseCode + "%' ";
            }
        }

        if (StringUtils.isNotBlank(searchSubjectArea)){
            if (StringUtils.isBlank(searchCourseCode)){
                query.append(" ident.division = '" + searchSubjectArea + "' ");
            } else {
                query.append(" ident.division = '" + searchSubjectArea + "' AND " + coCodeSearchString);
            }
        } else {
            query.append(coCodeSearchString);
        }

        if (crosslistIds != null && !crosslistIds.isEmpty()) {
            query.append( ") OR (lui.id IN('"+StringUtils.join(crosslistIds,"','")+"')" );
        }

        query.append("))");


        return query.toString();
    }

    /**
     * This method returns the subquery which joins the LuiEntity with LuiIdentifierEntity and search by
     * division or code.
     *
     * @param searchCourseCode
     * @param searchSubjectArea
     * @param isExactMatchSearch
     * @return
     */
    private List<String> getCrossListedLuiIds(String searchCourseCode, String searchSubjectArea, String searchAtpId, boolean isExactMatchSearch){
        String crossListedLuiIdsQuery =
                "SELECT cl2.lui.id " +
                        "FROM LuiIdentifierEntity cl1, LuiIdentifierEntity cl2 " +
                        "WHERE cl2.type = '" + LuiServiceConstants.LUI_IDENTIFIER_CROSSLISTED_TYPE_KEY + "' " +
                        "AND cl2.lui.atpId = '" + searchAtpId + "' " +
                        "AND cl1.lui.id = cl2.lui.id";


        String coCodeSearchString = "";
        if (StringUtils.isNotBlank(searchCourseCode)){
            if (isExactMatchSearch){
                coCodeSearchString = " AND cl1.code = '" + searchCourseCode + "' ";
            } else {
                coCodeSearchString = " AND cl1.code LIKE '" + searchCourseCode + "%' ";
            }
        }

        if (StringUtils.isNotBlank(searchSubjectArea)){
            if (StringUtils.isBlank(searchCourseCode)){
                crossListedLuiIdsQuery += " AND cl1.division = '" + searchSubjectArea + "' ";
            } else {
                crossListedLuiIdsQuery += " AND cl1.division = '" + searchSubjectArea + "' " + coCodeSearchString;
            }
        } else {
            crossListedLuiIdsQuery += coCodeSearchString;
        }
        long startTime = System.currentTimeMillis();
        TypedQuery query = getEntityManager().createQuery(crossListedLuiIdsQuery, String.class);
        LOGGER.info("******Time For Crosslist search******* " + (System.currentTimeMillis()-startTime) +"ms");
        return query.getResultList();
    }

    /**
     * Builds the Result Row and add it to the resultset.
     *
     * @param result data array from a search result row
     * @param searchSubjectArea
     * @param searchCourseCode
     * @param enableCrossListSearch
     * @param includePassFailAndAuditRecords
     * @param luiIds2ResultRow
     * @param luiIds2OrgCells
     * @param luiIds2AlternateCodes
     * @param resultInfo
     */
    private void buildSearchResultRow(Object[] result,
                                     String searchSubjectArea,
                                     String searchCourseCode,
                                     boolean enableCrossListSearch,
                                     boolean includePassFailAndAuditRecords,
                                     Map<String, SearchResultRowInfo> luiIds2ResultRow,
                                     Map<String, SearchResultCellInfo> luiIds2OrgCells,
                                     Map<String,String> luiIds2AlternateCodes,
                                     SearchResultInfo resultInfo){

        SearchResultRowInfo row = new SearchResultRowInfo();

        int i=0;
        String coCode = (String)result[i++];
        row.addCell(SearchResultColumns.CODE,coCode);
        row.addCell(SearchResultColumns.DESC,(String)result[i++]);
        row.addCell(SearchResultColumns.STATE,(String)result[i++]);
        row.addCell(SearchResultColumns.CREDIT_OPTION,(String)result[i++]);

        String graditOption = (String)result[i++];

        row.addCell(SearchResultColumns.GRADING_OPTION,graditOption);

        String courseOfferingId = (String)result[i++];

        String division = (String)result[i++];

        String luiIdentifierType = (String)result[i++];

        boolean isCrossListed = false;
        if (StringUtils.equals(luiIdentifierType,LuiServiceConstants.LUI_IDENTIFIER_CROSSLISTED_TYPE_KEY)){
            isCrossListed = true;
        }

        row.addCell(SearchResultColumns.CO_ID,courseOfferingId);
        row.addCell(SearchResultColumns.SUBJECT_AREA,division);

        boolean includeCurrentRow = isConsiderSearchResult(searchSubjectArea,searchCourseCode,division);

        row.addCell(SearchResultColumns.IS_CROSS_LISTED,"" + isCrossListed);

        //Roll up the org ids (if the org cell exists already then
        String deploymentOrg = (String)result[i++];

        String creditNameForDisplay = StringUtils.stripEnd(StringUtils.lowerCase((String)result[i++])," credits");
        creditNameForDisplay = StringUtils.stripEnd(creditNameForDisplay," credit");

        row.addCell(SearchResultColumns.CREDIT_OPTION_NAME,creditNameForDisplay);

        String gradingName = (String)result[i++];
        row.addCell(SearchResultColumns.GRADING_OPTION_NAME,gradingName);

        String courseDesc = (String)result[i++];

        SearchResultCellInfo defaultPassFailFlag = row.addCell(SearchResultColumns.HAS_STUDENT_SELECTABLE_PASSFAIL, Boolean.FALSE.toString());
        SearchResultCellInfo defaultAuditFlag = row.addCell(SearchResultColumns.CAN_AUDIT_COURSE, Boolean.FALSE.toString());
        SearchResultCellInfo defaultHonorsFlag = row.addCell(SearchResultColumns.IS_HONORS_COURSE, Boolean.FALSE.toString());

        if (includeCurrentRow && includePassFailAndAuditRecords){

            String luCodeType = (String)result[i++];
            String luCodeValue = (String)result[i++];

            boolean continueWithNextRow = processPassFailAndAuditDetails(graditOption,courseOfferingId,
                                                                         isCrossListed,deploymentOrg,
                                                                         defaultPassFailFlag,defaultAuditFlag,
                                                                         luiIds2ResultRow,luiIds2OrgCells,defaultHonorsFlag,luCodeType,luCodeValue);

            if (continueWithNextRow){
                return;
            }
        }

        row.addCell(SearchResultColumns.DESC_FORMATTED,courseDesc);

        //Rollup all the units deployment as a comma separated string.
        if(includeCurrentRow && luiIds2OrgCells.containsKey(courseOfferingId)){
            //Only do this for the root lui to avoid duplication
            SearchResultCellInfo orgCell = luiIds2OrgCells.get(courseOfferingId);
            orgCell.setValue(orgCell.getValue()+","+deploymentOrg);
            //Skip processing the rest of this record because multiple orgIDs are rolled up in the query
            return;
        }

        if (includeCurrentRow){
            resultInfo.getRows().add(row);
            //Put the value into the search result row, and save it in the mapping
            luiIds2OrgCells.put(courseOfferingId, row.addCell(SearchResultColumns.DEPLOYMENT_ORG_ID, deploymentOrg));
            if (includePassFailAndAuditRecords){
                luiIds2ResultRow.put(courseOfferingId,row);
            }
        }

        if (enableCrossListSearch){
            String alternateCodes = luiIds2AlternateCodes.get(courseOfferingId);
            String currentCode = getAlternateCodeUI(coCode,luiIdentifierType);
            if (!StringUtils.contains(alternateCodes,currentCode)){
                String buildAlternateCodes = StringUtils.defaultString(alternateCodes) + currentCode;
                luiIds2AlternateCodes.put(courseOfferingId,buildAlternateCodes + ",");
            }
        }

    }

    /**
     * Handles processing pass/fail and audit data. As each CO returns one row for passfail and one for audit,
     * this method maintains a map to combine the passfail/audit information with the corresponding CO data.
     *
     * @param graditOption
     * @param courseOfferingId
     * @param isCrossListed
     * @param deploymentOrg
     * @param defaultPassFailFlag
     * @param defaultAuditFlag
     * @param luiIds2ResultRow
     * @param luiIds2OrgCells
     *
     * @return false to skip processing the remaining logic for this particular row.
     */
    private boolean processPassFailAndAuditDetails(String graditOption,
                                                String courseOfferingId,
                                                boolean isCrossListed,
                                                String deploymentOrg,
                                                SearchResultCellInfo defaultPassFailFlag,
                                                SearchResultCellInfo defaultAuditFlag,
                                                Map<String, SearchResultRowInfo> luiIds2ResultRow,
                                                Map<String, SearchResultCellInfo> luiIds2OrgCells,
                                                SearchResultCellInfo defaultHonorsFlag,
                                                String luCodeType,String luCodeValue){

        if(luiIds2ResultRow.containsKey(courseOfferingId)){
            SearchResultRowInfo resultRow = luiIds2ResultRow.get(courseOfferingId);

            if (StringUtils.equals(graditOption,LrcServiceConstants.RESULT_GROUP_KEY_GRADE_PASSFAIL)){
                resultRow.getCells().get(10).setValue(Boolean.TRUE.toString());
            } else if (StringUtils.equals(graditOption,LrcServiceConstants.RESULT_GROUP_KEY_GRADE_AUDIT)){
                resultRow.getCells().get(11).setValue((Boolean.TRUE.toString()));
            } else {
                resultRow.getCells().get(4).setValue(graditOption);
            }

            if (StringUtils.equals(luCodeType,LuiServiceConstants.HONORS_LU_CODE) &&
                StringUtils.equalsIgnoreCase(luCodeValue,"true")){
                resultRow.getCells().get(12).setValue((Boolean.TRUE.toString()));
            }

            if(luiIds2OrgCells.containsKey(courseOfferingId) && !isCrossListed){
                //Only do this for the root lui to avoid duplication
                SearchResultCellInfo orgCell = luiIds2OrgCells.get(courseOfferingId);
                if (orgCell != null && orgCell.getValue() != null) {
                    String[] deploymentOrgs = new String[] {orgCell.getValue()};
                    if (!Arrays.asList(deploymentOrgs).contains(deploymentOrg)) {
                        orgCell.setValue(orgCell.getValue()+","+deploymentOrg);
                    }
                }
                //Skip processing the rest of this record because multiple orgIDs are rolled up in the query
            }
            return true;
        } else {
            if (StringUtils.equals(graditOption,LrcServiceConstants.RESULT_GROUP_KEY_GRADE_PASSFAIL)){
                defaultPassFailFlag.setValue(Boolean.TRUE.toString());
            } else if (StringUtils.equals(graditOption,LrcServiceConstants.RESULT_GROUP_KEY_GRADE_AUDIT)) {
                defaultAuditFlag.setValue(Boolean.TRUE.toString());
            }

            if (StringUtils.equals(luCodeType,LuiServiceConstants.HONORS_LU_CODE) &&
                StringUtils.equalsIgnoreCase(luCodeValue,"true")){
                defaultHonorsFlag.setValue((Boolean.TRUE.toString()));
            }
        }

        return false;
    }

    /**
     * Builds the crosslist details for each CO.
     *
     * @param resultInfo
     * @param luiIds2AlternateCodes
     */
    private void buildCrossListSearchResult(SearchResultInfo resultInfo,Map<String,String> luiIds2AlternateCodes){

        /**
         * If the result needs all the alternate code, iterate all the result set rows and look for
         * the matching Lui. If found, get all the alternate code and add it to the existing result
         * set.
         */
        for (SearchResultRowInfo row : resultInfo.getRows()){

            String courseOfferingCode = row.getCells().get(0).getValue();
            String courseOfferingId = row.getCells().get(5).getValue();
            boolean isCrossListed = BooleanUtils.toBoolean(row.getCells().get(7).getValue());

            String alternateCodes = luiIds2AlternateCodes.get(courseOfferingId);
            String ownerCode;
            String ownerAliases;
            if (!isCrossListed){
                alternateCodes = StringUtils.remove(alternateCodes,courseOfferingCode + OWNER_UI_SUFFIX);
                ownerCode = courseOfferingCode;
            } else {
                alternateCodes = StringUtils.remove(alternateCodes,courseOfferingCode);
                String partOfCodes = alternateCodes.substring(0, alternateCodes.indexOf(OWNER_UI_SUFFIX));
                int idx = alternateCodes.substring(0, alternateCodes.indexOf(OWNER_UI_SUFFIX)).lastIndexOf(",");
                if(idx >= 0){
                    ownerCode = partOfCodes.substring(idx + 1);
                }else{
                    ownerCode = partOfCodes.substring(0);
                }
            }

            ownerAliases = StringUtils.remove(luiIds2AlternateCodes.get(courseOfferingId),ownerCode + OWNER_UI_SUFFIX);
            row.addCell(SearchResultColumns.OWNER_CODE,ownerCode);
            row.addCell(SearchResultColumns.OWNER_ALIASES,ownerAliases);
            row.addCell(SearchResultColumns.CROSS_LISTED_COURSES,alternateCodes);

        }
    }

    private boolean isConsiderSearchResult(String searchSubjectArea, String searchCourseCode, String division){
        /*
         Both subject area and course code can be empty with schedule of classes as the search may be with
         other criterias like instructor, dept, title etc
        */
        if (StringUtils.isBlank(searchSubjectArea) && StringUtils.isBlank(searchCourseCode)){
            return true;
        } else if (StringUtils.equals(searchSubjectArea,division) || StringUtils.startsWith(searchCourseCode,division)){
            /*
             This is to make sure we consider only the user entered subject area or division in Manage CO.
             */
            return true;
        }
        return false;
    }

    /**
     * Append <code>"Owner"</code> if a lui identifier is of official type. This is used for display purpose.
     *
     * @param luiAlternateCode
     * @param luiIdentifierType
     * @return
     */
    protected String getAlternateCodeUI(String luiAlternateCode,String luiIdentifierType){
        if (StringUtils.equals(luiIdentifierType,LuiServiceConstants.LUI_IDENTIFIER_OFFICIAL_TYPE_KEY)){
            return luiAlternateCode + OWNER_UI_SUFFIX;
        } else {
            return luiAlternateCode;
        }
    }




}
