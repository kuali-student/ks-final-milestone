package org.kuali.student.common.ui.client.widgets;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;

public class KSBlockingDialog {
   private static KSDialog dialog = new KSDialog();
   private static Label messageLabel = new Label();
   static{
       FlowPanel mainPanel = new FlowPanel();

       messageLabel.setText("Loading...");
       Image twiddler = new Image("images/common/twiddler3.gif");
      
       mainPanel.setStyleName("KSBlockingDialog-mainPanel");
       messageLabel.setStyleName("KSBlockingDialog-message");
       twiddler.setStyleName("KSBlockingDialog-icon");
       
       mainPanel.add(twiddler);
       mainPanel.add(messageLabel);
       
       dialog.setCloseLinkVisible(false);
       dialog.setWidget(mainPanel);
       dialog.setMaxHeight(60);
       dialog.setMaxWidth(200);
   }
   public static void show(String message){
       messageLabel.setText(message);
       
       if(dialog.isShowing()){
           return;
       }
       dialog.center();
   }
   public static void hide(){
       dialog.hide();
   }
}
