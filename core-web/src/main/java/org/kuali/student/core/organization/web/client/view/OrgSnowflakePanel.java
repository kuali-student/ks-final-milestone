package org.kuali.student.core.organization.web.client.view;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.netthreads.gwt.jsviz.client.IGraphRender;
import com.netthreads.gwt.jsviz.client.gwt.JSVizPanel;

public class OrgSnowflakePanel extends Composite{
    
	VerticalPanel vPanel = new VerticalPanel();
    
	String nodeXML;

    public OrgSnowflakePanel(String nodeXML) {
		super.initWidget(vPanel);
		this.nodeXML=nodeXML;
	}

      protected void onLoad(){        
        this.setHeight("200");
    	  IGraphRender render = new JSVizOrgSnowflake(nodeXML);
        
        JSVizPanel graphPanel= new JSVizPanel("50", "50", render);
        graphPanel.getElement().setId("PANEL ID");
        vPanel.add(graphPanel);
        vPanel.setBorderWidth(2);
    }

}
