/**
 * 
 */
package org.kuali.student.rules.brms.parser;

import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.kuali.student.rules.util.*;
import org.kuali.student.brms.repository.drools.*;
import org.kuali.student.brms.repository.exceptions.RuleEngineRepositoryException;
import org.kuali.student.brms.repository.rule.Rule;
import org.kuali.student.brms.repository.rule.RuleFactory;
import org.kuali.student.brms.repository.rule.RuleImpl;
import org.kuali.student.brms.repository.rule.RuleSet;
import org.kuali.student.brms.repository.rule.RuleSetFactory;
import org.kuali.student.brms.repository.rule.RuleSetImpl;
import org.kuali.student.brms.repository.*;
/**
 * @author Rich Diaz
 *
 */
public class GenerateRuleSet {
	String functionString;
	ArrayList<String> ruleAttributes = new ArrayList<String>();
    ArrayList<String> lhs = new ArrayList<String>();
    ArrayList<String> rhs = new ArrayList<String>();
    
    Hashtable<String, String> funcConstraintsMap;
    String outcome;
    
    RuleEngineRepository brmsRepository;
    String rulesetUuid;
    String ruleSetName;
    String ruleSetDescription;
    String ruleName;
    String description;
    String category;
    RuleSet ruleSet;
    
	
	public static void main(String[] args){
		try{
			// Initialize repository
			DroolsJackrabbitRepository repo;
			URL url = DroolsJackrabbitRepository.class.getResource("/repository");
			System.out.println("the url " + url.toString());
			repo = new DroolsJackrabbitRepository(url);
			repo.initialize();
			
			repo.clearData();
			RuleEngineRepository brmsRepository = new RuleEngineRepositoryDroolsImpl( repo.getRepository() );
			String rulePackage = "org.kuali.student.rules.enrollment";
			//String rulesetUuid = brmsRepository.createRuleSet(rulePackage, "My package description" );
			
			
			GenerateRuleSet grs = new GenerateRuleSet("A0*B4+(C*D)");
			//grs.setRuleEngineRepository(brmsRepository);
			//grs.setRuleSetUuid(rulesetUuid);
			grs.setRuleSetName(rulePackage);
			grs.setRuleSetDescription("A rule set description");
			grs.setRuleName("Enrollment Physics");
			grs.setRuleDescription("A rule description"); // Rule description cannot be empty
			grs.setRuleCategory(null);
			grs.setRuleAttributes(null);
			
		    Hashtable<String, String> funcConstraintsMap = new Hashtable<String, String>();
		    funcConstraintsMap.put("A0", "A constraintID from DB");
		    funcConstraintsMap.put("B4", "A constraintID from DB");
		    funcConstraintsMap.put("C", "A constraintID from DB");
		    funcConstraintsMap.put("D", "A constraintID from DB");
		    grs.setLhsFuncConstraintMap(funcConstraintsMap);
		    grs.setRuleOutcome("System.out.println(\"I'm Enrolled\");");
		    
			grs.parse();
			
			// compile rule and save in repository
			//CompilerResultList results = brmsRepository.compileRuleSet(rulesetUuid);

            System.out.println("***** Rule source before compilation *****");
			System.out.println(grs.getRuleSet().getContent());
            System.out.println("\n******************************************");
			
			String rulesetUuid = brmsRepository.createRuleSet(grs.getRuleSet());
			
			// load rule from repo and print
            System.out.println("***** Rule source after compilation *****");
			RuleSet ruleset = brmsRepository.loadRuleSet(rulesetUuid);
            System.out.println(ruleset.getContent());
            System.out.println("******************************************");
			//Shutdown (and logout from) the repository:
			repo.shutdownRepository();
			 
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
    public GenerateRuleSet(String functionString) {
		this.functionString = functionString;
	}
	
	public void parse() throws Exception{
		Function f = new Function(functionString);
		List<String> funcVars = f.getVariables();
		
		RuleTemplate rt = new RuleTemplate();
		
        //ruleSet = new RuleSetImpl(ruleSetName);
		ruleSet = RuleSetFactory.getInstance().createRuleSet(ruleSetName);
        ruleSet.setDescription(ruleSetDescription);
        ruleSet.addHeader("import org.kuali.student.rules.util.Propositions");
        ruleSet.addHeader("import org.kuali.student.rules.util.Constraint");

        // create the final composite rule for the function
        List<String> symbols = f.getSymbols();
        
        String extRuleName = ruleName + " " + "Func";
        rt.setRuleName(extRuleName);
        rt.setRuleAttributes(ruleAttributes);
        
        // Left Hand Side
        lhs.add("props : Propositions()");
        lhs.add("eval(");
              
        for (String symbol : symbols) {
            if ( symbol.equals("+") ){
                lhs.add("||");
            }
            else if( symbol.equals("*") ){
                lhs.add("&&");
            }
            else if( symbol.equals("(") ){
                lhs.add("(");
            }
            else if( symbol.equals(")") ){
                lhs.add(")");
            }
            else {
                lhs.add("Propositions.getProposition(\"" + symbol + "\")");
            }
            
        }
        lhs.add(")");
        rt.setLHS(lhs);
        
        
        // Right Hand Side
        rhs.add(outcome);
        rt.setRHS(rhs);
        
        // Merge the template with the context set in lhs, rhs, etc ..
        String ruleSourceCode = rt.process("RuleTemplate.vm");
        saveRule(extRuleName, ruleSourceCode);
        
        ruleAttributes.clear();
        lhs.clear();
        rhs.clear();
        
        
        // create the proposition rule, one per function variable
		for (String var : funcVars) {
			extRuleName = ruleName + " (" + var + ")";
			rt.setRuleName(extRuleName);
			rt.setRuleAttributes(ruleAttributes);
			
			// Left Hand Side
			String dbConstraint = funcConstraintsMap.get(var);
			lhs.add("constraint : Constraint(constraintID == \"" + dbConstraint + "\")");
            lhs.add("Boolean(booleanValue == Boolean.TRUE) from constraint.apply(\"" + var + "\")");
            lhs.add("props : Propositions()");
            rt.setLHS(lhs);
			
			// Right Hand Side
            rhs.add("Propositions.setProposition(\"" + var + "\", true);");
            rhs.add("retract(constraint);");
            rhs.add("update(props);");
            rt.setRHS(rhs);
			
            // Merge the template with the context set in lhs, rhs, etc ..
			ruleSourceCode = rt.process("RuleTemplate.vm");
			saveRule(extRuleName, ruleSourceCode);
			
			ruleAttributes.clear();
            lhs.clear();
            rhs.clear();
        }
	}
	
	// Add rule to ruleset created in constructor
	private void saveRule(String extRuleName, String ruleSourceCode){
        Rule rule = RuleFactory.getInstance().createRule(extRuleName);
        rule.setDescription(description);
        rule.setCategory(category);
        rule.setContent(ruleSourceCode);
        rule.setFormat("drl");
        ruleSet.addRule(rule);
	}
	
	public void setRuleEngineRepository(RuleEngineRepository brmsRepository) {
	    this.brmsRepository = brmsRepository;
	}
	
	public void setRuleSetUuid(String rulesetUuid) {
        this.rulesetUuid = rulesetUuid;
    }
	
	public void setRuleName(String ruleName){
        this.ruleName = ruleName;
    }
	
    public void setRuleSetName(String ruleSetName){
        this.ruleSetName = ruleSetName;
    }
    
    public void setRuleSetDescription(String ruleSetDescription) {
        this.ruleSetDescription = ruleSetDescription;
    }
    
	public void setRuleDescription(String description) {
	    this.description = description;
	}
	
	public void setRuleCategory(String category){
	    this.category = category;
	}
	
	public void setRuleAttributes(ArrayList<String> ruleAttributes) {
	    if (ruleAttributes != null){
	        this.ruleAttributes = ruleAttributes;
	    }
	}
	
	public void setLhs(ArrayList<String> lhs) {
	    this.lhs = lhs;
	}
	
	public void setRhs(ArrayList<String> rhs) {
        this.rhs = rhs;
    }
	
	public RuleSet getRuleSet() {
	    return this.ruleSet;
	}
	
    public void setLhsFuncConstraintMap(Hashtable<String, String> funcConstraintsMap) {
        this.funcConstraintsMap = funcConstraintsMap;
    }
    
    public void setRuleOutcome(String outcome) {
        this.outcome = outcome;
    }
}
