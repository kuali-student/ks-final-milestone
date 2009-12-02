/*
 * Copyright 2007 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.lum.lu.ui.course.client.widgets;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.configurable.mvc.CustomNestedSection;
import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.HasModelDTOValue;
import org.kuali.student.common.ui.client.configurable.mvc.MultiplicitySection;
import org.kuali.student.common.ui.client.configurable.mvc.SimpleMultiplicityComposite;
import org.kuali.student.common.ui.client.configurable.mvc.Section.FieldLabelType;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTO;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue.StringType;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue.Type;
import org.kuali.student.common.ui.client.theme.Theme;
import org.kuali.student.common.ui.client.widgets.*;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonEnumerations.GoCancelEnum;
import org.kuali.student.common.ui.client.widgets.buttongroups.ConfirmCancelGroup;
import org.kuali.student.common.ui.client.widgets.buttongroups.GoCancelGroup;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonEnumerations.ConfirmCancelEnum;
import org.kuali.student.common.ui.client.widgets.list.KSCheckBoxList;
import org.kuali.student.common.ui.client.widgets.list.ListItems;
import org.kuali.student.common.ui.client.widgets.list.SelectionChangeHandler;
import org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract;
import org.kuali.student.common.ui.client.widgets.suggestbox.KSAdvancedSearchWindow;
import org.kuali.student.lum.lo.dto.LoInfo;
import org.kuali.student.lum.lu.ui.course.client.configuration.LUConstants;
import org.kuali.student.lum.lu.ui.course.client.configuration.mvc.CluDictionaryClassNameHelper;
import org.kuali.student.lum.lu.ui.course.client.service.LoRpcService;
import org.kuali.student.lum.lu.ui.course.client.service.LoRpcServiceAsync;
import org.kuali.student.lum.lu.ui.course.client.service.LuRpcService;
import org.kuali.student.lum.lu.ui.course.client.service.LuRpcServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * This class manages the users interactions when building/updating Learning Objectives within the
 * context of managing CLUs.  It allows the user to type in LO text directly or execute a search and
 * select one or more of the returned LOs.
 *
 * Users can then re-organize LOs on the screen including altering the sequence and creating sub LOs
 *
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 *
 *
 */
public class LOBuilder extends Composite  implements HasModelDTOValue {

    private static String type;
    private static String state;
    private static String messageGroup;

    private LoRpcServiceAsync loRpcServiceAsync = GWT.create(LoRpcService.class);
    private LuRpcServiceAsync luRpcServiceAsync = GWT.create(LuRpcService.class);

    CluCodePicker coursePicker = new CluCodePicker();

    KSAdvancedSearchWindow loWordSearchWindow;
    KSLightBox loCluSearchWindow;
    KSLightBox cluSearchResultsWindow ;

    VerticalPanel main = new VerticalPanel();

    HorizontalPanel searchMainPanel = new HorizontalPanel();
    SimplePanel searchSpacerPanel = new SimplePanel();
    HorizontalPanel searchLinkPanel = new HorizontalPanel();

    VerticalPanel loPanel = new VerticalPanel();

    KSLabel searchLink;
    LearningObjectiveList loList;
    KSLabel instructions ;

    private static final int NUM_INITIAL_LOS = 1;

    KSLightBox searchWindow ;
    private static final String SEARCH_BY_COURSE_CODE = "by Course Code";
    private static final String SEARCH_BY_WORD = "for words in Learning Objective";


    protected LOBuilder() {
        //TODO: should this be an error?  Can we set realistic defaults?

    }

    public LOBuilder(String luType, String luState, String luGroup) {
        super();
        initWidget(main);

        type = luType;
        state = luState;
        messageGroup = luGroup;

        ClickHandler searchClickHandler = new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (searchWindow == null) {
                    initSearchWindow();
                }
                searchWindow.show();
            }
        };

        searchLink = new KSLabel(getLabel(LUConstants.LO_SEARCH_LINK));
        searchLink.addClickHandler(searchClickHandler);

        instructions = new KSLabel(getLabel(LUConstants.LO_INSTRUCTIONS));
        searchLink = new KSLabel(getLabel(LUConstants.LO_SEARCH_LINK));
        searchLink.addClickHandler(searchClickHandler);
        KSImage searchImage = Theme.INSTANCE.getCommonImages().getSearchIcon();
        searchImage.addClickHandler(searchClickHandler);

        searchLinkPanel.add(searchImage);
        searchLinkPanel.add(searchLink);
        searchSpacerPanel.add(new KSLabel(""));

        searchMainPanel.add(searchSpacerPanel);
        searchMainPanel.add(searchLinkPanel);

        loList = new LearningObjectiveList();
        loPanel.add(loList);

        searchImage.addStyleName("KS-LOBuilder-Search-Image");
        searchLink.addStyleName("KS-LOBuilder-Search-Link");
        searchSpacerPanel.addStyleName("KS-LOBuilder-Spacer-Panel");
        searchMainPanel.addStyleName("KS-LOBuilder-Search-Panel");
        loPanel.addStyleName("KS-LOBuilder-LO-Panel");
        instructions.addStyleName("KS-LOBuilder-Instructions");

        main.add(searchMainPanel);
        main.add(instructions);
        main.add(loPanel);

    }


    public static class LearningObjectiveList extends SimpleMultiplicityComposite {
        private static final String STYLE_HIGHLIGHTED_ITEM = "KS-LOBuilder-Highlighted-Item";
        {
            setAddItemLabel(getLabel(LUConstants.LEARNING_OBJECTIVE_ADD_LABEL_KEY));
//          setShowDelete(false);
        }

        @Override
        public void redraw() {
            super.redraw();
            // populate with at least NUM_INITIAL_LOS items,
            // even if there aren't that many defined yet
            int startIdx = null == modelDTOList ? 0 : modelDTOList.get().size();
            for (int i = startIdx; i < NUM_INITIAL_LOS; i++) {
                addItem();
            }
        }

        @Override
        public Widget createItem() {
            final MultiplicitySection multi = new MultiplicitySection(CluDictionaryClassNameHelper.LO_INFO_CLASS,
                    "kuali.lo.type.singleUse", "draft");
            CustomNestedSection ns = new CustomNestedSection();
            ns.setCurrentFieldLabelType(FieldLabelType.LABEL_TOP);

            final LOPicker picker = new LOPicker();
            picker.addValueChangeHandler(new ValueChangeHandler<String>() {
                @Override
                public void onValueChange(ValueChangeEvent<String> event) {


                }
            });
            picker.addMoveUpAction(new ClickHandler(){
                @Override
                public void onClick(ClickEvent event) {
                    moveUp(multi);
                }
            });
            picker.addMoveDownAction(new ClickHandler(){
                @Override
                public void onClick(ClickEvent event) {
                    moveDown(multi);
                }
            });
            picker.addDeleteAction(new ClickHandler(){
                @Override
                public void onClick(ClickEvent event) {
                    delete(multi);
                }
            });
            FieldDescriptor fd = new FieldDescriptor("desc", null/* getLabel(LUConstants.LEARNING_OBJECTIVE_LO_NAME_KEY)*/, Type.STRING, picker);
            ns.addField(fd);
            ns.addStyleName("KS-LOBuilder-Section");
            multi.addSection(ns);

            return multi;
        }

        private void removeOldHighlights() {
            List<HasModelDTOValue> widgets = this.getModelDTOValueWidgets();
            for (HasModelDTOValue w: widgets) {
                ((Widget)w).removeStyleName(STYLE_HIGHLIGHTED_ITEM);
            }
        }

        private void addSelectedLO(String loDescription) {

           ModelDTO loModel = new ModelDTO(LoInfo.class.getName());
            StringType type = new StringType("kuali.lo.type.singleUse");
            StringType state = new StringType("draft");
            loModel.put("desc", loDescription);
            loModel.put("type", type);
            loModel.put("state", state);

            ModelDTOValue value = new ModelDTOValue.ModelDTOType();
            ((ModelDTOValue.ModelDTOType)value).set(loModel);

//          String test = super.getModelDTOValueWidgets().get(0).getValue().toString();
//          GWT.log("ModelDTOValue in Section = " + ((HasModelDTOValue)newItemWidget).getValue().toString(), null);
            
            Widget w = null;

            //TODO: If there are empty LO boxes the selected LOs should go in there
             
//            boolean foundEmptyWidget = false;
//            for (HasModelDTOValue v: super.getModelDTOValueWidgets()) {
//                if (v.getValue().toString().indexOf("desc") < 0) {
//                    GWT.log("ModelDTOValueActual = " + v.toString(), null);
//                    ((HasModelDTOValue)v).setValue(value);
//                    foundEmptyWidget = true;
//                    break;
//                }
//            }
//
//            if (!foundEmptyWidget) {
                w = super.addNewItem(value);
//            }
            w.addStyleName(STYLE_HIGHLIGHTED_ITEM);
        }

        private void moveUp(Widget item){
            Widget decrator = item.getParent().getParent();
            int index = LearningObjectiveList.this.itemsPanel.getWidgetIndex(decrator);
            if(index ==0){
                return;
            }
            LearningObjectiveList.this.itemsPanel.remove(decrator);
            LearningObjectiveList.this.itemsPanel.insert(decrator,index -1 );

        }
        private void moveDown(Widget item){
            Widget decrator = item.getParent().getParent();
            int index = LearningObjectiveList.this.itemsPanel.getWidgetIndex(decrator);
            if(index == LearningObjectiveList.this.itemsPanel.getWidgetCount()-1){
                return;
            }
            LearningObjectiveList.this.itemsPanel.remove(decrator);
            LearningObjectiveList.this.itemsPanel.insert(decrator,index+1 );

        }
        private void delete(Widget item){
            Widget decrator = item.getParent().getParent();
            int index = LearningObjectiveList.this.itemsPanel.getWidgetIndex(decrator);
            if(index == -1){
                return;
            }
            super.decorateItemWidget(item);
            LearningObjectiveList.this.itemsPanel.remove(decrator);
        }
    }

    private void initSearchWindow() {
        VerticalPanel main = new VerticalPanel();
        KSThinTitleBar titleBar = new KSThinTitleBar(getLabel(LUConstants.LO_SEARCH_LINK));

        VerticalPanel selectSearchPanel = new VerticalPanel();

        final KSRadioButton wordSearch = new KSRadioButton("searchType", "Search for words in Learning Objective");
        final KSRadioButton courseSearch = new KSRadioButton("searchType", "Search by Course Code");
        selectSearchPanel.add(wordSearch);
        selectSearchPanel.add(courseSearch);

        GoCancelGroup buttonPanel = new GoCancelGroup(new Callback<GoCancelEnum>(){

            @Override
            public void exec(GoCancelEnum result) {
                switch(result){
                    case GO:
                        if (wordSearch.getValue()) {
                            if (loWordSearchWindow == null) {
                                initWordSearchWindow();
                            }
                            else {
                                loWordSearchWindow.reset();
                            }
                            searchWindow.hide();
                            loWordSearchWindow.show();
                        }
                        else {
                            if (loCluSearchWindow == null) {
                                initCluSearchWindow();
                            }
                            else {
//                              ((CluCodePicker)loCluSearchWindow.getWidget()).clear();
                            }
                            searchWindow.hide();
                            loCluSearchWindow.show();
                        }

                        break;
                    case CANCEL:
                        searchWindow.hide();
                        loCluSearchWindow.hide();
                        loWordSearchWindow.hide();
                        break;
                }
            }
        });

        main.add(titleBar);
        main.add(selectSearchPanel);
        main.add(buttonPanel);

        searchWindow = new KSLightBox();
        searchWindow.setWidget(main);
    }    

    private void initWordSearchWindow() {

        loWordSearchWindow = new KSAdvancedSearchWindow(loRpcServiceAsync, "lo.search.loByDesc","lo.resultColumn.loDescId",
                getLabel(LUConstants.LEARNING_OBJECTIVE_WORD_SEARCH_KEY));
        loWordSearchWindow.setIgnoreCase(true);
        loWordSearchWindow.setPartialMatch(true);
        loWordSearchWindow.addSelectionHandler(new SelectionHandler<List<String>>(){
            public void onSelection(SelectionEvent<List<String>> event) {
                loList.removeOldHighlights();
                final List<String> selected = event.getSelectedItem();
                if (selected.size() > 0){
                    for (String loDesc : selected ) {
                        loList.addSelectedLO(loDesc);
                    }
                    loWordSearchWindow.hide();
                }
            }
        });
    }

    private void initCluSearchWindow() {

        loCluSearchWindow = new KSLightBox();
        KSThinTitleBar titleBar = new KSThinTitleBar(getLabel(LUConstants.LEARNING_OBJECTIVE_CLUCODE_SEARCH_KEY));

        VerticalPanel main = new VerticalPanel();
        final CluCodePicker picker = new CluCodePicker();
        ConfirmCancelGroup buttonPanel = new ConfirmCancelGroup(new Callback<ConfirmCancelEnum>(){

            @Override
            public void exec(ConfirmCancelEnum result) {
                switch(result){
                    case CONFIRM:

                        luRpcServiceAsync.getLoIdsByClu(picker.getValue(), new AsyncCallback<List<String>>() {

                            @Override
                            public void onFailure(Throwable caught) {
                                GWT.log("getLoIdsByClu failed ", caught);
                            }

                            @Override
                            public void onSuccess(List<String> result) {
                                loCluSearchWindow.hide();
                                showCourseSearchResultsWindow(picker.getText(), result);

                            }
                        });

                        break;
                    case CANCEL:
                        loCluSearchWindow.hide();
                        searchWindow.show();
                        break;
                }
            }
        });

        main.add(titleBar);
        main.add(picker);
        main.add(buttonPanel);
        loCluSearchWindow.setWidget(main);
    }

    private void showCourseSearchResultsWindow(final String selectedCluCode, final List<String> loIds) {
        if (loIds != null && !loIds.isEmpty()) {
            loRpcServiceAsync.getLoByIdList(loIds, new AsyncCallback<List<LoInfo>>() {

                @Override
                public void onFailure(Throwable caught) {
                    Window.alert("getLoByIdList failed " + caught.getMessage());
                }

                @Override
                public void onSuccess(List<LoInfo> result) {

                    cluSearchResultsWindow = new KSLightBox();
                    final KSThinTitleBar titleBar = new KSThinTitleBar("Learning Objectives for " + selectedCluCode);
                    final VerticalPanel main = new VerticalPanel();
                    final KSCheckBoxList loCheckBoxes = new KSCheckBoxList();
                    final ListItems loListItems = buildListItems(result);
                    loCheckBoxes.setListItems(loListItems);

                    main.add(titleBar);
                    main.add(loCheckBoxes);
                    main.add(new ConfirmCancelGroup(new Callback<ConfirmCancelEnum>(){

                        @Override
                        public void exec(ConfirmCancelEnum result) {
                            switch(result){
                                case CONFIRM:
                                    List<String> selectedItems = loCheckBoxes.getSelectedItems();
                                    for(String s: selectedItems){
                                        loList.addSelectedLO(loListItems.getItemText(s));
                                    }
                                    cluSearchResultsWindow.hide();
                                    break;
                                case CANCEL:
                                    loCluSearchWindow.show();
                                    cluSearchResultsWindow.hide();
                                    break;
                            }
                        }
                    }));
                    loCluSearchWindow.hide();
                    cluSearchResultsWindow.setWidget(main);
                    cluSearchResultsWindow.show();
                }

            });
        }
        else {
            Window.alert("No LOs found");

        }


    }

    private ListItems buildListItems(final List<LoInfo> loList) {

        ListItems list = new ListItems(){
            @Override
            public List<String> getAttrKeys() {
                List<String> attributes = new ArrayList<String>();
                attributes.add("Desc");
                return attributes;
            }

            @Override
            public String getItemAttribute(String id, String attrkey) {
                String value = null;
                for(LoInfo lo: loList){
                    if(lo.getId().equals(id)){
                        if(attrkey.equals("Desc")){
                            value = lo.getDesc().getPlain();
                        }
                        break;
                    }
                }
                return value;
            }

            @Override
            public int getItemCount() {
                return loList.size();
            }

            @Override
            public List<String> getItemIds() {
                List<String> ids = new ArrayList<String>();
                for(LoInfo lo: loList){
                    ids.add(lo.getId());
                }
                return ids;
            }

            @Override
            public String getItemText(String id) {
                String value = null;
                for(LoInfo lo: loList){
                    if(lo.getId().equals(id)){
                        value = lo.getDesc().getPlain();
                        break;
                    }
                }
                return value;
            }
        };

        return list;
    }

    private ListItems buildSearchListItems() {

        return new ListItems(){
            List<String> names = Arrays.asList(SEARCH_BY_WORD, SEARCH_BY_COURSE_CODE);

            @Override
            public List<String> getAttrKeys() {
                List<String> attributes = new ArrayList<String>();
                attributes.add("Name");
                return attributes;
            }

            @Override
            public String getItemAttribute(String id, String attrkey) {
                String value = null;
                Integer index;
                try{
                    index = Integer.valueOf(id);
                    value = names.get(index);
                } catch (Exception e) {
                }

                return value;
            }

            @Override
            public int getItemCount() {
                return names.size();
            }

            @Override
            public List<String> getItemIds() {
                List<String> ids = new ArrayList<String>();
                for(int i=0; i < names.size(); i++){
                    ids.add(String.valueOf(i));
                }
                return ids;
            }

            @Override
            public String getItemText(String id) {
                return getItemAttribute(id, "Name");
            }
        };

    }

    @Override
    public void setValue(ModelDTOValue modelDTOValue) {
        loList.setValue(modelDTOValue);

    }

    @Override
    public void setValue(ModelDTOValue value, boolean fireEvents) {
        setValue(value, fireEvents);
    }

    /**
     * @see com.google.gwt.user.client.ui.HasValue#getValue()
     */
    @Override
    public ModelDTOValue getValue() {
        return loList.getValue();
    }

    /**
     * @see org.kuali.student.common.ui.client.configurable.mvc.HasModelDTOValue#updateModelDTO(org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue)
     */
    @Override
    public void updateModelDTOValue() {
        loList.updateModelDTOValue();
    }

    /**
     * @see com.google.gwt.event.logical.shared.HasValueChangeHandlers#addValueChangeHandler(com.google.gwt.event.logical.shared.ValueChangeHandler)
     */
    @Override
    public HandlerRegistration addValueChangeHandler(ValueChangeHandler<ModelDTOValue> handler) {
        return loList.addValueChangeHandler(handler);
    }


    private static String getLabel(String labelKey) {
        return Application.getApplicationContext().getUILabel(messageGroup, type, state, labelKey);
    }
}
