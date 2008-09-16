/**
 * 
 */
package org.kuali.student.rules.devgui.client.controller;

import java.util.List;

import org.kuali.student.commons.ui.messages.client.Messages;
import org.kuali.student.commons.ui.mvc.client.ApplicationContext;
import org.kuali.student.commons.ui.mvc.client.Controller;
import org.kuali.student.commons.ui.mvc.client.model.Model;
import org.kuali.student.commons.ui.viewmetadata.client.ViewMetaData;
import org.kuali.student.rules.devgui.client.service.DevelopersGuiService;
import org.kuali.student.rules.devgui.client.model.BusinessRuleType;
import org.kuali.student.rules.devgui.client.model.RulesHierarchyInfo;
import org.kuali.student.rules.devgui.client.view.RuleTypesComposite;
import org.kuali.student.rules.devgui.client.view.RulesComposite;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.TabPanel;

/**
 * @author zzraly
 */
public class DevelopersGuiController extends Controller {
    public static final String VIEW_NAME = "org.kuali.student.rules.devgui";
    boolean loaded = false;

    // final Model<BusinessRuleInfo> businessRule = new Model<BusinessRuleInfo>();
    final Model<BusinessRuleType> businessRuleType = new Model<BusinessRuleType>();
    final Model<RulesHierarchyInfo> rulesHierarchyInfo = new Model<RulesHierarchyInfo>();

    final TabPanel tabs = new TabPanel();
    final RulesComposite businessRuleComposite = new RulesComposite();
    final RuleTypesComposite ruleTypesComposite = new RuleTypesComposite();

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
        super.initializeModel(BusinessRuleType.class, businessRuleType);
        super.initializeModel(RulesHierarchyInfo.class, rulesHierarchyInfo);
    }

    private void loadModelsData() {
        /*
        DevelopersGuiService.Util.getInstance().findBusinessRules(new AsyncCallback<List<BusinessRuleInfo>>() {
            public void onFailure(Throwable caught) {
                // just rethrow it and let the uncaught exception handler deal with it
                throw new RuntimeException("Unable to load BusinessRuleInfo objects", caught);
            }

            public void onSuccess(List<BusinessRuleInfo> rules) {
                // add the results to the model
                for (BusinessRuleInfo rule : rules) {
                    businessRule.add(rule);
                }
            }
        });  */

        DevelopersGuiService.Util.getInstance().findRulesHierarchyInfo(new AsyncCallback<List<RulesHierarchyInfo>>() {
            public void onFailure(Throwable caught) {
                // just rethrow it and let the uncaught exception handler deal with it
                throw new RuntimeException("Unable to load BusinessRuleInfo objects", caught);
            }

            public void onSuccess(List<RulesHierarchyInfo> rulesInfo) {
                // add the results to the model
                for (RulesHierarchyInfo ruleInfo : rulesInfo) {
                    rulesHierarchyInfo.add(ruleInfo);
                }
            }
        });

    }

    private void doLayout() {
        tabs.add(businessRuleComposite, "Rules");
        tabs.add(ruleTypesComposite, "Business Rule Types");
        // tabs.add(rulesVerticalSplitPanel, "Facts");
        tabs.setSize("100%", "800px");
        tabs.selectTab(0);
    }

    private void doEventListenerWiring() {
    // when a business rule is added TODO
    /*
    businessRule.addListener(ModelChangeEvent.AddEvent.class, new MVCEventListener() {
        public void onEvent(Class<? extends MVCEvent> event, Object data) {
        // businessRuleComposite.testForm(((BusinessRuleInfo) data).getName());
        }
    });  */
    }
}
