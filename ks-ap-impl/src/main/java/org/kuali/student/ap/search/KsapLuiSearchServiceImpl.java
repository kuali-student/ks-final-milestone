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
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.framework.context.CourseSearchConstants;
import org.kuali.student.ap.framework.util.KsapHelperUtil;
import org.kuali.student.common.collection.KSCollectionUtils;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * Lui Specific searches used to support the in the KSAP Module
 *
 * Note: Move into the Enroll Modules when rework of the search system complete
 */
public class KsapLuiSearchServiceImpl extends SearchServiceAbstractHardwiredImpl {
    private static final Logger LOG = LoggerFactory.getLogger(KsapLuiSearchServiceImpl.class);

    public static final TypeInfo KSAP_LUI_SEARCH;
    public static final TypeInfo KSAP_COURSE_SEARCH_OFFERED_REG_GROUP_IDS_BY_CO_ID;
    public static final TypeInfo KSAP_COURSE_SEARCH_OFFERED_REG_GROUP_IDS_BY_AO_ID;
    public static final TypeInfo KSAP_COURSE_SEARCH_AO_IDS_BY_OFFERED_REG_GROUP_ID;
    public static final TypeInfo KSAP_COURSE_SEARCH_FO_IDS_BY_OFFERED_REG_GROUP_ID;
    public static final TypeInfo KSAP_COURSE_SEARCH_LU_BY_LUI_TITLE;
    public static final TypeInfo KSAP_COURSE_SEARCH_LU_BY_LUI_DESCRIPTION;
    public static final TypeInfo KSAP_SEARCH_COURSEIDS_BY_TERM_SCHEDULED;
    public static final TypeInfo KSAP_SEARCH_LUI_NAME_BY_LUI_ID;

    public static final String DEFAULT_EFFECTIVE_DATE = "01/01/2012";
    static {
        // Create default search type
        TypeInfo info = new TypeInfo();
        info.setKey(CourseSearchConstants.KSAP_LUI_SEARCH_KEY);
        info.setName("KSAP Lui Search");
        info.setDescr(new RichTextHelper().fromPlain("Return search results from KSAP Lui search"));

        try {
            info.setEffectiveDate(DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.parse(DEFAULT_EFFECTIVE_DATE));
        } catch ( IllegalArgumentException ex) {
            throw new RuntimeException("failed to set effective date to: "+DEFAULT_EFFECTIVE_DATE, ex);
        }
        KSAP_LUI_SEARCH = info;

        info = new TypeInfo();
        info.setKey(CourseSearchConstants.KSAP_COURSE_SEARCH_OFFERED_REG_GROUP_IDS_BY_CO_ID_KEY);
        info.setName("Reg Group Id Search By CO Id");
        info.setDescr(new RichTextHelper().fromPlain(
                "Search for Registration Group ids for the associated Course Offering Id"));
        info.setEffectiveDate(DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.parse(DEFAULT_EFFECTIVE_DATE));
        KSAP_COURSE_SEARCH_OFFERED_REG_GROUP_IDS_BY_CO_ID = info;

        info = new TypeInfo();
        info.setKey(CourseSearchConstants.KSAP_COURSE_SEARCH_OFFERED_REG_GROUP_IDS_BY_AO_ID_KEY);
        info.setName("Reg Group Id Search By CO Id");
        info.setDescr(new RichTextHelper().fromPlain(
                "Search for Registration Group ids for the associated Course Offering Id"));
        info.setEffectiveDate(DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.parse(DEFAULT_EFFECTIVE_DATE));
        KSAP_COURSE_SEARCH_OFFERED_REG_GROUP_IDS_BY_AO_ID = info;

        info = new TypeInfo();
        info.setKey(CourseSearchConstants.KSAP_COURSE_SEARCH_AO_IDS_BY_OFFERED_REG_GROUP_ID_KEY);
        info.setName("AO ID search by Reg Group Id");
        info.setDescr(new RichTextHelper().fromPlain(
                "Search for Activity Offering ids for the associated offered Registration Group id"));
        info.setEffectiveDate(DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.parse(DEFAULT_EFFECTIVE_DATE));
        KSAP_COURSE_SEARCH_AO_IDS_BY_OFFERED_REG_GROUP_ID = info;

        info = new TypeInfo();
        info.setKey(CourseSearchConstants.KSAP_COURSE_SEARCH_FO_IDS_BY_OFFERED_REG_GROUP_ID_KEY);
        info.setName("AO ID search by Reg Group Id");
        info.setDescr(new RichTextHelper().fromPlain(
                "Search for Activity Offering ids for the associated offered Registration Group id"));
        info.setEffectiveDate(DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.parse(DEFAULT_EFFECTIVE_DATE));
        KSAP_COURSE_SEARCH_FO_IDS_BY_OFFERED_REG_GROUP_ID = info;

        info = new TypeInfo();
        info.setKey(CourseSearchConstants.KSAP_COURSE_SEARCH_LU_BY_LUI_TITLE_KEY);
        info.setName("CLU search by related lui title ");
        info.setDescr(new RichTextHelper().fromPlain(
                "Search for Course ids by matching text in a scheduled lui title"));
        info.setEffectiveDate(DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.parse(DEFAULT_EFFECTIVE_DATE));
        KSAP_COURSE_SEARCH_LU_BY_LUI_TITLE = info;

        info = new TypeInfo();
        info.setKey(CourseSearchConstants.KSAP_COURSE_SEARCH_LU_BY_LUI_DESCRIPTION_KEY);
        info.setName("CLU search by related lui description ");
        info.setDescr(new RichTextHelper().fromPlain(
                "Search for Course ids by matching text in a scheduled lui description"));
        info.setEffectiveDate(DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.parse(DEFAULT_EFFECTIVE_DATE));
        KSAP_COURSE_SEARCH_LU_BY_LUI_DESCRIPTION = info;

        // Create search that retrieves a list of clu ids based on whether the clu entry has a CO offered in a term
        info = new TypeInfo();
        info.setKey(CourseSearchConstants.KSAP_COURSE_SEARCH_COURSEVERSIONIDS_BY_TERM_SCHEDULED_KEY);
        info.setName("Course Id Search By COs Scheduled In Term");
        info.setDescr(new RichTextHelper().fromPlain("Search for course ids based on if COs for course are offered in a term"));
        info.setEffectiveDate(DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.parse(DEFAULT_EFFECTIVE_DATE));
        KSAP_SEARCH_COURSEIDS_BY_TERM_SCHEDULED = info;

         // Create search that retrieves a reg group name and status using the id of the reg group
        info = new TypeInfo();
        info.setKey(CourseSearchConstants.KSAP_SEARCH_LUI_NAME_BY_LUI_ID_KEY);
        info.setName("Lui Name Search By lui Id");
        info.setDescr(new RichTextHelper().fromPlain("Search for lui name based on its id"));
        info.setEffectiveDate(DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.parse(DEFAULT_EFFECTIVE_DATE));
        KSAP_SEARCH_LUI_NAME_BY_LUI_ID = info;
    }

    /**
     * Get the search type that the sub class implements.
     */
    @Override
    public TypeInfo getSearchType() {
        return KSAP_LUI_SEARCH;
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

        if (CourseSearchConstants.KSAP_COURSE_SEARCH_OFFERED_REG_GROUP_IDS_BY_CO_ID_KEY.equals(searchTypeKey)) {
            return KSAP_COURSE_SEARCH_OFFERED_REG_GROUP_IDS_BY_CO_ID;
        } else if (CourseSearchConstants.KSAP_COURSE_SEARCH_OFFERED_REG_GROUP_IDS_BY_AO_ID_KEY.equals(searchTypeKey)) {
            return KSAP_COURSE_SEARCH_OFFERED_REG_GROUP_IDS_BY_AO_ID;
        } else if (CourseSearchConstants.KSAP_COURSE_SEARCH_AO_IDS_BY_OFFERED_REG_GROUP_ID_KEY.equals(searchTypeKey)) {
            return KSAP_COURSE_SEARCH_AO_IDS_BY_OFFERED_REG_GROUP_ID;
        } else if (CourseSearchConstants.KSAP_COURSE_SEARCH_FO_IDS_BY_OFFERED_REG_GROUP_ID_KEY.equals(searchTypeKey)) {
            return KSAP_COURSE_SEARCH_FO_IDS_BY_OFFERED_REG_GROUP_ID;
        } else if (CourseSearchConstants.KSAP_COURSE_SEARCH_LU_BY_LUI_DESCRIPTION_KEY.equals(searchTypeKey)) {
            return KSAP_COURSE_SEARCH_LU_BY_LUI_DESCRIPTION;
        } else if (CourseSearchConstants.KSAP_COURSE_SEARCH_LU_BY_LUI_TITLE_KEY.equals(searchTypeKey)) {
            return KSAP_COURSE_SEARCH_LU_BY_LUI_TITLE;
        } else if (CourseSearchConstants.KSAP_COURSE_SEARCH_COURSEVERSIONIDS_BY_TERM_SCHEDULED_KEY.equals(searchTypeKey)) {
            return KSAP_SEARCH_COURSEIDS_BY_TERM_SCHEDULED;
        } else if (CourseSearchConstants.KSAP_SEARCH_LUI_NAME_BY_LUI_ID_KEY.equals(searchTypeKey)) {
            return KSAP_SEARCH_LUI_NAME_BY_LUI_ID;
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

        if (StringUtils.equals(searchRequestInfo.getSearchKey(), KSAP_COURSE_SEARCH_OFFERED_REG_GROUP_IDS_BY_CO_ID.getKey())) {
            resultInfo =  searchForOfferedRegistrationGroupIdsByCourseOfferingId(searchRequestInfo, contextInfo);
        } else if (StringUtils.equals(searchRequestInfo.getSearchKey(), KSAP_COURSE_SEARCH_OFFERED_REG_GROUP_IDS_BY_AO_ID.getKey())) {
            resultInfo =  searchForOfferedRegistrationGroupsIdsByActivityOfferingId(searchRequestInfo, contextInfo);
        } else if (StringUtils.equals(searchRequestInfo.getSearchKey(), KSAP_COURSE_SEARCH_AO_IDS_BY_OFFERED_REG_GROUP_ID.getKey())) {
            resultInfo =  searchForActivityOfferingsIdsByOfferedRegistrationGroupId(searchRequestInfo, contextInfo);
        } else if (StringUtils.equals(searchRequestInfo.getSearchKey(), KSAP_COURSE_SEARCH_FO_IDS_BY_OFFERED_REG_GROUP_ID.getKey())) {
            resultInfo =  searchForFormatOfferingsIdsByOfferedRegistrationGroupId(searchRequestInfo, contextInfo);
        } else if (StringUtils.equals(searchRequestInfo.getSearchKey(), KSAP_COURSE_SEARCH_LU_BY_LUI_TITLE.getKey())) {
            resultInfo =  searchForLuByLuiTitle(searchRequestInfo, contextInfo);
        } else if (StringUtils.equals(searchRequestInfo.getSearchKey(), KSAP_COURSE_SEARCH_LU_BY_LUI_DESCRIPTION.getKey())) {
            resultInfo =  searchForLuByLuiDescription(searchRequestInfo, contextInfo);
        } else if (StringUtils.equals(searchRequestInfo.getSearchKey(), KSAP_SEARCH_COURSEIDS_BY_TERM_SCHEDULED.getKey())) {
            resultInfo =  searchForClusScheduledForTerms(searchRequestInfo, contextInfo);
        }  else if (StringUtils.equals(searchRequestInfo.getSearchKey(), KSAP_SEARCH_LUI_NAME_BY_LUI_ID.getKey())) {
            resultInfo =  searchForLuiNameByLuiId(searchRequestInfo, contextInfo);
        } else {
            // If no matching search is found throw exception
            throw new OperationFailedException("Unsupported search type: " + searchRequestInfo.getSearchKey());
        }

        return resultInfo;
    }

    /**
     * Routed To from search method based on search type key pasted in the search request.
     * Used to create and execute for search type key KSAP_COURSE_SEARCH_LU_BY_LUI_DESCRIPTION.
     *
     * @see #search(org.kuali.student.r2.core.search.dto.SearchRequestInfo, org.kuali.student.r2.common.dto.ContextInfo)
     */
    protected SearchResultInfo searchForLuByLuiDescription(SearchRequestInfo searchRequestInfo, ContextInfo contextInfo)
            throws MissingParameterException, OperationFailedException {

        SearchRequestHelper requestHelper = new SearchRequestHelper(searchRequestInfo);

        String queryText = requestHelper.getParamAsString(CourseSearchConstants.SearchParameters.QUERYTEXT);
        List<String> atpIdList = requestHelper.getParamAsList(CourseSearchConstants.SearchParameters.ATP_ID_LIST);

        String queryStr =
                "SELECT  lui.CLU_ID"+
                        "    FROM" +
                        "    KSEN_LUI lui," +
                        "    KSEN_LUI_IDENT ident" +
                        "    WHERE "+
                        "    (UPPER( ' ' || lui.DESCR_PLAIN || ' ' ) LIKE '%' || :queryText || '%')" +
                        "    AND ident.LUI_ID = lui.ID" +
                        "    AND lui.ATP_ID IN :" + CourseSearchConstants.SearchParameters.ATP_ID_LIST +
                        "    AND lui.LUI_STATE = '" + LuiServiceConstants.LUI_CO_STATE_OFFERED_KEY+"'" +
                        "    ORDER BY ident.LUI_CD";

        // Set params and execute search
        Query query = getEntityManager().createNativeQuery(queryStr);
        query.setParameter(CourseSearchConstants.SearchParameters.QUERYTEXT, queryText);
        query.setParameter(CourseSearchConstants.SearchParameters.ATP_ID_LIST, atpIdList);

        List<Object> results = query.getResultList();

        // Compile results
        SearchResultInfo resultInfo = compileSearchResults(results);

        return resultInfo;
    }

    /**
     * Routed To from search method based on search type key pasted in the search request.
     * Used to create and execute for search type key KSAP_COURSE_SEARCH_LU_BY_LUI_TITLE.
     *
     * @see #search(org.kuali.student.r2.core.search.dto.SearchRequestInfo, org.kuali.student.r2.common.dto.ContextInfo)
     */
    protected SearchResultInfo searchForLuByLuiTitle(SearchRequestInfo searchRequestInfo, ContextInfo contextInfo)
            throws MissingParameterException, OperationFailedException {

        SearchRequestHelper requestHelper = new SearchRequestHelper(searchRequestInfo);
        String queryText = requestHelper.getParamAsString(CourseSearchConstants.SearchParameters.QUERYTEXT);
        List<String> atpIdList = requestHelper.getParamAsList(CourseSearchConstants.SearchParameters.ATP_ID_LIST);

        String queryStr =
                "SELECT  lui.CLU_ID"+
                        "    FROM" +
                        "    KSEN_LUI lui," +
                        "    KSEN_LUI_IDENT ident" +
                        "    WHERE "+
                        "    (UPPER( ' ' || ident.LNG_NAME || ' ' ) LIKE '%'||:queryText||'%'" +
                        "    OR UPPER( ' ' || ident.SHRT_NAME || ' ' ) LIKE '%' || :queryText || '%')" +
                        "    AND ident.LUI_ID = lui.ID" +
                        "    AND lui.ATP_ID IN :" + CourseSearchConstants.SearchParameters.ATP_ID_LIST +
                        "    AND lui.LUI_STATE = '" + LuiServiceConstants.LUI_CO_STATE_OFFERED_KEY+"'" +
                        "    ORDER BY ident.LUI_CD";

        // Set params and execute search
        Query query = getEntityManager().createNativeQuery(queryStr);
        query.setParameter(CourseSearchConstants.SearchParameters.QUERYTEXT, queryText);
        query.setParameter(CourseSearchConstants.SearchParameters.ATP_ID_LIST, atpIdList);

        List<Object> results = query.getResultList();

        // Compile results
        SearchResultInfo resultInfo = compileSearchResults(results);

        return resultInfo;
    }

    /**
     * Routed To from search method based on search type key pasted in the search request.
     * Used to create and execute for search type key KSAP_COURSE_SEARCH_OFFERED_REG_GROUP_IDS_BY_CO_ID_KEY.
     *
     * @see #search(org.kuali.student.r2.core.search.dto.SearchRequestInfo, org.kuali.student.r2.common.dto.ContextInfo)
     */
    protected SearchResultInfo searchForOfferedRegistrationGroupIdsByCourseOfferingId(SearchRequestInfo
                                                                                              searchRequestInfo, ContextInfo contextInfo)
            throws MissingParameterException, OperationFailedException {
        SearchRequestHelper requestHelper = new SearchRequestHelper(searchRequestInfo);

        String courseOfferingId = requestHelper.getParamAsString(CourseSearchConstants.SearchParameters
                .COURSE_OFFERING_ID);

        SearchResultInfo resultInfo = new SearchResultInfo();

        // Create sql string
        String queryStr =
                "SELECT lr2.RELATED_LUI_ID "+
                        "    FROM" +
                        "    KSEN_LUILUI_RELTN lr1, " +
                        "    KSEN_LUILUI_RELTN LR2," +
                        "    KSEN_LUI l1," +
                        "    KSEN_LUI l2 " +
                        "    WHERE lr1.LUILUI_RELTN_TYPE=" +
                        "'"+LuiServiceConstants.LUI_LUI_RELATION_DELIVERED_VIA_CO_TO_FO_TYPE_KEY+"'" +
                        "       AND lr1.LUI_ID= :courseOfferingId "+
                        "       AND lr1.LUILUI_RELTN_STATE='"+LuiServiceConstants.LUI_LUI_RELATION_ACTIVE_STATE_KEY+"'"+
                        "       AND lr1.RELATED_LUI_ID=lr2.LUI_ID" +
                        "       AND lr2.LUILUI_RELTN_TYPE=" +
                        "'"+LuiServiceConstants.LUI_LUI_RELATION_DELIVERED_VIA_FO_TO_RG_TYPE_KEY+"'"+
                        "       AND lr2.LUI_ID=l1.ID "+
                        "       AND l1.LUI_STATE= '"+LuiServiceConstants.LUI_FO_STATE_OFFERED_KEY+"'"+
                        "       AND lr1.LUILUI_RELTN_STATE='"+LuiServiceConstants.LUI_LUI_RELATION_ACTIVE_STATE_KEY+"'"+
                        "       AND lr2.RELATED_LUI_ID=l2.ID "+
                        "       AND l2.LUI_STATE= '"+LuiServiceConstants.REGISTRATION_GROUP_OFFERED_STATE_KEY+"'";

        // Set params and execute search
        Query query = getEntityManager().createNativeQuery(queryStr);
        query.setParameter(CourseSearchConstants.SearchParameters.COURSE_OFFERING_ID, courseOfferingId);
        List<Object> results = query.getResultList();

        // Compile results
        for(Object resultRow : results){
            SearchResultRowInfo row = new SearchResultRowInfo();
            row.addCell(CourseSearchConstants.SearchResultColumns.REG_GROUP_ID, (String)resultRow);
            resultInfo.getRows().add(row);
        }

        return resultInfo;
    }

    /**
     * Routed To from search method based on search type key pasted in the search request.
     * Used to create and execute for search type key KSAP_COURSE_SEARCH_OFFERED_REG_GROUP_IDS_BY_AO_ID_KEY.
     *
     * @see #search(org.kuali.student.r2.core.search.dto.SearchRequestInfo, org.kuali.student.r2.common.dto.ContextInfo)
     */
    protected SearchResultInfo searchForOfferedRegistrationGroupsIdsByActivityOfferingId(SearchRequestInfo
                                                                                                 searchRequestInfo, ContextInfo contextInfo)
            throws MissingParameterException, OperationFailedException {
        SearchRequestHelper requestHelper = new SearchRequestHelper(searchRequestInfo);

        String activityOfferingId = requestHelper.getParamAsString(CourseSearchConstants.SearchParameters
                .ACTIVITY_OFFERING_ID);

        SearchResultInfo resultInfo = new SearchResultInfo();

        // Create sql string
        String queryStr =
                "SELECT lr1.LUI_ID "+
                        "    FROM" +
                        "    KSEN_LUILUI_RELTN lr1, " +
                        "    KSEN_LUI l1 " +
                        "    WHERE lr1.LUILUI_RELTN_TYPE=" +
                        "'"+LuiServiceConstants.LUI_LUI_RELATION_REGISTERED_FOR_VIA_RG_TO_AO_TYPE_KEY+"'" +
                        "       AND lr1.RELATED_LUI_ID= :activityOfferingId "+
                        "       AND lr1.LUILUI_RELTN_STATE='"+LuiServiceConstants.LUI_LUI_RELATION_ACTIVE_STATE_KEY+"'"+
                        "       AND lr1.LUI_ID=l1.ID "+
                        "       AND l1.LUI_STATE= '"+LuiServiceConstants.REGISTRATION_GROUP_OFFERED_STATE_KEY+"'";

        // Set params and execute search
        Query query = getEntityManager().createNativeQuery(queryStr);
        query.setParameter(CourseSearchConstants.SearchParameters.ACTIVITY_OFFERING_ID, activityOfferingId);
        List<Object> results = query.getResultList();

        // Compile results
        for(Object resultRow : results){
            SearchResultRowInfo row = new SearchResultRowInfo();
            row.addCell(CourseSearchConstants.SearchResultColumns.REG_GROUP_ID, (String)resultRow);
            resultInfo.getRows().add(row);
        }

        return resultInfo;
    }

    /**
     * Routed To from search method based on search type key pasted in the search request.
     * Used to create and execute for search type key KSAP_COURSE_SEARCH_AO_IDS_BY_OFFERED_REG_GROUP_ID_KEY.
     *
     * @see #search(org.kuali.student.r2.core.search.dto.SearchRequestInfo, org.kuali.student.r2.common.dto.ContextInfo)
     */
    protected SearchResultInfo searchForActivityOfferingsIdsByOfferedRegistrationGroupId
    (SearchRequestInfo searchRequestInfo, ContextInfo contextInfo)
            throws MissingParameterException, OperationFailedException {
        SearchRequestHelper requestHelper = new SearchRequestHelper(searchRequestInfo);

        String registrationGroupId = requestHelper.getParamAsString(CourseSearchConstants.SearchParameters
                .REG_GROUP_ID);

        SearchResultInfo resultInfo = new SearchResultInfo();

        // Create sql string
        String queryStr =
                "SELECT lr1.RELATED_LUI_ID "+
                        "    FROM" +
                        "    KSEN_LUILUI_RELTN lr1, " +
                        "    KSEN_LUI l1," +
                        "    KSEN_LUI l2 " +
                        "    WHERE lr1.LUILUI_RELTN_TYPE=" +
                        "'"+LuiServiceConstants.LUI_LUI_RELATION_REGISTERED_FOR_VIA_RG_TO_AO_TYPE_KEY+"'" +
                        "       AND lr1.LUI_ID= :registrationGroupId "+
                        "       AND lr1.LUILUI_RELTN_STATE='"+LuiServiceConstants.LUI_LUI_RELATION_ACTIVE_STATE_KEY+"'"+
                        "       AND lr1.LUI_ID=l1.ID "+
                        "       AND l1.LUI_STATE= '"+LuiServiceConstants.REGISTRATION_GROUP_OFFERED_STATE_KEY+"'"+
                        "       AND lr1.RELATED_LUI_ID=l2.ID "+
                        "       AND l2.LUI_STATE= '"+LuiServiceConstants.LUI_AO_STATE_OFFERED_KEY+"'";

        // Set params and execute search
        Query query = getEntityManager().createNativeQuery(queryStr);
        query.setParameter(CourseSearchConstants.SearchParameters.REG_GROUP_ID, registrationGroupId);
        List<Object> results = query.getResultList();

        // Compile results
        for(Object resultRow : results){
            SearchResultRowInfo row = new SearchResultRowInfo();
            row.addCell(CourseSearchConstants.SearchResultColumns.ACTIVITY_OFFERING_ID, (String)resultRow);
            resultInfo.getRows().add(row);
        }

        return resultInfo;
    }

    /**
     * Routed To from search method based on search type key pasted in the search request.
     * Used to create and execute for search type key KSAP_COURSE_SEARCH_FO_IDS_BY_OFFERED_REG_GROUP_ID_KEY.
     *
     * @see #search(org.kuali.student.r2.core.search.dto.SearchRequestInfo, org.kuali.student.r2.common.dto.ContextInfo)
     */
    protected SearchResultInfo searchForFormatOfferingsIdsByOfferedRegistrationGroupId
    (SearchRequestInfo searchRequestInfo, ContextInfo contextInfo)
            throws MissingParameterException, OperationFailedException {
        SearchRequestHelper requestHelper = new SearchRequestHelper(searchRequestInfo);

        String registrationGroupId = requestHelper.getParamAsString(CourseSearchConstants.SearchParameters
                .REG_GROUP_ID);

        SearchResultInfo resultInfo = new SearchResultInfo();

        // Create sql string
        String queryStr =
                "SELECT lr1.LUI_ID "+
                        "    FROM" +
                        "    KSEN_LUILUI_RELTN lr1, " +
                        "    KSEN_LUI l1," +
                        "    KSEN_LUI l2 " +
                        "    WHERE lr1.LUILUI_RELTN_TYPE=" +
                        "'"+LuiServiceConstants.LUI_LUI_RELATION_DELIVERED_VIA_FO_TO_RG_TYPE_KEY+"'" +
                        "       AND lr1.RELATED_LUI_ID= :registrationGroupId "+
                        "       AND lr1.LUILUI_RELTN_STATE='"+LuiServiceConstants.LUI_LUI_RELATION_ACTIVE_STATE_KEY+"'"+
                        "       AND lr1.RELATED_LUI_ID=l1.ID "+
                        "       AND l1.LUI_STATE= '"+LuiServiceConstants.REGISTRATION_GROUP_OFFERED_STATE_KEY+"'"+
                        "       AND lr1.LUI_ID=l2.ID "+
                        "       AND l2.LUI_STATE= '"+LuiServiceConstants.LUI_FO_STATE_OFFERED_KEY+"'";

        // Set params and execute search
        Query query = getEntityManager().createNativeQuery(queryStr);
        query.setParameter(CourseSearchConstants.SearchParameters.REG_GROUP_ID, registrationGroupId);
        List<Object> results = query.getResultList();

        // Compile results
        for(Object resultRow : results){
            SearchResultRowInfo row = new SearchResultRowInfo();
            row.addCell(CourseSearchConstants.SearchResultColumns.FORMAT_OFFERING_ID, (String)resultRow);
            resultInfo.getRows().add(row);
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
                "    lui.CLU_ID";
        queryStr = queryStr +
                "    FROM" +
                "    KSEN_LUI lui ";
        queryStr = queryStr +
                "    WHERE" +
                "    lui.ATP_ID = :atpId " +
                "    AND lui.LUI_STATE = '"+ LuiServiceConstants.LUI_CO_STATE_OFFERED_KEY+"'";

        // Set params and execute search
        Query query = getEntityManager().createNativeQuery(queryStr);
        query.setParameter(CourseSearchConstants.SearchParameters.ATP_ID, searchAtpId);
        List<Object> results = query.getResultList();

        // Compile results
        for(Object resultRow : results){
            SearchResultRowInfo row = new SearchResultRowInfo();
            row.addCell(CourseSearchConstants.SearchResultColumns.COURSE_VERSION_INDEPENDENT_ID, getVersionId((String)resultRow));
            resultInfo.getRows().add(row);
        }

        return resultInfo;
    }

    /**
     * Routed To from search method based on search type key pasted in the search request.
     * Used to create and execute for search type key KSAP_SEARCH_LUI_NAME_BY_LUI_ID_KEY.
     *
     * @see #search(org.kuali.student.r2.core.search.dto.SearchRequestInfo, org.kuali.student.r2.common.dto.ContextInfo)
     */
    protected SearchResultInfo searchForLuiNameByLuiId(SearchRequestInfo searchRequestInfo, ContextInfo contextInfo)
            throws MissingParameterException, OperationFailedException {

        SearchRequestHelper requestHelper = new SearchRequestHelper(searchRequestInfo);
        String luiId = requestHelper.getParamAsString(CourseSearchConstants.SearchParameters.LUI_ID);
        SearchResultInfo resultInfo = new SearchResultInfo();

        // Create sql string
        String queryStr = "SELECT" +
                "    lui.NAME, lui.LUI_STATE";
        queryStr = queryStr +
                "    FROM" +
                "    KSEN_LUI lui ";
        queryStr = queryStr +
                "    WHERE" +
                "    lui.ID = :luiId ";
        // Set params and execute search
        Query query = getEntityManager().createNativeQuery(queryStr);
        query.setParameter(CourseSearchConstants.SearchParameters.LUI_ID, luiId);
        List<Object[]> results = query.getResultList();

        // Compile results
        for(Object[] resultRow  : results){
            SearchResultRowInfo row = new SearchResultRowInfo();
            row.addCell(CourseSearchConstants.SearchResultColumns.LUI_NAME, (String)resultRow[0]);
            row.addCell(CourseSearchConstants.SearchResultColumns.LUI_STATE, (String)resultRow[1]);
            resultInfo.getRows().add(row);
        }

        return resultInfo;
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
                row.addCell(CourseSearchConstants.SearchResultColumns.COURSE_VERSION_INDEPENDENT_ID, getVersionId((String)resultRow));
                resultInfo.getRows().add(row);
                resultList.add((String)resultRow);
            }
        }
        return resultInfo;
    }

    private String getVersionId(String cluId){
        String versionId;
        try{
            SearchRequestInfo request = new SearchRequestInfo(CourseSearchConstants.KSAP_COURSE_SEARCH_COURSE_IND_VERSION_BY_CLU_ID_KEY);
            request.addParam(CourseSearchConstants.SearchParameters.CLU_ID,cluId);
            SearchResultInfo resultsVersionId = KsapFrameworkServiceLocator.getSearchService().search(request, KsapFrameworkServiceLocator.getContext().getContextInfo());
            if(resultsVersionId.getRows().isEmpty()){
                throw new IllegalArgumentException("No version id found for course with id " +cluId);
            }
            SearchResultRowInfo rowVersionId = KSCollectionUtils.getRequiredZeroElement(resultsVersionId.getRows());
            versionId = KsapHelperUtil.getCellValue(rowVersionId,CourseSearchConstants.SearchResultColumns.COURSE_VERSION_INDEPENDENT_ID);
        } catch (MissingParameterException e) {
            throw new RuntimeException("Unable to load course item", e);
        } catch (InvalidParameterException e) {
            throw new RuntimeException("Unable to load course item", e);
        } catch (OperationFailedException e) {
            throw new RuntimeException("Unable to load course item", e);
        } catch (PermissionDeniedException e) {
            throw new RuntimeException("Unable to load course item", e);
        }
        return versionId;
    }
}
