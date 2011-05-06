package org.kuali.student.krms.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;
import org.kuali.rice.krms.api.engine.EngineResults;
import org.kuali.rice.krms.api.engine.ExecutionFlag;
import org.kuali.rice.krms.api.engine.ExecutionOptions;
import org.kuali.rice.krms.api.engine.ResultEvent;
import org.kuali.rice.krms.api.engine.SelectionCriteria;
import org.kuali.rice.krms.api.engine.Term;
import org.kuali.rice.krms.api.engine.TermResolver;
import org.kuali.rice.krms.framework.engine.Agenda;
import org.kuali.rice.krms.framework.engine.AgendaTree;
import org.kuali.rice.krms.framework.engine.AgendaTreeEntry;
import org.kuali.rice.krms.framework.engine.BasicAgenda;
import org.kuali.rice.krms.framework.engine.BasicAgendaTree;
import org.kuali.rice.krms.framework.engine.BasicAgendaTreeEntry;
import org.kuali.rice.krms.framework.engine.BasicContext;
import org.kuali.rice.krms.framework.engine.BasicRule;
import org.kuali.rice.krms.framework.engine.Context;
import org.kuali.rice.krms.framework.engine.ContextProvider;
import org.kuali.rice.krms.framework.engine.Proposition;
import org.kuali.rice.krms.framework.engine.ProviderBasedEngine;
import org.kuali.rice.krms.framework.engine.ResultLogger;
import org.kuali.rice.krms.framework.engine.Rule;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;

public class RulesExecutionTest {
    
    private static final ResultLogger LOG = ResultLogger.getInstance();

    private static List<TermResolver<?>> termResolvers;
    private static StatementServiceTranslationTest statementTranslator;
    private static SelectionCriteria selectionCriteria;
    private static Agenda agenda1;
    private static Agenda agenda2;
    private static Agenda agenda3;

    private static ExecutionOptions xOptions;
    
    @BeforeClass
    public static void setUp() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        termResolvers = new ArrayList<TermResolver<?>>();
        
        termResolvers.add(new CourseSetResolver());
        termResolvers.add(new CompletedCoursesResolver());
        termResolvers.add(new GradeForCourseResolver(new DummyLrcService()));
        termResolvers.add(new GpaForCourseResolver());
        termResolvers.add(new TestSetScoreResolver());
        termResolvers.add(new CompletedCreditsForCourseSetResolver());
        
        statementTranslator = new StatementServiceTranslationTest();
        statementTranslator.setUp();
        
        Map<String, String> contextQualifiers = new HashMap<String, String>();
        contextQualifiers.put("docTypeName", "Course.PreRequisities");
        
        Map<String, String> empty = Collections.emptyMap();
        selectionCriteria = SelectionCriteria.createCriteria(Constants.STATEMENT_EVENT_NAME, new Date(), contextQualifiers, empty);
        
        xOptions = new ExecutionOptions();
        xOptions.setFlag(ExecutionFlag.LOG_EXECUTION, true);
        
        agenda1 = statementTranslator.translateStatement("1");
        agenda2 = statementTranslator.translateStatement("2");
        agenda3 = statementTranslator.translateStatement("3");
        
        LOG.init();
    }
    
    private ProviderBasedEngine buildEngine(Agenda agenda) {
        Context context = new BasicContext(Arrays.asList(agenda), termResolvers);
        ContextProvider contextProvider = new ManualContextProvider(context);
        
        ProviderBasedEngine engine = new ProviderBasedEngine();
        engine.setContextProvider(contextProvider);
        
        return engine;
    }

    private HashMap<Term, Object> buildExecFacts(String studentId) {
        HashMap<Term, Object> execFacts = new HashMap<Term, Object>();
        execFacts.put(new Term(Constants.studentIdTermSpec), new String(studentId));
        
        return execFacts;
    }
    
    @Test
    public void executeStatementsForStudent1() {
        HashMap<Term, Object> execFacts = buildExecFacts("1");
        
        ProviderBasedEngine engine1 = buildEngine(agenda1);
        EngineResults results1 = engine1.execute(selectionCriteria, execFacts, xOptions);
        System.out.println("Results for Student 1, agenda 1");
        printEngineResults(results1);
        
        ProviderBasedEngine engine2 = buildEngine(agenda2);
        EngineResults results2 = engine2.execute(selectionCriteria, execFacts, xOptions);
        System.out.println("Results for Student 1, agenda 2");
        printEngineResults(results2);
        
        ProviderBasedEngine engine3 = buildEngine(agenda3);
        EngineResults results3 = engine3.execute(selectionCriteria, execFacts, xOptions);
        System.out.println("Results for Student 1, agenda 3");
        printEngineResults(results3);
        
    }

    @Test
    public void executeStatementsForStudent2() {
        HashMap<Term, Object> execFacts = buildExecFacts("2");
        
        ProviderBasedEngine engine1 = buildEngine(agenda1);
        EngineResults results1 = engine1.execute(selectionCriteria, execFacts, xOptions);
        System.out.println("Results for Student 2, agenda 1");
        printEngineResults(results1);
        
        ProviderBasedEngine engine2 = buildEngine(agenda2);
        EngineResults results2 = engine2.execute(selectionCriteria, execFacts, xOptions);
        System.out.println("Results for Student 2, agenda 2");
        printEngineResults(results2);
        
        ProviderBasedEngine engine3 = buildEngine(agenda3);
        EngineResults results3 = engine3.execute(selectionCriteria, execFacts, xOptions);
        System.out.println("Results for Student 2, agenda 3");
        printEngineResults(results3);
        
    }
    
    @Test
    public void executeStatementsForStudent3() {
        
        // change permission propositions to return false
        OrgPermissionProposition.setHasPermission(false);
        InstructorPermissionProposition.setHasPermission(false);
        
        HashMap<Term, Object> execFacts = buildExecFacts("3");
        
        ProviderBasedEngine engine1 = buildEngine(agenda1);
        EngineResults results1 = engine1.execute(selectionCriteria, execFacts, xOptions);
        System.out.println("Results for Student 3, agenda 1");
        printEngineResults(results1);
        
        ProviderBasedEngine engine2 = buildEngine(agenda2);
        EngineResults results2 = engine2.execute(selectionCriteria, execFacts, xOptions);
        System.out.println("Results for Student 3, agenda 2");
        printEngineResults(results2);
        
        ProviderBasedEngine engine3 = buildEngine(agenda3);
        EngineResults results3 = engine3.execute(selectionCriteria, execFacts, xOptions);
        System.out.println("Results for Student 3, agenda 3");
        printEngineResults(results3);
        
        // revert permission propositions
        OrgPermissionProposition.setHasPermission(true);
        InstructorPermissionProposition.setHasPermission(true);
    }
    
    @Test
    public void testSimpleAgenda() {
        
        String courseSetId = "1";
        Proposition prop = new CourseSetCompletionProposition(courseSetId);
        
        Rule rule = new BasicRule(prop, null);
        
        List<AgendaTreeEntry> treeEntries = new ArrayList<AgendaTreeEntry>();
        treeEntries.add(new BasicAgendaTreeEntry(rule));
        
        AgendaTree agendaTree = new BasicAgendaTree(treeEntries); 
        
        Map<String, String> emptyStringMap = Collections.emptyMap();
        Agenda result = new BasicAgenda(Constants.STATEMENT_EVENT_NAME, emptyStringMap, agendaTree);
        
        ProviderBasedEngine engine = buildEngine(result);
        
        HashMap<Term, Object> execFacts = buildExecFacts("1");
        
        EngineResults results = engine.execute(selectionCriteria, execFacts, xOptions);
        
        printEngineResults(results); 
    }

    private void printEngineResults(EngineResults results) {
        for(ResultEvent result : results.getAllResults()) {
            System.out.println(result.getType());
            System.out.println("Source object is of type: " + result.getSource().getClass().toString());
            System.out.println(result.getTimestamp());
            System.out.println(result.getResult());
            System.out.println();
        }
        System.out.println("---------------------------");
        System.out.println();
    }
    
}
