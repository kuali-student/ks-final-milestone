/**
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
 *
 * Created by vgadiyak on 1/8/14
 */
package org.kuali.student.enrollment.registration.search.service.impl;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.kuali.student.common.collection.KSCollectionUtils;
import org.kuali.student.r2.common.class1.search.SearchServiceAbstractHardwiredImplBase;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.CourseWaitListServiceConstants;
import org.kuali.student.r2.common.util.constants.LprServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.common.util.date.DateFormatters;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;
import org.kuali.student.r2.core.search.util.SearchRequestHelper;
import org.kuali.student.r2.lum.util.constants.LrcServiceConstants;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is used to do custom searches for Course Registration
 *
 * @author Kuali Student Team
 */
public class CourseRegistrationSearchServiceImpl extends SearchServiceAbstractHardwiredImplBase {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Resource
    private EntityManager entityManager;

    public static final Map<String, TypeInfo> searchKeyToSearchTypeMap;
    public static final List<TypeInfo> searchTypeList;

    public static final String REG_INFO_BY_PERSON_TERM_SEARCH_KEY =
            "kuali.search.type.lui.searchForCourseRegistrationByStudentAndTerm";
    public static final String REG_AND_WL_INFO_BY_PERSON_TERM_SEARCH_KEY =
            "kuali.search.type.lui.searchForCourseRegistrationAndWaitlistByStudentAndTerm";
    public static final String AO_SCHEDULES_CO_CREDITS_GRADING_OPTIONS_BY_IDS_SEARCH_KEY =
            "kuali.search.type.lui.searchForAOSchedulesAndCOCreditAndGradingOptionsByIds";
    public static final String LPR_TRANS_IDS_BY_PERSON_TERM_TYPE_SEARCH_KEY =
            "kuali.search.type.lpr.searchForLprTransIdsByAtpAndPersonAndTypeKey";
    public static final String REG_CART_BY_PERSON_TERM_SEARCH_KEY =
            "kuali.search.type.lui.searchForCourseRegistrationCartByStudentAndTerm";
    public static final String RVGS_BY_LUI_IDS_SEARCH_KEY =
            "kuali.search.type.lui.searchForRVGsByLuiIds";
    public static final String AOIDS_COUNT_SEARCH_KEY =
            "kuali.search.type.lui.countAoIds";
    public static final String AOIDS_TYPE_MAXSEATS_SEARCH_KEY =
            "kuali.search.type.lui.searchTypeMaxSeatsByAoIds";
    public static final String LPRS_BY_AOIDS_LPR_STATE_KEY =
            "kuali.search.type.lui.searchTypeAoLprsByAoIdsAndLprStates";
    public static final String LPRIDS_BY_MASTER_LPR_ID_SEARCH_KEY =
            "kuali.search.type.lui.searchForLprIdsByMasterLprId";
    public static final String SEAT_COUNT_INFO_BY_AOIDS_SEARCH_KEY =
            "kuali.search.type.lui.searchForSeatCountInfoByAOIds";
    public static final String SEAT_COUNT_INFO_BY_REG_GROUPS_SEARCH_KEY =
            "kuali.search.type.lui.searchForSeatCountsByRGIds";
    public static final String WL_BY_AO_IDS_SEARCH_KEY =
            "kuali.search.type.lui.searchForWaitlistByAoIds";
    public static final String CO_AND_AO_INFO_BY_CO_ID_SEARCH_KEY =
            "kuali.search.type.lui.searchForCoAndAoInfoByCoId";
    public static final String CO_SEARCH_INFO_SEARCH_KEY =
            "kuali.search.type.lui.searchForCOSearchInfo";
    public static final String RG_WAITLIST_AND_AO_SEATCOUNT_BY_COID_SEARCH_INFO_SEARCH_KEY =
            "kuali.search.type.lui.getRGWaitlistAndAOSeatcountsByCOId";

    public static final TypeInfo REG_INFO_BY_PERSON_TERM_SEARCH_TYPE;
    public static final TypeInfo REG_CART_BY_PERSON_TERM_SEARCH_TYPE;
    public static final TypeInfo AO_SCHEDULES_CO_CREDITS_GRADING_OPTIONS_BY_IDS_SEARCH_TYPE;
    public static final TypeInfo LPR_TRANS_IDS_BY_PERSON_TERM_TYPE_KEY_SEARCH_TYPE;
    public static final TypeInfo RVGS_BY_LUI_IDS_SEARCH_TYPE;
    public static final TypeInfo AOIDS_COUNT_SEARCH_TYPE;
    public static final TypeInfo AOIDS_TYPE_MAXSEATS_SEARCH_TYPE;
    public static final TypeInfo LPRS_BY_AOIDS_LPR_STATE_TYPE;
    public static final TypeInfo LPRIDS_BY_MASTER_LPR_ID_SEARCH_TYPE;
    public static final TypeInfo SEAT_COUNT_INFO_BY_AOIDS_SEARCH_TYPE;
    public static final TypeInfo REG_AND_WL_INFO_BY_PERSON_TERM_SEARCH_TYPE;
    public static final TypeInfo SEAT_COUNT_INFO_BY_REG_GROUPS_SEARCH_TYPE;
    public static final TypeInfo WL_BY_AO_IDS_SEARCH_TYPE;
    public static final TypeInfo CO_AND_AO_INFO_BY_CO_ID_SEARCH_TYPE;
    public static final TypeInfo CO_SEARCH_INFO_SEARCH_TYPE;
    public static final TypeInfo RG_WAITLIST_AND_AO_SEATCOUNT_BY_COID_SEARCH_INFO_SEARCH_TYPE;


    public static final String DEFAULT_EFFECTIVE_DATE = "01/01/2012";

    public static final class SearchParameters {
        public static final String AO_ID = "activityOfferingId";
        public static final String AO_IDS = "activityOfferingIds";
        public static final String AO_TYPES = "activityOfferingTypes";
        public static final String LPR_STATES = "lprStates";
        public static final String LUI_IDS = "luiIds";
        public static final String CO_ID = "courseOfferingId";
        public static final String COURSE_CODE = "courseCode";
        public static final String RVG_IDS = "rvgIds";
        public static final String PERSON_ID = "personId";
        public static final String CART_ID = "cartId";
        public static final String CART_ITEM_ID = "cartItemId";
        public static final String ATP_ID = "atpId";
        public static final String ATP_IDS = "atpIds";
        public static final String TYPE_KEY = "typeKey";
        public static final String MASTER_LPR_ID = "masterLprId";
        public static final String LPRT_TYPE = "lprtType";
        public static final String LPR_TYPE = "lprType";
    }

    public static final class SearchResultColumns {
        public static final String LUI_ID = "luiId";
        public static final String MASTER_LPR_ID = "masterLprId";
        public static final String PERSON_LUI_TYPE = "personLuiType";
        public static final String LUI_NAME = "luiName";
        public static final String LUI_LONG_NAME = "luiLongName";
        public static final String LUI_LEVEL = "luiLevel";
        public static final String LUI_CODE = "luiCode";
        public static final String LUI_TYPE = "luiType";
        public static final String LUI_DESC = "luiDesc";
        public static final String RES_VAL_GROUP_KEY = "resultValuesGroupKey";
        public static final String CREDITS = "credits";
        public static final String GRADING_OPTION_ID = "gradingOptionId";
        public static final String TBA_IND = "isTBA";
        public static final String ROOM_CODE = "roomCode";
        public static final String BUILDING_CODE = "buildingCode";
        public static final String WEEKDAYS = "weekdays";
        public static final String START_TIME_MS = "startTimeMs";
        public static final String END_TIME_MS = "endTimeMs";
        public static final String HONORS_FLAG = "honorsFlag";
        public static final String ATP_ID = "atpId";
        public static final String ATP_CD = "atpCd";
        public static final String ATP_NAME = "atpName";
        public static final String LPR_TRANS_ID = "lprTransId";

        public static final String CART_ID = "cartId";
        public static final String CART_ITEM_ID = "cartItemId";
        public static final String CART_STATE = "cartState";
        public static final String CART_ITEM_STATE = "cartItemState";
        public static final String CROSSLIST = "crossList";
        public static final String COURSE_CODE = "courseCode";
        public static final String COURSE_NUMBER = "courseNumber";
        public static final String COURSE_DIVISION = "courseDivision";
        public static final String COURSE_ID = "courseId";
        public static final String RG_CODE = "regGroupCode";
        public static final String RG_ID = "regGroupId";
        public static final String RG_WAITLIST_COUNT = "rgWaitlistCount";
        public static final String AO_NAME = "aoName";
        public static final String AO_TYPE = "aoType";
        public static final String AO_CODE = "aoCode";
        public static final String GRADING = "grading";
        public static final String RVG_ID = "rvgId";
        public static final String RVG_NAME = "rvgName";
        public static final String RVG_VALUE = "rvgValue";

        public static final String AO_ID = "activityOfferingId";
        public static final String AO_ATP_ID = "activityOfferingAtpId";
        public static final String AO_MAX_SEATS = "maxSeats";
        public static final String AO_IDS_ACTUAL_COUNT = "aoIdsActualCount";
        public static final String AO_IDS_EXPECTED_COUNT = "aoIdsExpectedCount";
        public static final String AO_WAITLIST_COUNT = "aoWaitlistCount";
        public static final String CWL_MAX_SIZE = "courseWaitlistMaxSize";
        public static final String CWL_ID = "courseWaitlistId";
        public static final String CWL_STATE = "courseWaitlistState";

        public static final String CO_ID = "courseOfferingId";
        public static final String CO_ATP_ID = "courseOfferingAtpId";
        public static final String CO_CODE = "courseOfferingCode";
        public static final String CO_SUBJECT_AREA = "courseOfferingSubjectArea";
        public static final String CO_LONG_NAME = "courseOfferingLongName";
        public static final String CO_DESC_FORMATTED = "courseOfferingDescFormatted";
        public static final String CO_CROSSLISTED_ID = "coCrossListedId";
        public static final String CO_CROSSLISTED_CODE = "coCrossListedCode";
        public static final String CO_CROSSLISTED_SUBJECT_AREA = "coCrossListedSubjectArea";
        public static final String CO_CLU_ID = "cluId";
        public static final String CO_STATE = "coState";
        public static final String CO_IDENT_TYPE = "coIdentType";

        public static final String SEAT_COUNT = "seatCount";
        public static final String WAITLIST_COUNT = "waitlistCount";
        public static final String SEATS_AVAILABLE = "seatsAvailable";

        public static final String LPR_ID = "lprId";
        public static final String LPR_TYPE = "lprType";
        public static final String LPR_STATE = "lprState";
        public static final String PERSON_ID = "personId";

        public static final String EFF_DATE = "effectiveDate";

        public static final String LPR_CREATETIME = "lprCreateTime";
    }

    /**
     * Convenience method for creating type info
     *
     * @param searchKey Search key
     * @param name      Fills the name field
     * @param desc      and the description field
     * @return a TypeInfo object
     */
    private static TypeInfo createTypeInfo(String searchKey, String name, String desc) {
        TypeInfo info = new TypeInfo();
        info.setKey(searchKey);
        info.setName(name);
        info.setDescr(new RichTextHelper().fromPlain(desc));
        info.setEffectiveDate(DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.parse(DEFAULT_EFFECTIVE_DATE));
        searchKeyToSearchTypeMap.put(info.getKey(), info);
        searchTypeList.add(info);
        return info;
    }

    static {
        searchKeyToSearchTypeMap = new HashMap<>();
        searchTypeList = new ArrayList<>();

        REG_INFO_BY_PERSON_TERM_SEARCH_TYPE =
                createTypeInfo(REG_INFO_BY_PERSON_TERM_SEARCH_KEY,
                        "Registration info by person and term",
                        "Returns registration info for given person and term");

        REG_CART_BY_PERSON_TERM_SEARCH_TYPE =
                createTypeInfo(REG_CART_BY_PERSON_TERM_SEARCH_KEY,
                        "Registration Cart by person and term",
                        "Returns registration cart for given person and term");

        AO_SCHEDULES_CO_CREDITS_GRADING_OPTIONS_BY_IDS_SEARCH_TYPE =
                createTypeInfo(AO_SCHEDULES_CO_CREDITS_GRADING_OPTIONS_BY_IDS_SEARCH_KEY,
                        "AO schedules by AO ids",
                        "Returns AO schedules for given aoID");

        // Search for LPR transactions by personId, atpId, and typeKey.  Can be used to fetch an ID for a
        // registration cart by looking for the registration cart ID.  It returns IDs only.
        LPR_TRANS_IDS_BY_PERSON_TERM_TYPE_KEY_SEARCH_TYPE =
                createTypeInfo(LPR_TRANS_IDS_BY_PERSON_TERM_TYPE_SEARCH_KEY,
                        "Lpr trans ids by person and term and typeKey",
                        "Returns Lpr trans ids by person and term and typeKey");

        RVGS_BY_LUI_IDS_SEARCH_TYPE =
                createTypeInfo(RVGS_BY_LUI_IDS_SEARCH_KEY,
                        "RVG information by list of lui Ids",
                        "Returns RVG keys names, and result values for credit options");

        AOIDS_COUNT_SEARCH_TYPE =
                createTypeInfo(AOIDS_COUNT_SEARCH_KEY,
                        "Count of valid AO ids (using AO types)",
                        "Returns a count of valid AO ids (using AO types) as a string");

        AOIDS_TYPE_MAXSEATS_SEARCH_TYPE =
                createTypeInfo(AOIDS_TYPE_MAXSEATS_SEARCH_KEY,
                        "(Id, type, max seats) for a list of AO ids",
                        "Returns (Id, type, max seats) for a list of AO ids");

        LPRS_BY_AOIDS_LPR_STATE_TYPE =
                createTypeInfo(LPRS_BY_AOIDS_LPR_STATE_KEY,
                        "(Id, type, state, lui, person) for a list of AO ids",
                        "Returns (Id, type, state, lui, person) for a list of AO ids");

        LPRIDS_BY_MASTER_LPR_ID_SEARCH_TYPE =
                createTypeInfo(LPRIDS_BY_MASTER_LPR_ID_SEARCH_KEY,
                        "(MasterLprId) for a list of LPR (AO, CO, RG) ids",
                        "Returns (Id) for a list of LPR (AO, CO, RG) ids");

        SEAT_COUNT_INFO_BY_AOIDS_SEARCH_TYPE =
                createTypeInfo(SEAT_COUNT_INFO_BY_AOIDS_SEARCH_KEY,
                        "(aoId, type, maxSeats, maxWaitlistSize, cwlId) for a list of AO ids",
                        "Returns (aoId, type, maxSeats, maxWaitlistSize, cwlId) for a list of AO ids");

        REG_AND_WL_INFO_BY_PERSON_TERM_SEARCH_TYPE =
                createTypeInfo(REG_AND_WL_INFO_BY_PERSON_TERM_SEARCH_KEY,
                        "Registration and waitlist info by person and term",
                        "Returns registration and waitlist info for given person and term");

        SEAT_COUNT_INFO_BY_REG_GROUPS_SEARCH_TYPE =
                createTypeInfo(SEAT_COUNT_INFO_BY_REG_GROUPS_SEARCH_KEY,
                        "(regGroupId, registeredCount, waitlistedCount) for a list of reg group ids",
                        "Returns (regGroupId, registeredCount, waitlistedCount) for a list of reg group ids");

        WL_BY_AO_IDS_SEARCH_TYPE =
                createTypeInfo(WL_BY_AO_IDS_SEARCH_KEY,
                        "waitlist information (aoid, rgid, atpid, lprid, personid, effectiveDate, numRegisteredForAo, " +
                                "maxAoSeats) for a list of activity offering ids. The activity ids passed in will match" +
                                "against all of the RGs that contain those AOs, and the search itself will be matched against" +
                                "all AOs that exist in those RGs",
                        "Returns waitlist information (aoid, rgid, atpid, lprid, personid, effectiveDate, " +
                                "numRegisteredForAo, maxAoSeats)  for a list of activity offering ids. The activity ids passed in will match" +
                                "against all of the RGs that contain those AOs, and the search itself will be matched against" +
                                "all AOs that exist in those RGs");

        CO_SEARCH_INFO_SEARCH_TYPE =
                createTypeInfo(CO_SEARCH_INFO_SEARCH_KEY,
                        "Course Offering Search information (luiId, atpid, description, title, code, level, division, number, credits, " +
                                "seatsAvailable) for all COs. This may be modified to only process COs for specified term ids or " +
                                "a list of CO Ids",
                        "ReturnsCourse Offering Search information (luiId, atpid, description, title, code, level, division, number, credits, " +
                                "seatsAvailable) for all COs. This may be modified to only process COs for specified term ids or " +
                                "a list of CO Ids");


        CO_AND_AO_INFO_BY_CO_ID_SEARCH_TYPE =
                createTypeInfo(CO_AND_AO_INFO_BY_CO_ID_SEARCH_KEY,
                        "Course Offering and Activity Offerings Info (coId, coCode, coDivision, coLongName, coDescription, coGradingOptions, coCreditOptions, " +
                                "aoId, aoType, aoName, maxAoSeats, numRegisteredForAo, aoScheduleId) for given Course Offering ID",
                        "Course Offering and Activity Offerings Info (coId, coCode, coDivision, coLongName, coDescription, coGradingOptions, coCreditOptions, " +
                                "aoId, aoType, aoName, maxAoSeats, numRegisteredForAo, aoScheduleId) for given Course Offering ID");
        RG_WAITLIST_AND_AO_SEATCOUNT_BY_COID_SEARCH_INFO_SEARCH_TYPE =
                createTypeInfo(RG_WAITLIST_AND_AO_SEATCOUNT_BY_COID_SEARCH_INFO_SEARCH_KEY,
                        "Up to date registration group waitlist counts and ao seatcounts by CO id (rgId,aoId,aoSeatcount,rgWaitlistCount)",
                        "Up to date registration group waitlist counts and ao seatcounts by CO id (rgId,aoId,aoSeatcount,rgWaitlistCount)");

    }

    @Override
    public TypeInfo getSearchType() {
        return REG_INFO_BY_PERSON_TERM_SEARCH_TYPE;
    }

    @Override
    public TypeInfo getSearchType(String searchTypeKey, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException {
        TypeInfo typeInfo = searchKeyToSearchTypeMap.get(searchTypeKey);
        if (typeInfo != null) {
            return typeInfo;
        }

        throw new DoesNotExistException("No Search Type Found for key: " + searchTypeKey);
    }

    @Override
    public List<TypeInfo> getSearchTypes(ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException {
        return searchTypeList;
    }

    @Override
    @Transactional(readOnly = true)
    public SearchResultInfo search(SearchRequestInfo searchRequestInfo, ContextInfo contextInfo)
            throws MissingParameterException, OperationFailedException, PermissionDeniedException {
        String searchKey = searchRequestInfo.getSearchKey();
        if (StringUtils.equals(REG_INFO_BY_PERSON_TERM_SEARCH_TYPE.getKey(), searchKey)) {
            return searchForCourseRegistrationByPersonAndTerm(searchRequestInfo);
        } else if (StringUtils.equals(LPR_TRANS_IDS_BY_PERSON_TERM_TYPE_KEY_SEARCH_TYPE.getKey(), searchKey)) {
            return searchForLprTransIdsByAtpAndPersonAndTypeKey(searchRequestInfo);
        } else if (StringUtils.equals(AO_SCHEDULES_CO_CREDITS_GRADING_OPTIONS_BY_IDS_SEARCH_TYPE.getKey(), searchRequestInfo.getSearchKey())) {
            return searchForAOSchedulesAndCOCreditAndGradingOptionsByIds(searchRequestInfo);
        } else if (StringUtils.equals(REG_CART_BY_PERSON_TERM_SEARCH_TYPE.getKey(), searchKey)) {
            return searchForCourseRegistrationCartByPersonAndTerm(searchRequestInfo);
        } else if (StringUtils.equals(RVGS_BY_LUI_IDS_SEARCH_TYPE.getKey(), searchKey)) {
            return searchForRVGsByLuiIds(searchRequestInfo);
        } else if (StringUtils.equals(AOIDS_COUNT_SEARCH_TYPE.getKey(), searchKey)) {
            return countValidAos(searchRequestInfo);
        } else if (StringUtils.equals(AOIDS_TYPE_MAXSEATS_SEARCH_TYPE.getKey(), searchKey)) {
            return searchForAoIdsTypeAndMaxSeats(searchRequestInfo);
        } else if (StringUtils.equals(LPRS_BY_AOIDS_LPR_STATE_TYPE.getKey(), searchKey)) {
            return searchForAoLprs(searchRequestInfo);
        } else if (StringUtils.equals(LPRIDS_BY_MASTER_LPR_ID_SEARCH_TYPE.getKey(), searchKey)) {
            return searchForLprIdsByMasterLprId(searchRequestInfo);
        } else if (StringUtils.equals(SEAT_COUNT_INFO_BY_AOIDS_SEARCH_TYPE.getKey(), searchKey)) {
            return searchForSeatCountInfoByAOIds(searchRequestInfo);
        } else if (StringUtils.equals(REG_AND_WL_INFO_BY_PERSON_TERM_SEARCH_TYPE.getKey(), searchKey)) {
            return searchForCourseRegistrationAndWaitlistByStudentAndTerm(searchRequestInfo);
        } else if (StringUtils.equals(SEAT_COUNT_INFO_BY_REG_GROUPS_SEARCH_TYPE.getKey(), searchKey)) {
            return searchForSeatCountsByRGIds(searchRequestInfo);
        } else if (StringUtils.equals(WL_BY_AO_IDS_SEARCH_TYPE.getKey(), searchKey)) {
            return searchForWaitlistByAoIds(searchRequestInfo);
        } else if (StringUtils.equals(CO_AND_AO_INFO_BY_CO_ID_SEARCH_TYPE.getKey(), searchRequestInfo.getSearchKey())) {
            return searchForCoAndAoInfoByCoId(searchRequestInfo);
        } else if (CO_SEARCH_INFO_SEARCH_TYPE.getKey().equals(searchKey)) {
            return searchForCOSearchInfo(searchRequestInfo);
        } else if (RG_WAITLIST_AND_AO_SEATCOUNT_BY_COID_SEARCH_INFO_SEARCH_TYPE.getKey().equals(searchKey)) {
            return getRGWaitlistAndAOSeatcountsByCOId(searchRequestInfo);
        } else {
            throw new OperationFailedException("Unsupported search type: " + searchRequestInfo.getSearchKey());
        }
    }

    /**
     * A quick way to get up-to-date information on a course offering's AO seat counts and RG waitlist counts
     *
     * @param searchRequestInfo search request
     * @return search results
     * @throws OperationFailedException
     */
    private SearchResultInfo getRGWaitlistAndAOSeatcountsByCOId(SearchRequestInfo searchRequestInfo) throws OperationFailedException {
        SearchResultInfo resultInfo = new SearchResultInfo();
        SearchRequestHelper requestHelper = new SearchRequestHelper(searchRequestInfo);
        String coId = requestHelper.getParamAsString(SearchParameters.CO_ID);
        String queryStr =
                "SELECT " +
                        "    rg2ao.LUI_ID         RGID, " +
                        "    rg2ao.RELATED_LUI_ID AOID, " +
                        "    ( " +
                        "        SELECT " +
                        "            COUNT(*) " +
                        "        FROM " +
                        "            KSEN_LPR lpr " +
                        "        WHERE " +
                        "            lpr.LUI_ID = rg2ao.RELATED_LUI_ID " +
                        "        AND lpr.LPR_TYPE='" + LprServiceConstants.REGISTRANT_AO_LPR_TYPE_KEY + "' " +
                        "        AND lpr.LPR_STATE='" + LprServiceConstants.ACTIVE_STATE_KEY + "') aoRegistered, " +
                        "    ( " +
                        "        SELECT " +
                        "            COUNT(*) " +
                        "        FROM " +
                        "            KSEN_LPR lpr " +
                        "        WHERE " +
                        "            lpr.LUI_ID = rg2ao.LUI_ID " +
                        "        AND lpr.LPR_TYPE='" + LprServiceConstants.WAITLIST_RG_LPR_TYPE_KEY + "' " +
                        "        AND lpr.LPR_STATE='" + LprServiceConstants.ACTIVE_STATE_KEY + "') rgWaitlisted " +
                        "FROM " +
                        "    KSEN_LUILUI_RELTN co2fo, " +
                        "    KSEN_LUILUI_RELTN fo2rg, " +
                        "    KSEN_LUILUI_RELTN rg2ao, " +
                        "    KSEN_LUI ao, " +
                        "    KSEN_LUI rg " +
                        "WHERE " +
                        "    co2fo.LUILUI_RELTN_TYPE='" + LuiServiceConstants.LUI_LUI_RELATION_DELIVERED_VIA_CO_TO_FO_TYPE_KEY + "' " +
                        "AND co2fo.LUILUI_RELTN_STATE='" + LuiServiceConstants.LUI_LUI_RELATION_ACTIVE_STATE_KEY + "' " +
                        "AND fo2rg.LUILUI_RELTN_TYPE='" + LuiServiceConstants.LUI_LUI_RELATION_DELIVERED_VIA_FO_TO_RG_TYPE_KEY + "' " +
                        "AND fo2rg.LUILUI_RELTN_STATE='" + LuiServiceConstants.LUI_LUI_RELATION_ACTIVE_STATE_KEY + "' " +
                        "AND rg2ao.LUILUI_RELTN_TYPE='" + LuiServiceConstants.LUI_LUI_RELATION_REGISTERED_FOR_VIA_RG_TO_AO_TYPE_KEY + "' " +
                        "AND rg2ao.LUILUI_RELTN_STATE='" + LuiServiceConstants.LUI_LUI_RELATION_ACTIVE_STATE_KEY + "' " +
                        "AND co2fo.RELATED_LUI_ID=fo2rg.LUI_ID " +
                        "AND fo2rg.RELATED_LUI_ID=rg2ao.LUI_ID " +
                        "AND co2fo.LUI_id=:courseOfferingId " +
                        "AND rg2ao.RELATED_LUI_ID = ao.ID " +
                        "AND ao.LUI_STATE = '" + LuiServiceConstants.LUI_AO_STATE_OFFERED_KEY + "' " +
                        "AND rg2ao.LUI_ID = rg.ID " +
                        "AND rg.LUI_STATE = '" + LuiServiceConstants.REGISTRATION_GROUP_OFFERED_STATE_KEY + "' ";

        Query query = entityManager.createNativeQuery(queryStr);
        query.setParameter(SearchParameters.CO_ID, coId);

        @SuppressWarnings("unchecked")
        List<Object[]> results = query.getResultList();

        for (Object[] resultRow : results) {
            int i = 0;
            SearchResultRowInfo row = new SearchResultRowInfo();
            row.addCell(SearchResultColumns.RG_ID, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.AO_ID, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.SEAT_COUNT, resultRow[i++].toString());
            row.addCell(SearchResultColumns.WAITLIST_COUNT, resultRow[i].toString());
            resultInfo.getRows().add(row);
        }

        return resultInfo;
    }

    /**
     * Grabs a view of Course Offering information that is the basis for the registration course search
     * Optional search parameters of term ids and lui ids can be passed in to limit the search.
     *
     * @param searchRequestInfo search request
     * @return search results
     */
    private SearchResultInfo searchForCOSearchInfo(SearchRequestInfo searchRequestInfo) {
        SearchResultInfo resultInfo = new SearchResultInfo();
        SearchRequestHelper requestHelper = new SearchRequestHelper(searchRequestInfo);
        List<String> luiIds = requestHelper.getParamAsList(SearchParameters.LUI_IDS);
        List<String> atpIds = requestHelper.getParamAsList(SearchParameters.ATP_ID);
        String queryStr =
                "SELECT\n" +
                        "    luii.LUI_ID_TYPE          coIdentType,\n" +
                        "    lui.LUI_STATE             coState,\n" +
                        "    lui.CLU_ID                cluid,\n" +
                        "    lui.id                    luiid,\n" +
                        "    lui.ATP_ID                atpid,\n" +
                        "    lui.DESCR_FORMATTED       description,\n" +
                        "    luii.LNG_NAME             title,\n" +
                        "    luii.LUI_CD               code,\n" +
                        "    clui.LVL                  crsLevel,\n" +
                        "    clui.DIVISION             division,\n" +
                        "    clui.SUFX_CD              suffix,\n" +
                        "    credits.RESULT_VAL_GRP_ID credits,\n" +
                        "    freeseats.sumao -\n" +
                        "    (\n" +
                        "        SELECT\n" +
                        "            COUNT(*)\n" +
                        "        FROM\n" +
                        "            KSEN_LPR lpr\n" +
                        "        WHERE\n" +
                        "            lpr.LUI_ID=lui.id\n" +
                        "        AND lpr.LPR_STATE='" + LprServiceConstants.ACTIVE_STATE_KEY + "'\n" +
                        "        AND lpr.LPR_TYPE='" + LprServiceConstants.REGISTRANT_CO_LPR_TYPE_KEY + "') seatsAvailable,\n" +
                        "    honorsCd.value            honorsFlag\n" +
                        "FROM\n" +
                        "    KSEN_LUI lui\n" +
                        "    INNER JOIN KSLU_CLU clu ON clu.id = lui.CLU_ID\n" +
                        "    INNER JOIN KSLU_CLU_IDENT clui on clui.id = clu.OFFIC_CLU_ID\n" +
                        "    INNER JOIN KSEN_LUI_IDENT luii ON luii.LUI_ID = lui.ID\n" +
                        "    INNER JOIN (\n" +
                        "        SELECT\n" +
                        "            luiid      luiid,\n" +
                        "            SUM(aomax) sumao\n" +
                        "        FROM\n" +
                        "            (\n" +
                        "                SELECT\n" +
                        "                    co2fo.LUI_ID      luiid,\n" +
                        "                    rg2ao.LUI_ID      rgid,\n" +
                        "                    MIN(ao.MAX_SEATS) aomax\n" +
                        "                FROM\n" +
                        "                    KSEN_LUI ao,\n" +
                        "                    KSEN_LUILUI_RELTN co2fo,\n" +
                        "                    KSEN_LUILUI_RELTN fo2rg,\n" +
                        "                    KSEN_LUILUI_RELTN rg2ao\n" +
                        "                WHERE\n" +
                        "                    co2fo.LUILUI_RELTN_TYPE='" + LuiServiceConstants.LUI_LUI_RELATION_DELIVERED_VIA_CO_TO_FO_TYPE_KEY + "'\n" +
                        "                AND co2fo.LUILUI_RELTN_STATE='" + LuiServiceConstants.LUI_LUI_RELATION_ACTIVE_STATE_KEY + "'\n" +
                        "                AND fo2rg.LUILUI_RELTN_TYPE='" + LuiServiceConstants.LUI_LUI_RELATION_DELIVERED_VIA_FO_TO_RG_TYPE_KEY + "'\n" +
                        "                AND fo2rg.LUILUI_RELTN_STATE='" + LuiServiceConstants.LUI_LUI_RELATION_ACTIVE_STATE_KEY + "'\n" +
                        "                AND rg2ao.LUILUI_RELTN_TYPE='" + LuiServiceConstants.LUI_LUI_RELATION_REGISTERED_FOR_VIA_RG_TO_AO_TYPE_KEY + "'\n" +
                        "                AND rg2ao.LUILUI_RELTN_STATE='" + LuiServiceConstants.LUI_LUI_RELATION_ACTIVE_STATE_KEY + "'\n" +
                        "                AND co2fo.RELATED_LUI_ID=fo2rg.LUI_ID\n" +
                        "                AND fo2rg.RELATED_LUI_ID=rg2ao.LUI_ID\n" +
                        "                AND rg2ao.RELATED_LUI_ID=ao.id\n" +
                        "                GROUP BY\n" +
                        "                    co2fo.LUI_ID,\n" +
                        "                    rg2ao.LUI_ID )\n" +
                        "        GROUP BY\n" +
                        "            luiid) freeseats on freeseats.luiid=lui.id\n" +
                        "    INNER JOIN KSEN_LUI_RESULT_VAL_GRP credits on credits.LUI_ID = lui.id\n" +
                        "    LEFT OUTER JOIN KSEN_LUI_LU_CD honorsCd on honorsCd.lui_id = luiId and honorsCd.lui_lucd_type = '" + LuiServiceConstants.HONORS_LU_CODE + "'\n" +
                        "WHERE\n" +
                        "    lui.LUI_TYPE='" + LuiServiceConstants.COURSE_OFFERING_TYPE_KEY + "'\n" +
                        "AND credits.RESULT_VAL_GRP_ID LIKE '" + LrcServiceConstants.RESULT_GROUP_KEY_KUALI_CREDITTYPE_CREDIT_BASE + "%'\n" +
                        (atpIds == null || atpIds.isEmpty() ? "" : "AND lui.ATP_ID IN(:atpIds)\n") + //Optional parameter to filter by luiids
                        (luiIds == null || luiIds.isEmpty() ? "" : "AND lui.ID IN(:luiIds)\n"); //Optional parameter to filter by term

        Query query = entityManager.createNativeQuery(queryStr);

        if (atpIds != null) {
            query.setParameter(SearchParameters.ATP_IDS, atpIds);
        }
        if (luiIds != null) {
            query.setParameter(SearchParameters.LUI_IDS, luiIds);
        }

        @SuppressWarnings("unchecked")
        List<Object[]> results = query.getResultList();

        for (Object[] resultRow : results) {
            int i = 0;
            SearchResultRowInfo row = new SearchResultRowInfo();
            row.addCell(SearchResultColumns.CO_IDENT_TYPE, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.CO_STATE, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.CO_CLU_ID, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.LUI_ID, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.ATP_ID, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.LUI_DESC, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.LUI_LONG_NAME, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.LUI_CODE, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.LUI_LEVEL, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.COURSE_DIVISION, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.COURSE_NUMBER, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.CREDITS, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.SEATS_AVAILABLE, resultRow[i++].toString());
            row.addCell(SearchResultColumns.HONORS_FLAG, (String) resultRow[i]);
            resultInfo.getRows().add(row);
        }

        return resultInfo;

    }

    /**
     * Given a set of AO ids, RG that contain those IDs are found. Then all AOs that exist in those RGs are selected,
     * along with any associated waitlist information per AO, including the number of people already registered, the
     * max seats for the AO, and the person currently waiting for that AO.
     * Given:
     * RG1      | RG2     | RG3
     * AO1 AO2  | AO1 AO3 | AO4 AO3
     * If AO1 is passed in, the search will match with all the RGs that contain AO1 (RG1,RG2)
     * Then all the AOs contained in those RGs are matched (AO1, AO2, AO3)
     * <p/>
     * The results will have:
     * AOID  RGID ATPID  LPRID PERSONID  EFFECTIVE_DATE NUM_REGISTERED_FOR_AO MAX_AO_SEATS
     * AO1   RG1  Fall12 123   Bob.Smith 1-1-2011 11:24 3                     3
     * AO2   RG1  Fall12 123   Jane.Doe  1-1-2011 11:25 1                     2
     * AO2   RG1  Fall12 123   Sue.Allen 1-1-2011 11:26 1                     2
     * <p/>
     * Using this information you can go line by line to see who gets in the AO and who does not.
     *
     * @param searchRequestInfo search request
     * @return search results
     * @throws OperationFailedException
     */
    private SearchResultInfo searchForWaitlistByAoIds(SearchRequestInfo searchRequestInfo) throws OperationFailedException {
        SearchResultInfo resultInfo = new SearchResultInfo();
        SearchRequestHelper requestHelper = new SearchRequestHelper(searchRequestInfo);
        List<String> aoIds = requestHelper.getParamAsList(SearchParameters.AO_IDS);
        String queryStr =
                "SELECT DISTINCT " +
                        "    rg2ao.related_lui_id aoid, " +
                        "    waitlistRgLpr.lui_Id rgid, " +
                        "    waitlistRgLpr.atp_Id atpid, " +
                        "    waitlistAoLpr.MASTER_LPR_ID lprid, " +
                        "    waitlistAoLpr.PERS_ID personid, " +
                        "    waitlistAoLpr.EFF_DT effectiveDate, " +
                        "    ( " +
                        "        SELECT " +
                        "            COUNT(*) " +
                        "        FROM " +
                        "            KSEN_LPR lpr " +
                        "        WHERE " +
                        "            lpr.LUI_ID = rg2ao.related_lui_id " +
                        "        AND lpr.LPR_TYPE='" + LprServiceConstants.REGISTRANT_AO_LPR_TYPE_KEY + "' " +
                        "        AND lpr.LPR_STATE='" + LprServiceConstants.ACTIVE_STATE_KEY + "') numRegisteredForAo, " +
                        "    aolui.max_seats maxAoSeats " +
                        "FROM " +
                        "    KSEN_LPR waitlistAoLpr, " +
                        "    KSEN_LPR waitlistRgLpr, " +
                        "    KSEN_LUI aolui, " +
                        "    KSEN_LUILUI_RELTN sourceAos2rg, " +
                        "    KSEN_LUILUI_RELTN rg2ao " +
                        "WHERE " +
                        "    sourceAos2rg.LUILUI_RELTN_TYPE='" + LuiServiceConstants.LUI_LUI_RELATION_REGISTERED_FOR_VIA_RG_TO_AO_TYPE_KEY + "' " +
                        "AND rg2ao.LUILUI_RELTN_TYPE='" + LuiServiceConstants.LUI_LUI_RELATION_REGISTERED_FOR_VIA_RG_TO_AO_TYPE_KEY + "' " +
                        "AND sourceAos2rg.RELATED_LUI_ID IN(:activityOfferingIds) " +
                        "AND rg2ao.LUI_ID=sourceAos2rg.LUI_ID " +
                        "AND waitlistRgLpr.LPR_TYPE ='" + LprServiceConstants.WAITLIST_RG_LPR_TYPE_KEY + "' " +
                        "AND waitlistAoLpr.LPR_TYPE ='" + LprServiceConstants.WAITLIST_AO_LPR_TYPE_KEY + "' " +
                        "AND waitlistRgLpr.LPR_STATE ='" + LprServiceConstants.ACTIVE_STATE_KEY + "' " +
                        "AND waitlistAoLpr.LPR_STATE ='" + LprServiceConstants.ACTIVE_STATE_KEY + "' " +
                        "AND waitlistAoLpr.LUI_ID=rg2ao.related_lui_id " +
                        "AND waitlistRgLpr.MASTER_LPR_ID=waitlistAoLpr.MASTER_LPR_ID " +
                        "AND aolui.id=rg2ao.related_lui_id " +
                        "ORDER BY waitlistAoLpr.EFF_DT, waitlistAoLpr.PERS_ID, waitlistRgLpr.Lui_Id ASC";

        Query query = entityManager.createNativeQuery(queryStr);
        query.setParameter(SearchParameters.AO_IDS, aoIds);

        @SuppressWarnings("unchecked")
        List<Object[]> results = query.getResultList();

        for (Object[] resultRow : results) {
            int i = 0;
            SearchResultRowInfo row = new SearchResultRowInfo();
            row.addCell(SearchResultColumns.AO_ID, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.RG_ID, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.ATP_ID, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.MASTER_LPR_ID, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.PERSON_ID, (String) resultRow[i++]);
            Date effectiveDate = (Date) resultRow[i++];
            if (effectiveDate != null) {
                row.addCell(SearchResultColumns.EFF_DATE, DateFormatters.DEFAULT_DATE_FORMATTER.format(effectiveDate));
            } else {
                row.addCell(SearchResultColumns.EFF_DATE, null);
            }
            BigDecimal seatCount = (BigDecimal) resultRow[i++];
            if (seatCount != null) {
                row.addCell(SearchResultColumns.SEAT_COUNT, String.valueOf(seatCount.intValue()));
            } else {
                row.addCell(SearchResultColumns.SEAT_COUNT, null);
            }
            BigDecimal maxSeats = (BigDecimal) resultRow[i];
            if (maxSeats != null) {
                row.addCell(SearchResultColumns.AO_MAX_SEATS, String.valueOf(maxSeats.intValue()));
            } else {
                row.addCell(SearchResultColumns.AO_MAX_SEATS, null);
            }
            resultInfo.getRows().add(row);
        }

        return resultInfo;

    }

    private SearchResultInfo searchForCourseRegistrationAndWaitlistByStudentAndTerm(SearchRequestInfo searchRequestInfo) throws OperationFailedException {
        SearchResultInfo resultInfo = new SearchResultInfo();
        SearchRequestHelper requestHelper = new SearchRequestHelper(searchRequestInfo);
        String atpId = requestHelper.getParamAsString(SearchParameters.ATP_ID);
        String personId = requestHelper.getParamAsString(SearchParameters.PERSON_ID);

        String queryStr =
                "SELECT " +
                        "    lpr.LPR_TYPE              lprType, " +
                        "    lpr.id                    lprId, " +
                        "    lpr.ATP_ID                atpId, " +
                        "    atp.ATP_CD                atpCode, " +
                        "    atp.NAME                  atpName, " +
                        "    coId.LUI_CD               courseCode, " +
                        "    co.ID                     courseId, " +
                        "    rg.NAME                   rgName, " +
                        "    ao.NAME                   aoName, " +
                        "    ao.LUI_TYPE               luiType, " +
                        "    coId.LNG_NAME             coTitle, " +
                        "    co.DESCR_FORMATTED        coDescription, " +
                        "    schedCmp.TBA_IND          isTBA, " +
                        "    room.ROOM_CD              room, " +
                        "    room2bldg.BUILDING_CD     building, " +
                        "    schedTmslt.WEEKDAYS       weekdays, " +
                        "    schedTmslt.START_TIME_MS  startTime, " +
                        "    schedTmslt.END_TIME_MS    endTime, " +
                        "    credits.RESULT_VAL_GRP_ID credits, " +
                        "    grading.RESULT_VAL_GRP_ID grading " +
                        "FROM " +
                        "    KSEN_LUI co, " +
                        "    KSEN_LUI rg, " +
                        "    KSEN_LUI_IDENT coId, " +
                        "    KSEN_LUILUI_RELTN fo2rg, " +
                        "    KSEN_LUILUI_RELTN co2fo, " +
                        "    KSEN_LUILUI_RELTN rg2ao, " +
                        "    KSEN_LUI ao " +
                        "LEFT OUTER JOIN " +
                        "    KSEN_LUI_SCHEDULE sched " +
                        "ON " +
                        "    sched.LUI_ID = ao.ID " +
                        "LEFT OUTER JOIN " +
                        "    KSEN_SCHED_CMP schedCmp " +
                        "ON " +
                        "    schedCmp.SCHED_ID = sched.SCHED_ID " +
                        "LEFT OUTER JOIN " +
                        "    KSEN_ROOM room " +
                        "ON " +
                        "    room.ID = schedCmp.ROOM_ID " +
                        "LEFT OUTER JOIN " +
                        "    KSEN_ROOM_BUILDING room2bldg " +
                        "ON " +
                        "    room2bldg.ID = room.BUILDING_ID " +
                        "LEFT OUTER JOIN " +
                        "    KSEN_SCHED_CMP_TMSLOT schedCmpTmslt " +
                        "ON " +
                        "    schedCmpTmslt.SCHED_CMP_ID = schedCmp.ID " +
                        "LEFT OUTER JOIN " +
                        "    KSEN_SCHED_TMSLOT schedTmslt " +
                        "ON " +
                        "    schedTmslt.ID = schedCmpTmslt.TM_SLOT_ID, " +
                        "    KSEN_LPR lpr " +
                        "LEFT OUTER JOIN " +
                        "    KSEN_LPR_RESULT_VAL_GRP credits " +
                        "ON " +
                        "    credits.LPR_ID = lpr.id " +
                        "AND credits.RESULT_VAL_GRP_ID LIKE '" + LrcServiceConstants.RESULT_GROUP_KEY_KUALI_CREDITTYPE_CREDIT_BASE + "%' " +
                        "LEFT OUTER JOIN " +
                        "    KSEN_LPR_RESULT_VAL_GRP grading " +
                        "ON " +
                        "    grading.LPR_ID = lpr.id " +
                        "AND grading.RESULT_VAL_GRP_ID LIKE '" + LrcServiceConstants.RESULT_GROUP_KEY_GRADE_BASE + ".%' " +
                        "LEFT OUTER JOIN " +
                        "    KSEN_ATP atp " +
                        "ON " +
                        "   atp.id = lpr.atp_id " +
                        "WHERE " +
                        "    lpr.PERS_ID = :personId " +
                        "AND lpr.LPR_STATE = '" + LprServiceConstants.ACTIVE_STATE_KEY + "' " +
                        "AND lpr.LPR_TYPE IN('" + LprServiceConstants.REGISTRANT_RG_LPR_TYPE_KEY + "', " +
                        "                    '" + LprServiceConstants.WAITLIST_RG_LPR_TYPE_KEY + "') " +
                        (!StringUtils.isEmpty(atpId) ? " AND lpr.ATP_ID = :atpId " : "") +
                        "AND rg2ao.LUILUI_RELTN_TYPE='" + LuiServiceConstants.LUI_LUI_RELATION_REGISTERED_FOR_VIA_RG_TO_AO_TYPE_KEY + "' " +
                        "AND fo2rg.LUILUI_RELTN_TYPE='" + LuiServiceConstants.LUI_LUI_RELATION_DELIVERED_VIA_FO_TO_RG_TYPE_KEY + "' " +
                        "AND co2fo.LUILUI_RELTN_TYPE='" + LuiServiceConstants.LUI_LUI_RELATION_DELIVERED_VIA_CO_TO_FO_TYPE_KEY + "' " +
                        "AND rg2ao.LUI_ID=lpr.LUI_ID " +
                        "AND fo2rg.RELATED_LUI_ID = lpr.LUI_ID " +
                        "AND co2fo.RELATED_LUI_ID = fo2rg.LUI_ID " +
                        "AND ao.id = rg2ao.RELATED_LUI_ID " +
                        "AND co.id = co2fo.LUI_ID " +
                        "AND rg.id = lpr.LUI_ID " +
                        "AND coId.LUI_ID = co.id " +
                        "ORDER BY lpr.ATP_ID, lpr.LPR_TYPE, lpr.id, ao.LUI_TYPE";


        Query query = entityManager.createNativeQuery(queryStr);
        query.setParameter(SearchParameters.PERSON_ID, personId);
        if (!StringUtils.isEmpty(atpId)) {
            query.setParameter(SearchParameters.ATP_ID, atpId);
        }

        @SuppressWarnings("unchecked")
        List<Object[]> results = query.getResultList();

        for (Object[] resultRow : results) {
            int i = 0;
            SearchResultRowInfo row = new SearchResultRowInfo();
            row.addCell(SearchResultColumns.LPR_TYPE, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.MASTER_LPR_ID, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.ATP_ID, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.ATP_CD, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.ATP_NAME, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.COURSE_CODE, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.COURSE_ID, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.RG_CODE, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.AO_NAME, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.AO_TYPE, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.LUI_LONG_NAME, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.LUI_DESC, (String) resultRow[i++]);
            BigDecimal tbaInd = (BigDecimal) resultRow[i++];
            row.addCell(SearchResultColumns.TBA_IND, (tbaInd == null) ? "" : tbaInd.toString());
            row.addCell(SearchResultColumns.ROOM_CODE, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.BUILDING_CODE, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.WEEKDAYS, (String) resultRow[i++]);
            BigDecimal startTimeMs = (BigDecimal) resultRow[i++];
            row.addCell(SearchResultColumns.START_TIME_MS, (startTimeMs == null) ? "" : startTimeMs.toString());
            BigDecimal endTimeMs = (BigDecimal) resultRow[i++];
            row.addCell(SearchResultColumns.END_TIME_MS, (endTimeMs == null) ? "" : endTimeMs.toString());
            row.addCell(SearchResultColumns.CREDITS, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.GRADING_OPTION_ID, (String) resultRow[i]);

            resultInfo.getRows().add(row);
        }

        return resultInfo;
    }


    /**
     * This method will return the registration counts for a list of regGroups. Right now it filters on the
     * lpr_type and state. Right now the filters are:
     * kuali.lpr.type.registrant.registration.group && kuali.lpr.state.registered // registered for a reg group
     * or
     * kuali.lpr.type.waitlist.registration.group && kuali.lpr.state.active // waitlisted for a reg group
     *
     * @param searchRequestInfo Must have a list of LUI_IDS passed in.
     * @return count, lui_id, and lpr_type
     */
    private SearchResultInfo searchForSeatCountsByRGIds(SearchRequestInfo searchRequestInfo) {
        SearchResultInfo resultInfo = new SearchResultInfo();
        SearchRequestHelper requestHelper = new SearchRequestHelper(searchRequestInfo);
        List<String> luiIds = requestHelper.getParamAsList(SearchParameters.LUI_IDS);

        String queryStr =
                "SELECT" +
                        "    COUNT(*), " +
                        "    lpr.lui_id, " +
                        "    lpr.lpr_type " +
                        "FROM " +
                        "    KSEN_LPR lpr " +
                        "WHERE " +
                        " lpr.lui_id in (:luiIds) " +
                        " AND   ( ( " +
                        "            LPR_TYPE = :rgRegType " +
                        "        AND lpr.lpr_state = :rgRegState) " +
                        "    OR  (" +
                        "            LPR_TYPE = :rgWlType " +
                        "        AND lpr.lpr_state = :rgWlState) ) " +
                        "GROUP BY" +
                        "    lpr.lui_id, " +
                        "    lpr.lpr_type ";


        Query query = entityManager.createNativeQuery(queryStr);
        query.setParameter(SearchParameters.LUI_IDS, luiIds);

        // configure the types and states. One time use so there's no Search Param Const
        query.setParameter("rgRegType", LprServiceConstants.REGISTRANT_RG_LPR_TYPE_KEY);
        query.setParameter("rgRegState", LprServiceConstants.ACTIVE_STATE_KEY);
        query.setParameter("rgWlType", LprServiceConstants.WAITLIST_RG_LPR_TYPE_KEY);
        query.setParameter("rgWlState", LprServiceConstants.ACTIVE_STATE_KEY);

        @SuppressWarnings("unchecked")
        List<Object[]> results = query.getResultList();

        for (Object[] resultRow : results) {
            int i = 0;
            SearchResultRowInfo row = new SearchResultRowInfo();
            row.addCell(SearchResultColumns.SEAT_COUNT, resultRow[i] == null ? null : (resultRow[i]).toString());
            i++;
            row.addCell(SearchResultColumns.LUI_ID, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.LPR_TYPE, (String) resultRow[i]);

            resultInfo.getRows().add(row);
        }

        return resultInfo;
    }

    /**
     * Searches for seat counts and waitlist counts for the given AO ids
     * Note this implementation assumes that there is one course waitlist per AO.
     *
     * @param searchRequestInfo ao ids to search on
     * @return list of search results
     */
    private SearchResultInfo searchForSeatCountInfoByAOIds(SearchRequestInfo searchRequestInfo) {
        SearchResultInfo resultInfo = new SearchResultInfo();
        SearchRequestHelper requestHelper = new SearchRequestHelper(searchRequestInfo);
        List<String> aoIds = requestHelper.getParamAsList(SearchParameters.AO_IDS);

        String queryStr =
                "SELECT " +
                        "    ao.ID AO_ID, " +
                        "    ao.LUI_TYPE LUI_TYPE, " +
                        "    ao.MAX_SEATS MAX_SEATS, " +
                        "    cwl.MAX_SIZE MAX_SIZE, " +
                        "    cwl.id CWL_ID, " +
                        "    ( " +
                        "        SELECT " +
                        "            COUNT(*) " +
                        "        FROM " +
                        "            KSEN_LPR lpr " +
                        "        WHERE " +
                        "            lpr.LUI_ID = ao.ID " +
                        "        AND lpr.LPR_TYPE = '" + LprServiceConstants.REGISTRANT_AO_LPR_TYPE_KEY + "' " +
                        "        AND lpr.LPR_STATE = '" + LprServiceConstants.ACTIVE_STATE_KEY + "') registered, " +
                        "    ( " +
                        "        SELECT " +
                        "            COUNT(*) " +
                        "        FROM " +
                        "            KSEN_LPR lpr " +
                        "        WHERE " +
                        "            lpr.LUI_ID = ao.ID " +
                        "        AND lpr.LPR_TYPE = '" + LprServiceConstants.WAITLIST_AO_LPR_TYPE_KEY + "' " +
                        "        AND lpr.LPR_STATE = '" + LprServiceConstants.ACTIVE_STATE_KEY + "') waitlisted " +
                        "FROM " +
                        "    KSEN_LUI ao " +
                        "LEFT OUTER JOIN " +
                        "    KSEN_CWL_ACTIV_OFFER cwl2ao " +
                        "ON " +
                        "    cwl2ao.ACTIV_OFFER_ID = ao.id " +
                        "LEFT OUTER JOIN " +
                        "    KSEN_CWL cwl " +
                        "ON " +
                        "    ( " +
                        "        cwl.id = cwl2ao.CWL_ID " +
                        "    AND cwl.CWL_STATE = '" + CourseWaitListServiceConstants.COURSE_WAIT_LIST_ACTIVE_STATE_KEY + "') " +
                        "WHERE " +
                        "    ao.ID IN (:activityOfferingIds)";


        Query query = entityManager.createNativeQuery(queryStr);
        query.setParameter(SearchParameters.AO_IDS, aoIds);

        @SuppressWarnings("unchecked")
        List<Object[]> results = query.getResultList();

        for (Object[] resultRow : results) {
            int i = 0;
            SearchResultRowInfo row = new SearchResultRowInfo();
            row.addCell(SearchResultColumns.AO_ID, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.AO_TYPE, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.AO_MAX_SEATS, resultRow[i] == null ? null : resultRow[i].toString());
            i++;
            row.addCell(SearchResultColumns.CWL_MAX_SIZE, resultRow[i] == null ? null : resultRow[i].toString());
            i++;
            row.addCell(SearchResultColumns.CWL_ID, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.AO_IDS_ACTUAL_COUNT, resultRow[i] == null ? null : resultRow[i].toString());
            i++;
            row.addCell(SearchResultColumns.AO_WAITLIST_COUNT, resultRow[i] == null ? null : resultRow[i].toString());

            resultInfo.getRows().add(row);
        }

        return resultInfo;
    }

    private SearchResultInfo searchForRVGsByLuiIds(SearchRequestInfo searchRequestInfo) throws OperationFailedException {
        SearchResultInfo resultInfo = new SearchResultInfo();
        SearchRequestHelper requestHelper = new SearchRequestHelper(searchRequestInfo);
        List<String> luiIds = requestHelper.getParamAsList(SearchParameters.LUI_IDS);

        String queryStr =
                "SELECT " +
                        "    lrvg.LUI_ID luiId, " +
                        "    rvg.ID      rvgId, " +
                        "    rvg.name    rvgName, " +
                        "    rvgVal.RESULT_VALUE rvgValue " +
                        "FROM " +
                        "    KSEN_LUI_RESULT_VAL_GRP lrvg, " +
                        "    KSEN_LRC_RVG rvg " +
                        "LEFT OUTER JOIN " +
                        "    KSEN_LRC_RVG_RESULT_VALUE rvg2Val " +
                        "ON " +
                        "    rvg2Val.RVG_ID=rvg.id " +
                        "AND rvg.id LIKE '" + LrcServiceConstants.RESULT_GROUP_KEY_KUALI_CREDITTYPE_CREDIT_BASE + "%' " +
                        "LEFT OUTER JOIN " +
                        "    KSEN_LRC_RESULT_VALUE rvgVal " +
                        "ON " +
                        "    rvgVal.ID = rvg2Val.RESULT_VALUE_ID " +
                        "WHERE " +
                        "    lrvg.LUI_ID IN (:luiIds) " +
                        "AND lrvg.RESULT_VAL_GRP_ID=rvg.ID";


        Query query = entityManager.createNativeQuery(queryStr);
        query.setParameter(SearchParameters.LUI_IDS, luiIds);

        @SuppressWarnings("unchecked")
        List<Object[]> results = query.getResultList();

        for (Object[] resultRow : results) {
            int i = 0;
            SearchResultRowInfo row = new SearchResultRowInfo();
            row.addCell(SearchResultColumns.LUI_ID, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.RVG_ID, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.RVG_NAME, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.RVG_VALUE, (String) resultRow[i]);

            resultInfo.getRows().add(row);
        }

        return resultInfo;
    }

    private SearchResultInfo searchForCourseRegistrationCartByPersonAndTerm(SearchRequestInfo searchRequestInfo) throws OperationFailedException {
        SearchResultInfo resultInfo = new SearchResultInfo();
        SearchRequestHelper requestHelper = new SearchRequestHelper(searchRequestInfo);
        String atpId = requestHelper.getParamAsString(SearchParameters.ATP_ID);
        String personId = requestHelper.getParamAsString(SearchParameters.PERSON_ID);
        String cartId = requestHelper.getParamAsString(SearchParameters.CART_ID);
        String cartItemId = requestHelper.getParamAsString(SearchParameters.CART_ITEM_ID);
        String lprtType = requestHelper.getParamAsString(SearchParameters.LPRT_TYPE);
        if (StringUtils.isEmpty(lprtType)) {
            lprtType = LprServiceConstants.LPRTRANS_REG_CART_TYPE_KEY;
        }

        String queryStr = "SELECT " +
                "    lprt.id                   cartId, " +
                "    lprti.ID                  cartItemId, " +
                "    lprt.LPR_TRANS_STATE       cartState, " +
                "    lprti.LPR_TRANS_ITEM_STATE cartItemState, " +
                "    lprti.CROSSLIST           crossList, " +
                "    coId.LUI_CD               courseCode, " +
                "    co.ID                     courseId, " +
                "    rg.NAME                   rgName, " +
                "    rg.ID                     rgId, " +
                "    ao.ID                     aoId, " +
                "    ao.NAME                   aoName, " +
                "    ao.LUI_TYPE               luiType, " +
                "    coId.LNG_NAME             coTitle, " +
                "    schedCmp.TBA_IND          isTBA, " +
                "    room.ROOM_CD              room, " +
                "    room2bldg.BUILDING_CD     building, " +
                "    schedTmslt.WEEKDAYS       weekdays, " +
                "    schedTmslt.START_TIME_MS  startTime, " +
                "    schedTmslt.END_TIME_MS    endTime, " +
                "    credits.RESULT_VAL_GRP_ID credits, " +
                "    grading.RESULT_VAL_GRP_ID grading " +
                "FROM " +
                "    KSEN_LPR_TRANS lprt, " +
                "    KSEN_LUI co, " +
                "    KSEN_LUI rg, " +
                "    KSEN_LUI_IDENT coId, " +
                "    KSEN_LUILUI_RELTN fo2rg, " +
                "    KSEN_LUILUI_RELTN co2fo, " +
                "    KSEN_LUILUI_RELTN rg2ao, " +
                "    KSEN_LUI ao " +
                "LEFT OUTER JOIN " +
                "    KSEN_LUI_SCHEDULE sched " +
                "ON " +
                "    sched.LUI_ID = ao.ID " +
                "LEFT OUTER JOIN " +
                "    KSEN_SCHED_CMP schedCmp " +
                "ON " +
                "    schedCmp.SCHED_ID = sched.SCHED_ID " +
                "LEFT OUTER JOIN " +
                "    KSEN_ROOM room " +
                "ON " +
                "    room.ID = schedCmp.ROOM_ID " +
                "LEFT OUTER JOIN " +
                "    KSEN_ROOM_BUILDING room2bldg " +
                "ON " +
                "    room2bldg.ID = room.BUILDING_ID " +
                "LEFT OUTER JOIN " +
                "    KSEN_SCHED_CMP_TMSLOT schedCmpTmslt " +
                "ON " +
                "    schedCmpTmslt.SCHED_CMP_ID = schedCmp.ID " +
                "LEFT OUTER JOIN " +
                "    KSEN_SCHED_TMSLOT schedTmslt " +
                "ON " +
                "    schedTmslt.ID = schedCmpTmslt.TM_SLOT_ID, " +
                "    KSEN_LPR_TRANS_ITEM lprti " +
                "LEFT OUTER JOIN " +
                "    KSEN_LPR_TRANS_ITEM_RVG credits " +
                "ON " +
                "    credits.LPR_TRANS_ITEM_ID = lprti.id " +
                "AND credits.RESULT_VAL_GRP_ID LIKE '" + LrcServiceConstants.RESULT_GROUP_KEY_KUALI_CREDITTYPE_CREDIT_BASE + "%' " +
                "LEFT OUTER JOIN " +
                "    KSEN_LPR_TRANS_ITEM_RVG grading " +
                "ON " +
                "    grading.LPR_TRANS_ITEM_ID = lprti.id " +
                "AND grading.RESULT_VAL_GRP_ID LIKE '" + LrcServiceConstants.RESULT_GROUP_KEY_GRADE_BASE + ".%' " +
                "WHERE " +
                "    lprt.REQUESTING_PERS_ID = :personId " +
                "AND ( lprt.LPR_TRANS_TYPE= :lprtType " +
                "      OR " +
                "     lprti.LPR_TRANS_ITEM_STATE = :lprtiProcessingState ) " +  // shows processing items
                "AND lprt.ATP_ID = :atpId " +
                "AND lprti.LPR_TRANS_ID=lprt.ID " +
                "AND rg2ao.LUILUI_RELTN_TYPE='" + LuiServiceConstants.LUI_LUI_RELATION_REGISTERED_FOR_VIA_RG_TO_AO_TYPE_KEY + "' " +
                "AND fo2rg.LUILUI_RELTN_TYPE='" + LuiServiceConstants.LUI_LUI_RELATION_DELIVERED_VIA_FO_TO_RG_TYPE_KEY + "' " +
                "AND co2fo.LUILUI_RELTN_TYPE='" + LuiServiceConstants.LUI_LUI_RELATION_DELIVERED_VIA_CO_TO_FO_TYPE_KEY + "' " +
                "AND rg2ao.LUI_ID=lprti.NEW_LUI_ID " +
                "AND fo2rg.RELATED_LUI_ID = lprti.NEW_LUI_ID " +
                "AND co2fo.RELATED_LUI_ID = fo2rg.LUI_ID " +
                "AND ao.id = rg2ao.RELATED_LUI_ID " +
                "AND co.id = co2fo.LUI_ID " +
                "AND rg.id = lprti.NEW_LUI_ID " +
                "AND coId.LUI_ID = co.id " +
                "AND coId.LUI_ID_TYPE = '" + LuiServiceConstants.LUI_IDENTIFIER_OFFICIAL_TYPE_KEY + "' " +
                "AND coId.LUI_ID_STATE = '" + LuiServiceConstants.LUI_IDENTIFIER_ACTIVE_STATE_KEY + "' " +
                (StringUtils.isEmpty(cartItemId) ? " " : "AND lprti.ID = :cartItemId ") +
                (StringUtils.isEmpty(cartId) ? " " : "AND lprt.ID = :cartId ") +
                "ORDER BY " +
                "    lprt.ID, " +
                "    lprti.ID, " +
                "    ao.LUI_TYPE";

        Query query = entityManager.createNativeQuery(queryStr);
        query.setParameter(SearchParameters.PERSON_ID, personId);
        query.setParameter(SearchParameters.ATP_ID, atpId);
        query.setParameter(SearchParameters.LPRT_TYPE, lprtType);
        query.setParameter("lprtiProcessingState", LprServiceConstants.LPRTRANS_ITEM_PROCESSING_STATE_KEY);

        if (!StringUtils.isEmpty(cartItemId)) {
            query.setParameter(SearchParameters.CART_ITEM_ID, cartItemId);
        }
        if (!StringUtils.isEmpty(cartId)) {
            query.setParameter(SearchParameters.CART_ID, cartId);
        }

        @SuppressWarnings("unchecked")
        List<Object[]> results = query.getResultList();

        for (Object[] resultRow : results) {
            int i = 0;
            SearchResultRowInfo row = new SearchResultRowInfo();
            row.addCell(SearchResultColumns.CART_ID, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.CART_ITEM_ID, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.CART_STATE, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.CART_ITEM_STATE, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.CROSSLIST, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.COURSE_CODE, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.COURSE_ID, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.RG_CODE, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.RG_ID, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.AO_ID, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.AO_NAME, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.AO_TYPE, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.LUI_LONG_NAME, (String) resultRow[i++]);
            BigDecimal tbaInd = (BigDecimal) resultRow[i++];
            row.addCell(SearchResultColumns.TBA_IND, (tbaInd == null) ? "" : tbaInd.toString());
            row.addCell(SearchResultColumns.ROOM_CODE, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.BUILDING_CODE, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.WEEKDAYS, (String) resultRow[i++]);
            BigDecimal startTimeMs = (BigDecimal) resultRow[i++];
            row.addCell(SearchResultColumns.START_TIME_MS, (startTimeMs == null) ? "" : startTimeMs.toString());
            BigDecimal endTimeMs = (BigDecimal) resultRow[i++];
            row.addCell(SearchResultColumns.END_TIME_MS, (endTimeMs == null) ? "" : endTimeMs.toString());
            row.addCell(SearchResultColumns.CREDITS, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.GRADING, (String) resultRow[i]);

            resultInfo.getRows().add(row);
        }

        return resultInfo;

    }

    /**
     * Returns list of Registration Info for the person: CO, AO, Schedules, etc.
     *
     * @throws OperationFailedException
     */
    private SearchResultInfo searchForCourseRegistrationByPersonAndTerm(SearchRequestInfo searchRequestInfo) throws OperationFailedException {
        SearchResultInfo resultInfo = new SearchResultInfo();
        SearchRequestHelper requestHelper = new SearchRequestHelper(searchRequestInfo);
        String atpId = requestHelper.getParamAsString(SearchParameters.ATP_ID);
        String personId = requestHelper.getParamAsString(SearchParameters.PERSON_ID);
        List<String> lprTypes = requestHelper.getParamAsList(SearchParameters.LPR_TYPE);

        StringBuilder queryBuilder = new StringBuilder("");

        queryBuilder.append(
                "SELECT atp.ID, atp.ATP_CD, atp.NAME as atp_name, " +
                        "lpr.LUI_ID, lpr.MASTER_LPR_ID, lpr.LPR_TYPE, lpr.LPR_STATE, lpr.CREDITS, lpr.GRADING_OPT_ID, lpr.CROSSLIST, lpr.CREATETIME, " +
                        "luiId.LUI_CD, lui.NAME as lui_name, lui.DESCR_FORMATTED, lui.LUI_TYPE, luiId.LNG_NAME, " +
                        "luiRes.RESULT_VAL_GRP_ID, schedCmp.TBA_IND, " +
                        "room.ROOM_CD, rBldg.BUILDING_CD, " +
                        "schedTmslt.WEEKDAYS, schedTmslt.START_TIME_MS, schedTmslt.END_TIME_MS " +
                        "FROM KSEN_ATP atp, " +
                        "     KSEN_LPR lpr, " +
                        "     KSEN_LUI lui, " +
                        "     KSEN_LUI_IDENT luiId " +
                        "LEFT OUTER JOIN KSEN_LUI_RESULT_VAL_GRP luiRes " +
                        "ON luiRes.LUI_ID = luiId.LUI_ID " +
                        "LEFT OUTER JOIN KSEN_LUI_SCHEDULE aoSched " +
                        "ON aoSched.LUI_ID = luiId.LUI_ID " +
                        "LEFT OUTER JOIN KSEN_SCHED_CMP schedCmp " +
                        "ON schedCmp.SCHED_ID = aoSched.SCHED_ID " +
                        "LEFT OUTER JOIN KSEN_ROOM room " +
                        "ON room.ID = schedCmp.ROOM_ID " +
                        "LEFT OUTER JOIN KSEN_ROOM_BUILDING rBldg " +
                        "ON rBldg.ID = room.BUILDING_ID " +
                        "LEFT OUTER JOIN KSEN_SCHED_CMP_TMSLOT schedCmpTmslt " +
                        "ON schedCmpTmslt.SCHED_CMP_ID = schedCmp.ID " +
                        "LEFT OUTER JOIN KSEN_SCHED_TMSLOT schedTmslt " +
                        "ON schedTmslt.ID = schedCmpTmslt.TM_SLOT_ID " +
                        "WHERE lpr.PERS_ID = :personId " +
                        "  AND atp.ID = lpr.ATP_ID " +
                        "  AND lui.ID = lpr.LUI_ID " +
                        "  AND luiId.LUI_ID = lui.ID " +
                        "  AND lpr.LPR_STATE = '" + LprServiceConstants.ACTIVE_STATE_KEY + "' ");

        if (!StringUtils.isEmpty(atpId)) {
            queryBuilder.append(" AND lpr.ATP_ID = :atpId ");
        }
        if (!CollectionUtils.isEmpty(lprTypes)) {
            queryBuilder.append(" AND lpr.LPR_TYPE in (:lprType) ");
        }

        Query query = entityManager.createNativeQuery(queryBuilder.toString());
        query.setParameter(SearchParameters.PERSON_ID, personId);
        if (!StringUtils.isEmpty(atpId)) {
            query.setParameter(SearchParameters.ATP_ID, atpId);
        }
        if (!CollectionUtils.isEmpty(lprTypes)) {
            query.setParameter(SearchParameters.LPR_TYPE, lprTypes);
        }

        @SuppressWarnings("unchecked")
        List<Object[]> results = query.getResultList();

        for (Object[] resultRow : results) {
            int i = 0;
            SearchResultRowInfo row = new SearchResultRowInfo();
            row.addCell(SearchResultColumns.ATP_ID, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.ATP_CD, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.ATP_NAME, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.LUI_ID, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.MASTER_LPR_ID, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.PERSON_LUI_TYPE, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.LPR_STATE, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.CREDITS, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.GRADING_OPTION_ID, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.CROSSLIST, (String) resultRow[i++]);
            Date lprCreateTime = (Date) resultRow[i++];
            row.addCell(SearchResultColumns.LPR_CREATETIME, lprCreateTime.toString());
            row.addCell(SearchResultColumns.LUI_CODE, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.LUI_NAME, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.LUI_DESC, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.LUI_TYPE, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.LUI_LONG_NAME, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.RES_VAL_GROUP_KEY, (String) resultRow[i++]);
            BigDecimal tbaInd = (BigDecimal) resultRow[i++];
            row.addCell(SearchResultColumns.TBA_IND, (tbaInd == null) ? "" : tbaInd.toString());
            row.addCell(SearchResultColumns.ROOM_CODE, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.BUILDING_CODE, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.WEEKDAYS, (String) resultRow[i++]);
            BigDecimal startTimeMs = (BigDecimal) resultRow[i++];
            row.addCell(SearchResultColumns.START_TIME_MS, (startTimeMs == null) ? "" : startTimeMs.toString());
            BigDecimal endTimeMs = (BigDecimal) resultRow[i];
            row.addCell(SearchResultColumns.END_TIME_MS, (endTimeMs == null) ? "" : endTimeMs.toString());
            resultInfo.getRows().add(row);
        }

        return resultInfo;
    }

    /**
     * @param searchRequestInfo Search request parameter
     * @return Search result with list of LPR Transaction IDs
     * @throws OperationFailedException
     */
    private SearchResultInfo searchForLprTransIdsByAtpAndPersonAndTypeKey(SearchRequestInfo searchRequestInfo)
            throws OperationFailedException {
        SearchRequestHelper requestHelper = new SearchRequestHelper(searchRequestInfo);
        String atpId = requestHelper.getParamAsString(SearchParameters.ATP_ID);
        String personId = requestHelper.getParamAsString(SearchParameters.PERSON_ID);
        String typeKey = requestHelper.getParamAsString(SearchParameters.TYPE_KEY);

        String queryStr = "SELECT lprTrans.ID lprtId " +
                "FROM KSEN_LPR_TRANS lprTrans WHERE " +
                " lprTrans.ATP_ID = :atpId AND " +
                " lprTrans.LPR_TRANS_TYPE = :typeKey AND " +
                " lprTrans.REQUESTING_PERS_ID = :personId";
        Query query = entityManager.createNativeQuery(queryStr);
        query.setParameter(SearchParameters.ATP_ID, atpId);
        query.setParameter(SearchParameters.PERSON_ID, personId);
        query.setParameter(SearchParameters.TYPE_KEY, typeKey);
        // For some reason, this only returns a list of strings (probably since only one item is being
        // queried for).
        @SuppressWarnings("unchecked")
        List<String> results = query.getResultList();

        SearchResultInfo resultInfo = new SearchResultInfo();
        for (String result : results) {
            SearchResultRowInfo row = new SearchResultRowInfo();
            row.addCell(SearchResultColumns.LPR_TRANS_ID, result);
            resultInfo.getRows().add(row);
        }

        return resultInfo;
    }

    /**
     * Returns list of Registration Info for the person: CO, AO, Schedules, etc.
     *
     * @throws OperationFailedException
     */
    private SearchResultInfo searchForAOSchedulesAndCOCreditAndGradingOptionsByIds(SearchRequestInfo searchRequestInfo) throws OperationFailedException {
        SearchResultInfo resultInfo = new SearchResultInfo();
        SearchRequestHelper requestHelper = new SearchRequestHelper(searchRequestInfo);
        List<String> luiIdsList = requestHelper.getParamAsList(SearchParameters.LUI_IDS);
        String luiIds = commaString(luiIdsList);

        String queryStr =
                "SELECT lui.ID, lui.NAME, luiId.LNG_NAME, luiRes.RESULT_VAL_GRP_ID, " +
                        "room.ROOM_CD, rBldg.BUILDING_CD, " +
                        "schedTmslt.WEEKDAYS, schedTmslt.START_TIME_MS, schedTmslt.END_TIME_MS " +
                        "FROM KSEN_LUI_IDENT luiId, KSEN_LUI lui " +
                        "LEFT OUTER JOIN KSEN_LUI_RESULT_VAL_GRP luiRes " +
                        "ON luiRes.LUI_ID = lui.ID " +
                        "AND (luiRes.RESULT_VAL_GRP_ID in (:rvgIds)" +
                        "       OR luiRes.RESULT_VAL_GRP_ID like '" + LrcServiceConstants.RESULT_GROUP_KEY_KUALI_CREDITTYPE_CREDIT_BASE + "%') " +
                        "LEFT OUTER JOIN KSEN_LUI_SCHEDULE aoSched " +
                        "ON aoSched.LUI_ID = lui.ID " +
                        "LEFT OUTER JOIN KSEN_SCHED_CMP schedCmp " +
                        "ON schedCmp.SCHED_ID = aoSched.SCHED_ID " +
                        "LEFT OUTER JOIN KSEN_ROOM room " +
                        "ON room.ID = schedCmp.ROOM_ID " +
                        "LEFT OUTER JOIN KSEN_ROOM_BUILDING rBldg " +
                        "ON rBldg.ID = room.BUILDING_ID " +
                        "LEFT OUTER JOIN KSEN_SCHED_CMP_TMSLOT schedCmpTmslt " +
                        "ON schedCmpTmslt.SCHED_CMP_ID = schedCmp.ID " +
                        "LEFT OUTER JOIN KSEN_SCHED_TMSLOT schedTmslt " +
                        "ON schedTmslt.ID = schedCmpTmslt.TM_SLOT_ID " +
                        "WHERE lui.ID IN (:luiIds) " +
                        "  AND luiId.LUI_ID = lui.ID";

        Query query = entityManager.createNativeQuery(queryStr);
        query.setParameter(SearchParameters.LUI_IDS, luiIds);
        query.setParameter(SearchParameters.RVG_IDS, Arrays.asList(CourseOfferingServiceConstants.ALL_STUDENT_REGISTRATION_OPTION_TYPE_KEYS));

        @SuppressWarnings("unchecked")
        List<Object[]> results = query.getResultList();

        for (Object[] resultRow : results) {
            int i = 0;
            SearchResultRowInfo row = new SearchResultRowInfo();
            row.addCell(SearchResultColumns.LUI_ID, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.LUI_NAME, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.LUI_LONG_NAME, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.RES_VAL_GROUP_KEY, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.ROOM_CODE, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.BUILDING_CODE, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.WEEKDAYS, (String) resultRow[i++]);
            BigDecimal startTimeMs = (BigDecimal) resultRow[i++];
            row.addCell(SearchResultColumns.START_TIME_MS, (startTimeMs == null) ? "" : startTimeMs.toString());
            BigDecimal endTimeMs = (BigDecimal) resultRow[i];
            row.addCell(SearchResultColumns.END_TIME_MS, (endTimeMs == null) ? "" : endTimeMs.toString());
            resultInfo.getRows().add(row);
        }

        return resultInfo;
    }

    private SearchResultInfo searchForAoIdsTypeAndMaxSeats(SearchRequestInfo searchRequestInfo)
            throws OperationFailedException {
        SearchResultInfo resultInfo = new SearchResultInfo();
        SearchRequestHelper requestHelper = new SearchRequestHelper(searchRequestInfo);
        List<String> aoIdsList = requestHelper.getParamAsList(SearchParameters.AO_IDS);
        String queryStr =
                "SELECT lui.ID, lui.LUI_TYPE, lui.MAX_SEATS " +
                        "FROM KSEN_LUI lui " +
                        "WHERE lui.ID IN (:activityOfferingIds) ";
        Query query = entityManager.createNativeQuery(queryStr);
        query.setParameter(SearchParameters.AO_IDS, aoIdsList);

        @SuppressWarnings("unchecked")
        List<Object[]> results = query.getResultList();

        for (Object[] resultRow : results) {
            int i = 0;
            SearchResultRowInfo row = new SearchResultRowInfo();
            row.addCell(SearchResultColumns.AO_ID, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.AO_TYPE, (String) resultRow[i++]);
            BigDecimal maxSeats = (BigDecimal) resultRow[i];
            if (maxSeats != null) {
                row.addCell(SearchResultColumns.AO_MAX_SEATS, String.valueOf(maxSeats.intValue()));
            } else {
                row.addCell(SearchResultColumns.AO_MAX_SEATS, null);
            }
            resultInfo.getRows().add(row);
        }

        return resultInfo;
    }

    private SearchResultInfo countValidAos(SearchRequestInfo searchRequestInfo)
            throws OperationFailedException {
        SearchResultInfo resultInfo = new SearchResultInfo();
        SearchRequestHelper requestHelper = new SearchRequestHelper(searchRequestInfo);
        List<String> aoIdsList = requestHelper.getParamAsList(SearchParameters.AO_IDS);
        // For now, hard code this list
        List<String> aoTypes = new ArrayList<>();
        aoTypes.add(LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY);
        aoTypes.add(LuiServiceConstants.LAB_ACTIVITY_OFFERING_TYPE_KEY);
        aoTypes.add(LuiServiceConstants.DISCUSSION_ACTIVITY_OFFERING_TYPE_KEY);

        String queryStr =
                "SELECT COUNT(lui.id) " +
                        "FROM KSEN_LUI lui " +
                        "WHERE lui.ID IN (:activityOfferingIds) " +
                        "AND lui.LUI_TYPE IN (:activityOfferingTypes)";
        Query query = entityManager.createNativeQuery(queryStr);
        query.setParameter(SearchParameters.AO_IDS, aoIdsList);
        query.setParameter(SearchParameters.AO_TYPES, aoTypes);

        @SuppressWarnings("unchecked")
        List<BigDecimal> countList = query.getResultList();
        BigDecimal countBig = KSCollectionUtils.getRequiredZeroElement(countList);
        int count = countBig.intValue();

        SearchResultRowInfo row = new SearchResultRowInfo();
        row.addCell(SearchResultColumns.AO_IDS_ACTUAL_COUNT, String.valueOf(count));
        row.addCell(SearchResultColumns.AO_IDS_EXPECTED_COUNT, String.valueOf(aoIdsList.size()));
        resultInfo.getRows().add(row);

        return resultInfo;
    }

    /**
     * Lets you search for AO-student LPRs based on a list of AO ids, and lpr states
     *
     * @param searchRequestInfo search request
     * @return Search results
     * @throws OperationFailedException
     */
    private SearchResultInfo searchForAoLprs(SearchRequestInfo searchRequestInfo)
            throws OperationFailedException {
        SearchResultInfo resultInfo = new SearchResultInfo();
        SearchRequestHelper requestHelper = new SearchRequestHelper(searchRequestInfo);
        List<String> aoIdsList = requestHelper.getParamAsList(SearchParameters.AO_IDS);
        List<String> lprStateList = requestHelper.getParamAsList(SearchParameters.LPR_STATES);
        String queryStr =
                "SELECT lpr.ID, lpr.LPR_TYPE, lpr.LPR_STATE, lpr.LUI_ID, lpr.PERS_ID " +
                        "FROM KSEN_LPR lpr " +
                        "WHERE lpr.LUI_ID IN (:activityOfferingIds) " +
                        "AND lpr.LPR_TYPE = '" + LprServiceConstants.REGISTRANT_AO_LPR_TYPE_KEY + "' ";
        boolean lprStateListIsNonEmpty = lprStateList != null && !lprStateList.isEmpty();
        if (lprStateListIsNonEmpty) {
            // If the list is empty or null, then pretend it doesn't exist, otherwise
            // add it to the query
            queryStr += "AND lpr.LPR_STATE IN (:lprStates)";
        }
        Query query = entityManager.createNativeQuery(queryStr);
        query.setParameter(SearchParameters.AO_IDS, aoIdsList);
        if (lprStateListIsNonEmpty) {
            query.setParameter(SearchParameters.LPR_STATES, lprStateList);
        }

        @SuppressWarnings("unchecked")
        List<Object[]> results = query.getResultList();

        for (Object[] resultRow : results) {
            int i = 0;
            SearchResultRowInfo row = new SearchResultRowInfo();
            row.addCell(SearchResultColumns.LPR_ID, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.LPR_TYPE, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.LPR_STATE, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.AO_ID, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.PERSON_ID, (String) resultRow[i]);
            resultInfo.getRows().add(row);
        }

        return resultInfo;
    }

    /**
     * Returns list of Registration Info for the person: CO, AO, Schedules, etc.
     *
     * @throws OperationFailedException
     */
    private SearchResultInfo searchForLprIdsByMasterLprId(SearchRequestInfo searchRequestInfo) throws OperationFailedException {
        SearchResultInfo resultInfo = new SearchResultInfo();
        SearchRequestHelper requestHelper = new SearchRequestHelper(searchRequestInfo);
        String masterLprId = requestHelper.getParamAsString(SearchParameters.MASTER_LPR_ID);
        String lprType = requestHelper.getParamAsString(SearchParameters.LPR_TYPE);
        List<String> lprStateList = requestHelper.getParamAsList(SearchParameters.LPR_STATES);

        String queryStr =
                "SELECT lpr.ID " +
                        "FROM KSEN_LPR lpr " +
                        "WHERE lpr.MASTER_LPR_ID = :masterLprId ";
        if (lprType != null) {
            queryStr += " AND lpr.LPR_TYPE = :lprType ";
        }
        if (lprStateList != null && !lprStateList.isEmpty()) {
            queryStr += " AND lpr.LPR_STATE IN (:lprStates) ";
        }
        Query query = entityManager.createNativeQuery(queryStr);
        query.setParameter(SearchParameters.MASTER_LPR_ID, masterLprId);
        if (lprType != null) {
            query.setParameter(SearchParameters.LPR_TYPE, lprType);
        }
        if (lprStateList != null && !lprStateList.isEmpty()) {
            query.setParameter(SearchParameters.LPR_STATES, lprStateList);
        }

        @SuppressWarnings("unchecked")
        List<String> results = query.getResultList();

        for (String resultRow : results) {
            SearchResultRowInfo row = new SearchResultRowInfo();
            row.addCell(SearchResultColumns.LPR_ID, resultRow);
            resultInfo.getRows().add(row);
        }

        return resultInfo;
    }

    private SearchResultInfo searchForCoAndAoInfoByCoId(SearchRequestInfo searchRequestInfo)
            throws MissingParameterException, OperationFailedException {
        SearchRequestHelper requestHelper = new SearchRequestHelper(searchRequestInfo);
        SearchResultInfo resultInfo = new SearchResultInfo();

        String queryStr =
                "SELECT co.ID coId, coId.LUI_CD coCode, coId.DIVISION coDivision, coId.LNG_NAME, co.CLU_ID," +
                        "co.DESCR_FORMATTED, coRes.RESULT_VAL_GRP_ID, " +
                        "coClId.LUI_ID coClId, coClId.LUI_CD coClCode, coClId.DIVISION coClDivision, " +
                        "co.ATP_ID coAtpId, ao.ATP_ID aoAtpId, " +
                        "ao.ID aoId, ao.LUI_TYPE, aoType.NAME, aoId.LUI_CD aoCode, ao.MAX_SEATS, " +
                        "(SELECT COUNT(*) FROM KSEN_LPR lpr " +
                        "  WHERE lpr.LUI_ID = ao.ID " +
                        "    AND lpr.LPR_TYPE = '" + LprServiceConstants.REGISTRANT_AO_LPR_TYPE_KEY + "' " +
                        "    AND lpr.LPR_STATE = '" + LprServiceConstants.ACTIVE_STATE_KEY + "') numRegisteredForAo, " +
                        "cwl.CWL_STATE as wlState, cwl.MAX_SIZE wlMaxSize, " +
                        "(SELECT COUNT(*) FROM KSEN_LPR lpr_wl " +
                        "  WHERE lpr_wl.LUI_ID = rg.ID " +
                        "    AND lpr_wl.LPR_TYPE = '" + LprServiceConstants.WAITLIST_RG_LPR_TYPE_KEY + "' " +
                        "    AND lpr_wl.LPR_STATE = '" + LprServiceConstants.ACTIVE_STATE_KEY + "') numWaitlistedForRG, " +
                        "rg.ID as rgId, rg.NAME as rgCode, " +
                        "schedCmp.TBA_IND, room.ROOM_CD, rBldg.BUILDING_CD, " +
                        "schedTmslt.WEEKDAYS, schedTmslt.START_TIME_MS, schedTmslt.END_TIME_MS, " +
                        "honorsCd.value as honorsFlag " +
                        "FROM KSEN_LUI co, KSEN_LUI_IDENT coId " +
                        // looking for grading and credit options for given CO
                        "LEFT OUTER JOIN KSEN_LUI_RESULT_VAL_GRP coRes " +
                        "ON coRes.LUI_ID = coId.LUI_ID " +
                        // getStudentRegGradingOptionsStr only includes Audit and Pass/Fail (as Letter is default), so want to add Letter to display
                        "AND (coRes.RESULT_VAL_GRP_ID in (:rvgIds)" +
                        "     OR coRes.RESULT_VAL_GRP_ID LIKE '" + LrcServiceConstants.RESULT_GROUP_KEY_KUALI_CREDITTYPE_CREDIT_BASE + "%') " +
                        // looking for cross-listed courses for given CO
                        "LEFT OUTER JOIN KSEN_LUI_IDENT coClId " +
                        "ON coClId.LUI_ID = coId.LUI_ID " +
                        "AND coClId.LUI_CD != coId.LUI_CD " +
                        "AND coClId.LUI_ID_TYPE in ('" + LuiServiceConstants.LUI_IDENTIFIER_CROSSLISTED_TYPE_KEY + "','" + LuiServiceConstants.LUI_IDENTIFIER_OFFICIAL_TYPE_KEY + "') " +
                        "AND coClId.LUI_ID_STATE = '" + LuiServiceConstants.LUI_IDENTIFIER_ACTIVE_STATE_KEY + "' " +
                        // finding all AOs for the given CO
                        // looking for FO for given CO
                        "LEFT OUTER JOIN KSEN_LUILUI_RELTN co2fo " +
                        "ON co2fo.LUI_ID = coId.LUI_ID " +
                        "AND co2fo.LUILUI_RELTN_TYPE = '" + LuiServiceConstants.LUI_LUI_RELATION_DELIVERED_VIA_CO_TO_FO_TYPE_KEY + "' " +
                        // looking for AOs for given FO (CO)
                        "LEFT OUTER JOIN KSEN_LUILUI_RELTN fo2ao " +
                        "ON fo2ao.LUI_ID = co2fo.RELATED_LUI_ID " +
                        "AND fo2ao.LUILUI_RELTN_TYPE = '" + LuiServiceConstants.LUI_LUI_RELATION_DELIVERED_VIA_FO_TO_AO_TYPE_KEY + "' " +
                        "LEFT OUTER JOIN KSEN_LUI ao " +
                        "ON ao.ID = fo2ao.RELATED_LUI_ID " +
                        "AND ao.LUI_STATE = '" + LuiServiceConstants.LUI_AO_STATE_OFFERED_KEY + "' " +
                        "LEFT OUTER JOIN KSEN_LUI_IDENT aoId " +
                        "ON aoId.LUI_ID = ao.ID " +
                        // looking up ao type for given AO
                        "LEFT OUTER JOIN KSEN_TYPE aoType " +
                        "ON ao.LUI_TYPE = aoType.TYPE_KEY " +
                        // looking for reg groups for given AO
                        "LEFT OUTER JOIN KSEN_LUILUI_RELTN rg2ao " +
                        "ON rg2ao.RELATED_LUI_ID = aoId.LUI_ID " +
                        "AND rg2ao.LUILUI_RELTN_TYPE = '" + LuiServiceConstants.LUI_LUI_RELATION_REGISTERED_FOR_VIA_RG_TO_AO_TYPE_KEY + "' " +
                        "LEFT OUTER JOIN KSEN_LUI rg " +
                        "ON rg.ID = rg2ao.LUI_ID " +
                        "AND rg.LUI_STATE = '" + LuiServiceConstants.REGISTRATION_GROUP_OFFERED_STATE_KEY + "' " +
                        // WL for AO
                        "LEFT OUTER JOIN KSEN_CWL_ACTIV_OFFER cwl2ao " +
                        "ON cwl2ao.ACTIV_OFFER_ID = ao.id " +
                        "LEFT OUTER JOIN KSEN_CWL cwl " +
                        "ON cwl.id = cwl2ao.CWL_ID " +
                        // Schedules for AOs
                        "LEFT OUTER JOIN KSEN_LUI_SCHEDULE aoSched " +
                        "ON aoSched.LUI_ID = ao.ID " +
                        "LEFT OUTER JOIN KSEN_SCHED_CMP schedCmp " +
                        "ON schedCmp.SCHED_ID = aoSched.SCHED_ID " +
                        "LEFT OUTER JOIN KSEN_ROOM room " +
                        "ON room.ID = schedCmp.ROOM_ID " +
                        "LEFT OUTER JOIN KSEN_ROOM_BUILDING rBldg " +
                        "ON rBldg.ID = room.BUILDING_ID " +
                        "LEFT OUTER JOIN KSEN_SCHED_CMP_TMSLOT schedCmpTmslt " +
                        "ON schedCmpTmslt.SCHED_CMP_ID = schedCmp.ID " +
                        "LEFT OUTER JOIN KSEN_SCHED_TMSLOT schedTmslt " +
                        "ON schedTmslt.ID = schedCmpTmslt.TM_SLOT_ID " +
                        // Honors
                        "left outer join KSEN_LUI_LU_CD honorsCd " +
                        "on honorsCd.lui_id = ao.id and honorsCd.lui_lucd_type = '" + LuiServiceConstants.HONORS_LU_CODE + "' " +
                        "WHERE coId.LUI_ID = co.ID " +
//                        "  AND coId.LUI_ID_TYPE = '" + LuiServiceConstants.LUI_IDENTIFIER_OFFICIAL_TYPE_KEY + "' " +
                        "  AND coId.LUI_ID_STATE = '" + LuiServiceConstants.LUI_IDENTIFIER_ACTIVE_STATE_KEY + "' " +
                        "  AND co.LUI_TYPE = '" + LuiServiceConstants.COURSE_OFFERING_TYPE_KEY + "' " +
                        "  AND co.ID = :courseOfferingId " +
                        "  AND coId.LUI_CD = :courseCode " +
                        " ORDER BY aoId.LUI_CD";

        Query query = getEntityManager().createNativeQuery(queryStr);
        query.setParameter(SearchParameters.CO_ID, requestHelper.getParamAsString(SearchParameters.CO_ID));
        query.setParameter(SearchParameters.COURSE_CODE, requestHelper.getParamAsString(SearchParameters.COURSE_CODE));
        query.setParameter(SearchParameters.RVG_IDS, getRvgIds());

        @SuppressWarnings("unchecked")
        List<Object[]> results = query.getResultList();

        for (Object[] resultRow : results) {
            int i = 0;
            SearchResultRowInfo row = new SearchResultRowInfo();
            row.addCell(SearchResultColumns.CO_ID, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.CO_CODE, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.CO_SUBJECT_AREA, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.CO_LONG_NAME, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.CO_CLU_ID, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.CO_DESC_FORMATTED, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.RES_VAL_GROUP_KEY, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.CO_CROSSLISTED_ID, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.CO_CROSSLISTED_CODE, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.CO_CROSSLISTED_SUBJECT_AREA, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.CO_ATP_ID, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.AO_ATP_ID, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.AO_ID, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.AO_TYPE, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.AO_NAME, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.AO_CODE, (String) resultRow[i++]);
            BigDecimal aoMaxSeats = (BigDecimal) resultRow[i++];
            if (aoMaxSeats != null) {
                row.addCell(SearchResultColumns.AO_MAX_SEATS, String.valueOf(aoMaxSeats.intValue()));
            } else {
                row.addCell(SearchResultColumns.AO_MAX_SEATS, null);
            }
            BigDecimal aoSeatCount = (BigDecimal) resultRow[i++];
            if (aoSeatCount != null) {
                row.addCell(SearchResultColumns.SEAT_COUNT, String.valueOf(aoSeatCount.intValue()));
            } else {
                row.addCell(SearchResultColumns.SEAT_COUNT, null);
            }
            row.addCell(SearchResultColumns.CWL_STATE, (String) resultRow[i++]);
            BigDecimal aoWlMaxSize = (BigDecimal) resultRow[i++];
            if (aoWlMaxSize != null) {
                row.addCell(SearchResultColumns.CWL_MAX_SIZE, String.valueOf(aoWlMaxSize.intValue()));
            } else {
                row.addCell(SearchResultColumns.CWL_MAX_SIZE, null);
            }
            BigDecimal aoWlCount = (BigDecimal) resultRow[i++];
            if (aoWlCount != null) {
                row.addCell(SearchResultColumns.RG_WAITLIST_COUNT, String.valueOf(aoWlCount.intValue()));
            } else {
                row.addCell(SearchResultColumns.RG_WAITLIST_COUNT, null);
            }
            row.addCell(SearchResultColumns.RG_ID, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.RG_CODE, (String) resultRow[i++]);
            BigDecimal tbaInd = (BigDecimal) resultRow[i++];
            row.addCell(SearchResultColumns.TBA_IND, (tbaInd == null) ? "" : tbaInd.toString());
            row.addCell(SearchResultColumns.ROOM_CODE, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.BUILDING_CODE, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.WEEKDAYS, (String) resultRow[i++]);
            BigDecimal startTimeMs = (BigDecimal) resultRow[i++];
            row.addCell(SearchResultColumns.START_TIME_MS, (startTimeMs == null) ? "" : startTimeMs.toString());
            BigDecimal endTimeMs = (BigDecimal) resultRow[i++];
            row.addCell(SearchResultColumns.END_TIME_MS, (endTimeMs == null) ? "" : endTimeMs.toString());
            row.addCell(SearchResultColumns.HONORS_FLAG, (String) resultRow[i]);

            resultInfo.getRows().add(row);
        }

        return resultInfo;
    }

    private List<String> getRvgIds() {
        List<String> rvgIds = new ArrayList<>();
        rvgIds.addAll(Arrays.asList(CourseOfferingServiceConstants.ALL_STUDENT_REGISTRATION_OPTION_TYPE_KEYS));
        rvgIds.addAll(Arrays.asList(CourseOfferingServiceConstants.ALL_GRADING_OPTION_TYPE_KEYS));
        return rvgIds;
    }

    private static String commaString(List<String> items) {
        return items.toString().replace("[", "'").replace("]", "'").replace(", ", "','");
    }


    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

}
