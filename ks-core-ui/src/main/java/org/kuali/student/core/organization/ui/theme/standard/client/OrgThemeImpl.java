package org.kuali.student.core.organization.ui.theme.standard.client;

import org.kuali.student.core.organization.ui.client.theme.OrgCss;
import org.kuali.student.core.organization.ui.client.theme.OrgTheme;


import com.google.gwt.core.client.GWT;

public class OrgThemeImpl implements OrgTheme{

    private final OrgCss orgCss = GWT.create(OrgCssImpl.class);
    @Override
    public OrgCss getOrgCss() {
        
        return orgCss;
    }

}
