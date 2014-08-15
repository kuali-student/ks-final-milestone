package org.kuali.student.ap.search;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.framework.context.CourseSearchConstants;
import org.kuali.student.ap.framework.context.support.DefaultKsapContext;
import org.kuali.student.ap.framework.util.KsapHelperUtil;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


/**
 * Class to test methods in KsapSearchSupportImpl
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:ks-ap-test-context.xml" })
@TransactionConfiguration(transactionManager = "JtaTxManager", defaultRollback = true)
@Transactional
public class KsapSearchSupportImplTest {

    KsapSearchSupportImpl searchImpl;

    @Resource
    EntityManager emEntityManager;

    ContextInfo contextInfo = null;

    @Before
    public void setUp() throws Exception {
        DefaultKsapContext.before("student1");
        contextInfo= KsapFrameworkServiceLocator.getContext().getContextInfo();
        searchImpl = new KsapSearchSupportImpl();
        searchImpl.setEntityManager(emEntityManager);

    }

    @After
    public void tearDown() {
        DefaultKsapContext.after();
    }

    @Test
    public void testIsThisThingOn() throws Exception {
        assertNotNull("searchImpl is null", searchImpl);
        assertNotNull("contextInfo is null", contextInfo);
    }

    @Test
    public void testSearchForClusScheduledForTerms() throws Exception {
        String termId = "testAtpId1";
        Map<String,List<String>> offeredCourseIdMap= new HashMap<String,List<String>>();
        SearchRequestInfo request = new SearchRequestInfo(CourseSearchConstants.KSAP_COURSE_SEARCH_COURSEVERSIONIDS_BY_TERM_SCHEDULED_KEY);
        request.addParam(CourseSearchConstants.SearchParameters.ATP_ID, termId);
        List<SearchResultRowInfo> rows = searchImpl.search(request,
                contextInfo).getRows();
        for(SearchResultRowInfo row : rows){
            String id = KsapHelperUtil.getCellValue(row, CourseSearchConstants.SearchResultColumns.COURSE_VERSION_INDEPENDENT_ID);
            if(offeredCourseIdMap.containsKey(id)){
                List<String> offeredTermIds = offeredCourseIdMap.get(id);
                offeredTermIds.add(termId);
                offeredCourseIdMap.put(id,offeredTermIds);
            }else{
                List<String> offeredTermIds = new ArrayList<String>();
                offeredTermIds.add(termId);
                offeredCourseIdMap.put(id,offeredTermIds);
            }
        }
        assertEquals(1, offeredCourseIdMap.size());
    }
}
