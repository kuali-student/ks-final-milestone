/**
 * 
 */
package org.kuali.student.rules.lumgui.client.controller;

import java.util.List;

import org.kuali.student.commons.ui.messages.client.Messages;
import org.kuali.student.commons.ui.mvc.client.ApplicationContext;
import org.kuali.student.commons.ui.mvc.client.Controller;
import org.kuali.student.commons.ui.mvc.client.model.Model;
import org.kuali.student.commons.ui.viewmetadata.client.ViewMetaData;
import org.kuali.student.rules.lumgui.client.model.LumModelObject;
import org.kuali.student.rules.lumgui.client.service.LumGuiService;
import org.kuali.student.rules.lumgui.client.view.LumComposite;
import org.kuali.student.rules.lumgui.client.view.LumUIEventListener;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author zzraly
 */
public class LumGuiController extends Controller implements LumUIEventListener {
    public static final String VIEW_NAME = "org.kuali.student.rules.lumgui";
    boolean loaded = false;

    final SimplePanel mainPanel = new SimplePanel();
    final LumComposite lumComposite = new LumComposite();
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
    }

    private void doLayout() {
        mainPanel.add(lumComposite);
        mainPanel.setSize("850px", "450px");
        lumComposite.setSize("850px", "450px");
        lumComposite.layoutWidgets();
    }

    // for debugging
    private void addBorder(Widget widget) {
        widget.getElement().getStyle().setProperty("border", "1px solid #87b3ff");
    }

    private void doEventListenerWiring() {
        lumComposite.setUpListeners();
        lumComposite.addLumUIEventListener(this);
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
        lumModelObject.setShowRequirementDialog(true);
        model.update(lumModelObject);        
    }
    
    public void hideRequirementDialog() {
        lumModelObject.setShowRequirementDialog(false);
        model.update(lumModelObject);        
    }    
}
