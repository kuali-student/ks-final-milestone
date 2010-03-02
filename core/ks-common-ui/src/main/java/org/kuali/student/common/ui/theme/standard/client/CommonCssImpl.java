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
                if(((CssResource)r).getText() != null && !((CssResource)r).getName().contains("resetCss") && !((CssResource)r).getName().contains("fontCss")){
                    injectString = injectString + "\n" + (((CssResource)r).getText());
                }
            }
        }
        return injectString;
	}

	@Override
	public String getResetCssString() {
		return ((CssResource)KSClientBundle.INSTANCE.getResource("resetCss")).getText();
	}
	
	@Override
	public String getInitializeCssString() {
		return ((CssResource)KSClientBundle.INSTANCE.getResource("fontCss")).getText();
	}

}