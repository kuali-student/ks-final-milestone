/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

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
