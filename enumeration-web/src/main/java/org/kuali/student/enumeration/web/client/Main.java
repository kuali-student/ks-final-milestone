package org.kuali.student.enumeration.web.client;




import org.kuali.student.enumeration.dto.EnumerationMeta;
import org.kuali.student.enumeration.dto.EnumerationMetaList;
import org.kuali.student.enumeration.web.client.service.EnumerationGWTService;
import org.kuali.student.enumeration.web.client.view.FetchEnumerationMetasPanel;
import org.kuali.student.enumeration.web.client.view.FetchEnumerationPanel;
import org.kuali.student.enumeration.web.client.view.RemoveEnumeratedValuePanel;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

public class Main implements EntryPoint {

    public Main(){
   
    }
    public void onModuleLoad() {
      RootPanel.get().add(new FetchEnumerationMetasPanel());
        
      RootPanel.get().add(new HTML("<HR>"));
      RootPanel.get().add(new RemoveEnumeratedValuePanel());
      RootPanel.get().add(new HTML("<HR>"));
      RootPanel.get().add(new FetchEnumerationPanel());
/*
  EnumeratedValue addEnumeratedValue(String enumerationKey,EnumeratedValue value){
  EnumeratedValue updateEnumeratedValue(String enumerationKey,String code,EnumeratedValue value){
*/
    }
}