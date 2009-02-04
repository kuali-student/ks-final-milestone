package org.kuali.student.core.organization.web.client.view;

import org.kuali.student.core.organization.web.client.service.OrgRpcService;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.visualization.client.AjaxLoader;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.visualizations.OrgChart;
import com.google.gwt.visualization.client.visualizations.OrgChart.Options;

public class OrgChartWidget extends Composite {
	SimplePanel root = new SimplePanel();
	String orgId;
	String hierarchyId;
	int maxLevels;
	
	public OrgChartWidget(String orgId, String hierarchyId, int maxLevels) {
		super.initWidget(root);
		this.orgId=orgId;
		this.hierarchyId=hierarchyId;
		this.maxLevels=maxLevels;
	}

	protected void onLoad() {
        Runnable onLoadCallback = new Runnable() {
            public void run() {
                	
            	OrgRpcService.Util.getInstance().getOrgDisplayTree(orgId, hierarchyId, maxLevels, new AsyncCallback<String>(){
 
					public void onFailure(Throwable caught) {
						Window.alert(caught.getMessage());
					}

					public void onSuccess(String result) {
						DataTable data = DataTable.create();
	                    data.addColumn(ColumnType.STRING, "Name");
	                    data.addColumn(ColumnType.STRING, "Manager");

						int lineCount=0;
		               	//Result format is nodeId,nodeName,parentNodeId|nodeId,nodeName,parentNodeId|nodeId,nodeName,parentNodeId
						for(String line:result.split("\\|")){
		               		
							String[] values = line.split(",");
		               		
							String nodeId=values[0];
		               		String nodeName=values[1];
		               				               		
		               		data.addRow();
							data.setCell(lineCount, 0, nodeId, nodeName, null);
			                
							if(lineCount>0){
			                	String nodeParentId=values[2];
			                	data.setCell(lineCount, 1, nodeParentId, null, null);		               		
			                }
			                
							lineCount++;
		               	}
						
	                    Options orgChartOpts = Options.create();
	                    OrgChart o = new OrgChart(data, orgChartOpts);
	                    
	                    root.add(o);
	                    	                    
					}
            	});
            }
          };

          // Load the visualization api, passing the onLoadCallback to be called
          // when loading is done.
          AjaxLoader.loadVisualizationApi(onLoadCallback, OrgChart.PACKAGE);
	}
}
