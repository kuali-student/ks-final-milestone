package org.kuali.student.r2.core.class1.search;

import org.apache.commons.lang.StringUtils;
import org.kuali.student.r2.common.class1.search.SearchServiceAbstractHardwiredImplBase;
import org.kuali.student.r2.common.dto.ContextInfo;
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
public class InstructorSearchServiceImpl extends SearchServiceAbstractHardwiredImplBase {

    @Resource
    private EntityManager entityManager;

    public static final TypeInfo INSTR_SEARCH_BY_AO_IDS_SEARCH_TYPE;

    public static final String INSTR_SEARCH_BY_AO_IDS_SEARCH_KEY = "kuali.search.type.lpr.searchForInstructorByAoIds";

    public static final class SearchParameters {
        public static final String AO_IDS = "aoIds";
    }

    public static final class SearchResultColumns {
        public static final String AO_ID = "id";
        public static final String AO_PERS_ID = "weekdays";
        public static final String AO_LPR_TYPE = "startTimeMillis";
    }

    static {
        TypeInfo info = new TypeInfo();
        info.setKey(INSTR_SEARCH_BY_AO_IDS_SEARCH_KEY);
        info.setName("Instructor search by AO Ids");
        info.setDescr(new RichTextHelper().fromPlain("Return a list of instructors for a list of AOs"));

        try {
            info.setEffectiveDate(DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.parse("01/01/2012"));
        } catch ( IllegalArgumentException ex) {
            throw new RuntimeException("bad code");
        }
        INSTR_SEARCH_BY_AO_IDS_SEARCH_TYPE = info;
    }


    /**
     * Get the search type that the sub class implements.
     */
    @Override
    public TypeInfo getSearchType() {
        return INSTR_SEARCH_BY_AO_IDS_SEARCH_TYPE;
    }


    @Override
    public SearchResultInfo search(SearchRequestInfo searchRequestInfo, ContextInfo contextInfo) throws MissingParameterException, OperationFailedException, PermissionDeniedException {

        // As this class expands, you can add multiple searches. Ie. right now there is only one search (so only one search key).
        if (StringUtils.equals(searchRequestInfo.getSearchKey(), INSTR_SEARCH_BY_AO_IDS_SEARCH_TYPE.getKey())) {
            return searchForInstructorsByAoIds(searchRequestInfo, contextInfo);
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
    protected SearchResultInfo searchForInstructorsByAoIds(SearchRequestInfo searchRequestInfo, ContextInfo contextInfo) throws MissingParameterException, OperationFailedException, PermissionDeniedException {
        SearchRequestHelper requestHelper = new SearchRequestHelper(searchRequestInfo);

        List<String> aoIds = requestHelper.getParamAsList(SearchParameters.AO_IDS);

        if (aoIds == null || aoIds.isEmpty()){
            throw new RuntimeException("AO Ids are required");
        }

        String aoIdsString = commaString(aoIds);

        String query =
                " Select "   +
                "  lui.id, " +
                "  lpr.personId, " +
                "  lpr.personRelationTypeId " +
                " FROM " +
                        "    LuiEntity lui, " +
                        "    LprEntity lpr " +
                " WHERE " +
                "    lui.id in ("+ aoIdsString +") " +
                        " AND lpr.luiId = lui.id ";


        List<Object[]> results = getEntityManager().createQuery(query).getResultList();

        SearchResultInfo resultInfo = new SearchResultInfo();
        resultInfo.setTotalResults(results.size());
        resultInfo.setStartAt(0);

        for (Object[] result : results) {
            SearchResultRowInfo row = new SearchResultRowInfo();

            int i=0;

            row.addCell(SearchResultColumns.AO_ID,(String)result[i++]);
            row.addCell(SearchResultColumns.AO_PERS_ID,(String)result[i++]);
            row.addCell(SearchResultColumns.AO_LPR_TYPE,(String)result[i++]);
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
