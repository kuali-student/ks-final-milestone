package org.kuali.student.rules.BRMSCore;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.io.StringWriter;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.Template;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.MethodInvocationException;

/**
 * An interface to BRMS Meta Data that allows to store and retrieve a business rule
 * @author Zdenek Zraly (zdenek.zraly@ub.ca)
 */
@Transactional //(propagation = Propagation.REQUIRED, readOnly = false)
public class BRMSMetaData {	
	
	public static final String FACT_CONTAINER = "AcademicRecord";
	VelocityContext context;
	
	@Autowired
    private FunctionalBusinessRuleDAO businessRuleDAO;
	
	long id;
		
	public String getRuleFunctionString(FunctionalBusinessRule rule) {
			
		Collection <RuleElement> ruleElements = rule.getRuleElements();
		
		ruleElements.size();
		
		String functionString = new String("");
		char proposition = 'A';
		for (RuleElement ruleElement : ruleElements) {
			functionString += " ";
			switch (ruleElement.getOperation()) {
				case AND_TYPE:
					functionString += "AND";
					break;
				case LPAREN_TYPE:
					functionString += "(";
					break;
				case NOT_TYPE:
					functionString += "NOT";
					break;
				case OR_TYPE:
					functionString += "OR";
					break;
				case PROPOSITION_TYPE:
					functionString += proposition;
					proposition++;					
					break;
				case RPAREN_TYPE:
					functionString += ")";
					break;
				case XOR_TYPE:
					functionString += "XOR";
					break;	
				default:
					functionString += "Not found";
			}
		}
		return functionString;
	}
	
	public HashMap <String, RuleProposition> getRulePropositions(FunctionalBusinessRule rule) {
		
		HashMap <String, RuleProposition> propositions = new HashMap <String, RuleProposition>();
		Collection <RuleElement> ruleElements = rule.getRuleElements();
		
		char key = 'A';
		for (RuleElement ruleElement : ruleElements) {
			
			if (ruleElement.getOperation() == RuleElementType.PROPOSITION_TYPE) {
				propositions.put(String.valueOf(key), ruleElement.getRuleProposition());
				key++;
			}
		}		
		return propositions;
	}	

	/**
	 * @return the businessRuleSetDAO
	 */
	public final FunctionalBusinessRuleDAO getBusinessRuleSetDAO() {
System.out.println("1-getClass="+this.getClass());
		return this.businessRuleDAO;
	}

	/**
	 * @param businessRuleSetDAO the businessRuleSetDAO to set
	 */
	public final void setBusinessRuleSetDAO(
			FunctionalBusinessRuleDAO businessRuleSetDAO) {
System.out.println("2-setClass="+this.getClass());
		this.businessRuleDAO = businessRuleSetDAO;
	}
	
    public void insertBusinessRuleMetadata() throws ConstraintViolationException {

		int ordinalPosition = 1;
		RuleElement ruleElement = null;
		RuleProposition ruleProp = null;
		LeftHandSide leftSide = null;
		RightHandSide rightSide = null;
		Operator operator = null;
		ArrayList<String> criteria = null;
		ArrayList<String> facts = null;
		
		//setup business rule meta data and main rule entity
		RuleMetaData metaData = new RuleMetaData("Tom Smith", new Date(), "", null, new Date(), new Date(), "1.1", "active");
		
		//we keep this entity empty for now
		BusinessRuleEvaluation businessRuleEvaluation = new BusinessRuleEvaluation();
		
		//create basic rule structure
		FunctionalBusinessRule busRule = new FunctionalBusinessRule("PR CHEM 200",
				"enrollment prerequisites for Chemistry 200", "PR 40244", metaData, businessRuleEvaluation);

		
		// =======================================
		// ( Completed any 2 of (Math 101, 102, 103) OR Completed any 3 of (Chem 101, 102, 103) )
		// =======================================
		ruleElement = new RuleElement(RuleElementType.LPAREN_TYPE, ordinalPosition++, "", "", null, null);
		ruleElement.setFunctionalBusinessRule(busRule);
		busRule.addRuleElement(ruleElement);
		
		// Completed any 2 of (Math 101, 102, 103)
		facts = new ArrayList<String>();		
		facts.add("MATH101, MATH102, MATH103");
		leftSide = new LeftHandSide("Student.LearningResults", FACT_CONTAINER, "countCLUMatches", criteria);
		operator = new Operator(ComparisonOperatorType.GREATER_THAN_OR_EQUAL_TO_TYPE);
		criteria = new ArrayList<String>();		
		criteria.add("2");		
		rightSide = new RightHandSide("requiredCourses", "java.lang.Integer", criteria);				
		ruleProp = new RuleProposition("Math Prerequisite", "enumeration of required Math courses", leftSide, operator, rightSide);
		ruleElement = new RuleElement(RuleElementType.PROPOSITION_TYPE, ordinalPosition++, "", "", null, ruleProp);
		ruleElement.setFunctionalBusinessRule(busRule);
		busRule.addRuleElement(ruleElement);
		
		// OR
		ruleElement = new RuleElement(RuleElementType.OR_TYPE, ordinalPosition++, "", "", null, null);
		ruleElement.setFunctionalBusinessRule(busRule);
		busRule.addRuleElement(ruleElement);
		
		// Completed any 3 of (Chem 101, 102, 103)
		facts = new ArrayList<String>();		
		facts.add("CHEM101, CHEM102, CHEM103");
		leftSide = new LeftHandSide("Student.LearningResults", FACT_CONTAINER, "countCLUMatches", criteria);
		operator = new Operator(ComparisonOperatorType.GREATER_THAN_OR_EQUAL_TO_TYPE);
		criteria = new ArrayList<String>();		
		criteria.add("3");		
		rightSide = new RightHandSide("requiredCourses", "java.lang.Integer", criteria);						
		ruleProp = new RuleProposition("Chemistry Prerequisite", "enumeration of required Chem courses", leftSide, operator, rightSide);
		ruleElement = new RuleElement(RuleElementType.PROPOSITION_TYPE, ordinalPosition++, "", "", null, ruleProp);
		ruleElement.setFunctionalBusinessRule(busRule);
		busRule.addRuleElement(ruleElement);
		
		ruleElement = new RuleElement(RuleElementType.RPAREN_TYPE, ordinalPosition++, "", "", null, null);
		ruleElement.setFunctionalBusinessRule(busRule);
		busRule.addRuleElement(ruleElement);

		
		// =======================================
		// AND Accumulated 10 creds in Basic Core
		// =======================================
		ruleElement = new RuleElement(RuleElementType.AND_TYPE, ordinalPosition++, "", "", null, null);
		ruleElement.setFunctionalBusinessRule(busRule);
		busRule.addRuleElement(ruleElement);
		
		facts = new ArrayList<String>();		
		facts.add("ENG100, PSYC102, MATH103");
		leftSide = new LeftHandSide("Student.LearningResults", FACT_CONTAINER, "countCoreUnits", criteria);
		operator = new Operator(ComparisonOperatorType.GREATER_THAN_OR_EQUAL_TO_TYPE);
		criteria = new ArrayList<String>();		
		criteria.add("2");		
		rightSide = new RightHandSide("requiredCoreUnits", "java.lang.Integer", criteria);				
		ruleProp = new RuleProposition("Required Core Units", "Number of core units", leftSide, operator, rightSide);
		ruleElement = new RuleElement(RuleElementType.PROPOSITION_TYPE, ordinalPosition++, "", "", null, ruleProp);
		ruleElement.setFunctionalBusinessRule(busRule);
		busRule.addRuleElement(ruleElement);
		
		
		// =======================================		
		// AND ( obtained instructor approval OR has Senior class standing )
		// =======================================		
		ruleElement = new RuleElement(RuleElementType.AND_TYPE, ordinalPosition++, "", "", null, null);
		ruleElement.setFunctionalBusinessRule(busRule);
		busRule.addRuleElement(ruleElement);		
		
		ruleElement = new RuleElement(RuleElementType.LPAREN_TYPE, ordinalPosition++, "", "", null, null);
		ruleElement.setFunctionalBusinessRule(busRule);
		busRule.addRuleElement(ruleElement);
		
		// obtained instructor approval
		facts = new ArrayList<String>();		
		facts.add("");
		leftSide = new LeftHandSide("Student.LearningResults", FACT_CONTAINER, "isApprovedByInstructor", criteria);
		operator = new Operator(ComparisonOperatorType.EQUAL_TO_TYPE);
		criteria = new ArrayList<String>();		
		criteria.add("true");		
		rightSide = new RightHandSide("instructorApproval", "java.lang.Boolean", criteria);				
		ruleProp = new RuleProposition("Instructor Approval", "approval granted", leftSide, operator, rightSide);		
		ruleElement = new RuleElement(RuleElementType.PROPOSITION_TYPE, ordinalPosition++, "", "", null, ruleProp);
		ruleElement.setFunctionalBusinessRule(busRule);
		busRule.addRuleElement(ruleElement);		
	
		// OR		
		ruleElement = new RuleElement(RuleElementType.OR_TYPE, ordinalPosition++, "", "", null, null);
		ruleElement.setFunctionalBusinessRule(busRule);
		busRule.addRuleElement(ruleElement);
		
		// has Senior class standing
		facts = new ArrayList<String>();		
		facts.add("");
		leftSide = new LeftHandSide("Student.LearningResults", FACT_CONTAINER, "hasSeniorStanding", criteria);
		operator = new Operator(ComparisonOperatorType.EQUAL_TO_TYPE);
		criteria = new ArrayList<String>();		
		criteria.add("true");		
		rightSide = new RightHandSide("seniorStanding", "java.lang.Boolean", criteria);				
		ruleProp = new RuleProposition("Senior Class Standing", "Senior standing achieved", leftSide, operator, rightSide);	
		ruleElement = new RuleElement(RuleElementType.PROPOSITION_TYPE, ordinalPosition++, "", "", null, ruleProp);
		ruleElement.setFunctionalBusinessRule(busRule);
		busRule.addRuleElement(ruleElement);		
		
		ruleElement = new RuleElement(RuleElementType.RPAREN_TYPE, ordinalPosition++, "", "", null, null);
		ruleElement.setFunctionalBusinessRule(busRule);
		//ruleElementDAO.createRuleElement(ruleElement);
		busRule.addRuleElement(ruleElement);				
		
		businessRuleDAO.createBusinessRule(busRule);	
		
		id = busRule.getId();
		System.out.println("Rule ID:" + id);
		System.out.println("Rule IDname:" + busRule.getRuleSetIdentifier());	
	}
    
    protected String[] getConfigLocations() {
    	return new String[] {"classpath:/application-context.xml"};
    }
    
    private String mapMetaRuleToDroolRule(String metaRule) {
        
        try{
        	Properties p = new Properties();
        	p.setProperty("resource.loader", "class");
        	p.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader ");
        	Velocity.init(p);
        }
        catch( Exception e ){
        	System.out.println("Problem initializing Velocity : " + e );
        	return null;
        }
        
        ArrayList<String> facts = new ArrayList<String>();
        facts.add("MATH101");
        facts.add("MATH102");
        facts.add("MATH103");
        
        context.put( "criteria", new String("1") );
        context.put("facts", facts);
    	Template template = null;
   		StringWriter sw = new StringWriter();
    	
    	try
    	{
    		template = Velocity.getTemplate("RuleWhenTemplate.vm");
       		template.merge( context, sw );    	    	   	   
    	}
    	catch( ResourceNotFoundException rnfe )
    	{
    	   // couldn't find the template
    		System.out.println("Velocity: Could not find the template. " + rnfe );
    		return null;        		
    	}
    	catch( ParseErrorException pee )
    	{
    	  // syntax error: problem parsing the template
    		System.out.println("Velocity: parsing template error. " + pee );
    		return null;    		
    	}
    	catch( MethodInvocationException mie )
    	{
    	  // something invoked in the template threw an exception
    		System.out.println("Velocity: template method exception. " + mie );
    		return null;
    	}
    	catch( Exception e )
    	{
    		System.out.println("Velocity: error occured. " + e );
    		return null;    
    	}

   		return sw.toString();  
    }    
    
    public FunctionalBusinessRule getFunctionalBusinessRuleTest(String ruleID) throws Exception {
        
        FunctionalBusinessRule rule = getBusinessRuleSetDAO().lookupBusinessRuleID(ruleID);         
        //FunctionalBusinessRule ruleSet = businessRuleSetDAO.lookupBusinessRule(ruleSetId);
        
        if (rule != null) {
            System.out.println("Found rule: " + rule.getName());        
        } else {
            System.out.println("Rule " + ruleID + " not found");
        }
        
        return rule;
    }    
    
}
