package org.kuali.student.lum.ui.requirements.client;

import org.kuali.student.lum.ui.requirements.client.controller.HomeMenuController;
import org.kuali.student.lum.ui.requirements.client.view.RequirementsResources;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.libideas.client.StyleInjector;
import com.google.gwt.libideas.resources.client.CssResource;
import com.google.gwt.libideas.resources.client.ResourcePrototype;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class RequirementsEntryPoint implements EntryPoint {

    HomeMenuController homeController = new HomeMenuController();
    
    public void onModuleLoad() {
        
        final String injectString = this.getCssString();
        StyleInjector.injectStylesheet(injectString);
        
        RootPanel.get().add(homeController);
        homeController.showDefaultView();                                     
    }
    
    public String getCssString(){
        String injectString = "";
         for(ResourcePrototype r: RequirementsResources.INSTANCE.getResources()){
             if(r instanceof CssResource){
                 if(((CssResource)r).getText() != null){
                     injectString = injectString + "\n" + (((CssResource)r).getText());
                 }
             }
         }
         return injectString;
    }    
}
