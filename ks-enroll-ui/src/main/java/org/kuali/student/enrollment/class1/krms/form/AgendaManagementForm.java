package org.kuali.student.enrollment.class1.krms.form;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.util.tree.Node;
import org.kuali.rice.core.api.util.tree.Tree;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.rice.krms.tree.node.CompareTreeNode;
import org.kuali.rice.krms.tree.node.TreeNode;
import org.kuali.rice.krms.dto.AgendaEditor;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingEditWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingListSectionWrapper;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.r2.core.acal.dto.TermInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AgendaManagementForm extends UifFormBase implements Serializable {

    private List<AgendaEditor> agendas;
    private transient Tree<CompareTreeNode, String> compareTree;

    public AgendaManagementForm(){
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
