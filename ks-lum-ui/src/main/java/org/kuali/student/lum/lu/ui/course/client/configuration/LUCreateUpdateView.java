package org.kuali.student.lum.lu.ui.course.client.configuration;



import org.kuali.student.common.ui.client.application.ApplicationComposite;
import org.kuali.student.common.ui.client.configurable.ConfigurableLayout;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.View;
import org.kuali.student.common.validator.Validator;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.ui.course.client.configuration.history.KSHistory;
import org.kuali.student.lum.lu.ui.course.client.service.CluProposal;
import org.kuali.student.lum.lu.ui.main.client.controller.LUMApplicationManager.LUMViews;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.IncrementalCommand;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;

public class LUCreateUpdateView extends Composite implements View {

    ApplicationComposite app = new ApplicationComposite();
    private final SimplePanel panel = new SimplePanel();
    private ConfigurableLayout<CluProposal> layout;
    private final String luType;
    private final String luState;
    private String id = null;

    public LUCreateUpdateView(String type, String state) {
        this.luType = type;
        this.luState = state;		        
        app.setContent(panel);
        super.initWidget(app);

        layout = getLayout(luType, luState);
    }

    private ConfigurableLayout<CluProposal> getLayout(String type, String state) {	   	
        LULayoutFactory factory = new LULayoutFactory();

        DefaultCreateUpdateLayout<CluProposal> layout = (DefaultCreateUpdateLayout<CluProposal>)factory.getLayout(type, state);
//      history = new KSHistory(getController(), layout);
        layout.addCancelSectionHandler(new ClickHandler(){
            public void onClick(ClickEvent event) {
                LUCreateUpdateView.this.getController().showView(LUMViews.HOME_MENU);
            }            
        });     

        return layout;
    }

    @Override
    public boolean beforeHide() {
        // TODO check if there are unsaved changes
        return true;
    }

    @Override
    public void beforeShow() {
        IncrementalCommand command = new IncrementalCommand() {

            @Override
            public boolean execute() {
                if(layout == null) {
                    return true; //wait for stupid thing to load
                }
                panel.setWidget(layout);
                if (id == null){
                    CluProposal cluProposal = new CluProposal();
                    CluInfo cluInfo = new CluInfo();
                    cluInfo.setState(luState);
                    cluInfo.setType(luType);
                    
                    cluProposal.setCluInfo(cluInfo);        
                    layout.setObject(cluProposal);                    
                    layout.render();
                } else {
                    //Load an existing clu
                }

                return false;
            }

        };
        if(command.execute()) //only scheduling it if I must
            DeferredCommand.addCommand(command);

    }

    @Override
    public Controller getController() {
        return Controller.findController(this);
    }

    @Override
    public String getName() {
        return this.getClass().getName();
    }

    public ConfigurableLayout<CluProposal> getLayout() {
        return layout;
    }

    
    public void setId(String id){
        this.id = id;
    }
    
    public void addLayoutToHistory(final KSHistory history, final LUMViews create_course) {
        IncrementalCommand command = new IncrementalCommand() {

            @Override
            public boolean execute() {
                if(layout == null) {
                    return true; //wait for stupid thing to load
                }
                history.addLayoutToView(create_course, layout);
                DefaultCreateUpdateLayout<CluProposal> l = null;
                boolean showStart = true;
                if(layout instanceof DefaultCreateUpdateLayout) {
                    l = (DefaultCreateUpdateLayout<CluProposal>)layout;
                    showStart = l.isShowStartSectionEnabled();
                    l.setShowStartSectionEnabled(false);//TODO this needs to be figured out
                }
                History.fireCurrentHistoryState();
                if(l != null)
                    l.setShowStartSectionEnabled(showStart);
                return false;
            }

        };
        if(command.execute()) //only scheduling it if I must
            DeferredCommand.addCommand(command);
    }

}
