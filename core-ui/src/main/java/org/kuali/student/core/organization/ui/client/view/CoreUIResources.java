package org.kuali.student.core.organization.ui.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.libideas.resources.client.CssResource;
import com.google.gwt.libideas.resources.client.ImmutableResourceBundle;

public interface CoreUIResources extends ImmutableResourceBundle {
    public static final CoreUIResources INSTANCE = (CoreUIResources) GWT.create(CoreUIResources.class);
    
    @Resource("org/kuali/student/core/organization/ui/public/KualiStudent.css")
    public CssResource generalCss();

}
