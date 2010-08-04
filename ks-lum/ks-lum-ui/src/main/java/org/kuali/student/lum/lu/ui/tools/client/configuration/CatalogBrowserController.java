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

package org.kuali.student.lum.lu.ui.tools.client.configuration;

import org.kuali.student.common.ui.client.configurable.mvc.layouts.TabbedSectionLayout;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.DataModelDefinition;
import org.kuali.student.common.ui.client.mvc.ModelProvider;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.service.MetadataRpcService;
import org.kuali.student.common.ui.client.service.MetadataRpcServiceAsync;
import org.kuali.student.common.ui.client.widgets.containers.KSTitleContainerImpl;
import org.kuali.student.common.ui.client.widgets.progress.BlockingTask;
import org.kuali.student.common.ui.client.widgets.progress.KSBlockingProgressIndicator;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.Metadata;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;


public class CatalogBrowserController extends TabbedSectionLayout
{
	private MetadataRpcServiceAsync metadataService = GWT.create(MetadataRpcService.class);
	private final DataModel dataModel = new DataModel ();
	private boolean initialized = false;
	private Controller controller;
	private static KSTitleContainerImpl container = new KSTitleContainerImpl("Catalog Browser");
	private BlockingTask initializingTask = new BlockingTask("Loading");

	public CatalogBrowserController (Controller controller)	{
		super (CatalogBrowserController.class.getName (), container);
		this.controller = controller;
		initialize();
	}

	private void initialize ()	{
		dataModel.setRoot(new Data ());
		
		super.setDefaultModelId (CatalogBrowserConfigurer.CATALOG_BROWSER_MODEL);
		super.registerModel (CatalogBrowserConfigurer.CATALOG_BROWSER_MODEL, new ModelProvider<DataModel> () {

			@Override
			public void requestModel (final ModelRequestCallback<DataModel> callback) {
				callback.onModelReady (dataModel);
			}
		});
	}

	private void init (final Callback<Boolean> onReadyCallback)
	{

		if (initialized) {
			onReadyCallback.exec (true);
		} else	{
    		KSBlockingProgressIndicator.addTask(initializingTask);
    		
			metadataService.getMetadata ("BrowseCourseCatalog", "default", "default", new AsyncCallback<Metadata> (){

				@Override
				public void onFailure (Throwable caught)
				{
					onReadyCallback.exec (false);
		    		KSBlockingProgressIndicator.removeTask(initializingTask);
					throw new RuntimeException ("Failed to get model definition.", caught);
				}

				@Override
				public void onSuccess (Metadata result)
				{
					DataModelDefinition def = new DataModelDefinition (result);
					dataModel.setDefinition (def);
					configure (def);
					initialized = true;
					onReadyCallback.exec (true);
		    		KSBlockingProgressIndicator.removeTask(initializingTask);
				}
			});
		}
	}

	private void configure (DataModelDefinition modelDefinition)	{
		CatalogBrowserConfigurer cfg = new CatalogBrowserConfigurer ();
		cfg.setModelDefinition (modelDefinition);
		cfg.setController (controller);
		cfg.configureCatalogBrowser (this);
	}

	/**
	 * @see org.kuali.student.common.ui.client.mvc.Controller#getViewsEnum()
	 */
	@Override
	public Class<? extends Enum<?>> getViewsEnum (){
		return CatalogBrowserConfigurer.Sections.class;
	}

	@Override
	public void showDefaultView (final Callback<Boolean> onReadyCallback)
	{
		dataModel.setRoot(new Data ());
		init (new Callback<Boolean> ()	{

			@Override
			public void exec (Boolean result)
			{
				if (result)	{
					doShowDefaultView (onReadyCallback);
				} else	{
					onReadyCallback.exec (false);
				}
			}

		});
	}
	
	@Override
	public void beforeShow(Callback<Boolean> onReadyCallback) {
		showDefaultView(onReadyCallback);
	}

	private void doShowDefaultView (final Callback<Boolean> onReadyCallback) {
		super.showDefaultView (onReadyCallback);
	}

	@Override
	public Enum<?> getViewEnumValue (String enumValue){
		return null;
	}

	@Override
	public void setParentController (Controller controller)	{
		super.setParentController (controller);
	}

}
