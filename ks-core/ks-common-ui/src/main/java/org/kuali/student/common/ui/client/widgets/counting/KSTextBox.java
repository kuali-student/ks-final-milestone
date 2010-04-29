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
package org.kuali.student.common.ui.client.widgets.counting;

import org.kuali.student.common.ui.client.widgets.counting.impl.KSTextBoxImpl;

import com.google.gwt.core.client.GWT;

/**
 * This is a description of what this class does - Gary Struthers don't forget to fill this in. 
 * 
 * @author Kuali Student Team (gstruthers@berkeley.edu)
 *
 */
public class KSTextBox extends KSTextBoxAbstract {
    
    private KSTextBoxAbstract textBox = GWT.create(KSTextBoxImpl.class);

    
    /**
     * This constructs a KSTextBox with a label showing how many
     * characters are in the text box and how many it can contain.
     * 
     */
    public KSTextBox() {
    	super();
        super.initWidget(textBox);
    }

    /**
     * This constructs a KSTextBox with a label showing how many
     * characters are in the text box and how many it can contain.
     * 
     * @param maxTextLength the application's defined max 
     */
    public KSTextBox(int maxTextLength) {
        super();
        initWidget(textBox);
        textBox.setMaxTextLength(maxTextLength);
    }

    public void setMaxTextLength(int maxTextLength) {
        textBox.setMaxTextLength(maxTextLength);
    }

    public int getMaxTextLength() {
        return textBox.getMaxTextLength();
    }
    

}
