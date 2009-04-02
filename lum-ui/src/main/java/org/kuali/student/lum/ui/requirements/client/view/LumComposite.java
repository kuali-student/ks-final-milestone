/**
 * 
 */
package org.kuali.student.lum.ui.requirements.client.view;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSDisclosureSection;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSRadioButton;
import org.kuali.student.common.ui.client.widgets.KSRichEditor;
import org.kuali.student.common.ui.client.widgets.KSTextBox;
import org.kuali.student.commons.ui.messages.client.Messages;
import org.kuali.student.commons.ui.mvc.client.Controller;
import org.kuali.student.commons.ui.mvc.client.MVC;
import org.kuali.student.commons.ui.mvc.client.model.Model;
import org.kuali.student.commons.ui.mvc.client.widgets.ModelBinding;
import org.kuali.student.commons.ui.viewmetadata.client.ViewMetaData;
import org.kuali.student.lum.ui.requirements.client.model.LumModelObject;
import org.kuali.student.lum.ui.requirements.client.service.RequirementsService;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
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
    KSButton btnSearchCourses = new KSButton("Search");
    KSButton btnComplexLevel = new KSButton("Complex Pre-requisite Rules");    
    KSRichEditor taPreReqRationale;
    LumTextArea<LumModelObject> taNaturalLanguage;
    LumTextArea<LumModelObject> taRreReqCourses;
    LumTextBox<LumModelObject> tbStatementId;
    VerticalPanel pnlNaturalLanguage1 = new VerticalPanel();      
    
    LumTextBox courseList = new LumTextBox<LumModelObject>(LumModelObject.FieldName.ALGEBRA.toString());
    
    // TODO use button when widgets are ready 
//  Button btnAddCourses;  
    
    //COMPLEX VIEW widgets
    KSButton  btnSimpleLevel = new KSButton ("Simple Rules");
    KSButton  btnAddRequirement =  new KSButton ("Add");
    KSButton  btnAddCourses = new KSButton ("Add Courses");
    KSButton  btnCancelRequirement;
    KSButton  btnChooseRequirement;
    KSButton  btnAddComposedRequirement;
    LumTextBox<LumModelObject> tbAlgebra;
    LumTextArea<LumModelObject> taNaturalLanguage2;
    LumRulePanel<LumModelObject> pnlRuleTable;
    
    //final KSAccordionPanel  naturalLanguageSection = new KSAccordionPanel ();
    KSDisclosureSection section1;
    KSDisclosureSection section2;
    
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
        //metadata = ApplicationContext.getViews().get(RequirementsController.VIEW_NAME);
        //messages = metadata.getMessages();
        
        pnlViews = new LumSwitchPanel<LumModelObject>(LumModelObject.FieldName.CURRENT_VIEW.toString());        
        
        /*****  create and layout the widgets for SIMPLE VIEW  *****/        
        Panel pnlSimpleView = new VerticalPanel();
        VerticalPanel pnlRationaleToComplexView = new VerticalPanel();
        HorizontalPanel pnlStatementSearch = new HorizontalPanel();
        pnlStatementSearch.setSpacing(10);
        pnlRationaleToComplexView.setSpacing(10);
        pnlSimpleView.add(pnlStatementSearch);
        pnlSimpleView.add(pnlRationaleToComplexView);
        
        /*****  create and layout the widgets for COMPLEX VIEW  *****/ 
        VerticalPanel pnlComplexView = new VerticalPanel();
        VerticalPanel pnlSimpleTreeView = new VerticalPanel();
        VerticalPanel pnlBooleanAlgebraView = new VerticalPanel();
        pnlComplexView.setSpacing(10);
        pnlSimpleTreeView.setSpacing(10);
        pnlBooleanAlgebraView.setSpacing(10);               
        pnlComplexView.add(pnlSimpleTreeView);
        pnlComplexView.add(pnlBooleanAlgebraView);
        pnlComplexView.add(pnlNaturalLanguage1);
        pnlNaturalLanguage1.setSpacing(0);
        pnlNaturalLanguage1.setBorderWidth(0);
       // pnlNaturalLanguage1.setWidth("70%");        
        
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
                Double.toString(pnlSimpleView.getOffsetWidth() * 0.7),
                "50px");                  
        prepareStatementSearchPanel(pnlStatementSearch);
        prepareRationaleToComplexViewPanel(pnlRationaleToComplexView);
        
        //setup widgets for COMPLEX VIEW
        pnlViews.showStack(1);           
      //  pnlComplexView.setSize(
      //          Double.toString(mainLumPanel.getOffsetWidth()),
      //          Double.toString(mainLumPanel.getOffsetHeight()));
        pnlSimpleTreeView.setSize(
                Double.toString(pnlComplexView.getOffsetWidth() * 0.9),
                Double.toString(pnlComplexView.getOffsetHeight() * 0.33));
        pnlBooleanAlgebraView.setSize(
                Double.toString(pnlComplexView.getOffsetWidth() * 0.9),
                Double.toString(pnlComplexView.getOffsetHeight() * 0.33));
        prepareSimpleTreePanel(pnlSimpleTreeView); 
        prepareBooleanAlgebraPanel(pnlBooleanAlgebraView);        
        prepareNaturalLanguagePanel2(pnlNaturalLanguage1); 
    } 
    
    // for debugging
    private void addBorder(Widget widget) {
        widget.getElement().getStyle().setProperty("border", "1px solid #87b3ff");
    }
    
    private void prepareStatementSearchPanel(Panel parent) {
        tbStatementId = new LumTextBox<LumModelObject>(LumModelObject.FieldName.
                STATEMENT_ID.toString());
        parent.add(new KSLabel("Statement Id"));
        parent.add(tbStatementId);
        parent.add(btnSearchCourses);
        parent.add(btnAddCourses);
    }
    
    private void prepareRationaleToComplexViewPanel(Panel parent) {
        HorizontalPanel topPanel = new HorizontalPanel();
        taPreReqRationale = new KSRichEditor();
        KSLabel rationale = new KSLabel("Rationale:"); 
        rationale.getElement().getStyle().setProperty("fontWeight", "bold");
        topPanel.add(rationale);
        SimplePanel tempPanel = new SimplePanel();
        tempPanel.setWidth("300px");
        topPanel.add(tempPanel);
        topPanel.add(btnComplexLevel);
        parent.add(topPanel);
        parent.add(taPreReqRationale);
        taPreReqRationale.setSize("100%", "50px");
        
        //add component selection
        VerticalPanel chooseComponentPanel = new VerticalPanel();
        KSLabel title1 = new KSLabel("Choose from ONE of the following"); 
        title1.getElement().getStyle().setProperty("fontWeight", "bold");
        chooseComponentPanel.add(title1);  
        KSLabel title2 = new KSLabel("Student must have taken:");
        title2.getElement().getStyle().setProperty("fontWeight", "bold");
        title2.getElement().getStyle().setProperty("fontSize", "12px");
        chooseComponentPanel.add(title2);        

        parent.add(chooseComponentPanel);
        
        final  VerticalPanel group1Panel = new VerticalPanel(); 
        final  HorizontalPanel radioPanel1 = new HorizontalPanel(); 
        final KSRadioButton ksRadio1 = new KSRadioButton("Group1", "", false);     
        radioPanel1.setSpacing(5);
        radioPanel1.add(ksRadio1);
        radioPanel1.add(new KSLabel("The following course(s):"));
        radioPanel1.add(courseList);
        final KSRadioButton ksRadio2 = new KSRadioButton("Group1", "", false);  
        final KSRadioButton ksRadio3 = new KSRadioButton("Group1", "", false);  
        final KSRadioButton ksRadio4 = new KSRadioButton("Group1", "", false);         
        group1Panel.add(radioPanel1);   
        group1Panel.add(ksRadio2);  
        group1Panel.add(ksRadio3);  
        group1Panel.add(ksRadio4);  
        parent.add(group1Panel);
        
        RequirementsService.Util.getInstance().getRuleRationale("test", "kuali.luStatementType.prereqAcademicReadiness", new AsyncCallback<String>() {
            public void onFailure(Throwable caught) {
                // just re-throw it and let the uncaught exception handler deal with it
                Window.alert(caught.getMessage());
                // throw new RuntimeException("Unable to load BusinessRuleInfo objects", caught);
            }

            public void onSuccess(String ruleRationale) {
                taPreReqRationale.setText(ruleRationale);
            }
        }); 
        
    }
    
    private void prepareSimpleTreePanel(Panel parent) {
        HorizontalPanel buttons = new HorizontalPanel();
        buttons.setSpacing(20);
        buttons.add(btnSimpleLevel);
        buttons.add(btnAddRequirement);
        parent.add(buttons);
    } 
    
    private void prepareBooleanAlgebraPanel(Panel parent) {
        parent.add(new Label("Algebra"));
        tbAlgebra = new LumTextBox<LumModelObject>(LumModelObject.FieldName.ALGEBRA.toString());
        pnlRuleTable = new LumRulePanel(LumModelObject.FieldName.
                ALGEBRA.toString());
        parent.add(pnlRuleTable);
        pnlRuleTable.setPreferredSize(
                Double.toString(parent.getOffsetWidth() * 0.9),
                Double.toString(parent.getOffsetHeight() * 0.5));
        parent.add(tbAlgebra);
    }
    
    private void prepareNaturalLanguagePanel2(Panel parent) {
        //naturalLanguageSection.addPanel("Course Calendar", new Label("Student must have completed...."));  
        //naturalLanguageSection.addPanel("Department Brochure", new Label("Student must have completed....2"));
        //parent.add(naturalLanguageSection);
        
        RequirementsService.Util.getInstance().getNaturalLanguage("test", "kuali.luStatementType.prereqAcademicReadiness", new AsyncCallback<String[] >() {
            public void onFailure(Throwable caught) {
                // just re-throw it and let the uncaught exception handler deal with it
                Window.alert(caught.getMessage());
                // throw new RuntimeException("Unable to load BusinessRuleInfo objects", caught);
            }

            public void onSuccess(String[] naturalLanguage) {
                section1 = new KSDisclosureSection(naturalLanguage[0], null, false);
                section2 = new KSDisclosureSection(naturalLanguage[2], null, false);   
                
                section1.clear();  
                section1.add(new Label(naturalLanguage[1]));  
                section2.clear();  
                section2.add(new Label(naturalLanguage[3])); 
                section1.setStyleName("KS-Disclosure-Section1");
                section2.setStyleName("KS-Disclosure-Section1");
                
                pnlNaturalLanguage1.add(section1);
                pnlNaturalLanguage1.add(section2);                
            }
        });                               
    }    
    
    public void setUpListeners() {
        Model<LumModelObject> model = (Model<LumModelObject>) controller.getModel(LumModelObject.class);
        binding1 = new ModelBinding<LumModelObject>(model, tbStatementId);
        binding2 = new ModelBinding<LumModelObject>(model, pnlViews);
        binding3 = new ModelBinding<LumModelObject>(model, pnlRuleTable);
        btnComplexLevel.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent clickEvent) {
                System.out.println("btnComplexLevel clicked");
                if (lumUIListeners != null && !lumUIListeners.isEmpty()) {
                    for (LumUIEventListener listener : lumUIListeners) {
                        listener.switchToComplexView();
                    }
                }
            }
        });
        btnSimpleLevel.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent clickEvent) {
                System.out.println("btnSimpleLevel clicked");
                if (lumUIListeners != null && !lumUIListeners.isEmpty()) {
                    for (LumUIEventListener listener : lumUIListeners) {
                        listener.switchToSimpleView();
                    }
                }
            }
        });          
        btnAddRequirement.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent clickEvent) {
                System.out.println("btnAddRequirement clicked");
                if (lumUIListeners != null && !lumUIListeners.isEmpty()) {
                    for (LumUIEventListener listener : lumUIListeners) {
                        listener.showRequirementDialog();
                    }
                }
            }
        });
        tbAlgebra.addKeyUpHandler(new KeyUpHandler() {
            public void onKeyUp(KeyUpEvent event) {
                System.out.println("tbAlgebra keyuped");
                //escape arrow key  
                if(event.getNativeKeyCode() == 37   
                        ||event.getNativeKeyCode() == 38  
                        ||event.getNativeKeyCode() == 39  
                        ||event.getNativeKeyCode() == 40){  
                    return;  
                }  
                if (lumUIListeners != null && !lumUIListeners.isEmpty()) {
                    for (LumUIEventListener listener : lumUIListeners) {
                        listener.updateAlgebra(tbAlgebra.getText());
                    }
                }
            }
        });
        btnAddCourses.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent clickEvent) {
                System.out.println("btnAddCourses clicked");
                if (lumUIListeners != null && !lumUIListeners.isEmpty()) {
                    for (LumUIEventListener listener : lumUIListeners) {
                        listener.showSearchDialog();
                    }
                }
            }
        });          
    }
    
    public void addLumUIEventListener(LumUIEventListener listener) {
        lumUIListeners.add(listener);
    }
}
