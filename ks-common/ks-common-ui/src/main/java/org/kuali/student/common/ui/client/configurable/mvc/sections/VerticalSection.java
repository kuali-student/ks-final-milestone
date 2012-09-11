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

package org.kuali.student.common.ui.client.configurable.mvc.sections;

import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.widgets.field.layout.layouts.VerticalFieldLayout;

import com.google.gwt.user.client.ui.Composite;

/**
 * A section which uses a VerticalFieldLayout
 * 
 * @author Kuali Student Team
 * @see VerticalFieldLayout
 */
public class VerticalSection extends BaseSection {

    private Composite showAllLink = null;

    public VerticalSection() {
        layout = new VerticalFieldLayout();
        this.add(layout);
    }

    public VerticalSection(SectionTitle title) {
        layout = new VerticalFieldLayout(title);
        this.add(layout);
    }

    public Composite getShowAllLink() {
        return showAllLink;
    }

    public void addShowAllLink(Composite showAllLink) {
        this.addWidget(showAllLink);
        this.showAllLink = showAllLink;
    }

}
