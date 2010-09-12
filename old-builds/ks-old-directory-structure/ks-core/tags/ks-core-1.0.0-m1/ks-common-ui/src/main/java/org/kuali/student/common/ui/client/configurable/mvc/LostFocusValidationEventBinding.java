/*
 * Copyright 2009 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 * 
 * http://www.osedu.org/licenses/ECL-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.kuali.student.common.ui.client.configurable.mvc;

import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.widgets.suggestbox.SuggestPicker;

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
        else {
            //TODO address the exception
          //  throw new IllegalArgumentException("It must implements HasBlurHandlers interface.");
        }
        //FIXME hack for orgPicker
        if(w instanceof SuggestPicker){
        	((SuggestPicker) w).addValueChangeHandler(new ValueChangeHandler<String>(){

				@Override
				public void onValueChange(ValueChangeEvent<String> event) {
					fd.setHasHadFocus(true);
					fd.getValidationRequestCallback().exec(true);	
				}
			});
        }
    }
}
