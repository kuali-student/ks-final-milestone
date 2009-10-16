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
package org.kuali.student.lum.ui.home.client;

import org.kuali.student.lum.ui.home.client.view.HomeMenuController;
import org.kuali.student.lum.ui.home.client.view.HomeResources;
import org.kuali.student.lum.ui.requirements.client.controller.CourseReqManager;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.libideas.client.StyleInjector;
import com.google.gwt.libideas.resources.client.CssResource;
import com.google.gwt.libideas.resources.client.ResourcePrototype;
import com.google.gwt.user.client.ui.RootPanel;

public class HomeEntryPoint implements EntryPoint{

	CourseReqManager courseReqManager = new CourseReqManager(null);
    
    @Override
    public void onModuleLoad() {
        final String injectString = this.getCssString();
        StyleInjector.injectStylesheet(injectString);   

        RootPanel.get().add(courseReqManager);
        courseReqManager.showDefaultView();            
    }

    public String getCssString(){
        String injectString = "";
         for(ResourcePrototype r: HomeResources.INSTANCE.getResources()){
             if(r instanceof CssResource){
                 if(((CssResource)r).getText() != null){
                     injectString = injectString + "\n" + (((CssResource)r).getText());
                 }
             }
         }
         return injectString;
    }
}
