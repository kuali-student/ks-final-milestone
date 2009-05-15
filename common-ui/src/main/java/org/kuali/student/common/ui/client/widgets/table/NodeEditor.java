package org.kuali.student.common.ui.client.widgets.table;

import java.util.List;
public interface NodeEditor {

    public boolean isAndable(Node target, List<Node> nodeList);
    
    public boolean isOrable(Node target, List<Node> nodeList);
    
    public boolean isAddable();
    public boolean isOrable();
    public boolean isAddable(Node target, List<Node> nodeList);
    
    public boolean isRemovable(List<Node> nodeList);
    
    public boolean isTogglable(); 
    
    public void doAnd();

    public void doOr();

    public void doRemoveFromGroup();

    public void doAddToGroup();

    public void doToggle();

    public boolean canUndo();

    public boolean canRedo();
    
    public void doUndo();
    
    public void doRedo();

}