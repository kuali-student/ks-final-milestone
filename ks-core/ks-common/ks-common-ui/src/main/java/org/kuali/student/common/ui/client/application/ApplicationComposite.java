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

package org.kuali.student.common.ui.client.application;

import org.kuali.student.common.ui.client.configurable.mvc.LayoutController;
import org.kuali.student.common.ui.client.widgets.containers.KSWrapper;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Application wrapping layout.  Deprecated for all future development.  Use an implementation of
 * LayoutController instead.
 * @author Kuali Student Team
 * @see LayoutController
 */
public class ApplicationComposite  extends Composite {
	private final Panel body = new VerticalPanel();
	//private final Header header = GWT.create(Header.class);
	private final KSWrapper headerFooterContainer = new KSWrapper();
	private final SimplePanel content = new SimplePanel();
	
	public ApplicationComposite() {
		super.initWidget(body);
		//body.add(header);
		headerFooterContainer.setContent(content);
		body.add(headerFooterContainer);
		//body.add(content);
		super.addStyleName("KS-Application");
		content.addStyleName("KS-Application-Content");
	}
	
	public void setContent(Widget w) {
		content.setWidget(w);
	}
	
	public Widget getContent() {
		return content.getWidget();
	}
	 
}
