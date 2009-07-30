/*
 * Copyright 2007 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.common.ui.client.configurable.mvc;

import com.google.gwt.user.client.ui.HTML;

/**
 * This is a description of what this class does - hjohnson don't forget to fill this in. 
 * 
 * @author Kuali Student Team (kuali-student@googlegroups.com)
 *
 */
public class SectionTitle extends HTML {

    private SectionTitle() {
        super();        
    }

    public static SectionTitle generateEmptyTitle() {
        return generateTitle(" ");        
    }

    public static SectionTitle generateH1Title(String titletext) {               
        return generateTitle("<h1>" + titletext + "</h1>");        
    }

    public static SectionTitle generateH2Title(String titletext) {
        return generateTitle("<h2>" + titletext + "</h2>");        
    }

    public static SectionTitle generateH3Title(String titletext) {
        return generateTitle("<h3>" + titletext + "</h3>");        
    }
    
    public static SectionTitle generateH4Title(String titletext) {
        return generateTitle("<h4>" + titletext + "</h4>");        
    }
    
    public static SectionTitle generateH5Title(String titletext) {
        return generateTitle("<h5>" + titletext + "</h5>");        
    }
    
    public static SectionTitle generateH6Title(String titletext) {
        return generateTitle("<h6>" + titletext + "</h6>");        
    }
    private static SectionTitle generateTitle(String text) {
        SectionTitle thisTitle = new SectionTitle();
        thisTitle.setHTML(text);
        return thisTitle;
    }
}


