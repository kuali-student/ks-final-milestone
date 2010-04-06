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

import java.util.List;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.layouts.ConfigurableLayout;
import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.common.ui.client.configurable.mvc.sections.VerticalSection;
import org.kuali.student.common.ui.client.configurable.mvc.views.SectionView;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.mvc.DataModelDefinition;
import org.kuali.student.core.assembly.data.LookupMetadata;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.assembly.data.QueryPath;
import org.kuali.student.lum.lu.ui.course.client.configuration.LUConstants;
import org.kuali.student.lum.lu.ui.course.client.configuration.course.CourseConfigurer.CourseSections;

import com.google.gwt.user.client.ui.Widget;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.BrowseCourseCatalogBySchoolOrCollegeConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.BrowseCourseCatalogConstants;
import org.kuali.student.lum.lu.ui.tools.client.widgets.Browser;

public class CatalogBrowserConfigurer
 implements BrowseCourseCatalogBySchoolOrCollegeConstants,
 BrowseCourseCatalogConstants
{

// public static String SECTION_CREATE_CLU_SET = "New CLU Set";
 // Message keys for top-level section label lookup
 public static final String BY_SUBJECT_AREA_LABEL_KEY = "cluSetInformation";
// public static final String NEW_CLU_SET = "Create New Clu Set";
// public static final String NEW_CLU_SET_INFO = "CLU set Information";
 public static final String BROWSE_BY_SUBJECT_AREAS = "Browse By Subject Areas";
 public static final String SUBJECT_AREAS_LIST = "Subject Areas List";
 private boolean WITH_DIVIDER = true;
 private boolean NO_DIVIDER = false;
 public static final String CLU_PROPOSAL_MODEL = "cluProposalModel";
 private DataModelDefinition modelDefinition;

 public void setModelDefinition (DataModelDefinition modelDefinition)
 {
  this.modelDefinition = modelDefinition;
 }

 public void configureCatalogBrowser (ConfigurableLayout layout)
 {
  layout.addSection (new String[]
   {
    "Browse by Subject Area",
    getLabel (BY_SUBJECT_AREA_LABEL_KEY)
   }, createBrowseSubjectAreaSection ());
 }

 private SectionView createBrowseSubjectAreaSection ()
 {
  VerticalSectionView section =
   initSectionView (CourseSections.LEARNING_OBJECTIVES, BROWSE_BY_SUBJECT_AREAS);
  VerticalSection oversight =
   initSection (getH3Title (SUBJECT_AREAS_LIST), WITH_DIVIDER);
  String fieldKey = BY_SUBJECT_AREA + "/" + COURSE_ID;
  addField (oversight, fieldKey, "", configureBySubjectAreaSearch (fieldKey));
  section.addSection (oversight);
  return section;
 }

 private VerticalSectionView initSectionView (Enum<?> viewEnum, String labelKey)
 {
  VerticalSectionView section =
   new VerticalSectionView (viewEnum, getLabel (labelKey), CLU_PROPOSAL_MODEL);
  section.addStyleName (LUConstants.STYLE_SECTION);
  section.setSectionTitle (getH1Title (labelKey));

  return section;
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
  return Application.getApplicationContext ().getUILabel ("", "", "", labelKey);
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

 private CatalogBrowser configureBySubjectAreaSearch (String fieldKey)
 {
  QueryPath path = QueryPath.concat (null, fieldKey);
  Metadata metaData = modelDefinition.getMetadata (path);
  CatalogBrowser browser = new CatalogBrowser (metaData.getInitialLookup (), metaData.
   getAdditionalLookups ());
  return browser;
 }

 // picker Classes
 public static class CatalogBrowser extends Browser
 {

  private String name;

  public CatalogBrowser (LookupMetadata inLookupMetadata,
                         List<LookupMetadata> additionalLookupMetadata)
  {
   super (inLookupMetadata, additionalLookupMetadata);
  }

  public String getName ()
  {
   return name;
  }

  public void setName (String name)
  {
   this.name = name;
  }

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
