package org.kuali.student.core.organization.ui.client.theme;



import com.google.gwt.core.client.GWT;

public interface OrgTheme {
    public static final OrgTheme INSTANCE = GWT.create(OrgTheme.class);
    
    public OrgCss getOrgCss();
}
