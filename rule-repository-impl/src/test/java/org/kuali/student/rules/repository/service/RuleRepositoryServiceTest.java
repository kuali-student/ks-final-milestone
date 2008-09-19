package org.kuali.student.rules.repository.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.kuali.student.rules.internal.common.entity.ComparisonOperator;
import org.kuali.student.rules.repository.drools.util.DroolsUtil;
import org.kuali.student.rules.repository.dto.RuleSetDTO;
import org.kuali.student.rules.rulemanagement.dto.BusinessRuleContainerDTO;
import org.kuali.student.rules.rulemanagement.dto.BusinessRuleInfoDTO;
import org.kuali.student.rules.rulemanagement.dto.FactStructureDTO;
import org.kuali.student.rules.rulemanagement.dto.LeftHandSideDTO;
import org.kuali.student.rules.rulemanagement.dto.RightHandSideDTO;
import org.kuali.student.rules.rulemanagement.dto.RuleElementDTO;
import org.kuali.student.rules.rulemanagement.dto.RulePropositionDTO;
import org.kuali.student.rules.rulemanagement.dto.YieldValueFunctionDTO;
import org.kuali.student.rules.translators.util.Constants;
import org.kuali.student.poc.common.test.spring.AbstractServiceTest;
import org.kuali.student.poc.common.test.spring.Client;

public class RuleRepositoryServiceTest extends AbstractServiceTest {

    //private final static String businessRule = "A0";
    private final static String businessRule = "A0*B4+(C*D)";

	private final static String FACT_STRUCTURE_ID = "kuali.student.fact.id";
    
    @Client(value="org.kuali.student.rules.repository.service.impl.RuleRepositoryServiceImpl", port="8181")
    public RuleRepositoryService service; 

    @BeforeClass
    public static void setUpOnce() throws Exception {
    }

    @AfterClass
    public static void tearDownOnce() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    private RuleSetDTO createRuleSet() {
    	RuleSetDTO dto = new RuleSetDTO("TestName", "Test description", "DRL");
    	return dto;
    }

    /*private List<org.kuali.student.rules.util.Constraint> getConstraints() {
        List<org.kuali.student.rules.util.Constraint> list = new ArrayList<org.kuali.student.rules.util.Constraint>();
        
        list.add( createConstraint( "A0: A constraintID from DB") );
        list.add( createConstraint( "B4: A constraintID from DB") );
        list.add( createConstraint( "C: A constraintID from DB") );
        list.add( createConstraint( "D: A constraintID from DB") );
        
        return list;
    }
    
    private org.kuali.student.rules.util.Constraint createConstraint( String constraintID ) {
        ConstraintStrategy cs = new MockConstraint();
        cs.setConstraintID(constraintID);
        org.kuali.student.rules.util.Constraint c = 
            new org.kuali.student.rules.util.Constraint(cs); 
        return c;
    }*/
    
    @Test
    public void testCreateAndLoadCategories() throws Exception {
        boolean b = service.createCategory("/", "EnrollmentRules", "A test category 1.0 description");
        assertTrue(b);
        b = service.createCategory("/EnrollmentRules", "Math", "A Math category description");
        assertTrue(b);
        b = service.createCategory("/EnrollmentRules", "English", "An English category description");
        assertTrue(b);
        b = service.createCategory("/EnrollmentRules/Math", "PreReq", "A PreReq category description");
        assertTrue(b);
        b = service.createCategory("/EnrollmentRules/Math", "CoReq", "A CoReq category description");
        assertTrue(b);

        List<String> category = service.fetchChildCategories("/");
        // assertTrue( category.length == 1 );
        assertEquals("EnrollmentRules", category.get(0));

        category = service.fetchChildCategories("/EnrollmentRules");
        assertTrue(category.size() == 2);
        assertEquals("Math", category.get(0));
        assertEquals("English", category.get(1));

        category = service.fetchChildCategories("/EnrollmentRules/Math");
        assertTrue(category.size() == 2);
        assertEquals("PreReq", category.get(0));
        assertEquals("CoReq", category.get(1));
    }

    @Test
    public void testCreateRuleSet() throws Exception {
    	RuleSetDTO ruleSet = service.createRuleSet( createRuleSet() );
        assertNotNull( ruleSet );
        service.removeRuleSet( ruleSet.getUUID() );
        assertTrue( true );
    }

    @Test
    public void testRemoveRuleSet() throws Exception {
    	RuleSetDTO ruleSet = service.createRuleSet( createRuleSet() );
        assertNotNull( ruleSet.getUUID() );
        service.removeRuleSet( ruleSet.getUUID() );
        assertTrue( true );
    }

    @Test
    public void testLoadRuleSet() throws Exception {
    	RuleSetDTO ruleSet1 = service.createRuleSet( createRuleSet() );
        assertNotNull( ruleSet1 );
        
        RuleSetDTO ruleSet2 = service.fetchRuleSet(ruleSet1.getUUID());
        assertNotNull( ruleSet2 );
        System.out.println( "Rule Set: " + ruleSet2.toString());

        service.removeRuleSet( ruleSet1.getUUID() );
    }

    @Test
    public void testLoadCompiledRuleSet() throws Exception {
    	RuleSetDTO ruleSet1 = service.createRuleSet( createRuleSet() );
        assertNotNull( ruleSet1 );
        
        byte[] binPkg = service.fetchCompiledRuleSet(ruleSet1.getUUID());
        org.drools.rule.Package pkg = DroolsUtil.getInstance().getPackage(binPkg);
        System.out.println( "pkg: " + pkg);
        assertNotNull(pkg);
        assertTrue(pkg.isValid());
        System.out.println( "Rule Set: " + pkg.getName());

        service.removeRuleSet( ruleSet1.getUUID() );
    }

    private Date createDate(int year, int month, int day, int hourOfDay, int minute) {
    	Calendar cal = Calendar.getInstance();
    	cal.set(year, month-1, day, hourOfDay, minute);
    	return cal.getTime();
    }

    private BusinessRuleInfoDTO createBusinessRule(List<RuleElementDTO> ruleElementList) {
        BusinessRuleInfoDTO bri = new BusinessRuleInfoDTO();
    	bri.setName("Rule-1");
    	bri.setDescription("Some business rule");
    	bri.setSuccessMessage("Success message");
    	bri.setFailureMessage("Failure message");
    	bri.setBusinessRuleId("1");
    	bri.setBusinessRuleTypeKey("kuali.student.businessrule.typekey.course.corequisites");
    	bri.setAnchorTypeKey("kuali.student.lui.course.id");
    	bri.setAnchorValue("CPR101");
    	bri.setRuleElementList(ruleElementList);
    	Date effectiveStartTime = createDate(2000, 1, 1, 12, 00);
    	Date effectiveEndTime = createDate(2100, 1, 1, 12, 00);
    	bri.setEffectiveStartTime(effectiveStartTime);
    	bri.setEffectiveEndTime(effectiveEndTime);
    	return bri;
    }

    private RuleElementDTO createRule1(YieldValueFunctionDTO yieldValueFunction) {
    	// Rule 1
    	//YieldValueFunctionDTO yieldValueFunction = new YieldValueFunctionDTO();
        yieldValueFunction.setYieldValueFunctionType("INTERSECTION");
        
        LeftHandSideDTO leftHandSide = new LeftHandSideDTO();
        //leftHandSide.setYieldValueFunction(yieldValueFunction);
       
        RightHandSideDTO rightHandSide = new RightHandSideDTO();
        rightHandSide.setExpectedValue("1");
        
        RulePropositionDTO ruleProposition = new RulePropositionDTO();
        ruleProposition.setName("Must have 1 of CPR 101");
        ruleProposition.setDescription("Course intersection");
        ruleProposition.setLeftHandSide(leftHandSide);
        ruleProposition.setRightHandSide(rightHandSide);
        ruleProposition.setComparisonDataType(java.lang.Integer.class.getName());
        ruleProposition.setComparisonOperatorType(ComparisonOperator.GREATER_THAN_OR_EQUAL_TO.toString());

    	RuleElementDTO re = new RuleElementDTO();
        re.setName("course.intersection.1.of.cpr101");
        re.setDescription("Must have 1 of CPR 101");
        re.setOperation("PROPOSITION");
        re.setRuleProposition(ruleProposition);
        
        return re;
    }
    
    private RuleElementDTO createRule2(YieldValueFunctionDTO yieldValueFunction) {
    	// Rule 1
        yieldValueFunction.setYieldValueFunctionType("SUM");
        
        LeftHandSideDTO leftHandSide = new LeftHandSideDTO();
        leftHandSide.setYieldValueFunction(yieldValueFunction);
       
        RightHandSideDTO rightHandSide = new RightHandSideDTO();
        rightHandSide.setExpectedValue("10.0");
        
        RulePropositionDTO ruleProposition = new RulePropositionDTO();
        ruleProposition.setName("Course credit summation");
        ruleProposition.setDescription("Course credit summation");
        ruleProposition.setLeftHandSide(leftHandSide);
        ruleProposition.setRightHandSide(rightHandSide);
        ruleProposition.setComparisonDataType(java.lang.Double.class.getName());
        ruleProposition.setComparisonOperatorType(ComparisonOperator.GREATER_THAN_OR_EQUAL_TO.toString());

    	RuleElementDTO re = new RuleElementDTO();
        re.setName("course.credits.sum");
        re.setDescription("Pre req check for CPR 101");
        re.setOperation("PROPOSITION");
        re.setRuleProposition(ruleProposition);
        
        return re;
    }
    
    private void createFact1(YieldValueFunctionDTO yieldValueFunction1) {
    	// Facts - Rule 1 - 1 of CPR101
        FactStructureDTO fs1 = new FactStructureDTO();
        fs1.setDataType(java.util.Set.class.getName());
        fs1.setFactStructureId(FACT_STRUCTURE_ID);
        fs1.setAnchorFlag(false);

        Map<String,String> definitionVariableMap1 = new HashMap<String,String>();
        definitionVariableMap1.put(Constants.DEF_CRITERIA_KEY, "CPR101");
        fs1.setDefinitionVariableList(definitionVariableMap1);

        Map<String,String> executionVariableMap1 = new HashMap<String,String>();
        //executionVariableMap1.put(Constants.EXE_FACT_KEY, "intersection.courseSet");
        fs1.setExecutionVariableList(executionVariableMap1);
        
        List<FactStructureDTO> factStructureList1 = new ArrayList<FactStructureDTO>();
        factStructureList1.add(fs1);
        yieldValueFunction1.setFactStructureList(factStructureList1);
    }
    
    private void createFact2(YieldValueFunctionDTO yieldValueFunction2) {
        // Facts - Rule 2 - Sum of credit > 10.0
        FactStructureDTO fs2 = new FactStructureDTO();
        fs2.setDataType(java.math.BigDecimal.class.getName());
        fs2.setFactStructureId(FACT_STRUCTURE_ID);
        fs2.setAnchorFlag(false);

        // Not need for summation or averages
        Map<String,String> definitionVariableMap2 = new HashMap<String,String>();
        definitionVariableMap2.put(Constants.DEF_CRITERIA_KEY, null);
        fs2.setDefinitionVariableList(definitionVariableMap2);

        Map<String,String> executionVariableMap2 = new HashMap<String,String>();
        //executionVariableMap2.put(Constants.EXE_FACT_KEY, "summation.courseSet");
        fs2.setExecutionVariableList(executionVariableMap2);
        
        List<FactStructureDTO> factStructureList2 = new ArrayList<FactStructureDTO>();
        factStructureList2.add(fs2);
        yieldValueFunction2.setFactStructureList(factStructureList2);
    }
    
    private RuleElementDTO getAndOperator() {
    	RuleElementDTO re = new RuleElementDTO();
        re.setName("And");
        re.setDescription("And");
        re.setOperation("AND");
        
        return re;
    }
    
    private Set<String> createSet(String list) {
        Set<String> set = new HashSet<String>();
        for( String s : list.split(",") ) {
        	set.add(s.trim());
        }
        return set;
    }
    
    private List<BigDecimal> createList(String list) {
    	List<BigDecimal> set = new ArrayList<BigDecimal>();
        for( String s : list.split(",") ) {
        	set.add(new BigDecimal(s.trim()));
        }
        return set;
    }
    
    private Map<String,Object> getFacts() {
		Map<String,Object> factMap = new HashMap<String,Object>();

		Set<String> courseSet1 = createSet("CPR101,MATH102,CHEM100");
		factMap.put("intersection.courseSet", courseSet1);

        List<BigDecimal> courseSet2 = createList("3.0,6.0,3.0");
        factMap.put("summation.courseSet", courseSet2);
        return factMap;
    }
    
    private BusinessRuleContainerDTO getBusinessRuleContainer() {
    	// Yield value functions
    	YieldValueFunctionDTO yieldValueFunction1 = new YieldValueFunctionDTO();
    	YieldValueFunctionDTO yieldValueFunction2 = new YieldValueFunctionDTO();

        // Rule elements
    	List<RuleElementDTO> ruleElementList = new ArrayList<RuleElementDTO>();
        ruleElementList.add(createRule1(yieldValueFunction1));
        ruleElementList.add(getAndOperator());
        ruleElementList.add(createRule2(yieldValueFunction2));

        // Create functional business rule
        BusinessRuleInfoDTO bri = createBusinessRule(ruleElementList);

    	// Facts - Rule 1 - 1 of CPR101
    	createFact1(yieldValueFunction1);

        // Facts - Rule 2 - Sum of credit > 10.0
    	createFact2(yieldValueFunction2);

        // Parse and generate functional business rule into Drools rules
        BusinessRuleContainerDTO container = new BusinessRuleContainerDTO("course.co.req", "Cource Co-Requisites");
        container.getBusinessRules().add(bri);
        
        return container;
    }
    
    @Ignore
    @Test
    public void testGenerateRuleSet() throws Exception {
    	BusinessRuleContainerDTO container = getBusinessRuleContainer();
    	RuleSetDTO ruleSet = service.generateRuleSet(container);
    	assertNotNull(ruleSet);
    	assertNotNull(ruleSet.getUUID());
    }
}
