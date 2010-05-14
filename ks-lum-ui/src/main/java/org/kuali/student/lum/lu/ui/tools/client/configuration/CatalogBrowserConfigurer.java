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
import org.kuali.student.common.ui.client.configurable.mvc.layouts.ConfigurableLayout;
import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.common.ui.client.configurable.mvc.views.SectionView;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.DataModelDefinition;
import org.kuali.student.common.ui.client.widgets.field.layout.element.MessageKeyInfo;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.assembly.data.QueryPath;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.BrowseCourseCatalogBySchoolOrCollegeConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.BrowseCourseCatalogConstants;
import org.kuali.student.lum.lu.ui.tools.client.widgets.KSBrowser;

import com.google.gwt.user.client.ui.Widget;

public class CatalogBrowserConfigurer implements BrowseCourseCatalogBySchoolOrCollegeConstants, BrowseCourseCatalogConstants {

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
		VerticalSectionView nestedSectionView =
			new VerticalSectionView (Sections.BROWSE_BY_SUBJECT_AREA,
					getLabel (CatalogBrowserConstants.BROWSE_BY_SUBJECT_AREA),
					CATALOG_BROWSER_MODEL);
		//nestedSectionView.addStyleName (LUConstants.STYLE_SECTION);
		//  VerticalSection verticalSection =
		//   initSection (getH3Title (CatalogBrowserConstants.BROWSE_BY_SUBJECT_AREA_INFO), WITH_DIVIDER);
		//  nestedSectionView.addSection (verticalSection);
		String fieldKey = BY_SUBJECT_AREA + "/" + COURSE_ID;
		addField (nestedSectionView, fieldKey, null, configureKSBrowser (fieldKey));
		return nestedSectionView;
	}

	private SectionView createBrowseBySchoolSection ()
	{
		VerticalSectionView nestedSectionView =
			new VerticalSectionView (Sections.BROWSE_BY_SCHOOL,
					getLabel (CatalogBrowserConstants.BROWSE_BY_SCHOOL),
					CATALOG_BROWSER_MODEL);
		//nestedSectionView.addStyleName (LUConstants.STYLE_SECTION);
		//  nestedSectionView.setSectionTitle (getH1Title (labelKey));
		//  VerticalSection verticalSection =
		//   initSection (getH3Title (CatalogBrowserConstants.BROWSE_BY_SCHOOL_INFO), WITH_DIVIDER);
		//  nestedSectionView.addSection (verticalSection);
		String fieldKey = BY_SCHOOL_OR_COLLEGE + "/" + COURSE_ID;
		addField (nestedSectionView, fieldKey, null, configureKSBrowser (fieldKey));
		return nestedSectionView;
	}

	private KSBrowser configureKSBrowser (String fieldKey)
	{
		QueryPath path = QueryPath.concat (null, fieldKey);
		Metadata metaData = modelDefinition.getMetadata (path);
		KSBrowser browser = new KSBrowser (metaData.getInitialLookup (), controller);
		return browser;
	}

	private String getLabel (String labelKey)
	{
		// TODO make the group, type and state configurable when framework is ready
		// tried created a new message group clusetmanagement but the entries are not read for some reason
		return Application.getApplicationContext ().getUILabel ("course", "course", "draft", labelKey);
	}

	// TODO - when DOL is pushed farther down into LOBuilder, revert these 5 methods to returning void again.
	protected FieldDescriptor addField(Section section, String fieldKey) {
		return addField(section, fieldKey, null, null, null);
	}    
	protected FieldDescriptor addField(Section section, String fieldKey, MessageKeyInfo messageKey) {
		return addField(section, fieldKey, messageKey, null, null);
	}
	protected FieldDescriptor addField(Section section, String fieldKey, MessageKeyInfo messageKey, Widget widget) {
		return addField(section, fieldKey, messageKey, widget, null);
	}
	protected FieldDescriptor addField(Section section, String fieldKey, MessageKeyInfo messageKey, String parentPath) {
		return addField(section, fieldKey, messageKey, null, parentPath);
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
