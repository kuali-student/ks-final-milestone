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

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.RootPanel;

public class ApplicationPanel {
	private static final ApplicationPanel _impl = GWT.create(ApplicationPanel.class);
	private ApplicationPanel() {
		
	}
	private AbsolutePanel _get() {
		AbsolutePanel result = RootPanel.get("applicationPanel");
		if (result == null) {
			result = RootPanel.get();
		}
		return result;
	}
	public static AbsolutePanel get() {
		return _impl._get();
	}
}
