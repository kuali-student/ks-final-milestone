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
package org.kuali.student.common.ui.client.widgets.breadcrumb;

/**
 * This is a description of what this class does - hjohnson don't forget to fill this in. 
 * 
 * @author Kuali Student Team (kuali-student@googlegroups.com)
 *
 */
public class KSBreadcrumbItem {
    
    String text;  
    String uri;
    String title;
    String target;

    
    
    /**
     * This constructs a ...
     * 
     */
    public KSBreadcrumbItem() {
        super();
    }
    
    

    /**
     * This constructs a ...
     * 
     * @param text
     * @param title
     */
    public KSBreadcrumbItem(String text, String title) {
        super();
        this.text = text;
        this.title = title;
    }



    /**
     * This constructs a ...
     * 
     * @param text
     * @param uri
     * @param title
     * @param target
     */
    public KSBreadcrumbItem(String text, String uri, String title, String target) {
        super();
        this.text = text;
        this.uri = uri;
        this.title = title;
        this.target = target;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }
    
    

}
