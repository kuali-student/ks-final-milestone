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
import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.configurable.mvc.WidgetConfigInfo;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.HasDataValue;
import org.kuali.student.common.ui.client.mvc.HasFocusLostCallbacks;
import org.kuali.student.common.ui.client.mvc.TranslatableValueWidget;
import org.kuali.student.common.ui.client.widgets.KSErrorDialog;
import org.kuali.student.core.assembly.data.Data.Value;
import org.kuali.student.core.assembly.data.LookupMetadata;
import org.kuali.student.core.assembly.data.LookupParamMetadata;

import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import java.util.Collections;
import java.util.LinkedHashMap;
import org.kuali.student.common.ui.client.application.ViewContext;
import org.kuali.student.common.ui.client.application.ViewContext.IdType;
import org.kuali.student.common.ui.client.event.ChangeViewActionEvent;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.widgets.layout.VerticalFlowPanel;
import org.kuali.student.lum.lu.ui.home.client.view.FindPanel;
import org.kuali.student.lum.lu.ui.main.client.controller.LUMApplicationManager.LUMViews;

public class KSBrowser extends Composite implements HasFocusLostCallbacks,
                                                    HasValueChangeHandlers<String>,
                                                    HasDataValue,
                                                    TranslatableValueWidget
{

 private List<BrowsePanel> browsePanels;
 private LookupMetadata fieldLookup;
 private List<LookupMetadata> cascadingLookups;
 private List<LookupParamMetadata> cascadingChildParameters;
 private WidgetConfigInfo config;
 private VerticalFlowPanel layout = new VerticalFlowPanel ();
 private Controller controller;

 public KSBrowser (LookupMetadata fieldLookup, Controller controller)
 {
  this.fieldLookup = fieldLookup;
  this.controller = controller;
  if (this.fieldLookup == null)
  {
   KSErrorDialog errorDialog = new KSErrorDialog ();
   errorDialog.show (new Throwable ("Invalid lookup configuration: missing initial lookup metadata."));
   return;
  }

  if (config == null)
  {
   config = new WidgetConfigInfo ();
  }

  browsePanels = new ArrayList ();
  cascadingLookups = new ArrayList ();
  cascadingChildParameters = new ArrayList ();
  cascadingLookups.add (this.fieldLookup);
  this.addChildLookups (this.fieldLookup);
//  Window.alert (cascadingLookups.size () + " cascading lookups found");
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
  browsePanels.get (0).executeSearch ();
 }

 private class ExecuteNextSearchCallback implements
  BrowsePanel.OnSelectedCallback
 {

  private BrowsePanel nextBrowsePanel;
  private LookupParamMetadata nextParamMetadata;

  public ExecuteNextSearchCallback (BrowsePanel nextBrowsePanel,
                                    LookupParamMetadata nextParamMetadata)
  {
   this.nextBrowsePanel = nextBrowsePanel;
   this.nextParamMetadata = nextParamMetadata;
  }

  @Override
  public void selected (List<String> selectedIds)
  {
   if (selectedIds.size () == 0)
   {
    Window.alert ("Please select a row before clicking");
    return;
   }
   Map<String, Object> parameters = new LinkedHashMap ();
   parameters.put (nextParamMetadata.getKey (), selectedIds.get (0));
   nextBrowsePanel.setParameters (parameters);
   nextBrowsePanel.executeSearch ();
  }

 }

 private class ViewCourseCallback implements BrowsePanel.OnSelectedCallback
 {

  private Controller controller;

  public ViewCourseCallback (Controller controller)
  {
   this.controller = controller;
  }

  @Override
  public void selected (List<String> selectedIds)
  {
   if (selectedIds.size () == 0)
   {
    Window.alert ("Please select a row before clicking");
    return;
   }
   ViewContext viewContext = new ViewContext ();
   viewContext.setId (selectedIds.get (0));
   viewContext.setIdType (IdType.OBJECT_ID);
   controller.fireApplicationEvent (new ChangeViewActionEvent<LUMViews> (LUMViews.VIEW_COURSE, viewContext));
   // TODO: worry about hiding this page?
  }

 }

 private void addChildLookups (LookupMetadata current)
 {
//  Window.alert ("Looking for children of " + current.getId ());
  for (LookupParamMetadata param : current.getParams ())
  {
   if (param.getChildLookup () != null)
   {
//    Window.alert (param.getKey () + " childlookup " + param.getChildLookup ());
    cascadingChildParameters.add (param);
    cascadingLookups.add (param.getChildLookup ());
    addChildLookups (param.getChildLookup ());
   }
  }
 }

 @Override
 public void addFocusLostCallback (Callback<Boolean> callback)
 {
  return;
 }

 @Override
 public HandlerRegistration addValueChangeHandler (
  ValueChangeHandler<String> handler)
 {
  return null;
 }

 @Override
 public void addValueChangeCallback (Callback<Value> callback)
 {
  return;
 }

 @Override
 public Value getValue ()
 {
  return null;
 }

 @Override
 public void setValue (Value value)
 {
  return;
 }

 @Override
 public void setValue (String id, String translation)
 {
  return;
 }

 @Override
 public void setValue (Map<String, String> translations)
 {
  return;
 }

}
