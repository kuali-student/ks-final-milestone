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

package org.kuali.student.lum.common.client.lo;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.application.KSAsyncCallback;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.service.DataSaveResult;
import org.kuali.student.common.ui.client.util.DebugIdUtils;
import org.kuali.student.common.ui.client.util.UtilConstants;
import org.kuali.student.common.ui.client.widgets.*;
import org.kuali.student.common.ui.client.widgets.KSButtonAbstract.ButtonStyle;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonEnumerations.ButtonEnum;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonGroup;
import org.kuali.student.common.ui.client.widgets.buttonlayout.ButtonRow;
import org.kuali.student.common.ui.client.widgets.field.layout.element.AbbrButton;
import org.kuali.student.common.ui.client.widgets.field.layout.element.AbbrButton.AbbrButtonType;
import org.kuali.student.common.ui.client.widgets.field.layout.element.LabelPanel;
import org.kuali.student.common.ui.client.widgets.field.layout.layouts.FieldLayoutComponent;
import org.kuali.student.common.ui.client.widgets.focus.FocusGroup;
import org.kuali.student.common.ui.client.widgets.list.ListItems;
import org.kuali.student.common.ui.client.widgets.list.SelectionChangeHandler;
import org.kuali.student.common.ui.client.widgets.notification.KSNotification;
import org.kuali.student.common.ui.client.widgets.notification.KSNotifier;
import org.kuali.student.common.ui.client.widgets.progress.BlockingTask;
import org.kuali.student.common.ui.client.widgets.progress.KSBlockingProgressIndicator;
import org.kuali.student.common.ui.client.widgets.suggestbox.KSSuggestBox;
import org.kuali.student.common.ui.client.widgets.suggestbox.SearchSuggestOracle;
import org.kuali.student.common.ui.client.widgets.suggestbox.SuggestPicker;
import org.kuali.student.lum.common.client.lo.rpc.LoCategoryRpcService;
import org.kuali.student.lum.common.client.lo.rpc.LoCategoryRpcServiceAsync;
import org.kuali.student.lum.common.client.lu.LUUIConstants;
import org.kuali.student.r1.common.assembly.data.Data;
import org.kuali.student.r1.common.assembly.data.Data.DataValue;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.lum.lo.dto.LoCategoryInfo;

import java.util.*;

/**
 * This class allows a user to select and remove LO categories within the context of
 * LO creation. New categories can be added 'on the fly' and are persisted in the database
 * independently of LO creation
 *
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 */
public class LOCategoryBuilder extends Composite implements HasValue<List<LoCategoryInfo>> {	//KSLAB-2091:  One instance for each LO entry box

    private String type;
    private String state;
    private String repoKey;
    private String messageGroup;

    private LoCategoryRpcServiceAsync loCatRpcServiceAsync;
    private LOCategoryPicker picker;
    private AbbrButton help = new AbbrButton(AbbrButtonType.HELP);

    LOCategoryListNew categoryList;
    Map<String, TypeInfo> categoryTypeMap;

    VerticalPanel root = new VerticalPanel();

    private KSButton addButton = new KSButton("Add", ButtonStyle.SECONDARY);

    private KSLightBox createCategoryWindow;
    Anchor browseCategoryLink;
    private final BlockingTask saving = new BlockingTask("Saving");

    public LOCategoryBuilder(String messageGroup, String type, String state, String loRepoKey) {
        super();

        this.type = type;
        this.state = state;
        this.repoKey = loRepoKey;
        this.messageGroup = messageGroup;

        loCatRpcServiceAsync = GWT.create(LoCategoryRpcService.class);
        picker = new LOCategoryPicker();
        categoryList = new LOCategoryListNew();

        initWidget(root);

        final FlowPanel selectedPanel = new FlowPanel();
        selectedPanel.setStyleName("KS-LOSelectedCategories");
        addButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                addEnteredCategory();
            }
        });
        browseCategoryLink = new Anchor(getLabelText(LUUIConstants.LO_CATEGORY_BROWSE_LABEL_KEY));
        browseCategoryLink.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
               // Filter out any categories already in the picker
                List<LoCategoryInfo> categoriesInPicker = categoryList.getValue();
                /*Category Management has a CategoryManagementTable -> has a DefaultTableModel -> has rowList 
                 * 															   							of category rows that go in mainPanel FlowPanel
                 * 																												 in 'Select Categories' pop KSLightBox*/
                final CategoryManagement categoryManagement = new CategoryManagement(true, true, categoriesInPicker);	
                categoryManagement.setDeleteButtonEnabled(false);
                categoryManagement.setInsertButtonEnabled(false);
                categoryManagement.setUpdateButtonEnabled(false);

                final KSLightBox pop = new KSLightBox();
                pop.setMaxWidth(600);
                pop.setMaxHeight(750);
                KSButton addButton = new KSButton("Add");
                KSButton cancelButton = new KSButton("Cancel", ButtonStyle.ANCHOR_LARGE_CENTERED);

                pop.addButton(addButton);
                pop.addButton(cancelButton);

                pop.setNonCaptionHeader(SectionTitle.generateH2Title(getLabelText(LUUIConstants.LO_CATEGORY_BROWSE_POPUP_LABEL_KEY)));
                FlowPanel mainPanel = new FlowPanel();
                mainPanel.add(categoryManagement);

                addButton.addClickHandler(new ClickHandler() {
                    @Override
                    public void onClick(ClickEvent event) {
                        List<LoCategoryInfo> list = categoryManagement.getSelectedCategoryList();
                        for (LoCategoryInfo info : list) {
                            addCategory(info);	//I think this adds categories selected from choices in 'Select Categories' pop KSLightBox to an LO
                        }
                        pop.hide();
                    }
                });

                cancelButton.addClickHandler(new ClickHandler() {
                    @Override
                    public void onClick(ClickEvent event) {
                        pop.hide();
                    }
                });


                pop.setWidget(mainPanel);
                pop.show();


            }
        });

        VerticalPanel main = new VerticalPanel();
        HorizontalPanel suggestPanel = new HorizontalPanel();
        suggestPanel.add(picker);
        suggestPanel.add(addButton);

        VerticalPanel suggestAndBrowsePanel = new VerticalPanel();
        suggestAndBrowsePanel.add(suggestPanel);
        suggestAndBrowsePanel.add(browseCategoryLink);


        selectedPanel.add(categoryList);

        String fieldHTMLId = HTMLPanel.createUniqueId();
        String title = getLabelText(LUUIConstants.LO_CATEGORY_KEY);
        String helpText = getLabelText(LUUIConstants.LO_CATEGORY_KEY + FieldLayoutComponent.HELP_MESSAGE_KEY);
        LabelPanel fieldTitle = new LabelPanel(title, fieldHTMLId);

        if (helpText != null) {
            setHelp(helpText);
        } else {
            help.setVisible(false);
        }

        fieldTitle.add(help);
        main.add(fieldTitle);
        main.add(suggestAndBrowsePanel);
        main.add(selectedPanel);
        root.add(main);

        main.addStyleName("KS-LOCategoryPicker");
        addButton.addStyleName("KS-LOCategoryPicker-Button");

    }

    public void setHelp(final String html) {
        if (html != null && !html.trim().equals("")) {
            help.setVisible(true);
            help.setHoverHTML(html);
            /*help.addClickHandler(new ClickHandler(){

                @Override
                public void onClick(ClickEvent event) {
                    //TODO show actual help window
                    Window.alert(html);

                }
            });*/
        } else {
            help.setVisible(false);
        }
    }

    private void addEnteredCategory() {

        if (categoryList == null)
            categoryList = new LOCategoryListNew();

        String selectedId = picker.getSelectedId();
        if (selectedId.trim().equals("") || selectedId.equals(UtilConstants.IMPOSSIBLE_CHARACTERS)) {
            showNewCategoryWindow();
        } else {
            loCatRpcServiceAsync.getData(picker.getSelectedId(), new KSAsyncCallback<Data>() {

                @Override
                public void handleFailure(Throwable caught) {
                    Window.alert("getLoCategory failed " + caught.getMessage());
                }

                @Override
                public void onSuccess(Data result) {
                    final LoCategoryInfo newCategory = CategoryDataUtil.toLoCategoryInfo(result);
                    addCategory(newCategory);

                }
            });
        }
    }

    private void showNewCategoryWindow() {

        final VerticalPanel main = new VerticalPanel();
        final KSDropDown typesDropDown = new KSDropDown();
        createCategoryWindow = new KSLightBox();

        FlexTable layoutTable = new FlexTable();
        final KSTextBox nameTextBox = new KSTextBox();
        nameTextBox.setText(picker.getText());//+ enteredWord);

        layoutTable.setWidget(0, 0, new KSLabel("Category"));
        layoutTable.setWidget(0, 1, new KSLabel("Type"));
        layoutTable.setWidget(1, 0, nameTextBox);
        layoutTable.setWidget(1, 1, typesDropDown);

        SectionTitle sectionTitle = SectionTitle.generateH2Title("Create New Category");
        //KSThinTitleBar titleBar = new KSThinTitleBar("Create New Category");
        createCategoryWindow.setNonCaptionHeader(sectionTitle);
        main.add(layoutTable);

        loCatRpcServiceAsync.getLoCategoryTypes(new KSAsyncCallback<List<TypeInfo>>() {

            @Override
            public void handleFailure(Throwable caught) {
                Window.alert("getLoCategoryTypes failed " + caught.getMessage());
            }

            @Override
            public void onSuccess(List<TypeInfo> result) {
                final LOCategoryTypeInfoList list = new LOCategoryTypeInfoList(result);
                typesDropDown.setListItems(list);
                if (categoryTypeMap == null) {
                    loadCategoryTypes(result);
                }

                CreateLoCancelGroup buttonPanel = new CreateLoCancelGroup(new Callback<LoCancelEnum>() {

                    @Override
                    public void exec(LoCancelEnum result) {
                        switch (result) {
                            case CREATE:

                                LoCategoryInfoHelper catHelper = new LoCategoryInfoHelper(new Data());
                                catHelper.setName(nameTextBox.getText());
                                catHelper.setStateKey("Active");
                                catHelper.setLoRepositoryKey(repoKey);
                                catHelper.setTypeKey(typesDropDown.getSelectedItem());

                                loCatRpcServiceAsync.saveData(catHelper.getData(), new KSAsyncCallback<DataSaveResult>() {
                                    @Override
                                    public void handleFailure(Throwable caught) {
                                        Window.alert("Create LO Category failed: " + caught.getMessage());
                                    }
                                    
                                    @Override
                                    public void handleVersionMismatch(Throwable caught) {
                                        super.handleVersionMismatch(caught);
                                        KSBlockingProgressIndicator.removeTask(saving);
                                    }

                                    @Override
                                    public void onSuccess(DataSaveResult result) {
                                        KSBlockingProgressIndicator.removeTask(saving);

                                        if (result.getValidationResults() != null && !result.getValidationResults().isEmpty()) {
                                            Window.alert("Create LO Category failed: " + result.getValidationResults().get(0).getMessage());
                                        } else {

                                            final LoCategoryInfo newCategory = CategoryDataUtil.toLoCategoryInfo(result.getValue());
                                            addCategory(newCategory);
                                            createCategoryWindow.hide();
                                            KSNotifier.add(new KSNotification("Create LO Category Successful", false, 3000));
                                        }
                                    }
                                });
                                break;
                            case CANCEL:
                                createCategoryWindow.hide();
                                break;
                        }
                    }
                });

                main.add(buttonPanel);
                main.setPixelSize(300, 300);
                createCategoryWindow.setWidget(main);
                createCategoryWindow.show();
            }


        });
    }

    private void loadCategoryTypes(List<TypeInfo> categoryTypes) {
        if (categoryTypeMap == null) {
            categoryTypeMap = new HashMap<String, TypeInfo>();
        }
        if (categoryTypes != null) {
            for (TypeInfo i : categoryTypes) {
                categoryTypeMap.put(i.getKey(), i);
            }
        }
    }

    /**
     * This method will check if an LO category is already in the picker box
     * and prevent it from being added to the picker.
     * <p>
     * 
     * @param categoryToCheck category to check if it is already in the picker box
     * @return true if the category is already in the picker box
     */
    private boolean isCategoryAlreadyAddedToPicker(LoCategoryInfo categoryToCheck){
        // TODO: do we need null checks?
        List<LoCategoryInfo> categoriesInPicker = categoryList.getValue();
        if (categoriesInPicker != null && categoryToCheck != null){
            for (LoCategoryInfo pickerCategory : categoriesInPicker) {
                boolean namesMatch = pickerCategory.getName().equalsIgnoreCase(categoryToCheck.getName());
                boolean typesMatch = pickerCategory.getTypeKey().equalsIgnoreCase(categoryToCheck.getTypeKey());
                if (namesMatch && typesMatch){
                    return true;
                }
            }
        }
        return false;
    }
    private void addCategory(final LoCategoryInfo category) {
        if (categoryTypeMap == null) {
            categoryTypeMap = new HashMap<String, TypeInfo>();
        }

        if (categoryTypeMap.containsKey(category.getTypeKey())) {
            // check if category is already added to picker.  only add it once.
            if (!isCategoryAlreadyAddedToPicker(category)){
                categoryList.addItem(category);
            }
            picker.reset();
        } else {
            loCatRpcServiceAsync.getLoCategoryType(category.getTypeKey(), new KSAsyncCallback<TypeInfo>() {

                @Override
                public void handleFailure(Throwable caught) {
                    Window.alert("getLoCategoryType failed " + caught.getMessage());
                }

                @Override
                public void onSuccess(TypeInfo result) {
                    categoryTypeMap.put(result.getKey(), result);
                    // check if category is already added to picker.  only add it once.
                    if (!isCategoryAlreadyAddedToPicker(category)){
                         categoryList.addItem(category);
                    } 
                    picker.reset();

                }

            });
        }

    }

    private String getLabelText(String labelKey) {
        return Application.getApplicationContext().getUILabel(messageGroup, type, state, labelKey);
    }

    @Override
    public void setValue(List<LoCategoryInfo> categories) {
        categoryList.setValue(categories);

    }

    @Override
    public void setValue(List<LoCategoryInfo> value, boolean fireEvents) {
        setValue(value);
    }

    /**
     * @see com.google.gwt.user.client.ui.HasValue#getValue()
     */
    @Override
    public List<LoCategoryInfo> getValue() {
        return categoryList.getValue();
    }

    /**
     * @see com.google.gwt.event.logical.shared.HasValueChangeHandlers#addValueChangeHandler(com.google.gwt.event.logical.shared.ValueChangeHandler)
     */
    @Override
    public HandlerRegistration addValueChangeHandler(ValueChangeHandler<List<LoCategoryInfo>> handler) {
        return categoryList.addValueChangeHandler(handler);
    }

    /**
     * This class provides a suggest box for existing LO categories backed by a search on category name
     *
     * @author Kuali Rice Team (kuali-rice@googlegroups.com)
     */
    private class LOCategoryPicker extends Composite implements SuggestPicker {

        //FIXME:   [KSCOR-225] Class needs to be rewritten to use KSPicker instead of SuggestPicker and use lookup config through metadata

        final SearchSuggestOracle loSearchOracle = new SearchSuggestOracle(
                "lo.search.loCategories",
                "lo.queryParam.loOptionalCategoryName",
                "lo.queryParam.loCategoryId",
                "lo.resultColumn.categoryId",
                "lo.resultColumn.categoryNameAndType");

        final KSSuggestBox suggestBox = new KSSuggestBox(loSearchOracle);

        private final FocusGroup focus = new FocusGroup(this);

        private VerticalPanel main = new VerticalPanel();

        protected LOCategoryPicker() {
            super();
            init();
        }

        public String getSelectedId() {
            return suggestBox.getSelectedId();
        }

        private void init() {
            focus.addWidget(suggestBox);
            loSearchOracle.setTextWidget(suggestBox.getTextBox());
            main.add(suggestBox);
            initWidget(main);
        }

        @Override
        public String getValue() {
            return suggestBox.getSelectedId();
        }

        @Override
        public void setValue(String value) {
            setValue(value, true);
        }

        @Override
        public void setValue(String value, boolean fireEvents) {
            suggestBox.reset();
            suggestBox.setValue(value, fireEvents);
        }


        @Override
        public HandlerRegistration addValueChangeHandler(ValueChangeHandler<String> handler) {
            return suggestBox.addValueChangeHandler(handler);
        }

        @Override
        public void fireEvent(GwtEvent<?> event) {
            super.fireEvent(event);
        }

        public void reset() {
            suggestBox.reset();
        }

        @Override
        public HandlerRegistration addFocusHandler(FocusHandler handler) {
            return focus.addFocusHandler(handler);
        }

        @Override
        public HandlerRegistration addBlurHandler(BlurHandler handler) {
            return focus.addBlurHandler(handler);
        }

        public String getText() {
            return suggestBox.getText();
        }

        @Override
        public HandlerRegistration addSelectionChangeHandler(SelectionChangeHandler handler) {
            return suggestBox.addSelectionChangeHandler(handler);
        }

        @Override
        protected void onEnsureDebugId(String baseID) {
            super.onEnsureDebugId(baseID);
            suggestBox.getTextBox().ensureDebugId(baseID);
        }
        
    }

    private class LOCategoryTypeInfoList implements ListItems {
        Map<String, TypeInfo> loTypeMap = new HashMap<String, TypeInfo>();

        public LOCategoryTypeInfoList(List<TypeInfo> loTypes) {
            for (TypeInfo type : loTypes) {
                loTypeMap.put(type.getKey(), type);
            }
        }

        public List<String> getAttrKeys() {
            return Arrays.asList("Name");
        }

        public String getItemAttribute(String id, String attrkey) {
            TypeInfo lo = loTypeMap.get(id);

            if (attrkey.equals("Name")) {
                return lo.getName();
            }

            return null;
        }

        public int getItemCount() {
            return loTypeMap.size();
        }

        public List<String> getItemIds() {
            List<String> keys = new ArrayList<String>();

            for (String s : loTypeMap.keySet()) {
                keys.add(s);
            }

            return keys;
        }

        public String getItemText(String id) {
            return ((TypeInfo) loTypeMap.get(id)).getName();
        }
    }

    public class LOCategoryListNew extends Composite implements HasValue<List<LoCategoryInfo>> {
        private static final String CATEGORY_TYPE_SEPARATOR = " - ";
        private VerticalPanel listPanel;
        private VerticalPanel main = new VerticalPanel();
        protected List<LoCategoryInfo> categories = new ArrayList<LoCategoryInfo>();

        final CloseHandler<KSItemLabel> deleteHandler = new CloseHandler<KSItemLabel>() {
            @Override
            public void onClose(CloseEvent<KSItemLabel> event) {
                KSItemLabel itemLabel = event.getTarget();
                String itemText = itemLabel.getDeletedKey();
                categoryList.removeItem(itemText);
                categoryList.redraw();
            }
        };

        public LOCategoryListNew() {
            listPanel = new VerticalPanel();
            main.add(listPanel);
            super.initWidget(main);
        }

        public HandlerRegistration addValueChangeHandler(ValueChangeHandler<List<LoCategoryInfo>> handler) {
            return addHandler(handler, ValueChangeEvent.getType());
        }

        private void fireChangeEvent() {
            ValueChangeEvent.fire(this, categories);
        }

        public void redraw() {

            if (null == categoryTypeMap || categoryTypeMap.isEmpty()) {

                loCatRpcServiceAsync.getLoCategoryTypes(new KSAsyncCallback<List<TypeInfo>>() {

                    @Override
                    public void handleFailure(Throwable caught) {
                        Window.alert("getLoCategoryTypes failed " + caught.getMessage());
                    }

                    @Override
                    public void onSuccess(List<TypeInfo> result) {
                        if (categoryTypeMap == null) {
                            loadCategoryTypes(result);
                        }
                        redrawCategoryTable();
                    }

                });
            } else {
                redrawCategoryTable();
            }
        }

        private void redrawCategoryTable() {
            listPanel.clear();

            for (int i = 0; i < categories.size(); i++) {
                String name = categories.get(i).getName();
                String typeKey = categories.get(i).getTypeKey();
                // TODO - need to somehow ensure that categoryTypeMap is initialized before redraw()
                KSItemLabel newItemLabel = new KSItemLabel(true, new CategoryDataParser());
                Data categoryData = CategoryDataUtil.toData(categories.get(i));
                newItemLabel.setValue(new DataValue(categoryData));
                newItemLabel.addCloseHandler(deleteHandler);

                listPanel.add(newItemLabel);
//               String name = categories.get(i).getName();
//               String typeKey = categories.get(i).getType();
//               // TODO - need to somehow ensure that categoryTypeMap is initialized before redraw() 
//               String typeName = "ERROR: uninitialized categoryTypeMap";
//               if (null != categoryTypeMap) {
//                   typeName = categoryTypeMap.get(typeKey).getName();
//               } 
//               categoryTable.setWidget(row, col++, new KSLabel(name + CATEGORY_TYPE_SEPARATOR + typeName));
//               KSLabel deleteLabel = new KSLabel("[x]");
//               deleteLabel.addStyleName("KS-LOBuilder-Search-Link");
//               deleteLabel.addClickHandler(deleteHandler);
//               categoryTable.setWidget(row, col++, deleteLabel);
//               row++;
//               col = 0;                                
            }
        }

        public List<LoCategoryInfo> getValue() {
            return categories;
        }

        public void setValue(List<LoCategoryInfo> categories) {
            this.categories = categories;
            redraw();
        }

        @Override
        public void setValue(List<LoCategoryInfo> value, boolean fireEvents) {
            setValue(value);
        }


        public void removeItem(String text) {

            int a = text.indexOf(CATEGORY_TYPE_SEPARATOR);
            text = text.substring(0, a);

            int i = 0;
            for (LoCategoryInfo catInfo : categories) {
                String name = catInfo.getName();

                if (name.equals(text)) {
                    categories.remove(i);
                    fireChangeEvent();
                    break;
                }
                i++;
            }
            redraw();
        }

        public void addItem(LoCategoryInfo category) {
            categories.add(category);
            fireChangeEvent();
            redraw();
        }
    }

    public class CategoryDataParser implements DataHelper {

        public CategoryDataParser() {
        }

        ;

        @Override
        public String getKey(Data data) {
            return parse(data);
        }

        @Override
        public String parse(Data data) {
            String result = null;
            if (data != null) {
                LoCategoryInfo loCategoryInfo = CategoryDataUtil.toLoCategoryInfo(data);
                String typeName = "ERROR: uninitialized categoryTypeMap";
                if (null != categoryTypeMap) {
                    typeName = categoryTypeMap.get(loCategoryInfo.getTypeKey()).getName();
                }
                result = loCategoryInfo.getName() +
                        LOCategoryBuilder.LOCategoryListNew.CATEGORY_TYPE_SEPARATOR + typeName;
            } else {
                result = "";
            }
            return result;
        }

    }

    public class CreateLoCancelGroup extends ButtonGroup<LoCancelEnum> {
        public CreateLoCancelGroup(Callback<LoCancelEnum> callback) {
            layout = new ButtonRow();
            this.addCallback(callback);

            addButton(LoCancelEnum.CANCEL);
            addButtonToSecondaryGroup(LoCancelEnum.CREATE);

            this.initWidget(layout);
        }

        private void addButton(final LoCancelEnum type) {
            KSButton button = new KSButton(type.getText(), new ClickHandler() {

                @Override
                public void onClick(ClickEvent event) {
                    sendCallbacks(type);
                }
            });
            layout.addButton(button);
            buttonMap.put(type, button);
        }

        private void addButtonToSecondaryGroup(final LoCancelEnum type) {
            KSButton button = new KSButton(type.getText(), new ClickHandler() {

                @Override
                public void onClick(ClickEvent event) {
                    sendCallbacks(type);
                }
            });
            ((ButtonRow) layout).addButtonToSecondaryGroup(button);
            buttonMap.put(type, button);
        }
    }

    public static enum LoCancelEnum implements ButtonEnum {
        CREATE, CANCEL;

        @Override
        public ButtonEnum getActionType() {
            return CREATE;
        }

        @Override
        public ButtonEnum getCancelType() {
            return CANCEL;
        }

        @Override
        public ButtonStyle getStyle() {
            return ButtonStyle.PRIMARY;
        }

        @Override
        public String getText() {
            switch (this) {
                case CREATE:
                    return "Create";
                case CANCEL:
                    return "Cancel";
            }
            return null;
        }
    }

    @Override
    protected void onEnsureDebugId(String baseID) {
        super.onEnsureDebugId(baseID);
        picker.ensureDebugId(baseID);
        addButton.ensureDebugId(baseID + "-Add");
        browseCategoryLink.ensureDebugId(DebugIdUtils.createWebDriverSafeDebugId(baseID + "-" + browseCategoryLink.getText()));
    }
    
    
}
