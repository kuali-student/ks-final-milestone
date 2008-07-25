package org.kuali.student.commons.ui.widgets;

import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ChangeListenerCollection;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.widgetideas.datepicker.client.impl.DatePickerCell;

/**
 * This class extends the DatePicker from the incubator in order to expose
 * the cell click event.  This allows us to detect when a cell (date) is 
 * selected and fire the appropriate event. 
 * 
 * 
 * Style Examples:
 * <pre>
 * .goog-date-picker .grid {
 *   border: 1px solid black;
 *   padding: 4px 4px 4px 4px;
 * }
 *
 * .goog-date-picker .grid td {
 *   border: 1px solid black;
 *   padding: 2px 2px 2px 2px;
 * }
 *
 * .goog-date-picker .selected {
 *   background: #99CCFF
 * }
 * </pre>
 * 
 * @author Kuali Student Team
 *
 */
public class DatePicker extends com.google.gwt.widgetideas.datepicker.client.DatePicker {

    private ChangeListenerCollection cellChangeListeners = new ChangeListenerCollection();

    
    /**
     * This overridden method detects if a DatePickerCell has been clicked and then
     * fires a change event on this object.  To detect this event use the 
     * addCellChangeListener(ChangeListener listener) method.
     * 
     * @see com.google.gwt.widgetideas.datepicker.client.DatePicker#onClick(com.google.gwt.user.client.ui.Widget)
     */
    public void onClick(Widget sender) {
        super.onClick(sender);
        if (sender instanceof DatePickerCell) {
            cellChangeListeners.fireChange(this);
        }
    }
    
    
    /**
     * This method adds a ChangeListener to the DatePicker widget.
     * 
     * @param listener
     */
    public void addCellChangeListener(ChangeListener listener){
        cellChangeListeners.add(listener);
    }

}
