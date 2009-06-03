package org.kuali.student.lum.ui.home.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.libideas.resources.client.CssResource;
import com.google.gwt.libideas.resources.client.ImmutableResourceBundle;

public interface HomeResources extends ImmutableResourceBundle{
    public static final HomeResources INSTANCE =  (HomeResources) GWT.create(HomeResources.class);
    @Resource("org/kuali/student/lum/lu/ui/home/public/Home.css")
    public CssResource homeCss();
}
