package org.kuali.student.ui.kitchensink.client.kscommons.blockingprogressindicator;

import static org.kuali.student.ui.kitchensink.client.KitchenSinkStyleConstants.STYLE_EXAMPLE;

import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.progress.BlockingTask;
import org.kuali.student.common.ui.client.widgets.progress.KSBlockingProgressIndicator;
import org.kuali.student.ui.kitchensink.client.kscommons.KSWidgetFactory;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

public class BlockingProgressIndicatorExample extends Composite {

    final VerticalPanel main = new VerticalPanel();
    final BlockingTask t = new BlockingTask("Task in progress.....");
    
    private final static int INTERVAL = 3000;

    final KSLabel label = KSWidgetFactory.getLabelInstance("Click the button to show the progress indicator for " + INTERVAL/1000 + " seconds" , true);
    final KSButton button;

    public BlockingProgressIndicatorExample() {

        main.addStyleName(STYLE_EXAMPLE);

        button = KSWidgetFactory.getButtonInstance("Click Me", 
                new ClickHandler () {

            @Override
            public void onClick(ClickEvent arg0) {
                KSBlockingProgressIndicator.addTask(t);
                
                Timer timer = new Timer() {
                    public void run() {
                        KSBlockingProgressIndicator.removeTask(t);
                    }
                };
                timer.schedule(INTERVAL);

            }

        });

        main.add(label);
        main.add(button);

        super.initWidget(main);
    }


}
