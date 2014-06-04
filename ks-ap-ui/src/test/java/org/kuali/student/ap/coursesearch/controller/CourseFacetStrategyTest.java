package org.kuali.student.ap.coursesearch.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertNotNull;

/**
 * Created with IntelliJ IDEA.
 * User: chmaurer
 * Date: 6/4/14
 * Time: 10:36 AM
 * To change this template use File | Settings | File Templates.
 */
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

}
