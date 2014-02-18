package org.kuali.student.core.krms.util;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.kuali.rice.krms.api.repository.LogicalOperator;
import org.kuali.rice.krms.api.repository.proposition.PropositionType;
import org.kuali.rice.krms.dto.PropositionEditor;
import org.kuali.rice.krms.dto.RuleEditor;
import org.kuali.rice.krms.service.RuleViewHelperService;
import org.kuali.rice.krms.service.impl.RuleViewHelperServiceImpl;
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

    private RuleViewHelperService viewHelper = new RuleViewHelperServiceImpl();

    @Test
    public void testExpressionValidation(){

        String[] array = new String[] { "A","E","C","D" };
        List<String> errorMessages = new ArrayList<String>();
        RuleLogicExpressionParser parser = new RuleLogicExpressionParser();

        parser.setExpression("E AND (C AND D)");
        parser.validateExpression(errorMessages, Arrays.asList(array));
        assertEquals(0, errorMessages.size());

        parser.setExpression("A AND (E AND C OR D)");
        parser.validateExpression(errorMessages, Arrays.asList(array));
        assertEquals(1, errorMessages.size());

    }

    @Test
    public void testParseExpressionToRule(){
        RuleLogicExpressionParser parser = new RuleLogicExpressionParser();

        RuleEditor rule = this.getFirstRuleEditor();
        String origExp = PropositionTreeUtil.configureLogicExpression(rule.getPropositionEditor());
        assertEquals("(C OR D) AND E", origExp);

        //Build the expression:
        parser.setExpression("C OR (E AND D)");
        PropositionEditor propositionEditor = parser.parseExpressionIntoRule(rule, viewHelper);

        String returnExp = PropositionTreeUtil.configureLogicExpression(propositionEditor);
        assertEquals("C OR (E AND D)", returnExp);
    }

    @Test
    public void testParseExpressionToRuleMultiProps(){
        RuleLogicExpressionParser parser = new RuleLogicExpressionParser();

        RuleEditor rule = this.getSecondRuleEditor();
        String origExp = PropositionTreeUtil.configureLogicExpression(rule.getPropositionEditor());
        assertEquals("C AND D AND E", origExp);

        //Build the expression:
        parser.setExpression("E AND C AND D");
        PropositionEditor propositionEditor = parser.parseExpressionIntoRule(rule, viewHelper);

        String returnExp = PropositionTreeUtil.configureLogicExpression(propositionEditor);
        assertEquals("E AND C AND D", returnExp);
    }

    @Test
    public void testParseExpressionForAndOr(){
        RuleLogicExpressionParser parser = new RuleLogicExpressionParser();

        RuleEditor rule = this.getSecondRuleEditor();
        String origExp = PropositionTreeUtil.configureLogicExpression(rule.getPropositionEditor());
        assertEquals("C AND D AND E", origExp);

        //Build the expression:
        parser.setExpression("E AND C OR D");
        PropositionEditor propositionEditor = parser.parseExpressionIntoRule(rule, viewHelper);

        //Even though the second operator is an OR, the parser should ignore it.
        String returnExp = PropositionTreeUtil.configureLogicExpression(propositionEditor);
        assertEquals("E AND C AND D", returnExp);
    }

    @Test
    public void testParseExpressionForAndOrWithCompoundChild(){
        RuleLogicExpressionParser parser = new RuleLogicExpressionParser();

        RuleEditor rule = this.getThirdRuleEditor();
        String origExp = PropositionTreeUtil.configureLogicExpression(rule.getPropositionEditor());
        assertEquals(origExp, "F AND (C OR D) AND E");

        //Build the expression:
        parser.setExpression("(C AND (E OR D) AND F");
        PropositionEditor propositionEditor = parser.parseExpressionIntoRule(rule, viewHelper);

        String returnExp = PropositionTreeUtil.configureLogicExpression(propositionEditor);
        assertEquals("C AND (E OR D) AND F", returnExp);
    }

    @Test
    public void testRuleFromStack(){

        String exp = "(F AND H) AND I AND (K AND L)";
        PropositionEditor prop = parseExpression(exp);
        assertNotNull(prop);
        assertEquals(exp,PropositionTreeUtil.configureLogicExpression(prop));

        String exp2 = "B AND (F AND H) AND I AND (K AND L)";
        PropositionEditor prop2 = parseExpression(exp2);
        assertNotNull(prop2);
        assertEquals(exp2,PropositionTreeUtil.configureLogicExpression(prop2));

        String exp3 = "B AND (F AND G AND H) AND I AND (K AND L)";
        PropositionEditor prop3 = parseExpression(exp3);
        assertNotNull(prop3);
        assertEquals(exp3,PropositionTreeUtil.configureLogicExpression(prop3));

        String exp4 = "B AND (F AND G AND H) AND I AND (K AND L AND O)";
        PropositionEditor prop4 = parseExpression(exp4);
        assertNotNull(prop4);
        assertEquals(exp4,PropositionTreeUtil.configureLogicExpression(prop4));

        String exp5 = "B AND C AND D AND (F AND G AND H) AND I AND (K OR L OR O)";
        PropositionEditor prop5 = parseExpression(exp5);
        assertNotNull(prop5);
        assertEquals(exp5,PropositionTreeUtil.configureLogicExpression(prop5));

        String exp6 = "B AND C AND D AND (F AND G AND H) AND I AND (K OR L OR (N AND O))";
        PropositionEditor prop6 = parseExpression(exp6);
        assertNotNull(prop6);
        assertEquals(exp6,PropositionTreeUtil.configureLogicExpression(prop6));

    }

    @Test
    public void testParserRemoveGroup(){
        RuleLogicExpressionParser parser = new RuleLogicExpressionParser();

        String exp = "F AND H AND I AND K AND L";
        RuleEditor rule = new RuleEditor();
        rule.setProposition(parseExpression(rule, "(F AND H) AND I AND (K AND L)"));
        parser.setExpression(exp);
        PropositionEditor prop = parser.parseExpressionIntoRule(rule, viewHelper);
        assertEquals(exp,PropositionTreeUtil.configureLogicExpression(prop));

        String exp2 = "B AND F AND H AND I AND K AND L";
        RuleEditor rule2 = new RuleEditor();
        rule2.setProposition(parseExpression(rule2, "B AND (F AND H) AND I AND (K AND L)"));
        parser.setExpression(exp2);
        PropositionEditor prop2 = parser.parseExpressionIntoRule(rule2, viewHelper);
        assertEquals(exp2,PropositionTreeUtil.configureLogicExpression(prop2));

        String exp3 = "B AND F AND G AND H AND I AND K AND L";
        RuleEditor rule3 = new RuleEditor();
        rule3.setProposition(parseExpression(rule3, "B AND (F AND G AND H) AND I AND (K AND L)"));
        parser.setExpression(exp3);
        PropositionEditor prop3 = parser.parseExpressionIntoRule(rule3, viewHelper);
        assertEquals(exp3,PropositionTreeUtil.configureLogicExpression(prop3));
    }

    @Test
    public void testParserAddGroup(){
        RuleLogicExpressionParser parser = new RuleLogicExpressionParser();

        String exp = "(F AND H) AND I AND (K AND L)";
        RuleEditor rule = new RuleEditor();
        rule.setProposition(parseExpression(rule, "F AND H AND I AND K AND L"));
        parser.setExpression(exp);
        PropositionEditor prop = parser.parseExpressionIntoRule(rule, viewHelper);
        assertEquals(exp,PropositionTreeUtil.configureLogicExpression(prop));

        String exp2 = "B AND (F AND H) AND I AND (K AND L)";
        RuleEditor rule2 = new RuleEditor();
        rule2.setProposition(parseExpression(rule2, "B AND F AND H AND I AND K AND L"));
        parser.setExpression(exp2);
        PropositionEditor prop2 = parser.parseExpressionIntoRule(rule2, viewHelper);
        assertEquals(exp2,PropositionTreeUtil.configureLogicExpression(prop2));

        String exp3 = "B AND (F AND G AND H) AND I AND (K AND L)";
        RuleEditor rule3 = new RuleEditor();
        rule3.setProposition(parseExpression(rule3, "B AND F AND G AND H AND I AND K AND L"));
        parser.setExpression(exp3);
        PropositionEditor prop3 = parser.parseExpressionIntoRule(rule3, viewHelper);
        assertEquals(exp3,PropositionTreeUtil.configureLogicExpression(prop3));
    }

    private PropositionEditor parseExpression(String expression){
        return parseExpression(new RuleEditor(), expression);
    }

    private PropositionEditor parseExpression(RuleEditor rule, String expression){

        RuleLogicExpressionParser parser = new RuleLogicExpressionParser();

        parser.setExpression(expression);
        Stack<ExpressionToken> tokenStack = new Stack<ExpressionToken>();
        for (ExpressionToken token : parser.getTokenList()) {
            tokenStack.push(token);
        }

        AlphaIterator it = new AlphaIterator(StringUtils.EMPTY);
        Map<String, PropositionEditor> simplePropositions = new HashMap<String, PropositionEditor>();
        Queue<PropositionEditor> compoundPropositions = new LinkedList<PropositionEditor>();
        for(int i = 0; i<15; i++){
            PropositionEditor prop = new PropositionEditor();
            prop.setKey((String)it.next());
            if("A".equals(prop.getKey())||"E".equals(prop.getKey())||"J".equals(prop.getKey())||"M".equals(prop.getKey())){
                prop.setPropositionTypeCode("C");
                prop.setCompoundEditors(new ArrayList<PropositionEditor>());
                compoundPropositions.add(prop);
            } else {
                prop.setPropositionTypeCode("S");
                simplePropositions.put(prop.getKey(),prop);
            }
        }
        return parser.ruleFromStack(tokenStack, simplePropositions, compoundPropositions, rule, viewHelper);

    }

    private RuleEditor getFirstRuleEditor(){
        //Build the root proposition
        PropositionEditor rootProp = this.createCompoundANDProposition("C-A", "Must have all of the following:");

        //Build the "B" proposition
        PropositionEditor beeProp = this.createCompoundORProposition("C-B", "Must have 1 of the following");

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
