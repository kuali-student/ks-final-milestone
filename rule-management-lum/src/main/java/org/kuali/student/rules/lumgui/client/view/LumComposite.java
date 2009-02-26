/**
 * 
 */
package org.kuali.student.rules.lumgui.client.view;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.commons.ui.messages.client.Messages;
import org.kuali.student.commons.ui.mvc.client.ApplicationContext;
import org.kuali.student.commons.ui.mvc.client.Controller;
import org.kuali.student.commons.ui.mvc.client.MVC;
import org.kuali.student.commons.ui.mvc.client.model.Model;
import org.kuali.student.commons.ui.mvc.client.widgets.ModelBinding;
import org.kuali.student.commons.ui.viewmetadata.client.ViewMetaData;
import org.kuali.student.rules.lumgui.client.controller.LumGuiController;
import org.kuali.student.rules.lumgui.client.model.LumModelObject;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author stse
 */
public class LumComposite extends Composite {

    // controller and meta data to be looked up externally
    Controller controller;
    ViewMetaData metadata;
    Messages messages;
    LumTextArea<LumModelObject> taPreReqRationale;
    LumTextArea<LumModelObject> taNaturalLanguage;
    LumTextArea<LumModelObject> taRreReqCourses;
    LumTextBox<LumModelObject> tbStatementId;
    Button btnSearch;
    Button btnComplexLevel;
    LumSwitchPanel<LumModelObject> pnlViews;
    List<LumUIEventListener> lumUIListeners = new ArrayList<LumUIEventListener>(7); 
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
        Panel pnlSimpleView = new VerticalPanel();
        pnlViews = new LumSwitchPanel<LumModelObject>(
                LumModelObject.FieldName.CURRENT_VIEW.toString());
        Panel pnlRationaleToComplexView = new VerticalPanel();
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
        pnlSimpleView.add(pnlRationaleToComplexView);
        pnlSimpleView.add(pnlPreReqCourses);
        pnlSimpleView.add(pnlNaturalLanguage);
        
        pnlViews.add(pnlSimpleView);
        pnlViews.add(new VerticalPanel());
        pnlViews.showStack(0);
        
//        test.setWidth("100px");
//        mainLumPanel.setSize("950px", "550px");
        mainLumPanel.add(pnlViews);
        
        // sizes and layout are to be done AFTER the containing panels
        // are added to mainLumPanel to make size settings effective
        pnlSimpleView.setSize(
                Double.toString(mainLumPanel.getOffsetWidth()),
                Double.toString(mainLumPanel.getOffsetHeight()));
        pnlRationaleToComplexView.setSize(
                Double.toString(pnlSimpleView.getOffsetWidth() * 0.9),
                Double.toString(pnlSimpleView.getOffsetHeight() * 0.33));
        pnlPreReqCourses.setSize(
                Double.toString(pnlSimpleView.getOffsetWidth() * 0.9),
                Double.toString(pnlSimpleView.getOffsetHeight() * 0.33));
        pnlNaturalLanguage.setSize(
                Double.toString(pnlSimpleView.getOffsetWidth() * 0.9),
                Double.toString(pnlSimpleView.getOffsetHeight() * 0.33));
        prepareStatementSearchPanel(pnlStatementSearch);
        prepareRationaleToComplexViewPanel(pnlRationaleToComplexView);
        preparePreReqCoursesPanel(pnlPreReqCourses);
        prepareNaturalLanguagePanel(pnlNaturalLanguage);
    }
    
    // for debugging
    private void addBorder(Widget widget) {
        widget.getElement().getStyle().setProperty("border", "1px solid #87b3ff");
    }
    
    private void prepareStatementSearchPanel(Panel parent) {
        btnSearch = new Button("Search");
        tbStatementId = new LumTextBox<LumModelObject>(LumModelObject.FieldName.
                STATEMENT_ID.toString());
        parent.add(new Label("Statement Id"));
        parent.add(tbStatementId);
        parent.add(btnSearch);
    }
    
    private void prepareRationaleToComplexViewPanel(Panel parent) {
        HorizontalPanel topPanel = new HorizontalPanel();
        taPreReqRationale = new LumTextArea<LumModelObject>(LumModelObject.FieldName.
                RATIONALE.toString());
        btnComplexLevel = new Button("Complex Pre-requisite Rules");
        topPanel.add(new Label(messages.get("lum.rationale")));
        topPanel.add(btnComplexLevel);
        parent.add(topPanel);
        parent.add(taPreReqRationale);
        taPreReqRationale.setSize(
                Double.toString(parent.getOffsetWidth() * 0.9),
                Double.toString(parent.getOffsetHeight() * 0.8));
    }
    
    private void preparePreReqCoursesPanel(Panel parent) {
        taRreReqCourses = new LumTextArea<LumModelObject>(LumModelObject.FieldName.
                PRE_REQ_COURSES.toString());
//        btnAddCourses = new Button(messages.get("lum.addPreReqCourses"));
        parent.add(new Label(messages.get("lum.prereqCourses")));
        parent.add(taRreReqCourses);
        taRreReqCourses.setSize(
                Double.toString(parent.getOffsetWidth() * 0.9),
                Double.toString(parent.getOffsetHeight() * 0.8));
    }
    
    private void prepareNaturalLanguagePanel(Panel parent) {
        taNaturalLanguage = new LumTextArea<LumModelObject>(LumModelObject.FieldName.
                NATURAL_LANGUAGE.toString());
        parent.add(new Label(messages.get("lum.naturalLanguage")));
        parent.add(taNaturalLanguage);
        taNaturalLanguage.setSize(
                Double.toString(parent.getOffsetWidth() * 0.9),
                Double.toString(parent.getOffsetHeight() * 0.8));
    }

    public void setUpListeners() {
        Model<LumModelObject> model = (Model<LumModelObject>) controller.getModel(LumModelObject.class);
        new ModelBinding<LumModelObject>(model, tbStatementId);
        new ModelBinding<LumModelObject>(model, pnlViews);
        btnComplexLevel.addClickListener(new ClickListener() {
            public void onClick(Widget sender) {
                System.out.println("btnComplexLevel clicked");
                if (lumUIListeners != null && !lumUIListeners.isEmpty()) {
                    for (LumUIEventListener listener : lumUIListeners) {
                        listener.switchToComplexView();
                    }
                }
            }
        });
    }
    
    public void addLumUIEventListener(LumUIEventListener listener) {
        lumUIListeners.add(listener);
    }

}
