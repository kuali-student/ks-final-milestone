/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.r2.common.class1.search;

import org.junit.*;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;
import org.kuali.student.r2.core.search.service.SearchService;
import org.kuali.student.r2.core.search.util.SearchResultHelper;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;

import static org.junit.Assert.assertEquals;

/**
 *
 * @author nwright
 */
public class TestSearchServiceDispatcherImpl {

    public TestSearchServiceDispatcherImpl() {
    }
    public static String principalId = "123";
    public ContextInfo callContext = null;
    private SearchService searchService;

    @Before
    public void setUp() {
        SearchServiceDispatcherImpl dispatcher = new SearchServiceDispatcherImpl();
        dispatcher.getSearchServices().add(new TestSearchService1 ());
        dispatcher.getSearchServices().add(new TestSearchService2 ());
        searchService = dispatcher;
        principalId = "123";
        callContext = new ContextInfo();
        callContext.setPrincipalId(principalId);
//        try {
//            loadData();
//        } catch (Exception ex) {
//            throw new RuntimeException(ex);
//        }

    }

    @BeforeClass
    public static void setUpClass()
            throws Exception {
    }

    @AfterClass
    public static void tearDownClass()
            throws Exception {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testSearch()
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        System.out.println("testSearch");
        TypeInfo actType = searchService.getSearchType(TestSearchService1.SEARCH_TYPE.getKey(), callContext);
        assertEquals(TestSearchService1.SEARCH_TYPE.getKey(), actType.getKey());

        actType = searchService.getSearchType(TestSearchService2.SEARCH_TYPE.getKey(), callContext);
        assertEquals(TestSearchService2.SEARCH_TYPE.getKey(), actType.getKey());

        SearchRequestInfo request = new SearchRequestInfo();
        request.setSearchKey(TestSearchService1.SEARCH_TYPE.getKey());
        SearchResultInfo result = searchService.search(request, callContext);
        assertEquals(1, result.getRows().size());
        SearchResultHelper helper = new SearchResultHelper (result);
        assertEquals ("value1", helper.get(0, "test1"));
        
        request = new SearchRequestInfo();
        request.setSearchKey(TestSearchService2.SEARCH_TYPE.getKey());
        result = searchService.search(request, callContext);
        assertEquals(1, result.getRows().size());
        helper = new SearchResultHelper (result);
        assertEquals ("value2", helper.get(0, "test2"));
    }

    public static class TestSearchService1
            extends SearchServiceAbstractHardwiredImpl {

        public static final TypeInfo SEARCH_TYPE;

        static {
            TypeInfo info = new TypeInfo();
            info.setKey("test.search.type.1");
            SEARCH_TYPE = info;
        }

        @Override
        public TypeInfo getSearchType() {
            return SEARCH_TYPE;
        }

        @Override
        public SearchResultInfo search(SearchRequestInfo searchRequestInfo, ContextInfo contextInfo)
                throws MissingParameterException,
                OperationFailedException,
                PermissionDeniedException {
            if (!searchRequestInfo.getSearchKey().equals(SEARCH_TYPE.getKey())) {
                throw new OperationFailedException("Unsupported search type: " + searchRequestInfo.getSearchKey());
            }
            SearchResultInfo info = new SearchResultInfo();
            SearchResultRowInfo row = new SearchResultRowInfo();
            info.getRows().add(row);
            row.addCell("test1", "value1");
            return info;

        }
    }

    public static class TestSearchService2
            extends SearchServiceAbstractHardwiredImpl {

        public static final TypeInfo SEARCH_TYPE;

        static {
            TypeInfo info = new TypeInfo();
            info.setKey("test.search.type.2");
            SEARCH_TYPE = info;
        }

        @Override
        public TypeInfo getSearchType() {
            return SEARCH_TYPE;
        }

        @Override
        public SearchResultInfo search(SearchRequestInfo searchRequestInfo, ContextInfo contextInfo)
                throws MissingParameterException,
                OperationFailedException,
                PermissionDeniedException {
            if (!searchRequestInfo.getSearchKey().equals(SEARCH_TYPE.getKey())) {
                throw new OperationFailedException("Unsupported search type: " + searchRequestInfo.getSearchKey());
            }
            SearchResultInfo info = new SearchResultInfo();
            SearchResultRowInfo row = new SearchResultRowInfo();
            info.getRows().add(row);
            row.addCell("test2", "value2");
            return info;

        }
    }
}
