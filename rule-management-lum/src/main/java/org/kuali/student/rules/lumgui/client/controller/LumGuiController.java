/**
 * 
 */
package org.kuali.student.rules.lumgui.client.controller;

import java.util.List;

import org.kuali.student.commons.ui.messages.client.Messages;
import org.kuali.student.commons.ui.mvc.client.ApplicationContext;
import org.kuali.student.commons.ui.mvc.client.Controller;
import org.kuali.student.commons.ui.mvc.client.model.Model;
import org.kuali.student.commons.ui.mvc.client.widgets.ModelBinding;
import org.kuali.student.commons.ui.viewmetadata.client.ViewMetaData;
import org.kuali.student.rules.lumgui.client.model.LumModelObject;
import org.kuali.student.rules.lumgui.client.model.ReqComponentTypeInfo;
import org.kuali.student.rules.lumgui.client.model.RequirementComponentVO;
import org.kuali.student.rules.lumgui.client.model.StatementVO;
import org.kuali.student.rules.lumgui.client.service.LumGuiService;
import org.kuali.student.rules.lumgui.client.view.LumComposite;
import org.kuali.student.rules.lumgui.client.view.LumRequirementDialog;
import org.kuali.student.rules.lumgui.client.view.LumUIEventListener;
import org.kuali.student.rules.rulemanagement.dto.BusinessRuleInfoDTO;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author zzraly
 */
public class LumGuiController extends Controller implements LumUIEventListener {

    public static final String SCREEN_SIZE_WIDTH = "950px";
    public static final String SCREEN_SIZE_HEIGHT = "550px";
    public static final String VIEW_NAME = "org.kuali.student.rules.lumgui";
    boolean loaded = false;

    final SimplePanel mainPanel = new SimplePanel();
    final LumComposite lumComposite = new LumComposite();
    final LumRequirementDialog<LumModelObject> lumRequirementDialog = new LumRequirementDialog<LumModelObject>();
    ModelBinding<LumModelObject> lumRequirementDialogBinding; 
    final Model<LumModelObject> model = new Model<LumModelObject>();
    final LumModelObject lumModelObject = new LumModelObject();

    ViewMetaData metadata;
    Messages messages;

    public LumGuiController() {
        super.initWidget(mainPanel);
    }

    /**
     * onLoad is called when the widget is bound to the DOM. Most operations should take place in the onLoad event, as some
     * parts of the MVC framework may not be fully initialized at the point the constructor is called. The onLoad event may
     * be called multiple times for a given widget, so a flag should be set to indicate that the widget is already "loaded".
     * 
     * @see com.google.gwt.user.client.ui.Widget#onLoad()
     */
    @Override
    protected void onLoad() {
        super.onLoad();
        if (!loaded) {
            loaded = true;

            // get a reference to our view metadata and internationalization messages
            metadata = ApplicationContext.getViews().get(LumGuiController.VIEW_NAME);
            messages = metadata.getMessages();

            // initialize our controller's models
            setupModels();

            // retrieve initial data from BRMS service
            loadModelsData();

            // set up the tabs, etc
            doLayout();

            // wire up any MVCEvent listeners
            doEventListenerWiring();
        }
    }

    private void setupModels() {
        super.initializeModel(LumModelObject.class, model);
    }

    private void loadModelsData() {    	
    	System.out.println("Load Models Data");
    	lumModelObject.setShowAlgebra(false);
    	lumModelObject.setShowRequirementDialog(false);
    	lumModelObject.setCurrentView(LumModelObject.LumView.SIMPLE_VIEW);
    	//TODO remove when done test
    	lumModelObject.setStatement(getTestStatement());
    	// end test
    	model.update(lumModelObject);
    	
    	lumRequirementDialogBinding = new ModelBinding<LumModelObject>(model, lumRequirementDialog);    
    }
    
    private StatementVO getTestStatement() {
        StatementVO statement = new StatementVO();
        StatementVO subStatement1 = new StatementVO();
        StatementVO subStatement2 = new StatementVO();
        RequirementComponentVO rc1 = new RequirementComponentVO();
        RequirementComponentVO rc2 = new RequirementComponentVO();
        RequirementComponentVO rc3 = new RequirementComponentVO();
        RequirementComponentVO rc4 = new RequirementComponentVO();
        
        
        rc1.setDesc("Student must have completed all of BIO 110, BIO 112, BIO 116, BIO 118");
        rc1.setId("RC_1");
        rc2.setDesc("Minimum GPA of 2.5");
        rc2.setId("RC_2");
        rc3.setDesc("Student must have completed 8 units from First Year Psyc");
        rc3.setId("RC_3");
        rc4.setDesc("Student must have completed 6 units from Pre-med Chemistry");
        rc4.setId("RC_4");
        
        subStatement1.setOperator(StatementVO.Operator.AND);
        subStatement1.addRequirementComponent(rc1);
        subStatement1.addRequirementComponent(rc2);
        subStatement1.setId("statement 1");
        
        subStatement2.setOperator(StatementVO.Operator.OR);
        subStatement2.addRequirementComponent(rc3);
        subStatement2.addRequirementComponent(rc4);
        subStatement2.setId("statement 2");
        
        statement.setOperator(StatementVO.Operator.AND);
        statement.addStatement(subStatement1);
        statement.addStatement(subStatement2);
        statement.setId("root statement");
        
        return statement;
    }

    private void doLayout() {
        mainPanel.add(lumComposite);
        mainPanel.setSize(SCREEN_SIZE_WIDTH, SCREEN_SIZE_HEIGHT);
        lumComposite.setSize(SCREEN_SIZE_WIDTH, SCREEN_SIZE_HEIGHT);
        lumComposite.layoutWidgets();
        lumRequirementDialog.layoutWidgets();
    }

    // for debugging
    private void addBorder(Widget widget) {
        widget.getElement().getStyle().setProperty("border", "1px solid #87b3ff");
    }

    private void doEventListenerWiring() {
        lumComposite.setUpListeners();
        lumComposite.addLumUIEventListener(this);
        lumRequirementDialog.setUpListeners();
        lumRequirementDialog.addLumUIEventListener(this);
   
        //setup for test
        switchToComplexView();
        showRequirementDialog();        
    }

    public void switchToComplexView() {
        lumModelObject.setCurrentView(LumModelObject.LumView.COMPLEX_VIEW);
        model.update(lumModelObject);
    }

    public void switchToSimpleView() {
        lumModelObject.setCurrentView(LumModelObject.LumView.SIMPLE_VIEW);
        model.update(lumModelObject);
    }
 
    public void showRequirementDialog() {
       // lumComposite.removeFromParent();
     //   mainPanel.add(lumRequirementDialog);
        lumModelObject.setShowRequirementDialog(true);
        model.update(lumModelObject);             
    }
    
    public void hideRequirementDialog() {
        lumModelObject.setShowRequirementDialog(false);
        model.update(lumModelObject);        
    }    
    
    public void updateExampleAndWorkArea() {
        model.update(lumModelObject); 
    }    
}
