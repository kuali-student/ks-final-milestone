package org.kuali.student.common.ui.client.widgets.table;

import java.util.List;

public interface NodeEditor {


    public boolean isAndOrOrable(Node target, List<Node> nodeList);
    
    public boolean isAddable(Node target, List<Node> nodeList);
    
    public boolean isRemovable(List<Node> nodeList);
    
    public void doAnd(Node target,List<Node> nodeList);

    public void doOr(Node target,List<Node> nodeList);

    public void doRemove(Node target,List<Node> nodeList);

    public void doAdd(Node target, List<Node> nodeList);
    
    public boolean canUndo();

    public boolean canRedo();
    
    public void doUndo();
    
    public void doRedo();

}