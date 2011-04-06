package org.kuali.student.lum.lu.ui.browseprogram.client.views;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.kuali.student.common.assembly.data.LookupMetadata;
import org.kuali.student.common.assembly.data.Metadata;
import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.application.KSAsyncCallback;
import org.kuali.student.common.ui.client.application.ViewContext;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.DataModelDefinition;
import org.kuali.student.common.ui.client.mvc.ViewComposite;
import org.kuali.student.common.ui.client.service.MetadataRpcService;
import org.kuali.student.common.ui.client.service.MetadataRpcServiceAsync;
import org.kuali.student.common.ui.client.widgets.filter.FilterEvent;
import org.kuali.student.common.ui.client.widgets.filter.FilterEventHandler;
import org.kuali.student.common.ui.client.widgets.filter.FilterResetEventHandler;
import org.kuali.student.common.ui.client.widgets.filter.KSFilterOptions;
import org.kuali.student.common.ui.client.widgets.layout.HorizontalBlockFlowPanel;
import org.kuali.student.common.ui.client.widgets.progress.BlockingTask;
import org.kuali.student.common.ui.client.widgets.progress.KSBlockingProgressIndicator;
import org.kuali.student.common.ui.client.widgets.searchtable.ResultRow;
import org.kuali.student.common.ui.shared.IdAttributes.IdType;
import org.kuali.student.lum.lu.ui.tools.client.widgets.BrowsePanel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;


public class BrowseProgramView extends ViewComposite {
    
	private final BlockingTask initializingTask = new BlockingTask("Loading");
    protected MetadataRpcServiceAsync metadataServiceAsync = GWT.create(MetadataRpcService.class);
    
	private HorizontalBlockFlowPanel layout = new HorizontalBlockFlowPanel();
	protected KSFilterOptions dependencyFilter;
	protected DataModelDefinition searchDefinition;
	protected boolean initialized = false;
	BrowsePanel browsePanel;
	
	public BrowseProgramView(Controller controller, String name,
			Enum<?> viewType) {
		super(controller, name, viewType);
	}

	private void init() {
		initWidget(layout);
		Metadata metaData;
        
        
        List<LookupMetadata> lookups = new ArrayList<LookupMetadata>();
        metaData = searchDefinition.getMetadata("filter");
        lookups.add(metaData.getInitialLookup());
        
        dependencyFilter = new KSFilterOptions(metaData.getAdditionalLookups());
        dependencyFilter.addFilterEventHandler(new FilterEventHandler(){
			@Override
			public void onDeselect(FilterEvent e) {
				handleSelections(e.getSelections());
			}
			@Override
			public void onSelect(FilterEvent e) {
				handleSelections(e.getSelections());
			}
       	
        });
        
        dependencyFilter.addFilterResetEventHandler(new FilterResetEventHandler(){
			@Override
			public void onReset() {
				handleSelections(null);				
			}        	
        });
		
        layout.add(dependencyFilter);
        
		metaData = searchDefinition.getMetadata("search");
		
		browsePanel = new BrowsePanel(metaData.getInitialLookup());
		
		browsePanel.setOnSelectectedCallback(new ViewCourseCallback());
		layout.add(browsePanel);
		
		browsePanel.executeSearch(new Callback<Boolean>(){
			@Override
			public void exec(Boolean result) {
				//after search, load up the filter information
			}
		});
	}   

	private void handleSelections(Map<String, List<String>> selections) {
		if(selections == null || selections.isEmpty()){
			browsePanel.showAllRows();
		}else{
			HashSet<String> rowKeys = new HashSet<String>();
			for(ResultRow resultRow:browsePanel.getAllResultRows()){
				boolean matches = true;
				for(Entry<String, List<String>> entry:selections.entrySet()){
					String entryKey = entry.getKey();
					String rowValue = resultRow.getValue(entryKey);
					List<String> entryValue = entry.getValue();
					if(entryValue!=null){
						if("lu.resultColumn.resultComponentId".equals(entryKey)||
	                       "lu.resultColumn.luOptionalCampusLocation".equals(entryKey)){
							String[] rowValues = rowValue.split("<br/>");
							ArrayList<String> intersection = new ArrayList<String>(entryValue);
							intersection.retainAll(Arrays.asList(rowValues));
							if(intersection.isEmpty()){
								matches = false;
								break;
							}
						}else if(!entryValue.contains(rowValue)){
							matches = false;
							break;
						}
					}
				}
				if(matches){
					rowKeys.add(resultRow.getId());
				}
			}
			
			browsePanel.showOnlyRows(rowKeys);
		}
		

		
	}
	
	@Override
	public void beforeShow(final Callback<Boolean> onReadyCallback) {
        if (!initialized) {
    		KSBlockingProgressIndicator.addTask(initializingTask);

    		//This loads search definitions for the dependency analysis search 
            metadataServiceAsync.getMetadata("browseProgram", null, null, new KSAsyncCallback<Metadata>(){

                @Override
                public void handleFailure(Throwable caught) {
                    KSBlockingProgressIndicator.removeTask(initializingTask);
                    throw new RuntimeException("Failed to load search definitions.", caught);                        
                }

                @Override
                public void onSuccess(Metadata result) {
                	searchDefinition = new DataModelDefinition(result);
                	init();
                	onReadyCallback.exec(true);                	
                    initialized = true;
                    KSBlockingProgressIndicator.removeTask(initializingTask);
                }
            
            });	        
        } else {
        	onReadyCallback.exec(true);
        }
	}
	
	private class ViewCourseCallback implements BrowsePanel.OnSelectedCallback {

		@Override
		public void selected (List<String> selectedIds)	{
			if (selectedIds.size () == 0) {
				Window.alert ("Please select a row before clicking");
				return;
			}
			ViewContext viewContext = new ViewContext ();
			viewContext.setId (selectedIds.get (0));
			viewContext.setIdType (IdType.OBJECT_ID);
			Application.navigate("/HOME/CURRICULUM_HOME/PROGRAM_VIEW", viewContext);
		}
	}




}
