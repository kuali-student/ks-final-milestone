package org.kuali.student.rules.brms.drools.translator;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;

import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.drools.RuleBase;
import org.drools.RuleBaseFactory;
import org.drools.WorkingMemory;
import org.drools.compiler.PackageBuilder;
import org.drools.rule.Package;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.kuali.student.rules.brms.core.entity.ComparisonOperator;
import org.kuali.student.rules.brms.core.entity.ComputationAssistant;
import org.kuali.student.rules.brms.core.entity.FunctionalBusinessRule;
import org.kuali.student.rules.brms.core.entity.LeftHandSide;
import org.kuali.student.rules.brms.core.entity.Operator;
import org.kuali.student.rules.brms.core.entity.RightHandSide;
import org.kuali.student.rules.brms.core.entity.RuleElement;
import org.kuali.student.rules.brms.core.entity.RuleElementType;
import org.kuali.student.rules.brms.core.entity.RuleMetaData;
import org.kuali.student.rules.brms.core.entity.RuleProposition;
import org.kuali.student.rules.brms.core.entity.ValueType;
import org.kuali.student.rules.brms.core.entity.YieldValueFunction;
import org.kuali.student.rules.brms.repository.rule.RuleSet;
import org.kuali.student.rules.common.util.CourseEnrollmentRequest;
import org.kuali.student.rules.statement.PropositionContainer;


public class GenerateRuleSetTest {

    private final String FACT_CONTAINER = "AcademicRecord";
    private GenerateRuleSet generateRuleSet = GenerateRuleSet.getInstance();
    private FunctionalBusinessRule businessRule;

    @Before
    public void setUp() throws Exception {
        int ordinalPosition = 1;

        RuleMetaData metaData = new RuleMetaData("Tom Smith", new Date(), "", null, new Date(), new Date(), "1.1",
        "active");
        // create basic rule structure
        businessRule = new FunctionalBusinessRule("Intermediate CPR",
                "enrollment co-requisites for Intermediate CPR 201", "Rule 1 Success Message",
                "Rule 1 Failure Message", "1", null, metaData, "Student Enrolls in Course", "course-co-req", "course",
                "CPR 201");

        // 1 of CPR 101
        ComputationAssistant compAssistant = new ComputationAssistant(YieldValueFunction.INTERSECTION);
        LeftHandSide leftSide = new LeftHandSide("CPR 101", FACT_CONTAINER, compAssistant, ValueType.NUMBER);
        Operator operator = new Operator(ComparisonOperator.EQUAL_TO);
        RightHandSide rightSide = new RightHandSide("java.lang.Integer", "1");
        RuleProposition ruleProp = new RuleProposition("co-requisites", "enumeration of required co-requisite courses",
                "prop error message", leftSide, operator, rightSide);
        RuleElement ruleElement = new RuleElement(RuleElementType.PROPOSITION_TYPE, ordinalPosition++, "", "", null, ruleProp);
        businessRule.addRuleElement(ruleElement);
    }

    @After
    public void tearDown() throws Exception {
        businessRule = null;
    }
    
    private static RuleBase getRuleBase( String source ) throws Exception {
        PackageBuilder builder = new PackageBuilder();

        builder.addPackageFromDrl( new StringReader( source ) );

        Package pkg = builder.getPackage();
        
        //add the package to a rulebase (deploy the rule package).
        RuleBase ruleBase = RuleBaseFactory.newRuleBase();
        ruleBase.addPackage( pkg );
        return ruleBase;
    }
    
    private CourseEnrollmentRequest getRequest() {
        CourseEnrollmentRequest request = new CourseEnrollmentRequest();
        Set<String> luiIds = new HashSet<String>( Arrays.asList( "CPR 101,MATH 102".split(",") ) );
        request.setLuiIds( luiIds );
        return request;
    }

    @Test
    public void testParse() throws Exception {
        // Generate Drools rule set source code
        RuleSet ruleSet = this.generateRuleSet.parse( this.businessRule );
        assertNotNull( ruleSet );
        
        // Collection of Propositions
        PropositionContainer prop = new PropositionContainer();

        // Rule set source code
        String source = ruleSet.getContent();

        // Execute Drools rule set source code
        WorkingMemory workingMemory = getRuleBase( source ).newStatefulSession();
        workingMemory.insert( getRequest() );
        workingMemory.insert( prop );
        workingMemory.fireAllRules();
        assertTrue( prop.getRuleResult() );
    }
}
