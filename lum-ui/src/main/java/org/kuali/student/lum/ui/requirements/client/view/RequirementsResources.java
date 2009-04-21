package org.kuali.student.lum.ui.requirements.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.libideas.resources.client.CssResource;
import com.google.gwt.libideas.resources.client.ImmutableResourceBundle;

public interface RequirementsResources extends ImmutableResourceBundle{
    public static final RequirementsResources INSTANCE =  (RequirementsResources) GWT.create(RequirementsResources.class);
    @Resource("org/kuali/student/lum/ui/requirements/public/Requirements.css")
    public CssResource requirementsCss();
}
