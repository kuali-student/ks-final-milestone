/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.core.organization.ui.client.view;

import java.util.List;

import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.core.organization.dto.OrgTreeInfo;
import org.kuali.student.core.organization.ui.client.service.OrgRpcService;
import org.kuali.student.core.organization.ui.client.service.OrgRpcServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.visualization.client.AjaxLoader;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.Selection;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.events.SelectHandler;
import com.google.gwt.visualization.client.visualizations.OrgChart;
import com.google.gwt.visualization.client.visualizations.OrgChart.Options;

public class OrgChartWidget extends Composite {
    
    private OrgRpcServiceAsync orgRpcServiceAsync = GWT.create(OrgRpcService.class);
    
    DeckPanel w = new DeckPanel();
	ScrollPanel root = new ScrollPanel();
	String orgId;
	String hierarchyId;
	int maxLevels;
	boolean loaded = false;
	
	public OrgChartWidget(String orgId, String hierarchyId, int maxLevels) {
		super.initWidget(w);
		w.add(root);

		root.setStyleName("ks-orgChart");
		this.orgId=orgId;
		this.hierarchyId=hierarchyId;
		this.maxLevels=maxLevels;

        KSLabel lbl = new KSLabel("Please Wait...");
        w.add(lbl);
        w.showWidget(1);
	}

	protected void onLoad() {
	    if (!loaded){
            Runnable onLoadCallback = new Runnable() {
                public void run() {

                	orgRpcServiceAsync.getOrgDisplayTree(orgId, hierarchyId, maxLevels, new AsyncCallback<List<OrgTreeInfo> >(){

    					public void onFailure(Throwable caught) {
    						Window.alert(caught.getMessage());
    					}

    					public void onSuccess(List<OrgTreeInfo>  results) {

    						final DataTable data = DataTable.create();
    	                    data.addColumn(ColumnType.STRING, "OrgId");
    	                    data.addColumn(ColumnType.STRING, "ParentOrgId");
    						data.addRows(results.size() + 1);

    						int lineCount=0;

    						for(OrgTreeInfo orgTreeInfo:results){
     							data.setCell(lineCount, 0, orgTreeInfo.getOrgId(), orgTreeInfo.getDisplayName(), null);

    							if(orgTreeInfo.getParentId()!=null && !"".equals(orgTreeInfo.getParentId())){
    			                	data.setCell(lineCount, 1, orgTreeInfo.getParentId(), null, null);
    			                }

    							lineCount++;
    		               	}

    	                    final Options orgChartOpts = Options.create();
    	                    orgChartOpts.setOption("size", "small");
    	                    final OrgChart o = new OrgChart(data, orgChartOpts);

    	                    o.addSelectHandler(new SelectHandler(){

    							public void onSelect(SelectEvent event) {
    								//Called after the user selects a chart element
    								JsArray<Selection> selections = o.getSelections();
		        					if (selections.length() != 1) {
		        						Window.alert("Logic error. No Organization selected");
		        						return;
		        					}
    		                       
    							}

    	                    });

    	                    root.add(o);
    	                    while(w.getWidgetCount() != 1)
    	                        w.remove(w.getWidgetCount() - 1);
    	                    w.showWidget(0);


    					}
                	});
                }
              };

              // Load the visualization api, passing the onLoadCallback to be called
              // when loading is done.
              AjaxLoader.loadVisualizationApi(onLoadCallback, OrgChart.PACKAGE);

              loaded=true;
	    } else {
            while(w.getWidgetCount() != 1)
                w.remove(w.getWidgetCount() - 1);
            w.showWidget(0);
	    }

	}
}
