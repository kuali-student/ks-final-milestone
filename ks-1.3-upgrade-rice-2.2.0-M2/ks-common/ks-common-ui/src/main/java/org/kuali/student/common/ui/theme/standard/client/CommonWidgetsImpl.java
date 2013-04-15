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

package org.kuali.student.common.ui.theme.standard.client;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.theme.CommonWidgets;
import org.kuali.student.common.ui.client.widgets.KSLabel;

import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

public class CommonWidgetsImpl implements CommonWidgets{

	@Override
	public List<KSLabel> getHeaderCustomLinks() {
		List<KSLabel> links = new ArrayList<KSLabel>();
		return links;
	}

	@Override
	public Widget getHeaderWidget() {
		return new Image(KSClientBundle.INSTANCE.headerImage());
	}

}
