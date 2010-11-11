package org.kuali.student.lum.program.client.widgets;

import java.util.Date;

import org.kuali.student.common.ui.client.application.ViewContext;
import org.kuali.student.common.ui.client.configurable.mvc.DefaultWidgetFactory;
import org.kuali.student.common.ui.client.configurable.mvc.binding.HasDataValueBinding;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.HasDataValue;
import org.kuali.student.common.ui.client.mvc.history.HistoryManager;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.assembly.data.QueryPath;
import org.kuali.student.lum.common.client.widgets.AppLocations;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramUtils;
import org.kuali.student.lum.program.client.events.AfterSaveEvent;
import org.kuali.student.lum.program.client.events.ModelLoadedEvent;
import org.kuali.student.lum.program.client.major.MajorController;
import org.kuali.student.lum.program.client.properties.ProgramProperties;

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
    private Label historyLabel = new Label(ProgramProperties.get().sideBar_history());    
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
    	setVersion((Long)model.get(ProgramConstants.VERSION),versionLabel);
        setDate((Date) model.get(ProgramConstants.LAST_UPDATED_DATE), lastUpdatedDate);
        lastReviewDate.setText((String) model.get(ProgramConstants.LAST_REVIEW_DATE));
        setWidget(ProgramConstants.SCHEDULED_REVIEW_DATE, scheduledReviewDate, model);
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
    		versionLabel.setText(ProgramProperties.get().sideBar_version(String.valueOf(version)));
    	} else {
    		versionLabel.setText(ProgramProperties.get().sideBar_version(""));
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
        content.add(createVersionPanel());
        content.add(createDatePanel(ProgramProperties.get().sideBar_programLastUpdated(), lastUpdatedDate, false));
        if (type == Type.MAJOR) {
            content.add(createDatePanel(ProgramProperties.get().sideBar_scheduledReviewDate(), scheduledReviewDate, true));
            content.add(createDatePanel(ProgramProperties.get().sideBar_lastReviewDate(), lastReviewDate, true));
        }
    }

    private Widget createDatePanel(String title, Widget widget, boolean showEdit) {
        VerticalPanel verticalPanel = new VerticalPanel();
        HorizontalPanel datePanel = new HorizontalPanel();
        datePanel.addStyleName("datePanel");
        datePanel.add(widget);
        if (state == State.EDIT && showEdit) {
            Anchor edit = new Anchor(ProgramProperties.get().common_edit());
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

    private Widget createVersionPanel(){
    	VerticalPanel  verticalPanel = new VerticalPanel();
    	versionLabel = new Label(ProgramProperties.get().sideBar_version(""));
    	viewVersion = new Anchor(ProgramProperties.get().sideBar_viewHistory());
    	viewVersion.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				HistoryManager.navigate(AppLocations.Locations.VIEW_PROGRAM_VERSIONS.getLocation());			
			}
    		
    	});
    	//viewVersion.setVisible(false);
    	
    	verticalPanel.add(versionLabel);
    	verticalPanel.add(viewVersion);
    	
    	return verticalPanel;
    }
    
    public void setState(State state) {
        this.state = state;
        buildLayout();
    }

    private void setStyles() {
        content.addStyleName("sideBar");
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
}
