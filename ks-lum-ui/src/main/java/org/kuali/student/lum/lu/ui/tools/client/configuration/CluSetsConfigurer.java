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

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.LayoutController;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.layouts.ConfigurableLayout;
import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.common.ui.client.configurable.mvc.sections.VerticalSection;
import org.kuali.student.common.ui.client.configurable.mvc.views.SectionView;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.DataModelDefinition;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.widgets.KSDatePicker;
import org.kuali.student.common.ui.client.widgets.KSTextArea;
import org.kuali.student.common.ui.client.widgets.search.KSPicker;
import org.kuali.student.common.ui.client.widgets.search.SelectedResults;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.LookupMetadata;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.assembly.data.QueryPath;
import org.kuali.student.lum.lu.ui.course.client.configuration.LUConstants;
import org.kuali.student.lum.lu.ui.course.client.configuration.course.CourseConfigurer.CourseSections;
import org.kuali.student.lum.lu.ui.tools.client.widgets.itemlist.CluItemListFieldBinding;
import org.kuali.student.lum.lu.ui.tools.client.widgets.itemlist.CluItemValue;
import org.kuali.student.lum.lu.ui.tools.client.widgets.itemlist.ItemList;
import org.kuali.student.lum.lu.ui.tools.client.widgets.itemlist.ItemListFieldBinding;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.Widget;

public class CluSetsConfigurer {

    private boolean WITH_DIVIDER = true;
    private boolean NO_DIVIDER = false;

    public static final String CLUSET_MGT_MODEL = "cluSetManagementModel";

    private DataModelDefinition modelDefinition;

    public enum CluSetSections{
        CREATE_CLU_SET_CONTENT
    }

    public void setModelDefinition(DataModelDefinition modelDefinition){
    	this.modelDefinition = modelDefinition;
    }

    public void configureCluSetManager(ConfigurableLayout layout) {
        layout.addSection(new String[] {"Manage CLU Sets", getLabel(ToolsConstants.NEW_CLU_SET_LABEL_KEY)}, createCluSetSection());
        layout.addSection(new String[] {"Manage CLU Sets", getLabel(ToolsConstants.NEW_CLU_SET_LABEL_KEY)}, editCluSetSection());        
    }

    private SectionView createCluSetSection() {
        NestedSectionView section = initNestedSectionView(CourseSections.GOVERNANCE, ToolsConstants.NEW_CLU_SET);
        VerticalSection defineCluSet = initSection(getH3Title(ToolsConstants.NEW_CLU_SET_INFO), WITH_DIVIDER);
        // FIXME set approprate modelDTOType here
        CluSetContentEditorSection clusetDetails = initCluSetContentEditorSection(getH3Title("Content"), !WITH_DIVIDER); 
        AddCluLightBox addCourseLightBox = new AddCluLightBox(configureSearch(ToolsConstants.SEARCH_COURSE));
        clusetDetails.setAddApprovedCourseWidget(addCourseLightBox);
        
        final ItemList<CluItemValue> cluItemList = new ItemList<CluItemValue>();
        final FieldDescriptor clusFieldDescriptor = addField(clusetDetails, ToolsConstants.CLU_SET_CLUS_FIELD, "APPROVED COURSES", cluItemList);
        final CluItemListFieldBinding cluItemListFieldBinding = new CluItemListFieldBinding();
        clusFieldDescriptor.setWidgetBinding(cluItemListFieldBinding);
        // updates model when the list of cluItem is changed
        cluItemList.addValueChangeHandler(new ValueChangeHandler<List<CluItemValue>>() {
            @Override
            public void onValueChange(ValueChangeEvent<List<CluItemValue>> event) {
                final LayoutController parent = LayoutController.findParentLayout(cluItemList);
                if(parent != null){
                    parent.requestModel(new ModelRequestCallback<DataModel>() {
                        @Override
                        public void onModelReady(DataModel model) {
                            cluItemListFieldBinding.setModelValue(cluItemList, model, clusFieldDescriptor.getFieldKey());
                        }

                        @Override
                        public void onRequestFail(Throwable cause) {
                            GWT.log("Unable to retrieve model" + clusFieldDescriptor.getFieldKey(), null);
                        }
                        
                    });
                }
            }
        });
        
        // updates the widget "cluItemList" when the user finishes selected clus from the addCourseLightBox
        addCourseLightBox.addSelectionCompleteCallback(
                new Callback<List<CluItemValue>>() {
                    @Override
                    public void exec(List<CluItemValue> result) {
                        List<CluItemValue> cluItems = cluItemList.getValue();
                        if (result != null) {
                            if (cluItems == null || cluItems.isEmpty()) {
                                cluItemList.setValue(result);
//                              // this causes cluItemList to be redrawn with new data
//                                f.getFieldWidget()
                            } else {
                                for (CluItemValue selectedCluItem: result) {
                                    if (!cluItems.contains(selectedCluItem)) {
                                        cluItems.add(selectedCluItem);
                                    }
                                }
                                // this causes cluItemList to be redrawn with new data
                                cluItemList.setValue(cluItems);
                            }
                        }
                    }
                });

        addField(defineCluSet, ToolsConstants.CLU_SET_ORGANIZATION_FIELD, getLabel(ToolsConstants.ORGANIZATION));
        addField(defineCluSet, ToolsConstants.CLU_SET_TITLE_FIELD, getLabel(ToolsConstants.TITLE));
        addField(defineCluSet, ToolsConstants.CLU_SET_DESCRIPTION_FIELD, getLabel(ToolsConstants.DESCRIPTION), new KSTextArea());
        addField(defineCluSet, ToolsConstants.CLU_SET_EFF_DATE_FIELD, getLabel(ToolsConstants.EFFECTIVE_DATE), new KSDatePicker());
        addField(defineCluSet, ToolsConstants.CLU_SET_EXP_DATE_FIELD, getLabel(ToolsConstants.EXPIRATION_DATE), new KSDatePicker());
        
        section.addSection(clusetDetails);
        section.addSection(defineCluSet);
        
        return section;
	}
    
    private SectionView editCluSetSection() {
        VerticalSectionView section = initSectionView(CourseSections.LEARNING_OBJECTIVES, ToolsConstants.EDIT_CLU_SET);
        VerticalSection oversight = initSection(getH3Title(ToolsConstants.EDIT_CLU_SET_INFO), WITH_DIVIDER);
        //addField(oversight, COURSE + "/" + ACADEMIC_SUBJECT_ORGS);
        addField(oversight, ToolsConstants.SEARCH_CLU_SET, "", configureSearch(ToolsConstants.SEARCH_CLU_SET));
        section.addSection(oversight);
        return section;
	}
    
    private NestedSectionView initNestedSectionView (Enum<?> viewEnum, String labelKey) {
        NestedSectionView section = new NestedSectionView(viewEnum, getLabel(labelKey), CLUSET_MGT_MODEL);
        section.addStyleName(LUConstants.STYLE_SECTION);
        section.setSectionTitle(getH1Title(labelKey));

        return section;
    }

    private VerticalSectionView initSectionView (Enum<?> viewEnum, String labelKey) {
        VerticalSectionView section = new VerticalSectionView(viewEnum, getLabel(labelKey), CLUSET_MGT_MODEL);
        section.addStyleName(LUConstants.STYLE_SECTION);
        section.setSectionTitle(getH1Title(labelKey));

        return section;
    }

    private static CluSetContentEditorSection initCluSetContentEditorSection(SectionTitle title, boolean withDivider) {
        CluSetContentEditorSection cluSetContentEditorSection = new CluSetContentEditorSection();
        if (title !=  null) {
            cluSetContentEditorSection.setSectionTitle(title);
        }
        cluSetContentEditorSection.addStyleName(LUConstants.STYLE_SECTION);
        if (withDivider)
            cluSetContentEditorSection.addStyleName(LUConstants.STYLE_SECTION_DIVIDER);
        return cluSetContentEditorSection;
    }

    private static VerticalSection initSection(SectionTitle title, boolean withDivider) {
        VerticalSection section = new VerticalSection();
        if (title !=  null) {
          section.setSectionTitle(title);
        }
        section.addStyleName(LUConstants.STYLE_SECTION);
        if (withDivider)
            section.addStyleName(LUConstants.STYLE_SECTION_DIVIDER);
        return section;
    }
    
    private String getLabel(String labelKey) {
        // TODO make the group, type and state configurable when framework is ready
        // tried created a new message group clusetmanagement but the entries are not read for some reason
        return Application.getApplicationContext().getUILabel("course", "course", "draft", labelKey);
    }

    private SectionTitle getH1Title(String labelKey) {
        return SectionTitle.generateH1Title(getLabel(labelKey));
    }

    private SectionTitle getH2Title(String labelKey) {
        return SectionTitle.generateH2Title(getLabel(labelKey));
    }

    private SectionTitle getH3Title(String labelKey) {
        return SectionTitle.generateH3Title(getLabel(labelKey));
    }

    private SectionTitle getH4Title(String labelKey) {
        return SectionTitle.generateH4Title(getLabel(labelKey));
    }

    private SectionTitle getH5Title(String labelKey) {
        return SectionTitle.generateH5Title(getLabel(labelKey));
    }

    private Picker configureSearch(String fieldKey) {
        QueryPath path = QueryPath.concat(null, fieldKey);
        Metadata metaData = modelDefinition.getMetadata(path);
        Picker picker = new Picker(metaData.getInitialLookup(), metaData.getAdditionalLookups());
        return picker;    
    }
    
//    public class CourseList extends UpdatableMultiplicityComposite {
//        private final String parentPath;
//        public CourseList(String parentPath){
//            super(StyleType.TOP_LEVEL);
//            this.parentPath = parentPath;
//            setAddItemLabel("Add Course");
////            setItemLabel(getLabel(LUConstants.FORMAT_LABEL_KEY));
//        }
//
//        public Widget createItem() {
//            String path = QueryPath.concat(parentPath, String.valueOf(itemCount-1)).toString();
//            GroupSection item = new GroupSection();
//            addField(item, "id", "" , path);
//            addField(item, "name", "", path);
//            return item;
//        }
//    }

    // picker Classes
    public static class Picker extends KSPicker {

        private String name;

        public Picker(LookupMetadata inLookupMetadata, List<LookupMetadata> additionalLookupMetadata) {
            super(inLookupMetadata, additionalLookupMetadata);
        }
        
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }       
    }
    
    
    // TODO - when DOL is pushed farther down into LOBuilder,
    // revert these 5 methods to returning void again.
    private FieldDescriptor addField(Section section, String fieldKey) {
    	return addField(section, fieldKey, null, null, null);
    }    
    private FieldDescriptor addField(Section section, String fieldKey, String fieldLabel) {
    	return addField(section, fieldKey, fieldLabel, null, null);
    }
    private FieldDescriptor addField(Section section, String fieldKey, String fieldLabel, Widget widget) {
    	return addField(section, fieldKey, fieldLabel, widget, null);
    }
    private FieldDescriptor addField(Section section, String fieldKey, String fieldLabel, String parentPath) {
        return addField(section, fieldKey, fieldLabel, null, parentPath);
    }
    private FieldDescriptor addField(Section section, String fieldKey, String fieldLabel, Widget widget, String parentPath) {
        QueryPath path = QueryPath.concat(parentPath, fieldKey);
    	Metadata meta = modelDefinition.getMetadata(path);

    	FieldDescriptor fd = new FieldDescriptor(path.toString(), fieldLabel, meta);
    	if (widget != null) {
    		fd.setFieldWidget(widget);
    	}
    	section.addField(fd);
    	return fd;
    }
}
