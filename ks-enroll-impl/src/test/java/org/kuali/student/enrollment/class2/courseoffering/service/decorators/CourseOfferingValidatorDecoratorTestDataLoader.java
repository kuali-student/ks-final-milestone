package org.kuali.student.enrollment.class2.courseoffering.service.decorators;

import org.kuali.student.r2.common.class1.search.SearchServiceMockImpl;
import org.kuali.student.r2.common.class1.search.SearchServiceMockImplAbstractDataLoader;

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
        params.put("coId", new String[]{"co1"});

        row1 = new HashMap<String, String>();
        row1.put("aoId", "ao1");
        row1.put("aoCode", "A");

        Map<String,String> row2 = new HashMap<String, String>();
        row2.put("aoId", "ao2");
        row2.put("aoCode", "B");

        this.searchServiceMock.addSearchResult(makeSearchRequestInfo("kuali.search.type.lui.searchForAoCodesByCoId", params), makeSearchResultInfo(Arrays.asList(row1, row2)));
    }
}
