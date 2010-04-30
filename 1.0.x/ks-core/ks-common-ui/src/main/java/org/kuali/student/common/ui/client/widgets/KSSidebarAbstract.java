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

import org.kuali.student.common.ui.client.widgets.KSFloatPanel.FloatLocation;

import com.google.gwt.user.client.ui.Widget;



public abstract class KSSidebarAbstract{
	protected abstract void init();
	
	protected abstract void init(FloatLocation location);
	
	public abstract void show();
	
	public abstract void hide();
	
	public abstract void showContent(Widget content, String title);
	
	public abstract  void hideContent();

	public abstract void addTab(Widget content, String name);
	
	public abstract void removeTab(String name);
}
