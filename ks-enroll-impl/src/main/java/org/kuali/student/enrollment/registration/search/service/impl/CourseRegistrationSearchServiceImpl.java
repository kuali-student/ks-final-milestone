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

import org.apache.commons.lang.StringUtils;
import org.kuali.student.r2.common.class1.search.SearchServiceAbstractHardwiredImplBase;
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
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * This class is used to do custom searches for Course Registration
 *
 * @author Kuali Student Team
 */
public class CourseRegistrationSearchServiceImpl extends SearchServiceAbstractHardwiredImplBase {

    @Resource
    private EntityManager entityManager;

    public static final String REG_INFO_BY_PERSON_TERM_SEARCH_KEY = "kuali.search.type.lui.searchForCourseRegistrationByStudentAndTerm";
    public static final String AO_SCHEDULES_BY_AO_IDS_SEARCH_KEY = "kuali.search.type.lui.searchForActivityOfferingSchedulesByAOIds";

    public static final TypeInfo REG_INFO_BY_PERSON_TERM_SEARCH_TYPE;
    public static final TypeInfo AO_SCHEDULES_BY_AO_IDS_SEARCH_TYPE;

    public static final String DEFAULT_EFFECTIVE_DATE = "01/01/2012";

    public static final class SearchParameters {
        public static final String AO_ID = "activityOfferingId";
        public static final String AO_IDS = "activityOfferingIds";
        public static final String CO_ID = "courseOfferingId";
        public static final String RG_ID = "regGroupId";
        public static final String PERSON_ID = "personId";
        public static final String ATP_ID = "atpId";
    }

    public static final class SearchResultColumns {
        public static final String LUI_ID = "luiId";
        public static final String MASTER_LUI_ID = "masterLuiId";
        public static final String PERSON_LUI_TYPE = "personLuiType";
        public static final String LUI_NAME = "luiName";
        public static final String LUI_LONG_NAME = "luiLongName";
        public static final String LUI_CODE = "luiCode";
        public static final String LUI_TYPE = "luiType";
        public static final String LUI_DESC = "luiDesc";
        public static final String CREDITS = "credits";
        public static final String ROOM_CODE = "roomCode";
        public static final String BUILDING_CODE = "buildingCode";
        public static final String WEEKDAYS = "weekdays";
        public static final String START_TIME_MS = "startTimeMs";
        public static final String END_TIME_MS = "endTimeMs";
        public static final String ATP_ID = "atpId";
        public static final String ATP_CD = "atpCd";
        public static final String ATP_NAME = "atpName";
    }

    static {
        TypeInfo info = new TypeInfo();
        info.setKey(REG_INFO_BY_PERSON_TERM_SEARCH_KEY);
        info.setName("Registraion info by person and term");
        info.setDescr(new RichTextHelper().fromPlain("Returns registraion info for given person and term"));
        info.setEffectiveDate(DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.parse(DEFAULT_EFFECTIVE_DATE));

        REG_INFO_BY_PERSON_TERM_SEARCH_TYPE = info;

        info = new TypeInfo();
        info.setKey(AO_SCHEDULES_BY_AO_IDS_SEARCH_KEY);
        info.setName("AO schedules by AO ids");
        info.setDescr(new RichTextHelper().fromPlain("Returns AO schedules for given aoID"));
        info.setEffectiveDate(DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.parse(DEFAULT_EFFECTIVE_DATE));

        AO_SCHEDULES_BY_AO_IDS_SEARCH_TYPE = info;
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
        if (REG_INFO_BY_PERSON_TERM_SEARCH_KEY.equals(searchTypeKey)) {
            return REG_INFO_BY_PERSON_TERM_SEARCH_TYPE;
        } else if (AO_SCHEDULES_BY_AO_IDS_SEARCH_KEY.equals(searchTypeKey)) {
            return AO_SCHEDULES_BY_AO_IDS_SEARCH_TYPE;
        }

        throw new DoesNotExistException("No Search Type Found for key: " + searchTypeKey);
    }

    @Override
    public List<TypeInfo> getSearchTypes(ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException {
        return Arrays.asList(REG_INFO_BY_PERSON_TERM_SEARCH_TYPE, AO_SCHEDULES_BY_AO_IDS_SEARCH_TYPE);
    }

    @Override
    @Transactional(readOnly = true)
    public SearchResultInfo search(SearchRequestInfo searchRequestInfo, ContextInfo contextInfo) throws MissingParameterException, OperationFailedException, PermissionDeniedException {

        if (REG_INFO_BY_PERSON_TERM_SEARCH_TYPE.getKey().equals(searchRequestInfo.getSearchKey())) {
            return searchForCourseRegistrationByPersonAndTerm(searchRequestInfo);
        } else if (AO_SCHEDULES_BY_AO_IDS_SEARCH_TYPE.getKey().equals(searchRequestInfo.getSearchKey())) {
            return searchForActivityOfferingSchedulesByAoIds(searchRequestInfo);
        } else {
            throw new OperationFailedException("Unsupported search type: " + searchRequestInfo.getSearchKey());
        }
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

        String queryStr =
                "SELECT atp.ID, atp.ATP_CD, atp.NAME as atp_name, lpr.LUI_ID, lpr.MASTER_LUI_ID, lpr.LPR_TYPE, lpr.CREDITS, " +
                        "luiId.LUI_CD, lui.NAME as lui_name, lui.DESCR_FORMATTED, lui.LUI_TYPE, luiId.LNG_NAME, " +
                        "room.ROOM_CD, rBldg.BUILDING_CD, " +
                        "schedTmslt.WEEKDAYS, schedTmslt.START_TIME_MS, schedTmslt.END_TIME_MS " +
                        "FROM KSEN_ATP atp, " +
                        "     KSEN_LPR lpr, " +
                        "     KSEN_LUI lui, " +
                        "     KSEN_LUI_IDENT luiId " +
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
                        "  AND lui.ATP_ID = lpr.ATP_ID " +
                        "  AND luiId.LUI_ID = lui.ID ";

        if (!StringUtils.isEmpty(atpId)) {
            queryStr = queryStr + " AND lpr.ATP_ID = :atpId ";
        }

        Query query = entityManager.createNativeQuery(queryStr);
        query.setParameter(SearchParameters.PERSON_ID, personId);
        if (!StringUtils.isEmpty(atpId)) {
            query.setParameter(SearchParameters.ATP_ID, atpId);
        }
        List<Object[]> results = query.getResultList();

        for (Object[] resultRow : results) {
            int i = 0;
            SearchResultRowInfo row = new SearchResultRowInfo();
            row.addCell(SearchResultColumns.ATP_ID, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.ATP_CD, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.ATP_NAME, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.LUI_ID, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.MASTER_LUI_ID, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.PERSON_LUI_TYPE, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.CREDITS, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.LUI_CODE, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.LUI_NAME, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.LUI_DESC, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.LUI_TYPE, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.LUI_LONG_NAME, (String) resultRow[i++]);
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
     * Returns list of Registration Info for the person: CO, AO, Schedules, etc.
     *
     * @throws OperationFailedException
     */
    private SearchResultInfo searchForActivityOfferingSchedulesByAoIds(SearchRequestInfo searchRequestInfo) throws OperationFailedException {
        SearchResultInfo resultInfo = new SearchResultInfo();
        SearchRequestHelper requestHelper = new SearchRequestHelper(searchRequestInfo);
        List<String> aoIdsList = requestHelper.getParamAsList(SearchParameters.AO_IDS);
        String aoIds = commaString(aoIdsList);

        String queryStr =
                "SELECT lui.ID, lui.NAME, room.ROOM_CD, rBldg.BUILDING_CD, " +
                        "schedTmslt.WEEKDAYS, schedTmslt.START_TIME_MS, schedTmslt.END_TIME_MS " +
                        "FROM KSEN_LUI lui " +
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
                        "WHERE lui.ID IN (:aoIds)";

        Query query = entityManager.createNativeQuery(queryStr);
        query.setParameter(SearchParameters.AO_IDS, aoIds);
        List<Object[]> results = query.getResultList();

        for (Object[] resultRow : results) {
            int i = 0;
            SearchResultRowInfo row = new SearchResultRowInfo();
            row.addCell(SearchResultColumns.LUI_ID, (String) resultRow[i++]);
            row.addCell(SearchResultColumns.LUI_NAME, (String) resultRow[i++]);
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

    private static String commaString(List<String> items){
        return items.toString().replace("[", "'").replace("]", "'").replace(", ", "','");
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

}
