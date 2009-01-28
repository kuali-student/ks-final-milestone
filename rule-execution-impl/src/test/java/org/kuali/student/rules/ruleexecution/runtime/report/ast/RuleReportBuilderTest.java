package org.kuali.student.rules.ruleexecution.runtime.report.ast;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kuali.student.rules.internal.common.statement.PropositionContainer;
import org.kuali.student.rules.internal.common.statement.propositions.IntersectionProposition;
import org.kuali.student.rules.internal.common.statement.propositions.PropositionType;
import org.kuali.student.rules.internal.common.statement.report.PropositionReport;
import org.kuali.student.rules.internal.common.statement.report.RuleReport;
import org.kuali.student.rules.ruleexecution.runtime.drools.DroolsRuleBase;
import org.kuali.student.rules.ruleexecution.runtime.drools.SimpleExecutorDroolsImpl;
import org.kuali.student.rules.ruleexecution.runtime.report.ast.RuleReportBuilder;

public class RuleReportBuilderTest {
	private String functionalRuleString;

	private IntersectionProposition<String> subsetPropA = new IntersectionProposition<String>("A1", "A", null, null, null, null );
	private IntersectionProposition<String> subsetPropB = new IntersectionProposition<String>("B1", "B", null, null, null, null );
	private IntersectionProposition<String> subsetPropC = new IntersectionProposition<String>("C1", "C", null, null, null, null );
	private IntersectionProposition<String> subsetPropD = new IntersectionProposition<String>("D1", "D", null, null, null, null );

	private PropositionReport propositionReportA = new PropositionReport("A", PropositionType.INTERSECTION);
	private PropositionReport propositionReportB = new PropositionReport("B", PropositionType.INTERSECTION);
	private PropositionReport propositionReportC = new PropositionReport("C", PropositionType.INTERSECTION);
	private PropositionReport propositionReportD = new PropositionReport("D", PropositionType.INTERSECTION);

	private RuleReportBuilder ruleReportBuilder;
    private final static DroolsRuleBase ruleBase = new DroolsRuleBase();
	private static SimpleExecutorDroolsImpl executor = new SimpleExecutorDroolsImpl();

    @BeforeClass
    public static void setUpOnce() throws Exception {
		executor.setEnableStatisticsLogging(true);
    	executor.setRuleBaseCache(ruleBase);
    }
    
    @AfterClass
    public static void tearDownOnce() throws Exception {
    	executor = null;
    }

	@Before
	public void setUp() throws Exception {
	    this.ruleReportBuilder = new RuleReportBuilder(executor);
	}
	
	@After
	public void tearDown() throws Exception {
	}

	private PropositionReport getProposition(List<PropositionReport> list, String name) {
		for(PropositionReport report : list) {
			if(report.getPropositionName().equals(name)) {
				return report;
			}
		}
		return null;
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
	    
	    RuleReport ruleReport = ruleReportBuilder.execute(pc);
	    String actual = ruleReport.getFailureMessage();
	    
	    Assert.assertEquals(expected, actual);
        Assert.assertEquals(4, ruleReport.getPropositionReports().size());
        Assert.assertFalse(getProposition(ruleReport.getPropositionReports(), "A").isSuccessful());
        Assert.assertTrue(getProposition(ruleReport.getPropositionReports(), "B").isSuccessful());
        Assert.assertTrue(getProposition(ruleReport.getPropositionReports(), "C").isSuccessful());
        Assert.assertTrue(getProposition(ruleReport.getPropositionReports(), "D").isSuccessful());
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
        
        RuleReport ruleReport = ruleReportBuilder.execute(pc);
        String actual = ruleReport.getFailureMessage();
        
        Assert.assertEquals(expected, actual);
        Assert.assertEquals(4, ruleReport.getPropositionReports().size());
        Assert.assertFalse(getProposition(ruleReport.getPropositionReports(), "A").isSuccessful());
        Assert.assertTrue(getProposition(ruleReport.getPropositionReports(), "B").isSuccessful());
        Assert.assertFalse(getProposition(ruleReport.getPropositionReports(), "C").isSuccessful());
        Assert.assertFalse(getProposition(ruleReport.getPropositionReports(), "D").isSuccessful());
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
        
        RuleReport ruleReport = ruleReportBuilder.execute(pc);
        String actual = ruleReport.getSuccessMessage();
        
        Assert.assertEquals(expected, actual);
        Assert.assertEquals(4, ruleReport.getPropositionReports().size());
        Assert.assertTrue(getProposition(ruleReport.getPropositionReports(), "A").isSuccessful());
        Assert.assertTrue(getProposition(ruleReport.getPropositionReports(), "B").isSuccessful());
        Assert.assertTrue(getProposition(ruleReport.getPropositionReports(), "C").isSuccessful());
        Assert.assertTrue(getProposition(ruleReport.getPropositionReports(), "D").isSuccessful());
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
        
        RuleReport ruleReport = ruleReportBuilder.execute(pc);
        String actual = ruleReport.getSuccessMessage();
        
        Assert.assertEquals(expected, actual);
        Assert.assertEquals(4, ruleReport.getPropositionReports().size());
        Assert.assertTrue(getProposition(ruleReport.getPropositionReports(), "A").isSuccessful());
        Assert.assertTrue(getProposition(ruleReport.getPropositionReports(), "B").isSuccessful());
        Assert.assertFalse(getProposition(ruleReport.getPropositionReports(), "C").isSuccessful());
        Assert.assertTrue(getProposition(ruleReport.getPropositionReports(), "D").isSuccessful());
    }

}
