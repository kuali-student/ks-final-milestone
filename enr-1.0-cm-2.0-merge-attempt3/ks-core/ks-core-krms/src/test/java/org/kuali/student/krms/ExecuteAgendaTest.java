package org.kuali.student.krms;

import org.junit.Assert;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.kuali.rice.krms.api.engine.*;
import org.kuali.rice.krms.api.repository.agenda.AgendaDefinition;
import org.kuali.rice.krms.framework.engine.*;
import org.kuali.rice.krms.framework.engine.expression.ComparisonOperator;
import org.kuali.rice.krms.framework.engine.expression.ComparisonOperatorServiceImpl;
import org.kuali.student.common.util.krms.ManualContextProvider;

import java.util.*;

public class ExecuteAgendaTest {
    /*private static final ResultLogger LOG = ResultLogger.getInstance();

    private ComparisonOperator operatorGreaterThan;
    private ComparisonOperator operatorEquals;

    private Proposition oneCourseFromList;
    private Proposition permFromDept;

    private static final Term permissionTerm = new Term("Departmental Permission");
    private static final Term numberOfCoursesTerm = new Term("Number of Courses from List");

    private static final TermResolver<Boolean> permissionTermResolver = new TermResolverMock<Boolean>("Departmental Permission", Boolean.TRUE);
    private static final TermResolver<Integer> numberOfCoursesTermResolver = new TermResolverMock<Integer>("Number of Courses from List", 2);

    @Before
    public void setUp() {
        operatorGreaterThan = ComparisonOperator.GREATER_THAN;
        operatorGreaterThan.setComparisonOperatorService(ComparisonOperatorServiceImpl.getInstance());

        operatorEquals = ComparisonOperator.EQUALS;
        operatorEquals.setComparisonOperatorService(ComparisonOperatorServiceImpl.getInstance());

        oneCourseFromList = new ComparableTermBasedProposition(operatorGreaterThan, numberOfCoursesTerm, 1);
        permFromDept = new ComparableTermBasedProposition(ComparisonOperator.EQUALS, permissionTerm, Boolean.TRUE);

        ActionMock.resetActionsFired();
    }


    @Test
    public void testAllRulesAgenda() {

        Rule rule1 = new BasicRule("GEOG123 Prerequisites", oneCourseFromList, Collections.<Action>singletonList(new ActionMock("a1")));
        Rule rule2 = new BasicRule("GEOG123 Corequisites", permFromDept, Collections.<Action>singletonList(new ActionMock("a2")));

        AgendaTreeEntry entry1 = new BasicAgendaTreeEntry(rule1);
        AgendaTreeEntry entry2 = new BasicAgendaTreeEntry(rule2);
        BasicAgendaTree agendaTree = new BasicAgendaTree(entry1, entry2);

        Agenda agenda = new BasicAgenda(Collections.singletonMap(AgendaDefinition.Constants.EVENT, "GEOG123 Course Requirements"), agendaTree);

        execute(agenda);

        Assert.assertTrue(ActionMock.actionFired("a1"));
        Assert.assertTrue(ActionMock.actionFired("a2"));
    }                      */

    /**
     * execute the engine against a trivial context containing the given agenda.
     * a default agenda qualifier of Event=test will be used.
     * @param agenda
     */
    private void execute(Agenda agenda) {
        execute(agenda, Collections.singletonMap(AgendaDefinition.Constants.EVENT, "GEOG123 Course Requirements"));
    }

    /**
     * execute the engine against a trivial context containing the given agenda.
     * the given agenda qualifier will be used.
     * @param agenda
     * @param agendaQualifiers
     */
    private void execute(Agenda agenda, Map<String, String> agendaQualifiers) {
        Map<String, String> contextQualifiers = new HashMap<String, String>();
        contextQualifiers.put("docTypeName", "Proposal");

        List<TermResolver<?>> testResolvers = new ArrayList<TermResolver<?>>();
        //testResolvers.add(permissionTermResolver);
        //testResolvers.add(numberOfCoursesTermResolver);

        Context context = new BasicContext(Arrays.asList(agenda), testResolvers);
        ContextProvider contextProvider = new ManualContextProvider(context);

        SelectionCriteria selectionCriteria = SelectionCriteria.createCriteria(null, contextQualifiers,
                agendaQualifiers);

        ProviderBasedEngine engine = new ProviderBasedEngine();
        engine.setContextProvider(contextProvider);

        // Set execution options to log execution
        ExecutionOptions executionOptions = new ExecutionOptions().setFlag(ExecutionFlag.LOG_EXECUTION, true);

        EngineResults results = engine.execute(selectionCriteria, Facts.EMPTY_FACTS, executionOptions);
        Assert.assertNotNull(results);
    }

}
