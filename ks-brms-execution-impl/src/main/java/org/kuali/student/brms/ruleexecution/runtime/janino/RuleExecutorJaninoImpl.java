/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.brms.ruleexecution.runtime.janino;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.codehaus.janino.CompileException;
import org.codehaus.janino.ExpressionEvaluator;
import org.codehaus.janino.Parser.ParseException;
import org.codehaus.janino.Scanner.ScanException;
import org.kuali.student.brms.internal.common.statement.PropositionContainer;
import org.kuali.student.brms.internal.common.statement.propositions.rules.AverageRuleProposition;
import org.kuali.student.brms.internal.common.statement.propositions.rules.IntersectionRuleProposition;
import org.kuali.student.brms.internal.common.statement.propositions.rules.MaxRuleProposition;
import org.kuali.student.brms.internal.common.statement.propositions.rules.MinRuleProposition;
import org.kuali.student.brms.internal.common.statement.propositions.rules.RuleProposition;
import org.kuali.student.brms.internal.common.statement.propositions.rules.SimpleComparableRuleProposition;
import org.kuali.student.brms.internal.common.statement.propositions.rules.SubsetRuleProposition;
import org.kuali.student.brms.internal.common.statement.propositions.rules.SumRuleProposition;
import org.kuali.student.brms.internal.common.statement.report.RuleReport;
import org.kuali.student.brms.internal.common.utils.BusinessRuleUtil;
import org.kuali.student.brms.repository.dto.RuleSetInfo;
import org.kuali.student.brms.ruleexecution.exceptions.RuleSetExecutionException;
import org.kuali.student.brms.ruleexecution.runtime.ExecutionResult;
import org.kuali.student.brms.ruleexecution.runtime.RuleExecutor;
import org.kuali.student.brms.rulemanagement.dto.BusinessRuleInfo;
import org.kuali.student.brms.rulemanagement.dto.RuleElementInfo;
import org.kuali.student.brms.rulemanagement.dto.RulePropositionInfo;
import org.kuali.student.brms.util.FactContainer;

/**
 * <p><b>
 * THIS CLASS IS EXPERIMENTAL AND NOT PRODUCTION QUALITY AND THEREFORE 
 * SHOULD NOT BE USED IN PRODUCITON.
 * </b></p>
 * <p>
 * Rule executor using the Janino compiler to evaluate and execute boolean 
 * expressions of the form "(A and B) or (A and C)" where each variable is a 
 * proposition.
 * </p>
 * <p>
 * <b>Note:</b> This class needs to be refactored and does not produce 
 * correct error and success messages when executing agendas.
 * </p>
 */
public class RuleExecutorJaninoImpl implements RuleExecutor {

	@Override
	public void addRuleSet(BusinessRuleInfo businessRule, RuleSetInfo ruleSet) {
	}

	@Override
	public void clearRuleSetCache() {
	}

	@Override
	public boolean containsRuleSet(BusinessRuleInfo businessRule) {
		return false;
	}

	private boolean isRuleAllowedToExecute(BusinessRuleInfo businessRule) {
		long currentDate = System.currentTimeMillis();
		if(currentDate < businessRule.getEffectiveDate().getTime() || currentDate >= businessRule.getExpirationDate().getTime()) {
			return false;
		}
		return true;
	}
	
	@Override
	public ExecutionResult execute(BusinessRuleInfo businessRule, Map<String, Object> factMap) {
		String ruleExpression = BusinessRuleUtil.createFunctionalString(businessRule);

		String javaRuleExpression = ruleExpression.replace("AND", "&&");
		javaRuleExpression = javaRuleExpression.replace("OR", "||");
		
		Map<String, RulePropositionInfo> propositionMap = BusinessRuleUtil.getRulePropositions(businessRule);
		
		FactContainer factContainer = new FactContainer(
				businessRule.getId(), 
				businessRule.getAnchor(), businessRule.getAnchorTypeKey(), 
				propositionMap, factMap);
		factContainer.setState(FactContainer.State.COMPLETE);

		List<Object> facts = Arrays.asList(new Object[] {factContainer});
		
		if (!isRuleAllowedToExecute(businessRule)) {
			PropositionContainer prop = factContainer.getPropositionContainer();
	        prop.setOverrideReport(true);
	        prop.setFunctionalRuleString(ruleExpression);
	        prop.setRuleResult(false);
	        RuleReport report = new RuleReport();
	        report.setSuccessful(false);
	        report.setFailureMessage("Rule has expired and was not executed");
	        prop.setRuleReport(report);

			ExecutionResult result = new ExecutionResult();
	        result.setResults(facts);
	        result.setExecutionLog("Janino Execution Complete");
	        result.setExecutionResult(Boolean.TRUE);
	        
	        return result;
		}

        List<RuleElementInfo> ruleElements = businessRule.getBusinessRuleElementList();
        List<String> parameterNames = new ArrayList<String>();
        List<Class<?>> parameterTypes = new ArrayList<Class<?>>();

System.out.println("\n\n***** execute *****");
System.out.println("ruleExpression = " + ruleExpression );
System.out.println("javaRuleExpression = " + javaRuleExpression );
//System.out.println("propositionMap = " + propositionMap );
        int counter = 1;
		for (RuleElementInfo ruleElement : ruleElements) {
        	if(ruleElement.getBusinessRuleElemnetTypeKey().equals("PROPOSITION")) {
	        	parameterNames.add(BusinessRuleUtil.PROPOSITION_PREFIX + String.valueOf(counter++));
	        	parameterTypes.add(Boolean.class);
        	}
        }
//System.out.println("parameterNames = " + parameterNames );
//System.out.println("parameterTypes = " + parameterTypes );
        
		ExpressionEvaluator expressionEvaluator;
		try {
			expressionEvaluator = new ExpressionEvaluator(
					javaRuleExpression, // expression
				    Boolean.class,  // expressionType
				    parameterNames.toArray(new String[] {}), // parameterNames
				    parameterTypes.toArray(new Class<?>[] {})  // parameterTypes
				);
		} catch (CompileException e) {
			throw new RuleSetExecutionException(e);
		} catch (ParseException e) {
			throw new RuleSetExecutionException(e);
		} catch (ScanException e) {
			throw new RuleSetExecutionException(e);
		}

		List<RuleProposition> propositions = createPropositions(businessRule, propositionMap, factMap);
		Boolean[] propositionResults = getPropositionResults(propositions);
		
		Boolean res;
		try {
			res = (Boolean) expressionEvaluator.evaluate(propositionResults); // parameterValues
		} catch (InvocationTargetException e) {
			System.out.println("Exception Message="+e.getMessage());
			throw new RuleSetExecutionException(e);
		} 

		createPropositionContainer(res, factContainer, ruleExpression, propositions);
		
		ExecutionResult result = new ExecutionResult();
        result.setResults(facts);
        result.setExecutionLog("Janino Execution Complete");
        result.setExecutionResult(Boolean.TRUE);

System.out.println("***** Proposition result = " + factContainer.getPropositionContainer().getPropositionMap());
System.out.println("***** Proposition result = " + res + " *****\n\n");

        return result;
	}
	
	private void createPropositionContainer(Boolean res, FactContainer factContainer, String ruleExpression, List<RuleProposition> propositions) {
		factContainer.getPropositionContainer().setFunctionalRuleString(ruleExpression);
		factContainer.getPropositionContainer().setRuleResult(res);
		for(RuleProposition prop : propositions) {
			factContainer.getPropositionContainer().addProposition(prop);
		}
	}
	
	private Boolean[] getPropositionResults(List<RuleProposition> propositions) {
		Boolean[] results = new Boolean[propositions.size()];
		for(int i=0; i<propositions.size(); i++) {
			results[i] = propositions.get(i).getResult();
System.out.println(propositions.get(i).getMessageId() + ": result = " + results[i]);
		}
		return results;
	}

	private List<RuleProposition> createPropositions(BusinessRuleInfo businessRule, Map<String, RulePropositionInfo> propositionMap, Map<String, Object> factMap) {
		List<RuleProposition> propositions = new ArrayList<RuleProposition>();
		
		for(Map.Entry<String, RulePropositionInfo> entry : propositionMap.entrySet()) {
			String key = entry.getKey();
			RulePropositionInfo ruleProposition = entry.getValue();
            String yvf = ruleProposition.getLeftHandSide().getYieldValueFunction().getYieldValueFunctionType();
            
        	if (yvf.equals("SIMPLECOMPARABLE")) {
        		RuleProposition proposition = new SimpleComparableRuleProposition(key, key, ruleProposition, factMap);
        		proposition.apply();
        		propositions.add(proposition);
            }
            else if (yvf.equals("SUBSET")) {
            	RuleProposition proposition = new SubsetRuleProposition<Comparable<?>>(key, key, ruleProposition, factMap);
        		proposition.apply();
        		propositions.add(proposition);
            }
       		else if (yvf.equals("INTERSECTION")) {
       			RuleProposition proposition = new IntersectionRuleProposition<Comparable<?>>(key, key, ruleProposition, factMap);
        		proposition.apply();
        		propositions.add(proposition);
       		}
            else if (yvf.equals("SUM")) {
                RuleProposition proposition = new SumRuleProposition<Number>(key, key, ruleProposition, factMap);
        		proposition.apply();
        		propositions.add(proposition);
            }
            else if (yvf.equals("AVERAGE")) {
                RuleProposition proposition = new AverageRuleProposition<Number>(key, key, ruleProposition, factMap);
        		proposition.apply();
        		propositions.add(proposition);
            }
            else if (yvf.equals("MIN")) {
                RuleProposition proposition = new MinRuleProposition(key, key, ruleProposition, factMap);
        		proposition.apply();
        		propositions.add(proposition);
            }
            else if (yvf.equals("MAX")) {
                RuleProposition proposition = new MaxRuleProposition(key, key, ruleProposition, factMap);
        		proposition.apply();
        		propositions.add(proposition);
            }
        }
		return propositions;
	}

	@Override
	public void removeRuleSet(BusinessRuleInfo businessRule, RuleSetInfo ruleSet) {
	}

	@Override
	public void setGlobalObjects(Map<String, Object> globalObjectMap) {
	}

}
