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

package org.kuali.student.common.ui.client;

import org.kuali.student.common.ui.client.theme.Theme;
import org.kuali.student.common.ui.client.widgets.KSErrorDialog;
import org.kuali.student.common.ui.client.widgets.progress.KSBlockingProgressIndicator;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.StyleElement;
import com.google.gwt.dom.client.StyleInjector;

public class CommonUI implements EntryPoint {
    public StyleElement commonStyle;
    public StyleElement resetStyle;
    public StyleElement initStyle;

	public void onModuleLoad() {
		KSErrorDialog.bindUncaughtExceptionHandler();
		KSBlockingProgressIndicator.initialize();
        resetStyle = StyleInjector.injectStylesheet(Theme.INSTANCE.getCommonCss().getResetCssString());
        initStyle = StyleInjector.injectStylesheet(Theme.INSTANCE.getCommonCss().getInitializeCssString());
        commonStyle = StyleInjector.injectStylesheet(Theme.INSTANCE.getCommonCss().getCssString());
	}
}
