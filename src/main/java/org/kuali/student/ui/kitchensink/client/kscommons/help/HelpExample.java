package org.kuali.student.ui.kitchensink.client.kscommons.help;

import org.kuali.student.common.ui.client.widgets.KSHelp;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.SimplePanel;

public class HelpExample extends Composite {

    final SimplePanel main = new SimplePanel();
    final FlexTable table = new FlexTable();

    final KSHelp help =  GWT.create(KSHelp.class);


    public HelpExample() {

        help.setHelpId("1234567qwerty");
        main.add(help);

        super.initWidget(main);
    }
}
