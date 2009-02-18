package org.kuali.student.ui.kitchensink.client.kscommons.help;

import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSHelp;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.SimplePanel;

public class HelpExample extends Composite {

    final SimplePanel panel = new SimplePanel();
    FlexTable table = new FlexTable();
    final KSHelp ksHelp = new KSHelp("help-id12345");


    public HelpExample() {
       
       
        panel.add(ksHelp);

        super.initWidget(panel);
    }
    

}
