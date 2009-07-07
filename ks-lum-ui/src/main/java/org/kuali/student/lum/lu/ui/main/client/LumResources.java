package org.kuali.student.lum.lu.ui.main.client;


import com.google.gwt.core.client.GWT;
import com.google.gwt.libideas.resources.client.CssResource;
import com.google.gwt.libideas.resources.client.ImmutableResourceBundle;

public interface LumResources extends ImmutableResourceBundle{
    public static final LumResources INSTANCE =  (LumResources) GWT.create(LumResources.class);
    @Resource("org/kuali/student/lum/lu/ui/main/public/LumMain.css")
    public CssResource homeCss();
}
