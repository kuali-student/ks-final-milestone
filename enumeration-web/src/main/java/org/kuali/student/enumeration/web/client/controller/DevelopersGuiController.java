package org.kuali.student.enumeration.web.client.controller;

import java.util.List;

import org.kuali.student.commons.ui.messages.client.Messages;
import org.kuali.student.commons.ui.mvc.client.ApplicationContext;
import org.kuali.student.commons.ui.mvc.client.Controller;
import org.kuali.student.commons.ui.mvc.client.model.Model;
import org.kuali.student.commons.ui.viewmetadata.client.ViewMetaData;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.TabPanel;

public class DevelopersGuiController extends Controller {
    public static final String VIEW_NAME = "org.kuali.student.rules.devgui";
    boolean loaded = false;

    final TabPanel tabs = new TabPanel();

    ViewMetaData metadata;
    Messages messages;

    public DevelopersGuiController() {
        super.initWidget(tabs);
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
            metadata = ApplicationContext.getViews().get(DevelopersGuiController.VIEW_NAME);
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
    // super.initializeModel(BusinessRuleInfo.class, businessRule);
    }

    private void loadModelsData() {

        System.out.println("Load Models Data");

    }

    private void doLayout() {

        // tabs.add(rulesVerticalSplitPanel, "Facts");
        tabs.setSize("100%", "900px");
        tabs.selectTab(0);
    }

    private void doEventListenerWiring() {

    }
}
