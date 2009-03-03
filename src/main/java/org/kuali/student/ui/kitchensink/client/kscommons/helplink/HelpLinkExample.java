package org.kuali.student.ui.kitchensink.client.kscommons.helplink;

import static org.kuali.student.ui.kitchensink.client.KitchenSinkStyleConstants.STYLE_EXAMPLE;

import org.kuali.student.common.ui.client.dto.HelpInfo;
import org.kuali.student.common.ui.client.widgets.KSHelpLink;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.SimplePanel;

public class HelpLinkExample extends Composite {

    final SimplePanel main = new SimplePanel();
    final FlexTable table = new FlexTable();
    private HelpInfo helpInfo;

    final KSHelpLink helpLink =  new KSHelpLink();


    public HelpLinkExample() {

        main.addStyleName(STYLE_EXAMPLE);
//
//        helpInfo = buildTestHelpInfo();
//        helpLink.setHelpInfo(helpInfo);
//        Window.alert("Title" +   helpLink.getHelpInfo().getTitle()
//        + "\n" + helpLink.getHelpInfo().getUrl());     
                
        main.add(helpLink);

        super.initWidget(main);
    }

    private HelpInfo buildTestHelpInfo(){
        HelpInfo testInfo = new HelpInfo();
        testInfo.setId("123456");
        testInfo.setTitle("Help Title");
        testInfo.setShortVersion("HELP TEXT HELP TEXT HELP TEXT HELP TEXT");
        testInfo.setUrl("http://www.kuali.org");
        return testInfo;
    }

}
