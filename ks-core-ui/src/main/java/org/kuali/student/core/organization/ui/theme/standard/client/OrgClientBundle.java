package org.kuali.student.core.organization.ui.theme.standard.client;


import com.google.gwt.core.client.GWT;
import com.google.gwt.libideas.resources.client.CssResource;
import com.google.gwt.libideas.resources.client.ImmutableResourceBundle;

public interface OrgClientBundle extends ImmutableResourceBundle{
    public static final OrgClientBundle INSTANCE =  GWT.create(OrgClientBundle.class);
    
    @Resource("org/kuali/student/core/organization/ui/theme/standard/public/css/Org.css")
    public CssResource orgCss();

}
