package org.kuali.student.common.ui.client.widgets;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Label;
/**
 * 
 * @deprecated
 * */
public class KSMessageDialog{
    KSDialog dialog = new KSDialog();
    public KSMessageDialog(String title, String message){
        dialog.setTitle(title);
        Label messageLabel = new Label();
        messageLabel.setText(message);
        dialog.setContent(messageLabel);
        dialog.addButton(KSDialog.OkText, new ClickHandler (){
            @Override
            public void onClick(ClickEvent event) {
                dialog.hide();
            }
        });
    }
    public void show(){
        dialog.show();
    }
}