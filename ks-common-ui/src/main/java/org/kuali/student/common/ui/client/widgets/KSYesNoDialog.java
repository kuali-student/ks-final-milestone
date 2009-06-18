package org.kuali.student.common.ui.client.widgets;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Label;
/**
 * 
 * @deprecated
 * */
public class KSYesNoDialog{
    KSDialog dialog = new KSDialog();

    public int result = -1;
    
    public KSYesNoDialog(String title, String message){
        dialog.setTitle(title);
        Label messageLabel = new Label();
        messageLabel.setText(message);
        dialog.setContent(messageLabel);
        dialog.addButton(KSDialog.YesText, new ClickHandler (){
            @Override
            public void onClick(ClickEvent event) {
                result = KSDialog.Yes;
                dialog.hide();
            }
        });
        dialog.addButton(KSDialog.NoText, new ClickHandler (){
            @Override
            public void onClick(ClickEvent event) {
                result = KSDialog.No;
                dialog.hide();
            }
        });
    }
    public int getResult(){
        return result;
    }
    public void show(){
        dialog.show();
    }
}