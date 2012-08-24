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

import org.kuali.student.common.ui.client.util.DebugIdUtils;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.RadioButton;

/**
 * KSRadioButton wraps gwt RadioButton.  This class provides most of the same functionality, but sets KS css styles
 * for its default look and a variety of RadioButton events (for improved browser compatibility and customizability).
 *
 * @author Kuali Student Team
 *
 */
@Deprecated
public class KSRadioButton extends RadioButton{

    /**
     * Creates a new radio button associated with a particular group name, and initialized with the given label (optionally treated as HTML).
     *
     * @param name the name of the radio button group
     * @param label the text to appear in the label
     * @param asHTML true to treat the label as HTML, false otherwise.
     */
    public KSRadioButton(String group, String label, boolean asHTML) {
        super(group, label, asHTML);
        ensureDebugId(DebugIdUtils.createWebDriverSafeDebugId(group + "-" + label));
        setupDefaultStyle();
    }

    /**
     * Creates a new radio associated with a particular group name, and initialized with the given HTML label.
     *
     * @param name the name of the radio button group
     * @param label the text to appear in the label
     */
    public KSRadioButton(String group, String label) {
        super(group, label);
        ensureDebugId(DebugIdUtils.createWebDriverSafeDebugId(group + "-" + label));
        setupDefaultStyle();
    }

    /**
     * Creates a new radio associated with a particular group name.
     *
     * @param name the name of the radio button group
     */
    public KSRadioButton(String group) {
        super(group);
        setupDefaultStyle();
    }

    /**
     * This method sets the default style for the radio button and radio button events.
     *
     */
    private void setupDefaultStyle() {
        addStyleName("KS-Radio");

        this.addBlurHandler(new BlurHandler(){
            public void onBlur(BlurEvent event) {
                KSRadioButton.this.removeStyleName("KS-Radio-Focus");

            }
        });

        this.addFocusHandler(new FocusHandler(){
            public void onFocus(FocusEvent event) {
                KSRadioButton.this.addStyleName("KS-Radio-Focus");

            }
        });

        //hover does not function correctly on radio buttons
/*        this.addMouseOverHandler(new MouseOverHandler(){
            public void onMouseOver(MouseOverEvent event) {
                KSRadioButton.this.addStyleName(KSStyles.KS_RADIO_HOVER_STYLE);

            }
        });

        this.addMouseOutHandler(new MouseOutHandler(){

            public void onMouseOut(MouseOutEvent event) {
                KSRadioButton.this.removeStyleName(KSStyles.KS_RADIO_HOVER_STYLE);

            }

        });*/

        this.addValueChangeHandler(new ValueChangeHandler<Boolean>(){
        @Override

            public void onValueChange(ValueChangeEvent<Boolean> event) {
                if(KSRadioButton.this.getValue()){
                    KSRadioButton.this.addStyleName("KS-Radio-Selected");
                }
                else{
                    KSRadioButton.this.removeStyleName("KS-Radio-Selected");
                    KSRadioButton.this.setFocus(false);
                }

            }
        });

    }


}


