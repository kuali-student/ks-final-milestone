package org.kuali.student.enrollment.class1.krms.util;

/**
 * Created with IntelliJ IDEA.
 * User: SW
 * Date: 2013/02/26
 * Time: 3:30 PM
 * To change this template use File | Settings | File Templates.
 */

import org.junit.Assert;
import org.junit.Test;
import org.kuali.rice.krms.api.repository.LogicalOperator;
import org.kuali.rice.krms.api.repository.proposition.PropositionType;
import org.kuali.student.enrollment.class1.krms.dto.PropositionEditor;
import org.kuali.student.enrollment.class1.krms.dto.RuleEditor;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Test that the authorization decorator works against CourseOfferingService
 *
 * @author Kuali Student Team (ks.collab@kuali.org)
 */
public class TestRuleLogicExpressionParser {

    @Test
    public void testParseExpressionToRule(){
        RuleLogicExpressionParser parser = new RuleLogicExpressionParser();

        RuleEditor rule = this.getRuleEditor();
        String origExp = PropositionTreeUtil.configureLogicExpression((PropositionEditor) rule.getProposition());
        assertEquals(origExp, "A(B(C OR D) AND E)");

        //Build the expression:
        parser.setExpression("A(C OR B(E AND D))");
        PropositionEditor propositionEditor = parser.parseExpressionIntoRule(rule);

        String returnExp = PropositionTreeUtil.configureLogicExpression(propositionEditor);
        assertEquals(returnExp, "A(C OR B(E AND D))");
    }

    private RuleEditor getRuleEditor(){
        //Build the root proposition
        PropositionEditor rootProp = new PropositionEditor();
        rootProp.setKey("A");
        rootProp.setDescription("Must have all of the following:");
        rootProp.setCompoundOpCode(LogicalOperator.AND.getCode());
        rootProp.setPropositionTypeCode(PropositionType.COMPOUND.getCode());

        //Build the "B" proposition
        PropositionEditor beeProp = new PropositionEditor();
        beeProp.setKey("B");
        beeProp.setDescription("Must have 1 of the following");
        beeProp.setCompoundOpCode(LogicalOperator.OR.getCode());
        beeProp.setPropositionTypeCode(PropositionType.COMPOUND.getCode());

        //Build the "C" proposition
        PropositionEditor ceeProp = new PropositionEditor();
        ceeProp.setKey("C");
        ceeProp.setDescription("Must have a big grade");
        ceeProp.setPropositionTypeCode(PropositionType.SIMPLE.getCode());

        //Build the "D" proposition
        PropositionEditor deeProp = new PropositionEditor();
        deeProp.setKey("D");
        deeProp.setDescription("Must have permission");
        deeProp.setPropositionTypeCode(PropositionType.SIMPLE.getCode());

        //Add cee and dee to bee
        List<PropositionEditor> editors = new ArrayList<PropositionEditor>();
        editors.add(ceeProp);
        editors.add(deeProp);
        beeProp.setCompoundEditors(editors);

        //Build the "B" proposition
        PropositionEditor eehProp = new PropositionEditor();
        eehProp.setKey("E");
        eehProp.setDescription("Must have MATH100");
        eehProp.setPropositionTypeCode(PropositionType.SIMPLE.getCode());

        //Build the RuleEditor
        RuleEditor rule = new RuleEditor();
        editors = new ArrayList<PropositionEditor>();
        editors.add(beeProp);
        editors.add(eehProp);
        rootProp.setCompoundEditors(editors);
        rule.setProposition(rootProp);

        return rule;
    }
}
