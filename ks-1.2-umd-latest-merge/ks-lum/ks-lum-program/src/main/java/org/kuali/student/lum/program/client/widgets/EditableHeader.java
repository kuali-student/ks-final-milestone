package org.kuali.student.lum.program.client.widgets;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSButtonAbstract;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.lum.program.client.ProgramMsgConstants;
import org.kuali.student.lum.program.client.events.ChangeViewEvent;

/**
 * @author Igor
 */
public class EditableHeader extends Composite {

    private FlowPanel content = new FlowPanel();

    private KSLabel sectionTitle;

    private KSButton editButton = new KSButton(getLabel(ProgramMsgConstants.COMMON_EDIT), KSButtonAbstract.ButtonStyle.DEFAULT_ANCHOR);

    private Enum<?> viewToken;

    private HandlerManager eventBus;

    public EditableHeader(String title, Enum<?> viewToken, HandlerManager eventBus) {
        initWidget(content);
        this.eventBus = eventBus;
        this.viewToken = viewToken;
        sectionTitle = new KSLabel(title);
        buildLayout();
        setStyles();
        bind();
    }

    private void bind() {
        editButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                eventBus.fireEvent(new ChangeViewEvent(viewToken));
            }
        });
    }

    private void setStyles() {
        sectionTitle.addStyleName("sectionTitle");
        content.addStyleName("editableHeader");
        editButton.addStyleName("sectionEditLink");
    }

    private void buildLayout() {
        content.add(sectionTitle);
        content.add(editButton);
    }
    
    protected String getLabel(String messageKey) {
        return Application.getApplicationContext().getUILabel(ProgramMsgConstants.PROGRAM_MSG_GROUP, messageKey);
    }
}
