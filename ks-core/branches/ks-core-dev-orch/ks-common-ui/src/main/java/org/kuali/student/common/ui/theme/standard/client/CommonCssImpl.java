package org.kuali.student.common.ui.theme.standard.client;

import org.kuali.student.common.ui.client.theme.CommonCss;

import com.google.gwt.libideas.resources.client.CssResource;
import com.google.gwt.libideas.resources.client.ResourcePrototype;

public class CommonCssImpl implements CommonCss {

	@Override
	public String getCssString() {
       String injectString = "";
        for(ResourcePrototype r: KSClientBundle.INSTANCE.getResources()){
            if(r instanceof CssResource){
                if(((CssResource)r).getText() != null){
                    injectString = injectString + "\n" + (((CssResource)r).getText());
                }
            }
        }
        return injectString;
	}

}