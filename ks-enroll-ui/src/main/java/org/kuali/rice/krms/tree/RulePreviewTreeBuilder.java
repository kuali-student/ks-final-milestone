package org.kuali.rice.krms.tree;

import org.apache.commons.lang.StringEscapeUtils;
import org.kuali.rice.krms.api.repository.proposition.PropositionDefinitionContract;
import org.kuali.rice.krms.api.repository.rule.RuleDefinitionContract;
import org.kuali.student.enrollment.class1.krms.dto.PropositionEditor;
import org.kuali.student.enrollment.class1.krms.dto.RuleEditor;

/**
 * Created with IntelliJ IDEA.
 * User: SW
 * Date: 2013/02/12
 * Time: 2:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class RulePreviewTreeBuilder extends RuleViewTreeBuilder {

    @Override
    protected String buildNodeLabel(RuleDefinitionContract rule, PropositionEditor prop, boolean refreshNl){
        //Build the node label.
        String prefix = this.getPropositionPrefix((RuleEditor)rule, prop);
        return prefix + StringEscapeUtils.escapeHtml(this.getDescription(prop, refreshNl));
    }
}
