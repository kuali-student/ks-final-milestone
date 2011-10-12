package org.kuali.student.enrollment.class1.lrr.termresolver;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.kuali.rice.krms.api.engine.TermSpecification;
import org.kuali.student.common.util.krms.RulesExecutionConstants;
import org.kuali.student.enrollment.lpr.service.LuiPersonRelationService;
import org.kuali.student.enrollment.lrr.service.LearningResultRecordService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * @author alubbers
 *
 * Test class for checking term resolver for completed courses
 *
 */
public class TestCompletedCoursesResolver {

    private LuiPersonRelationService lprServiceValidation;
    private LearningResultRecordService lrrServiceValidation;
    public static String principalId = "123";
    public ContextInfo callContext = ContextInfo.newInstance();

    @Autowired
    public void setLprServiceValidation(LuiPersonRelationService luiServiceValidation) {
        this.lprServiceValidation = luiServiceValidation;
    }

    @Autowired
    public void setLrrServiceValidation(LearningResultRecordService lrrServiceValidation) {
        this.lrrServiceValidation = lrrServiceValidation;
    }

    @Before
    public void setUp() {
        principalId = "123";
        callContext = ContextInfo.getInstance(callContext);
        callContext.setPrincipalId(principalId);
    }

    @Test
    @Ignore
    // TODO add real test data
    public void testResolveTerm() {
        CompletedCoursesResolver resovler = new CompletedCoursesResolver();
        resovler.setLrrService(lrrServiceValidation);
        resovler.setLprService(lprServiceValidation);

        Map<TermSpecification, Object> prereqs = new HashMap<TermSpecification, Object>();
        prereqs.put(RulesExecutionConstants.studentIdTermSpec, "1001");
        prereqs.put(RulesExecutionConstants.contextInfoTermSpec, callContext);

        Collection<String> completedCourses = resovler.resolve(prereqs, new HashMap<String, String>());

        List<String> expectedResults = Arrays.asList(new String[]{"1", "2"});

        expectedResults.removeAll(completedCourses);

        assertTrue(expectedResults.isEmpty());
    }

}
