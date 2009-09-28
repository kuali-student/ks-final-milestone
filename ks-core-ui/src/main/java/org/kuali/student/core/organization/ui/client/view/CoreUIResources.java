package org.kuali.student.core.organization.ui.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.libideas.resources.client.CssResource;
import com.google.gwt.libideas.resources.client.ImmutableResourceBundle;

public interface CoreUIResources extends ImmutableResourceBundle {
    public static final CoreUIResources INSTANCE = (CoreUIResources) GWT.create(CoreUIResources.class);
    
    public static final String BACKGROUNDCOLOR = "#ffffff";
    public static final String SearchResultsTableOdd = "#f1c232";
    public static final String SearchResultsTableEven = "#ffd966";
    public static final String SelectionChoosing = "#ccdddd";
    public static final String SelectionChosen = "#ddeeee";
    public static final String SubSelectionChoosing = "#eeeeee";
    public static final String SubSelectionChosen = "#ffffff";
    
    
    @Resource("org/kuali/student/core/organization/ui/public/KualiStudent.css")
    public CssResource generalCss();

}
