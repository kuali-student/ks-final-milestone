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

    private static final String CORRECT_MSG = "Correct!";
    private static final String RETRY_MSG = "Keep Trying!";

    private final VerticalPanel main = new VerticalPanel();
    private final FlexTable table = new FlexTable();

    private final KSLabel instructions = new KSLabel("Type the characters from the box on the left to the corresponding box on the right to see dynamic feedback\n\n");

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

        //TODO: still needs some work to make the default state work properly, 
        //      i.e.  make the help link clickable

        help1.setStateDefault();
        help2.setStateDefault();
        help3.setStateDefault();

        source1.setText("red");
        source1.setEnabled(false);
        source1.setReadOnly(true);

        source2.setText("orange");
        source2.setEnabled(false);
        source2.setReadOnly(true);

        source3.setText("yellow");
        source3.setEnabled(false);
        source3.setReadOnly(true);

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

    private HelpInfo buildTestHelpInfo(){
        HelpInfo testInfo = new HelpInfo();
        testInfo.setId("123456");
        testInfo.setTitle("Copy Help");
        testInfo.setShortVersion("Click for help");
        testInfo.setUrl("http://www.kuali.org");
        return testInfo;
    }

}
