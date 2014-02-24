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
import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.common.ui.client.configurable.mvc.views.SectionView;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.DataModelDefinition;
import org.kuali.student.common.ui.client.widgets.KSErrorDialog;
import org.kuali.student.common.ui.client.widgets.field.layout.element.MessageKeyInfo;
import org.kuali.student.lum.common.client.lu.LUUIConstants;
import org.kuali.student.lum.lu.ui.tools.client.widgets.KSBrowser;
import org.kuali.student.r1.common.assembly.data.Metadata;
import org.kuali.student.r1.common.assembly.data.QueryPath;

import com.google.gwt.user.client.ui.Widget;

public class CatalogBrowserConfigurer {

	public static final String CATALOG_BROWSER_MODEL = "CatalogBrowserModel";
	private DataModelDefinition modelDefinition;

	public enum Sections {
		BROWSE_BY_SUBJECT_AREA, BROWSE_BY_SCHOOL;
	}

	public void setModelDefinition(DataModelDefinition modelDefinition) {
		this.modelDefinition = modelDefinition;
	}

	private Controller controller;

	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	public void configureCatalogBrowser(CatalogBrowserController layout) {
		// layout.setContentTitle("Browse Course Catalog");
		layout.addStyleName("browseCatalog");
        layout.setBasicTitle(Application.getApplicationContext().getUILabel(LUUIConstants.COURSE_GROUP_NAME,
                LUUIConstants.BROWSE_COURSE_CATALOG_LABEL_KEY));
        layout.addTab(createBrowseBySubjectAreaSection(),
                Application.getApplicationContext().getUILabel(LUUIConstants.COURSE_GROUP_NAME,
                        LUUIConstants.BROWSE_COURSE_CATALOG_BY_TAB_LABEL_KEY));
        layout.addTab(
                createBrowseBySchoolSection(),
                Application.getApplicationContext().getUILabel(LUUIConstants.COURSE_GROUP_NAME,
                        CatalogBrowserConstants.BROWSE_BY_SCHOOL_LABEL_KEY));
		layout.setDefaultView(Sections.BROWSE_BY_SUBJECT_AREA);
	}

	protected SectionView createBrowseBySubjectAreaSection() {
		//constructor sets the attributes of the browse subject area tab
		VerticalSectionView nestedSectionView = 
                new VerticalSectionView(Sections.BROWSE_BY_SUBJECT_AREA,
                        Application.getApplicationContext().getUILabel(LUUIConstants.COURSE_GROUP_NAME,
                                CatalogBrowserConstants.BROWSE_BY_SCHOOL_LABEL_KEY), CATALOG_BROWSER_MODEL);
		String fieldKey = CatalogBrowserConstants.FULLY_QUALIFIED_BY_SUBJECT_AREA;
		addField(nestedSectionView, fieldKey, null,	configureKSBrowser(fieldKey));
		return nestedSectionView;
	}

	protected SectionView createBrowseBySchoolSection() {
		//constructor sets the attributes of the browse school tab
		VerticalSectionView nestedSectionView = 
			new VerticalSectionView(Sections.BROWSE_BY_SCHOOL, "Browse by School", CATALOG_BROWSER_MODEL);
		String fieldKey = CatalogBrowserConstants.FULLY_QUALIFIED_BY_SCHOOL_OR_COLLEGE;
		addField(nestedSectionView, fieldKey, null,	configureKSBrowser(fieldKey));
		return nestedSectionView;
	}

	private String formatMetadata(Metadata md, String fieldKey) {
        StringBuffer bufferMsg = new StringBuffer("metadata for fieldKey=");
        bufferMsg.append(fieldKey);
        // + "\n Name=" + md.getName ()
        bufferMsg.append("\n LabelKey=");
        bufferMsg.append(md.getLabelKey());
        bufferMsg.append("\n defaultValuePath=");
        bufferMsg.append(md.getDefaultValuePath());
        bufferMsg.append("\n LookupContextPath=");
        bufferMsg.append(md.getLookupContextPath());
        // + "\n maskForatter=" + md.getMaskFormatter ()
        // + "\n partialMaskFormatter=" + md.getPartialMaskFormatter ()
        bufferMsg.append("\n dataType=");
        bufferMsg.append(md.getDataType());
        bufferMsg.append( "\n defaultValue=");
        bufferMsg.append(md.getDefaultValue());
        bufferMsg.append("\n WriteAccess=");
        bufferMsg.append(md.getWriteAccess());
        bufferMsg.append("\n initialLookup=");
        bufferMsg.append(md.getInitialLookup());
        bufferMsg.append("\n additionalLookups=");
        bufferMsg.append(md.getAdditionalLookups());
		if (md.getProperties() != null) {
            bufferMsg.append("\n It has ");
            bufferMsg.append(md.getProperties().size());
            bufferMsg.append(" properties: \n");
			for (String fk : md.getProperties().keySet()) {
                bufferMsg.append("\n");
                bufferMsg.append(formatMetadata(md.getProperties().get(fk), fk));
			}
		}
		return bufferMsg.toString();
	}

	private KSBrowser configureKSBrowser(String fieldKey) {
		QueryPath path = QueryPath.concat(null, fieldKey);
		Metadata md = modelDefinition.getMetadata(path);
		if (md == null) {
			KSErrorDialog.show(new NullPointerException(
					"Invalid lookup configuration: missing field in metadata."
							+ formatMetadata(modelDefinition.getMetadata(),	fieldKey)));
			return null;
		}
		if (md.getInitialLookup() == null) {
			KSErrorDialog.show(new NullPointerException(
					"Invalid lookup configuration: missing initial lookup in metadata."
							+ formatMetadata(modelDefinition.getMetadata(),	fieldKey)));
			return null;
		}
		KSBrowser browser = new KSBrowser(md.getInitialLookup(), controller);
		return browser;
	}

	protected FieldDescriptor addField(Section section, String fieldKey, MessageKeyInfo messageKey, Widget widget) {
		return addField(section, fieldKey, messageKey, widget, null);
	}

	protected FieldDescriptor addField(Section section, String fieldKey,
			MessageKeyInfo messageKey, Widget widget, String parentPath) {
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
