package org.kuali.student.ui.personidentity.client.view;

import java.util.List;
import java.util.Vector;

import org.kuali.student.commons.ui.mvc.client.ApplicationContext;
import org.kuali.student.commons.ui.widgets.DateBox;
import org.kuali.student.ui.personidentity.client.model.GwtPersonInfo;
import org.kuali.student.ui.personidentity.client.model.GwtPersonNameInfo;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @author Garey
 */
public class PersonInfoWidget extends Composite {

    private GwtPersonInfo pInfo = null;

    //TextArea updateComment = new TextArea();
    //TextArea createUserComment = new TextArea();
    
    EditLabel updateComment = new EditLabel(new TextArea(), false);
    EditLabel createUserComment = new EditLabel(new TextArea(), false);
    
    LabelWidget lblWgt = null;
    VerticalPanel rootPanel = new VerticalPanel();
    PersonNameInfoWidget pNameWidget = new PersonNameInfoWidget();
    PersonCitizenshipInfoWidget pCitzInfoWidget = new PersonCitizenshipInfoWidget();

    VerticalPanel adminContainer = new VerticalPanel();

    // DisclosurePanel discNameInfo = new DisclosurePanel("Name", true);
    // DisclosurePanel discNameInfo = new DisclosurePanel(I18N.i18nConstant.name(), true);
    DisclosurePanel discNameInfo = new DisclosurePanel(ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("name"), true);
    
    // DisclosurePanel discCitzInfo = new DisclosurePanel("Citizenship Details", true);
    // DisclosurePanel discCitzInfo = new DisclosurePanel(I18N.i18nConstant.citizenshipDetails(), true);
    DisclosurePanel discCitzInfo = new DisclosurePanel(ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("citizenshipDetails"), true);

    // DisclosurePanel discBioInfo = new DisclosurePanel("Biographic Details", true);
    // DisclosurePanel discBioInfo = new DisclosurePanel(I18N.i18nConstant.citizenshipDetails(), true);
    DisclosurePanel discBioInfo = new DisclosurePanel(ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("biographicDetails"), true);

    // DisclosurePanel discAdminInfo = new DisclosurePanel("Admin Details", true);
    // DisclosurePanel discAdminInfo = new DisclosurePanel(I18N.i18nConstant.adminDetails(), true);
    DisclosurePanel discAdminInfo = new DisclosurePanel(ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("adminDetails"), true);

    ListBox personTypes = new ListBox();
    EditLabel dob = new EditLabel(new DateBox(), false);
    Label personId = new Label();
    SearchListBox gender = new SearchListBox();
    EditLabel genderEdit = new EditLabel(gender, false);
    

    VerticalPanel vpBioData = new VerticalPanel();
    FlexTable fTable = new FlexTable();

    // Label lDob = new Label("Date of Birth");
    // Label lDob = new Label(I18N.i18nConstant.dateOfBirth());
    Label lDob = new Label(ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("dateOfBirth"));

    // Label lPersonId = new Label("Student Number");
    // Label lPersonId = new Label(I18N.i18nConstant.studentNumber());
    Label lPersonId = new Label(ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("studentNumber"));
    // Label lGender = new Label("Gender");
    // Label lGender = new Label(I18N.i18nConstant.gender());
    Label lGender = new Label(ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("gender"));

    LabelWidget lwCreateComment = null;

    boolean isEditable = false;

    public void setEditable(boolean editable) {
        this.isEditable = editable;
        this.displayEditable();
    }

    protected void displayEditable() {
        pNameWidget.setEditable(this.isEditable);
        dob.setIsEditable(this.isEditable);
        genderEdit.setIsEditable(this.isEditable);
        pCitzInfoWidget.setEditable(this.isEditable);        
        updateComment.setIsEditable(this.isEditable);
        createUserComment.setIsEditable(this.isEditable);
    }

    public GwtPersonInfo getPersonObj() {
        GwtPersonInfo pInfo = new GwtPersonInfo();

        Vector<GwtPersonNameInfo> vNameList = new Vector<GwtPersonNameInfo>();

        if (dob.getText() != null && !dob.getText().equals(""))
            pInfo.setBirthDate(DateBox.formatter.parse(dob.getText()));
        pInfo.setGender(gender.getValue(gender.getSelectedIndex()).charAt(0));

        vNameList.add(pNameWidget.getNameInfoObj());
        pInfo.setName(vNameList);
        pInfo.setCitizenship(pCitzInfoWidget.getPersonCitzInfoObj());
        pInfo.setUpdateUserComment(updateComment.getText());
        pInfo.setCreateUserComment(createUserComment.getText());
        pInfo.setPersonId(personId.getText());

        return pInfo;
    }

    public PersonInfoWidget(final GwtPersonInfo pInfo) {

        this.pInfo = pInfo;

        setup();

        updateView(this.pInfo);
        initWidget(rootPanel);

    }

    protected void setup() {

        rootPanel.setWidth("75%");
        // We do not have this information
        // rootPanel.add(personTypes);

        discNameInfo.setWidth("100%");
        
        // discNameInfo.setTitle(I18N.i18nConstant.name());
        discNameInfo.setTitle(ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("name"));
        //discNameInfo.addStyleName("KS-CreatePerson-Sectionbar");
        discNameInfo.addStyleName("KS-CitzInfo-Panel");
        
        
        discNameInfo.add(pNameWidget);

        fTable.setWidget(0, 0, lPersonId);
        //personId.setReadOnly(true);
        fTable.setWidget(0, 1, personId);

        fTable.setWidget(1, 0, lDob);
        fTable.setWidget(1, 1, dob);

        fTable.setWidget(2, 0, lGender);

        // gender.addItem("Male", "M");
        // gender.addItem(I18N.i18nConstant.male(), "M");
        gender.addItem(ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("male"), "M");

        // gender.addItem("Female", "F");
        // gender.addItem(I18N.i18nConstant.female(), "F");
        gender.addItem(ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("female"), "F");
        fTable.setWidget(2, 1, genderEdit);

        fTable.addStyleName("KS-Label");
        
        discBioInfo.setWidth("100%");
        discBioInfo.add(fTable);

        discCitzInfo.setWidth("100%");
        // discCitzInfo.setTitle("Citizenship Details");
        discCitzInfo.setTitle(ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("citizenshipDetails"));
        discCitzInfo.add(pCitzInfoWidget);

        // lwCreateComment = new LabelWidget("Create Comment", createUserComment);
        // lwCreateComment = new LabelWidget(I18N.i18nConstant.createComment(), createUserComment);
        lwCreateComment = new LabelWidget(ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("createComment"), createUserComment);

        lwCreateComment.getLabel().setWidth("30%");
        lwCreateComment.setLabelStyleName("KS-CreateUserComment-Label");
        lwCreateComment.addStyleName("KS-Label");
        
        // lblWgt = new LabelWidget("Update Comments: ", updateComment);
        // lblWgt = new LabelWidget(I18N.i18nConstant.updateComments(), updateComment);
        lblWgt = new LabelWidget(ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("updateComments"), updateComment);
        lblWgt.addStyleName("KS-UpdateComments-Widget");
        lblWgt.getLabel().setWidth("30%");
        lblWgt.setLabelStyleName("KS-UpdateComments-Label");

        adminContainer.add(lblWgt); // remove it for now
        adminContainer.add(lwCreateComment);
        adminContainer.setWidth("100%");
        discAdminInfo.setWidth("100%");
        // discAdminInfo.setTitle("Admin Details");
        // discAdminInfo.setTitle(I18N.i18nConstant.adminDetails());
        discAdminInfo.setTitle(ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("adminDetails"));
        discAdminInfo.addStyleName("KS-AdminInfo-Panel");
        discAdminInfo.add(adminContainer);

        rootPanel.add(discNameInfo);
        rootPanel.add(discBioInfo);
        rootPanel.add(discCitzInfo);
       // rootPanel.add(discAdminInfo);

        rootPanel.addStyleName("KS-PersonIdentity-Panel");
        personTypes.addStyleName("KS-PersonTypes-ListBox");

        //discNameInfo.addStyleName("KS-NameInfo-Panel");
        discNameInfo.addStyleName("KS-CitzInfo-Panel");
        discCitzInfo.addStyleName("KS-CitzInfo-Panel");
        //discBioInfo.addStyleName("KS-BioInfo-Panel");
        discBioInfo.addStyleName("KS-CitzInfo-Panel");

        dob.addStyleName("KS-DOB-TextBox");
        personId.addStyleName("KS-Label");
        gender.addStyleName("KS-Gender-SearchListBox");

        lDob.addStyleName("KS-DOB-Label");
        lPersonId.addStyleName("KS-PersonId-Label");
        lGender.addStyleName("KS-Gender-Label");

        lwCreateComment.addStyleName("KS-CreateUserComment-Widget");
    }

    protected void setupStyles() {

    }

    public void updateView(GwtPersonInfo pInfo) {
        if (pInfo != null) {

            if (pInfo.getBirthDate() != null)
                this.dob.setText(DateBox.formatter.format(pInfo.getBirthDate()));

            this.createUserComment.setText(pInfo.getCreateUserComment());
            this.updateComment.setText(pInfo.getUpdateUserComment());
            this.gender.setItemSelected(String.valueOf(pInfo.getGender()), true);
            this.updateComment.addStyleName("KS-Label");
            
            List<GwtPersonNameInfo> l = pInfo.getName();

            if (l != null && l.size() > 0)
                this.pNameWidget.populateFields(l.get(0));
            else
                this.pNameWidget.populateFields(null);

            this.pCitzInfoWidget.populateFields(pInfo.getCitizenship());
            this.personId.setText(pInfo.getPersonId());
            // this.setVisible(true);
        } else {
            // this.setVisible(false);
        }
    }
}
