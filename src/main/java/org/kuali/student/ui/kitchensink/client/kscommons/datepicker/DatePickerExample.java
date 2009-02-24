package org.kuali.student.ui.kitchensink.client.kscommons.datepicker;

import static org.kuali.student.ui.kitchensink.client.KitchenSinkStyleConstants.STYLE_EXAMPLE;

import org.kuali.student.common.ui.client.widgets.KSDatePicker;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.ui.kitchensink.client.kscommons.KSWidgetFactory;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

public class DatePickerExample extends Composite {

    final VerticalPanel main = new VerticalPanel();
    
    final KSDatePicker datePicker = KSWidgetFactory.getDatePickerInstance();
    final KSLabel label = new KSLabel("Click in the box to open the date picker: ");

    public DatePickerExample() {
        
        main.addStyleName(STYLE_EXAMPLE);

        
// Can't do this as have no access to DatePicker in KSDate Picker
//        ksDatePicker.addValueChangeHandler(new ValueChangeHandler<Date>(){
//
//            public void onValueChange(ValueChangeEvent<Date> event) {
//                
//              Window.alert("You picked " + ksDatePicker.getValue());
//            }   
//        });
               
        main.add(label);
        main.add(datePicker);

        super.initWidget(main);
    }
}
