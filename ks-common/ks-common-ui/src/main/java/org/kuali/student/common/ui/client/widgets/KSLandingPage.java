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

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class KSLandingPage extends Composite{
	private VerticalPanel layout = new VerticalPanel();
	private SimplePanel wrapper = new SimplePanel();
	private HorizontalPanel titlePanel = new HorizontalPanel();
	private HorizontalPanel titleWidgetsPanel = new HorizontalPanel();
	private HorizontalPanel innerLayout = new HorizontalPanel();
	private VerticalPanel contentLayout = new VerticalPanel();
	private VerticalPanel descLayout = new VerticalPanel();
	private SimplePanel content = new SimplePanel();
	private KSLabel titleLabel = new KSLabel();
	private HTML largeDescription = new HTML();

	public KSLandingPage(String title, String largeDescription){
		this.titleLabel.setText(title);
		this.largeDescription.setHTML(largeDescription);
		titlePanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_BOTTOM);
		titlePanel.add(titleLabel);
		titlePanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		titlePanel.add(titleWidgetsPanel);
		descLayout.add(this.largeDescription);
		contentLayout.add(descLayout);
		contentLayout.add(content);
		innerLayout.add(contentLayout);
		layout.add(titlePanel);
		layout.add(innerLayout);
		wrapper.setWidget(layout);
		this.largeDescription.addStyleName("KS-LandingPage-Description");
		this.titleLabel.addStyleName("KS-LandingPage-Title");
		this.layout.addStyleName("KS-LandingPage-Panel");
		this.innerLayout.addStyleName("KS-LandingPage-ContentPanel");
		this.titlePanel.addStyleName("KS-LandingPage-TitlePanel");
		this.wrapper.addStyleName("KS-LandingPage");

		this.initWidget(wrapper);

	}

	public KSLandingPage(String title, Image mainImage, String largeDescription){
		this.titleLabel.setText(title);
		this.largeDescription.setHTML(largeDescription);
		innerLayout.add(mainImage);

		titlePanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_BOTTOM);
		titlePanel.add(titleLabel);
		titlePanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		titlePanel.add(titleWidgetsPanel);
		descLayout.add(this.largeDescription);
		contentLayout.add(descLayout);
		contentLayout.add(content);
		innerLayout.add(contentLayout);

		layout.add(titlePanel);
		layout.add(innerLayout);
		wrapper.setWidget(layout);
		this.largeDescription.addStyleName("KS-LandingPage-Description");
		this.titleLabel.addStyleName("KS-LandingPage-Title");
		this.layout.addStyleName("KS-LandingPage-Panel");
		this.innerLayout.addStyleName("KS-LandingPage-ContentPanel");
		this.titlePanel.addStyleName("KS-LandingPage-TitlePanel");
		this.wrapper.addStyleName("KS-LandingPage");
		this.titleWidgetsPanel.addStyleName("KS-LandingPage-TitleWidgetsPanel");

		this.initWidget(wrapper);
	}

	public void setContent(Widget content){
		this.content.setWidget(content);
	}

	public void setSubDescWidget(Widget sub){
		descLayout.add(sub);
	}

	public void addTitlePanelWidget(Widget widget){
		titleWidgetsPanel.setSpacing(5);
		titleWidgetsPanel.add(widget);
	}
}
