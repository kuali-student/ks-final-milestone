package org.kuali.student.common.ui.client.widgets.table;

import java.util.List;

public interface NodeEditor {
    public boolean canUndo();

    public boolean canRedo();

    public boolean isAndOrOrable(List<Node> nodeList);
    
    public boolean isAddable(Node to, List<Node> nodeList);
    
    public boolean isRemovable(Node from ,List<Node> nodeList);
    
    
    public Node doAnd(List<Node> nodeList);

    public Node doOr(List<Node> nodeList);

    public Node doRemove(Node from ,List<Node> nodeList);

    public Node doAdd(Node to, List<Node> nodeList);

}