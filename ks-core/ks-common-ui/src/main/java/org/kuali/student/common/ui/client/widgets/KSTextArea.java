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

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.TextArea;

/**
 * KSTextArea wraps gwt TextArea.  This class provides most of the same functionality, but sets KS css styles
 * for its default look and a variety of TextArea events (for improved browser compatibility and customizability).
 *
 * @author Kuali Student Team
 *
 */
public class KSTextArea extends TextArea{

    /**
     * Creates a new empty text area.
     *
     */
    public KSTextArea() {
        super();
        setupDefaultStyle();
    }

    /**
     * Creates a new text area using the text area element specified.
     *
     * @param element a <TextArea> element
     */
    public KSTextArea(Element element) {
        super(element);
        setupDefaultStyle();
    }

    /**
     * This method sets the default style for the text area and text area events.
     *
     */
    private void setupDefaultStyle() {
        addStyleName("KS-Textarea");

        this.addBlurHandler(new BlurHandler(){
            public void onBlur(BlurEvent event) {
                KSTextArea.this.removeStyleName("KS-Textarea-Focus");

            }
        });

        this.addFocusHandler(new FocusHandler(){
            public void onFocus(FocusEvent event) {
                KSTextArea.this.addStyleName("KS-Textarea-Focus");

            }
        });

        this.addMouseOverHandler(new MouseOverHandler(){
            public void onMouseOver(MouseOverEvent event) {
                KSTextArea.this.addStyleName("KS-Textarea-Hover");

            }
        });

        this.addMouseOutHandler(new MouseOutHandler(){

            public void onMouseOut(MouseOutEvent event) {
                KSTextArea.this.removeStyleName("KS-Textarea-Hover");

            }

        });

    }



}
