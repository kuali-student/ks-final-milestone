package org.kuali.student.krms.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.kuali.rice.krms.api.Agenda;
import org.kuali.rice.krms.api.Asset;
import org.kuali.rice.krms.api.AssetResolver;
import org.kuali.rice.krms.api.Context;
import org.kuali.rice.krms.api.ContextProvider;
import org.kuali.rice.krms.api.EngineResults;
import org.kuali.rice.krms.api.ExecutionOptions;
import org.kuali.rice.krms.api.Proposition;
import org.kuali.rice.krms.api.Rule;
import org.kuali.rice.krms.api.SelectionCriteria;
import org.kuali.rice.krms.framework.engine.AgendaTree;
import org.kuali.rice.krms.framework.engine.BasicAgenda;
import org.kuali.rice.krms.framework.engine.BasicContext;
import org.kuali.rice.krms.framework.engine.BasicRule;
import org.kuali.rice.krms.framework.engine.ComparableTermBasedProposition;
import org.kuali.rice.krms.framework.engine.ComparisonOperator;
import org.kuali.rice.krms.framework.engine.CompoundProposition;
import org.kuali.rice.krms.framework.engine.LogicalOperator;
import org.kuali.rice.krms.framework.engine.ProviderBasedEngine;
import org.kuali.rice.krms.framework.engine.ResultLogger;
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
		
		Proposition prop1 = new ComparableTermBasedProposition(ComparisonOperator.GREATER_THAN, totalCostAsset, Integer.valueOf(1));
		Proposition prop2 = new ComparableTermBasedProposition(ComparisonOperator.LESS_THAN, totalCostAsset, Integer.valueOf(1000));
		Proposition prop3 = new ComparableTermBasedProposition(ComparisonOperator.GREATER_THAN, totalCostAsset, Integer.valueOf(1000));
		CompoundProposition compoundProp1 = new CompoundProposition(LogicalOperator.AND, Arrays.asList(prop1, prop2, prop3));
		
		Rule rule = new BasicRule("InBetween",compoundProp1, null);
		
		AgendaTree agendaTree = new AgendaTree(Arrays.asList(rule), null, null, null); 
		Agenda agenda = new BasicAgenda("test", new HashMap<String, String>(), agendaTree);
		
		Map<String, String> contextQualifiers = new HashMap<String, String>();
		contextQualifiers.put("docTypeName", "Proposal");
		
		List<AssetResolver<?>> resolvers = new ArrayList<AssetResolver<?>>();
		resolvers.add(gpaResolver);
		resolvers.add(learningResultsResolver);
		
		Context context = new BasicContext(contextQualifiers, Arrays.asList(agenda), resolvers);
		ContextProvider contextProvider = new ManualContextProvider(context);
		
		SelectionCriteria selectionCriteria = SelectionCriteria.createCriteria("test", contextQualifiers, Collections.EMPTY_MAP);
		
		ProviderBasedEngine engine = new ProviderBasedEngine();
		engine.setContextProvider(contextProvider);
		
		// Set execution options to log execution
		HashMap<String, String> xOptions = new HashMap<String, String>();
		xOptions.put(ExecutionOptions.LOG_EXECUTION.toString(), Boolean.toString(true));
		
		HashMap<Asset, Object> execFacts = new HashMap<Asset, Object>();
		execFacts.put(studentId, new String("013005779"));
		
		LOG.init();
		EngineResults results = engine.execute(selectionCriteria, execFacts, xOptions);
		
	}
	
	private static final Asset totalCostAsset = new Asset("totalCost","Integer");
	
	private static final AssetResolver<Integer> testResolver = new AssetResolver<Integer>(){
		
		@Override
		public int getCost() { return 1; }
		
		@Override
		public Asset getOutput() { return totalCostAsset; }
		
		@Override
		public Set<Asset> getPrerequisites() { return Collections.emptySet(); }
		
		@Override
		public Integer resolve(Map<Asset, Object> resolvedPrereqs) {
			return 5;
		}
	};
	

	private static final Asset studentId = new Asset("gpa","String");
	
	private static final Asset gpaAsset = new Asset("gpa","Float");
	
	private static final AssetResolver<Float> gpaResolver = new AssetResolver<Float>(){
		
		@Override
		public int getCost() { return 1; }
		
		@Override
		public Asset getOutput() { return gpaAsset; }
		
		@Override
		public Set<Asset> getPrerequisites() {
			Set<Asset> preReqs = new HashSet<Asset>();
			
			preReqs.add(studentId);
			return preReqs; 
		}
		
		@Override
		public Float resolve(Map<Asset, Object> resolvedPrereqs) {
			return new Float(3.2);
		}
	};
	
	
	private class LearningResult {
		private String grade;
		private CluInfo clu;
	}
	
	private static final Asset learningResultsAsset = new Asset("academicRecord","ListOfLearningResults");
	
	private static final AssetResolver<List<LearningResult>> learningResultsResolver = new AssetResolver<List<LearningResult>>(){
		
		@Override
		public int getCost() { return 1; }
		
		@Override
		public Asset getOutput() { return gpaAsset; }
		
		@Override
		public Set<Asset> getPrerequisites() {
			Set<Asset> preReqs = new HashSet<Asset>();
			
			preReqs.add(studentId);
			return preReqs; 
		}
		
		@Override
		public List<LearningResult> resolve(Map<Asset, Object> resolvedPrereqs) {
			return new ArrayList<LearningResult>();
		}
	};

	private static final Asset enrolledProgramsAsset = new Asset("academicRecord","ListOfPrograms");
	
	private static final AssetResolver<List<CredentialProgramInfo>> enrolledProgramsResolver = new AssetResolver<List<CredentialProgramInfo>>(){
		
	    @Autowired
	    ProgramService programService;
		
		@Override
		public int getCost() { return 1; }
		
		@Override
		public Asset getOutput() { return gpaAsset; }
		
		@Override
		public Set<Asset> getPrerequisites() {
			Set<Asset> preReqs = new HashSet<Asset>();
			
			preReqs.add(studentId);
			return preReqs; 
		}
		
		@Override
		public List<CredentialProgramInfo> resolve(Map<Asset, Object> resolvedPrereqs) {
			List<CredentialProgramInfo> programs = new ArrayList<CredentialProgramInfo>();
			String id = "asdfasdfasdf";
			try {
				programs.add(programService.getCredentialProgram(id));
			} catch (Exception e) {}
			
			return programs;
		}
	};
}
