package org.kuali.student.lum.lu.ui.home.client;

import org.kuali.student.lum.lu.ui.home.client.view.HomeResources;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.libideas.client.StyleInjector;
import com.google.gwt.libideas.resources.client.CssResource;
import com.google.gwt.libideas.resources.client.ResourcePrototype;

public class HomeEntryPoint implements EntryPoint{

    @Override
    public void onModuleLoad() {
        //RootPanel.get().add(new HomePanel());
        final String injectString = this.getCssString();
        StyleInjector.injectStylesheet(injectString);   
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
