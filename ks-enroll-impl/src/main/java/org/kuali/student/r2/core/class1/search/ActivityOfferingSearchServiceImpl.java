package org.kuali.student.r2.core.class1.search;

import org.apache.commons.lang.StringUtils;
import org.kuali.student.enrollment.class1.lui.model.LuiEntity;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: gtaylor
 * Date: 3/11/13
 * Time: 12:56 PM
 *
 * This class is to be used to call ActivityOffering specific DB searches.
 */
public class ActivityOfferingSearchServiceImpl extends SearchServiceAbstractHardwiredImplBase {

    @Resource
    private EntityManager entityManager;

    public static final TypeInfo SCH_IDS_BY_AO_SEARCH_TYPE;
    public static final TypeInfo AOS_AND_CLUSTERS_BY_CO_ID_SEARCH_TYPE;
    public static final TypeInfo REG_GROUPS_BY_CO_ID_SEARCH_TYPE;
    public static final TypeInfo AOS_WO_CLUSTER_BY_FO_ID_SEARCH_TYPE;

    public static final String SCH_IDS_BY_AO_SEARCH_KEY = "kuali.search.type.lui.searchForScheduleIdsByAoId";
    public static final TypeInfo COLOCATED_AOS_BY_AO_IDS_SEARCH_TYPE;
    public static final TypeInfo FO_BY_CO_ID_SEARCH_TYPE;
    public static final TypeInfo RELATED_AO_TYPES_BY_CO_ID_SEARCH_TYPE;

    public static final String AOS_AND_CLUSTERS_BY_CO_ID_SEARCH_KEY = "kuali.search.type.lui.searchForAOsAndClustersByCoId";
    public static final String REG_GROUPS_BY_CO_ID_SEARCH_KEY = "kuali.search.type.lui.searchForRegGroupsByCoId";
    public static final String AOS_WO_CLUSTER_BY_FO_ID_SEARCH_KEY = "kuali.search.type.lui.searchForAOsWithoutClusterByFormatId";
    public static final String COLOCATED_AOS_BY_AO_IDS_SEARCH_KEY = "kuali.search.type.lui.searchForAosByAoIds";
    public static final String FO_BY_CO_ID_SEARCH_KEY = "kuali.search.type.lui.searchForFOByCoId";
    public static final String RELATED_AO_TYPES_BY_CO_ID_SEARCH_KEY = "kuali.search.type.lui.searchForRelatedAoTypesByCoId";

    public static final class SearchParameters {
        public static final String AO_ID = "id";
        public static final String CO_ID = "coId";
        public static final String FO_ID = "foId";
        public static final String AO_IDS = "aoIds";
    }

    public static final class SearchResultColumns {
        public static final String SCHEDULE_ID = "scheduleId";
        public static final String FO_ID = "foId";
        public static final String FORMAT_ID = "formatId";
        public static final String FO_NAME = "foName";
        public static final String AOC_ID = "aocId";
        public static final String AOC_NAME = "aocName";
        public static final String AOC_PRIVATE_NAME = "aocPrivateName";
        public static final String AO_ID = "aoId";
        public static final String AO_TYPE = "aoType";
        public static final String AO_STATE = "aoState";
        public static final String AO_MAX_SEATS = "aoMaxSeats";
        public static final String AO_CODE = "aoCode";
        public static final String CO_CODE = "coCode";
        public static final String LUI_SET_ID = "luiSetId";
        public static final String RG_NAME = "rgId";
        public static final String RG_ID = "rgName";
        public static final String RG_STATE = "rgState";
    }


    static {
        TypeInfo info = new TypeInfo();
        info.setKey(SCH_IDS_BY_AO_SEARCH_KEY);
        info.setName("Activity Offering Search");
        info.setDescr(new RichTextHelper().fromPlain("Return search results for Activity Offerings"));

        try {
            info.setEffectiveDate(DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.parse("01/01/2012"));
        } catch ( IllegalArgumentException ex) {
            throw new RuntimeException("bad code");
        }
        SCH_IDS_BY_AO_SEARCH_TYPE = info;

        info = new TypeInfo();
        info.setKey(AOS_AND_CLUSTERS_BY_CO_ID_SEARCH_KEY);
        info.setName("Activity Offerings for CO Search");
        info.setDescr(new RichTextHelper().fromPlain("Return search results for Activity Offerings by CO ID"));

        try {
            info.setEffectiveDate(DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.parse("01/01/2012"));
        } catch ( IllegalArgumentException ex) {
            throw new RuntimeException("bad code");
        }
        AOS_AND_CLUSTERS_BY_CO_ID_SEARCH_TYPE = info;

        info = new TypeInfo();
        info.setKey(REG_GROUPS_BY_CO_ID_SEARCH_KEY);
        info.setName("Reg Groups for CO Search");
        info.setDescr(new RichTextHelper().fromPlain("Return search results for Reg Groups by CO ID"));

        try {
            info.setEffectiveDate(DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.parse("01/01/2012"));
        } catch ( IllegalArgumentException ex) {
            throw new RuntimeException("bad code");
        }
        REG_GROUPS_BY_CO_ID_SEARCH_TYPE = info;


        info = new TypeInfo();
        info.setKey(COLOCATED_AOS_BY_AO_IDS_SEARCH_KEY);
        info.setName("Colocated AOs Search");
        info.setDescr(new RichTextHelper().fromPlain("Return search results for Colocated AOs"));

        try {
            info.setEffectiveDate(DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.parse("01/01/2012"));
        } catch ( IllegalArgumentException ex) {
            throw new RuntimeException("bad code");
        }
        COLOCATED_AOS_BY_AO_IDS_SEARCH_TYPE = info;

        info = new TypeInfo();
        info.setKey(AOS_WO_CLUSTER_BY_FO_ID_SEARCH_KEY);
        info.setName("AOs without cluster by format offering search");
        info.setDescr(new RichTextHelper().fromPlain("Returns a list of AO Ids that are not assigned to a cluster"));

        try {
            info.setEffectiveDate(DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.parse("01/01/2012"));
        } catch ( IllegalArgumentException ex) {
            throw new RuntimeException("bad code");
        }
        AOS_WO_CLUSTER_BY_FO_ID_SEARCH_TYPE = info;

        info = new TypeInfo();
        info.setKey(FO_BY_CO_ID_SEARCH_KEY);
        info.setName("AOs without cluster by format offering search");
        info.setDescr(new RichTextHelper().fromPlain("Returns a list of AO Ids that are not assigned to a cluster"));

        try {
            info.setEffectiveDate(DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.parse("01/01/2012"));
        } catch ( IllegalArgumentException ex) {
            throw new RuntimeException("bad code");
        }
        FO_BY_CO_ID_SEARCH_TYPE = info;


        info = new TypeInfo();
        info.setKey(RELATED_AO_TYPES_BY_CO_ID_SEARCH_KEY);
        info.setName("Related AO Types for course offering");
        info.setDescr(new RichTextHelper().fromPlain("Returns a list of AO Types allowed for the FOs tied "));

        try {
            info.setEffectiveDate(DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.parse("01/01/2012"));
        } catch ( IllegalArgumentException ex) {
            throw new RuntimeException("bad code");
        }
        RELATED_AO_TYPES_BY_CO_ID_SEARCH_TYPE = info;

    }


    @Override
    public TypeInfo getSearchType() {
        return SCH_IDS_BY_AO_SEARCH_TYPE;
    }

    @Override
    public TypeInfo getSearchType(String searchTypeKey, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException {
        if (SCH_IDS_BY_AO_SEARCH_KEY.equals(searchTypeKey)) {
            return SCH_IDS_BY_AO_SEARCH_TYPE;
        }
        if (AOS_AND_CLUSTERS_BY_CO_ID_SEARCH_KEY.equals(searchTypeKey)) {
            return AOS_AND_CLUSTERS_BY_CO_ID_SEARCH_TYPE;
        }
        if (REG_GROUPS_BY_CO_ID_SEARCH_KEY.equals(searchTypeKey)) {
            return REG_GROUPS_BY_CO_ID_SEARCH_TYPE;
        }
        if (COLOCATED_AOS_BY_AO_IDS_SEARCH_KEY.equals(searchTypeKey)) {
            return COLOCATED_AOS_BY_AO_IDS_SEARCH_TYPE;
        }
        if (AOS_WO_CLUSTER_BY_FO_ID_SEARCH_KEY.equals(searchTypeKey)) {
            return AOS_WO_CLUSTER_BY_FO_ID_SEARCH_TYPE;
        }
        if (FO_BY_CO_ID_SEARCH_KEY.equals(searchTypeKey)) {
            return FO_BY_CO_ID_SEARCH_TYPE;
        }
        if (RELATED_AO_TYPES_BY_CO_ID_SEARCH_KEY.equals(searchTypeKey)) {
            return RELATED_AO_TYPES_BY_CO_ID_SEARCH_TYPE;
        }
        throw new DoesNotExistException("No Search Type Found for key:"+searchTypeKey);
    }

    @Override
    public List<TypeInfo> getSearchTypes(ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException {
        return Arrays.asList(SCH_IDS_BY_AO_SEARCH_TYPE, AOS_AND_CLUSTERS_BY_CO_ID_SEARCH_TYPE,
                REG_GROUPS_BY_CO_ID_SEARCH_TYPE, AOS_WO_CLUSTER_BY_FO_ID_SEARCH_TYPE, COLOCATED_AOS_BY_AO_IDS_SEARCH_TYPE, FO_BY_CO_ID_SEARCH_TYPE,
                RELATED_AO_TYPES_BY_CO_ID_SEARCH_TYPE);
    }


    @Override
    public SearchResultInfo search(SearchRequestInfo searchRequestInfo, ContextInfo contextInfo) throws MissingParameterException, OperationFailedException, PermissionDeniedException {

        // As this class expands, you can add multiple searches. Ie. right now there is only one search (so only one search key).
        if (SCH_IDS_BY_AO_SEARCH_TYPE.getKey().equals(searchRequestInfo.getSearchKey())) {
            return searchForScheduleIdsByAoId(searchRequestInfo,contextInfo);
        } 
        else if (AOS_AND_CLUSTERS_BY_CO_ID_SEARCH_KEY.equals(searchRequestInfo.getSearchKey())){
            return searchForAOsAndClustersByCoId(searchRequestInfo);
        }
        else if (REG_GROUPS_BY_CO_ID_SEARCH_KEY.equals(searchRequestInfo.getSearchKey())){
            return searchForRegGroupsByCoId(searchRequestInfo);
        }
        else if (COLOCATED_AOS_BY_AO_IDS_SEARCH_KEY.equals(searchRequestInfo.getSearchKey())){
            return searchForAosByAoIds(searchRequestInfo);
        }
        else if (AOS_WO_CLUSTER_BY_FO_ID_SEARCH_KEY.equals(searchRequestInfo.getSearchKey())){
            return searchForAOsWithoutClusterByFormatOffering(searchRequestInfo);
        }
        else if (FO_BY_CO_ID_SEARCH_KEY.equals(searchRequestInfo.getSearchKey())){
            return searchForFOByCOId(searchRequestInfo);
        }
        else if (RELATED_AO_TYPES_BY_CO_ID_SEARCH_KEY.equals(searchRequestInfo.getSearchKey())){
            return searchForRelatedAoTypesByCoId(searchRequestInfo);
        }
        else{
            throw new OperationFailedException("Unsupported search type: " + searchRequestInfo.getSearchKey());
        }
    }

    private SearchResultInfo searchForRelatedAoTypesByCoId(SearchRequestInfo searchRequestInfo) {
        SearchResultInfo resultInfo = new SearchResultInfo();

        SearchRequestHelper requestHelper = new SearchRequestHelper(searchRequestInfo);
        String coId = requestHelper.getParamAsString(SearchParameters.CO_ID);

        String queryStr =
            "SELECT co2fo.relatedLui.id," +
            "       relatedTypes " +
            "FROM LuiLuiRelationEntity co2fo," +
            "     IN(co2Fo.relatedLui.relatedLuiTypes) relatedTypes " +
            "WHERE co2fo.luiLuiRelationType = 'kuali.lui.lui.relation.type.deliveredvia.co2fo' " +
            "  AND co2fo.lui.id = :coId ";

        Query query = entityManager.createQuery(queryStr);
        query.setParameter(SearchParameters.CO_ID, coId);       // After updating an oracle driver the List binding is causing massive problems
        List<Object[]> results = query.getResultList();

        for(Object[] resultRow : results){
            int i = 0;
            SearchResultRowInfo row = new SearchResultRowInfo();
            row.addCell(SearchResultColumns.FO_ID, (String)resultRow[i++]);
            row.addCell(SearchResultColumns.AO_TYPE, (String)resultRow[i++]);
            resultInfo.getRows().add(row);
        }

        return resultInfo;
    }

    private SearchResultInfo searchForAosByAoIds(SearchRequestInfo searchRequestInfo) {
        SearchResultInfo resultInfo = new SearchResultInfo();

        SearchRequestHelper requestHelper = new SearchRequestHelper(searchRequestInfo);
        List<String> aoIds = requestHelper.getParamAsList(SearchParameters.AO_IDS);
        String aoIdStr =   commaString(aoIds);

        String queryStr =
                "SELECT aoMatchIds," +
                "       co_ident.code," +
                "       ao_ident.code," +
                "       lset.id " +
                "FROM LuiSetEntity lset," +
                "     IN(lset.luiIds) aoMatchIds," +
                "     IN(lset.luiIds) aoIds," +
                "     LuiIdentifierEntity co_ident," +
                "     LuiIdentifierEntity ao_ident," +
                "     LuiLuiRelationEntity co2fo," +
                "     LuiLuiRelationEntity fo2ao " +
                "WHERE lset.luiSetType = 'kuali.luiset.type.colocated.offering.set' " +
                "  AND aoMatchIds IN(" + aoIdStr + ") " +
                "  AND co2fo.luiLuiRelationType = 'kuali.lui.lui.relation.type.deliveredvia.co2fo' " +
                "  AND fo2ao.luiLuiRelationType = 'kuali.lui.lui.relation.type.deliveredvia.fo2ao' " +
                "  AND co2fo.relatedLui.id = fo2ao.lui.id " +
                "  AND fo2ao.relatedLui.id = aoIds " +
                "  AND co2fo.lui.id = co_ident.lui.id " +
                "  AND aoIds = ao_ident.lui.id " +
                "  AND co_ident.type = 'kuali.lui.identifier.type.official' " +
                "  AND ao_ident.type = 'kuali.lui.identifier.type.official' " +
                "  AND aoMatchIds != aoIds";

        Query query = entityManager.createQuery(queryStr);
//        query.setParameter(SearchParameters.AO_IDS, aoIds);
        List<Object[]> results = query.getResultList();

        for(Object[] resultRow : results){
            int i = 0;
            SearchResultRowInfo row = new SearchResultRowInfo();
            row.addCell(SearchResultColumns.AO_ID, (String)resultRow[i++]);
            row.addCell(SearchResultColumns.CO_CODE, (String)resultRow[i++]);
            row.addCell(SearchResultColumns.AO_CODE, (String)resultRow[i++]);
            row.addCell(SearchResultColumns.LUI_SET_ID, (String)resultRow[i++]);
            resultInfo.getRows().add(row);
        }

        return resultInfo;
    }

    private SearchResultInfo searchForRegGroupsByCoId(SearchRequestInfo searchRequestInfo) {
        SearchResultInfo resultInfo = new SearchResultInfo();

        SearchRequestHelper requestHelper = new SearchRequestHelper(searchRequestInfo);
        String coId = requestHelper.getParamAsString(SearchParameters.CO_ID);

        String queryStr =
                "SELECT rg2ao.relatedLui.id," +
                "       rg2ao.lui.id," +
                "       rg2ao.lui.name," +
                "       rg2ao.lui.luiState " +
                "FROM LuiLuiRelationEntity co2fo," +
                "     LuiLuiRelationEntity fo2ao," +
                "     LuiLuiRelationEntity rg2ao " +
                "WHERE co2fo.luiLuiRelationType = 'kuali.lui.lui.relation.type.deliveredvia.co2fo' " +
                "  AND fo2ao.luiLuiRelationType = 'kuali.lui.lui.relation.type.deliveredvia.fo2ao' " +
                "  AND rg2ao.luiLuiRelationType = 'kuali.lui.lui.relation.type.registeredforvia.rg2ao' " +
                "  AND co2fo.lui.id = :coId " +
                "  AND co2fo.relatedLui.id = fo2ao.lui.id " +
                "  AND rg2ao.relatedLui.id = fo2ao.relatedLui.id ";

        Query query = entityManager.createQuery(queryStr);
        query.setParameter(SearchParameters.CO_ID, coId);
        List<Object[]> results = query.getResultList();

        for(Object[] resultRow : results){
            int i = 0;
            SearchResultRowInfo row = new SearchResultRowInfo();
            row.addCell(SearchResultColumns.AO_ID, (String)resultRow[i++]);
            row.addCell(SearchResultColumns.RG_ID, (String)resultRow[i++]);
            row.addCell(SearchResultColumns.RG_NAME, (String)resultRow[i++]);
            row.addCell(SearchResultColumns.RG_STATE, (String)resultRow[i++]);
            resultInfo.getRows().add(row);
        }

        return resultInfo;

    }

    /**
     *
     * @param searchRequestInfo   Contains an Activity Offering ID that we will use to find the scheduleIds
     * @return
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    protected SearchResultInfo searchForScheduleIdsByAoId(SearchRequestInfo searchRequestInfo, ContextInfo contextInfo) throws MissingParameterException, OperationFailedException, PermissionDeniedException {
        SearchRequestHelper requestHelper = new SearchRequestHelper(searchRequestInfo);

        String aoId = requestHelper.getParamAsString(SearchParameters.AO_ID);

        if (aoId == null || aoId.isEmpty()){
            throw new RuntimeException("Activity Offering id is required");
        }

        List<String> results = Collections.EMPTY_LIST;
        List<LuiEntity> luis = entityManager.createNamedQuery("Lui.getLuisByLuiId").setParameter("aoId", aoId).getResultList();
        if(luis != null && !luis.isEmpty()) {
            LuiEntity lui = luis.get(0);
            results = new ArrayList<String>();
            results.addAll(lui.getScheduleIds());
        }

        SearchResultInfo resultInfo = new SearchResultInfo();
        resultInfo.setTotalResults(results.size());
        resultInfo.setStartAt(0);

        for (String result : results) {
            SearchResultRowInfo row = new SearchResultRowInfo();
            resultInfo.getRows().add(row);
            row.addCell(SearchResultColumns.SCHEDULE_ID, result);
        }

        return resultInfo;

    }

    private SearchResultInfo searchForAOsAndClustersByCoId(SearchRequestInfo searchRequestInfo){
        SearchResultInfo resultInfo = new SearchResultInfo();

        SearchRequestHelper requestHelper = new SearchRequestHelper(searchRequestInfo);
        String coId = requestHelper.getParamAsString(SearchParameters.CO_ID);
//TODO JPQL does not support on clauses in outer joins, so to accomplish this, we would need to update the entities
//        String queryStr =
//                "SELECT DISTINCT " +
//                "       fo.id," +
//                "       fo.name," +
//                "       clster.id," +
//                "       clster.name," +
//                "       clster.privateName," +
//                "       ao.id," +
//                "       ao.luiType," +
//                "       ao.luiState," +
//                "       ao.scheduleId," +
//                "       ao.maxSeats," +
//                "       aoIdent.code " +
//                "FROM LuiLuiRelationEntity co2fo " +
//                        "LEFT OUTER JOIN co2fo.relatedLui fo " +
//                        "LEFT OUTER JOIN ActivityOfferingClusterEntity clster ON (clster.formatOfferingId = fo.id)" +
//                        "LEFT OUTER JOIN clster.aoSets clsterSet " +
//                        "LEFT OUTER JOIN clsterSet.aoIds clst2ao " +
//                        "LEFT OUTER JOIN LuiEntity ao ON (ao.id = aocSetAoIds) " +
//                        "LEFT OUTER JOIN ao.identifiers aoIdent " +
//                "WHERE co2fo.lui.id = :coId " +
//                "  AND co2fo.luiLuiRelationType = 'kuali.lui.lui.relation.type.deliveredvia.co2fo' " +
//                "  AND clster.formatOfferingId = fo.id ";
////                "  AND aoIdent.type = 'kuali.lui.identifier.type.official'";

        String queryStr = "SELECT DISTINCT " +
                "    co2fo.RELATED_LUI_ID AS col_0_0_, " +
                "    fo.NAME              AS col_1_0_, " +
                "    fo.CLU_ID            AS col_2_0_, " +
                "    clster.ID            AS col_3_0_, " +
                "    clster.NAME          AS col_4_0_, " +
                "    clster.PRIVATE_NAME  AS col_5_0_, " +
                "    ao.ID                AS col_6_0_, " +
                "    ao.LUI_TYPE          AS col_7_0_, " +
                "    ao.LUI_STATE         AS col_8_0_, " +
                "    ao.MAX_SEATS         AS col_9_0_, " +
                "    aoIdent.LUI_CD       AS col_10_0_ " +
                "FROM " +
                "    KSEN_LUILUI_RELTN co2fo " +
                "LEFT OUTER JOIN " +
                "    KSEN_LUI fo " +
                "ON " +
                "    co2fo.RELATED_LUI_ID=fo.ID " +
                "LEFT OUTER JOIN " +
                "    KSEN_CO_AO_CLUSTER clster " +
                "ON " +
                "    co2fo.RELATED_LUI_ID=clster.FORMAT_OFFERING_ID " +
                "LEFT OUTER JOIN " +
                "    KSEN_CO_AO_CLUSTER_SET clsterSet " +
                "ON " +
                "    clster.ID=clsterSet.AO_CLUSTER_ID " +
                "LEFT OUTER JOIN " +
                "    KSEN_CO_AO_CLUSTER_SET_AO clst2ao " +
                "ON " +
                "    clsterSet.ID=clst2ao.AOC_SET_ID " +
                "LEFT OUTER JOIN " +
                "    KSEN_LUI ao " +
                "ON " +
                "    clst2ao.ACTIVITY_OFFERING_ID=ao.ID " +
                "LEFT OUTER JOIN " +
                "    KSEN_LUI_IDENT aoIdent " +
                "ON " +
                "    ao.ID=aoIdent.LUI_ID " +
                "AND aoIdent.LUI_ID_TYPE='kuali.lui.identifier.type.official' " +
                "WHERE " +
                "    co2fo.LUI_ID= :coId " +
                "AND co2fo.LUILUI_RELTN_TYPE='kuali.lui.lui.relation.type.deliveredvia.co2fo'";
        Query query = entityManager.createNativeQuery(queryStr);
        query.setParameter(SearchParameters.CO_ID, coId);
        List<Object[]> results = query.getResultList();

        for(Object[] resultRow : results){
            int i = 0;
            SearchResultRowInfo row = new SearchResultRowInfo();
            row.addCell(SearchResultColumns.FO_ID, (String)resultRow[i++]);
            row.addCell(SearchResultColumns.FO_NAME, (String)resultRow[i++]);
            row.addCell(SearchResultColumns.FORMAT_ID, (String)resultRow[i++]);
            row.addCell(SearchResultColumns.AOC_ID, (String)resultRow[i++]);
            row.addCell(SearchResultColumns.AOC_NAME, (String)resultRow[i++]);
            row.addCell(SearchResultColumns.AOC_PRIVATE_NAME, (String)resultRow[i++]);
            String aoId = (String)resultRow[i++];
            row.addCell(SearchResultColumns.AO_ID, aoId);
            row.addCell(SearchResultColumns.AO_TYPE, (String)resultRow[i++]);
            row.addCell(SearchResultColumns.AO_STATE, (String)resultRow[i++]);

            String strSchedules = "";
            List<String> scheduleIds = searchForScheduleIdsByAOId(aoId);
            if(scheduleIds != null && !scheduleIds.isEmpty()) {
                for(String scheduleId : scheduleIds) {
                    strSchedules = scheduleId + ",";
                }

                row.addCell(SearchResultColumns.SCHEDULE_ID, strSchedules.substring(0, strSchedules.lastIndexOf(",")));
            } else {
                row.addCell(SearchResultColumns.SCHEDULE_ID,null);
            }

            row.addCell(SearchResultColumns.AO_MAX_SEATS, resultRow[i]==null?null:resultRow[i].toString());
            i++;
            row.addCell(SearchResultColumns.AO_CODE, (String)resultRow[i++]);
            resultInfo.getRows().add(row);
        }

        return resultInfo;
    }


    private List<String> searchForScheduleIdsByAOId(String aoId) {
        if( aoId == null || aoId.trim().isEmpty() ) return Collections.EMPTY_LIST;

        LuiEntity entity = entityManager.find(LuiEntity.class, aoId.trim());
        if(entity != null && entity.getScheduleIds() != null) {
            return entity.getScheduleIds();
        }

        return Collections.EMPTY_LIST;
    }

    protected SearchResultInfo searchForAOsWithoutClusterByFormatOffering(SearchRequestInfo searchRequestInfo){
        SearchResultInfo resultInfo = new SearchResultInfo();

        SearchRequestHelper requestHelper = new SearchRequestHelper(searchRequestInfo);
        String foId = requestHelper.getParamAsString(SearchParameters.FO_ID);

        String queryStr =
                "SELECT rel.relatedLui.id aoId " +
                        "FROM LuiLuiRelationEntity rel " +
                        "WHERE rel.lui.id = :foId " +
                        "  AND rel.luiLuiRelationType = 'kuali.lui.lui.relation.type.deliveredvia.fo2ao' " +
                        "  AND rel.relatedLui.id not in ( " +
                        "SELECT aocSetAoIds " +
                        "FROM ActivityOfferingClusterEntity aoc " +
                        "     IN(aoc.aoSets) aocSets, " +
                        "     IN(aocSets.aoIds) aocSetAoIds " +
                        "WHERE " +
                        "  aoc.formatOfferingId = :foId ) ";

        Query query = entityManager.createQuery(queryStr);
        query.setParameter(SearchParameters.FO_ID, foId);
        List<Object[]> results = query.getResultList();

        for(Object[] resultRow : results){
            int i = 0;
            SearchResultRowInfo row = new SearchResultRowInfo();
            row.addCell(SearchResultColumns.AO_ID, (String)resultRow[i++]);
            resultInfo.getRows().add(row);
        }

        return resultInfo;
    }

    protected SearchResultInfo searchForFOByCOId(SearchRequestInfo searchRequestInfo){
        SearchResultInfo resultInfo = new SearchResultInfo();

        SearchRequestHelper requestHelper = new SearchRequestHelper(searchRequestInfo);
        String coId = requestHelper.getParamAsString(SearchParameters.CO_ID);

        String queryStr =
                "SELECT rel.relatedLui.id as foId, " +
                        "fo_lui.name as foName " +
                        "FROM LuiLuiRelationEntity rel, " +
                        "     LuiEntity fo_lui " +
                        "WHERE rel.lui.id = :coId " +
                        "  AND rel.luiLuiRelationType = 'kuali.lui.lui.relation.type.deliveredvia.co2fo' " +
                        "  AND rel.relatedLui.id = fo_lui.id";

        Query query = entityManager.createQuery(queryStr);
        query.setParameter(SearchParameters.CO_ID, coId);
        List<Object[]> results = query.getResultList();

        for(Object[] resultRow : results){
            int i = 0;
            SearchResultRowInfo row = new SearchResultRowInfo();
            row.addCell(SearchResultColumns.FO_ID, (String)resultRow[i++]);
            row.addCell(SearchResultColumns.FO_NAME, (String)resultRow[i++]);
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
