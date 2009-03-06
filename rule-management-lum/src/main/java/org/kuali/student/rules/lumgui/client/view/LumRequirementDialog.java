package org.kuali.student.rules.lumgui.client.view;

import org.kuali.student.commons.ui.messages.client.Messages;
import org.kuali.student.commons.ui.mvc.client.ApplicationContext;
import org.kuali.student.commons.ui.mvc.client.Controller;
import org.kuali.student.commons.ui.mvc.client.MVC;
import org.kuali.student.commons.ui.viewmetadata.client.ViewMetaData;
import org.kuali.student.rules.lumgui.client.controller.LumGuiController;
import org.kuali.student.rules.lumgui.client.model.LumModelObject;

import com.google.gwt.user.client.ui.Button;
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

/**
 * @author stse & Zdenek
 */
public class LumRequirementDialog extends Composite {
 /*   
    // controller and meta data to be looked up externally
    Controller controller;
    ViewMetaData metadata;
    Messages messages;
    
    KSModalDialogPanel reqEditDialog = new KSModalDialogPanel();
    Button btnCancelRequirement;
    Button btnAddComposedRequirement;
    
    final FlexTable dialogBorderPanel = new FlexTable();
    final VerticalPanel dialogContents = new VerticalPanel();    
    final HorizontalPanel reqTypesChoicePanel = new HorizontalPanel();    
    VerticalPanel multiBoxPanel = new VerticalPanel();
    SimplePanel requirementCompositionPanel = new SimplePanel();
    VerticalPanel bottomPanel = new VerticalPanel();
    
    boolean loaded = false;

    public LumRequirementDialog() {
        super.initWidget(reqEditDialog);
    }

    @Override
    protected void onLoad() {
        super.onLoad();
        if (!loaded) {
            loaded = true;
            // get a reference to our parent controller
            controller = MVC.findParentController(this);
            layoutWidgets();
        }
    }
    
    public void layoutWidgets() {
        // get a reference to our view meta data and internationalization messages
        metadata = ApplicationContext.getViews().get(LumGuiController.VIEW_NAME);
        messages = metadata.getMessages();
        
       
        // sizes and layout are to be done AFTER the containing panels
        // are added to mainLumPanel to make size settings effective
        
        
        reqEditDialog.setPixelSize((int)(mainLumPanel.getOffsetWidth() * 0.8), (int)(mainLumPanel.getOffsetHeight() * 0.7));           

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
        
    }   */  
}
