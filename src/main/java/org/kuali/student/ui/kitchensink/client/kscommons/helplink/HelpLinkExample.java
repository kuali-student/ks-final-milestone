package org.kuali.student.ui.kitchensink.client.kscommons.helplink;

import static org.kuali.student.ui.kitchensink.client.KitchenSinkStyleConstants.STYLE_EXAMPLE;

import org.kuali.student.common.ui.client.dto.HelpInfo;
import org.kuali.student.common.ui.client.widgets.KSHelpLink;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSTextBox;

import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.VerticalPanel;

public class HelpLinkExample extends Composite {

    private static final String YELLOW = "yellow";
    private static final String ORANGE = "orange";
    private static final String RED = "red";
    private static final String CORRECT_MSG = "Correct!";
    private static final String RETRY_MSG = "Keep Trying!";

    private final VerticalPanel main = new VerticalPanel();
    private final FlexTable table = new FlexTable();

    private final KSLabel instructions = new KSLabel("Type the characters from the boxes on the left to the corresponding box on the right to see dynamic feedback\n\n");

    private final KSTextBox source1 = new KSTextBox();
    private final KSTextBox source2 = new KSTextBox();
    private final KSTextBox source3 = new KSTextBox();

    private final KSTextBox target1 = new KSTextBox();
    private final KSTextBox target2 = new KSTextBox();
    private final KSTextBox target3 = new KSTextBox();

    private final KSHelpLink help1 =  new KSHelpLink();
    private final KSHelpLink help2 =  new KSHelpLink();
    private final KSHelpLink help3 =  new KSHelpLink();

    public HelpLinkExample() {

        main.addStyleName(STYLE_EXAMPLE);

        doLayout();
        
        //TODO: Use a single handler instead of creating 3 indiv handlers

        target1.addKeyUpHandler(new KeyUpHandler() {

            @Override
            public void onKeyUp(KeyUpEvent arg0) {
                if (source1.getText().equals(target1.getText().trim())) {
                    help1.setStateOK(CORRECT_MSG);                    
                }
                else {
                    help1.setStateError(RETRY_MSG);
                }                
            }});

        target2.addKeyUpHandler(new KeyUpHandler() {

            @Override
            public void onKeyUp(KeyUpEvent arg0) {
                if (source2.getText().equals(target2.getText().trim())) {
                    help2.setStateOK(CORRECT_MSG);                    
                }
                else {
                    help2.setStateError(RETRY_MSG);
                }

            }});

        target3.addKeyUpHandler(new KeyUpHandler() {

            @Override
            public void onKeyUp(KeyUpEvent arg0) {
                if (source3.getText().equals(target3.getText().trim())) {
                    help3.setStateOK(CORRECT_MSG);                    
                }
                else {
                    help3.setStateError(RETRY_MSG);
                }

            }});

        main.add(table);

        super.initWidget(main);
    }

    private void doLayout() {

        help1.setStateDefault();
        help1.setHelpInfo(buildRedHelp());
        help2.setStateDefault();
        help2.setHelpInfo(buildOrangeHelp());
        help3.setStateDefault();
        help3.setHelpInfo(buildYellowHelp());

        source1.setText(RED);
        source1.setEnabled(false);
        source1.setReadOnly(true);
        source1.setName(RED);

        source2.setText(ORANGE);
        source2.setEnabled(false);
        source2.setReadOnly(true);
        source2.setName(ORANGE);

        source3.setText(YELLOW);
        source3.setEnabled(false);
        source3.setReadOnly(true);
        source2.setName(YELLOW);

        int row = 0;        
        table.setWidget(row, 0, instructions);
        table.getFlexCellFormatter().setColSpan(row, 0, 3);

        row++;
        table.setWidget(row, 0, source1);
        table.setWidget(row, 1, target1);
        table.setWidget(row, 2, help1);

        row++;
        table.setWidget(row, 0, source2);
        table.setWidget(row, 1, target2);
        table.setWidget(row, 2, help2);

        row++;
        table.setWidget(row, 0, source3);
        table.setWidget(row, 1, target3);
        table.setWidget(row, 2, help3);
    }


    private HelpInfo buildRedHelp(){
        HelpInfo testInfo = new HelpInfo();
        testInfo.setId("123456");
        testInfo.setTitle("Help for Red");
        testInfo.setShortVersion("Type each character from the field on the left to the field on the right");
        testInfo.setUrl("http://en.wikipedia.org/wiki/Red");
        return testInfo;
    }
    
    private HelpInfo buildOrangeHelp(){
        HelpInfo testInfo = new HelpInfo();
        testInfo.setId("123456");
        testInfo.setTitle("Help for Orange");
        testInfo.setShortVersion("Type each character from the field on the left to the field on the right");
        testInfo.setUrl("http://en.wikipedia.org/wiki/Orange_(colour)");
        return testInfo;
    }

    private HelpInfo buildYellowHelp(){
        HelpInfo testInfo = new HelpInfo();
        testInfo.setId("123456");
        testInfo.setTitle("Help for Yellow");
        testInfo.setShortVersion("Type each character from the field on the left to the field on the right");
        testInfo.setUrl("http://en.wikipedia.org/wiki/Yellow");
        return testInfo;
    }
}
