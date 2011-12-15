package org.kuali.student.common.ui.client.widgets;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;

public class KSFooter  extends Composite{
   FlowPanel contentPanel = new FlowPanel();
   KSLabel firstLinePanel = new KSLabel();
   HTMLPanel secondLinePanel;
   public KSFooter(){
       firstLinePanel.setText("Copyright 2005-2010 The Kuali Foundation.  All Rights Reserved.");
       secondLinePanel = new HTMLPanel("Portions of Kuali are copyrighted by other parties as described in the " +
    		   "<a href='#/HOME/ACKNOWLEDGEMENTS'>Acknowledgements</a> screen.");
       contentPanel.add(firstLinePanel);
       contentPanel.add(secondLinePanel);
       contentPanel.setStyleName("KS-Footer");
       firstLinePanel.setStyleName("KS-Footer-Line1");
       secondLinePanel.setStyleName("KS-Footer-Line");
       super.initWidget(contentPanel);
   }
}
