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

import static org.kuali.student.common.ui.client.widgets.KSStyles.KS_H1_SECTION_TITLE;
import static org.kuali.student.common.ui.client.widgets.KSStyles.KS_H2_SECTION_TITLE;
import static org.kuali.student.common.ui.client.widgets.KSStyles.KS_H3_SECTION_TITLE;
import static org.kuali.student.common.ui.client.widgets.KSStyles.KS_H4_SECTION_TITLE;
import static org.kuali.student.common.ui.client.widgets.KSStyles.KS_H5_SECTION_TITLE;
import static org.kuali.student.common.ui.client.widgets.KSStyles.KS_H6_SECTION_TITLE;
import static org.kuali.student.common.ui.client.widgets.KSStyles.KS_SECTION_TITLE;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.ComplexPanel;

/**
 * This is a description of what this class does - hjohnson don't forget to fill this in. 
 * 
 * @author Kuali Student Team (kuali-student@googlegroups.com)
 *
 */
public class SectionTitle extends ComplexPanel {


	
    private SectionTitle(Element e) {
        this.setElement(e);
    }

    public static SectionTitle generateEmptyTitle() {
        return generateTitle(DOM.createSpan(), null);  
    }

    public static SectionTitle generateH1Title(String titletext) {
    	
    	Element headerElement = DOM.createElement("H1");
    	headerElement.setInnerText(titletext);
        return generateTitle(headerElement, KS_H1_SECTION_TITLE);        
    }
    
    public static SectionTitle generateH2Title(String titletext) {
    	
    	Element headerElement = DOM.createElement("H2");
    	headerElement.setInnerText(titletext);
        return generateTitle(headerElement, KS_H2_SECTION_TITLE);        
    }
    
    public static SectionTitle generateH3Title(String titletext) {
    	
    	Element headerElement = DOM.createElement("H3");
    	headerElement.setInnerText(titletext);
        return generateTitle(headerElement, KS_H3_SECTION_TITLE);        
    }
    
    public static SectionTitle generateH4Title(String titletext) {
    	
    	Element headerElement = DOM.createElement("H4");
    	headerElement.setInnerText(titletext);
        return generateTitle(headerElement, KS_H4_SECTION_TITLE);        
    }
    
    public static SectionTitle generateH5Title(String titletext) {
    	
    	Element headerElement = DOM.createElement("H5");
    	headerElement.setInnerText(titletext);
        return generateTitle(headerElement, KS_H5_SECTION_TITLE);        
    }
    
    public static SectionTitle generateH6Title(String titletext) {
    	
    	Element headerElement = DOM.createElement("H6");
    	headerElement.setInnerText(titletext);
        return generateTitle(headerElement, KS_H6_SECTION_TITLE);        
    }
    
    private static SectionTitle generateTitle(Element header, String styleName) {
    	SectionTitle thisTitle = new SectionTitle(header);
    	thisTitle.addStyleName(KS_SECTION_TITLE);
        if(styleName != null){
        	thisTitle.addStyleName(styleName);
        }
        return thisTitle;
    }
    
/*    private static SectionTitle generateTitle(String text, String styleName) {
        SectionTitle thisTitle = new SectionTitle();
        thisTitle.setHTML(text);
        thisTitle.addStyleName(KS_SECTION_TITLE);
        if(styleName != null){
        	thisTitle.addStyleName(styleName);
        }
        return thisTitle;
    }*/
    
    public void setText(String text){
    	this.getElement().setInnerText(text);
    }
}


