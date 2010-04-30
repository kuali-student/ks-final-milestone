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

package org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable;

import com.google.gwt.user.client.ui.Image;

/**
 * Factory interface that defines the images used to render a DynamicTable 
 * @author wilj
 *
 */
/**
 * @author wilj
 *
 */
public interface TableImages {
	/**
	 * Returns the small "busy spinner" image used to indicate that records are loading
	 * @return the small "busy spinner" image used to indicate that records are loading
	 */
	public Image getBusySpinner();

	/**
	 * Returns the spinner image used to indicate that the export process is running
	 * @return the spinner image used to indicate that the export process is running
	 */
	public Image getExportSpinner();

	/**
	 * Returns the image used to access the table options menu
	 * @return the image used to access the table options menu
	 */
	public Image getOptionsButton();

	/**
	 * Returns the descending sort indicator image
	 * @return the descending sort indicator image
	 */
	public Image getSortArrowDown();

	/**
	 * Returns the unsorted sort indicator image
	 * @return the unsorted sort indicator image
	 */
	public Image getSortArrowUnsorted();

	/**
	 * Returns the ascending sort indicator image
	 * @return the ascending sort indicator image
	 */
	public Image getSortArrowUp();
}
