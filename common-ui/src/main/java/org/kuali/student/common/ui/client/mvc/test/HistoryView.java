package org.kuali.student.common.ui.client.mvc.test;

import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.ViewComposite;
import org.kuali.student.common.ui.client.mvc.events.ViewChangeEvent;
import org.kuali.student.common.ui.client.mvc.events.ViewChangeHandler;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSLabel;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class HistoryView extends ViewComposite {
    private final VerticalPanel panel = new VerticalPanel();
    private final KSLabel label = new KSLabel("Notice that the history only contains view change events that are local to the controller, no child or parent view change events are captured.  This is because the ViewChangeEvent is a checked event.");
    private final ListBox history = new ListBox();
    private final KSButton reset = new KSButton("Reset History", new ClickHandler() {
        public void onClick(ClickEvent event) {
            history.clear();
        }
    });

    public HistoryView(Controller controller) {
        super(controller, "History");
        super.initWidget(panel);
        panel.add(label);
        panel.add(history);
        panel.add(reset);
        controller.addApplicationEventHandler(ViewChangeEvent.TYPE, new ViewChangeHandler() {
            public void onViewChange(ViewChangeEvent event) {
                history.addItem(event.getNewView().getName());
            }
        });
    }

}
