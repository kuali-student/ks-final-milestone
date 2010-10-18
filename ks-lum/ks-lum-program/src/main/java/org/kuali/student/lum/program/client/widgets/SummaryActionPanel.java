package org.kuali.student.lum.program.client.widgets;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.history.HistoryManager;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.QueryPath;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramManager;
import org.kuali.student.lum.program.client.ProgramStatus;
import org.kuali.student.lum.program.client.events.ModelLoadedEvent;
import org.kuali.student.lum.program.client.events.ModelLoadedEventHandler;
import org.kuali.student.lum.program.client.events.UpdateEvent;
import org.kuali.student.lum.program.client.properties.ProgramProperties;

/**
 * @author Igor
 */
public class SummaryActionPanel extends Composite {

    private HorizontalPanel content = new HorizontalPanel();

    private KSButton approveButton = new KSButton(ProgramProperties.get().button_approve());

    private KSButton activateButton = new KSButton(ProgramProperties.get().button_activate());

    private Anchor exitAnchor = new Anchor(ProgramProperties.get().link_exit());

    private DataModel dataModel;

    public SummaryActionPanel() {
        initWidget(content);
        buildLayout();
        setStyles();
        bind();
    }

    private void bind() {
        ProgramManager.getEventBus().addHandler(ModelLoadedEvent.TYPE, new ModelLoadedEventHandler() {
            @Override
            public void onEvent(ModelLoadedEvent event) {
                dataModel = event.getModel();
                processStatus(ProgramStatus.of(dataModel.<String>get(ProgramConstants.STATE)));
            }
        });
        approveButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                processButtonClick(ProgramStatus.APPROVE);
            }
        });
        activateButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                processButtonClick(ProgramStatus.ACTIVE);
            }
        });
        exitAnchor.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                HistoryManager.navigate("/HOME/CURRICULUM_HOME");
            }
        });
    }

    private void processButtonClick(ProgramStatus status) {
        QueryPath path = new QueryPath();
        path.add(new Data.StringKey(ProgramConstants.STATE));
        dataModel.set(path, status.getValue());
        ProgramManager.getEventBus().fireEvent(new UpdateEvent());
    }

    private void processStatus(ProgramStatus programStatus) {
        if (programStatus == ProgramStatus.DRAFT) {
            enableButtons(true, false);
        } else if (programStatus == ProgramStatus.APPROVE) {
            enableButtons(false, true);
        } else if (programStatus == ProgramStatus.ACTIVE) {
            enableButtons(false, false);
        }
    }

    private void setStyles() {
        content.addStyleName("programActionPanel");
        content.setVerticalAlignment(HasVerticalAlignment.ALIGN_BOTTOM);
    }

    private void buildLayout() {
        content.add(approveButton);
        content.add(activateButton);
        content.add(exitAnchor);
    }

    private void enableButtons(boolean enableApprove, boolean enableActivate) {
        approveButton.setEnabled(enableApprove);
        activateButton.setEnabled(enableActivate);
    }
}
