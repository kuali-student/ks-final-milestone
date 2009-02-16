package org.kuali.student.rules.devgui.client;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.kuali.student.rules.internal.common.entity.RuleElementType;
import org.kuali.student.rules.rulemanagement.dto.LeftHandSideDTO;
import org.kuali.student.rules.rulemanagement.dto.RightHandSideDTO;
import org.kuali.student.rules.rulemanagement.dto.RuleElementDTO;
import org.kuali.student.rules.rulemanagement.dto.RulePropositionDTO;
import org.kuali.student.rules.rulemanagement.dto.YieldValueFunctionDTO;

/**
 * @author zzraly
 */
public class GuiUtilTest {

    @Test
    public void testAssembleRuleFromComposition_singleProposition() {

        Map<Integer, RuleElementDTO> definedPropositions = createTestPropositions();

        assertEquals("A", GuiUtil.assembleRuleFromComposition("A", definedPropositions));
        assertEquals("INTERSECTION = 10", GuiUtil.assembleRuleFromComposition("P1", definedPropositions));
        assertEquals("INTERSECTION = 10", GuiUtil.assembleRuleFromComposition(" P1 ", definedPropositions));
        assertEquals("( INTERSECTION = 10 )", GuiUtil.assembleRuleFromComposition(" ( P1)", definedPropositions));
    }

    // @Test
    public void testCreateRuleElementsFromComposition_singleProposition() {

        Map<Integer, RuleElementDTO> definedPropositions = createTestPropositions();
        List<RuleElementDTO> elemList = new ArrayList<RuleElementDTO>();
        elemList.add(definedPropositions.get(new Integer(1)));

        assertEquals(elemList, GuiUtil.createRuleElementsFromComposition("P1", definedPropositions));
        assertEquals("INTERSECTION = 10", GuiUtil.createRuleElementsFromComposition(" P1 ", definedPropositions));
        assertEquals("( INTERSECTION = 10 )", GuiUtil.createRuleElementsFromComposition(" ( P1)", definedPropositions));
    }

    @Test
    public void testAssembleRuleFromComposition_twoPropositions() {

        Map<Integer, RuleElementDTO> definedPropositions = createTestPropositions();

        assertEquals("INTERSECTION = 10 AND INTERSECTION = 10", GuiUtil.assembleRuleFromComposition("P1 AND P2", definedPropositions));
        assertEquals("( INTERSECTION = 10 AND INTERSECTION = 10 )", GuiUtil.assembleRuleFromComposition("(P1 AND P2)", definedPropositions));
    }

    @Test
    public void testAssembleRuleFromComposition_threePropositions() {

        Map<Integer, RuleElementDTO> definedPropositions = createTestPropositions();

        assertEquals("( INTERSECTION = 10 AND INTERSECTION = 10 ) OR INTERSECTION = 10", GuiUtil.assembleRuleFromComposition("(P1 AND P2) OR P3", definedPropositions));
        assertEquals("( INTERSECTION = 10 AND INTERSECTION = 10 ) OR INTERSECTION = 10", GuiUtil.assembleRuleFromComposition("(P1 AND P2) OR P3", definedPropositions));
        assertEquals("( INTERSECTION = 10 AND INTERSECTION = 10 ) OR ( INTERSECTION = 10 AND INTERSECTION = 10 )", GuiUtil.assembleRuleFromComposition("(P1 AND P2) OR ( P3 AND P4 ) ", definedPropositions));
        assertEquals("( ( INTERSECTION = 10 OR INTERSECTION = 10 ) AND INTERSECTION = 10 )", GuiUtil.assembleRuleFromComposition("( ( P1 OR P2) AND P3)", definedPropositions));
    }

    @Test
    public void testValidateCompositionFormat_singleProposition() {

        Set<Integer> definedPropositions1 = new HashSet<Integer>();
        definedPropositions1.add(1);
        Set<Integer> definedPropositions5 = new HashSet<Integer>();
        definedPropositions5.add(1);
        definedPropositions5.add(2);
        definedPropositions5.add(3);
        definedPropositions5.add(4);
        definedPropositions5.add(5);

        assertEquals("Invalid Proposition format. Expected 'P' but found: 'A'", GuiUtil.validateRuleComposition("A", definedPropositions5));
        assertEquals(GuiUtil.COMPOSITION_IS_VALID_MESSAGE, GuiUtil.validateRuleComposition(" P1 ", definedPropositions1));
        assertEquals("Warning: Unused Propositions - P2 P3 P4 P5 ", GuiUtil.validateRuleComposition("P1", definedPropositions5));
        assertEquals("Expected 'AND' or 'OR' but found 'P2...'", GuiUtil.validateRuleComposition(" P1 P2", definedPropositions5));
        assertEquals(GuiUtil.COMPOSITION_IS_VALID_MESSAGE, GuiUtil.validateRuleComposition(" ( P1)", definedPropositions1));
    }

    @Test
    public void testValidateCompositionFormat_twoPropositions() {

        Set<Integer> definedPropositions2 = new HashSet<Integer>();
        definedPropositions2.add(1);
        definedPropositions2.add(2);
        Set<Integer> definedPropositions5 = new HashSet<Integer>();
        definedPropositions5.add(1);
        definedPropositions5.add(2);
        definedPropositions5.add(3);
        definedPropositions5.add(4);
        definedPropositions5.add(5);

        assertEquals(GuiUtil.COMPOSITION_IS_VALID_MESSAGE, GuiUtil.validateRuleComposition("P1 AND P2", definedPropositions2));
        assertEquals("Expected 'Px' or '(Px' but found nothing.", GuiUtil.validateRuleComposition("(P1 AND P2 ) OR", definedPropositions2));
        assertEquals("Expected 'AND' or 'OR' but found '( AND...'", GuiUtil.validateRuleComposition("P1 ( AND P2) ", definedPropositions2));
        assertEquals(GuiUtil.COMPOSITION_IS_VALID_MESSAGE, GuiUtil.validateRuleComposition("(P1 AND P2)", definedPropositions2));
        assertEquals("Expected 'AND' or 'OR' but found '(...'", GuiUtil.validateRuleComposition("(P1 AND P2(", definedPropositions2));
        assertEquals("Expected 'Px' or '(Px' but found ')' in ')P1 A...'", GuiUtil.validateRuleComposition(")P1 AND P2)", definedPropositions2));
    }

    @Test
    public void testValidateCompositionFormat_threePropositions() {

        Set<Integer> definedPropositions3 = new HashSet<Integer>();
        definedPropositions3.add(1);
        definedPropositions3.add(2);
        definedPropositions3.add(3);
        Set<Integer> definedPropositions4 = new HashSet<Integer>();
        definedPropositions4.add(1);
        definedPropositions4.add(2);
        definedPropositions4.add(3);
        definedPropositions4.add(4);

        assertEquals(GuiUtil.COMPOSITION_IS_VALID_MESSAGE, GuiUtil.validateRuleComposition("(P1 AND P2) OR P3", definedPropositions3));
        assertEquals(GuiUtil.COMPOSITION_IS_VALID_MESSAGE, GuiUtil.validateRuleComposition("(P1 AND P2) OR P3", definedPropositions3));
        assertEquals(GuiUtil.COMPOSITION_IS_VALID_MESSAGE, GuiUtil.validateRuleComposition(" P1 OR (P2 AND P3 )", definedPropositions3));
        assertEquals(GuiUtil.COMPOSITION_IS_VALID_MESSAGE, GuiUtil.validateRuleComposition("(P1 AND P2) OR ( P3 AND P4 ) ", definedPropositions4));
        assertEquals(GuiUtil.COMPOSITION_IS_VALID_MESSAGE, GuiUtil.validateRuleComposition("( ( P1 OR P2) AND P3)", definedPropositions3));
        assertEquals("Expected 'AND' or 'OR' but found '( OR ...'", GuiUtil.validateRuleComposition("(P1 AND P2 ( OR P3", definedPropositions3));
        assertEquals("Expected 'Px' or '(Px' but found ')' in ')P1 A...'", GuiUtil.validateRuleComposition(")P1 AND P2 ( OR P3", definedPropositions3));
        assertEquals("Found ambiguous proposition composition with multiple logical operators not grouped by brackets. Please add brackets: 'AND P...'", GuiUtil.validateRuleComposition(" P1 OR P2 AND P3 ", definedPropositions3));
        assertEquals("Found ambiguous proposition composition with multiple logical operators not grouped by brackets. Please add brackets: 'AND P...'", GuiUtil.validateRuleComposition(" ( P1 OR P2 AND P3 )", definedPropositions3));
        assertEquals("Expected 'AND' or 'OR' but found 'P3...'", GuiUtil.validateRuleComposition(" P1 OR P2 P3", definedPropositions3));
        assertEquals("Expected 'Px' but found 'OR' in 'OR P2...'", GuiUtil.validateRuleComposition(" OR P2 AND P3", definedPropositions3));
    }

    private static Map<Integer, RuleElementDTO> createTestPropositions() {
        Map<Integer, RuleElementDTO> definedPropositions = new HashMap<Integer, RuleElementDTO>();

        RulePropositionDTO ruleProp = new RulePropositionDTO();
        LeftHandSideDTO leftHandSide = new LeftHandSideDTO();
        YieldValueFunctionDTO yvf = new YieldValueFunctionDTO();
        yvf.setYieldValueFunctionType(GuiUtil.YieldValueFunctionType.INTERSECTION.name());
        leftHandSide.setYieldValueFunction(yvf);
        RightHandSideDTO rightHandSide = new RightHandSideDTO();
        rightHandSide.setExpectedValue("10");
        ruleProp.setLeftHandSide(leftHandSide);
        ruleProp.setComparisonOperatorTypeKey(GuiUtil.ComparisonOperator.EQUAL_TO.name());
        ruleProp.setRightHandSide(rightHandSide);
        
        //create empty fule element
        RuleElementDTO newElem = new RuleElementDTO();
        newElem.setId("");
        newElem.setName("");
        newElem.setDescription("");
        newElem.setOrdinalPosition(1);
        newElem.setBusinessRuleElemnetTypeKey(RuleElementType.PROPOSITION.toString()); 
        newElem.setBusinessRuleProposition(ruleProp);

        // populate test data
        definedPropositions.put(1, newElem);
        definedPropositions.put(2, newElem);
        definedPropositions.put(3, newElem);
        definedPropositions.put(4, newElem);
        definedPropositions.put(5, newElem);

        return definedPropositions;
    }

}
