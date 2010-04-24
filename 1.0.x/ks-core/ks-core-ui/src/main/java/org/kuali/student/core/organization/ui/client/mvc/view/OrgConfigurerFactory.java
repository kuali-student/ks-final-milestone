package org.kuali.student.core.organization.ui.client.mvc.view;

import java.util.List;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.HorizontalSection;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.layouts.ConfigurableLayout;
import org.kuali.student.common.ui.client.configurable.mvc.sections.VerticalSection;
import org.kuali.student.common.ui.client.configurable.mvc.views.SectionView;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue.Type;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSDatePicker;
import org.kuali.student.common.ui.client.widgets.KSTextBox;
import org.kuali.student.core.organization.dto.OrgInfo;
import org.kuali.student.core.organization.ui.client.configuration.OrgConstants;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;


public class OrgConfigurerFactory {

	public enum OrgSections {
		ORG_INFO, ORG_RELATIONS, POSITIONS, PERSON_RELATIONS,BROWSE_TREE,BROWSE_LIST,BROWSE_CHART,BROWSE_NAME,SEARCH
	}

	private static final String STYLE_SECTION_DIVIDER = "STYLE_SECTION_DIVIDER";
	private static final String STYLE_SECTION = "STYLE_SECTION";
	public static final String ORG_PROPOSAL_MODEL = "orgProposalModel";
	private static String groupName;
	
//	public static VerticalSection getOrgInfoSection(){
//        VerticalSection section = new VerticalSection();
//        section.addStyleName(STYLE_SECTION_DIVIDER);
//        section.addStyleName(STYLE_SECTION);
//        section.setSectionTitle(SectionTitle.generateH1Title("SomeTitle"));
//        section.addField(new FieldDescriptor("type", "Type", Type.STRING, new OrgTypePicker()));
//        section.addField(new FieldDescriptor("longName", "Name", Type.STRING));
//        section.addField(new FieldDescriptor("shortName", "Abbreviation", Type.STRING));
//        section.addField(new FieldDescriptor("longDesc", "Description", Type.STRING, new KSTextBox()));
//        section.addField(new FieldDescriptor("effectiveDate", "Effective Date", Type.DATE, new KSDatePicker()));
//        section.addField(new FieldDescriptor("expirationDate", "Expiration Date", Type.DATE, new KSDatePicker()));
//        return section;
//	}
	
	public static void configureOrgProposal(ConfigurableLayout layout){
	    groupName = OrgConstants.ORG_GROUP;
	    layout.addSection(new String[] {"Create", "Create"}, generateOrgCreateSection());
	    layout.addSection(new String[] {"Create", "Create"}, generatePositionCreateSection());
	    layout.addSection(new String[] {"Create", "Create"}, generateRelationCreateSection());
	    layout.addSection(new String[] {"Search", "Search & Modify"}, generateSearchSection());
	    layout.addSection(new String[] {"Browse", "Browse"}, generateBrowseTreeSection());
	    layout.addSection(new String[] {"Browse", "Browse"}, generateBrowseListSection());
	    layout.addSection(new String[] {"Browse", "Browse"}, generateBrowseChartSection());
	    layout.addSection(new String[] {"Browse", "Browse"}, generateBrowseNameSection());
	    
	}

	public static SectionView generateOrgCreateSection() {
        VerticalSectionView section = initSectionView(OrgSections.ORG_INFO, "Organization");
        // Cross-listed
        VerticalSection orgInfo = new VerticalSection();
        orgInfo.setSectionTitle(getH3Title(""));
        // crossListed.setInstructions("Enter Department and/or Subject Code/Course Number.");
        orgInfo.addField(new FieldDescriptor("orgInfo/orgType", getLabel(OrgConstants.ORG_TYPE_LABEL_KEY), Type.STRING, new OrgTypePicker()));
        orgInfo.addField(new FieldDescriptor("orgInfo/orgName", getLabel(OrgConstants.ORG_NAME_LABEL_KEY), Type.STRING));
        orgInfo.addField(new FieldDescriptor("orgInfo/orgAbbr", getLabel(OrgConstants.ORG_ABBR_LABEL_KEY), Type.STRING));
        orgInfo.addField(new FieldDescriptor("orgInfo/orgDesc", getLabel(OrgConstants.ORG_DESC_LABEL_KEY), Type.STRING));
        orgInfo.addField(new FieldDescriptor("orgInfo/effectiveDate", getLabel(OrgConstants.ORG_EFF_DATE_LABEL_KEY), Type.DATE, new KSDatePicker()));
        orgInfo.addField(new FieldDescriptor("orgInfo/expirationDate", getLabel(OrgConstants.ORG_EXP_DATE_LABEL_KEY), Type.DATE, new KSDatePicker()));
        
        orgInfo.addStyleName("KS-LUM-Section-Divider");
        // courseNumber.addSection(crossListed);
        section.addSection(orgInfo);
        return section;

    }

    public static SectionView generatePositionCreateSection() {
        VerticalSectionView section = initSectionView(OrgSections.POSITIONS, "Position");
        // Cross-listed
        VerticalSection orgPosInfo = new VerticalSection();
        orgPosInfo.setSectionTitle(getH3Title(""));
        orgPosInfo.addField(new FieldDescriptor("orgPosInfo/orgPersonRelationTypeKey", getLabel(OrgConstants.POS_RELATION_LABEL_KEY), Type.STRING, new OrgPositionTypePicker()));
        orgPosInfo.addField(new FieldDescriptor("orgPosInfo/title", getLabel(OrgConstants.POS_TITLE_LABEL_KEY), Type.STRING));
        orgPosInfo.addField(new FieldDescriptor("orgPosInfo/desc", getLabel(OrgConstants.POS_DESC_LABEL_KEY), Type.STRING));
        orgPosInfo.addField(new FieldDescriptor("orgPosInfo/minPpl", getLabel(OrgConstants.POS_MIN_PPL_LABEL_KEY), Type.STRING));
        orgPosInfo.addField(new FieldDescriptor("orgPosInfo/maxPpl", getLabel(OrgConstants.POS_MAX_PPL_LABEL_KEY), Type.STRING));
        
        orgPosInfo.addStyleName("KS-LUM-Section-Divider");

        section.addSection(orgPosInfo);
        return section;

    }

    public static SectionView generateRelationCreateSection() {
        
      
        VerticalSectionView section = initSectionView(OrgSections.ORG_RELATIONS, "Relationships/Links");
        // Cross-listed
        VerticalSection orgInfo = new VerticalSection();
        orgInfo.setSectionTitle(getH3Title(""));
        // crossListed.setInstructions("Enter Department and/or Subject Code/Course Number.");
        orgInfo.addField(new FieldDescriptor("orgOrgRelInfo/orgOrgName",  getLabel(OrgConstants.REL_ORGNAME_LABEL_KEY), Type.STRING, new OrgButtonPicker()));
        orgInfo.addField(new FieldDescriptor("orgOrgRelInfo/orgOrgId",  getLabel(OrgConstants.REL_ORGID_LABEL_KEY), Type.STRING));
        orgInfo.addField(new FieldDescriptor("orgOrgRelInfo/orgOrgRelType",  getLabel(OrgConstants.REL_TYPE_LABEL_KEY), Type.STRING, new OrgRelationTypePicker()));
        orgInfo.addField(new FieldDescriptor("orgOrgRelInfo/orgOrgEffDate",  getLabel(OrgConstants.REL_EFF_DATE_LABEL_KEY), Type.STRING));
        orgInfo.addField(new FieldDescriptor("orgOrgRelInfo/orgOrgExpDate",  getLabel(OrgConstants.REL_EXP_DATE_LABEL_KEY), Type.STRING));
        orgInfo.addField(new FieldDescriptor("orgOrgRelInfo/orgOrgNote",  getLabel(OrgConstants.REL_NOTE_LABEL_KEY), Type.STRING));
        orgInfo.addStyleName("KS-LUM-Section-Divider");
        
        // courseNumber.addSection(crossListed);
        section.addSection(orgInfo);
        return section;

    }
    
    public static SectionView generateSearchSection() {
        VerticalSectionView section = initSectionView(OrgSections.SEARCH, "Search & Modify");
        // Cross-listed
        VerticalSection orgInfo = new VerticalSection();
        orgInfo.setSectionTitle(getH3Title(""));
        // crossListed.setInstructions("Enter Department and/or Subject Code/Course Number.");
        orgInfo.addField(new FieldDescriptor("cluInfo/alternateIdentifiers", "Label45", Type.STRING));
        orgInfo.addStyleName("KS-LUM-Section-Divider");
        // courseNumber.addSection(crossListed);
        section.addSection(orgInfo);
        return section;

    }
    
    public static SectionView generateBrowseTreeSection() {
        VerticalSectionView section = initSectionView(OrgSections.BROWSE_TREE, "by Tree");
        // Cross-listed
        VerticalSection orgInfo = new VerticalSection();
        orgInfo.setSectionTitle(getH3Title(""));
        // crossListed.setInstructions("Enter Department and/or Subject Code/Course Number.");
        orgInfo.addField(new FieldDescriptor("cluInfo/alternateIdentifiers", "Label45", Type.STRING));
        orgInfo.addStyleName("KS-LUM-Section-Divider");
        // courseNumber.addSection(crossListed);
        section.addSection(orgInfo);
        return section;

    }
    
    public static SectionView generateBrowseListSection() {
        VerticalSectionView section = initSectionView(OrgSections.BROWSE_LIST, "by List");
        // Cross-listed
        VerticalSection orgInfo = new VerticalSection();
        orgInfo.setSectionTitle(getH3Title(""));
        // crossListed.setInstructions("Enter Department and/or Subject Code/Course Number.");
        orgInfo.addField(new FieldDescriptor("cluInfo/alternateIdentifiers", "Label45", Type.STRING));
        orgInfo.addStyleName("KS-LUM-Section-Divider");
        // courseNumber.addSection(crossListed);
        section.addSection(orgInfo);
        return section;

    }
    
    public static SectionView generateBrowseChartSection() {
        VerticalSectionView section = initSectionView(OrgSections.BROWSE_CHART, "by Chart");
        // Cross-listed
        VerticalSection orgInfo = new VerticalSection();
        orgInfo.setSectionTitle(getH3Title(""));
        // crossListed.setInstructions("Enter Department and/or Subject Code/Course Number.");
        orgInfo.addField(new FieldDescriptor("cluInfo/alternateIdentifiers", "Label45", Type.STRING));
        orgInfo.addStyleName("KS-LUM-Section-Divider");
        // courseNumber.addSection(crossListed);
        section.addSection(orgInfo);
        return section;

    }
    
    public static SectionView generateBrowseNameSection() {
        VerticalSectionView section = initSectionView(OrgSections.BROWSE_NAME, "by Name");
        // Cross-listed
        VerticalSection orgInfo = new VerticalSection();
        orgInfo.setSectionTitle(getH3Title(""));
        // crossListed.setInstructions("Enter Department and/or Subject Code/Course Number.");
        orgInfo.addField(new FieldDescriptor("cluInfo/alternateIdentifiers", "Label45", Type.STRING));
        orgInfo.addStyleName("KS-LUM-Section-Divider");
        // courseNumber.addSection(crossListed);
        section.addSection(orgInfo);
        return section;

    }
	
    private static VerticalSectionView initSectionView (Enum<?> viewEnum, String labelKey) {
        VerticalSectionView section = new VerticalSectionView(viewEnum, labelKey, ORG_PROPOSAL_MODEL);
        section.addStyleName("KS-LUM-Section");
        section.setSectionTitle(getH1Title(labelKey));
        return section;

    }
	
    private static VerticalSection initSection(SectionTitle title, boolean withDivider) {
        VerticalSection section = new VerticalSection();
        if (title !=  null) {
          section.setSectionTitle(title);
        }
        section.addStyleName("KS-LUM-Section");
        if (withDivider)
            section.addStyleName("KS-LUM-Section-Divider");
        return section;
    }
    
    private static String getLabel(String labelKey) {
        return Application.getApplicationContext().getUILabel(groupName, "org", "draft", labelKey);
    }
    
    private static SectionTitle getH1Title(String labelKey) {
        return SectionTitle.generateH1Title(getLabel(labelKey));
    } 
    private static SectionTitle getH3Title(String labelKey) {
        return SectionTitle.generateH3Title(getLabel(labelKey));
    } 
    

}
