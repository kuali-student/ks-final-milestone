package org.kuali.student.enrollment.class1.krms.dto;

import org.apache.commons.lang.StringEscapeUtils;
import org.kuali.rice.krms.impl.repository.PropositionBo;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: SW
 * Date: 2013/02/12
 * Time: 2:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class LogicRuleViewer extends RulePreviewer {

    private Map<String, String> propositionAlpha;

    public void initPreviewTree(RuleEditor ruleEditor){
        propositionAlpha = ruleEditor.getPropositionAlpha();
        super.initPreviewTree(ruleEditor);
    }

    @Override
    protected String buildNodeLabel(PropositionEditor prop){
        //Build the node label.
        String prefix = "";
        if(propositionAlpha != null && propositionAlpha.containsKey(prop.getId())) {
            prefix = "<b>" + propositionAlpha.get(prop.getId()) + ".</b> ";
        }

        return prefix + StringEscapeUtils.escapeHtml(prop.getDescription());
    }
}
