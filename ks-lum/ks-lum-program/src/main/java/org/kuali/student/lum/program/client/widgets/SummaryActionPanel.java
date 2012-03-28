package org.kuali.student.lum.program.client.widgets;

import java.util.List;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.history.HistoryManager;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSButtonAbstract.ButtonStyle;
import org.kuali.student.common.ui.client.widgets.KSLightBox;
import org.kuali.student.common.ui.client.widgets.notification.KSNotification;
import org.kuali.student.common.ui.client.widgets.notification.KSNotifier;
import org.kuali.student.lum.common.client.widgets.AppLocations;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramMsgConstants;
import org.kuali.student.lum.program.client.ProgramStatus;
import org.kuali.student.lum.program.client.events.AfterSaveEvent;
import org.kuali.student.lum.program.client.events.ModelLoadedEvent;
import org.kuali.student.lum.program.client.events.StateChangeEvent;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.infc.ValidationResult.ErrorLevel;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Igor
 */
public class SummaryActionPanel extends Composite {

    private final HorizontalPanel content = new HorizontalPanel();

    private final KSButton approveButton = new KSButton(getLabel(ProgramMsgConstants.BUTTON_APPROVE));

    private final KSButton activateButton = new KSButton(getLabel(ProgramMsgConstants.BUTTON_ACTIVATE), ButtonStyle.SECONDARY);

    private final Anchor exitAnchor = new Anchor(getLabel(ProgramMsgConstants.LINK_BACKCURRICULUM));

    private final KSLightBox activateDialog = new KSLightBox();
    private Section activateSection;

    private DataModel dataModel;

    private HandlerManager eventBus;

    public SummaryActionPanel(Section activateSection, HandlerManager eventBus) {
        initWidget(content);
        this.activateSection = activateSection;
        this.eventBus = eventBus;
        buildLayout();
        setStyles();
        bind();
    }

    private void bind() {
        eventBus.addHandler(AfterSaveEvent.TYPE, new AfterSaveEvent.Handler() {
            @Override
            public void onEvent(AfterSaveEvent event) {
                dataModel = event.getModel();
                processStatus();
            }
        });
        eventBus.addHandler(ModelLoadedEvent.TYPE, new ModelLoadedEvent.Handler() {
            @Override
            public void onEvent(ModelLoadedEvent event) {
                dataModel = event.getModel();
                processStatus();
            }
        });
        approveButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                processButtonClick(ProgramStatus.APPROVED);
            }
        });
        activateButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                processActivateClick();
            }
        });
        exitAnchor.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                HistoryManager.navigate(AppLocations.Locations.CURRICULUM_MANAGEMENT.getLocation());
            }
        });
    }

    private void processStatus() {
        processStatus(ProgramStatus.of(dataModel));
    }

    private void processButtonClick(ProgramStatus status) {
        eventBus.fireEvent(new StateChangeEvent(status));
    }

    private void processActivateClick() {
        String versionFromId = dataModel.get(ProgramConstants.VERSION_FROM_ID);
        if (versionFromId != null) {
            activateSection.updateWidgetData(dataModel);
            activateDialog.show();
        } else {
            processButtonClick(ProgramStatus.ACTIVE);
        }
    }

    private void processStatus(ProgramStatus programStatus) {
        if (programStatus == ProgramStatus.DRAFT) {
            enableButtons(true, false);
        } else if (programStatus == ProgramStatus.APPROVED) {
            enableButtons(false, true);
        } else if (programStatus == ProgramStatus.ACTIVE) {
            enableButtons(false, false);
        } else if (programStatus == ProgramStatus.SUPERSEDED) {
            content.setVisible(false);
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

        buildActivateDialog();
    }

    private void buildActivateDialog() {
        FlowPanel panel = new FlowPanel();

        panel.add((Widget) activateSection);

        KSButton activate = new KSButton(getLabel(ProgramMsgConstants.BUTTON_ACTIVATE), new ClickHandler() {
            public void onClick(ClickEvent event) {
                activateSection.updateModel(dataModel);
                dataModel.validate(new Callback<List<ValidationResultInfo>>(){
					@Override
					public void exec(List<ValidationResultInfo> results) {
						// Process the results on the additional fields view
                        if (ErrorLevel.OK.equals(activateSection.processValidationResults(results))) {
                            // Call activate once the data is valid
    						processButtonClick(ProgramStatus.ACTIVE);
    		                activateDialog.hide();
                        } else {
                            KSNotifier.add(new KSNotification("Unable to save, please check fields for errors.", false, true, 5000));
                        }

					}
                });
            }
        });
        activateDialog.addButton(activate);

        KSButton cancel = new KSButton(getLabel(ProgramMsgConstants.COMMON_CANCEL), ButtonStyle.ANCHOR_LARGE_CENTERED, new ClickHandler() {
            public void onClick(ClickEvent event) {
                activateDialog.hide();
            }
        });
        activateDialog.addButton(cancel);

        activateDialog.setWidget(panel);
    }

    private void enableButtons(boolean enableApprove, boolean enableActivate) {
        approveButton.setEnabled(enableApprove);
        activateButton.setEnabled(enableActivate);
    }
    
    protected String getLabel(String messageKey) {
        return Application.getApplicationContext().getUILabel(ProgramMsgConstants.PROGRAM_MSG_GROUP, messageKey);
    }
}
