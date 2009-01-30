package org.kuali.student.core.organization.web.client.view;

/*
 * Copyright 2007 Alistair Rutherford 
 * 
 * http://code.google.com/p/gwt-jsviz/
 * http://www.netthreads.co.uk
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.netthreads.gwt.jsviz.client.DOMEx;
import com.netthreads.gwt.jsviz.client.DataGraphNode;
import com.netthreads.gwt.jsviz.client.EdgeProperties;
import com.netthreads.gwt.jsviz.client.EventHandler;
import com.netthreads.gwt.jsviz.client.IGraphRender;
import com.netthreads.gwt.jsviz.client.JavaScriptObjectHelper;
import com.netthreads.gwt.jsviz.client.Layout;
import com.netthreads.gwt.jsviz.client.NodeConfig;
import com.netthreads.gwt.jsviz.client.SnowflakeNode;
import com.netthreads.gwt.jsviz.client.Timer;
import com.netthreads.gwt.jsviz.client.XMLTreeLoader;
import com.netthreads.gwt.jsviz.client.gwt.JSVizPanel;


/**
 * Render graph
 * 
 */
public class JSVizOrgSnowflake implements IGraphRender
{
	String nodeXML;
	JSVizPanel panel = null;

	
	public JSVizOrgSnowflake(String nodeXML) {
		this.nodeXML=nodeXML;
	}

	public Layout create(JSVizPanel panel)
	{
    	this.panel = panel;
    	
        Layout layout = Layout.create(Layout.LAYOUT_SNOWFLAKE, panel.getElement(), true);

        // Assign callbacks
        layout.initialise(this);
        
		return layout;
	}
	
    /**
     * Create custom elements.
     *
     * @param panel Graph rendered into this panel.
     */
    public void render(JSVizPanel panel)
    {
    	Layout layout = panel.getLayout();
    	
    	XMLTreeLoader loader = XMLTreeLoader.create(layout.getDataGraph());
		//loader.load("data/treedata1.xml" );
    	
    	
		loader.loadString(nodeXML);
		
		
		// Attach timer
		Timer buildTimer = Timer.create(0);
		buildTimer.subscribe(layout);
		buildTimer.start();

    }

    /**
     * model - callback hooked into jsviz code
     *  
     */
    public NodeConfig model(DataGraphNode dataNode)
    {
    	NodeConfig config = NodeConfig.create();
    	config.setChildRadius(100);
    	int angle = dataNode.isRoot()?360:100;
    	config.setFanAngle(angle);
    	config.setRootAngle(0);
		return config;
    }

    /**
     * view - callback hooked into jsviz code
     *  
     */
    public Element view(DataGraphNode dataNode, SnowflakeNode modelNode)
    {
    	Element nodeElement = null;
    	
        if (panel.getLayout().hasSVG())
        {
        	// SVG needs 'setElementAttribute', the DOM.setAttribute call has no effect. 
            nodeElement = DOMEx.createElementNS("http://www.w3.org/2000/svg", "circle");
            JavaScriptObjectHelper.setElementAttribute(nodeElement, "stroke", "#888888");
            JavaScriptObjectHelper.setElementAttribute(nodeElement, "stroke-width", ".25px");

            JavaScriptObjectHelper.setElementAttribute(nodeElement, "fill", dataNode.getColor());
            JavaScriptObjectHelper.setElementAttribute(nodeElement, "r", "6px");

            EventHandler eventHandler = EventHandler.create(panel.getLayout(), modelNode);
            JavaScriptObjectHelper.setAttribute(nodeElement, "onmousedown", eventHandler);
        }
        else
        {
        	Label label=new Label(dataNode.getNodeName());
        	final String nodeName = dataNode.getNodeName();
        	final String nodeId = dataNode.getNodeId();
        	label.addClickListener(new ClickListener(){
				public void onClick(Widget sender) {
					Window.alert("Clicked "+ nodeName + " with id: " + nodeId);
				}
        	});
        	nodeElement = label.getElement();
            
            DOM.setStyleAttribute(nodeElement, "position", "absolute");
            panel.add(label);
            EventHandler eventHandler = EventHandler.create(panel.getLayout(), modelNode);
            JavaScriptObjectHelper.setAttribute(nodeElement, "onmousedown", eventHandler);
        }
        
        return nodeElement;
    }

   
    /**
     * viewEdgeBuilder - callback hooked into jsviz code
     *  
     */
    public EdgeProperties viewEdgeBuilder(DataGraphNode dataNodeSrc, DataGraphNode dataNodeDest)
    {
    	EdgeProperties properties = EdgeProperties.create();
    	
        if (panel.getLayout().hasSVG())
        {
        	properties.setStroke(dataNodeSrc.getColor());
        	properties.setStrokeWidth("2px");
        	properties.setStrokeDashArray("1,1");
        	assert(false);
		} 
        else 
		{        	
//        	properties.setPixelColor("#000000");
//        	properties.setPixelWidth("1px");
//        	properties.setPixelHeight("1px");
        	
		}
        
        return properties;
	}
}
