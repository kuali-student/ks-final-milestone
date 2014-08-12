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
import org.kuali.student.r2.common.class1.search.SearchServiceAbstractHardwiredImpl;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.common.util.date.DateFormatters;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;
import org.kuali.student.r2.core.search.util.SearchRequestHelper;
import org.kuali.student.r2.lum.util.constants.CluServiceConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Query;
import java.util.List;

public class KsapSearchSupportImpl extends SearchServiceAbstractHardwiredImpl {
    private static final Logger LOG = LoggerFactory.getLogger(KsapSearchSupportImpl.class);

    /**
     * NOTE!!:  Only rather static type of queries (e.g. KSAP_COURSE_SEARCH_COURSEIDS_BY_TERM_SCHEDULE) should be
     * added to this search impl.  Other more dynamic queries (whose parameters vary greatly/frequently)
     * should be added to other existing (e.g. KsapCourseSearchImpl) or new classes,
     * that have other more dynamic like queries, so that very different caching strategies can be
     * used for the dynamic queries (... vs more static queries).
     */


    // Search Types
    public static final TypeInfo KSAP_SEARCH_SUPPORT;
    public static final TypeInfo KSAP_SEARCH_COURSEIDS_BY_TERM_SCHEDULED;
    public static final TypeInfo KSAP_SEARCH_COURSEIDS_BY_TERM_OFFERED;
    public static final TypeInfo KSAP_SEARCH_GENERAL_EDUCATION_VALUES;
    public static final TypeInfo KSAP_SEARCH_COURSEIDS_BY_GENERAL_EDUCATION;
    public static final TypeInfo KSAP_SEARCH_ALL_DIVISION_CODES;
    public static final TypeInfo KSAP_SEARCH_COURSEID_TITLE_AND_STATUS_BY_SUBJ_CD;
    public static final TypeInfo KSAP_SEARCH_COURSEIDS_BY_VERSION_IND_ID;

    public static final String DEFAULT_EFFECTIVE_DATE = "01/01/2012";

    static {
        // Create default search type
        TypeInfo info = new TypeInfo();
        info.setKey(CourseSearchConstants.KSAP_COURSE_SEARCH_KEY);
        info.setName("KSAP Course Search Support");
        info.setDescr(new RichTextHelper().fromPlain("Return search results from KSAP course search support"));

        try {
            info.setEffectiveDate(DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.parse("01/01/2012"));
        } catch ( IllegalArgumentException ex) {
            throw new RuntimeException("failed to set effective date to 01/01/2012", ex);
        }
        KSAP_SEARCH_SUPPORT = info;

        // Create search that retrieves a list of clu ids based on whether the clu entry has a CO offered in a term
        info = new TypeInfo();
        info.setKey(CourseSearchConstants.KSAP_COURSE_SEARCH_COURSEVERSIONIDS_BY_TERM_SCHEDULED_KEY);
        info.setName("Course Id Search By COs Scheduled In Term");
        info.setDescr(new RichTextHelper().fromPlain("Search for course ids based on if COs for course are offered in a term"));
        info.setEffectiveDate(DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.parse(DEFAULT_EFFECTIVE_DATE));
        KSAP_SEARCH_COURSEIDS_BY_TERM_SCHEDULED = info;

        // Creates search that retrieves a list of clu ids based on whether the clu entry has a relation to a term key
        info = new TypeInfo();
        info.setKey(CourseSearchConstants.KSAP_COURSE_SEARCH_COURSEIDS_BY_TERM_OFFERED_KEY);
        info.setName("Course Id Search By Terms Offered");
        info.setDescr(new RichTextHelper().fromPlain("Search for course ids based on if it is set to be offered in a type of term"));
        info.setEffectiveDate(DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.parse(DEFAULT_EFFECTIVE_DATE));
        KSAP_SEARCH_COURSEIDS_BY_TERM_OFFERED = info;

        // Creates search that retrieves a list of clu ids based on whether the clu entry has a relation to a term key
        info = new TypeInfo();
        info.setKey(CourseSearchConstants.KSAP_COURSE_SEARCH_GENERAL_EDUCATION_VALUES_KEY);
        info.setName("General Education Values");
        info.setDescr(new RichTextHelper().fromPlain("Search for possible values for General Education"));
        info.setEffectiveDate(DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.parse(DEFAULT_EFFECTIVE_DATE));
        KSAP_SEARCH_GENERAL_EDUCATION_VALUES = info;

        // Creates search that retrieves a list of clu ids based on whether the clu entry has a relation to a term key
        info = new TypeInfo();
        info.setKey(CourseSearchConstants.KSAP_COURSE_SEARCH_COURSEIDS_BY_GENERAL_EDUCATION_KEY);
        info.setName("Course Id Search By General Education");
        info.setDescr(new RichTextHelper().fromPlain("Search for course ids based on General Education values"));
        info.setEffectiveDate(DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.parse(DEFAULT_EFFECTIVE_DATE));
        KSAP_SEARCH_COURSEIDS_BY_GENERAL_EDUCATION = info;

        // Creates search that retrieves Course Title, ID, and Status by Course Subject-Code
        info = new TypeInfo();
        info.setKey(CourseSearchConstants.KSAP_COURSE_SEARCH_COURSEID_TITLE_AND_STATUS_BY_SUBJ_CD_KEY);
        info.setName("Lookup Course Title, ID, and Status by Course Subject-Code");
        info.setDescr(new RichTextHelper().fromPlain("Lookup Course Title, ID, and Status by Course Subject-Code"));
        info.setEffectiveDate(DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.parse(DEFAULT_EFFECTIVE_DATE));
        KSAP_SEARCH_COURSEID_TITLE_AND_STATUS_BY_SUBJ_CD = info;

        // Creates search that retrieves All Division Codes (ENGL, PHYS, BSCI)
        info = new TypeInfo();
        info.setKey(CourseSearchConstants.KSAP_COURSE_SEARCH_ALL_DIVISION_CODES_KEY);
        info.setName("Lookup Course Title, ID, and Status by Course Subject-Code");
        info.setDescr(new RichTextHelper().fromPlain("Lookup Course Title, ID, and Status by Course Subject-Code"));
        info.setEffectiveDate(DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.parse(DEFAULT_EFFECTIVE_DATE));
        KSAP_SEARCH_ALL_DIVISION_CODES = info;

        // Creates search that retrieves All Course IDs for a given version independent id
        info = new TypeInfo();
        info.setKey(CourseSearchConstants.KSAP_COURSE_SEARCH_COURSEIDS_BY_VERSION_IND_ID_KEY);
        info.setName("Lookup Course IDs by Version Independent ID");
        info.setDescr(new RichTextHelper().fromPlain("Lookup Course IDs by Version Independent ID"));
        info.setEffectiveDate(DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.parse(DEFAULT_EFFECTIVE_DATE));
        KSAP_SEARCH_COURSEIDS_BY_VERSION_IND_ID = info;
    }

    /**
     * Get the search type that the sub class implements.
     */
    @Override
    public TypeInfo getSearchType() {
        return KSAP_SEARCH_SUPPORT;
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

        if (CourseSearchConstants.KSAP_COURSE_SEARCH_COURSEVERSIONIDS_BY_TERM_SCHEDULED_KEY.equals(searchTypeKey)) {
            return KSAP_SEARCH_COURSEIDS_BY_TERM_SCHEDULED;
        } else if (CourseSearchConstants.KSAP_COURSE_SEARCH_COURSEIDS_BY_TERM_OFFERED_KEY.equals(searchTypeKey)) {
            return KSAP_SEARCH_COURSEIDS_BY_TERM_OFFERED;
        } else if (CourseSearchConstants.KSAP_COURSE_SEARCH_GENERAL_EDUCATION_VALUES_KEY.equals(searchTypeKey)) {
            return KSAP_SEARCH_GENERAL_EDUCATION_VALUES;
        } else if (CourseSearchConstants.KSAP_COURSE_SEARCH_COURSEIDS_BY_GENERAL_EDUCATION_KEY.equals(searchTypeKey)) {
            return KSAP_SEARCH_COURSEIDS_BY_GENERAL_EDUCATION;
        } else if (CourseSearchConstants.KSAP_COURSE_SEARCH_COURSEID_TITLE_AND_STATUS_BY_SUBJ_CD_KEY.equals(searchTypeKey)) {
            return KSAP_SEARCH_ALL_DIVISION_CODES;
        } else if (CourseSearchConstants.KSAP_COURSE_SEARCH_ALL_DIVISION_CODES_KEY.equals(searchTypeKey)) {
            return KSAP_SEARCH_ALL_DIVISION_CODES;
        } else if (CourseSearchConstants.KSAP_COURSE_SEARCH_COURSEIDS_BY_VERSION_IND_ID_KEY.equals(searchTypeKey)) {
            return KSAP_SEARCH_COURSEIDS_BY_VERSION_IND_ID;
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

        if (StringUtils.equals(searchRequestInfo.getSearchKey(), KSAP_SEARCH_COURSEIDS_BY_TERM_SCHEDULED.getKey())) {
            resultInfo =  searchForClusScheduledForTerms(searchRequestInfo, contextInfo);
        }else if (StringUtils.equals(searchRequestInfo.getSearchKey(), KSAP_SEARCH_COURSEIDS_BY_TERM_OFFERED.getKey())) {
            resultInfo =  searchForCluIdsOfferedForTerms(searchRequestInfo, contextInfo);
        }else if (StringUtils.equals(searchRequestInfo.getSearchKey(), KSAP_SEARCH_GENERAL_EDUCATION_VALUES.getKey())) {
            resultInfo =  searchForGeneralEducationValues(searchRequestInfo, contextInfo);
        }else if (StringUtils.equals(searchRequestInfo.getSearchKey(), KSAP_SEARCH_COURSEIDS_BY_GENERAL_EDUCATION.getKey())) {
            resultInfo =  searchForCluIdsForGeneralEducation(searchRequestInfo, contextInfo);
        }else if (StringUtils.equals(searchRequestInfo.getSearchKey(),KSAP_SEARCH_COURSEID_TITLE_AND_STATUS_BY_SUBJ_CD.getKey())) {
            resultInfo =  searchForCourseIdTitleAndStatusBySubjectCode(searchRequestInfo, contextInfo);
        }else if (StringUtils.equals(searchRequestInfo.getSearchKey(),KSAP_SEARCH_ALL_DIVISION_CODES.getKey())) {
            resultInfo =  searchForAllDivisionCodes(searchRequestInfo, contextInfo);
        }else if (StringUtils.equals(searchRequestInfo.getSearchKey(),KSAP_SEARCH_COURSEIDS_BY_VERSION_IND_ID.getKey())) {
            resultInfo =  searchForCluIdsByIndependentId(searchRequestInfo, contextInfo);
        }else {
            // If no matching search is found throw exception
            throw new OperationFailedException("Unsupported search type: " + searchRequestInfo.getSearchKey());
        }

        return resultInfo;
    }

    /**
     * Routed To from search method based on search type key pasted in the search request.
     * Used to create and execute for search type key KSAP_COURSE_SEARCH_COURSEVERSIONIDS_BY_TERM_SCHEDULED_KEY.
     *
     * @see #search(org.kuali.student.r2.core.search.dto.SearchRequestInfo, org.kuali.student.r2.common.dto.ContextInfo)
     */
    protected SearchResultInfo searchForClusScheduledForTerms(SearchRequestInfo searchRequestInfo, ContextInfo contextInfo)
            throws MissingParameterException, OperationFailedException {

        SearchRequestHelper requestHelper = new SearchRequestHelper(searchRequestInfo);
        String searchAtpId = requestHelper.getParamAsString(CourseSearchConstants.SearchParameters.ATP_ID);
        SearchResultInfo resultInfo = new SearchResultInfo();

        // Create sql string
        String queryStr = "SELECT" +
                "    DISTINCT" +
                "    clu.VER_IND_ID";
        queryStr = queryStr +
                "    FROM" +
                "    KSLU_CLU clu, " +
                "    KSEN_LUI lui ";
        queryStr = queryStr +
                "    WHERE" +
                "    lui.ATP_ID = :atpId " +
                "    AND lui.CLU_ID = clu.ID " +
                "    AND lui.LUI_STATE = '"+ LuiServiceConstants.LUI_CO_STATE_OFFERED_KEY+"'";

        // Set params and execute search
        Query query = getEntityManager().createNativeQuery(queryStr);
        query.setParameter(CourseSearchConstants.SearchParameters.ATP_ID, searchAtpId);
        List<Object> results = query.getResultList();

        // Compile results
        for(Object resultRow : results){
            SearchResultRowInfo row = new SearchResultRowInfo();
            row.addCell(CourseSearchConstants.SearchResultColumns.COURSE_VERSION_INDEPENDENT_ID, (String)resultRow);
            resultInfo.getRows().add(row);
        }

        return resultInfo;
    }

    /**
     * Routed To from search method based on search type key pasted in the search request.
     * Used to create and execute for search type key KSAP_COURSE_SEARCH_COURSEIDS_BY_TERM_OFFERED_KEY.
     *
     * @see #search(org.kuali.student.r2.core.search.dto.SearchRequestInfo, org.kuali.student.r2.common.dto.ContextInfo)
     */
    protected SearchResultInfo searchForCluIdsOfferedForTerms(SearchRequestInfo searchRequestInfo, ContextInfo contextInfo)
            throws MissingParameterException, OperationFailedException {

        SearchRequestHelper requestHelper = new SearchRequestHelper(searchRequestInfo);
        String searchAtpType = requestHelper.getParamAsString(CourseSearchConstants.SearchParameters.ATP_TYPE_KEY);
        SearchResultInfo resultInfo = new SearchResultInfo();

        // Create sql string
        String queryStr = "SELECT" +
                "    cluAtp.CLU_ID";
        queryStr = queryStr +
                "    FROM" +
                "    KSLU_CLU_ATP_TYPE_KEY cluAtp, " +
                "    KSLU_CLU clu ";
        queryStr = queryStr +
                "    WHERE" +
                "    cluAtp.ATP_TYPE_KEY = :atpTypeKey " +
                "    AND clu.ID = cluAtp.CLU_ID "+
                "    AND clu.ST = 'Active'" +
                "    AND clu.LUTYPE_ID = '"+ CluServiceConstants.CREDIT_COURSE_LU_TYPE_KEY+"'";

        // Set params and execute search
        Query query = getEntityManager().createNativeQuery(queryStr);
        query.setParameter(CourseSearchConstants.SearchParameters.ATP_TYPE_KEY, searchAtpType);
        List<Object> results = query.getResultList();

        // Compile results
        for(Object resultRow : results){
            SearchResultRowInfo row = new SearchResultRowInfo();
            row.addCell(CourseSearchConstants.SearchResultColumns.CLU_ID, (String)resultRow);
            resultInfo.getRows().add(row);
        }

        return resultInfo;
    }

    /**
     * Routed To from search method based on search type key pasted in the search request.
     * Used to create and execute for search type key KSAP_COURSE_SEARCH_GENERAL_EDUCATION_VALUES.
     *
     * @see #search(org.kuali.student.r2.core.search.dto.SearchRequestInfo, org.kuali.student.r2.common.dto.ContextInfo)
     */
    protected SearchResultInfo searchForGeneralEducationValues(SearchRequestInfo searchRequestInfo, ContextInfo contextInfo)
            throws MissingParameterException, OperationFailedException{
        SearchResultInfo resultInfo = new SearchResultInfo();

        // Create sql string
        String queryStr = "SELECT DISTINCT" +
                "    cluSet.ID," +
                "    cluSet.NAME," +
                "    cluSetAttr.ATTR_VALUE";
        queryStr = queryStr +
                "    FROM" +
                "    KSLU_CLU_SET cluSet, " +
                "    KSLU_CLU_SET_ATTR cluSetAttr, " +
                "    KSLU_CLU_SET_JN_CLU setJnClu, " +
                "    KSLU_CLU_SET_JN_CLU_SET cluSetJNCluSet ";
        queryStr = queryStr +
                "    WHERE" +
                "    cluSetJNCluSet.CLU_SET_PARENT_ID = ( " +
                "    SELECT c.ID FROM KSLU_CLU_SET c where c.NAME = 'General Education' "+
                "    )" +
                "    AND cluSetAttr.OWNER = cluSet.ID" +
                "    AND cluSetAttr.OWNER = setJnClu.CLU_SET_ID" +
                "    AND setJnClu.CLU_SET_ID = cluSetJNCluSet.CLU_SET_CHILD_ID";

        // Set params and execute search
        Query query = getEntityManager().createNativeQuery(queryStr);
        List<Object[]> results = query.getResultList();

        // Compile results
        for(Object[] resultRow : results){
            SearchResultRowInfo row = new SearchResultRowInfo();
            row.addCell(CourseSearchConstants.SearchResultColumns.CLU_SET_ID, (String)resultRow[0]);
            row.addCell(CourseSearchConstants.SearchResultColumns.CLU_SET_NAME, (String)resultRow[1]);
            row.addCell(CourseSearchConstants.SearchResultColumns.CLU_SET_ATTR_VALUE, (String)resultRow[2]);
            resultInfo.getRows().add(row);
        }

        return resultInfo;
    }

    /**
     * Routed To from search method based on search type key pasted in the search request.
     * Used to create and execute for search type key KSAP_COURSE_SEARCH_COURSEIDS_BY_GENERAL_EDUCATION.
     *
     * @see #search(org.kuali.student.r2.core.search.dto.SearchRequestInfo, org.kuali.student.r2.common.dto.ContextInfo)
     */
    protected SearchResultInfo searchForCluIdsForGeneralEducation(SearchRequestInfo searchRequestInfo, ContextInfo contextInfo)
            throws MissingParameterException, OperationFailedException{
        SearchResultInfo resultInfo = new SearchResultInfo();
        SearchRequestHelper requestHelper = new SearchRequestHelper(searchRequestInfo);
        String searchGenEd = requestHelper.getParamAsString(CourseSearchConstants.SearchParameters.GENED_KEY);


        // Create sql string
        String queryStr = "SELECT" +
                "    clu.ID";
        queryStr = queryStr +
                "    FROM" +
                "    KSLU_CLU clu, " +
                "    KSLU_CLU_SET_JN_CLU setJnClu ";
        queryStr = queryStr +
                "    WHERE" +
                "    setJnClu.CLU_SET_ID = '" +searchGenEd+"'" +
                "    AND clu.VER_IND_ID = setJnClu.CLU_VER_IND_ID";

        // Set params and execute search
        Query query = getEntityManager().createNativeQuery(queryStr);
        List<Object> results = query.getResultList();

        // Compile results
        for(Object resultRow : results){
            SearchResultRowInfo row = new SearchResultRowInfo();
            row.addCell(CourseSearchConstants.SearchResultColumns.CLU_ID, (String)resultRow);
            resultInfo.getRows().add(row);
        }

        return resultInfo;
    }

    /**
     * Routed To from search method based on search type key pasted in the search request.
     * Used to create and execute for search type key KSAP_COURSE_SEARCH_COURSEIDS_BY_GENERAL_EDUCATION.
     *
     * @see #search(org.kuali.student.r2.core.search.dto.SearchRequestInfo, org.kuali.student.r2.common.dto.ContextInfo)
     */
    protected SearchResultInfo searchForCourseIdTitleAndStatusBySubjectCode(SearchRequestInfo searchRequestInfo,
            ContextInfo contextInfo)
            throws MissingParameterException, OperationFailedException{
        SearchResultInfo resultInfo = new SearchResultInfo();
        SearchRequestHelper requestHelper = new SearchRequestHelper(searchRequestInfo);
        String subjectPrefix =requestHelper.getParamAsString(CourseSearchConstants.SearchParameters.COURSE_SUBJECT_PREFIX);
        String subjectSuffix = requestHelper.getParamAsString(CourseSearchConstants.SearchParameters.COURSE_SUBJECT_SUFFIX);

        // Create sql string
        String queryStr =
                "SELECT clu.id, id.LNG_NAME, clu.ST " +
                "   FROM KSLU_CLU clu, KSLU_CLU_IDENT id " +
                "   WHERE clu.offic_clu_id = id.id " +
                "      AND trim( id.DIVISION ) = :courseSubjectPrefix "+
                "      AND trim( id.SUFX_CD ) = :courseSubjectSuffix ";

        // Set params and execute search
        Query query = getEntityManager().createNativeQuery(queryStr);
        query.setParameter(CourseSearchConstants.SearchParameters.COURSE_SUBJECT_PREFIX, subjectPrefix);
        query.setParameter(CourseSearchConstants.SearchParameters.COURSE_SUBJECT_SUFFIX, subjectSuffix);
        List<Object[]> results = query.getResultList();

        // Compile results
        for(Object[] resultRow : results){
            SearchResultRowInfo row = new SearchResultRowInfo();
            row.addCell(CourseSearchConstants.SearchResultColumns.CLU_ID, (String)resultRow[0]);
            row.addCell(CourseSearchConstants.SearchResultColumns.CLU_TITLE, (String)resultRow[1]);
            row.addCell(CourseSearchConstants.SearchResultColumns.CLU_STATUS, (String)resultRow[2]);
            resultInfo.getRows().add(row);
        }

        return resultInfo;
    }
    /**
     * Routed To from search method based on search type key pasted in the search request.
     * Used to create and execute for search type key KSAP_COURSE_SEARCH_ALL_DIVISION_CODES_KEY.
     *
     * @see #search(org.kuali.student.r2.core.search.dto.SearchRequestInfo, org.kuali.student.r2.common.dto.ContextInfo)
     */
    protected SearchResultInfo searchForAllDivisionCodes(SearchRequestInfo searchRequestInfo,
            ContextInfo contextInfo)
            throws MissingParameterException, OperationFailedException{
        SearchResultInfo resultInfo = new SearchResultInfo();

        // Create sql string
        String queryStr ="SELECT distinct( ident.DIVISION )"+
                "FROM KSLU_CLU_IDENT ident "+
                "WHERE ident.DIVISION IS NOT null";

        // Set params and execute search
        Query query = getEntityManager().createNativeQuery(queryStr);
        List<Object> results = query.getResultList();

        // Compile results
        for(Object resultRow : results){
            SearchResultRowInfo row = new SearchResultRowInfo();
            row.addCell(CourseSearchConstants.SearchResultColumns.DIVISION_CODE, (String)resultRow);
            resultInfo.getRows().add(row);
        }

        return resultInfo;
    }

    /**
     * Routed To from search method based on search type key posted in the search request.
     * Used to create and execute for search type key KSAP_COURSE_SEARCH_COURSEIDS_BY_VERSION_IND_ID_KEY.
     *
     * @see #search(org.kuali.student.r2.core.search.dto.SearchRequestInfo, org.kuali.student.r2.common.dto.ContextInfo)
     */
    protected SearchResultInfo searchForCluIdsByIndependentId(SearchRequestInfo searchRequestInfo,
                                                              ContextInfo contextInfo)
            throws MissingParameterException, OperationFailedException{
        SearchResultInfo resultInfo = new SearchResultInfo();
        SearchRequestHelper requestHelper = new SearchRequestHelper(searchRequestInfo);
        String verIndId =requestHelper.getParamAsString(CourseSearchConstants.SearchParameters.VERSION_IND_ID);

        // Create sql string
        String queryStr ="SELECT clu.ID " +
                "FROM KSLU_CLU clu " +
                "WHERE clu.VER_IND_ID = :versionIndependentId";

        // Set params and execute search
        Query query = getEntityManager().createNativeQuery(queryStr);
        query.setParameter(CourseSearchConstants.SearchParameters.VERSION_IND_ID, verIndId);
        List<Object> results = query.getResultList();

        // Compile results
        for(Object resultRow : results){
            SearchResultRowInfo row = new SearchResultRowInfo();
            row.addCell(CourseSearchConstants.SearchResultColumns.CLU_ID, (String)resultRow);
            resultInfo.getRows().add(row);
        }

        return resultInfo;

    }
}
