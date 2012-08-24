package org.kuali.student.r2.core.class1.search;

import org.apache.commons.lang.StringUtils;
import org.kuali.student.r2.common.dao.GenericEntityDao;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;
import org.kuali.student.r2.core.search.util.SearchRequestHelper;

import javax.persistence.TemporalType;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: venkat
 * Date: 8/15/12
 * Time: 3:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class CourseOfferingHistorySearchImpl extends SearchServiceAbstractHardwiredImpl {

    private String noOfYears;

    private GenericEntityDao genericEntityDao;

    public static final String COURSE_ID = "cluId";

    public static final TypeInfo PAST_CO_SEARCH;

    static {
        TypeInfo info = new TypeInfo();
        info.setKey("kuali.search.type.lui.pastCourseOfferings");
        info.setName("Past COs");
        info.setDescr(new RichTextHelper().fromPlain("Get all the past 5 years Course Offerings"));
        DateFormat mmddyyyy = new SimpleDateFormat("MM/dd/yyyy");
        try {
            info.setEffectiveDate(mmddyyyy.parse("01/01/2012"));
        } catch (ParseException ex) {
            throw new RuntimeException("bad code");
        }
        PAST_CO_SEARCH = info;
    }

    @Override
    public TypeInfo getSearchType() {
        return PAST_CO_SEARCH;
    }

    @Override
    public SearchResultInfo search(SearchRequestInfo searchRequestInfo, ContextInfo contextInfo)
            throws MissingParameterException, OperationFailedException, PermissionDeniedException {

        if (!StringUtils.equals(searchRequestInfo.getSearchKey(),PAST_CO_SEARCH.getKey())) {
            throw new OperationFailedException("Unsupported search type: " + searchRequestInfo.getSearchKey());
        }

        SearchRequestHelper requestHelper = new SearchRequestHelper(searchRequestInfo);

        String courseId = requestHelper.getParamAsString(COURSE_ID);

        if (StringUtils.isEmpty(courseId)){
            throw new RuntimeException("Course id is required");
        }

        if (StringUtils.isEmpty(noOfYears)){
            throw new RuntimeException("No of years should be configured");
        }

        Date startDate = null;
        int year = Calendar.getInstance().get(Calendar.YEAR) - Integer.parseInt(noOfYears);

        try {
            startDate = new SimpleDateFormat("MM/dd/yyyy").parse("01/01/" + year);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        List<String> luiIds = genericEntityDao.getEm().createQuery("select lui.id from LuiEntity lui,AtpEntity atp " +
                "where lui.atpId=atp.id and lui.cluId = :cluId and " +
                "lui.luiType = '" + LuiServiceConstants.COURSE_OFFERING_TYPE_KEY + "' and " +
                "lui.luiState = '" + LuiServiceConstants.LUI_CO_STATE_OFFERED_KEY + "' " +
                "and atp.startDate >= :startDate").setParameter("startDate", startDate, TemporalType.DATE).setParameter("cluId", courseId).getResultList();

        SearchResultInfo resultInfo = new SearchResultInfo();
        resultInfo.setTotalResults(luiIds.size());
        resultInfo.setStartAt(0);

        for (String luiId : luiIds) {
            SearchResultRowInfo row = new SearchResultRowInfo();
            resultInfo.getRows().add(row);
            row.addCell("courseOfferingId",luiId);
        }

        return resultInfo;
    }

    public void setGenericEntityDao(GenericEntityDao genericEntityDao) {
        this.genericEntityDao = genericEntityDao;
    }

    public void setNoOfYears(String noOfYears) {
        this.noOfYears = noOfYears;
    }
}
