package org.kuali.student.krms.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.kuali.rice.krms.api.engine.EngineResults;
import org.kuali.rice.krms.api.engine.ExecutionFlag;
import org.kuali.rice.krms.api.engine.ExecutionOptions;
import org.kuali.rice.krms.api.engine.SelectionCriteria;
import org.kuali.rice.krms.api.engine.Term;
import org.kuali.rice.krms.api.engine.TermResolutionException;
import org.kuali.rice.krms.api.engine.TermResolver;
import org.kuali.rice.krms.api.engine.TermSpecification;
import org.kuali.rice.krms.api.repository.LogicalOperator;
import org.kuali.rice.krms.framework.engine.Agenda;
import org.kuali.rice.krms.framework.engine.AgendaTree;
import org.kuali.rice.krms.framework.engine.AgendaTreeEntry;
import org.kuali.rice.krms.framework.engine.BasicAgenda;
import org.kuali.rice.krms.framework.engine.BasicAgendaTree;
import org.kuali.rice.krms.framework.engine.BasicAgendaTreeEntry;
import org.kuali.rice.krms.framework.engine.BasicContext;
import org.kuali.rice.krms.framework.engine.BasicRule;
import org.kuali.rice.krms.framework.engine.ComparableTermBasedProposition;
import org.kuali.rice.krms.framework.engine.ComparisonOperator;
import org.kuali.rice.krms.framework.engine.CompoundProposition;
import org.kuali.rice.krms.framework.engine.Context;
import org.kuali.rice.krms.framework.engine.ContextProvider;
import org.kuali.rice.krms.framework.engine.Proposition;
import org.kuali.rice.krms.framework.engine.ProviderBasedEngine;
import org.kuali.rice.krms.framework.engine.ResultLogger;
import org.kuali.rice.krms.framework.engine.Rule;
import org.kuali.student.core.statement.service.StatementService;
import org.kuali.student.lum.course.service.CourseService;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.program.dto.CredentialProgramInfo;
import org.kuali.student.lum.program.service.ProgramService;
import org.springframework.beans.factory.annotation.Autowired;

public class KRMSTest {
	private static final ResultLogger LOG = ResultLogger.getInstance();
	
    @Autowired
    CourseService courseService;
    @Autowired
    StatementService statementService;
    
	@Test
	public void integrationTest() {

		// build a simple rule
//		ComparableTerm term1 = new SimpleComparableTerm<Integer>(Integer.valueOf(100));
//		ResolvableComparableTerm<Integer> resolvableComparableTerm = new ResolvableComparableTerm<Integer>(testResolver.getOutput());
		
	    Term totalCostTerm = new Term(totalCostAsset);
	    
		Proposition prop1 = new ComparableTermBasedProposition<Integer>(ComparisonOperator.GREATER_THAN, totalCostTerm, Integer.valueOf(1));
		Proposition prop2 = new ComparableTermBasedProposition<Integer>(ComparisonOperator.LESS_THAN, totalCostTerm, Integer.valueOf(1000));
		Proposition prop3 = new ComparableTermBasedProposition<Integer>(ComparisonOperator.GREATER_THAN, totalCostTerm, Integer.valueOf(1000));
		CompoundProposition compoundProp1 = new CompoundProposition(LogicalOperator.AND, Arrays.asList(prop1, prop2, prop3));
		
		Rule rule = new BasicRule("InBetween",compoundProp1, null);
		
		List<AgendaTreeEntry> treeEntries = new ArrayList<AgendaTreeEntry>();
		treeEntries.add(new BasicAgendaTreeEntry(rule));
		
		AgendaTree agendaTree = new BasicAgendaTree(treeEntries); 
		Agenda agenda = new BasicAgenda("test", new HashMap<String, String>(), agendaTree);
		
		Map<String, String> contextQualifiers = new HashMap<String, String>();
		contextQualifiers.put("docTypeName", "Proposal");
		
		List<TermResolver<?>> resolvers = new ArrayList<TermResolver<?>>();
		resolvers.add(gpaResolver);
		resolvers.add(learningResultsResolver);
		
		Context context = new BasicContext(Arrays.asList(agenda), resolvers);
		ContextProvider contextProvider = new ManualContextProvider(context);
		
		SelectionCriteria selectionCriteria = SelectionCriteria.createCriteria("test", new Date(), contextQualifiers, Collections.EMPTY_MAP);
		
		ProviderBasedEngine engine = new ProviderBasedEngine();
		engine.setContextProvider(contextProvider);
		
		// Set execution options to log execution
		ExecutionOptions xOptions = new ExecutionOptions();
		xOptions.setFlag(ExecutionFlag.LOG_EXECUTION, true);
		
		HashMap<Term, Object> execFacts = new HashMap<Term, Object>();
		execFacts.put(new Term(studentId), new String("013005779"));
		
		LOG.init();
		EngineResults results = engine.execute(selectionCriteria, execFacts, xOptions);
		
	}
	
	private static final TermSpecification totalCostAsset = new TermSpecification("totalCost","Integer");
	
	private static final TermResolver<Integer> testResolver = new TermResolver<Integer>(){
		
		@Override
		public int getCost() { return 1; }
		
		@Override
		public TermSpecification getOutput() { return totalCostAsset; }
		
		@Override
		public Set<TermSpecification> getPrerequisites() { return Collections.emptySet(); }
		
        @Override
        public Set<String> getParameterNames() {
            return Collections.emptySet();
        }

        @Override
        public Integer resolve(Map<TermSpecification, Object> resolvedPrereqs, Map<String, String> parameters) throws TermResolutionException {
            return 5;
        }
	};
	

	private static final TermSpecification studentId = new TermSpecification("studentId","String");
	
	private static final TermSpecification gpaAsset = new TermSpecification("gpa","Float");
	
	private static final TermResolver<Float> gpaResolver = new TermResolver<Float>(){
		
		@Override
		public int getCost() { return 1; }
		
		@Override
		public TermSpecification getOutput() { return gpaAsset; }
		
		@Override
		public Set<TermSpecification> getPrerequisites() {
			Set<TermSpecification> preReqs = new HashSet<TermSpecification>();
			
			preReqs.add(studentId);
			return preReqs; 
		}
		
		@Override
        public Float resolve(Map<TermSpecification, Object> resolvedPrereqs, Map<String, String> parameters) throws TermResolutionException {
            return new Float(3.2);
        }

        @Override
        public Set<String> getParameterNames() {
            return Collections.emptySet();
        }
	};
	
	
	private class LearningResult {
		private String grade;
		private CluInfo clu;
	}
	
	private static final TermSpecification learningResultsAsset = new TermSpecification("academicRecord","ListOfLearningResults");
	
	private static final TermResolver<List<LearningResult>> learningResultsResolver = new TermResolver<List<LearningResult>>(){
		
		@Override
		public int getCost() { return 1; }
		
		@Override
		public TermSpecification getOutput() { return gpaAsset; }
		
		@Override
		public Set<TermSpecification> getPrerequisites() {
			Set<TermSpecification> preReqs = new HashSet<TermSpecification>();
			
			preReqs.add(studentId);
			return preReqs; 
		}
		
        @Override
        public Set<String> getParameterNames() {
            return Collections.emptySet();
        }

        @Override
        public List<LearningResult> resolve(Map<TermSpecification, Object> resolvedPrereqs, Map<String, String> parameters) throws TermResolutionException {
            return Collections.emptyList();
        }
	};

	private static final TermSpecification enrolledProgramsAsset = new TermSpecification("academicRecord","ListOfPrograms");
	
	private static final TermResolver<List<CredentialProgramInfo>> enrolledProgramsResolver = new TermResolver<List<CredentialProgramInfo>>(){
		
	    @Autowired
	    ProgramService programService;
		
		@Override
		public int getCost() { return 1; }
		
		@Override
		public TermSpecification getOutput() { return gpaAsset; }
		
		@Override
		public Set<TermSpecification> getPrerequisites() {
			Set<TermSpecification> preReqs = new HashSet<TermSpecification>();
			
			preReqs.add(studentId);
			return preReqs; 
		}
		
		@Override
        public List<CredentialProgramInfo> resolve(Map<TermSpecification, Object> resolvedPrereqs, Map<String, String> parameters) throws TermResolutionException {
			List<CredentialProgramInfo> programs = new ArrayList<CredentialProgramInfo>();
			String id = "asdfasdfasdf";
			try {
				programs.add(programService.getCredentialProgram(id));
			} catch (Exception e) {}
			
			return programs;
		}

		@Override
        public Set<String> getParameterNames() {
            return Collections.emptySet();
        }
	};
}
