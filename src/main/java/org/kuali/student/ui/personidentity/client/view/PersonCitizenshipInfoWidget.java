package org.kuali.student.ui.personidentity.client.view;

import org.kuali.student.commons.ui.mvc.client.ApplicationContext;
import org.kuali.student.commons.ui.widgets.DateBox;
import org.kuali.student.ui.personidentity.client.model.GwtPersonCitizenshipInfo;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;

public class PersonCitizenshipInfoWidget extends Composite {

    FlexTable fTable = new FlexTable();

    SearchListBox countryOfCitizenshipCode = new SearchListBox();
    SearchListBox citizenStatusCode = new SearchListBox();
    
    EditLabel   editCountryOfCitizenshipCode = new EditLabel(countryOfCitizenshipCode, false);
    EditLabel   editCitizenStatusCode = new EditLabel(citizenStatusCode, false);
    EditLabel effectiveStartDate = new EditLabel(new DateBox(), false);
    EditLabel effectiveEndDate = new EditLabel(new DateBox(), false);

    // Label lCountryOfCitizenshipCode = new Label("Country of Citizenship");
    // Label lCountryOfCitizenshipCode = new Label(I18N.i18nConstant.countryOfCitizenship());
    Label lCountryOfCitizenshipCode = new Label(ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("countryOfCitizenship"));

    // Label lCitizenStatusCode = new Label("Citizenship Status");
    // Label lCitizenStatusCode = new Label(I18N.i18nConstant.citizenshipStatus());
    Label lCitizenStatusCode = new Label(ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("citizenshipStatus"));

    // Label lCitizenEffective = new Label("Citizenship Effective");
    // Label lCitizenEffective = new Label(I18N.i18nConstant.citizenshipEffective());
    Label lCitizenEffectiveStartDate = new Label(ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("citizenshipEffectiveStartDate"));
    Label lCitizenEffectiveEndDate = new Label(ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("citizenshipEffectiveEndDate"));

    public PersonCitizenshipInfoWidget() {
        this.arrangeWidgets();
        this.populateCountryCodes();
        this.populateStatusCodes();
        this.initWidget(fTable);

    }

    public GwtPersonCitizenshipInfo getPersonCitzInfoObj() {
        GwtPersonCitizenshipInfo pInfo = new GwtPersonCitizenshipInfo();

        pInfo.setCitizenStatusCode(citizenStatusCode.getSelectedValue());
        pInfo.setCountryOfCitizenshipCode(countryOfCitizenshipCode.getSelectedValue());
        try {
            if (!"".equals(effectiveEndDate.getText()))
                pInfo.setEffectiveEndDate(DateBox.formatter.parse(effectiveEndDate.getText()));
            if (!"".equals(effectiveStartDate.getText()))
                pInfo.setEffectiveStartDate(DateBox.formatter.parse(effectiveStartDate.getText()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return pInfo;
    }

    public PersonCitizenshipInfoWidget(GwtPersonCitizenshipInfo pInfo) {
        this.arrangeWidgets();
        this.populateCountryCodes();
        this.populateStatusCodes();
        this.initWidget(fTable);
    }

    public void setData(GwtPersonCitizenshipInfo pInfo) {
        countryOfCitizenshipCode.setItemSelected(pInfo.getCountryOfCitizenshipCode(), true);
        citizenStatusCode.setItemSelected(pInfo.getCitizenStatusCode(), true);
        this.effectiveStartDate.setText(pInfo.getEffectiveStartDate().toGMTString());
        this.effectiveEndDate.setText(pInfo.getEffectiveEndDate().toGMTString());
    }

    public void populateFields(GwtPersonCitizenshipInfo pInfo) {
        if (pInfo != null) {
            countryOfCitizenshipCode.setItemSelected(pInfo.getCountryOfCitizenshipCode(), true);
            citizenStatusCode.setItemSelected(pInfo.getCitizenStatusCode(), true);
            if (pInfo.getEffectiveStartDate() != null)
                this.effectiveStartDate.setText(DateBox.formatter.format(pInfo.getEffectiveStartDate()));
            else
                this.effectiveStartDate.clear();
            if (pInfo.getEffectiveEndDate() != null)
                this.effectiveEndDate.setText(DateBox.formatter.format(pInfo.getEffectiveEndDate()));
            else
                this.effectiveEndDate.clear();
        } else {
            countryOfCitizenshipCode.setItemSelected(0, false);
            citizenStatusCode.setItemSelected(0, false);
            this.effectiveStartDate.clear();
            this.effectiveEndDate.clear();
        }
    }

    protected void arrangeWidgets() {
        fTable.addStyleName("KS-PersonCitizenshipInfo-Panel");

        countryOfCitizenshipCode.addStyleName("KS-ListBox");
        citizenStatusCode.addStyleName("KS-ListBox");
        // GPT: this is an edit-label, not a text box
        //effectiveStartDate.addStyleName("KS-TextBox");
        //effectiveEndDate.addStyleName("KS-TextBox");

        lCountryOfCitizenshipCode.addStyleName("KS-Label");
        lCitizenStatusCode.addStyleName("KS-Label");
        lCitizenEffectiveStartDate.addStyleName("KS-Label");
        lCitizenEffectiveEndDate.addStyleName("KS-Label");

        fTable.setWidget(0, 0, lCountryOfCitizenshipCode);
        fTable.setWidget(0, 1, editCountryOfCitizenshipCode);

        fTable.setWidget(1, 0, lCitizenStatusCode);
        fTable.setWidget(1, 1, editCitizenStatusCode);

        fTable.setWidget(2, 0, lCitizenEffectiveStartDate);
        fTable.setWidget(2, 1, effectiveStartDate);
        
        fTable.setWidget(3, 0, lCitizenEffectiveEndDate);
        fTable.setWidget(3, 1, effectiveEndDate);

        /*
        HorizontalPanel hPanel = new HorizontalPanel();
        hPanel.addStyleName("KS-EffectiveDate-Panel");
        hPanel.add(effectiveStartDate);
*/
        // HTML lCitizenEffectiveTo=new HTML(" to ");
        // HTML lCitizenEffectiveTo=new HTML(I18N.i18nConstant.to());
        //HTML lCitizenEffectiveTo = new HTML(ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("to"));
/*
        lCitizenEffectiveTo.addStyleName("KS-CitizenEffectiveTo-Label");
        hPanel.add(lCitizenEffectiveTo);
        hPanel.add(effectiveEndDate);
        fTable.setWidget(2, 1, hPanel);
        */
    }

    protected void populateStatusCodes() {
        // citizenStatusCode.addItem("Citizen", "1");
        // citizenStatusCode.addItem(I18N.i18nConstant.citizen(), "1");
        citizenStatusCode.addItem(ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("citizen"), "1");

        // citizenStatusCode.addItem("Not Citizen", "2");
        // citizenStatusCode.addItem(I18N.i18nConstant.notCitizen(), "2");
        citizenStatusCode.addItem(ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("notCitizen"), "2");
    }

    public void setEditable(boolean isEditable){
        effectiveStartDate.setIsEditable(isEditable);
        effectiveEndDate.setIsEditable(isEditable);
        editCountryOfCitizenshipCode.setIsEditable(isEditable);
        editCitizenStatusCode.setIsEditable(isEditable);
    }
    
    protected void populateCountryCodes() {
        // countryOfCitizenshipCode.addItem("United States", "USA");
        // countryOfCitizenshipCode.addItem(I18N.i18nConstant.unitedStates(), "USA");
        countryOfCitizenshipCode.addItem(ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("unitedStates"), "USA");

        // countryOfCitizenshipCode.addItem("Canada", "CND");
        // countryOfCitizenshipCode.addItem(I18N.i18nConstant.canada(), "CND");
        countryOfCitizenshipCode.addItem(ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("canada"), "CND");

        // countryOfCitizenshipCode.addItem("Japan", "JPN");
        // countryOfCitizenshipCode.addItem(I18N.i18nConstant.japan(), "JPN");
        countryOfCitizenshipCode.addItem(ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("japan"), "JPN");

        // countryOfCitizenshipCode.addItem("South Korea", "SKO");
        // countryOfCitizenshipCode.addItem(I18N.i18nConstant.southKorea(), "SKO");
        countryOfCitizenshipCode.addItem(ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("southKorea"), "SKO");

        // countryOfCitizenshipCode.addItem("England", "ENG");
        // countryOfCitizenshipCode.addItem(I18N.i18nConstant.england(), "ENG");
        countryOfCitizenshipCode.addItem(ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("england"), "ENG");

        // countryOfCitizenshipCode.addItem("France", "FRA");
        // countryOfCitizenshipCode.addItem(I18N.i18nConstant.france(), "FRA");
        countryOfCitizenshipCode.addItem(ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("france"), "FRA");

        // countryOfCitizenshipCode.addItem("Russia", "RUS");
        // countryOfCitizenshipCode.addItem(I18N.i18nConstant.russia(), "RUS");
        countryOfCitizenshipCode.addItem(ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("russia"), "RUS");

        // countryOfCitizenshipCode.addItem("Spain", "SPA");
        // countryOfCitizenshipCode.addItem(I18N.i18nConstant.spain(), "SPA");
        countryOfCitizenshipCode.addItem(ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("spain"), "SPA");

        // countryOfCitizenshipCode.addItem("Mexico", "MEX");
        // countryOfCitizenshipCode.addItem(I18N.i18nConstant.mexico(), "MEX");
        countryOfCitizenshipCode.addItem(ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("mexico"), "MEX");

    }
}
