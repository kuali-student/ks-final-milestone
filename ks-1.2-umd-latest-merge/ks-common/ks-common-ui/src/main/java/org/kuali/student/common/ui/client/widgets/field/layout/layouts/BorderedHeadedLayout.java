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
 * Date: 14-Jul-2010
 * Time: 10:52:16 AM
 */

package org.kuali.student.common.ui.client.widgets.field.layout.layouts;

import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;

/**
 * This class will add a single border to the layout and a background colour to the header
 * Title text is .H4 heading
 *
 */
public class BorderedHeadedLayout extends HeadedLayout{

	
	public BorderedHeadedLayout(){
		super();
		verticalLayout.addStyleName("ks-form-bordered-body");
		body.setStyleName("ks-form-bordered");
		body.add(verticalLayout);

	}
	
	public BorderedHeadedLayout(String titleText, boolean updateable){
		super();
		this.updateable = updateable;
		
		buildHeader(titleText, updateable);

		body.add(header);
		body.add(verticalLayout);

		body.setStyleName("ks-form-bordered");
		verticalLayout.addStyleName("ks-form-bordered-body");
	}

	@Override
	public void setLayoutTitle(SectionTitle layoutTitle) {
		layoutTitle.addStyleName("ks-form-bordered-header-title");
		header.setHeaderTitle(layoutTitle);
		
	}

	@Override
	protected void buildHeader(String titleText, boolean updateable) {
		SectionTitle title = SectionTitle.generateH4Title(titleText);
		header = new Header(title, updateable);
		header.setStyleName("ks-form-bordered-header");
		title.addStyleName("ks-form-bordered-header-title");
		
	}
	
}
