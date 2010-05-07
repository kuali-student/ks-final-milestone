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

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.layouts.ConfigurableLayout;
import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.common.ui.client.configurable.mvc.sections.VerticalSection;
import org.kuali.student.common.ui.client.configurable.mvc.views.SectionView;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.DataModelDefinition;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.assembly.data.QueryPath;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.BrowseCourseCatalogBySchoolOrCollegeConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.BrowseCourseCatalogConstants;
import org.kuali.student.lum.lu.ui.course.client.configuration.LUConstants;
import org.kuali.student.lum.lu.ui.tools.client.widgets.KSBrowser;

import com.google.gwt.user.client.ui.Widget;

public class CatalogBrowserConfigurer
 implements BrowseCourseCatalogBySchoolOrCollegeConstants,
            BrowseCourseCatalogConstants
{

 private boolean WITH_DIVIDER = true;
 private boolean NO_DIVIDER = false;
 public static final String CATALOG_BROWSER_MODEL = "CatalogBrowserModel";
 private DataModelDefinition modelDefinition;

 public enum Sections
 {

  BROWSE_BY_SUBJECT_AREA,
  BROWSE_BY_SCHOOL;
 }

 public void setModelDefinition (DataModelDefinition modelDefinition)
 {
  this.modelDefinition = modelDefinition;
 }

 private Controller controller;

 public Controller getController ()
 {
  return controller;
 }

 public void setController (Controller controller)
 {
  this.controller = controller;
 }


 public void configureCatalogBrowser (ConfigurableLayout layout)
 {
  
// addStartSection(layout);
  layout.addSection (new String[]
   {
    "Browse by Subject Area"
//    getLabel (CatalogBrowserConstants.BROWSE_BY_SUBJECT_AREA_LABEL_KEY)
   }, createBrowseBySubjectAreaSection ());
  layout.addSection (new String[]
   {
    "Browse by School"
//    getLabel (CatalogBrowserConstants.BROWSE_BY_SCHOOL_LABEL_KEY)
   }, createBrowseBySchoolSection ());
 }

 private SectionView createBrowseBySubjectAreaSection ()
 {
  CatalogBrowserNestedSectionView nestedSectionView =
      new CatalogBrowserNestedSectionView (Sections.BROWSE_BY_SUBJECT_AREA,
                          getLabel (CatalogBrowserConstants.BROWSE_BY_SUBJECT_AREA),
                          CATALOG_BROWSER_MODEL);
  nestedSectionView.addStyleName (LUConstants.STYLE_SECTION);
//  VerticalSection verticalSection =
//   initSection (getH3Title (CatalogBrowserConstants.BROWSE_BY_SUBJECT_AREA_INFO), WITH_DIVIDER);
//  nestedSectionView.addSection (verticalSection);
  String fieldKey = BY_SUBJECT_AREA + "/" + COURSE_ID;
  addField (nestedSectionView, fieldKey, "", configureKSBrowser (fieldKey));
  return nestedSectionView;
 }

 private SectionView createBrowseBySchoolSection ()
 {
  CatalogBrowserNestedSectionView nestedSectionView =
   new CatalogBrowserNestedSectionView (Sections.BROWSE_BY_SCHOOL,
                          getLabel (CatalogBrowserConstants.BROWSE_BY_SCHOOL),
                          CATALOG_BROWSER_MODEL);
  nestedSectionView.addStyleName (LUConstants.STYLE_SECTION);
//  nestedSectionView.setSectionTitle (getH1Title (labelKey));
//  VerticalSection verticalSection =
//   initSection (getH3Title (CatalogBrowserConstants.BROWSE_BY_SCHOOL_INFO), WITH_DIVIDER);
//  nestedSectionView.addSection (verticalSection);
  String fieldKey = BY_SCHOOL_OR_COLLEGE + "/" + COURSE_ID;
  addField (nestedSectionView, fieldKey, "", configureKSBrowser (fieldKey));
  return nestedSectionView;
 }

 private KSBrowser configureKSBrowser (String fieldKey)
 {
  QueryPath path = QueryPath.concat (null, fieldKey);
  Metadata metaData = modelDefinition.getMetadata (path);
  KSBrowser browser = new KSBrowser (metaData.getInitialLookup (), controller);
  return browser;
 }

 private static VerticalSection initSection (SectionTitle title,
                                             boolean withDivider)
 {
  VerticalSection section = new VerticalSection ();
  if (title != null)
  {
   section.setSectionTitle (title);
  }
  section.addStyleName (LUConstants.STYLE_SECTION);
  if (withDivider)
  {
   section.addStyleName (LUConstants.STYLE_SECTION_DIVIDER);
  }
  return section;
 }

 private String getLabel (String labelKey)
 {
  // TODO make the group, type and state configurable when framework is ready
  // tried created a new message group clusetmanagement but the entries are not read for some reason
  return Application.getApplicationContext ().getUILabel ("course", "course", "draft", labelKey);
 }

 private SectionTitle getH1Title (String labelKey)
 {
  return SectionTitle.generateH1Title (getLabel (labelKey));
 }

 private SectionTitle getH2Title (String labelKey)
 {
  return SectionTitle.generateH2Title (getLabel (labelKey));
 }

 private SectionTitle getH3Title (String labelKey)
 {
  return SectionTitle.generateH3Title (getLabel (labelKey));
 }

 private SectionTitle getH4Title (String labelKey)
 {
  return SectionTitle.generateH4Title (getLabel (labelKey));
 }

 private SectionTitle getH5Title (String labelKey)
 {
  return SectionTitle.generateH5Title (getLabel (labelKey));
 }

 // TODO - when DOL is pushed farther down into LOBuilder,
 // revert these 5 methods to returning void again.
 private FieldDescriptor addField (Section section, String fieldKey)
 {
  return addField (section, fieldKey, null, null, null);
 }

 private FieldDescriptor addField (Section section, String fieldKey,
                                   String fieldLabel)
 {
  return addField (section, fieldKey, fieldLabel, null, null);
 }

 private FieldDescriptor addField (Section section, String fieldKey,
                                   String fieldLabel, Widget widget)
 {
  return addField (section, fieldKey, fieldLabel, widget, null);
 }

 private FieldDescriptor addField (Section section, String fieldKey,
                                   String fieldLabel, String parentPath)
 {
  return addField (section, fieldKey, fieldLabel, null, parentPath);
 }

 private FieldDescriptor addField (Section section, String fieldKey,
                                   String fieldLabel, Widget widget,
                                   String parentPath)
 {
  QueryPath path = QueryPath.concat (parentPath, fieldKey);
  Metadata meta = modelDefinition.getMetadata (path);

  FieldDescriptor fd = new FieldDescriptor (path.toString (), fieldLabel, meta);
  if (widget != null)
  {
   fd.setFieldWidget (widget);
  }
  section.addField (fd);
  return fd;
 }

}
