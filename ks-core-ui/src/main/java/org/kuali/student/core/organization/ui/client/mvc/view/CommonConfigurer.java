package org.kuali.student.core.organization.ui.client.mvc.view;

import java.util.List;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.layouts.ConfigurableLayout;
import org.kuali.student.common.ui.client.configurable.mvc.multiplicity.MultiplicityItem;
import org.kuali.student.common.ui.client.configurable.mvc.multiplicity.RemovableItem;
import org.kuali.student.common.ui.client.configurable.mvc.multiplicity.RemovableItemWithHeader;
import org.kuali.student.common.ui.client.configurable.mvc.multiplicity.UpdatableMultiplicityComposite;
import org.kuali.student.common.ui.client.configurable.mvc.multiplicity.MultiplicityComposite.StyleType;
import org.kuali.student.common.ui.client.configurable.mvc.sections.BaseSection;
import org.kuali.student.common.ui.client.configurable.mvc.sections.GroupSection;
import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.common.ui.client.configurable.mvc.sections.VerticalSection;
import org.kuali.student.common.ui.client.configurable.mvc.views.SectionView;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.DataModelDefinition;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSTextBox;
import org.kuali.student.common.ui.client.widgets.buttons.KSLinkButton;
import org.kuali.student.common.ui.client.widgets.buttons.KSLinkButton.ButtonStyle;
import org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract;
import org.kuali.student.common.ui.client.widgets.list.ListItems;
import org.kuali.student.common.ui.client.widgets.search.AdvancedSearchWindow;
import org.kuali.student.common.ui.client.widgets.search.SearchPanel;
import org.kuali.student.common.ui.client.widgets.search.SelectedResults;
import org.kuali.student.common.ui.client.widgets.search.SuggestBoxWAdvSearch;
import org.kuali.student.core.assembly.data.LookupMetadata;
import org.kuali.student.core.assembly.data.LookupParamMetadata;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.assembly.data.QueryPath;
import org.kuali.student.core.organization.ui.client.configuration.OrgConstants;
import org.kuali.student.core.organization.ui.client.mvc.model.FieldInfo;
import org.kuali.student.core.organization.ui.client.mvc.model.MultipleFieldInfoImpl;
import org.kuali.student.core.organization.ui.client.mvc.model.SectionConfigInfo;
import org.kuali.student.core.organization.ui.client.mvc.model.SectionViewInfo;
import org.kuali.student.core.organization.ui.client.mvc.view.OrgConfigurerFactory.OrgSections;
import org.kuali.student.core.organization.ui.client.service.OrgRpcService;
import org.kuali.student.core.organization.ui.client.service.OrgRpcServiceAsync;


import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class CommonConfigurer {

    private String sectionName;
    private String modelName;
    private DataModelDefinition modelDefinition;
    private SectionConfigInfo sectionConfigInfo;
    private static String groupName;
    private boolean WITH_DIVIDER = true;
    private boolean NO_DIVIDER = false;
    public static final String ORG_PROPOSAL_MODEL = "orgProposalModel";
    public static final String ORG_SEARCH = "searchOrgs";
    public PositionTable positionTable;
    public OrgPersonRelationTypePicker personRelationPicker;
    private String orgId;
    public static final String ORG_INFO_PATH                  = "orgInfo";
    public static final String POSITION_PATH                  = "OrgPositionRestrictionInfo";
    public static final String PERSON_PATH                  = "orgPersonRelationInfo";
    public static final String ORGORG_PATH                  = "orgOrgRelationInfo";
    
    public enum SectionsEnum {
        ORG_INFO, ORG_RELATIONS, POSITIONS, PERSON_RELATIONS,BROWSE_TREE,BROWSE_LIST,BROWSE_CHART,BROWSE_NAME,SEARCH
    }
    
    public void setModelDefinition(DataModelDefinition modelDefinition){
        this.modelDefinition = modelDefinition;
    }
    
    public void setSectionConfigInfo(SectionConfigInfo sectionConfigInfo){
        this.sectionConfigInfo=sectionConfigInfo;
    }
    
    public void setOrgId(String orgId){
        this.orgId=orgId;
    
    }
    
    public String getOrgId(){
        return orgId;
    }
    
    /**
     * The method will create all the sections as defined in the screen config XML
     * @param layout
     */
    public void configureOrgProposal(ConfigurableLayout layout){
        List<SectionViewInfo> sectionViewInfoList;
        sectionViewInfoList = sectionConfigInfo.getSectionViewInfoList();
        for(SectionViewInfo sectionViewInfo:sectionViewInfoList){
          layout.addSection(new String[] {sectionViewInfo.getTab(),sectionViewInfo.getMenu()}, generateSection(sectionViewInfo));
//          layout.addStartSection(generateSection(sectionViewInfo));
        }
        
//        layout.addSection(new String[] {"Create", "Create"}, generateOrgCreateSection());
    }
    
    /**
     * The method will parse through the section parent tag and read all the field declarations.
     * @param sectionViewInfo
     * @return
     */
    private SectionView generateSection(SectionViewInfo sectionViewInfo){
        try {
        String tempTestEnum = sectionViewInfo.getSectionEnum();
        VerticalSectionView section = initSectionView(SectionsEnum.valueOf(sectionViewInfo.getSectionEnum()), sectionViewInfo.getSectionName());
//        VerticalSectionView section = initSectionView(SectionsEnum.ORG_INFO, sectionViewInfo.getSectionName());
        VerticalSection orgInfo = initSection(getH3Title(sectionViewInfo.getSectionName()), WITH_DIVIDER);   
        orgInfo.setSectionTitle(getH3Title(""));
        groupName="org";
        List<FieldInfo> fieldList = sectionViewInfo.getfields();
        if (fieldList != null) {
                for (FieldInfo field : fieldList) {
                    // Parse through all the field declarations and add specific behaviors for non text fields.
                    if (field instanceof MultipleFieldInfoImpl) {
                        List<FieldInfo> fieldMultiList = ((MultipleFieldInfoImpl) field).getFields();

                        // Add multiplicity widget
                        addField(orgInfo, field.getKey(), getLabel(field.getLabel()), new CommonMultiplicityList("Add", " ", fieldMultiList, field.getKey()));

                    } else {
                        // Initialize the Widget picker with specific widgets like pickers, drop-downs, etc
                        Widget widget = null;
                        if (field.getWidget() != null) {
                            if (field.getWidget().equals("OrgLocateTree")) {
                                widget = new OrgTree(section);
                            }
                            if (field.getWidget().equals("PositionTable")) {
                                positionTable = new PositionTable();
                                DOM.setElementAttribute(positionTable.getElement(), "id", "orgPositionsTable");
                                widget = positionTable; 
                            }

                        }
                        addField(orgInfo, field.getKey(), getLabel(field.getLabel()), widget);
                    }
                }
            }
//        for(MultipleField multipleField: fieldList){
//            
//        }
        orgInfo.addStyleName("KS-CORE-Section-Divider");
        // courseNumber.addSection(crossListed);
        section.addSection(orgInfo);
        //section.addSection(orgInfo);
        return section; 
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    
    private static VerticalSection initSection(SectionTitle title, boolean withDivider) {
        VerticalSection section = new VerticalSection();
        if (title !=  null) {
          section.setSectionTitle(title);
        }
        section.addStyleName("KS-CORE-Section");
        if (withDivider)
            section.addStyleName("KS-CORE-Section-Divider");
        return section;
    }
    
    private static VerticalSectionView initSectionView (Enum<?> viewEnum, String labelKey) {
        VerticalSectionView section = new VerticalSectionView(viewEnum, labelKey, ORG_PROPOSAL_MODEL);
        section.addStyleName("KS-CORE-Section");
        section.setSectionTitle(getH1Title(labelKey));
        return section;
    }
    private static SectionTitle getH1Title(String labelKey) {
        return SectionTitle.generateH1Title(getLabel(labelKey));
    } 
    private static SectionTitle getH3Title(String labelKey) {
        return SectionTitle.generateH3Title(getLabel(labelKey));
    } 
    private static String getLabel(String labelKey) {
        return Application.getApplicationContext().getUILabel(groupName, "org", "draft", labelKey);
    }
    
    
    private void addField(Section section, String fieldKey, String fieldLabel) {
        addField(section, fieldKey, fieldLabel, null);
    }
    private void addField(Section section, String fieldKey, String fieldLabel, Widget widget) {
        addField(section, fieldKey, fieldLabel, widget, null);
    }
    private void addField(Section section, String fieldKey, String fieldLabel, Widget widget, String parentPath) {
        QueryPath path = QueryPath.concat(parentPath, fieldKey);
        Metadata meta = modelDefinition.getMetadata(path);
        
        FieldDescriptor fd = new FieldDescriptor(path.toString(), fieldLabel, meta);
        if (widget != null) {
            fd.setFieldWidget(widget);
        }
        section.addField(fd);
    }
    
    /**
     * 
     * This class will define the common implementation for adding a multiplicity widget.
     * It initializes a list of fieldInfo that is read from the screen config xml. 
     * The parent query path is also passed.
     * @author Kuali Student Team
     *
     */
    public class CommonMultiplicityList extends UpdatableMultiplicityComposite {
        private  List<FieldInfo> fieldList;
        private String parentPath;
        
        public CommonMultiplicityList(String addItemLabel, String itemLabel,List<FieldInfo> fieldList,String parentPath)
        {
        	super(StyleType.TOP_LEVEL);
            setAddItemLabel(getLabel(addItemLabel));
            setItemLabel(getLabel(itemLabel));
            this.fieldList=fieldList;
            this.parentPath=parentPath;
        }

        @Override
        public MultiplicityItem getItemDecorator(StyleType style) {
            RemovableItemWithHeader item = new RemovableItemWithHeader();
            item.setItemLabel(itemLabel);            
            
            if(parentPath.equals(POSITION_PATH)){
                QueryPath qPath = QueryPath.concat(null, POSITION_PATH);
                Metadata meta = modelDefinition.getMetadata(qPath);
                if(!meta.isCanEdit()){
                    item.isReadOnly(true);
                }
                else{
                    item.isReadOnly(false);
                }
            }
            if(parentPath.equals(ORGORG_PATH)){
                QueryPath qPath = QueryPath.concat(null, ORGORG_PATH);
                Metadata meta = modelDefinition.getMetadata(qPath);
                if(!meta.isCanEdit()){
                    item.isReadOnly(true);
                }
                else{
                    item.isReadOnly(false);
                }
            }
            if(parentPath.equals(PERSON_PATH)){
                QueryPath qPath = QueryPath.concat(null, PERSON_PATH);
                Metadata meta = modelDefinition.getMetadata(qPath);
                if(!meta.isCanEdit()){
                    item.isReadOnly(true);
                }
                else{
                    item.isReadOnly(false);
                }
            }
            return item;
        }
        
        public Widget generateAddWidget() {
            //Label addWidget =  new Label(addItemLabel);
            //addWidget.addStyleName("KS-Multiplicity-Link-Label");
            KSLinkButton addWidget;
            if(style == StyleType.TOP_LEVEL){
                addWidget = new KSLinkButton(addItemLabel, ButtonStyle.FORM_LARGE);
            }
            else{
                addWidget = new KSLinkButton(addItemLabel, ButtonStyle.FORM_SMALL);
            }
            addWidget.addClickHandler(new ClickHandler(){
                public void onClick(ClickEvent event) {
                    addItem();
                }            
            });
            
            if(parentPath.equals(POSITION_PATH)){
                QueryPath qPath = QueryPath.concat(null, POSITION_PATH);
                Metadata meta = modelDefinition.getMetadata(qPath);
                if(!meta.isCanEdit()){
                    addWidget.setEnabled(false);
                }
                else{
                    addWidget.setEnabled(true);
                }
            }
            if(parentPath.equals(ORGORG_PATH)){
                QueryPath qPath = QueryPath.concat(null, ORGORG_PATH);
                Metadata meta = modelDefinition.getMetadata(qPath);
                if(!meta.isCanEdit()){
                    addWidget.setEnabled(false);
                }
                else{
                    addWidget.setEnabled(true);
                }
            }
            if(parentPath.equals(PERSON_PATH)){
                QueryPath qPath = QueryPath.concat(null, PERSON_PATH);
                Metadata meta = modelDefinition.getMetadata(qPath);
                if(!meta.isCanEdit()){
                    addWidget.setEnabled(false);
                }
                else{
                    addWidget.setEnabled(true);
                }
            }
            return addWidget;
        }

        private boolean readPermission(String path){
            QueryPath qPath = QueryPath.concat(null, path);
            Metadata meta = modelDefinition.getMetadata(qPath);
            return meta.isCanEdit();
        }
        @Override
        public Widget createItem() {
            String path = QueryPath.concat(parentPath, String.valueOf(itemCount-1)).toString();
            GroupSection ns = new GroupSection();
            //ns.setCurrentFieldLabelType(FieldLabelType.LABEL_TOP);
            for (FieldInfo field : fieldList) {
                
//                if(path.equals("orgPersonRelationInfo" + "/" + String.valueOf(itemCount-1).toString())){
//                    if(field.getKey().equals("type")){
//                        QueryPath metaPath = QueryPath.concat(null, "orgPersonRelationInfo" + "/" + "*" + "/" + "type");
//                        Metadata meta = modelDefinition.getMetadata(metaPath);
//                        LookupMetadata lookup = meta.getInitialLookup();
//                        LookupParamMetadata parammeta = lookup.getParams().get(0);
//                        parammeta.setDefaultValueString(orgId);
//                    }
//                }
                Widget widget = null;
                //Define specific widgets as they are defined in the xml config.
                if (field.getWidget() != null) {
                    QueryPath qPath = QueryPath.concat(path, field.getKey());
                    Metadata meta = modelDefinition.getMetadata(qPath);
                    if (field.getWidget().equals("OrgRelationTypePicker")) {

                        if(meta.isCanEdit()){
                            widget = new OrgRelationTypePicker();

                        }
                        else{
//                            final OrgRelationTypePicker picker = new OrgRelationTypePicker();
//                            picker.onLoad();
//                            ListItems list = picker.getListItems();
//                            if(list==null){
//                                picker.addWidgetReadyCallback(new Callback<Widget>() {
//                                    @Override
//                                    public void exec(Widget widget) {
//                                        final ListItems searchResults = picker.getListItems();
//                                        String value =searchResults.getItemText("REV_kuali.org.CurriculumChild");
//                                        
//                                    }      
//                                });
//                                
//                            }
                            widget = new KSLabel();
                        }
                        
                    }
                    
                    if (field.getWidget().equals("OrgPositionTypePicker")) {
                        
                        widget = new OrgPositionTypePicker();
                    }
                    if (field.getWidget().equals("OrgPersonRelationTypePicker")) {
                        if(meta.isCanEdit()){
                        personRelationPicker = new OrgPersonRelationTypePicker();
                        personRelationPicker.setOrgId(orgId);
                        widget = personRelationPicker;
                        }
                        else{
                            widget = new KSLabel();
                        }
                        
                    }
                       
                        
                }
                addField(ns, field.getKey(), getLabel(field.getLabel()),widget,path);    
            }
            return ns;
        }
    }
    
    
}
