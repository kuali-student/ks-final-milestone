package org.kuali.student.ap.coursesearch.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:ks-ap-test-context.xml" })
public class CourseFacetStrategyTest {

    public static final String principalId = "student1";
    public ContextInfo context;

    CourseFacetStrategyImpl strategy = null;

    @Before
    public void setUp() {
        context = new ContextInfo();
        context.setPrincipalId(principalId);
        strategy = new CourseFacetStrategyImpl();
    }

    @Test
    public void testIsThisThingOn() {
         assertNotNull("Strategy is null", strategy);
    }

    @Test
    public void testGetFacetColumnsReversed(){
        List<String> facets = strategy.getFacetColumnsReversed();
        assertEquals(5,facets.size());
        assertEquals("facet_quarter",facets.get(0));
        assertEquals("facet_genedureq",facets.get(1));
        assertEquals("facet_credits",facets.get(2));
        assertEquals("facet_level",facets.get(3));
        assertEquals("facet_curriculum",facets.get(4));
    }

    @Test
    public void testGetFacetColumns(){
        Map<String, Integer> facets = strategy.getFacetColumns();
        assertEquals((long)facets.get("facet_curriculum"),(long)4);
        assertEquals((long)facets.get("facet_credits"),(long)2);
        assertEquals((long)facets.get("facet_genedureq"),(long)1);
        assertEquals((long)facets.get("facet_level"),(long)3);
        assertEquals((long)facets.get("facet_quarter"),(long)0);
    }

}
