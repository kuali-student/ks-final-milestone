package org.kuali.student.enrollment.class1.krms.util;

import org.junit.Test;
import org.kuali.rice.krms.api.repository.LogicalOperator;
import org.kuali.rice.krms.api.repository.proposition.PropositionType;
import org.kuali.rice.krms.dto.PropositionEditor;
import org.kuali.rice.krms.dto.RuleEditor;
import org.kuali.rice.krms.util.AlphaIterator;
import org.kuali.rice.krms.util.ExpressionToken;
import org.kuali.rice.krms.util.PropositionTreeUtil;
import org.kuali.rice.krms.util.RuleLogicExpressionParser;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Test that the authorization decorator works against CourseOfferingService
 *
 * @author Kuali Student Team (ks.collab@kuali.org)
 */
public class TestRuleLogicExpressionParser {

    @Test
    public void testExpressionValidation(){
        String[] array = new String[] { "A","E","C","D" };
        List<String> errorMessages = new ArrayList<String>();
        RuleLogicExpressionParser parser = new RuleLogicExpressionParser();

        parser.setExpression("A(E AND C AND D)");
        parser.validateExpression(errorMessages, Arrays.asList(array));
        assertEquals(0, errorMessages.size());

        parser.setExpression("A(E AND C OR D)");
        parser.validateExpression(errorMessages, Arrays.asList(array));
        assertEquals(1, errorMessages.size());

    }

    @Test
    public void testParseExpressionToRule(){
        RuleLogicExpressionParser parser = new RuleLogicExpressionParser();

        RuleEditor rule = this.getFirstRuleEditor();
        String origExp = PropositionTreeUtil.configureLogicExpression(rule.getPropositionEditor());
        assertEquals(origExp, "A(B(C OR D) AND E)");

        //Build the expression:
        parser.setExpression("A(C OR B(E AND D))");
        PropositionEditor propositionEditor = parser.parseExpressionIntoRule(rule);

        String returnExp = PropositionTreeUtil.configureLogicExpression(propositionEditor);
        assertEquals(returnExp, "A(C OR B(E AND D))");
    }

    @Test
    public void testParseExpressionToRuleMultiProps(){
        RuleLogicExpressionParser parser = new RuleLogicExpressionParser();

        RuleEditor rule = this.getSecondRuleEditor();
        String origExp = PropositionTreeUtil.configureLogicExpression(rule.getPropositionEditor());
        assertEquals("A(C AND D AND E)", origExp);

        //Build the expression:
        parser.setExpression("A(E AND C AND D)");
        PropositionEditor propositionEditor = parser.parseExpressionIntoRule(rule);

        String returnExp = PropositionTreeUtil.configureLogicExpression(propositionEditor);
        assertEquals("A(E AND C AND D)", returnExp);
    }

    @Test
    public void testParseExpressionForAndOr(){
        RuleLogicExpressionParser parser = new RuleLogicExpressionParser();

        RuleEditor rule = this.getSecondRuleEditor();
        String origExp = PropositionTreeUtil.configureLogicExpression(rule.getPropositionEditor());
        assertEquals("A(C AND D AND E)", origExp);

        //Build the expression:
        parser.setExpression("A(E AND C OR D)");
        PropositionEditor propositionEditor = parser.parseExpressionIntoRule(rule);

        //Even though the second operator is an OR, the parser should ignore it.
        String returnExp = PropositionTreeUtil.configureLogicExpression(propositionEditor);
        assertEquals("A(E AND C AND D)", returnExp);
    }

    @Test
    public void testParseExpressionForAndOrWithCompoundChild(){
        RuleLogicExpressionParser parser = new RuleLogicExpressionParser();

        RuleEditor rule = this.getThirdRuleEditor();
        String origExp = PropositionTreeUtil.configureLogicExpression(rule.getPropositionEditor());
        assertEquals(origExp, "A(F AND B(C OR D) AND E)");

        //Build the expression:
        parser.setExpression("A(C AND B(E OR D) AND F)");
        PropositionEditor propositionEditor = parser.parseExpressionIntoRule(rule);

        String returnExp = PropositionTreeUtil.configureLogicExpression(propositionEditor);
        assertEquals("A(C AND B(E OR D) AND F)", returnExp);
    }

    @Test
    public void testRPN(){

        String exp = "A(E(F AND H) AND I AND J(K AND L))";
        PropositionEditor prop = parseExpression(exp);
        assertNotNull(prop);
        assertEquals(exp,PropositionTreeUtil.configureLogicExpression(prop));

        String exp2 = "A(B AND E(F AND H) AND I AND J(K AND L))";
        PropositionEditor prop2 = parseExpression(exp2);
        assertNotNull(prop2);
        assertEquals(exp2,PropositionTreeUtil.configureLogicExpression(prop2));

        String exp3 = "A(B AND E(F AND G AND H) AND I AND J(K AND L))";
        PropositionEditor prop3 = parseExpression(exp3);
        assertNotNull(prop3);
        assertEquals(exp3,PropositionTreeUtil.configureLogicExpression(prop3));

        String exp4 = "A(B AND E(F AND G AND H) AND I AND J(K AND L AND O))";
        PropositionEditor prop4 = parseExpression(exp4);
        assertNotNull(prop4);
        assertEquals(exp4,PropositionTreeUtil.configureLogicExpression(prop4));

        String exp5 = "A(B AND C AND D AND E(F AND G AND H) AND I AND J(K OR L OR O))";
        PropositionEditor prop5 = parseExpression(exp5);
        assertNotNull(prop5);
        assertEquals(exp5,PropositionTreeUtil.configureLogicExpression(prop5));

        String exp6 = "A(B AND C AND D AND E(F AND G AND H) AND I AND J(K OR L OR M(N AND O)))";
        PropositionEditor prop6 = parseExpression(exp6);
        assertNotNull(prop6);
        assertEquals(exp6,PropositionTreeUtil.configureLogicExpression(prop6));

    }

    private PropositionEditor parseExpression(String expression){

        RuleLogicExpressionParser parser = new RuleLogicExpressionParser();

        parser.setExpression(expression);
        Queue<ExpressionToken> rpnList = parser.getRPN(parser.getTokenList());

        AlphaIterator it = new AlphaIterator();
        List<PropositionEditor> rcs = new ArrayList<PropositionEditor>();
        for(int i = 0; i<15; i++){
            PropositionEditor prop = new PropositionEditor();
            prop.setKey((String)it.next());
            if("A".equals(prop.getKey())||"E".equals(prop.getKey())||"J".equals(prop.getKey())||"M".equals(prop.getKey())){
                prop.setPropositionTypeCode("C");
            } else {
                prop.setPropositionTypeCode("S");
            }
            rcs.add(prop);
        }
        return parser.ruleFromRPN(rpnList, rcs);

    }

    private RuleEditor getFirstRuleEditor(){
        //Build the root proposition
        PropositionEditor rootProp = this.createCompoundANDProposition("A", "Must have all of the following:");

        //Build the "B" proposition
        PropositionEditor beeProp = this.createCompoundORProposition("B", "Must have 1 of the following");

        //Add cee and dee to bee
        List<PropositionEditor> editors = new ArrayList<PropositionEditor>();
        editors.add(this.createSimpleProposition("C", "Must have a big grade"));
        editors.add(this.createSimpleProposition("D", "Must have permission"));
        beeProp.setCompoundEditors(editors);

        //Build the RuleEditor
        RuleEditor rule = new RuleEditor();
        editors = new ArrayList<PropositionEditor>();
        editors.add(beeProp);
        editors.add(this.createSimpleProposition("E", "Must have MATH100"));
        rootProp.setCompoundEditors(editors);
        rule.setProposition(rootProp);

        return rule;
    }

    private RuleEditor getSecondRuleEditor(){
        //Build the root proposition
        PropositionEditor rootProp = this.createCompoundANDProposition("A", "Must have all of the following:");

        //Build the RuleEditor
        RuleEditor rule = new RuleEditor();
        List<PropositionEditor> editors = new ArrayList<PropositionEditor>();
        editors.add(this.createSimpleProposition("C", "Must have a big grade"));
        editors.add(this.createSimpleProposition("D", "Must have permission"));
        editors.add(this.createSimpleProposition("E", "Must have MATH100"));
        rootProp.setCompoundEditors(editors);
        rule.setProposition(rootProp);

        return rule;
    }

    private RuleEditor getThirdRuleEditor(){
        //Build the root proposition
        PropositionEditor rootProp = this.createCompoundANDProposition("A", "Must have all of the following:");

        //Build the "B" proposition
        PropositionEditor beeProp = this.createCompoundORProposition("B", "Must have 1 of the following");

        //Add cee and dee to bee
        List<PropositionEditor> editors = new ArrayList<PropositionEditor>();
        editors.add(this.createSimpleProposition("C", "Must have a big grade"));
        editors.add(this.createSimpleProposition("D", "Must have permission"));
        beeProp.setCompoundEditors(editors);

        //Build the RuleEditor
        RuleEditor rule = new RuleEditor();
        editors = new ArrayList<PropositionEditor>();
        editors.add(this.createSimpleProposition("F", "Must have MATH200"));
        editors.add(beeProp);
        editors.add(this.createSimpleProposition("E", "Must have MATH100"));
        rootProp.setCompoundEditors(editors);
        rule.setProposition(rootProp);

        return rule;
    }

    private PropositionEditor createProposition(String key, String desc){
        PropositionEditor prop = new PropositionEditor();
        prop.setKey(key);
        prop.setDescription(desc);
        return prop;
    }

    private PropositionEditor createSimpleProposition(String key, String desc){
        PropositionEditor prop = createProposition(key, desc);
        prop.setPropositionTypeCode(PropositionType.SIMPLE.getCode());
        return prop;
    }

    private PropositionEditor createCompoundORProposition(String key, String desc){
        PropositionEditor prop = createProposition(key, desc);
        prop.setCompoundOpCode(LogicalOperator.OR.getCode());
        prop.setPropositionTypeCode(PropositionType.COMPOUND.getCode());
        return prop;
    }

    private PropositionEditor createCompoundANDProposition(String key, String desc){
        PropositionEditor prop = createProposition(key, desc);
        prop.setCompoundOpCode(LogicalOperator.AND.getCode());
        prop.setPropositionTypeCode(PropositionType.COMPOUND.getCode());
        return prop;
    }
}
