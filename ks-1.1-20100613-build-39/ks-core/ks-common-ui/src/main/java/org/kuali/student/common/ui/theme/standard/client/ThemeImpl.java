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

import org.kuali.student.common.ui.client.theme.CommonCss;
import org.kuali.student.common.ui.client.theme.CommonImages;
import org.kuali.student.common.ui.client.theme.CommonWidgets;
import org.kuali.student.common.ui.client.theme.RichTextEditorImages;
import org.kuali.student.common.ui.client.theme.Theme;

import com.google.gwt.core.client.GWT;

public class ThemeImpl implements Theme{

	private final CommonImages commonImages = GWT.create(CommonImagesImpl.class);
	private final CommonCss commonCss = GWT.create(CommonCssImpl.class);
	private final CommonWidgets commonWidgets = GWT.create(CommonWidgetsImpl.class);
	private final RichTextEditorImages richText = GWT.create(RichTextEditorImagesImpl.class);
	
	@Override
	public CommonImages getCommonImages() {
		return commonImages;
	}

	@Override
	public CommonCss getCommonCss() {
		return commonCss;
	}

	@Override
	public RichTextEditorImages getRichTextEditorImages() {
		return richText;
	}

	@Override
	public CommonWidgets getCommonWidgets() {
		return commonWidgets;
	}

}
