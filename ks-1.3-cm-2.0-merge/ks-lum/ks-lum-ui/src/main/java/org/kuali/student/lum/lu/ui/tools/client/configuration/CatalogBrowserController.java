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

import org.kuali.student.r1.common.assembly.data.Data;
import org.kuali.student.r1.common.assembly.data.Metadata;
import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.application.KSAsyncCallback;
import org.kuali.student.common.ui.client.configurable.mvc.layouts.TabMenuController;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.DataModelDefinition;
import org.kuali.student.common.ui.client.mvc.ModelProvider;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.security.AuthorizationCallback;
import org.kuali.student.common.ui.client.security.RequiresAuthorization;
import org.kuali.student.common.ui.client.service.MetadataRpcService;
import org.kuali.student.common.ui.client.service.MetadataRpcServiceAsync;
import org.kuali.student.common.ui.client.util.WindowTitleUtils;
import org.kuali.student.common.ui.client.widgets.containers.KSTitleContainerImpl;
import org.kuali.student.common.ui.client.widgets.progress.BlockingTask;
import org.kuali.student.common.ui.client.widgets.progress.KSBlockingProgressIndicator;
import org.kuali.student.lum.common.client.lu.LUUIPermissions;

import com.google.gwt.core.client.GWT;


public class CatalogBrowserController extends TabMenuController implements RequiresAuthorization
{
	private MetadataRpcServiceAsync metadataService = GWT.create(MetadataRpcService.class);
	private final DataModel dataModel = new DataModel ();
	private boolean initialized = false;
	private Controller controller;
	private static KSTitleContainerImpl container = new KSTitleContainerImpl("Catalog Browser");
	private BlockingTask initializingTask = new BlockingTask("Loading");
	
	//enum is necessary for the page to be added to breadcrumbs
	public enum CatalogBrowserViews {
		COURSE_CATALOG
    };

	public CatalogBrowserController (Controller controller)	{
		super(CatalogBrowserController.class.getName());
		this.controller = controller;
		//sets the name of the page to be used in breadcrumbs
		super.setName("Course Catalog");
		//sets enum
		setViewEnum(CatalogBrowserViews.COURSE_CATALOG);
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

 private static final String METADATA_OBJECT_KEY_BROWSE = "browse";

	private void init (final Callback<Boolean> onReadyCallback)
	{

		if (initialized) {
			onReadyCallback.exec (true);
		} else	{
    		KSBlockingProgressIndicator.addTask(initializingTask);
    		
			metadataService.getMetadata (METADATA_OBJECT_KEY_BROWSE, null, null, new KSAsyncCallback<Metadata> (){

				@Override
				public void handleFailure (Throwable caught)
				{
					onReadyCallback.exec (false);
		    		KSBlockingProgressIndicator.removeTask(initializingTask);
					throw new RuntimeException ("Failed to get model definition for " + METADATA_OBJECT_KEY_BROWSE, caught);
				}

				@Override
				public void onSuccess (Metadata result)
				{
     if (result == null)
     {
					 onReadyCallback.exec (false);
		    KSBlockingProgressIndicator.removeTask(initializingTask);
				 	throw new RuntimeException ("Got null metdata for " + METADATA_OBJECT_KEY_BROWSE);
     }
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
		CatalogBrowserConfigurer cfg = GWT.create(CatalogBrowserConfigurer.class);
		cfg.setModelDefinition (modelDefinition);
		cfg.setController (controller);
		cfg.configureCatalogBrowser (this);
	}
	
	@Override
	public void beforeShow(final Callback<Boolean> onReadyCallback) {
		WindowTitleUtils.setContextTitle(name);
		dataModel.setRoot(new Data ());
		init (new Callback<Boolean> ()	{

			@Override
			public void exec (Boolean result)
			{
				if (result)	{
					showDefaultView (onReadyCallback);
				} else	{
					onReadyCallback.exec (false);
				}
			}
			
		});
	}

	@Override
	public Enum<?> getViewEnumValue (String enumValue){
		return null;
	}

	@Override
	public void setParentController (Controller controller)	{
		super.setParentController (controller);
	}
	
	@Override
    public boolean isAuthorizationRequired() {
        return true;
    }

    @Override
    public void setAuthorizationRequired(boolean required) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public void checkAuthorization(final AuthorizationCallback authCallback) {
        Application.getApplicationContext().getSecurityContext().checkScreenPermission(LUUIPermissions.USE_BROWSE_CATALOG_SCREEN, new Callback<Boolean>() {
            @Override
            public void exec(Boolean result) {

                final boolean isAuthorized = result;
            
                if(isAuthorized){
                    authCallback.isAuthorized();
                }
                else
                    authCallback.isNotAuthorized("User is not authorized: " + LUUIPermissions.USE_BROWSE_CATALOG_SCREEN);
            }   
        });
    }

}
