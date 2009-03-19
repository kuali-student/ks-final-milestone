package org.kuali.student.ui.kitchensink.client.kscommons.datepicker;

import static org.kuali.student.ui.kitchensink.client.KitchenSinkStyleConstants.STYLE_EXAMPLE;

import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSDatePicker;
import org.kuali.student.common.ui.client.widgets.KSLabel;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

public class DatePickerExample extends Composite {

    private final VerticalPanel main = new VerticalPanel();
    private final KSButton button = new KSButton("Submit");
    
    private final KSDatePicker datePicker = new KSDatePicker();
    private final KSLabel label = new KSLabel("Click in the box to open the date picker: ", false);
    
    private static final DateTimeFormat DF = DateTimeFormat.getMediumDateFormat();

    public DatePickerExample() {
        
        main.addStyleName(STYLE_EXAMPLE);
        
        button.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent arg0) {
                Window.alert("You selected " + DF.format(datePicker.getDate()));
                
            }} );
        
        main.add(label);
        main.add(datePicker);
        main.add(button);
        
        super.initWidget(main);
    }
}
