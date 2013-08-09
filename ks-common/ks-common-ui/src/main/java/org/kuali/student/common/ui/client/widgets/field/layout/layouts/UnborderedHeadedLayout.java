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
 *
 * Date: 14-Jul-2010
 * Time: 10:52:16 AM
 */

package org.kuali.student.common.ui.client.widgets.field.layout.layouts;

import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;


/**
 * This class has no border and the header text is underlined
 * Title text is .H5 heading
 *
 * // TODO rename styles to remove references to course format and activity
 *
 */
public class UnborderedHeadedLayout extends HeadedLayout {

    public UnborderedHeadedLayout(){
		super();
		verticalLayout.addStyleName("ks-form-course-format-activity");
		body.add(verticalLayout);

	}
	
	public UnborderedHeadedLayout(String titleText, boolean updateable){
		super();
		buildHeader(titleText, updateable);
		verticalLayout.addStyleName("ks-form-course-format-activity");
		body.add(header);
		body.add(verticalLayout);

	}

    @Override
    protected void buildHeader(String titleText, boolean updateable) {
		SectionTitle title = SectionTitle.generateH5Title(titleText);
		header = new Header(title, updateable);
		header.setStyleName("ks-form-course-format-activity-header");
		title.addStyleName("ks-form-course-format-activity-header-title");
	}

    @Override
	public void setLayoutTitle(SectionTitle layoutTitle) {
		layoutTitle.addStyleName("ks-form-bordered-activity-header-title");
		header.setHeaderTitle(layoutTitle);

	}

}
