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
