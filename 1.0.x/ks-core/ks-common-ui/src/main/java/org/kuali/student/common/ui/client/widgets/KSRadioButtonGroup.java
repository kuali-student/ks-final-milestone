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

package org.kuali.student.common.ui.client.widgets;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;

/**
 * 
 * Not an actual widget, but rather a utility class that implements behav 
 * 
 * @author Kuali Student Team
 *
 */
public class KSRadioButtonGroup {
    private final List<KSRadioButton> radioButtons = new ArrayList<KSRadioButton>();
    private final ValueChangeHandler<Boolean> changeHandler = new ValueChangeHandler<Boolean>() {
        public void onValueChange(ValueChangeEvent<Boolean> event) {
            handlerManager.fireEvent(event);
        }
    };
    private final HandlerManager handlerManager = new HandlerManager(this);
    
    public void addRadioButton(KSRadioButton button) {
        radioButtons.add(button);
        button.addValueChangeHandler(changeHandler);
    }
    public List<KSRadioButton> getRadioButtons() {
        return radioButtons;
    }
    
    public HandlerRegistration addValueChangeHandler(ValueChangeHandler<Boolean> handler) {
        return handlerManager.addHandler(ValueChangeEvent.getType(), handler);
    }
}
