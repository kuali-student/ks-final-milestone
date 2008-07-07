package org.kuali.student.rules.runtime.ast;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.kuali.student.rules.common.statement.PropositionContainer;
import org.kuali.student.rules.common.statement.PropositionReport;
import org.kuali.student.rules.common.statement.SubsetProposition;
import org.kuali.student.rules.rulesetexecution.runtime.ast.GenerateRuleReport;

public class GenerateRuleReportTest {

	private String functionalRuleString;
	
	private SubsetProposition<String> subsetPropA = new SubsetProposition<String>("A", null, null, null, null );
	private SubsetProposition<String> subsetPropB = new SubsetProposition<String>("B", null, null, null, null );
	private SubsetProposition<String> subsetPropC = new SubsetProposition<String>("C", null, null, null, null );
	private SubsetProposition<String> subsetPropD = new SubsetProposition<String>("D", null, null, null, null );
	
	private PropositionReport propositionReportA = new PropositionReport();
	private PropositionReport propositionReportB = new PropositionReport();
	private PropositionReport propositionReportC = new PropositionReport();
	private PropositionReport propositionReportD = new PropositionReport();
    
	@Before
	public void setUp() throws Exception {
	}
	
	@Test
	public void testExecuteRuleFailureMessage1()
	{
	    functionalRuleString = "A*B*C*D";
	    
	    PropositionContainer pc = new PropositionContainer();
	    pc.setFunctionalRuleString(functionalRuleString);
	    pc.setRuleResult(false);
	    
	    subsetPropA.setResult(false);
	    subsetPropB.setResult(true);
	    subsetPropC.setResult(true);
	    subsetPropD.setResult(true);
	    
	    propositionReportA.setFailureMessage("Need MATH 200");
	    propositionReportB.setFailureMessage("Need MATH 110");
	    propositionReportC.setFailureMessage("Need 15 credits or more of 1st year science");
	    propositionReportD.setFailureMessage("Need English 6000");
	    
	    subsetPropA.setReport(propositionReportA);
	    subsetPropB.setReport(propositionReportB);
	    subsetPropC.setReport(propositionReportC);
	    subsetPropD.setReport(propositionReportD);
	    
	    pc.addProposition(subsetPropA);
	    pc.addProposition(subsetPropB);
	    pc.addProposition(subsetPropC);
	    pc.addProposition(subsetPropD);
	    
	    String expected = "Need MATH 200";
	    
	    PropositionContainer propContainer = new GenerateRuleReport().executeRule(pc);
	    PropositionReport ruleReport = propContainer.getRuleReport();
	    String actual = ruleReport.getFailureMessage();
	    
        assertEquals(expected, actual);
	    
	}
	
	@Test
	public void testExecuteRuleFailureMessage2()
	{
	    // New code for PropositionContainer starts here
	    functionalRuleString = "A+(B*C)+D";
        
        PropositionContainer pc = new PropositionContainer();
        pc.setFunctionalRuleString(functionalRuleString);
        pc.setRuleResult(false);
        
        // need to set proposition result and failure message for testing.
        subsetPropA.setResult(false);
        subsetPropB.setResult(true);
        subsetPropC.setResult(false);
        subsetPropD.setResult(false);
        
        propositionReportA.setFailureMessage("Need MATH 200");
        propositionReportB.setFailureMessage("Need MATH 110");
        propositionReportC.setFailureMessage("Need 15 credits or more of 1st year science");
        propositionReportD.setFailureMessage("Need English 6000");
        
        subsetPropA.setReport(propositionReportA);
        subsetPropB.setReport(propositionReportB);
        subsetPropC.setReport(propositionReportC);
        subsetPropD.setReport(propositionReportD);
        
        pc.addProposition(subsetPropA);
        pc.addProposition(subsetPropB);
        pc.addProposition(subsetPropC);
        pc.addProposition(subsetPropD);
        
        String expected = "Need MATH 200 OR Need 15 credits or more of 1st year science OR Need English 6000";
        
        PropositionContainer propContainer = new GenerateRuleReport().executeRule(pc);
        PropositionReport ruleReport = propContainer.getRuleReport();
        String actual = ruleReport.getFailureMessage();
        
        assertEquals(expected, actual);
	}
	
	
	
	
	@Test
    public void testExecuteRuleSuccessMessage1()
    {
        functionalRuleString = "A*B*C*D";
        
        PropositionContainer pc = new PropositionContainer();
        pc.setFunctionalRuleString(functionalRuleString);
        pc.setRuleResult(true);
        
        subsetPropA.setResult(true);
        subsetPropB.setResult(true);
        subsetPropC.setResult(true);
        subsetPropD.setResult(true);
        
        propositionReportA.setSuccessMessage("Have MATH 200");
        propositionReportB.setSuccessMessage("Have MATH 110");
        propositionReportC.setSuccessMessage("Have 15 credits or more of 1st year science");
        propositionReportD.setSuccessMessage("Have English 6000");
        
        subsetPropA.setReport(propositionReportA);
        subsetPropB.setReport(propositionReportB);
        subsetPropC.setReport(propositionReportC);
        subsetPropD.setReport(propositionReportD);
        
        pc.addProposition(subsetPropA);
        pc.addProposition(subsetPropB);
        pc.addProposition(subsetPropC);
        pc.addProposition(subsetPropD);
        
        String expected = "Have MATH 200 AND Have MATH 110 AND Have 15 credits or more of 1st year science AND Have English 6000";
        
        PropositionContainer propContainer = new GenerateRuleReport().executeRule(pc);
        PropositionReport ruleReport = propContainer.getRuleReport();
        String actual = ruleReport.getSuccessMessage();
        
        assertEquals(expected, actual);
        
    }
    
    @Test
    public void testExecuteRuleSuccessMessage2()
    {
        // New code for PropositionContainer starts here
        functionalRuleString = "A+(B*C)+D";
        
        PropositionContainer pc = new PropositionContainer();
        pc.setFunctionalRuleString(functionalRuleString);
        pc.setRuleResult(true);
        
        // need to set proposition result and failure message for testing.
        subsetPropA.setResult(true);
        subsetPropB.setResult(true);
        subsetPropC.setResult(false);
        subsetPropD.setResult(true);
        
        propositionReportA.setSuccessMessage("Have MATH 200");
        propositionReportB.setSuccessMessage("Have MATH 110");
        propositionReportC.setSuccessMessage("Have 15 credits or more of 1st year science");
        propositionReportD.setSuccessMessage("Have English 6000");
        
        subsetPropA.setReport(propositionReportA);
        subsetPropB.setReport(propositionReportB);
        subsetPropC.setReport(propositionReportC);
        subsetPropD.setReport(propositionReportD);
        
        pc.addProposition(subsetPropA);
        pc.addProposition(subsetPropB);
        pc.addProposition(subsetPropC);
        pc.addProposition(subsetPropD);
        
        String expected = "Have MATH 200 OR Have English 6000";
        
        PropositionContainer propContainer = new GenerateRuleReport().executeRule(pc);
        PropositionReport ruleReport = propContainer.getRuleReport();
        String actual = ruleReport.getSuccessMessage();
        
        assertEquals(expected, actual);
    }
	
	@After
	public void tearDown() throws Exception {
	}

}
