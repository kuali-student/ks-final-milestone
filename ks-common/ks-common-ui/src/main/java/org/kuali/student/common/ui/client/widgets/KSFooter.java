package org.kuali.student.common.ui.client.widgets;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;

public class KSFooter  extends Composite{
   FlowPanel contentPanel = new FlowPanel();
   FlowPanel firstLinePanel = new FlowPanel();
   FlowPanel secondLinePanel = new FlowPanel();
   public KSFooter(){
       super.initWidget(contentPanel);
       contentPanel.setStyleName("KS-Footer");
       firstLinePanel.setStyleName("KS-Footer-Line");
       secondLinePanel.setStyleName("KS-Footer-Line");
       
       contentPanel.add(firstLinePanel);
       contentPanel.add(secondLinePanel);
       
       KSSpan copyrightLabel = new KSSpan("Copyright 2010 Kuali Reference University");
       firstLinePanel.add(copyrightLabel);
       
       firstLinePanel.add(new KSSpan("|"));

       KSSpan contactAdminLabel = new KSSpan("Contact Kuali Student Administrator");
       firstLinePanel.add(contactAdminLabel);

       firstLinePanel.add(new KSSpan("|"));
       
       KSSpan feedbackLabel = new KSSpan("Feedback");
       firstLinePanel.add(feedbackLabel);

       firstLinePanel.add(new KSSpan("|"));
       
       KSSpan faqLabel = new KSSpan("FAQ");
       firstLinePanel.add(faqLabel);
       firstLinePanel.add(new KSSpan("|"));

       KSSpan helpLabel = new KSSpan("Help");
       firstLinePanel.add(helpLabel);

       KSSpan addressLabel = new KSSpan("Kuali Reference Uniersity One University Drive, Southside, Ca, 90045");
       secondLinePanel.add(addressLabel);
   }
}
