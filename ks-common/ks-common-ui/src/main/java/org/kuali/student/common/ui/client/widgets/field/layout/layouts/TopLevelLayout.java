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

package org.kuali.student.common.ui.client.widgets.field.layout.layouts;

/**
 * A top level layout that is exactly the same as VerticalFieldLayout except it has a different style name
 * of "ks-page-content" defined.
 * @author Kuali Student Team
 *
 */
public class TopLevelLayout extends VerticalFieldLayout{

	public TopLevelLayout(){
		this.setStyleName("ks-page-content");
	}
}
