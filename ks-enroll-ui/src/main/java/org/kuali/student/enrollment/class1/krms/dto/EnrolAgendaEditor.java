package org.kuali.student.enrollment.class1.krms.dto;

import org.kuali.rice.core.api.util.tree.Tree;
import org.kuali.rice.krms.tree.node.CompareTreeNode;
import org.kuali.rice.krms.dto.AgendaEditor;
import java.util.List;

public class EnrolAgendaEditor extends AgendaEditor {

    private List<AgendaEditor> agendas;
    private transient Tree<CompareTreeNode, String> compareTree;

    public EnrolAgendaEditor(){
    }

    public List<AgendaEditor> getAgendas() {
        return agendas;
    }

    public void setAgendas(List<AgendaEditor> agendas) {
        this.agendas = agendas;
    }

    public Tree<CompareTreeNode, String> getCompareTree() {
        return compareTree;
    }

    public void setCompareTree(Tree<CompareTreeNode, String> compareTree) {
        this.compareTree = compareTree;
    }
}
