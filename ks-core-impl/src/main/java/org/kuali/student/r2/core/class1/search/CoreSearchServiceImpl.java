package org.kuali.student.r2.core.class1.search;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
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
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;
import org.kuali.student.r2.core.search.util.SearchRequestHelper;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: gtaylor
 * Date: 3/11/13
 * Time: 12:56 PM
 *
 * This class is to be used to call Core specific DB searches.
 *
 */
public class CoreSearchServiceImpl extends SearchServiceAbstractHardwiredImplBase {

    private static Logger LOG = Logger.getLogger(CoreSearchServiceImpl.class);

    public static final TypeInfo SCH_AND_ROOM_SEARH_BY_ID_SEARCH_TYPE;
    public static final TypeInfo SCH_RQST_TIMESLOT_BY_REF_ID_AND_TYPE_SEARCH_TYPE;
    public static final TypeInfo ACAL_GET_HOLIDAYS_BY_TERM_SEARCH_TYPE;
    public static final TypeInfo SCH_IDS_WITH_TBA_BY_SCH_IDS_SEARCH_TYPE;
    public static final TypeInfo SCH_REQ_REF_IDS_WITH_NON_TBA_BY_SCH_REQ_REF_IDS_SEARCH_TYPE;

    public static final String SCH_AND_ROOM_SEARH_BY_ID_SEARCH_KEY = "kuali.search.type.core.searchForScheduleAndRoomById";
    public static final String SCH_RQST_TIMESLOT_BY_REF_ID_AND_TYPE_SEARCH_KEY = "kuali.search.type.core.searchForScheduleRequestByRefIdAndType";
    public static final String ACAL_GET_HOLIDAYS_BY_TERM_SEARCH_KEY = "kuali.search.type.core.searchForHolidaysByTermId";
    public static final String SCH_IDS_WITH_NON_TBA_BY_SCH_IDS_SEARCH_KEY = "kuali.search.type.scheduling.searchForScheduleIdsWithNonTBAByScheduleIds";
    public static final String SCH_REQ_REF_IDS_WITH_NON_TBA_BY_SCH_REQ_REF_IDS_SEARCH_KEY = "kuali.search.type.scheduling.searchForScheduleReqRefIdsWithNonTBAByScheduleReqRefIds";

    public static final class SearchParameters {
        public static final String SCHEDULE_IDS = "scheduleIds";
        public static final String REF_IDS = "refIds";
        public static final String REF_TYPE = "refType";
        public static final String SCH_REQ_SET_TYPE = "schedReqSetType";
        public static final String TERM_ID = "termId";
        public static final String ROOM_IDS = "roomIds";
    }

    public static final class SearchResultColumns {
        public static final String AO_ID = "aoId";
        public static final String SCH_ID = "id";
        public static final String CMP_ID = "cmpId";
        public static final String WEEKDAYS = "weekdays";
        public static final String START_TIME = "startTimeMillis";
        public static final String END_TIME = "endTimeMillis";
        public static final String TIME_SLOT_STATE = "timeSlotState";
        public static final String ROOM_ID = "roomId";
        public static final String ROOM_CODE = "roomCode";
        public static final String BLDG_NAME = "name";
        public static final String BLDG_CODE = "bldgCode";
        public static final String TBA_IND = "tbaInd";
        public static final String REF_ID = "refId";
        public static final String MIN_TBA = "minTBA";

        public static final String MSTONE_ID = "id";
        public static final String MSTONE_NAME = "name";
        public static final String MSTONE_START_DT = "startDate";
        public static final String MSTONE_END_DT = "endDate";
        public static final String MSTONE_ALL_DAY = "isAllDay";
        public static final String MSTONE_INSTR_DAY = "isInstructionalDay";
        public static final String MSTONE_DT_RANGE = "isDateRange";
    }

    static {
        TypeInfo info = new TypeInfo();
        info.setKey(SCH_AND_ROOM_SEARH_BY_ID_SEARCH_KEY);
        info.setName("Schedule And Room Search");
        info.setDescr(new RichTextHelper().fromPlain("Return search results for both Schedules and Rooms for a particular AO"));

        try {
            info.setEffectiveDate(DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.parse("01/01/2012"));
        } catch ( IllegalArgumentException ex) {
            throw new RuntimeException("bad code", ex);
        }
        SCH_AND_ROOM_SEARH_BY_ID_SEARCH_TYPE = info;

        info = new TypeInfo();
        info.setKey(SCH_RQST_TIMESLOT_BY_REF_ID_AND_TYPE_SEARCH_KEY);
        info.setName("Activity Offerings for CO Search");
        info.setDescr(new RichTextHelper().fromPlain("Return search results for Activity Offerings by CO ID"));

        try {
            info.setEffectiveDate(DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.parse("01/01/2012"));
        } catch ( IllegalArgumentException ex) {
            throw new RuntimeException("bad code", ex);
        }
        SCH_RQST_TIMESLOT_BY_REF_ID_AND_TYPE_SEARCH_TYPE = info;

        info = new TypeInfo();
        info.setKey(ACAL_GET_HOLIDAYS_BY_TERM_SEARCH_KEY);
        info.setName("Academic Calendar Search for Holidays by Term id");
        info.setDescr(new RichTextHelper().fromPlain("Return a list of holiday milestones for a particular term"));

        try {
            info.setEffectiveDate(DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.parse("01/01/2012"));
        } catch ( IllegalArgumentException ex) {
            throw new RuntimeException("bad code", ex);
        }
        ACAL_GET_HOLIDAYS_BY_TERM_SEARCH_TYPE = info;

        info = new TypeInfo();
        info.setKey(SCH_IDS_WITH_NON_TBA_BY_SCH_IDS_SEARCH_KEY);
        info.setName("Schedules with TBA Search");
        info.setDescr(new RichTextHelper().fromPlain("Return Schedule Ids that have schedule components with TBA"));
        try {
            info.setEffectiveDate(DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.parse("01/01/2012"));
        } catch ( IllegalArgumentException ex) {
            throw new RuntimeException("bad code", ex);
        }
        SCH_IDS_WITH_TBA_BY_SCH_IDS_SEARCH_TYPE = info;

        info = new TypeInfo();
        info.setKey(SCH_REQ_REF_IDS_WITH_NON_TBA_BY_SCH_REQ_REF_IDS_SEARCH_KEY);
        info.setName("Schedule Request Reference Ids with TBA Search");
        info.setDescr(new RichTextHelper().fromPlain("Return Schedule Request Reference Ids that have components with TBA"));
        try {
            info.setEffectiveDate(DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.parse("01/01/2012"));
        } catch ( IllegalArgumentException ex) {
            throw new RuntimeException("bad code", ex);
        }
        SCH_REQ_REF_IDS_WITH_NON_TBA_BY_SCH_REQ_REF_IDS_SEARCH_TYPE = info;
    }


    @Override
    public TypeInfo getSearchType() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public TypeInfo getSearchType(String searchTypeKey, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException {
        if (SCH_AND_ROOM_SEARH_BY_ID_SEARCH_TYPE.getKey().equals(searchTypeKey)) {
            return SCH_AND_ROOM_SEARH_BY_ID_SEARCH_TYPE;
        }
        if (SCH_RQST_TIMESLOT_BY_REF_ID_AND_TYPE_SEARCH_TYPE.getKey().equals(searchTypeKey)) {
            return SCH_RQST_TIMESLOT_BY_REF_ID_AND_TYPE_SEARCH_TYPE;
        }
        if (ACAL_GET_HOLIDAYS_BY_TERM_SEARCH_TYPE.getKey().equals(searchTypeKey)) {
            return ACAL_GET_HOLIDAYS_BY_TERM_SEARCH_TYPE;
        }
        if (SCH_IDS_WITH_TBA_BY_SCH_IDS_SEARCH_TYPE.getKey().equals(searchTypeKey)) {
            return SCH_IDS_WITH_TBA_BY_SCH_IDS_SEARCH_TYPE;
        }
        if (SCH_REQ_REF_IDS_WITH_NON_TBA_BY_SCH_REQ_REF_IDS_SEARCH_TYPE.getKey().equals(searchTypeKey)) {
            return SCH_REQ_REF_IDS_WITH_NON_TBA_BY_SCH_REQ_REF_IDS_SEARCH_TYPE;
        }
        throw new DoesNotExistException("No Search Type Found for key:"+searchTypeKey);
    }

    @Override
    public List<TypeInfo> getSearchTypes(ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException {
        return Arrays.asList(SCH_AND_ROOM_SEARH_BY_ID_SEARCH_TYPE, SCH_RQST_TIMESLOT_BY_REF_ID_AND_TYPE_SEARCH_TYPE, ACAL_GET_HOLIDAYS_BY_TERM_SEARCH_TYPE, SCH_IDS_WITH_TBA_BY_SCH_IDS_SEARCH_TYPE, SCH_REQ_REF_IDS_WITH_NON_TBA_BY_SCH_REQ_REF_IDS_SEARCH_TYPE);
    }


    @Override
    @Transactional(readOnly = true)
    public SearchResultInfo search(SearchRequestInfo searchRequestInfo, ContextInfo contextInfo) throws MissingParameterException, OperationFailedException, PermissionDeniedException {

        // As this class expands, you can add multiple searches. Ie. right now there is only one search (so only one search key).
        if (StringUtils.equals(searchRequestInfo.getSearchKey(), SCH_AND_ROOM_SEARH_BY_ID_SEARCH_TYPE.getKey())) {
            return searchForScheduleAndRoomById(searchRequestInfo, contextInfo);
        } else if (StringUtils.equals(searchRequestInfo.getSearchKey(), SCH_RQST_TIMESLOT_BY_REF_ID_AND_TYPE_SEARCH_TYPE.getKey())) {
            return searchForScheduleRequestsByRefIdAndType(searchRequestInfo, contextInfo);
        } else if (StringUtils.equals(searchRequestInfo.getSearchKey(), ACAL_GET_HOLIDAYS_BY_TERM_SEARCH_TYPE.getKey())) {
            return searchForHolidaysByTermId(searchRequestInfo, contextInfo);
        } else if (SCH_IDS_WITH_NON_TBA_BY_SCH_IDS_SEARCH_KEY.equals(searchRequestInfo.getSearchKey())) {
            return searchForScheduleIdsWithTBAByScheduleIds(searchRequestInfo);
        } else if (SCH_REQ_REF_IDS_WITH_NON_TBA_BY_SCH_REQ_REF_IDS_SEARCH_KEY.equals(searchRequestInfo.getSearchKey())) {
            return searchForScheduleReqRefIdsWithNonTBAByScheduleReqRefIds(searchRequestInfo);
        } else{
            throw new OperationFailedException("Unsupported search type: " + searchRequestInfo.getSearchKey());
        }
    }

    /**
     * Searches for schedule requests ids for reference objects to find if at least one of the scheduled requests is not TBA
     * @param searchRequestInfo a list of ref ids (and types)
     * @return list of ref object ids and the minumum TBA indicator on the request components for each ref object
     */
    private SearchResultInfo searchForScheduleReqRefIdsWithNonTBAByScheduleReqRefIds(SearchRequestInfo searchRequestInfo) {
        SearchResultInfo resultInfo = new SearchResultInfo();

        SearchRequestHelper requestHelper = new SearchRequestHelper(searchRequestInfo);
        List<String> refIds = requestHelper.getParamAsList(SearchParameters.REF_IDS);
        List<String> refType = requestHelper.getParamAsList(SearchParameters.REF_TYPE);
        List<String> schedReqSetType = requestHelper.getParamAsList(SearchParameters.SCH_REQ_SET_TYPE);

        String queryStr =
                "SELECT roIds, MIN(src.isTBA)" +
                        "  FROM ScheduleRequestSetEntity srs, IN(srs.refObjectIds) roIds, ScheduleRequestComponentEntity src" +
                        " WHERE roIds IN (:refIds)" +
                        "   AND src.scheduleRequest.scheduleRequestSetId = srs.id" +
                        "   AND srs.schedReqSetType = :schedReqSetType" +
                        "   AND srs.refObjectTypeKey = :refType" +
                        " GROUP BY roIds";

        Query query = entityManager.createQuery(queryStr);
        query.setParameter(SearchParameters.REF_IDS, refIds);
        query.setParameter(SearchParameters.REF_TYPE, refType);
        query.setParameter(SearchParameters.SCH_REQ_SET_TYPE, schedReqSetType);

        List<Object[]> results = query.getResultList();

        for (Object[] result : results) {
            SearchResultRowInfo row = new SearchResultRowInfo();
            row.addCell(SearchResultColumns.REF_ID, result[0].toString());
            row.addCell(SearchResultColumns.MIN_TBA, result[1].toString());
            resultInfo.getRows().add(row);
        }

        return resultInfo;
    }

    /**
     * Searches for scheduling info given a list of schedule Ids.
     *
     * Because schedules derived from TBA requests can be missing one or more fields an outer join
     * to the building/room tables is needed to insure that all of the data is picked up. However, since
     * the schedule component and building/room entities don't have JPA mappings the best way (I found) to
     * accomplish the outer join is to make two queries and join manually. The performance difference between
     * the old, broken query and the two query approach was only around 10ms for 201208/ENGL101.
     *
     * @param searchRequestInfo Provides a list of schedule Ids.
     * @param contextInfo context of the call
     * @return scheduling info
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     */
    protected SearchResultInfo searchForScheduleAndRoomById(SearchRequestInfo searchRequestInfo, ContextInfo contextInfo)
            throws MissingParameterException, OperationFailedException, PermissionDeniedException {
        SearchRequestHelper requestHelper = new SearchRequestHelper(searchRequestInfo);

        List<String> schIds = requestHelper.getParamAsList(SearchParameters.SCHEDULE_IDS);

        if (schIds == null || schIds.isEmpty()){
            throw new RuntimeException("Schedule Ids are required");
        }

        //   First get the scheduleComponent, room id, and timeslot info.
        String scheduleQuery =
                "SELECT " +
                    "sch.id," +
                    "cmp.roomId," +
                    "tmslot.weekdays," +
                    "tmslot.startTimeMillis," +
                    "tmslot.endTimeMillis," +
                    "tmslot.timeSlotState," +
                    "cmp.isTBA " +
                "FROM " +
                    "ScheduleEntity sch JOIN sch.scheduleComponents cmp, " +
                    "TimeSlotEntity tmslot, IN ( cmp.timeSlotIds ) cmp_tmslot " +
                "WHERE " +
                    "tmslot.id = cmp_tmslot " +
                    "AND sch.id IN (:scheduleIds)";

        Query schedQuery = entityManager.createQuery(scheduleQuery);
        schedQuery.setParameter(SearchParameters.SCHEDULE_IDS, schIds);

        List<Object[]> scheduleResults = schedQuery.getResultList();

        //  Get the room Ids from the schedule query.
        Set<String> roomIds = new HashSet<String>();
        for (Object[] result : scheduleResults) {
            int roomIdOffset = 1;
            String roomId = (String) result[roomIdOffset];
            roomIds.add(roomId != null ? roomId : "");
        }

        //  Use the roomIds to query for building/room info
        String facilityQueryText =
            "SELECT " +
                "room.id," +
                "room.roomCode," +
                "bldg.buildingCode," +
                "bldg.name " +
            "FROM " +
                "RoomEntity room," +
                "RoomBuildingEntity bldg " +
            "WHERE " +
                "room.buildingId = bldg.id " +
                "AND room.id IN (:roomIds)";

        Query facilityQuery = entityManager.createQuery(facilityQueryText);
        facilityQuery.setParameter(SearchParameters.ROOM_IDS, roomIds);

        List<Object[]> facilityResults = facilityQuery.getResultList();

        Map<String, Map<String, String>> rooms = new HashMap<String, Map<String, String>>(facilityResults.size());
        for (Object[] result : facilityResults) {
            Map<String, String> rowMap = new HashMap<String, String>(4);
            int i = 0;
            String roomId = (String) result[i++];
            rowMap.put(SearchResultColumns.ROOM_ID, roomId);
            rowMap.put(SearchResultColumns.ROOM_CODE, (String) result[i++]);
            rowMap.put(SearchResultColumns.BLDG_CODE, (String) result[i++]);
            rowMap.put(SearchResultColumns.BLDG_NAME, (String) result[i++]);
            rooms.put(roomId, rowMap);
        }

        //  Set up the response.
        SearchResultInfo resultInfo = new SearchResultInfo();
        resultInfo.setTotalResults(scheduleResults.size());
        resultInfo.setStartAt(0);

        //  Join the results.
        for (Object[] result : scheduleResults) {
            SearchResultRowInfo row = new SearchResultRowInfo();
            int i = 0;
            row.addCell(SearchResultColumns.SCH_ID, (String) result[i++]);
            String roomId = (String) result[i++];
            row.addCell(SearchResultColumns.ROOM_ID, roomId != null ? roomId : "");
            row.addCell(SearchResultColumns.WEEKDAYS, (String) result[i++]);
            Long startTime = (Long)result[i++];  // So, the underlying value is a long. we need to convert that to a string w/o NPE
            row.addCell(SearchResultColumns.START_TIME, (startTime != null ? startTime.toString() : ""));
            Long endTime = (Long)result[i++];
            row.addCell(SearchResultColumns.END_TIME, (endTime != null ? endTime.toString() : ""));
            row.addCell(SearchResultColumns.TIME_SLOT_STATE, (String) result[i++]);
            row.addCell(SearchResultColumns.TBA_IND, result[i++].toString());

            //  Add the building/room info.
            if (StringUtils.isNotBlank(roomId)) {
                Map<String, String> roomMap = rooms.get(roomId);
                row.addCell(SearchResultColumns.ROOM_CODE, roomMap.get(SearchResultColumns.ROOM_CODE));
                row.addCell(SearchResultColumns.BLDG_CODE, roomMap.get(SearchResultColumns.BLDG_CODE));
                row.addCell(SearchResultColumns.BLDG_NAME, roomMap.get(SearchResultColumns.BLDG_NAME));
            } else {
                row.addCell(SearchResultColumns.ROOM_CODE, "");
                row.addCell(SearchResultColumns.BLDG_CODE, "");
                row.addCell(SearchResultColumns.BLDG_NAME, "");
            }
            resultInfo.getRows().add(row);
        }

        return resultInfo;
    }

    /**
     *
     * @param searchRequestInfo Contains an Activity Offering ID that we will use to find the scheduleId
     * @param contextInfo context of the call
     * @return schedule requests
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     */
    protected SearchResultInfo searchForScheduleRequestsByRefIdAndType(SearchRequestInfo searchRequestInfo, ContextInfo contextInfo) throws MissingParameterException, OperationFailedException, PermissionDeniedException {
        SearchRequestHelper requestHelper = new SearchRequestHelper(searchRequestInfo);

        List<String> refIds = requestHelper.getParamAsList(SearchParameters.REF_IDS);
        String refType = requestHelper.getParamAsString(SearchParameters.REF_TYPE);

        if (refIds == null || refIds.isEmpty()){
            throw new RuntimeException("Reference Ids are required");
        }

        if (refType == null || refType.isEmpty()){
            throw new RuntimeException("Reference Type is required");
        }

        String refIdsStr = commaString(refIds);

        String queryStr =
                " Select "   +
                        "  schReq.refObjectId as aoId, " +
                        "  cmp.id as cmpId, " +
                        "  tmslot.weekdays, " +
                        "  tmslot.startTimeMillis, " +
                        "  tmslot.endTimeMillis, " +
                        "  tmslot.timeSlotState, " +
                        "  room.roomCode, " +
                        "  bldg.name, " +
                        "  cmp.isTBA " +
                        " FROM " +
                        "    ScheduleRequestEntity schReq, " +
                        "    IN(schReq.scheduleRequestComponents) cmp, " +
                        "    IN ( cmp.timeSlotIds ) cmp_tmslot, " +
                        "    IN ( cmp.roomIds ) cmp_room, " +
                        "    IN ( cmp.buildingIds ) cmp_bldg, " +
                        "    TimeSlotEntity tmslot, " +
                        "    RoomEntity room, " +
                        "    RoomBuildingEntity bldg  " +
                        " WHERE " +
                        "     schReq.refObjectTypeKey = :refType " +
                        " AND schReq.refObjectId in ("+ refIdsStr +") " +
                        " AND (tmslot.id = cmp_tmslot or tmslot.id is NULL) " +
                        " AND (room.id = cmp_room or room.id is NULL) " +
                        " AND (bldg.id = cmp_bldg or bldg.id is null) ";

        Query query = entityManager.createQuery(queryStr);
        query.setParameter(SearchParameters.REF_TYPE, refType);

        List<Object[]> results = query.getResultList();

        SearchResultInfo resultInfo = new SearchResultInfo();
        resultInfo.setTotalResults(results.size());
        resultInfo.setStartAt(0);

        for (Object[] result : results) {
            SearchResultRowInfo row = new SearchResultRowInfo();

            int i=0;

            row.addCell(SearchResultColumns.AO_ID,(String)result[i++]);
            row.addCell(SearchResultColumns.CMP_ID,(String)result[i++]);
            row.addCell(SearchResultColumns.WEEKDAYS,(String)result[i++]);

            Long startTime = (Long)result[i++];  // So, the underlying value is a long. we need to convert that to a string w/o NPE
            row.addCell(SearchResultColumns.START_TIME,(startTime != null ? startTime.toString(): ""));

            Long endTime = (Long)result[i++];
            row.addCell(SearchResultColumns.END_TIME,(endTime != null ? endTime.toString(): ""));

            row.addCell(SearchResultColumns.TIME_SLOT_STATE,(String)result[i++]);
            row.addCell(SearchResultColumns.ROOM_CODE,(String)result[i++]);
            row.addCell(SearchResultColumns.BLDG_NAME,(String)result[i++]);
            row.addCell(SearchResultColumns.TBA_IND,result[i++].toString());
            resultInfo.getRows().add(row);
        }

        return resultInfo;

    }


    /**
     * This method will do a direct DB query to find holidays associated with a term. Terms hang off acals. Holidays
     * hang off acals, but there is no direct link. So the query goes: term->Acal->hcal->holidays (milestones)
     *
     * @param searchRequestInfo this should contain the termId (atpId) of the term that you want to find corresponding
     *                          holidays.
     * @param contextInfo context of the call
     * @return holidays associated with a term
     * @throws OperationFailedException 
     */
    protected SearchResultInfo searchForHolidaysByTermId(SearchRequestInfo searchRequestInfo, ContextInfo contextInfo) throws OperationFailedException{
        SearchRequestHelper requestHelper = new SearchRequestHelper(searchRequestInfo);

        String termId = requestHelper.getParamAsString(SearchParameters.TERM_ID);

        if (termId == null || termId.isEmpty()){
            throw new RuntimeException("termId is required");
        }

        String queryStr =
                "SELECT " +
                        "    mstone.id, " +
                        "    mstone.name, " +
                        "    mstone.startDate, " +
                        "    mstone.endDate, " +
                        "    mstone.isAllDay, " +
                        "    mstone.isInstructionalDay, " +
                        "    mstone.isDateRange " +
                        "FROM " +
                        "    AtpAtpRelationEntity relTerm, " +
                        "    AtpAtpRelationEntity relHol, " +
                        "    AtpEntity atpHol, " +
                        "    AtpMilestoneRelationEntity mstoneRel, " +
                        "    MilestoneEntity mstone " +
                        "WHERE " +
                        "    relTerm.atpType = :relTermAtpType " +
                        "AND relTerm.atpState = :relTermAtpState " +
                        "AND relTerm.atp.atpType = :relTermAtpAtpType " +
                        "AND relHol.atp.id = relTerm.atp.id " +
                        "AND atpHol.id = relHol.relatedAtp.id " +
                        "AND atpHol.atpType = :atpHolAtpType " +
                        "AND mstoneRel.atpId = atpHol.id " +
                        "AND mstone.id = mstoneRel.milestoneId " +
                        "AND relTerm.relatedAtp.id = :termId ";

        Query query = entityManager.createQuery(queryStr);
        query.setParameter("relTermAtpType", AtpServiceConstants.ATP_ATP_RELATION_INCLUDES_TYPE_KEY);
        query.setParameter("relTermAtpState", AtpServiceConstants.ATP_ATP_RELATION_ACTIVE_STATE_KEY);
        query.setParameter("relTermAtpAtpType", AtpServiceConstants.ATP_ACADEMIC_CALENDAR_TYPE_KEY);
        query.setParameter("atpHolAtpType", AtpServiceConstants.ATP_HOLIDAY_CALENDAR_TYPE_KEY);
        query.setParameter(SearchParameters.TERM_ID, termId);

        List<Object[]> results = query.getResultList();

        SearchResultInfo resultInfo = new SearchResultInfo();
        resultInfo.setTotalResults(results.size());
        resultInfo.setStartAt(0);

        for (Object[] result : results) {
            SearchResultRowInfo row = new SearchResultRowInfo();

            int i=0;

            row.addCell(SearchResultColumns.MSTONE_ID,(String)result[i++]);
            row.addCell(SearchResultColumns.MSTONE_NAME,(String)result[i++]);
            row.addCell(SearchResultColumns.MSTONE_START_DT,((result[i] != null ? DateFormatters.DEFAULT_TIMESTAMP_FORMATTER.format((java.util.Date)result[i]) : "")));
            i++;
            row.addCell(SearchResultColumns.MSTONE_END_DT,((result[i] != null ? DateFormatters.DEFAULT_TIMESTAMP_FORMATTER.format((java.util.Date)result[i]) : "")));
            i++;
            row.addCell(SearchResultColumns.MSTONE_ALL_DAY,(result[i] != null ? (result[i]).toString() : "false"));
            i++;
            row.addCell(SearchResultColumns.MSTONE_INSTR_DAY,(result[i] != null ? (result[i]).toString() : "false"));
            i++;
            row.addCell(SearchResultColumns.MSTONE_DT_RANGE,(result[i] != null ? (result[i]).toString() : "false"));

            resultInfo.getRows().add(row);
        }

        return resultInfo;
    }

    /**
     * Method is used to determine AO state. A list of schedule ids is passed in and only those with a component that is not TBA
     * are returned.
     * @param searchRequestInfo a list of schedule ids
     * @return list of the passed in ids that have a tba indicator set to true
     */
    private SearchResultInfo searchForScheduleIdsWithTBAByScheduleIds(SearchRequestInfo searchRequestInfo) {
        SearchResultInfo resultInfo = new SearchResultInfo();

        SearchRequestHelper requestHelper = new SearchRequestHelper(searchRequestInfo);
        List<String> scheduleIds = requestHelper.getParamAsList(SearchParameters.SCHEDULE_IDS);

        String queryStr =
                "SELECT DISTINCT se.id" +
                        "  FROM ScheduleEntity se, IN(se.scheduleComponents) sc" +
                        " WHERE sc.isTBA = FALSE" +
                        "   AND se.id IN (:scheduleIds)";

        Query query = entityManager.createQuery(queryStr);
        query.setParameter(SearchParameters.SCHEDULE_IDS, scheduleIds);
        List<String> results = query.getResultList();

        for (String id : results) {
            SearchResultRowInfo row = new SearchResultRowInfo();
            row.addCell(SearchResultColumns.SCH_ID, id);
            resultInfo.getRows().add(row);
        }

        return resultInfo;
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

}
