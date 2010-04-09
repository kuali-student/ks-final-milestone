/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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
import org.kuali.student.common.ui.client.event.SaveActionEvent;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.DataModelDefinition;
import org.kuali.student.common.ui.client.mvc.ModelProvider;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.mvc.WorkQueue;
import org.kuali.student.common.ui.client.mvc.WorkQueue.WorkItem;
import org.kuali.student.common.ui.client.widgets.KSLightBox;
import org.kuali.student.common.ui.client.widgets.KSProgressIndicator;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.Metadata;

import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.BrowseCourseCatalogMetadata;

public class CatalogBrowserController extends TabbedSectionLayout
{

 private final DataModel dataModel = new DataModel ();
 private WorkQueue modelRequestQueue;
 private boolean initialized = false;
 final KSLightBox progressWindow = new KSLightBox ();
 private Controller controller;


 public CatalogBrowserController (Controller controller)
 {
  super (CatalogBrowserController.class.getName ());
  this.controller = controller;
//  Window.alert ("about to initialize controller");
  initialize ();
 }

 private void initialize ()
 {
  super.setDefaultModelId (CatalogBrowserConfigurer.CATALOG_BROWSER_MODEL);
  super.registerModel (CatalogBrowserConfigurer.CATALOG_BROWSER_MODEL,
                       new ModelProvider<DataModel> ()
  {

   @Override
   public void requestModel (final ModelRequestCallback<DataModel> callback)
   {
    if (modelRequestQueue == null)
    {
     modelRequestQueue = new WorkQueue ();
    }

    WorkItem workItem = new WorkItem ()
    {

     @Override
     public void exec (Callback<Boolean> workCompleteCallback)
     {
      dataModel.setRoot (new Data ());
      callback.onModelReady (dataModel);
      workCompleteCallback.exec (true);

     }

    };
    modelRequestQueue.submit (workItem);
   }

  });

 }

 private void init (final Callback<Boolean> onReadyCallback)
 {
  KSProgressIndicator progressInd = new KSProgressIndicator ();
  progressInd.setText ("Loading");
  progressInd.show ();
  progressWindow.setWidget (progressInd);

  if (initialized)
  {
   onReadyCallback.exec (true);
  }
  else
  {
   progressWindow.show ();

   // TODO: replace this with some sort of asynch call like below
   Metadata result =
    new BrowseCourseCatalogMetadata ().getMetadata ("", "");
   DataModelDefinition def = new DataModelDefinition (result);
   dataModel.setDefinition (def);
   init (def);
   initialized = true;
   onReadyCallback.exec (true);
   progressWindow.hide ();

//   cluSetManagementRpcServiceAsync.getMetadata ("", "", new AsyncCallback<Metadata> ()
//   {
//
//    @Override
//    public void onFailure (Throwable caught)
//    {
//     onReadyCallback.exec (false);
//     progressWindow.hide ();
//     throw new RuntimeException ("Failed to get model definition.", caught);
//    }
//
//    @Override
//    public void onSuccess (Metadata result)
//    {
//     DataModelDefinition def = new DataModelDefinition (result);
//     dataModel.setDefinition (def);
//     init (def);
//     initialized = true;
//     onReadyCallback.exec (true);
//     progressWindow.hide ();
//    }
//
//   });
  }
 }

 private void init (DataModelDefinition modelDefinition)
 {

  CatalogBrowserConfigurer cfg = new CatalogBrowserConfigurer ();
  cfg.setModelDefinition (modelDefinition);
  cfg.setController (controller);
  cfg.configureCatalogBrowser (this);

  if ( ! initialized)
  {
  }

  initialized = true;
 }

 /**
  * @see org.kuali.student.common.ui.client.mvc.Controller#getViewsEnum()
  */
 @Override
 public Class<? extends Enum<?>> getViewsEnum ()
 {
  return CatalogBrowserConfigurer.Sections.class;
 }

 @SuppressWarnings("unchecked")
 @Override
 public void requestModel (Class modelType, final ModelRequestCallback callback)
 {
 }

 public void doSaveAction (final SaveActionEvent saveActionEvent)
 {
 }

 @Override
 public void showDefaultView (final Callback<Boolean> onReadyCallback)
 {
  init (new Callback<Boolean> ()
  {

   @Override
   public void exec (Boolean result)
   {
    if (result)
    {
     doShowDefaultView (onReadyCallback);
    }
    else
    {
     onReadyCallback.exec (false);
    }
   }

  });
 }

 private void doShowDefaultView (final Callback<Boolean> onReadyCallback)
 {
  super.showDefaultView (onReadyCallback);
 }

 @Override
 public Enum<?> getViewEnumValue (String enumValue)
 {
  // TODO Auto-generated method stub
  return null;
 }

 @Override
 public void setParentController (Controller controller)
 {
  // TODO Auto-generated method stub
  super.setParentController (controller);
 }

}
