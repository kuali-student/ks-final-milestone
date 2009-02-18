package org.kuali.student.ui.kitchensink.client.kscommons.datepicker;

import java.util.Date;

import org.kuali.student.common.ui.client.widgets.KSDatePicker;
import org.kuali.student.common.ui.client.widgets.KSLabel;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;

public class DatePickerExample extends Composite {

    final HorizontalPanel panel = new HorizontalPanel();
    final KSDatePicker ksDatePicker = new KSDatePicker();
    final KSLabel label = new KSLabel("  Click in the box to open the date picker");

    public DatePickerExample() {
        
// Can't do this as have no access to DatePicker in KSDate Picker
// Not sure how to get the value
//        ksDatePicker.addValueChangeHandler(new ValueChangeHandler<Date>(){
//
//            public void onValueChange(ValueChangeEvent<Date> event) {
//                
//              Window.alert("You picked " + ksDatePicker.getValue());
//            }   
//        });
        
        panel.add(ksDatePicker);
        panel.add(label);
        super.initWidget(panel);
    }
}
