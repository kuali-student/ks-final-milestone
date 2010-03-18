package org.kuali.student.core.organization.ui.theme.standard.client;

import org.kuali.student.core.organization.ui.client.theme.OrgCss;

import com.google.gwt.libideas.resources.client.CssResource;
import com.google.gwt.libideas.resources.client.ResourcePrototype;

public class OrgCssImpl implements OrgCss{

    @Override
    public String getCssString() {
        String injectString = "";
        for(ResourcePrototype r: OrgClientBundle.INSTANCE.getResources()){
            if(r instanceof CssResource){
                if(((CssResource)r).getText() != null){
                    injectString = injectString + "\n" + (((CssResource)r).getText());
                }
            }
        }
        return injectString;
    }

}
