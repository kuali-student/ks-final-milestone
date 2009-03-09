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
import org.kuali.student.rules.lumgui.client.model.ReqComponentTypeInfo;
import org.kuali.student.rules.lumgui.client.service.LumGuiService;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author stse & Zdenek
 */
public class LumComposite extends Composite {

    // controller and meta data to be looked up externally
    Controller controller;
    Messages messages;    
    ViewMetaData metadata;
    ModelBinding<LumModelObject> binding1;
    ModelBinding<LumModelObject> binding2;
    ModelBinding<LumModelObject> binding3;
   
    final SimplePanel mainLumPanel = new SimplePanel();
    LumSwitchPanel<LumModelObject> pnlViews;
    List<LumUIEventListener> lumUIListeners = new ArrayList<LumUIEventListener>(7);     
        
    //SIMPLE VIEW widgets
    Button btnSearchCourses;
    Button btnComplexLevel;    
    LumTextArea<LumModelObject> taPreReqRationale;
    LumTextArea<LumModelObject> taNaturalLanguage;
    LumTextArea<LumModelObject> taRreReqCourses;
    LumTextBox<LumModelObject> tbStatementId;
    // TODO use button when widgets are ready 
//  Button btnAddCourses;  
    
    //COMPLEX VIEW widgets
    Button btnSimpleLevel;
    Button btnAddRequirement;
    Button btnCancelRequirement;
    Button btnChooseRequirement;
    Button btnAddComposedRequirement;
    LumTextBox<LumModelObject> tbAlgebra;
    LumTextArea<LumModelObject> taNaturalLanguage2;    
    LumRuleTable<LumModelObject> ruleTable;     
    
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
        
        pnlViews = new LumSwitchPanel<LumModelObject>(LumModelObject.FieldName.CURRENT_VIEW.toString());        
        
        /*****  create and layout the widgets for SIMPLE VIEW  *****/        
        Panel pnlSimpleView = new VerticalPanel();
        Panel pnlRationaleToComplexView = new VerticalPanel();
        Panel pnlPreReqCourses = new VerticalPanel();
        Panel pnlNaturalLanguage = new VerticalPanel();
        HorizontalPanel pnlStatementSearch = new HorizontalPanel();
        pnlStatementSearch.setSpacing(5);
        pnlSimpleView.add(pnlStatementSearch);
        pnlSimpleView.add(pnlRationaleToComplexView);
        pnlSimpleView.add(pnlPreReqCourses);
        pnlSimpleView.add(pnlNaturalLanguage);
        
        // TODO test remove when done
        ruleTable = new LumRuleTable<LumModelObject>(LumModelObject.FieldName.STATEMENT.toString());
       // pnlSimpleView.add(ruleTable);
        // end test      
        
        /*****  create and layout the widgets for COMPLEX VIEW  *****/ 
        Panel pnlComplexView = new VerticalPanel();
        Panel pnlSimpleTreeView = new VerticalPanel();
        Panel pnlBooleanAlgebraView = new VerticalPanel();
        Panel pnlNaturalLanguage2 = new VerticalPanel();           
        pnlComplexView.add(pnlSimpleTreeView);
        pnlComplexView.add(pnlBooleanAlgebraView);
        pnlComplexView.add(pnlNaturalLanguage2);

        pnlViews.add(pnlSimpleView);
        pnlViews.add(pnlComplexView);     
        mainLumPanel.add(pnlViews);
        
        // sizes and layout are to be done AFTER the containing panels
        // are added to mainLumPanel to make size settings effective
        
        //setup widgets for SIMPLE VIEW
        pnlViews.showStack(0);        
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
        
        //setup widgets for COMPLEX VIEW
        pnlViews.showStack(1);           
        pnlComplexView.setSize(
                Double.toString(mainLumPanel.getOffsetWidth()),
                Double.toString(mainLumPanel.getOffsetHeight()));
        pnlSimpleTreeView.setSize(
                Double.toString(pnlComplexView.getOffsetWidth() * 0.9),
                Double.toString(pnlComplexView.getOffsetHeight() * 0.33));
        pnlBooleanAlgebraView.setSize(
                Double.toString(pnlComplexView.getOffsetWidth() * 0.9),
                Double.toString(pnlComplexView.getOffsetHeight() * 0.33));
        pnlNaturalLanguage2.setSize(
                Double.toString(pnlComplexView.getOffsetWidth() * 0.9),
                Double.toString(pnlComplexView.getOffsetHeight() * 0.33));      
        prepareSimpleTreePanel(pnlSimpleTreeView); 
        prepareBoleanAlgebraPanel(pnlBooleanAlgebraView);        
        prepareNaturalLanguagePanel2(pnlNaturalLanguage2); 
    } 
    
    // for debugging
    private void addBorder(Widget widget) {
        widget.getElement().getStyle().setProperty("border", "1px solid #87b3ff");
    }
    
    private void prepareStatementSearchPanel(Panel parent) {
        btnSearchCourses = new Button("Search");
        tbStatementId = new LumTextBox<LumModelObject>(LumModelObject.FieldName.
                STATEMENT_ID.toString());
        parent.add(new Label("Statement Id"));
        parent.add(tbStatementId);
        parent.add(btnSearchCourses);
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
                Double.toString(parent.getOffsetHeight() * 0.5));
    }
    
    private void preparePreReqCoursesPanel(Panel parent) {
        taRreReqCourses = new LumTextArea<LumModelObject>(LumModelObject.FieldName.
                PRE_REQ_COURSES.toString());
//        btnAddCourses = new Button(messages.get("lum.addPreReqCourses"));
        parent.add(new Label(messages.get("lum.prereqCourses")));
        parent.add(taRreReqCourses);
        taRreReqCourses.setSize(
                Double.toString(parent.getOffsetWidth() * 0.9),
                Double.toString(parent.getOffsetHeight() * 0.5));
    }
    
    private void prepareNaturalLanguagePanel(Panel parent) {
        taNaturalLanguage = new LumTextArea<LumModelObject>(LumModelObject.FieldName.
                NATURAL_LANGUAGE.toString());
        parent.add(new Label(messages.get("lum.naturalLanguage")));
        parent.add(taNaturalLanguage);
        taNaturalLanguage.setSize(
                Double.toString(parent.getOffsetWidth() * 0.9),
                Double.toString(parent.getOffsetHeight() * 0.5));         
    }
    
    private void prepareSimpleTreePanel(Panel parent) {
        btnSimpleLevel = new Button("Simple Rules");
        parent.add(btnSimpleLevel);
        btnAddRequirement = new Button("Add");
        parent.add(btnAddRequirement);        
    } 
    
    private void prepareBoleanAlgebraPanel(Panel parent) {
        parent.add(new Label("Algebra"));
        tbAlgebra = new LumTextBox<LumModelObject>(LumModelObject.FieldName.ALGEBRA.toString());
        parent.add(tbAlgebra);
    }
    
    private void prepareNaturalLanguagePanel2(Panel parent) {
        taNaturalLanguage2 = new LumTextArea<LumModelObject>(LumModelObject.FieldName.
                NATURAL_LANGUAGE.toString());
        parent.add(new Label(messages.get("lum.naturalLanguage")));
        parent.add(taNaturalLanguage2);        
        taNaturalLanguage2.setPixelSize(
                (int)(parent.getOffsetWidth() * 0.9),
                (int)(parent.getOffsetHeight() * 0.8));         
    }    
    
    public void setUpListeners() {
        Model<LumModelObject> model = (Model<LumModelObject>) controller.getModel(LumModelObject.class);
        binding1 = new ModelBinding<LumModelObject>(model, tbStatementId);
        binding2 = new ModelBinding<LumModelObject>(model, pnlViews);
        binding3 = new ModelBinding<LumModelObject>(model, ruleTable);
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
        btnSimpleLevel.addClickListener(new ClickListener() {
            public void onClick(Widget sender) {
                System.out.println("btnSimpleLevel clicked");
                if (lumUIListeners != null && !lumUIListeners.isEmpty()) {
                    for (LumUIEventListener listener : lumUIListeners) {
                        listener.switchToSimpleView();
                    }
                }
            }
        });          
        btnAddRequirement.addClickListener(new ClickListener() {
            public void onClick(Widget sender) {
                System.out.println("btnAddRequirement clicked");
                if (lumUIListeners != null && !lumUIListeners.isEmpty()) {
                    for (LumUIEventListener listener : lumUIListeners) {
                        listener.showRequirementDialog();
                    }
                }
            }
        });                                  
    }
    
    public void addLumUIEventListener(LumUIEventListener listener) {
        lumUIListeners.add(listener);
    }    
}
