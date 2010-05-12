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

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.LayoutController;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.binding.ModelWidgetBinding;
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
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSTextArea;
import org.kuali.student.common.ui.client.widgets.field.layout.element.MessageKeyInfo;
import org.kuali.student.common.ui.client.widgets.search.KSPicker;
import org.kuali.student.common.ui.client.widgets.search.SearchPanel;
import org.kuali.student.common.ui.client.widgets.search.SelectedResults;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.LookupMetadata;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.assembly.data.QueryPath;
import org.kuali.student.core.assembly.data.Data.DataValue;
import org.kuali.student.core.assembly.data.Data.Value;
import org.kuali.student.core.search.dto.SearchRequest;
import org.kuali.student.lum.lu.ui.course.client.configuration.LUConstants;
import org.kuali.student.lum.lu.ui.tools.client.widgets.CluSetRangeLabel;
import org.kuali.student.lum.lu.ui.tools.client.widgets.itemlist.CluSetRangeModelUtil;
import org.kuali.student.lum.lu.ui.tools.client.widgets.itemlist.ItemList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Widget;

public class CluSetsConfigurer {

    private boolean WITH_DIVIDER = true;
    private boolean NO_DIVIDER = false;

    public static final String CREATE_CLUSET_MGT_MODEL = "createCluSetManagementModel";
    public static final String EDIT_CLUSET_MGT_MODEL = "editCluSetManagementModel";
    public static final String VIEW_CLUSET_MGT_MODEL = "viewCluSetManagerModel";
    public static final String EDIT_SEARCH_CLUSET_MGT_MODEL = "editSearchCluSetManagementModel";
    public static final String VIEW_SEARCH_CLUSET_MGT_MODEL = "viewSearchCluSetManagementModel";
    private String editSearchCluSetId = null;
    private String viewSearchCluSetId = null;


    private DataModelDefinition modelDefinition;

    public enum CluSetSections{
        CREATE_CLU_SET, EDIT_CLU_SET, VIEW_CLU_SET
    }

    public void setModelDefinition(DataModelDefinition modelDefinition){
    	this.modelDefinition = modelDefinition;
    }

    public void configureCluSetManager(ConfigurableLayout layout) {
//        addStartSection(layout);
        layout.addSection(new String[] {"Manage CLU Sets", getLabel(ToolsConstants.NEW_CLU_SET_LABEL_KEY)}, createCluSetSection());
        layout.addSection(new String[] {"Manage CLU Sets", getLabel(ToolsConstants.NEW_CLU_SET_LABEL_KEY)}, editCluSetSection());
        layout.addSection(new String[] {"View CLU Sets"}, viewCluSetSection());
    }

    private void addClusetDetailsSections(SectionView parentView, final String modelId) {
        VerticalSection defineCluSet = initSection(getH3Title(ToolsConstants.NEW_CLU_SET_INFO), WITH_DIVIDER);
        final CluSetContentEditorSection clusetDetails = initCluSetContentEditorSection(getH3Title("Content"), !WITH_DIVIDER, modelId);
        ModelIdPlaceHolder modelIdObj = new ModelIdPlaceHolder(modelId);
//        AddCluLightBox addCourseLightBox = new AddCluLightBox(configureSearch(ToolsConstants.SEARCH_COURSE));
//        clusetDetails.setAddApprovedCourseWidget(addCourseLightBox);
//
//        final ItemList<CluItemValue> cluItemList = new ItemList<CluItemValue>();

        // ****** Add Clus *******
        addField(clusetDetails, ToolsConstants.CLU_SET_CLUS_FIELD, generateMessageInfo("Courses")).setModelId(modelId);
        // END OF items related to Add Clus

        // ****** Add Clu Range *******
        //TODO add cluset and clurange here
        final Picker cluSetRangePicker = configureSearch(ToolsConstants.CLU_SET_CLU_SET_RANGE_EDIT_FIELD);
        final FieldDescriptor cluRangeFieldEditDescriptor = addField(clusetDetails, ToolsConstants.CLU_SET_CLU_SET_RANGE_EDIT_FIELD, generateMessageInfo("Course Range"), cluSetRangePicker);
        final CluSetRangeLabel clusetRangeLabel = new CluSetRangeLabel();
        final FieldDescriptor cluRangeFieldDescriptor = addField(clusetDetails, ToolsConstants.CLU_SET_CLU_SET_RANGE_FIELD, null, clusetRangeLabel);
        cluSetRangePicker.getSearchWindow().addActionCompleteCallback(new Callback<Boolean>() {
        
            @Override
            public void exec(Boolean result) {
                cluSetRangePicker.getSearchWindow().hide();
                final LayoutController parent = LayoutController.findParentLayout(cluSetRangePicker);
                if(parent != null){
                    parent.requestModel(modelId, new ModelRequestCallback<DataModel>() {
                        @Override
                        public void onModelReady(DataModel model) {
//                            ((ModelWidgetBinding)cluRangeFieldDescriptor.getModelWidgetBinding()).setWidgetValue(widget, model, path)
//                            CluSetHelper cluSetHelper = CluSetHelper.wrap(model.getRoot());
//                            cluSetHelper.setCluRangeParams(value)
                            SearchRequest searchRequest = cluSetRangePicker.getSearchWindow()
                                .getSearchRequest();
                            Data searchRequestData = CluSetRangeModelUtil.INSTANCE.
                                toData(searchRequest, null);
                            DataValue dataValue = new DataValue(searchRequestData);
                            LookupMetadata lookupMetadata = null;
                            
                            // look for the lookupMetaData corresponding to the searchRequest
                            List<LookupMetadata> lookupMDs = new ArrayList<LookupMetadata>();
                            lookupMDs.add(cluSetRangePicker.getInitLookupMetadata());
                            lookupMetadata = findLookupMetadata(searchRequest.getSearchKey(), 
                                    lookupMDs);
                            if (lookupMetadata == null || 
                                    !nullSafeEquals(lookupMetadata.getSearchTypeId(), 
                                            searchRequest.getSearchKey())) {
                                lookupMetadata = findLookupMetadata(searchRequest.getSearchKey(), 
                                        cluSetRangePicker.getAdditionalLookupMetadata());
                            }
                            
                            clusetRangeLabel.setLookupMetadata(lookupMetadata);
                            clusetRangeLabel.setValue(dataValue);
                        }

                        @Override
                        public void onRequestFail(Throwable cause) {
                            GWT.log("Unable to retrieve model" + cluRangeFieldDescriptor.getFieldKey(), null);
                        }
                        
                    });
                }
            }
        });
        // updates model when the value is changed
        // this is done to allow data to be accessed to do cross field checks 
        // in this case the checkboxes and the clusetRangeLabel
        clusetRangeLabel.addValueChangeCallback(new Callback<Value>() {
            @Override
            public void exec(Value event) {
              final LayoutController parent = LayoutController.findParentLayout(clusetRangeLabel);
              if(parent != null){
                  parent.requestModel(modelId, new ModelRequestCallback<DataModel>() {
                      @Override
                      public void onModelReady(DataModel model) {
                          ((ModelWidgetBinding)cluRangeFieldDescriptor.getModelWidgetBinding()).
                          setModelValue(clusetRangeLabel, model, cluRangeFieldDescriptor.getFieldKey());
                      }

                      @Override
                      public void onRequestFail(Throwable cause) {
                          GWT.log("Unable to retrieve model" + cluRangeFieldDescriptor.getFieldKey(), null);
                      }
                      
                  });
              }
          }
        });
        // END OF items related to Add Clu Range
        
        addField(modelIdObj, defineCluSet, ToolsConstants.CLU_SET_TYPE_FIELD);
        addField(modelIdObj, defineCluSet, ToolsConstants.CLU_SET_ORGANIZATION_FIELD, generateMessageInfo(ToolsConstants.ORGANIZATION));
        addField(modelIdObj, defineCluSet, ToolsConstants.CLU_SET_NAME_FIELD, generateMessageInfo(ToolsConstants.TITLE));
        addField(modelIdObj, defineCluSet, ToolsConstants.CLU_SET_DESCRIPTION_FIELD, generateMessageInfo(ToolsConstants.DESCRIPTION), new KSTextArea());
        addField(modelIdObj, defineCluSet, ToolsConstants.CLU_SET_EFF_DATE_FIELD, generateMessageInfo(ToolsConstants.EFFECTIVE_DATE), new KSDatePicker());
        addField(modelIdObj, defineCluSet, ToolsConstants.CLU_SET_EXP_DATE_FIELD, generateMessageInfo(ToolsConstants.EXPIRATION_DATE), new KSDatePicker());
        
        parentView.addSection(clusetDetails);
        parentView.addSection(defineCluSet);
    }
    
    private static boolean nullSafeEquals(Object obj1, Object obj2) {
        return (obj1 == null && obj2 == null ||
                obj1 != null && obj2 != null && obj1.equals(obj2));
    }
    
    private static LookupMetadata findLookupMetadata(String lookupId, List<LookupMetadata> lookupMetadatas) {
        LookupMetadata result = null;
        if (lookupMetadatas != null) {
            for (LookupMetadata lookupMetadata : lookupMetadatas) {
                if (nullSafeEquals(lookupMetadata.getSearchTypeId(), lookupId)) {
                    result = lookupMetadata;
                }
            }
        }
        return result;
    }

    private SectionView createCluSetSection() {
        VerticalSectionView section = initNestedSectionView(CluSetSections.CREATE_CLU_SET, ToolsConstants.NEW_CLU_SET, CREATE_CLUSET_MGT_MODEL);
        addClusetDetailsSections(section, CluSetsConfigurer.CREATE_CLUSET_MGT_MODEL);
        return section;
	}

    private SectionView editCluSetSection() {
        final VerticalSectionView section = initNestedSectionView(CluSetSections.EDIT_CLU_SET, ToolsConstants.EDIT_CLU_SET, EDIT_CLUSET_MGT_MODEL);
        VerticalSection oversight = initSection(getH3Title(ToolsConstants.EDIT_CLU_SET_INFO), WITH_DIVIDER);
        Picker cluSetPicker = configureSearch(ToolsConstants.SEARCH_CLU_SET);
        cluSetPicker.addBasicSelectionCompletedCallback(new Callback<SelectedResults>() {
            @Override
            public void exec(SelectedResults result) {
                if (result != null && result.getReturnKey() != null && result.getReturnKey().trim().length() > 0) {
                    editSearchCluSetId = result.getReturnKey();
                    // TODO retrieve cluset by id and
                    final LayoutController parent = LayoutController.findParentLayout(section);
                    if(parent != null){
                        parent.requestModel(CluSetsConfigurer.EDIT_SEARCH_CLUSET_MGT_MODEL, new ModelRequestCallback<DataModel>() {
                            @Override
                            public void onModelReady(DataModel model) {
                                section.updateWidgetData(model);
                            }

                            @Override
                            public void onRequestFail(Throwable cause) {
                                GWT.log("Unable to retrieve model" + EDIT_SEARCH_CLUSET_MGT_MODEL.toString(), null);
                            }

                        });
                    }
                }
            }
        });
        //addField(oversight, COURSE + "/" + ACADEMIC_SUBJECT_ORGS);
        addField(oversight, ToolsConstants.SEARCH_CLU_SET, generateMessageInfo(""), cluSetPicker);
        
        section.addSection(oversight);
        addClusetDetailsSections(section, CluSetsConfigurer.EDIT_CLUSET_MGT_MODEL);
        return section;
	}

    private SectionView viewCluSetSection() {
        final VerticalSectionView sectionView = initVerticalSectionView(CluSetSections.VIEW_CLU_SET, ToolsConstants.VIEW_CLU_SET, VIEW_CLUSET_MGT_MODEL);


        Picker cluSetPicker = configureSearch(ToolsConstants.SEARCH_CLU_SET);
        cluSetPicker.addBasicSelectionCompletedCallback(new Callback<SelectedResults>() {
            @Override
            public void exec(SelectedResults result) {
                if (result != null && result.getReturnKey() != null && result.getReturnKey().trim().length() > 0) {
                    viewSearchCluSetId = result.getReturnKey();
                    // TODO retrieve cluset by id and
                    final LayoutController parent = LayoutController.findParentLayout(sectionView);
                    if(parent != null){
                        parent.requestModel(CluSetsConfigurer.VIEW_SEARCH_CLUSET_MGT_MODEL, new ModelRequestCallback<DataModel>() {
                            @Override
                            public void onModelReady(DataModel model) {
                                sectionView.updateWidgetData(model);
                                
                                //FIXME do something else here, rework logic
                                //sectionView.redraw();
                            }

                            @Override
                            public void onRequestFail(Throwable cause) {
                                GWT.log("Unable to retrieve model" + VIEW_SEARCH_CLUSET_MGT_MODEL.toString(), null);
                            }

                        });
                    }
                }
            }
        });
        addField(sectionView, ToolsConstants.SEARCH_CLU_SET, generateMessageInfo(""), cluSetPicker);

        VerticalSection nameSection = initSection(null, WITH_DIVIDER);
        addField(nameSection, ToolsConstants.CLU_SET_NAME_FIELD, generateMessageInfo("Name"), new KSLabel());
        sectionView.addSection(nameSection);

        VerticalSection expirationDateSection = initSection(null, WITH_DIVIDER);
        addField(expirationDateSection, ToolsConstants.CLU_SET_EXP_DATE_FIELD, generateMessageInfo(ToolsConstants.EFFECTIVE_DATE), new KSLabel());
        sectionView.addSection(expirationDateSection);


        return sectionView;
    }

    private VerticalSectionView initVerticalSectionView(Enum<?> viewEnum, String labelKey, String modelId) {
        VerticalSectionView section = new VerticalSectionView(viewEnum, getLabel(labelKey), modelId);
        section.addStyleName(LUConstants.STYLE_SECTION);
        //TODO is setting to H1 here necessary?
        //section.setSectionTitle(getH1Title(labelKey));

        return section;
    }

    private VerticalSectionView initNestedSectionView (Enum<?> viewEnum, String labelKey, String modelId) {
        VerticalSectionView section = new VerticalSectionView(viewEnum, getLabel(labelKey), modelId);
        section.addStyleName(LUConstants.STYLE_SECTION);
        //TODO is setting to H1 here necessary?
        //section.setSectionTitle(getH1Title(labelKey));

        return section;
    }

    private static CluSetContentEditorSection initCluSetContentEditorSection(SectionTitle title, boolean withDivider, String modelId) {
        CluSetContentEditorSection cluSetContentEditorSection = new CluSetContentEditorSection(modelId);
        if (title !=  null) {
        	cluSetContentEditorSection = new CluSetContentEditorSection(title, modelId);
        }
        cluSetContentEditorSection.addStyleName(LUConstants.STYLE_SECTION);
        if (withDivider)
            cluSetContentEditorSection.addStyleName(LUConstants.STYLE_SECTION_DIVIDER);
        return cluSetContentEditorSection;
    }

    private static VerticalSection initSection(SectionTitle title, boolean withDivider) {
        VerticalSection section = new VerticalSection(title);
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

    private SearchPanel configureSearchPanel(String fieldKey) {
        QueryPath path = QueryPath.concat(null, fieldKey);
        Metadata metaData = modelDefinition.getMetadata(path);
        SearchPanel searchPanel = new SearchPanel(metaData.getAdditionalLookups());
        return searchPanel;
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

    public static class Picker extends KSPicker {

        private String name;
        private LookupMetadata initLookupMetadata;
        private List<LookupMetadata> additionalLookupMetadata;

        public Picker(LookupMetadata inLookupMetadata, List<LookupMetadata> additionalLookupMetadata) {
            super(inLookupMetadata, additionalLookupMetadata);
            this.initLookupMetadata = inLookupMetadata;
            this.additionalLookupMetadata = additionalLookupMetadata;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public LookupMetadata getInitLookupMetadata() {
            return initLookupMetadata;
        }

        public void setInitLookupMetadata(LookupMetadata initLookupMetadata) {
            this.initLookupMetadata = initLookupMetadata;
        }

        public List<LookupMetadata> getAdditionalLookupMetadata() {
            return additionalLookupMetadata;
        }

        public void setAdditionalLookupMetadata(List<LookupMetadata> additionalLookupMetadata) {
            this.additionalLookupMetadata = additionalLookupMetadata;
        }
        
    }
    private FieldDescriptor addField(ModelIdPlaceHolder modelIdObj, Section section, String fieldKey) {
    	return addField(modelIdObj, section, fieldKey, null, null, null);
    }
    private FieldDescriptor addField(ModelIdPlaceHolder modelIdObj, Section section, String fieldKey, MessageKeyInfo messageKey) {
    	return addField(modelIdObj, section, fieldKey, messageKey, null, null);
    }
    private FieldDescriptor addField(ModelIdPlaceHolder modelIdObj, Section section, String fieldKey, MessageKeyInfo messageKey, Widget widget) {
    	return addField(modelIdObj, section, fieldKey, messageKey, widget, null);
    }
    private FieldDescriptor addField(ModelIdPlaceHolder modelIdObj, Section section, String fieldKey, MessageKeyInfo messageKey, String parentPath) {
        return addField(modelIdObj, section, fieldKey, messageKey, null, parentPath);
    }

    // TODO - when DOL is pushed farther down into LOBuilder,
    // revert these 5 methods to returning void again.
    // TODO - when DOL is pushed farther down into LOBuilder,
    // revert these 5 methods to returning void again.
    protected FieldDescriptor addField(Section section, String fieldKey) {
    	return addField(null, section, fieldKey, null, null, null);
    }    
    protected FieldDescriptor addField(Section section, String fieldKey, MessageKeyInfo messageKey) {
    	return addField(null, section, fieldKey, messageKey, null, null);
    }
    protected FieldDescriptor addField(Section section, String fieldKey, MessageKeyInfo messageKey, Widget widget) {
    	return addField(null, section, fieldKey, messageKey, widget, null);
    }
    protected FieldDescriptor addField(Section section, String fieldKey, MessageKeyInfo messageKey, String parentPath) {
        return addField(null, section, fieldKey, messageKey, null, parentPath);
    }
    private FieldDescriptor addField(ModelIdPlaceHolder modelId, Section section, String fieldKey, MessageKeyInfo messageKey, Widget widget, String parentPath) {
        QueryPath path = QueryPath.concat(parentPath, fieldKey);
    	Metadata meta = modelDefinition.getMetadata(path);

    	FieldDescriptor fd = new FieldDescriptor(path.toString(), messageKey, meta);
    	if (widget != null) {
    		fd.setFieldWidget(widget);
    	}
    	if (modelId != null) {
    		fd.setModelId(modelId.getModelId());
    	}
    	section.addField(fd);
    	return fd;
    }

    public String getEditSearchCluSetId() {
        return editSearchCluSetId;
    }

    public void setEditSearchCluSetId(String searchCluSetId) {
        this.editSearchCluSetId = searchCluSetId;
    }

    public String getViewSearchCluSetId() {
        return viewSearchCluSetId;
    }

    public void setViewSearchCluSetId(String viewSearchCluSetId) {
        this.viewSearchCluSetId = viewSearchCluSetId;
    }
    
    protected MessageKeyInfo generateMessageInfo(String labelKey) {
        // TODO make the group, type and state configurable when framework is ready
        // tried created a new message group clusetmanagement but the entries are not read for some reason
        return new MessageKeyInfo("course", "course", "draft", labelKey);
    }
    
    class ModelIdPlaceHolder {
    	private String modelId;
    	
    	public ModelIdPlaceHolder(String modelId) {
    		setModelId(modelId);
    	}
    	
		public String getModelId() {
			return modelId;
		}

		public void setModelId(String modelId) {
			this.modelId = modelId;
		}
    }
}
