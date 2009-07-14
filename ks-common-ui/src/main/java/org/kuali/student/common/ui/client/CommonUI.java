package org.kuali.student.common.ui.client;

import org.kuali.student.common.ui.client.css.KSCommonResources;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.StyleElement;
import com.google.gwt.libideas.client.StyleInjector;
import com.google.gwt.libideas.resources.client.CssResource;
import com.google.gwt.libideas.resources.client.ResourcePrototype;

public class CommonUI implements EntryPoint {
    public StyleElement commonStyle;

	public void onModuleLoad() {
	    final String injectString = this.getCssString();
        commonStyle = StyleInjector.injectStylesheet(injectString);
	}
	
	public String getCssString(){
	       String injectString = "";
	        for(ResourcePrototype r: KSCommonResources.INSTANCE.getResources()){
	            if(r instanceof CssResource){
	                if(((CssResource)r).getText() != null){
	                    injectString = injectString + "\n" + (((CssResource)r).getText());
	                }
	            }
	        }
	        return injectString;
	}
}
