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

package org.kuali.student.ap.search;

import org.apache.commons.lang.StringUtils;
import org.kuali.student.ap.framework.context.CourseSearchConstants;
import org.kuali.student.ap.framework.util.KsapHelperUtil;
import org.kuali.student.r2.common.class1.search.SearchServiceAbstractHardwiredImpl;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.common.util.date.DateFormatters;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;
import org.kuali.student.r2.core.search.util.SearchRequestHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class KsapCourseSearchCluSearchImpl extends SearchServiceAbstractHardwiredImpl {
    private static final Logger LOG = LoggerFactory.getLogger(KsapCourseSearchCluSearchImpl.class);



    // Search Types
    public static final TypeInfo KSAP_COURSE_SEARCH;
    public static final TypeInfo KSAP_COURSE_SEARCH_LU_BY_CLU_TITLE;
    public static final TypeInfo KSAP_COURSE_SEARCH_LU_BY_CLU_DESCRIPTION;
    public static final TypeInfo KSAP_COURSE_SEARCH_LU_BY_DIVISION_AND_CODE;
    public static final TypeInfo KSAP_COURSE_SEARCH_LU_BY_DIVISION_AND_LEVEL;
    public static final TypeInfo KSAP_COURSE_SEARCH_LU_BY_CODE;
    public static final TypeInfo KSAP_COURSE_SEARCH_LU_BY_LEVEL;
    public static final TypeInfo KSAP_COURSE_SEARCH_LU_BY_FULL_CODE;
    public static final TypeInfo KSAP_COURSE_SEARCH_LU_BY_DIVISION;
    public static final TypeInfo KSAP_COURSE_SEARCH_COURSE_INFO_BY_ID;
    public static final TypeInfo KSAP_COURSE_SEARCH_COURSE_IND_VERSION_BY_CLU_ID;

    public static final String DEFAULT_EFFECTIVE_DATE = "01/01/2012";

    static {
        // Create default search type
        TypeInfo info = new TypeInfo();
        info.setKey(CourseSearchConstants.KSAP_COURSE_SEARCH_KEY);
        info.setName("KSAP Course Search");
        info.setDescr(new RichTextHelper().fromPlain("Return search results from KSAP course search"));

        try {
            info.setEffectiveDate(DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.parse("01/01/2012"));
        } catch ( IllegalArgumentException ex) {
            throw new RuntimeException("failed to set effective date to: 01/01/2012", ex);
        }
        KSAP_COURSE_SEARCH = info;

        info = new TypeInfo();
        info.setKey(CourseSearchConstants.KSAP_COURSE_SEARCH_LU_BY_CLU_TITLE_KEY);
        info.setName("CLU search by Clu title ");
        info.setDescr(new RichTextHelper().fromPlain(
                "Search for Course ids by matching text in the course title"));
        info.setEffectiveDate(DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.parse(DEFAULT_EFFECTIVE_DATE));
        KSAP_COURSE_SEARCH_LU_BY_CLU_TITLE = info;

        info = new TypeInfo();
        info.setKey(CourseSearchConstants.KSAP_COURSE_SEARCH_LU_BY_CLU_DESCRIPTION_KEY);
        info.setName("CLU search by Clu description ");
        info.setDescr(new RichTextHelper().fromPlain(
                "Search for Course ids by matching text in the course description"));
        info.setEffectiveDate(DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.parse(DEFAULT_EFFECTIVE_DATE));
        KSAP_COURSE_SEARCH_LU_BY_CLU_DESCRIPTION = info;

        info = new TypeInfo();
        info.setKey(CourseSearchConstants.KSAP_COURSE_SEARCH_LU_BY_DIVISION_AND_CODE_KEY);
        info.setName("CLU search by Clu division and suffix code ");
        info.setDescr(new RichTextHelper().fromPlain(
                "Search for Course ids by matching the course division and suffix code"));
        info.setEffectiveDate(DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.parse(DEFAULT_EFFECTIVE_DATE));
        KSAP_COURSE_SEARCH_LU_BY_DIVISION_AND_CODE = info;

        info = new TypeInfo();
        info.setKey(CourseSearchConstants.KSAP_COURSE_SEARCH_LU_BY_DIVISION_AND_LEVEL_KEY);
        info.setName("CLU search by Clu division and level ");
        info.setDescr(new RichTextHelper().fromPlain(
                "Search for Course ids by matching the course division and level"));
        info.setEffectiveDate(DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.parse(DEFAULT_EFFECTIVE_DATE));
        KSAP_COURSE_SEARCH_LU_BY_DIVISION_AND_LEVEL = info;

        info = new TypeInfo();
        info.setKey(CourseSearchConstants.KSAP_COURSE_SEARCH_LU_BY_CODE_KEY);
        info.setName("CLU search by Clu code ");
        info.setDescr(new RichTextHelper().fromPlain(
                "Search for Course ids by matching the course code"));
        info.setEffectiveDate(DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.parse(DEFAULT_EFFECTIVE_DATE));
        KSAP_COURSE_SEARCH_LU_BY_CODE = info;

        info = new TypeInfo();
        info.setKey(CourseSearchConstants.KSAP_COURSE_SEARCH_LU_BY_LEVEL_KEY);
        info.setName("CLU search by Clu level");
        info.setDescr(new RichTextHelper().fromPlain(
                "Search for Course ids by matching the course level"));
        info.setEffectiveDate(DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.parse(DEFAULT_EFFECTIVE_DATE));
        KSAP_COURSE_SEARCH_LU_BY_LEVEL = info;

        info = new TypeInfo();
        info.setKey(CourseSearchConstants.KSAP_COURSE_SEARCH_LU_BY_FULL_CODE_KEY);
        info.setName("CLU search by Clu code");
        info.setDescr(new RichTextHelper().fromPlain(
                "Search for Course ids by matching the full course code"));
        info.setEffectiveDate(DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.parse(DEFAULT_EFFECTIVE_DATE));
        KSAP_COURSE_SEARCH_LU_BY_FULL_CODE = info;

        info = new TypeInfo();
        info.setKey(CourseSearchConstants.KSAP_COURSE_SEARCH_LU_BY_DIVISION_KEY);
        info.setName("CLU search by Clu division");
        info.setDescr(new RichTextHelper().fromPlain(
                "Search for Course ids by matching the course division"));
        info.setEffectiveDate(DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.parse(DEFAULT_EFFECTIVE_DATE));
        KSAP_COURSE_SEARCH_LU_BY_DIVISION = info;

        info = new TypeInfo();
        info.setKey(CourseSearchConstants.KSAP_COURSE_SEARCH_COURSE_INFO_BY_ID_KEY);
        info.setName("Retrieve Information on Specific CLU");
        info.setDescr(new RichTextHelper().fromPlain(
                "Retrieve specific course information using the courses id"));
        info.setEffectiveDate(DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.parse(DEFAULT_EFFECTIVE_DATE));
        KSAP_COURSE_SEARCH_COURSE_INFO_BY_ID = info;

        info = new TypeInfo();
        info.setKey(CourseSearchConstants.KSAP_COURSE_SEARCH_COURSE_IND_VERSION_BY_CLU_ID_KEY);
        info.setName("Retrieve Independent Version Id for clu");
        info.setDescr(new RichTextHelper().fromPlain(
                "Retrieve specific independent version id using the courses id"));
        info.setEffectiveDate(DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.parse(DEFAULT_EFFECTIVE_DATE));
        KSAP_COURSE_SEARCH_COURSE_IND_VERSION_BY_CLU_ID = info;
    }

    /**
     * Get the search type that the sub class implements.
     */
    @Override
    public TypeInfo getSearchType() {
        return KSAP_COURSE_SEARCH;
    }

    /**
     * @see org.kuali.student.r2.core.search.service.SearchService#getSearchType(String, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public TypeInfo getSearchType(String searchTypeKey, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException {

        if (CourseSearchConstants.KSAP_COURSE_SEARCH_LU_BY_CLU_DESCRIPTION_KEY.equals(searchTypeKey)) {
            return KSAP_COURSE_SEARCH_LU_BY_CLU_DESCRIPTION;
        }else if (CourseSearchConstants.KSAP_COURSE_SEARCH_LU_BY_CLU_TITLE_KEY.equals(searchTypeKey)) {
            return KSAP_COURSE_SEARCH_LU_BY_CLU_TITLE;
        }else if (CourseSearchConstants.KSAP_COURSE_SEARCH_LU_BY_DIVISION_KEY.equals(searchTypeKey)) {
            return KSAP_COURSE_SEARCH_LU_BY_DIVISION;
        }else if (CourseSearchConstants.KSAP_COURSE_SEARCH_LU_BY_DIVISION_AND_CODE_KEY.equals(searchTypeKey)) {
            return KSAP_COURSE_SEARCH_LU_BY_DIVISION_AND_CODE;
        }else if (CourseSearchConstants.KSAP_COURSE_SEARCH_LU_BY_DIVISION_AND_LEVEL_KEY.equals(searchTypeKey)) {
            return KSAP_COURSE_SEARCH_LU_BY_DIVISION_AND_LEVEL;
        }else if (CourseSearchConstants.KSAP_COURSE_SEARCH_LU_BY_CODE_KEY.equals(searchTypeKey)) {
            return KSAP_COURSE_SEARCH_LU_BY_CODE;
        }else if (CourseSearchConstants.KSAP_COURSE_SEARCH_LU_BY_LEVEL_KEY.equals(searchTypeKey)) {
            return KSAP_COURSE_SEARCH_LU_BY_LEVEL;
        }else if (CourseSearchConstants.KSAP_COURSE_SEARCH_LU_BY_FULL_CODE_KEY.equals(searchTypeKey)) {
            return KSAP_COURSE_SEARCH_LU_BY_FULL_CODE;
        }else if (CourseSearchConstants.KSAP_COURSE_SEARCH_COURSE_INFO_BY_ID_KEY.equals(searchTypeKey)) {
            return KSAP_COURSE_SEARCH_COURSE_INFO_BY_ID;
        }else if (CourseSearchConstants.KSAP_COURSE_SEARCH_COURSE_IND_VERSION_BY_CLU_ID_KEY.equals(searchTypeKey)) {
            return KSAP_COURSE_SEARCH_COURSE_IND_VERSION_BY_CLU_ID;
        }

        // If no matching search type is found throw exception
        throw new DoesNotExistException("No Search Type Found for key:"+searchTypeKey);
    }

    /**
     * @see org.kuali.student.r2.core.search.service.SearchService#search(org.kuali.student.r2.core.search.dto.SearchRequestInfo, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public SearchResultInfo search(SearchRequestInfo searchRequestInfo, ContextInfo contextInfo) throws MissingParameterException, OperationFailedException, PermissionDeniedException {
        SearchResultInfo resultInfo;

        if (StringUtils.equals(searchRequestInfo.getSearchKey(), KSAP_COURSE_SEARCH_LU_BY_CLU_TITLE.getKey())) {
            resultInfo =  searchForLuByCluTitle(searchRequestInfo, contextInfo);
        }else if (StringUtils.equals(searchRequestInfo.getSearchKey(), KSAP_COURSE_SEARCH_LU_BY_CLU_DESCRIPTION.getKey())) {
            resultInfo =  searchForLuByCluDescription(searchRequestInfo, contextInfo);
        }else if (StringUtils.equals(searchRequestInfo.getSearchKey(), KSAP_COURSE_SEARCH_LU_BY_DIVISION_AND_CODE.getKey())) {
            resultInfo =  searchForLuByDivisionAndCode(searchRequestInfo, contextInfo);
        }else if (StringUtils.equals(searchRequestInfo.getSearchKey(), KSAP_COURSE_SEARCH_LU_BY_DIVISION_AND_LEVEL.getKey())) {
            resultInfo =  searchForLuByDivisionAndLevel(searchRequestInfo, contextInfo);
        }else if (StringUtils.equals(searchRequestInfo.getSearchKey(), KSAP_COURSE_SEARCH_LU_BY_CODE.getKey())) {
            resultInfo =  searchForLuByCode(searchRequestInfo, contextInfo);
        }else if (StringUtils.equals(searchRequestInfo.getSearchKey(), KSAP_COURSE_SEARCH_LU_BY_LEVEL.getKey())) {
            resultInfo =  searchForLuByLevel(searchRequestInfo, contextInfo);
        }else if (StringUtils.equals(searchRequestInfo.getSearchKey(), KSAP_COURSE_SEARCH_LU_BY_FULL_CODE.getKey())) {
            resultInfo =  searchForLuByFullCode(searchRequestInfo, contextInfo);
        }else if (StringUtils.equals(searchRequestInfo.getSearchKey(), KSAP_COURSE_SEARCH_LU_BY_DIVISION.getKey())) {
            resultInfo =  searchForLuByDivision(searchRequestInfo, contextInfo);
        }else if (StringUtils.equals(searchRequestInfo.getSearchKey(), KSAP_COURSE_SEARCH_COURSE_INFO_BY_ID.getKey())) {
            resultInfo =  searchForCourseInfoById(searchRequestInfo, contextInfo);
        }else if (StringUtils.equals(searchRequestInfo.getSearchKey(), KSAP_COURSE_SEARCH_COURSE_IND_VERSION_BY_CLU_ID.getKey())) {
            resultInfo =  searchForIndVersionByClu(searchRequestInfo, contextInfo);
        }else {
            // If no matching search is found throw exception
            throw new OperationFailedException("Unsupported search type: " + searchRequestInfo.getSearchKey());
        }

        return resultInfo;
    }

    /**
     * Routed To from search method based on search type key pasted in the search request.
     * Used to create and execute for search type key KSAP_COURSE_SEARCH_COURSE_INFO_BY_ID.
     *
     * @see #search(org.kuali.student.r2.core.search.dto.SearchRequestInfo, org.kuali.student.r2.common.dto.ContextInfo)
     */
    protected SearchResultInfo searchForCourseInfoById(SearchRequestInfo searchRequestInfo, ContextInfo contextInfo)
            throws MissingParameterException, OperationFailedException {

        SearchRequestHelper requestHelper = new SearchRequestHelper(searchRequestInfo);
        SearchResultInfo resultInfo = new SearchResultInfo();

        List<String> versionIdList = requestHelper.getParamAsList(CourseSearchConstants.SearchParameters.VERSION_IND_ID_LIST);

        String queryStr =
                "SELECT  clu.ID, ident.LNG_NAME, trim( ident.SUFX_CD ), trim( ident.DIVISION ), trim( ident.LVL )," +
                "ident.cd, clu.ver_ind_id" +
                        "    FROM" +
                        "    KSLU_CLU clu," +
                        "    KSLU_CLU_IDENT ident" +
                        "    WHERE clu.VER_IND_ID IN :"+CourseSearchConstants.SearchParameters.VERSION_IND_ID_LIST +
                        "    AND clu.offic_clu_id = ident.id" +
                        "    AND clu.EFF_DT = " +
                        "    (select min(c.EFF_DT) from KSLU_CLU c WHERE c.VER_IND_ID = clu.VER_IND_ID " +
                        "    AND c.ID NOT IN (SELECT att.OWNER FROM KSLU_CLU_ATTR att where att.ATTR_NAME='course.catalogOmit_ind' and att.ATTR_VALUE='Y')" +
                        "    AND (c.ST = 'Active' OR c.ST = 'Superseded' OR c.ST = 'Retired')" +
                        "    AND (c.EXPIR_DT >= :endDate OR c.EXPIR_DT IS NULL)" +
                        "    AND c.LUTYPE_ID='kuali.lu.type.CreditCourse'" +
                        ")" +
                        "    AND clu.ID NOT IN (SELECT att.OWNER FROM KSLU_CLU_ATTR att where att.ATTR_NAME='course.catalogOmit_ind' and att.ATTR_VALUE='Y')" +
                        "    AND (clu.ST = 'Active' OR clu.ST = 'Superseded' OR clu.ST = 'Retired')" +
                        "    AND (clu.EXPIR_DT >= :endDate OR clu.EXPIR_DT IS NULL)" +
                        "    AND clu.LUTYPE_ID='kuali.lu.type.CreditCourse'";

        // Set params and execute search
        Query query = getEntityManager().createNativeQuery(queryStr);
        query.setParameter(CourseSearchConstants.SearchParameters.VERSION_IND_ID_LIST, versionIdList);
        query.setParameter(CourseSearchConstants.SearchParameters.END_DATE, contextInfo.getCurrentDate());
        List<Object[]> results = query.getResultList();

        // Compile results
        for(Object resultRow[] : results){
            SearchResultRowInfo row = new SearchResultRowInfo();
            row.addCell(CourseSearchConstants.SearchResultColumns.CLU_ID, (String)resultRow[0]);
            row.addCell(CourseSearchConstants.SearchResultColumns.COURSE_NAME, (String)resultRow[1]);
            row.addCell(CourseSearchConstants.SearchResultColumns.COURSE_NUMBER, (String)resultRow[2]);
            row.addCell(CourseSearchConstants.SearchResultColumns.COURSE_SUBJECT, (String)resultRow[3]);
            row.addCell(CourseSearchConstants.SearchResultColumns.COURSE_LEVEL, (String)resultRow[4]);
            row.addCell(CourseSearchConstants.SearchResultColumns.COURSE_CODE, (String)resultRow[5]);
            row.addCell(CourseSearchConstants.SearchResultColumns.COURSE_VERSION_INDEPENDENT_ID, (String)resultRow[6]);
            resultInfo.getRows().add(row);
        }

        return resultInfo;
    }

    /**
     * Routed To from search method based on search type key pasted in the search request.
     * Used to create and execute for search type key KSAP_COURSE_SEARCH_LU_BY_DIVISION.
     *
     * @see #search(org.kuali.student.r2.core.search.dto.SearchRequestInfo, org.kuali.student.r2.common.dto.ContextInfo)
     */
    protected SearchResultInfo searchForLuByDivision(SearchRequestInfo searchRequestInfo, ContextInfo contextInfo)
            throws MissingParameterException, OperationFailedException {

        SearchRequestHelper requestHelper = new SearchRequestHelper(searchRequestInfo);


        String division = requestHelper.getParamAsString(CourseSearchConstants.SearchParameters
                .DIVISION);

        String queryStr =
                "SELECT  clu.VER_IND_ID"+
                "    FROM" +
                "    KSLU_CLU clu," +
                "    KSLU_CLU_IDENT ident" +
                "    WHERE ident.DIVISION = :"+CourseSearchConstants.SearchParameters.DIVISION +
                "    AND ident.ID = clu.OFFIC_CLU_ID" +
                     getCourseSearchRestrictions() +
                "    ORDER BY ident.CD";

        // Set params and execute search
        Query query = getEntityManager().createNativeQuery(queryStr);
        query.setParameter(CourseSearchConstants.SearchParameters.DIVISION, division);
        query.setParameter(CourseSearchConstants.SearchParameters.END_DATE, contextInfo.getCurrentDate());
        List<Object> results = query.getResultList();

        // Compile results
        SearchResultInfo resultInfo = compileSearchResults(results);

        return resultInfo;
    }

    /**
     * Routed To from search method based on search type key pasted in the search request.
     * Used to create and execute for search type key KSAP_COURSE_SEARCH_LU_BY_FULL_CODE.
     *
     * @see #search(org.kuali.student.r2.core.search.dto.SearchRequestInfo, org.kuali.student.r2.common.dto.ContextInfo)
     */
    protected SearchResultInfo searchForLuByFullCode(SearchRequestInfo searchRequestInfo, ContextInfo contextInfo)
            throws MissingParameterException, OperationFailedException {

        SearchRequestHelper requestHelper = new SearchRequestHelper(searchRequestInfo);

        String code = requestHelper.getParamAsString(CourseSearchConstants.SearchParameters
                .CODE);

        String queryStr =
                "SELECT  clu.VER_IND_ID"+
                        "    FROM" +
                        "    KSLU_CLU clu," +
                        "    KSLU_CLU_IDENT ident" +
                        "    WHERE ident.CD LIKE :"+CourseSearchConstants.SearchParameters.CODE +"||'%'"+
                        "    AND ident.ID = clu.OFFIC_CLU_ID" +
                             getCourseSearchRestrictions() +
                        "    ORDER BY ident.CD";

        // Set params and execute search
        Query query = getEntityManager().createNativeQuery(queryStr);
        query.setParameter(CourseSearchConstants.SearchParameters.CODE, code);
        query.setParameter(CourseSearchConstants.SearchParameters.END_DATE, contextInfo.getCurrentDate());

        List<Object> results = query.getResultList();
        // Compile results
        SearchResultInfo resultInfo = compileSearchResults(results);

        return resultInfo;
    }

    /**
     * Routed To from search method based on search type key pasted in the search request.
     * Used to create and execute for search type key KSAP_COURSE_SEARCH_LU_BY_LEVEL.
     *
     * @see #search(org.kuali.student.r2.core.search.dto.SearchRequestInfo, org.kuali.student.r2.common.dto.ContextInfo)
     */
    protected SearchResultInfo searchForLuByLevel(SearchRequestInfo searchRequestInfo, ContextInfo contextInfo)
            throws MissingParameterException, OperationFailedException {

        SearchRequestHelper requestHelper = new SearchRequestHelper(searchRequestInfo);

        String level = requestHelper.getParamAsString(CourseSearchConstants.SearchParameters
                .LEVEL);

        String queryStr =
                "SELECT  clu.VER_IND_ID"+
                        "    FROM" +
                        "    KSLU_CLU clu," +
                        "    KSLU_CLU_IDENT ident" +
                        "    WHERE ident.LVL = :"+CourseSearchConstants.SearchParameters.LEVEL +
                        "    AND ident.ID = clu.OFFIC_CLU_ID" +
                        getCourseSearchRestrictions() +
                        "    ORDER BY ident.CD";

        // Set params and execute search
        Query query = getEntityManager().createNativeQuery(queryStr);
        query.setParameter(CourseSearchConstants.SearchParameters.LEVEL, level);
        query.setParameter(CourseSearchConstants.SearchParameters.END_DATE, contextInfo.getCurrentDate());

        List<Object> results = query.getResultList();

        // Compile results
        SearchResultInfo resultInfo = compileSearchResults(results);

        return resultInfo;
    }

    /**
     * Routed To from search method based on search type key pasted in the search request.
     * Used to create and execute for search type key KSAP_COURSE_SEARCH_LU_BY_CODE.
     *
     * @see #search(org.kuali.student.r2.core.search.dto.SearchRequestInfo, org.kuali.student.r2.common.dto.ContextInfo)
     */
    protected SearchResultInfo searchForLuByCode(SearchRequestInfo searchRequestInfo, ContextInfo contextInfo)
            throws MissingParameterException, OperationFailedException {

        SearchRequestHelper requestHelper = new SearchRequestHelper(searchRequestInfo);

        String code = requestHelper.getParamAsString(CourseSearchConstants.SearchParameters
                .CODE);

        String queryStr =
                "SELECT  clu.VER_IND_ID"+
                        "    FROM" +
                        "    KSLU_CLU clu," +
                        "    KSLU_CLU_IDENT ident" +
                        "    WHERE ident.SUFX_CD = :"+CourseSearchConstants.SearchParameters.CODE +
                        "    AND ident.ID = clu.OFFIC_CLU_ID" +
                             getCourseSearchRestrictions() +
                        "    ORDER BY ident.CD";

        // Set params and execute search
        Query query = getEntityManager().createNativeQuery(queryStr);
        query.setParameter(CourseSearchConstants.SearchParameters.CODE, code);
        query.setParameter(CourseSearchConstants.SearchParameters.END_DATE, contextInfo.getCurrentDate());

        List<Object> results = query.getResultList();

        // Compile results
        SearchResultInfo resultInfo = compileSearchResults(results);
        return resultInfo;
    }

    /**
     * Routed To from search method based on search type key pasted in the search request.
     * Used to create and execute for search type key KSAP_COURSE_SEARCH_LU_BY_DIVISION_AND_LEVEL.
     *
     * @see #search(org.kuali.student.r2.core.search.dto.SearchRequestInfo, org.kuali.student.r2.common.dto.ContextInfo)
     */
    protected SearchResultInfo searchForLuByDivisionAndLevel(SearchRequestInfo searchRequestInfo, ContextInfo contextInfo)
            throws MissingParameterException, OperationFailedException {

        SearchRequestHelper requestHelper = new SearchRequestHelper(searchRequestInfo);

        String level = requestHelper.getParamAsString(CourseSearchConstants.SearchParameters
                .LEVEL);
        String division = requestHelper.getParamAsString(CourseSearchConstants.SearchParameters
                .DIVISION);

        String queryStr =
                "SELECT  clu.VER_IND_ID"+
                        "    FROM" +
                        "    KSLU_CLU clu," +
                        "    KSLU_CLU_IDENT ident" +
                        "    WHERE ident.LVL = :"+CourseSearchConstants.SearchParameters.LEVEL +
                        "    AND ident.DIVISION = :"+CourseSearchConstants.SearchParameters.DIVISION +
                        "    AND ident.ID = clu.OFFIC_CLU_ID" +
                        getCourseSearchRestrictions() +
                        "    ORDER BY ident.CD";

        // Set params and execute search
        Query query = getEntityManager().createNativeQuery(queryStr);
        query.setParameter(CourseSearchConstants.SearchParameters.LEVEL, level);
        query.setParameter(CourseSearchConstants.SearchParameters.DIVISION, division);
        query.setParameter(CourseSearchConstants.SearchParameters.END_DATE, contextInfo.getCurrentDate());

        List<Object> results = query.getResultList();

        // Compile results
        SearchResultInfo resultInfo = compileSearchResults(results);

        return resultInfo;
    }

    /**
     * Routed To from search method based on search type key pasted in the search request.
     * Used to create and execute for search type key KSAP_COURSE_SEARCH_LU_BY_DIVISION_AND_CODE.
     *
     * @see #search(org.kuali.student.r2.core.search.dto.SearchRequestInfo, org.kuali.student.r2.common.dto.ContextInfo)
     */
    protected SearchResultInfo searchForLuByDivisionAndCode(SearchRequestInfo searchRequestInfo, ContextInfo contextInfo)
            throws MissingParameterException, OperationFailedException {

        SearchRequestHelper requestHelper = new SearchRequestHelper(searchRequestInfo);


        String code = requestHelper.getParamAsString(CourseSearchConstants.SearchParameters
                .CODE);
        String division = requestHelper.getParamAsString(CourseSearchConstants.SearchParameters
                .DIVISION);

        String queryStr =
                "SELECT  clu.VER_IND_ID"+
                        "    FROM" +
                        "    KSLU_CLU clu," +
                        "    KSLU_CLU_IDENT ident" +
                        "    WHERE ident.SUFX_CD = :"+CourseSearchConstants.SearchParameters.CODE +
                        "    AND ident.DIVISION = :"+CourseSearchConstants.SearchParameters.DIVISION +
                        "    AND ident.ID = clu.OFFIC_CLU_ID" +
                        getCourseSearchRestrictions() +
                        "    ORDER BY ident.CD";

        // Set params and execute search
        Query query = getEntityManager().createNativeQuery(queryStr);
        query.setParameter(CourseSearchConstants.SearchParameters.CODE, code);
        query.setParameter(CourseSearchConstants.SearchParameters.DIVISION, division);
        query.setParameter(CourseSearchConstants.SearchParameters.END_DATE, contextInfo.getCurrentDate());

        List<Object> results = query.getResultList();

        // Compile results
        SearchResultInfo resultInfo = compileSearchResults(results);

        return resultInfo;
    }

    /**
     * Routed To from search method based on search type key pasted in the search request.
     * Used to create and execute for search type key KSAP_COURSE_SEARCH_LU_BY_CLU_DESCRIPTION.
     *
     * @see #search(org.kuali.student.r2.core.search.dto.SearchRequestInfo, org.kuali.student.r2.common.dto.ContextInfo)
     */
    protected SearchResultInfo searchForLuByCluDescription(SearchRequestInfo searchRequestInfo, ContextInfo contextInfo)
            throws MissingParameterException, OperationFailedException {

        SearchRequestHelper requestHelper = new SearchRequestHelper(searchRequestInfo);

        String queryText = requestHelper.getParamAsString(CourseSearchConstants.SearchParameters
                .QUERYTEXT);

        String queryStr =
                "SELECT  clu.VER_IND_ID"+
                        "    FROM" +
                        "    KSLU_CLU clu," +
                        "    KSLU_RICH_TEXT_T cluDescr," +
                        "    KSLU_CLU_IDENT ident" +
                        "    WHERE "+
                        "    (UPPER( ' ' || cluDescr.PLAIN || ' ' ) LIKE '%'||:queryText||'%')" +
                        "    AND ident.ID = clu.OFFIC_CLU_ID" +
                        "    AND cluDescr.ID = clu.RT_DESCR_ID" +
                        getCourseSearchRestrictions() +
                        "    ORDER BY ident.CD";

        // Set params and execute search
        Query query = getEntityManager().createNativeQuery(queryStr);
        query.setParameter(CourseSearchConstants.SearchParameters.QUERYTEXT, queryText);
        query.setParameter(CourseSearchConstants.SearchParameters.END_DATE, contextInfo.getCurrentDate());

        List<Object> results = query.getResultList();

        // Compile results
        SearchResultInfo resultInfo = compileSearchResults(results);

        return resultInfo;
    }

    /**
     * Routed To from search method based on search type key pasted in the search request.
     * Used to create and execute for search type key KSAP_COURSE_SEARCH_LU_BY_CLU_TITLE.
     *
     * @see #search(org.kuali.student.r2.core.search.dto.SearchRequestInfo, org.kuali.student.r2.common.dto.ContextInfo)
     */
    protected SearchResultInfo searchForLuByCluTitle(SearchRequestInfo searchRequestInfo, ContextInfo contextInfo)
            throws MissingParameterException, OperationFailedException {

        SearchRequestHelper requestHelper = new SearchRequestHelper(searchRequestInfo);

        String queryText = requestHelper.getParamAsString(CourseSearchConstants.SearchParameters
                .QUERYTEXT);

        String queryStr =
                "SELECT  clu.VER_IND_ID"+
                        "    FROM" +
                        "    KSLU_CLU clu," +
                        "    KSLU_CLU_IDENT ident" +
                        "    WHERE "+
                        "    (UPPER( ' ' || ident.LNG_NAME || ' ' ) LIKE '%'||:queryText||'%')" +
                        "    AND ident.ID = clu.OFFIC_CLU_ID" +
                        getCourseSearchRestrictions() +
                        "    ORDER BY ident.CD";

        // Set params and execute search
        Query query = getEntityManager().createNativeQuery(queryStr);
        query.setParameter(CourseSearchConstants.SearchParameters.QUERYTEXT, queryText);
        query.setParameter(CourseSearchConstants.SearchParameters.END_DATE, contextInfo.getCurrentDate());

        List<Object> results = query.getResultList();

        // Compile results
        SearchResultInfo resultInfo = compileSearchResults(results);

        return resultInfo;
    }

    /**
     * Routed To from search method based on search type key pasted in the search request.
     * Used to create and execute for search type key KSAP_COURSE_SEARCH_COURSE_IND_VERSION_BY_CLU_ID_KEY.
     *
     * @see #search(org.kuali.student.r2.core.search.dto.SearchRequestInfo, org.kuali.student.r2.common.dto.ContextInfo)
     */
    protected SearchResultInfo searchForIndVersionByClu(SearchRequestInfo searchRequestInfo, ContextInfo contextInfo)
            throws MissingParameterException, OperationFailedException {
        SearchRequestHelper requestHelper = new SearchRequestHelper(searchRequestInfo);

        String cluId = requestHelper.getParamAsString(CourseSearchConstants.SearchParameters.CLU_ID);

        SearchResultInfo resultInfo = new SearchResultInfo();

        // Create sql string
        String queryStr = "SELECT clu.VER_IND_ID FROM KSLU_CLU clu WHERE clu.ID =:"+ CourseSearchConstants
            .SearchParameters.CLU_ID;

        // Set params and execute search
        Query query = getEntityManager().createNativeQuery(queryStr);
        query.setParameter(CourseSearchConstants.SearchParameters.CLU_ID, cluId);
        Object result = query.getSingleResult();

        // Compile Results
        if(result!=null){
            SearchResultRowInfo row = new SearchResultRowInfo();
            row.addCell(CourseSearchConstants.SearchResultColumns.COURSE_VERSION_INDEPENDENT_ID, (String)result);
            resultInfo.getRows().add(row);
        }

        return resultInfo;
    }

    /**
     *
     *
     * @return Segemnt of sql string for general course search restrictions
     */
    private String getCourseSearchRestrictions(){
        String restrictions =
                "    AND clu.ID NOT IN (SELECT att.OWNER FROM KSLU_CLU_ATTR att where att.ATTR_NAME='course.catalogOmit_ind' and att.ATTR_VALUE='Y')" +
                "    AND (clu.ST = 'Active' OR clu.ST = 'Superseded' OR clu.ST = 'Retired')" +
                "    AND (clu.EXPIR_DT >= :endDate OR clu.EXPIR_DT IS NULL)" +
                "    AND clu.LUTYPE_ID='kuali.lu.type.CreditCourse'";

        return restrictions;
    }

    /**
     * Compiles a list of results from a search into a single result info
     *
     * @param results - A list of search results to compile
     * @return Compiled Search Results
     */
    private SearchResultInfo compileSearchResults(List<Object> results){
        SearchResultInfo resultInfo = new SearchResultInfo();
        List<String> resultList = new ArrayList<String>();
        for(Object resultRow : results){
            SearchResultRowInfo row = new SearchResultRowInfo();
            if(!resultList.contains((String)resultRow)){
                row.addCell(CourseSearchConstants.SearchResultColumns.COURSE_VERSION_INDEPENDENT_ID, (String)resultRow);
                resultInfo.getRows().add(row);
                resultList.add((String)resultRow);
            }
        }
        return resultInfo;
    }
}
