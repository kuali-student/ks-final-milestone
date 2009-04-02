package org.kuali.student.common.ui.client.widgets.table;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Widget;

public class RuleEditTable extends FlexTable {
    RuleEditTableModel tableModel = new RuleEditTableModel();
    ClickHandler nodeClickHandler;
    private List<Widget> widgetList = new ArrayList<Widget>();

    public RuleEditTable() {
      // super.setBorderWidth(1);
    }
    public void setNodeList(List<Node<Token>> list){
        tableModel.setNodeList(list);
        refresh();
    }
    public void setNodeFromExpressionEditor(Node<Token> node){
        tableModel.setNodeFromExpressionEditor(node);
        refresh();
    }
    public RuleEditTableModel getModel(){
        return tableModel;
    }
    public void setNodeClickHandler(ClickHandler cl){
        nodeClickHandler = cl;
    }
    public void refresh(){
        clear();
        int i=0;
        for(Widget w: tableModel.getWidgetList()){
            super.setWidget(i++, 0, w);
        }
        List<NodeWidget> list = tableModel.getAllNodeWidget();
        for(NodeWidget w: list){
          w.clearCheckBoxClickHandler();
          if(nodeClickHandler != null){
              w.addCheckBoxClickHandler(nodeClickHandler);
          }
        }
        String str = getModel().getExpressionString();
    }

    public void doRemoveFromGroup(){
        getModel().doRemoveFromGroup();
        refresh();
    }
    public void doAddToGroup(){
        getModel().doAddToGroup();
        refresh();
    }
    public void doAnd(){
        getModel().doAnd();
        refresh();
    }
    public void doToggle(){
        getModel().doToggle();
        refresh();
    }
    public void doOr(){
        getModel().doOr();
        refresh();
    }
    public void doReDo(){
        getModel().doRedo();
        refresh();
    }
    public void doUnDo(){
        getModel().doUndo();
        refresh();
    }
}
