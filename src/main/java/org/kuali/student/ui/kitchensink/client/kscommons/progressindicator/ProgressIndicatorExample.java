package org.kuali.student.ui.kitchensink.client.kscommons.progressindicator;

import static org.kuali.student.ui.kitchensink.client.KitchenSinkStyleConstants.STYLE_EXAMPLE;
import static org.kuali.student.ui.kitchensink.client.KitchenSinkStyleConstants.STYLE_PROGRESS_INDICATOR_TEXTBOX;
import static org.kuali.student.ui.kitchensink.client.KitchenSinkStyleConstants.STYLE_PROGRESS_INDICATOR_TWIDDLER;

import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSProgressIndicator;
import org.kuali.student.common.ui.client.widgets.KSTextBox;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ProgressIndicatorExample extends Composite {

    final VerticalPanel main = new VerticalPanel();

    private final HorizontalPanel calculationPanel = new HorizontalPanel();
    private final HorizontalPanel buttonPanel = new HorizontalPanel();

    final KSLabel label = new KSLabel("Enter a number in each box and click the Calculate button to see the indicator.");
    private final KSProgressIndicator twiddler = new KSProgressIndicator();

    private final KSTextBox number1 = new KSTextBox();
    private final KSTextBox number2 = new KSTextBox();

    private final KSButton button = new KSButton("Calculate");
    private final KSButton resetButton = new KSButton("Reset");

    private final KSTextBox resultBox = new KSTextBox();

    private final static int INTERVAL = 3000;


    public ProgressIndicatorExample() {

        main.addStyleName(STYLE_EXAMPLE);
        number1.addStyleName(STYLE_PROGRESS_INDICATOR_TEXTBOX);
        number2.addStyleName(STYLE_PROGRESS_INDICATOR_TEXTBOX);
        resultBox.addStyleName(STYLE_PROGRESS_INDICATOR_TEXTBOX);
        twiddler.addStyleName(STYLE_PROGRESS_INDICATOR_TWIDDLER);
        

        calculationPanel.add(number1);
        calculationPanel.add(number2);

        buttonPanel.add(button);
        buttonPanel.add(resetButton);
        buttonPanel.add(twiddler);

        main.add(label);
        main.add(calculationPanel);
        main.add(buttonPanel);
        main.add(resultBox);

        resultBox.setEnabled(false);
        resultBox.setReadOnly(true);
        twiddler.hide();

        button.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent arg0) {

                final int num1;
                final int num2;
                try {
                    twiddler.setText("Please wait......");
                    twiddler.show();
                    
                    num1 = Integer.parseInt(number1.getValue().trim());
                    num2 = Integer.parseInt(number2.getValue().trim());
                    
                    Timer timer1 = new Timer() {
                        public void run() {
                            twiddler.setText("Almost done.....!");
                        }
                    };
                    timer1.schedule(INTERVAL);

                    Timer timer2 = new Timer() {
                        public void run() {
                            resultBox.setValue(String.valueOf(num1+num2));
                            twiddler.hide();
                        }
                    };
                    timer2.schedule(INTERVAL+3000);          
                } 
                catch (NumberFormatException e) {
                    
                    Window.alert("Enter an integer in both boxes");
                    twiddler.hide();

                }
            }});

        resetButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent arg0) {
                number1.setText("");
                number2.setText("");
                resultBox.setText("");
               

            }});

        super.initWidget(main);
    }


}
