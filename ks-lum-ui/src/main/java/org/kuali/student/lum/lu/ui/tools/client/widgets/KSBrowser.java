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

package org.kuali.student.lum.lu.ui.tools.client.widgets;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.application.ViewContext;
import org.kuali.student.common.ui.client.configurable.mvc.WidgetConfigInfo;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.widgets.layout.VerticalFlowPanel;
import org.kuali.student.common.ui.shared.IdAttributes.IdType;
import org.kuali.student.r1.common.assembly.data.LookupMetadata;
import org.kuali.student.r1.common.assembly.data.LookupParamMetadata;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;

public class KSBrowser extends Composite {

	private List<BrowsePanel> browsePanels;
	private LookupMetadata fieldLookup;
	private List<LookupMetadata> cascadingLookups;
	private List<LookupParamMetadata> cascadingChildParameters;
	private WidgetConfigInfo config;
	private VerticalFlowPanel layout = new VerticalFlowPanel ();

	public KSBrowser (LookupMetadata fieldLookup, Controller controller)
	{
		this.fieldLookup = fieldLookup;

		if (config == null)
		{
			config = new WidgetConfigInfo ();
		}

		browsePanels = new ArrayList<BrowsePanel> ();
		cascadingLookups = new ArrayList<LookupMetadata> ();
		cascadingChildParameters = new ArrayList<LookupParamMetadata> ();
		cascadingLookups.add (this.fieldLookup);
		this.addChildLookups (this.fieldLookup);
		Collections.reverse (cascadingChildParameters);
		Collections.reverse (cascadingLookups);

		for (LookupMetadata lookupMetadata : cascadingLookups)
		{
			BrowsePanel browsePanel = new BrowsePanel (lookupMetadata);
			browsePanels.add (browsePanel);
			layout.add (browsePanel);
		}
		for (int i = 0; i < browsePanels.size () - 1; i ++)
		{
			BrowsePanel currentBP = browsePanels.get (i);
			LookupParamMetadata nextParamMetadata = this.cascadingChildParameters.get (i);
			BrowsePanel nextBP = browsePanels.get (i + 1);
			currentBP.setOnSelectectedCallback (new ExecuteNextSearchCallback (nextBP, nextParamMetadata));
		}
		browsePanels.get (browsePanels.size () - 1).setOnSelectectedCallback (new ViewCourseCallback (controller));

		this.initWidget (layout);
		browsePanels.get (0).executeSearch (new Callback<Boolean>(){
			public void exec(Boolean result) {
			}
		});
	}

	private class ExecuteNextSearchCallback implements BrowsePanel.OnSelectedCallback {

		private BrowsePanel nextBrowsePanel;
		private LookupParamMetadata nextParamMetadata;

		public ExecuteNextSearchCallback (BrowsePanel nextBrowsePanel,
				LookupParamMetadata nextParamMetadata) {
			this.nextBrowsePanel = nextBrowsePanel;
			this.nextParamMetadata = nextParamMetadata;
		}

		@Override
		public void selected (List<String> selectedIds)	{
			if (selectedIds.size () == 0) {
				Window.alert ("Please select a row before clicking");
				return;
			}
			Map<String, Object> parameters = new LinkedHashMap<String, Object> ();
			parameters.put (nextParamMetadata.getKey (), selectedIds.get (0));
			nextBrowsePanel.setParameters (parameters);
			nextBrowsePanel.executeSearch (new Callback<Boolean>(){
				public void exec(Boolean result) {
				}
			});
		}
	}

	private class ViewCourseCallback implements BrowsePanel.OnSelectedCallback {

		private Controller controller;

		public ViewCourseCallback (Controller controller)
		{
			this.controller = controller;
		}

		@Override
		public void selected (List<String> selectedIds)	{
			if (selectedIds.size () == 0) {
				Window.alert ("Please select a row before clicking");
				return;
			}
			ViewContext viewContext = new ViewContext ();
			viewContext.setId (selectedIds.get (0));
			viewContext.setIdType (IdType.OBJECT_ID);
			Application.navigate("/HOME/CURRICULUM_HOME/VIEW_COURSE", viewContext);
		}
	}

	private void addChildLookups (LookupMetadata current) {
		for (LookupParamMetadata param : current.getParams ())	{
			if (param.getChildLookup () != null) {
				cascadingChildParameters.add (param);
				cascadingLookups.add (param.getChildLookup ());
				addChildLookups (param.getChildLookup ());
			}
		}
	}

}
