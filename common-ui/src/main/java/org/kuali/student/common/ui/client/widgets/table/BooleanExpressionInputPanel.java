package org.kuali.student.common.ui.client.widgets.table;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
public class BooleanExpressionInputPanel extends VerticalPanel{

    TextBox textBox = new TextBox();
    ExpressionParser parser = new ExpressionParser();
    RuleEditorModel ruleEditorModel;
    private String oldInput = "";
    
    private HTML errorMessage = new HTML();
    private Label expressionFromTableEditor = new Label();
    
    public BooleanExpressionInputPanel(RuleEditorModel m){
        textBox.setPixelSize(600, 30);
        
        this.add(textBox);
        this.add(errorMessage);
        this.add(expressionFromTableEditor);
        
        ruleEditorModel = m;
        textBox.addKeyUpHandler(new KeyUpHandler() {
            @Override
            public void onKeyUp(KeyUpEvent event) {
                // escape arrow key
                if (event.getNativeKeyCode() == 37 || event.getNativeKeyCode() == 38 || event.getNativeKeyCode() == 39 || event.getNativeKeyCode() == 40) {
                    return;
                }
                String expression = textBox.getText();
                if(expression.length() == 0){
                    ruleEditorModel.setNodeFromExpressionEditor(null);
                }
                if(oldInput.trim().equals(expression.trim())){
                    return;
                }else{
                    oldInput = expression.trim();
                }
                Node root = parser.parse(expression);
                if (parser.hasError() == false) {
                    ruleEditorModel.setNodeFromExpressionEditor(root);
                }else{
                    displayError();
                }
            }
        });
    }
    public void addExpressionFromTableEditor(String s){
        expressionFromTableEditor.setText(s);
        oldInput = s;
       // textBox.setText(s);
        parser.parse(s);
        displayError();
      
    }
    private void displayError(){
        errorMessage.setText("");
        if(parser.hasError()){
            StringBuilder sb = new StringBuilder("Error Message: <BR>");
            for (String error : parser.getErrorMessage()) {
                sb.append(error + ",<BR>");
            }
            errorMessage.setHTML(sb.toString()+"<HR>");
        }
    }
}
