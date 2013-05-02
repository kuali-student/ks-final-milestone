package org.kuali.student.enrollment.class1.krms.dto;

import org.kuali.rice.core.api.util.tree.Tree;
import org.kuali.rice.krms.api.repository.agenda.AgendaDefinition;
import org.kuali.rice.krms.tree.node.CompareTreeNode;
import org.kuali.rice.krms.dto.AgendaEditor;
import java.util.List;

public class EnrolAgendaEditor extends AgendaEditor {

    public EnrolAgendaEditor(){
        super();
    }

    public EnrolAgendaEditor(AgendaDefinition definition){
        super(definition);
    }

}
