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

package org.kuali.student.common.ui.client.widgets;

/**
 * A panel that floats above content on either the left or right side of the screen.  This panel can open and close
 * (a drawer like effect) when a button is pressed. 
 * 
 * @author Kuali Student Team
 *
 */
public interface  KSCollapsableFloatPanel extends KSFloatPanel {
	/**
	 * Check if the collapsable panel is "expanded".  True if it is expanded, false otherwise.
	 * 
	 * @return True if the panel is expanded, false otherwise.
	 */
	public boolean isExpanded();

	/**
	 * Set the panel's expanded flag.  True will expand the panel, false will collapse the panel.
	 * 
	 * @param expanded boolean representing if the panel is expanded.
	 */
	public void setExpanded(boolean expanded);
}
