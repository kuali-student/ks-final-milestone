package org.kuali.student.lum.program.client.widgets;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.kuali.student.r1.common.assembly.data.Metadata;
import org.kuali.student.r1.common.assembly.data.QueryPath;
import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.application.ViewContext;
import org.kuali.student.common.ui.client.configurable.mvc.DefaultWidgetFactory;
import org.kuali.student.common.ui.client.configurable.mvc.binding.HasDataValueBinding;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.HasDataValue;
import org.kuali.student.common.ui.client.mvc.history.HistoryManager;
import org.kuali.student.lum.common.client.widgets.AppLocations;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramMsgConstants;
import org.kuali.student.lum.program.client.ProgramUtils;
import org.kuali.student.lum.program.client.events.AfterSaveEvent;
import org.kuali.student.lum.program.client.events.ModelLoadedEvent;
import org.kuali.student.lum.program.client.major.MajorController;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Igor
 */
public class ProgramSideBar extends Composite {

    private final VerticalPanel content = new VerticalPanel();

    private State state = State.VIEW;

    private Type type;

    private Label versionLabel;
    private Anchor viewVersion;
    private Label historyLabel = new Label(getLabel(ProgramMsgConstants.SIDEBAR_HISTORY));
    private Label lastUpdatedDate = new Label();
    private SimplePanel scheduledReviewDate = new SimplePanel();
    private Label lastReviewDate = new Label();
    private final HandlerManager eventBus;

    private ViewContext viewContext;

    private final SideBarDialogManager dialogManager;

    public ProgramSideBar(HandlerManager eventBus, Type type) {
        this.eventBus = eventBus;
        this.type = type;
        dialogManager = new SideBarDialogManager(eventBus);
        initWidget(content);
        setStyles();
        buildLayout();
        bind();
    }

    public void initialize(MajorController controller) {
        DataModel model = controller.getProgramModel();
        dialogManager.configureView(model.getDefinition(), controller);
        viewContext = controller.getViewContext();
        updateFields(model);
    }

    private void bind() {
        eventBus.addHandler(ModelLoadedEvent.TYPE, new ModelLoadedEvent.Handler() {
            @Override
            public void onEvent(ModelLoadedEvent event) {
                updateFields(event.getModel());
            }
        });
        eventBus.addHandler(AfterSaveEvent.TYPE, new AfterSaveEvent.Handler() {
            @Override
            public void onEvent(AfterSaveEvent event) {
                DataModel model = event.getModel();
                dialogManager.configureView(model.getDefinition(), event.getController());
                updateFields(event.getModel());
            }
        });
    }

    private void updateFields(DataModel model) {
    	String programType = model.get(ProgramConstants.TYPE);

    	//The sidebar fields should only be updated if the model that was changed/loaded is associated with the sidebar
    	boolean doUpdate =	(type == ProgramSideBar.Type.CORE && ProgramConstants.CORE_LU_TYPE_ID.equals(programType)) ||
    						(type == ProgramSideBar.Type.CREDENTIAL && programType.startsWith(ProgramConstants.CRED_LU_TYPE_PREFIX)) ||
    						(type == ProgramSideBar.Type.MAJOR && ProgramConstants.MAJOR_LU_TYPE_ID.equals(programType)); 
    	
    	if (doUpdate){
	    	setVersion((Long)model.get(ProgramConstants.VERSION), versionLabel);
	        setDate((Date) model.get(ProgramConstants.LAST_UPDATED_DATE), lastUpdatedDate);
	        lastReviewDate.setText((String) model.get(ProgramConstants.LAST_REVIEW_DATE));
	        setWidget(ProgramConstants.SCHEDULED_REVIEW_DATE, scheduledReviewDate, model);
    	}
    }

    private void setDate(Date updatedDate, Label lastUpdatedDate) {
        if (updatedDate != null) {
            lastUpdatedDate.setText(ProgramUtils.df.format(updatedDate));
        } else {
            lastUpdatedDate.setText("");
        }
    }

    private void setVersion(Long version, Label versionaLabel){
    	if (version != null){
       		viewVersion.setVisible(version >= 1);
    		versionLabel.setText(getLabel(ProgramMsgConstants.SIDEBAR_VERSION, String.valueOf(version)));
    	} else {
    		versionLabel.setText(getLabel(ProgramMsgConstants.SIDEBAR_VERSION, ""));
    		viewVersion.setVisible(false);
    	}
    }

    private void setWidget(String path, SimplePanel container, DataModel model) {
        Metadata mData = model.getMetadata(QueryPath.parse(path));

        // CoreProgram and CredentialProgram don't have SCHEDULED_REVIEW_DATE
        if (null == mData && ProgramConstants.SCHEDULED_REVIEW_DATE.equals(path)) {
            return;
        }
        Widget widget = DefaultWidgetFactory.getInstance().getReadOnlyWidget(model.getMetadata(QueryPath.parse(path)));
        HasDataValueBinding.INSTANCE.setWidgetValue((HasDataValue) widget, model, path);
        container.setWidget(widget);
    }


    private void buildLayout() {
        content.clear();
        Label history = new Label(getLabel(ProgramMsgConstants.SIDEBAR_HISTORY));
        history.addStyleName("KS-Program-History");
        content.add(history);

        content.add(createVersionPanel());
        content.add(createDatePanel(getLabel(ProgramMsgConstants.SIDEBAR_PROGRAMLASTUPDATED), lastUpdatedDate, false));
        if (type == Type.MAJOR) {
            content.add(createDatePanel(getLabel(ProgramMsgConstants.SIDEBAR_SCHEDULEDREVIEWDATE), scheduledReviewDate, true));
            content.add(createDatePanel(getLabel(ProgramMsgConstants.SIDEBAR_LASTREVIEWDATE), lastReviewDate, true));
        }
        content.add(createVersionHistoryPanel());
    }

    private Widget createDatePanel(String title, Widget widget, boolean showEdit) {
        VerticalPanel verticalPanel = new VerticalPanel();
        HorizontalPanel datePanel = new HorizontalPanel();
        datePanel.addStyleName("datePanel");
        datePanel.add(widget);
        if (state == State.EDIT && showEdit) {
            Anchor edit = new Anchor(getLabel(ProgramMsgConstants.COMMON_EDIT));
            edit.addClickHandler(new ClickHandler() {

                @Override
                public void onClick(ClickEvent event) {
                    dialogManager.show();
                }
            });
            datePanel.add(edit);
        }
        verticalPanel.add(new Label(title));
        verticalPanel.add(datePanel);
        return verticalPanel;
    }

    private Widget createHistoryPanel(){
    	VerticalPanel  verticalPanel = new VerticalPanel();
    	versionLabel = new Label(getLabel(ProgramMsgConstants.SIDEBAR_VERSION, ""));
    	verticalPanel.add(versionLabel);
    	return verticalPanel;
    }

    private Widget createVersionPanel(){
    	VerticalPanel  verticalPanel = new VerticalPanel();
    	versionLabel = new Label(getLabel(ProgramMsgConstants.SIDEBAR_VERSION, ""));
    	verticalPanel.add(versionLabel);
    	return verticalPanel;
    }

    private Widget createVersionHistoryPanel(){
    	VerticalPanel  verticalPanel = new VerticalPanel();
    	viewVersion = new Anchor(getLabel(ProgramMsgConstants.SIDEBAR_VIEWHISTORY));
    	viewVersion.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				switch (type){
					case MAJOR: HistoryManager.navigate(AppLocations.Locations.VIEW_PROGRAM_VERSIONS.getLocation()); break;
					case CORE: HistoryManager.navigate(AppLocations.Locations.VIEW_CORE_VERSIONS.getLocation()); break;
					case CREDENTIAL: HistoryManager.navigate(AppLocations.Locations.VIEW_BACC_VERSIONS.getLocation()); break;
				}


			}

    	});
    	//viewVersion.setVisible(false);

    	verticalPanel.add(viewVersion);

    	return verticalPanel;
    }

    public void setState(State state) {
        this.state = state;
        buildLayout();
    }

    private void setStyles() {
        content.addStyleName("sideBar");
        content.addStyleName("KS-Program-History-Sidebar");
        historyLabel.addStyleName("history");
    }

    public static enum State {
        EDIT,
        VIEW
    }

    public static enum Type {
        CREDENTIAL,
        CORE,
        MAJOR
    }
    
    private String getLabel(String messageKey) {
        return Application.getApplicationContext().getUILabel(ProgramMsgConstants.PROGRAM_MSG_GROUP, messageKey);
    }
    
    private String getLabel(String messageKey, String parameter) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("0", parameter);
        return Application.getApplicationContext().getUILabel(ProgramMsgConstants.PROGRAM_MSG_GROUP, messageKey, parameters);
    } 
}
