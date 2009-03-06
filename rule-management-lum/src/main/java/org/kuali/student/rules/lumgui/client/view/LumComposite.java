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
    ViewMetaData metadata;
    Messages messages;
    LumSwitchPanel<LumModelObject> pnlViews;
    List<LumUIEventListener> lumUIListeners = new ArrayList<LumUIEventListener>(7);     
    
    //SIMPLE VIEW widgets
    Button btnSearch;
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
    ModelBinding<LumModelObject> binding1;
    ModelBinding<LumModelObject> binding2;
    ModelBinding<LumModelObject> binding3;
    ModelBinding<LumModelObject> binding4;
    ModelBinding<LumModelObject> binding5;
    LumRuleTable<LumModelObject> ruleTable; 
    LumListBox<LumModelObject> requirementTypes;    
    TextArea taExample;

    
    final FlexTable dialogBorderPanel = new FlexTable();
    final VerticalPanel dialogContents = new VerticalPanel();    
    final HorizontalPanel reqTypesChoicePanel = new HorizontalPanel();    
    VerticalPanel multiBoxPanel = new VerticalPanel();
    SimplePanel requirementCompositionPanel = new SimplePanel();
    VerticalPanel bottomPanel = new VerticalPanel();
    
    //REQUIREMENT DIALOG widgets
    LumDialog<LumModelObject> dgCompRequirement;
    Button btnCloseDialog;    
    
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
        dgCompRequirement = new LumDialog("RequirementComposition");        
        pnlComplexView.add(pnlSimpleTreeView);
        pnlComplexView.add(pnlBooleanAlgebraView);
        pnlComplexView.add(pnlNaturalLanguage2);

        pnlViews.add(pnlSimpleView);
        pnlViews.add(pnlComplexView);
        pnlViews.add(dgCompRequirement);       
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
        
     
        System.out.println("Size: " + pnlSimpleView.getOffsetWidth());
        
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

        
        pnlViews.showStack(2);
        dgCompRequirement.setPixelSize((int)(mainLumPanel.getOffsetWidth() * 0.8), (int)(mainLumPanel.getOffsetHeight() * 0.7));           

        System.out.println("test1: " + Double.toString(mainLumPanel.getOffsetWidth() * 0.8));
        System.out.println("test2: " + dgCompRequirement.getOffsetWidth());
        prepareRequirementDialog(dgCompRequirement);    
        

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

    private void prepareRequirementDialog(final DialogBox dialogWidget) {      
       
        dialogBorderPanel.setCellSpacing(5);        
        dialogBorderPanel.setWidget(0, 0, dialogContents);       
        
        // Add panel to choose requirement type
        taExample = new TextArea();
        TextArea requirementCompositionText = new TextArea();
        requirementTypes = new LumListBox<LumModelObject>(LumModelObject.FieldName.REQ_TYPE.toString(), false, taExample, requirementCompositionText);
        requirementTypes.setVisibleItemCount(10);       
        multiBoxPanel.add(new Label("Requirement Types"));
        multiBoxPanel.add(requirementTypes);
        
        final SimplePanel reqWrapperPanel = new SimplePanel();        
        reqWrapperPanel.add(multiBoxPanel);
        reqWrapperPanel.setStyleName("KSLumPanel");   
        
        //Requirement Type Example
        VerticalPanel examplePanel = new VerticalPanel();
        Label label = new Label("Examples");
        examplePanel.setSize("100%", "100%");
        examplePanel.add(label);
        examplePanel.add(taExample);
        
        final SimplePanel exampleWrapperPanel = new SimplePanel();         
        exampleWrapperPanel.add(examplePanel); 
        exampleWrapperPanel.setStyleName("KSLumPanel");     
               
        reqTypesChoicePanel.setSize("100%", "100%");
        reqTypesChoicePanel.add(reqWrapperPanel);
        reqTypesChoicePanel.add(exampleWrapperPanel);           
        reqTypesChoicePanel.setStyleName("KSLumContentPanel");                   

        // Add Requirement Composition Panel
        taNaturalLanguage2 = new LumTextArea<LumModelObject>(LumModelObject.FieldName.NATURAL_LANGUAGE.toString());             
        VerticalPanel compositionText = new VerticalPanel();
        compositionText.setStyleName("KSLumPanel");
        compositionText.add(new Label("Work Area"));
        compositionText.add(requirementCompositionText);
        requirementCompositionPanel.setSize("100%", "100px");        
        requirementCompositionPanel.add(compositionText); 
        requirementCompositionPanel.setStyleName("KSLumContentPanel");           
        
        // Add panel for buttons
        SimplePanel bottomWrapperPanel = new SimplePanel();
        bottomWrapperPanel.setStyleName("KSLumPanel");        
        DockPanel bottomDockPanel = new DockPanel();
        bottomDockPanel.setSize("100%", "100%");
        bottomWrapperPanel.setStyleName("KSLumPanel");
        btnCancelRequirement = new Button("Cancel");  
        btnAddComposedRequirement = new Button("Add");
        bottomDockPanel.setHorizontalAlignment(DockPanel.ALIGN_LEFT);
        bottomDockPanel.add(btnCancelRequirement, DockPanel.WEST);
        bottomDockPanel.setHorizontalAlignment(DockPanel.ALIGN_RIGHT);
        bottomDockPanel.add(btnAddComposedRequirement, DockPanel.EAST);        
        
        dialogContents.add(reqTypesChoicePanel);
        dialogContents.add(requirementCompositionPanel); 
        dialogContents.add(bottomDockPanel);
        
        dialogWidget.setAnimationEnabled(true);
        dialogWidget.setText("Add a Requirement");
        dialogWidget.add(dialogBorderPanel);
       
        dialogBorderPanel.setPixelSize((int)(mainLumPanel.getOffsetWidth() * 0.9), (int)(mainLumPanel.getOffsetHeight() * 0.7));  
        dialogContents.setSize("100%", "100%");        
        //bottomDockPanel.setSize("100%", "20%");
        
        reqWrapperPanel.setWidth(Double.toString(reqTypesChoicePanel.getOffsetWidth() * 0.3));          
        exampleWrapperPanel.setWidth(Double.toString(reqTypesChoicePanel.getOffsetWidth() * 0.7));                 
        taExample.setSize("100%", Double.toString(examplePanel.getOffsetHeight() * 0.7));
        requirementCompositionText.setSize("500px", "100px");
        
        LumGuiService.Util.getInstance().getReqComponentTypesForLuStatementType("1", new AsyncCallback<List<ReqComponentTypeInfo>>() {

            public void onFailure(Throwable caught) {
                // just re-throw it and let the uncaught exception handler deal with it
                Window.alert(caught.getMessage());
                // throw new RuntimeException("Unable to load BusinessRuleInfo objects", caught);
            }

            public void onSuccess(List<ReqComponentTypeInfo> reqComponentTypeInfoList) {
                for (ReqComponentTypeInfo reqInfo : reqComponentTypeInfoList) {                   
                    requirementTypes.addItem(reqInfo.getName(), reqInfo.getDesc());
                    System.out.println(reqInfo.getName());
                }
            }
        });         
        
        
        //System.out.println("Width: " + requirementCompositionPanel.getOffsetWidth());
        //System.out.println("Height: " + requirementCompositionPanel.getOffsetHeight());        
        
        /*DeferredCommand.addCommand(new Command()
        {
            public void execute()
            {
                System.out.println("Size1: " + dialogBorderPanel.getOffsetWidth());
                System.out.println("Size2: " + dgCompRequirement.getOffsetWidth());
            }
        }); */                
    }         
    
    public void setUpListeners() {
        Model<LumModelObject> model = (Model<LumModelObject>) controller.getModel(LumModelObject.class);
        binding1 = new ModelBinding<LumModelObject>(model, tbStatementId);
        binding2 = new ModelBinding<LumModelObject>(model, pnlViews);
        binding3 = new ModelBinding<LumModelObject>(model, ruleTable);
        binding4 = new ModelBinding<LumModelObject>(model, requirementTypes);
        binding5 = new ModelBinding<LumModelObject>(model, dgCompRequirement);
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
        btnCancelRequirement.addClickListener(new ClickListener() {
            public void onClick(Widget sender) {
                System.out.println("btnCancelRequirement clicked");
                if (lumUIListeners != null && !lumUIListeners.isEmpty()) {
                    for (LumUIEventListener listener : lumUIListeners) {
                        listener.hideRequirementDialog();
                    }
                }
            }
        });  
        requirementTypes.addClickListener(new ClickListener() {
            public void onClick(Widget sender) {
                System.out.println("requirementTypes clicked");
                if (lumUIListeners != null && !lumUIListeners.isEmpty()) {
                    for (LumUIEventListener listener : lumUIListeners) {
                       listener.updateExampleAndWorkArea();
                    }
                }
            }
        });                    
    }
    
    public void addLumUIEventListener(LumUIEventListener listener) {
        lumUIListeners.add(listener);
    }    
}
