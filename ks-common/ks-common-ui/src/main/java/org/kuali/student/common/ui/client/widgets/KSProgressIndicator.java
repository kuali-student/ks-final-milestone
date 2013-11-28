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


import org.kuali.student.common.ui.client.widgets.impl.KSProgressIndicatorImpl;

import com.google.gwt.core.client.GWT;

/**
 * KSProgressIndicator can be shown to indicate processing of some request.  It can then be hidden again, to
 * indicate process completion.
 * 
 * The indicator contains a twiddler/spinner image and a text label.
 * 
 * @author Kuali Student Team
 *
 */
public class KSProgressIndicator extends KSProgressIndicatorAbstract{ 
    KSProgressIndicatorAbstract indicator = GWT.create(KSProgressIndicatorImpl.class);
    
    public KSProgressIndicator() {
        this.initWidget(indicator);
        
    }

    /**
     * Hides the progress indicator.
     * 
     */
    @Override
    public void hide() {
        indicator.hide();
        
    }

    /**
     * Shows the progress indicator.
     * 
     */
    @Override
    public void show() {
        indicator.show();
        
    }

    /**
     * Sets the text for the progress indicator, explaining what is being processed.
     * 
     * @param labelText the text/title of the progress indicator
     */
    @Override
    public void setText(String labelText) {
        indicator.setText(labelText);
        
    }


}
