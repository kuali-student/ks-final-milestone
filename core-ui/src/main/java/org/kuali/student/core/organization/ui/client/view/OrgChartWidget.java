package org.kuali.student.core.organization.ui.client.view;

import java.util.List;

import org.kuali.student.core.organization.dto.OrgInfo;
import org.kuali.student.core.organization.dto.OrgTreeInfo;
import org.kuali.student.core.organization.ui.client.service.OrgRpcService;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.visualization.client.AjaxLoader;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.events.Handler;
import com.google.gwt.visualization.client.events.SelectHandler;
import com.google.gwt.visualization.client.visualizations.OrgChart;
import com.google.gwt.visualization.client.visualizations.OrgChart.Options;

public class OrgChartWidget extends Composite {
	ScrollPanel root = new ScrollPanel();
	String orgId;
	String hierarchyId;
	int maxLevels;
	boolean loaded = false;
	
	public OrgChartWidget(String orgId, String hierarchyId, int maxLevels) {
		super.initWidget(root);
		root.setStyleName("ks-orgChart");
		this.orgId=orgId;
		this.hierarchyId=hierarchyId;
		this.maxLevels=maxLevels;
	}

	protected void onLoad() {
	    if (!loaded){
            Runnable onLoadCallback = new Runnable() {
                public void run() {
                    	
                	OrgRpcService.Util.getInstance().getOrgDisplayTree(orgId, hierarchyId, maxLevels, new AsyncCallback<List<OrgTreeInfo> >(){
     
    					public void onFailure(Throwable caught) {
    						Window.alert(caught.getMessage());
    					}
    
    					public void onSuccess(List<OrgTreeInfo>  results) {
                            
    						final DataTable data = DataTable.create();
    	                    data.addColumn(ColumnType.STRING, "Name");
    	                    data.addColumn(ColumnType.STRING, "Manager");
    
    						int lineCount=0;
    
    						for(OrgTreeInfo orgTreeInfo:results){
    		               		
    		               		data.addRow();
    							data.setCell(lineCount, 0, orgTreeInfo.getOrgId(), orgTreeInfo.getDisplayName(), null);
    			                
    							if(orgTreeInfo.getParentId()!=null && !"".equals(orgTreeInfo.getParentId())){
    			                	data.setCell(lineCount, 1, orgTreeInfo.getParentId(), null, null);		               		
    			                }
    			                
    							lineCount++;
    		               	}
    						
    	                    final Options orgChartOpts = Options.create();
    	                    final OrgChart o = new OrgChart(data, orgChartOpts);
    	                    
    	                    Handler.addHandler(o, "select", new SelectHandler(){
    
    							public void onSelect(SelectEvent event) {
    			                    OrgCreatePanel.showPopup(OrgCreatePanel.CREATE_ORG_ALL, data.getValueString(o.getSelections().get(0).getRow(), 0), "Edit", new SelectionHandler<OrgInfo>(){
    			                        @Override
    			                        public void onSelection(SelectionEvent<OrgInfo> event) {
    			                            data.setFormattedValue(o.getSelections().get(0).getRow(), 0, event.getSelectedItem().getLongName());
    			                            o.draw(data, orgChartOpts);
    			                        }
    			                    });

    							}
    	                    	
    	                    });
    	                    
    	                    root.add(o);
    	                    	                    
    					}
                	});
                }
              };

              // Load the visualization api, passing the onLoadCallback to be called
              // when loading is done.
              AjaxLoader.loadVisualizationApi(onLoadCallback, OrgChart.PACKAGE);
              
              loaded=true;
	    }
	}
}
