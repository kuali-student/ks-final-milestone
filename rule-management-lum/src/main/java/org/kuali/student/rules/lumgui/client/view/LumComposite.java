/**
 * 
 */
package org.kuali.student.rules.lumgui.client.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.kuali.student.commons.ui.logging.client.Logger;
import org.kuali.student.commons.ui.messages.client.Messages;
import org.kuali.student.commons.ui.mvc.client.ApplicationContext;
import org.kuali.student.commons.ui.mvc.client.Controller;
import org.kuali.student.commons.ui.mvc.client.MVC;
import org.kuali.student.commons.ui.mvc.client.MVCEvent;
import org.kuali.student.commons.ui.mvc.client.model.Model;
import org.kuali.student.commons.ui.mvc.client.widgets.ModelBinding;
import org.kuali.student.commons.ui.validators.client.ValidationResult;
import org.kuali.student.commons.ui.validators.client.Validator;
import org.kuali.student.commons.ui.viewmetadata.client.ViewMetaData;
import org.kuali.student.commons.ui.widgets.tables.ModelTableSelectionListener;
import org.kuali.student.commons.ui.widgets.trees.TreeMouseOverListener;
import org.kuali.student.rules.lumgui.client.DateRange;
import org.kuali.student.rules.lumgui.client.controller.LumGuiController;
import org.kuali.student.rules.lumgui.client.model.LumModel;
import org.kuali.student.rules.lumgui.client.service.LumGuiService;
import org.kuali.student.rules.factfinder.dto.FactParamDTO;
import org.kuali.student.rules.factfinder.dto.FactStructureDTO;
import org.kuali.student.rules.factfinder.dto.FactTypeInfoDTO;

import com.google.gwt.user.client.DOM;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FocusListener;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.HorizontalSplitPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.SourcesTabEvents;
import com.google.gwt.user.client.ui.TabListener;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TextBoxBase;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.VerticalSplitPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author stse
 */
public class LumComposite extends Composite {

    // controller and meta data to be looked up externally
    Controller controller;
    ViewMetaData metadata;
    Messages messages;
    LumTextArea<LumModel> taPreReqRationale;
    LumTextArea<LumModel> taNaturalLanguage;
    LumTextArea<LumModel> taRreReqCourses;
    LumTextBox<LumModel> tbStatementId;
    Button btnSearch;
    Button btnComplexLevel;
    Panel pnlSimpleView;
    ModelBinding<LumModel> binding;
    Model<LumModel> lumModel;
    // TODO use button when widgets are ready 
//    Button btnAddCourses;

    final SimplePanel mainLumPanel = new SimplePanel();    
    
    boolean loaded = false;

    public LumComposite() {
        super.initWidget(mainLumPanel);
    }

    @Override
    protected void onLoad() {
        super.onLoad();
        if (!loaded) {
            loaded = true;
            // get a reference to our parent controller
            controller = MVC.findParentController(this);
		}
	}
    
    public void layoutWidgets() {
        // get a reference to our view meta data and internationalization messages
        metadata = ApplicationContext.getViews().get(LumGuiController.VIEW_NAME);
        messages = metadata.getMessages();
        
        // create and layout the widgets
        pnlSimpleView = new VerticalPanel();
        Panel pnlRationale = new VerticalPanel();
        Panel pnlPreReqCourses = new VerticalPanel();
        Panel pnlNaturalLanguage = new VerticalPanel();
        HorizontalPanel pnlStatementSearch = new HorizontalPanel();

        pnlStatementSearch.setSpacing(5);
        // debug
//        addBorder(pnlRationale);
//        addBorder(pnlPreReqCourses);
//        addBorder(pnlNaturalLanguage);
//        addBorder(pnlSubMainPanel);
//        addBorder(mainLumPanel);
        // end debug
        pnlSimpleView.add(pnlStatementSearch);
        pnlSimpleView.add(pnlRationale);
        pnlSimpleView.add(pnlPreReqCourses);
        pnlSimpleView.add(pnlNaturalLanguage);
        
//        test.setWidth("100px");
//        mainLumPanel.setSize("950px", "550px");
        mainLumPanel.add(pnlSimpleView);
        
        // sizes and layout are to be done AFTER the containing panels
        // are added to mainLumPanel to make size settings effective
        pnlSimpleView.setSize(
                Double.toString(mainLumPanel.getOffsetWidth()),
                Double.toString(mainLumPanel.getOffsetHeight()));
        pnlRationale.setSize(
                Double.toString(pnlSimpleView.getOffsetWidth() * 0.9),
                Double.toString(pnlSimpleView.getOffsetHeight() * 0.33));
        pnlPreReqCourses.setSize(
                Double.toString(pnlSimpleView.getOffsetWidth() * 0.9),
                Double.toString(pnlSimpleView.getOffsetHeight() * 0.33));
        pnlNaturalLanguage.setSize(
                Double.toString(pnlSimpleView.getOffsetWidth() * 0.9),
                Double.toString(pnlSimpleView.getOffsetHeight() * 0.33));
        prepareStatementSearchPanel(pnlStatementSearch);
        prepareRationalePanel(pnlRationale);
        preparePreReqCoursesPanel(pnlPreReqCourses);
        prepareNaturalLanguagePanel(pnlNaturalLanguage);
    }
    
    // for debugging
    private void addBorder(Widget widget) {
        widget.getElement().getStyle().setProperty("border", "1px solid #87b3ff");
    }
    
    private void prepareStatementSearchPanel(Panel parent) {
        btnSearch = new Button("Search");
        tbStatementId = new LumTextBox<LumModel>(LumModel.FieldName.
                STATEMENT_ID.toString());
        parent.add(new Label("Statement Id"));
        parent.add(tbStatementId);
        parent.add(btnSearch);
    }
    
    private void prepareRationalePanel(Panel parent) {
        taPreReqRationale = new LumTextArea<LumModel>(LumModel.FieldName.
                RATIONALE.toString());
        System.out.println("lum.rationale" + messages.get("lum.rationale"));
        parent.add(new Label(messages.get("lum.rationale")));
        parent.add(taPreReqRationale);
        taPreReqRationale.setSize(
                Double.toString(parent.getOffsetWidth() * 0.9),
                Double.toString(parent.getOffsetHeight() * 0.8));
    }
    
    private void preparePreReqCoursesPanel(Panel parent) {
        taRreReqCourses = new LumTextArea<LumModel>(LumModel.FieldName.
                PRE_REQ_COURSES.toString());
//        btnAddCourses = new Button(messages.get("lum.addPreReqCourses"));
        parent.add(new Label(messages.get("lum.prereqCourses")));
        parent.add(taRreReqCourses);
        taRreReqCourses.setSize(
                Double.toString(parent.getOffsetWidth() * 0.9),
                Double.toString(parent.getOffsetHeight() * 0.8));
    }
    
    private void prepareNaturalLanguagePanel(Panel parent) {
        taNaturalLanguage = new LumTextArea<LumModel>(LumModel.FieldName.
                NATURAL_LANGUAGE.toString());
        parent.add(new Label(messages.get("lum.naturalLanguage")));
        parent.add(taNaturalLanguage);
        taNaturalLanguage.setSize(
                Double.toString(parent.getOffsetWidth() * 0.9),
                Double.toString(parent.getOffsetHeight() * 0.8));
    }

    public void setUpListeners() {
        Model<LumModel> model = (Model<LumModel>) controller.getModel(LumModel.class);
        lumModel = model;
        binding = new ModelBinding<LumModel>(model, tbStatementId);
    }

}
