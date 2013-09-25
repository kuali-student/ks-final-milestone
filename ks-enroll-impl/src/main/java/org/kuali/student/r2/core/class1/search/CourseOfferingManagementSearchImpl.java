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
import org.kuali.student.r2.common.dao.GenericEntityDao;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.common.util.constants.LprServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.common.util.date.DateFormatters;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;
import org.kuali.student.r2.core.search.util.SearchRequestHelper;
import org.kuali.student.r2.lum.util.constants.LrcServiceConstants;

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

    private GenericEntityDao genericEntityDao;

    private static final String OWNER_UI_SUFFIX = " (Owner)";

    private static final Logger LOGGER = Logger.getLogger(CourseOfferingManagementSearchImpl.class);

    public static final class SearchParameters {
        public static final String COURSE_CODE = "courseCode";
        public static final String SUBJECT_AREA = "subjectArea";
        public static final String ATP_ID = "atpId";
        public static final String CROSS_LIST_SEARCH_ENABLED = "crossListSearchEnabled";
        public static final String FILTER_CO_STATES = "filterCoStates";
        public static final String IS_EXACT_MATCH_CO_CODE_SEARCH = "isExactMatchSearch";
        public static final String INSTRUCTOR_ID = "instructorId";
        public static final String DEPARTMENT_ID = "departmentId";
        public static final String DESCRIPTION = "description";
        /**
         * For Manage co, it's not needed to fetch the pass/fail and audit records. This is
         * needed for schedule of classes search. So, we turn it on in schedule of classses.
         */
        public static final String INCLUDE_PASSFAIL_AUDIT_RESULTS = "includePassFailAndAuditResults";
    }

    public static final class SearchResultColumns {
        public static final String CODE = "courseOfferingCode";
        public static final String DESC = "courseOfferingDesc";
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
    }
    public static final TypeInfo CO_MANAGEMENT_SEARCH;

    public static final String CO_MANAGEMENT_SEARCH_KEY = "kuali.search.type.lui.courseOfferingManagementDisplay";

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
    }

    @Override
    public TypeInfo getSearchType() {
        return CO_MANAGEMENT_SEARCH;
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

        if (!StringUtils.equals(searchRequestInfo.getSearchKey(), CO_MANAGEMENT_SEARCH.getKey())) {
            throw new OperationFailedException("Unsupported search type: " + searchRequestInfo.getSearchKey());
        }

        SearchRequestHelper requestHelper = new SearchRequestHelper(searchRequestInfo);
        long start = System.currentTimeMillis();
        String searchCourseCode = requestHelper.getParamAsString(SearchParameters.COURSE_CODE);
        String searchSubjectArea = requestHelper.getParamAsString(SearchParameters.SUBJECT_AREA);
        String searchAtpId = requestHelper.getParamAsString(SearchParameters.ATP_ID);
        boolean enableCrossListSearch = BooleanUtils.toBoolean(requestHelper.getParamAsString(SearchParameters.CROSS_LIST_SEARCH_ENABLED));
        boolean isExactMatchSearch = BooleanUtils.toBoolean(requestHelper.getParamAsString(SearchParameters.IS_EXACT_MATCH_CO_CODE_SEARCH));
        List<String> filterCOStates = requestHelper.getParamAsList(SearchParameters.FILTER_CO_STATES);
        String instructorId = requestHelper.getParamAsString(SearchParameters.INSTRUCTOR_ID);
        String departmentId = requestHelper.getParamAsString(SearchParameters.DEPARTMENT_ID);
        String description = requestHelper.getParamAsString(SearchParameters.DESCRIPTION);
        boolean includePassFailAndAuditRecords = BooleanUtils.toBoolean(requestHelper.getParamAsString(SearchParameters.INCLUDE_PASSFAIL_AUDIT_RESULTS));

        SearchResultInfo resultInfo = new SearchResultInfo();
        resultInfo.setStartAt(0);

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
                "    lui.formatted " +
                "FROM" +
                "    LuiIdentifierEntity ident, " +
                "    LuiEntity lui ";

        /*
         If the department id is supplied, join KSEN_LUI_UNITS_CONT_OWNER
         */
        if (StringUtils.isNotBlank(departmentId)){
            query = query +
                    "    JOIN lui.luiContentOwner dept ";
        }

        query = query +
                "    LEFT JOIN lui.luiUnitsDeployment unitsDeployment, " +
                "    IN(lui.resultValuesGroupKeys) lui_rvg1," +
                "    ResultValuesGroupEntity lrc_rvg1, " +
                "    IN(lui.resultValuesGroupKeys) lui_rvg2," +
                "    ResultValuesGroupEntity lrc_rvg2 ";

        /*
         For instructor search, include the Lpr table and join with lui ids
         */
        if (StringUtils.isNotBlank(instructorId)){
            query = query + ",  LprEntity lpr ";
        }

        query = query +
                "    WHERE" +
                "    lui.id = ident.lui.id" +
                "    AND lui.luiType = 'kuali.lui.type.course.offering'" +
                "    AND lui.atpId = '" + searchAtpId + "' " +
                "    AND lrc_rvg1.id = lui_rvg1" +
                "    AND lrc_rvg1.resultScaleId LIKE 'kuali.result.scale.credit.%' " +
                "    AND lrc_rvg2.id = lui_rvg2" +
                "    AND lrc_rvg2.resultScaleId LIKE 'kuali.result.scale.grade.%' ";

        if (!includePassFailAndAuditRecords){
            query = query +
                    //Exclude these two types that can cause duplicates.
                    // audit and passfail are moved into different fields, after that there can be only one grading option
                    // of Satisfactory, Letter, or Percentage
                    "    AND lrc_rvg2 NOT IN ('kuali.resultComponent.grade.audit','kuali.resultComponent.grade.passFail') ";
        }

        boolean excludeLuiCodeSearchQuery = false;

        if (StringUtils.isBlank(searchCourseCode) && StringUtils.isBlank(searchSubjectArea)) {
            excludeLuiCodeSearchQuery = true;
        }

        if (!excludeLuiCodeSearchQuery){
            query = query +
                    "    AND ident.lui.id IN (SELECT ident_subquery.lui.id FROM LuiIdentifierEntity ident_subquery WHERE ";

            String coCodeSearchString = "";
            if (StringUtils.isNotBlank(searchCourseCode)){
                if (isExactMatchSearch){
                    coCodeSearchString = " ident_subquery.code = '" + searchCourseCode + "' ";
                } else {
                    coCodeSearchString = " ident_subquery.code like '" + searchCourseCode + "%' ";
                }
            }

            if (StringUtils.isNotBlank(searchSubjectArea)){
                if (StringUtils.isBlank(searchCourseCode)){
                    query = query + " ident_subquery.division = '" + searchSubjectArea + "' ";
                } else {
                    query = query + " ident_subquery.division = '" + searchSubjectArea + "' AND " + coCodeSearchString;
                }
            } else {
                query = query + coCodeSearchString;
            }

            query = query + ") ";

        }

        /*
        Search for the lpr with main instructor type and active state.
         */
        if (StringUtils.isNotBlank(instructorId)){
            query = query + "   AND lpr.luiId = lui.id " +
                            "   AND lpr.personId = '" + instructorId +  "'" +
                            "   AND lpr.personRelationTypeId = '" + LprServiceConstants.INSTRUCTOR_MAIN_TYPE_KEY + "'" +
                            "   AND lpr.personRelationStateId = '" + LprServiceConstants.ACTIVE_STATE_KEY + "'";
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

        /**
         * Search for the course offerings first for a term and subjectarea/coursecode
         */
        List<Object[]> results = genericEntityDao.getEm().createQuery(query).getResultList();

        resultInfo.setTotalResults(results.size());
        resultInfo.setStartAt(0);

        Map<String,String> luiIds2AlternateCodes = new HashMap<String, String>();

        Map<String, SearchResultCellInfo> luiIds2OrgCells = new HashMap<String, SearchResultCellInfo>();
        Map<String, SearchResultRowInfo> luiIds2ResultRow = new HashMap<String, SearchResultRowInfo>(results.size());

        for (Object[] result : results) {
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

            row.addCell(SearchResultColumns.CREDIT_OPTION_NAME,(String)result[i++]);

            String gradingName = (String)result[i++];
            row.addCell(SearchResultColumns.GRADING_OPTION_NAME,gradingName);

            SearchResultCellInfo defaultPassFailFlag = row.addCell(SearchResultColumns.HAS_STUDENT_SELECTABLE_PASSFAIL, Boolean.FALSE.toString());
            SearchResultCellInfo defaultAuditFlag = row.addCell(SearchResultColumns.CAN_AUDIT_COURSE, Boolean.FALSE.toString());

            if (includeCurrentRow){
                if(luiIds2ResultRow.containsKey(courseOfferingId)){
                    SearchResultRowInfo resultRow = luiIds2ResultRow.get(courseOfferingId);

                    if (StringUtils.equals(graditOption,LrcServiceConstants.RESULT_GROUP_KEY_GRADE_PASSFAIL)){
                        resultRow.getCells().get(10).setValue(Boolean.TRUE.toString());
                    } else if (StringUtils.equals(graditOption,LrcServiceConstants.RESULT_GROUP_KEY_GRADE_AUDIT)){
                        resultRow.getCells().get(11).setValue((Boolean.TRUE.toString()));
                    } else {
                        resultRow.getCells().get(4).setValue(graditOption);
                    }

                    if(luiIds2OrgCells.containsKey(courseOfferingId)){
                        if(!isCrossListed){
                            //Only do this for the root lui to avoid duplication
                            SearchResultCellInfo orgCell = luiIds2OrgCells.get(courseOfferingId);
                            orgCell.setValue(orgCell.getValue()+","+deploymentOrg);
                            //Skip processing the rest of this record because multiple orgIDs are rolled up in the query
                        }
                    }
                    continue;
                } else {
                    if (StringUtils.equals(graditOption,LrcServiceConstants.RESULT_GROUP_KEY_GRADE_PASSFAIL)){
                        defaultPassFailFlag.setValue(Boolean.TRUE.toString());
                    } else if (StringUtils.equals(graditOption,LrcServiceConstants.RESULT_GROUP_KEY_GRADE_AUDIT)) {
                        defaultAuditFlag.setValue(Boolean.TRUE.toString());
                    }
                }
            }

            row.addCell(SearchResultColumns.DESC_FORMATTED,(String)result[i++]);

            //Rollup all the units deployment as a comma separated string.
            if(includeCurrentRow && luiIds2OrgCells.containsKey(courseOfferingId)){
                //Only do this for the root lui to avoid duplication
                SearchResultCellInfo orgCell = luiIds2OrgCells.get(courseOfferingId);
                orgCell.setValue(orgCell.getValue()+","+deploymentOrg);
                //Skip processing the rest of this record because multiple orgIDs are rolled up in the query
                continue;
            }

            /**
             * If the row matches with the user entered subject area/course course, add it to the result
             * list.
             */
            /*if (StringUtils.isNotBlank(searchSubjectArea)){
                if (StringUtils.equals(searchSubjectArea,division)){
                    resultInfo.getRows().add(row);
                    //Put the value into the search result row, and save it in the mapping
                    luiIds2OrgCells.put(courseOfferingId, row.addCell(SearchResultColumns.DEPLOYMENT_ORG_ID, deploymentOrg));
                    luiIds2ResultRow.put(courseOfferingId,row);
                }
            } else {
                if (StringUtils.startsWith(searchCourseCode,division)){
                    resultInfo.getRows().add(row);
                    //Put the value into the search result row, and save it in the mapping
                    luiIds2OrgCells.put(courseOfferingId, row.addCell(SearchResultColumns.DEPLOYMENT_ORG_ID, deploymentOrg));
                    luiIds2ResultRow.put(courseOfferingId,row);
                }
            }*/

            if (includeCurrentRow){
                resultInfo.getRows().add(row);
                //Put the value into the search result row, and save it in the mapping
                luiIds2OrgCells.put(courseOfferingId, row.addCell(SearchResultColumns.DEPLOYMENT_ORG_ID, deploymentOrg));
                luiIds2ResultRow.put(courseOfferingId,row);
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

        if (enableCrossListSearch){

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

        long end = System.currentTimeMillis();
        LOGGER.info("******TIME TAKEN TO SEARCH CO FOR (Subject Area=" + searchSubjectArea + ",Term=" + searchAtpId + ",Course=" + searchCourseCode + ")*************"+(end-start) + " ms");

        resultInfo.setStartAt(0);
        luiIds2AlternateCodes.clear();
        luiIds2OrgCells.clear();
        luiIds2ResultRow.clear();
        return resultInfo;
    }

    private boolean isConsiderSearchResult(String searchSubjectArea, String searchCourseCode, String division){
        if (StringUtils.isBlank(searchSubjectArea) && StringUtils.isBlank(searchCourseCode)){
            return true;
        } else if (StringUtils.equals(searchSubjectArea,division) || StringUtils.startsWith(searchCourseCode,division)){
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

    private static String commaString(List<String> items){
        StringBuilder sb = new StringBuilder();
        String delim = "";
        for (String str : items) {
            sb.append(delim).append("'" + str + "'");
            delim = ",";
        }
        return sb.toString();
    }

    public void setGenericEntityDao(GenericEntityDao genericEntityDao) {
        this.genericEntityDao = genericEntityDao;
    }


}
