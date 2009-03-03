package org.kuali.student.ui.kitchensink.client.kscommons.datepicker;

import org.kuali.student.ui.kitchensink.client.KitchenSinkExample;

import com.google.gwt.user.client.ui.Widget;

public class DatePickerExampleDescriptor extends KitchenSinkExample {
    public DatePickerExampleDescriptor() {
        super();
        super.addResource("java", "DatePickerExample.java", "kscommons/datepicker/DatePickerExample.java", "Example usage of KSDatePicker.");
        super.addResource("css", "DatePickerExample.css", "examplecss/DatePickerExample.css", "Example styling of KSDatePicker.");
    }
    public String getDescription() {       
        return "DatePicker is used to facilitate the selection of a date for entry into a text field.";
    }

    public Widget getExampleWidget() {
        return new DatePickerExample();
    }

    public String getTitle() {
        return "Date Picker";
    }

}
