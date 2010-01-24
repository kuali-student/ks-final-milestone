package org.kuali.student.commons.ui.widgets;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusListener;
import com.google.gwt.user.client.ui.HasFocus;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.PopupListener;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;


/**
 * This is a basic text box widget that pops up a date picker when clicked.
 * 
 *  Because it implements HasText and HasFocus, it is fully functional with
 *  the EditLabel widget.
 * 
 * @author Kuali Student Team
 *
 */
public class DateBox extends Composite implements HasText, HasFocus {
	public	final TextBox box = new TextBox();
	private DatePicker picker = new DatePicker();
	private PopupPanel popup = new PopupPanel(true);
	public static final DateTimeFormat formatter = DateTimeFormat.getFormat("MM/dd/y");
	
	public DateBox() {
		FlowPanel p = new FlowPanel();
		p.add(box);
		initWidget(p);
		
		popup.add(picker);
		box.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				showDatePicker();
			}
		});
		box.addKeyboardListener(new KeyboardListener(){

            public void onKeyDown(Widget sender, char keyCode, int modifiers) {
            }

            public void onKeyPress(Widget sender, char keyCode, int modifiers) {
            }
            public void onKeyUp(Widget sender, char keyCode, int modifiers) {
                if(keyCode == KeyboardListener.KEY_ENTER){
                    String dateString = box.getText();
                    try{
                    Date dateInput = formatter.parse(dateString);
                    picker.setFullDate(dateInput);
                    }catch(Exception e) {}
                    return;
                }
                String dateString = box.getText();
                try{
                Date dateInput = formatter.parse(dateString);
                picker.setFullDate(dateInput);
                }catch(Exception e) {}

            }
		    
		});
		popup.addPopupListener(new PopupListener() {
			public void onPopupClosed(PopupPanel sender, boolean autoClosed) {
                //setText(formatter.format(picker.selectedDate()));
                hideDatePicker();			
            }
		});
		
		picker.addCellChangeListener(new ChangeListener(){
            public void onChange(Widget sender) {
                setText(formatter.format(picker.selectedDate()));
                hideDatePicker();                
            }});
		
	}
	
	public void clear(){
		box.setText("");
	}
	
	public void setText(String text){
		box.setText(text);
	}

	public void showDatePicker() {
		Date current = null;

		String value = box.getText().trim();
		if (!value.equals("")) {
			try {
				current = formatter.parse(value);
			} catch (IllegalArgumentException e) {
				//Log.info("cannot parse as date: " + value);
			}
		}
        if(current != null){
            picker.setFullDate(current);
        }
		if (current == null) {
			current = new Date();
		}

		popup.setPopupPosition( getAbsoluteLeft(), getAbsoluteTop()+getOffsetHeight());

		popup.show();
	}

	public void hideDatePicker() {
		popup.hide();
	}
    
    public String getText() {
        return box.getText();
    }

    public int getTabIndex() {
        return box.getTabIndex();
    }

    public void setAccessKey(char key) {
       box.setAccessKey(key);
        
    }
    
    public void setFocus(boolean focused) {
        box.setFocus(focused);
        
    }

    public void setTabIndex(int index) {
        box.setTabIndex(index);
        
    }
    
    public void addFocusListener(FocusListener listener) {
       box.addFocusListener(listener);
        
    }
    
    public void removeFocusListener(FocusListener listener) {
        box.removeFocusListener(listener);
    }

    public void addKeyboardListener(KeyboardListener listener) {
        box.addKeyboardListener(listener);
        
    }

    public void removeKeyboardListener(KeyboardListener listener) {
        box.removeKeyboardListener(listener);
        
    }
}