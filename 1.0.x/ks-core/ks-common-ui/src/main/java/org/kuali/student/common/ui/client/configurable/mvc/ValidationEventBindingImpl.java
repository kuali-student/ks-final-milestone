/**
 * Copyright 2010 The Kuali Foundation Licensed under the
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
import org.kuali.student.common.ui.client.mvc.HasDataValue;
import org.kuali.student.common.ui.client.mvc.HasFocusLostCallbacks;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.list.HasSelectionChangeHandlers;
import org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract;
import org.kuali.student.common.ui.client.widgets.list.SelectionChangeEvent;
import org.kuali.student.common.ui.client.widgets.list.SelectionChangeHandler;
import org.kuali.student.common.ui.client.widgets.suggestbox.SuggestPicker;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.Data.Value;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.HasBlurHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.Widget;

public class ValidationEventBindingImpl  implements ValidationEventBinding {
    
    public void bind(final FieldDescriptor fd) {
    	Widget w = fd.getFieldWidget();
        
        if (w instanceof HasSelectionChangeHandlers){
        	((HasSelectionChangeHandlers) w).addSelectionChangeHandler(new SelectionChangeHandler(){
				@Override
				public void onSelectionChange(SelectionChangeEvent event) {
					fd.setHasHadFocus(true);
                	fd.getValidationRequestCallback().exec(true);
				}
			});
        }
        else if (w instanceof HasBlurHandlers) {
            ((HasBlurHandlers)w).addBlurHandler(new BlurHandler() {
                public void onBlur(BlurEvent event) {
                	fd.setHasHadFocus(true);
                	fd.getValidationRequestCallback().exec(true);
                }
            });
        }
        else if(w instanceof HasFocusLostCallbacks){
        	((HasFocusLostCallbacks) w).addFocusLostCallback(new Callback<Boolean>(){
				@Override
				public void exec(Boolean result) {
					fd.setHasHadFocus(true);
                	fd.getValidationRequestCallback().exec(true);
				}
			});
        }
        else if(w instanceof MultiplicityComposite || w instanceof KSLabel 
        		|| w instanceof org.kuali.student.common.ui.client.configurable.mvc.multiplicity.MultiplicityComposite){
        	//Do nothing these are valid but do not fire validation events (maybe?)
        }
        else {
            //TODO address the exception
        	GWT.log("The field with key: " + fd.getFieldKey() +
        			" does not use a widget which implements an interface that can perform on the fly validation", null);
        }

    }
}
