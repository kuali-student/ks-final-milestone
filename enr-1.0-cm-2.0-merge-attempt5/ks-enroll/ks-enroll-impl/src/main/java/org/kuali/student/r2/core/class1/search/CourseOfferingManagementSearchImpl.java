package org.kuali.student.r2.core.class1.search;

import org.apache.commons.lang.StringUtils;
import org.kuali.student.r2.common.class1.search.SearchServiceAbstractHardwiredImpl;
import org.kuali.student.r2.common.dao.GenericEntityDao;
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

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: swedev
 * Date: 11/18/12
 * Time: 4:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class CourseOfferingManagementSearchImpl extends SearchServiceAbstractHardwiredImpl {

    private GenericEntityDao genericEntityDao;

    public static final String COURSE_IDS = "courseOfferingIds";       // these are really luiIds

    public static final TypeInfo CO_MANAGEMENT_SEARCH;

    public static final String CO_MANAGEMENT_SEARCH_KEY = "kuali.search.type.lui.courseOfferingManagementDisplay";

    static {
        TypeInfo info = new TypeInfo();
        info.setKey(CO_MANAGEMENT_SEARCH_KEY);
        info.setName("Manage Course Offering Search");
        info.setDescr(new RichTextHelper().fromPlain("Return search results from manage course offering screen quickly"));

        try {
            info.setEffectiveDate(DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.parse("01/01/2012"));
        } catch ( IllegalArgumentException ex) {
            throw new RuntimeException("bad code");
        }
        CO_MANAGEMENT_SEARCH = info;
    }

    @Override
    public TypeInfo getSearchType() {
        return CO_MANAGEMENT_SEARCH;
    }

    @Override
    public SearchResultInfo search(SearchRequestInfo searchRequestInfo, ContextInfo contextInfo)
            throws MissingParameterException, OperationFailedException, PermissionDeniedException {

        if (!StringUtils.equals(searchRequestInfo.getSearchKey(), CO_MANAGEMENT_SEARCH.getKey())) {
            throw new OperationFailedException("Unsupported search type: " + searchRequestInfo.getSearchKey());
        }

        SearchRequestHelper requestHelper = new SearchRequestHelper(searchRequestInfo);

        List<String> courseIds = requestHelper.getParamAsList(COURSE_IDS);

        if (courseIds == null || courseIds.isEmpty()){
            throw new RuntimeException("Course ids are required");
        }

        List<Object[]> results = genericEntityDao.getEm().createQuery("SELECT" +
                "    ident.code," +
                "    ident.longName," +
                "    lui.luiState," +
                "    lui_rvg1," +
                "    lui_rvg2,        " +
                "    lui.id,        " +
                "    ident.division        " +
                "FROM" +
                "    LuiIdentifierEntity ident," +
                "    LuiEntity lui," +
                "    IN(lui.resultValuesGroupKeys) lui_rvg1," +
                "    ResultValuesGroupEntity lrc_rvg1,    " +
                "    IN(lui.resultValuesGroupKeys) lui_rvg2," +
                "    ResultValuesGroupEntity lrc_rvg2  " +
                "WHERE" +
                "    lui.id = ident.lui.id" +
                "    AND lrc_rvg1.id = lui_rvg1" +
                "    AND lrc_rvg1.resultScaleId LIKE 'kuali.result.scale.credit.%'    " +
                "    AND lrc_rvg2.id = lui_rvg2" +
                "    AND lrc_rvg2.resultScaleId LIKE 'kuali.result.scale.grade.%'" +
                //Exclude these two types that can cause duplicates.
                // audit and passfail are moved into different fields, after that there can be only one grading option
                // of Satisfactory, Letter, or Percentage
                "    AND lrc_rvg2 NOT IN ('kuali.resultComponent.grade.audit','kuali.resultComponent.grade.passFail')" +
                "    AND ident.lui.id IN  ("+ commaString(courseIds) +")", Object[].class).getResultList();

        SearchResultInfo resultInfo = new SearchResultInfo();
        resultInfo.setTotalResults(results.size());
        resultInfo.setStartAt(0);

        for (Object[] result : results) {
            SearchResultRowInfo row = new SearchResultRowInfo();
            resultInfo.getRows().add(row);
            int i=0;
            row.addCell("courseOfferingCode",(String)result[i++]);
            row.addCell("courseOfferingDesc",(String)result[i++]);
            row.addCell("courseOfferingState",(String)result[i++]);
            row.addCell("courseOfferingCreditOption",(String)result[i++]);
            row.addCell("courseOfferingGradingOption",(String)result[i++]);
            row.addCell("courseOfferingId",(String)result[i++]);
            row.addCell("subjectArea",(String)result[i++]);
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

    public void setGenericEntityDao(GenericEntityDao genericEntityDao) {
        this.genericEntityDao = genericEntityDao;
    }





}
