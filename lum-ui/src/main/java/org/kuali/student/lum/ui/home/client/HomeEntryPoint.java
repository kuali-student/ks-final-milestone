package org.kuali.student.lum.ui.home.client;

import org.kuali.student.lum.ui.home.client.view.HomeMenuController;
import org.kuali.student.lum.ui.home.client.view.HomeResources;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.libideas.client.StyleInjector;
import com.google.gwt.libideas.resources.client.CssResource;
import com.google.gwt.libideas.resources.client.ResourcePrototype;
import com.google.gwt.user.client.ui.RootPanel;

public class HomeEntryPoint implements EntryPoint{

    HomeMenuController homeController = new HomeMenuController();
    
    @Override
    public void onModuleLoad() {
        final String injectString = this.getCssString();
        StyleInjector.injectStylesheet(injectString);   

        RootPanel.get().add(homeController);
        homeController.showDefaultView();            
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
