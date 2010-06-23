package org.kuali.student.common.ui.client.demo;

import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSLightBox;
import org.kuali.student.common.ui.client.widgets.KSButtonAbstract.ButtonStyle;
import org.kuali.student.common.ui.client.widgets.progress.BlockingTask;
import org.kuali.student.common.ui.client.widgets.progress.KSBlockingProgressIndicator;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;


public class KSLightBoxDemo extends Composite{
   public KSLightBoxDemo(){
       super.initWidget(createDemoPage());
   }
   private Widget createDemoPage(){
       VerticalPanel p = new VerticalPanel();
       
       
       KSButton button = new KSButton("Lightbox", new ClickHandler() {
           @Override
           public void onClick(ClickEvent event) {
               final KSLightBox box = new KSLightBox(new HTML("Proposal Comments"));
              // box.setWidget(new Label("asdf"));
               box.setSize(400, 200);
               box.show();
               box.setWidget(new Label("asdf"));
               
           }
       });
       p.add(button);
       
       button = new KSButton("KSBlockingProgressIndicator", new ClickHandler() {
           @Override
           public void onClick(ClickEvent event) {
               KSBlockingProgressIndicator.addTask(new BlockingTask("this is a test."));
           }
       });
       p.add(button);
       
       p.add(new KSButton("Lightbox", ButtonStyle.SECONDARY));
       
       return p;
   }
}
