package org.kuali.student.lum.ui.theme.standard.client;

import org.kuali.student.lum.lu.ui.main.client.theme.LumCss;

import com.google.gwt.libideas.resources.client.CssResource;
import com.google.gwt.libideas.resources.client.ResourcePrototype;

public class LumCssImpl implements LumCss{

	@Override
	public String getCssString() {
       String injectString = "";
        for(ResourcePrototype r: LumClientBundle.INSTANCE.getResources()){
            if(r instanceof CssResource){
                if(((CssResource)r).getText() != null){
                    injectString = injectString + "\n" + (((CssResource)r).getText());
                }
            }
        }
        return injectString;
	}

}
