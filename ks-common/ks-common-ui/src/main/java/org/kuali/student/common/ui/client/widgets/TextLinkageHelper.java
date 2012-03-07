package org.kuali.student.common.ui.client.widgets;

import com.google.gwt.event.dom.client.HasKeyUpHandlers;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.HasText;
/**
 * TextLinkageHelper is used to link two text fields. As you type in one field the text 
 * appears on the linked field.
 * 
 * */
@Deprecated
public class TextLinkageHelper {
    static String masterString;
    public static void link(final HasText master, final HasText slave) {
        masterString = master.getText();
        if (master instanceof HasKeyUpHandlers) {
              HasKeyUpHandlers masterTextBox = (HasKeyUpHandlers) master;
              masterTextBox.addKeyUpHandler(new KeyUpHandler() {
                @Override
                public void onKeyUp(KeyUpEvent event) {
                      if(masterString.equals(slave.getText()) 
                              ||"".equals(slave.getText()) ){
                          masterString = master.getText();
                          slave.setText(masterString);
                      }
                }
              });
          }
      } 
}
