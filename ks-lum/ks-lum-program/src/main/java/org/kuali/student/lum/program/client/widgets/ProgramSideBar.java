package org.kuali.student.lum.program.client.widgets;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
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
    private Label lastUpdatedDate = new Label();
    private SimplePanel scheduledReviewDate = new SimplePanel();
    private Label lastReviewDate = new Label();

    private DateTimeFormat df = DateTimeFormat.getFormat("dd-MMM-yyyy");

    public ProgramSideBar(HandlerManager eventBus) {
        this.eventBus = eventBus;
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
                lastUpdatedDate.setText(df.format((Date) model.get(ProgramConstants.LAST_UPDATED_DATE)));
                Widget widget = DefaultWidgetFactory.getInstance().getReadOnlyWidget(model.getMetadata(QueryPath.parse(ProgramConstants.SCHEDULED_REVIEW_DATE)));
                HasDataValueBinding.INSTANCE.setWidgetValue((HasDataValue) widget, model, ProgramConstants.SCHEDULED_REVIEW_DATE);
                scheduledReviewDate.setWidget(widget);
                lastReviewDate.setText(model.<String>get(ProgramConstants.LAST_REVIEW_DATE));
            }
        });
    }

    private void buildLayout() {
        content.clear();
        content.add(new Label(ProgramProperties.get().sideBar_history()));
        content.add(new Label(ProgramProperties.get().sideBar_version(version)));
        content.add(createDatePanel(ProgramProperties.get().sideBar_programLastUpdated(), lastUpdatedDate, false));
        content.add(createDatePanel(ProgramProperties.get().sideBar_scheduledReviewDate(), scheduledReviewDate, true));
        content.add(createDatePanel(ProgramProperties.get().sideBar_lastReviewDate(), lastReviewDate, true));
    }

    private Widget createDatePanel(String title, Widget widget, boolean showEdit) {
        VerticalPanel verticalPanel = new VerticalPanel();
        HorizontalPanel datePanel = new HorizontalPanel();
        datePanel.add(widget);
        if (state == State.EDIT && showEdit) {
            Anchor edit = new Anchor("edit");
            edit.addClickHandler(new ClickHandler() {

                @Override
                public void onClick(ClickEvent event) {
                    Window.alert("Dialog popup");
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
    }

    public static enum State {
        EDIT,
        VIEW
    }
}
