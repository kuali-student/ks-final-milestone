package org.kuali.student.ui.kitchensink.client.kscommons.ruletable;

import static org.kuali.student.ui.kitchensink.client.KitchenSinkStyleConstants.STYLE_EXAMPLE;

import org.kuali.student.common.ui.client.widgets.table.ExpressionParser;
import org.kuali.student.common.ui.client.widgets.table.Node;
import org.kuali.student.common.ui.client.widgets.table.TreeTable;

import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class RuleTableExample extends Composite {

    final VerticalPanel main = new VerticalPanel();
    TreeTable table = new TreeTable();
    TextBox input = new TextBox();
    HTML error = new HTML();

     ExpressionParser parser = new ExpressionParser();

    public RuleTableExample() {
        main.addStyleName(STYLE_EXAMPLE);
        input.setPixelSize(400, 40);
        main.add(new HTML("Boolean algebra, for example: a and b"));
        main.add(input);
        main.add(error);
        main.add(new HTML("<BR>"));
        input.addKeyUpHandler(new KeyUpHandler(){
            @Override
            public void onKeyUp(KeyUpEvent event) {
                //escape arrow key
                
                if(event.getNativeKeyCode() == 37 
                    ||event.getNativeKeyCode() == 38
                    ||event.getNativeKeyCode() == 39
                    ||event.getNativeKeyCode() == 40){
                    return;
                }
                doKey();
            }
        });

        super.initWidget(main);

    }
    private void doKey(){
        String expression = input.getText();
        Node root = parser.parse(expression);
        
        if(parser.hasError()){
            StringBuilder sb = new StringBuilder("Error Message: ");
            for(String error: parser.getErrorMessage()){
               sb.append(error+",");
               
            }
            error.setHTML(sb.toString());
            return;
        }
        error.setHTML("");
        main.remove(table);
        table = new TreeTable();
        table.setPixelSize(300,300);
        main.add(table);
        table.buildTable(root);
    }
}
