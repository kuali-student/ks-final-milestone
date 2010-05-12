/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.brms.devgui.client;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.kuali.student.brms.internal.common.entity.RuleElementType;
import org.kuali.student.brms.rulemanagement.dto.LeftHandSideInfo;
import org.kuali.student.brms.rulemanagement.dto.RightHandSideInfo;
import org.kuali.student.brms.rulemanagement.dto.RuleElementInfo;
import org.kuali.student.brms.rulemanagement.dto.RulePropositionInfo;
import org.kuali.student.brms.rulemanagement.dto.YieldValueFunctionInfo;

/**
 * @author zzraly
 */
public class GuiUtilTest {

    @Test
    public void testAssembleRuleFromComposition_singleProposition() {

        Map<Integer, RuleElementInfo> definedPropositions = createTestPropositions();

        assertEquals("A", GuiUtil.assembleRuleFromComposition("A", definedPropositions));
        assertEquals("INTERSECTION = 10", GuiUtil.assembleRuleFromComposition("P1", definedPropositions));
        assertEquals("INTERSECTION = 10", GuiUtil.assembleRuleFromComposition(" P1 ", definedPropositions));
        assertEquals("( INTERSECTION = 10 )", GuiUtil.assembleRuleFromComposition(" ( P1)", definedPropositions));
    }

    // @Test
    public void testCreateRuleElementsFromComposition_singleProposition() {

        Map<Integer, RuleElementInfo> definedPropositions = createTestPropositions();
        List<RuleElementInfo> elemList = new ArrayList<RuleElementInfo>();
        elemList.add(definedPropositions.get(new Integer(1)));

        assertEquals(elemList, GuiUtil.createRuleElementsFromComposition("P1", definedPropositions));
        assertEquals("INTERSECTION = 10", GuiUtil.createRuleElementsFromComposition(" P1 ", definedPropositions));
        assertEquals("( INTERSECTION = 10 )", GuiUtil.createRuleElementsFromComposition(" ( P1)", definedPropositions));
    }

    @Test
    public void testAssembleRuleFromComposition_twoPropositions() {

        Map<Integer, RuleElementInfo> definedPropositions = createTestPropositions();

        assertEquals("INTERSECTION = 10 AND INTERSECTION = 10", GuiUtil.assembleRuleFromComposition("P1 AND P2", definedPropositions));
        assertEquals("( INTERSECTION = 10 AND INTERSECTION = 10 )", GuiUtil.assembleRuleFromComposition("(P1 AND P2)", definedPropositions));
    }

    @Test
    public void testAssembleRuleFromComposition_threePropositions() {

        Map<Integer, RuleElementInfo> definedPropositions = createTestPropositions();

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

    private static Map<Integer, RuleElementInfo> createTestPropositions() {
        Map<Integer, RuleElementInfo> definedPropositions = new HashMap<Integer, RuleElementInfo>();

        RulePropositionInfo ruleProp = new RulePropositionInfo();
        LeftHandSideInfo leftHandSide = new LeftHandSideInfo();
        YieldValueFunctionInfo yvf = new YieldValueFunctionInfo();
        yvf.setYieldValueFunctionType(GuiUtil.YieldValueFunctionType.INTERSECTION.name());
        leftHandSide.setYieldValueFunction(yvf);
        RightHandSideInfo rightHandSide = new RightHandSideInfo();
        rightHandSide.setExpectedValue("10");
        ruleProp.setLeftHandSide(leftHandSide);
        ruleProp.setComparisonOperatorTypeKey(GuiUtil.ComparisonOperator.EQUAL_TO.name());
        ruleProp.setRightHandSide(rightHandSide);
        
        //create empty fule element
        RuleElementInfo newElem = new RuleElementInfo();
        newElem.setId("");
        newElem.setName("");
        newElem.setDesc("");
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
