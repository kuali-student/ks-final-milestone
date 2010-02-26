package org.kuali.student.core.organization.ui.client.mvc.view;

import java.util.List;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.layouts.ConfigurableLayout;
import org.kuali.student.common.ui.client.configurable.mvc.multiplicity.MultiplicityItem;
import org.kuali.student.common.ui.client.configurable.mvc.multiplicity.RemovableItem;
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
import org.kuali.student.common.ui.client.widgets.KSTextBox;
import org.kuali.student.common.ui.client.widgets.search.AdvancedSearchWindow;
import org.kuali.student.common.ui.client.widgets.search.SearchPanel;
import org.kuali.student.common.ui.client.widgets.search.SelectedResults;
import org.kuali.student.common.ui.client.widgets.search.SuggestBoxWAdvSearch;
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
import org.kuali.student.core.organization.ui.client.view.OrgLocateTree;
import org.kuali.student.core.organization.ui.client.view.OrgUpdatePanel;

import com.google.gwt.core.client.GWT;
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
                            if (field.getWidget().equals("OrgTypePicker")) {
                                widget = new OrgTypePicker();
                            }
                            if (field.getWidget().equals("OrgUpdatePanel")) {
//                                widget = new OrgUpdatePanel();
                                widget = getOrgAdvanceSearch();
                            }
                            if (field.getWidget().equals("OrgLocateTree")) {
                                widget = new OrgLocateTree(section);
                            }
                            if (field.getWidget().equals("PositionTable")) {
                                positionTable = new PositionTable();
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
            org.kuali.student.common.ui.client.configurable.mvc.sections.RemovableItemWithHeader item = new org.kuali.student.common.ui.client.configurable.mvc.sections.RemovableItemWithHeader(style);
            item.setItemLabel(itemLabel);            
            return item;
        }

        @Override
        public Widget createItem() {
            String path = QueryPath.concat(parentPath, String.valueOf(itemCount-1)).toString();
            GroupSection ns = new GroupSection();
            //ns.setCurrentFieldLabelType(FieldLabelType.LABEL_TOP);
            for (FieldInfo field : fieldList) {
                Widget widget = null;
                //Define specific widgets as they are defined in the xml config.
                if (field.getWidget() != null) {
                    if (field.getWidget().equals("OrgRelationTypePicker")) {
                        widget = new OrgRelationTypePicker();
                    }
                    if (field.getWidget().equals("OrgPositionTypePicker")) {
                        widget = new OrgPositionTypePicker();
                    }
                    if (field.getWidget().equals("OrgButtonPicker")) {
                        widget = new OrgButtonPicker();
                    }
                    if (field.getWidget().equals("OrgPersonRelationTypePicker")) {
                        personRelationPicker = new OrgPersonRelationTypePicker();
                        personRelationPicker.setOrgId(orgId);
                        widget = personRelationPicker;
                    }
                }
                addField(ns, field.getKey(), getLabel(field.getLabel()),widget,path);    
            }
            return ns;
        }
    }
    
    /**
     * Temporary method to return Org Advance Search 
     */
    private Widget getOrgAdvanceSearch(){
        Metadata searchMetadata = modelDefinition.getMetadata(QueryPath.parse("orgSearchInfo"));  //no type or state at this point
        SearchPanel organization = new SearchPanel(searchMetadata.getProperties().get(ORG_SEARCH).getInitialLookup());                
        final AdvancedSearchWindow orgSearchWindow = new AdvancedSearchWindow("Organization", organization);
        KSTextBox orgTextBox = new KSTextBox();   //FIXME this will be suggest box
        final SuggestBoxWAdvSearch adminDepPicker = new SuggestBoxWAdvSearch(orgTextBox, orgSearchWindow);  
        
        orgSearchWindow.addSelectionCompleteCallback(new Callback<List<SelectedResults>>(){
            public void exec(List<SelectedResults> results) {
                if (results.size() > 0){
                    adminDepPicker.getSuggestBox().setText(results.get(0).getDisplayKey());
                    adminDepPicker.getSuggestBox().setValue(results.get(0).getReturnKey());
                    orgSearchWindow.hide();
                }
            }
        });               
        return adminDepPicker;
    }
    
}
