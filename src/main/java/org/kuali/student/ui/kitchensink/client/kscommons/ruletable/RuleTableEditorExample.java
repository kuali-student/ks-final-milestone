package org.kuali.student.ui.kitchensink.client.kscommons.ruletable;

import org.kuali.student.common.ui.client.widgets.table.BooleanExpressionEditor;
import org.kuali.student.common.ui.client.widgets.table.Node;
import org.kuali.student.common.ui.client.widgets.table.Token;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class RuleTableEditorExample extends Composite {
    final VerticalPanel main = new VerticalPanel();
    
    public RuleTableEditorExample(){
        main.add(preRuleEditor());
        super.initWidget(main);
    }

    private VerticalPanel preRuleEditor(){
        VerticalPanel panel = new VerticalPanel();
        final ListBox conditionListBox = new ListBox();
        conditionListBox.setPixelSize(300,300);
        conditionListBox.setMultipleSelect(true);
        conditionListBox.addItem("a");
        conditionListBox.addItem("b");
        conditionListBox.addItem("c");
        conditionListBox.addItem("d");
        conditionListBox.addItem("e");
        conditionListBox.addItem("f");
        conditionListBox.addItem("g");
        conditionListBox.addItem("h");
        conditionListBox.addItem("i");
        conditionListBox.addItem("j");
        conditionListBox.addItem("k");
        conditionListBox.addItem("l");
        
        final RadioButton andRadioButton = new RadioButton("condition","And");
        final RadioButton orRadioButton = new RadioButton("condition","Or");
        andRadioButton.setValue(true);
        Button okButton = new Button("OK");
        
        okButton.addClickHandler(new ClickHandler(){
            @Override
            public void onClick(ClickEvent event) {
                
                //RootPanel.get().clear();
                BooleanExpressionEditor ruleEditor = new BooleanExpressionEditor();
                //List<Node<Token>> list = new ArrayList<Node<Token>>();
                
                Node<Token> rootNode = new Node<Token>();
                if(andRadioButton.getValue()){
                    rootNode.setUserObject(Token.createAndToken());
                }else{
                    rootNode.setUserObject(Token.createOrToken());
                }
           
                
                for(int i=0;i<conditionListBox.getItemCount();i++){
                    String condition = conditionListBox.getItemText(i);
                    rootNode.addNode(createNode(condition));
                }
                ruleEditor.setNodeFromExpressionEditor(rootNode);

                FlexTable layout = new FlexTable();
                layout.setWidget(0,0,new HTML(""));
                layout.getCellFormatter().setWidth(0, 0, "100px");
                layout.setWidget(0,1,ruleEditor);
                layout.setWidget(1,0,new HTML(""));
                
                main.clear();
                main.add(layout);
            }
            
        });
        panel.add(conditionListBox);
        panel.add(andRadioButton);
        panel.add(orRadioButton);
        panel.add(okButton);
        
        return panel;
    }
    private Node<Token> createNode(String name) {
        Node<Token> aNode = new Node<Token>();
        Token aToken = new Token();
        aToken.type = Token.Condition;
        aToken.value = name;
        aNode.setUserObject(aToken);
        return aNode;
    }
}
