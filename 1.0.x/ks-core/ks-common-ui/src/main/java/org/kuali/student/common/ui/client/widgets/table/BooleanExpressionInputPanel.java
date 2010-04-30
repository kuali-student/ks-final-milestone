/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.common.ui.client.widgets.table;

import org.kuali.student.common.ui.client.widgets.KSModalDialogPanel;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class BooleanExpressionInputPanel extends VerticalPanel {
    Hyperlink editLink = new Hyperlink("Edit", "");
    BooleanExpressionEditorModel ruleEditorModel;
    private String oldInput = "";

    private Label expressionFromTableEditor = new Label();

    public BooleanExpressionInputPanel(BooleanExpressionEditorModel m) {
        this.add(expressionFromTableEditor);
        this.add(editLink);
        ruleEditorModel = m;
        editLink.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                final AlgebraEditDialog dialog = new AlgebraEditDialog();
                dialog.setAlgebra(oldInput);
                dialog.setModal(true);
               // dialog.setLocation(0,0);
                dialog.show();
            }
        });
    }
     public void addExpressionFromTableEditor(String s){
     expressionFromTableEditor.setText(s);
     oldInput = s;
     //textBox.setText(s);
    // parser.parse(s);
     //displayError();
     }
     class AlgebraEditDialog extends KSModalDialogPanel {
         ExpressionParser parser = new ExpressionParser();
         TextBox textBox = new TextBox();
         Button okButton = new Button("OK");
         Node root;
         private HTML errorMessage = new HTML();
         private int result = -1;
         public final static int OK = 1;
         public final static int Cancel = 2;

         public AlgebraEditDialog() {
             textBox.setPixelSize(600, 30);
             HorizontalPanel panel = new HorizontalPanel();
             VerticalPanel verticalPanel = new VerticalPanel();
             verticalPanel.add(errorMessage);
             verticalPanel.add(panel);
             Button cancelButton = new Button("Cancel");
             okButton.addClickHandler(new ClickHandler() {
                 @Override
                 public void onClick(ClickEvent arg0) {
                     result = OK;
                     hide();
                     Node root = getAlgebra();
                     ruleEditorModel.setNodeFromExpressionEditor(root);

                 }
             });
             cancelButton.addClickHandler(new ClickHandler() {

                 @Override
                 public void onClick(ClickEvent arg0) {
                     result = Cancel;
                     hide();

                 }
             });
             
             panel.add(textBox);
           //  panel.add(errorMessage);
             panel.add(okButton);
             panel.add(cancelButton);
             textBox.addKeyUpHandler(new KeyUpHandler() {
                 @Override
                 public void onKeyUp(KeyUpEvent event) {
                     String expression = textBox.getText();
                     root = parser.parse(expression);
                     if (parser.hasError() == false) {
                         okButton.setEnabled(true);
                     } else {
                         okButton.setEnabled(false);
                     }
                     displayError();
                 }
             });
             super.setWidget(verticalPanel);
         }

         private void displayError() {
             errorMessage.setText("");
             if (parser.hasError()) {
                 StringBuilder sb = new StringBuilder("Error Message: <BR>");
                 for (String error : parser.getErrorMessage()) {
                     sb.append(error + ",<BR>");
                 }
                 errorMessage.setHTML(sb.toString() + "<HR>");
             }
         }

         public int getResult() {
             return result;
         }

         public void setAlgebra(String algebra) {
             textBox.setText(algebra);
         }

         public Node getAlgebra() {
             if (textBox.getText() == null || textBox.getText().isEmpty()) {
                 return null;
             } else {
                 root = parser.parse(textBox.getText());
                 return root;
             }

         }
     }
}


// textBox.addKeyUpHandler(new KeyUpHandler() {
// @Override
// public void onKeyUp(KeyUpEvent event) {
// escape arrow key
// if (event.getNativeKeyCode() == 37 || event.getNativeKeyCode() == 38 || event.getNativeKeyCode() == 39 ||
// event.getNativeKeyCode() == 40) {
// return;
// }
// String expression = textBox.getText();
// if(expression.length() == 0){
// ruleEditorModel.setNodeFromExpressionEditor(null);
// }
// if(oldInput.trim().equals(expression.trim())){
// return;
// }else{
// oldInput = expression.trim();
// }
// Node root = parser.parse(expression);
// if (parser.hasError() == false) {
// ruleEditorModel.setNodeFromExpressionEditor(root);
// applyButton.setEnabled(true);
// }else{
// applyButton.setEnabled(false);
// displayError();
// }
// }
// });
