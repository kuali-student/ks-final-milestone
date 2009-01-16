package org.kuali.student.enumeration.web.client;

import org.kuali.student.enumeration.web.client.view.EnumerationMetaPanel;
import org.kuali.student.enumeration.web.client.view.AddUpdateEnumeratedValuePanel;
import org.kuali.student.enumeration.web.client.view.FetchEnumerationPanel;
import org.kuali.student.enumeration.web.client.view.RemoveEnumeratedValuePanel;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;

public class Main implements EntryPoint {

    public Main(){
   
    }
    public void onModuleLoad() {
      RootPanel.get().add(new EnumerationMetaPanel());
      RootPanel.get().add(new HTML("<HR>"));
      RootPanel.get().add(new RemoveEnumeratedValuePanel());
      RootPanel.get().add(new HTML("<HR>"));
      RootPanel.get().add(new FetchEnumerationPanel());
      RootPanel.get().add(new HTML("<HR>"));
      RootPanel.get().add(new AddUpdateEnumeratedValuePanel());
    }
}