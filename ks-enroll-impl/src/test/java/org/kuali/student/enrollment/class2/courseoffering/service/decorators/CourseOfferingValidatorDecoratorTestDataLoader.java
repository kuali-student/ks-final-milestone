package org.kuali.student.enrollment.class2.courseoffering.service.decorators;

import org.kuali.student.r2.common.class1.search.SearchServiceMockImpl;
import org.kuali.student.r2.common.class1.search.SearchServiceMockImplAbstractDataLoader;
import org.kuali.student.r2.core.class1.search.ActivityOfferingSearchServiceImpl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * A data loader for the searches used in the CourseOfferingValidatorDecorator unit tests.
 */
public class CourseOfferingValidatorDecoratorTestDataLoader extends SearchServiceMockImplAbstractDataLoader {

    public CourseOfferingValidatorDecoratorTestDataLoader(SearchServiceMockImpl searchServiceMock) {
        super(searchServiceMock);
        loadData();
    }

    @Override
    protected void loadData() {
        /*  Search request and result for Clu related types */
        Map<String, String[]> params = new HashMap<String, String[]>();
        params.put("lu.queryParam.cluId", new String[]{"fo2"});
        params.put("lu.queryParam.luOptionalRelationType", new String[]{"luLuRelationType.contains"});

        Map<String,String> row1 = new HashMap<String, String>();
        row1.put("lu.resultColumn.cluType", "kuali.lu.type.activity.Lecture");

        this.searchServiceMock.addSearchResult(makeSearchRequestInfo("lu.search.relatedTypes", params), makeSearchResultInfo(Arrays.asList(row1)));

        /* Search request and result for AO Codes By CO Id */
        params = new HashMap<String, String[]>();
        params.put(ActivityOfferingSearchServiceImpl.SearchResultColumns.CO_ID, new String[]{"co1"});

        row1 = new HashMap<String, String>();
        row1.put(ActivityOfferingSearchServiceImpl.SearchResultColumns.AO_ID, "ao1");
        row1.put(ActivityOfferingSearchServiceImpl.SearchResultColumns.AO_CODE, "A");
        row1.put(ActivityOfferingSearchServiceImpl.SearchResultColumns.AO_TYPE, "kuali.lui.type.activity.offering.lecture");

        Map<String,String> row2 = new HashMap<String, String>();
        row2.put(ActivityOfferingSearchServiceImpl.SearchResultColumns.AO_ID, "ao2");
        row2.put(ActivityOfferingSearchServiceImpl.SearchResultColumns.AO_CODE, "B");
        row2.put(ActivityOfferingSearchServiceImpl.SearchResultColumns.AO_TYPE, "kuali.lui.type.activity.offering.lecture");

        this.searchServiceMock.addSearchResult(makeSearchRequestInfo(ActivityOfferingSearchServiceImpl.AO_CODES_TYPES_BY_CO_ID_SEARCH_KEY, params), makeSearchResultInfo(Arrays.asList(row1, row2)));

        /* Search request and result for Term Id By offering Id */
        params = new HashMap<String, String[]>();
        params.put("offeringId", new String[]{"offering1"});
        Map<String, String> row3 = new HashMap<String, String>();
        row3.put(ActivityOfferingSearchServiceImpl.SearchResultColumns.ATP_ID, "termH2G242");
        this.searchServiceMock.addSearchResult(makeSearchRequestInfo(ActivityOfferingSearchServiceImpl.TERM_ID_BY_OFFERING_ID_SEARCH_KEY, params), makeSearchResultInfo(Arrays.asList(row3)));
    }
}
