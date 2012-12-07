package org.kuali.student.enrollment.class1.krms.dto;

import org.kuali.rice.core.api.util.tree.Tree;
import org.kuali.rice.krms.impl.repository.AgendaItemBo;
import org.kuali.rice.krms.impl.repository.RuleBo;
import org.kuali.rice.krms.impl.ui.AgendaEditor;

/**
 * Created with IntelliJ IDEA.
 * User: SW
 * Date: 2012/12/04
 * Time: 4:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class StudentAgendaEditor extends AgendaEditor {

    /**
     * @return the agendaItemLine
     */
    public RuleBo getRule() {
        return this.getAgendaItemLine().getRule();
    }

    public String getPropositionSummary(){
        return this.getRule().getPropositionSummary();
    }

    public Tree getPropositionTree(){
        return this.getRule().getPropositionTree();
    }

}
