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
import org.kuali.student.common.ui.client.widgets.field.layout.layouts.HorizontalLayout;

/**
 * This class uses TableFieldLayout to build a section with fields on the right and field labels on the left .
 * 
 * Fields are separated with a light grey horizontal line.
 *
 */
public class HorizontalSection extends BaseSection{
	public HorizontalSection(){
		init();
	}

	public HorizontalSection(SectionTitle title){
		init();
		layout.setLayoutTitle(title);
	}

	private void init() {
		layout = new HorizontalLayout();
		this.add(layout);
	}
	
	public void nextRow(){
		((HorizontalLayout)layout).nextRow();
	}

}
