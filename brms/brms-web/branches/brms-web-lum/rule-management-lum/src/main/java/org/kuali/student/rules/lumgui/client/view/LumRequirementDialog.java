package org.kuali.student.rules.lumgui.client.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.kuali.student.commons.ui.messages.client.Messages;
import org.kuali.student.commons.ui.mvc.client.ApplicationContext;
import org.kuali.student.commons.ui.mvc.client.Controller;
import org.kuali.student.commons.ui.mvc.client.MVC;
import org.kuali.student.commons.ui.mvc.client.widgets.ModelWidget;
import org.kuali.student.commons.ui.viewmetadata.client.ViewMetaData;
import org.kuali.student.rules.lumgui.client.controller.LumGuiController;
import org.kuali.student.rules.lumgui.client.model.ILumModelObject;
import org.kuali.student.rules.lumgui.client.model.LumModelObject;
import org.kuali.student.rules.lumgui.client.model.ReqComponentTypeInfo;
import org.kuali.student.rules.lumgui.client.service.LumGuiService;

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
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author stse & Zdenek
 */
public class LumRequirementDialog <T extends ILumModelObject> extends Composite implements ModelWidget<T> {
   
    // controller and meta data to be looked up externally
    Controller controller;
    ViewMetaData metadata;
    Messages messages;
    private T modelObject;
    private String modelObjectFieldName;   
    List<LumUIEventListener> lumUIListeners = new ArrayList<LumUIEventListener>(7);    
    
    SimplePanel mainDialogPanel = new SimplePanel();
    final FlexTable dialogBorderPanel = new FlexTable();
   // KSModalDialogPanel reqEditDialog = new KSModalDialogPanel();
    DialogBox reqEditDialog = new DialogBox();
    final VerticalPanel dialogContents = new VerticalPanel();       
    ListBox requirementTypesList = new ListBox();
    TextArea exampleArea = new TextArea();
    Button btnCancelRequirement = new Button("Cancel");
    Button btnAddComposedRequirement = new Button("Add");
    
    boolean loaded = false;

    public LumRequirementDialog() {
      super.initWidget(mainDialogPanel);
    }

    public void update(T modelObject) {
        System.out.println("in update()...");
        this.modelObject = modelObject;
        boolean show = ((LumModelObject)modelObject).isShowRequirementDialog();
        if (show) {
            System.out.println("Showing dialog...");
            reqEditDialog.show();
        } else {
            System.out.println("Hiding dialog...");
            reqEditDialog.hide();
        }      
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
        
        LumGuiService.Util.getInstance().getReqComponentTypesForLuStatementType("1", new AsyncCallback<List<ReqComponentTypeInfo>>() {
            public void onFailure(Throwable caught) {
                // just re-throw it and let the uncaught exception handler deal with it
                Window.alert(caught.getMessage());
                // throw new RuntimeException("Unable to load BusinessRuleInfo objects", caught);
            }

            public void onSuccess(List<ReqComponentTypeInfo> reqComponentTypeInfoList) {
                //lumModelObject.setReqComponentTypeInfoList(reqComponentTypeInfoList);
                for (ReqComponentTypeInfo reqInfo : reqComponentTypeInfoList) {                   
                    requirementTypesList.addItem(reqInfo.getName(), reqInfo.getDesc());
                    System.out.println(reqInfo.getName());
                }
            }
        });           
        
        // get a reference to our view meta data and internationalization messages
        metadata = ApplicationContext.getViews().get(LumGuiController.VIEW_NAME);
        messages = metadata.getMessages();
 
        // Setup Requirement Type selection area
        requirementTypesList.setVisibleItemCount(10);
        VerticalPanel multiBoxPanel = new VerticalPanel();        
        multiBoxPanel.add(new Label("Requirement Types"));
        multiBoxPanel.add(requirementTypesList);        
        final SimplePanel reqWrapperPanel = new SimplePanel();        
        reqWrapperPanel.add(multiBoxPanel);
        reqWrapperPanel.setStyleName("KSLumPanel");   
        
        //Setup Requirement Type Example area
        VerticalPanel examplePanel = new VerticalPanel();
        examplePanel.setSize("100%", "100%");
        examplePanel.add(new Label("Examples"));
        examplePanel.add(exampleArea);
        final SimplePanel exampleWrapperPanel = new SimplePanel();         
        exampleWrapperPanel.add(examplePanel); 
        exampleWrapperPanel.setStyleName("KSLumPanel");     
               
        final HorizontalPanel reqTypesChoicePanel = new HorizontalPanel();         
        reqTypesChoicePanel.setSize("100%", "100%");
        reqTypesChoicePanel.add(reqWrapperPanel);
        reqTypesChoicePanel.add(exampleWrapperPanel);           
        reqTypesChoicePanel.setStyleName("KSLumContentPanel");                   

        // Setup Requirement Composition area
        TextArea requirementCompositionText = new TextArea();
        VerticalPanel compositionText = new VerticalPanel();
        compositionText.add(new Label("Work Area"));
        compositionText.add(requirementCompositionText);
        compositionText.setStyleName("KSLumPanel");
        SimplePanel requirementCompositionPanel = new SimplePanel();        
        requirementCompositionPanel.setSize("100%", "100px");        
        requirementCompositionPanel.add(compositionText); 
        requirementCompositionPanel.setStyleName("KSLumContentPanel");           
        
        // Setup bottom buttons area      
        DockPanel bottomDockPanel = new DockPanel();
        bottomDockPanel.setSize("100%", "100%");
        bottomDockPanel.setHorizontalAlignment(DockPanel.ALIGN_LEFT);
        bottomDockPanel.add(btnCancelRequirement, DockPanel.WEST);
        bottomDockPanel.setHorizontalAlignment(DockPanel.ALIGN_RIGHT);
        bottomDockPanel.add(btnAddComposedRequirement, DockPanel.EAST);        
        SimplePanel bottomWrapperPanel = new SimplePanel();        
        bottomWrapperPanel.add(bottomDockPanel);
        bottomWrapperPanel.setStyleName("KSLumPanel");           
        
        // setup the dialog itself
        dialogContents.setSize("500px", "400px");   
        dialogContents.add(reqTypesChoicePanel);
        dialogContents.add(requirementCompositionPanel); 
        dialogContents.add(bottomWrapperPanel);
       
        //dialogBorderPanel.setCellSpacing(5);        
        //dialogBorderPanel.setWidget(0, 0, dialogContents);
        // reqEditDialog.add(dialogBorderPanel);  
    
        mainDialogPanel.add(reqEditDialog);
        mainDialogPanel.setSize("500px", "200px");
        
        reqEditDialog.setWidget(dialogContents);
        reqEditDialog.setText("Add a Requirement");      
        reqEditDialog.center();                     
        reqEditDialog.setAnimationEnabled(true);
        reqEditDialog.setTitle("Add a Requirement");
        //addBorder(reqEditDialog);
  

        
        //reqEditDialog.setHeader("Add a Requirement");              
        //reqEditDialog.setPixelSize((int)(mainLumPanel.getOffsetWidth() * 0.8), (int)(mainLumPanel.getOffsetHeight() * 0.7));           
        
        reqWrapperPanel.setWidth(Double.toString(reqTypesChoicePanel.getOffsetWidth() * 0.3));          
        exampleWrapperPanel.setWidth(Double.toString(reqTypesChoicePanel.getOffsetWidth() * 0.7));                 
        examplePanel.setSize("100%", Double.toString(examplePanel.getOffsetHeight() * 0.7));
        requirementCompositionText.setSize("500px", "100px");                           
    }   
    
    private void addBorder(Widget widget) {
        widget.getElement().getStyle().setProperty("border", "1px solid #87b3ff");
    }
    
    public void setUpListeners() {    
        
        btnCancelRequirement.addClickListener(new ClickListener() {
            public void onClick(Widget sender) {
                System.out.println("btnCancelRequirement clicked");
                if (lumUIListeners != null && !lumUIListeners.isEmpty()) {
                    for (LumUIEventListener listener : lumUIListeners) {
                        //TODO check if user is not in the middle of editing i.e. ask if 'Abort changes?'
                        listener.hideRequirementDialog();
                    }
                }
            }
        });  
        requirementTypesList.addClickListener(new ClickListener() {
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
    
    public void add(T modelObject) {
        this.modelObject = modelObject;
    }

    public void addBulk(Collection<T> collection) {
        if (collection != null && !collection.isEmpty()) {
            this.modelObject = collection.iterator().next();
        }
    }

    public void clear() {
        modelObject = null;
    }

    public List<T> getItems() {
        List<T> items = new ArrayList<T>();
        items.add(modelObject);
        return items;
    }

    public T getSelection() {
        return modelObject;
    }

    public void remove(T modelObject) {
    }

    public void select(T modelObject) {
    }    
    
}
