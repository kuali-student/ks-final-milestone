package org.kuali.student.common.ui.client.widgets.table;


import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;

public class NodeWidget extends Composite{
    private Node node;
    HTML html = new HTML();
    public NodeWidget(Node n){
        node = n;
        html.setHTML(node.getUserObject().toString());
        super.initWidget(html);
    }
    public Node getNode(){
        return node;
    }
    public void setNode(Node n){
        node = n;
        html.setHTML(node.getUserObject().toString());
    }
}