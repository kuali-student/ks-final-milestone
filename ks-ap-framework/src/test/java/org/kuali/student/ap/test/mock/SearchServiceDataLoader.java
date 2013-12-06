package org.kuali.student.ap.test.mock;

import org.kuali.student.r2.common.class1.search.SearchServiceMockImpl;
import org.kuali.student.r2.common.class1.search.SearchServiceMockImplAbstractDataLoader;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: chmaurer
 * Date: 11/22/13
 * Time: 3:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class SearchServiceDataLoader extends SearchServiceMockImplAbstractDataLoader {

    public SearchServiceDataLoader(SearchServiceMockImpl searchServiceMock) {
        super(searchServiceMock);
        loadData();
    }

    @Override
    protected void loadData() {
        /*  Search request and result for Clu related types */
        Map<String, String[]> params = new HashMap<String, String[]>();

        Map<String,String> row1 = new HashMap<String, String>();
        Map<String,String> row2 = new HashMap<String, String>();
        Map<String,String> row3 = new HashMap<String, String>();

        params.put( "subject", new String[]{"PHIL"} );
        row1.put("lu.resultColumn.cluId", "asdf");
        this.searchServiceMock.addSearchResult(makeSearchRequestInfo("ksap.course.prereqsearch.subject", params), makeSearchResultInfo(Arrays.asList(row1)));



        params = new HashMap<String, String[]>();
        row1 = new HashMap<String, String>();

        params.put( "subject", new String[]{"PHIL"} );
        params.put( "range", new String[]{"8__"} );
        row1.put("lu.resultColumn.cluId", "asdf");
        this.searchServiceMock.addSearchResult(makeSearchRequestInfo("ksap.course.prereqsearch.range", params), makeSearchResultInfo(Arrays.asList(row1)));

        params = new HashMap<String, String[]>();
        row1 = new HashMap<String, String>();
        row2 = new HashMap<String, String>();

        params.put( "subject", new String[]{"PHIL"} );
        params.put( "range", new String[]{"8__"} );
        row1.put("lu.resultColumn.cluId", "asdf");
        row1.put("lu.resultColumn.luOptionalCode", "868");
        row2.put("lu.resultColumn.cluId", "qwerty");
        row2.put("lu.resultColumn.luOptionalCode", "777");
        this.searchServiceMock.addSearchResult(makeSearchRequestInfo("ksap.course.prereqsearch.exclusions", params), makeSearchResultInfo(Arrays.asList(row1, row2)));

        params = new HashMap<String, String[]>();
        row1 = new HashMap<String, String>();
        row2 = new HashMap<String, String>();
        row3 = new HashMap<String, String>();
        row1.put("lu.resultColumn.division", "CHEM");
        row2.put("lu.resultColumn.division", "ENGL");
        row3.put("lu.resultColumn.division", "PHYS");
        this.searchServiceMock.addSearchResult(makeSearchRequestInfo("ksap.distinct.clu.divisions", params), makeSearchResultInfo(Arrays.asList(row1, row2, row3)));


    }
}