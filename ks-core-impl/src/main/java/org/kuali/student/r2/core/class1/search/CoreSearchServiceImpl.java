package org.kuali.student.r2.core.class1.search;

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

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Arrays;
import java.util.List;

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

    @Resource
    private EntityManager entityManager;

    public static final TypeInfo SCH_AND_ROOM_SEARH_BY_ID_SEARCH_TYPE;
    public static final TypeInfo SCH_RQST_TIMESLOT_BY_REF_ID_AND_TYPE_SEARCH_TYPE;

    public static final String SCH_AND_ROOM_SEARH_BY_ID_SEARCH_KEY = "kuali.search.type.core.searchForScheduleAndRoomById";
    public static final String SCH_RQST_TIMESLOT_BY_REF_ID_AND_TYPE_SEARCH_KEY = "kuali.search.type.core.searchForScheduleRequestByRefIdAndType";

    public static final class SearchParameters {
        public static final String SCHEDULE_IDS = "scheduleIds";
        public static final String REF_IDS = "refIds";
        public static final String REF_TYPE = "refType";
    }

    public static final class SearchResultColumns {
        public static final String AO_ID = "aoId";
        public static final String SCH_ID = "id";
        public static final String CMP_ID = "cmpId";
        public static final String WEEKDAYS = "weekdays";
        public static final String START_TIME = "startTimeMillis";
        public static final String END_TIME = "endTimeMillis";
        public static final String TIME_SLOT_STATE = "timeSlotState";
        public static final String ROOM_CODE = "roomCode";
        public static final String BLDG_NAME = "name";
        public static final String TBA_IND = "tbaInd";
    }

    static {
        TypeInfo info = new TypeInfo();
        info.setKey(SCH_AND_ROOM_SEARH_BY_ID_SEARCH_KEY);
        info.setName("Schedule And Room Search");
        info.setDescr(new RichTextHelper().fromPlain("Return search results for both Schedules and Rooms for a particular AO"));

        try {
            info.setEffectiveDate(DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.parse("01/01/2012"));
        } catch ( IllegalArgumentException ex) {
            throw new RuntimeException("bad code");
        }
        SCH_AND_ROOM_SEARH_BY_ID_SEARCH_TYPE = info;

        info = new TypeInfo();
        info.setKey(SCH_RQST_TIMESLOT_BY_REF_ID_AND_TYPE_SEARCH_KEY);
        info.setName("Activity Offerings for CO Search");
        info.setDescr(new RichTextHelper().fromPlain("Return search results for Activity Offerings by CO ID"));

        try {
            info.setEffectiveDate(DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.parse("01/01/2012"));
        } catch ( IllegalArgumentException ex) {
            throw new RuntimeException("bad code");
        }
        SCH_RQST_TIMESLOT_BY_REF_ID_AND_TYPE_SEARCH_TYPE = info;
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

        throw new DoesNotExistException("No Search Type Found for key:"+searchTypeKey);
    }

    @Override
    public List<TypeInfo> getSearchTypes(ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException {
        return Arrays.asList(SCH_AND_ROOM_SEARH_BY_ID_SEARCH_TYPE, SCH_RQST_TIMESLOT_BY_REF_ID_AND_TYPE_SEARCH_TYPE);
    }


    @Override
    public SearchResultInfo search(SearchRequestInfo searchRequestInfo, ContextInfo contextInfo) throws MissingParameterException, OperationFailedException, PermissionDeniedException {

        // As this class expands, you can add multiple searches. Ie. right now there is only one search (so only one search key).
        if (StringUtils.equals(searchRequestInfo.getSearchKey(), SCH_AND_ROOM_SEARH_BY_ID_SEARCH_TYPE.getKey())) {
            return searchForScheduleAndRoomById(searchRequestInfo, contextInfo);
        }else if (StringUtils.equals(searchRequestInfo.getSearchKey(), SCH_RQST_TIMESLOT_BY_REF_ID_AND_TYPE_SEARCH_TYPE.getKey())) {
            return searchForScheduleRequestsByRefIdAndType(searchRequestInfo, contextInfo);
        } else{
            throw new OperationFailedException("Unsupported search type: " + searchRequestInfo.getSearchKey());
        }
    }

    /**
     *
     * @param searchRequestInfo   Contains an Activity Offering ID that we will use to find the scheduleId
     * @param contextInfo
     * @return
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     */
    protected SearchResultInfo searchForScheduleAndRoomById(SearchRequestInfo searchRequestInfo, ContextInfo contextInfo) throws MissingParameterException, OperationFailedException, PermissionDeniedException {
        SearchRequestHelper requestHelper = new SearchRequestHelper(searchRequestInfo);

        List<String> schIds = requestHelper.getParamAsList(SearchParameters.SCHEDULE_IDS);

        if (schIds == null || schIds.isEmpty()){
            throw new RuntimeException("Schedule Ids are required");
        }

        String scheduleIds = commaString(schIds);

        String query =
                " Select "   +
                "  sch.id, " +
                "  tmslot.weekdays, " +
                "  tmslot.startTimeMillis, " +
                "  tmslot.endTimeMillis, " +
                "  tmslot.timeSlotState, " +
                "  room.roomCode, " +
                "  bldg.name, " +
                "  cmp.isTBA " +
                " FROM " +
                "    ScheduleEntity sch, " +
                "    TimeSlotEntity tmslot, " +
                "    ScheduleComponentEntity cmp, " +
                "    IN ( cmp.timeSlotIds ) cmp_tmslot, " +
                "    RoomEntity room, " +
                "    RoomBuildingEntity bldg  " +
                " WHERE " +
                "    sch.id in ("+ scheduleIds +") " +
                " AND cmp.schedule.id = sch.id "    +
                " AND tmslot.id = cmp_tmslot " +
                " AND tmslot.timeSlotState = 'kuali.scheduling.timeslot.state.active' " +
                " AND cmp.roomId = room.id " +
                " AND room.buildingId = bldg.id  ";


        List<Object[]> results = getEntityManager().createQuery(query).getResultList();

        SearchResultInfo resultInfo = new SearchResultInfo();
        resultInfo.setTotalResults(results.size());
        resultInfo.setStartAt(0);

        for (Object[] result : results) {
            SearchResultRowInfo row = new SearchResultRowInfo();

            int i=0;

            row.addCell(SearchResultColumns.SCH_ID,(String)result[i++]);
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
     *
     * @param searchRequestInfo   Contains an Activity Offering ID that we will use to find the scheduleId
     * @param contextInfo
     * @return
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

        try{
            String querySt = query.unwrap(org.hibernate.Query.class).getQueryString();
            System.out.println(querySt);
        } catch (Exception ex){}

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

    private static String commaString(List<String> items){
        StringBuilder sb = new StringBuilder();
        String delim = "";
        for (String str : items) {
            sb.append(delim).append("'" + str + "'");
            delim = ",";
        }
        return sb.toString();
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


}
