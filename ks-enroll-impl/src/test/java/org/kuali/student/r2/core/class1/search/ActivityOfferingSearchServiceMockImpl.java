package org.kuali.student.r2.core.class1.search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.lui.dto.LuiInfo;
import org.kuali.student.enrollment.lui.service.LuiService;
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
import org.kuali.student.r2.core.search.service.SearchService;
import org.kuali.student.r2.core.search.util.SearchRequestHelper;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created with IntelliJ IDEA.
 * User: gtaylor
 * Date: 3/11/13
 * Time: 12:56 PM
 *
 * This class is to be used to call ActivityOffering specific DB searches.
 * 
 * This is a mock version.
 */
public class ActivityOfferingSearchServiceMockImpl implements SearchService {

    private int maxInClauseElements = 100;

    public static final TypeInfo SCH_IDS_BY_AO_SEARCH_TYPE;
    public static final TypeInfo AOS_AND_CLUSTERS_BY_CO_ID_SEARCH_TYPE;
    public static final TypeInfo AO_AND_FO_IDS_BY_CO_ID_SEARCH_TYPE;
    public static final TypeInfo REG_GROUPS_BY_CO_ID_SEARCH_TYPE;
    public static final TypeInfo AOS_WO_CLUSTER_BY_FO_ID_SEARCH_TYPE;
    public static final TypeInfo AO_CODES_TYPES_BY_CO_ID_SEARCH_TYPE;
    public static final TypeInfo TERM_ID_BY_OFFERING_ID_SEARCH_TYPE;
    public static final TypeInfo TOTAL_MAX_SEATS_BY_AO_IDS_SEARCH_TYPE;
    public static final TypeInfo COLOCATED_AOS_BY_AO_IDS_SEARCH_TYPE;
    public static final TypeInfo FO_BY_CO_ID_SEARCH_TYPE;
    public static final TypeInfo FO_IDS_BY_CO_ID_SEARCH_TYPE;
    public static final TypeInfo WL_IND_BY_CO_ID_SEARCH_TYPE;
    public static final TypeInfo RELATED_AO_TYPES_BY_CO_ID_SEARCH_TYPE;
    public static final TypeInfo AO_CLUSTER_COUNT_BY_FO_TYPE;
    public static final TypeInfo AO_ID_AND_TYPE_BY_FO_TYPE;
    public static final TypeInfo COLOCATED_AOIDS_BY_AO_IDS_SEARCH_TYPE;
    public static final TypeInfo CO_IDS_AND_FO_IDS_AND_ATP_IDS_BY_AO_IDS_SEARCH_TYPE;

    public static final String SCH_IDS_BY_AO_SEARCH_KEY = "kuali.search.type.lui.searchForScheduleIdsByAoId";
    public static final String AOS_AND_CLUSTERS_BY_CO_ID_SEARCH_KEY = "kuali.search.type.lui.searchForAOsAndClustersByCoId";
    public static final String AO_AND_FO_IDS_BY_CO_ID_SEARCH_KEY = "kuali.search.type.lui.searchForAOIdsFOIdsByCoId";
    public static final String REG_GROUPS_BY_CO_ID_SEARCH_KEY = "kuali.search.type.lui.searchForRegGroupsByCoId";
    public static final String AOS_WO_CLUSTER_BY_FO_ID_SEARCH_KEY = "kuali.search.type.lui.searchForAOsWithoutClusterByFormatId";
    public static final String COLOCATED_AOS_BY_AO_IDS_SEARCH_KEY = "kuali.search.type.lui.searchForAosByAoIds";
    public static final String COLOCATED_AOIDS_BY_AO_IDS_SEARCH_KEY = "kuali.search.type.lui.searchForColocatedAoIdsByAoIds";
    public static final String FO_BY_CO_ID_SEARCH_KEY = "kuali.search.type.lui.searchForFOByCoId";
    public static final String FO_IDS_BY_CO_ID_SEARCH_KEY = "kuali.search.type.lui.searchForFOIdsByCoId";
    public static final String WL_IND_BY_CO_ID_SEARCH_KEY = "kuali.search.type.lui.searchForWLIndicatorByCOId";
    public static final String RELATED_AO_TYPES_BY_CO_ID_SEARCH_KEY = "kuali.search.type.lui.searchForRelatedAoTypesByCoId";
    public static final String AO_CODES_TYPES_BY_CO_ID_SEARCH_KEY = "kuali.search.type.lui.searchForAoCodesAndTypesByCoId";
    public static final String TERM_ID_BY_OFFERING_ID_SEARCH_KEY = "kuali.search.type.lui.searchForTermIdByOfferingId";
    public static final String TOTAL_MAX_SEATS_BY_AO_IDS_SEARCH_KEY = "kuali.search.type.lui.searchForTotalMaxSeatsByAOIds";
    public static final String AO_CLUSTER_COUNT_BY_FO_SEARCH_KEY = "kuali.search.type.lui.getCountOfAOClustersByFO";
    public static final String AO_ID_AND_TYPE_BY_FO_SEARCH_KEY = "kuali.search.type.lui.getAoIdAndTypeByFoId";
    public static final String CO_IDS_AND_FO_IDS_AND_ATP_IDS_BY_AO_IDS_SEARCH_KEY = "kuali.search.type.lui.searchForCOIdsAndFOIdsAndAtpIdsByAOIds";

    public static final String DEFAULT_EFFECTIVE_DATE = "01/01/2012";


    private static final int RESULTROW_AOID_OFFSET = 6;
    private static final int RESULTROW_SCHED_OFFSET = 9;

    public static final class SearchParameters {
        public static final String AO_ID = "id";
        public static final String CO_ID = "coId";
        public static final String OFFERING_ID = "offeringId";
        public static final String FO_ID = "foId";
        public static final String AO_IDS = "aoIds";
        public static final String AO_STATES = "aoStates";
        public static final String REGGROUP_STATES = "regGroupStates";
        public static final String FO_REL_TYPE = "foRelType";
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
        public static final String CO_ID = "coId";
        public static final String RG_NAME = "rgId";
        public static final String RG_ID = "rgName";
        public static final String RG_STATE = "rgState";
        public static final String ATP_ID = "atpId";
        public static final String TOTAL_MAX_SEATS = "totalMaxSeats";
        public static final String AO_CLUSTER_COUNT = "aoClusterCount";
        public static final String WL_IND = "wlInd";
    }

    static {
        TypeInfo info = new TypeInfo();
        info.setKey(SCH_IDS_BY_AO_SEARCH_KEY);
        info.setName("Activity Offering Search");
        info.setDescr(new RichTextHelper().fromPlain("Return search results for Activity Offerings"));
        info.setEffectiveDate(DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.parse(DEFAULT_EFFECTIVE_DATE));

        SCH_IDS_BY_AO_SEARCH_TYPE = info;

        info = new TypeInfo();
        info.setKey(AOS_AND_CLUSTERS_BY_CO_ID_SEARCH_KEY);
        info.setName("Activity Offerings for CO Search");
        info.setDescr(new RichTextHelper().fromPlain("Return search results for Activity Offerings by CO ID"));
        info.setEffectiveDate(DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.parse(DEFAULT_EFFECTIVE_DATE));

        AOS_AND_CLUSTERS_BY_CO_ID_SEARCH_TYPE = info;

        info = new TypeInfo();
        info.setKey(AO_AND_FO_IDS_BY_CO_ID_SEARCH_KEY);
        info.setName("Activity Offering and Format Offering IDs by CO Id Search");
        info.setDescr(new RichTextHelper().fromPlain("Return search results for Activity Offering and Format Offering Ids by CO ID"));
        info.setEffectiveDate(DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.parse(DEFAULT_EFFECTIVE_DATE));

        AO_AND_FO_IDS_BY_CO_ID_SEARCH_TYPE = info;

        info = new TypeInfo();
        info.setKey(REG_GROUPS_BY_CO_ID_SEARCH_KEY);
        info.setName("Reg Groups for CO Search");
        info.setDescr(new RichTextHelper().fromPlain("Return search results for Reg Groups by CO ID"));
        info.setEffectiveDate(DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.parse(DEFAULT_EFFECTIVE_DATE));

        REG_GROUPS_BY_CO_ID_SEARCH_TYPE = info;

        info = new TypeInfo();
        info.setKey(COLOCATED_AOS_BY_AO_IDS_SEARCH_KEY);
        info.setName("Colocated AOs Search");
        info.setDescr(new RichTextHelper().fromPlain("Return search results for Colocated AOs"));
        info.setEffectiveDate(DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.parse(DEFAULT_EFFECTIVE_DATE));

        COLOCATED_AOS_BY_AO_IDS_SEARCH_TYPE = info;

        info = new TypeInfo();
        info.setKey(AOS_WO_CLUSTER_BY_FO_ID_SEARCH_KEY);
        info.setName("AOs without cluster by format offering search");
        info.setDescr(new RichTextHelper().fromPlain("Returns a list of AO Ids that are not assigned to a cluster"));
        info.setEffectiveDate(DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.parse(DEFAULT_EFFECTIVE_DATE));

        AOS_WO_CLUSTER_BY_FO_ID_SEARCH_TYPE = info;

        info = new TypeInfo();
        info.setKey(FO_BY_CO_ID_SEARCH_KEY);
        info.setName("FOs by course offering ID search");
        info.setDescr(new RichTextHelper().fromPlain("Returns a list of FO Ids & names"));
        info.setEffectiveDate(DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.parse(DEFAULT_EFFECTIVE_DATE));

        FO_BY_CO_ID_SEARCH_TYPE = info;

        info = new TypeInfo();
        info.setKey(FO_IDS_BY_CO_ID_SEARCH_KEY);
        info.setName("FO IDs by course offering ID search");
        info.setDescr(new RichTextHelper().fromPlain("Returns a list of FO Ids"));
        info.setEffectiveDate(DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.parse(DEFAULT_EFFECTIVE_DATE));

        FO_IDS_BY_CO_ID_SEARCH_TYPE = info;

        info = new TypeInfo();
        info.setKey(WL_IND_BY_CO_ID_SEARCH_KEY);
        info.setName("WaitList indicator by course offering ID search");
        info.setDescr(new RichTextHelper().fromPlain("Returns WaitList Indicator"));
        info.setEffectiveDate(DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.parse(DEFAULT_EFFECTIVE_DATE));

        WL_IND_BY_CO_ID_SEARCH_TYPE = info;

        info = new TypeInfo();
        info.setKey(RELATED_AO_TYPES_BY_CO_ID_SEARCH_KEY);
        info.setName("Related AO Types for course offering");
        info.setDescr(new RichTextHelper().fromPlain("Returns a list of AO Types allowed for the FOs tied "));
        info.setEffectiveDate(DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.parse(DEFAULT_EFFECTIVE_DATE));

        RELATED_AO_TYPES_BY_CO_ID_SEARCH_TYPE = info;

        info = new TypeInfo();
        info.setKey(AO_CODES_TYPES_BY_CO_ID_SEARCH_KEY);
        info.setName("AO codes and types for course offering id");
        info.setDescr(new RichTextHelper().fromPlain("Returns a list of AO codes and types for a given CO id"));
        info.setEffectiveDate(DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.parse(DEFAULT_EFFECTIVE_DATE));

        AO_CODES_TYPES_BY_CO_ID_SEARCH_TYPE = info;

        info = new TypeInfo();
        info.setKey(TERM_ID_BY_OFFERING_ID_SEARCH_KEY);
        info.setName("Term Id for offering id");
        info.setDescr(new RichTextHelper().fromPlain("Returns term id for a given offering id"));
        info.setEffectiveDate(DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.parse(DEFAULT_EFFECTIVE_DATE));

        TERM_ID_BY_OFFERING_ID_SEARCH_TYPE = info;

        info = new TypeInfo();
        info.setKey(TOTAL_MAX_SEATS_BY_AO_IDS_SEARCH_KEY);
        info.setName("Total Max seats for AO Set id");
        info.setDescr(new RichTextHelper().fromPlain("Returns Total Max seats for AO Set id"));
        info.setEffectiveDate(DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.parse(DEFAULT_EFFECTIVE_DATE));

        TOTAL_MAX_SEATS_BY_AO_IDS_SEARCH_TYPE = info;

        info = new TypeInfo();
        info.setKey(AO_CLUSTER_COUNT_BY_FO_SEARCH_KEY);
        info.setName("get AO cluster count by Format offering");
        info.setDescr(new RichTextHelper().fromPlain("Returns the number of AO clusters for a particular Format Offering"));
        info.setEffectiveDate(DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.parse(DEFAULT_EFFECTIVE_DATE));

        AO_CLUSTER_COUNT_BY_FO_TYPE = info;

        info = new TypeInfo();
        info.setKey(AO_ID_AND_TYPE_BY_FO_SEARCH_KEY);
        info.setName("get AO ids and types by Format offering");
        info.setDescr(new RichTextHelper().fromPlain("Returns AO ids and types for a particular Format Offering"));
        info.setEffectiveDate(DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.parse(DEFAULT_EFFECTIVE_DATE));

        AO_ID_AND_TYPE_BY_FO_TYPE = info;

        info = new TypeInfo();
        info.setKey(COLOCATED_AOIDS_BY_AO_IDS_SEARCH_KEY);
        info.setName("get AO ids that are colocated");
        info.setDescr(new RichTextHelper().fromPlain("Returns AO ids that are colocated from a list of ao ids passed in"));
        info.setEffectiveDate(DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.parse(DEFAULT_EFFECTIVE_DATE));

        COLOCATED_AOIDS_BY_AO_IDS_SEARCH_TYPE = info;
        
        info = new TypeInfo();
        info.setKey(CO_IDS_AND_FO_IDS_AND_ATP_IDS_BY_AO_IDS_SEARCH_KEY);
        info.setName("get CO ids and FO ids and Atp Ids by AO ids");
        info.setDescr(new RichTextHelper().fromPlain("Returns CO ids and FO ids and Atp Ids that are related to the AO ids passed in"));
        info.setEffectiveDate(DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.parse(DEFAULT_EFFECTIVE_DATE));
        
        CO_IDS_AND_FO_IDS_AND_ATP_IDS_BY_AO_IDS_SEARCH_TYPE = info;
        
        
        
    }
    
    @Resource (name="CourseOfferingService")
    private CourseOfferingService coService;
    
    @Resource (name="LuiService")
    private LuiService luiService;

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
        if (AO_AND_FO_IDS_BY_CO_ID_SEARCH_KEY.equals(searchTypeKey)) {
            return AO_AND_FO_IDS_BY_CO_ID_SEARCH_TYPE;
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
        if (FO_IDS_BY_CO_ID_SEARCH_KEY.equals(searchTypeKey)) {
            return FO_IDS_BY_CO_ID_SEARCH_TYPE;
        }
        if (WL_IND_BY_CO_ID_SEARCH_KEY.equals(searchTypeKey)) {
            return WL_IND_BY_CO_ID_SEARCH_TYPE;
        }
        if (RELATED_AO_TYPES_BY_CO_ID_SEARCH_KEY.equals(searchTypeKey)) {
            return RELATED_AO_TYPES_BY_CO_ID_SEARCH_TYPE;
        }
        if (AO_CODES_TYPES_BY_CO_ID_SEARCH_KEY.equals(searchTypeKey)) {
            return AO_CODES_TYPES_BY_CO_ID_SEARCH_TYPE;
        }
        if (TERM_ID_BY_OFFERING_ID_SEARCH_KEY.equals(searchTypeKey)) {
            return TERM_ID_BY_OFFERING_ID_SEARCH_TYPE;
        }
        if (TOTAL_MAX_SEATS_BY_AO_IDS_SEARCH_KEY.equals(searchTypeKey)) {
            return TOTAL_MAX_SEATS_BY_AO_IDS_SEARCH_TYPE;
        }
        if (AO_CLUSTER_COUNT_BY_FO_SEARCH_KEY.equals(searchTypeKey)) {
            return AO_CLUSTER_COUNT_BY_FO_TYPE;
        }
        if (AO_ID_AND_TYPE_BY_FO_SEARCH_KEY.equals(searchTypeKey)) {
            return AO_ID_AND_TYPE_BY_FO_TYPE;
        }
        if (COLOCATED_AOIDS_BY_AO_IDS_SEARCH_KEY.equals(searchTypeKey)) {
            return COLOCATED_AOIDS_BY_AO_IDS_SEARCH_TYPE;
        }
        if (CO_IDS_AND_FO_IDS_AND_ATP_IDS_BY_AO_IDS_SEARCH_KEY.equals(searchTypeKey)) {
        	return CO_IDS_AND_FO_IDS_AND_ATP_IDS_BY_AO_IDS_SEARCH_TYPE;
        }
        	
        throw new DoesNotExistException("No Search Type Found for key:"+searchTypeKey);
    }

    @Override
    public List<TypeInfo> getSearchTypes(ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException {
        return Arrays.asList(SCH_IDS_BY_AO_SEARCH_TYPE, AOS_AND_CLUSTERS_BY_CO_ID_SEARCH_TYPE, AO_AND_FO_IDS_BY_CO_ID_SEARCH_TYPE,
                REG_GROUPS_BY_CO_ID_SEARCH_TYPE, AOS_WO_CLUSTER_BY_FO_ID_SEARCH_TYPE, COLOCATED_AOS_BY_AO_IDS_SEARCH_TYPE, FO_BY_CO_ID_SEARCH_TYPE,
                FO_IDS_BY_CO_ID_SEARCH_TYPE, WL_IND_BY_CO_ID_SEARCH_TYPE, RELATED_AO_TYPES_BY_CO_ID_SEARCH_TYPE, TERM_ID_BY_OFFERING_ID_SEARCH_TYPE,
                AO_CODES_TYPES_BY_CO_ID_SEARCH_TYPE, AO_CLUSTER_COUNT_BY_FO_TYPE, AO_ID_AND_TYPE_BY_FO_TYPE, COLOCATED_AOIDS_BY_AO_IDS_SEARCH_TYPE, CO_IDS_AND_FO_IDS_AND_ATP_IDS_BY_AO_IDS_SEARCH_TYPE);
    }

    @Override
    @Transactional(readOnly = true)
    public SearchResultInfo search(SearchRequestInfo searchRequestInfo, ContextInfo contextInfo) throws MissingParameterException, OperationFailedException, PermissionDeniedException {

        if (SCH_IDS_BY_AO_SEARCH_TYPE.getKey().equals(searchRequestInfo.getSearchKey())) {
            return searchForScheduleIdsByAoId(searchRequestInfo);
        }
        else if (AOS_AND_CLUSTERS_BY_CO_ID_SEARCH_KEY.equals(searchRequestInfo.getSearchKey())){
            return searchForAOsAndClustersByCoId(searchRequestInfo);
        }
        else if (AO_AND_FO_IDS_BY_CO_ID_SEARCH_KEY.equals(searchRequestInfo.getSearchKey())){
            try {
				return searchForAOIdsFOIdsByCoId(searchRequestInfo);
			} catch (InvalidParameterException e) {
				throw new OperationFailedException("searchForAOIdsFOIdsByCoId failed", e);
			}
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
        else if (FO_IDS_BY_CO_ID_SEARCH_KEY.equals(searchRequestInfo.getSearchKey())){
            return searchForFOIdsByCOId(searchRequestInfo);
        }
        else if (WL_IND_BY_CO_ID_SEARCH_KEY.equals(searchRequestInfo.getSearchKey())){
            return searchForWLIndicatorByCOId(searchRequestInfo);
        }
        else if (RELATED_AO_TYPES_BY_CO_ID_SEARCH_KEY.equals(searchRequestInfo.getSearchKey())){
            return searchForRelatedAoTypesByCoId(searchRequestInfo);
        }
        else if (AO_CODES_TYPES_BY_CO_ID_SEARCH_KEY.equals(searchRequestInfo.getSearchKey())){
            return searchForAoCodesAndTypesByCoId(searchRequestInfo);
        }
        else if (TERM_ID_BY_OFFERING_ID_SEARCH_KEY.equals(searchRequestInfo.getSearchKey())){
            return searchForTermIdByOfferingId(searchRequestInfo);
        }
        else if (TOTAL_MAX_SEATS_BY_AO_IDS_SEARCH_KEY.equals(searchRequestInfo.getSearchKey())){
            return searchForTotalMaxSeatsByAOIds(searchRequestInfo);
        }
        else if (AO_CLUSTER_COUNT_BY_FO_SEARCH_KEY.equals(searchRequestInfo.getSearchKey())){
            return searchForAOClusterCountByFO(searchRequestInfo);
        }
        else if (AO_ID_AND_TYPE_BY_FO_SEARCH_KEY.equals(searchRequestInfo.getSearchKey())){
            return searchForAoIdAndTypeByFO(searchRequestInfo);
        }
        else if (COLOCATED_AOIDS_BY_AO_IDS_SEARCH_KEY.equals(searchRequestInfo.getSearchKey())){
            return searchForColocatedAoIdsByAoIds(searchRequestInfo);
        }
        else if (CO_IDS_AND_FO_IDS_AND_ATP_IDS_BY_AO_IDS_SEARCH_KEY.equals(searchRequestInfo.getSearchKey())){
            try {
				return searchForCOIdsAndFOIdsAndAtpIdsByAOIds(searchRequestInfo);
			} catch (DoesNotExistException e) {
				throw new OperationFailedException("searchForCOIdsAndFOIdsAndAtpIdsByAOIds failed", e);
			} catch (InvalidParameterException e) {
				throw new OperationFailedException("searchForCOIdsAndFOIdsAndAtpIdsByAOIds failed", e);
			}
        }
        else{
            throw new OperationFailedException("Unsupported search type: " + searchRequestInfo.getSearchKey());
        }
    }

    
	private SearchResultInfo searchForCOIdsAndFOIdsAndAtpIdsByAOIds(
			SearchRequestInfo searchRequestInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

		SearchResultInfo resultInfo = new SearchResultInfo();

        SearchRequestHelper requestHelper = new SearchRequestHelper(searchRequestInfo);
        
        List<String>aoIds = requestHelper.getParamAsList(SearchParameters.AO_IDS);
        
        ContextInfo contextInfo = new ContextInfo();
        
        contextInfo.setCurrentDate(new Date());
        
        List<LuiInfo> aos = luiService.getLuisByIds(aoIds, contextInfo);
        
        for (LuiInfo ao	: aos) {
			
        	List<LuiInfo> fos = luiService.getLuisByRelatedLuiAndRelationType(ao.getId(), LuiServiceConstants.LUI_LUI_RELATION_DELIVERED_VIA_FO_TO_AO_TYPE_KEY, contextInfo);
        	
        	for (LuiInfo fo : fos) {
				
        		List<LuiInfo> cos = luiService.getLuisByRelatedLuiAndRelationType(fo.getId(), LuiServiceConstants.LUI_LUI_RELATION_DELIVERED_VIA_CO_TO_FO_TYPE_KEY, contextInfo);
        		
        		for (LuiInfo co : cos) {
					
					SearchResultRowInfo row = new SearchResultRowInfo();
					row.addCell(SearchResultColumns.CO_ID, co.getId());
					row.addCell(SearchResultColumns.AO_ID,
							ao.getId());
					row.addCell(SearchResultColumns.ATP_ID,
							ao.getAtpId());
					row.addCell(SearchResultColumns.FO_ID,
							fo.getId());
					resultInfo.getRows().add(row);
        		}
			}
		}
       
        return resultInfo;
	}

	private SearchResultInfo searchForColocatedAoIdsByAoIds(SearchRequestInfo searchRequestInfo) {
        SearchResultInfo resultInfo = new SearchResultInfo();

        SearchRequestHelper requestHelper = new SearchRequestHelper(searchRequestInfo);
        List<String> aoIdsList = requestHelper.getParamAsList(SearchParameters.AO_IDS);

        List<String> results = new ArrayList<String>();
        for(String result : results){
            SearchResultRowInfo row = new SearchResultRowInfo();
            row.addCell(SearchResultColumns.AO_ID, result);
            resultInfo.getRows().add(row);
        }

        return resultInfo;
    }

    private SearchResultInfo searchForTotalMaxSeatsByAOIds(SearchRequestInfo searchRequestInfo) {
        SearchResultInfo resultInfo = new SearchResultInfo();

        SearchRequestHelper requestHelper = new SearchRequestHelper(searchRequestInfo);
        List<String> aoIdsList = requestHelper.getParamAsList(SearchParameters.AO_IDS);

        List<Long> results = new ArrayList<Long>();

        for(Long result : results){
            SearchResultRowInfo row = new SearchResultRowInfo();
            if (result == null || result.equals("")) {
                row.addCell(SearchResultColumns.TOTAL_MAX_SEATS, "0");
            } else {
                row.addCell(SearchResultColumns.TOTAL_MAX_SEATS, result.toString());
            }
            resultInfo.getRows().add(row);
        }

        return resultInfo;
    }

    private SearchResultInfo searchForRelatedAoTypesByCoId(SearchRequestInfo searchRequestInfo) throws OperationFailedException {
        SearchResultInfo resultInfo = new SearchResultInfo();

        SearchRequestHelper requestHelper = new SearchRequestHelper(searchRequestInfo);
        String coId = requestHelper.getParamAsString(SearchParameters.CO_ID);

        List<Object[]> results = new ArrayList<Object[]>();

        for(Object[] resultRow : results){
            int i = 0;
            SearchResultRowInfo row = new SearchResultRowInfo();
            row.addCell(SearchResultColumns.FO_ID, (String)resultRow[i++]);
            row.addCell(SearchResultColumns.AO_TYPE, (String)resultRow[i]);
            resultInfo.getRows().add(row);
        }

        return resultInfo;
    }

    /**
     * Finds a list of AO codes, types, and Ids given a CO id.
     * @throws OperationFailedException 
     */
     private SearchResultInfo searchForAoCodesAndTypesByCoId(SearchRequestInfo searchRequestInfo) throws OperationFailedException {
        SearchResultInfo resultInfo = new SearchResultInfo();
        SearchRequestHelper requestHelper = new SearchRequestHelper(searchRequestInfo);
        String coId = requestHelper.getParamAsString(SearchParameters.CO_ID);

     
        List<Object[]> results = new ArrayList<Object[]>();

        for(Object[] resultRow : results){
            int i = 0;
            SearchResultRowInfo row = new SearchResultRowInfo();
            row.addCell(SearchResultColumns.AO_ID, (String)resultRow[i++]);
            row.addCell(SearchResultColumns.AO_CODE, (String)resultRow[i++]);
            row.addCell(SearchResultColumns.AO_TYPE, (String)resultRow[i]);
            resultInfo.getRows().add(row);
        }
        return resultInfo;
    }

    private SearchResultInfo searchForAosByAoIds(SearchRequestInfo searchRequestInfo) {
        SearchResultInfo resultInfo = new SearchResultInfo();

        SearchRequestHelper requestHelper = new SearchRequestHelper(searchRequestInfo);
        List<String> aoIdsList = requestHelper.getParamAsList(SearchParameters.AO_IDS);
        List<String> aoStates = requestHelper.getParamAsList(SearchParameters.AO_STATES);
        String filterAOStates = "'" + StringUtils.join(aoStates, "','") + "'";

       
        List<Object[]> results = new ArrayList<Object[]>();

        for(Object[] resultRow : results){
            int i = 0;
            SearchResultRowInfo row = new SearchResultRowInfo();
            row.addCell(SearchResultColumns.AO_ID, (String)resultRow[i++]);
            row.addCell(SearchResultColumns.CO_CODE, (String)resultRow[i++]);
            row.addCell(SearchResultColumns.AO_CODE, (String)resultRow[i]);
            resultInfo.getRows().add(row);
        }

        return resultInfo;
    }

    private SearchResultInfo searchForRegGroupsByCoId(SearchRequestInfo searchRequestInfo) throws OperationFailedException {
        SearchResultInfo resultInfo = new SearchResultInfo();

        SearchRequestHelper requestHelper = new SearchRequestHelper(searchRequestInfo);
        String coId = requestHelper.getParamAsString(SearchParameters.CO_ID);
        List<String> regGroupStates = requestHelper.getParamAsList(SearchParameters.REGGROUP_STATES);

      
        List<Object[]> results = new ArrayList<Object[]>();
        
        for(Object[] resultRow : results){
            int i = 0;
            SearchResultRowInfo row = new SearchResultRowInfo();
            row.addCell(SearchResultColumns.AO_ID, (String)resultRow[i++]);
            row.addCell(SearchResultColumns.RG_ID, (String)resultRow[i++]);
            row.addCell(SearchResultColumns.RG_NAME, (String)resultRow[i++]);
            row.addCell(SearchResultColumns.RG_STATE, (String)resultRow[i]);
            resultInfo.getRows().add(row);
        }

        return resultInfo;
    }

    /**
     *
     * @param searchRequestInfo   Contains an Activity Offering ID that we will use to find the scheduleIds
     * @return ScheduleIds
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    protected SearchResultInfo searchForScheduleIdsByAoId(SearchRequestInfo searchRequestInfo) throws MissingParameterException, OperationFailedException, PermissionDeniedException {
        SearchRequestHelper requestHelper = new SearchRequestHelper(searchRequestInfo);

        String aoId = requestHelper.getParamAsString(SearchParameters.AO_ID);

        if (aoId == null || aoId.isEmpty()){
            throw new RuntimeException("Activity Offering id is required");
        }

        List<String> results = new ArrayList<String>();
        
        SearchResultInfo resultInfo = new SearchResultInfo();

        for (String result : results) {
            SearchResultRowInfo row = new SearchResultRowInfo();
            row.addCell(SearchResultColumns.SCHEDULE_ID, result);
            resultInfo.getRows().add(row);
        }
        return resultInfo;
    }

    private SearchResultInfo searchForAOsAndClustersByCoId(SearchRequestInfo searchRequestInfo) throws OperationFailedException{
        SearchResultInfo resultInfo = new SearchResultInfo();

        SearchRequestHelper requestHelper = new SearchRequestHelper(searchRequestInfo);
        String coId = requestHelper.getParamAsString(SearchParameters.CO_ID);
        List<String> aoStates = requestHelper.getParamAsList(SearchParameters.AO_STATES);

        List<Object[]> results = new ArrayList<Object[]>();

        Map<String, SearchResultRowInfo> aoMap = new HashMap<String, SearchResultRowInfo>();
        for(Object[] resultRow : results){
            int i = 0;
            String aoId = (String)resultRow[RESULTROW_AOID_OFFSET];

            SearchResultRowInfo row;
            if(aoId == null || (row = aoMap.get(aoId)) == null) {
                row = new SearchResultRowInfo();
                row.addCell(SearchResultColumns.FO_ID, (String)resultRow[i++]);
                row.addCell(SearchResultColumns.FO_NAME, (String)resultRow[i++]);
                row.addCell(SearchResultColumns.FORMAT_ID, (String)resultRow[i++]);
                row.addCell(SearchResultColumns.AOC_ID, (String)resultRow[i++]);
                row.addCell(SearchResultColumns.AOC_NAME, (String)resultRow[i++]);
                row.addCell(SearchResultColumns.AOC_PRIVATE_NAME, (String)resultRow[i++]);
                row.addCell(SearchResultColumns.AO_ID, (String)resultRow[i++]);
                row.addCell(SearchResultColumns.AO_TYPE, (String)resultRow[i++]);
                row.addCell(SearchResultColumns.AO_STATE, (String)resultRow[i++]);
                row.addCell(SearchResultColumns.SCHEDULE_ID, (String)resultRow[i++]);
                row.addCell(SearchResultColumns.AO_MAX_SEATS, resultRow[i]==null?null:resultRow[i].toString());
                i++; // increment from previous row
                row.addCell(SearchResultColumns.AO_CODE, (String)resultRow[i++]);
                row.addCell(SearchResultColumns.ATP_ID, resultRow[i]==null?null:resultRow[i].toString());
                resultInfo.getRows().add(row);
                aoMap.put(aoId, row);
            } else {
                row.addCell(SearchResultColumns.SCHEDULE_ID, (String)resultRow[RESULTROW_SCHED_OFFSET]);
            }
        }

        return resultInfo;
    }

    private SearchResultInfo searchForAOIdsFOIdsByCoId(SearchRequestInfo searchRequestInfo) throws OperationFailedException, InvalidParameterException, MissingParameterException, PermissionDeniedException{
        SearchResultInfo resultInfo = new SearchResultInfo();

        SearchRequestHelper requestHelper = new SearchRequestHelper(searchRequestInfo);
        String coId = requestHelper.getParamAsString(SearchParameters.CO_ID);

        // co2fo, fo2ao
        ContextInfo contextInfo = new ContextInfo();
        
        contextInfo.setCurrentDate(new Date());
        
        List<String> foIds = luiService.getLuiIdsByLuiAndRelationType(coId, LuiServiceConstants.LUI_LUI_RELATION_DELIVERED_VIA_CO_TO_FO_TYPE_KEY, contextInfo);
        
        
        for (String foId : foIds) {
		
        	List<String> currentAoIds = luiService.getLuiIdsByLuiAndRelationType(foId, LuiServiceConstants.LUI_LUI_RELATION_DELIVERED_VIA_FO_TO_AO_TYPE_KEY, contextInfo); 
		
        	for (String aoId : currentAoIds) {
				
        		 SearchResultRowInfo row = new SearchResultRowInfo();
                 row.addCell(SearchResultColumns.AO_ID, aoId);
                 row.addCell(SearchResultColumns.FO_ID, foId);
                 resultInfo.getRows().add(row);
			}
        }
        
       
        return resultInfo;
    }

    protected SearchResultInfo searchForAOsWithoutClusterByFormatOffering(SearchRequestInfo searchRequestInfo) throws OperationFailedException{
        SearchResultInfo resultInfo = new SearchResultInfo();

        SearchRequestHelper requestHelper = new SearchRequestHelper(searchRequestInfo);
        String foId = requestHelper.getParamAsString(SearchParameters.FO_ID);

       
        List<Object[]> results = new ArrayList<Object[]>();

        for(Object[] resultRow : results){
            int i = 0;
            SearchResultRowInfo row = new SearchResultRowInfo();
            row.addCell(SearchResultColumns.AO_ID, (String)resultRow[i]);
            resultInfo.getRows().add(row);
        }

        return resultInfo;
    }

    protected SearchResultInfo searchForFOByCOId(SearchRequestInfo searchRequestInfo) throws OperationFailedException{
        SearchResultInfo resultInfo = new SearchResultInfo();

        SearchRequestHelper requestHelper = new SearchRequestHelper(searchRequestInfo);
        String coId = requestHelper.getParamAsString(SearchParameters.CO_ID);

      
        List<Object[]> results = new ArrayList<Object[]>();

        for(Object[] resultRow : results){
            int i = 0;
            SearchResultRowInfo row = new SearchResultRowInfo();
            row.addCell(SearchResultColumns.FO_ID, (String)resultRow[i++]);
            row.addCell(SearchResultColumns.FO_NAME, (String)resultRow[i]);
            resultInfo.getRows().add(row);
        }

        return resultInfo;
    }

    protected SearchResultInfo searchForFOIdsByCOId(SearchRequestInfo searchRequestInfo) throws OperationFailedException{
        SearchResultInfo resultInfo = new SearchResultInfo();

        SearchRequestHelper requestHelper = new SearchRequestHelper(searchRequestInfo);
        String coId = requestHelper.getParamAsString(SearchParameters.CO_ID);

        
        List<String> results = new ArrayList<String>();

        for(String result : results){
            SearchResultRowInfo row = new SearchResultRowInfo();
            row.addCell(SearchResultColumns.FO_ID, result);
            resultInfo.getRows().add(row);
        }

        return resultInfo;
    }

    protected SearchResultInfo searchForWLIndicatorByCOId(SearchRequestInfo searchRequestInfo) throws OperationFailedException{
        SearchResultInfo resultInfo = new SearchResultInfo();

        SearchRequestHelper requestHelper = new SearchRequestHelper(searchRequestInfo);
        String coId = requestHelper.getParamAsString(SearchParameters.CO_ID);

        List<String> results = new ArrayList<String>();

        for(String result : results){
            SearchResultRowInfo row = new SearchResultRowInfo();
            row.addCell(SearchResultColumns.WL_IND, result);
            resultInfo.getRows().add(row);
        }

        return resultInfo;
    }

    /**
     * Finds a list of AO codes and Ids given a CO id.
     * @throws OperationFailedException 
     */
    private SearchResultInfo searchForTermIdByOfferingId(SearchRequestInfo searchRequestInfo) throws OperationFailedException {
        SearchResultInfo resultInfo = new SearchResultInfo();
        SearchRequestHelper requestHelper = new SearchRequestHelper(searchRequestInfo);
        String offeringId = requestHelper.getParamAsString(SearchParameters.OFFERING_ID);

        return resultInfo;
    }

    /**
     * This method will return a AO cluster count based on the FO Id passed in.
     *
     * This was made as a performance improvement.
     *
     * @param searchRequestInfo FO id search params
     * @return a AO cluster count
     * @throws OperationFailedException 
     */
    private SearchResultInfo searchForAOClusterCountByFO(SearchRequestInfo searchRequestInfo) throws OperationFailedException {
        SearchResultInfo resultInfo = new SearchResultInfo();

        SearchRequestHelper requestHelper = new SearchRequestHelper(searchRequestInfo);
        String formatOfferingId = requestHelper.getParamAsString(SearchParameters.FO_ID);

      
        List<Long> results = new ArrayList<Long>();

        for(Long result : results){
            SearchResultRowInfo row = new SearchResultRowInfo();
            row.addCell(SearchResultColumns.AO_CLUSTER_COUNT, result.toString());
            resultInfo.getRows().add(row);
        }

        return resultInfo;
    }

    /**
     * This method will return AO_ID, AO_TYPE  based on the FO Id passed in.
     *
     * This was made as a performance improvement.
     *
     * @param searchRequestInfo FO id search params
     * @return AO_ID, AO_TYPE
     * @throws OperationFailedException 
     */
    private SearchResultInfo searchForAoIdAndTypeByFO(SearchRequestInfo searchRequestInfo) throws OperationFailedException {
        SearchResultInfo resultInfo = new SearchResultInfo();

        SearchRequestHelper requestHelper = new SearchRequestHelper(searchRequestInfo);
        String formatOfferingId = requestHelper.getParamAsString(SearchParameters.FO_ID);

        List<Object[]> results = new ArrayList<Object[]>();

        for(Object[] resultRow : results){
            int i = 0;
            SearchResultRowInfo row = new SearchResultRowInfo();
            row.addCell(SearchResultColumns.AO_ID, (String)resultRow[i++]);
            row.addCell(SearchResultColumns.AO_TYPE, (String)resultRow[i]);
            resultInfo.getRows().add(row);
        }

        return resultInfo;
    }

    private static String commaString(List<String> items){
        return items.toString().replace("[", "'").replace("]", "'").replace(", ", "','");
    }

}
