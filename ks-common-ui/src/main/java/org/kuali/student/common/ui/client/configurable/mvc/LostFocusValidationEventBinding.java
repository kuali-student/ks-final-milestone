package org.kuali.student.common.ui.client.configurable.mvc;

import org.kuali.student.common.ui.client.mvc.Callback;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.HasBlurHandlers;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.Widget;

public class LostFocusValidationEventBinding  implements ValidationEventBinding {
    
    public void bind(final FieldDescriptor fd) {
    	Widget w = fd.getFieldWidget();
        if (w instanceof HasBlurHandlers) {
            ((HasBlurHandlers)w).addBlurHandler(new BlurHandler() {
                public void onBlur(BlurEvent event) {
                	fd.setHasHadFocus(true);
                	fd.getValidationRequestCallback().exec(true);
                }
            });
        }
        else if(w instanceof HasValueChangeHandlers){
        	((HasValueChangeHandlers) w).addValueChangeHandler(new ValueChangeHandler(){

				@Override
				public void onValueChange(ValueChangeEvent event) {
					fd.setHasHadFocus(true);
					fd.getValidationRequestCallback().exec(true);	
				}
			});
        } else {
            //TODO address the exception
          //  throw new IllegalArgumentException("It must implements HasBlurHandlers interface.");
        }
    }
}