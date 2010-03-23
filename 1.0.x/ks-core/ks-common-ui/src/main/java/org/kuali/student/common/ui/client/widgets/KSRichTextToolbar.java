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
package org.kuali.student.common.ui.client.widgets;


import org.kuali.student.common.ui.client.widgets.impl.KSRichTextToolbarImpl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RichTextArea;

/**
 * KSRichTextToolbar provides an interface for adding different text formatting styles to text in a
 * a RichTextArea.
 * 
 * The class implementation is a modified version of the one found in the GWT Samples.
 * 
 * @author Kuali Student Team
 *
 */
public class KSRichTextToolbar extends KSRichTextToolbarAbstract{ 

    private KSRichTextToolbarAbstract richTextToolbar = GWT.create(KSRichTextToolbarImpl.class);

    /**
     * Creates a new toolbar that drives the given rich text area.
     * 
     * @param richText the rich text area to be controlled
     */
    public KSRichTextToolbar(RichTextArea richText) {
        init(richText);
        initWidget(richTextToolbar);
    }



    /**
     * Returns true if the toolbar is being interacted with, false otherwise.
     * 
     * @return true if the toolbar is being interacted with, false otherwise.
     */
    public boolean inUse(){
        return richTextToolbar.inUse();
    }



    /**
     * Initializes this toolbar with the specified options.
     * 
     *      
     */
    @Override
    protected void init(RichTextArea richText) {
        richTextToolbar.init(richText);
        
    }

}
