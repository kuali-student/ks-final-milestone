package org.kuali.student.common.ui.client.demo;

import org.kuali.student.common.ui.client.widgets.headers.KSDocumentHeader;
import org.kuali.student.common.ui.client.widgets.headers.KSHeader;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class HeaderDemo extends Composite{
    public HeaderDemo(){
        super.initWidget(createDemoPage());
    }
    private Widget createDemoPage(){
        VerticalPanel p = new VerticalPanel();
        
        p.add(new KSHeader());
        
        KSDocumentHeader ksDocumentHeader = new KSDocumentHeader();
        ksDocumentHeader.setInfo("You have unsaved changes.");
        ksDocumentHeader.setTitle("Title");
        ksDocumentHeader.addWidget(new Label("Status: Draft"));     
        p.add(ksDocumentHeader);
        return p;
    }
}
