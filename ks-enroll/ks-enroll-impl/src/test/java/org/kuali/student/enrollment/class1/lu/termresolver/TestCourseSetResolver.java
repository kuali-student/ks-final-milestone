package org.kuali.student.enrollment.class1.lu.termresolver;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.kuali.rice.krms.api.engine.TermSpecification;
import org.kuali.student.common.util.krms.RulesExecutionConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.lum.lu.service.LuService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * @author alubbers
 *
 * Test class for checking the term resolver for course sets
 */
public class TestCourseSetResolver {

    private LuService luServiceValidation;
    public static String principalId = "123";
    public ContextInfo callContext = ContextInfo.newInstance();

    @Autowired
    public void setLuServiceValidation(LuService luService) {
        this.luServiceValidation = luService;
    }

    @Before
    public void setUp() {
        principalId = "123";
        callContext = ContextInfo.getInstance(callContext);
        callContext.setPrincipalId(principalId);
    }

    @Test
    @Ignore
    // TODO use real test data
    public void testResolveTerm() {
        CourseSetResolver resolver = new CourseSetResolver();
        resolver.setLuService(luServiceValidation);

        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put(RulesExecutionConstants.COURSE_SET_ID_TERM_PROPERTY_NAME, "1");

        Map<TermSpecification, Object> prereqs = Collections.singletonMap(RulesExecutionConstants.contextInfoTermSpec, (Object)callContext);

        Collection<String> courseIds = resolver.resolve(prereqs, parameters);

        List<String> expectedResults = Arrays.asList(new String[]{"1", "2"});

        expectedResults.removeAll(courseIds);

        assertTrue(expectedResults.isEmpty());
    }

}
