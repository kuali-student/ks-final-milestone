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

package org.kuali.student.common.ui.client.widgets.counting.impl;

import static com.google.gwt.event.dom.client.KeyCodes.KEY_BACKSPACE;
import static com.google.gwt.event.dom.client.KeyCodes.KEY_CTRL;
import static com.google.gwt.event.dom.client.KeyCodes.KEY_DELETE;
import static com.google.gwt.event.dom.client.KeyCodes.KEY_DOWN;
import static com.google.gwt.event.dom.client.KeyCodes.KEY_END;
import static com.google.gwt.event.dom.client.KeyCodes.KEY_ENTER;
import static com.google.gwt.event.dom.client.KeyCodes.KEY_ESCAPE;
import static com.google.gwt.event.dom.client.KeyCodes.KEY_HOME;
import static com.google.gwt.event.dom.client.KeyCodes.KEY_LEFT;
import static com.google.gwt.event.dom.client.KeyCodes.KEY_PAGEDOWN;
import static com.google.gwt.event.dom.client.KeyCodes.KEY_PAGEUP;
import static com.google.gwt.event.dom.client.KeyCodes.KEY_RIGHT;
import static com.google.gwt.event.dom.client.KeyCodes.KEY_SHIFT;
import static com.google.gwt.event.dom.client.KeyCodes.KEY_TAB;
import static com.google.gwt.event.dom.client.KeyCodes.KEY_UP;

import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSTextArea;
import org.kuali.student.common.ui.client.widgets.counting.KSTextAreaAbstract;

import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.VerticalPanel;



/**
 * This is a description of what this class does - Gary Struthers don't forget to fill this in. 
 * 
 * @author Kuali Student Team (gstruthers@berkeley.edu)
 *
 */
public class KSTextAreaImpl extends KSTextAreaAbstract {
    private final VerticalPanel panel = new VerticalPanel();
    private KSTextArea textArea = new KSTextArea();
    private KSLabel label = new KSLabel();
    private static final String REMAINING = " Characters remaining | "; //TODO get from resource
    private static final String MAXIMUM = " Character Maximum"; //TODO get from resource
    private int maxTextLength = -1;

    private final KeyDownHandler keyDownHandler = new KeyDownHandler(){

        public void onKeyDown(KeyDownEvent event) {
            String text = textArea.getText();
            if(text.length() >= maxTextLength){
                int keyCode = event.getNativeKeyCode();
                if ((keyCode != (char) KEY_BACKSPACE) && (keyCode != (char) KEY_CTRL)
                        && (keyCode != (char) KEY_DELETE) && (keyCode != (char) KEY_DOWN)
                        && (keyCode != (char) KEY_END) && (keyCode != (char) KEY_ENTER)
                        && (keyCode != (char) KEY_ESCAPE)  && (keyCode != (char) KEY_HOME) 
                        && (keyCode != (char) KEY_LEFT) && (keyCode != (char) KEY_PAGEDOWN)
                        && (keyCode != (char) KEY_PAGEUP) && (keyCode != (char) KEY_RIGHT)
                        && (keyCode != (char) KEY_SHIFT)
                        && (keyCode != (char) KEY_TAB)&& (keyCode != (char) KEY_UP)) {
                    textArea.cancelKey();
                } 
            } 
        }            
    };
    private final KeyUpHandler keyUpHandler = new KeyUpHandler(){
        public void onKeyUp(KeyUpEvent event) {
            int remaining = maxTextLength - textArea.getText().length();
            label.setText(remaining + REMAINING + maxTextLength + MAXIMUM); 

        }
    };

    public KSTextAreaImpl() {
        super();
        initWidget(panel);
        textArea.addKeyDownHandler(keyDownHandler);
        textArea.addKeyUpHandler(keyUpHandler);
        panel.add(textArea);
        panel.add(label);
    }



    /**
     * Maximum number of characters application allows for this field.
     * @return the maxTextLength
     */
    @Override
    public int getMaxTextLength() {
        return maxTextLength;
    }

    /**
     * Maximum character length application allows in this field.
     * Should be called in the wrapper class constructor
     * @param maxTextLength the maxTextLength to set
     */
    @Override
    protected void setMaxTextLength(int maxTextLength) {
        this.maxTextLength = maxTextLength;
        label.setText(maxTextLength + REMAINING + maxTextLength + MAXIMUM);
    }

}
