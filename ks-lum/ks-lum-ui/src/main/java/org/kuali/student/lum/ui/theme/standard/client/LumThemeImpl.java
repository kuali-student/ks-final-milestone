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

package org.kuali.student.lum.ui.theme.standard.client;

import org.kuali.student.lum.lu.ui.main.client.theme.LumCss;
import org.kuali.student.lum.lu.ui.main.client.theme.LumImages;
import org.kuali.student.lum.lu.ui.main.client.theme.LumTheme;
import org.kuali.student.lum.lu.ui.main.client.theme.LumWidgets;

import com.google.gwt.core.client.GWT;

public class LumThemeImpl implements LumTheme{

	private final LumImages lumImages = GWT.create(LumImagesImpl.class);
	private final LumWidgets lumWidgets = GWT.create(LumWidgetsImpl.class);
	private final LumCss lumCss = GWT.create(LumCssImpl.class);
	
	@Override
	public LumCss getLumCss() {
		return lumCss;
	}

	@Override
	public LumImages getLumImages() {
		return lumImages;
	}

	@Override
	public LumWidgets getLumWidgets() {
		return lumWidgets;
	}

}
