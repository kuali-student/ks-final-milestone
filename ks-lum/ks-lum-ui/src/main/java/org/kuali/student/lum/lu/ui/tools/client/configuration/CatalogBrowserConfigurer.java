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

import com.google.gwt.user.client.ui.Widget;
import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.common.ui.client.configurable.mvc.views.SectionView;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.DataModelDefinition;
import org.kuali.student.common.ui.client.widgets.KSErrorDialog;
import org.kuali.student.common.ui.client.widgets.field.layout.element.MessageKeyInfo;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.assembly.data.QueryPath;
import org.kuali.student.lum.lu.ui.tools.client.widgets.KSBrowser;


public class CatalogBrowserConfigurer {

	public static final String CATALOG_BROWSER_MODEL = "CatalogBrowserModel";
	private DataModelDefinition modelDefinition;

	public enum Sections {
		BROWSE_BY_SUBJECT_AREA,
		BROWSE_BY_SCHOOL;
	}

	public void setModelDefinition (DataModelDefinition modelDefinition) {
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


	public void configureCatalogBrowser (CatalogBrowserController layout)
	{
		layout.setContentTitle("Browse Course Catalog");
		layout.addTab(createBrowseBySubjectAreaSection (), "Browse By Subject Area");
		layout.addTab(createBrowseBySchoolSection (), "Browse By School");
		layout.setDefaultView(Sections.BROWSE_BY_SUBJECT_AREA);
	}

	private SectionView createBrowseBySubjectAreaSection ()
	{
		VerticalSectionView nestedSectionView =
			new VerticalSectionView (Sections.BROWSE_BY_SUBJECT_AREA,
					getLabel (CatalogBrowserConstants.BROWSE_BY_SUBJECT_AREA),
					CATALOG_BROWSER_MODEL);
		String fieldKey = CatalogBrowserConstants.FULLY_QUALIFIED_BY_SUBJECT_AREA;
		addField (nestedSectionView, fieldKey, null, configureKSBrowser (fieldKey));
		return nestedSectionView;
	}

	private SectionView createBrowseBySchoolSection ()
	{
		VerticalSectionView nestedSectionView =
			new VerticalSectionView (Sections.BROWSE_BY_SCHOOL,
					getLabel (CatalogBrowserConstants.BROWSE_BY_SCHOOL),
					CATALOG_BROWSER_MODEL);
		String fieldKey =  CatalogBrowserConstants.FULLY_QUALIFIED_BY_SCHOOL_OR_COLLEGE;
		addField (nestedSectionView, fieldKey, null, configureKSBrowser (fieldKey));
		return nestedSectionView;
	}

 private String formatMetadata (Metadata md, String fieldKey) {
  String msg = "metadata for fieldKey=" + fieldKey
//    + "\n Name=" + md.getName ()
    + "\n LabelKey=" + md.getLabelKey ()
    + "\n defaultValuePath=" + md.getDefaultValuePath ()
    + "\n LookupContextPath="  + md.getLookupContextPath ()
//    + "\n maskForatter="  + md.getMaskFormatter ()
//    + "\n partialMaskFormatter="  + md.getPartialMaskFormatter ()
    + "\n dataType="  + md.getDataType ()
    + "\n defaultValue="  + md.getDefaultValue ()
    + "\n WriteAccess="  + md.getWriteAccess ()
    + "\n initialLookup="  + md.getInitialLookup ()
    + "\n additionalLookups="  + md.getAdditionalLookups ()
    ;
  if (md.getProperties () != null) {
   msg += "\n It has " + md.getProperties ().size () + " properties: \n";
   for (String fk : md.getProperties ().keySet ()) {
    msg += "\n" + formatMetadata (md.getProperties ().get (fk), fk);
   }
  }
  return msg;
 }

	private KSBrowser configureKSBrowser (String fieldKey)
	{
		QueryPath path = QueryPath.concat (null, fieldKey);
		Metadata md = modelDefinition.getMetadata (path);
  if (md == null) {
			KSErrorDialog.show (new NullPointerException
     ("Invalid lookup configuration: missing field in metadata."
     + formatMetadata (modelDefinition.getMetadata (), fieldKey)));
   return null;
  }
  if (md.getInitialLookup () == null) {
			KSErrorDialog.show (new NullPointerException
     ("Invalid lookup configuration: missing initial lookup in metadata." 
     + formatMetadata (modelDefinition.getMetadata (), fieldKey)));
			return null;
		}
		KSBrowser browser = new KSBrowser (md.getInitialLookup (), controller);
		return browser;
	}

	private String getLabel (String labelKey)
	{
		// TODO make the group, type and state configurable when framework is ready
		// tried created a new message group clusetmanagement but the entries are not read for some reason
		return Application.getApplicationContext ().getUILabel ("course", "course", "draft", labelKey);
	}

	protected FieldDescriptor addField(Section section, String fieldKey, MessageKeyInfo messageKey, Widget widget) {
		return addField(section, fieldKey, messageKey, widget, null);
	}
	
	protected FieldDescriptor addField(Section section, String fieldKey, MessageKeyInfo messageKey, Widget widget, String parentPath) {
		QueryPath path = QueryPath.concat(parentPath, fieldKey);
		Metadata meta = modelDefinition.getMetadata(path);

		FieldDescriptor fd = new FieldDescriptor(path.toString(), messageKey, meta);
		if (widget != null) {
			fd.setFieldWidget(widget);
		}
		section.addField(fd);
		return fd;
	}

}
