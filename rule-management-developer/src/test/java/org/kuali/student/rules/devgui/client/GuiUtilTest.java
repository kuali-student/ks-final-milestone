package org.kuali.student.rules.devgui.client;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.kuali.student.rules.rulemanagement.dto.LeftHandSideDTO;
import org.kuali.student.rules.rulemanagement.dto.RightHandSideDTO;
import org.kuali.student.rules.rulemanagement.dto.RuleElementDTO;
import org.kuali.student.rules.rulemanagement.dto.RulePropositionDTO;
import org.kuali.student.rules.rulemanagement.dto.YieldValueFunctionDTO;

/**
 * @author zzraly
 */
public class GuiUtilTest {

    /*
     * Creates a string of text that represents the complete rule, including details on each proposition (left, operator and right hand side)
     * 
     * @param composition - business rule composition e.g. (P1 AND P2) OR P3
     * @param definedPropositions - list of proposition details with sequence numbers e.g. P1
     * @return list of business rule types
     * @throws OperationFailedException
     * 
     */

    @Test
    public void testAssembleRuleFromComposition() {

        Map<Integer, RuleElementDTO> definedPropositions = new HashMap<Integer, RuleElementDTO>();

        RuleElementDTO ruleElem = new RuleElementDTO();
        RulePropositionDTO ruleProp = new RulePropositionDTO();
        LeftHandSideDTO leftHandSide = new LeftHandSideDTO();
        YieldValueFunctionDTO yvf = new YieldValueFunctionDTO();
        yvf.setYieldValueFunctionType(GuiUtil.YieldValueFunctionType.INTERSECTION.name());
        leftHandSide.setYieldValueFunction(yvf);
        RightHandSideDTO rightHandSide = new RightHandSideDTO();
        rightHandSide.setExpectedValue("10");
        ruleProp.setLeftHandSide(leftHandSide);
        ruleProp.setComparisonOperatorType(GuiUtil.ComparisonOperator.EQUAL_TO.name());
        ruleProp.setRightHandSide(rightHandSide);
        ruleElem.setRuleProposition(ruleProp);

        // populate test data
        definedPropositions.put(1, ruleElem);
        definedPropositions.put(2, ruleElem);
        definedPropositions.put(3, ruleElem);
        definedPropositions.put(4, ruleElem);
        definedPropositions.put(5, ruleElem);

        assertEquals("A", GuiUtil.assembleRuleFromComposition("A", definedPropositions));
        assertEquals("INTERSECTION = 10", GuiUtil.assembleRuleFromComposition("P1", definedPropositions));
        assertEquals("INTERSECTION = 10", GuiUtil.assembleRuleFromComposition(" P1 ", definedPropositions));
        assertEquals("( INTERSECTION = 10 )", GuiUtil.assembleRuleFromComposition(" ( P1)", definedPropositions));
        assertEquals("INTERSECTION = 10 AND INTERSECTION = 10", GuiUtil.assembleRuleFromComposition("P1 AND P2", definedPropositions));
        assertEquals("( INTERSECTION = 10 AND INTERSECTION = 10 )", GuiUtil.assembleRuleFromComposition("(P1 AND P2)", definedPropositions));
        assertEquals("( INTERSECTION = 10 AND INTERSECTION = 10 ) OR INTERSECTION = 10", GuiUtil.assembleRuleFromComposition("(P1 AND P2) OR P3", definedPropositions));
        assertEquals("( INTERSECTION = 10 AND INTERSECTION = 10 ) OR INTERSECTION = 10", GuiUtil.assembleRuleFromComposition("(P1 AND P2) OR P3", definedPropositions));
        assertEquals("( INTERSECTION = 10 AND INTERSECTION = 10 ) OR ( INTERSECTION = 10 AND INTERSECTION = 10 )", GuiUtil.assembleRuleFromComposition("(P1 AND P2) OR ( P3 AND P4 ) ", definedPropositions));
        assertEquals("( ( INTERSECTION = 10 OR INTERSECTION = 10 ) AND INTERSECTION = 10 )", GuiUtil.assembleRuleFromComposition("( ( P1 OR P2) AND P3)", definedPropositions));
    }

    /*
     * Retrieves next token from rule composition e.g. Px, (, ), OR, AND
     * 
     * @param composition - business rule composition e.g. (P1 AND P2) OR P3
     * @param definedPropositions - list of proposition details with sequence numbers e.g. P1
     * @return list of business rule types
     * @throws OperationFailedException
     * 
     */

    @Test
    public void testGetNextTokenFromComposition() {

    }

    @Test
    public void testValidateRuleComposition() {

    }

    // call when parsing Proposition e.g. P1, e.g. (P1), e.g. P1 OR P2 AND P3, e.g. (P1 AND P2 OR P3) etc.

    @Test
    public void testValidateCompositionFormat() throws Exception {

    }

    // expects the first token to be proposition in format 'Px' e.g. P1

    @Test
    public void getPropositionID() throws Exception { // TODO our own exception here?

    }
}
