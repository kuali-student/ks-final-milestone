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

/**
 * General purpose exception thrown by the DynamicTable and some of its associated classes
 * @author wilj
 *
 */
public class TableException extends RuntimeException {
	private static final long serialVersionUID = -2574376216134473710L;

	public TableException() {
		super();
	}

	public TableException(final String message) {
		super(message);
	}

	public TableException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public TableException(final Throwable cause) {
		super(cause);
	}

}
