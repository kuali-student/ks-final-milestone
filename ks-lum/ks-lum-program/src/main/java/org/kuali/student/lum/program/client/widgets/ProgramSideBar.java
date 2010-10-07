package org.kuali.student.lum.program.client.widgets;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.*;
import org.kuali.student.common.ui.client.configurable.mvc.DefaultWidgetFactory;
import org.kuali.student.common.ui.client.configurable.mvc.binding.HasDataValueBinding;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.HasDataValue;
import org.kuali.student.core.assembly.data.QueryPath;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.events.ModelLoadedEvent;
import org.kuali.student.lum.program.client.events.ModelLoadedEventHandler;
import org.kuali.student.lum.program.client.properties.ProgramProperties;

import java.util.Date;

/**
 * @author Igor
 */
public class ProgramSideBar extends Composite {

    private VerticalPanel content = new VerticalPanel();

    private State state = State.VIEW;

    private HandlerManager eventBus;

    private String version = "1";
    private Label historyLabel = new Label(ProgramProperties.get().sideBar_history());
    private Label lastUpdatedDate = new Label();
    private SimplePanel scheduledReviewDate = new SimplePanel();
    private SimplePanel lastReviewDate = new SimplePanel();

    private SideBarDialogManager dialogManager;

    private DateTimeFormat df = DateTimeFormat.getFormat("dd-MMM-yyyy");

    public ProgramSideBar(HandlerManager eventBus) {
        this.eventBus = eventBus;
        dialogManager = new SideBarDialogManager(eventBus);
        initWidget(content);
        setStyles();
        buildLayout();
        bind();
    }

    private void bind() {
        eventBus.addHandler(ModelLoadedEvent.TYPE, new ModelLoadedEventHandler() {
            @Override
            public void onEvent(ModelLoadedEvent event) {
                DataModel model = event.getModel();
                Date updatedDate = (Date) model.get(ProgramConstants.LAST_UPDATED_DATE);
                if (updatedDate != null) {
                    lastUpdatedDate.setText(df.format(updatedDate));
                }
                setWidget(ProgramConstants.SCHEDULED_REVIEW_DATE, scheduledReviewDate, model);
                setWidget(ProgramConstants.LAST_REVIEW_DATE, lastReviewDate, model);
            }
        });
    }

    private void setWidget(String path, SimplePanel container, DataModel model) {
        Widget widget = DefaultWidgetFactory.getInstance().getReadOnlyWidget(model.getMetadata(QueryPath.parse(path)));
        HasDataValueBinding.INSTANCE.setWidgetValue((HasDataValue) widget, model, path);
        container.setWidget(widget);
    }


    private void buildLayout() {
        content.clear();
        content.add(historyLabel);
        content.add(new Label(ProgramProperties.get().sideBar_version(version)));
        content.add(createDatePanel(ProgramProperties.get().sideBar_programLastUpdated(), lastUpdatedDate, false));
        content.add(createDatePanel(ProgramProperties.get().sideBar_scheduledReviewDate(), scheduledReviewDate, true));
        content.add(createDatePanel(ProgramProperties.get().sideBar_lastReviewDate(), lastReviewDate, true));
    }

    private Widget createDatePanel(String title, Widget widget, boolean showEdit) {
        VerticalPanel verticalPanel = new VerticalPanel();
        HorizontalPanel datePanel = new HorizontalPanel();
        datePanel.addStyleName("datePanel");
        datePanel.add(widget);
        if (state == State.EDIT && showEdit) {
            Anchor edit = new Anchor("edit");
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
}
